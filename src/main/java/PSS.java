import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

import Task.Task;
import Task.RecurringTask;
import Task.AntiTask;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class PSS {
	/**
	 * takes a path string to a json file, creates a taskSchedule, takes the content from
	 * the json file and iterates through the tasks adding them to the
	 * Schedule object based on their type
	 * @param pathString
	 * @return taskSchedule
	 */
    public static Schedule readFromFile(String pathString){
        Schedule taskSchedule = new Schedule();
        try{
			String jstring = Files.readString(Path.of(pathString));
			JSONArray jarray = new JSONArray(jstring);
			for (int i = 0; i < jarray.length(); i++){
				JSONObject j = jarray.getJSONObject(i);
				switch (Schedule.categorizeTask(j.getString("Type"))){
					case "recurring":
						taskSchedule.createTask(j.getString("Name"),
												j.getString("Type"),
												j.getNumber("StartTime").floatValue(),
												j.getNumber("Duration").floatValue(),
												j.getNumber("StartDate").intValue(),
												j.getNumber("StartDate").intValue(),
												j.getNumber("EndDate").intValue(),
												j.getNumber("Frequency").intValue());
						break;
					case "transient":
					case "anti":
						taskSchedule.createTask(j.getString("Name"),
												j.getString("Type"),
												j.getNumber("StartTime").floatValue(),
												j.getNumber("Duration").floatValue(),
												j.getNumber("Date").intValue());
						break;
					default:
						System.out.printf("Error, bad task type %s!\n", Schedule.categorizeTask(j.getString("Type")));
						return null;
				}
			}
        }
        catch(Exception e){
			System.out.println(e);
			System.out.println("Error, malformed task in file! Aborting.");
			return null;
        }
		return taskSchedule;
    }

	/**
	 * takes in the path to the json file that is to be created, and the Schedule object that 
	 * is going to written into json format. It then writes it to a file using a method that takes
	 * the Schedule's tasks and changes them into json format
	 * @param pathString
	 * @param taskSchedule
	 */
    public static void writeToFile(String pathString, Schedule taskSchedule){
        try{
			Files.write(Path.of(pathString), generateTasksJSON(taskSchedule).getBytes());
        }
        catch(Exception e){
			System.out.println(e.toString());
        }
    }

	/**
	 * Takes in a Schedule and goes through each task and writes them in json file
	 * format. Returns a string format for writeToFile to use
	 * @param taskSchedule
	 * @return taskJson.toString()
	 */
	public static String generateTasksJSON(Schedule taskSchedule){
		JSONArray tasksJSON = new JSONArray();

		for (Task t : taskSchedule.getTaskList()){
			JSONObject tJSON = new JSONObject();
			tJSON.put("Name", t.getName());
			tJSON.put("Type", t.GetType());
			tJSON.put("StartTime", t.GetStartTime());
			tJSON.put("Duration", t.GetDuration());
			tJSON.put("Date", t.GetDate());
			if (t instanceof RecurringTask){
				tJSON.put("StartDate", ((RecurringTask)t).GetStartDate());
				tJSON.put("EndDate", ((RecurringTask)t).GetEndDate());
				tJSON.put("Frequency", ((RecurringTask)t).GetFrequency());
			}
			tasksJSON.put(tJSON);
		}

		return tasksJSON.toString();
	}

	/**
	 * Takes in a Schedule object, the number of days the user wants to see
	 * (daily, weekly, monthly), and the start date of the part schedule wanted
	 * to view. It then ensures that any recurring tasks do not have any anti-tasks,
	 * if it does it will not print that instance of the recurring task.
	 * @param taskSchedule
	 * @param days
	 * @param startDate
	 * @return s: A properly formatted schedule for the user to view
	 */
	public static String viewSchedule(Schedule taskSchedule, int days, int startDate){
		LocalDate sd = datetoLocalDate(startDate);
		String s = String.format("Schedule from %s to %s (%d days):\n", sd.toString(), sd.plusDays(days-1).toString(), days);
		sortSchedule(taskSchedule);
		ArrayList<Task> antiTasks = new ArrayList<Task>();
		for(Task t : taskSchedule.getTaskList()){
			if(Schedule.categorizeTask(t.GetType()).equals("anti")){
				antiTasks.add(t);
			}
		}
		LocalDate date = datetoLocalDate(startDate);
		for (int i = 0; i < days; i++){
			boolean hasTasks = false;
			for(Task t : taskSchedule.getTaskList()){
				if (!sameDay(date, t)) continue;
				if (t instanceof RecurringTask){
					RecurringTask r = (RecurringTask)t;
					boolean cancelled = false;
					for(Task a: antiTasks){
						if (sameDay(date, a) && a.GetStartTime() == t.GetStartTime() && a.GetDuration() == t.GetDuration()){
							cancelled = true;
							break;
						}
					}
					if (!cancelled){
						s += ((hasTasks) ? "" : (date.toString() + ":\n")) + r.toString() + "\n";
						hasTasks = true;
					}
				} else if (t instanceof AntiTask){
					assert true; // we don't output those
				} else {
					s += ((hasTasks) ? "" : (date.toString() + ":\n")) + t.toString() + "\n";
					hasTasks = true;
				}
			}
			date = date.plusDays(1);
		}

		return s; 
	}

	/**
	 * Takes in a date and formats it cleanly for the user
	 * Used in Schedule for viewTask
	 * @param date
	 * @return String formatted to match up with viewSchedule
	 */
	public static String formatDate(int date){
		return String.format("%d-%d-%d",  ((date-date%10000)/10000), (date - (date-date%10000))/100, (date%100));
	}

	/**
	 * Checks if the recurring task has the same date as
	 * given
	 * @param date
	 * @param r
	 * @return
	 */
	public static boolean sameDay(LocalDate date, RecurringTask r){
		LocalDate sd = datetoLocalDate(r.GetStartDate());
		LocalDate ed = datetoLocalDate(r.GetEndDate());
		
		if (date.compareTo(sd) < 0 || date.compareTo(ed) >= 0) return false;
		
		int f = r.GetFrequency();
		return date.toEpochDay()%f == sd.toEpochDay()%f;
	}
	/**
	 * Checks if a Task has the same date as the date given
	 * @param date
	 * @param t
	 * @return boolean if day is same
	 */
	public static boolean sameDay(LocalDate date, Task t){
		if (t instanceof RecurringTask){
			return sameDay(date, (RecurringTask) t);
		}
		return date.compareTo(datetoLocalDate(t.GetDate())) == 0;
	}
	/**
	 * Creates a local date based on the parameters
	 * year, month, day
	 * @param d
	 * @return LocalDate of d
	 */
	public static LocalDate datetoLocalDate(int d){
		return LocalDate.of((d-d%10000)/10000, (d - (d-d%10000))/100, d%100);
	}
	/**
	 * Takes in a Schedule object and organizes tasks based on start time
	 * @param taskSchedule
	 */
	public static void sortSchedule(Schedule taskSchedule){
		List<Task> sortedTasks = taskSchedule.getTaskList();
		Collections.sort(sortedTasks, new TaskComparator());
		taskSchedule.setTaskList(sortedTasks);
	}
}