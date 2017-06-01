package com.svds.dpt.apis;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

//@Configuration
//@EnableCassandraRepositories(basePackages = { "com.svds.dpt.apis" })
public class CassandraConfigFinal extends AbstractCassandraConfiguration {
  @Override
  protected String getKeyspaceName() {
    return "demo";
  }

  @Override
  protected String getContactPoints() {
    return "localhost";
  }
}
