import java.util.Objects;

public class Course implements Comparable<Course> {
    private boolean isGraduateCourse;
    private int courseNum;
    private String courseDept;
    private int numCredits;

    public Course(boolean isGraduateCourse, int courseNum, String courseDept, int numCredits) {
        this.isGraduateCourse = isGraduateCourse;
        this.courseNum = courseNum;
        this.courseDept = courseDept;
        this.numCredits = numCredits;
    }

    public boolean isGraduateCourse() {
        return isGraduateCourse;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public String getCourseDept() {
        return courseDept;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public String getCourseName() {
        return String.format("%s%s%s", isGraduateCourse ? "G" : "U", courseDept, courseNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (isGraduateCourse != course.isGraduateCourse) return false;
        if (courseNum != course.courseNum) return false;
        if (numCredits != course.numCredits) return false;
        return courseDept.equals(course.courseDept);
    }

    public int compareTo(Course c) {
        if (c == null) throw new NullPointerException("The specified object is null");
        if (this.getClass() != c.getClass()) throw new ClassCastException("The specified object's type prevents it from being compared to this object");
        return Integer.compare(this.courseNum, c.courseNum);
    }

    @Override
    public String toString() {
        return String.format("Course: %3s-%3d | Number of Credits: %02d | %s", courseDept, courseNum, numCredits, isGraduateCourse ? "Graduate" : "Undergraduate");
    }

    @Override
    public int hashCode() {
        return Objects.hash(isGraduateCourse, courseNum, courseDept, numCredits);
    }
}
