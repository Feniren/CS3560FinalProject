import java.util.*;
import javax.swing.*;

import Task.*;
import UserInterface.*;

public class Main {
  public static void main(String[] args) {
    boolean DisplayJSwingMenu = false;
    ArrayList<Schedule> Schedules = new ArrayList<Schedule>();

    if (DisplayJSwingMenu){
      SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run(){
          MainMenu MainWindow = new MainMenu(Schedules);
          MainWindow.SetVisibility(true);
        }
      });
    }
  }
}