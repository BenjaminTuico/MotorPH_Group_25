import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI_Prac2 implements ActionListener{
	private static JFrame frame; 
    private static JPanel panel;
	private static JLabel userLabel;
	private static JTextField userText;
	private static JLabel passLabel;
	private static JPasswordField passText;
	private static JButton button;
	private static JLabel success;
	

	public static void main(String[] args) {
		
		//Frame is the window
		frame = new JFrame("MotorPH");
		//Panel is the layout
		panel = new JPanel();
		frame.setSize(350, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		panel.setLayout(null);
		
		userLabel = new JLabel("Username:");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165,25);
		panel.add(userText);
		
		passLabel = new JLabel("Password:");
		passLabel.setBounds(10, 50, 80, 25);
		panel.add(passLabel);
		
		passText = new JPasswordField(20);
		passText.setBounds(100, 50, 165, 25);
		panel.add(passText);
		
		button = new JButton("Login");
		button.setBounds(100, 80, 165, 25);
		button.addActionListener(new GUI_Prac2());
		panel.add(button);
		
		success = new JLabel("");
		success.setBounds(100, 110, 185, 25);
		panel.add(success);
		
		

		
		
		
		
		frame.setVisible(true);
		
		

		
		
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Code for the login menu
		String user = userText.getText();
		String password = passText.getText();
		System.out.println(user +", " + password);
		
		if(user.equals("admin") && password.equals("1234")) {
			frame.dispose();
			new AdminDashboard();
		} else if (user.equals("employee")&& password.equals("1234")){
				frame.dispose();
				new EmployeeDashboard();
				
		} else {
			success.setText("Wrong User & Password");
			return;
		}
		
	}

}
