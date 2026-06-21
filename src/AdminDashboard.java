import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.BorderFactory;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AdminDashboard implements ActionListener {
    private static JFrame frame;
    private static JPanel mainPanel;

    // Left sidebar
    private static JPanel sidePanel;

    // Center panel
    private static JPanel centerPanel;
    private static JTextField searchText;
    private static JButton searchButton;

    // Right panel
    private static JPanel rightPanel;

    // Action buttons
    private static JButton salaryComputationButton;
    private static JButton updateButton;
//    private static JButton viewAllButton;
    private static JButton addButton;
    private static JButton logoutButton;

    // Employee detail labels (left side)
    private static JLabel empNameLabel;
    private static JLabel empIDLabel;
    private static JLabel empPositionLabel;
    private static JLabel empStatusLabel;
    private static JLabel empBirthdayLabel;
    private static JLabel empSSSLabel;
    private static JLabel empPhilHealthLabel;
    private static JLabel empTINLabel;
    private static JLabel empPagIbigLabel;

    // DAGDAG: Dynamic variables para sa live tracking hook counters
    private static JLabel totalEmployeesValueLabel;
    private static JLabel activeEmployeesValueLabel;

    public AdminDashboard() {
        // Window setup — landscape
        frame = new JFrame("Admin Dashboard");
        frame.setSize(900, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 250));
        frame.add(mainPanel);

        buildSidePanel();
        buildCenterPanel();
        buildRightPanel();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ─────────────────────────────────────────
    // LEFT SIDEBAR — Employee Details
    // ─────────────────────────────────────────
    private void buildSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setBounds(0, 0, 220, 560);
        sidePanel.setBackground(new Color(0, 0, 0));
        sidePanel.setLayout(null);
        mainPanel.add(sidePanel);

        // App title
        JLabel appTitle = new JLabel("MotorPH", SwingConstants.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 20));
        appTitle.setForeground(Color.WHITE);
        appTitle.setBounds(0, 20, 220, 30);
        sidePanel.add(appTitle);

