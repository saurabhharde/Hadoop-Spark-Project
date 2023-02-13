package Main_Project;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.collect_list;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;

@SuppressWarnings("deprecation")
public class InsertIntoHbase {

    public static void main(String[] args) throws IOException {
		
		System.setProperty("hadoop.home.dir", "C:/hadoop");
		
		Logger.getLogger("org.apache").setLevel(Level.WARN);
		
		  
		SparkSession spark = SparkSession.builder().appName("testingsql")
													.getOrCreate();

		Dataset<Row> processDetails = spark.read().option("header", false).option("inferSchema", true)
										   .csv("/user/bigdata/saurabh/Data/Processes/*/*")
										   .toDF("session","student_Id","exercise","activity","start_time","end_time"
												 ,"idle_time","mouse_wheel","mouse_wheel_click","mouse_click_left"
												 ,"mouse_click_right","mouse_movement","keystroke");

		processDetails = processDetails.withColumn("session",functions.array("session","exercise","activity","start_time","end_time"
				 ,"idle_time","mouse_wheel","mouse_wheel_click","mouse_click_left"
				 ,"mouse_click_right","mouse_movement","keystroke"));
		
		processDetails = processDetails.select("student_id","session");

		Dataset<Row> newSessionData = processDetails.orderBy("session").groupBy("student_id").agg(collect_list("session").as("session"));

       
       String[] finalGradeColumns= {"student_id2","ES1_1","ES1_2","ES2_1","ES2_2","ES3_1","ES3_2","ES3_3","ES3_4"
    		   						,"ES3_5","ES4_1","ES4_2","ES5_1","ES5_2","ES5_3","ES6_1","ES6_2","Total"};
       
       
       Dataset<Row> finalGrdaeList1 =spark.read().option("header", true).option("inferSchema", true)
			   .csv("/user/bigdata/saurabh/Data/final_grades1.csv")
				  .toDF(finalGradeColumns);;

	   Dataset<Row> finalGrdaeList2 =spark.read().option("header", true).option("inferSchema", true)
			   .csv("/user/bigdata/saurabh/Data/final_grades2.csv")
					  .toDF(finalGradeColumns);

       
       Dataset<Row> finalGradeList=finalGrdaeList1.union(finalGrdaeList2);
    
       finalGradeList=finalGradeList.withColumn("FinalGrades", functions.array("ES1_1","ES1_2","ES2_1","ES2_2","ES3_1","ES3_2","ES3_3","ES3_4","ES3_5",
    		   																	"ES4_1","ES4_2","ES5_1","ES5_2","ES5_3","ES6_1","ES6_2","Total"));
       finalGradeList = finalGradeList.select(col("student_id2"),col("FinalGrades"));
       finalGradeList=finalGradeList.orderBy("student_id2").groupBy("student_id2").agg(collect_list("FinalGrades").as("FinalGrades"));

      
       Dataset<Row> intermidateGrades = spark.read().format("com.crealytics:spark-excel")
				.option("header", true).option("inferSchema", true)
				   .csv("/user/bigdata/saurabh/Data/Intermediate_grades.csv")
				   .toDF("student_id3","session_2","session_3","session_4","session_5","session_6");

      intermidateGrades = intermidateGrades.withColumn("intermidateGrades", functions.array("session_2","session_3","session_4","session_5","session_6"));
      intermidateGrades = intermidateGrades.select(col("student_id3"),col("intermidateGrades")).orderBy(col("student_id3"));
      
      Dataset<Row> joinDataset1 = newSessionData.join(finalGradeList, col("student_id").equalTo(col("student_id2")), "leftouter");
      Dataset<Row> joinDataset2 = joinDataset1.join(intermidateGrades, col("student_id").equalTo(col("student_id3")), "leftouter");
      
      Dataset<Row> joinDataset = joinDataset2.orderBy("student_id").select(col("student_id").cast(DataTypes.IntegerType),col("session"),col("FinalGrades"),col("intermidateGrades"));

      List<Row> dataframe = joinDataset.collectAsList();
      
      // Create connection
      Configuration config = HBaseConfiguration.create();
      
      config.set("hbase.zookeeper.quorum", "master.ellicium.com,node1.ellicium.com,node2.ellicium.com");
      config.setInt("hbase.zookeeper.property.clientPort", 2181);
      
      Connection connection = ConnectionFactory.createConnection(config);
      Admin admin = connection.getAdmin();
      
      System.out.println("connection done");
      

      // Create table descriptor
      HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("details_of_student"));
      tableDescriptor.addFamily(new HColumnDescriptor("student_information"));
      
      admin.createTable(tableDescriptor);
      System.out.println("Table and column family created");
      
      
      // Get table
      Table table = connection.getTable(tableDescriptor.getTableName());

      // Iterate through rows of dataframe
      for (Row row : dataframe) {
        // Create put with row key
    	  
        Put put = new Put(Bytes.toBytes(row.get(0).toString()));
    
         
       if(row.isNullAt(2)) {
        // Add columns to put
        put.addColumn(Bytes.toBytes("student_information"), Bytes.toBytes("session"), Bytes.toBytes(row.getList(1).toString()));
        
        put.addColumn(Bytes.toBytes("student_information"), Bytes.toBytes("intermidateGrades"), Bytes.toBytes(row.getList(3).toString()));
        
        table.put(put);
        
        continue;
       }
       else {
    	   
    	put.addColumn(Bytes.toBytes("student_information"), Bytes.toBytes("session"), Bytes.toBytes(row.getList(1).toString()));
        
    	put.addColumn(Bytes.toBytes("student_information"), Bytes.toBytes("FinalGrades"), Bytes.toBytes(row.getList(2).toString()));
        
        put.addColumn(Bytes.toBytes("student_information"), Bytes.toBytes("intermidateGrades"), Bytes.toBytes(row.getList(3).toString()));
        
        // Save the put
        table.put(put);
       }
        
      }
      System.out.println("data inserted");

      // Close table
      table.close();


    }
}