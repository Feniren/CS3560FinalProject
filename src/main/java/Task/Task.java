package Task;

import java.beans.Transient;
import java.time.DateTimeException;
import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;

public class Task
{
  String Name;
  String Type;
  float StartTime;
  float Duration;
  int Date;

  // constructor parameters
  public Task(String name, String type, float startTime, float duration, int date){
    this.Name = name;
    this.Type = type;
    this.StartTime = startTime;
    this.Duration = duration;
    this.Date = date;
  }

  // For testing purposes for now
  public Task() {
    Name = "Your mom";
    Type = "Idk";
    StartTime = 0.0f;
    Duration = 0.0f;
    Date = 20240101;
  }
  
  /**
   * A function designed to set and verify a date variable for a the Task object.
   * @param date
   * @return true or false based on if the Date is verified to be correct.
   */
  public boolean SetDate(int date) {
    // Looks to convert date to a string into Year-Month-Day Format and verify
    String DateFormatter;

    // convert date to string an reformat
    DateFormatter = String.valueOf(date);
    DateFormatter = DateFormatter.substring(0, 4) + "-" + DateFormatter.substring(4, 6) + "-" + DateFormatter.substring(6, DateFormatter.length());

    // Verify Date String (Seems to work)
    try
    {
      LocalDate.parse(DateFormatter);
    }
    catch (DateTimeException e)
    {
      return false;
    }

    Date = date;
    return true;
  }

  /**
   * A function that will verify the time and set to the Task Object
   * @param startTime
   * @return true or false based on if 
   */
  public boolean SetStartTime(float startTime) {
    if ((startTime >= 0) && (startTime <= 23.75)) {  
      StartTime = startTime;
      return true;
    }

    return false;
  }

  /**
   * A function that will verify the duration and set to the Task object
   * @param duration
   */
  public boolean SetDuration(float duration) {
    if ((duration >= 0.25) && (duration <= 23.75))
    {
      Duration = duration;
      return true;
    }

    return false;
  }

  /**
   * Sets Task object to a subclass of Task and creates object
   * @param type
   */
  public void SetType(String type)
  {
    // Should subclass objects be made here?
    if (type.equals("Recurring Task"))
    {

    }
    else if (type.equals("Anti Task"))
    {

    }
    else if (type.equals("Transient Task"))
    {
      
    } 
    else 
    {
      throw new IllegalArgumentException(type);
    }
  }

  // // testing if this works...
  // Task testTask = new Task("Your mom", "idk", 0.0f, 0.0f, 20240101);
}
