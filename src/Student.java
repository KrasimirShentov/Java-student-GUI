import java.util.ArrayList;

public class Student extends Subjects{

    private String name = "";
    private String studentNumber;
    private String course;
    private String specialty;
    private ArrayList<Subjects> subjects;

    public Student(String name, String studentNumber, String course, String specialty){
        super("", 0.00);
        this.name = name;
        this.studentNumber = studentNumber;
        this.course = course;
        this.specialty = specialty;
        this.subjects = new ArrayList<>();
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name){
    }
    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber){
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
    }

    public ArrayList<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subject) {
        this.subjects.add(subject);
    }

    public void addSubject(String subjectName, double grade){
        Subjects sub = new Subjects(subjectName, grade);
        subjects.add(sub);
    }

    public void editSubject(String oldSubjectName, String newSubjectName, double newGrade) {
        for (Subjects subject : subjects) {
            if (subject.getSubjectName().equals(oldSubjectName)) {
                subject.setSubjectName(newSubjectName);
                subject.setGrade(newGrade);
                return;
            }
        }
    }
    public String ToString(){
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(name).append("\n");
        result.append("Student Number: ").append(studentNumber).append("\n");
        result.append("Course: ").append(course).append("\n");
        result.append("Specialty: ").append(specialty).append("\n");

        if (!subjects.isEmpty()) {
            result.append("Subjects:\n");
            for (Subjects subject : subjects) {
                result.append("  Subject Name: ").append(subject.getSubjectName())
                        .append(", Grade: ").append(subject.getGrade()).append("\n");
            }
        }

        return result.toString();
    }
}
