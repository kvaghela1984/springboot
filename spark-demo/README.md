# **How to run this application locally.**

`spark-submit --class spark.examples.PiEstimation --master local target/spark-demo-1.0-SNAPSHOT.jar`
`spark-submit --jars /Users/kamlesh/.m2/repository/org/postgresql/postgresql/42.2.9/postgresql-42.2.9.jar --class spark.examples.JavaSQLDataSourceExample --master local target/spark-demo-1.0-SNAPSHOT.jar`

# **To Debug**

`export SPARK_SUBMIT_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 && spark-submit --jars /Users/kamlesh/.m2/repository/org/postgresql/postgresql/42.2.9/postgresql-42.2.9.jar --class spark.examples.JavaSQLDataSourceExample --master local target/spark-demo-1.0-SNAPSHOT.jar`
