package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static final String mySqlUser = "root";
    private static final String mySqlPwd = "secretpassword";
    //private static final String mySqlCS = "jdbc:mysql://localhost:3306/Cars?autoReconnect=true&useSSL=false";
    private static final String mySqlCS = "jdbc:mysql://localhost:3306/Cars";

//    private static final String oracalUser = "root";
//    private static final String oracalPwd = "";
//    private static final String oracalCS = "jdbc:oracle://localhost:1521:xe";


    public static Connection getConnection(DBType dbType) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", mySqlUser);
        properties.setProperty("password", mySqlPwd);
        properties.setProperty("useSSL", "false");
        properties.setProperty("auth_plugin", "mysql_native_password");
        Connection conn = null;
        switch (dbType){
            case MYSQLDB:
                conn = DriverManager.getConnection(mySqlCS, properties);
                break;
//            case ORADB:
//                conn = DriverManager.getConnection(oracalCS, oracalUser, oracalPwd);
//                break;
            default:
                return null;
        }

        return conn;
    }

    public static void showErrorMessage(SQLException e){
        System.err.println("Error :" + e.getMessage());
        System.err.println("Error code: " + e.getErrorCode());
    }
}
