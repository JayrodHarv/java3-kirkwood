<%@include file="/WEB-INF/smp/top.jsp" %>
<main class="container-lg py-3" style="margin-top: 75px; margin-bottom: 40px;">

    <!-- Flash Message -->
    <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

    <div class="row">
        <h2 class="form-label">Edit Profile</h2>
    </div>

    <div class="row">
        <form method="POST" action="${appURL}/smp-edit-profile" enctype="multipart/form-data">

            <input type="hidden" name="userID" value="${results.userID}">

            <!-- Display Name -->
            <div class="input-group mb-4">
                <label for="displayName" class="input-group-text">Display Name *</label>
                <input type="text" class="form-control rounded-end <c:if test="${not empty results.displayNameError}">is-invalid</c:if>" id="displayName" name="displayName" value="${results.displayName}">
                <c:if test="${not empty results.displayNameError}">
                    <div class="invalid-feedback">${results.displayNameError}</div>
                </c:if>
            </div>

            <!-- Profile Picture -->
            <div class="mb-4">
                <label for="image" class="form-label">Profile Picture</label>
                <div class="input-group">
                    <input type="file" accept="image/png, image/jpeg" class="form-control <c:if test="${not empty results.pfpError}">is-invalid</c:if>" id="image" name="pfp">
                    <c:if test="${not empty results.pfpError}">
                        <div class="invalid-feedback">${results.pfpError}</div>
                    </c:if>
                </div>
                <div id="imagePreview" class="rounded-circle" style="height: 140px; width: 140px">
                    <img src="${results.base64Pfp}" class="w-100"/>
                </div>
            </div>

            <div class="btn btn-group p-0">
                <button type="submit" class="btn btn-primary">Save changes</button>
            </div>
        </form>
    </div>
</main>
<%@include file="/WEB-INF/smp/bottom.jsp" %>