# Data Platform Tutorial: Initialization

## You Need These Tools
* Git ([link](https://git-scm.com/downloads))
* Docker (developed using version 17.03.1-ce, build c6d412e) ([link](https://docs.docker.com/engine/installation/))
* Java 8 ([link](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html))
* Maven 3 ([link](https://maven.apache.org/download.cgi))
* cURL ([link](https://curl.haxx.se/download.html))
* Some kind of editor or Scala IDE. We used ([Intellij](https://www.jetbrains.com/idea/download/)).
* Microsoft account for [Azure Notebooks](https://notebooks.azure.com/)
* (Optional) Scala 2.11
* A Twitter account (verified using a cell phone)

If you use a mac and have the Xcode Command-Line Tools installed, you should already have `git` and `maven`.

## Please Clone This Repo

https://github.com/silicon-valley-data-science/data-platform-tutorial

## Please pull the following Docker images

* spotify/kafka
* cassandra:3.0.13
* hopsoft/graphite-statsd
* jupyter/pyspark-notebook

## Project Structure

Three directories correspond to the four parts of this tutorial.

1. ***dpt-ingest-transforms***: Contains a scala project that will used to cover ***ingestion*** and ***transforms***.
2. ***dpt-apis***: Contains a Java Springboot application used to cover ***web service APIs***.
3. ***notebooks***: Contains a Jupyter ***notebook*** that demonstrates some data exploration and visualization
   techniques.

### File Names

The classes come in two flavors:

* <b>`${CLASS_NAME}`Tutorial</b> - Stubbed in code that will be completed by participants as they go through the tutorial.
* <b>`${CLASS_NAME}`Final</b> - Final version of the code. This is how participants code should look when complete.  

Other files exist as helpers.
