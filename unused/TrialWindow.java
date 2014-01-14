package _interface;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import _record.Course;
import _interface.Solution.SolutionImage;
import _processor.Register;


public class TrialWindow extends JFrame{
	
	Solution sol;
	
	String title;
	
	Course[] bc;
	boolean[][] bs;
	
	int[] states;
	
	JButton trial;  
	
	JPanel p;
	
	JPanel p0;
	
	SwapCourseWindow previous;
	
	TrialWindow ref = this;
	
	
	JButton back;
	
	TrialWindow(Course[] courses, boolean[][] instates, SwapCourseWindow scw, String t) {
		bc = courses;
		bs = instates;
		
		previous = scw;
		
		title = t;
		
		this.setTitle(title);
		
		states = new int[bc.length];
		//System.out.println(bs.length);
		for (int i = 0; i<bc.length; i++) {
			int j = 0; 
	//		System.out.println("state# of course" + i + "is " + bc[i].state);
			while (j<bc[i].state && !bs[i][j]) j++;
			//if (j==0) j--;
			states[i] = j;
		}
		
		sol = new Solution(courses, states, courses.length, 0,title);
		
		trial = new JButton("Start new trial");
		trial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable(){
					public void run() {
						SelectCourseWindow select = new SelectCourseWindow("Please select courses to add",bc,bs);
						select.setVisible(true);
						
						select.title = ref.title;
						select.setTitle(select.title + "- Select courses to add");
						select.setSize(650,450);
					}
				});
				t.start();	 
			}
		});
		
		back = new JButton("Back to the profile");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ref.setVisible(false);
				previous.setVisible(true);
			}
		});
		
		
		
		p = new JPanel();
		p.setLayout(new GridLayout(1,2));
	
		//p.add(sol.si.full);
		
		p0 = new JPanel();
		p0.setLayout(new GridLayout(4,1));
		p0.add(trial);
		
		p.add(p0);
		
		this.setLayout(new BorderLayout());
		this.add(sol.sip);
		this.setSize(SolutionImage.FULL_WIDTH+20,SolutionImage. FULL_HEIGHT+100);
		this.add(trial, BorderLayout.SOUTH);
		this.add(back,BorderLayout.NORTH);
	//	this.add( sol. new SolutionImagePanel());
		
		
		
	}
	
	
	
}
