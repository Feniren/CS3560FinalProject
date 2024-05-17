package UserInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import Task.*;

public class MainMenu{
    protected String Text;
    private ArrayList<Schedule> Schedules;
    private JFrame Window;

    public MainMenu(){
    }

    public MainMenu(ArrayList<Schedule> Schedules){
        Window = new JFrame();
        Window.setTitle("PSS");
        Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Window.setSize(800, 500);
        Window.setLocationRelativeTo(null);

        this.Schedules = Schedules;

        GenerateWindow();
    }

    public void GenerateWindow(){
        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        MainPanel.setBackground(Color.LIGHT_GRAY);

        JButton CreateScheduleButton = new JButton("Create Schedule");

        CreateScheduleButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Schedule Schedule = new Schedule();

                ScheduleMenu CreateScheduleMenu = new ScheduleMenu(MainMenu.this, Schedule);

                SetVisibility(false);
            }
        });

        MainPanel.add(CreateScheduleButton);

        JButton ReadScheduleButton = new JButton("Read Schedule");

        MainPanel.add(ReadScheduleButton);

        Window.add(MainPanel, BorderLayout.CENTER);
    }

    public void SetText(String Text){
        this.Text = Text;
    }

    public void SetVisibility(boolean Visible){
        if (Visible){
            Window.setVisible(true);
        }
        else{
            Window.setVisible(false);
        }
    }

    public void AddSchedule(Schedule Schedule){
        Schedules.add(Schedule);
    }
}
