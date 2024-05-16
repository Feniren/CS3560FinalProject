import Task.Task;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		Schedule testSchedule = new Schedule("day", 20240101, new ArrayList<Task>());
		testSchedule.createTask("asdf", "Meal", 9.0f, 2.5f, 20240101, 20240101, 20240202, 5); //Recurring Task
		testSchedule.createTask("Your mom", "Visit", 15.5f, 1.0f, 20240101, 20240102); //Transient Test
		//testSchedule.ViewTask("asdf");
		//testSchedule.ViewTask("Your mom");

		System.out.println(PSS.viewSchedule(testSchedule, 7, 20240101));
		System.out.println(PSS.generateTasksJSON(testSchedule));
		PSS.writeToFile("./sched.json", testSchedule);
		System.out.println(PSS.generateTasksJSON(PSS.readFromFile("./sched.json")));
	}
}