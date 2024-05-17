package UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TextInputMenu extends MainMenu{
    public JButton ApplyButton;
    private MainMenu PreviousWindow;
    private JFrame Window;

    public TextInputMenu(MainMenu PreviousMenu){
        Window = new JFrame();
        Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Window.setSize(400, 250);
        Window.setLocationRelativeTo(null);

        this.PreviousWindow = PreviousMenu;

        ApplyButton = new JButton();

        GenerateWindow();

        SetVisibility(true);
    }

    public void GenerateWindow(){
        JPanel MenuPanel = new JPanel();
        MenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        MenuPanel.setBackground(Color.LIGHT_GRAY);

        JTextField TextField = new JTextField(20);

        TextField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Text = TextField.getText();

                ApplyText();
            }
        });

        MenuPanel.add(TextField);

        JButton EnterButton = new JButton("Enter");

        EnterButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Text = TextField.getText();

                ApplyText();
            }
        });

        MenuPanel.add(EnterButton);

        JButton BackButton = new JButton("Back");

        BackButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                SetVisibility(false);
            }
        });

        MenuPanel.add(BackButton);

        Window.add(MenuPanel, BorderLayout.CENTER);
    }

    public void ApplyText(){
        if (!Text.equals("")){
            PreviousWindow.SetText(Text);

            SetVisibility(false);
        }
    }

    public void SetTitle(String Title){
        Window.setTitle(Title);
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
