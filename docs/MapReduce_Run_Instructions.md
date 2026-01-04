# MapReduce Job Run Instructions

This document provides instructions on how to build and run the refactored MapReduce examples on your local Hadoop cluster.

## Prerequisites

Before running any of the MapReduce jobs, ensure the following:

1.  **Hadoop Cluster is Running:** Your local Hadoop cluster must be up and running. Use Podman to start it:
    ```bash
    cd hadoop-cluster
    podman-compose up -d
    ```
2.  **Podman Installed:** All operations (building and running) are performed via Podman containers. You do **not** need Maven or a JDK installed on your host machine.

## General Build Instructions

Since Maven is not installed on the host, we use a Maven container to build the JAR files. Run the following command from the **project root** to build a specific example:

```bash
# Example for WordCount
podman run --rm -v ".:/usr/src/mymaven" -w /usr/src/mymaven/examples/mapreduce/wordcount docker.io/library/maven:3.9.6-eclipse-temurin-11 mvn clean package -DskipTests
```

This will generate a JAR file in the project's `target/` directory.

---

## 1. WordCount Example (with Combiner)

This example counts word occurrences and uses a **Combiner** to optimize network traffic.

**Project Path:** `examples/mapreduce/wordcount`
**New Structure:** `WordCount.java` (Driver), `WordCountMapper.java`, `WordCountCombiner.java`, `WordCountReducer.java`.

### a. Prepare Input Data
```bash
podman exec namenode hdfs dfs -mkdir -p /user/input/wordcount
podman exec namenode hdfs dfs -put /opt/hadoop/host/examples/mapreduce/wordcount/data/input.txt /user/input/wordcount/
```

### b. Run the Job
```bash
podman exec namenode hadoop jar /opt/hadoop/host/examples/mapreduce/wordcount/target/wordcount-1.0-SNAPSHOT.jar /user/input/wordcount /user/output/wordcount
```

### c. View Output
```bash
podman exec namenode hdfs dfs -cat /user/output/wordcount/part-r-00000
```

---

## 2. Log Analysis Example (with Combiner)

Processes web logs to count hits per IP address using regex extraction and a **Combiner**.

**Project Path:** `examples/mapreduce/advanced/loganalysis`
**New Structure:** `LogAnalysis.java` (Driver), `LogAnalysisMapper.java`, `LogAnalysisCombiner.java`, `LogAnalysisReducer.java`.

### a. Prepare Input Data
```bash
podman exec namenode hdfs dfs -mkdir -p /user/input/loganalysis
podman exec namenode hdfs dfs -put /opt/hadoop/host/examples/mapreduce/advanced/loganalysis/data/sample.log /user/input/loganalysis/
```

### b. Run the Job
```bash
podman exec namenode hadoop jar /opt/hadoop/host/examples/mapreduce/advanced/loganalysis/target/loganalysis-1.0-SNAPSHOT.jar /user/input/loganalysis /user/output/loganalysis
```

### c. View Output
```bash
podman exec namenode hdfs dfs -cat /user/output/loganalysis/part-r-00000
```

---

## 3. Matrix Multiplication Example

Performs matrix multiplication. Note that a simple Combiner is **not** used here as the logic requires values from both matrices to be present in the Reducer.

**Project Path:** `examples/mapreduce/advanced/matrixmultiplication`
**New Structure:** `MatrixMultiplication.java` (Driver), `MatrixMapper.java`, `MatrixReducer.java`.

### a. Prepare Input Data
```bash
podman exec namenode hdfs dfs -mkdir -p /user/input/matrixmultiplication
podman exec namenode hdfs dfs -put /opt/hadoop/host/examples/mapreduce/advanced/matrixmultiplication/data/matrix.txt /user/input/matrixmultiplication/
```

### b. Run the Job
```bash
podman exec namenode hadoop jar /opt/hadoop/host/examples/mapreduce/advanced/matrixmultiplication/target/matrixmultiplication-1.0-SNAPSHOT.jar /user/input/matrixmultiplication /user/output/matrixmultiplication
```

### c. View Output
```bash
podman exec namenode hdfs dfs -cat /user/output/matrixmultiplication/part-r-00000
```

---

## Troubleshooting & Tips

*   **HDFS Paths:** The container sees the project root at `/opt/hadoop/host`.
*   **Clean Up:** Output directories must be deleted before re-running a job:
    ```bash
    podman exec namenode hdfs dfs -rm -r /user/output/<job_name>
    ```
*   **Build Failures:** Ensure you are running the `podman run ... mvn` command from the root of the project so the volume mapping works correctly.
