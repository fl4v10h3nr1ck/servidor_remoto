package servidor_remoto;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

	
	cons.fill = GridBagConstraints.NONE;
	cons.gridwidth  = GridBagConstraints.REMAINDER;	
	cons.anchor = GridBagConstraints.WEST;
	cons.weighty  = 0;
	cons.weightx = 1;
	cons.insets = new Insets(2, 2, 2, 2);
	
	JButton bt_teste_conexao;
	this.add(bt_teste_conexao = new JButton("Testar Conexão"), cons);
	

	cons.fill = GridBagConstraints.BOTH;
	cons.weighty  = 1;
	
	this.add(new JScrollPane(monitor.area_console), cons);
	
	
		bt_teste_conexao.addActionListener( new ActionListener(){
		public void actionPerformed( ActionEvent event ){
				    	
		monitor.testaConexao();
		}});
	}
	
	
	
	
}
