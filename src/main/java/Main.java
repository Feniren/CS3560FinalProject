import Task.Task;
import Task.RecurringTask;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");

    RecurringTask task = new RecurringTask();

    System.out.println(task.SetDate(20200400));
    System.out.println(task.SetStartTime(0.1f));
    System.out.println(task.SetDuration(24.999999f));

    if (task instanceof RecurringTask){
      System.out.println("Good");
    }

    //task.Print();
  }
}