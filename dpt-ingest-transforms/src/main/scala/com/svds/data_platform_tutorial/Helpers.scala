package com.svds.data_platform_tutorial

import java.io._
import java.util.Properties

import org.apache.spark.rdd.RDD

object Helpers {
  def loadTwitterAuthFromFile(path: String): Unit = {
    val props = new Properties()
    props.load(new FileInputStream(path))
    import scala.collection.JavaConversions.mapAsScalaMap
    mapAsScalaMap(props.asInstanceOf[java.util.Map[String, String]]) foreach { case (key, value) => System.setProperty("twitter4j." + key, value)}
  }
  
  def validateTwitterEnv(): Unit = {
    List("twitter4j.oauth.consumerKey",
      "twitter4j.oauth.consumerSecret",
      "twitter4j.oauth.accessToken",
      "twitter4j.oauth.accessTokenSecret")
        .foreach(key => {
          if (System.getProperty(key) == null) {
            throw new RuntimeException(s"Cannot find $key in the environment")
          }
        })
  }

  def termSearchRDD(searchTerms: List[String])(tweets: RDD[String]): RDD[String] = {
    val searchRegex = s"(?i)(${searchTerms.mkString("|")})".r
    val filtered = tweets.flatMap(tweet => searchRegex.findAllIn(tweet).map(_.toString).toTraversable)
    val caseMap = searchTerms.map(e => (e.toLowerCase, e)).toMap
    val cased = filtered.map(k => caseMap.getOrElse(k.toLowerCase, k))
    cased
  }

  def termSearch(terms: String*): String => Array[String] = {
    val searchRegex = s"(?i)(${terms.mkString("|")})".r
    val caseMap = terms.map(e => (e.toLowerCase, e)).toMap
    (text: String) =>
      searchRegex.findAllIn(text).map(_.toString).map(x => caseMap.getOrElse(x.toLowerCase, x)).toArray
  }
}



