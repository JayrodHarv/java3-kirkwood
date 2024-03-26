package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.Tag;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class TagDAO {
    public static List<Tag> getAll() {
        List<Tag> tags = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_tags()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
                String TagID = resultSet.getString("TagID");
                String Description = resultSet.getString("Description");
                Tag tag = new Tag(TagID, Description);
                tags.add(tag);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return tags;
    }
}
