import java.awt.*;
import javax.swing.*;

public class Calculator extends JFrame{

	private JLabel item1;
	private JTextField grades;
	private JTextField percents;
	
	public Calculator(){
		initUI();
	}
	
	private void initUI(){
		setTitle("Grade Calculator");
		ImageIcon calcIcon = new ImageIcon("calculator icon.png");
		setIconImage(calcIcon.getImage());         //these two lines create the icon
		setSize(600,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel descript = new JLabel("Please enter your currents grades and their respecitve" 
				+ " weight percentages.");
		
		createLayout(descript);
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(() -> {
            Calculator calc = new Calculator();
    		calc.setVisible(true);
        });
	}
	
	public void createLayout(JComponent... arg) {
		Container pane = getContentPane();
		GroupLayout g1 = new GroupLayout(pane);
		pane.setLayout(g1);
		
		g1.setAutoCreateContainerGaps(true);
		
		g1.setHorizontalGroup(g1.createSequentialGroup().addComponent(arg[0]));
		
		g1.setVerticalGroup(g1.createSequentialGroup().addComponent(arg[0]));
	}
	
}
