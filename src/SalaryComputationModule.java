import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SalaryComputationModule implements ActionListener {
    // This code declares the GUI components needed for the module
    private static JFrame frame;
    private static JPanel mainPanel;
    private static JPanel loginBox;
    private static JLabel titleLabel;
    private static JLabel searchLabel;
    private static JTextField searchText;
    private static JButton salaryComputeButton;
    private static JButton backButton;

    // This constructor initializes and builds the whole graphical user interface (GUI)
    public SalaryComputationModule() {
        // This code configures the main window frame properties
        frame = new JFrame("Salary Computation");
        frame.setSize(450, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // This code initializes the main panel and sets the solid background color (RGB: 60, 161, 195)
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(60, 161, 195));
        frame.add(mainPanel);

        // This code creates the translucent white container card inside the window
        loginBox = new JPanel();
        loginBox.setBounds(45, 20, 340, 460);
        loginBox.setBackground(new Color(255, 255, 255, 220));
        loginBox.setLayout(null);
        mainPanel.add(loginBox);

        // This code configures and adds the "Salary Calculator" header title
        titleLabel = new JLabel("Salary Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(10, 30, 320, 30);
        loginBox.add(titleLabel);

        // This code adds the instruction label for entering the Employee ID
        searchLabel = new JLabel("Enter Employee ID:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
        searchLabel.setBounds(35, 110, 150, 20);
        loginBox.add(searchLabel);

        // This code creates the input text field where the user types the ID
        searchText = new JTextField();
        searchText.setBounds(35, 135, 270, 35);
        loginBox.add(searchText);

        // This code configures the "Compute" button with its blue theme and action listener
        salaryComputeButton = new JButton("Compute");
        salaryComputeButton.setBounds(35, 190, 270, 40);
        salaryComputeButton.setFont(new Font("Arial", Font.BOLD, 14));
        salaryComputeButton.setOpaque(true);
        salaryComputeButton.setBorderPainted(false);
        salaryComputeButton.setBackground(Color.decode("#3CA1C3"));
        salaryComputeButton.setForeground(Color.WHITE);
        salaryComputeButton.addActionListener(this);
        loginBox.add(salaryComputeButton);

        // This code configures the "Admin Dashboard" back button with its dark grey theme
        backButton = new JButton("Admin Dashboard");
        backButton.setBounds(35, 245, 270, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setBackground(new Color(30, 30, 30));
        backButton.setForeground(Color.white);
        backButton.addActionListener(this);
        loginBox.add(backButton);

        // This code centers the window on the screen and makes it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // This method determines the SSS deduction based on the employee's basic salary bracket
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

    // This method calculates the PhilHealth contribution (Employee share is 50% of total premium)
    private static double computePhilHealth(double basicSalary) {
        double monthlyPremium;
        
        if(basicSalary <= 10000) {
            monthlyPremium = 300.00;
        } else if(basicSalary >= 60000) {
            monthlyPremium = 1800.00;
        } else {
            monthlyPremium = basicSalary * 0.03; // 3% premium rate
        }
        double employeeShare = monthlyPremium * 0.50;
        return employeeShare;
    }

    // This method calculates the Pag-IBIG deduction with a maximum contribution ceiling of PHP 100
    private static double computePagIbig(double basicSalary) {
        double employeeRate;
        if(basicSalary >= 1000 && basicSalary <= 1500) {
            employeeRate = 0.01; // 1% rate
        } else {
            employeeRate = 0.02; // 2% rate
        }
        
        double contribution = basicSalary * employeeRate;
        
        if (contribution > 100) contribution = 100; // PHP 100 cap limit
        return contribution;
    }

    // This main method acts as the entry point to run the app
    public static void main(String[] args) {
        new SalaryComputationModule();
    }

    // This code handles the button click events (Compute and Back/Dashboard)
    @Override
    public void actionPerformed(ActionEvent e) {
        // This code triggers when the Compute button is clicked
        if (e.getSource() == salaryComputeButton) {
            String empID = searchText.getText().trim();

            // This code checks if the input field is empty
            if (empID.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an Employee ID!", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String EmpData = "EmployeeData.csv";
            String line = "";
            boolean ifFound = false;

            // This code opens and scans the CSV database to find the matching Employee ID
            try (BufferedReader br = new BufferedReader(new FileReader(EmpData))) {
                br.readLine(); // This line skips the CSV column header row

                while ((line = br.readLine()) != null) {
                    // This regex correctly splits the CSV line even if fields contain commas inside quotes
                    String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    String csvID = data[0].replaceAll("\"", "").trim();

                    // This code executes if the Employee ID is successfully found
                    if (csvID.equals(empID)) {
                        String employeeHrlyRate = data[18].replaceAll("\"", "").trim();
                        String employeeBasicSalaryStr = data[13].replaceAll("\"", "").replaceAll(",", "").trim();
                        double basicSalary = Double.parseDouble(employeeBasicSalaryStr);

                        // This code invokes the deduction computation methods
                        double sssDeduction = computeSSS(basicSalary);
                        double philHealthDeduction = computePhilHealth(basicSalary);
                        double pagIbigDeduction = computePagIbig(basicSalary);
                        
                        // This code calculates total deductions and computes the final net salary
                        double totalDeductions = sssDeduction + philHealthDeduction + pagIbigDeduction;
                        double netSalary = basicSalary - totalDeductions;

                        // This code structures the formatted summary message string
                        String details = "Employee Salary Details:\n\n"
                                + "Basic Salary:      PHP " + String.format("%,.2f", basicSalary) + "\n"
                                + "Hourly Rate:       PHP " + employeeHrlyRate + "\n\n"
                                + "--- Deductions ---\n"
                                + "SSS:               PHP " + String.format("%,.2f", sssDeduction) + "\n"
                                + "PhilHealth:        PHP " + String.format("%,.2f", philHealthDeduction) + "\n"
                                + "Pag-IBIG:          PHP " + String.format("%,.2f", pagIbigDeduction) + "\n"
                                + "Total Deductions:  PHP " + String.format("%,.2f", totalDeductions) + "\n\n"
                                + "Net Salary:        PHP " + String.format("%,.2f", netSalary);

                        // This code displays the complete summary inside an information dialog box
                        JOptionPane.showMessageDialog(frame, details, "Salary Computation", JOptionPane.INFORMATION_MESSAGE);
                        ifFound = true;
                        break;
                    }
                }

                // This code triggers if the loop finishes and no matching ID was found
                if (!ifFound) {
                    JOptionPane.showMessageDialog(frame, "Employee ID " + empID + " does not exist.", "Not Found", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e1) {
                // This code catches and displays unexpected system or file reading errors
                JOptionPane.showMessageDialog(frame, "Error reading employeeData: " + e1.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }

        // This code triggers when the Admin Dashboard/Back button is clicked
        } else if (e.getSource() == backButton) {
            frame.dispose(); // This code closes the current computation window
            new AdminDashboard(); // This code re-opens the primary Admin Dashboard
        }
    }
}