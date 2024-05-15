import java.util.ArrayList;

import Task.Task;

public class Main {
  public static void main(String[] args) {
    // System.out.println("Hello world!");

    // Task task = new Task();

    // System.out.println(task.SetDate(20200400));
    // System.out.println(task.SetStartTime(0.1f));
    // System.out.println(task.SetDuration(24.999999f));

    ArrayList<Task> tasks = new ArrayList<Task>();
    Schedule testSchedule = new Schedule("day", 20240101, tasks);

    testSchedule.createTask("recTask1", "Class", 1.25f, 3.5f, 20240501, 20240528, 7); // recurrent task
    //testSchedule.createTask("recTask2", "Class", 1.25f, 3.5f, 20240502, 20240526, 7); // recurrent task
    testSchedule.createTask("TransTask1", "Visit", 1.25f, 1.5f, 20240501); // transient task
    testSchedule.createTask("TransTask2", "Visit", 1.25f, 1.5f, 20240508); // transient task
    //testSchedule.createTask("TransTask2", "Visit", 0.25f, 1.5f, 20240501); // transient task
    testSchedule.createTask("mimis2", "Cancellation", 1.25f, 3.5f, 20240501); //antitask
    testSchedule.createTask("TransTask1", "Visit", 1.25f, 1.5f, 20240501); // transient task


    // testSchedule.editTaskMenu();

    testSchedule.printList();
  }
}