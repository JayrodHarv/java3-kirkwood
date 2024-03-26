<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid">
    <form action="${appURL}/add-build" method="POST">
        <!-- Building Name -->
        <div class="mb-4">
            <label for="buildName" class="form-label">Build Name</label>
            <div class="input-group input-group-lg">
                <input type="text" class="<c:if test="${not empty results.buildNameError}">is-invalid</c:if> form-control border-0 bg-light rounded-end ps-1" placeholder="" id="buildName" name="buildName" value="${results.buildName}">
                <c:if test="${not empty results.buildNameError}">
                    <div class="invalid-feedback">${results.buildNameError}</div>
                </c:if>
            </div>
        </div>

        <!-- Description -->
        <div class="mb-4">
            <label for="description" class="form-label">Description</label>
            <div class="input-group input-group-lg">
                <input type="text" class="<c:if test="${not empty results.descriptionError}">is-invalid</c:if> form-control border-0 bg-light rounded-end ps-1" placeholder="" id="description" name="description" value="${results.description}">
                <c:if test="${not empty results.descriptionError}">
                    <div class="invalid-feedback">${results.descriptionError}</div>
                </c:if>
            </div>
        </div>

        <!-- Tags TODO: Make it so user can create new tag if one doesn't exist for their needs -->
        <div class="mb-4">
            <label for="tags" class="form-label">Tags</label>
            <div class="input-group input-group-lg">
                <select multiple class="form-select <c:if test="${not empty results.tagError}">is-invalid</c:if>" id="tags">
                    <c:forEach items="${tags}" var="t">
                        <option value="${t.getTagID()}" data-bs-toggle="tooltip" title="${t.getDescription()}">${t.getTagID()}</option>
                    </c:forEach>
                </select>
            </div>
            <small class="form-text">Select multiple by using shift or control</small>
            <c:if test="${not empty results.tagError}">
                <div class="invalid-feedback">${results.tagError}</div>
            </c:if>
        </div>
        <a href="${appURL}/server-builds" class="btn btn-secondary">Back</a>
        <button type="submit" class="btn btn-primary">Save changes</button>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>