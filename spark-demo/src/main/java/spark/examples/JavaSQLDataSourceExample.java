/*
 * Copyright (c) 2020.
 * All Rights reserved
 * @Author Kamlesh Vaghela
 */

package spark.examples;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// $example off:schema_merging$
import java.util.Properties;

// $example on:basic_parquet_example$
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
// $example on:schema_merging$
// $example on:json_dataset$
// $example off:json_dataset$
// $example off:schema_merging$
// $example off:basic_parquet_example$


public class JavaSQLDataSourceExample {

    // $example on:schema_merging$
    public static class Square implements Serializable {
        private int value;
        private int square;

        // Getters and setters...
        // $example off:schema_merging$
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getSquare() {
            return square;
        }

        public void setSquare(int square) {
            this.square = square;
        }
        // $example on:schema_merging$
    }
    // $example off:schema_merging$

    // $example on:schema_merging$
    public static class Cube implements Serializable {
        private int value;
        private int cube;

        // Getters and setters...
        // $example off:schema_merging$
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getCube() {
            return cube;
        }

        public void setCube(int cube) {
            this.cube = cube;
        }
        // $example on:schema_merging$
    }
    // $example off:schema_merging$

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL data sources example")
                .getOrCreate();

        runBasicDataSourceExample(spark);
        runBasicParquetExample(spark);
        runParquetSchemaMergingExample(spark);
        runJsonDatasetExample(spark);
        runJdbcDatasetExample(spark);

