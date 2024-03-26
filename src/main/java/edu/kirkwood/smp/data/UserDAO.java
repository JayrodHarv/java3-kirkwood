package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class UserDAO {
//    public static List<User> getAll(){
//        List<User> users = new ArrayList<>();
//        try(Connection connection = getConnection();
//            CallableStatement statement = connection.prepareCall("{CALL sp_get_all_users()}");
//            ResultSet resultSet = statement.executeQuery()
//        ) {
//            while(resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String firstName = resultSet.getString("first_name");
//                String lastName = resultSet.getString("last_name");
//                String email = resultSet.getString("email");
//                String phone = resultSet.getString("phone");
//                char[] password = resultSet.getString("password").toCharArray();
//                String language = resultSet.getString("language");
//                String status = resultSet.getString("status");
//                String privileges = resultSet.getString("privileges");
//                Instant created_at = resultSet.getTimestamp("created_at").toInstant();
//                Instant last_logged_in = resultSet.getTimestamp("last_logged_in").toInstant();
//                Instant updated_at = resultSet.getTimestamp("updated_at").toInstant();
//                User user = new User(id, firstName, lastName, email, phone, password, language, status, privileges, created_at, last_logged_in, updated_at);
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            System.out.println("Likely bad SQL query");
//            System.out.println(e.getMessage());
//        }
//        return users;
//    }

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
                byte[] Pfp = resultSet.getBytes("Pfp");
                user = new User(UserID, Password, DisplayName, Language, Status, Role, CreatedAt, LastLoggedIn, UpdatedAt, Pfp);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static List<String> add(User user) {
        List<String> results = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_user(?, ?, ?)}")) {
                    statement.setString(1, user.getUserID());
                    String encryptedPassword = BCrypt.hashpw(new String(user.getPassword()), BCrypt.gensalt(12));
                    statement.setString(2, encryptedPassword);
                    statement.setString(3, user.getDisplayName());
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
