package edu.kirkwood.learnx.data;

import edu.kirkwood.learnx.models.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends Database {
    public static void main(String[] args) {
        getAll().forEach(System.out::println);
    }

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
}
