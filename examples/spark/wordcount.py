from pyspark.sql import SparkSession
import sys

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: wordcount.py <file>", file=sys.stderr)
        sys.exit(-1)

    spark = SparkSession.builder.appName("WordCount").getOrCreate()

    # Read the input file
    lines = spark.read.text(sys.argv[1]).rdd.map(lambda r: r[0])

    # Word count logic
    counts = lines.flatMap(lambda line: line.split(" ")) \
                  .map(lambda word: (word, 1)) \
                  .reduceByKey(lambda a, b: a + b)

    # Collect and print the results
    output = counts.collect()
    for (word, count) in output:
        print("%s: %i" % (word, count))

    spark.stop()
