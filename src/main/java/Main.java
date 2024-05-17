import java.util.ArrayList;
import java.util.Scanner;
import Task.Task;

public class Main {
    public static void main(String[] args) {
        int userIntInput = 0;
        String userStringInput = "";
        String jsonPath = "";
        Schedule testSchedule = new Schedule();
        Scanner input = new Scanner(System.in);

        System.out.println("Would you like to create a new schedule or insert a json file of your own schedule? \nEnter an int value corresponding to your choice");
        System.out.println("1) Create new schedule");
        System.out.println("2) Insert json file");
        System.out.println("3) Exit program");
        System.out.println();

        try {
            userIntInput = input.nextInt();
            input.nextLine(); // Clear the buffer

            switch (userIntInput) {
                case 1:
                    ArrayList<Task> tasks = new ArrayList<>();
                    testSchedule = new Schedule("day", 20240101, tasks);
                    break;
                case 2:
                    System.out.println("Type out the path string for the json file you wish to use.");
                    jsonPath = input.nextLine();
                    testSchedule = PSS.readFromFile(jsonPath);
                    break;
                case 3:
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Please enter a number corresponding to the available options.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please restart the program.");
            input.nextLine(); // Clear the buffer
        }

        System.out.println("Type out the json path you would like to save to:");
        jsonPath = input.nextLine();

        while (true) {
            System.out.println("Please choose an option listed below");
            System.out.println("1) Add task to schedule");
            System.out.println("2) Edit task in schedule");
            System.out.println("3) Remove task in schedule");
            System.out.println("4) View Schedule");
            System.out.println("5) View Task");
            System.out.println("6) Exit program");

            try {
                userIntInput = input.nextInt();
                input.nextLine(); // Clear the buffer

                switch (userIntInput) {
                    case 1:
                        addTaskToSchedule(testSchedule, input);
                        PSS.writeToFile(jsonPath, testSchedule);
                        break;
                    case 2:
                        testSchedule.editTaskMenu();
                        PSS.writeToFile(jsonPath, testSchedule);
                        break;
                    case 3:
                        System.out.println("Enter a task name to delete:");
                        userStringInput = input.nextLine();
                        testSchedule.DeleteTask(userStringInput);
                        PSS.writeToFile(jsonPath, testSchedule);
                        break;
                    case 4:
                        viewSchedule(testSchedule, input);
                        break;
                    case 5:
                        System.out.println("Enter a task name to view:");
                        userStringInput = input.nextLine();
                        testSchedule.ViewTask(userStringInput);
                        break;
                    case 6:
                        input.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                input.nextLine(); // Clear the buffer
            }
        }
    }

    private static void addTaskToSchedule(Schedule schedule, Scanner input) {
        String name, classType;
        float startTime, duration;
        int date, endDate, frequency;

        System.out.println("What task type would you like to add?");
        System.out.println("1) Transient Task");
        System.out.println("2) Recurrent Task");
        System.out.println("3) Anti-Task");

        try {
            int taskType = input.nextInt();
            input.nextLine(); // Clear the buffer

            switch (taskType) {
                case 1:
                case 3:
                    System.out.println("Enter a name for your task:");
                    name = input.nextLine();
                    System.out.println("Enter a class type:");
                    classType = input.nextLine();
                    System.out.println("Enter a start time for your task:");
                    startTime = input.nextFloat();
                    System.out.println("Enter a duration for your task:");
                    duration = input.nextFloat();
                    System.out.println("Enter a date for your task, formatted as YYYYMMDD:");
                    date = input.nextInt();

                    schedule.createTask(name, classType, startTime, duration, date);
                    break;
                case 2:
                    System.out.println("Enter a name for your task:");
                    name = input.nextLine();
                    System.out.println("Enter a class type:");
                    classType = input.nextLine();
                    System.out.println("Enter a start time for your task:");
                    startTime = input.nextFloat();
                    System.out.println("Enter a duration for your task:");
                    duration = input.nextFloat();
                    System.out.println("Enter a start date for your task, formatted as YYYYMMDD:");
                    date = input.nextInt();
                    System.out.println("Enter an end date for your task, formatted as YYYYMMDD:");
                    endDate = input.nextInt();
                    System.out.println("Enter a frequency for your task, 1 being daily and 7 being weekly:");
                    frequency = input.nextInt();

                    schedule.createTask(name, classType, startTime, duration, date, endDate, frequency);
                    break;
                default:
                    System.out.println("Invalid input. Returning to main menu.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Returning to main menu.");
            input.nextLine(); // Clear the buffer
        }
    }

    private static void viewSchedule(Schedule schedule, Scanner input) {
        try {
            System.out.println("What duration of time would you like to view?");
            System.out.println("1) Day");
            System.out.println("2) Week");
            System.out.println("3) Month");
            int frame = input.nextInt();
            System.out.println("What date would you like to start from? (YYYYMMDD)");
            int givenDate = input.nextInt();

            switch (frame) {
                case 1:
                    String viewer = PSS.viewSchedule(schedule, 1, givenDate);
                    System.out.println(viewer);
                    break;
                case 2:
                    String weekViewer = PSS.viewSchedule(schedule, 7, givenDate);
                    System.out.println(weekViewer);
                    break;
                case 3:
                    String monthlyViewer = PSS.viewSchedule(schedule, 30, givenDate);
                    System.out.println(monthlyViewer);
                    break;
                default:
                    System.out.println("Invalid duration given.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Returning to main menu.");
            input.nextLine(); // Clear the buffer
        }
    }
}
