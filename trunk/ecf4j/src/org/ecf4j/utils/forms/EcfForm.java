package org.ecf4j.utils.forms;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class EcfForm extends JFrame {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField fieldNumEcf = null;

	/**
	 * This method initializes 
	 * 
	 */
	public EcfForm() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(184, 58));
        this.setContentPane(getJPanel());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("EcfConfig");
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("NumECF");
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(jLabel, null);
			jPanel.add(getFieldNumEcf(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes fieldNumEcf	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFieldNumEcf() {
		if (fieldNumEcf == null) {
			fieldNumEcf = new JTextField();
			fieldNumEcf.setPreferredSize(new Dimension(100, 20));
		}
		return fieldNumEcf;
	}
	
	public int getNumEcf(){
		try {
			return Integer.parseInt(fieldNumEcf.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public void setNumEcf(int arg){
		fieldNumEcf.setText(""+arg);
	}
	
}  //  @jve:decl-index=0:visual-constraint="1,-52"
