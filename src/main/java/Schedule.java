import java.util.ArrayList;
import java.util.List;

import Task.Task;

public class Schedule {
    String timeFrame; // may change later to an int value, month length depends on start time which is annoying
    int startDate; // beginning point of schedule
    List<Task> taskList; // list of all tasks may be sorted who knows,

    public Schedule(){
        timeFrame = "day"; // day, week, month
        startDate = 20240101;
        taskList = new ArrayList<Task>();
    }

    // searches the list for a task of a given name
    public Task findTask(String Name) {
        for (Task task: taskList) {
            if(task.getName().equals(Name)) {
                return task;
            }
        }
        return null;
    }

    // checks for task conflicts, returns false if no conflicts detected
    // current idea is to have it take a task name and have it check the list for all other task times
    // may encounter issues 
    public boolean CheckTaskOverlap(String Name){ 
        Task checkTask = findTask(Name);
        for (Task task: taskList) { //iterate through list
            //check time conflicts WIP
            
        }
        return false;
    }

    // prints out task info
    public void ViewTask(String Name) {
        Task task = findTask(Name);
        // needs getters for Task 
        // will probably need to look at Type to be able to display all info
        // System.out.println(task.getName());
        // System.out.println(task.getType());
        // System.out.println(task.getStartTime());
        // System.out.println(task.getDuration());
        // System.out.println(task.getDate());
    }

    // basic function implemented deletes a function, will need to add checks for potential conflicts in the future
    // a modified checkOverlap may be used for Anti-Tasks to check if more than 1 tasks are overlapping it
    public void DeleteTask(String Name) {
        Task task = findTask(Name);
        if(task == null) {
            System.out.println("Failed to delete Task. " + Name + " does not exist.");
        } else {
            boolean isRemoved = taskList.remove(task);
            if (isRemoved == true) {
                System.out.println(Name + " was successfully removed.");
            } else {
                System.out.println(Name + " was not removed.");
            }
        }
    }

    // implementation, create copy of task being altered, copy is altered and checks for overlap and validity before being added to list
    // if added to list remove original task
    public void editTask(String Name) {
        Task ogTask = findTask(Name);
        //preemptive delete task for later checks
        DeleteTask(Name);
        Task tempTask = ogTask;

        System.out.println("This is the current information of " + Name);
        ViewTask(Name);
        // implementation should allow for multiple values to change, perhaps values that the user doesnt want to change should take a flag val
        
        
    }

    public void createTask(String Name, String classType, int startTime, int duration) {
        // depending on classType more info will be needed
        if(classType == "Transient") {

        }
        if(classType == "Transient") {
            
        }
        if(classType == "Anti") {

        }
        else {
            System.out.println("wat");
        }
    }
    
}