        spark.stop();
    }

    private static void runBasicDataSourceExample(SparkSession spark) {
        // $example on:generic_load_save_functions$
        Dataset<Row> usersDF = spark.read().load("src/main/resources/users.parquet");
        usersDF.select("name", "favorite_color").write().mode(SaveMode.Overwrite).save("namesAndFavColors.parquet");
        // $example off:generic_load_save_functions$
        // $example on:manual_load_options$
        Dataset<Row> peopleDF =
                spark.read().format("json").load("src/main/resources/people.json");
        peopleDF.select("name", "age").write().format("parquet").mode(SaveMode.Overwrite).save("namesAndAges.parquet");
        // $example off:manual_load_options$
        // $example on:manual_load_options_csv$
        Dataset<Row> peopleDFCsv = spark.read().format("csv")
                .option("sep", ";")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("src/main/resources/people.csv");
        // $example off:manual_load_options_csv$
        // $example on:load_with_path_glob_filter$
//        Dataset<Row> partitionedUsersDF = spark.read().format("orc")
//                .option("pathGlobFilter", "*.orc")
//                .load("src/main/resources/partitioned_users.orc");
        // $example off:load_with_path_glob_filter$
        // $example on:manual_save_options_orc$
        usersDF.write().format("orc")
                .option("orc.bloom.filter.columns", "favorite_color")
                .option("orc.dictionary.key.threshold", "1.0")
                .option("orc.column.encoding.direct", "name")
                .mode(SaveMode.Overwrite)
                .save("users_with_options.orc");
        // $example off:manual_save_options_orc$
        // $example on:direct_sql$
        Dataset<Row> sqlDF =
                spark.sql("SELECT * FROM parquet.`src/main/resources/users.parquet`");
        // $example off:direct_sql$
        // $example on:write_sorting_and_bucketing$
        peopleDF.write().mode(SaveMode.Overwrite).bucketBy(42, "name").sortBy("age").saveAsTable("people_bucketed");
        // $example off:write_sorting_and_bucketing$
        // $example on:write_partitioning$
        usersDF
                .write()
                .mode(SaveMode.Overwrite)
                .partitionBy("favorite_color")
                .format("parquet")
                .save("namesPartByColor.parquet");
        // $example off:write_partitioning$
        // $example on:write_partition_and_bucket$
        peopleDF
                .write()
                .mode(SaveMode.Overwrite)
                .partitionBy("age")
                .bucketBy(42, "name")
                .saveAsTable("people_partitioned_bucketed");
        // $example off:write_partition_and_bucket$

        spark.sql("DROP TABLE IF EXISTS people_bucketed");
        spark.sql("DROP TABLE IF EXISTS people_partitioned_bucketed");
    }

    private static void runBasicParquetExample(SparkSession spark) {
        // $example on:basic_parquet_example$
        Dataset<Row> peopleDF = spark.read().json("src/main/resources/people.json");

        // DataFrames can be saved as Parquet files, maintaining the schema information
        peopleDF.write().mode(SaveMode.Overwrite).parquet("people.parquet");

        // Read in the Parquet file created above.
        // Parquet files are self-describing so the schema is preserved
        // The result of loading a parquet file is also a DataFrame
        Dataset<Row> parquetFileDF = spark.read().parquet("people.parquet");

        // Parquet files can also be used to create a temporary view and then used in SQL statements
        parquetFileDF.createOrReplaceTempView("parquetFile");
        Dataset<Row> namesDF = spark.sql("SELECT name FROM parquetFile WHERE age BETWEEN 13 AND 19");
        Dataset<String> namesDS = namesDF.map(
                (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
                Encoders.STRING());
        namesDS.show();
        // +------------+
        // |       value|
        // +------------+
        // |Name: Justin|
        // +------------+
        // $example off:basic_parquet_example$
    }

    private static void runParquetSchemaMergingExample(SparkSession spark) {
        // $example on:schema_merging$
        List<Square> squares = new ArrayList<>();
        for (int value = 1; value <= 5; value++) {
            Square square = new Square();
            square.setValue(value);
            square.setSquare(value * value);
            squares.add(square);
        }

        // Create a simple DataFrame, store into a partition directory
        Dataset<Row> squaresDF = spark.createDataFrame(squares, Square.class);
        squaresDF.write().mode(SaveMode.Overwrite).parquet("data/test_table/key=1");

        List<Cube> cubes = new ArrayList<>();
        for (int value = 6; value <= 10; value++) {
            Cube cube = new Cube();
            cube.setValue(value);
            cube.setCube(value * value * value);
            cubes.add(cube);
        }

        // Create another DataFrame in a new partition directory,
        // adding a new column and dropping an existing column
        Dataset<Row> cubesDF = spark.createDataFrame(cubes, Cube.class);
        cubesDF.write().mode(SaveMode.Overwrite).parquet("data/test_table/key=2");

        // Read the partitioned table
        Dataset<Row> mergedDF = spark.read().option("mergeSchema", true).parquet("data/test_table");
        mergedDF.printSchema();

        // The final schema consists of all 3 columns in the Parquet files together
        // with the partitioning column appeared in the partition directory paths
        // root
        //  |-- value: int (nullable = true)
        //  |-- square: int (nullable = true)
        //  |-- cube: int (nullable = true)
        //  |-- key: int (nullable = true)
        // $example off:schema_merging$
    }

    private static void runJsonDatasetExample(SparkSession spark) {
        // $example on:json_dataset$
        // A JSON dataset is pointed to by path.
        // The path can be either a single text file or a directory storing text files
        Dataset<Row> people = spark.read().json("src/main/resources/people.json");

        // The inferred schema can be visualized using the printSchema() method
        people.printSchema();
        // root
        //  |-- age: long (nullable = true)
        //  |-- name: string (nullable = true)

        // Creates a temporary view using the DataFrame
        people.createOrReplaceTempView("people");

        // SQL statements can be run by using the sql methods provided by spark
        Dataset<Row> namesDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");
        namesDF.show();
        // +------+
        // |  name|
        // +------+
        // |Justin|
        // +------+

        // Alternatively, a DataFrame can be created for a JSON dataset represented by
        // a Dataset<String> storing one JSON object per string.
        List<String> jsonData = Arrays.asList(
                "{\"name\":\"Yin\",\"address\":{\"city\":\"Columbus\",\"state\":\"Ohio\"}}");
        Dataset<String> anotherPeopleDataset = spark.createDataset(jsonData, Encoders.STRING());
        Dataset<Row> anotherPeople = spark.read().json(anotherPeopleDataset);
        anotherPeople.show();
        // +---------------+----+
        // |        address|name|
        // +---------------+----+
        // |[Columbus,Ohio]| Yin|
        // +---------------+----+
        // $example off:json_dataset$
    }

    private static void runJdbcDatasetExample(SparkSession spark) {
        // $example on:jdbc_dataset$
        // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
        // Loading data from a JDBC source
        Dataset<Row> jdbcDF = spark.read()
                .format("jdbc")
                .option("driver","org.postgresql.Driver")
                .option("url", "jdbc:postgresql://localhost:5432/postgres")
                .option("dbtable", "users")
                .option("user", "postgres")
                .option("password", "postgres")
                .load();

        jdbcDF.show();
        jdbcDF.createOrReplaceTempView("user");
        Dataset<Row> result = spark.sql("SELECT * FROM user where username = 'kvaghela' ");
        result.collectAsList().stream().forEach(row -> {
            Timestamp created_on = (Timestamp) row.getAs("created_on");
            System.out.println(row.getAs("username").toString());
            System.out.println(row.getAs("email").toString());
            System.out.println(row.getAs("password").toString());
            System.out.println(created_on.toString());
        });
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "postgres");
        connectionProperties.put("password", "postgres");
        connectionProperties.put("driver", "org.postgresql.Driver");
        Dataset<Row> jdbcDF2 = spark.read()
                .jdbc("jdbc:postgresql://localhost:5432/postgres", "users", connectionProperties);
        /*
        // Saving data to a JDBC source
        jdbcDF.write()
                .format("jdbc")
                .option("url", "jdbc:postgresql:dbserver")
                .option("dbtable", "schema.tablename")
                .option("user", "username")
                .option("password", "password")
                .save();

        jdbcDF2.write()
                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);

        // Specifying create table column data types on write
        jdbcDF.write()
                .option("createTableColumnTypes", "name CHAR(64), comments VARCHAR(1024)")
                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);
        // $example off:jdbc_dataset$
        */
    }
}