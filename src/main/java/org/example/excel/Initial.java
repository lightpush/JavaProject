package org.example.excel;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Initial extends JFrame {
    public Initial() {
        // Set up the initial frame

        // Create a button
        JButton button = new JButton("Click Me");

        // Add action listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the TabbedTable
                new TabbedTable();

                // Close the initial frame
                dispose();
            }
        });

        // Add the button to the frame
        add(button);

        // Set frame properties
        setTitle("Initial Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setSize(1450, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Initial initialFrame = new Initial();
                initialFrame.setVisible(true);
            }
        });
    }
}
