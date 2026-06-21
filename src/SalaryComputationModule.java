import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class SalaryComputationModule implements ActionListener {
    // Declares the window frame and main panels for the Graphical User Interface (GUI)
    private static JFrame frame;
    private static JPanel mainPanel;
    private static JPanel loginBox;
    
    // Declares labels, input fields, and buttons for individual computations
    private static JLabel titleLabel;
    private static JLabel searchLabel;
    private static JTextField searchText;
    private static JButton salaryComputeButton;
    private static JButton backButton;

    // Declares the components needed to show the employee list and run batch calculations
    private static JTable employeeTable;
    private static DefaultTableModel tableModel;
    private static JScrollPane scrollPane;
    private static JButton computeAllButton;
    private static JLabel tableTitleLabel;

    // Constructor method: Runs automatically when an instance of this class is created
    public SalaryComputationModule() {
        // Creates the main window frame, sets its dimensions, and prevents resizing
        frame = new JFrame("Salary Computation & Payroll Dashboard");
        frame.setSize(850, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Creates the primary layout panel and sets a solid blue background color
        mainPanel = new JPanel();
        mainPanel.setLayout(null); // Using absolute positioning (null layout) for precise pixel placement
        mainPanel.setBackground(new Color(60, 161, 195));
        frame.add(mainPanel);

        // =========================================================================
        // LEFT PANEL: Individual Salary Calculator UI Box
        // =========================================================================
        loginBox = new JPanel();
        loginBox.setBounds(25, 20, 320, 520);
        loginBox.setBackground(new Color(255, 255, 255, 220)); // White color with partial transparency (alpha = 220)
        loginBox.setLayout(null);
        mainPanel.add(loginBox);

        // Header title for the individual calculator section
        titleLabel = new JLabel("Salary Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(10, 30, 300, 30);
        loginBox.add(titleLabel);

        // Guide text prompting the user to type an Employee ID
        searchLabel = new JLabel("Enter Employee ID:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
        searchLabel.setBounds(25, 110, 150, 20);
        loginBox.add(searchLabel);

        // Text field input component where the user types the specific ID
        searchText = new JTextField();
        searchText.setBounds(25, 135, 270, 35);
        loginBox.add(searchText);

        // Button to calculate the payroll breakdown for a single employee
        salaryComputeButton = new JButton("Compute Individual");
        salaryComputeButton.setBounds(25, 190, 270, 40);
        salaryComputeButton.setFont(new Font("Arial", Font.BOLD, 14));
        salaryComputeButton.setOpaque(true);
        salaryComputeButton.setBorderPainted(false);
        salaryComputeButton.setBackground(Color.decode("#3CA1C3"));
        salaryComputeButton.setForeground(Color.WHITE);
        salaryComputeButton.addActionListener(this); // Registers this button to listen for click events
        loginBox.add(salaryComputeButton);

        // Button to exit this module and return back to the Main Admin Dashboard
        backButton = new JButton("Admin Dashboard");
        backButton.setBounds(25, 245, 270, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setBackground(new Color(30, 30, 30));
        backButton.setForeground(Color.white);
        backButton.addActionListener(this); // Registers this button to listen for click events
        loginBox.add(backButton);

        // =========================================================================
        // RIGHT PANEL: Employee JTable Registry & Batch Processing UI
        // =========================================================================
        tableTitleLabel = new JLabel("Employee Records Database");
        tableTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tableTitleLabel.setForeground(Color.WHITE);
        tableTitleLabel.setBounds(370, 20, 300, 25);
        mainPanel.add(tableTitleLabel);

        // Defines the structural columns for our user data grid view
        String[] columnHeaders = {"ID", "Last Name", "First Name", "Position", "Basic Salary"};
        
        // Configures the data model and completely disables direct cell editing by users
        tableModel = new DefaultTableModel(columnHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making all grid cells read-only
            }
        };
        
        // Instantiates the visual JTable component using our customized table data model
        employeeTable = new JTable(tableModel);
        employeeTable.getTableHeader().setReorderingAllowed(false); // Prevents users from dragging columns out of order
        
        // Wraps the table inside a JScrollPane component to enable scrolling mechanics
        scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(370, 55, 440, 425);
        mainPanel.add(scrollPane);

        // Green action button to process the entire company payroll overview at once
        computeAllButton = new JButton("Compute All Salaries");
        computeAllButton.setBounds(370, 495, 440, 45);
        computeAllButton.setFont(new Font("Arial", Font.BOLD, 15));
        computeAllButton.setBackground(new Color(39, 174, 96));
        computeAllButton.setForeground(Color.WHITE);
        computeAllButton.setOpaque(true);
        computeAllButton.setBorderPainted(false);
        computeAllButton.addActionListener(this); // Registers this button to listen for click events
        mainPanel.add(computeAllButton);

        // Automatically triggers the method to load CSV files into our visible table
        loadCSVToTable();

        // Standard setup to center the application frame on screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Custom method that reads data from our file and inserts it directly into the JTable
    public static void loadCSVToTable() {
        String csvFile = "EmployeeData.csv";
        String line = "";
        tableModel.setRowCount(0); // Clears out existing records before loading fresh data

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Reads and discards the first line (headers row) of the CSV file

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skips blank rows safely

                // Advanced regex split that correctly handles commas inside enclosed quotation marks
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                
                // Checks if the row array contains enough indexes before extracting data fields
                if (data.length >= 14) {
                    String id = data[0].replaceAll("\"", "").trim();
                    String lastName = data[1].replaceAll("\"", "").trim();
                    String firstName = data[2].replaceAll("\"", "").trim();
                    String position = data[11].replaceAll("\"", "").trim();
                    String basicSalary = data[13].replaceAll("\"", "").trim();

                    // Packages the extracted fields together into a Vector row item
                    Vector<String> row = new Vector<>();
                    row.add(id);
                    row.add(lastName);
                    row.add(firstName);
                    row.add(position);
                    row.add(basicSalary);

                    // appends the row containing the formatted items into the JTable model structure
                    tableModel.addRow(row);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error rendering JTable: " + ex.getMessage());
        }
    }

    // Helper method to look up and calculate standard SSS Contribution deductions
    private static double computeSSS(double basicSalary) {
        if (basicSalary < 3250) return 135.00;
        else if (basicSalary < 3750) return 157.50;
        else if (basicSalary < 4250) return 180.00;
        else if (basicSalary < 4750) return 202.50;
        else if (basicSalary < 5250) return 225.00;
        else if (basicSalary < 5750) return 247.50;
        else if (basicSalary < 6250) return 270.00;
        else if (basicSalary < 6750) return 292.50;
        else if (basicSalary < 7250) return 315.00;
        else if (basicSalary < 7750) return 337.50;
        else if (basicSalary < 8250) return 360.00;
        else if (basicSalary < 8750) return 382.50;
        else if (basicSalary < 9250) return 405.00;
        else if (basicSalary < 9750) return 427.50;
        else if (basicSalary < 10250) return 450.00;
        else if (basicSalary < 10750) return 472.50;
        else if (basicSalary < 11250) return 495.00;
        else if (basicSalary < 11750) return 517.50;
        else if (basicSalary < 12250) return 540.00;
        else if (basicSalary < 12750) return 562.50;
        else if (basicSalary < 13250) return 585.00;
        else if (basicSalary < 13750) return 607.50;
        else if (basicSalary < 14250) return 630.00;
        else if (basicSalary < 14750) return 652.50;
        else return 675.00;
    }

    // Helper method to look up and calculate PhilHealth Premium employee deductions
    private static double computePhilHealth(double basicSalary) {
        double monthlyPremium;
        if(basicSalary <= 10000) {
            monthlyPremium = 300.00;
        } else if(basicSalary >= 60000) {
            monthlyPremium = 1800.00;
        } else {
            monthlyPremium = basicSalary * 0.03; // Evaluates premium based on a flat 3% calculation rate
        }
        return monthlyPremium * 0.50; // Returns the exact 50% shared portion deducted from the employee
    }

    // Helper method to compute Pag-IBIG Fund contribution employee deductions
    private static double computePagIbig(double basicSalary) {
        double employeeRate;
        if(basicSalary >= 1000 && basicSalary <= 1500) {
            employeeRate = 0.01; // 1% deduction rate bracket
        } else {
            employeeRate = 0.02; // 2% deduction rate bracket
        }
        double contribution = basicSalary * employeeRate;
        if (contribution > 100) contribution = 100; // Enforces the statutory cap limit of PHP 100.00 maximum
        return contribution;
    }

    // Application entry execution thread point
    public static void main(String[] args) {
        new SalaryComputationModule();
    }

    // Core event handler method that executes specific logic whenever GUI components are clicked
    @Override
    public void actionPerformed(ActionEvent e) {
    	
        // =========================================================================
        // HANDLER ACTION: INDIVIDUAL SALARY CALCULATION
        // =========================================================================
        if (e.getSource() == salaryComputeButton) {
            String empID = searchText.getText().trim();

            // Prevents execution if the user left the input search text field completely empty
            if (empID.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an Employee ID!", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String EmpData = "EmployeeData.csv";
            String line = "";
            boolean ifFound = false;

            // Open stream to securely parse values row-by-row directly from the CSV spreadsheet file
            try (BufferedReader br = new BufferedReader(new FileReader(EmpData))) {
                br.readLine(); // Discards the column headers tracking definition row

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    String csvID = data[0].replaceAll("\"", "").trim();

                    // Evaluates if the ID inside the file matches the exact query input text entered
                    if (csvID.equals(empID)) {
                        String lastName = data[1].replaceAll("\"", "").trim();
                        String firstName = data[2].replaceAll("\"", "").trim();
                        String position = data[11].replaceAll("\"", "").trim(); 

                        // Sanitizes string format numbers by scrubbing away formatting quotes and comma characters
                        String employeeBasicSalaryStr = data[13].replaceAll("\"", "").replaceAll(",", "").trim();
                        double basicSalary = Double.parseDouble(employeeBasicSalaryStr);

                        // Invokes separate calculations tracking statutory government requirements
                        double sssDeduction = computeSSS(basicSalary);
                        double philHealthDeduction = computePhilHealth(basicSalary);
                        double pagIbigDeduction = computePagIbig(basicSalary);
                        
                        // Tallies totals up and derives the net payout sum
                        double totalDeductions = sssDeduction + philHealthDeduction + pagIbigDeduction;
                        double netSalary = basicSalary - totalDeductions;

                        // Assembles the final results layout text to display inside the output pop-up dialog box
                        String message = "Employee Salary Details:\n\n"
                                + "Name: " + firstName + " " + lastName + "\n"
                                + "ID: " + csvID + "\n"
                                + "Position: " + position + "\n"
                                + "---------------------------\n"
                                + String.format("Basic Salary:      PHP %,.2f\n", basicSalary)
                                + "---------------------------\n"
                                + "--- Deductions ---\n"
                                + String.format("SSS:               PHP %,.2f\n", sssDeduction)
                                + String.format("PhilHealth:        PHP %,.2f\n", philHealthDeduction)
                                + String.format("Pag-IBIG:          PHP %,.2f\n", pagIbigDeduction)
                                + String.format("Total Deductions:  PHP %,.2f\n\n", totalDeductions)
                                + String.format("Net Salary:        PHP %,.2f", netSalary);

                        JOptionPane.showMessageDialog(null, message, "Salary Computation", JOptionPane.INFORMATION_MESSAGE);
                        ifFound = true;
                        break; // Terminates the tracking search loop process once the correct employee record is processed
                    }
                }

                // Notifies the system operator if the target ID parameter could not be matched
                if (!ifFound) {
                    JOptionPane.showMessageDialog(frame, "Employee ID " + empID + " does not exist.", "Not Found", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Error reading employeeData: " + e1.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }

        // =========================================================================
        // HANDLER ACTION: COMPUTE ALL SALARIES (BATCH PROCESSING)
        // =========================================================================
        } else if (e.getSource() == computeAllButton) {
            int rowCount = tableModel.getRowCount();
            
            // Validates that there are records loaded inside the visible database grid table view
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(frame, "There are no records in the table to compute.", "Empty Table", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tracking collectors used to evaluate complete sums across the company roster
            double aggregateCompanyNetPayroll = 0;
            double aggregateCompanyDeductions = 0;
            
            // Initializes a string accumulation helper class to compile the mass printable text list
            StringBuilder reportBuilder = new StringBuilder();
            reportBuilder.append("\n==================================================\n");
            reportBuilder.append("============= NetPay Of All Employees =============\n\n");

            // Iterates through every visible row indexed within our visual layout spreadsheet table
            for (int i = 0; i < rowCount; i++) {
                try {
                    // Extracts the text properties contained inside targeted grid cell index columns
                    String empID = tableModel.getValueAt(i, 0).toString();
                    String lastName = tableModel.getValueAt(i, 1).toString();
                    String firstName = tableModel.getValueAt(i, 2).toString();
                    
                    // Uses regular expressions to match and retain only numbers and period decimal symbols
                    String salaryStr = tableModel.getValueAt(i, 4).toString().replaceAll("[^0-9.]", "").trim();
                    double basicSalary = Double.parseDouble(salaryStr);

                    // Calculates standard deductions for the current row employee instance
                    double sss = computeSSS(basicSalary);
                    double philHealth = computePhilHealth(basicSalary);
                    double pagIbig = computePagIbig(basicSalary);

                    double totalDeductions = sss + philHealth + pagIbig;
                    double netSalary = basicSalary - totalDeductions;

                    // Increments financial metric sums securely into global tracker scopes
                    aggregateCompanyDeductions += totalDeductions;
                    aggregateCompanyNetPayroll += netSalary;

                    // Appends individual payroll row listings directly to the summary log document
                    reportBuilder.append(String.format("ID: %s | %s, %s | Net: PHP %,.2f\n", empID, lastName, firstName, netSalary));

                } catch (Exception ex) {
                    // Logs debug failure records directly out onto the system terminal console if a single line crashes
                    System.err.println("Skipped processing row index: " + i + " | ID: " + tableModel.getValueAt(i, 0));
                    System.err.println("Error reason: " + ex.getMessage());
                }
            }

            // Closes and appends standard trailing line borders onto our completed summary logs document
            reportBuilder.append("\n==================================================\n");
            reportBuilder.append(String.format("Total Number of Staff: %d\n", rowCount));
            reportBuilder.append(String.format("Total Deductions (All Staff): PHP %,.2f\n", aggregateCompanyDeductions));
            reportBuilder.append(String.format("Total Net Payroll Distribution: PHP %,.2f\n", aggregateCompanyNetPayroll));
            reportBuilder.append("==================================================\n");

            // Instantiates a custom scrollable text text field viewer framework layout element
            javax.swing.JTextArea textArea = new javax.swing.JTextArea(reportBuilder.toString());
            textArea.setEditable(false); // Locks user input modifications to preserve generated results integrity
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Applies monospaced lettering for neatly aligned tables
            JScrollPane scroll = new JScrollPane(textArea);
            scroll.setPreferredSize(new java.awt.Dimension(510, 400));

            // Launches a cleanly packaged pop-up pane showing individual list entries plus company global totals combined
            JOptionPane.showMessageDialog(frame, scroll, "Batch Payroll Computation Result", JOptionPane.INFORMATION_MESSAGE);

        // Handles the navigation button event to destroy current frame views and load back the entry panel
        } else if (e.getSource() == backButton) {
            frame.dispose(); // Closes out and releases system memory resources linked to the current window
            new AdminDashboard(); // Opens up an active window interface for the dashboard class
        }
    }
}