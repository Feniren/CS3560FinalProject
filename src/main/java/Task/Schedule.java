package Task;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Schedule
{
    String timeFrame; // may change later to an int value, month length depends on start time which is annoying
    int startDate; // beginning point of schedule
    List<Task> taskList; // list of all tasks may be sorted who knows,

    public List<Task> GetTaskList(){
        return taskList;
    }

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

    /**
     * Prints the list of all current tasks
     */
    public void printList() {
        System.out.println("Printing Task List: ");
        for (Task task : taskList) {
            viewTask(task.GetName());
        }
    }

    /**
     * searches the list for a task of a given name
     * @param Name Task name to be searched
     * @return task, null, Result is based if a task is found or not
     */
    public Task findTask(String Name) {
        for (Task task: taskList) {
            if(task.GetName().equals(Name)) {
                return task;
            }
        }
        return null;
    }

    // current idea is to have it take a task name and have it check the list for all other task times
    // may encounter issues
    // Should try to simplify this in the future

    /**
     * Checks a new task's time and date to see if it overlaps with any current tasks
     * @param name Name of task to be checked
     * @return True or False if Task overlaps or does not respectively
     */
    public boolean checkTaskOverlap(String name) {
        Task taskToCheck = findTask(name);
        if (taskToCheck == null) { // checks if task exists, removal of this breaks function
            return false;
        }

        boolean flag = false; // false means no overlap, true means overlap
        switch(categorizeTask(taskToCheck.GetType())) {
            case "transient":
                TransientTask checkTTask = (TransientTask) taskToCheck;
                for(Task task: taskList) {
                    if (task.GetName().equals(name)) {
                        System.out.println("Same task name, no overlap");
                        continue; // Same Task, no overlap
                    }
                    // Trans vs Recurring
                    if (categorizeTask(task.GetType()).equals("recurring")) {
                        RecurringTask recurringTask = (RecurringTask) task;
                        if (checkRecurringOverlap(checkTTask, recurringTask)) {
                            flag = true;
                            System.out.println("overlap detected, checkRecurringOverlap");
                            break;
                        }
                        // Trans vs Anti
                    } else if (categorizeTask(task.GetType()).equals("anti")) {
                        AntiTask antiTask = (AntiTask) task;
                        if (checkAntiOverlap(antiTask, checkTTask)) {
                            flag = true;
                            break;
                        }
                        //Trans vs Trans
                    } else if (task.GetDate() == checkTTask.GetDate()) {
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
                    if(task.GetName().equals(name)) {
                        continue; // same task, no overlap
                    }
                    // Trans vs Recurring
                    if(categorizeTask(task.GetType()).equals("transient")) {
                        TransientTask transientTask = (TransientTask) task;
                        if(checkRecurringOverlap(transientTask, checkRTask)) {
                            flag = true;
                            break;
                        }
                        // Anti vs Recurring
                    } else if(categorizeTask(task.GetType()).equals("anti")) {
                        AntiTask antiTask = (AntiTask) task;
                        if (checkAntiRecurringOverlap(antiTask, checkRTask)) {
                            flag = true;
                            break;
                        }
                    } else if (categorizeTask(task.GetType()).equals("recurring")) {
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
                    if(task.GetName().equals(name)) {
                        continue;
                    }
                    // Trans vs anti
                    if(categorizeTask(task.GetType()).equals("transient")) {
                        TransientTask transientTask = (TransientTask) task;
                        if(checkAntiOverlap(checkATask, transientTask)) {
                            flag = true;
                            break;
                        }
                        // Anti vs Recurring
                    } else if(categorizeTask(task.GetType()).equals("recurring")) {
                        RecurringTask recurringTask = (RecurringTask) task;
                        if(checkAntiRecurringOverlap(checkATask, recurringTask)) {
                            flag = false; //we want overlap with recurring
                            break;
                        } else {
                            flag = true;
                            continue;
                        }

                        // anti vs anti
                    } else if(task.GetDate() == checkATask.GetDate()) {
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
                // System.out.println("completed check");
                break;
            default:
                System.out.println("Error: Invalid Task Type");
                break;
        }
        return flag;
    }

    // main check overlap function for the other functions
    /**
     * The general checkOverlap Function for checking overlap each Task Type
     * @param taskStartTime Beginning time of a task
     * @param taskDuration Length of task
     * @param checkingStartTime Beginning time of comparison Task
     * @param checkingEndTime Ending Time of comparison Task
     * @return True if overlap is found, false otherwise
     */
    private boolean checkOverlap(float taskStartTime, float taskDuration, float checkingStartTime, float checkingEndTime) {
        float taskEndTime = taskStartTime + taskDuration + 0.5f;
        float startTime = taskStartTime - 0.25f;

        if ((startTime >= checkingStartTime && startTime < checkingEndTime) ||
                (taskEndTime > checkingStartTime && taskEndTime <= checkingEndTime) ||
                (startTime <= checkingStartTime && taskEndTime >= checkingEndTime)) {
            return true;
        }
        return false;
    }

    // Simplified functions, should hopefully work
    /**
     * Prepares variables to check overlap between Anti Tasks
     * @param checkATask The new task that is being checked
     * @param antiTask Task from list categorized as Anti Task
     * @return Call checkOverlap function for variables
     */
    private boolean checkAntiToAntiOverlap(AntiTask checkATask, AntiTask antiTask) {
        // float antiStartTime = antiTask.getStartTime();
        // float checkingStartTime = checkATask.getStartTime();
        float checkingEndTime = checkATask.GetStartTime() + checkATask.GetDuration() + 0.5f;

        return checkOverlap(antiTask.GetStartTime(), antiTask.GetDuration(), checkATask.GetStartTime(), checkingEndTime);
    }
    /**
     * Prepares variables to check overlap between Transient Tasks
     * @param checkTTask The new task that is being checked
     * @param transientTask Task from list categorized as Transient Task
     * @return Call checkOverlap function for variables
     */
    private boolean checkTranstoTransOverlap(TransientTask checkTTask, TransientTask transientTask) {
        // float transStartTime = transientTask.getStartTime();
        // float checkingStartTime = checkTTask.getStartTime();
        float checkingEndTime = checkTTask.GetStartTime() + checkTTask.GetDuration() + 0.5f;

        return checkOverlap(transientTask.GetStartTime(), transientTask.GetDuration(), checkTTask.GetStartTime(), checkingEndTime);
    }

    /**
     * Prepares variables to check overlap between Recurrent Tasks
     * @param checkRTask The new task that is being checked
     * @param recurringTask Task from list categorized as Recurrent Task
     * @return Call checkOverlap function for variables
     */
    private boolean checkRectoRecOverlap(RecurringTask checkRTask, RecurringTask recurringTask) {
        // float recurringStartTime = recurringTask.getStartTime();
        // float checkingStartTime = checkRTask.getStartTime();
        float checkingEndTime = checkRTask.GetStartTime() + checkRTask.GetDuration() + 0.5f;

        return checkOverlap(recurringTask.GetStartTime(), recurringTask.GetDuration(), checkRTask.GetStartTime(), checkingEndTime);
    }

    /**
     * Prepares variables to check overlap between Recurring and Transient Tasks
     * @param transientTask Transient Task to be checked with Recurrent
     * @param recurringTask Recurring Task to be checked with Transient
     * @return Call checkOverlap function for variables, false otherwise
     */
    private boolean checkRecurringOverlap(TransientTask transientTask, RecurringTask recurringTask) {
        if (transientTask.GetDate() >= recurringTask.GetStartDate() &&
                transientTask.GetDate() <= recurringTask.GetEndDate()) {
            return checkOverlap(transientTask.GetStartTime(), transientTask.GetDuration(),
                    recurringTask.GetStartTime(), recurringTask.GetDuration());
        }
        return false;
    }

    /**
     * Prepares variables to check overlap between Anti and Transient Tasks
     * @param antiTask Anti Task to check with Transient
     * @param transientTask Transient Task to be checked with Anti
     * @return Call checkOverlap function for variables, false otherwise
     */
    private boolean checkAntiOverlap(AntiTask antiTask, TransientTask transientTask) {
        if (antiTask.GetDate() == transientTask.GetDate()) {
            return checkOverlap(antiTask.GetStartTime(), antiTask.GetDuration(),
                    transientTask.GetStartTime(), transientTask.GetDuration());
        }
        return false;
    }

    /**
     * Prepares variables to check overlap between Anti and Recurrent Tasks
     * @param antiTask Anti Task to check with Recurrent
     * @param recurringTask Recurrent Task to be checked with Anti
     * @return Call checkOverlap function for variables, false otherwise
     */
    private boolean checkAntiRecurringOverlap(AntiTask antiTask, RecurringTask recurringTask) {
        if (antiTask.GetDate() >= recurringTask.GetStartDate() &&
                antiTask.GetDate() <= recurringTask.GetEndDate()) {
            return checkOverlap(antiTask.GetStartTime(), antiTask.GetDuration(),
                    recurringTask.GetStartTime(), recurringTask.GetDuration());
        }
        return false;
    }


    /**
     * prints out task info
     * @param Name The name of task to be viewed and printed
     */
    public void viewTask(String Name) {
        Task task = findTask(Name);

        System.out.println("====================================");
        System.out.println("Task Name: " + task.GetName());
        System.out.println("Task Type: " + task.GetType());
        System.out.println("Task Start Time: " + task.GetStartTime());
        System.out.println("Task Duration: " + task.GetDuration());
        System.out.println("Task Date: " + task.GetDate());

        switch(categorizeTask(task.GetType())) {
            case "transient":
                break;
            case "recurring":
                RecurringTask recTask = (RecurringTask) task;
                System.out.println("Task End Date: " + recTask.GetEndDate());
                System.out.println("Task Start Date: " + recTask.GetStartDate());
                System.out.println("Task Frequency: " + recTask.GetFrequency());
            case "anti":
                break;

        }
        System.out.println("====================================");
    }

    // a modified checkOverlap may be used for Anti-Tasks to check if more than 1 tasks are overlapping it
    // if anti task, cannot delete it, if recurring task has anti task cannot delete, can easily delete transient
    /**
     * Deletes Task unless it conflicts with a rule
     * @param Name Name of chosen task to delete
     */
    public void DeleteTask(String Name) {
        Task task = findTask(Name);
        if(task == null) {
            System.out.println("Failed to delete Task. " + Name + " does not exist.");
        } else if (categorizeTask(task.GetType()).equals("anti")) {
            if (checkTaskOverlap(Name)) {
                System.out.println(Name + " was not removed. There exists a task dependent on this AntiTask");
            } else {
                boolean isRemoved = taskList.remove(task);
                if (isRemoved) {
                    System.out.println(Name + " was successfully removed.");
                } else {
                    System.out.println(Name + " was not removed.");
                }
            }
        } else if (categorizeTask(task.GetType()).equals("recurring")) {
            if (checkTaskOverlap(Name)) {
                System.out.println(Name + " was not removed. There exists a task dependent on this Recurrent Task");
            } else {
                boolean isRemoved = taskList.remove(task);
                if (isRemoved) {
                    System.out.println(Name + " was successfully removed.");
                } else {
                    System.out.println(Name + " was not removed.");
                }
            }
        } else {
            boolean isRemoved = taskList.remove(task);
            if (isRemoved) {
                System.out.println(Name + " was successfully removed.");
            } else {
                System.out.println(Name + " was not removed.");
            }
        }
    }

    // function does not have proper checks yet
    public void editTask(String chosenName, Task ogTask, Scanner input) {
        boolean mainLoop = true;
        categorizeTask(chosenName);

        String taskName = ogTask.GetName();
        String classType = ogTask.GetType();
        float startTime = ogTask.GetStartTime();
        float duration = ogTask.GetDuration();
        int date = ogTask.GetDate();

        // default value if there is no recurring
        int endDate = 0;
        int frequency = 0;
        if (ogTask instanceof RecurringTask) {
            endDate = ((RecurringTask) ogTask).GetEndDate();
            frequency = ((RecurringTask) ogTask).GetFrequency();
        }

        //plan to use createTask option to create a ne task and delete old task

        int choice;
        while (mainLoop) {
            System.out.println("Edit Options: ");
            System.out.println("1) Change Task Name");
            System.out.println("2) Change Task Type");
            System.out.println("3) Change Task Start Time");
            System.out.println("4) Change Task Duration");
            System.out.println("5) Change Task Date");
            System.out.println("6) Change End Date");
            System.out.println("7) Change Frequency");
            System.out.println("8) Finalize Changes");
            System.out.println("9) Cancel Edit");

            choice = input.nextInt();
            input.nextLine();

            switch(choice) {
                case 1:
                    System.out.println("Enter new Name: ");
                    taskName = input.nextLine();
                    break;
                case 2:
                    System.out.println("Enter new Type: ");
                    classType = input.nextLine();
                    break;
                case 3:
                    System.out.println("Enter new Start Time: ");
                    startTime = input.nextFloat();
                    break;
                case 4:
                    System.out.println("Enter new Duration: ");
                    duration = input.nextFloat();
                    break;
                case 5:
                    System.out.println("Enter new Date: ");
                    date = input.nextInt();
                    break;
                case 6:
                    System.out.println("Enter new End Date: ");
                    endDate = input.nextInt();
                case 7:
                    System.out.println("Enter new Frequency (1, 7, or 0): ");
                    frequency = input.nextInt();
                    break;
                case 8:
                    if (frequency == 0) {
                        createTask(taskName, classType, startTime, duration, date);
                    } else {
                        createTask(taskName, classType, startTime, duration, date, endDate, date, frequency);
                    }
                    mainLoop = false;
                    break;
                case 9:
                    System.out.println("Edit Cancelled");
                    mainLoop = false;
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    public void editTaskMenu() {
        Scanner input = new Scanner(System.in);
        boolean mainLoop = true;

        int choice;
        while (mainLoop) {
            System.out.println("Edit Task Menu");
            System.out.println("1) Choose Task");
            System.out.println("2) Cancel Edit");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Please Enter the Task to be Edited: ");
                    String chosenName = input.nextLine();
                    Task ogTask = findTask(chosenName);
                    if (ogTask != null) {
                        System.out.println("You have chosen to edit: " + chosenName);
                        editTask(chosenName, ogTask, input); // Call the editTask function
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;
                case 2:
                    System.out.println("Edit Option Cancelled");
                    mainLoop = false; // exit loop
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        input.close();
    }

    /**
     * Categorizes task based on the type of class that is associated with Transient, Recurrent, and Anti
     * @param classType The Task type associated with a task class
     * @return taskType, Returns task type as Transient, Recurrent, or Anti
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

    // cannot lower case the class types, must be captilized properly? completed?
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
            if(task.GetName().equals(name)){
                nameFlag = true;
            }
        }
        if(nameFlag){
            // change class type to possibly another term to change, maybe make function that reads all allowed names
            switch(categorizeTask(classType)) {
                case "transient":
                    // Create TransientTask
                    if (additionalArgs.length >= 0) {

                        newTask = new TransientTask(date, duration, name, startTime, classType);
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
                        newTask = new RecurringTask(duration, endDate, frequency, name, startDate, startTime, classType);
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
                        newTask = new AntiTask(date, duration, name, startTime, classType);
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