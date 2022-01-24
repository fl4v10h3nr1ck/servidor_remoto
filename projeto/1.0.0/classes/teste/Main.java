package teste;

import servidor_remoto.Monitor;


public class Main {

	
	
	
	public static void main(String args[]){
				
		
	Monitor	monitor = new Monitor(new Msg(), true);
	
	/*
	JFrame frame = new JFrame();
	frame.setSize(500, 500);
	frame.add(monitor.painel_de_status);
	
	frame.setVisible(true);
	*/
	
	monitor.run();
		
	monitor.mostraFormDeConfiguracao();
	}
	

	
	
	
	
}
