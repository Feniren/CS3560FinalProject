package Task;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

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

  public int getDate() {
    return Date;
  }

  public float getDuration() {
    return Duration;
  }

  public float getStartTime() {
    return StartTime;
  }

  public String getType() {
    return Type;
  }
  
  public String getName(){
    return Name;
  }

  public String toString(){
	DecimalFormat f = new DecimalFormat("#.##");
	DecimalFormat i = new DecimalFormat("00");

	String s = String.format("%s:%s %s (for %s hour%s): %s (%s)",
								i.format((int)getStartTime()%12), i.format((getStartTime() - (int)getStartTime())*60),
								(getStartTime() >= 12.0f) ? "PM" : "AM", f.format(getDuration()), ((getDuration()>1) ? "s" : ""), getName(), getType());
	
	return s;
  }

  public float RoundFloat(float number){
    String Number = String.valueOf(number);
    String LeadingNumbers = "";
    String TrailingNumbers = "";
    int DecimalIndex = 0;
    float DecimalNumbers;

    for (int i = 0; i < Number.length(); i++){
      if (Number.charAt(i) == '.'){
        DecimalIndex = i;

        break;
      }
      else{
        LeadingNumbers += String.valueOf(Number.charAt(i));
      }
    }

    for (int i = (DecimalIndex + 1); i < Number.length(); i++){
      TrailingNumbers += String.valueOf(Number.charAt(i));
    }

    DecimalNumbers = Float.parseFloat("0." + TrailingNumbers);

    if (DecimalNumbers < 0.125f){
      DecimalNumbers = 0.0f;
    }
    else if ((DecimalNumbers >= 0.125f) && (DecimalNumbers < 0.375f)){
      DecimalNumbers = 0.25f;
    }
    else if ((DecimalNumbers >= 0.375f) && (DecimalNumbers < 0.625f)){
      DecimalNumbers = 0.5f;
    }
    else if ((DecimalNumbers >= 0.625f) && (DecimalNumbers < 0.875f)){
      DecimalNumbers = 0.75f;
    }
    else{
      DecimalNumbers = 1.0f;
    }

    return (Float.parseFloat(LeadingNumbers) + DecimalNumbers);
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
    DateFormat DateValidator = new SimpleDateFormat("yyyy-mm-dd");

    DateFormatter = String.valueOf(date);
    DateFormatter = DateFormatter.substring(0, 4) + "-" + DateFormatter.substring(4, 6) + "-" + DateFormatter.substring(6, DateFormatter.length());

    DateValidator.setLenient(false);

    try{
      DateValidator.parse(DateFormatter);
    }
    catch (Exception e){
      return false;
    }

    Date = date;

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
      Duration = RoundFloat(duration);

      System.out.println(Duration);

      return true;
    }

    return false;
  }

  void SetType(String Type){
  }
}