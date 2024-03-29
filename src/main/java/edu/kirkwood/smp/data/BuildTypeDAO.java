package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.BuildType;
import edu.kirkwood.smp.models.World;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
                String BuildType = resultSet.getString("BuildType");
                String Description = resultSet.getString("Description");
                BuildType buildType = new BuildType(BuildType, Description);
                buildTypes.add(buildType);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return buildTypes;
    }
}
