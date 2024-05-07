package edu.kirkwood.smp.data;

import edu.kirkwood.shared.ImageHelper;
import edu.kirkwood.smp.models.*;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
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

                    Blob blob = resultSet.getBlob("Image");
                    InputStream inputStream = blob.getBinaryStream();
                    String imageType = URLConnection.guessContentTypeFromStream(inputStream);

                    byte[] Image = ImageHelper.getImageBytesFromInputStream(inputStream);

                    String base64Image = ImageHelper.getBase64Image(imageType, Image);

                    Date DateBuilt = resultSet.getDate("DateBuilt");
                    String Coordinates = resultSet.getString("Coordinates");
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
                    String BuildTypeID = resultSet.getString("BuildTypeID");
                    String BuildTypeDescription = resultSet.getString("buildtype_description");
                    BuildType buildType = new BuildType(BuildTypeID, BuildTypeDescription);

                    BuildVM buildVM = new BuildVM(BuildID, Image, DateBuilt, Coordinates, CreatedAt, BuildDescription, user, world, buildType);
                    buildVM.setBase64Image(base64Image);
                    builds.add(buildVM);
                }
            } catch (IOException e) {
                System.out.println("Error decoding image from database");
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return builds;
    }

    public static int getBuildCount(String worldID, String buildTypeID, String userDisplayName) {
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_number_of_builds(?,?,?)}");
        ) {
            statement.setString(1, worldID);
            statement.setString(2, buildTypeID);
            statement.setString(3, userDisplayName);
            try(ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    return resultSet.getInt("number_of_builds");
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static Build get(String buildID) {
        Build build = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_build(?)}");
        ) {
            statement.setString(1, buildID);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String BuildID = resultSet.getString("BuildID");
                String UserID = resultSet.getString("UserID");

                Blob blob = resultSet.getBlob("Image");
                InputStream inputStream = blob.getBinaryStream();
                String imageType = URLConnection.guessContentTypeFromStream(inputStream);

                byte[] Image = ImageHelper.getImageBytesFromInputStream(inputStream);

                String base64Image = ImageHelper.getBase64Image(imageType, Image);

                String WorldID = resultSet.getString("WorldID");
                String BuildType = resultSet.getString("BuildTypeID");
                Date DateBuilt = resultSet.getDate("DateBuilt");
                String Coordinates = resultSet.getString("Coordinates");
                Instant CreatedAt = resultSet.getTimestamp("CreatedAt").toInstant();
                String BuildDescription = resultSet.getString("Description");

                build = new Build(BuildID, UserID, Image, WorldID, BuildType, DateBuilt, Coordinates, CreatedAt, BuildDescription);
                build.setBase64Image(base64Image);
            }
            resultSet.close();
        } catch (SQLException | IOException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return build;
    }

    public static boolean add(Build build) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_build(?,?,?,?,?,?,?,?)}")) {
                    statement.setString(1, build.getBuildID());
                    statement.setString(2, build.getUserID());
                    statement.setBlob(3, new SerialBlob(build.getImage()));
                    statement.setString(4, build.getWorldID());
                    statement.setString(5, build.getBuildTypeID());
                    if(build.getDateBuilt() != null) {
                        statement.setDate(6, new Date(build.getDateBuilt().getTime()));
                    } else {
                        statement.setDate(6, null);
                    }
                    statement.setString(7, build.getCoordinates());
                    statement.setString(8, build.getDescription());
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

    public static boolean edit(Build build) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_build(?,?,?,?,?,?,?)}")) {
                    statement.setString(1, build.getBuildID());
                    statement.setBlob(2, new SerialBlob(build.getImage()));
                    statement.setString(3, build.getWorldID());
                    statement.setString(4, build.getBuildTypeID());
                    if(build.getDateBuilt() != null) {
                        statement.setDate(5, new Date(build.getDateBuilt().getTime()));
                    } else {
                        statement.setDate(5, null);
                    }
                    statement.setString(6, build.getCoordinates());
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

    public static boolean delete(String buildID) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_delete_build(?)}")) {
                    statement.setString(1, buildID);
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
