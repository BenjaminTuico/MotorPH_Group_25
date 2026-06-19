import java.awt.Color;
import java.awt.Font; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI_Prac2 implements ActionListener {
	private static JFrame frame; 
	private static JPanel mainPanel;
	private static JPanel loginBox; 
	private static JLabel titleLabel, subTitleLabel;
	private static JLabel userLabel;
	private static JTextField userText;
	private static JLabel passLabel;
	private static JPasswordField passText;
	private static JButton button;
	private static JLabel success;

	public static void main(String[] args) {
		
		// 1. Create the main window
		frame = new JFrame("MotorPH");
		frame.setSize(450, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		// 2. Create the background panel
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		frame.add(mainPanel);
		
		// 3. Load and set the background image 
		ImageIcon bgIcon = new ImageIcon(GUI_Prac2.class.getResource("/black-bg.jfif"));
		JLabel background = new JLabel(bgIcon);
		background.setBounds(0, 0, 450, 550); 
		
		// 4. Create the white login box container
		loginBox = new JPanel();
		loginBox.setBounds(45, 40, 340, 430);
		loginBox.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
		loginBox.setLayout(null);
		mainPanel.add(loginBox);
		
		// 5. Add header titles inside the login box
		titleLabel = new JLabel("Sign in to MotorPH", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22)); 
		titleLabel.setBounds(10, 30, 320, 30);
		loginBox.add(titleLabel);
		
		subTitleLabel = new JLabel("Enter your admin or employee credentials.", SwingConstants.CENTER);
		subTitleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		subTitleLabel.setForeground(Color.GRAY);
		subTitleLabel.setBounds(10, 65, 320, 20);
		loginBox.add(subTitleLabel);
		
		// 6. Username label and input field
		userLabel = new JLabel("Username:");
		userLabel.setBounds(35, 120, 80, 20);
		userLabel.setFont(new Font("Arial", Font.BOLD, 12)); 
		loginBox.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(35, 145, 270, 35); 
		loginBox.add(userText);
		
		// 7. Password label and input field
		passLabel = new JLabel("Password:");
		passLabel.setBounds(35, 200, 80, 20);
		passLabel.setFont(new Font("Arial", Font.BOLD, 12)); 
		loginBox.add(passLabel);
		
		passText = new JPasswordField(20);
		passText.setBounds(35, 225, 270, 35); 
		loginBox.add(passText);
		
		// 8. Login button and error message label
		button = new JButton("Login");
		button.setBounds(35, 290, 270, 40);
		button.setFont(new Font("Arial", Font.BOLD, 14)); 
		button.setBackground(new Color(30, 30, 30)); // Dark modern style
		button.setForeground(Color.WHITE);
		button.addActionListener(new GUI_Prac2());
		loginBox.add(button);
		
		success = new JLabel("", SwingConstants.CENTER);
		success.setFont(new Font("Arial", Font.BOLD, 12));
		success.setForeground(Color.RED);
		success.setBounds(35, 350, 270, 25);
		loginBox.add(success);
		
		// 9. Put background image at the very bottom layer
		mainPanel.add(background);
		mainPanel.setComponentZOrder(background, mainPanel.getComponentCount() - 1);

		// 10. Center the window on the screen and make it visible
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
		
	} 

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Get text from inputs and remove extra spaces (.trim)
		String user = userText.getText().trim();
		String password = passText.getText().trim();
		System.out.println(user + ", " + password);
		
		// Check user access levels
		if(user.equals("admin") && password.equals("1234")) {
			frame.dispose();
			new AdminDashboard();
		} else if (user.equals("employee") && password.equals("1234")){
			frame.dispose();
			new EmployeeDashboard();
		} else {
			success.setText("Wrong User & Password");
		}
	}
}