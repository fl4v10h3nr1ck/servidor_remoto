package servidor_remoto;



public abstract class Mensageiro {

	
private Servidor servidor;	
	
	
	
	public abstract boolean envia();


	

	protected void setServidor(Servidor servidor){
		
	this.servidor = servidor;	
	}



	

	public Mensagem enviaDados(Mensagem mensagem){
		
	return this.servidor.enviaDados(mensagem);	
	}
	
	
	
	
	
	
	
	
}
