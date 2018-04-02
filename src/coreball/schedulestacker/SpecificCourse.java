package coreball.schedulestacker;

/**
 * The specific instance of a course
 * Created by Changyuan Lin on 02 Mar 2018.
 */
public class SpecificCourse {

	private String courseName;
	private int semester; // I don't actually use these but whatever
	private String period;
	private String teacherLast;
	private String teacherFirst;
	private String room;

	public SpecificCourse(String courseName, int semester, String period, String teacherLast, String teacherFirst, String room) {
		this.courseName = courseName;
		this.semester = semester;
		this.period = period;
		this.teacherLast = teacherLast;
		this.teacherFirst = teacherFirst;
		this.room = room;
	}

	public String getCourseName() {
		return courseName;
	}

	public int getSemester() {
		return semester;
	}

	public String getTeacherLast() {
		return teacherLast;
	}

	public String getTeacherFirst() {
		return teacherFirst;
	}

	public String getRoom() {
		return room;
	}

}
