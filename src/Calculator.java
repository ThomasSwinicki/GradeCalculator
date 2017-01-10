//Thomas Swinicki

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import net.miginfocom.swing.MigLayout;

public class Calculator extends JFrame implements ActionListener{
	
	private JTextField grade1;      //inputs for grades and percentages for user
	private JTextField percent1;
	private JTextField grade2;
	private JTextField percent2;
	private JTextField grade3;
	private JTextField percent3;
	private JTextField grade4;
	private JTextField percent4;
	private JTextField grade5;
	private JTextField percent5;
	private ArrayList<JTextField> gs = new ArrayList<JTextField>(); //arrayList of grades
	private ArrayList<JTextField> ps = new ArrayList<JTextField>(); //arrayList of percentages
	private JLabel finalGrade = new JLabel();//label that will display the final grade
	private JLabel neededGrade = new JLabel();//label that will display the grade needed to get 
	//the a desired grade defined by user
	private Container pane = getContentPane();
	private JLabel desiredGrade; //prompt for the grade user wishes to obtain
	private JTextField dGrade; //input for desired grade
	private JLabel estimatedGrade; //prompt for grade user estimates they will get
	private JTextField eGrade; //input for estimated grade
	private JLabel dGradeOutput = new JLabel();
	private JLabel eGradeOutput = new JLabel();
	
	public Calculator(){
		initUI();
	}
	
