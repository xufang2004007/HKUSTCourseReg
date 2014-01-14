package _processor;

import _record.*;
import _interface.MyWindow;
import java.io.*;

import javax.swing.*;
import java.util.*;

public class CourseReg {
	
	static final String path = "2014spring";
	static String version = "3.27c";
	static String information = "Updated on 14-Jan-2014";
	static String URL = "https://w5.ab.ust.hk/wcq/cgi-bin/1330/subject/";
	public static MyWindow myWindow;
	
	public static void updateViaURL() throws IOException{
		Course.init();
		
		CourseReader.mode = 1;
		for (int i = 0; i<FileLocater.SCHOOL_NUM; i++)
			for (int j = 0; j<FileLocater.list[i].deptNum; j++)
				CourseReader.readFromURL(URL+FileLocater.list[i].dept[j],i,j);
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("courseInfo.ser"));
		oos.writeObject(Course.courseCount);
		oos.writeObject(Course.list_code);
		oos.writeObject(Course.list);
		oos.close();
	}
	public static void main(String[] args)throws IOException {

/*	
		CourseReader.mode=0;
		for (int i = 0; i<FileLocater.SCHOOL_NUM; i++)
		for (int j = 0; j<FileLocater.list[i].deptNum; j++)
			CourseReader.readFromTXT(path+"/" + FileLocater.list[i].name + "/" + FileLocater.list[i].dept[j] + ".txt",i,j);


*/
		
		
		//updateViaURL();
	
		
	/*	
	BufferedReader in = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("LANG.html"))));
		CourseReader.mode = 2;
		
		Course c = CourseReader.getCourse(in);
		while (c!=null) {
			c.print();
			c = CourseReader.getCourse(in);
			
		}
		
*/

	
		
		try {
			ObjectInputStream ios = new ObjectInputStream(new FileInputStream("courseInfo.ser"));
			Course.courseCount = (int[][]) ios.readObject();
			Course.list_code =(HashMap<String, Course>) ios.readObject();
			Course.list = (Course[][][]) ios.readObject();
		}
		catch (ClassNotFoundException enfe) {
			System.err.println("class not found exception");
		}
	

		
		//	Course.list[0][0][0].print();
		//Course.list_code.get("MATH217").print();
		
		//CourseReader.readFromTXT("courseInfo/LANG/LANG100-199.txt", 4, 3);

	/*	for (int i = 0; i<Course.list[4][3].length; i++)
			Course.list[4][3][i].print();
		*/	//System.out.prinltn(Course.list[4][3][i].print())
		
	//		for (int i = 0; i<Course.courseCount; i++) Course.list[i].print();

		myWindow = new MyWindow(version,information);
		myWindow.setVisible(true);
		myWindow.setSize(300,150);
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myWindow.setTitle("CourseReg" + version);
		//Course[] selectedList = Course.list;
		//Register.start(selectedList, Course.courseCount);
			
	}
}

