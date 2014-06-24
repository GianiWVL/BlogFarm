package blogfarm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenu extends JFrame {
    
    public MainMenu(){
       
        JButton btnFeedURL = new JButton("Feed Manager");
        
        ActionListener action = (ActionEvent e) -> {
            if("FeedManager".equals(e.getActionCommand())){
                FeedManager feedmgr = new FeedManager();
            }
        };

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
       
        btnFeedURL.setActionCommand("FeedManager");
        btnFeedURL.addActionListener(action);
 
        getContentPane().add(btnFeedURL);
        
        pack();
        setVisible(true);
    }
    
     
    
}
