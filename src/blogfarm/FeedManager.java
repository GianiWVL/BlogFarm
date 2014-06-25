package blogfarm;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedManager extends JDialog {

    private String[] listData;
    private JList listFeed;
    private JScrollPane listScroller;
    private final JPanel contentList = new JPanel();
    private final JTextField txtFeedURL = new JTextField();
    
    public FeedManager(){
        
        JPanel content = new JPanel();
        JPanel contentFirstRow = new JPanel();
        JPanel contentButtons = new JPanel();
        
        JButton btnOK = new JButton("OK");
        JButton btnAdd = new JButton("Add Feed");
        JButton btnDelete = new JButton("Delete Feed");
        
        loadListbox();  
        
        
        JLabel lblFeedURL = new JLabel("Feed URL:");
        
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
                    System.out.println("hah");
                    addFeed();
                    break;
                case "Delete":
                    break;
                }
            }
        };

        btnOK.setActionCommand("OK");
        btnOK.addActionListener(action);
        
        btnAdd.setActionCommand("AddFeed");
        btnAdd.addActionListener(action);
                
        btnDelete.setActionCommand("Delete");
        btnDelete.addActionListener(action);
  
        contentFirstRow.setLayout(new BoxLayout(contentFirstRow, BoxLayout.LINE_AXIS));
        
        contentFirstRow.add(Box.createHorizontalGlue());
        contentFirstRow.add(Box.createRigidArea(new Dimension(5, 0)));
        contentFirstRow.add(lblFeedURL);
        contentFirstRow.add(Box.createRigidArea(new Dimension(5, 0)));
        contentFirstRow.add(txtFeedURL);
        contentFirstRow.add(Box.createRigidArea(new Dimension(5, 0)));
        contentFirstRow.add(btnAdd);
        
        contentButtons.setLayout(new BoxLayout(contentButtons, BoxLayout.LINE_AXIS));
        contentButtons.add(btnOK);
        contentButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        contentButtons.add(btnDelete);
        
        this.contentList.setLayout(new BoxLayout(contentList,BoxLayout.PAGE_AXIS));      
        
        //ToDo make method to reload ui
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(contentFirstRow);
        content.add(contentList);
        content.add(contentButtons);
        
        add(content,BorderLayout.NORTH);        
        
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
        }
    }
}
