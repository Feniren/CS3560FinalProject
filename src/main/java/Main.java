import Task.Task;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    ArrayList<Task> tasks = new ArrayList<Task>();
    Schedule testSchedule = new Schedule("day", 20240101, tasks);
    // Task task = new Task();

    // System.out.println(task.SetDate(20200401));
    // System.out.println(task.SetDuration(10.999999f));

    // test for transient overlap detected
    testSchedule.createTask("asdf", "Meal", 0.0f, 0.15f, 20240101, 20240101, 20240202, 1); //Recurring Task

    // testSchedule.ViewTask("asdf");

    testSchedule.createTask("mimis1", "Cancellation", 0.0f, 0.15f, 20240101); //antitask

    testSchedule.createTask("mimis2", "Cancellation", 0.0f, 0.15f, 20240501); //antitask

    testSchedule.createTask("Your mom", "Visit", 0.0f, 0.0f, 20240303); //Transient Test

    // testSchedule.DeleteTask("asdf");

    testSchedule.DeleteTask("mimis1");
      
    //testSchedule.viewTask("Your mom"); // found overlap and deleted

    //testSchedule.createTask("Your mom", "Visit", 0.0f, 0.0f, 20240101); //Transient Test

    testSchedule.printList(); //before your mom deletion

    //testSchedule.editTask("Your mom", "test", "Sleep", 1.15f, 0.30f, 20250101, 20250201, 7);
    
    //testSchedule.printList(); // after

    testSchedule.editTaskMenu();

    testSchedule.printList();
  }
}

/*
 * Edit Task Menu
1) Choose Task
2) Cancel Edit
2
Edit Option Cancelled
Printing Task List:
====================================
Task Name: asdf
Task Type: Meal
Task Start Time: 0.0
Task Duration: 0.15
Task Date: 20240101
Task End Date: 20240202
Task Start Date: 20240101
Task Frequency: 1
====================================
====================================
Task Name: Your mom
Task Type: Visit
Task Start Time: 0.0
Task Duration: 0.0
Task Date: 20240303
====================================
====================================
Task Name: test
Task Type: Sleep
Task Start Time: 0.0
Task Duration: 0.15
Task Date: 20240101
Task End Date: 20240101
Task Start Date: 20240202
Task Frequency: 7
====================================
 */