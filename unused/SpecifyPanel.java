package _interface;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import _record.Course;
import _interface.Solution.SolutionImage;
import _processor.Register;

public class SpecifyPanel extends JPanel{
	JComboBox list;
	
	SolutionWindow s;
	Course[] listToProceed;
	boolean[][] statesToProceed;
	int courseNum;
	
	String[] names;
	
	SwapCourseWindow host;
	
	JPanel last;
	
	int credits;
	
	JLabel creditLabel;
	
	HashMap<String, JFrame> details;
	
	JButton detail;
	
	HashMap<String, CourseSpecifyWindow>  specifies;
	JButton specify;
	
	HashMap<String, CourseModifyWindow> modifies;
	
	JButton save;
	
	JButton next;
	
	JButton back;
	
	JPanel p;
	
	JPanel ref = this;
	
	Course[] courses;
	
	boolean[][] bs;
	
	
	SpecifyPanel(String[] selected, SwapCourseWindow caller, JPanel lastPage, boolean[][] BaseStates ) {
		
		courses = new Course[selected.length];
		bs = BaseStates;
		for (int i = 0; i<selected.length; i++) courses[i] = Course.list_code.get(selected[i]);
		
		names = selected;
		
		host = caller;
		
		last = lastPage;
		
		list = new JComboBox(names);
		
		for (int i = 0; i<names.length; i++) {
			credits += Course.list_code.get(names[i]).credit;
		}
		
		creditLabel = new JLabel("Total credits: " + String.valueOf(credits));
	
		
		details = new HashMap<String, JFrame>();
		specifies = new HashMap<String, CourseSpecifyWindow>();
		modifies = new HashMap<String, CourseModifyWindow>();
		
		//dummy, not shown+
		detail = new JButton("Course detail");
		detail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					public void run() {
						if (details.get((String) list.getSelectedItem()) == null) {
							details.put((String) list.getSelectedItem(), new CourseDetailWindow(Course.list_code.get((String) list.getSelectedItem())));
						}
						//details.get((String) list.getSelectedItem()).setSize(200,300);
						details.get((String) list.getSelectedItem()).setVisible(true);
						details.get((String) list.getSelectedItem()).setTitle((String) list.getSelectedItem() + "-Detail");
					}
				});
				t.start();
				
			}
		});
		
		for (int i = 0; i<selected.length; i++) {
			specifies.put(selected[i],new CourseSpecifyWindow(Course.list_code.get(selected[i])));
		}
		//System.out.println(selected[0]);
		
		
		
		specify = new JButton("Specify session");
				for (int i = 0; i<names.length; i++)
					modifies.put(names[i], new CourseModifyWindow(Course.list_code.get(names[i]),bs[i]));
				
			
		specify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					public void run() {
				/*		if (details.get((String) list.getSelectedItem()) == null) {
							details.put((String) list.getSelectedItem(), new CourseDetailWindow(Course.list_code.get((String) list.getSelectedItem())));
						}
						//details.get((String) list.getSelectedItem()).setSize(200,300);
						details.get((String) list.getSelectedItem()).setVisible(true);
						details.get((String) list.getSelectedItem()).setTitle((String) list.getSelectedItem() + "-Detail");
					*/	//above open the detail window;
					/*	
						if (specifies.get((String) list.getSelectedItem()) == null) {
							specifies.put((String) list.getSelectedItem(), new CourseSpecifyWindow(Course.list_code.get((String) list.getSelectedItem())));
						}
						specifies.get((String) list.getSelectedItem()).setVisible(true);
						specifies.get((String) list.getSelectedItem()).setTitle((String) list.getSelectedItem() + "-Specify");
						*/
						modifies.get((String) list.getSelectedItem()).setVisible(true);
						modifies.get((String) list.getSelectedItem()).setTitle((String) list.getSelectedItem()+ "-Modify");
		
					}
				});
				t.start();
			}
		});
		
		JButton save = new JButton("Save profile");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				JFileChooser fc = new JFileChooser(File.separator+"prf");
				


				fc.setCurrentDirectory(new File("courseInfo.ser"));
				fc.showSaveDialog(null);
				File f = fc.getSelectedFile();
				
				try{
				
					boolean[][] states = new boolean[courses.length][];
					
					for (int i = 0; i<courses.length; i++) {
						
						states[i] = modifies.get(courses[i].code).getLegalStates();
					}
					
					
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
					oos.writeObject(courses);
					oos.writeObject(states);
					oos.close();
				}
				catch (IOException ioe){
					System.err.println("IOException while trying to save the profile!");
				}
				
			}
		});
		
		
		JButton next = new JButton("Next->");
	/*	next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean[][] states = new boolean[courses.length][];
				
				for (int i = 0; i<courses.length; i++) {
					
					states[i] = specifies.get(courses[i].code).getStates();
				}
				
				//host.remove(ref);
			//	host.add(new TrialPanel(courses, states));
				//JPanel p = new JPanel();
				//p.add(new TrialPanel(courses,states).sol.sip);
				//host.add(p);
				//host.setSize(SolutionImage.FULL_WIDTH+20,SolutionImage. FULL_HEIGHT+100);
				new TrialWindow(courses,states,host,host.title).setVisible(true);
				host.setVisible(false);
				
			}
		});
		
		*/
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 s = new SolutionWindow();
				 s.setVisible(true);
				 s.setSize(800,400);
				 s.setTitle(host.title + "- Solution");
				Thread t = new Thread(new Runnable() {
					public void run() {
						courseNum = names.length;
						System.out.println(courseNum);
							//	System.out.println("I'm here");
							//System.out.println(ref.bc.length);
							listToProceed = new Course[courseNum];
							statesToProceed = new boolean[courseNum][];
							
							for (int i = 0; i<courseNum; i++) {
								listToProceed[i] = Course.list_code.get(names[i]);
								statesToProceed[i] = modifies.get(names[i]).getLegalStates();
							}
						
						
						Register.start(listToProceed, statesToProceed, courseNum, s,host.title);

						
					}
				});
				
				t.start();
			}
		});
		
		JButton back = new JButton("<-Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				host.remove(ref);
				host.add(last);
				host.setSize(650,450);
				host.setResizable(true);
				host.setTitle(host.title + "- Select course");
			}
		});
		
		
		
		
		p = new JPanel();
		
		
		
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		p.add(list ,c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		
		p.add(specify, c);
		
		
		c.gridy++;
		p.add(detail,c);
		
		c.gridy++;
		p.add(back, c);
		
		c.gridy++;
		p.add(next,c);
		c.gridy++;
		p.add(save, c);
		
		
	
		
		
		c.gridy++;
		//p.add(creditLabel, c);
		
		
		
		this.add(p);
		
		
		
		
		
	}
}
