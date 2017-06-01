package com.svds.data_platform_tutorial

import com.datastax.spark.connector._
import com.datastax.spark.connector.streaming._
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamTransformFinal extends LazyLogging {
  
  def main(args: Array[String]): Unit = {
      
    val conf = new SparkConf()
      .setMaster("local[4]")
      .setAppName("Streaming transform")
      .set("spark.cassandra.connection.host", "cassandra")
    val ssc = new StreamingContext(conf, Seconds(5))
    
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "kafka:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "demo",
      "auto.offset.reset" -> "latest", // earliest, none
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    
    val numPartitions = 3
    val sub = Subscribe[String, String](Array("test_topic"), kafkaParams)
    val stream = KafkaUtils.createDirectStream[String, String](ssc, PreferConsistent, sub)
      .map(record => record.value)
      .map(Converters.base64ToTweet)
      .repartition(numPartitions)

    stream.foreachRDD(rdd => logger.info(s"Got ${rdd.count()} tweets"))

    // Write raw tweets to Cassandra
    stream
      .map(tweet => (tweet.getId, tweet.getCreatedAt.getTime, tweet.getUser.getScreenName, tweet.getText))
      .saveToCassandra("demo", "raw_tweets")

    val searchTerms = List("nba", "nfl")
    stream
      .map(_.getText)
      .transform(Helpers.termSearchRDD(searchTerms) _)
      .window(Seconds(60))
      .countByValue(numPartitions)
      .foreachRDD{ (rddCounts, time) =>
        logger.info(s"Windowed counts: ${rddCounts.collectAsMap()}")
        rddCounts.map(x => (time.milliseconds, x._1, x._2.toInt)).saveToCassandra("demo", "window_snapshots")
      }

    logger.info("Starting streaming context")
    ssc.start()
    ssc.awaitTermination()
  }
}
