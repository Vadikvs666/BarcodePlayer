/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public Barcode selectByBarcode(String code){
        Barcode barcode =new Barcode();
        String sql = "SELECT * "
                          + "FROM barcode WHERE barcode = ?";
        try{
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);
            ResultSet rs  = pstmt.executeQuery();
            rs.next();
            barcode.setBarcode(rs.getString("barcode"));
            barcode.setCount(rs.getInt("count"));
            barcode.setId(rs.getInt("id"));
            barcode.setPrice(rs.getDouble("price"));
            barcode.setTitle(rs.getString("title"));
            barcode.setVideo(rs.getString("video"));
            
        }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return barcode;
    }
    public Barcode selectById(int id){
        
        Barcode barcode =new Barcode();
        String sql = "SELECT * "
                          + "FROM barcode WHERE id = ?";
        try{
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            rs.next();
            barcode.setBarcode(rs.getString("barcode"));
            barcode.setCount(rs.getInt("count"));
            barcode.setId(rs.getInt("id"));
            barcode.setPrice(rs.getDouble("price"));
            barcode.setTitle(rs.getString("title"));
            barcode.setVideo(rs.getString("video"));
            
        }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return barcode;
    }
    public ArrayList<Barcode> select()
    {
        ArrayList<Barcode> list = new ArrayList<Barcode>();
        return list;
    }
    public void update(Barcode barcode){
        String sql = "UPDATE barcode SET title=?, price=?, count=?, video=? "
                + "WHRERE barcode=?";
        try{
            final PreparedStatement pStmtt = conn.prepareStatement(sql);
            pStmtt.setString(1, barcode.getTitle());
            pStmtt.setDouble(2, barcode.getPrice());
            pStmtt.setInt(3, barcode.getCount());
            pStmtt.setString(4, barcode.getVideo());
            pStmtt.setString(5, barcode.getBarcode());
            pStmtt.execute();
        }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
    public void delete(Barcode barcode){
        
    }
}
