package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddInfo extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton addButton;
    private JPanel addPanel;
    private JButton cancelButton;
    private JButton deleteButton;
    private JButton updateButton;


    public AddInfo () {
        setTitle("Add region");
        setContentPane(addPanel);
        setMinimumSize(new Dimension(450, 500));
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  registerRegions();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Delete();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Update();
            }
        });
    }

    public void addRegionName() {
        registerRegions();
    }
    public void registerRegions (){
        int region_id = Integer.parseInt(textField1.getText());
        String region_name = textField2.getText();

        if (String.valueOf(region_id).isEmpty() || region_name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all field",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        info = addRegionsToDatabase(Integer.parseInt(String.valueOf(region_id)), region_name);
        if (info != null) {
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add region",
                            "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Info info;
    private Info addRegionsToDatabase(int region_id, String region_name) {
        Info info = new Info();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "12345678");
            Statement statement = connection.createStatement();
            String sql = " INSERT INTO regions (region_id, region_name)" + " VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(region_id)));
            preparedStatement.setString(2, region_name);

            int addRow = preparedStatement.executeUpdate();
            if (addRow > 0) {
                info = new Info();
                info.region_id = Integer.parseInt(String.valueOf(region_id));
                info.region_name = region_name;
            }

            statement.close();
            connection.close();
        }
        catch(SQLException e){
                e.printStackTrace();
        }
            return info;
        }
    }


