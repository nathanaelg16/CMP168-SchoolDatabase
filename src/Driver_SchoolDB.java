import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//LINK TO VIDEO: https://youtu.be/bdxKLDnPRhc
//FOR SOURCE CODE VISIT https://github.com/nathanaelg16
public class Driver_SchoolDB {
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Faculty> faculty = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<GeneralStaff> generalStaff = new ArrayList<>();
    private static ArrayList<Person> people = new ArrayList<>();
    private static ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException{
        /*System.out.println("Welcome to the SCHOOL DATABASE\nBy: Nathanael Gutierrez\nVersion: 1.0.0-stable\n");
        Thread.sleep(2000);*/
        Scanner in = null;
        try {
            File dbFile = new File("SchoolDB_Initial.txt");
            FileInputStream stream = new FileInputStream(dbFile);
            in = new Scanner(stream);
            int lineNumber = 0;
            while (in.hasNext()) {
                lineNumber++;
                String line = in.nextLine();
                System.out.println(line); //display read line
                int index = line.indexOf(':');
                if (index != -1) {
                    String object = line.substring(0, index);
                    String[] attr = line.substring(index + 1).trim().split(",");
                    trimWhitespace(attr);
                    switch (object) {
                        case "Person":
                            Person p;
                            if (attr.length == 1) {
                                p = new Person();
                            } else if (attr.length == 2) {
                                p = new Person(attr[0], Integer.parseInt(attr[1]));
                            } else
                                throw new InvalidObjectException(String.format("No constructor found for object specified on line %d.", lineNumber));
                            people.add(p);
                            break;
                        case "Course":
                            if (attr.length != 4)
                                throw new InvalidObjectException(String.format("No constructor found for object specified on line %d.", lineNumber));
                            Course c = new Course(Boolean.parseBoolean(attr[0]), Integer.parseInt(attr[1]), attr[2], Integer.parseInt(attr[3]));
                            courses.add(c);
                            break;
                        case "Faculty":
                            Faculty f;
                            if (attr.length == 1) {
                                f = attr[0].isEmpty() ? new Faculty() : new Faculty(Boolean.parseBoolean(attr[0]));
                            } else if (attr.length == 2) {
                                f = new Faculty(attr[0], Boolean.parseBoolean(attr[1]));
                            } else if (attr.length == 4) {
                                f = new Faculty(attr[0], Integer.parseInt(attr[1]), attr[2], Boolean.parseBoolean(attr[3]));
                            } else throw new InvalidObjectException(String.format("No constructor found for object specified on line %d.", lineNumber));
                            faculty.add(f);
                            break;
                        case "Student":
                            Student s;
                            if (attr.length == 1) {
                                s = attr[0].isEmpty() ? new Student() : new Student(Boolean.parseBoolean(attr[0]));
                            } else if (attr.length == 2) {
                                s = new Student(attr[0], Boolean.parseBoolean(attr[1]));
                            } else if (attr.length == 4) {
                                s = new Student(attr[0], Integer.parseInt(attr[1]), attr[2], Boolean.parseBoolean(attr[3]));
                            } else
                                throw new InvalidObjectException(String.format("No constructor found for object specified on line %d.", lineNumber));
                            students.add(s);
                            break;
                        case "GeneralStaff":
                            GeneralStaff g;
                            if (attr.length == 1) {
                                g = attr[0].isEmpty() ? new GeneralStaff() : new GeneralStaff(attr[0]);
                            } else if (attr.length == 2) {
                                g = new GeneralStaff(attr[0], attr[1]);
                            } else if (attr.length == 4) {
                                g = new GeneralStaff(attr[0], Integer.parseInt(attr[1]), attr[2], attr[3]);
                            } else {
                                throw new InvalidObjectException(String.format("No constructor found for object specified on line %d.", lineNumber));
                            }
                            generalStaff.add(g);
                            break;
                        case "Employee":
                            Employee e;
                            if (attr.length == 1) {
                                e = attr[0].isEmpty() ? new Employee() : new Employee(attr[0]);
                            } else if (attr.length == 3) {
                                e = new Employee(attr[0], Integer.parseInt(attr[1]), attr[2]);
                            } else throw new InvalidObjectException(String.format("No constructor found for object specified on line %d.", lineNumber));
                            employees.add(e);
                            break;
                        default:
                            throw new InvalidObjectException(String.format("Unexpected object named %s found in file.", object));
                    }
                } //else no object found on this line
            }
        } catch (FileNotFoundException e) {
            System.err.println("The database file could not be found.");
        } catch (Exception e) {
            System.err.println(String.format("\nERROR: %s\n", e.getMessage()));
        } finally {
            if (in != null) {
                in.close();
            }
        }
        printSchoolDatabaseInfo();

//        final String createCourseMenuOption = "(1) Create a course";
//        final String createNewGeneralStaffMenuOption = "(2) Add new general staff";
//        final String createNewFacultyMemberMenuOption = "(3) Add new faculty";
//        final String createNewStudentMenuOption = "(4) Add new student";
//        final String viewCoursesMenuOption = "(5) View courses";
//        final String viewFacultyMenuOption = "(6) View faculty";
//        final String viewStudentsMenuOption = "(7) View students";
//        final String viewGeneralStaffMenuOption = "(8) View general staff";
//
//        in = new Scanner(System.in);
//        boolean q = true;
//
//        while (q) {
//            System.out.printf("\n\n***********\nMAIN MENU\n***********\n\n\t%-40s %s\n\t%-40s %s\n\n\t%-40s %s\n\t%-40s %s\n\nSelect an option, or quit (0): ",
//                    createCourseMenuOption, createNewFacultyMemberMenuOption, createNewGeneralStaffMenuOption,
//                    createNewStudentMenuOption, viewCoursesMenuOption, viewStudentsMenuOption,
//                    viewFacultyMenuOption, viewGeneralStaffMenuOption);
//            try {
//                int option = in.nextInt();
//                if (option <= 0) {
//                    q = false;
//                } else if (option > 8) {
//                    throw new InvalidInputException();
//                } else {
//                    InsertionMode insertionMode;
//                    switch (option) {
//                        case 1:
//                            insertionMode = getInsertionMode(in);
//                            if (insertionMode == null) break;
//                            createNewCourse(in, insertionMode);
//                            break;
//                        case 2:
//                            insertionMode = getInsertionMode(in);
//                            if (insertionMode == null) break;
//                            addNewGeneralStaff(in, insertionMode);
//                            break;
//                        case 3:
//                            insertionMode = getInsertionMode(in);
//                            if (insertionMode == null) break;
//                            addNewFaculty(in, insertionMode);
//                            break;
//                        case 4:
//                            insertionMode = getInsertionMode(in);
//                            if (insertionMode == null) break;
//                            addNewStudent(in, insertionMode);
//                            break;
//                        case 5:
//                            coursesMenu(in);
//                            break;
//                        case 6:
//                            facultyMenu(in);
//                            break;
//                        case 7:
//                            studentsMenu(in);
//                            break;
//                        case 8:
//                            generalStaffMenu(in);
//                            break;
//                        default:
//                            //should never happen
//                            throw new InvalidInputException();
//                    }
//                }
//            } catch (InputMismatchException | InvalidInputException e) {
//                System.err.println("Please select a valid option.");
//                in.nextLine(); //skip the new line
//                Thread.sleep(1000);
//            }
//        }
//        printSchoolDatabaseInfo();
//        writeSchoolDatabaseInfoToFile();
    }

