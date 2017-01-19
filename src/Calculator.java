//Thomas Swinicki

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
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
	private Integer curNumGradeRows = 7;
	//list containing booleans that are true if the text fields for grades/percentages are empty
	private ArrayList<Boolean> gsBools = new ArrayList<Boolean>(); 
	private ArrayList<Boolean> psBools = new ArrayList<Boolean>();
	
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
		descript.setFont(new Font("Serif", Font.BOLD, 14));
		
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
		
		for(JTextField j : gs){
			j.getDocument().addDocumentListener(new MyGradeDocumentListener(j));
			gsBools.add(true);
		}
		for(JTextField j: ps){
			j.getDocument().addDocumentListener(new MyPercentDocumentListener(j));
			psBools.add(true);
		}

		//labels for where the grades and percentages go
		JLabel grades =  new JLabel("Grades");
		grades.setFont(new Font("Serif", Font.BOLD, 14));
		JLabel percentages = new JLabel("Percentage");
		percentages.setFont(new Font("Serif", Font.BOLD, 14));
		
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
		desiredGrade.setFont(new Font("Serif", Font.BOLD, 14));
		estimatedGrade.setFont(new Font("Serif", Font.BOLD, 14));
		dGrade = new JTextField(5);
		eGrade = new JTextField(5);
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new Clear());
		
		//create layout for the GUI
		createLayout(descript, grades, grade1, percentages, percent1, calculate, finalGrade,
				neededGrade, grade2, percent2, grade3, percent3, grade4, percent4,
				grade5, percent5, desiredGrade, dGrade, estimatedGrade, eGrade, dGradeOutput,
				eGradeOutput, addGrade, clearBtn);
	
	}
	
	private class MyGradeDocumentListener implements DocumentListener{

		private JTextField jtf;
		
		public MyGradeDocumentListener(JTextField j){
			jtf = j;
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			change();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			change();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			change();
		}
		
		public void change(){
			if(!jtf.getText().equals("")){
				gsBools.set(gs.indexOf(jtf) , false);
			}
			else 
				gsBools.set(gs.indexOf(jtf) , true);
		}
	}
	
	private class MyPercentDocumentListener implements DocumentListener{

		private JTextField jtf;
		
		public MyPercentDocumentListener(JTextField j){
			jtf = j;
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			change();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			change();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			change();
		}
		
		public void change(){
			if(!jtf.getText().equals("")){
				psBools.set(ps.indexOf(jtf) , false);
			}
			else 
				psBools.set(ps.indexOf(jtf) , true);
		}
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
			if(gsBools.get(i) == false  && psBools.get(i) == false){
				float grade = 0;
				double perc = 0;
				try{
				grade = Float.parseFloat(gs.get(i).getText());
				perc = ((double) Integer.parseInt(ps.get(i).getText()) /100);
				}
				catch(NumberFormatException n){
					grade = 0;
					perc = 0;
					JOptionPane.showMessageDialog(this, "Please input numbers.", "Error", 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				fGrade += grade * perc;
				totalPerc += perc * 100;
			}
			else if(gsBools.get(i) == true && psBools.get(i) == false){
				JOptionPane.showMessageDialog(this, "Please input all grades", "Error", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if(gsBools.get(i) == false && psBools.get(i) == true){
				JOptionPane.showMessageDialog(this, "Please input all percentages" , "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			/*else if(gs.get(i).getText().isEmpty() && !ps.get(i).getText().isEmpty()){
				//alert user to input all grades
				JOptionPane.showMessageDialog(this, "Please input all grades", "Error", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if(!gs.get(i).getText().isEmpty() && ps.get(i).getText().isEmpty()){
				//alert user to input all percentages
				JOptionPane.showMessageDialog(this, "Please input all percentages" , "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}*/
			
		}
		double remainingPerc = 100-totalPerc;
		
		//create floats for desired and estimated grades
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
		finalGrade.setFont(new Font("Serif", Font.BOLD, 14));
		
		//get the desired and estimated grade output if there is an input for them
		if(dGradeFloat != null){
			Float neededGrade = null;
			if(remainingPerc != 0)
				neededGrade = (dGradeFloat - Float.parseFloat(nFormat.format(fGrade))) / 
				(Float.parseFloat(nFormat.format(remainingPerc))/100);
			dGradeOutput.setText("You need to receive a grade of " + neededGrade + " on the"
		+ " remaining " + remainingPerc+ "% of your grade for the final grade to be " + dGradeFloat
		+ ".");
			dGradeOutput.setFont(new Font("Serif", Font.BOLD, 14));
		}
		if(eGradeFloat != null){
			Float eFinalGrade = null;
			if(remainingPerc != 0)
				eFinalGrade = Float.parseFloat(nFormat.format(fGrade)) + (eGradeFloat *
						(Float.parseFloat(nFormat.format(remainingPerc))/100));
			eGradeOutput.setText("If you receive a " + eGradeFloat + " on the remaining "
					+ remainingPerc + "% of your grade, you will receive a final grade of " +
					eFinalGrade + ".");
			eGradeOutput.setFont(new Font("Serif", Font.BOLD, 14));
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
    		JTextField newG = new JTextField(5);
    		JTextField newP = new JTextField(5);
    		newG.getDocument().addDocumentListener(new MyGradeDocumentListener(newG));
    		newP.getDocument().addDocumentListener(new MyPercentDocumentListener(newP));
    		pane.add(newG, "cell 0 " + curNumGradeRows.toString() + ",  gapleft 300" );
    		pane.add(newP, "cell 1 " + curNumGradeRows.toString() );
    		curNumGradeRows++;
    		gs.add(newG);
    		ps.add(newP);
    		gsBools.add(true);
    		psBools.add(true);
    		pack();
    	}
    }
    
    private class Clear implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for(JTextField g : gs)
				g.setText("");
			for(JTextField p: ps)
				p.setText("");
		}
    	
    }
	
	//layout using MigLayout
	public void createLayout(JComponent... arg){
		MigLayout m1 = new MigLayout("wrap 2");
		pane.setLayout(m1);
		
		//for quick reference on which indices correspond to which component
		//createLayout(descript, grades, grade1, percentages, percent1, calculate, finalGrade,
		//neededGrade, grade2, percent2, grade3, percent3, grade4, percent4,
		//grade5, percent5, desiredGrade, dGrade, estimatedGrade, eGrade, dGradeOutput,
		//eGradeOutput, addGrade, clearBtn);
		pane.add(arg[0], "wrap, gap 5");
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
		pane.add(arg[5], "center, south , gap 350 350 5 5"); //calculate button 
		pane.add(arg[21], "wrap, south, gaptop 10, gap 5"); //eGradeOutput label
		pane.add(arg[20], "wrap, south, gaptop 10, gap 5"); //dGradeOutput label
		pane.add(arg[6], "wrap, south, gaptop 10, gap 5"); //finalGrade label
		pane.add(arg[19], "south, gap 5 750 5"); //eGrade text field
		pane.add(arg[18], "south, gap 5"); //estimatedGrade label
		pane.add(arg[17] ,"south, gap 5 750 5"); //dGrade text field
		pane.add(arg[16], "south, gap 5"); //desiredGrade label
		pane.add(arg[23], "south, gap 350 350 5"); //clear button
		pane.add(arg[22], "wrap , alignright, south, gap 350 350 10"); //addGrade button
		
		pack();
	}
	
}
