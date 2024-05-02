<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <div class="col-md-3 mb-2 mb-md-0">
            <a href="${appURL}/learnx" class="d-inline-flex link-body-emphasis text-decoration-none">
                <img src="${appURL}/images/learnx/learnx-logo-light.png" style="height: 32px" alt="Logo">
            </a>
        </div>

        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${appURL}/learnx" class="nav-link px-2 <c:if test="${pageTitle eq 'Home'}">link-secondary text-decoration-underline</c:if>"><fmt:message key="topNav.home"></fmt:message></a></li>
            <li><a href="${appURL}/courses" class="nav-link px-2 <c:if test="${pageTitle eq 'Courses'}">link-secondary text-decoration-underline</c:if>"><fmt:message key="topNav.courses"></fmt:message></a></li>

            <c:choose>
                <c:when test="${sessionScope.activeUser.privileges eq 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link ${pageTitle eq "Admin Dashboard" ? "link-secondary text-decoration-underline" : ""}" href="${appURL}/admin-dashboard"><i class="fas fa-user-cog fa-fw me-1"></i><fmt:message key="topNav.admin"></fmt:message></a>
                    </li>
                </c:when>
                <c:when test="${sessionScope.activeUser.privileges eq 'teach'}">
                    <li class="nav-item">
                        <a class="nav-link ${pageTitle == "Teacher Dashboard" ? "link-secondary text-decoration-underline" : ""}" href="${appURL}/teacher"><i class="fas fa-user-tie fa-fw me-1"></i><fmt:message key="topNav.teacher"></fmt:message></a>
                    </li>
                </c:when>
                <c:when test="${sessionScope.activeUser.privileges eq 'student'}">
                    <li class="nav-item">
                        <a class="nav-link ${pageTitle == "Student Dashboard" ? "link-secondary text-decoration-underline" : ""}" href="${appURL}/student"><i class="fas fa-user-graduate fa-fw me-1"></i><fmt:message key="topNav.student"></fmt:message></a>
                    </li>
                </c:when>
            </c:choose>
        </ul>

        <div class="col-md-3 text-end">
            <c:choose>
                <c:when test="${empty activeUser}">
                    <a href="${appURL}/login" class="btn btn-outline-orange me-2"><fmt:message key="topNav.signin"></fmt:message></a>
                    <a href="${appURL}/signup" class="btn btn-orange"><fmt:message key="topNav.signup"></fmt:message></a>
                </c:when>
                <c:otherwise>
                    <a href="${appURL}/logout" class="btn btn-outline-orange me-2"><fmt:message key="topNav.signout"></fmt:message></a>
                    <a href="${appURL}/edit-profile" class="btn btn-orange"><fmt:message key="topNav.edit-profile"></fmt:message></a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
</div>