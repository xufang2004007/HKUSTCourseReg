package _interface;


import javax.swing.*;
import java.awt.*;
import _record.Course;

public class CourseDetailWindow extends JFrame{
	
	Course course;

	JPanel p;
	
	JLabel title;
	
	int width,height;
	
	JScrollPane sp;
	
	static final int labelWidth = 200, labelHeight = 20;
	
	CourseDetailWindow(Course C) {
		course = C;
		
		
		
		title = new JLabel(course.present()[0][0]);
		p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//title.setBounds(0,0,labelWidth,labelHeight);
		//width = labelWidth;
		//height = labelHeight;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		int count = 0;
		
		
		p.add(title, c);
		
		for (int i = 1; i<course.present().length; i++) {
			
			for (int j = 0; j<course.present()[i].length; j++) {
				JLabel temp = new JLabel(course.present()[i][j]);
		//		temp.setBounds(0,height,labelWidth,labelHeight);
				c.gridy ++;
				p.add(temp, c);
				count ++;
		//		height +=labelHeight;
			}
			if (count>20) {
				count = 0;
				c.gridx ++;
				c.gridy = 0;
			}
		}
	
	
		sp = new JScrollPane(p);
		this.add(sp);
		this.setSize(400,600);
		
	}
}
