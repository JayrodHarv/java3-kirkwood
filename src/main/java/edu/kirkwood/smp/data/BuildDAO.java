package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.Build;
import edu.kirkwood.smp.models.Tag;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class BuildDAO {
    public static List<Build> getAll() {
        List<Build> builds = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_builds()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
                String BuildID = resultSet.getString("BuildID");
                String UserID = resultSet.getString("UserID");
                List<Tag> Tags = new ArrayList<>();
                try(CallableStatement statement2 = connection.prepareCall("{CALL sp_get_build_tags(?)}");
                ) {
                    statement2.setString(1, BuildID);
                    try(ResultSet resultSet2 = statement2.executeQuery()){
                        while(resultSet2.next()) {
                            String tagID = resultSet2.getString("TagID");
                            String description = resultSet2.getString("Description");
                            Tags.add(new Tag(tagID, description));
                        }
                    }
                }
                byte[] Image = resultSet.getBytes("Image");
                Instant CreatedAt = resultSet.getTimestamp("CreatedAt").toInstant();
                String Description = resultSet.getString("Description");
                Build build = new Build(BuildID, UserID, Tags, Image, CreatedAt, Description);
                builds.add(build);
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
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_build(?, ?, ?, ?)}")) {
                    statement.setString(1, build.getBuildID());
                    statement.setString(2, build.getUserID());
                    statement.setBytes(3, build.getImage());
                    statement.setString(4, build.getDescription());
                    // Tag shenanigans
                    List<String> tagIDs = new ArrayList<>();
                    for (Tag tag : build.getTags()) {
                        tagIDs.add(tag.getTagID());
                    }
                    String tagStr = String.join(",", tagIDs);
                    statement.setString(5, tagStr);
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected != 0) {
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
