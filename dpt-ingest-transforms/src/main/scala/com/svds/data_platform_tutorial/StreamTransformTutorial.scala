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

object StreamTransformTutorial extends LazyLogging {
  
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
    val sub = null // define subscription
    val stream = KafkaUtils.createDirectStream[String, String](ssc, PreferConsistent, sub)
      // map to tweet objects and repartition

    stream.foreachRDD(rdd => logger.info(s"Got ${rdd.count()} tweets"))

    // Write raw tweets to Cassandra
    stream
      // map to required schema
      .saveToCassandra("demo", "raw_tweets")

    val searchTerms = List("nba", "nfl")
    stream
      // get text of tweet
      // search for terms
      // windowed counts
      .foreachRDD{ (rddCounts, time) =>
        // logger.info(s"Windowed counts: ${rddCounts.collectAsMap()}")
        // map to required schema and save to cassandra window_snapshots table
      }

    logger.info("Starting streaming context")
    // start the stream and wait
  }
}
