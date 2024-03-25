<div class="container-fluid p-0 sticky-top">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between mb-4 p-3 bg-dark">
        <div class="col-md-3 mb-2 mb-md-0">
            <a href="${appURL}/smp" class="d-inline-flex link-body-emphasis text-decoration-none">
                <img src="${appURL}/images/smp/void.png" style="height: 32px" alt="Logo">
                <h3 class="text-light">${smpName}</h3>
            </a>
        </div>

        <ul class="nav col-12 col-md-auto mb-2 justify-content-left mb-md-0">
            <li><a href="${appURL}/smp" class="nav-link px-2 link-secondary">Home</a></li>
            <li><a href="${appURL}/world-map" class="nav-link px-2">World Map</a></li>
            <li><a href="${appURL}/server-builds" class="nav-link px-2">Server Builds</a></li>
            <li><a href="${appURL}/discussion-forms" class="nav-link px-2">Discussion Forms</a></li>
            <li><a href="${appURL}/votes" class="nav-link px-2">Votes</a></li>
        </ul>

        <div class="col-md-3 text-end">
            <c:choose>
                <c:when test="${empty activeUser}">
                    <a href="${appURL}/smp-login" class="btn btn-outline-primary me-2">Login</a>
                    <a href="${appURL}/smp-signup" class="btn btn-primary">Sign Up</a>
                </c:when>
                <c:otherwise>
                    <a href="${appURL}/smp-logout" class="btn btn-outline-primary me-2">Logout</a>
                    <a href="${appURL}/smp-edit-profile" class="btn btn-primary">Edit Profile</a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
</div>