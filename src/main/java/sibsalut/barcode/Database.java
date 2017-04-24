/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
                System.out.println("Executed sql: \n"+sql+"\n Result="+String.valueOf(res));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
