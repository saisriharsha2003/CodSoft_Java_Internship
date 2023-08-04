import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_NAME = "student_data.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentDataFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void saveStudentDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                writer.write(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
                writer.newLine();
            }
            System.out.println("Student data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving student data to file: " + e.getMessage());
        }
    }

    public void loadStudentDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String name = data[0];
                    int rollNumber = Integer.parseInt(data[1]);
                    String grade = data[2];
                    Student student = new Student(name, rollNumber, grade);
                    students.add(student);
                }
            }
            System.out.println("Student data loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Creating a new file.");
        } catch (IOException e) {
            System.out.println("Error loading student data from file: " + e.getMessage());
        }
    }
}


