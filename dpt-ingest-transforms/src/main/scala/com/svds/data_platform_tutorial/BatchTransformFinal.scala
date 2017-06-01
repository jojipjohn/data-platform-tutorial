package com.svds.data_platform_tutorial

import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession

object BatchTransformFinal extends LazyLogging {
  
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[4]")
      .appName("Batch Transform Tutorial")
      .config("spark.cassandra.connection.host", "cassandra")
      .getOrCreate()

    import spark.implicits._

    val raw = spark.read
      .format("kafka")
      .option("kafka.bootstrap.servers", "kafka:9092")
      .option("subscribe", "test_topic")
      .load()

    logger.info("Raw input looks like:")
    raw.show()

    val tweets = raw
      .filter("key is not null") // ignore console producer messages
      .selectExpr("CAST(value AS STRING)")
      .as[String]
      .map { base64Tweet =>
        val tweet = Converters.base64ToTweet(base64Tweet)
        (tweet.getId, tweet.getCreatedAt.getTime, tweet.getUser.getScreenName, tweet.getText.filter(_ >= ' '))
      }
      .toDF("id", "when", "sender", "value")

    spark.udf.register("termSearch", Helpers.termSearch("nba", "nfl"))

    val withTerm = tweets.selectExpr("id", "when", "sender", "explode(termSearch(value)) as term", "value")

    logger.info("Transformed looks like:")
    withTerm.show()

    withTerm.write.option("header", "true").mode("overwrite").csv("tweets-batch")

    spark.stop()
  }
}
