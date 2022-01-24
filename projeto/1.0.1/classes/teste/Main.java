package teste;

import javax.swing.JFrame;

import servidor_remoto.Monitor;
import servidor_remoto.PainelDeStatusDeSincronismo;


public class Main {

	
	
	
	public static void main(String args[]){
				
		
	//Monitor	monitor = new Monitor(new Msg());
	
	
	JFrame frame = new JFrame();
	frame.setSize(500, 500);
	frame.add(new PainelDeStatusDeSincronismo(new Msg()));
	
	frame.setVisible(true);
	
	
	//monitor.run();
	
	//monitor.mostraFormDeConfiguracao();
	//monitor.mostraFormConsole();
	}
	

	
	
	
	
}
