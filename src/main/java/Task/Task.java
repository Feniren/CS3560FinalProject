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
  String[] TaskTypes;

  public Task(){
    Name = "";
    Type = "";
    StartTime = 0.0f;
    Duration = 0.0f;
    Date = 20240101;
    TaskTypes = new String[1];

    TaskTypes[0] = "Null";
  }

  public boolean DateValid(int date){
    String DateFormatter;
    DateFormat DateValidator = new SimpleDateFormat("yyyy-mm-dd");

    DateFormatter = String.valueOf(date);
    DateFormatter = DateFormatter.substring(0, 4) + "-" + DateFormatter.substring(4, 6) + "-" + DateFormatter.substring(6, DateFormatter.length());

    DateValidator.setLenient(false);

    try{
      DateValidator.parse(DateFormatter);
    }
    catch (Exception e){
      System.out.println("Invalid Date");

      return false;
    }

    return true;
  }

  public int GetDate(){
    return Date;
  }

  public float GetDuration(){
    return Duration;
  }

  public String GetName(){
    return Name;
  }

  public float GetStartTime(){
    return StartTime;
  }

  public String GetType(){
    return Type;
  }
  
  public String getName(){
    return Name;
  }

  public String toString(){
	DecimalFormat f = new DecimalFormat("#.##");
	DecimalFormat i = new DecimalFormat("00");

	String s = String.format("%s:%s %s (for %s hour%s): %s (%s)",
								i.format((int)GetStartTime()%12), i.format((GetStartTime() - (int)GetStartTime())*60),
								(GetStartTime() >= 12.0f) ? "PM" : "AM", f.format(GetDuration()), ((GetDuration()>1) ? "s" : ""), getName(), GetType());
	
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
    if (DateValid(date)){
      Date = date;

      return true;
    }

    return false;
  }

  public boolean SetDuration(float duration){
    if ((duration >= 0.25) && (duration <= 23.75)){
      if ((StartTime + duration) <= 24.0f){
        Duration = RoundFloat(duration);

        return true;
      }
      else{
        System.out.println("Duration too long for selected start time " + Float.toString(StartTime + duration));

        return false;
      }
    }

    System.out.println("Invalid Duration");

    return false;
  }

  public boolean SetName(String name){
    Name = name;

    return true;
  }

  public boolean SetStartTime(float startTime){
    if ((startTime >= 0) && (startTime <= 23.75)){
      StartTime = RoundFloat(startTime);
      return true;
    }

    System.out.println("Invalid Start Time");

    return false;
  }

  public boolean SetType(String type){
    for (int i = 0; i < TaskTypes.length; i++){
      if (TaskTypes[i].equals(type)){
        Type = type;

        return true;
      }
    }

    return false;
  }
}