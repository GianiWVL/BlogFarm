package blogfarm;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedManager extends JDialog {

    private String[] listData;
    
    private JList listFeed;
    
    private JScrollPane listScroller;
    
    private JPanel contentList = new JPanel();
    private JPanel content = new JPanel();
    private final JTextField txtFeedURL = new JTextField();    
    private final JPanel contentFirstRow = new JPanel();
    private final JPanel contentButtons = new JPanel();
    
    private final JLabel lblFeedURL = new JLabel("Feed URL:");
    
    private final JButton btnOK = new JButton("OK");
    private final JButton btnAdd = new JButton("Add Feed");
    private final JButton btnDelete = new JButton("Delete Feed");
        
    public FeedManager(){

        loadListbox();  

        this.setPreferredSize(new Dimension(500,255));
        this.setResizable(false);
        this.setSize(500, 500);

        ActionListener action = (ActionEvent e) -> {
            if(null != e.getActionCommand()){
                switch (e.getActionCommand()) {
                case "OK":
                    this.dispose();
                    break;
                case "AddFeed":                   
                    addFeed();
                    txtFeedURL.setText("");
                    break;
                case "Delete":
                    removeFromList(this.listFeed.getSelectedValue().toString());
                    break;
                }
            }
        };

        this.btnOK.setActionCommand("OK");
        this.btnOK.addActionListener(action);
        
        this.btnAdd.setActionCommand("AddFeed");
        this.btnAdd.addActionListener(action);
                
        this.btnDelete.setActionCommand("Delete");
        this.btnDelete.addActionListener(action);
  
        this.contentFirstRow.setLayout(new BoxLayout(this.contentFirstRow, BoxLayout.LINE_AXIS));
        
        this.contentFirstRow.add(Box.createHorizontalGlue());
        this.contentFirstRow.add(Box.createRigidArea(new Dimension(5, 0)));
        this.contentFirstRow.add(this.lblFeedURL);
        this.contentFirstRow.add(Box.createRigidArea(new Dimension(5, 0)));
        this.contentFirstRow.add(this.txtFeedURL);
        this.contentFirstRow.add(Box.createRigidArea(new Dimension(5, 0)));
        this.contentFirstRow.add(this.btnAdd);
        
        this.contentButtons.setLayout(new BoxLayout(this.contentButtons, BoxLayout.LINE_AXIS));
        this.contentButtons.add(this.btnOK);
        this.contentButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        this.contentButtons.add(this.btnDelete);

        buildLayout();                  
    }
    
    private void buildLayout(){
        this.getContentPane().removeAll();
        
        this.content = new JPanel();
        this.content.setLayout(new BoxLayout(this.content, BoxLayout.PAGE_AXIS));
        this.content.add(this.contentFirstRow);
        this.content.add(this.contentList);
        this.content.add(this.contentButtons);
        
        add(this.content);
        
        pack();
        setVisible(true);
        
    }
    
    private void loadListbox(){
        Database db = new Database("jdbc:derby://localhost:1527/BlogFarm","Giani","lekismeki");
        db.connect();
        
        String[][] listDataToConvert = db.execSelectQuery("SELECT FEEDURL FROM TBLFEED");
        
        db.close();
        
        if(listDataToConvert != null){

            this.listData = new String[listDataToConvert.length];
            
            for(int i = 0; i < listDataToConvert.length; i++){
                this.listData[i] = listDataToConvert[i][0];
            }
            
            this.contentList = new JPanel();
            this.contentList.setLayout(new BoxLayout(contentList,BoxLayout.PAGE_AXIS));
            
            this.listFeed = new JList(listData);
            this.listScroller = new JScrollPane(this.listFeed);
            
            this.listFeed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.listFeed.setLayoutOrientation(JList.VERTICAL);
            this.listFeed.setVisibleRowCount(10);
            
            this.contentList.add(listScroller);
        }
    }
    
    private void addFeed(){
        if(txtFeedURL.getText().length() != 0){
            Database db = new Database("jdbc:derby://localhost:1527/BlogFarm","Giani","lekismeki");
            db.connect();
            db.execInsertQuery("INSERT INTO TBLFEED(FEEDURL) VALUES('"+ txtFeedURL.getText() +"')");
            db.close();
            
            loadListbox();
            
            buildLayout();
        }
    }
    
    private void removeFromList(String item){
        Database db = new Database("jdbc:derby://localhost:1527/BlogFarm","Giani","lekismeki");
        db.connect();
        db.execDeleteQuery("DELETE FROM TBLFEED WHERE FEEDURL = '"+ item + "'");
        db.close();
        
        loadListbox();
            
        buildLayout();
    }
}