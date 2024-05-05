package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.models.BuildType;
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

@WebServlet("/add-role")
public class AddRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=add-roles");
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canAddRoles()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        req.setAttribute("pageTitle", "Add Role");
        req.getRequestDispatcher("WEB-INF/smp/add-role.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String buildTypeID = req.getParameter("roleID");
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

        results.put("buildTypeID", buildTypeID);
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
        results.put("description", description);

        if(buildTypeID.isEmpty()) {
            results.put("buildTypeError", "You must enter a name for the build type");
        }

        if(description.isEmpty()) {
            results.put("descriptionError", "You must enter a description");
        }

        if(!results.containsKey("buildTypeError") && !results.containsKey("descriptionError")) {
            try {
                BuildType buildType = new BuildType(buildTypeID, description);
                if(BuildTypeDAO.add(buildType)) {
                    session.setAttribute("flashMessageSuccess", "Successfully added new build type: " + buildTypeID);
                    resp.sendRedirect("smp-build-types");
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Something went wrong while adding build type. Please try again.");
                }
            } catch (Exception ex) {
                session.setAttribute("flashMessageError", ex.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Add Build Type");
        req.getRequestDispatcher("WEB-INF/smp/add-build-type.jsp").forward(req, resp);
    }
}
