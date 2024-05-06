package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.RoleDAO;
import edu.kirkwood.smp.models.Role;
import edu.kirkwood.smp.models.UserVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/edit-role")
public class EditRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        String roleID = req.getParameter("roleID");
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=edit-role");
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canEditRoles()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        if(roleID == null || roleID.isEmpty()) {
            session.setAttribute("flashMessageWarning", "No role id provided");
            resp.sendRedirect("smp-roles");
            return;
        }

        try {
            Role role = RoleDAO.get(roleID);
            if(role != null) {
                req.setAttribute("role", role);
            } else {
                session.setAttribute("flashMessageWarning", "No such role found with the name: " + roleID);
                resp.sendRedirect("smp-roles");
                return;
            }
        } catch(Exception ex) {
            session.setAttribute("flashMessageDanger", "Unable to retrieve role data:\n" + ex.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Role");
        req.getRequestDispatcher("WEB-INF/smp/edit-role.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String roleID = req.getParameter("roleID");
        String oldRoleID = req.getParameter("oldRoleID");
        // build permissions
        String[] canAddBuilds = req.getParameterValues("canAddBuilds") == null ? new String[] {"0"} : req.getParameterValues("canAddBuilds");
        String[] canEditAllBuilds = req.getParameterValues("canEditAllBuilds")  == null ? new String[] {"0"} : req.getParameterValues("canEditAllBuilds");
        String[] canDeleteAllBuilds = req.getParameterValues("canDeleteAllBuilds")  == null ? new String[] {"0"} : req.getParameterValues("canDeleteAllBuilds");
        // build type permissions
        String[] canViewBuildTypes = req.getParameterValues("canViewBuildTypes") == null ? new String[] {"0"} : req.getParameterValues("canViewBuildTypes");
        String[] canAddBuildTypes = req.getParameterValues("canAddBuildTypes") == null ? new String[] {"0"} : req.getParameterValues("canAddBuildTypes");
        String[] canEditBuildTypes = req.getParameterValues("canEditBuildTypes")  == null ? new String[] {"0"} : req.getParameterValues("canEditBuildTypes");
        String[] canDeleteBuildTypes = req.getParameterValues("canDeleteBuildTypes")  == null ? new String[] {"0"} : req.getParameterValues("canDeleteBuildTypes");
        // world permissions
        String[] canViewWorlds = req.getParameterValues("canViewWorlds") == null ? new String[] {"0"} : req.getParameterValues("canViewWorlds");
        String[] canAddWorlds = req.getParameterValues("canAddWorlds") == null ? new String[] {"0"} : req.getParameterValues("canAddWorlds");
        String[] canEditWorlds = req.getParameterValues("canEditWorlds")  == null ? new String[] {"0"} : req.getParameterValues("canEditWorlds");
        String[] canDeleteWorlds = req.getParameterValues("canDeleteWorlds")  == null ? new String[] {"0"} : req.getParameterValues("canDeleteWorlds");
        // vote permissions
        String[] canViewAllVotes = req.getParameterValues("canViewAllVotes") == null ? new String[] {"0"} : req.getParameterValues("canViewAllVotes");
        String[] canAddVotes = req.getParameterValues("canAddVotes") == null ? new String[] {"0"} : req.getParameterValues("canAddVotes");
        String[] canEditAllVotes = req.getParameterValues("canEditAllVotes")  == null ? new String[] {"0"} : req.getParameterValues("canEditAllVotes");
        String[] canDeleteAllVotes = req.getParameterValues("canDeleteAllVotes")  == null ? new String[] {"0"} : req.getParameterValues("canDeleteAllVotes");
        // role permissions
        String[] canViewRoles = req.getParameterValues("canViewRoles") == null ? new String[] {"0"} : req.getParameterValues("canViewRoles");
        String[] canAddRoles = req.getParameterValues("canAddRoles") == null ? new String[] {"0"} : req.getParameterValues("canAddRoles");
        String[] canEditRoles = req.getParameterValues("canEditRoles")  == null ? new String[] {"0"} : req.getParameterValues("canEditRoles");
        String[] canDeleteRoles = req.getParameterValues("canDeleteRoles")  == null ? new String[] {"0"} : req.getParameterValues("canDeleteRoles");
        // user permissions
        String[] canViewUsers = req.getParameterValues("canViewUsers") == null ? new String[] {"0"} : req.getParameterValues("canViewUsers");
        String[] canAddUsers = req.getParameterValues("canAddUsers") == null ? new String[] {"0"} : req.getParameterValues("canAddUsers");
        String[] canEditUsers = req.getParameterValues("canEditUsers")  == null ? new String[] {"0"} : req.getParameterValues("canEditUsers");
        String[] canBanUsers = req.getParameterValues("canBanUsers")  == null ? new String[] {"0"} : req.getParameterValues("canBanUsers");

        String description = req.getParameter("description");

        Map<String, String> results = new HashMap<>();

        results.put("roleID", roleID);
        // build permissions
        results.put("canAddBuilds", canAddBuilds[0]);
        results.put("canEditAllBuilds", canEditAllBuilds[0]);
        results.put("canDeleteAllBuilds", canDeleteAllBuilds[0]);
        // build type permissions
        results.put("canViewBuildTypes", canViewBuildTypes[0]);
        results.put("canAddBuildTypes", canAddBuildTypes[0]);
        results.put("canEditBuildTypes", canEditBuildTypes[0]);
        results.put("canDeleteBuildTypes", canDeleteBuildTypes[0]);
        // world permissions
        results.put("canViewWorlds", canViewWorlds[0]);
        results.put("canAddWorlds", canAddWorlds[0]);
        results.put("canEditWorlds", canEditWorlds[0]);
        results.put("canDeleteWorlds", canDeleteWorlds[0]);
        // vote permissions
        results.put("canViewAllVotes", canViewAllVotes[0]);
        results.put("canAddVotes", canAddVotes[0]);
        results.put("canEditAllVotes", canEditAllVotes[0]);
        results.put("canDeleteAllVotes", canDeleteAllVotes[0]);
        // role permissions
        results.put("canViewRoles", canViewRoles[0]);
        results.put("canAddRoles", canAddRoles[0]);
        results.put("canEditRoles", canEditRoles[0]);
        results.put("canDeleteRoles", canDeleteRoles[0]);
        // user permissions
        results.put("canViewUsers", canViewUsers[0]);
        results.put("canAddUsers", canAddUsers[0]);
        results.put("canEditUsers", canEditUsers[0]);
        results.put("canBanUsers", canBanUsers[0]);

        results.put("description", description);

        if(roleID.isEmpty()) {
            results.put("roleIDError", "You must enter a name for the role");
        }

        if(description.isEmpty()) {
            results.put("descriptionError", "You must enter a description");
        }

        if(!results.containsKey("roleIDError") && !results.containsKey("descriptionError")) {
            try {
                Role role = new Role();
                role.setRoleID(roleID);
                // build permissions
                role.setCanAddBuilds(canAddBuilds[0].equals("yes"));
                role.setCanEditAllBuilds(canEditAllBuilds[0].equals("yes"));
                role.setCanDeleteAllBuilds(canDeleteAllBuilds[0].equals("yes"));
                // build type permissions
                role.setCanViewBuildTypes(canViewBuildTypes[0].equals("yes"));
                role.setCanAddBuildTypes(canAddBuildTypes[0].equals("yes"));
                role.setCanEditBuildTypes(canEditBuildTypes[0].equals("yes"));
                role.setCanDeleteBuildTypes(canDeleteBuildTypes[0].equals("yes"));
                // world permissions
                role.setCanViewWorlds(canViewWorlds[0].equals("yes"));
                role.setCanAddWorlds(canAddWorlds[0].equals("yes"));
                role.setCanEditWorlds(canEditWorlds[0].equals("yes"));
                role.setCanDeleteWorlds(canDeleteWorlds[0].equals("yes"));
                // vote permissions
                role.setCanViewAllVotes(canViewAllVotes[0].equals("yes"));
                role.setCanAddVotes(canAddVotes[0].equals("yes"));
                role.setCanEditAllVotes(canEditAllVotes[0].equals("yes"));
                role.setCanDeleteAllVotes(canDeleteAllVotes[0].equals("yes"));
                // role permissions
                role.setCanViewRoles(canViewRoles[0].equals("yes"));
                role.setCanAddRoles(canAddRoles[0].equals("yes"));
                role.setCanEditRoles(canEditRoles[0].equals("yes"));
                role.setCanDeleteRoles(canDeleteRoles[0].equals("yes"));
                // user permissions
                role.setCanViewUsers(canViewUsers[0].equals("yes"));
                role.setCanAddUsers(canAddUsers[0].equals("yes"));
                role.setCanEditUsers(canEditUsers[0].equals("yes"));
                role.setCanBanUsers(canBanUsers[0].equals("yes"));

                role.setDescription(description);
                if(RoleDAO.edit(role, oldRoleID)) {
                    session.setAttribute("flashMessageSuccess", "Successfully edited role: " + roleID);
                    resp.sendRedirect("smp-roles");
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Something went wrong while editing role. Please try again.");
                }
            } catch (Exception ex) {
                session.setAttribute("flashMessageError", ex.getMessage());
            }
        }

        try {
            Role role = RoleDAO.get(roleID);
            if(role != null) {
                req.setAttribute("role", role);
            } else {
                session.setAttribute("flashMessageWarning", "No such role found with the name: " + roleID);
                resp.sendRedirect("smp-roles");
                return;
            }
        } catch(Exception ex) {
            session.setAttribute("flashMessageDanger", "Unable to retrieve role data:\n" + ex.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Role");
        req.getRequestDispatcher("WEB-INF/smp/edit-role.jsp").forward(req, resp);
    }
}
