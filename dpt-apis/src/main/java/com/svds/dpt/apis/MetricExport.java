package com.svds.dpt.apis;

import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.ExportMetricReader;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.boot.actuate.metrics.reader.MetricRegistryMetricReader;
import org.springframework.boot.actuate.metrics.statsd.StatsdMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MetricExport {
  private static final Logger logger = LoggerFactory.getLogger(MetricExport.class);
  
  private static final String prefix = "tutorial";
  
  //@Bean
  public MetricRegistry metricRegistry() {
    final MetricRegistry metricRegistry = new MetricRegistry();
    return metricRegistry;
  }
  
  //@Bean
  //@ExportMetricWriter
  MetricWriter metricWriter() {
    return new StatsdMetricWriter(prefix, "localhost", 8125);
  }
  
  //@Bean
  //@ExportMetricReader
  MetricReader metricReader() {
    return new MetricRegistryMetricReader(metricRegistry());
  }
}
