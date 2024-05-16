import Task.Task;

public class TaskComparator implements java.util.Comparator<Task> {

    public int compare(Task o1, Task o2) {
        float task1 = o1.getStartTime();
        float task2 = o2.getStartTime();
        if(task1<task2) return -1;
        if(task1>task2) return 1;
        return 0;
    }

}
