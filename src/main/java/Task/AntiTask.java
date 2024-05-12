package Task;

public class AntiTask extends Task{
    public AntiTask(String name, String type, float startTime, float duration, int date)
    {
        super(name, type, startTime, duration, date);
    }

    /**
     * Designed to remove an existing RecurringTask object
     * @param task the object that will be removed
     * @return True if the task was successfully removed or False if it failed.
     */
    public boolean cancelTask(RecurringTask task) {
        return true;
    }
}