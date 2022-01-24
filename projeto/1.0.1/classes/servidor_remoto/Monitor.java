package servidor_remoto;


import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JTextArea;





public final class Monitor implements Runnable{


public volatile boolean encerrar;	

public Servidor servidor;	

private Thread thread;	

public volatile boolean bloqueio_remoto_ativo;

public volatile boolean bloqueio_local_ativo;

public JTextArea area_console;

public boolean sincronismo_manual;

public PainelDeStatusDeSincronismo painel_de_status;




	public Monitor(Mensageiro mensageiro){
		
	this(mensageiro, null);
	}



	
	

	public Monitor(Mensageiro mensageiro, PainelDeStatusDeSincronismo painel_de_status){
		
	this.encerrar = false;	
	
	this.servidor = new Servidor(mensageiro, this);
	
	this.sincronismo_manual = this.servidor.sincronizacaoManual()==null || 
								this.servidor.sincronizacaoManual().compareTo("SIM")!=0?false:true;	
	
	
	this.thread = new Thread(this);
	
	this.bloqueio_remoto_ativo = false;
	
	this.bloqueio_local_ativo= false;
	
	this.area_console = new JTextArea();
	this.area_console.setEditable(false);
	this.area_console.setBackground(Color.BLACK);
	this.area_console.setForeground(Color.GREEN);
	
	this.painel_de_status = painel_de_status;
	
	this.painel_de_status.addComponentes();
	
	this.painel_de_status.setMonitor(this);
	
	if(!this.sincronismo_manual)
	this.run();
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
			
		
			if(this.servidor.sincronizacaoManual()==null || this.servidor.sincronizacaoManual().compareTo("SIM")!=0){
			
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
		
	this.setConsole("______________________________________________", false);
		
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
													   servidor.sincronizacaoManual());
	form.setVisible(true);
	}
	
	
	
	
	
	
	
	public void setConsole(String texto){
		
	setConsole(texto, true);
	}
	
	
	
	
	
	
	
	public void setConsole(String texto, boolean mostrar_horario){
	
	String aux = this.area_console.getText()+
					(mostrar_horario?new SimpleDateFormat("dd/MM/yyyy HH:mm:ss | ").format(new Date()):"")+
						texto+"\n";
		
	this.area_console.setText(aux);
	System.out.print(aux);
	}

	
	
	
	
	
	public void mostraFormConsole(){
		
	Console con= new Console(this);
	con.setVisible(true);		
	}
	

	
	
	
	public void testaConexao(){
		
	this.setConsole("INICIANDO TESTE DE CONEXÃO");
	
	Mensagem	mensagem = new Mensagem();
	mensagem.setTipo("TESTE_DE_CONEXAO");	
	mensagem.setStatus("INDEFINIDO");	
		
	mensagem.setValores(new HashMap<String, Object>());
	
	Mensagem	retorno = this.servidor.enviaDados(mensagem);
	

	if(retorno.getValores().get("ERRO_COD")!=null && 
			retorno.getValores().get("ERRO_COD").toString().length()>0 &&
				retorno.getValores().get("ERRO_COD").toString().compareTo("ERRO_DE_CONEXAO")==0)
	this.setConsole("FALHA AO ESTABELECER CONEXÃO. SERVIDOR OFFLINE.");
	else
	this.setConsole("CONEXÃO ESTABELECIDA COM SUCESSO. SERVIDOR ONLINE.");
	
	
	
	
	this.setConsole("______________________________________________", false);
	}

	
	
	
}
