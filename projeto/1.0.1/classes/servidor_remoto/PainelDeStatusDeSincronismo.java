package servidor_remoto;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;





public class PainelDeStatusDeSincronismo extends JPanel{


private static final long serialVersionUID = 1L;


private JButton bt_sincronizar;

private Monitor monitor;

private JLabel lb_status;




public Color cor_fonte;

public Color cor_fundo;




	public PainelDeStatusDeSincronismo(Mensageiro mensageiro){

	this(mensageiro, Color.BLACK, Color.WHITE);
	}


	
	

	public PainelDeStatusDeSincronismo(Mensageiro mensageiro, Color cor_fonte, Color cor_fundo){
	
	this.cor_fonte = cor_fonte;

	this.cor_fundo =  cor_fundo;
	
	this.setMonitor(new Monitor(mensageiro, this));
	}
	
	
	
	
	
	
	public void addComponentes(){
		
	this.setLayout(new GridBagLayout());

	this.setBackground(this.cor_fundo);
	
	GridBagConstraints cons = new GridBagConstraints();  

	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.WEST;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 0;
	cons.insets = new Insets(0, 26, 0, 0);
	
	cons.insets = new Insets(0, 0, 0, 0);
	cons.gridwidth = 1;
	this.lb_status = new JLabel("Pronto para sincronizar");	
	this.lb_status.setForeground(this.cor_fonte);
	this.add(this.lb_status, cons);
	
	
	cons.ipadx = 15;
	cons.insets = new Insets(0, 10, 0, 0);
	this.bt_sincronizar  = new JButton("Sincronizar", new ImageIcon(getClass().getResource("/icons/sync.png")));
	bt_sincronizar.setToolTipText("Sincronizar Servidor Remoto");
	this.add(bt_sincronizar, cons);
	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weightx = 1;	
	this.add(new JLabel(""), cons);
	
		bt_sincronizar.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
			
		sincronizando();
		}}); 
		
		
	this.setConfiguracaoDeMonitor();
	}
	
	

	
	
	
	public void setMonitor(Monitor monitor){
	
	this.monitor = monitor;	
	this.setConfiguracaoDeMonitor();
	}
	
	
	
	
	
	public Monitor getMonitor(){
		
	return this.monitor;	
	}
	
	
	
	

	
	public void sincronizando(){
		
	if(this.monitor!=null)		
	this.monitor.run();
	}
	
	
	
	
	
	
	
	public String mensagemPersonalizada(){
		
	return "";	
	}
	
	
	
	
	
	protected void sincronizacaoIniciada(){
		
	this.lb_status.setText("Sincronizando...");
		
	this.lb_status.paintImmediately(0,0,lb_status.getWidth(),lb_status.getHeight());
	
		if(monitor!=null && monitor.sincronismo_manual){
		this.bt_sincronizar.setEnabled(false);
		this.bt_sincronizar.paintImmediately(0,0,bt_sincronizar.getWidth(),bt_sincronizar.getHeight());
		}
	}
		
	
	
	
	
	

	protected void sincronizacaoTerminada(String status){
		
	String person = mensagemPersonalizada();
			
	this.lb_status.setText((status!=null?status:"")+(person!=null && person.length()>0?"("+person+")":""));
	
	this.lb_status.paintImmediately(0,0,lb_status.getWidth(),lb_status.getHeight());
	
	if(monitor!=null && monitor.sincronismo_manual)
	this.bt_sincronizar.setEnabled(true);
		
	}
	
	
	
	

	
	
	private void setConfiguracaoDeMonitor(){
	
		if(monitor!=null){	
		
			if(monitor.servidor.sincronizacaoManual()!=null && monitor.servidor.sincronizacaoManual().compareTo("SIM")==0){
			
			if(!monitor.sincronismo_manual)
			bt_sincronizar.setVisible(false);	
			}
			else{
				
			this.lb_status.setVisible(false);
			bt_sincronizar.setVisible(false);	
			}
		}
	}
	
	
	
	
	
	
}