//        @SuppressWarnings("unused")
		JLabel appSubtitle = new JLabel("Employee System", SwingConstants.CENTER);
        appSubtitle.setFont(new Font("Arial", Font.PLAIN, 11));
        appSubtitle.setForeground(Color.GRAY);
        appSubtitle.setBounds(0, 48, 220, 20);
        sidePanel.add(appSubtitle);

        // Divider
        JPanel divider1 = new JPanel();
        divider1.setBounds(20, 78, 180, 1);
        divider1.setForeground(Color.WHITE);
        sidePanel.add(divider1);

        // Avatar circle placeholder
        JPanel avatar = new JPanel();
        avatar.setBounds(85, 95, 50, 50);
        avatar.setBackground(new Color(255, 107, 107));
        avatar.setBorder(BorderFactory.createLineBorder(new Color(255, 150, 150), 2));
        sidePanel.add(avatar);

        // Employee info section title
        JLabel infoTitle = new JLabel("Employee Details", SwingConstants.CENTER);
        infoTitle.setFont(new Font("Arial", Font.BOLD, 13));
        infoTitle.setForeground(new Color(255, 107, 107));
        infoTitle.setBounds(0, 158, 220, 20);
        sidePanel.add(infoTitle);

        // Divider
        JPanel divider2 = new JPanel();
        divider2.setBounds(20, 183, 180, 1);
        divider2.setForeground(Color.WHITE);
        sidePanel.add(divider2);

        // Employee detail labels — these get updated after search
        empNameLabel     = makeSideLabel("Name: —", 195);
        empIDLabel       = makeSideLabel("ID: —", 220);
        empPositionLabel = makeSideLabel("Position: —", 245);
        empStatusLabel   = makeSideLabel("Status: —", 270);
        empBirthdayLabel = makeSideLabel("Birthday: —", 295);
        empSSSLabel      = makeSideLabel("SSS: —", 320);
        empPhilHealthLabel = makeSideLabel("PhilHealth: —", 345);
        empTINLabel      = makeSideLabel("TIN: —", 370);
        empPagIbigLabel  = makeSideLabel("Pag-IBIG: —", 395);

        sidePanel.add(empNameLabel);
        sidePanel.add(empIDLabel);
        sidePanel.add(empPositionLabel);
        sidePanel.add(empStatusLabel);
        sidePanel.add(empBirthdayLabel);
        sidePanel.add(empSSSLabel);
        sidePanel.add(empPhilHealthLabel);
        sidePanel.add(empTINLabel);
        sidePanel.add(empPagIbigLabel);

        // Divider before logout
        JPanel divider3 = new JPanel();
        divider3.setBounds(20, 460, 180, 1);
        divider3.setForeground(Color.WHITE);
        sidePanel.add(divider3);

        // Logout button at bottom of sidebar
        logoutButton = new JButton("Sign Out");
        logoutButton.setBounds(20, 475, 180, 38);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(new Color(200, 50, 50));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(this);
        sidePanel.add(logoutButton);
    }

    // Helper to create consistent sidebar labels
    private JLabel makeSideLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 11));
        label.setForeground(Color.WHITE);
        label.setBounds(15, y, 200, 18);
        return label;
    }

    // ─────────────────────────────────────────
    // CENTER PANEL — Search + Action Buttons
    // ─────────────────────────────────────────
    private void buildCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBounds(220, 0, 420, 560);
        centerPanel.setBackground(new Color(245, 245, 250));
        centerPanel.setLayout(null);
        mainPanel.add(centerPanel);

        // Header
        JLabel headerTitle = new JLabel("Admin Dashboard");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 22));
        headerTitle.setForeground(new Color(30, 30, 45));
        headerTitle.setBounds(25, 25, 300, 30);
        centerPanel.add(headerTitle);

        JLabel headerSub = new JLabel("Manage employee records");
        headerSub.setFont(new Font("Arial", Font.PLAIN, 12));
        headerSub.setForeground(new Color(150, 150, 170));
        headerSub.setBounds(25, 52, 300, 20);
        centerPanel.add(headerSub);

        // Search bar background card
        JPanel searchCard = new JPanel();
        searchCard.setBounds(20, 90, 380, 70);
        searchCard.setBackground(Color.WHITE);
        searchCard.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 235), 1));
        searchCard.setLayout(null);
        centerPanel.add(searchCard);

        JLabel searchLabel = new JLabel("Employee ID:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
        searchLabel.setForeground(new Color(100, 100, 120));
        searchLabel.setBounds(15, 10, 100, 20);
        searchCard.add(searchLabel);

        searchText = new JTextField();
        searchText.setBounds(15, 35, 280, 25);
        searchText.setFont(new Font("Arial", Font.PLAIN, 13));
        searchText.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
        searchText.setBackground(Color.WHITE);
        searchCard.add(searchText);

        // Search button with icon look
        searchButton = new JButton("Search");
        searchButton.setBounds(305, 32, 75, 30);
        searchButton.setFont(new Font("Arial", Font.PLAIN, 10));
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setBackground(new Color(0, 0, 0));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);
        searchCard.add(searchButton);

        // Section label
        JLabel actionsLabel = new JLabel("Quick Actions");
        actionsLabel.setFont(new Font("Arial", Font.BOLD, 15));
        actionsLabel.setForeground(new Color(30, 30, 45));
        actionsLabel.setBounds(25, 178, 200, 25);
        centerPanel.add(actionsLabel);

        // Action buttons — card style
        salaryComputationButton = makeActionButton("Salary Computation", new Color(60, 161, 195), 215);
        updateButton = makeActionButton(" Manage Employee Data", new Color(232, 168, 56), 275);
//        viewAllButton = makeActionButton("View All Employee", new Color(39, 174, 96), 335);

        centerPanel.add(salaryComputationButton);
        centerPanel.add(updateButton);
