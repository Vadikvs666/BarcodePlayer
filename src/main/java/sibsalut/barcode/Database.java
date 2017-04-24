/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vadim
 */
public class Database {

    private static Database instance;
    private Connection conn;

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        connect();
        createBarcodeTable();
    }

    private void connect() {
        conn = null;
        try {
            String url = "jdbc:sqlite:barcode.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }

    private void createBarcodeTable() {
        if (conn != null) {
            String sql = "CREATE TABLE IF NOT EXISTS "
                    + "barcode ( id integer PRIMARY KEY , "
                    + "barcode text, "
                    + "title text, "
                    + "price real, "
                    + "count integer, "
                    + "video text )";
            try {
                Statement stmt = conn.createStatement();
                Boolean res=stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void insert(Barcode barcode){
        String sql = "INSERT INTO barcode (barcode, title, price, count, video) "
                + "VALUES(?, ?, ?, ?, ?, )";
        try{
            final PreparedStatement pStmtt = conn.prepareStatement(sql);
            pStmtt.setString(1, barcode.getBarcode());
            pStmtt.setString(2, barcode.getTitle());
            pStmtt.setString(3, String.valueOf(barcode.getPrice()));
            pStmtt.setString(4, String.valueOf(barcode.getCount()));
            pStmtt.setString(5, barcode.getVideo());
            pStmtt.execute();
        }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
       
    }
    public Barcode selectByBarcode(String barcode){
        
        return new Barcode();
    }
    public Barcode selectById(int id){
        
        return new Barcode();
    }
    public ArrayList<Barcode> select()
    {
        ArrayList<Barcode> list = new ArrayList<Barcode>();
        return list;
    }
    public void update(Barcode barcode){
        
    }
    public void delete(Barcode barcode){
        
    }
}
