package com.svds.dpt.utils;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

// Helper class to load a CSV of tweets into the database.
// schema: tweet id, timestamp, user, term, tweet
public class Csv2Cass {
  public static void main(String args[]) {
    String csvPath = "/path/to/your.csv";
    
    Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
    Session session = cluster.connect("demo");
    PreparedStatement pstmt = session.prepare("insert into demo.raw_tweets (id,when,sender,value) values (?,?,?,?)");

    try {
      CSVParser parser = CSVParser.parse(new File(csvPath), Charset.forName("UTF8"), CSVFormat.RFC4180);
      for (CSVRecord record : parser) {
        if (record.size() == 5) {
          // id, time, user, term, content
          BoundStatement bstmt = pstmt.bind(
              Long.parseLong(record.get(0)),
              Long.parseLong(record.get(1)),
              record.get(2),
              record.get(4));
          session.execute(bstmt);
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace(System.err);
    }
    
    session.close();
    cluster.close();
  }
}
