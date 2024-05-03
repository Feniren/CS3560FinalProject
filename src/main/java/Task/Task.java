package Task;

import java.time.format.DateTimeFormatter;

public class Task{
  String Name;
  String Type;
  float StartTime;
  float Duration;
  int Date;

  public Task(){
    Name = "Your mom";
    Type = "Idk";
    StartTime = 0.0f;
    Duration = 0.0f;
    Date = 20240101;
  }

  public boolean SetDate(int date){
    /*String[] DateComponents = new String[3];

    DateComponents[0] = String.valueOf(date).substring(0, 4);
    DateComponents[1] = String.valueOf(date).substring(4, 6);
    DateComponents[2] = String.valueOf(date).substring(6, 8);

    if ((Integer.parseInt(DateComponents[1]) < 1) || (Integer.parseInt(DateComponents[1]) > 12)){
      return false;
    }

    if ((Integer.parseInt(DateComponents[2]) < 1)){
      return false;
    }

    return true;*/

    String DateFormatter;
    DateTimeFormatter DateValidator;

    DateFormatter = String.valueOf(date);
    DateFormatter = DateFormatter.substring(0, 4) + "-" + DateFormatter.substring(4, 6) + "-" + DateFormatter.substring(6, DateFormatter.length());

    try{
      DateValidator.parse(DateFormatter);
    }
    catch (DateTimeException e){
      return false;
    }

    return true;
  }

  public boolean SetStartTime(float startTime){
    if ((startTime >= 0) && (startTime <= 23.75)){
      StartTime = startTime;

      return true;
    }

    return false;
  }

  public boolean SetDuration(float duration){
    if ((duration >= 0.25) && (duration <= 23.75)){
      Duration = duration;
    }
  }

  void SetType(String Type){
  }
}