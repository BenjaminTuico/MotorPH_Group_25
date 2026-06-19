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

public class UpdateEmployee implements ActionListener {
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
	
	private static JLabel totalEmployeesValueLabel;
	private static JLabel activeEmployeesValueLabel;
	private static String lastDeletedLine = null; 

	public UpdateEmployee() {
		frame = new JFrame("Manage Employee Data");
		frame.setSize(900, 650); // BINAGO: Itinaas ang vertical size upang magkasya ang mga bagong government ID fields
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(245, 245, 250));
		frame.add(mainPanel);
		
		updateEmployeeSidePanel();
		updateEmployeeCenterPanel();
		buildRightPanel();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void updateEmployeeSidePanel() {
		sidePanel = new JPanel();
		sidePanel.setBounds(0, 0, 220, 650); // BINAGO: Inadjust ang taas batay sa frame size
		sidePanel.setBackground(new Color(232, 168, 56)); 
		sidePanel.setLayout(null);
		mainPanel.add(sidePanel);
		
		adminDashboardButton = new JButton("Admin Dashboard");
		adminDashboardButton.setBounds(20, 550, 180, 38); // BINAGO: Inibaba ang pwesto ng button
		adminDashboardButton.setFont(new Font("Arial", Font.BOLD, 15));
		adminDashboardButton.setOpaque(true);
		adminDashboardButton.setBorderPainted(false);
		adminDashboardButton.setFocusPainted(false);
		adminDashboardButton.setContentAreaFilled(false);
		adminDashboardButton.setForeground(Color.WHITE);
		adminDashboardButton.addActionListener(this);
		sidePanel.add(adminDashboardButton);
	}
	
	private void updateEmployeeCenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setBounds(220, 0, 420, 650); // BINAGO: Inadjust ang taas batay sa frame size
		centerPanel.setBackground(new Color(245, 245, 250));
		centerPanel.setLayout(null);
		mainPanel.add(centerPanel);
		
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

