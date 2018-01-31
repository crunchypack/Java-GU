import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import BetacriticEasyDatabase.*;


// @author Petroula

public class BetaCritic extends JPanel {
	
	static DatabaseManagerMedia manager;
	public static LoginPanel loginPanel;
	public static User user;
	
public static void main(String[] args) {
	
	manager = new DatabaseManagerMedia("settings.txt");
	  
	JFrame Projectframe = new JFrame("Betacritic");
	
	loginPanel = new LoginPanel(Projectframe);

    Projectframe.setContentPane(new MainPage(Projectframe));      
    Projectframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Projectframe.pack();
    Projectframe.setLocationRelativeTo(null);
    Projectframe.setVisible(true);
    ImageIcon icon = new ImageIcon("BETA.png");
    Projectframe.setIconImage(icon.getImage());

  }
}

