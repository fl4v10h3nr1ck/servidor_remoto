package servidor_remoto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.Consts;




public final class Servidor {
	

private String url;
private String chave;
private long intervalo;
private String sincronizar;



private final String path = "com/example/app/prefs/servidor_remoto";

private Mensageiro mensageiro;

private Monitor monitor;




	public Servidor(Mensageiro mensageiro, Monitor monitor){
	
	this.mensageiro = mensageiro;
		
	this.mensageiro.setServidor(this);
	
	this.monitor = monitor;
	
	this.getCredenciais();
	}




	
	
	public boolean trocaDeMensagem(){
	
	return mensageiro.envia();
	}
	
	
	
	
	
	private void getCredenciais(){
		
	Preferences preferences	= null;
		
		try {
							
		preferences = Preferences.userRoot().node(this.path);
							
		if(!preferences.nodeExists(""))
		return;
			
		
		this.url=preferences.get("url", "");
		this.chave=preferences.get("chave", "");
		this.intervalo=Long.parseLong(preferences.get("intervalo", "0"));
		this.sincronizar=preferences.get("sincronizar", "NAO");
		}
		catch (BackingStoreException e) {return;}
				
	return;		
	}
	
	
	
	
	
	
	protected Mensagem enviaDados(Mensagem mensagem){
		
	String retorno = "";	
		try {
				
		Form form = Form.form();	
		form.add("chave", this.chave);
		form.add("tipo", mensagem.getTipo());
		form.add("status", mensagem.getStatus());
		
			if(mensagem.getValores() !=null){
				
			Set<String> chaves = mensagem.getValores().keySet();  
	        
				for (String chave : chaves){  
		            
					if(chave != null){  
					
					String valor = 	mensagem.getValores().get(chave) ==null?
							"":mensagem.getValores().get(chave).toString();
					form.add(chave, valor);
					}
		        } 
			}

		retorno = new String(Request.Post(this.url)
		.useExpectContinue()
		.version(HttpVersion.HTTP_1_1)
		.bodyForm(form.build(), Consts.UTF_8).execute().returnContent().asString());
		
		
		if(retorno ==null || retorno.length()==0)
		return this.getMensagemDeErro("Servidor não respondeu. Servidor indisponível.");
		
		
		String dados_retorno[] = retorno.split(Pattern.quote("#_#")); 
		
		if(dados_retorno.length==0)
		return this.getMensagemDeErro("Servidor não respondeu. Servidor indisponível.");
				
		Mensagem mensagem_retorno = new Mensagem();
		Map<String, Object> valores = new HashMap<String, Object>();
		
			for(int i = 0; i < dados_retorno.length;i++){
			
				if(i==0){	
				
				mensagem_retorno.setTipo(dados_retorno[0]);
				continue;
				}
			
				if(i==1){
				
				mensagem_retorno.setStatus(dados_retorno[1]);
				continue;
				}
			
			valores.put("val"+(i-1), dados_retorno[i]);
			}
		
		mensagem_retorno.setValores(valores);

		return mensagem_retorno;
		} 
		catch (IOException  e) {
		
		this.monitor.setConsole("Falha na conexão. Servidor indisponível.");
		return this.getMensagemDeErro("Falha na conexão. Servidor indisponível.");
		}
		
	}
	
	
	
	
	
	public String getUrl(){return this.url;}
	public String getChave(){return this.chave;}
	public String getPath(){return this.path;}
	public long getIntervalo(){return this.intervalo;}
	public String getSincronizar(){return this.sincronizar;}
	
	
	
	
	private Mensagem getMensagemDeErro(String erro){
		
	Mensagem mensagem = new Mensagem();
	mensagem.setStatus("ERRO");	
	mensagem.setTipo("INDEFINIDO");	
	
	Map<String, Object> valores = new HashMap<String, Object>();
	valores.put("ERRO", erro);
	
	mensagem.setValores(valores);
	
	return mensagem;
	}
	
	

	
	
	
	public Mensageiro getMensageiro(){
		
	return this.mensageiro;	
	}
	
	
	
}
