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

    public void printList() {
        System.out.println("Printing Task List: ");
        for (Task task : taskList) {
            viewTask(task.getName());
        }
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

    public boolean checkTaskOverlap(String name) {
        Task taskToCheck = findTask(name); // can leave like as long as we dont call methods exclusive to a subclass according to stackoverflow
        if (taskToCheck == null) { // checks if task exists // prints prematurely should either remove or provide another check
            System.out.println("Error: Task Not Found");
            return false;
        }

        boolean flag = false; // false means no overlap, true means overlap
        switch(categorizeTask(taskToCheck.getType())) {
            case "transient":
                TransientTask checkTTask = (TransientTask) taskToCheck;
                for(Task task: taskList) {
                    if (task.getName().equals(name)) {
                        System.out.println("Same task name, no overlap");
                        continue; // Same Task, no overlap
                    }
                    // Trans vs Recurring
                    if (categorizeTask(task.getType()).equals("recurring")) {
                        RecurringTask recurringTask = (RecurringTask) task;
                        if (checkRecurringOverlap(checkTTask, recurringTask)) {
                            flag = true;
                            System.out.println("overlap detected, checkRecurringOverlap");
                            break;
                        }
                        // Trans vs Anti
                    } else if (categorizeTask(task.getType()).equals("anti")) {
                        AntiTask antiTask = (AntiTask) task;
                        if (checkAntiOverlap(antiTask, checkTTask)) {
                            flag = true;
                            break;
                        }
                    //Trans vs Trans
                    } else if (task.getDate() == checkTTask.getDate()) { 
                        System.out.println("Two transient, same date");
                        TransientTask transTask = (TransientTask) task;
                        if (checkTranstoTransOverlap(checkTTask, transTask)) {
                            flag = true;
                            break;
                        }
                    }
                }
                break;
            case "recurring":
                RecurringTask checkRTask = (RecurringTask) taskToCheck;
                for(Task task : taskList) {
                    if(task.getName().equals(name)) {
                        continue; // same task, no overlap
                    }
                    // Trans vs Recurring
                    if(categorizeTask(task.getType()).equals("transient")) {
                        TransientTask transientTask = (TransientTask) task;
                        if(checkRecurringOverlap(transientTask, checkRTask)) {
                            flag = true;
                            break;
                        }
                    // Anti vs Recurring
                    } else if(categorizeTask(task.getType()).equals("anti")) {
                        AntiTask antiTask = (AntiTask) task;
                        if (checkAntiRecurringOverlap(antiTask, checkRTask)) {
                            flag = true;
                            break;
                        }
                    } else if (categorizeTask(task.getType()).equals("recurring")) { 
                        System.out.println("Two Recurrence, same date");
                        RecurringTask recurringTask = (RecurringTask) task;
                        if (checkRectoRecOverlap(checkRTask, recurringTask)) {
                            flag = true;
                            break;
                        }
                    }
                }
                break;
            case "anti":
                AntiTask checkATask = (AntiTask) taskToCheck;
                
                for (Task task : taskList) {
                    if(task.getName().equals(name)) {
                        continue;
                    }
                    // Trans vs anti
                    if(categorizeTask(task.getType()).equals("transient")) {
                        TransientTask transientTask = (TransientTask) task;
                        if(checkAntiOverlap(checkATask, transientTask)) {
                            flag = true;
                            break;
                        }
                    // Anti vs Recurring
                    } else if(categorizeTask(task.getType()).equals("recurring")) {
                        RecurringTask recurringTask = (RecurringTask) task;
                        if(checkAntiRecurringOverlap(checkATask, recurringTask)) {
                            flag = false; //we want overlap with recurring
                            break;
                        } else {
                            flag = true;
                            continue;
                        }
                        
                    // anti vs anti
                    } else if(task.getDate() == checkATask.getDate()) {
                        System.out.println("Two anti, same date");
                        AntiTask antiTask = (AntiTask) task;
                        if (checkAntiToAntiOverlap(checkATask, antiTask)) {
                            flag = true;
                            break;
                        }
                        continue;
                    } else { // anti tasks on different date or no overlap on same date
                        continue;
                    }
                }                            
                System.out.println("completed check");
                break;
            default:
                System.out.println("Error: Invalid Task Type");
                break;
        }
        return flag;
    }

    private boolean checkAntiToAntiOverlap(AntiTask checkATask, AntiTask antiTask) {
        float antiEndTime = antiTask.getStartTime() + antiTask.getDuration() + 0.5f;
        float antiStartTime = antiTask.getStartTime() - 0.25f; 
        float checkingEndTask = checkATask.getStartTime() + checkATask.getDuration() + 0.5f;
        float checkingStartTask = checkATask.getStartTime() - 0.25f;
        if ((antiStartTime >= checkingStartTask && antiStartTime < checkingEndTask) ||
        (antiEndTime > checkingStartTask && antiEndTime <= checkingEndTask) ||
        (antiStartTime <= checkingStartTask && antiEndTime >= checkingEndTask)) {
            System.out.println("overlap seen, checkAntiToAntiOverlap function");
            return true; // overlap is seen
        }
        return false;
    }
    
    private boolean checkTranstoTransOverlap(TransientTask checkTTask, TransientTask transientTask) {
        float transEndTime = transientTask.getStartTime() + transientTask.getDuration() + 0.5f;
        float transStartTime = transientTask.getStartTime() - 0.25f; 
        float checkingEndTask = checkTTask.getStartTime() + checkTTask.getDuration() + 0.5f;
        float checkingStartTask = checkTTask.getStartTime() - 0.25f;
        if ((transStartTime >= checkingStartTask && transStartTime < checkingEndTask) ||
            (transEndTime > checkingStartTask && transEndTime <= checkingEndTask) ||
            (transStartTime <= checkingStartTask && transEndTime >= checkingEndTask)) {
                System.out.println("overlap seen, checkTranstoTranbsOverlap function");
                return true; // overlap is seen
            }
        return false;
    }

    private boolean checkRectoRecOverlap(RecurringTask checkRTask, RecurringTask recurringTask) {
        float recEndTime = recurringTask.getStartTime() + recurringTask.getDuration() + 0.5f;
        float recStartTime = recurringTask.getStartTime() - 0.25f; 
        float checkingEndTask = checkRTask.getStartTime() + checkRTask.getDuration() + 0.5f;
        float checkingStartTask = checkRTask.getStartTime() - 0.25f;
        if(checkRTask.getStartDate() >= recurringTask.getStartDate() && checkRTask.getStartDate() <= recurringTask.getEndDate() ||
            checkRTask.getEndDate() >= recurringTask.getStartDate() && checkRTask.getEndDate() <= recurringTask.getEndDate()) { //within date range
            // if daily to daily
            if(checkRTask.getFrequency() == recurringTask.getFrequency() && checkRTask.getFrequency() == 1) {
                if ((recStartTime >= checkingStartTask && recStartTime < checkingEndTask) ||
                (recEndTime > checkingStartTask && recEndTime <= checkingEndTask) ||
                (recStartTime <= checkingStartTask && recEndTime >= checkingEndTask)) {
                    System.out.println("overlap seen, checkTranstoTranbsOverlap function");
                return true; // overlap is seen
            }
            
            // if weekly to daily
            } else if(checkRTask.getFrequency() != recurringTask.getFrequency()) {
                if((recStartTime >= checkingStartTask && recStartTime < checkingEndTask) ||
                (recEndTime > checkingStartTask && recEndTime <= checkingEndTask) ||
                (recStartTime <= checkingStartTask && recEndTime >= checkingEndTask)) {
                    return true;
                }
            // if weekly to weekly
            } else if (checkRTask.getFrequency() == recurringTask.getFrequency() && checkRTask.getFrequency() == 7) {
                if((recStartTime >= checkingStartTask && recStartTime < checkingEndTask) ||
                (recEndTime > checkingStartTask && recEndTime <= checkingEndTask) ||
                (recStartTime <= checkingStartTask && recEndTime >= checkingEndTask)) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    // should hopefully function
    // trans vs recurring
    private boolean checkRecurringOverlap(TransientTask transientTask, RecurringTask recurringTask) {
        int recurringEndDate = recurringTask.getEndDate();
        int recurringStartDate = recurringTask.getStartDate();
        float recurringStartTime = recurringTask.getStartTime();
        float recurringEndTime = recurringTask.getStartTime() + recurringTask.getDuration(); 
        float transEndTime = transientTask.getStartTime() + transientTask.getDuration() + 0.5f;
        float transStartTime = transientTask.getStartTime() - 0.25f; 

        if (recurringTask.getFrequency() == 1) { // daily
            if(transientTask.getDate() <= recurringEndDate && transientTask.getDate() >= recurringStartDate) {
                if ((transStartTime >= recurringStartTime && transStartTime < recurringEndTime) ||
                (transEndTime > recurringStartTime && transEndTime <= recurringEndTime) ||
                (transStartTime <= recurringStartTime && transEndTime >= recurringEndTime)) {
                    return true; // overlap is seen
                }
            }
        } else if (recurringTask.getFrequency() == 7) { // weekly
            if ((transStartTime >= recurringStartTime && transStartTime < recurringEndTime) ||
            (transEndTime > recurringStartTime && transEndTime <= recurringEndTime) ||
            (transStartTime <= recurringStartTime && transEndTime >= recurringEndTime)) {
                return true; // overlap is seen
            }
        }
        return false;
    }

    // Transient vs anti
    private boolean checkAntiOverlap(AntiTask antiTask, TransientTask transientTask) {
        
        if(antiTask.getDate() == transientTask.getDate()) {//same date
            if (antiTask.getStartTime() >= transientTask.getStartTime() - 0.25f && 
                antiTask.getStartTime() <= transientTask.getStartTime() + transientTask.getDuration() + 0.5f) { //start time greater than or equal to start time but less than duration
                return true;
            } else if (antiTask.getStartTime() + antiTask.getDuration() <= transientTask.getStartTime() + transientTask.getDuration() + 0.5f &&
                    antiTask.getStartTime() + antiTask.getDuration() >= transientTask.getStartTime() - 0.25f){ //end time within timeframe of task
                return true;
            }
        }

        return false;
    }

    // anti vs recurring
    private boolean checkAntiRecurringOverlap(AntiTask antiTask, RecurringTask recurringTask) {
        int recurringEndDate = recurringTask.getEndDate();
        int recurringStartDate = recurringTask.getStartDate();
        float recurringStartTime = recurringTask.getStartTime();
        float recurringEndTime = recurringTask.getStartTime() + recurringTask.getDuration(); 
        float antiEndTime = antiTask.getStartTime() + antiTask.getDuration() + 0.5f;
        float antiStartTime = antiTask.getStartTime() - 0.25f; 

        if (recurringTask.getFrequency() == 1) { // daily
            if(antiTask.getDate() <= recurringEndDate && antiTask.getDate() >= recurringStartDate) { 
                if ((antiStartTime >= recurringStartTime && antiStartTime < recurringEndTime) ||
                (antiEndTime > recurringStartTime && antiEndTime <= recurringEndTime) ||
                (antiStartTime <= recurringStartTime && antiEndTime >= recurringEndTime)) {
                    System.out.println("Testing testing");
                    return true; // overlap is seen
                }
            }
        } else if (recurringTask.getFrequency() == 7) { // weekly
            if ((antiStartTime >= recurringStartTime && antiStartTime < recurringEndTime) ||
            (antiEndTime > recurringStartTime && antiEndTime <= recurringEndTime) ||
            (antiStartTime <= recurringStartTime && antiEndTime >= recurringEndTime)) {
                return true; // overlap is seen
            }
        }
        return false;
    }

    // completed
    /**
     * prints out task info
     * @param Name
     */
    public void viewTask(String Name) {
        Task task = findTask(Name);

        System.out.println("====================================");
        System.out.println("Task Name: " + task.getName());
        System.out.println("Task Type: " + task.getType());
        System.out.println("Task Start Time: " + task.getStartTime());
        System.out.println("Task Duration: " + task.getDuration());
        System.out.println("Task Date: " + task.getDate());
        
        switch(categorizeTask(task.getType())) {
            case "transient":
                break;
            case "recurring":
                RecurringTask recTask = (RecurringTask) task;
                System.out.println("Task End Date: " + recTask.getEndDate());
                System.out.println("Task Start Date: " + recTask.getStartDate());
                System.out.println("Task Frequency: " + recTask.getFrequency());
            case "anti":
                break;
            
        }
        System.out.println("====================================");
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
        } else if (categorizeTask(task.getType()).equals("anti")) {
            if (checkTaskOverlap(Name)) {
                System.out.println(Name + " was not removed. There exists a task dependent on this AntiTask");
            } else {
                boolean isRemoved = taskList.remove(task);
                if (isRemoved == true) {
                    System.out.println(Name + " was successfully removed.");
                } else {
                    System.out.println(Name + " was not removed.");
                }
            }
        } else if (categorizeTask(task.getType()).equals("recurring")) {
            if (checkTaskOverlap(Name)) {
                System.out.println(Name + " was not removed. There exists a task dependent on this Recurrent Task");
            } else {
                boolean isRemoved = taskList.remove(task);
                if (isRemoved == true) {
                    System.out.println(Name + " was successfully removed.");
                } else {
                    System.out.println(Name + " was not removed.");
                }
            }
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

    /*
     * Current plan, make new menu thats asks for what individual changes
     */
    public void editTask(String Name, String newName, String newClassType, float newStartTime, float newDuration, int newDate, Object... additionalArgs) {
        Task ogTask = findTask(Name);
        if (ogTask == null) {
            System.out.println("Task not found");
            return;
        }
        System.out.println("This is the current information of " + Name);
        viewTask(Name);

        Task tempTask = ogTask;

        
        switch(categorizeTask(newClassType)) {
            case "transient":
                TransientTask transTask = (TransientTask) ogTask;
                DeleteTask(Name); // preemptive deletion of task
                transTask.setDate(newDate);
                transTask.setName(newName);
                transTask.setStartTime(newStartTime);
                transTask.setDuration(newDuration);
                transTask.setType(newClassType);
                if(checkTaskOverlap(newName)) {
                    System.out.println("Overlap Seen");
                    DeleteTask(newName);
                    
                    return;
                }
                break;
            case "recurring": //this scares me...
                int endDate = (int) additionalArgs[0];
                int frequency = (int) additionalArgs[1];
                if(frequency != 1 || frequency != 7) {
                    System.out.println("Invalid frequency: " + frequency);
                    return;
                }
                RecurringTask recTask = (RecurringTask) ogTask;
                DeleteTask(Name); // preemptive deletion of task
                recTask.setDate(newDate);
                recTask.setStartDate(newDate);
                recTask.setName(newName);
                recTask.setStartTime(newStartTime);
                recTask.setDuration(newDuration);
                recTask.setType(newClassType);
                recTask.setEndDate(endDate);
                recTask.setFrequency(frequency);
                if(checkTaskOverlap(newName)) {
                    System.out.println("Overlap Seen");
                    DeleteTask(newName);
                    return;
                }
                break;
            case "anti":
                AntiTask antiTask = (AntiTask) ogTask;
                DeleteTask(Name); // preemptive deletion of task
                // createTask("cfunt", "Sleep", 1.15f, 0.30f, 20250101, 20250201, 7);
                antiTask.setDate(newDate);
                antiTask.setName(newName);
                antiTask.setStartTime(newStartTime);
                antiTask.setDuration(newDuration);
                antiTask.setType(newClassType);
                if(checkTaskOverlap(newName)) {
                    System.out.println("Overlap Seen");
                    DeleteTask(newName);
                    return;
                }
                break;
        }

        System.out.println("This is the current information of " + newName);
        viewTask(newName);
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
        boolean nameFlag = false;
        for(Task task: taskList) { //check if name already exists within list
            if(task.getName() == name) {
                nameFlag = true;
            }
        }
        if(nameFlag == false) {
            // change class type to possibly another term to change, maybe make function that reads all allowed names 
            switch(categorizeTask(classType)) {
                case "transient":
                    // Create TransientTask
                    if (additionalArgs.length >= 0) {
                    
                        newTask = new TransientTask(name, classType, startTime, duration, date);
                        taskList.add(newTask);
                        if(checkTaskOverlap(name)) {
                            Task task = findTask(name);
                            taskList.remove(task);
                            System.out.println("overlap detected, could not create task");
                        }
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
                        if(checkTaskOverlap(name)) {
                            Task task = findTask(name);
                            taskList.remove(task);
                            System.out.println("overlap detected, could not create task");
                        }
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
                        if(checkTaskOverlap(name)) {
                            Task task = findTask(name);
                            taskList.remove(task);
                            System.out.println("overlap detected, could not create task");
                        }
                    } else {
                        System.out.println("Error when creating Anti-Task");
                    }
                    break;
                default:
                    System.out.println("Invalid task type");
                }
        }

        return newTask;
    }


}
