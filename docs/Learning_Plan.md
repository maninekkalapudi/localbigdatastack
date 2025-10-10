# A Beginner's Learning Plan for the Big Data Ecosystem

This document outlines a structured learning plan for a beginner to understand HDFS and the broader big data ecosystem. It's designed to be a roadmap for a hands-on learning session.

---

### Session 1: The Core of Hadoop

**Objective:** Understand the fundamental concepts of Hadoop and get comfortable with HDFS.

**Topics:**

1.  **What is Big Data?**
    -   The 3 Vs: Volume, Velocity, and Variety.
    -   The need for a new processing paradigm.

2.  **The Hadoop Ecosystem: An Overview**
    -   **HDFS**: The Hadoop Distributed File System for storage.
    -   **YARN**: Yet Another Resource Negotiator for cluster resource management.
    -   **MapReduce**: The original processing framework for Hadoop.

3.  **Deep Dive into HDFS Architecture**
    -   **NameNode**: The master of the filesystem. Understand its role in storing metadata and managing the file-to-block mapping.
    -   **DataNode**: The slaves that store the actual data blocks.
    -   **Blocks**: How files are split into large blocks (e.g., 128MB).
    -   **Replication**: The concept of creating multiple copies of each block for fault tolerance.
    -   **Rack Awareness**: How the NameNode tries to place replicas on different racks to protect against rack failure.

**Hands-on Exercises:**

-   Use the `hdfs dfs` commands to perform basic file operations: `ls`, `mkdir`, `put`, `get`, `cat`.
-   Use `hdfs fsck` to inspect the block distribution of a file.
-   Change the replication factor of a file using `hdfs dfs -setrep` and observe the changes with `fsck`.
-   Simulate a datanode failure by stopping one of the datanode containers (`docker-compose stop datanode1`) and see how HDFS reacts. Check the NameNode UI (http://localhost:9870) to see the under-replicated blocks.

---

### Session 2: Processing Data with MapReduce

**Objective:** Understand the MapReduce programming model and write a custom MapReduce job.

**Topics:**

1.  **The MapReduce Programming Model**
    -   **Mappers**: The logic that processes input data and generates intermediate key-value pairs.
    -   **Reducers**: The logic that aggregates the intermediate data from the mappers.
    -   **Shuffling and Sorting**: The magic that happens between the map and reduce phases.
    -   **Combiners**: An optimization to reduce the amount of data sent to the reducers.

**Hands-on Exercises:**

-   Compile and run the WordCount example provided in this project.
-   Analyze the job execution in the YARN UI (http://localhost:8088) and the MapReduce HistoryServer UI (http://localhost:8188).
-   **Challenge**: Write a more complex MapReduce job, such as calculating the average value from a dataset or performing a secondary sort.

---

### Session 3: Hive - SQL on Hadoop

**Objective:** Learn how to use Hive to query data in HDFS using a familiar SQL-like syntax.

**Topics:**

1.  **Introduction to Hive**
    -   What is Hive and why is it so popular?
    -   Hive Architecture: Hive Server, Metastore, and Driver.

2.  **Hive Data Model**
    -   **Managed vs. External Tables**: Understand the difference and when to use each.
    -   **Partitioning and Bucketing**: Techniques for optimizing query performance.

**Hands-on Exercises:**

-   Connect to the Hive server using a SQL client (like DBeaver or the `beeline` CLI in the `hive-server` container).
-   Run the provided `example.hql` script.
-   Create your own partitioned table, load data into it, and run queries with `WHERE` clauses on the partition key.
-   **Challenge**: Write a User-Defined Function (UDF) in Java and use it in a Hive query.

---

### Session 4: Spark - The Modern Data Processing Engine

**Objective:** Learn the fundamentals of Apache Spark and how to run Spark jobs on YARN.

**Topics:**

1.  **Introduction to Spark**
    -   Why Spark is faster and more flexible than MapReduce.
    -   Spark Architecture: Driver, Executors, and the role of the Cluster Manager (YARN in our case).

2.  **Spark APIs**
    -   **RDDs (Resilient Distributed Datasets)**: The low-level API.
    -   **DataFrames and Datasets**: The modern, high-level structured APIs.

3.  **Spark on YARN**
    -   How Spark jobs are submitted to and managed by YARN.

**Hands-on Exercises:**

-   Run the provided PySpark WordCount example using `spark-submit` inside the `spark` container.
-   Rewrite the WordCount example to use the DataFrame API.
-   Read data from HDFS, process it with Spark, and write the results back to HDFS.
-   **Challenge**: Use Spark SQL to run SQL queries on your data.

---

### Next Steps: Exploring the Ecosystem

Once you are comfortable with HDFS, MapReduce, Hive, and Spark, you can explore other parts of the big data ecosystem:

-   **Data Ingestion**: Look into **Apache Sqoop** for importing data from relational databases and **Apache Flume** for ingesting streaming data.
-   **NoSQL Databases**: Explore **Apache HBase**, a NoSQL database that runs on top of HDFS, for low-latency, random read/write access to your data.
-   **Workflow Orchestration**: Learn about **Apache Oozie** or **Apache Airflow** for scheduling and managing complex data pipelines.
-   **Cloud Platforms**: Try running your jobs on a managed cloud service like **Amazon EMR**, **Google Cloud Dataproc**, or **Azure HDInsight**.
-   **Modern Data Architectures**: Read about the **Data Lakehouse** concept and technologies like **Delta Lake**, **Apache Iceberg**, and **Apache Hudi**.
