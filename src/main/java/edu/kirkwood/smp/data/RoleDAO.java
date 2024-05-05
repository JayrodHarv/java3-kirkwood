package edu.kirkwood.smp.data;

import edu.kirkwood.smp.models.BuildType;
import edu.kirkwood.smp.models.Role;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.kirkwood.smp.data.Database.getConnection;

public class RoleDAO {
    public static List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_all_roles()}");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while(resultSet.next()) {
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

                roles.add(role);
            }
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return roles;
    }

    public static BuildType get(String buildTypeID) {
        BuildType buildType = null;
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_buildtype(?)}");
        ) {
            statement.setString(1, buildTypeID);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String BuildTypeID = resultSet.getString("BuildTypeID");
                String Description = resultSet.getString("Description");
                buildType = new BuildType(BuildTypeID, Description);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Likely bad SQL query");
            System.out.println(e.getMessage());
        }
        return buildType;
    }

    public static boolean add(Role role) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_insert_role(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                    // 24 parameters
                    statement.setString(1, role.getRoleID());
                    // builds
                    statement.setBoolean(2, role.canAddBuilds());
                    statement.setBoolean(3, role.canEditAllBuilds());
                    statement.setBoolean(4, role.canDeleteAllBuilds());
                    // build types
                    statement.setBoolean(5, role.canViewBuildTypes());
                    statement.setBoolean(6, role.canAddBuildTypes());
                    statement.setBoolean(7, role.canEditBuildTypes());
                    statement.setBoolean(8, role.canDeleteBuildTypes());
                    // worlds
                    statement.setBoolean(9, role.canViewWorlds());
                    statement.setBoolean(10, role.canAddWorlds());
                    statement.setBoolean(11, role.canEditWorlds());
                    statement.setBoolean(12, role.canDeleteWorlds());
                    // votes
                    statement.setBoolean(13, role.canViewAllVotes());
                    statement.setBoolean(14, role.canAddVotes());
                    statement.setBoolean(15, role.canEditAllVotes());
                    statement.setBoolean(16, role.canDeleteAllVotes());
                    // roles
                    statement.setBoolean(17, role.canViewRoles());
                    statement.setBoolean(18, role.canAddRoles());
                    statement.setBoolean(19, role.canEditRoles());
                    statement.setBoolean(20, role.canDeleteRoles());
                    // users
                    statement.setBoolean(21, role.canViewUsers());
                    statement.setBoolean(22, role.canAddUsers());
                    statement.setBoolean(23, role.canEditUsers());
                    statement.setBoolean(24, role.canBanUsers());

                    statement.setString(25, role.getDescription());
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected > 0) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean edit(Role role) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_role(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                    // 25 parameters
                    statement.setString(1, role.getRoleID());
                    // builds
                    statement.setBoolean(2, role.canAddBuilds());
                    statement.setBoolean(3, role.canEditAllBuilds());
                    statement.setBoolean(4, role.canDeleteAllBuilds());
                    // build types
                    statement.setBoolean(5, role.canViewBuildTypes());
                    statement.setBoolean(6, role.canAddBuildTypes());
                    statement.setBoolean(7, role.canEditBuildTypes());
                    statement.setBoolean(8, role.canDeleteBuildTypes());
                    // worlds
                    statement.setBoolean(9, role.canViewWorlds());
                    statement.setBoolean(10, role.canAddWorlds());
                    statement.setBoolean(11, role.canEditWorlds());
                    statement.setBoolean(12, role.canDeleteWorlds());
                    // votes
                    statement.setBoolean(13, role.canViewAllVotes());
                    statement.setBoolean(14, role.canAddVotes());
                    statement.setBoolean(15, role.canEditAllVotes());
                    statement.setBoolean(16, role.canDeleteAllVotes());
                    // roles
                    statement.setBoolean(17, role.canViewRoles());
                    statement.setBoolean(18, role.canAddRoles());
                    statement.setBoolean(19, role.canEditRoles());
                    statement.setBoolean(20, role.canDeleteRoles());
                    // users
                    statement.setBoolean(21, role.canViewUsers());
                    statement.setBoolean(22, role.canAddUsers());
                    statement.setBoolean(23, role.canEditUsers());
                    statement.setBoolean(24, role.canBanUsers());

                    statement.setString(25, role.getDescription());
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected > 0) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean delete(String roleID) {
        boolean result = false;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_delete_role(?)}")) {
                    statement.setString(1, roleID);
                    int rowsAffected = statement.executeUpdate();
                    if(rowsAffected > 0) {
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
