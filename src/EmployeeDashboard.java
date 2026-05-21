import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class EmployeeDashboard implements ActionListener{
	public static JFrame frame;
	public static JLabel employeelabel;
	
	
	public EmployeeDashboard() {
		frame = new JFrame("Employee Dashboard");
		frame.setLayout(null);
		frame.setSize(350, 200);
		
		employeelabel = new JLabel("My Salary");
		employeelabel.setBounds(100, 10, 300, 50);
		frame.add(employeelabel);
		
		
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}
