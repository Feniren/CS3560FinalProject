package Task;

public class TransientTask extends Task{
    public TransientTask(){
        TaskTypes = new String[3];

        TaskTypes[0] = "Appointment";
        TaskTypes[1] = "Shopping";
        TaskTypes[2] = "Visit";
    }

    public TransientTask(int date, float duration, String name, float startTime, String type){
        TaskTypes = new String[3];

        TaskTypes[0] = "Appointment";
        TaskTypes[1] = "Shopping";
        TaskTypes[2] = "Visit";

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
            SetType("Appointment");
        }
    }
}
