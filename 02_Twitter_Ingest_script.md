# Data Platform Tutorial: Ingest from Twitter

### Objectives
* Connect to an external API
* Create a Kafka client
* Push data to Kafka

### Prerequisites
* You have a Twitter account
* Maven 3
* Java 8 development environment

## Create a Twitter Application

Make sure you have a Twitter account.
Then go to https://apps.twitter.com/ to create an application.
Don't worry about the URLs. Localhost addresses are accepted.

Click on "Keys and Access Tokens".
Copy down the "Consumer Key" and the "Consumer Secret".

Press "Create My Access Token"
Copy down the "Access Token" and "Access Token Secret".

These four values will be important in order to connect to the Twitter API.
Paste them into the stubbed in properties file called `twitter-secrets.properties`.

## Create a Scala Project

If you plan to use an IDE, leverage the "New Project from Existing Sources" feature to import the `pom.xml` located
in this directory.

If you don't plan to use an IDE, great. You can run from the command line this way:

    mvn exec:java -Dexec.mainClass="${fully qualified class name}" -Dany-env-value=the-main-class-needs -Dexec.args="arguments to the main method"

We will be concerned with the source files located at `src/main/scala/com/svds/data_platform_tutorial`.

### `TwitterIngestTutorial.scala`

This is a stub that you will expand during the course of this tutorial.

### `TwitterIngestFinal.scala`

Ideally, if you follow along and don't run into any snags, you will end up with code that looks roughly like this file.
It may come in handy if you get stuck.

## Coding Steps
* <b>Step 1</b>: The Kafka Writer
* <b>Step 2</b>: Validating Twitter settings
* <b>Step 3</b>: Building the `SparkConf`
* <b>Step 4</b>: Create the stream
* <b>Step 5</b>: Publish to stdout
* <b>Step 6</b>: Publish to Kafka

## Supplemental Help

### Run `TwitterIngestFinal` from the command line

mvn exec:java -Dexec.mainClass="com.svds.data_platform_tutorial.TwitterIngestFinal"
