package servidor_remoto;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;





public class FormDeSincronizacao extends JDialog{

	

private static final long serialVersionUID = 1L;


private JProgressBar progressBar;





	public FormDeSincronizacao(Monitor monitor) {
	
	super();
	
	this.setTitle("Sincronizando...");
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	this.setSize(400 , 100);
	this.setLocationRelativeTo(null);
	this.setLayout(new GridBagLayout());
	//this.setModal(true);
	this.getContentPane().setBackground(Color.WHITE);  
	this.setAlwaysOnTop(true); 
	
	this.adicionarComponentes();
	}

	
	
	
	
	public void adicionarComponentes(){	
		
	GridBagConstraints cons = new GridBagConstraints();   	
		
	this.setLayout(new GridBagLayout());
	    
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty  = 1;
	cons.weightx = 1;
	cons.insets = new Insets(5, 5, 5, 5);
	
	this.progressBar = new JProgressBar(0, 10);
	progressBar.setValue(0);
	progressBar.setStringPainted(true);
	this.add(progressBar, cons);
	}
	
	
	
	
	
	
	public void sincronizar(){
		
		//try {
			
		
		//new Thread().sleep(2000);
		
		this.progressBar.setValue(5);
		
		
		//new Thread().sleep(2000);
		
		this.progressBar.setValue(8);
		
		
		//new Thread().sleep(2000);
		
		this.progressBar.setValue(10);
		
		//}catch (InterruptedException e) {e.printStackTrace();}
				
				
	}
	
	
	
	
	
	protected void mostrar(){
		
	this.setVisible(true);
	}
	
	
	
}

