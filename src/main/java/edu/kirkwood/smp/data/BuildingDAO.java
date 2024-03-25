package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.Building;
import edu.kirkwood.smp.models.Tag;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class BuildingDAO {
    public static List<Building> getAll() {
        List<Building> buildings = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_buildings()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
                int BuildingID = resultSet.getInt("BuildingID");
                int UserID = resultSet.getInt("UserID");
                List<Tag> Tags = new ArrayList<Tag>();
                try(Connection connection2 = getConnection();
                    CallableStatement statement2 = connection.prepareCall("{CALL sp_get_building_tags(?)}");
                ) {
                    statement2.setInt(1, BuildingID);
                    ResultSet resultSet2 = statement2.executeQuery();
                    while(resultSet.next()) {
                        int buildingid = resultSet.getInt("BuildingID");
                        String tagid = resultSet.getString("TagID");
                        Tags.add(new Tag(buildingid, tagid));
                    }
                }
                String Name = resultSet.getString("Name");
                byte[] Image = resultSet.getBytes("Image");
                Instant CreatedAt = resultSet.getTimestamp("CreatedAt").toInstant();
                String Description = resultSet.getString("Description");
                Building building = new Building(BuildingID, UserID, Tags, Name, Image, CreatedAt, Description);
                buildings.add(building);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return buildings;
    }
}
