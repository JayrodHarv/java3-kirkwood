<div class="container-fluid px-3 sticky-top bg-dark py-2">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between">
        <div class="mb-md-0 align-items-center">
            <a href="${appURL}/smp" class="d-inline-flex link-body-emphasis text-decoration-none">
                <img src="${appURL}/images/smp/NoGoOutsideWebsiteIcon.png" style="height: 65px" alt="Logo">
            </a>
        </div>

        <ul class="nav col-12 col-md-auto me-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${appURL}/smp" class="nav-link px-2 <c:if test="${pageTitle eq 'Home'}">link-secondary</c:if>">Home</a></li>
            <li><a href="${appURL}/world-map" class="nav-link px-2 <c:if test="${pageTitle eq 'World Map'}">link-secondary</c:if>">World Map</a></li>
            <li><a href="${appURL}/server-builds" class="nav-link px-2 <c:if test="${pageTitle eq 'Server Builds'}">link-secondary</c:if>">Server Builds</a></li>
            <li><a href="${appURL}/discussion-forms" class="nav-link px-2 <c:if test="${pageTitle eq 'Discussion Forms'}">link-secondary</c:if>">Discussion Forms</a></li>
            <li><a href="${appURL}/votes" class="nav-link px-2 <c:if test="${pageTitle eq 'Votes'}">link-secondary</c:if>">Votes</a></li>

            <c:choose>
                <c:when test="${sessionScope.activeSMPUser.role eq 'admin'}">
                    <li><a href="/smp-admin-dashboard" class="nav-link px-2 <c:if test="${pageTitle eq 'Admin Dashboard'}">link-secondary</c:if>">Admin Dashboard</a></li>
                </c:when>
            </c:choose>

        </ul>

        <div class="text-end">
            <c:choose>
                <c:when test="${empty activeSMPUser}">
                    <a href="${appURL}/smp-login" class="btn btn-outline-primary me-2">Login</a>
                    <a href="${appURL}/smp-signup" class="btn btn-primary">Sign Up</a>
                </c:when>
                <c:otherwise>
                    <div class="dropdown text-end" data-bs-theme="dark">
                        <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="${appURL}/images/smp/server-icon.png" alt="user profile picture" width="45" height="45" class="rounded-circle">
                        </a>
                        <ul class="dropdown-menu text-small">
                            <li><a class="dropdown-item" href="#">Settings</a></li>
                            <li><a class="dropdown-item" href="${appURL}/smp-edit-profile">Profile</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${appURL}/smp-logout">Sign out</a></li>
                        </ul>
                    </div>
<%--                    <a href="${appURL}/smp-logout" class="btn btn-outline-primary me-2">Logout</a>--%>
<%--                    <a href="${appURL}/smp-edit-profile" class="btn btn-primary">Edit Profile</a>--%>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
</div>