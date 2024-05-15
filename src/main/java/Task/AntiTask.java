package Task;

public class AntiTask extends Task{
    public AntiTask(){
        TaskTypes = new String[1];

        TaskTypes[0] = "Cancellation";
    }

    public AntiTask(int date, float duration, String name, float startTime, String type){
        TaskTypes = new String[1];

        TaskTypes[0] = "Cancellation";

        if (!SetDate(date)){
            SetDate(20240101);
        }

        if (!SetDuration(duration)){
            SetDuration(0.25f);
        }

        if (!SetName(name)){
            SetName(name);
        }

        if (!SetStartTime(startTime)){
            SetStartTime(0.0f);
        }

        if (!SetType(type)){
            SetType("Cancellation");
        }
    }
}