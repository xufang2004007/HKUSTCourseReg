package _interface;


import javax.swing.*;
import _record.Course;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import _record.FileLocater;

public class SelectCourseWindow extends JFrame{
	JList courseList;
	JList selectedList;
	JScrollPane coursePane;
	JScrollPane selectedPane;
	DefaultListModel selectModel,courseModel;
	
	JButton select;
	JButton remove;
	JButton next;
	JButton cancel;
	
	String[] names;
	String[] collected;
	
	HashMap<String, Object> states;
	
	JList schoolList;
	JScrollPane schoolPane;
	int chosenSchool;
	
	JList deptList;
	JScrollPane deptPane;
	DefaultListModel schoolModel, deptModel;
	
	JButton enterSchool, enterDept;
	
	
	public String title;
	
	JPanel p,p1,p2,p3,p0;
	
	SelectCourseWindow ref = this;
	
	String display;
	
	JLabel info;
	
	Course[] bc;
	boolean[][] bs;
	
	public SelectCourseWindow(String display,Course[] baseCourses, boolean[][] baseStates) {
		//names = new String[Course.courseCount];

		//for (int i = 0; i<Course.courseCount; i++) names[i] = Course.list[i].getCode();
	
		bc = baseCourses;
		bs = baseStates;
		
		
		title = this.getTitle();
		this.setTitle(title + "-- Select course");
		
		schoolModel = new DefaultListModel();
		for (int i = 0; i<FileLocater.SCHOOL_NUM; i++) schoolModel.addElement(FileLocater.list[i]);
		
		schoolList = new JList(schoolModel);
		schoolList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "First Character"));
		
		schoolPane = new JScrollPane(schoolList);
		schoolPane.setSize(100,80);
		schoolPane.setBorder(null);
		
		enterSchool = new JButton("Enter");
		enterSchool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					public void run() {
						if (schoolList.getSelectedValue()!=null) {
							deptModel.clear();
							for (int i = 0; i<((FileLocater)schoolList.getSelectedValue()).deptNum; i++) {
								deptModel.addElement(((FileLocater)schoolList.getSelectedValue()).dept[i]);
							}
							chosenSchool = schoolList.getSelectedIndex();
						}
					}
				});
				
				t.start();
			}
		});
		
		
		deptModel = new DefaultListModel();
		
		deptList = new JList(deptModel);
		deptList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Department List"));
		
		deptPane = new JScrollPane(deptList);
		deptPane.setSize(100, 80);
		deptPane.setBorder(null);
		
		enterDept = new JButton("Enter");
		enterDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Thread t = new Thread(new Runnable() {
					public void run() {
						if (deptList.getSelectedValue()!=null) {
							courseModel.clear();
							
							//System.out.println(Course.list[chosenSchool][deptList.getSelectedIndex()].length);
							for (int i = 0; i<Course.courseCount[chosenSchool][deptList.getSelectedIndex()]; i++){
								
								courseModel.addElement(Course.list[chosenSchool][deptList.getSelectedIndex()][i].code +"  " + Course.list[chosenSchool][deptList.getSelectedIndex()][i].name);
								//System.out.println(Course.list[chosenSchool][deptList.getSelectedIndex()][i].code);
								
							}
							
							coursePane.repaint();
								
						}
					}
				});
				
				t.start();
				
			}
		});
		
		
		
		
		courseModel = new DefaultListModel();
		
		courseList = new JList(courseModel);
	//	for (int i = 0; i<Course.courseCount; i++) courseModel.addElement(names[i]);
		courseList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Course List"));
		
		coursePane = new JScrollPane(courseList);
		coursePane.setSize(300,200);
		coursePane.setBorder(null);
		
		selectModel = new DefaultListModel();
		selectedList = new JList(selectModel);
		selectedList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Selected List"));
		
		selectedPane = new JScrollPane(selectedList);
		selectedPane.setSize(300,200);
		selectedPane.setBorder(null);
		
		states = new HashMap<String, Object>();
		
		select = new JButton("select -->");
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseList.getSelectedValue()!= null) {
					if (states.get(courseList.getSelectedValue()) == null) {
						states.put((String)courseList.getSelectedValue(), new Object());
						selectModel.addElement((String)courseList.getSelectedValue());
					}
				}
			}
		});
		
		remove = new JButton("<-- remove");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedList.getSelectedValue()!= null) {
				
						states.remove((String)selectedList.getSelectedValue());
						selectModel.remove(selectedList.getSelectedIndex());
					
				}
			}
			
		});
		
		next = new JButton("Next");
		next.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e) {
				ref.remove(p0);
				collected = new String[selectModel.getSize()];
				for (int i = 0; i<selectModel.getSize(); i++) collected[i] = new String((String)selectModel.elementAt(i));
				ref.add(new ModifyPanel(collected,ref,p0,bc,bs));
				ref.setSize(230,180);
				ref.setTitle(title+ "- modify");
				ref.setResizable(false);

				ref.repaint();
			}
		
		});
		
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ref.setVisible(false);
			}
		});
		
		info  = new JLabel(display);
		info.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		
		p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(schoolPane);
		p1.add(enterSchool, BorderLayout.SOUTH);
		
		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(deptPane);
		p2.add(enterDept, BorderLayout.SOUTH);
		
		p3 = new JPanel();
		p3.setLayout(new GridLayout(2,1));
		p3.add(p1);
		p3.add(p2);
		
		
		p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 4;
		p.add(coursePane, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		p.add(select, c);
		
		c.gridy = 1;
		p.add(remove, c);
		c.gridy = 2;
		p.add(next, c);
		c.gridy = 3;
		p.add(cancel, c);
		
		
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 4;
		p.add(selectedPane, c);
		
		p0 = new JPanel();
		p0.setLayout(new BorderLayout());
		p0.add(p3, BorderLayout.WEST);
		p0.add(info, BorderLayout.NORTH);
		p0.add(p);
		
		
		this.add(p0);

	}
}

