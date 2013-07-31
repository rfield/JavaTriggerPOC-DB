JavaTriggerPOC - DB
===================

Author:	Richard Field
Date:	July 2013

In legacy applications, database tables often need to participate in the well-known Observer pattern, meaing that as the data in the table changes, application code needs to be executed.
The typical programming idiom for this situation involves a database trigger associated with the table of interest. Often the trigger inserts new records into a "shadow table" whenever an update occurs. The shadow table is checked using some sort of polling mechanism, either via a brute force method like Unix cron or using a third party adapter provided by an integration platform such as the Mule ESB, Apache Camel or TIBCO BusinessWorks.

In general, Polling, or "repeated refresh", though simple, is more or less an architectural anti-pattern, despite its popularity.

This project is part of a working proof-of-concept demonstrating the use of Oracle Triggers executing a Java Procedure in order to "publish" data changes to a process running outside the database.


The overall POC consists of 3 parts:

The Observer
------------
The current implementation is a RESTful service, invoked via HTTP POST.

The Subject
-----------
The Oracle database table which needs to be "observed" for changes in state.

The Publisher
-------------
An Oracle trigger which invokes a Java-based Stored Procedure to execute the HTTP POST.


This project, JavaTriggerPOC-DB, consists of the Subject and the Publisher.

For convenience, the Observer component is in a separate project. This is because Oracle 11g only supports Java 1.5. So the Subject and Publisher must be built with Java 1.5. The Observer component, which uses Spring MVC REST, requires Java 1.6 annoation support.


Set Up
------
You must have JDK 1.6 installed. The POM files will downgrade to 1.5 compatibility where required.

You must also have access to an Oracle Enterprise database. Oracle Express Edition (Oracle XE) does not support Java Triggers, though the loadjava/dropjava commands are available which is helpful.


Instructions
------------

1) mvn clean
2) mvn compile
3) mvn package
4) Run the CreateTable.sql
5) Run the LoadJava_Libraries.bat to load the Apache HTTP Client libs into the database
6) Run the LoadJava_AppTrigger.bat to load the Java Trigger into the database
7) Run the CreateProcedure.sql script
8) Run the CreateTrigger.sql script
9) Start the Observer REST Service in Tomcat
10) Insert a row into the MOVIE table and observe a new row in MOVIE_SHADOW and the Observer REST Service getting invoked


Notes
-----
1. Don't forget to grant permission to open socket connections from within Oracle, as in:

    exec dbms_java.grant_permission( 'RFIELD', 'SYS:java.net.SocketPermission', 'HostRunningTheObserver:8080', 'connect,resolve' )
    
   You'll get an error in the Oracle trace logs if you forget to do this.

2. When creating Java procedures in the database using DDL, non-primitive type parameters must use full packages names such as java.lang.String.
3. Of course, the .bat scripts must be modified for the particular environment.
4. For whatever reason, LONG and LONG RAW data types will not generally work with triggers. Oracle documentation is not specific as to why.