		JPanel searchCard = new JPanel();
		searchCard.setBounds(20, 70, 380, 95);
		searchCard.setBackground(Color.WHITE);
		searchCard.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 235), 1));
		searchCard.setLayout(null);
		centerPanel.add(searchCard);

		deleteButton = new JButton("Delete ID");
		deleteButton.setBounds(280, 30, 120, 20); 
		deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
		deleteButton.setForeground(new Color(200, 50, 50)); 
		deleteButton.setOpaque(false);
		deleteButton.setContentAreaFilled(false);
		deleteButton.setBorderPainted(false);
		deleteButton.setFocusPainted(false);
		deleteButton.addActionListener(this);
		searchCard.add(deleteButton);

		undoButton = new JButton("Undo");
		undoButton.setBounds(280, 10, 120, 20);
		undoButton.setFont(new Font("Arial", Font.PLAIN, 12));
		undoButton.setForeground(Color.GRAY); 
		undoButton.setOpaque(false);
		undoButton.setContentAreaFilled(false);
		undoButton.setBorderPainted(false);
		undoButton.setFocusPainted(false);
		undoButton.setEnabled(false); 
		undoButton.addActionListener(this);
		searchCard.add(undoButton);

		JLabel searchLabel = new JLabel("Employee ID:");
		searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
		searchLabel.setForeground(new Color(100, 100, 120));
		searchLabel.setBounds(15, 15, 100, 20);
		searchCard.add(searchLabel);

		searchText = new JTextField();
		searchText.setBounds(15, 58, 280, 25);
		searchText.setFont(new Font("Arial", Font.PLAIN, 13));
		searchText.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(232, 168, 56)));
		searchText.setBackground(Color.WHITE);
		searchCard.add(searchText);

		searchButton = new JButton("Search");
		searchButton.setBounds(305, 55, 70, 30);
		searchButton.setFont(new Font("Arial", Font.PLAIN, 10));
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
		
		nameText = new JTextField();
		lastText = new JTextField();
		birthdayText = new JTextField();
		salaryText = new JTextField();
		
		// DAGDAG: In-initialize ang mga bagong government ID textfields
		sssText = new JTextField();
		philHealthText = new JTextField();
		tinText = new JTextField();
		pagIbigText = new JTextField();
		
		String[] statusOptions = {"", "Regular", "Probationary"};
		statusChoice = new JComboBox<>(statusOptions);
		statusChoice.setSelectedIndex(0); 
		
		String[] positionOptions = {
			"", "Chief Executive Officer", "Chief Operating Officer", "Chief Financial Officer", 
			"Chief Marketing Officer", "IT Operations and Systems", "HR Manager", "HR Team Leader", 
			"HR Rank and File", "Accounting Head", "Payroll Manager", "Payroll Team Leader", "Payroll Rank and File", "Account Team Leader",
			"Account Rank and File", "Sales & Marketing", "Supply Chain and Logistics", "Customer Service and Relations"
		};
		positionChoice = new JComboBox<>(positionOptions);
		positionChoice.setSelectedIndex(0);
		
		// -------------------------------------------------------------------------
		// ROW 1: Names and Birthday
		// -------------------------------------------------------------------------
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

		// -------------------------------------------------------------------------
		// ROW 2: Position, Status, and Salary
		// -------------------------------------------------------------------------
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

		// -------------------------------------------------------------------------
		// DAGDAG ROW 3: SSS, PhilHealth, and TIN Fields
		// -------------------------------------------------------------------------
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

		// -------------------------------------------------------------------------
		// DAGDAG ROW 4: Pag-IBIG Field (At isinunod ang control buttons)
		// -------------------------------------------------------------------------
		JLabel pagIbigLabel = new JLabel("Pag-IBIG Number:");
		pagIbigLabel.setFont(new Font("Arial", Font.BOLD, 11));
		pagIbigLabel.setForeground(new Color(70, 70, 85));
		pagIbigLabel.setBounds(20, 384, 115, 15);
		centerPanel.add(pagIbigLabel);
		
		pagIbigText.setBounds(20, 402, 115, 28);
		pagIbigText.setFont(new Font("Arial", Font.PLAIN, 12));
		pagIbigText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
		centerPanel.add(pagIbigText);
		
		// Inayos ang posisyon ng Save Changes at Add Employee sa ibabang bahagi ng frame
		saveButton = new JButton("Save Changes");
		saveButton.setBounds(20, 465, 180, 45);
		saveButton.setFont(new Font("Arial", Font.BOLD, 13));
		saveButton.setBackground(new Color(39, 174, 96)); 
		saveButton.setForeground(Color.WHITE);
		saveButton.setOpaque(true);
		saveButton.setBorderPainted(false);
		saveButton.addActionListener(this);
		centerPanel.add(saveButton);

		addButton = new JButton("Add Employee");
		addButton.setBounds(215, 465, 180, 45);
		addButton.setFont(new Font("Arial", Font.BOLD, 13));
		addButton.setBackground(new Color(60, 161, 195)); 
		addButton.setForeground(Color.WHITE);
		addButton.setOpaque(true);
		addButton.setBorderPainted(false);
		addButton.addActionListener(this);
		centerPanel.add(addButton);
	}

	private void clearFields() {
		searchText.setText(""); 
		nameText.setText("");
		lastText.setText("");
		birthdayText.setText("");
		salaryText.setText("");
		statusChoice.setSelectedIndex(0);
		positionChoice.setSelectedIndex(0);
		
		// DAGDAG: Nililinis din ang gov ID fields kapag may binurang entry o nag reset
		sssText.setText("");
		philHealthText.setText("");
		tinText.setText("");
		pagIbigText.setText("");
	}
	
	private void buildRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setBounds(640, 0, 260, 650); // BINAGO: Inadjust ang taas batay sa frame size
		rightPanel.setBackground(new Color(255, 255, 255));
		rightPanel.setLayout(null);
		mainPanel.add(rightPanel);

		JLabel rightTitle = new JLabel("System Info");
		rightTitle.setFont(new Font("Arial", Font.BOLD, 15));
		rightTitle.setForeground(new Color(30, 30, 45));
		rightTitle.setBounds(20, 25, 220, 25);
		rightPanel.add(rightTitle);

		totalEmployeesValueLabel = addInfoCard("Total Employees", "0", new Color(255, 107, 107), 65);
		activeEmployeesValueLabel = addInfoCard("Active", "0", new Color(39, 174, 96), 155);
		
		addInfoCard("Positions", "12", new Color(60, 161, 195), 245);
		addInfoCard("Departments", "6", new Color(232, 168, 56), 335);

		updateSystemInfoCounters();
	}

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

	private void updateSystemInfoCounters() {
		String csvFile = "EmployeeData.csv";
		int totalCount = 0;
		int activeCount = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			br.readLine(); 
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue;
				}

				totalCount++; 

				String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
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

		if (totalEmployeesValueLabel != null) {
			totalEmployeesValueLabel.setText(String.valueOf(totalCount));
		}
		if (activeEmployeesValueLabel != null) {
			activeEmployeesValueLabel.setText(String.valueOf(activeCount));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String csvFile = "EmployeeData.csv"; 
		
		// =========================================================================
		// ACTION HANDLER FOR ADD EMPLOYEE BUTTON
		// =========================================================================
		if (e.getSource() == addButton) {
			String inputID = searchText.getText().trim();
			String firstName = nameText.getText().trim();
			String lastName = lastText.getText().trim();
			String birthday = birthdayText.getText().trim();
			String position = positionChoice.getSelectedItem().toString(); 
			String status = statusChoice.getSelectedItem().toString();
			String salary = salaryText.getText().trim();
			
			String sss = sssText.getText().trim();
			String philhealth = philHealthText.getText().trim();
			String tin = tinText.getText().trim();
			String pagibig = pagIbigText.getText().trim();

			if (inputID.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please fill out Employee ID, First Name, and Last Name fields.", "Missing Data", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
				br.readLine(); 
				String line;
				while ((line = br.readLine()) != null) {
					String[] splitData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					if (splitData.length > 0) {
						String existingID = splitData[0].replaceAll("\"", "").trim();
						if (existingID.equals(inputID)) {
							JOptionPane.showMessageDialog(frame, "Employee ID " + inputID + " already exists in the database!", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error performing verification scan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String[] newRow = new String[19]; 
			for (int i = 0; i < newRow.length; i++) {
				newRow[i] = "\"\""; 
			}

			newRow[0] = "\"" + inputID + "\"";
			newRow[1] = "\"" + lastName + "\"";
			newRow[2] = "\"" + firstName + "\"";
			newRow[3] = "\"" + birthday + "\"";
			
			
			newRow[6] = "\"" + sss + "\"";
			newRow[7] = "\"" + philhealth + "\"";
			newRow[8] = "\"" + tin + "\"";
			newRow[9] = "\"" + pagibig + "\"";
			
			newRow[10] = "\"" + status + "\"";
			newRow[11] = "\"" + position + "\"";
			newRow[13] = "\"" + salary + "\"";

			String combinedCsvLine = String.join(",", newRow);

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
				bw.write(combinedCsvLine);
				bw.newLine();
				bw.flush();

				JOptionPane.showMessageDialog(frame, "New employee successfully registered to database!", "Success", JOptionPane.INFORMATION_MESSAGE);
				clearFields();

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
		
		
		// ACTION HANDLER: SEARCH BUTTON
		if(e.getSource() == searchButton) {
			String inputID = searchText.getText().trim();

			if (inputID.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter an Employee ID.");
				clearFields();
				return;
			}
			String line = "";
			boolean ifFound = false;

			try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
				br.readLine(); 

				while ((line = br.readLine()) != null) {
					String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					String csvID = data[0].replaceAll("\"", "").trim();

					if (csvID.equals(inputID)) {
						nameText.setText(data[2].replaceAll("\"","").trim());
						nameText.setFont(new Font("Arial", Font.PLAIN, 12));
						
						lastText.setText(data[1].replaceAll("\"","").trim());       
						lastText.setFont(new Font("Arial", Font.PLAIN, 12));
						
						birthdayText.setText(data[3].replaceAll("\"","").trim());   
						birthdayText.setFont(new Font("Arial", Font.PLAIN, 12));
						
						
						if (data.length > 9) {
							sssText.setText(data[6].replaceAll("\"", "").trim());
							philHealthText.setText(data[7].replaceAll("\"", "").trim());
							tinText.setText(data[8].replaceAll("\"", "").trim());
							pagIbigText.setText(data[9].replaceAll("\"", "").trim());
							
							sssText.setFont(new Font("Arial", Font.PLAIN, 12));
							philHealthText.setFont(new Font("Arial", Font.PLAIN, 12));
							tinText.setFont(new Font("Arial", Font.PLAIN, 12));
							pagIbigText.setFont(new Font("Arial", Font.PLAIN, 12));
						}
						
						String currentPosition = data[11].replaceAll("\"","").trim();
						positionChoice.setSelectedItem(currentPosition);
						positionChoice.setFont(new Font("Arial", Font.PLAIN, 12));
						
						String currentStatus = data[10].replaceAll("\"","").trim();
						if(currentStatus.equalsIgnoreCase("Probationary")) {
							statusChoice.setSelectedItem("Probationary");
						} else if(currentStatus.equalsIgnoreCase("Regular")) {
							statusChoice.setSelectedItem("Regular");
						} else {
							statusChoice.setSelectedIndex(0);
						}
						statusChoice.setFont(new Font("Arial", Font.PLAIN, 12));
						
						salaryText.setText(data[13].replaceAll("\"","").trim());
						salaryText.setFont(new Font("Arial", Font.PLAIN, 12));

						ifFound = true;
						break;
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
		
		
		// ACTION HANDLER: SAVE CHANGES BUTTON	
		if(e.getSource() == saveButton) {
			String inputID = searchText.getText().trim();
			
			if(inputID.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please search and select an employee first.");
				return;
			}
			
			int confirm = JOptionPane.showConfirmDialog(frame,
					"Are you sure you want to save the updated changes to the database?", 
					"Confirm Updates", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(confirm != JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(frame, "Changes discarded.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			List<String> fileMemory = new ArrayList<>();
			String line = "";
			boolean updated = false;
			
			try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
				String header = br.readLine();
				if(header != null) {
					fileMemory.add(header);
				}
				
				while((line = br.readLine()) != null) {
					String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					String csvID = data[0].replaceAll("\"", "").trim();
					
					if(csvID.equals(inputID)) {
						data[1] = "\"" + lastText.getText().trim() + "\"";
						data[2] = "\"" + nameText.getText().trim() + "\"";
						data[3] = "\"" + birthdayText.getText().trim() + "\"";
						
						
						data[6] = "\"" + sssText.getText().trim() + "\"";
						data[7] = "\"" + philHealthText.getText().trim() + "\"";
						data[8] = "\"" + tinText.getText().trim() + "\"";
						data[9] = "\"" + pagIbigText.getText().trim() + "\"";
						
						data[10] = "\"" + statusChoice.getSelectedItem().toString() + "\""; 
						data[11] = "\"" + positionChoice.getSelectedItem().toString() + "\""; 
						data[13] = "\"" + salaryText.getText().trim() + "\"";
						
						line = String.join(",", data);
						updated = true;
					}
					fileMemory.add(line);
				} 
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error reading memory track: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
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

		// ACTION HANDLER: ADMIN DASHBOARD BUTTON
		if (e.getSource() == adminDashboardButton) {
			frame.dispose();
			new AdminDashboard();
		}

		
		// ACTION HANDLER: DELETE EMPLOYEE BUTTON
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

			try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
				String header = br.readLine();
				if (header != null) fileMemory.add(header); 

				while ((line = br.readLine()) != null) {
					String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					String csvID = data[0].replaceAll("\"", "").trim();

					if (csvID.equals(inputID)) {
						lastDeletedLine = line; 
						ifFound = true;
						continue; 
					}
					fileMemory.add(line);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
			}

			if (ifFound) {
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
					for (String memoryLine : fileMemory) {
						bw.write(memoryLine);
						bw.newLine();
					}
					bw.flush(); 
					
					JOptionPane.showMessageDialog(frame, "Employee record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
					clearFields();
					
					undoButton.setEnabled(true);
					undoButton.setForeground(new Color(39, 174, 96)); 
					
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

		
		// ACTION HANDLER: UNDO BUTTON

		if (e.getSource() == undoButton) {
			if (lastDeletedLine == null) {
				JOptionPane.showMessageDialog(frame, "Nothing to undo.", "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
				bw.write(lastDeletedLine);
				bw.newLine();
				bw.flush(); 
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error restoring data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			JOptionPane.showMessageDialog(frame, "Undo successful! Employee record restored.", "Restored", JOptionPane.INFORMATION_MESSAGE);
			
			lastDeletedLine = null;
			undoButton.setEnabled(false);
			undoButton.setForeground(Color.GRAY);
			
			new javax.swing.Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					updateSystemInfoCounters();
				}
			}).start();
		}
	}
	
	public static void main(String[] args) {
		new UpdateEmployee();
	}
}