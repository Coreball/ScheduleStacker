package coreball.schedulestacker;

/**
 * The specific instance of a course
 * Created by Changyuan Lin on 02 Mar 2018.
 */
public class SpecificCourse {

	private String courseName;
	private int semester;
	private String period;
	private String teacher;

	public SpecificCourse(String courseName, int semester, String period, String teacher) {
		this.courseName = courseName;
		this.semester = semester;
		this.period = period;
		this.teacher = teacher;
	}

	public String getTeacher() {
		return teacher;
	}

	public String getCourseName() {
		return courseName;
	}

}
