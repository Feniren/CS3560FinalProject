import Task.Task;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");

    Task task = new Task();

    System.out.println(task.SetDate(20200401));
    System.out.println(task.SetDuration(10.2999999999f));

    //task.Print();
  }
}