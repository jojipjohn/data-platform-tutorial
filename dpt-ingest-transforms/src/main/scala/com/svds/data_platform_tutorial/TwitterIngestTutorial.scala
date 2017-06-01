package com.svds.data_platform_tutorial

import java.util.Properties
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.twitter.TwitterUtils


object TwitterIngestTutorial extends LazyLogging {

  def main(args: Array[String]): Unit = {

    Helpers.loadTwitterAuthFromFile("/full/path/of/your/twitter/creds/properties")
    Helpers.validateTwitterEnv()

    // set up context
    // create stream
    // process stream
    // some housekeeping
  }

  def publishTweets(tweets: RDD[(Long, String)]): Unit = {
  }

  case class KafkaWriter(brokers: String, topic: String) extends LazyLogging {
    private val config = new Properties() {
      // add configuration settings here.
    }

    val producer = new KafkaProducer[String, String](config)

    // @todo: exercise for reader: make this asynchronous.
    def write(key: String, data: String): Unit = {
      // create record
      // send to producer
    }

    def close(): Unit = {
      // close producer.
    }
  }

}
