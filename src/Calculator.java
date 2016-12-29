//Thomas Swinicki

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Calculator extends JFrame{

	private JLabel grade_lbl;
	
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
		
		//add label and text field where the user will input grades
		JTextField grade1 = new JTextField(5);
		JLabel grade_lbl = new JLabel();
		grade1.getDocument().addDocumentListener(new MyDocumentListener());
		
		//create layout for the GUI
		createLayout(descript, grade1, grade_lbl);
		
	}
	
	//The class that will allow for editing of the text field
	private class MyDocumentListener implements DocumentListener {

        private String text;

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateLabel(e);
        }

       @Override
        public void changedUpdate(DocumentEvent e) {
        }

       /* only method we are interested in for the purposes of this program, which are to
       submit different grades */
        private void updateLabel(DocumentEvent e) {

            Document doc = e.getDocument();
            int len = doc.getLength();

            try {
                text = doc.getText(0, len);
            } catch (BadLocationException ex) {
                Logger.getLogger(Calculator.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
            grade_lbl.setText(text);

        }
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
		
		g1.setHorizontalGroup(g1.createSequentialGroup().addComponent(arg[0]).addComponent(arg[1])
                .addGap(250));
		
		g1.setVerticalGroup(g1.createSequentialGroup().addComponent(arg[0], GroupLayout.DEFAULT_SIZE, 
                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addComponent(arg[1])
        .addGap(150));
		
		pack();
	}
	

    
	
}
