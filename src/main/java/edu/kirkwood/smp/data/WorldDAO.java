package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.Build;
import edu.kirkwood.smp.models.World;

import javax.sql.rowset.serial.SerialBlob;
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

    public static World get(String worldID) {
        World world = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_world(?)}")
        ) {
            statement.setString(1, worldID);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String WorldID = resultSet.getString("WorldID");
                Date DateStarted = resultSet.getDate("DateStarted");
                String Description = resultSet.getString("Description");
                world = new World(WorldID, DateStarted, Description);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return world;
    }

    public static boolean add(World world) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_world(?,?,?)}")) {
                    statement.setString(1, world.getWorldID());
                    statement.setDate(2, new java.sql.Date(world.getDateStarted().getTime()));
                    statement.setString(3, world.getDescription());
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected > 0) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean edit(World world, String oldWorldID) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_world(?,?,?,?)}")) {
                    statement.setString(1, world.getWorldID());
                    statement.setDate(2, new java.sql.Date(world.getDateStarted().getTime()));
                    statement.setString(3, world.getDescription());
                    statement.setString(4, oldWorldID);
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected > 0) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean delete(String worldID) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_delete_world(?)}")) {
                    statement.setString(1, worldID);
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected > 0) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
