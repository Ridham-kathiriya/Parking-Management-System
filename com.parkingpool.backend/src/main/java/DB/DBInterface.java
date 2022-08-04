package DB;
import java.sql.Connection;
import java.sql.SQLException;

public interface DBInterface {
    Connection getDBConnection() throws SQLException;
    void clearDBConnection() throws SQLException;
}
