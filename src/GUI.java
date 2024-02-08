import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GUI extends JFrame {

    private ArrayList<Student> students;
    private JTextField studentNumberField;
    private JTextField courseField;
    private JTextField specialtyField;
    private JTextArea studentResult;

    JPanel panel = new JPanel();

    public GUI() {

        students = new ArrayList<>();

        Student s1 = new Student("Ivan Neznam", "2301321023", "3", "Software Engineering");
        s1.addSubject("Math", 5.5);
        s1.addSubject("Math 2", 6);
        s1.addSubject("Science", 4.5);

        Student s2 = new Student("Joro Ne", "2301321024", "3", "Software Engineering");
        s2.addSubject("Math", 6);
        s2.addSubject("Science", 3.5);

        Student s3 = new Student("Georgi Da", "2301321025", "3", "Software Engineering");
        s3.addSubject("Math", 4.5);
        s3.addSubject("Science", 3);

        students.add(s1);
        students.add(s2);
        students.add(s3);

        initializeUI();

    }
    private void initializeUI() {
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(4, 2));
        JLabel studentNumberLabel = new JLabel("Enter student's number:");
        studentNumberField = new JTextField(10);
        JLabel courseLabel = new JLabel("Enter student's course:");
        courseField = new JTextField(10);
        JLabel specialtyLabel = new JLabel("Enter student's specialty:");
        specialtyField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton editButton = new JButton("Edit");
        JButton showSubjectsButton = new JButton("Show All Subjects");
        JButton showAverageGradeButton = new JButton("Show Average Grade");

        topPanel.add(studentNumberLabel);
        topPanel.add(studentNumberField);
        topPanel.add(courseLabel);
        topPanel.add(courseField);
        topPanel.add(specialtyLabel);
        topPanel.add(specialtyField);
        topPanel.add(searchButton);
        topPanel.add(addButton);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(showSubjectsButton);
        buttonPanel.add(showAverageGradeButton);

        studentResult = new JTextArea(10, 40);
        studentResult.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(studentResult), BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchStudent());
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        editButton.addActionListener(e -> editStudent());
        showSubjectsButton.addActionListener(e -> printAllSubjects());
        showAverageGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String specialty = specialtyField.getText();
                String course = courseField.getText();
                averageGrade(specialty, course);
            }
        });

        updateStudentList();
    }

    //Search student and his subjects by student Number!
    private void searchStudent() {
        String inputStudentNumber = studentNumberField.getText();
        Student foundStudent = null;

        for (Student student : students) {
            if (student.getStudentNumber().equals(inputStudentNumber)) {
                foundStudent = student;
                break;
            }
        }

        if (foundStudent != null) {
            studentResult.setText("Student Information:\n" + foundStudent.ToString());
        } else {
            JOptionPane.showMessageDialog(this, "Student with student number " + inputStudentNumber + " not found.");
        }
    }

    private void addStudent() {
        try {
            String name = JOptionPane.showInputDialog("Enter student's name:");
            if (name.length() < 3) {
                throw new IllegalArgumentException("Name must contain at least 3 characters.");
            }

            String studentNumber = JOptionPane.showInputDialog("Enter student's number:");
            if (!studentNumber.matches("\\d{10}")) {
                throw new IllegalArgumentException("Student number must be exactly 10 digits.");
            }

            String course = JOptionPane.showInputDialog("Enter student's course:");
            if (course.length() != 1 || !Character.isDigit(course.charAt(0))) {
                throw new IllegalArgumentException("Course must be exactly 1 digit long.");
            }

            String specialty = JOptionPane.showInputDialog("Enter student's specialty:");
            if (specialty.length() <= 1) {
                throw new IllegalArgumentException("Specialty must contain at least 3 characters.");
            }

            Student newStudent = new Student(name, studentNumber, course, specialty);
            addSubjectsToStudent(newStudent);

            students.add(newStudent);
            updateStudentList();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Delete student and his subjects by student Number!
    private void deleteStudent() {
        try {
            String inputStudentNumber = studentNumberField.getText();
            Student foundStudent = null;

            for (Student student : students) {
                if (student.getStudentNumber().equals(inputStudentNumber)) {
                    foundStudent = student;
                    break;
                }
            }

            if (foundStudent != null) {
                students.remove(foundStudent);
                updateStudentList();
            } else {
                throw new IllegalArgumentException("Student with student number " + inputStudentNumber + " not found.");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Edit student and his subjects by student Number!
    private void editStudent() {
        try {
            String inputStudentNumber = studentNumberField.getText();
            Student foundStudent = null;

            for (Student student : students) {
                if (student.getStudentNumber().equals(inputStudentNumber)) {
                    foundStudent = student;
                    break;
                }
            }

            if (foundStudent != null) {
                String newName = JOptionPane.showInputDialog("Enter new name for student:", foundStudent.getName());
                String newStudentNumber = JOptionPane.showInputDialog("Enter new student number:", foundStudent.getStudentNumber());
                String newCourse = JOptionPane.showInputDialog("Enter new course for student:", foundStudent.getCourse());
                String newSpeciality = JOptionPane.showInputDialog("Enter new specialty for student:", foundStudent.getSpecialty());

                foundStudent.setName(newName);
                foundStudent.setStudentNumber(newStudentNumber);
                foundStudent.setCourse(newCourse);
                foundStudent.setSpecialty(newSpeciality);
                editSubjects(foundStudent);
                updateStudentList();
            } else {
                throw new IllegalArgumentException("Student with student number " + inputStudentNumber + " not found.");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Prints the new list with all the updates.
    private void updateStudentList() {
        StringBuilder studentList = new StringBuilder();
        for (Student student : students) {
            studentList.append(student.ToString()).append("\n");
        }
        studentResult.setText("Student List:\n" + studentList.toString());
    }
    private void addSubjectsToStudent(Student student) {
        try {
            int subjectCount = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of subjects:"));

            for (int i = 0; i < subjectCount; i++) {
                String subjectName = JOptionPane.showInputDialog("Enter subject name:");
                double subjectGrade = Double.parseDouble(JOptionPane.showInputDialog("Enter subject grade:"));
                student.addSubject(subjectName, subjectGrade);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Invalid input for subject count or grade.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editSubjects(Student student) {
        int subjectCount = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of subjects to edit:"));

        for (int i = 0; i < subjectCount; i++) {
            String oldSubjectName = JOptionPane.showInputDialog("Enter current subject name to edit:");
            String newSubjectName = JOptionPane.showInputDialog("Enter new subject name:");
            double newGrade = Double.parseDouble(JOptionPane.showInputDialog("Enter new grade for the subject:"));


            for (Subjects subject : student.getSubjects()) {
                if (subject.getSubjectName().equals(oldSubjectName)) {
                    subject.setSubjectName(newSubjectName);
                    subject.setGrade(newGrade);
                    break;
                }
            }
        }

        updateStudentList();
    }

    //Show all the subjects that all students study
    private void printAllSubjects() {
        StringBuilder subjectsInfo = new StringBuilder("Subjects Information:\n");

        for (Student student : students) {
            subjectsInfo.append("Student: ").append(student.getName()).append("\n");

            for (Subjects subject : student.getSubjects()) {
                subjectsInfo.append("  Subject: ").append(subject.getSubjectName())
                        .append(", Grade: ").append(subject.getGrade()).append("\n");
            }

            subjectsInfo.append("\n");
        }

        studentResult.setText(subjectsInfo.toString());
    }

    //Calculates the average grade of all students in the same course and specialty.
    private void averageGrade(String specialty, String course) {
        double totalGrade = 0;
        int count = 0;

        for (Student student : students) {
            if (student.getSpecialty().equals(specialty) && student.getCourse().equals(course)) {
                for (Subjects subject : student.getSubjects()) {
                    totalGrade += subject.getGrade();
                    count++;
                }
            }
        }

        double average = count > 0 ? totalGrade / count : 0;

        // Formatting the average to display with two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedAverage = decimalFormat.format(average);

        JOptionPane.showMessageDialog(this, "Average Grade for " + specialty + " in course " + course + ": " + formattedAverage);
    }
}