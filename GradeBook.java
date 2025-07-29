package java2.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

class GradeBook {
    private int courseNo;
    private String courseName;

    private String[] instructorName;
    private final ArrayList<Student> list;

    public GradeBook() {
        list = new ArrayList<>();
        instructorName = new String[]{"", ""};
        courseName = "<unknown_course_name>";
        courseNo = -1;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setInstructorName(String[] instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorName() {
        return instructorName[0] + " " + instructorName[1];
    }

    public int getNumberOfStudents() {
        return list.size();
    }

    public void addRecord() {
        Student newStudent = getNewStudentRecord();
        System.out.printf(Locale.ENGLISH, "The final grade for %1$s(ID# %2$d) is %3$.2f(%4$s)%n", newStudent.getName(), newStudent.getID(), newStudent.getAvg(), newStudent.getCharGrade() + "");
        list.add(newStudent);
    }

    private Student getNewStudentRecord() {
        Scanner input = new Scanner(System.in);
        int ID;
        String[] name = {"", ""};
        int[] grades = new int[3];

        do {
            System.out.print("Enter the student's ID and then name : ");
            String text = input.nextLine().trim();
            String[] parts = text.split(" ");
            if (parts.length > 1) {
                try {
                    ID = Integer.parseInt(parts[0]);
                    if (searchRecord(ID) != null) {
                        System.out.printf(Locale.ENGLISH, "ERROR: the student ID #%d is already exists%n", ID);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid student ID!");
                    continue;
                }

                name[0] = parts[1];

                for (int i = 2; i < parts.length; i++) {
                    if (!parts[i].isEmpty()) {
                        name[1] += parts[i] + " ";
                    }
                }
                name[1] = name[1].trim();
                break;
            }
        } while (true);

        do {
            System.out.print("Enter the student's exam grades : ");
            String text = input.nextLine().trim();
            String[] parts = text.split(" ");
            if (parts.length == 3) {
                try {
                    grades[0] = Integer.parseInt(parts[0]);
                    grades[1] = Integer.parseInt(parts[1]);
                    grades[2] = Integer.parseInt(parts[2]);
                    if (isValidGrade(grades[0]) && isValidGrade(grades[1]) && isValidGrade(grades[2])) {
                        break;
                    }

                } catch (NumberFormatException e) {
                }
            }
            System.out.println("Invalid exam grades!");
        } while (true);

        return new Student(ID, name, grades);
    }

    private boolean isValidGrade(int grade) {
        return (grade >= 0 && grade <= 100);
    }

    public void addRecordsFromFile(String fname) {
        ArrayList<Student> students = new ArrayList<>();
        if (fname == null || fname.trim().length() == 0) {
            System.out.println("file to read data is invalid");
            return;
        }

        File fl = new File(fname);
        if (!fl.exists()) {
            System.out.println("file to read data is not exist");
            return;
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fname);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                int ID = -1;
                String[] name = null;
                int[] grades = null;

                //each row is ID;[firstname,lastname];[exam 1,exam 2,final exam]
                String[] parts = line.trim().split(";");
                if (parts.length == 3) {

                    try {
                        ID = Integer.parseInt(parts[0].trim());
                    } catch (NumberFormatException e) {

                    }


                    String namePart = parts[1].trim();
                    if (namePart.startsWith("[") && namePart.endsWith("]")) {
                        //remove []
                        namePart = namePart.substring(1, namePart.length() - 1).trim();
                        String[] fullName = namePart.trim().split(",");
                        if (fullName.length == 2) {
                            String firstName = fullName[0].trim();
                            String lastName = fullName[1].trim();
                            name = new String[]{firstName, lastName};
                        }
                    }

                    String gradesPart = parts[2].trim();
                    if (gradesPart.startsWith("[") && gradesPart.endsWith("]")) {
                        //remove []
                        gradesPart = gradesPart.substring(1, gradesPart.length() - 1).trim();
                        String[] gradesArray = gradesPart.split(",");
                        if (gradesArray.length == 3) {
                            try {
                                int exam1 = Integer.parseInt(gradesArray[0].trim());
                                int exam2 = Integer.parseInt(gradesArray[1].trim());
                                int finalExam = Integer.parseInt(gradesArray[2].trim());
                                if (isValidGrade(exam1) && isValidGrade(exam2) && isValidGrade(finalExam)) {
                                    grades = new int[]{exam1, exam2, finalExam};
                                }
                            } catch (NumberFormatException e) {

                            }
                        }
                    }

                    if (ID != -1 && name != null && grades != null) {
                        Student student = new Student(ID, name, grades);
                        students.add(student);
                    }
                }
            }

            try {
                reader.close();
            } catch (IOException e) {
            }
        } catch (IOException e) {
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {

                }
            }
        }
        if (students.isEmpty()) {
            System.out.println("No records in this file!");
        } else {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                if(searchRecord(student.getID()) == null){
                    System.out.println(student);
                    list.add(student);
                }
            }
        }
    }

    public Student searchRecord(int ID) {
        int countStudents = getNumberOfStudents();
        for (int i = 0; i < countStudents; i++) {
            Student student = list.get(i);
            if (student.getID() == ID) {
                return student;
            }
        }
        return null;
    }

    public int[] countCharGrades() {
        int[] grades = new int[5];
        int countStudents = getNumberOfStudents();
        if (countStudents == 0) {
            return grades;
        }

        for (int i = 0; i < countStudents; i++) {
            Student student = list.get(i);
            switch (student.getCharGrade()) {
                case 'A':
                    grades[0]++;
                    break;
                case 'B':
                    grades[1]++;
                    break;
                case 'C':
                    grades[2]++;
                    break;
                case 'D':
                    grades[3]++;
                    break;
                case 'F':
                    grades[4]++;
                    break;
            }
        }
        return grades;
    }

    public Student maxRecord() {
        int countStudents = getNumberOfStudents();
        if (countStudents == 0) {
            return null;
        }
        Student max = list.get(0);
        for (int i = 1; i < countStudents; i++) {
            Student student = list.get(i);
            if (student.getAvg() > max.getAvg()) {
                max = student;
            }
        }
        return max;
    }

    public Student minRecord() {
        int countStudents = getNumberOfStudents();

        if (countStudents == 0) {
            return null;
        }
        Student min = list.get(0);
        for (int i = 1; i < countStudents; i++) {
            Student student = list.get(i);
            if (student.getAvg() < min.getAvg()) {
                min = student;
            }
        }
        return min;
    }

    private double getAverageScore() {
        int countStudents = getNumberOfStudents();
        if (countStudents == 0) return 0.0;
        double sum = 0.0;

        for (int i = 0; i < countStudents; i++) {
            Student student = list.get(i);
            sum += student.getAvg();
        }
        return sum / countStudents;
    }

    public String printStatistics() {
        double averageScore = getAverageScore();
        Student min = minRecord();
        Student max = maxRecord();
        double lowestScore = 0.0;
        double highestScore = 0.0;
        if (min != null) {
            lowestScore = min.getAvg();
        }
        if (max != null) {
            highestScore = max.getAvg();
        }

        int[] countGrades = countCharGrades();
        int studentsCount = getNumberOfStudents();
        double[] percentGrades = new double[countGrades.length];
        if (studentsCount > 0) {
            for (int i = 0; i < percentGrades.length; i++) {
                percentGrades[i] = ((double) countGrades[i] / (double) studentsCount) * 100.0;
            }
        }
        return String.format(Locale.ENGLISH, "Statistical Results of %1$s %2$d (Instructor : %3$s):", getCourseName(), getCourseNo(), getInstructorName())
                + "\n" + "Total number of student records: " + studentsCount
                + "\n" + String.format(Locale.ENGLISH, "Average Score %.2f" , averageScore)
                + "\n" + String.format(Locale.ENGLISH, "Highest Score %.2f", highestScore)
                + "\n" + String.format(Locale.ENGLISH, "Lowest Score %.2f", lowestScore)
                + "\n" + String.format(Locale.ENGLISH, "Total 'A' Grades: %1$d (%2$.2f %% of class)", countGrades[0], percentGrades[0])
                + "\n" + String.format(Locale.ENGLISH, "Total 'B' Grades: %1$d (%2$.2f %% of class)", countGrades[1], percentGrades[1])
                + "\n" + String.format(Locale.ENGLISH, "Total 'C' Grades: %1$d (%2$.2f %% of class)", countGrades[2], percentGrades[2])
                + "\n" + String.format(Locale.ENGLISH, "Total 'D' Grades: %1$d (%2$.2f %% of class)", countGrades[3], percentGrades[3])
                + "\n" + String.format(Locale.ENGLISH, "Total 'F' Grades: %1$d (%2$.2f %% of class)", countGrades[4], percentGrades[4]);
    }


}

