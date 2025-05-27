import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentGradeTracker extends JFrame {

    private JTextField nameField, gradeField;
    private JTextArea displayArea;
    private ArrayList<String> studentNames = new ArrayList<>();
    private ArrayList<Double> studentGrades = new ArrayList<>();

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Define button colors
        Color normalGreen = new Color(34, 139, 34); // ForestGreen
        Color hoverGreen = new Color(50, 205, 50);  // LimeGreen

        // Hover effect
        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setBackground(hoverGreen);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setBackground(normalGreen);
            }
        };

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Student Data"));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Grade");
        JButton clearButton = new JButton("Clear All");

        JButton[] topButtons = {addButton, clearButton};

        for (JButton btn : topButtons) {
            btn.setFocusPainted(false);
            btn.setBackground(normalGreen);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 12));
            btn.addMouseListener(hoverEffect);
        }

        inputPanel.add(addButton);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.NORTH);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBorder(BorderFactory.createTitledBorder("Student Grades"));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton averageButton = new JButton("Calculate Average");
        JButton highestButton = new JButton("Find Highest");
        JButton lowestButton = new JButton("Find Lowest");

        JButton[] bottomButtons = {averageButton, highestButton, lowestButton};

        for (JButton btn : bottomButtons) {
            btn.setFocusPainted(false);
            btn.setBackground(normalGreen);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 12));
            btn.addMouseListener(hoverEffect);
        }

        statsPanel.add(averageButton);
        statsPanel.add(highestButton);
        statsPanel.add(lowestButton);

        add(statsPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addGrade());
        clearButton.addActionListener(e -> clearAll());
        averageButton.addActionListener(e -> showAverage());
        highestButton.addActionListener(e -> showHighest());
        lowestButton.addActionListener(e -> showLowest());
    }

    private void addGrade() {
        String name = nameField.getText().trim();
        String gradeStr = gradeField.getText().trim();

        if (name.isEmpty() || gradeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeStr);
            studentNames.add(name);
            studentGrades.add(grade);
            displayArea.append(name + ": " + grade + "\n");
            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade. Please enter a numeric value.");
        }
    }

    private void showAverage() {
        if (studentGrades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades to calculate.");
            return;
        }

        double sum = 0;
        for (double grade : studentGrades) {
            sum += grade;
        }
        double average = sum / studentGrades.size();
        JOptionPane.showMessageDialog(this, "Average Grade: " + String.format("%.2f", average));
    }

    private void showHighest() {
        if (studentGrades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades to evaluate.");
            return;
        }

        double max = studentGrades.get(0);
        String topStudent = studentNames.get(0);
        for (int i = 1; i < studentGrades.size(); i++) {
            if (studentGrades.get(i) > max) {
                max = studentGrades.get(i);
                topStudent = studentNames.get(i);
            }
        }
        JOptionPane.showMessageDialog(this, "Highest Grade: " + max + " by " + topStudent);
    }

    private void showLowest() {
        if (studentGrades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades to evaluate.");
            return;
        }

        double min = studentGrades.get(0);
        String lowStudent = studentNames.get(0);
        for (int i = 1; i < studentGrades.size(); i++) {
            if (studentGrades.get(i) < min) {
                min = studentGrades.get(i);
                lowStudent = studentNames.get(i);
            }
        }
        JOptionPane.showMessageDialog(this, "Lowest Grade: " + min + " by " + lowStudent);
    }

    private void clearAll() {
        studentNames.clear();
        studentGrades.clear();
        displayArea.setText("");
        nameField.setText("");
        gradeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeTracker tracker = new StudentGradeTracker();
            tracker.setVisible(true);
        });
    }
}
