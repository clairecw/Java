import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Properties;
import java.io.*;
import java.net.*;

public class FCT {
	public static String pickFile() {
		JFileChooser fileChooser = null;
		String fileName = null;
		String mediaDir = getMediaDirectory();
		
		try {
			File file = new File(mediaDir);
			if (file.exists()) fileChooser = new JFileChooser(file);
		} catch (Exception e) {}
		  
		if (fileChooser == null) fileChooser = new JFileChooser();
		  
		fileName = pickPath(fileChooser);
		return fileName;
	}		
			
	public static String pickImageFile() {
		JFileChooser fileChooser = null;
		String fileName = null;
		String mediaDir = getMediaDirectory();
		
		try {
			File file = new File(mediaDir);
			if (file.exists()) fileChooser = new JFileChooser(file);
		} catch (Exception e) {}
		  
		if (fileChooser == null) fileChooser = new JFileChooser();
		  
		FileNameExtensionFilter filter = new FileNameExtensionFilter("pictures: jpg, gif, png", "jpg", "gif", "png");
		fileChooser.setFileFilter(filter);
		
		fileName = pickPath(fileChooser);
		return fileName;
	}
	
	public static String pickPath(JFileChooser fileChooser) {
		String path = "";
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		
		int returnVal = fileChooser.showOpenDialog(frame);
		 
		if (returnVal == JFileChooser.APPROVE_OPTION)
		  path = fileChooser.getSelectedFile().getPath();
		return path;
    }
    
    public static String getMediaDirectory() {
		String directory = null;
		boolean done = false;
		File dirFile = null;
		
		try {
			Class currClass = Class.forName("FileChooser");
			URL classURL = currClass.getResource("FileChooser.class");
			URL fileURL = new URL(classURL,"../images/");
			directory = fileURL.getPath();
			directory = URLDecoder.decode(directory, "UTF-8");
			dirFile = new File(directory);
			if (dirFile.exists()) {
				return directory;
			}
		} catch (Exception ex) {}
		
		return directory;
	}
  
}