    private static void writeSchoolDatabaseInfoToFile() {
        try {
            FileOutputStream out = new FileOutputStream("SchoolDB_Final.txt");
            PrintWriter writer = new PrintWriter(out);

            for (Course c : courses) {
                writer.printf("Course: %b,%d,%s,%d\n", c.isGraduateCourse(), c.getCourseNum(), c.getCourseDept(), c.getNumCredits());
            }
            writer.print("\n");

            for (Faculty f : faculty) {
                writer.printf("Faculty: ");
                if (!f.getName().isEmpty()) writer.printf("%s,", f.getName());
                if (f.getBirthYear() != 0) writer.printf("%d,", f.getBirthYear());
                if (!f.getDeptName().isEmpty()) writer.printf("%s,", f.getDeptName());
                writer.printf("%b\n", f.isTenured());
            }
            writer.print("\n");

            for (Student s : students) {
                writer.printf("Student: ");
                if (!s.getName().isEmpty()) writer.printf("%s,", s.getName());
                if (s.getBirthYear() != 0) writer.printf("%d,", s.getBirthYear());
                if (!s.getMajor().isEmpty()) writer.printf("%s,", s.getMajor());
                writer.printf("%b\n", s.isGraduate());
            }
            writer.print("\n");

            for (GeneralStaff g : generalStaff) {
                writer.printf("GeneralStaff: ");
                if (!g.getName().isEmpty()) writer.printf("%s,", g.getName());
                if (g.getBirthYear() != 0) writer.printf("%d,", g.getBirthYear());
                if (!g.getDeptName().isEmpty()) writer.printf("%s,", g.getDeptName());
                writer.printf("%s\n", g.getDuty());
            }
            writer.print("\n");

            for (Employee e : employees) {
                writer.printf("Employee: ");
                if (!e.getName().isEmpty()) writer.printf("%s,", e.getName());
                if (e.getBirthYear() != 0) writer.printf("%d,", e.getBirthYear());
                writer.printf("%s\n", e.getDeptName());
            }
            writer.print("\n");

            for (Person p : people) {
                writer.printf("Person: ");
                if (!p.getName().isEmpty()) writer.printf("%s,", p.getName());
                if (p.getBirthYear() != 0) writer.printf("%d\n", p.getBirthYear());
            }
            writer.print("\n");

            writer.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("**** Unable to write to file ****");
        }
    }

