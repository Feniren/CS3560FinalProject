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

    public int setEndDate(int endDate) {
        return EndDate = endDate;
    }

    public int setStartDate(int startDate) {
        return StartDate = startDate;
    }

    public int setFrequency(int frequency) {
        return Frequency = frequency;
    }

    public int getEndDate() {
        return EndDate;
    }

    public int getStartDate() {
        return StartDate;
    }

    public int getFrequency() {
        return Frequency;
    }

    // Testing if this works...
    // RecurringTask rTask = new RecurringTask("asdf", "recurring task thing", 0.0f, 0.0f, 20240101, 20240101, 20240202, 5);  
}
