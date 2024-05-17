package UserInterface;

import Task.Schedule;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TaskTypeMenu extends MainMenu{
    private MainMenu PreviousWindow;
    private Task.Schedule Schedule;
    private TextInputMenu TextWindow;
    private JFrame Window;

    public TaskTypeMenu(MainMenu PreviousWindow){
        Window = new JFrame();
        Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Window.setSize(400, 250);
        Window.setLocationRelativeTo(null);

        this.PreviousWindow = PreviousWindow;

        GenerateWindow();

        SetVisibility(true);
    }

    @Override
    public void GenerateWindow(){
        JPanel MenuPanel = new JPanel();
        MenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        MenuPanel.setBackground(Color.LIGHT_GRAY);

        JButton TransientTaskButton = new JButton("Transient Task");

        TransientTaskButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TextInputMenu TextInputWindow = new TextInputMenu(TaskTypeMenu.this);

                TextInputWindow.SetTitle("Enter Task Name");

                SetVisibility(false);
            }
        });

        MenuPanel.add(TransientTaskButton);

        JButton RecurringTaskButton = new JButton("Recurring Task");

        RecurringTaskButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TextInputMenu TextInputWindow = new TextInputMenu(TaskTypeMenu.this);

                TextInputWindow.SetTitle("Enter Task Name");

                SetVisibility(false);
            }
        });

        MenuPanel.add(RecurringTaskButton);

        JButton AntiTaskButton = new JButton("Anti Task");

        AntiTaskButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TextInputMenu TextInputWindow = new TextInputMenu(TaskTypeMenu.this);

                TextInputWindow.SetTitle("Enter Task Name");

                SetVisibility(false);
            }
        });

        MenuPanel.add(AntiTaskButton);

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
