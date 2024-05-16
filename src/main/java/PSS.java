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

public class PSS {
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
												j.getNumber("Date").intValue(),
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
						System.out.println("Error, malformed task in file!");
						return null;
				}
			}
        }
        catch(Exception e){
			System.out.println("Error, malformed task in file! Aborting.");
			return null;
        }
		return taskSchedule;
    }
    public static void writeToFile(String pathString, Schedule taskSchedule){
        try{
			Files.write(Path.of(pathString), generateTasksJSON(taskSchedule).getBytes());
        }
        catch(Exception e){
			System.out.println(e.toString());
        }
    }

	public static String generateTasksJSON(Schedule taskSchedule){
		JSONArray tasksJSON = new JSONArray();

		for (Task t : taskSchedule.getTaskList()){
			JSONObject tJSON = new JSONObject();
			tJSON.put("Name", t.getName());
			tJSON.put("Type", t.getType());
			tJSON.put("StartTime", t.getStartTime());
			tJSON.put("Duration", t.getDuration());
			tJSON.put("Date", t.getDate());
			if (t instanceof RecurringTask){
				tJSON.put("StartDate", ((RecurringTask)t).getStartDate());
				tJSON.put("EndDate", ((RecurringTask)t).getEndDate());
				tJSON.put("Frequency", ((RecurringTask)t).getFrequency());
			}
			tasksJSON.put(tJSON);
		}

		return tasksJSON.toString();
	}

	public static String viewSchedule(Schedule taskSchedule, int days, int startDate){
		String s = String.format("Schedule from %s to %s (%d days):\n", formatDate(startDate), formatDate(startDate+days-1), days);
		sortSchedule(taskSchedule);
		ArrayList<Task> antiTasks = new ArrayList<Task>();
		for(Task t : taskSchedule.getTaskList()){
			if(Schedule.categorizeTask(t.getType()).equals("anti")){
				antiTasks.add(t);
			}
		}
		for (int currentdate = startDate; currentdate < startDate + days; currentdate++){
			s += formatDate(currentdate) + ":";
			boolean hasTasks = false;
			for(Task t : taskSchedule.getTaskList()){
				if (!sameDay(currentdate, t)) continue;
				if (t instanceof RecurringTask){
					RecurringTask r = (RecurringTask)t;
					boolean cancelled = false;
					for(Task a: antiTasks){
						if (sameDay(a.getDate(), r) && a.getStartTime() == t.getStartTime()
													&& a.getDuration() == t.getDuration()){
							cancelled = true;
							break;
						}
					}
					if (!cancelled){
						s += ((hasTasks) ? "" : "\n") + r.toString() + "\n";
						hasTasks = true;
					}
				} else if (t instanceof AntiTask){
					assert true; // we don't output those
				} else {
					s += ((hasTasks) ? "" : "\n") + t.toString() + "\n";
					hasTasks = true;
				}
			}
			if (!hasTasks) s += " No tasks!\n";
		}

		return s; 
	}

	public static String formatDate(int date){
		return String.format("%d-%d-%d",  ((date-date%10000)/10000), (date - (date-date%10000))/100, (date%100));
	}

	public static boolean sameDay(int date, RecurringTask r){
		if (date < r.getStartDate() || date >= r.getEndDate()) return false;
		
		int f = r.getFrequency();
		return (date%f == r.getStartDate()%f);
	}
	
	public static boolean sameDay(int date, Task t){
		if (t instanceof RecurringTask){
			return sameDay(date, (RecurringTask) t);
		}
		return (date == t.getDate());
	}

	public static void sortSchedule(Schedule taskSchedule){
		List<Task> sortedTasks = taskSchedule.getTaskList();
		Collections.sort(sortedTasks, new TaskComparator());
		taskSchedule.setTaskList(sortedTasks);
	}
}
