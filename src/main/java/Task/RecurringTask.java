package Task;

public class RecurringTask extends Task{
    int EndDate;
    int StartDate;
    int Frequency;

    public RecurringTask(){
        StartDate = Date;
    }

    public boolean SetFrequency(int frequency){
        if ((frequency == 1) || (frequency == 7)){
            Frequency = frequency;

            return true;
        }

        return false;
    }
}