package compression;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class TestAddingIndexSQLite {
	public static void main(String[] args) {
		File file = new File("C:\\Users\\Cameron\\Desktop\\CS132a\\MapReduce-project\\output\\part-r-00000.gz");
		readAndInsert(file);
	}
	
	public static void readAndInsert(File f) {
		String url = "jdbc:sqlite:C:\\Users\\Cameron\\sqlite\\sqlite3\\db\\test.db";
		String sql = "INSERT INTO testIndex VALUES(?, ?)";
		
		try {
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			InputStream in = new GZIPInputStream(new BufferedInputStream(new FileInputStream(f)));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			int i = 0;
			while (in.available() > 0 && i < 200) {
				//System.out.println(in.available());
//				System.out.println(reader.readLine());
				String line = reader.readLine();
				//System.out.println(line);
				String[] keyValue = line.split(" -> "); //splits into word and {docIDs=[indices]}
				String[] indices = keyValue[1].substring(1, keyValue[1].length()-1).split("],"); //splits {docIDs=[indices]}
				//into docID, [indices]
				for (String index : indices) {
					String[] units = index.split("="); //splits docID, indices into docID and [indices]
					stmt.setString(1, keyValue[0]);
					stmt.setString(2, units[0]);
					if (stmt.executeUpdate() != 0) {
						System.out.println("Entry added.");
					}
					else {
						System.out.println("Entry failed to be added.");
					}
//					String[] locations = units[1].split(","); //splits [indices] into one index at a time
//					for (String l:locations) {
//						l = l.replaceAll("\\[", ""); //Get pure numbers
//						l = l.replaceAll("\\]", ""); //Get pure numbers
//						l = l.replaceAll(" ", ""); //Get pure numbers
//						stmt.setString(1, keyValue[0]);
//						stmt.setString(2, units[0]);
//						stmt.setString(3, l);
//						
//						if (stmt.executeUpdate() != 0) {
//							System.out.println("Entry added.");
//						}
//						else {
//							System.out.println("Entry failed to be added.");
//						}
//						//System.out.println(l);
//					}
				}
				i++;
			}
			in.close();
			
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.out.println("Could not close statement!");
				e.printStackTrace();
			}
			
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Could not close connection!");
				e.printStackTrace();
			}
		}
		
		catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