	private void initUI(){
		setTitle("Grade Calculator");
		
		//these next two lines create the icon
		ImageIcon calcIcon = new ImageIcon("calculator icon.png");
		setIconImage(calcIcon.getImage());      
		
		//configuring size of window
		setSize(600,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//adds instructions for the calculator
		JLabel descript = new JLabel("Please enter your currents grades and their respecitve" 
				+ " weight percentages.");
		
		//add text field where the user will input grades and percentages
		grade1 = new JTextField(5);
		percent1 = new JTextField(5);
		grade2 = new JTextField(5);
		percent2 = new JTextField(5);
		grade3 = new JTextField(5);
		percent3 = new JTextField(5);
		grade4 = new JTextField(5);
		percent4 = new JTextField(5);
		grade5 = new JTextField(5);
		percent5 = new JTextField(5);
		gs.add(grade1);
		gs.add(grade2);
		gs.add(grade3);
		gs.add(grade4);
		gs.add(grade5);
		ps.add(percent1);
		ps.add(percent2);
		ps.add(percent3);
		ps.add(percent4);
		ps.add(percent5);

		//labels for where the grades and percentages go
		JLabel grades =  new JLabel("Grades");
		grades.setFont(new Font("Serif", Font.PLAIN, 14));
		JLabel percentages = new JLabel("Percentage");
		percentages.setFont(new Font("Serif", Font.PLAIN, 14));
		
		//button to calculate grades
		JButton calculate = new JButton("Calculate");
		calculate.addActionListener(this);
		
		//button to add grades
		JButton addGrade = new JButton("Add grade");
		addGrade.addActionListener(new AddGrade());
		
		//desired and estimated grade labels/text fields
		desiredGrade= new JLabel("Input the overrall grade you wish to obtain:");
		estimatedGrade= new JLabel("Input a grade to represent the remaining percentage to see"
				+ " its impact on your final grade:");
		dGrade = new JTextField(5);
		eGrade = new JTextField(5);
		
		//create layout for the GUI
		createLayout(descript, grades, grade1, percentages, percent1, calculate, finalGrade,
				neededGrade, grade2, percent2, grade3, percent3, grade4, percent4,
				grade5, percent5, desiredGrade, dGrade, estimatedGrade, eGrade, dGradeOutput,
				eGradeOutput);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		/*this method will calculate the grade of the user based on the percentages and 
		 *grades that were input... it will also tell the use what percentage of their grade
		 *remains and what they need to score on the remaining percentage to obtain a desired
		 *grade which they input*/
		double fGrade = 0;
		double totalPerc = 0;
		for(int i = 0; i < gs.size(); i++){
			if((!gs.get(i).equals(null) || gs.get(i).equals("")) && 
			(!ps.get(i).equals(null) || ps.get(i).equals("")) ){
				float grade = 0;
				double perc = 0;
				try{
				grade = Float.parseFloat(gs.get(i).getText());
				perc = ((double) Integer.parseInt(ps.get(i).getText()) /100);
				}
				catch(NumberFormatException n){
					grade = 0;
					perc = 0;
				}
				fGrade += grade * perc;
				totalPerc += perc * 100;
			}
			
		}
		double remainingPerc = 100-totalPerc;
		
		Float dGradeFloat;
		try{
			dGradeFloat = Float.parseFloat(dGrade.getText());
		}
		catch(NumberFormatException m){
			dGradeFloat = null;
		}
		Float eGradeFloat;
		try{
			eGradeFloat = Float.parseFloat(eGrade.getText());
		}
		catch(NumberFormatException t){
			eGradeFloat = null;
		}
		
		NumberFormat nFormat = new DecimalFormat("#0.00");//will make grade go to 100ths
		finalGrade.setText("Your current grade is " + nFormat.format(fGrade) + 
				" and makes up " + (int)totalPerc + "% of your grade.");
		
		if(dGradeFloat != null){
			Float neededGrade = null;
			if(remainingPerc != 0)
				neededGrade = (dGradeFloat - Float.parseFloat(nFormat.format(fGrade))) / 
				(Float.parseFloat(nFormat.format(remainingPerc))/100);
			dGradeOutput.setText("You need to receive a grade of " + neededGrade + " on the"
		+ " remaining " + remainingPerc+ "% of your grade for the final grade to be " + dGradeFloat);
		}
		if(eGradeFloat != null){
			Float eFinalGrade = null;
			if(remainingPerc != 0)
				eFinalGrade = Float.parseFloat(nFormat.format(fGrade)) + (eGradeFloat *
						(Float.parseFloat(nFormat.format(remainingPerc))/100));
			eGradeOutput.setText("If you receive a " + eGradeFloat + " on the remaining "
					+ remainingPerc + "% of your grade, you will receive a final grade of " +
					eFinalGrade);
		}
		
		pack();
	}
	
    public static void main(String[] args){
		EventQueue.invokeLater(() -> {
            Calculator calc = new Calculator();
    		calc.setVisible(true);
        });
	}
    
    public class AddGrade implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e){
    		
    	}
    }
	
	
	//may consider using a different layout for program/need to learn more about GroupLayout
	/*public void createLayout(JComponent... arg) {
		Container pane = getContentPane();
		GroupLayout g1 = new GroupLayout(pane);
		pane.setLayout(g1);
		
		g1.setAutoCreateGaps(true);
		g1.setAutoCreateContainerGaps(true);
		
		//for quick reference on which arguments correspond to which component
		//createLayout(descript, grades, grade1, percentages, percent1, calculate);
		
		g1.setHorizontalGroup(
				g1.createSequentialGroup()
					.addComponent(arg[0])
					.addComponent(arg[1])
					.addGroup(g1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(arg[2]))
					.addComponent(arg[3])
					.addGroup(g1.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(arg[4]))
		);
		
		g1.setVerticalGroup(
				g1.createSequentialGroup()
					.addComponent(arg[0])
					.addGroup(g1.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(arg[1])
							.addComponent(arg[3]))
					.addGroup(g1.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(arg[2],GroupLayout.DEFAULT_SIZE, 
				                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(arg[4],GroupLayout.DEFAULT_SIZE, 
				                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		pack();
	}*/
	
	//layout using MigLayout
	public void createLayout(JComponent... arg){
		//Container pane = getContentPane();
		MigLayout m1 = new MigLayout("wrap 2");
		pane.setLayout(m1);
		
		//for quick reference on which indices correspond to which component
		//createLayout(descript, grades, grade1, percentages, percent1, calculate, finalGrade,
		//neededGrade, grade2, percent2, grade3, percent3, grade4, percent4,
		//grade5, percent5, desiredGrade, dGrade, estimatedGrade, eGrade, dGradeOutput,
		//eGradeOutput);
		pane.add(arg[0], "wrap");
		pane.add(arg[1], "gapleft 300");
		pane.add(arg[3], "gapright 300");
		pane.add(arg[2], "gapleft 300");
		pane.add(arg[4], "gapright 300");
		pane.add(arg[8], "gapleft 300");
		pane.add(arg[9], "gapright 300");
		pane.add(arg[10], "gapleft 300");
		pane.add(arg[11], "gapright 300");
		pane.add(arg[12], "gapleft 300");
		pane.add(arg[13], "gapright 300");
		pane.add(arg[14], "gapleft 300");
		pane.add(arg[15], "gapright 300");
		pane.add(arg[16], "gaptop 50px");
		pane.add(arg[17]);
		pane.add(arg[18]);
		pane.add(arg[19]);
		pane.add(arg[6], "wrap");
		pane.add(arg[20], "wrap");
		pane.add(arg[21], "wrap");
		pane.add(arg[5], "align right");
		
		pack();
	}
	
}
