import Task.Task;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");

    Task task = new Task();

    System.out.println(task.SetDate(20200400));
    System.out.println(task.SetStartTime(0.1f));
    System.out.println(task.SetDuration(24.999999f));

    //task.Print();
  }
}