//connects mysql to your profile make sure to change username & password

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class eventOrganizer extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5;
    JTextField tf1, tf2, tf3, tf4, tf5;
    JButton insertBtn, deleteBtn, updateBtn, displayBtn;
    Connection con;

    eventOrganizer() {
        setTitle("Event Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Event ID: ");
        l2 = new JLabel("Event Name: ");
        l3 = new JLabel("Cost per Ticket: ");
        l4 = new JLabel("Venue: ");
        l5 = new JLabel("Date (YYYY-MM-DD): ");

        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();

        insertBtn = new JButton("Insert");
        deleteBtn = new JButton("Delete");
        updateBtn = new JButton("Update");
        displayBtn = new JButton("Display");

        insertBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        displayBtn.addActionListener(this);

        setLayout(null);

        add(l1);
        add(tf1);
        add(l2);
        add(tf2);
        add(l3);
        add(tf3);
        add(l4);
        add(tf4);
        add(l5);
        add(tf5);

        add(insertBtn);
        add(deleteBtn);
        add(updateBtn);
        add(displayBtn);

        l1.setBounds(50, 20, 100, 30);
        tf1.setBounds(180, 20, 150, 30);
        l2.setBounds(50, 60, 100, 30);
        tf2.setBounds(180, 60, 150, 30);
        l3.setBounds(50, 100, 120, 30);
        tf3.setBounds(180, 100, 150, 30);
        l4.setBounds(50, 140, 100, 30);
        tf4.setBounds(180, 140, 150, 30);
        l5.setBounds(50, 180, 150, 30);
        tf5.setBounds(180, 180, 150, 30);

        insertBtn.setBounds(50, 230, 100, 40);
        deleteBtn.setBounds(150, 230, 100, 40);
        updateBtn.setBounds(250, 230, 100, 40);
        displayBtn.setBounds(350, 230, 100, 40);


        //make sure you have jar file to be selected in reference library before you run the program 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventtracker", "root", "password");
        } catch (Exception e) {
            System.out.println(e);
        }
        setVisible(true);
    }

    // ----------------------------------------
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertBtn) {
            insertEvent();
        } else if (e.getSource() == deleteBtn) {
            deleteEvent();
        } else if (e.getSource() == updateBtn) {
            updateEvent();
        } else if (e.getSource() == displayBtn) {
            displayEvents();
        }
    }

    // ----------------------------------------
    private void insertEvent() {
        try {
            if (!validateInput()) {
                return;
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Events VALUES(?,?,?,?,?)");
            stmt.setInt(1, Integer.parseInt(tf1.getText()));
            stmt.setString(2, tf2.getText());
            stmt.setDouble(3, Double.parseDouble(tf3.getText()));
            stmt.setString(4, tf4.getText());
            stmt.setString(5, tf5.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event Inserted Successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // ----------------------------------------
    private void deleteEvent() {
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Events WHERE id=?");
            stmt.setInt(1, Integer.parseInt(tf1.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event Deleted Successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // ----------------------------------------
    private void updateEvent() {
        try {
            if (!validateInput()) {
                return;
            }
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE Events SET eventName=?, cost=?, venue=?, eventDate=? WHERE id=?");
            stmt.setString(1, tf2.getText());
            stmt.setDouble(2, Double.parseDouble(tf3.getText()));
            stmt.setString(3, tf4.getText());
            stmt.setString(4, tf5.getText());
            stmt.setInt(5, Integer.parseInt(tf1.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event Updated Successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // ----------------------------------------
    private void displayEvents() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Events");
            StringBuilder data = new StringBuilder("Events List:\n\n");
            while (rs.next()) {
                data.append("ID: ").append(rs.getInt(1)).append(", Name: ").append(rs.getString(2))
                        .append(", Cost: ₹").append(rs.getDouble(3)).append(", Venue: ").append(rs.getString(4))
                        .append(", Date: ").append(rs.getString(5)).append("\n");
            }
            JOptionPane.showMessageDialog(this, data.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // ----------------------------------------
    private boolean validateInput() {
        try {
            if (tf2.getText().isEmpty() || tf4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Event Name and Venue cannot be empty!");
                return false;
            }
            double cost = Double.parseDouble(tf3.getText());
            if (cost <= 0) {
                JOptionPane.showMessageDialog(this, "Cost per ticket must be a positive number!");
                return false;
            }
            String dateStr = tf5.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date eventDate = sdf.parse(dateStr);
            if (eventDate.before(new Date())) {
                JOptionPane.showMessageDialog(this, "Event date cannot be in the past!");
                return false;
            }
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format! Please check all fields.");
            return false;
        }
    }

    // ----------------------------------------
    public static void main(String[] args) {
        new eventOrganizer();
    }
}
