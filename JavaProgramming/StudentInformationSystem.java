import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentInformationSystem {
    // Student class
    static class Student {
        private final String studentID;
        private final String name;
        private final String email;

        public Student(String studentID, String name, String email) {
            this.studentID = studentID;
            this.name = name;
            this.email = email;
        }

        public String getStudentID() { return studentID; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    // Course class
    static class Course {
        private final String courseID;
        private final String courseName;
        private final int credits;

        public Course(String courseID, String courseName, int credits) {
            this.courseID = courseID;
            this.courseName = courseName;
            this.credits = credits;
        }

        public String getCourseID() { return courseID; }
        public String getCourseName() { return courseName; }
        public int getCredits() { return credits; }
    }

    // Enrollment class
    static class Enrollment {
        private final Student student;
        private final Course course;
        private final String grade;

        public Enrollment(Student student, Course course, String grade) {
            this.student = student;
            this.course = course;
            this.grade = grade;
        }

        public Student getStudent() { return student; }
        public Course getCourse() { return course; }
        public String getGrade() { return grade; }
    }

    // Transcript class
    static class Transcript {
        private final Student student;
        private final List<Enrollment> enrollments;

        public Transcript(Student student, List<Enrollment> enrollments) {
            this.student = student;
            this.enrollments = enrollments;
        }

        public void generateTranscript() {
            System.out.println("Transcript for: " + student.getName());
            for (Enrollment enrollment : enrollments) {
                System.out.println(enrollment.getCourse().getCourseName() + ": " + enrollment.getGrade());
            }
        }
    }

    // Main class to manage the system
    static class SIS {
        private final List<Student> students;
        private final List<Course> courses;
        private final List<Enrollment> enrollments;

        public SIS() {
            students = new ArrayList<>();
            courses = new ArrayList<>();
            enrollments = new ArrayList<>();
        }

        public void addStudent(String studentID, String name, String email) {
            Student student = new Student(studentID, name, email);
            students.add(student);
        }

        public void addCourse(String courseID, String courseName, int credits) {
            Course course = new Course(courseID, courseName, credits);
            courses.add(course);
        }

        public void enrollStudent(String studentID, String courseID, String grade) {
            Student student = findStudent(studentID);
            Course course = findCourse(courseID);
            if (student != null && course != null) {
                Enrollment enrollment = new Enrollment(student, course, grade);
                enrollments.add(enrollment);
            }
        }

        public void generateTranscript(String studentID) {
            Student student = findStudent(studentID);
            if (student != null) {
                List<Enrollment> studentEnrollments = getEnrollmentsByStudent(student);
                Transcript transcript = new Transcript(student, studentEnrollments);
                transcript.generateTranscript();
            }
        }

        private Student findStudent(String studentID) {
            for (Student student : students) {
                if (student.getStudentID().equals(studentID)) {
                    return student;
                }
            }
            return null;
        }

        private Course findCourse(String courseID) {
            for (Course course : courses) {
                if (course.getCourseID().equals(courseID)) {
                    return course;
                }
            }
            return null;
        }

        private List<Enrollment> getEnrollmentsByStudent(Student student) {
            List<Enrollment> studentEnrollments = new ArrayList<>();
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getStudent().equals(student)) {
                    studentEnrollments.add(enrollment);
                }
            }
            return studentEnrollments;
        }
    }

    public static void main(String[] args) {
        SIS sis = new SIS();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Information System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student");
            System.out.println("4. Generate Transcript");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student ID: ");
                    String studentID = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Student Email: ");
                    String email = scanner.nextLine();
                    sis.addStudent(studentID, name, email);
                }
                case 2 -> {
                    System.out.print("Enter Course ID: ");
                    String courseID = scanner.nextLine();
                    System.out.print("Enter Course Name: ");
                    String courseName = scanner.nextLine();
                    System.out.print("Enter Credits: ");
                    int credits = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    sis.addCourse(courseID, courseName, credits);
                }
                case 3 -> {
                    System.out.print("Enter Student ID: ");
                    String sID = scanner.nextLine();
                    System.out.print("Enter Course ID: ");
                    String cID = scanner.nextLine();
                    System.out.print("Enter Grade: ");
                    String grade = scanner.nextLine();
                    sis.enrollStudent(sID, cID, grade);
                }
                case 4 -> {
                    System.out.print("Enter Student ID: ");
                    String stID = scanner.nextLine();
                    sis.generateTranscript(stID);
                }
                case 5 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
