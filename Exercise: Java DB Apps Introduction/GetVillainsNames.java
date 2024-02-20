package DBAppsIntroductionExercise;

import java.sql.*;
import java.util.Properties;

public class minionsMain {
    public static void main(String[] args) throws SQLException {

        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "deadpool7");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", credentials);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                "v.name, COUNT(mv.minion_id) as count " +
                "FROM " +
                "villains v " +
                "JOIN " +
                "minions_villains mv ON v.id = mv.villain_id " +
                "GROUP BY v.name " +
                "HAVING count > 15 " +
                "ORDER BY count DESC; ");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.printf("%s %d", resultSet.getString(1),resultSet.getInt(2)).append(System.lineSeparator());
        }

    }
}
