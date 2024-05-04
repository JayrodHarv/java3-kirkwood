package edu.kirkwood.smp.data;

import edu.kirkwood.shared.ImageHelper;
import edu.kirkwood.smp.models.Build;
import edu.kirkwood.smp.models.BuildType;
import edu.kirkwood.smp.models.World;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class BuildTypeDAO {
    public static List<BuildType> getAll() {
        List<BuildType> buildTypes = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_buildtypes()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
                String BuildTypeID = resultSet.getString("BuildTypeID");
                String Description = resultSet.getString("Description");
                BuildType buildType = new BuildType(BuildTypeID, Description);
                buildTypes.add(buildType);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return buildTypes;
    }

    public static BuildType get(String buildTypeID) {
        BuildType buildType = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_buildtype(?)}");
        ) {
            statement.setString(1, buildTypeID);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String BuildTypeID = resultSet.getString("BuildTypeID");
                String Description = resultSet.getString("Description");
                buildType = new BuildType(BuildTypeID, Description);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return buildType;
    }

    public static boolean add(BuildType buildType) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_buildtype(?,?)}")) {
                    statement.setString(1, buildType.getBuildTypeID());
                    statement.setString(2, buildType.getDescription());
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

    public static boolean edit(BuildType buildType) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_buildtype(?,?)}")) {
                    statement.setString(1, buildType.getBuildTypeID());
                    statement.setString(2, buildType.getDescription());
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

    public static boolean delete(String buildTypeID) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_delete_buildtype(?)}")) {
                    statement.setString(1, buildTypeID);
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
