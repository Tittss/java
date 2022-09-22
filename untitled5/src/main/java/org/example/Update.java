package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Update extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JButton updateButton;
    private JPanel Update;

    public Update() {
        setContentPane(Update);
        setTitle("Update");
        setSize(450,300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRegion();
                dispose();
            }
        });
    }
    public void updateRegion() {
        regionUpdate();
    }
    public void regionUpdate() {
        int region_id = Integer.parseInt(textField1.getText());
        String region_name = textField2.getText();

        if (String.valueOf(region_id).isEmpty() || region_name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all field",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        info = updateRegionsToDatabase(Integer.parseInt(String.valueOf(region_id)), region_name);
        if (info != null) {
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to update region",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public Info info;
    private Info updateRegionsToDatabase(int region_id, String region_name) {
        Info info = new Info();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "12345678");
            Statement statement = connection.createStatement();
            String sql = "UPDATE regions SET region_name=? WHERE region_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(2, Integer.parseInt(String.valueOf(region_id)));
            preparedStatement.setString(1, region_name);

            preparedStatement.executeUpdate();

            statement.close();
            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return info;
    }
}