//        centerPanel.add(viewAllButton);
    }

    // Helper to create consistent action buttons
    private JButton makeActionButton(String text, Color color, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(20, y, 380, 48);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        btn.addActionListener(this);
        return btn;
    }

    // ─────────────────────────────────────────
    // RIGHT PANEL — Stats / Info Cards
    // ─────────────────────────────────────────
    private void buildRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setBounds(640, 0, 260, 560);
        rightPanel.setBackground(new Color(255, 255, 255));
        rightPanel.setLayout(null);
        mainPanel.add(rightPanel);

        JLabel rightTitle = new JLabel("System Info");
        rightTitle.setFont(new Font("Arial", Font.BOLD, 15));
        rightTitle.setForeground(new Color(30, 30, 45));
        rightTitle.setBounds(20, 25, 220, 25);
        rightPanel.add(rightTitle);

        // BINAGO: Sinalo ng mga global variables ang return JLabels ng mga cards para mabago ang text dynamically
        totalEmployeesValueLabel = addInfoCard("Total Employees", "0", new Color(255, 107, 107), 65);
        activeEmployeesValueLabel = addInfoCard("Active", "0", new Color(39, 174, 96), 155);
        
        addInfoCard("Positions", "12", new Color(60, 161, 195), 245);
        addInfoCard("Departments", "6", new Color(232, 168, 56), 335);

        // DAGDAG: Inilunsad ang counter function sa startup ng window
        updateSystemInfoCounters();
    }

    // BINAGO: Ginawang `JLabel` ang return type sa halip na `void` para makuha ang reference ng value label
    private JLabel addInfoCard(String title, String value, Color accent, int y) {
        JPanel card = new JPanel();
        card.setBounds(15, y, 230, 75);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, accent));
        card.setLayout(null);
        rightPanel.add(card);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(120, 120, 140));
        titleLabel.setBounds(12, 12, 210, 20);
        card.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 26));
        valueLabel.setForeground(new Color(30, 30, 45));
        valueLabel.setBounds(12, 35, 210, 30);
        card.add(valueLabel);

        return valueLabel;
    }

    // DAGDAG: Ang mismong file scanner block para mag-recalculate ng values mula sa CSV file
    private void updateSystemInfoCounters() {
        String csvFile = "EmployeeData.csv";
        int totalCount = 0;
        int activeCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Lakpasan ang table column headers
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                totalCount++; 

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                
                // Sinisigurado nating sapat ang haba ng array bago basahin ang index ng status (index 10 o 11 base sa file)
                if (data.length > 10) {
                    String status = data[10].replaceAll("\"", "").trim();
                    if (status.equalsIgnoreCase("Regular") || status.equalsIgnoreCase("Probationary")) {
                        activeCount++;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Counter System Scanning Error: " + ex.getMessage());
        }

        // I-set ang text kung hindi null ang mga objects sa UI swing memory heap
        if (totalEmployeesValueLabel != null) {
            totalEmployeesValueLabel.setText(String.valueOf(totalCount));
        }
        if (activeEmployeesValueLabel != null) {
            activeEmployeesValueLabel.setText(String.valueOf(activeCount));
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Search logic
        if (e.getSource() == searchButton) {
            String inputID = searchText.getText().trim();

            if (inputID.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an Employee ID.");
                return;
            }

            String csvFile = "EmployeeData.csv";
            String line = "";
            boolean ifFound = false;

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                br.readLine(); // skip header

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    String csvID = data[0].replaceAll("\"", "").trim();

                    if (csvID.equals(inputID)) {
                        // Update left sidebar labels with found employee data
                        empNameLabel.setText("Name: " + data[2].replaceAll("\"","").trim()
                                + " " + data[1].replaceAll("\"","").trim());
                        empIDLabel.setText("ID: " + data[0].replaceAll("\"","").trim());
                        empPositionLabel.setText("Position: " + data[11].replaceAll("\"","").trim());
                        empStatusLabel.setText("Status: " + data[10].replaceAll("\"","").trim());
                        empBirthdayLabel.setText("Birthday: " + data[3].replaceAll("\"","").trim());
                        empSSSLabel.setText("SSS: " + data[6].replaceAll("\"","").trim());
                        empPhilHealthLabel.setText("PhilHealth: " + data[7].replaceAll("\"","").trim());
                        empTINLabel.setText("TIN: " + data[8].replaceAll("\"","").trim());
                        empPagIbigLabel.setText("Pag-IBIG: " + data[9].replaceAll("\"","").trim());

                        ifFound = true;
                        break;
                    }
                }

                if (!ifFound) {
                    JOptionPane.showMessageDialog(frame,
                        "Employee ID " + inputID + " does not exist.",
                        "Not Found", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                    "Error reading file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            // DAGDAG: Magre-recount din tuwing magsi-search para laging up-to-date ang dashboard panels
            updateSystemInfoCounters();
        }

        // Logout
        if (e.getSource() == logoutButton) {
            frame.dispose();
            LoginWindow.main(null);
        }

        // Salary Computation
        if (e.getSource() == salaryComputationButton) {
            frame.dispose();
            SalaryComputationModule.main(null);
        }

        // Manage Employee Data Button
        if (e.getSource() == updateButton) {
            frame.dispose();
            new UpdateEmployee();
        }

        if (e.getSource() == addButton) {
            JOptionPane.showMessageDialog(frame, "Add Employee feature coming soon!");
        }
    }
}