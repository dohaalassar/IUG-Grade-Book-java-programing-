package java2.project;


import java.util.Locale;
import java.util.Scanner;

class TestGradeBook {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the IUG Grade Book.");
        System.out.println("Please give us some basic information...");

        String courseName;
        int courseNo;
        do {
            System.out.print("\nWhat is the course number for your class: ");
            String s = input.nextLine().trim();
            String[] parts = s.split(" ");
            if (parts.length == 2) {
                courseName = parts[0];
                try {
                    courseNo = Integer.parseInt(parts[1]);
                    break;
                } catch (NumberFormatException e) {
                }
            }
            System.out.println("Invalid course number!");
        } while (true);


        String[] instructorName = {"", ""};//first name and last name
        do {
            System.out.print("\nWhat is the name of the instructor: ");
            String fullInstructorName = input.nextLine().trim();
            String[] parts = fullInstructorName.split(" ");
            if (parts.length >= 2) {
                instructorName[0] = parts[0];
                for (int i = 1; i < parts.length; i++) {
                    if (!parts[i].isEmpty()) {
                        instructorName[1] += parts[i] + " ";
                    }
                }
                instructorName[1] = instructorName[1].trim();
                break;
            }
            System.out.println("Invalid instructor's name!");
        } while (true);

        System.out.println("\nThank you. Processing...");

        GradeBook gradeBook = new GradeBook();
        gradeBook.setCourseName(courseName.toUpperCase(Locale.ROOT));
        gradeBook.setCourseNo(courseNo);
        gradeBook.setInstructorName(instructorName);
        int choice;
        do {
            showMenu();
            System.out.print("Enter Your Choice: ");
             choice = input.nextInt();
            switch (choice) {
                case 1://add record
                    System.out.println();
                    gradeBook.addRecord();
                    break;
                case 2://add records from file
                    input.nextLine();
                    System.out.println();
                    System.out.print("Enter your file name : ");
                    String fname = input.nextLine();
                    gradeBook.addRecordsFromFile(fname.trim());//"values.txt"
                    break;
                case 3:// Display a student record
                    System.out.println();
                    System.out.print("Enter new ID : ");
                    int ID = input.nextInt();
                    Student student = gradeBook.searchRecord(ID);
                    if (student != null) {
                        System.out.println(student);
                    } else {
                        System.out.printf(Locale.ENGLISH, "ERROR: there is no record for student ID # %d%n", ID);
                    }
                    break;
                case 4://Display statistical results of class
                    System.out.println();
                    System.out.println(gradeBook.printStatistics());
                    break;
                case 5://Quit
                    System.out.println();
                    System.out.println("Thank you for using the IUG Grade Book.");
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid Choice: please try again.");
                    break;
            }
        } while (choice != 5);
    }


    public static void showMenu() {
        System.out.println("\n----------------------------------------------------");
        System.out.println("\nIUG Grade Book");
        System.out.println("\n----------------------------------------------------");
        System.out.println("\n1. Add a student record");
        System.out.println("\n2. Add student records from file");
        System.out.println("\n3. Display a student record");
        System.out.println("\n4. Display statistical results of class");
        System.out.println("\n5. Quit\n");
    }
}
