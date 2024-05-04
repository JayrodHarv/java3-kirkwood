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
                <li class="nav-item"><a href="${appURL}/world-map" class="nav-link px-2 <c:if test="${pageTitle eq 'World Map'}">link-light</c:if>">World Map</a></li>
                <li class="nav-item"><a href="${appURL}/server-builds" class="nav-link px-2 <c:if test="${pageTitle eq 'Server Builds'}">link-light</c:if>">Server Builds</a></li>
                <li class="nav-item"><a href="${appURL}/discussion-forms" class="nav-link px-2 <c:if test="${pageTitle eq 'Discussion Forms'}">link-light</c:if>">Discussion Forms</a></li>
                <li class="nav-item"><a href="${appURL}/votes" class="nav-link px-2 <c:if test="${pageTitle eq 'Votes'}">link-light</c:if>">Votes</a></li>

                <c:choose>
                    <c:when test="${sessionScope.activeSMPUser.role eq 'admin'}">
                        <li class="nav-item"><a href="${appURL}/smp-admin-dashboard" class="nav-link px-2 <c:if test="${pageTitle eq 'Admin Dashboard'}">link-light</c:if>">Admin Dashboard</a></li>
                    </c:when>
                </c:choose>
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
                                <li><a class="dropdown-item" href="${appURL}/smp-edit-profile">Profile</a></li>
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