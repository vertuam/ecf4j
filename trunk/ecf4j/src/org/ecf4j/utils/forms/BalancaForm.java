/**
 * 
 */
package org.ecf4j.utils.forms;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class BalancaForm extends JFrame {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField fieldPeso = null;
	private JButton jButton = null;

	/**
	 * This method initializes 
	 * 
	 */
	public BalancaForm() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(356, 76));
        this.setTitle("Balança");
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
			jLabel.setText("Peso");
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(jLabel, null);
			jPanel.add(getFieldPeso(), null);
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes fieldPeso	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFieldPeso() {
		if (fieldPeso == null) {
			fieldPeso = new JTextField();
			fieldPeso.setPreferredSize(new Dimension(200, 20));
		}
		return fieldPeso;
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
					onSend(fieldPeso.getText());
				}
			});
		}
		return jButton;
	}
	
	public void onSend(String peso){
		
	}
	
	public void setPeso(String arg){
		fieldPeso.setText(arg);
	}
	
	public String getPeso(){
		return fieldPeso.getText();
	}
}  //  @jve:decl-index=0:visual-constraint="542,-187"
