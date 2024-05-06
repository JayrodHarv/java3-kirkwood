<style>

    @media (max-width: 991.98px) {
        .offcanvas-collapse {
            position: fixed;
            top: 75px; /* Height of navbar */
            bottom: 0;
            left: 100%;
            width: 100%;
            padding-right: 1rem;
            padding-left: 1rem;
            overflow-y: auto;
            visibility: hidden;
            background-color: #343a40;
            transition: transform .3s ease-in-out, visibility .3s ease-in-out;
        }
        .offcanvas-collapse.open {
            visibility: visible;
            transform: translateX(-100%);
        }
    }
</style>
<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark" style="height: 75px" aria-label="Main navigation">
    <div class="container-fluid">
        <img src="${appURL}/images/smp/NoGoOutsideWebsiteIcon.png" style="height: 65px" alt="Logo">
        <button class="navbar-toggler p-0 border-0" type="button" id="navbarSideCollapse" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a href="${appURL}/smp" class="nav-link px-2 <c:if test="${pageTitle eq 'Home'}">link-light</c:if>">Home</a></li>
<%--                <li class="nav-item"><a href="${appURL}/world-map" class="nav-link px-2 <c:if test="${pageTitle eq 'World Map'}">link-light</c:if>">World Map</a></li>--%>

                <li class="nav-item"><a href="${appURL}/server-builds" class="nav-link px-2 <c:if test="${pageTitle eq 'Server Builds'}">link-light</c:if>">Server Builds</a></li>

<%--                <li class="nav-item"><a href="${appURL}/discussion-forms" class="nav-link px-2 <c:if test="${pageTitle eq 'Discussion Forms'}">link-light</c:if>">Discussion Forms</a></li>--%>
                <li class="nav-item"><a href="${appURL}/votes" class="nav-link px-2 <c:if test="${pageTitle eq 'Votes'}">link-light</c:if>">Votes</a></li>

                <c:if test="${sessionScope.activeSMPUser.getRole().canViewUsers()
                                or sessionScope.activeSMPUser.getRole().canViewWorlds()
                                or sessionScope.activeSMPUser.getRole().canViewBuildTypes()
                                or sessionScope.activeSMPUser.getRole().canViewRoles()}">
<%--                        <li class="nav-item"><a href="${appURL}/smp-admin-dashboard" class="nav-link px-2 <c:if test="${pageTitle eq 'Admin Dashboard'}">link-light</c:if>">Admin Dashboard</a></li>--%>
                    <li class="nav-item dropdown">
                        <a class="nav-link nav-link px-2 dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">Manage</a>
                        <ul class="dropdown-menu dropdown-menu-dark">
                          <c:if test="${sessionScope.activeSMPUser.getRole().canViewUsers()}">
                            <li><a class="dropdown-item" href="${appURL}/smp-users">Users</a></li>
                          </c:if>
                          <c:if test="${sessionScope.activeSMPUser.getRole().canViewRoles()}">
                            <li><a class="dropdown-item" href="${appURL}/smp-roles">Roles</a></li>
                          </c:if>
                          <c:if test="${sessionScope.activeSMPUser.getRole().canViewWorlds()}">
                            <li><a class="dropdown-item" href="${appURL}/smp-worlds">Worlds</a></li>
                          </c:if>
                          <c:if test="${sessionScope.activeSMPUser.getRole().canViewBuildTypes()}">
                            <li><a class="dropdown-item" href="${appURL}/smp-build-types">Build Types</a></li>
                          </c:if>
                        </ul>
                    </li>
                </c:if>
            </ul>
            <div class="text-end">
                <c:choose>
                    <c:when test="${empty activeSMPUser}">
                        <a href="${appURL}/smp-login" class="btn btn-outline-primary me-2">Sign In</a>
                        <a href="${appURL}/smp-signup" class="btn btn-primary">Sign Up</a>
                    </c:when>
                    <c:otherwise>
                        <div class="dropdown text-end px-2" data-bs-theme="dark">
                            <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                <img src="${activeSMPUser.getBase64Pfp()}" alt="pfp" width="45" height="45" class="rounded-circle">
                                ${activeSMPUser.getDisplayName()}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end text-small">
                                <li><h5 class="dropdown-item-text">Role: ${activeSMPUser.getRole().getRoleID()}</h5></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${appURL}/smp-edit-profile?userID=${activeSMPUser.getUserID()}">Profile</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${appURL}/smp-logout">Sign out</a></li>
                            </ul>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>

<script>
    (() => {
        'use strict'
        document.querySelector('#navbarSideCollapse').addEventListener('click', () => {
            document.querySelector('.offcanvas-collapse').classList.toggle('open')
        })
    })()
</script>