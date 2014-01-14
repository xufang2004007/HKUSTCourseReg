package _interface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import _record.Course;
import _processor.Register;

public class ModifyPanel extends JPanel{
	
	JComboBox list;
	
	
	JButton detail,modify,prev,next;
	
	JPanel p;
	
	JPanel p0;
	
	String[] names;
	
	boolean[] states;
	
	//JFrame host;
	
	SelectCourseWindow host;
	
	JPanel last;
	
	ModifyPanel ref = this;
	
	int credits;
	
	JLabel creditLabel;
	
	HashMap<String, JFrame> details;
	HashMap<String, CourseModifyWindow> modifies;
	
	Course[] listToProceed;
	boolean[][] statesToProceed;
	int courseNum;
	
	SolutionWindow s;
	
	Course[] bc;
	boolean[][] bs;
	
	ModifyPanel(String[] selected, SelectCourseWindow caller, JPanel lastPage, Course[] baseCourses, boolean[][] baseStates ) {
		
		bc = baseCourses;
		
		bs = baseStates;
		
		names = selected;
		
		host = caller;
		
		last = lastPage;
		
		list = new JComboBox(names);
		
		for (int i = 0; i<names.length; i++) {
			credits += Course.list_code.get(names[i]).credit;
		}
		
		creditLabel = new JLabel("Total credits: " + String.valueOf(credits));
		
		details = new HashMap<String, JFrame>();
		modifies = new HashMap<String, CourseModifyWindow>();
		
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
		
		modify = new JButton("Modify");
		Thread t = new Thread(new Runnable(){
			public void run() {
				for (int i = 0; i<names.length; i++)
					modifies.put(names[i], new CourseModifyWindow(Course.list_code.get(names[i]),null));
				
			}
		});
		
		t.start();
		modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
						modifies.get((String) list.getSelectedItem()).setVisible(true);
						modifies.get((String) list.getSelectedItem()).setTitle((String) list.getSelectedItem()+ "-Modify");
			}
		});
	
		
		prev = new JButton("<-Prev");
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				host.remove(ref);
				host.add(last);
				host.setSize(650,450);
				host.setResizable(true);
				host.setTitle(host.title + "- Select course");
			}
		});
		
		
		next = new JButton("Next->");
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
						if (ref.bc == null) {
							listToProceed = new Course[courseNum];
							statesToProceed = new boolean[courseNum][];
							for (int i = 0; i<courseNum; i++) {
								listToProceed[i] = Course.list_code.get(names[i]);
								statesToProceed[i] = modifies.get(names[i]).getLegalStates();
							}
						
						}
						else {
						//	System.out.println("I'm here");
							//System.out.println(ref.bc.length);
							listToProceed = new Course[courseNum+ref.bc.length];
							statesToProceed = new boolean[courseNum+ref.bc.length][];
							
							for (int i = 0; i<courseNum; i++) {
								listToProceed[i] = Course.list_code.get(names[i]);
								statesToProceed[i] = modifies.get(names[i]).getLegalStates();
							}
						
							for (int i = 0; i<ref.bc.length; i++) {
								listToProceed[i+courseNum] = ref.bc[i];
								statesToProceed[i+courseNum] = ref.bs[i];
							//	System.out.println("here?");
							}
							courseNum = courseNum + ref.bc.length;
						}
						
						
						Register.start(listToProceed, statesToProceed, courseNum, s,host.title);

						
					}
				});
				
				t.start();
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
		
		p.add(detail, c);
		
		c.gridy++;
		p.add(modify, c);
		
		c.gridy++;
		p.add(prev, c);
		
		c.gridy++;
		p.add(next,c);
		
		c.gridy++;
	//	p.add(creditLabel, c);
		
		this.add(p);
		
	}
	
	
}
