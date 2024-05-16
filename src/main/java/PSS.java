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
		LocalDate sd = datetoLocalDate(startDate);
		String s = String.format("Schedule from %s to %s (%d days):\n", sd.toString(), sd.plusDays(days-1).toString(), days);
		sortSchedule(taskSchedule);
		ArrayList<Task> antiTasks = new ArrayList<Task>();
		for(Task t : taskSchedule.getTaskList()){
			if(Schedule.categorizeTask(t.getType()).equals("anti")){
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
						if (sameDay(date, a) && a.getStartTime() == t.getStartTime() && a.getDuration() == t.getDuration()){
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

	public static String formatDate(int date){
		return String.format("%d-%d-%d",  ((date-date%10000)/10000), (date - (date-date%10000))/100, (date%100));
	}

	public static boolean sameDay(LocalDate date, RecurringTask r){
		LocalDate sd = datetoLocalDate(r.getStartDate());
		LocalDate ed = datetoLocalDate(r.getEndDate());
		
		if (date.compareTo(sd) < 0 || date.compareTo(ed) >= 0) return false;
		
		int f = r.getFrequency();
		return date.toEpochDay()%f == sd.toEpochDay()%f;
	}
	
	public static boolean sameDay(LocalDate date, Task t){
		if (t instanceof RecurringTask){
			return sameDay(date, (RecurringTask) t);
		}
		return date.compareTo(datetoLocalDate(t.getDate())) == 0;
	}

	public static LocalDate datetoLocalDate(int d){
		return LocalDate.of((d-d%10000)/10000, (d - (d-d%10000))/100, d%100);
	}

	public static void sortSchedule(Schedule taskSchedule){
		List<Task> sortedTasks = taskSchedule.getTaskList();
		Collections.sort(sortedTasks, new TaskComparator());
		taskSchedule.setTaskList(sortedTasks);
	}
}