    private static InsertionMode getInsertionMode(Scanner scanner) {
        scanner.nextLine(); //skip new line

        final String individualInsertModeOption = "(1) Add one";
        final String batchInsertModeOption = "(2) Add many";

        System.out.printf("\n\n*******\nMODE\n*******\n\n\t%-20s %s\n\nSelect an option, or cancel (0): ", individualInsertModeOption, batchInsertModeOption);
        int modeOption = scanner.nextInt();
        if (modeOption > 0) {
            if (modeOption > 2) throw new InvalidInputException();
            else return (modeOption == 1) ? InsertionMode.INDIVIDUAL : InsertionMode.BATCH;
        } else return null;
    }

    private static void facultyMenu(Scanner scanner) throws InterruptedException {
        scanner.nextLine(); //skip the new line

        final String listCoursesMenuOption = "(1) List courses";
        final String addCoursesMenuOption = "(2) Add courses";
        final String changeTenureMenuOption = "(3) Change tenure";
        final String queryCoursesMenuOption = "(4) Query courses";

        boolean invalid = false;
        do {
            faculty.sort(null);
            try {
                printNumberedObjects("FACULTY:", faculty);
                System.out.printf("\n\t%s\n\t%s\n\t%s\n\t%s\n\n", listCoursesMenuOption,
                        addCoursesMenuOption, changeTenureMenuOption, queryCoursesMenuOption);
                System.out.print("Select an option, or cancel (0): ");
                int option = scanner.nextInt();

                if (option > 0) {
                    if (option > 4) throw new InvalidInputException();
                    System.out.print("Select faculty: ");
                    int index = scanner.nextInt();
                    if (index > faculty.size() || index < 1) throw new InvalidInputException("Invalid index selected");

                    Faculty f = faculty.get(index - 1);
                    switch (option) {
                        case 2:
                            addCourseToObject(f, scanner);
                        case 1:
                            String allCourses = f.getAllCoursesTaughtAsString();
                            String[] courses = allCourses.split(",");
                            printNumberedObjects("COURSES:", (Object[]) courses);
                            Thread.sleep(1000);

                            System.out.print("Select an option or enter 0: ");
                            int selection = scanner.nextInt();
                            if (selection > 0) {
                                if (selection > f.getNumCoursesTaught())
                                    throw new InvalidInputException("Invalid index selected");
                                printObjects("COURSE:", f.getCourseTaught(selection - 1));
                                Thread.sleep(1000);
                            }
                            break;
                        case 3:
                            scanner.nextLine(); //skip new line

                            String tenuredEntry;
                            do {
                                System.out.print("\tIs Tenured? (Y/N): ");
                                tenuredEntry = scanner.nextLine();
                                if (tenuredEntry.trim().isEmpty()) {
                                    System.err.println("**** This field is mandatory. ****");
                                    Thread.sleep(1000);
                                    invalid = true;
                                } else {
                                    if (!(tenuredEntry.equalsIgnoreCase("Y") || tenuredEntry.equalsIgnoreCase("N"))) {
                                        System.err.println("**** Please input 'Y' or 'N' ****");
                                        Thread.sleep(1000);
                                        invalid = true;
                                    } else {
                                        f.setTenured(tenuredEntry.equalsIgnoreCase("Y"));
                                        invalid = false;
                                    }
                                }
                            } while (invalid);
                            break;
                        case 4:
                            scanner.nextLine(); //skip new line
                            printNumberedObjects("COURSES:", Driver_SchoolDB.courses);

                            System.out.print("Select an option or enter 0: ");
                            int courseIndex = scanner.nextInt();
                            if (courseIndex > 0) {
                                if (courseIndex > Driver_SchoolDB.courses.size())
                                    throw new InvalidInputException("Invalid index selected");
                                Course c = Driver_SchoolDB.courses.get(courseIndex - 1);
                                Course[] coursesTaught = f.getCoursesTaught();
                                boolean taught = false;
                                for (Course course : coursesTaught) {
                                    if (c.equals(course)) {
                                        taught = true;
                                        break;
                                    }
                                }
                                System.out.printf("\nSelected faculty member teaches %s-%s: ", c.getCourseDept(), c.getCourseNum());
                                Thread.sleep(500);
                                System.out.print(taught);
                                Thread.sleep(1000);
                            }
                            break;
                    }
                } else invalid = false;
            } catch (InputMismatchException | InvalidInputException e) {
                System.err.println("**** Please select a valid option ****");
                Thread.sleep(1000);
                scanner.nextLine(); //skip the new line
                invalid = true;
            }
        } while (invalid);
    }

