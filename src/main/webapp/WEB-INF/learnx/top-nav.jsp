<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <div class="col-md-3 mb-2 mb-md-0">
            <a href="${appURL}/learnx" class="d-inline-flex link-body-emphasis text-decoration-none">
                <img src="${appURL}/images/learnx/learnx-logo-light.png" style="height: 32px" alt="Logo">
            </a>
        </div>

        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${appURL}/learnx" class="nav-link px-2 link-secondary">Home</a></li>
            <li><a href="${appURL}/courses" class="nav-link px-2">Courses</a></li>

            <c:choose>
                <c:when test="${sessionScope.activeUser.privileges eq 'student'}">
                    <li><a href="#" class="nav-link px-2">Student Dashboard</a></li>
                </c:when>
                <c:when test="${sessionScope.activeUser.privileges eq 'teacher'}">
                    <li><a href="#" class="nav-link px-2">Teacher Dashboard</a></li>
                </c:when>
                <c:when test="${sessionScope.activeUser.privileges eq 'admin'}">
                    <li><a href="#" class="nav-link px-2">Admin Dashboard</a></li>
                </c:when>
            </c:choose>
        </ul>

        <div class="col-md-3 text-end">
            <c:choose>
                <c:when test="${empty activeUser}">
                    <a href="${appURL}/login" class="btn btn-outline-orange me-2">Login</a>
                    <a href="${appURL}/signup" class="btn btn-orange">Sign-up</a>
                </c:when>
                <c:otherwise>
                    <a href="${appURL}/logout" class="btn btn-outline-orange me-2">Logout</a>
                    <a href="${appURL}/edit-profile" class="btn btn-orange">Edit Profile</a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
</div>