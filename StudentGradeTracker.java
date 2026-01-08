import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Integer> grades;

    Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    double getAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    int getHighest() {
        int max = grades.get(0);
        for (int grade : grades) {
            if (grade > max) max = grade;
        }
        return max;
    }

    int getLowest() {
        int min = grades.get(0);
        for (int grade : grades) {
            if (grade < min) min = grade;
        }
        return min;
    }
}

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int numStudents = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 0; i < numStudents; i++) {
            System.out.print("\nEnter student name: ");
            String name = sc.nextLine();
            Student student = new Student(name);

            System.out.print("Enter number of subjects for " + name + ": ");
            int numSubjects = sc.nextInt();

            for (int j = 0; j < numSubjects; j++) {
                System.out.print("Enter grade for subject " + (j + 1) + ": ");
                int grade = sc.nextInt();
                student.grades.add(grade);
            }
            sc.nextLine(); // consume newline
            students.add(student);
        }

        System.out.println("\n===== STUDENT GRADE REPORT =====");
        for (Student s : students) {
            System.out.println("\nName: " + s.name);
            System.out.println("Grades: " + s.grades);
            System.out.printf("Average: %.2f\n", s.getAverage());
            System.out.println("Highest: " + s.getHighest());
            System.out.println("Lowest: " + s.getLowest());
        }

        sc.close();
    }
}

