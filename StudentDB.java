package Main_Project;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.collect_list;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;

import scala.collection.Iterator;
import scala.collection.mutable.WrappedArray.ofRef;


public class StudentDB {

	public static void main(String[] args) throws IOException {
		
//		System.setProperty("hadoop.home.dir", "C:/hadoop");
		
		Logger.getLogger("org.apache").setLevel(Level.WARN);
		
		  
		SparkSession spark = SparkSession.builder().appName("testingsql")
													.getOrCreate();

		Dataset<Row> processDetails = spark.read().option("header", false).option("inferSchema", true)
										   .csv("/user/bigdata/saurabh/Data/Processes/*/*")///user/bigdata/saurabh/Data/Processes/*/*
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
			   .csv("/user/bigdata/saurabh/Data/final_grades1.csv")  ///user/bigdata/saurabh/Data/final_grades1.csv
				  .toDF(finalGradeColumns);;

	   Dataset<Row> finalGrdaeList2 =spark.read().option("header", true).option("inferSchema", true)
			   .csv("/user/bigdata/saurabh/Data/final_grades2.csv") ///user/bigdata/saurabh/Data/final_grades2.csv
					  .toDF(finalGradeColumns);

       
       Dataset<Row> finalGradeList=finalGrdaeList1.union(finalGrdaeList2);
    
       finalGradeList=finalGradeList.withColumn("FinalGrades", functions.array("ES1_1","ES1_2","ES2_1","ES2_2","ES3_1","ES3_2","ES3_3","ES3_4","ES3_5",
    		   																	"ES4_1","ES4_2","ES5_1","ES5_2","ES5_3","ES6_1","ES6_2","Total"));
       finalGradeList = finalGradeList.select(col("student_id2"),col("FinalGrades"));
       finalGradeList=finalGradeList.orderBy("student_id2").groupBy("student_id2").agg(collect_list("FinalGrades").as("FinalGrades"));

      
       Dataset<Row> intermidateGrades = spark.read().format("com.crealytics:spark-excel")
				.option("header", true).option("inferSchema", true)
				   .csv("/user/bigdata/saurabh/Data/Intermediate_grades.csv")  ///user/bigdata/saurabh/Data/Intermediate_grades.csv
				   .toDF("student_id3","session_2","session_3","session_4","session_5","session_6");

      intermidateGrades = intermidateGrades.withColumn("intermidateGrades", functions.array("session_2","session_3","session_4","session_5","session_6"));
      intermidateGrades = intermidateGrades.select(col("student_id3"),col("intermidateGrades")).orderBy(col("student_id3"));
      
      Dataset<Row> joinDataset1 = newSessionData.join(finalGradeList, col("student_id").equalTo(col("student_id2")), "leftouter");
      Dataset<Row> joinDataset2 = joinDataset1.join(intermidateGrades, col("student_id").equalTo(col("student_id3")), "leftouter");
      
      Dataset<Row> joinDataset = joinDataset2.orderBy("student_id").select(col("student_id").cast(DataTypes.IntegerType),col("session"),col("FinalGrades"),col("intermidateGrades"));
      
      JavaRDD<Row> javardd = joinDataset.toJavaRDD();
     
      JavaRDD<Student> newjavaRdd = javardd.map(row -> 
       {	
    	    int studentId = row.getAs("student_id");
    	    ArrayList<StudentProcess> studentProcessList = new ArrayList<>();
    	    ArrayList<IntermediateGrades> intermediateGrades = new ArrayList<>();
    	    ArrayList<FinalGrades> finalGrades = new ArrayList<>();
    	    
    	    @SuppressWarnings("rawtypes")
    	    Iterator sessionList = ((scala.collection.mutable.WrappedArray.ofRef)row.getAs("session")).iterator();
    	      while (sessionList.hasNext()) {
    	    	  
    	    	  StudentProcess studentProcess = new StudentProcess();
    	    	  
    	    	  @SuppressWarnings("rawtypes")
    	    	  Iterator innerListIte = ((scala.collection.mutable.WrappedArray.ofRef)sessionList.next()).iterator();
    	    	   
    	    		   studentProcess.setSession((String)innerListIte.next());
    	    		   studentProcess.setExercise((String)innerListIte.next());
    	    		   studentProcess.setActivity((String)innerListIte.next());
    	    		   studentProcess.setStart_time((String)innerListIte.next());
    	    		   studentProcess.setEnd_time((String)innerListIte.next());
    	    		   studentProcess.setIdle_time((String)innerListIte.next());
    	    		   studentProcess.setMouse_wheel((String)innerListIte.next());
    	    		   studentProcess.setMouse_wheel_click((String)innerListIte.next());
    	    		   studentProcess.setMouse_click_left((String)innerListIte.next());
    	    		   studentProcess.setMouse_click_right((String)innerListIte.next());
    	    		   studentProcess.setMouse_movement((String)innerListIte.next());
    	    		   studentProcess.setKeystroke((String)innerListIte.next());
    	    		   
    	    		   studentProcessList.add(studentProcess);
    	    		   
    	    	   
    	      }
    	    @SuppressWarnings("rawtypes")  
    	    Iterator intGradesListIte = ((scala.collection.mutable.WrappedArray.ofRef)row.getAs("intermidateGrades")).iterator();
    	      while(intGradesListIte.hasNext()) {
    	    	  IntermediateGrades intGrades = new IntermediateGrades();
    	    	  
    	    	  intGrades.setSession_2((double)intGradesListIte.next());
    	    	  intGrades.setSession_3((double)intGradesListIte.next());
    	    	  intGrades.setSession_4((double)intGradesListIte.next());
    	    	  intGrades.setSession_5((double)intGradesListIte.next());
    	    	  intGrades.setSession_6((double)intGradesListIte.next());
    	    	  
    	    	  intermediateGrades.add(intGrades);
    	      }
    	     @SuppressWarnings("rawtypes")
    	     ofRef finalGradesList = ((scala.collection.mutable.WrappedArray.ofRef)row.getAs("FinalGrades"));
    	    if (finalGradesList != null) {
    	    	@SuppressWarnings("rawtypes")
    	    	Iterator finalGradeListIte = finalGradesList.iterator();
    	    	while(finalGradeListIte.hasNext()) {
    	    		if(finalGradeListIte.isEmpty()) {
    	    			continue;
    	    		}
    	    		else {
    	    			FinalGrades finalGrade =new FinalGrades();
    	    			
						@SuppressWarnings("rawtypes")
						ofRef innerFinalGradesList = ((scala.collection.mutable.WrappedArray.ofRef)finalGradeListIte.next());
    	    			@SuppressWarnings("rawtypes")
						Iterator innerFinalGradesListIte = innerFinalGradesList.iterator();
    	    			
    	    			
    	    			finalGrade.setES1_1((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES1_2((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES2_1((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES2_2((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES3_1((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES3_2((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES3_3((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES3_4((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES3_5((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES4_1((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES4_2((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES5_1((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES5_2((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES5_3((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES6_1((double)innerFinalGradesListIte.next());
    	    			finalGrade.setES6_2((double)innerFinalGradesListIte.next());
    	    			finalGrade.setTotal((double)innerFinalGradesListIte.next());
    	    			
    	    			finalGrades.add(finalGrade);
    	    		}
    	    	}
    	    }

    	    Student student = new Student(studentId,studentProcessList,intermediateGrades,finalGrades);
    	    
    return student;
       });
       
       System.out.println("Number of rows present in student object : "+newjavaRdd.count());       
       
	}
}
