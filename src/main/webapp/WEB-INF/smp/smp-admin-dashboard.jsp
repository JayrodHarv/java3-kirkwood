<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid p-0" style="margin-top: 75px; margin-bottom: 40px;">
    <div class="nav-scroller bg-dark shadow-sm">
        <nav class="nav bg-dark" aria-label="Secondary navigation">
            <a class="nav-link <c:if test="${results.page eq 'smp-users'}">link-light</c:if>" aria-current="page" href="${appURL}/smp-admin-dashboard?page=smp-users">All Users</a>
            <a class="nav-link <c:if test="${results.page eq 'worlds'}">link-light</c:if>" aria-current="page" href="${appURL}/smp-admin-dashboard?page=worlds">Worlds</a>
            <a class="nav-link <c:if test="${results.page eq 'build-types'}">link-light</c:if>" aria-current="page" href="${appURL}/smp-admin-dashboard?page=build-types">Build Types</a>
        </nav>
    </div>
    <div class="container py-3">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <c:choose>
            <c:when test="${results.page eq 'smp-users'}">
                <%@include file="/WEB-INF/smp/smp-users.jsp"%>
            </c:when>
            <c:when test="${results.page eq 'worlds'}">

            </c:when>
            <c:when test="${results.page eq 'build-types'}">

            </c:when>
        </c:choose>

    </div>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>