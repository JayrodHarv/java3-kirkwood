<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-top: 75px; margin-bottom: 40px;">
    <form action="${appURL}/edit-build-type" method="POST">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <div class="container-fluid p-0">
            <h5 class="form-label">${pageTitle}</h5>

            <!-- Build Type -->
            <div class="input-group mb-4">
                <label for="buildTypeID" class="input-group-text">Build Type Name *</label>
                <input type="text" class="form-control rounded-end <c:if test="${not empty results.buildTypeError}">is-invalid</c:if>" placeholder="Ex. Capitol" id="buildTypeID" name="buildTypeID" value="${results.buildTypeID}">
                <c:if test="${not empty results.buildTypeIDError}">
                    <div class="invalid-feedback">${results.buildTypeIDError}</div>
                </c:if>
            </div>

            <!-- Description -->
            <div class="input-group mb-4">
                <label for="description" class="input-group-text">Description *</label>
                <input type="text" class="form-control rounded-end <c:if test="${not empty results.descriptionError}">is-invalid</c:if>" id="description" name="description" value="${results.description}">
                <c:if test="${not empty results.descriptionError}">
                    <div class="invalid-feedback">${results.descriptionError}</div>
                </c:if>
            </div>
        </div>
        <div class="d-flex justify-content-between p-0">
            <div class="btn btn-group p-0">
                <a href="${appURL}/smp-admin-dashboard?page=build-types" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </div>
            <p class="form-text text-end">* Indicates required field</p>
        </div>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
