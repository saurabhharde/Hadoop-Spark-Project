package Main_Project;

public class StudentProcess {
	private String session;
	private String exercise;
	private String activity;
	private String start_time;
	private String end_time;
	private String idle_time;
	private String mouse_wheel;
	private String mouse_wheel_click;
	private String mouse_click_left;
	private String mouse_click_right;
	private String mouse_movement;
	private String keystroke;
	
	public StudentProcess() {}
	
	public StudentProcess(String session,String exercise,String activity,String start_time
						  ,String end_time,String idle_time, String mouse_wheel,String mouse_wheel_click
						  ,String mouse_click_left,String mouse_click_right,String mouse_movement,String keystroke) {
		this.session=session;
		this.exercise=exercise;
		this.activity=activity;
		this.start_time=start_time;
		this.end_time=end_time;
		this.idle_time=idle_time;
		this.mouse_wheel=mouse_wheel;
		this.mouse_click_left=mouse_click_left;
		this.mouse_click_right=mouse_click_right;
		this.mouse_wheel_click=mouse_wheel_click;
		this.keystroke=keystroke;
	}
	
	public void setSession(String session) {
		this.session = session;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public void setIdle_time(String idle_time) {
		this.idle_time = idle_time;
	}

	public void setMouse_wheel(String mouse_wheel) {
		this.mouse_wheel = mouse_wheel;
	}

	public void setMouse_wheel_click(String mouse_wheel_click) {
		this.mouse_wheel_click = mouse_wheel_click;
	}

	public void setMouse_click_left(String mouse_click_left) {
		this.mouse_click_left = mouse_click_left;
	}

	public void setMouse_click_right(String mouse_click_right) {
		this.mouse_click_right = mouse_click_right;
	}

	public void setMouse_movement(String mouse_movement) {
		this.mouse_movement = mouse_movement;
	}

	public void setKeystroke(String keystroke) {
		this.keystroke = keystroke;
	}

	public String getSession() {
		return session;
	}

	public String getExercise() {
		return exercise;
	}

	public String getActivity() {
		return activity;
	}

	public String getStart_time() {
		return start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public String getIdle_time() {
		return idle_time;
	}

	public String getMouse_wheel() {
		return mouse_wheel;
	}

	public String getMouse_wheel_click() {
		return mouse_wheel_click;
	}

	public String getMouse_click_left() {
		return mouse_click_left;
	}

	public String getMouse_click_right() {
		return mouse_click_right;
	}

	public String getMouse_movement() {
		return mouse_movement;
	}

	public String getKeystroke() {
		return keystroke;
	}

	@Override
	public String toString() {
		return "[ " + session + ", " + exercise + ", " + activity
				+ ", " + start_time + ", " + end_time + ", " + idle_time
				+ ", " + mouse_wheel + ", " + mouse_wheel_click + ", "
				+ mouse_click_left + ", " + mouse_click_right + ", " + mouse_movement
				+ ", " + keystroke + " ]";
	}
	
	
}
