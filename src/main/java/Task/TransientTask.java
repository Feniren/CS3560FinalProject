package Task;

public class TransientTask extends Task{
    int EndDate;
    float Frequency;

    public TransientTask(String name, String type, float startTime, float duration, int date, int endDate, float frequency) 
    {
        super(name, type, startTime, duration, date);
        EndDate = endDate;
        Frequency = frequency;
    }
    
}
