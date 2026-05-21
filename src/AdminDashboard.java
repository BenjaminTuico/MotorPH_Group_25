import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AdminDashboard implements ActionListener {
	private static JFrame frame;
	private static JLabel searchLabel;
	private static JTextField searchText;
	private static JButton searchButton;
	private static JButton logoutButton;

	public AdminDashboard(){
		frame = new JFrame("Admin Dashboard");
		frame.setLayout(null);
		frame.setSize(350, 250); 
		
		searchLabel = new JLabel("Enter Employee ID");
		searchLabel.setBounds(10, 20, 150, 25);
		frame.add(searchLabel);
		
		searchText = new JTextField();
		searchText.setBounds(130, 20, 150, 25);
		frame.add(searchText);
		
		searchButton = new JButton("Search");
		searchButton.setBounds(10, 70, 100, 30);
		searchButton.addActionListener(this); 
		frame.add(searchButton);
		
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(180, 70, 100, 30);
		logoutButton.addActionListener(this); 
		frame.add(logoutButton);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		
		//Code for reading EmployeeData.csv
		if (e.getSource() == searchButton) {
			
			String inputID = searchText.getText().trim();
			
			
			String csvFile = "EmployeeData.csv";
			String line = "";
			boolean ifFound = false;
			
			try (BufferedReader br = new BufferedReader (new FileReader(csvFile))){
				
				
				br.readLine();
				
				
				while ((line = br.readLine()) != null) {
					
					String[] data = line.split(",");
					
					
					String csvID = data[0].replaceAll("\"", "").trim();
					//On the csv/worksheet always starts from 0
					if(csvID.equals(inputID)) {
						String employeeID = data[0].replaceAll("\"", "").trim();
						String firstName = data[1].replaceAll("\"", "").trim();
						String lastName = data[2].replaceAll("\"", "").trim();
						String birthDay = data[3].replaceAll("\"", "").trim();
						
						JOptionPane.showMessageDialog(frame, "Employee Found: " + "ID: " + employeeID + " | " + firstName + " " + lastName + " | " + "Birthday: " + birthDay);
						ifFound = true;
						break;
					}
				}
			//Code for employee does not exist
				if (ifFound == false) {
					JOptionPane.showMessageDialog(frame, "Employee ID " + inputID + " does not exist.");
				}
			
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error reading employeeData: " + ex.getMessage());
			}
			
		}
		//Code for logout button
		if (e.getSource() == logoutButton) {
			GUI_Prac2.main(null);
			frame.dispose();
			
			
		}
		
	} 
}