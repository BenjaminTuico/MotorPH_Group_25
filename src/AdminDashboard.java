import java.awt.Color; 
import java.awt.Font;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.ImageIcon;
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
	private static JPanel loginBox; 
	private static JLabel dashboardTitle; 
	private static JLabel searchLabel;
	private static JTextField searchText;
	private static JButton searchButton;
	private static JButton logoutButton;

	public AdminDashboard(){
		// 1. Window setup
		frame = new JFrame("Admin Dashboard");
		frame.setSize(450, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 2. Main panel setup
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		frame.add(mainPanel);
		
		// 3. Load and set the background image
		ImageIcon bgIcon = new ImageIcon(GUI_Prac2.class.getResource("/motorph_bg3.jfif"));
		JLabel background = new JLabel(bgIcon);
		background.setBounds(0, 0, 450, 550);
		
		// 4. Center container box setup
		loginBox = new JPanel();
		loginBox.setBounds(45, 40, 340, 430);
		loginBox.setBackground(new Color(255, 255, 255, 220)); 
		loginBox.setLayout(null);
		mainPanel.add(loginBox);
		
		// 5. Dashboard header title
		dashboardTitle = new JLabel("Admin Panel", SwingConstants.CENTER);
		dashboardTitle.setFont(new Font("Arial", Font.BOLD, 22));
		dashboardTitle.setBounds(10, 30, 320, 30);
		loginBox.add(dashboardTitle);
		
		// 6. Search label setup
		searchLabel = new JLabel("Enter Employee ID:");
		searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
		searchLabel.setBounds(35, 110, 150, 20);
		loginBox.add(searchLabel); 
		
		// 7. Search text input field
		searchText = new JTextField();
		searchText.setBounds(35, 135, 270, 35); 
		loginBox.add(searchText); 
		
		// 8. Search button setup
		searchButton = new JButton("Search");
		searchButton.setBounds(35, 190, 270, 40);
		searchButton.setFont(new Font("Arial", Font.BOLD, 14));
		searchButton.setBackground(new Color(30, 30, 30)); 
		searchButton.setForeground(Color.WHITE);
		searchButton.addActionListener(this); 
		loginBox.add(searchButton); 
		
		// 9. Logout button setup
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(35, 245, 270, 40);
		logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
		logoutButton.setBackground(new Color(200, 50, 50)); 
		logoutButton.setForeground(Color.WHITE);
		logoutButton.addActionListener(this); 
		loginBox.add(logoutButton);
		
		// 10. Put background image at the very bottom layer
		mainPanel.add(background);
		mainPanel.setComponentZOrder(background, mainPanel.getComponentCount() - 1);
		
		// 11. Center window and make it visible
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		
		// Handle search logic
		if (e.getSource() == searchButton) {
			String inputID = searchText.getText().trim();
			
			// Simple validation check if input is empty
			if (inputID.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter an Employee ID.");
				return;
			}
			
			String csvFile = "EmployeeData.csv";
			String line = "";
			boolean ifFound = false;
			
			try (BufferedReader br = new BufferedReader (new FileReader(csvFile))){
				
				br.readLine(); // Skip the CSV header row
				
				while ((line = br.readLine()) != null) {
					String[] data = line.split(",");
					String csvID = data[0].replaceAll("\"", "").trim();
					
					// If ID matches, read details from CSV array indices
					if(csvID.equals(inputID)) {
						String employeeID = data[0].replaceAll("\"", "").trim();
						String firstName = data[1].replaceAll("\"", "").trim();
						String lastName = data[2].replaceAll("\"", "").trim();
						String birthDay = data[3].replaceAll("\"", "").trim();
						String employeeSSS = data[7].replaceAll("\"", "").trim();
						String employeePhilHealth = data[9].replaceAll("\"", "").trim();
						String employeeTIN = data[9].replaceAll("\"", "").trim();
						String employeePagIbig = data[10].replaceAll("\"", "").trim();
						String employeeStatus = data[11].replaceAll("\"", "").trim();
						String employeePosition = data[12].replaceAll("\"", "").trim();
						
						// Modern multi-line message view
						String details = "Employee Details:\n\n" +
										 "ID: " + employeeID + "\n" +
										 "Name: " + firstName + " " + lastName + "\n" +
										 "Birthday: " + birthDay + "\n" +
										 "SSS: " + employeeSSS + "\n" +
										 "PhilHealth #: " + employeePhilHealth + "\n" +
										 "TIN #: " + employeeTIN + "\n" +
										 "Pag-ibig #: " + employeePagIbig + "\n" +
										 "Status: " + employeeStatus + "\n" +
										 "Position: " + employeePosition + "\n"
										 ;
						
						JOptionPane.showMessageDialog(frame, details, "Success", JOptionPane.INFORMATION_MESSAGE);
						ifFound = true;
						break;
					}
				}
				
				// If loop completes without finding the ID
				if (!ifFound) {
					JOptionPane.showMessageDialog(frame, "Employee ID " + inputID + " does not exist.", "Not Found", JOptionPane.WARNING_MESSAGE);
				}
			
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error reading employeeData: " + ex.getMessage());
			}
		}
		
		// Handle logout logic
		if (e.getSource() == logoutButton) {
			frame.dispose(); // Close admin panel
			GUI_Prac2.main(null); // Open back login panel
		}
	} 
}