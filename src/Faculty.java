import java.util.Arrays;
import java.util.Objects;

public class Faculty extends Employee implements Comparable<Person> {
    private Course[] coursesTaught;
    private int numCoursesTaught;
    private boolean isTenured;

    public Faculty() {
        super();
        this.coursesTaught = new Course[100];
        this.numCoursesTaught = 0;
        this.isTenured = false;
    }

    public Faculty(boolean isTenured) {
        super();
        this.coursesTaught = new Course[100];
        this.numCoursesTaught = 0;
        this.isTenured = isTenured;
    }

    public Faculty(String deptName, boolean isTenured) {
        super(deptName);
        this.coursesTaught = new Course[100];
        this.numCoursesTaught = 0;
        this.isTenured = isTenured;
    }

    public Faculty(String name, int birthYear, String deptName, boolean isTenured) {
        super(name, birthYear, deptName);
        this.coursesTaught = new Course[100];
        this.numCoursesTaught = 0;
        this.isTenured = isTenured;
    }

    public Course[] getCoursesTaught() {
        return coursesTaught;
    }

    public Course getCourseTaught(int index) {
        if (invalidIndex(index)) return null;
        return coursesTaught[index];
    }

    public String getCourseTaughtAsString(int index) {
        Course course = getCourseTaught(index);
        if (course == null) return "";
        return String.format("%s-%s", course.getCourseDept(), course.getCourseNum());
    }

    public String getAllCoursesTaughtAsString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numCoursesTaught; i++) {
            builder.append(getCourseTaughtAsString(i)).append(",");
        }
        return builder.length() > 0 ? builder.deleteCharAt(builder.length() - 1).toString() : builder.toString();
    }

    public void addCourseTaught(Course course) {
        if (numCoursesTaught < 100) {
            this.coursesTaught[numCoursesTaught++] = course;
        }
    }

    public void addCoursesTaught(Course[] course) {
        for (Course c : course) {
            addCourseTaught(c);
        }
    }

    private boolean invalidIndex(int index) {
        return index < 0 || index >= numCoursesTaught;
    }

    public int getNumCoursesTaught() {
        return numCoursesTaught;
    }

    public boolean isTenured() {
        return isTenured;
    }

    public void setTenured(boolean tenured) {
        isTenured = tenured;
    }

    @Override
    public String toString() {
        return String.format("%s Faculty: %11s | Number of Courses Taught: %3d | Courses Taught: %s", super.toString(), isTenured ? "Is Tenured" : "Not Tenured", numCoursesTaught, getAllCoursesTaughtAsString());
    }

    @Override
    public int compareTo(Person p) {
        if (p == null) throw new NullPointerException("The specified object is null");
        if (this.getClass() != p.getClass()) throw new ClassCastException("The specified object's type prevents it from being compared to this object");
        return Integer.compare(this.numCoursesTaught, ((Faculty) p).numCoursesTaught);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Faculty faculty = (Faculty) o;
        return numCoursesTaught == faculty.numCoursesTaught &&
                isTenured == faculty.isTenured &&
                Arrays.equals(coursesTaught, faculty.coursesTaught);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), numCoursesTaught, isTenured);
        result = 31 * result + Arrays.hashCode(coursesTaught);
        return result;
    }
}
