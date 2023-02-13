package Main_Project;

import java.util.ArrayList;

public class Student {
	private int student_id;
	private ArrayList<StudentProcess> sessions = new ArrayList<>();
	private ArrayList<IntermediateGrades> intermediateGrades= new ArrayList<>();
	private ArrayList<FinalGrades> finalGrades = new ArrayList<>();
	
	public Student() {}
	
	public Student(int student_id, ArrayList<StudentProcess> session,
					ArrayList<IntermediateGrades> intermediateGrades, ArrayList<FinalGrades> finalGrades) {
		this.student_id=student_id;
		this.sessions=session;
		this.intermediateGrades=intermediateGrades;
		this.finalGrades=finalGrades;
	}



	public int getStudent_id() {
		return student_id;
	}



	public ArrayList<StudentProcess> getSessions() {
		return sessions;
	}



	public ArrayList<IntermediateGrades> getIntermediateGrades() {
		return intermediateGrades;
	}



	public ArrayList<FinalGrades> getFinalGrades() {
		return finalGrades;
	}

	@Override
	public String toString() {
		return "[ " + student_id + ", "+ sessions + ", "
				+ intermediateGrades +", " + finalGrades + " ]";
	}
	
	
}
