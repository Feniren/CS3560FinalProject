import java.util.ArrayList;
import java.util.Scanner;
import Task.Task;

public class Main {
  public static void main(String[] args) {
    int userInput = 0;
    String userStringInput = "";
    String jsonPath = "";
    Boolean loop = false;
    Schedule testSchedule = new Schedule();
    Scanner input = new Scanner(System.in);

     System.out.println("Would you like to create a new schedule or insert a json file of your own schedule? \nEnter an int value corresponding to your choice");
     System.out.println("1) Create new schedule");
     System.out.println("2) Insert json file");
     System.out.println("3) Exit program");
     System.out.println();
     int userIntInput = input.nextInt();
     System.out.println("Press enter again.");

     input.nextLine();
     while(loop == false) {
          switch(userIntInput) {
               case 1:
                    ArrayList<Task> tasks = new ArrayList<Task>();
                    testSchedule = new Schedule("day", 20240101, tasks);
                    loop = true;
                    break;
               case 2:
                    System.out.println("Type out the path string for the json file you wish to use.");
                    jsonPath = input.nextLine();
                    testSchedule = PSS.readFromFile(jsonPath);
                    loop = true;
                    // go to pss
                    break;
               case 3:
                    loop = true;
                    userIntInput = 3;
                    break;
               default:
                    System.out.println("Please enter a number corresponding to the available options.");
                    userIntInput = input.nextInt();
                    System.out.println("Press enter again.");
                    input.nextLine();
                    break;
          }
     }

     if(userIntInput != 3) {
          loop = false;
          userStringInput = "";
          System.out.println("Type out the json path you would like to save to");
          jsonPath = input.nextLine();
     }
     
     while(loop == false) {
          String name = "";

          System.out.println("Please choose an option listed below");
          System.out.println("1) Add task to schedule");
          System.out.println("2) Edit task in schedule");
          System.out.println("3) Remove task in schedule");
          System.out.println("4) View Schedule");
          System.out.println("5) View Task");
          System.out.println("6) Exit program");
          userIntInput = input.nextInt();
          System.out.println("Press enter again.");
          input.nextLine();
          switch(userIntInput) {
               case 1:
                    System.out.println("What task type would you like to add?");
                    System.out.println("1) Transient Task");
                    System.out.println("2) Recurrent Task");
                    System.out.println("3) Anti-Task");
                    String classType = "";
                    float startTime = 0.0f;
                    float duration = 0.0f;
                    int date = 0;
                    int endDate = 0;
                    int frequency = 1;
                    userIntInput = input.nextInt();
                    switch(userIntInput) {
                         case 1: case 2:
                              System.out.println("Enter a name for your task");
                              name = input.nextLine();
                              System.out.println("Enter a class type");
                              classType = input.nextLine();
                              System.out.println("Enter a start time for your task");
                              startTime = input.nextFloat();
                              System.out.println("Press enter again.");
                              input.nextLine();
                              System.out.println("Enter a duration for your task");
                              duration = input.nextFloat();
                              System.out.println("Press enter again.");
                              input.nextLine();
                              System.out.println("Enter a date for your task, formatted as YYYYMMDD");
                              date = input.nextInt();
                              System.out.println("Press enter again.");
                              input.nextLine();
                              

                              testSchedule.createTask(name, classType, startTime, duration, date);
                              break;
                         case 3:
                              System.out.println("Enter a name for your task");
                              name = input.nextLine();
                              System.out.println("Enter a class type");
                              classType = input.nextLine();
                              System.out.println("Enter a start time for your task");
                              startTime = input.nextFloat();
                              System.out.println("Press enter again.");
                              input.nextLine();
                              System.out.println("Enter a duration for your task");
                              duration = input.nextFloat();
                              System.out.println("Press enter again.");
                              input.nextLine();
                              System.out.println("Enter a start date for your task, formatted as YYYYMMDD");
                              date = input.nextInt();
                              System.out.println("Press enter again.");
                              input.nextLine();
                              System.out.println("Enter an end date for your task, formatted as YYYYMMDD");
                              endDate = input.nextInt();
                              System.out.println("Press enter again.");
                              input.nextLine();
                              System.out.println("Enter a frequency for your task, 1 being daily and 7 being weekly");
                              frequency = input.nextInt();
                              System.out.println("Press enter again.");
                              input.nextLine();

                              testSchedule.createTask(name, classType, startTime, duration, date, endDate, frequency);
                              break;
                         default:
                              System.out.println("Invalid input returning to menu.");  
                              break;
                    }
                    PSS.writeToFile(jsonPath, testSchedule);
                    break;
               case 2:
                    testSchedule.editTaskMenu();
                    PSS.writeToFile(jsonPath, testSchedule);
                    break;
               case 3:
                    System.out.println("Enter a task name to delete");
                    name = input.nextLine();
                    testSchedule.DeleteTask(name);
                    PSS.writeToFile(jsonPath, testSchedule);
                    break;
               case 4:
                    System.out.println("What duration of time would you like to view?");
                    System.out.println("1) Day");
                    System.out.println("2) Week");
                    System.out.println("3) Month");
                    int frame = input.nextInt();
                    System.out.println("Press enter again.");
                    input.nextLine();
                    System.out.println("What date would you like to start from?");
                    int givenDate = input.nextInt();
                    System.out.println("Press enter again.");
                    // input.nextLine();
                    switch(frame) {
                         case 1:
                              System.out.println("REading frame: " + frame);
                              System.out.println("testSchedule: " + testSchedule + " " + 1 + " " + givenDate);
                              PSS.viewSchedule(testSchedule, 1, givenDate);
                              break;
                         case 2:
                              PSS.viewSchedule(testSchedule, 7, givenDate);
                              break;
                         case 3:
                              PSS.viewSchedule(testSchedule, 30, givenDate);
                              break;
                         default:
                              System.out.println("Invalid duration given.");
                              break;
                    }
                    System.out.println("testtestste");
                    break;
               case 5:
                    System.out.println("Enter a task name to view");
                    name = input.nextLine();
                    testSchedule.ViewTask(name);
                    break;
               case 6:
                    input.close();
                    System.exit(0); 
                    break;
               default:
                    System.out.println("Invalid input");
                    break;
          }
          userIntInput = 0;
          userStringInput = "";
     }
     
    input.close();

  }
}