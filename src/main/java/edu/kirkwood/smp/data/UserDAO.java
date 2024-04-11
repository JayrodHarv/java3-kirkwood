package edu.kirkwood.smp.data;

import edu.kirkwood.shared.ImageHelper;
import edu.kirkwood.smp.models.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class UserDAO {
    public static List<User> getAll() {
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_all_users()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
                String UserID = resultSet.getString("UserID");
                String DisplayName = resultSet.getString("DisplayName");
                char[] Password = resultSet.getString("Password").toCharArray();
                String Language = resultSet.getString("Language");
                String Status = resultSet.getString("Status");
                String Role = resultSet.getString("Role");
                Instant CreatedAt = resultSet.getTimestamp("CreatedAt").toInstant();
                Instant LastLoggedIn = resultSet.getTimestamp("LastLoggedIn").toInstant();
                Instant UpdatedAt = resultSet.getTimestamp("UpdatedAt").toInstant();

                Blob blob = resultSet.getBlob("Pfp");
                InputStream inputStream = blob.getBinaryStream();
                String imageType = URLConnection.guessContentTypeFromStream(inputStream);

                byte[] Pfp = ImageHelper.getImageBytesFromInputStream(inputStream);

                String base64Image = ImageHelper.getBase64Image(imageType, Pfp);
                User user = new User(UserID, Password, DisplayName, Language, Status, Role, CreatedAt, LastLoggedIn, UpdatedAt, Pfp);
                user.setBase64Pfp(base64Image);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public static User get(String email) {
        User user = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_user(?)}");
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String UserID = resultSet.getString("UserID");
                String DisplayName = resultSet.getString("DisplayName");
                char[] Password = resultSet.getString("Password").toCharArray();
                String Language = resultSet.getString("Language");
                String Status = resultSet.getString("Status");
                String Role = resultSet.getString("Role");
                Instant CreatedAt = resultSet.getTimestamp("CreatedAt").toInstant();
                Instant LastLoggedIn = resultSet.getTimestamp("LastLoggedIn").toInstant();
                Instant UpdatedAt = resultSet.getTimestamp("UpdatedAt").toInstant();

                Blob blob = resultSet.getBlob("Pfp");
                InputStream inputStream = blob.getBinaryStream();
                String imageType = URLConnection.guessContentTypeFromStream(inputStream);

                byte[] Pfp = ImageHelper.getImageBytesFromInputStream(inputStream);

                String base64Image = ImageHelper.getBase64Image(imageType, Pfp);
                user = new User(UserID, Password, DisplayName, Language, Status, Role, CreatedAt, LastLoggedIn, UpdatedAt, Pfp);
                user.setBase64Pfp(base64Image);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static List<String> add(User user) {
        List<String> results = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_user(?,?,?,?)}")) {
                    statement.setString(1, user.getUserID());
                    String encryptedPassword = BCrypt.hashpw(new String(user.getPassword()), BCrypt.gensalt(12));
                    statement.setString(2, encryptedPassword);
                    statement.setString(3, user.getDisplayName());
                    statement.setBlob(4, new SerialBlob(user.getPfp()));
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected == 1) {
                        try (CallableStatement statement2 = connection.prepareCall("{CALL sp_get_2fa_code(?)}")) {
                            statement2.setString(1, user.getUserID());
                            try(ResultSet resultSet = statement2.executeQuery()) {
                                if(resultSet.next()) {
                                    String code = resultSet.getString("Code");
                                    String created_at = resultSet.getTimestamp("CreatedAt").toString();
                                    results.add(code);
                                    results.add(created_at);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public static void update(User user) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_user(?, ?, ?, ?, ?, ?)}")) {
                    statement.setString(1, user.getUserID());
                    statement.setString(2, user.getDisplayName());
                    statement.setString(3, user.getLanguage());
                    statement.setString(4, user.getStatus());
                    statement.setString(5, user.getRole());
                    statement.setTimestamp(6, Timestamp.from(user.getLastLoggedIn()));
                    statement.executeUpdate();
                    // TODO: Return the rows affected and throw exception if user not updated
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
