package UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Task.*;

public class ScheduleMenu extends MainMenu{
    private MainMenu PreviousWindow;
    private Schedule Schedule;
    private JFrame Window;

    public ScheduleMenu(MainMenu PreviousWindow, Schedule Schedule){
        Window = new JFrame();
        Window.setTitle("Schedule Menu");
        Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Window.setSize(800, 500);
        Window.setLocationRelativeTo(null);

        this.PreviousWindow = PreviousWindow;
        this.Schedule = Schedule;

        GenerateWindow();

        SetVisibility(true);
    }

    @Override
    public void GenerateWindow(){
        JPanel MenuPanel = new JPanel();
        MenuPanel.setLayout(new GridLayout(0, 1, 10, 5));
        MenuPanel.setBackground(Color.LIGHT_GRAY);

        JButton AddTaskButton = new JButton("Add Task");

        AddTaskButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TextInputMenu TextWindow = new TextInputMenu(ScheduleMenu.this);

                TextWindow.SetTitle("Enter Task Name");
            }
        });

        MenuPanel.add(AddTaskButton);

        JButton RemoveTaskButton = new JButton("Remove Task");

        RemoveTaskButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            }
        });

        MenuPanel.add(RemoveTaskButton);

        JButton BackButton = new JButton("Back");

        BackButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                SetVisibility(false);

                PreviousWindow.SetVisibility(true);
            }
        });

        MenuPanel.add(BackButton);

        Window.add(MenuPanel, BorderLayout.WEST);
    }

    @Override
    public void SetVisibility(boolean Visible){
        if (Visible){
            Window.setVisible(true);
        }
        else{
            Window.setVisible(false);
        }
    }
}
