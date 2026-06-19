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
import javax.swing.SwingConstants;

public class EmployeeDashboard implements ActionListener {
	private static JFrame frame;
	private static JPanel mainPanel;
	private static JPanel infoBox; 
	private static JLabel titleLabel;
	
	// Labels to display the actual data
	private static JLabel idLabel;
	private static JLabel nameLabel;
	private static JLabel birthdayLabel;
	private static JLabel positionLabel;
	private static JLabel statusLabel;
	private static JButton logoutButton;

	public EmployeeDashboard() {
		// 1. Window setup
		frame = new JFrame("Employee Dashboard");
		frame.setSize(450, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 2. Main panel setup
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		frame.add(mainPanel);
		
		// 3. Load background image (FIXED: Added missing semicolon at the end)
		ImageIcon bgIcon = new ImageIcon(GUI_Prac2.class.getResource("/motorph_bg3.jfif"));
		JLabel background = new JLabel(bgIcon);
		background.setBounds(0, 0, 450, 550);
		
		// 4. Center container box setup
		infoBox = new JPanel();
		infoBox.setBounds(45, 40, 340, 430);
		infoBox.setBackground(new Color(255, 255, 255, 220));
		infoBox.setLayout(null);
		mainPanel.add(infoBox);
		
		// 5. Dashboard header title
		titleLabel = new JLabel("My Profile", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setBounds(10, 30 ,320, 30);
		infoBox.add(titleLabel);
		
		// 6. Initialize UI Labels
		idLabel = new JLabel("Employee ID: ");
		idLabel.setFont(new Font("Arial", Font.BOLD, 14));
		idLabel.setBounds(40, 100, 260, 25);
		infoBox.add(idLabel);
		
		nameLabel = new JLabel("Name: ");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
		nameLabel.setBounds(40, 140, 260, 25);
		infoBox.add(nameLabel);
		
		birthdayLabel = new JLabel("Birthday: ");
		birthdayLabel.setFont(new Font("Arial", Font.BOLD, 14));
		birthdayLabel.setBounds(40, 180, 260, 25);
		infoBox.add(birthdayLabel);
		
		positionLabel = new JLabel("Position: ");
		positionLabel.setFont(new Font("Arial", Font.BOLD, 14));
		positionLabel.setBounds(40, 220, 260, 25);
		infoBox.add(positionLabel);
		
		statusLabel = new JLabel("Status: ");
		statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
		statusLabel.setBounds(40, 260, 260, 25);
		infoBox.add(statusLabel);
		
		// 7. Logout button
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(35, 340, 270, 40);
		logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
		logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
		logoutButton.setBackground(new Color(200, 50,50));
		logoutButton.setForeground(Color.WHITE);
		logoutButton.addActionListener(this);
		infoBox.add(logoutButton);
		
		// 8. Put background image at the very bottom layer
		mainPanel.add(background);
		mainPanel.setComponentZOrder(background, mainPanel.getComponentCount() - 1);
		
		// 9. Call the method to load default employee data
		loadEmployeeData("10001");
		
		// 10. Center window and make it visible
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	private void loadEmployeeData(String targetID) {
		String csvFile = "EmployeeData.csv";
		String line = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			br.readLine(); // Skip header line
			
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				String csvID = data[0].replaceAll("\"", "").trim();
				
				if (csvID.equals(targetID)) {
					String firstName = data[1].replaceAll("\"", "").trim();
					String lastName = data[2].replaceAll("\"", "").trim();
					String birthDay = data[3].replaceAll("\"", "").trim();
					String status = data[11].replaceAll("\"", "").trim();
					String position = data[12].replaceAll("\"", "").trim();

					
					
//					String status = data.length > 10 ? data[10].replaceAll("\"", "").trim() : "Regular";
//					
					// Update JLabels on screen with actual CSV values
					idLabel.setText("Employee ID: " + csvID);
					nameLabel.setText("Name: " + firstName + " " + lastName);
					birthdayLabel.setText("Birthday: " + birthDay);
					statusLabel.setText("Status: " + status);
					positionLabel.setText("Position: " + position);

					
					statusLabel.setForeground(new Color(40, 140, 40)); // Green color for active status
					return;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Error loading profile: " + e.getMessage());
		}
	} // FIXED: Cleared up the messy brackets here

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logoutButton) {
			frame.dispose();
			GUI_Prac2.main(null);
		}
	}
}
