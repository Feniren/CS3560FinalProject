import java.util.ArrayList;
import java.util.List;

import Task.AntiTask;
import Task.RecurringTask;
import Task.Task;
import Task.TransientTask;

public class Schedule 
{
    String timeFrame; // may change later to an int value, month length depends on start time which is annoying
    int startDate; // beginning point of schedule
    List<Task> taskList; // list of all tasks may be sorted who knows,

    public Schedule(String timeFrame, int startDate, List<Task> taskList) 
    {
        this.timeFrame = timeFrame;
        this.startDate = startDate;
        this.taskList = taskList;
    }

    // Testing purposes
    public Schedule(){
        timeFrame = "day"; // day, week, month
        startDate = 20240101;
        taskList = new ArrayList<Task>();
    }


    // completed
    /**
     * searches the list for a task of a given name
     * @param Name
     * @return task, null, Result is based if a task is found or not
     */
    public Task findTask(String Name) {
        for (Task task: taskList) {
            if(task.getName().equals(Name)) {
                return task;
            }
        }
        return null;
    }

    // current idea is to have it take a task name and have it check the list for all other task times
    // may encounter issues


    /*
     * needs to avoid overlap betwen recurring and anti-task
     * Check for date, duration, and time
     */

     /*
      * Create function that checks recurrence frequency
      */

    /**
     * Checks for task conflicts, returns false if no conflicts detected
     * @param Name
     * @return
     */ 
    public boolean CheckTaskOverlap(String Name){ 
        boolean flag = false; // if overlapping flag change to true
        switch(categorizeTask(findTask(Name).getType())) {
            case "transient":
                TransientTask checkTTask = (TransientTask) findTask(Name);
                for (int i = 0; i < taskList.size(); i++) { //iterate through list
                    if(taskList.get(i).getName() == Name) { // same task no overlap
                        continue;
                    }
                    else if(taskList.get(i).getDate() != checkTTask.getDate()) {
                        continue;
                    }
                    
                    if(categorizeTask(taskList.get(i).getType()) == "recurring") { // in case of recurring need to check if dates could overlap
                        RecurringTask tempTask = (RecurringTask) taskList.get(i);
                        int tempEndDate = tempTask.getEndDate();
                        int tempStartDate = tempTask.getStartDate();
                        float tempStartTime = tempTask.getStartTime();
                        float tempEndTime = tempTask.getStartTime() + tempTask.getDuration(); 
                        float mainEndTime = checkTTask.getStartTime() + checkTTask.getDuration() + 0.25f;
                        float mainTime = checkTTask.getStartTime() - 0.25f; 
                        
                        // task either ends before checked task or starts after checked task
                        if(tempEndDate < checkTTask.getDate() || startDate > checkTTask.getDate()) {
                            continue;
                        }
                        else if (tempEndDate == checkTTask.getDate() ) { // if task end date on same day
                            // check times if they overlap
                        } else if (tempEndDate > checkTTask.getDate()) { // if task end date after
                            // check if times overlap with startTime of ttask
                            

                            // checking start time
                            // start time exists on established task
                            if ((mainEndTime > tempStartTime && tempStartTime > mainTime) || 
                                tempStartTime < mainTime ||
                                tempEndTime > mainTime || 
                                tempStartTime == mainTime ||
                                tempStartTime == mainEndTime ||
                                tempStartTime < mainEndTime || 
                                tempEndTime > mainEndTime
                                ) {
                                flag = true;
                            } else {
                                flag = false;
                            }
                        }
                    }

                    //will complete later
                    taskList.get(i);
                }
                break;
            case "recurring":
                RecurringTask checkRTask = (RecurringTask) findTask(Name);
                break;
            case "anti":
                AntiTask checkATask = (AntiTask) findTask(Name);
                break;
        }            
        return false;
    }

    // completed
    /**
     * prints out task info
     * @param Name
     */
    public void ViewTask(String Name) {
        Task task = findTask(Name);

        System.out.println("====================================");
        System.out.println(task.getName());
        System.out.println(task.getType());
        System.out.println(task.getStartTime());
        System.out.println(task.getDuration());
        System.out.println(task.getDate());
        
        switch(categorizeTask(task.getType())) {
            case "transient":
                break;
            case "recurring":
                RecurringTask recTask = (RecurringTask) task;
                System.out.println(recTask.getEndDate());
                System.out.println(recTask.getStartDate());
                System.out.println(recTask.getFrequency());
            case "anti":
                break;
            
        }
    
    }

    // a modified checkOverlap may be used for Anti-Tasks to check if more than 1 tasks are overlapping it
    // if anti task, cannot delete it, if recurring task has anti task cannot delete, can easily delete transient
    /**
     * Basic function implemented deletes a function, will need to add checks for potential conflicts in the future
     * @param Name
     */
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

    // if added to list remove original task
    /**
     * Implementation, create copy of task being altered, copy is altered and checks for overlap and validity before being added to list
     * @param Name
     */
    public void editTask(String Name) {
        Task ogTask = findTask(Name);
        //preemptive delete task for later checks
        DeleteTask(Name);
        Task tempTask = ogTask;
        switch(tempTask.getType()) {
            case "transient":
                break;
            case "recurring":
                break;
            case "anti":
                break;
            
        }

        System.out.println("This is the current information of " + Name);
        ViewTask(Name);
        // implementation should allow for multiple values to change, perhaps values that the user doesnt want to change should take a flag val
        
    }

    // completed
    /**
     * 
     * @param classType
     * @return
     */
    public String categorizeTask(String classType) {
        String taskType = "";
        switch(classType){
            case "Visit": case "Shopping": case "Appointment":
                taskType = "transient";
                break;
            case "Class": case "Study": case "Sleep": case "Exercise": case "Work": case "Meal":
                taskType = "recurring";
                break;
            case "Cancellation":
                taskType = "anti";
                break;
            default:
                System.out.println("Error Finding classType name");
        }
        return taskType;
    }

    // partly finished, needs checks for overlap, cannot lower case the class types, must be captilized properly
    /**
     * Create new task object with the given task subclass to be chosen
     * @param Name
     * @param classType
     * @param startTime
     * @param duration
     */
    public Task createTask(String name, String classType, float startTime, float duration, int date, Object... additionalArgs) {
        Task newTask = null;
        
        // change class type to possibly another term to change, maybe make function that reads all allowed names 
        switch(categorizeTask(classType)) {
            case "transient":
                // Create TransientTask
                if (additionalArgs.length >= 0) {
                    newTask = new TransientTask(name, classType, startTime, duration, date);
                    taskList.add(newTask);
                } else {
                    System.out.println("Error when creating Transient-Task");
                }
                break;
            case "recurring":
                // Create RecurringTask
                if (additionalArgs.length >= 3) {
                    int endDate = (int) additionalArgs[0];
                    int startDate = (int) additionalArgs[1];
                    int frequency = (int) additionalArgs[2];
                    newTask = new RecurringTask(name, classType, startTime, duration, date, endDate, startDate, frequency);
                    System.out.println("Crfeated");
                    taskList.add(newTask);

                    // newTask = new AntiTask(name, classType, startTime, duration, date);
                    // taskList.add(newTask);
                } else {
                    System.out.println("Error when creating Recurring-Task");
                }
                break;
            case "anti":
                // Create AntiTask
                if (additionalArgs.length >= 0) {
                    newTask = new AntiTask(name, classType, startTime, duration, date);
                    taskList.add(newTask);
                } else {
                    System.out.println("Error when creating Anti-Task");
                }
                break;
            default:
                System.out.println("Invalid task type");
        }

        return newTask;
    }


}
