package servidor_remoto;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JScrollPane;





public class Console extends JDialog{

	
	
private static final long serialVersionUID = 1L;



	public Console(Monitor monitor){
		
	super();
	
	this.setTitle("Monitor de Sincronização");
	this.setSize(600, 500);	
	this.setLocationRelativeTo(null);
	this.setLayout(new GridBagLayout());
	this.setModal(true);
	
	GridBagConstraints cons = new GridBagConstraints();  

	cons.fill = GridBagConstraints.BOTH;
	cons.gridwidth  = GridBagConstraints.REMAINDER;	
	cons.weighty  = 1;
	cons.weightx = 1;
	cons.insets = new Insets(2, 2, 2, 2);
	
	this.add(new JScrollPane(monitor.area_console), cons);
	}
	
	
	
	
}
