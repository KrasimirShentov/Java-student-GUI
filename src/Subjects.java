public class Subjects {
    private String subjectName;
    private double grade;

    public Subjects(String subjectName, double grade) {
        this.subjectName = subjectName;
        this.grade = grade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        if (subjectName.matches("\\d+")) {
            throw new IllegalArgumentException("SubjectName cannot contain any digits.");
        }
        else{
            this.subjectName = subjectName;
        }
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade % 1 == 0) {
            this.grade = grade;
        }
        else {
            System.out.println("Invalid grade format. Grade must be an integer.");
        }
    }


}
