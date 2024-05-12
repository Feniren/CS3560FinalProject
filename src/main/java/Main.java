import Task.Task;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    ArrayList<Task> tasks = new ArrayList<Task>();
    Schedule testSchedule = new Schedule("day", 20240101, tasks);
    // Task task = new Task();

    // System.out.println(task.SetDate(20200401));
    // System.out.println(task.SetDuration(10.999999f));

    testSchedule.createTask("asdf", "Meal", 0.0f, 0.0f, 20240101, 20240101, 20240202, 5); //Recurring Task

    testSchedule.ViewTask("asdf");

    testSchedule.createTask("Your mom", "Visit", 0.0f, 0.0f, 20240101, 20240102); //Transient Test
      
    testSchedule.ViewTask("Your mom");
  }
}