package servidor_remoto;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;





public class FormSetCredenciais extends JDialog{

	

private static final long serialVersionUID = 1L;



private JTextField tf_url;	
private JTextField tf_chave;
private JTextField tf_intervalo_em_seg;
private JCheckBox chk_sincronizar;


private String path;




	public FormSetCredenciais(String url, 
								String chave, 
									long intervalo, 
										String path,
											String permitido_sincronizar) {
	

	super();
	
	this.setTitle("Configuração de Servidor WEB");
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	this.setSize(600 , 170);
	this.setLocationRelativeTo(null);
	this.setLayout(new GridBagLayout());
	this.setModal(true);
	this.getContentPane().setBackground(Color.WHITE);  
	
	adicionarComponentes();
	
	this.tf_url.setText(url);
	this.tf_chave.setText(chave);
	this.tf_intervalo_em_seg.setText(""+(intervalo/60000));
	this.chk_sincronizar.setSelected(permitido_sincronizar!=null && permitido_sincronizar.compareTo("SIM")==0?true:false);
	this.path =path;
	}

	
	
	
	
	public void adicionarComponentes(){	
		
	GridBagConstraints cons = new GridBagConstraints();   	
		
	this.setLayout(new GridBagLayout());
	    
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty  = 0;
	cons.weightx = 1;
	cons.insets = new Insets(5, 5, 5, 5);
	JPanel panel1 = new JPanel(new GridBagLayout());
	panel1.setBackground(Color.white);
	this.add(panel1, cons);
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty  = 0;
	cons.weightx = 1;
	cons.insets = new Insets(5, 0, 0, 0);
	panel1.add(new JLabel("<html>URL do servidor WEB:<font color=red>*</font></html>"), cons);
	
	cons.insets = new Insets(0, 0, 0, 0);
	this.tf_url = new JTextField();
	panel1.add(this.tf_url, cons);
	
	cons.gridwidth = 1;
	cons.weightx = 0.8;
	cons.insets = new Insets(5, 0, 0, 0);
	panel1.add(new JLabel("<html>Chave de Acesso:<font color=red>*</font></html>"), cons);
	cons.weightx = 0.1;
	panel1.add(new JLabel("<html>Intervalo (em min.):<font color=red>*</font></html>"), cons);
	cons.gridwidth = GridBagConstraints.REMAINDER;
	panel1.add(new JLabel(""), cons);
	
	
	
	cons.gridwidth = 1;
	cons.weightx = 0.8;
	cons.insets = new Insets(0, 0, 0, 0);
	this.tf_chave = new JTextField();
	panel1.add(this.tf_chave, cons);
	
	
	cons.weightx = 0.1;
	this.tf_intervalo_em_seg = new JTextField();
	this.tf_intervalo_em_seg.setDocument( new Format_TextField_MaxLengthOnlyNum( 2, this.tf_intervalo_em_seg )  ); 
	panel1.add(this.tf_intervalo_em_seg, cons);


	cons.gridwidth = GridBagConstraints.REMAINDER;
	panel1.add(this.chk_sincronizar = new JCheckBox("Exibir Sincronizador", false), cons);
	this.chk_sincronizar.setBackground(Color.white);
	
	
	
	cons.insets = new Insets(5, 0, 0, 0);
	cons.fill = GridBagConstraints.NONE;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weightx = 0;
	cons.ipadx = 30;
	cons.anchor = GridBagConstraints.CENTER;
	JButton bt_add = new JButton("Salvar Credenciais");
	panel1.add(bt_add, cons);
	
	
	
		bt_add.addActionListener( new ActionListener(){
		public void actionPerformed( ActionEvent event ){
				    	
			if(salvar()){	
			
			JOptionPane.showMessageDialog(null, "Credenciais do servidor Salvas com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);		
			dispose();	
			}
		}});
	}
	
	
	
	
	
	private boolean salvar(){
		
	Preferences preferences	= null;
	
		if(this.tf_url.getText().length()==0){
			
		JOptionPane.showMessageDialog(null, "Informe a URL do servidor.", "ERRO", JOptionPane.ERROR_MESSAGE);			
		return false;
		}
	
		if(this.tf_chave.getText().length()==0){
			
		JOptionPane.showMessageDialog(null, "Informe a chave de acesso do servidor.", "ERRO", JOptionPane.ERROR_MESSAGE);			
		return false;
		}
		
		
		if(this.tf_intervalo_em_seg.getText().length()==0){
			
		JOptionPane.showMessageDialog(null, "Informe o intervalo de sincronismo em minutos.", "ERRO", JOptionPane.ERROR_MESSAGE);			
		return false;
		}
		
		try {
					
		preferences = Preferences.userRoot().node(this.path);
					
			if(!preferences.nodeExists("")){
				
			JOptionPane.showMessageDialog(null, "Falha na gravação das preferências.", "ERRO", JOptionPane.ERROR_MESSAGE);			
			return false;
			}
			
		preferences.put("url", this.tf_url.getText());
		preferences.put("chave", this.tf_chave.getText());
		preferences.put("intervalo", ""+(Integer.parseInt(this.tf_intervalo_em_seg.getText())*60000));
		preferences.put("sincronizar", this.chk_sincronizar.isSelected()?"SIM":"NAO");
		}
		catch (BackingStoreException e) {
			
		JOptionPane.showMessageDialog(null, "Falha na gravação das preferências.", "ERRO", JOptionPane.ERROR_MESSAGE);				
		return false;
		}
		
	return true;		
	}

	
}

