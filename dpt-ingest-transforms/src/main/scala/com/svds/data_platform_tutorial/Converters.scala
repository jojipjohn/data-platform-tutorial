package com.svds.data_platform_tutorial

import java.io._

import sun.misc.BASE64Decoder
import twitter4j.{BASE64Encoder, Status}


// converts tweets back and forth between strings.
object Converters {
  def tweetToBase64(tweet: Status): String = {
    val bos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(bos)
    oos.writeObject(tweet)
    oos.close()
    BASE64Encoder.encode(bos.toByteArray)
  }

  def base64ToTweet(s: String): Status = {
    // todo: find a library so we can support non-oracle JDK.
    val bytes = new BASE64Decoder().decodeBuffer(s)
    val bis = new ByteArrayInputStream(bytes)
    val ois = new ObjectInputStream(bis)
    val tweet = ois.readObject().asInstanceOf[Status]
    ois.close()
    tweet
  }
}