    private static void addCourseToObject(Object o, Scanner scanner) throws InvalidObjectException, InterruptedException {
        scanner.nextLine(); //skip the new line
        if (!(o.getClass().getName().equals(Student.class.getName()) || o.getClass().getName().equals(Faculty.class.getName()))) {
            throw new InvalidObjectException();
        }

        final boolean isStudent = o.getClass().getName().equals(Student.class.getName());

        printNumberedObjects("COURSES:", courses);

        boolean invalid = false;
        do {
            try {
                System.out.printf("\n\nSelect courses [comma-separated], create new course (%d), or cancel (0): ", courses.size() + 1);
                String input = scanner.nextLine().trim();
                String[] indices = input.split(",");
                trimWhitespace(indices);

                int[] intIndices = new int[indices.length];
                for (int i = 0; i < indices.length; i++) {
                    int index = Integer.parseInt(indices[i]);
                    if (index > courses.size() + 1) throw new InvalidInputException();
                    intIndices[i] = index;
                } // if for-loop doesn't throw an exception then they are all valid inputs, else code below shouldn't execute

                for (int index : intIndices) {
                    if (index > 0) {
                        if (index == courses.size() + 1) {
                            scanner.nextLine(); //skip the new line
                            Course[] createdCourses = createNewCourse(scanner, InsertionMode.BATCH);
                            if (isStudent) ((Student) o).addCoursesTaken(createdCourses);
                            else ((Faculty) o).addCoursesTaught(createdCourses);
                        } else {
                            Course c = courses.get(index - 1);
                            if (isStudent) ((Student) o).addCourseTaken(c);
                            else ((Faculty) o).addCourseTaught(c);
                        }
                    } else {
                        invalid = false;
                    }
                }
            } catch (Exception e) {
                System.err.println("**** Please select a valid option ****");
                Thread.sleep(1000);
                invalid = true;
            }
        } while (invalid);
    }

