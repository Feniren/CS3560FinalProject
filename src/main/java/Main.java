import java.util.ArrayList;
import java.util.Scanner;
import Task.Task;

public class Main {
  public static void main(String[] args) {
    // System.out.println("Hello world!");

    // Task task = new Task();

    // System.out.println(task.SetDate(20200400));
    // System.out.println(task.SetStartTime(0.1f));
    // System.out.println(task.SetDuration(24.999999f));

    int userInput = 0;
    String userStringInput = "";
    String jsonPath = "";
    Schedule testSchedule;
    Scanner input = new Scanner(System.in);

     System.out.println("Would you like to create a new schedule or insert a json file of your own schedule? \nEnter an int value corresponding to your choice");
     System.out.println("1) Create new schedule");
     System.out.println("2) Insert json file");
     System.out.println("3) Exit program");
     System.out.println();
     int userIntInput = input.nextInt();
     while(userIntInput != 3) {
          switch(userIntInput) {
               case 1:
                    ArrayList<Task> tasks = new ArrayList<Task>();
                    testSchedule = new Schedule("day", 20240101, tasks);
                    break;
               case 2:
                    System.out.println("Type out the path string for the json file you wish to use.");
                    jsonPath = input.nextLine();
                    testSchedule = PSS.readFromFile(jsonPath);
                    // go to pss
                    break;
               case 3:
                    input.close();
                    System.exit(0);
               default:
                    System.out.println("Please enter a number corresponding to the available options.");
          }
     }
     userIntInput = 0;
     userStringInput = "";
     
     while(userIntInput != 7) {
          String name = "";
          System.out.println("Please choose an option listed below");
          System.out.println("1) Add task to schedule");
          System.out.println("2) Edit task in schedule");
          System.out.println("3) Remove task in schedule");
          System.out.println("4) View Schedule");
          System.out.println("5) View Task");
          System.out.println("6) Switch Schedule");
          System.out.println("7) Exit program");
          userIntInput = input.nextInt();
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
                              System.out.println("Enter a duration for your task");
                              duration = input.nextFloat();
                              System.out.println("Enter a date for your task, formatted as YYYYMMDD");
                              date = input.nextInt();
                              

                              testSchedule.createTask(name, classType, startTime, duration, date);
                              break;
                         case 3:
                              System.out.println("Enter a name for your task");
                              name = input.nextLine();
                              System.out.println("Enter a class type");
                              classType = input.nextLine();
                              System.out.println("Enter a start time for your task");
                              startTime = input.nextFloat();
                              System.out.println("Enter a duration for your task");
                              duration = input.nextFloat();
                              System.out.println("Enter a start date for your task, formatted as YYYYMMDD");
                              date = input.nextInt();
                              System.out.println("Enter an end date for your task, formatted as YYYYMMDD");
                              endDate = input.nextInt();
                              System.out.println("Enter a frequency for your task, 1 being daily and 7 being weekly");
                              frequency = input.nextInt();

                              testSchedule.createTask(name, classType, startTime, duration, date, endDate, frequency);
                              break;
                         default:
                              System.out.println("Invalid input returning to menu."); 
                      
                    }

               case 2:
                    testSchedule.editTaskMenu();

               case 3:
                    System.out.println("Enter a task name to delete");
                    name = input.nextLine();
                    testSchedule.DeleteTask(name);
               case 4:
                    
                    System.out.println
               case 5:

               default:
                    System.out.println("Please choose an option listed below");
                    System.out.println("1) Add task to schedule");
                    System.out.println("2) Edit task in schedule");
                    System.out.println("3) Remove task in schedule");
                    System.out.println("4) Switch schedule");
                    System.out.println("5) Exit program");
          }
          userIntInput = 0;
          userStringInput = "";
     }
     


    //testSchedule.createTask("recTask1", "Class", 1.25f, 3.5f, 20240501, 20240528, 7); // recurrent task
    testSchedule.createTask("recTask2", "Class", 1.25f, 3.5f, 20240501, 20240526, 7); // recurrent task
    // testSchedule.createTask("TransTask1", "Visit", 1.25f, 1.5f, 20240501); // transient task overlap with recTask2
    // testSchedule.createTask("TransTask2", "Visit", 1.25f, 1.5f, 20240508); // transient task overlap with recTask2
    testSchedule.createTask("TransTask3", "Visit", 1.25f, 1.5f, 20240509); // transient task prints no overlap
    // testSchedule.createTask("TransTask2", "Visit", 0.25f, 1.5f, 20240501); // transient task, overlap
    testSchedule.createTask("RecTask3", "Class", 1.25f, 3.5f, 20240801, 20240726,7); // Change this to 7 and you get an infinite Invalid Date Print
    // testSchedule.createTask("RecTask4", "Class", 1.25f, 3.5f, 20240801, 20240826,7); // Change this to 7 and you get an infinite Invalid Date Print
    testSchedule.createTask("mimis2", "Cancellation", 1.25f, 3.5f, 20240501); //antitask prints with overlap to recurrence
    // testSchedule.createTask("TransTask4", "Visit", 1.25f, 1.5f, 20240501); // transient task, prints on overlap to recur during to cancellation task
    // testSchedule.createTask("RecTask5", "Class", 1.25f, 3.55f, 20240709, 20240825, 7); // inputted as a editTask setup

    testSchedule.printList();
    input.close();

    // testSchedule.editTaskMenu();

    // testSchedule.printList();

    // Line 166 changed print line to stop printing same task name, no overlap. Also added to ignore case.

    // rec to rec changes are weird, sometimes changes are made but it keeps defaulting to frequency 1 (FIXED)

    /*
     * ====================================
Task Name: RecTask3
Task Type: Class
Task Start Time: 1.25
Task Duration: 3.5
Task End Date: 20240605
Task Start Date: 20240522
Task Frequency: 1
====================================

Changing Task Features...
3.5
checking task Task.RecurringTask@6f539caf
Two Recurrence, same date (This section worth investigating, line 229)
[20240101, 20240103, 20240104, 20240105, 20240106, 20240107, 20240108, 20240109, 20240110, 20240111, 20240112, 20240113, 20240114, 20240115, 20240116, 20240117, 20240118, 20240119, 20240120, 20240121, 20240122, 20240123, 20240124]
[20240101, 20240103, 20240104, 20240105, 20240106, 20240107, 20240108, 20240109, 20240110, 20240111, 20240112, 20240113, 20240114, 20240115, 20240116, 20240117, 20240118, 20240119, 20240120, 20240121, 20240122, 20240123, 20240124]
Edit Task Menu

====================================
Task Name: RecTask3
Task Type: Class
Task Start Time: 1.25
Task Duration: 3.5
Task End Date: 20240122
Task Start Date: 20240101
Task Frequency: 1
====================================
     */

    /*
     * having problem when editing task to be same name to (FIXED)
     * Odd problem, overlap is caught for Recurrent Task when it overlaps after being edited but is still created...Frequency problem?, it fills any parts that had conflicts with default values 
     * Change Transient to Recurrent (WORKS) 
     * Recurrent to Trans? (works)
     * Anti to Trans? (works)
     * Trans to Anti? (works)
     * Anti to Recurrent? (works)
     * 
     * Can cancel tasks that overlap like its supposed to, but it will remove the original task (found a possible fix)
     */

    /*
     * End Result: 
====================================
Task Name: recTask2
Task Type: Class
Task Start Time: 1.25
Task Duration: 3.5
Task End Date: 20240526
Task Start Date: 20240501
Task Frequency: 7
Task Date: 20240101
====================================
viewTaskTask.TransientTask@5f184fc6
====================================
Task Name: TransTask3
Task Type: Visit
Task Start Time: 1.25
Task Duration: 1.5
Task Date: 20240509
====================================
viewTaskTask.AntiTask@5b480cf9
====================================
Task Name: mimis2
Task Type: Cancellation
Task Start Time: 1.25
Task Duration: 3.5
Task Date: 20240501
====================================
viewTaskTask.TransientTask@6f496d9f
====================================
Task Name: TransTask4
Task Type: Visit
Task Start Time: 1.25
Task Duration: 1.5
Task Date: 20240501
====================================
     */

  }
}