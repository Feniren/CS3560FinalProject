package Task;

public class RecurringTask extends Task{
    int EndDate;
    int StartDate;
    int Frequency;

    public RecurringTask(){
        EndDate = 20240102;
        Frequency = 1;
        StartDate = 20240101;

        TaskTypes = new String[6];

        TaskTypes[0] = "Class";
        TaskTypes[1] = "Exercise";
        TaskTypes[2] = "Meal";
        TaskTypes[3] = "Sleep";
        TaskTypes[4] = "Study";
        TaskTypes[5] = "Work";
    }

    public RecurringTask(float duration, int endDate, int frequency, String name, int startDate, float startTime, String type){
        TaskTypes = new String[6];

        TaskTypes[0] = "Class";
        TaskTypes[1] = "Exercise";
        TaskTypes[2] = "Meal";
        TaskTypes[3] = "Sleep";
        TaskTypes[4] = "Study";
        TaskTypes[5] = "Work";

        if (!SetDuration(duration)){
            SetDuration(0.25f);
        }

        if (!SetEndDate(endDate)){
            SetEndDate(20240102);
        }

        if (!SetFrequency(frequency)){
            SetFrequency(1);
        }

        if (!SetName(name)){
            SetName(name);
        }

        if (!SetStartDate(startDate)){
            SetStartDate(20240101);
        }

        if (!SetStartTime(startTime)){
            SetStartTime(0.0f);
        }

        if (!SetType(type)){
            SetType("Class");
        }
    }

    public int GetEndDate(){
        return EndDate;
    }

    public int GetFrequency(){
        return Frequency;
    }

    public int GetStartDate(){
        return StartDate;
    }

    public boolean SetFrequency(int frequency){
        if ((frequency == 1) || (frequency == 7)){
            Frequency = frequency;

            return true;
        }

        return false;
    }

    public boolean SetStartDate(int startDate){
        if (DateValid(startDate)){
            StartDate = startDate;

            return true;
        }

        return false;
    }

    public boolean SetEndDate(int endDate){
        if (DateValid(endDate)){
            if ((endDate - StartDate) > 0){
                EndDate = endDate;

                return true;
            }
            else{
                System.out.println("End Date not after Start Date");

                return false;
            }
        }

        return false;
    }

	public String toString(){
		return super.toString() + String.format(" (repeats every %d day%s)", GetFrequency(), (GetFrequency()>1) ? "s" : "");
	}

    // Testing if this works...
    // RecurringTask rTask = new RecurringTask("asdf", "recurring task thing", 0.0f, 0.0f, 20240101, 20240101, 20240202, 5);  
}