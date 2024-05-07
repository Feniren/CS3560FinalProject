package Task;

public class RecurringTask extends Task{
    int EndDate;
    int StartDate;
    int Frequency;

    // I think this is how it should look like...
    public RecurringTask(String name, String type, float startTime, float duration, int date, int startDate, int endDate, int frequency)
    {
        super(name, type, startTime, duration, date);
        StartDate = startDate;
        EndDate = endDate;
        Frequency = frequency;
    }

    // Testing if this works...
    RecurringTask rTask = new RecurringTask("asdf", "recurring task thing", 0.0f, 0.0f, 20240101, 20240101, 20240202, 5);  
}

