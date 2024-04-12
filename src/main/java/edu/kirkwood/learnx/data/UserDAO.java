package edu.kirkwood.learnx.data;

import edu.kirkwood.learnx.models.User;
import edu.kirkwood.shared.CommunicationService;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO extends Database {

    public static List<User> getAll(){
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_all_users()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                char[] password = resultSet.getString("password").toCharArray();
                String language = resultSet.getString("language");
                String status = resultSet.getString("status");
                String privileges = resultSet.getString("privileges");
                Instant created_at = resultSet.getTimestamp("created_at").toInstant();
                Instant last_logged_in = resultSet.getTimestamp("last_logged_in").toInstant();
                Instant updated_at = resultSet.getTimestamp("updated_at").toInstant();
                User user = new User(id, firstName, lastName, email, phone, password, language, status, privileges, created_at, last_logged_in, updated_at);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static void delete(User user) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_delete_user(?)}")) {
                    statement.setInt(1, user.getId());
                    statement.execute();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User get(String email){
        User user = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_user(?)}");
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phone = resultSet.getString("phone");
                char[] password = resultSet.getString("password").toCharArray();
                String language = resultSet.getString("language");
                String status = resultSet.getString("status");
                String privileges = resultSet.getString("privileges");
                Instant created_at = resultSet.getTimestamp("created_at").toInstant();
                Instant last_logged_in = resultSet.getTimestamp("last_logged_in").toInstant();
                Instant updated_at = resultSet.getTimestamp("updated_at").toInstant();
                user = new User(id, firstName, lastName, email, phone, password, language, status, privileges, created_at, last_logged_in, updated_at);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static User get(int id) {
        User user = null;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_get_user_by_id(?)}")) {
                    statement.setInt(1, id);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            String firstName = resultSet.getString("first_name");
                            String lastName = resultSet.getString("last_name");
                            String email = resultSet.getString("email");
                            String phone = resultSet.getString("phone");
                            char[] password = resultSet.getString("password").toCharArray();
                            String language = resultSet.getString("language");
                            String status = resultSet.getString("status");
                            String privileges = resultSet.getString("privileges");
                            Instant created_at = resultSet.getTimestamp("created_at").toInstant();
                            Instant last_logged_in = resultSet.getTimestamp("last_logged_in").toInstant();
                            Instant updated_at = resultSet.getTimestamp("updated_at").toInstant();
                            user = new User(id, firstName, lastName, email, phone, password, language, status, privileges, created_at, last_logged_in, updated_at);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static List<String> add(User user) {
        List<String> results = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_add_user(?, ?)}")) {
                    statement.setString(1, user.getEmail());
                    String encryptedPassword = BCrypt.hashpw(new String(user.getPassword()), BCrypt.gensalt(12));
                    statement.setString(2, encryptedPassword);
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected == 1) {
                        try (CallableStatement statement2 = connection.prepareCall("{CALL sp_get_2fa_code(?)}")) {
                            statement2.setString(1, user.getEmail());
                            try(ResultSet resultSet = statement2.executeQuery()) {
                                if(resultSet.next()) {
                                    String code = resultSet.getString("code");
                                    String created_at = resultSet.getTimestamp("created_at").toString();
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
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_user(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                    statement.setInt(1, user.getId());
                    statement.setString(2, user.getFirstName());
                    statement.setString(3, user.getLastName());
                    statement.setString(4, user.getEmail());
                    statement.setString(5, user.getPhone());
                    statement.setString(6, user.getLanguage());
                    statement.setString(7, user.getStatus());
                    statement.setString(8, user.getPrivileges());
                    statement.setTimestamp(9, Timestamp.from(user.getLast_logged_in()));
                    statement.executeUpdate();
                    // TODO: Return the rows affected and throw exception if user not updated
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                return CommunicationService.sendPasswordResetEmail(email, uuid, req);
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
                            Instant created_at = resultSet.getTimestamp("created_at").toInstant();
                            Duration duration = Duration.between(created_at, now);
                            long minutesElapsed = duration.toMinutes();
                            if(minutesElapsed < 30) {
                                email = resultSet.getString("email");
                            }
                            int id = resultSet.getInt("id");
                            try (CallableStatement statement2 = connection.prepareCall("{CALL sp_delete_password_reset(?)}")) {
                                statement2.setInt(1, id);
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
                if(rowsAffected == 1) {
                    try(CallableStatement statement2 = connection.prepareCall("{CALL sp_delete_password_reset(?)}")
                    ) {
                        statement2.setString(1, email);
                        statement2.executeUpdate();
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
