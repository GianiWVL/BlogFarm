package blogfarm;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedManager extends JDialog {

    public FeedManager(){
        
        Database db = new Database("jdbc:derby://localhost:1527/BlogFarm","Giani","lekismeki");
        db.connect();
        
        JPanel content = new JPanel();
        JPanel contentFirstRow = new JPanel();
        JPanel contentList = new JPanel();
        JPanel contentButtons = new JPanel();
        
        JButton btnOK = new JButton("OK");
        JButton btnAdd = new JButton("Add Feed");
        JButton btnDelete = new JButton("Delete Feed");
        
        String[][] listDataToConvert = db.execSelectQuery("SELECT FEEDURL FROM TBLFEED");
        String[] listData = new String[listDataToConvert.length];
        for(int i = 0; i < listDataToConvert.length; i++){
            listData[i] = listDataToConvert[i][0];
        }
        
        JList listFeed = new JList(listData);
        JScrollPane listScroller = new JScrollPane(listFeed);
        
        JTextField txtFeedURL = new JTextField();
        JLabel lblFeedURL = new JLabel("Feed URL:");
        
        this.setPreferredSize(new Dimension(500,255));
        this.setResizable(false);
        this.setSize(500, 500);

        ActionListener action = (ActionEvent e) -> {
            if("OK".equals(e.getActionCommand())){
                this.dispose();
            }
            System.out.println(e.getActionCommand());
        };

        btnOK.setActionCommand("OK");
        btnOK.addActionListener(action);
        
        btnAdd.setActionCommand("AddFeed");
        btnAdd.addActionListener(action);
                
        btnDelete.setActionCommand("Delete");
        btnDelete.addActionListener(action);
               
        listFeed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listFeed.setLayoutOrientation(JList.VERTICAL);
        listFeed.setVisibleRowCount(10);
        
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
        
        contentList.setLayout(new BoxLayout(contentList,BoxLayout.PAGE_AXIS));
        contentList.add(listScroller);
        
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(contentFirstRow);
        content.add(contentList);
        content.add(contentButtons);
        
        add(content,BorderLayout.NORTH);        
        
        pack();
        setVisible(true);
               
    }
}
