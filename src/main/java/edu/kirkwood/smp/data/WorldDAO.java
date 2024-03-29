package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.World;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class WorldDAO {
    public static List<World> getAll() {
        List<World> worlds = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_worlds()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
                String WorldID = resultSet.getString("WorldID");
                Date DateStarted = resultSet.getDate("DateStarted");
                String Description = resultSet.getString("Description");
                World world = new World(WorldID, DateStarted, Description);
                worlds.add(world);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return worlds;
    }
}
