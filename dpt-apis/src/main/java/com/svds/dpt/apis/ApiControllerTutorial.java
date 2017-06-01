package com.svds.dpt.apis;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiControllerTutorial {
  
  public static final String indexResponse = "{\"status\"=\"just wrong\"}";
  
  @RequestMapping("/")
  public String index() {
    return indexResponse;
  }
  
  @RequestMapping("/getTweet")
  public ResponseEntity<Tweet> lookupTweet(@RequestParam(value = "tweetId", defaultValue = "0") long tweetId) {
    // start timing the operation
    
    ResponseEntity<Tweet> response = null;
    
    // build the select statement
    // execute the query
    // 404 for null, 200 for tweet
    
    return response;
  }
  
  @RequestMapping("/getTweetsByUser")
  public ResponseEntity<List<Tweet>> getTweetsByUser(@RequestParam(value = "user") String user) {
    ResponseEntity<List<Tweet>> response = null;
    
    // build select statement
    // execute query
    
    // 404 for null/empty
    // 200 and return list if non-empty
    
    return response;
  }
}
