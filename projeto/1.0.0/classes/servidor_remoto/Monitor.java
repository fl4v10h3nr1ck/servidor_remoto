package servidor_remoto;


import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;





public final class Monitor implements Runnable{


public volatile boolean encerrar;	

public Servidor servidor;	

private Thread thread;	

public volatile boolean bloqueio_remoto_ativo;

public volatile boolean bloqueio_local_ativo;

public JTextArea area_console;

public boolean sincronismo_manual;

public PainelDeStatus painel_de_status;





	public Monitor(Mensageiro mensageiro){
		
	this(mensageiro, false, null);
	}



	
	
	

	public Monitor(Mensageiro mensageiro, boolean sincronismo_manual){
	
	this(mensageiro, sincronismo_manual, null);
	}
	
		


	
	


	public Monitor(Mensageiro mensageiro, boolean sincronismo_manual, PainelDeStatus painel_de_status){
	
	this.sincronismo_manual = sincronismo_manual;	
		
	this.encerrar = false;	
	
	this.servidor = new Servidor(mensageiro, this);
	
	this.thread = new Thread(this);
	
	this.bloqueio_remoto_ativo = false;
	
	this.bloqueio_local_ativo= false;
	
	this.area_console = new JTextArea();
	this.area_console.setEditable(false);
	this.area_console.setBackground(Color.BLACK);
	this.area_console.setForeground(Color.GREEN);
	
	if(painel_de_status==null)
	this.painel_de_status = new PainelDeStatus();
	else
	this.painel_de_status = painel_de_status;
	
	this.painel_de_status.addComponentes();
	this.painel_de_status.setMonitor(this);
	}
	
		


	
	
	
	
	
	
	
	
	
	@Override
	public void run() {

	boolean encerrar  =this.encerrar;	
		
		
	this.setConsole("Monitor de sincronismo ligado (intervalo "+this.servidor.getIntervalo()+") modo "+
																(this.sincronismo_manual?"MANUAL":"AUTO"));		
	
		if(this.servidor.getUrl()!= null && 
				this.servidor.getUrl().length()>0 && 
					this.servidor.getChave()!= null && 
						this.servidor.getChave().length()>0 &&
							this.servidor.getIntervalo()>0){
			
		
			if(this.servidor.getSincronizar()==null || this.servidor.getSincronizar().compareTo("SIM")!=0){
			
			this.setConsole("Sincronização desligada para este terminal.");			
			return;	
			}	

			while(!encerrar){
			
				try {
				
					if(!this.bloqueio_local_ativo){	
	
					this.bloqueio_remoto_ativo = true;

					this.sincroniza();
					
					this.bloqueio_remoto_ativo = false;
					
						if(this.sincronismo_manual){
							
						encerrar  =true;
						continue;
						}
					
					Thread.sleep(this.servidor.getIntervalo());
					}
					else
					Thread.sleep(10000);

				} 
				catch (InterruptedException e) {this.setConsole("(InterruptedException) Falha grave.");return;}
			}
			
		this.setConsole("Monitor de sincronismo desligado.");			
		}
		else	
		this.setConsole("Credenciais de sincronismo não definidas, monitor desligado.");			
		
	}

	
	
	
	
	
	
	public void sincroniza() throws InterruptedException{
	
	this.painel_de_status.sincronizacaoIniciada();
	
	Thread.sleep(2000);

	boolean troca  = this.servidor.trocaDeMensagem();
		
	if(!troca)
	this.setConsole("Falha na Última Transferência.");
		
	this.setConsole("______________________________________________");
		
	this.painel_de_status.sincronizacaoTerminada(troca?
				"Última Sincronização em "+(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()))+".":
					"Falha na Última Transferência.");	
	}
	
	
	
	

	
	
	public void parar(){
		
	this.thread.interrupt();
	}
	
	
	
	
	
	
	public void mostraFormDeConfiguracao(){
		
	FormSetCredenciais	form  = new FormSetCredenciais(servidor.getUrl(), 
													   servidor.getChave(),
													   this.servidor.getIntervalo(),
													   servidor.getPath(),
													   servidor.getSincronizar());
	form.setVisible(true);
	}
	
	
	
	
	
	
	public void setConsole(String texto){
		
	this.area_console.setText(this.area_console.getText()+texto+"\n");
	System.out.println(texto);
	}

	
	
	
	
	
	public void mostraFormConsole(){
		
	Console con= new Console(this);
	con.setVisible(true);		
	}
	

	
}
