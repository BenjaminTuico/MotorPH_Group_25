import java.io.BufferedReader;
import java.io.FileReader;

public class EmployeeSearch {
	public String[] findEmployee(String id) {
		String path ="EmployeeData.csv";
		String line = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader ("EmployeeData.csv"))){
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				if (values[0].equals(id)) {
					return values;
				}
			}
		} catch (Exception e) {
			System.out.println("Error reading CSV: " + e.getMessage());
		}return null;

	}	
}