import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// ActionListener interface allows this class to handle button clicks
public class UpdateEmployee implements ActionListener {
	// --- GUI Components Declarations ---
	private static JFrame frame;
	private static JPanel mainPanel;
	private static JPanel sidePanel;
	private static JPanel centerPanel;
	private static JPanel rightPanel; 
	private static JTextField searchText;
	private static JTextField nameText;       
	private static JTextField salaryText;
	private static JTextField birthdayText;
	private static JTextField lastText;
	private static JTextField sssText;
	private static JTextField philHealthText;
	private static JTextField tinText;
	private static JTextField pagIbigText;
	private static JButton saveButton;
	private static JButton searchButton;
	private static JButton deleteButton;
	private static JButton undoButton;
	private static JButton adminDashboardButton;
	private static JComboBox<String> statusChoice;
	private static JComboBox<String> positionChoice; 
	private static JButton addButton;
	private static JButton clearAllButton; // New button component declaration for clearing inputs
	
	// --- Dashboard Counters & Temporary Memory ---
	private static JLabel totalEmployeesValueLabel;
	private static JLabel activeEmployeesValueLabel;
	private static String lastDeletedLine = null; // Stores the last deleted row for the Undo feature

	// Constructor: This runs automatically when the object is created
	public UpdateEmployee() {
		// Create the main window container
		frame = new JFrame("Manage Employee Data");
		frame.setSize(900, 650); // Set window width to 900px and height to 650px
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close program when window X is clicked
		frame.setResizable(false); // Disable window resizing to keep layout intact
		
		// Create the base background panel
		mainPanel = new JPanel();
		mainPanel.setLayout(null); // Use absolute positioning (coordinates) for manual item placement
		mainPanel.setBackground(new Color(245, 245, 250)); // Soft gray-blue background
		frame.add(mainPanel); // Attach base panel to window frame
		
		// Build the sub-panels (Layout split into 3 vertical zones)
		updateEmployeeSidePanel();   // Left column: Navigation
		updateEmployeeCenterPanel(); // Middle column: Main data entry forms
		buildRightPanel();           // Right column: Live database metrics
		
		frame.setLocationRelativeTo(null); // Center the application window on screen
		frame.setVisible(true); // Reveal the window frame to the user
	}
	
	// Layout Method: Builds the left sidebar navigation column
	private void updateEmployeeSidePanel() {
		sidePanel = new JPanel();
		sidePanel.setBounds(0, 0, 220, 650); // Position at left edge (0,0), width 220px, full height
		sidePanel.setBackground(new Color(232, 168, 56)); // Accent gold color
		sidePanel.setLayout(null);
		mainPanel.add(sidePanel);
		
		// Configure Admin Dashboard Return Navigation button
		adminDashboardButton = new JButton("Admin Dashboard");
		adminDashboardButton.setBounds(20, 550, 180, 38); 
		adminDashboardButton.setFont(new Font("Arial", Font.BOLD, 15));
		adminDashboardButton.setOpaque(true);
		adminDashboardButton.setBorderPainted(false);
		adminDashboardButton.setFocusPainted(false);
		adminDashboardButton.setContentAreaFilled(false);
		adminDashboardButton.setForeground(Color.WHITE);
		adminDashboardButton.addActionListener(this); // Tell Java to look at actionPerformed() when clicked
		sidePanel.add(adminDashboardButton);
	}
	
	// Layout Method: Builds the middle column containing the search card and entry input boxes
	private void updateEmployeeCenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setBounds(220, 0, 420, 650); // Placed next to sidebar, width 420px
		centerPanel.setBackground(new Color(245, 245, 250));
		centerPanel.setLayout(null);
		mainPanel.add(centerPanel);
		
		// Section Titles
		JLabel headerTitle = new JLabel("Update Employee");
		headerTitle.setFont(new Font("Arial", Font.BOLD, 22));
		headerTitle.setForeground(new Color(30, 30, 45));
		headerTitle.setBounds(25, 25, 300, 30);
		centerPanel.add(headerTitle);

