package DBAppsIntroductionExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getMinionNames {
    private static final DBTools DB_TOOLS = new DBTools("root", "   ", "minions_db");
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        extracted();

    }

    private static void extracted() throws SQLException, IOException {
        int villainId = Integer.parseInt(READER.readLine());
        String sql = "SELECT name FROM villains WHERE id = ?";
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            System.out.printf("No villain with ID %d exists in database", villainId);
            return;
        }
        String villainName = resultSet.getString("name");
        System.out.println("Villain name:" + villainName);
        sql = "SELECT * FROM minions m " + "JOIN minions_villains mv ON m.id = mv.minion_id "
                + "WHERE villain_id = ?";
        preparedStatement = DB_TOOLS.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, villainId);
        resultSet = preparedStatement.executeQuery();
        int index = 0;
        while (resultSet.next()) {
            System.out.printf("%d, %s %d", ++index, resultSet.getString("name"), resultSet.getInt("age")).append(System.lineSeparator());
        }
    }
}