    private static void studentsMenu(Scanner scanner) throws InterruptedException {
        scanner.nextLine(); //skip the new line

        final String listCoursesMenuOption = "(1) List courses";
        final String addCoursesMenuOption = "(2) Add courses";
        final String changeMajorMenuOption = "(3) Change major";
        final String changeGraduateMenuOption = "(4) Change graduate status";

        boolean invalid = false;
        do {
            students.sort(null);
            try {
                printNumberedObjects("STUDENTS:", students);
                System.out.printf("\n\t%-40s %s\n\t%-40s %s\n\nSelect an option, or cancel (0): ", listCoursesMenuOption,
                        changeMajorMenuOption, addCoursesMenuOption, changeGraduateMenuOption);

                int option = scanner.nextInt();

                if (option > 0) {
                    if (option > 4) throw new InvalidInputException();
                    System.out.print("Select student: ");
                    int index = scanner.nextInt();
                    if (index > students.size() || index < 1) throw new InvalidInputException("Invalid index selected");

                    Student s = students.get(index - 1);
                    switch (option) {
                        case 2:
                            addCourseToObject(s, scanner);
                        case 1:
                            String allCourses = s.getAllCoursesTakenAsString();
                            String[] courses = allCourses.split(",");
                            printNumberedObjects("COURSES:", (Object[]) courses);
                            System.out.printf("TOTAL NUMBER OF CREDITS: %d\n\n", s.getNumCredits());
                            Thread.sleep(1000);

                            System.out.print("Select an option or enter 0: ");
                            int selection = scanner.nextInt();
                            if (selection > 0) {
                                if (selection > s.getNumCoursesTaken())
                                    throw new InvalidInputException("Invalid index selected");
                                printObjects("COURSE:", s.getCourseTaken(selection - 1));
                                Thread.sleep(1000);
                            }
                            break;
                        case 4:
                            scanner.nextLine(); //skip new line

                            String graduateEntry;
                            do {
                                System.out.print("\tIs Graduate Student? (Y/N): ");
                                graduateEntry = scanner.nextLine();
                                if (graduateEntry.trim().isEmpty()) {
                                    System.err.println("**** This field is mandatory. ****");
                                    Thread.sleep(1000);
                                    invalid = true;
                                } else {
                                    if (!(graduateEntry.equalsIgnoreCase("Y") || graduateEntry.equalsIgnoreCase("N"))) {
                                        System.err.println("**** Please input 'Y' or 'N' ****");
                                        Thread.sleep(1000);
                                        invalid = true;
                                    } else {
                                        s.setGraduate(graduateEntry.equalsIgnoreCase("Y"));
                                    }
                                }
                            } while (invalid);
                            break;
                        case 3:
                            scanner.nextLine(); //skip new line
                            String major;
                            do {
                                System.out.print("\tMajor: ");
                                major = scanner.nextLine().trim();

                                if (major.isEmpty()) {
                                    System.err.println("**** This field is mandatory. ****");
                                    Thread.sleep(1000);
                                    invalid = true;
                                } else invalid = false;
                            } while (invalid);
                            s.setMajor(major);
                            break;
                    }
                } else invalid = false;
            } catch (InputMismatchException | InvalidInputException e) {
                System.err.println("**** Please select a valid option ****");
                Thread.sleep(1000);
                scanner.nextLine(); //skip the new line
                invalid = true;
            }
        } while (invalid);
    }

    private static void generalStaffMenu(Scanner scanner) throws InterruptedException {
        scanner.nextLine(); //skip the new line
        generalStaff.sort(null);

        printObjects("GENERAL STAFF:", generalStaff);
        Thread.sleep(1000);
    }

    private static void coursesMenu(Scanner scanner) throws InterruptedException {
        scanner.nextLine(); //skip the new line
        courses.sort(null);

        printObjects("COURSES:", courses);
        Thread.sleep(1000);
    }

    private static void printSchoolDatabaseInfo() {
        /* Display all objects */
        System.out.print("\n**************************************************************\nSCHOOL DATABASE INFO:\n\n");
        printObjects("COURSES:", courses);
        printObjects("PERSONS:", people);
        printObjects("EMPLOYEES:", employees);
        printObjects("GENERAL STAFF:", generalStaff);
        printObjects("FACULTY:", faculty);
        printObjects("STUDENTS:", students);
        System.out.print("**************************************************************\n\n");
    }