		JLabel headerSub = new JLabel("Manage employee data");
		headerSub.setFont(new Font("Arial", Font.PLAIN, 12));
		headerSub.setForeground(new Color(150, 150, 170));
		headerSub.setBounds(25, 52, 300, 20);
		centerPanel.add(headerSub);

		// White box container representing the Search section
		JPanel searchCard = new JPanel();
		searchCard.setBounds(20, 70, 380, 95);
		searchCard.setBackground(Color.WHITE);
		searchCard.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 235), 1));
		searchCard.setLayout(null);
		centerPanel.add(searchCard);

		// Button to trigger profile deletion
		deleteButton = new JButton("Delete ID");
		deleteButton.setBounds(280, 35, 100, 20); // Adjusted boundary to avoid overlaps
		deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
		deleteButton.setForeground(new Color(200, 50, 50)); // Crimson color for warning actions
		deleteButton.setOpaque(false);
		deleteButton.setContentAreaFilled(false);
		deleteButton.setBorderPainted(false);
		deleteButton.setFocusPainted(false);
		deleteButton.addActionListener(this);
		searchCard.add(deleteButton);

		// Button to restore the last deleted row
		undoButton = new JButton("Undo");
		undoButton.setBounds(280, 10, 100, 20); // Adjusted boundary to avoid overlaps
		undoButton.setFont(new Font("Arial", Font.PLAIN, 12));
		undoButton.setForeground(Color.GRAY); 
		undoButton.setOpaque(false);
		undoButton.setContentAreaFilled(false);
		undoButton.setBorderPainted(false);
		undoButton.setFocusPainted(false);
		undoButton.setEnabled(false); // Disabled by default until a deletion occurs
		undoButton.addActionListener(this);
		searchCard.add(undoButton);

		JLabel searchLabel = new JLabel("Employee ID:");
		searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
		searchLabel.setForeground(new Color(100, 100, 120));
		searchLabel.setBounds(15, 15, 100, 20);
		searchCard.add(searchLabel);

		// Input box where user types the target ID to search or edit
		searchText = new JTextField();
		searchText.setBounds(15, 55, 260, 25); // Slightly shortened to give the Search button more room
		searchText.setFont(new Font("Arial", Font.PLAIN, 13));
		// Flat design underline look
		searchText.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(232, 168, 56)));
		searchText.setBackground(Color.WHITE);
		searchCard.add(searchText);

		// Button to initiate CSV scanning logic
		searchButton = new JButton("Search");
		searchButton.setBounds(285, 52, 85, 30); // Expanded width from 70 to 85 so "Search" text is fully readable
		searchButton.setFont(new Font("Arial", Font.BOLD, 12)); // Enhanced font style for clarity
		searchButton.setOpaque(true);
		searchButton.setBorderPainted(false);
		searchButton.setBackground(new Color(232, 168, 56));
		searchButton.setForeground(Color.WHITE);
		searchButton.addActionListener(this);
		searchCard.add(searchButton);

		JLabel actionsLabel = new JLabel("Employee Information");
		actionsLabel.setFont(new Font("Arial", Font.BOLD, 15));
		actionsLabel.setForeground(new Color(30, 30, 45));
		actionsLabel.setBounds(25, 178, 200, 25);
		centerPanel.add(actionsLabel);
		
		// Initialize input text field components
		nameText = new JTextField();
		lastText = new JTextField();
		birthdayText = new JTextField();
		salaryText = new JTextField();
		sssText = new JTextField();
		philHealthText = new JTextField();
		tinText = new JTextField();
		pagIbigText = new JTextField();
		
		// Dropdown menu settings for Employment Status
		String[] statusOptions = {"", "Regular", "Probationary"};
		statusChoice = new JComboBox<>(statusOptions);
		statusChoice.setSelectedIndex(0); 
		
		// Dropdown menu settings for Company Job Positions
		String[] positionOptions = {
			"", "Chief Executive Officer", "Chief Operating Officer", "Chief Financial Officer", 
			"Chief Marketing Officer", "IT Operations and Systems", "HR Manager", "HR Team Leader", 
			"HR Rank and File", "Accounting Head", "Payroll Manager", "Payroll Team Leader", "Payroll Rank and File", "Account Team Leader",
			"Account Rank and File", "Sales & Marketing", "Supply Chain and Logistics", "Customer Service and Relations"
		};
		positionChoice = new JComboBox<>(positionOptions);
		positionChoice.setSelectedIndex(0);
		
		// --- ROW 1 DESIGN: Name Info and Birthday Layout Placement ---
		JLabel fNameLabel = new JLabel("First Name:");
		fNameLabel.setFont(new Font("Arial", Font.BOLD, 11));
		fNameLabel.setForeground(new Color(70, 70, 85));
		fNameLabel.setBounds(20, 210, 115, 15);
		centerPanel.add(fNameLabel);
		
		nameText.setBounds(20, 228, 115, 28);
		nameText.setFont(new Font("Arial", Font.PLAIN, 12));
		nameText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(nameText);

		JLabel mNameLabel = new JLabel("Last Name:");
		mNameLabel.setFont(new Font("Arial", Font.BOLD, 11));
		mNameLabel.setForeground(new Color(70, 70, 85));
		mNameLabel.setBounds(150, 210, 115, 15);
		centerPanel.add(mNameLabel);
		
		lastText.setBounds(150, 228, 115, 28);
		lastText.setFont(new Font("Arial", Font.PLAIN, 12));
		lastText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(lastText);

		JLabel lNameLabel = new JLabel("Birthday: mm/dd/yyyy");
		lNameLabel.setFont(new Font("Arial", Font.BOLD, 11));
		lNameLabel.setForeground(new Color(70, 70, 85));
		lNameLabel.setBounds(280, 210, 115, 15);
		centerPanel.add(lNameLabel);
		
		birthdayText.setBounds(280, 228, 115, 28);
		birthdayText.setFont(new Font("Arial", Font.PLAIN, 12));
		birthdayText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(birthdayText);

		// --- ROW 2 DESIGN: Professional Information Placement ---
		JLabel posLabel = new JLabel("Position:");
		posLabel.setFont(new Font("Arial", Font.BOLD, 11));
		posLabel.setForeground(new Color(70, 70, 85));
		posLabel.setBounds(20, 268, 115, 15);
		centerPanel.add(posLabel);
		
		positionChoice.setBounds(20, 286, 115, 28);
		positionChoice.setFont(new Font("Arial", Font.PLAIN, 12));
		positionChoice.setBackground(Color.WHITE);
		centerPanel.add(positionChoice);

		JLabel statLabel = new JLabel("Status:");
		statLabel.setFont(new Font("Arial", Font.BOLD, 11));
		statLabel.setForeground(new Color(70, 70, 85));
		statLabel.setBounds(150, 268, 115, 15);
		centerPanel.add(statLabel);
		
		statusChoice.setBounds(150, 286, 115, 28);
		statusChoice.setFont(new Font("Arial", Font.PLAIN, 12));
		statusChoice.setBackground(Color.WHITE);
		centerPanel.add(statusChoice);

		JLabel salLabel = new JLabel("Basic Salary:");
		salLabel.setFont(new Font("Arial", Font.BOLD, 11));
		salLabel.setForeground(new Color(70, 70, 85));
		salLabel.setBounds(280, 268, 115, 15);
		centerPanel.add(salLabel);
		
		salaryText.setBounds(280, 286, 115, 28);
		salaryText.setFont(new Font("Arial", Font.PLAIN, 12));
		salaryText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(salaryText);

		// --- ROW 3 DESIGN: Government Identity Numbers Placement ---
		JLabel sssLabel = new JLabel("SSS Number:");
		sssLabel.setFont(new Font("Arial", Font.BOLD, 11));
		sssLabel.setForeground(new Color(70, 70, 85));
		sssLabel.setBounds(20, 326, 115, 15);
		centerPanel.add(sssLabel);
		
		sssText.setBounds(20, 344, 115, 28);
		sssText.setFont(new Font("Arial", Font.PLAIN, 12));
		sssText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(sssText);

		JLabel philHealthLabel = new JLabel("PhilHealth No:");
		philHealthLabel.setFont(new Font("Arial", Font.BOLD, 11));
		philHealthLabel.setForeground(new Color(70, 70, 85));
		philHealthLabel.setBounds(150, 326, 115, 15);
		centerPanel.add(philHealthLabel);
		
		philHealthText.setBounds(150, 344, 115, 28);
		philHealthText.setFont(new Font("Arial", Font.PLAIN, 12));
		philHealthText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(philHealthText);

		JLabel tinLabel = new JLabel("TIN Number:");
		tinLabel.setFont(new Font("Arial", Font.BOLD, 11));
		tinLabel.setForeground(new Color(70, 70, 85));
		tinLabel.setBounds(280, 326, 115, 15);
		centerPanel.add(tinLabel);
		
		tinText.setBounds(280, 344, 115, 28);
		tinText.setFont(new Font("Arial", Font.PLAIN, 12));
		tinText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(tinText);

		// --- ROW 4 DESIGN: Secondary Government Metrics Placement ---
		JLabel pagIbigLabel = new JLabel("Pag-IBIG Number:");
		pagIbigLabel.setFont(new Font("Arial", Font.BOLD, 11));
		pagIbigLabel.setForeground(new Color(70, 70, 85));
		pagIbigLabel.setBounds(20, 384, 115, 15);
		centerPanel.add(pagIbigLabel);
		
		pagIbigText.setBounds(20, 402, 115, 28);
		pagIbigText.setFont(new Font("Arial", Font.PLAIN, 12));
		pagIbigText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(pagIbigText);
		
		// --- FIXED THREE-BUTTON ROW DESIGN ---
		// Pinalawak ang lapad ng mga button at ginawang 11-point Font Size para magkasya nang maayos ang text nang hindi pumuputol
		saveButton = new JButton("Save Changes");
		saveButton.setBounds(20, 465, 120, 45); // Fixed: Width adjusted to 120px to fit full title text cleanly
		saveButton.setFont(new Font("Arial", Font.BOLD, 11));
		saveButton.setBackground(new Color(39, 174, 96)); // Green color for successful save actions
		saveButton.setForeground(Color.WHITE);
		saveButton.setOpaque(true);
		saveButton.setBorderPainted(false);
		saveButton.addActionListener(this);
		centerPanel.add(saveButton);

		// Button to insert a brand new row entry into the CSV database
		addButton = new JButton("Add Employee");
		addButton.setBounds(150, 465, 120, 45); // Fixed: Width adjusted to 120px to fit full title text cleanly
		addButton.setFont(new Font("Arial", Font.BOLD, 11));
		addButton.setBackground(new Color(60, 161, 195)); // Calm blue color for adding data
		addButton.setForeground(Color.WHITE);
		addButton.setOpaque(true);
		addButton.setBorderPainted(false);
		addButton.addActionListener(this);
		centerPanel.add(addButton);
		
		// Button to reset/clean all form input values instantly
		clearAllButton = new JButton("Clear All");
		clearAllButton.setBounds(280, 465, 115, 45); // Placed next to Add Employee button, width 115px
		clearAllButton.setFont(new Font("Arial", Font.BOLD, 11));
		clearAllButton.setBackground(new Color(120, 120, 135)); // Neutral dark gray color for cleaning actions
		clearAllButton.setForeground(Color.WHITE);
		clearAllButton.setOpaque(true);
		clearAllButton.setBorderPainted(false);
		clearAllButton.addActionListener(this); // Listen for clicks to invoke clearing logic
		centerPanel.add(clearAllButton);
	}

	// Utility Method: Wipes out text inside entry components to make them clean again
	private void clearFields() {
		searchText.setText(""); 
		nameText.setText("");
		lastText.setText("");
		birthdayText.setText("");
		salaryText.setText("");
		statusChoice.setSelectedIndex(0);
		positionChoice.setSelectedIndex(0);
		
		sssText.setText("");
		philHealthText.setText("");
		tinText.setText("");
		pagIbigText.setText("");
	}
	
	// Layout Method: Builds right panel column showing statistical info boxes
	private void buildRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setBounds(640, 0, 260, 650); 
		rightPanel.setBackground(new Color(255, 255, 255));
		rightPanel.setLayout(null);
		mainPanel.add(rightPanel);

		JLabel rightTitle = new JLabel("System Info");
		rightTitle.setFont(new Font("Arial", Font.BOLD, 15));
		rightTitle.setForeground(new Color(30, 30, 45));
		rightTitle.setBounds(20, 25, 220, 25);
		rightPanel.add(rightTitle);

		// Create stat cards with vertical stack layout configurations (Y coordinate shifts down)
		totalEmployeesValueLabel = addInfoCard("Total Employees", "0", new Color(255, 107, 107), 65);
		activeEmployeesValueLabel = addInfoCard("Active", "0", new Color(39, 174, 96), 155);
		
		addInfoCard("Positions", "12", new Color(60, 161, 195), 245);
		addInfoCard("Departments", "6", new Color(232, 168, 56), 335);

		updateSystemInfoCounters(); // Automatically run the file counter during interface startup
	}

	// Reusable Helper Method: Quickly draws styled info cards with title and a large metric number
	private JLabel addInfoCard(String title, String value, Color accent, int y) {
		JPanel card = new JPanel();
		card.setBounds(15, y, 230, 75);
		card.setBackground(Color.WHITE);
		// Left color border accent styling
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

		return valueLabel; // Return the label so we can change its number text later
	}

	// Business Logic Method: Reads CSV file to determine real-time employment stat counters
	private void updateSystemInfoCounters() {
		String csvFile = "EmployeeData.csv";
		int totalCount = 0;
		int activeCount = 0;

		// Open the file with automatic error control (try-with-resources)
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			br.readLine(); // Skip the very first header line of the CSV sheet
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue; // Ignore blank lines to avoid counting empty rows
				}
				totalCount++; 

				// Smart splits columns using comma but avoids breaking inside words containing quotation marks
				String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				if (data.length > 10) {
					String status = data[10].replaceAll("\"", "").trim();
					// An employee is marked active if status reads Regular or Probationary
					if (status.equalsIgnoreCase("Regular") || status.equalsIgnoreCase("Probationary")) {
						activeCount++;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Counter System Scanning Error: " + ex.getMessage());
		}

		// Push findings onto the graphic layout cards if components exist
		if (totalEmployeesValueLabel != null) {
			totalEmployeesValueLabel.setText(String.valueOf(totalCount));
		}
		if (activeEmployeesValueLabel != null) {
			activeEmployeesValueLabel.setText(String.valueOf(activeCount));
		}
	}

	// Auto-Increment Logic: Looks at existing profiles to calculate the next unique ID sequence
	private int generateNextEmployeeID(String csvFilePath) {
		int maxID = 10000; // Database baseline minimum sequence starter
		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
			br.readLine(); // Skip header structure
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				
				String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				if (data.length > 0) {
					try {
						int currentID = Integer.parseInt(data[0].replaceAll("\"", "").trim());
						if (currentID > maxID) {
							maxID = currentID; // Keep track of the highest ID found
						}
					} catch (NumberFormatException nfe) {
						// Ignore corrupted non-numeric sequences so system does not crash
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Error calculating auto-increment sequence: " + ex.getMessage());
		}
		return maxID + 1; // Return the new ID (highest ID + 1)
	}

	// Data Validation Logic: Inspects syntax formatting rules before committing file writes
	private boolean validateInputs(String birthday, String salary, String sss, String philHealth, String tin, String pagibig) {
		StringBuilder errorMessages = new StringBuilder(); // Collects errors to display all at once

		// 1. Birthday Check: Verifies mm/dd/yyyy formatting structure matches exactly
		String birthdayRegex = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$";
		if (!birthday.matches(birthdayRegex)) {
			errorMessages.append("- Invalid Birthday Format! Use mm/dd/yyyy (e.g., 11/09/2003).\n");
		}

		// 2. Salary Check: Accepts full digits (e.g., 10000) or comma separated financial notations (e.g., 10,000)
		String salaryRegex = "^\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?$|^\\d+(\\.\\d{1,2})?$";
		if (!salary.matches(salaryRegex)) {
			errorMessages.append("- Invalid Salary! Full numbers only are allowed (e.g., 10000 or 10,000).\n");
		}

		// 3. SSS Check: Checks if number pattern matches the example format
		if (!sss.isEmpty() && !sss.matches("^[0-9\\-]+$")) {
			errorMessages.append("- Invalid SSS Number! Must contain something like this ' 44-4506057-3 '.\n");
		}

		// 4. PhilHealth Check: Only allows numbers and dashes
		if (!philHealth.isEmpty() && !philHealth.matches("^[0-9\\-]+$")) {
			errorMessages.append("- Invalid PhilHealth Number! Must contain numbers and dashes only.\n");
		}

		// 5. TIN Check: Checks if format matches the standard company layout pattern
		if (!tin.isEmpty() && !tin.matches("^[0-9\\-]+$")) {
			errorMessages.append("- Invalid TIN Number! Must contain something like this ' 442-605-657-000 '.\n");
		}

		// 6. Pag-IBIG Check: Only allows numbers and dashes
		if (!pagibig.isEmpty() && !pagibig.matches("^[0-9\\-]+$")) {
			errorMessages.append("- Invalid Pag-Ibig Number! Must contain numbers and dashes only.\n");
		}
		
		// If errors were accumulated, cancel save actions and show errors in a pop-up window
		if (errorMessages.length() > 0) {
			JOptionPane.showMessageDialog(frame, 
					"Please correct the following errors before saving:\n\n" + errorMessages.toString(), 
					"Validation Errors Detected", 
					JOptionPane.WARNING_MESSAGE);
			return false; // Validation failed
		}
		
		return true; // Validation passed successfully!
	}

	// Main Controller Event Link: This runs whenever any UI button is clicked
	@Override
	public void actionPerformed(ActionEvent e) {
		String csvFile = "EmployeeData.csv"; 
		
		// --- BUTTON CLICK LOGIC: ADD EMPLOYEE ---
		if (e.getSource() == addButton) {
			// Extract all values typed by the user in the form textfields
			String firstName = nameText.getText().trim();
			String lastName = lastText.getText().trim();
			String birthday = birthdayText.getText().trim();
			String position = positionChoice.getSelectedItem().toString(); 
			String status = statusChoice.getSelectedItem().toString();
			String salary = salaryText.getText().trim();
			String sss = sssText.getText().trim();
			String philHealth = philHealthText.getText().trim();
			String tin = tinText.getText().trim();
			String pagibig = pagIbigText.getText().trim();

			// Basic presence verification check
			if (firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || salary.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please fill out required fields (First Name, Last Name, Birthday, Salary).", "Missing Data", JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Run data format rule check; stops if format validation fails
			if (!validateInputs(birthday, salary, sss, philHealth , tin, pagibig)) {
				return;
			}

			// Generate the new unique sequence ID
			int nextID = generateNextEmployeeID(csvFile);
			String generatedIDStr = String.valueOf(nextID);

			// Setup an empty 19-column database record line row array map
			String[] newRow = new String[19]; 
			for (int i = 0; i < newRow.length; i++) {
				newRow[i] = "\"\""; // Pre-fill with empty quotes to prevent null column errors
			}

			// Map inputs to their correct column positions inside the CSV schema array
			newRow[0] = "\"" + generatedIDStr + "\"";
			newRow[1] = "\"" + lastName + "\"";
			newRow[2] = "\"" + firstName + "\"";
			newRow[3] = "\"" + birthday + "\"";
			newRow[6] = "\"" + sss + "\"";
			newRow[7] = "\"" + philHealth + "\"";
			newRow[8] = "\"" + tin + "\"";
			newRow[9] = "\"" + pagibig + "\"";
			newRow[10] = "\"" + status + "\"";
			newRow[11] = "\"" + position + "\"";
			newRow[13] = "\"" + salary + "\"";

			// Join all array columns into a single comma-separated CSV line string
			String combinedCsvLine = String.join(",", newRow);

			// Append the new row to the end of the CSV file
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
				bw.write(combinedCsvLine);
				bw.newLine();
				bw.flush();

				JOptionPane.showMessageDialog(frame, "New employee successfully registered!\nGenerated Employee ID: " + generatedIDStr, "Success", JOptionPane.INFORMATION_MESSAGE);
				clearFields();

				// Trigger an interface stat card recount delay timer loop
				new javax.swing.Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						updateSystemInfoCounters();
					}
				}).start();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error writing new record entry: " + ex.getMessage(), "File IO Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// --- BUTTON CLICK LOGIC: SEARCH ---
		if(e.getSource() == searchButton) {
			String inputID = searchText.getText().trim();

			if (inputID.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter an Employee ID.");
				clearFields();
				return;
			}

			// Only allow numeric lookups to avoid crash vulnerabilities
			if (!inputID.matches("^\\d+$")) {
				JOptionPane.showMessageDialog(frame, "Invalid ID format! Employee ID must contain digits only.", "Validation Error", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String line = "";
			boolean ifFound = false;

			// Scan the file row by row to find a matching ID number
			try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
				br.readLine(); // Skip header line

				while ((line = br.readLine()) != null) {
					String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					String csvID = data[0].replaceAll("\"", "").trim();

					// If match is found, populate textfields with the database column values
					if (csvID.equals(inputID)) {
						nameText.setText(data[2].replaceAll("\"","").trim());
						lastText.setText(data[1].replaceAll("\"","").trim());       
						birthdayText.setText(data[3].replaceAll("\"","").trim());   
						
						if (data.length > 9) {
							sssText.setText(data[6].replaceAll("\"", "").trim());
							philHealthText.setText(data[7].replaceAll("\"", "").trim());
							tinText.setText(data[8].replaceAll("\"", "").trim());
							pagIbigText.setText(data[9].replaceAll("\"", "").trim());
						}
						
						String currentPosition = data[11].replaceAll("\"","").trim();
						positionChoice.setSelectedItem(currentPosition);
						
						String currentStatus = data[10].replaceAll("\"","").trim();
						if(currentStatus.equalsIgnoreCase("Probationary")) {
							statusChoice.setSelectedItem("Probationary");
						} else if(currentStatus.equalsIgnoreCase("Regular")) {
							statusChoice.setSelectedItem("Regular");
						} else {
							statusChoice.setSelectedIndex(0);
						}
						
						salaryText.setText(data[13].replaceAll("\"","").trim());

						ifFound = true; // Mark as found
						break; // Stop scanning the file
					}
				}

				if (!ifFound) {
					JOptionPane.showMessageDialog(frame, "Employee ID " + inputID + " does not exist.", "Not Found", JOptionPane.WARNING_MESSAGE);					
					clearFields();
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// --- BUTTON CLICK LOGIC: SAVE CHANGES ---
		if(e.getSource() == saveButton) {
			String inputID = searchText.getText().trim();
			
			if(inputID.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please search and select an employee first.");
				return;
			}

			String birthday = birthdayText.getText().trim();
			String salary = salaryText.getText().trim();
			String sss = sssText.getText().trim();
			String philHealth = philHealthText.getText().trim();
			String tin = tinText.getText().trim();
			String pagibig = pagIbigText.getText().trim();

			// Run input checks before saving edits
			if (!validateInputs(birthday, salary, sss, philHealth, tin, pagibig)) {
				return;
			}
			
			int confirm = JOptionPane.showConfirmDialog(frame,
					"Are you sure you want to save the updated changes to the database?", 
					"Confirm Updates", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(confirm != JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(frame, "Changes discarded.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			List<String> fileMemory = new ArrayList<>(); // Temporarily stores the entire file in RAM
			String line = "";
			boolean updated = false;
			
			// Step 1: Read the entire file and update the matching row in memory (RAM)
			try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
				String header = br.readLine();
				if(header != null) {
					fileMemory.add(header);
				}
				
				while((line = br.readLine()) != null) {
					String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					String csvID = data[0].replaceAll("\"", "").trim();
					
					if(csvID.equals(inputID)) {
						// Overwrite columns with the modified values from the textfields
						data[1] = "\"" + lastText.getText().trim() + "\"";
						data[2] = "\"" + nameText.getText().trim() + "\"";
						data[3] = "\"" + birthday + "\"";
						data[6] = "\"" + sssText.getText().trim() + "\"";
						data[7] = "\"" + philHealthText.getText().trim() + "\"";
						data[8] = "\"" + tinText.getText().trim() + "\"";
						data[9] = "\"" + pagIbigText.getText().trim() + "\"";
						data[10] = "\"" + statusChoice.getSelectedItem().toString() + "\""; 
						data[11] = "\"" + positionChoice.getSelectedItem().toString() + "\""; 
						data[13] = "\"" + salary + "\"";
						
						line = String.join(",", data); // Rebuild the comma-separated line
						updated = true;
					}
					fileMemory.add(line); // Store line in our memory list
				} 
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error reading memory track: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Step 2: Overwrite the CSV file with the updated memory list
			if(updated) {
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
					for(String memoryLine : fileMemory) {
						bw.write(memoryLine);
						bw.newLine();
					}
					bw.flush(); 
					JOptionPane.showMessageDialog(frame, "Employee data has been successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
					
					new javax.swing.Timer(100, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							updateSystemInfoCounters();
						}
					}).start();
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(frame, "Error overwriting CSV database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} 
		} 

		// --- BUTTON CLICK LOGIC: ADMIN DASHBOARD NAVIGATION ---
		if (e.getSource() == adminDashboardButton) {
			frame.dispose(); // Close current window screen frame container
			new AdminDashboard(); // Open the main dashboard form class file window
		}

		// --- BUTTON CLICK LOGIC: DELETE EMPLOYEE ---
		if (e.getSource() == deleteButton) {
			String inputID = searchText.getText().trim();

			if (inputID.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please type an Employee ID first before deleting.", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(frame, 
					"Are you sure you want to delete Employee ID " + inputID + "?", 
					"Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm != JOptionPane.YES_OPTION) {
				return;
			}

			List<String> fileMemory = new ArrayList<>();
			String line = "";
			boolean ifFound = false;

			// Read file and skip the line matching the target ID to delete it
			try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
				String header = br.readLine();
				if (header != null) fileMemory.add(header); 

				while ((line = br.readLine()) != null) {
					String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					String csvID = data[0].replaceAll("\"", "").trim();

					if (csvID.equals(inputID)) {
						lastDeletedLine = line; // Save the line to RAM so the Undo feature can restore it
						ifFound = true;
						continue; // Skip adding this line to fileMemory (deletes it)
					}
					fileMemory.add(line);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
			}

			// Write the filtered list back to the CSV file
			if (ifFound) {
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
					for (String memoryLine : fileMemory) {
						bw.write(memoryLine);
						bw.newLine();
					}
					bw.flush(); 
					
					JOptionPane.showMessageDialog(frame, "Employee record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
					clearFields();
					
					// Enable the Undo button since a profile was successfully deleted
					undoButton.setEnabled(true);
					undoButton.setForeground(new Color(39, 174, 96)); // Turn Undo button green
					
					new javax.swing.Timer(100, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							updateSystemInfoCounters();
						}
					}).start();
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Error overwriting database: " + ex.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Employee ID " + inputID + " not found.", "Not Found", JOptionPane.ERROR_MESSAGE);
			}
		}

		// --- BUTTON CLICK LOGIC: UNDO DELETION ---
		if (e.getSource() == undoButton) {
			if (lastDeletedLine == null) {
				JOptionPane.showMessageDialog(frame, "Nothing to undo.", "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Append the saved 'lastDeletedLine' back into the end of the CSV file
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
				bw.write(lastDeletedLine);
				bw.newLine();
				bw.flush(); 
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error restoring data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			JOptionPane.showMessageDialog(frame, "Undo successful! Employee record restored.", "Restored", JOptionPane.INFORMATION_MESSAGE);
			
			// Reset temporary memory tracking variables back to empty states
			lastDeletedLine = null;
			undoButton.setEnabled(false); // Disable Undo button again
			undoButton.setForeground(Color.GRAY); // Turn Undo button back to gray
			
			new javax.swing.Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					updateSystemInfoCounters();
				}
			}).start();
		}
		
		// --- BUTTON CLICK LOGIC: CLEAR ALL ---
		if (e.getSource() == clearAllButton) {
			clearFields(); // Directly invoke the utility method to wipe out all inputs
		}
	}
	
	// Main Execution Entry Point Method
	public static void main(String[] args) {
		new UpdateEmployee(); // Instantiates and launches the graphical layout application
	}
}