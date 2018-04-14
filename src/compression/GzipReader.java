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

public class GzipReader {
	public static void main(String[] args) {
		File file = new File("C:\\Users\\Cameron\\Desktop\\CS132a\\MapReduce-project\\output\\part-r-00000.gz");
		if (file.exists())
			read(file);
		else {
			try {
				throw new FileNotFoundException("The file listed does not exist.");
			}
			catch(FileNotFoundException e) {
				System.out.println("redundant error message is redundant error message...");
			}
		}
	}
	
	public static void read(File f) {
		try {
			InputStream in = new GZIPInputStream(new BufferedInputStream(new FileInputStream(f)));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			int i = 0;
			while (in.available() > 0 && i < 200) {
				//System.out.println(in.available());
//				System.out.println(reader.readLine());
				String line = reader.readLine();
				System.out.println(line);
				String[] keyValue = line.split(" -> "); //splits into word and {docIDs=[indices]}
				String[] indices = keyValue[1].substring(1, keyValue[1].length()-1).split("],"); //splits {docIDs=[indices]}
				//into docID, [indices]
				for (String index : indices) {
					String[] units = index.split("="); //splits docID, indices into docID and [indices]
					String[] locations = units[1].split(","); //splits [indices] into one index at a time
					for (String l:locations) {
						l = l.replaceAll("\\[", ""); //Get pure numbers
						l = l.replaceAll("\\]", ""); //Get pure numbers
						l = l.replaceAll(" ", ""); //Get pure numbers
						System.out.println(l);
					}
				}
				i++;
			}
			in.close();
		}
		
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
