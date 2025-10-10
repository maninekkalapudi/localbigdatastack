# Local Multi-Node Hadoop Cluster with Docker

This project provides a complete setup for a local multi-node Hadoop cluster using Docker and Docker Compose. It includes a NameNode, two DataNodes, a ResourceManager, two NodeManagers, and a HistoryServer.

The cluster is pre-configured and ready to run MapReduce, Hive, and Spark jobs. This repository is designed to be a learning environment for big data enthusiasts and developers.

## Prerequisites

- **Docker Desktop**: Ensure you have the latest version installed and running.
- **Git**: For cloning the repository.

## Project Structure

```
.
├── hadoop-cluster/         # Infrastructure-related files
│   ├── config/             # Hadoop configuration files
│   │   ├── hadoop/
│   │   └── hadoop.env
│   └── docker-compose.yml  # Docker Compose file for the cluster
├── examples/               # Example jobs
│   ├── mapreduce/          # MapReduce examples
│   └── wordcount/
│   ├── src/
│   │   └── WordCount.java
│   └── pom.xml
├── hive/                   # Hive examples
│   └── scripts/
│       └── example.hql
└── spark/                  # Spark examples
        └── wordcount.py
└── README.md               # This file
```

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd <repository-name>
```

### 2. Start the Hadoop Cluster

Navigate to the `hadoop-cluster` directory and run the following commands:

```bash
cd hadoop-cluster

# Format the NameNode (only needs to be done once)
docker-compose run --rm namenode hdfs namenode -format

# Start all cluster services
docker-compose up -d
```

### 3. Verify the Cluster

After a few moments, all services should be up and running. You can check the status of the containers with:

```bash
docker-compose ps
```

You can also access the web UIs for the different services:

- **HDFS NameNode**: [http://localhost:9870](http://localhost:9870)
- **YARN ResourceManager**: [http://localhost:8088](http://localhost:8088)
- **MapReduce HistoryServer**: [http://localhost:8188](http://localhost:8188)

## Running the Examples

### MapReduce: WordCount

This example counts the occurrences of each word in the provided XML configuration files.

1.  **Build the JAR file**:
    Navigate to the `examples/mapreduce/wordcount` directory and use Maven to build the project:

    ```bash
    cd ../../examples/mapreduce/wordcount
    mvn clean package
    ```

2.  **Copy the JAR to the NameNode container**:

    ```bash
    docker cp target/wordcount-1.0-SNAPSHOT.jar namenode:/opt/hadoop/
    ```

3.  **Run the WordCount job**:

    ```bash
    docker exec namenode hadoop jar /opt/hadoop/wordcount-1.0-SNAPSHOT.jar /user/root/input /user/root/output
    ```

4.  **View the results**:

    ```bash
    docker exec namenode hdfs dfs -cat /user/root/output/part-r-00000
    ```

### Hive

(Coming soon! This section will be updated with instructions on how to run Hive queries.)

### Spark

(Coming soon! This section will be updated with instructions on how to run Spark jobs.)

## Managing the Cluster

All commands should be run from the `hadoop-cluster` directory.

-   **Stop the cluster** (without removing data):

    ```bash
    docker-compose stop
    ```

-   **Start the cluster**:

    ```bash
    docker-compose start
    ```

-   **Stop and remove the cluster** (deletes all data):

    ```bash
    docker-compose down -v
    ```

## Troubleshooting

-   **Permission Errors on Windows**: If you encounter permission errors, ensure that the project directory is located in your user's home directory (e.g., `C:\Users\<YourUser>\...`). Docker Desktop for Windows has better support for file sharing in this location.
-   **`envtoconf.py` error**: If you see a `ValueError: too many values to unpack` from `envtoconf.py`, make sure you have removed the `environment` key from the `namenode` service in the `docker-compose.yml` file.
-   **ResourceManager not starting**: If the `resourcemanager` fails to start, ensure that the YARN queue configuration is present in `config/hadoop/yarn-site.xml`.

