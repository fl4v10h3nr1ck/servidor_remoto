package servidor_remoto;

import java.util.Map;

public class Mensagem {

	
private String Status;
private String tipo;
private Map<String, Object> valores;


public String getTipo() {	return tipo;}
public void setTipo(String tipo) {	this.tipo = tipo;}

public Map<String, Object> getValores() {	return valores;}
public void setValores(Map<String, Object> valores) {	this.valores = valores;}

public String getStatus() {	return Status;}
public void setStatus(String status) {	Status = status;} 
	
	
	
	
	
}
