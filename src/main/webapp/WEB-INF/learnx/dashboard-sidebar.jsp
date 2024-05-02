<div class="col-xl-3">
    <!-- Responsive offcanvas body START -->
    <div class="offcanvas-xl offcanvas-end" tabindex="-1" id="offcanvasSidebar">
        <!-- Offcanvas header -->
        <div class="offcanvas-header bg-light">
            <button  type="button" class="btn-close" data-bs-dismiss="offcanvas" data-bs-target="#offcanvasSidebar" aria-label="Close"></button>
        </div>
        <!-- Offcanvas body -->
        <div class="offcanvas-body p-3 p-xl-0">
            <div class="bg-dark border rounded-3 p-3 w-100">
                <!-- Dashboard menu -->
                <div class="list-group list-group-dark list-group-borderless">
                    <c:if test="${sessionScope.activeUser.privileges eq 'admin'}">
                        <a class="list-group-item <c:if test="${sideBarTab eq 'admin-dashboard'}">active</c:if>" href="${appURL}/admin-dashboard">Admin Dashboard</a>
                        <a class="list-group-item <c:if test="${sideBarTab eq 'all-users'}">active</c:if>" href="${appURL}/all-users">All Users</a>
                        <a class="list-group-item <c:if test="${sideBarTab eq 'all-courses-admin'}">active</c:if>" href="${appURL}/all-courses-admin">All Courses</a>
                    </c:if>
                    <a class="list-group-item <c:if test="${sideBarTab eq 'edit-profile'}">active</c:if>" href="${appURL}/edit-profile"><i class="fas fa-user-pen me-2"></i>Edit Profile</a>
                    <c:if test="${sessionScope.activeUser.privileges ne 'teach' && sessionScope.activeUser.privileges ne 'admin'}">
                        <a class="list-group-item <c:if test="${sideBarTab eq 'delete-account'}">active</c:if>" href="${appURL}/delete-account"><i class="bi bi-trash fa-fw me-2"></i>Delete Account</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <!-- Responsive offcanvas body END -->
</div>