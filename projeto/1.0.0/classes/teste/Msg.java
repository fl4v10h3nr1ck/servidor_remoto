package teste;

import java.util.HashMap;
import java.util.Map;

import servidor_remoto.Mensageiro;
import servidor_remoto.Mensagem;

public class Msg extends Mensageiro{

	
	
	@Override
	public boolean envia() {
	
		
	Mensagem	mensagem = new Mensagem();
	mensagem.setTipo("ENVIAR_REPASSE");	
	mensagem.setStatus("INDEFINIDO");	
		
	Map<String, Object> valores = new HashMap<String, Object>();
	valores.put("quant", "2");
	valores.put("unidade_destino", "DESTINO");
	valores.put("unidade_envio", "REMETENTE");
	valores.put("nome_item", "NOME ITEM");
	valores.put("codigo", "3453453543543435345");
	
	mensagem.setValores(valores);
	
	Mensagem	retorno = this.enviaDados(mensagem);
	
	return retorno.getStatus().compareTo("ERRO")==0?false:true;
	}

	
	
		
		
		
		
}
	
	
