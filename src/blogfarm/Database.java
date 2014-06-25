package blogfarm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    
    private final String host, username, password;
    private String[][] results;
    private Connection con;
    
    public Database(String phost, String pusername, String ppassword){ 
        this.host = phost;
        this.username = pusername;
        this.password = ppassword;       
    }
    
    public void connect(){
        try{
             this.con = DriverManager.getConnection(this.host, this.username, this.password);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void close(){
        try{
            this.con.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public String[][] execSelectQuery(String pSQL){
        String SQL = pSQL;
        
        try{
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(SQL);
            
            rs.last();
            int columns = rs.getMetaData().getColumnCount();
            int rows = rs.getRow();
            
            System.out.println(rows + " " + columns);
            rs.beforeFirst();
            
            if(columns > 1 && rows > 0){
                this.results = new String[rows][columns];
                while(rs.next()){
                    for(int i = 1; i <= columns; i++){
                        results[rs.getRow()-1][i-1] = rs.getString(i);
                        System.out.println(rs.getString(i));
                    }
                }               
            }else if(columns == 1 && rows > 0){
                this.results = new String[rows][1];
                while(rs.next()){
                    results[rs.getRow()-1][0] = rs.getString(1);
                    System.out.println(rs.getString(1));
                }
            }           
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return this.results;
    }
    
    public void execInsertQuery(String pSQL){
        String SQL = pSQL;
        try{
            Statement stmt = this.con.createStatement();
            stmt.execute(SQL);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
}
