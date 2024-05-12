import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

import Task.Task;
import Task.RecurringTask;

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
}
