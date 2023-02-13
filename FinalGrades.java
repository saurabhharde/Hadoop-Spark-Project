package Main_Project;

public class FinalGrades {

	private double ES1_1;
	private double ES1_2;
	private double ES2_1;
	private double ES2_2;
	private double ES3_1;
	private double ES3_2;
	private double ES3_3;
	private double ES3_4;
	private double ES3_5;
	private double ES4_1;
	private double ES4_2;
	private double ES5_1;
	private double ES5_2;
	private double ES5_3;
	private double ES6_1;
	private double ES6_2;
	private double total;
	
	public FinalGrades() {}
	
	public FinalGrades( double ES1_1, double ES1_2, double ES2_1,double ES2_2, double ES3_1, double ES3_2, double ES3_3, double ES3_4
					  , double ES3_5, double ES4_1, double ES4_2, double ES5_1, double ES5_2, double ES5_3, double ES6_1, double ES6_2, double total) {
		this.ES1_1=ES1_1;
		this.ES1_2=ES1_2;
		this.ES2_1=ES2_1;
		this.ES2_2=ES2_2;
		this.ES3_1=ES3_1;
		this.ES3_2=ES3_2;
		this.ES3_3=ES3_3;
		this.ES3_4=ES3_4;
		this.ES3_5=ES3_5;
		this.ES4_1=ES4_1;
		this.ES4_2=ES4_2;
		this.ES5_1=ES5_1;
		this.ES5_2=ES5_2;
		this.ES5_3=ES5_3;
		this.ES6_1=ES6_1;
		this.ES6_2=ES6_2;
		this.total=total;
	}
	

	public void setES1_1(double eS1_1) {
		ES1_1 = eS1_1;
	}


	public void setES1_2(double eS1_2) {
		ES1_2 = eS1_2;
	}


	public void setES2_1(double eS2_1) {
		ES2_1 = eS2_1;
	}


	public void setES2_2(double eS2_2) {
		ES2_2 = eS2_2;
	}


	public void setES3_1(double eS3_1) {
		ES3_1 = eS3_1;
	}


	public void setES3_2(double eS3_2) {
		ES3_2 = eS3_2;
	}


	public void setES3_3(double eS3_3) {
		ES3_3 = eS3_3;
	}


	public void setES3_4(double eS3_4) {
		ES3_4 = eS3_4;
	}


	public void setES3_5(double eS3_5) {
		ES3_5 = eS3_5;
	}


	public void setES4_1(double eS4_1) {
		ES4_1 = eS4_1;
	}


	public void setES4_2(double eS4_2) {
		ES4_2 = eS4_2;
	}


	public void setES5_1(double eS5_1) {
		ES5_1 = eS5_1;
	}


	public void setES5_2(double eS5_2) {
		ES5_2 = eS5_2;
	}


	public void setES5_3(double eS5_3) {
		ES5_3 = eS5_3;
	}


	public void setES6_1(double eS6_1) {
		ES6_1 = eS6_1;
	}


	public void setES6_2(double eS6_2) {
		ES6_2 = eS6_2;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public double getES1_1() {
		return ES1_1;
	}

	public double getES1_2() {
		return ES1_2;
	}

	public double getES2_1() {
		return ES2_1;
	}

	public double getES2_2() {
		return ES2_2;
	}

	public double getES3_1() {
		return ES3_1;
	}

	public double getES3_2() {
		return ES3_2;
	}

	public double getES3_3() {
		return ES3_3;
	}

	public double getES3_4() {
		return ES3_4;
	}

	public double getES3_5() {
		return ES3_5;
	}

	public double getES4_1() {
		return ES4_1;
	}

	public double getES4_2() {
		return ES4_2;
	}

	public double getES5_1() {
		return ES5_1;
	}

	public double getES5_2() {
		return ES5_2;
	}

	public double getES5_3() {
		return ES5_3;
	}

	public double getES6_1() {
		return ES6_1;
	}

	public double getES6_2() {
		return ES6_2;
	}

	public double getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return "[ " + ES1_1 + ", " + ES1_2 + ", " + ES2_1 + ", " + ES2_2 + ", "
				+ ES3_1 + ", " + ES3_2 + ", " + ES3_3 + ", " + ES3_4 + ", " + ES3_5 + ", "
				+ ES4_1 + ", " + ES4_2 + ", " + ES5_1 + ", " + ES5_2 + ", " + ES5_3 + ", "
				+ ES6_1 + ", " + ES6_2 + ", " + total + " ]";
	}
	
	
	
}
