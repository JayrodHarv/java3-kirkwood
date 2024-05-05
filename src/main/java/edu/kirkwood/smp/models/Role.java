package edu.kirkwood.smp.models;

public class Role {
    private String RoleID;
    // Build Permissions
    private boolean CanAddBuilds;
    private boolean CanEditAllBuilds;
    private boolean CanDeleteAllBuilds;
    // Build Type Permissions
    private boolean CanViewBuildTypes;
    private boolean CanAddBuildTypes;
    private boolean CanEditBuildTypes;
    private boolean CanDeleteBuildTypes;
    // World Permissions
    private boolean CanViewWorlds;
    private boolean CanAddWorlds;
    private boolean CanEditWorlds;
    private boolean CanDeleteWorlds;
    // Vote Permissions
    private boolean CanViewAllVotes;
    private boolean CanAddVotes;
    private boolean CanEditAllVotes;
    private boolean CanDeleteAllVotes;
    // Role Permissions
    private boolean CanViewRoles;
    private boolean CanAddRoles;
    private boolean CanEditRoles;
    private boolean CanDeleteRoles;
    // User Permissions
    private boolean CanViewUsers;
    private boolean CanAddUsers;
    private boolean CanEditUsers;
    private boolean CanBanUsers;

    private String Description;

    public Role() {
    }

    public Role(String roleID, boolean canAddBuilds, boolean canEditAllBuilds, boolean canDeleteAllBuilds, boolean canViewBuildTypes, boolean canAddBuildTypes, boolean canEditBuildTypes, boolean canDeleteBuildTypes, boolean canViewWorlds, boolean canAddWorlds, boolean canEditWorlds, boolean canDeleteWorlds, boolean canViewAllVotes, boolean canAddVotes, boolean canEditAllVotes, boolean canDeleteAllVotes, boolean canViewRoles, boolean canAddRoles, boolean canEditRoles, boolean canDeleteRoles, boolean canViewUsers, boolean canAddUsers, boolean canEditUsers, boolean canBanUsers, String description) {
        RoleID = roleID;
        CanAddBuilds = canAddBuilds;
        CanEditAllBuilds = canEditAllBuilds;
        CanDeleteAllBuilds = canDeleteAllBuilds;
        CanViewBuildTypes = canViewBuildTypes;
        CanAddBuildTypes = canAddBuildTypes;
        CanEditBuildTypes = canEditBuildTypes;
        CanDeleteBuildTypes = canDeleteBuildTypes;
        CanViewWorlds = canViewWorlds;
        CanAddWorlds = canAddWorlds;
        CanEditWorlds = canEditWorlds;
        CanDeleteWorlds = canDeleteWorlds;
        CanViewAllVotes = canViewAllVotes;
        CanAddVotes = canAddVotes;
        CanEditAllVotes = canEditAllVotes;
        CanDeleteAllVotes = canDeleteAllVotes;
        CanViewRoles = canViewRoles;
        CanAddRoles = canAddRoles;
        CanEditRoles = canEditRoles;
        CanDeleteRoles = canDeleteRoles;
        CanViewUsers = canViewUsers;
        CanAddUsers = canAddUsers;
        CanEditUsers = canEditUsers;
        CanBanUsers = canBanUsers;
        Description = description;
    }

    public String getRoleID() {
        return RoleID;
    }

    public void setRoleID(String roleID) {
        RoleID = roleID;
    }

    public boolean canAddBuilds() {
        return CanAddBuilds;
    }

    public void setCanAddBuilds(boolean canAddBuilds) {
        CanAddBuilds = canAddBuilds;
    }

    public boolean canEditAllBuilds() {
        return CanEditAllBuilds;
    }

    public void setCanEditAllBuilds(boolean canEditAllBuilds) {
        CanEditAllBuilds = canEditAllBuilds;
    }

    public boolean canDeleteAllBuilds() {
        return CanDeleteAllBuilds;
    }

    public void setCanDeleteAllBuilds(boolean canDeleteAllBuilds) {
        CanDeleteAllBuilds = canDeleteAllBuilds;
    }

