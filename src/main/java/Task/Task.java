package Task;

import java.text.*;
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

    if (DecimalNumbers < 0.375){
      DecimalNumbers = 0.25f;
    }
    else if ((DecimalNumbers >= 0.375) && (DecimalNumbers < 0.625)){
      DecimalNumbers = 0.5f;
    }
    else{
      DecimalNumbers = 0.75f;
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