CREATE TABLE IF NOT EXISTS users (
  id INT,
  first_name STRING,
  last_name STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INPATH '/opt/hive/examples/data/sample_data.csv' INTO TABLE users;

SELECT * FROM users;