    private static void addNewStudent(Scanner scanner, InsertionMode mode) throws InterruptedException {
        scanner.nextLine(); //skip the new line

        boolean addAnother = (mode == InsertionMode.BATCH);
        do {
            String name, major;
            int birthYear = 0;
            boolean isGraduateStudent = false;
            boolean invalid; //will be used to break out of loops
            boolean allArgsNecessary = false;

            System.out.print("\nAdd new student:\n******************\n\tName: ");
            name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                allArgsNecessary = true;
                do {
                    System.out.print("\tBirth year: ");
                    String birthYearString = scanner.nextLine();
                    try {
                        birthYear = Integer.parseInt(birthYearString.trim());
                        if (birthYear < 1900) throw new InvalidInputException("Birth year before 1900");
                        invalid = false; //if this line is reached, the year was parsed successfully
                    } catch (NumberFormatException | InvalidInputException e) {
                        System.err.println("**** Input must be a valid numeric year. Please try again. ****");
                        Thread.sleep(1000);
                        invalid = true;
                    }
                } while (invalid);
            }

            do {
                System.out.print("\tMajor: ");
                major = scanner.nextLine().trim();

                if (allArgsNecessary && major.isEmpty()) {
                    System.err.println("**** This field is mandatory. ****");
                    Thread.sleep(1000);
                    invalid = true;
                } else invalid = false;
            } while (invalid);

            boolean gradFieldNecessary = !major.isEmpty();

            String graduateStudentEntry;
            do {
                System.out.print("\tIs Graduate Student? (Y/N): ");
                graduateStudentEntry = scanner.nextLine();
                if ((allArgsNecessary || gradFieldNecessary) && graduateStudentEntry.trim().isEmpty()) {
                    System.err.println("**** This field is mandatory. ****");
                    Thread.sleep(1000);
                    invalid = true;
                } else if (!graduateStudentEntry.isEmpty()) {
                    if (!(graduateStudentEntry.equalsIgnoreCase("Y") || graduateStudentEntry.equalsIgnoreCase("N"))) {
                        System.err.println("**** Please input 'Y' or 'N' ****");
                        Thread.sleep(1000);
                        invalid = true;
                    } else {
                        isGraduateStudent = graduateStudentEntry.equalsIgnoreCase("Y");
                        invalid = false;
                    }
                } else invalid = false;
            } while (invalid);

            Student s;
            if (allArgsNecessary) {
                s = new Student(name, birthYear, major, isGraduateStudent);
            } else if (gradFieldNecessary) {
                s = new Student(major, isGraduateStudent);
            } else if (!graduateStudentEntry.isEmpty()) {
                s = new Student(isGraduateStudent);
            } else {
                s = new Student();
            }
            students.add(s);

            if (addAnother) {
                System.out.print("\nAdd another? (Y/N): ");
                addAnother = scanner.nextLine().equalsIgnoreCase("Y");
            }
        } while (addAnother);
    }

    private static void addNewFaculty(Scanner scanner, InsertionMode mode) throws InterruptedException {
        scanner.nextLine(); //skip the new line

        boolean addAnother = (mode == InsertionMode.BATCH);
        do {
            String name, deptName;
            int birthYear = 0;
            boolean isTenured = false;
            boolean invalid; //will be used to break out of loops
            boolean allArgsNecessary = false;

            System.out.print("\nAdd new faculty:\n******************\n\tName: ");
            name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                allArgsNecessary = true;
                do {
                    System.out.print("\tBirth year: ");
                    String birthYearString = scanner.nextLine();
                    try {
                        birthYear = Integer.parseInt(birthYearString.trim());
                        if (birthYear < 1900) throw new InvalidInputException("Birth year before 1900");
                        invalid = false; //if this line is reached, the year was parsed successfully
                    } catch (NumberFormatException | InvalidInputException e) {
                        System.err.println("**** Input must be a valid numeric year. Please try again. ****");
                        Thread.sleep(1000);
                        invalid = true;
                    }
                } while (invalid);
            }

            do {
                System.out.print("\tDepartment: ");
                deptName = scanner.nextLine().trim();

                if (allArgsNecessary && deptName.isEmpty()) {
                    System.err.println("**** This field is mandatory. ****");
                    Thread.sleep(1000);
                    invalid = true;
                } else invalid = false;
            } while (invalid);

            boolean tenuredFieldNecessary = !deptName.isEmpty();

            String tenuredEntry;
            do {
                System.out.print("\tIs Tenured? (Y/N): ");
                tenuredEntry = scanner.nextLine();
                if ((allArgsNecessary || tenuredFieldNecessary) && tenuredEntry.trim().isEmpty()) {
                    System.err.println("**** This field is mandatory. ****");
                    Thread.sleep(1000);
                    invalid = true;
                } else if (!tenuredEntry.isEmpty()) {
                    if (!(tenuredEntry.equalsIgnoreCase("Y") || tenuredEntry.equalsIgnoreCase("N"))) {
                        System.err.println("**** Please input 'Y' or 'N' ****");
                        Thread.sleep(1000);
                        invalid = true;
                    } else {
                        isTenured = tenuredEntry.equalsIgnoreCase("Y");
                        invalid = false;
                    }
                } else invalid = false;
            } while (invalid);

            Faculty f;
            if (allArgsNecessary) {
                f = new Faculty(name, birthYear, deptName, isTenured);
            } else if (tenuredFieldNecessary) {
                f = new Faculty(deptName, isTenured);
            } else if (!tenuredEntry.isEmpty()) {
                f = new Faculty(isTenured);
            } else {
                f = new Faculty();
            }
            faculty.add(f);

            if (addAnother) {
                System.out.print("\nAdd another? (Y/N): ");
                addAnother = scanner.nextLine().equalsIgnoreCase("Y");
            }
        } while (addAnother);
    }

    private static void addNewGeneralStaff(Scanner scanner, InsertionMode mode) throws InterruptedException {
        scanner.nextLine(); //skip the new line

        boolean addAnother = (mode == InsertionMode.BATCH);
        do {
            String name, deptName, duty;
            int birthYear = 0;
            boolean invalid; //will be used to break out of loops
            boolean allArgsNecessary = false;

            System.out.print("\nAdd new general staff:\n******************\n\tName: ");
            name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                allArgsNecessary = true;
                do {
                    System.out.print("\tBirth year: ");
                    String birthYearString = scanner.nextLine();
                    try {
                        birthYear = Integer.parseInt(birthYearString.trim());
                        if (birthYear < 1900) throw new InvalidInputException("Birth year before 1900");
                        invalid = false; //if this line is reached, the year was parsed successfully
                    } catch (NumberFormatException | InvalidInputException e) {
                        System.err.println("**** Input must be a valid numeric year. Please try again. ****");
                        Thread.sleep(1000);
                        invalid = true;
                    }
                } while (invalid);
            }

            do {
                System.out.print("\tDepartment: ");
                deptName = scanner.nextLine().trim();

                if (allArgsNecessary && deptName.isEmpty()) {
                    System.err.println("**** This field is mandatory. ****");
                    Thread.sleep(1000);
                    invalid = true;
                } else invalid = false;
            } while (invalid);

            boolean dutyNecessary = !deptName.isEmpty();
            do {
                System.out.print("\tDuty: ");
                duty = scanner.nextLine().trim();

                if ((allArgsNecessary || dutyNecessary) && duty.isEmpty()) {
                    System.err.println("**** This field is mandatory. ****");
                    Thread.sleep(1000);
                    invalid = true;
                } else invalid = false;
            } while (invalid);

            GeneralStaff g;
            if (allArgsNecessary) {
                g = new GeneralStaff(name, birthYear, deptName, duty);
            } else if (dutyNecessary) {
                g = new GeneralStaff(deptName, duty);
            } else if (!duty.isEmpty()) {
                g = new GeneralStaff(duty);
            } else {
                g = new GeneralStaff();
            }
            generalStaff.add(g);

            if (addAnother) {
                System.out.print("\nAdd another? (Y/N): ");
                addAnother = scanner.nextLine().equalsIgnoreCase("Y");
            }
        } while (addAnother);
    }

    private static Course[] createNewCourse(Scanner scanner, InsertionMode mode) throws InterruptedException {
        scanner.nextLine(); //skip the new line

        ArrayList<Course> createdCourses = new ArrayList<>(); //to be returned

        boolean addAnother = (mode == InsertionMode.BATCH);
        do {
            String courseDept;
            int courseNum = 0;
            int numCredits = 0;
            boolean isGraduateCourse = false;

            boolean invalid; //will be used to break out of loops

            System.out.print("\nAdd new course:\n******************\n");
            do {
                System.out.print("\tDepartment: ");
                courseDept = scanner.nextLine().trim();
                if (courseDept.isEmpty()) {
                    System.err.println("**** This field is mandatory. ****");
                    Thread.sleep(1000);
                    invalid = true;
                } else invalid = false;
            } while (invalid);

            do {
                System.out.print("\tCourse #: ");
                String courseNumEntry = scanner.nextLine().trim();
                try {
                    courseNum = Integer.parseInt(courseNumEntry.trim());
                    if (courseNum < 0) throw new InvalidInputException("Input was a negative integer");
                    invalid = false; //if this line is reached, the int was parsed successfully
                } catch (NumberFormatException | InvalidInputException e) {
                    System.err.println("**** Input must be a positive number. Please try again. ****");
                    Thread.sleep(1000);
                    invalid = true;
                }
            } while (invalid);

            do {
                System.out.print("\tCredits: ");
                String creditsEntry = scanner.nextLine().trim();
                try {
                    numCredits = Integer.parseInt(creditsEntry.trim());
                    if (numCredits < 0) throw new InvalidInputException("Input was a negative integer");
                    invalid = false; //if this line is reached, the int was parsed successfully
                } catch (NumberFormatException | InvalidInputException e) {
                    System.err.println("**** Input must be a positive number. Please try again. ****");
                    Thread.sleep(1000);
                    invalid = true;
                }
            } while (invalid);

            String graduateEntry;
            do {
                System.out.print("\tGraduate Course? (Y/N): ");
                graduateEntry = scanner.nextLine();
                if (graduateEntry.trim().isEmpty()) {
                    System.err.println("**** This field is mandatory. ****");
                    Thread.sleep(1000);
                    invalid = true;
                } else if (!graduateEntry.isEmpty()) {
                    if (!(graduateEntry.equalsIgnoreCase("Y") || graduateEntry.equalsIgnoreCase("N"))) {
                        System.err.println("**** Please input 'Y' or 'N' ****");
                        Thread.sleep(1000);
                        invalid = true;
                    } else {
                        isGraduateCourse = graduateEntry.equalsIgnoreCase("Y");
                        invalid = false;
                    }
                } else invalid = false;
            } while (invalid);

            Course c = new Course(isGraduateCourse, courseNum, courseDept, numCredits);
            courses.add(c);
            createdCourses.add(c);

            if (addAnother) {
                System.out.print("\nAdd another? (Y/N): ");
                addAnother = scanner.nextLine().equalsIgnoreCase("Y");
            }
        } while (addAnother);

        Course[] cArr = new Course[0];
        return createdCourses.toArray(cArr);
    }

    private static void printObjects(String header, ArrayList<?> arrayList) {
        System.out.printf("************************************************\n%s\n", header);
        //Display students
        for (Object o : arrayList) {
            System.out.println(o.toString());
        }
        System.out.print("************************************************\n");
    }

    private static void printObjects(String header, Object... objects) {
        System.out.printf("************************************************\n%s\n", header);
        //Display students
        for (Object o : objects) {
            System.out.println(o.toString());
        }
        System.out.print("************************************************\n");
    }

    private static void printNumberedObjects(String header, ArrayList<?> arrayList) {
        System.out.printf("************************************************\n%s\n\n", header);
        //Display students
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.printf("(%d) %s\n", (i + 1), arrayList.get(i).toString());
        }
        System.out.print("************************************************\n");
    }

    private static void printNumberedObjects(String header, Object... objects) {
        System.out.printf("************************************************\n%s\n\n", header);
            //Display contents
            for (int i = 0; i < objects.length; i++) {
                String s = objects[i].toString();
                if (s.isEmpty()) break;
                System.out.printf("(%d) %s\n", (i + 1), s);
            }
        System.out.print("************************************************\n");
    }

    private static void trimWhitespace(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].trim();
        }
    }

    private enum InsertionMode {
        INDIVIDUAL, BATCH
    }
}

class InvalidObjectException extends RuntimeException {
    public InvalidObjectException() {
        super();
    }

    public InvalidObjectException(String message) {
        super(message);
    }
}

class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
