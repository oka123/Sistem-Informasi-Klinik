package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {

    private static final String URL =
        "jdbc:mysql://klinik-klinik.c.aivencloud.com:12241/klinik";
    private static final String USER = "avnadmin";
    private static final String PASS = "AVNS_5e6NcoUQA0Q-vk_YdAs";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
