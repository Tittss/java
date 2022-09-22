package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Delete extends JFrame{
    public Info info;
    private JTextField textField1;
    private JButton deleteButton;
    private JPanel Delete;

    public Delete() {
        setContentPane(Delete);
        setTitle("Delete");
        setSize(450,200);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRegion();
                dispose();
            }
        });
    }
    public void deleteRegion() {
        regionDelete();
    }
    public void regionDelete() {
        int region_id = Integer.parseInt(textField1.getText());
        if (String.valueOf(region_id).isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter region ID field",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        info = deleteRegionsToDatabase(Integer.parseInt(String.valueOf(region_id)));
        if (info != null) {
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to delete region",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private Info deleteRegionsToDatabase(int region_id) {
        Info info = new Info();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "12345678");
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM regions WHERE region_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(region_id)));

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
