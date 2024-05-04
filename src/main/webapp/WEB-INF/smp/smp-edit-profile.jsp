<%@include file="/WEB-INF/smp/top.jsp" %>
<main class="container-lg py-3" style="margin-top: 75px; margin-bottom: 40px;">

    <!-- Flash Message -->
    <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

    <div class="row">
        <h2 class="form-label">Edit Profile</h2>
    </div>

    <div class="row">

        <!-- Description -->
        <div class="input-group mb-4">
            <label for="description" class="input-group-text">Description *</label>
            <input type="text" class="form-control rounded-end <c:if test="${not empty results.descriptionError}">is-invalid</c:if>" id="description" name="description" value="${results.description}">
            <c:if test="${not empty results.descriptionError}">
                <div class="invalid-feedback">${results.descriptionError}</div>
            </c:if>
        </div>

    </div>
</main>
<%@include file="/WEB-INF/smp/bottom.jsp" %>