package org.ecf4j.utils.forms;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.WindowConstants;

public class ScannerForm extends JFrame {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField fieldCodigo = null;
	private JButton jButton = null;

	/**
	 * This method initializes 
	 * 
	 */
	public ScannerForm() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(356, 76));
        this.setTitle("Scanner");
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setContentPane(getJPanel());
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("C�digo");
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(jLabel, null);
			jPanel.add(getFieldCodigo(), null);
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes fieldCodigo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFieldCodigo() {
		if (fieldCodigo == null) {
			fieldCodigo = new JTextField();
			fieldCodigo.setPreferredSize(new Dimension(200, 20));
		}
		return fieldCodigo;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Send");
			jButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					onSend(fieldCodigo.getText());
				}
			});
		}
		return jButton;
	}
	
	public void onSend(String codigo){
		
	}
	
	public void setCodigo(String arg){
		fieldCodigo.setText(arg);
	}
	
	public String getCodigo(){
		return fieldCodigo.getText();
	}
}  //  @jve:decl-index=0:visual-constraint="180,-13"
