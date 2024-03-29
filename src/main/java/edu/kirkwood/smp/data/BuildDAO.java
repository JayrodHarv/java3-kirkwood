package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.*;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class BuildDAO {
    public static List<BuildVM> getAll(int limit, int offset, String worldID, String buildTypeID, String userDisplayName) {
        List<BuildVM> builds = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_builds(?,?,?,?,?)}");
        ) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            statement.setString(3, worldID);
            statement.setString(4, buildTypeID);
            statement.setString(5, userDisplayName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Build
                    String BuildID = resultSet.getString("BuildID");
                    byte[] Image = resultSet.getBytes("Image");
                    Date DateBuilt = resultSet.getDate("DateBuilt");
                    Instant CreatedAt = resultSet.getTimestamp("CreatedAt").toInstant();
                    String BuildDescription = resultSet.getString("build_description");

                    // User
                    String UserID = resultSet.getString("UserID");
                    String UserDisplayName = resultSet.getString("DisplayName");
                    byte[] UserPfp = resultSet.getBytes("Pfp");
                    User user = new User(UserID, UserDisplayName, UserPfp);

                    // World
                    String WorldID = resultSet.getString("WorldID");
                    Date WorldDateStarted = resultSet.getDate("DateStarted");
                    String WorldDescription = resultSet.getString("world_description");
                    World world = new World(WorldID, WorldDateStarted, WorldDescription);

                    // BuildType
                    String BuildTypeID = resultSet.getString("BuildType");
                    String BuildTypeDescription = resultSet.getString("buildtype_description");
                    BuildType buildType = new BuildType(BuildTypeID, BuildTypeDescription);

                    BuildVM buildVM = new BuildVM(BuildID, Image, DateBuilt, CreatedAt, BuildDescription, user, world, buildType);
                    builds.add(buildVM);
                }
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return builds;
    }

    public static boolean add(Build build) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_build(?,?,?,?,?,?,?)}")) {
                    statement.setString(1, build.getBuildID());
                    statement.setString(2, build.getUserID());
                    statement.setBytes(3, build.getImage());
                    statement.setString(4, build.getWorldID());
                    statement.setString(5, build.getBuildTypeID());
                    statement.setDate(6, new Date(build.getDateBuilt().getTime()));
                    statement.setString(7, build.getDescription());
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected == 1) {
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
