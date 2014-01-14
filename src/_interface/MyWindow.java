package _interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import _record.Course;
import _record.CourseReader;
import _record.FileLocater;
import _processor.CourseReg;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyWindow extends JFrame{
	
	
	MyMenuBar menubar;
	JPanel mainP;
	int taskCount;
	int swapCount;
	
	class MyMenuBar extends JMenuBar{
		JMenu file;
		
		
		JMenuItem load;
		JMenuItem select;
		
		JLabel welcome;
		
		//JButton newTask;
		JButton newSwap;
		JButton update;
		
		JLabel note;
		
		JMenuBar ref = this;
		
		
		
		
		MyMenuBar(String version, String information) {
		/*	file = new JMenu("File");
			
			load = new JMenuItem("Load");
			select = new JMenuItem("Select");
			file.add(load);	
			file.add(select);
			select.addActionListener(new SelectCourseListener());
			this.add(file);
			*/
			
			welcome = new JLabel("Welcome to CourseReg v" + version + "!");
			welcome.setFont(new Font("SansSerif", Font.BOLD, 16));
			
			note = new JLabel(information);
	//		newTask = new JButton("Start Selecting Courses");
	//		newTask.addActionListener(new SelectCourseListener());
			
			newSwap = new JButton("Start Adding Courses");
			newSwap.addActionListener(new SwapCourseListener());
			
			update = new JButton("Update Course information");
			update.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					note.setText("Updating...");
					ref.repaint();
					Thread t = new Thread(new Runnable(){
						
						
						public void run() {
							newSwap.setEnabled(false);
							try{
								CourseReg.updateViaURL();	
								DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
								Calendar cal = Calendar.getInstance();
								note.setText("Course information updated at "+dateFormat.format(cal.getTime()));
								
							}
							catch (IOException e){	
							}
							newSwap.setEnabled(true);
						}
					});
					t.start();
				}
			});
			
			this.setLayout(new GridLayout(4,1));
			this.add(welcome);
			//this.add(newTask);
			this.add(newSwap);
			this.add(note);
			this.add(update);
		}
	}
	public void updateNote(String s){
		menubar.note.setText(s);
		repaint();
	}

	/*
	class SelectCourseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					SelectCourseWindow select = new SelectCourseWindow("Please select courses",null,null);
					select.setVisible(true);
					taskCount++;
					select.title = "Selection Task" + String.valueOf(taskCount);
					select.setTitle(select.title + "- Select course");
					select.setSize(650,450);
					
				}
			});
			
			t.start();
		}
	}
*/
	class SwapCourseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					SwapCourseWindow swap = new SwapCourseWindow();
					swap.setVisible(true);
					swapCount++;                                    
					swap.title = "Adding Task" + String.valueOf(swapCount);
					swap.setTitle(swap.title + "- ");
					swap.setSize(850,450);
					
				}
			});
			t.start();
		}
	}
	
	public MyWindow(String version, String information) {
		menubar = new MyMenuBar(version,information);
		
		
		
		mainP = new JPanel();
		mainP.setLayout(new BorderLayout());
		mainP.add(menubar, BorderLayout.NORTH);
		this.add(mainP);
		
	}
	
	
}
