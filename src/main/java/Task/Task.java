package Task;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

public class Task{
  String Name;
  String Type;
  float StartTime;
  float Duration;
  int Date;

  // constructor parameters
  public Task(String name, String type, float startTime, float duration, int date)
  {
    this.Name = name;
    this.Type = type;
    this.StartTime = startTime;
    this.Duration = duration;
    this.Date = date;
  }

  // For testing purposes for now
  public Task(){
    Name = "Your mom";
    Type = "Idk";
    StartTime = 0.0f;
    Duration = 0.0f;
    Date = 20240101;
  }
  
  /**
   * A function designed to set a date variable for a the Task object.
   * @param date
   * @return
   */
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

  /**
   * 
   * @param startTime
   * @return
   */
  public boolean SetStartTime(float startTime){
    if ((startTime >= 0) && (startTime <= 23.75)){
      StartTime = startTime;

      return true;
    }

    return false;
  }

  /**
   * 
   * @param duration
   * @return
   */
  public boolean SetDuration(float duration){
    if ((duration >= 0.25) && (duration <= 23.75)){
      Duration = duration;
    }
  }

  void SetType(String Type){
  }

  // // testing if this works...
  // Task testTask = new Task("Your mom", "idk", 0.0f, 0.0f, 20240101);
}
