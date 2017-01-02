//Thomas Swinicki

import java.awt.*;
import javax.swing.*;

public class Calculator extends JFrame{
	
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
		JTextField grade1 = new JTextField(5);
		JTextField percent1 = new JTextField(5);

		//labels for where the grades and percentages go
		JLabel grades =  new JLabel("Grades");
		grades.setFont(new Font("Serif", Font.PLAIN, 14));
		JLabel percentages = new JLabel("Percentage");
		
		//create layout for the GUI
		createLayout(descript, grades, grade1, percentages, percent1);
		
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(() -> {
            Calculator calc = new Calculator();
    		calc.setVisible(true);
        });
	}
	
	//may consider using a different layout for program/ need to learn more about GroupLayout
	public void createLayout(JComponent... arg) {
		Container pane = getContentPane();
		GroupLayout g1 = new GroupLayout(pane);
		pane.setLayout(g1);
		
		g1.setAutoCreateContainerGaps(true);
		
		g1.setHorizontalGroup(g1.createSequentialGroup().addComponent(arg[0]).addComponent(arg[1])
				.addComponent(arg[2]).addComponent(arg[3]).addComponent(arg[4])
                .addGap(250));
		
		g1.setVerticalGroup(g1.createSequentialGroup().addComponent(arg[0])
        .addComponent(arg[1])
        .addComponent(arg[2], GroupLayout.DEFAULT_SIZE, 
                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addComponent(arg[3]).addComponent(arg[4], GroupLayout.DEFAULT_SIZE, 
                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(150));
		
		pack();
	}
	

    
	
}
