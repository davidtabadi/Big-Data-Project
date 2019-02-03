Big Data Project - Java, Kafka, MongoDB, MySql

The project uses several Data Bases, written in Java, allows users sending 

POJO’s (Objects) to Kafka (Pub-Sub), 

while Kafka Producer sends object records every second to the topic and

Kafka Consumer listens to it, all the objects are stored in 

mongoDB collection (NoSQL) and object records not read so far from mongoDB inserted to 

RDBMS table (MySQL). 

Instructions:

Install Kafka latest version (binary and not the source)

Starting Kafka (before, must start Zookeeper)

Zookeeper in cmd

bin/zookeeper-server-start.sh config/zookeeper.properties

Kafka in cmd

bin/kafka-server-start.sh config/server.properties

Create Topic EventTopic in cmd 

bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic EventTopic

Install MongoDB

Starting mongoDB:

cd bin/

first mongod.exe and then mongo.exe

use Exercise

check data stored TopicEvent in collection

db.TopicEvent.find()

Install mySQL 8.0.4 latest version 

install workbench with username and password 

create database Exercise;

use Exercise;

create table Events

check for the data 

select * from Events;