    public boolean canViewBuildTypes() {
        return CanViewBuildTypes;
    }

    public void setCanViewBuildTypes(boolean canViewBuildTypes) {
        CanViewBuildTypes = canViewBuildTypes;
    }

    public boolean canAddBuildTypes() {
        return CanAddBuildTypes;
    }

    public void setCanAddBuildTypes(boolean canAddBuildTypes) {
        CanAddBuildTypes = canAddBuildTypes;
    }

    public boolean canEditBuildTypes() {
        return CanEditBuildTypes;
    }

    public void setCanEditBuildTypes(boolean canEditBuildTypes) {
        CanEditBuildTypes = canEditBuildTypes;
    }

    public boolean canDeleteBuildTypes() {
        return CanDeleteBuildTypes;
    }

    public void setCanDeleteBuildTypes(boolean canDeleteBuildTypes) {
        CanDeleteBuildTypes = canDeleteBuildTypes;
    }

    public boolean canViewWorlds() {
        return CanViewWorlds;
    }

    public void setCanViewWorlds(boolean canViewWorlds) {
        CanViewWorlds = canViewWorlds;
    }

    public boolean canAddWorlds() {
        return CanAddWorlds;
    }

    public void setCanAddWorlds(boolean canAddWorlds) {
        CanAddWorlds = canAddWorlds;
    }

    public boolean canEditWorlds() {
        return CanEditWorlds;
    }

    public void setCanEditWorlds(boolean canEditWorlds) {
        CanEditWorlds = canEditWorlds;
    }

    public boolean canDeleteWorlds() {
        return CanDeleteWorlds;
    }

    public void setCanDeleteWorlds(boolean canDeleteWorlds) {
        CanDeleteWorlds = canDeleteWorlds;
    }

    public boolean canViewAllVotes() {
        return CanViewAllVotes;
    }

    public void setCanViewAllVotes(boolean canViewAllVotes) {
        CanViewAllVotes = canViewAllVotes;
    }

    public boolean canAddVotes() {
        return CanAddVotes;
    }

    public void setCanAddVotes(boolean canAddVotes) {
        CanAddVotes = canAddVotes;
    }

    public boolean canEditAllVotes() {
        return CanEditAllVotes;
    }

    public void setCanEditAllVotes(boolean canEditAllVotes) {
        CanEditAllVotes = canEditAllVotes;
    }

    public boolean canDeleteAllVotes() {
        return CanDeleteAllVotes;
    }

    public void setCanDeleteAllVotes(boolean canDeleteAllVotes) {
        CanDeleteAllVotes = canDeleteAllVotes;
    }

    public boolean canViewRoles() {
        return CanViewRoles;
    }

    public void setCanViewRoles(boolean canViewRoles) {
        CanViewRoles = canViewRoles;
    }

    public boolean canAddRoles() {
        return CanAddRoles;
    }

    public void setCanAddRoles(boolean canAddRoles) {
        CanAddRoles = canAddRoles;
    }

    public boolean canEditRoles() {
        return CanEditRoles;
    }

    public void setCanEditRoles(boolean canEditRoles) {
        CanEditRoles = canEditRoles;
    }

    public boolean canDeleteRoles() {
        return CanDeleteRoles;
    }

    public void setCanDeleteRoles(boolean canDeleteRoles) {
        CanDeleteRoles = canDeleteRoles;
    }

    public boolean canViewUsers() {
        return CanViewUsers;
    }

    public void setCanViewUsers(boolean canViewUsers) {
        CanViewUsers = canViewUsers;
    }

    public boolean canAddUsers() {
        return CanAddUsers;
    }

    public void setCanAddUsers(boolean canAddUsers) {
        CanAddUsers = canAddUsers;
    }

    public boolean canEditUsers() {
        return CanEditUsers;
    }

    public void setCanEditUsers(boolean canEditUsers) {
        CanEditUsers = canEditUsers;
    }

    public boolean canBanUsers() {
        return CanBanUsers;
    }

    public void setCanBanUsers(boolean canBanUsers) {
        CanBanUsers = canBanUsers;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
