package Main_Project;

public class IntermediateGrades {
	
	private double session_2;
	private double session_3;
	private double session_4;
	private double session_5;
	private double session_6;

	
	public IntermediateGrades() {}
	
	public IntermediateGrades( double session_2, double session_3, double session_4, double session_5, double session_6) {
		
		this.session_2=session_2;
		this.session_3=session_3;
		this.session_4=session_4;
		this.session_5=session_5;
		this.session_6=session_6;

	}

	public void setSession_2(double session_2) {
		this.session_2 = session_2;
	}

	public void setSession_3(double session_3) {
		this.session_3 = session_3;
	}

	public void setSession_4(double session_4) {
		this.session_4 = session_4;
	}

	public void setSession_5(double session_5) {
		this.session_5 = session_5;
	}

	public void setSession_6(double session_6) {
		this.session_6 = session_6;
	}

	public double getSession_2() {
		return session_2;
	}

	public double getSession_3() {
		return session_3;
	}

	public double getSession_4() {
		return session_4;
	}

	public double getSession_5() {
		return session_5;
	}

	public double getSession_6() {
		return session_6;
	}

	@Override
	public String toString() {
		return "[ " + session_2 + ", " + session_3 + ", " + session_4
				+ ", " + session_5 + ", " + session_6 + " ]";
	}
	
	
	
}
