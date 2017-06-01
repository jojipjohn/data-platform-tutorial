package com.svds.dpt.apis;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "raw_tweets")
public class Tweet {
  @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private long tweetId;
  
  @PrimaryKeyColumn(name = "when", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
  private long timestamp;
  
  @PrimaryKeyColumn(name = "sender", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
  private String screenName;
  
  @Column("value")
  private String contents;
  
  public Tweet(long tweetId, long timestamp, String screenName, String contents) {
    this.tweetId = tweetId;
    this.timestamp = timestamp;
    this.screenName = screenName;
    this.contents = contents;
  }

  public long getTweetId() {
    return tweetId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getScreenName() {
    return screenName;
  }

  public String getContents() {
    return contents;
  }

  @Override
  public String toString() {
    return String.format("%d,%d,%s,%.10s", tweetId, timestamp, screenName, contents);
  }
}
