# HDFS Command Reference

This document provides a list of important HDFS commands for interacting with the Hadoop Distributed File System.

All these commands are run by executing them inside the `namenode` container using `docker exec`.

---

### HDFS User Commands

These are the day-to-day commands for interacting with files and directories in HDFS.

| Command | Description | Example |
| :--- | :--- | :--- |
| `ls` | Lists the contents of a directory. | `docker exec namenode hdfs dfs -ls /user/root` |
| `mkdir` | Creates a new directory. | `docker exec namenode hdfs dfs -mkdir /user/root/new_dir` |
| `put` | Copies a file from the container's local filesystem to HDFS. | `docker exec namenode hdfs dfs -put /opt/hadoop/README.txt /user/root/` |
| `get` | Copies a file from HDFS to the container's local filesystem. | `docker exec namenode hdfs dfs -get /user/root/output/part-r-00000 /tmp/` |
| `cat` | Displays the content of a file in HDFS to the console. | `docker exec namenode hdfs dfs -cat /user/root/output/part-r-00000` |
| `tail` | Displays the last kilobyte of a file. | `docker exec namenode hdfs dfs -tail /user/root/output/part-r-00000` |
| `rm` | Removes a file. | `docker exec namenode hdfs dfs -rm /user/root/README.txt` |
| `rm -r` | Removes a directory and all its contents. | `docker exec namenode hdfs dfs -rm -r /user/root/output` |
| `mv` | Moves or renames a file or directory. | `docker exec namenode hdfs dfs -mv /user/root/new_dir /user/root/renamed_dir` |
| `du` | Displays the disk usage of files and directories. | `docker exec namenode hdfs dfs -du -h /user/root` |
| `df` | Displays the free space on the filesystem. | `docker exec namenode hdfs dfs -df -h` |
| `chmod` | Changes the permissions of a file or directory. | `docker exec namenode hdfs dfs -chmod 777 /user/root/new_dir` |
| `chown` | Changes the owner of a file or directory. | `docker exec namenode hdfs dfs -chown hive:hive /user/root/new_dir` |

---

### HDFS Admin Commands

These commands are used for managing the cluster and diagnosing issues.

| Command | Description | Example |
| :--- | :--- | :--- |
| `dfsadmin -report` | Provides a detailed report of the HDFS cluster status, including capacity, usage, and the status of each datanode. | `docker exec namenode hdfs dfsadmin -report` |
| `fsck` | Runs a filesystem check to diagnose the health of files and blocks. Very useful for finding missing or corrupt blocks. | `docker exec namenode hdfs fsck / -files -blocks` |
| `balancer` | Runs the HDFS balancer, which redistributes blocks evenly across the datanodes. | `docker exec namenode hdfs balancer -threshold 5` |
| `safemode` | Allows you to manually enter or leave safemode. The NameNode enters safemode automatically on startup. | `docker exec namenode hdfs dfsadmin -safemode get`<br>`docker exec namenode hdfs dfsadmin -safemode enter`<br>`docker exec namenode hdfs dfsadmin -safemode leave` |
| `metasave` | Saves the NameNode's main metadata structures to a file on the NameNode container for offline analysis. | `docker exec namenode hdfs dfsadmin -metasave /tmp/metasave.txt` |
| `setrep` | Changes the replication factor of a file or directory. | `docker exec namenode hdfs dfs -setrep -w 3 /user/root/output/part-r-00000` |
