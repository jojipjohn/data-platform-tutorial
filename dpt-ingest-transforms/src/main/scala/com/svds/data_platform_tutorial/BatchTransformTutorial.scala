package com.svds.data_platform_tutorial

import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession

object BatchTransformTutorial extends LazyLogging {
  
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      // Set master, app name, and cassandra host
      .getOrCreate()

    import spark.implicits._

    val raw = spark.read
      // format, kafka bootstrap servers, topic
      .load()

    logger.info("Raw input looks like:")
    raw.show()

    val tweets = raw
      // filter
      // cast value to string
      // make into Dataset of String
      // map base 64 encoded tweet string into fields
      .toDF("id", "when", "sender", "value")

    // register udf

    val withTerm = tweets // select columns and explode of udf in order wanted for output

    logger.info("Transformed looks like:")
    withTerm.show()

    // write to csv with header

    spark.stop()
  }
}
