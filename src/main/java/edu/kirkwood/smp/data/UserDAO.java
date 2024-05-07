package edu.kirkwood.smp.data;

import edu.kirkwood.shared.CommunicationService;
import edu.kirkwood.shared.ImageHelper;
import edu.kirkwood.smp.models.Role;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.smp.models.UserVM;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                String Role = resultSet.getString("RoleID");
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
                String RoleID = resultSet.getString("RoleID");
                Instant CreatedAt = resultSet.getTimestamp("CreatedAt").toInstant();
                Instant LastLoggedIn = resultSet.getTimestamp("LastLoggedIn").toInstant();
                Instant UpdatedAt = resultSet.getTimestamp("UpdatedAt").toInstant();

                Blob blob = resultSet.getBlob("Pfp");
                InputStream inputStream = blob.getBinaryStream();
                String imageType = URLConnection.guessContentTypeFromStream(inputStream);
                byte[] Pfp = ImageHelper.getImageBytesFromInputStream(inputStream);
                String base64Image = ImageHelper.getBase64Image(imageType, Pfp);
                user = new User(UserID, Password, DisplayName, Language, Status, RoleID, CreatedAt, LastLoggedIn, UpdatedAt, Pfp);
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

    public static UserVM getVM(String email) {
        UserVM user = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_userVM(?)}");
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String UserID = resultSet.getString("UserID");
                String DisplayName = resultSet.getString("DisplayName");
                String Language = resultSet.getString("Language");
                String Status = resultSet.getString("Status");
                Instant CreatedAt = resultSet.getTimestamp("CreatedAt").toInstant();
                Instant LastLoggedIn = resultSet.getTimestamp("LastLoggedIn").toInstant();
                Instant UpdatedAt = resultSet.getTimestamp("UpdatedAt").toInstant();

                Blob blob = resultSet.getBlob("Pfp");
                InputStream inputStream = blob.getBinaryStream();
                String imageType = URLConnection.guessContentTypeFromStream(inputStream);
                byte[] Pfp = ImageHelper.getImageBytesFromInputStream(inputStream);
                String base64Image = ImageHelper.getBase64Image(imageType, Pfp);

                // Role
                Role role = new Role();
                role.setRoleID(resultSet.getString("RoleID"));
                // Build Permissions
                role.setCanAddBuilds(resultSet.getBoolean("CanAddBuilds"));
                role.setCanEditAllBuilds(resultSet.getBoolean("CanEditAllBuilds"));
                role.setCanDeleteAllBuilds(resultSet.getBoolean("CanDeleteAllBuilds"));
                // Build Type Permissions
                role.setCanViewBuildTypes(resultSet.getBoolean("CanViewBuildTypes"));
                role.setCanAddBuildTypes(resultSet.getBoolean("CanAddBuildTypes"));
                role.setCanEditBuildTypes(resultSet.getBoolean("CanEditBuildTypes"));
                role.setCanDeleteBuildTypes(resultSet.getBoolean("CanDeleteBuildTypes"));
                // World Permissions
                role.setCanViewWorlds(resultSet.getBoolean("CanViewWorlds"));
                role.setCanAddWorlds(resultSet.getBoolean("CanAddWorlds"));
                role.setCanEditWorlds(resultSet.getBoolean("CanEditWorlds"));
                role.setCanDeleteWorlds(resultSet.getBoolean("CanDeleteWorlds"));
                // Vote Permissions
                role.setCanViewAllVotes(resultSet.getBoolean("CanViewAllVotes"));
                role.setCanAddVotes(resultSet.getBoolean("CanAddVotes"));
                role.setCanEditAllVotes(resultSet.getBoolean("CanEditAllVotes"));
                role.setCanDeleteAllVotes(resultSet.getBoolean("CanDeleteAllVotes"));
                // Role Permissions
                role.setCanViewRoles(resultSet.getBoolean("CanViewRoles"));
                role.setCanAddRoles(resultSet.getBoolean("CanAddRoles"));
                role.setCanEditRoles(resultSet.getBoolean("CanEditRoles"));
                role.setCanDeleteRoles(resultSet.getBoolean("CanDeleteRoles"));
                // User Permissions
                role.setCanViewUsers(resultSet.getBoolean("CanViewUsers"));
                role.setCanAddUsers(resultSet.getBoolean("CanAddUsers"));
                role.setCanEditUsers(resultSet.getBoolean("CanEditUsers"));
                role.setCanBanUsers(resultSet.getBoolean("CanBanUsers"));

                role.setDescription(resultSet.getString("Description"));

                user = new UserVM(UserID, DisplayName, Language, Status, role, CreatedAt, LastLoggedIn, UpdatedAt, Pfp);
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

    public static boolean update(User user) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_user(?,?,?,?,?,?,?)}")) {
                    statement.setString(1, user.getUserID());
                    statement.setString(2, user.getDisplayName());
                    statement.setBlob(3, new SerialBlob(user.getPfp()));
                    statement.setString(4, user.getLanguage());
                    statement.setString(5, user.getStatus());
                    statement.setString(6, user.getRole());
                    statement.setTimestamp(7, Timestamp.from(user.getLastLoggedIn()));
                    int rows = statement.executeUpdate();
                    return rows > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean delete(String userID) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_delete_user(?)}")) {
                    statement.setString(1, userID);
                    return 0 < statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean passwordReset(String email, HttpServletRequest req) {
        User userFromDB = get(email);
        if(userFromDB != null) {
            try(Connection conn = getConnection()) {
                String uuid = String.valueOf(UUID.randomUUID());
                //TODO: check database if uuid already exists
                try(CallableStatement statement = conn.prepareCall("{CALL sp_add_password_reset(?,?)}")) {
                    statement.setString(1, email);
                    statement.setString(2, uuid);
                    statement.executeUpdate();
                }
                return CommunicationService.sendPasswordResetEmailSMP(email, uuid, req);
            } catch (SQLException e) {
                System.out.println("Likely bad SQL query");
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public static String getPasswordReset(String token) {
        String email = "";
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_get_password_reset(?)}")) {
                    statement.setString(1, token);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            Instant now = Instant.now();
                            Instant created_at = resultSet.getTimestamp("CreatedAt").toInstant();
                            Duration duration = Duration.between(created_at, now);
                            long minutesElapsed = duration.toMinutes();
                            if(minutesElapsed < 30) {
                                email = resultSet.getString("UserID");
                            }
                            int resetID = resultSet.getInt("ResetID");
                            try (CallableStatement statement2 = connection.prepareCall("{CALL sp_delete_password_reset(?)}")) {
                                statement2.setInt(1, resetID);
                                statement2.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return email;
    }

    public static boolean resetPassword(String email, String password) {
        int rowsAffected = 0;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_user_password(?, ?)}")) {
                    statement.setString(1, email);
                    String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                    statement.setString(2, encryptedPassword);
                    rowsAffected = statement.executeUpdate();
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
