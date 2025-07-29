package java2.project;
import java.util.Locale;

class Student {
    private final int ID;
    private final String[] name;
    private final int[] grades;
    private final double avg;
    private final char charGrade;

    public Student(int ID, String[] name, int[] grades) {
        this.ID = ID;
        this.name = name;
        this.grades = grades;
        avg = calculateAvg();
        charGrade = calculateCharacterGrade();
    }

    public Student() {
        ID = -1;
        grades = new int[3];
        name = new String[]{"", ""};
        avg = calculateAvg();
        charGrade = calculateCharacterGrade();
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "Student Record for %1$s (ID # %2$d ): ", getName(), getID())
                + "\n" + "Exam 1 : " + grades[0]
                + "\n" + "Exam 2 : " + grades[1]
                + "\n" + "Final Exam : " + grades[2]
                + "\n" + String.format(Locale.ENGLISH, "Final Grade: %.2f", getAvg())
                + "\n" + "Letter Grade: " + getCharGrade();
    }

    public String getName() {
        return name[0] + " " + name[1];
    }

    public int getID() {
        return ID;
    }

    public double getAvg() {
        return avg;
    }

    public char getCharGrade() {
        return charGrade;
    }

    private double calculateAvg() {
        return (grades[0] * 30.0 + grades[1] * 30.0 + grades[2] * 40.0) / 100.0;
    }

    private char calculateCharacterGrade() {
        if (avg >= 90) {
            return 'A';
        } else if (avg >= 80) {
            return 'B';
        } else if (avg >= 70) {
            return 'C';
        } else if (avg >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }


}
