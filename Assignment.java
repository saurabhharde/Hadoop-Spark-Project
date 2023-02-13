package Main_Project;

import static org.apache.spark.sql.functions.*;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;



public class Assignment {
	

	public static void main(String[] args) throws IOException {
		System.setProperty("hadoop.home.dir", "C:/hadoop");
		
		Logger.getLogger("org.apache").setLevel(Level.WARN);
		
		SparkSession spark = SparkSession.builder().appName("testingsql").master("local[*]").config("spark.sql.warehouse.dir","file:///c:/temp/").enableHiveSupport().getOrCreate();//.master("local[*]").config("spark.sql.warehouse.dir","file:///c:/temp/")
		
		
		Dataset<Row> dataset = spark.read().option("header", false).csv("C:\\Main_Project\\EPMDataset\\EPM Dataset 2\\Data\\Processes\\*\\*")//"/user/bigdata/saurabh/Processes/*/*"
									.toDF("session","student_Id","exercise","activity","start_time","end_time"
											 ,"idle_time","mouse_wheel","mouse_wheel_click","mouse_click_left"
											 ,"mouse_click_right","mouse_movement","keystroke");
		dataset.cache();
//		dataset.show();
		
		System.out.println("nuber of records present in all session file : "+dataset.count());
		
		 dataset = dataset.withColumn("record_id", row_number().over(Window.orderBy("session"))); 
		 Dataset<Row> processes_source = dataset.select("record_id","session","student_Id","exercise","activity","start_time","end_time"
											 ,"idle_time","mouse_wheel","mouse_wheel_click","mouse_click_left"
											 ,"mouse_click_right","mouse_movement","keystroke");
		processes_source.cache();
//		spark.sql("create database processes");
//		spark.sql("use processes");
//		processes_source.write().saveAsTable("processes_sources");
	
		
		Dataset<Row> errorLogRecords1 = processes_source.select(col("record_id"),lit("start_time").alias("Column_name"),col("start_time").alias("value"))
												.where((to_timestamp(col("start_time"), "dd.MM.yyyy HH:mm:ss").isNull())
												.or(not(col("start_time").rlike("\\d{2}.\\d{2}.\\d{4}.\\d{2}.\\d{2}.\\d{2}"))) );
		
		Dataset<Row> errorLogRecords2 = processes_source.select(col("record_id"),lit("end_time").alias("Column_name"),col("end_time").alias("value"))
												 .where((to_timestamp(col("end_time"), "dd.MM.yyyy HH:mm:ss").isNull())
												 .or(not(col("end_time").rlike("\\d{2}.\\d{2}.\\d{4}.\\d{2}.\\d{2}.\\d{2}"))));
		
		Dataset<Row> errorLogRecords = errorLogRecords1.union(errorLogRecords2);
		System.out.println(errorLogRecords.count());
//		errorLogRecords.write().saveAsTable("error_Log_Records");
		
		spark.close();
		
		spark.stop();
		
	}
	
}