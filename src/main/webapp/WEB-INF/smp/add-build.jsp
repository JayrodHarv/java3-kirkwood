<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-top: 75px; margin-bottom: 40px;">
    <form action="${appURL}/add-build" method="POST" enctype="multipart/form-data">

        <%-- Build Add Fail--%>
        <c:if test="${not empty results.buildAddFail}">
            <p class="alert alert-danger my-2">${results.buildAddFail}</p>
        </c:if>

        <!-- Build Info -->
        <div class="container-fluid p-0">
            <h5 class="form-label">Build Info</h5>

            <!-- Build Name -->
            <div class="input-group mb-4">
                <label for="buildName" class="input-group-text">Build Name *</label>
                <input type="text" class="form-control rounded-end <c:if test="${not empty results.buildNameError}">is-invalid</c:if>" placeholder="Ex. Brucemore" id="buildName" name="buildName" value="${results.buildName}">
                <c:if test="${not empty results.buildNameError}">
                    <div class="invalid-feedback">${results.buildNameError}</div>
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

            <!-- Date Built -->
            <div class="input-group mb-4">
                <label for="dateBuilt" class="input-group-text">Date Built</label>
                <input type="date" class="datepicker form-control rounded-end <c:if test="${not empty results.dateBuiltError}">is-invalid</c:if>" id="dateBuilt" name="dateBuilt" value="${results.dateBuilt}">
                <c:if test="${not empty results.dateBuiltError}">
                    <div class="invalid-feedback">${results.dateBuiltError}</div>
                </c:if>
            </div>

            <!-- Coordinates -->
            <div class="container-fluid p-0 justify-content-between">
                <h5 class="form-label">Coordinates</h5>
                <div class="input-group mb-4 fit-content">
                    <label class="input-group-text" for="xCoord">X</label>
                    <input type="number" class="form-control <c:if test="${not empty results.coordError}">is-invalid</c:if>" id="xCoord" name="xCoord" value="${results.xCoord}">
                    <label class="input-group-text" for="yCoord">Y</label>
                    <input type="number" class="form-control <c:if test="${not empty results.coordError}">is-invalid</c:if>" id="yCoord" name="yCoord" value="${results.yCoord}">
                    <label class="input-group-text" for="zCoord">Z</label>
                    <input type="number" class="form-control rounded-end <c:if test="${not empty results.coordError}">is-invalid</c:if>" id="zCoord" name="zCoord" value="${results.zCoord}">
                    <c:if test="${not empty results.coordError}">
                        <div class="invalid-feedback">${results.coordError}</div>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- Image -->
        <div class="container-fluid p-0 mb-4">
            <h5 class="form-label">Image *</h5>
            <div class="input-group">
                <input type="file" accept="image/png, image/jpeg" class="form-control <c:if test="${not empty results.imageError}">is-invalid</c:if>" id="image" name="image" value="${results.image}">
                <c:if test="${not empty results.imageError}">
                    <div class="invalid-feedback">${results.imageError}</div>
                </c:if>
            </div>
            <div id="imagePreview">

            </div>
        </div>

        <!-- Tags TODO: Make it so user can create new tag if one doesn't exist for their needs -->
        <div class="container-fluid p-0 justify-content-between">
            <h5 class="form-label">Tags *</h5>
            <!-- Server Tag -->
            <div class="input-group mb-4">
                <label class="input-group-text" for="world">World</label>
                <select class="form-select <c:if test="${not empty results.tagError}">is-invalid</c:if>" id="world" name="world">
                    <c:forEach items="${worlds}" var="w">
                        <option value="${w.getWorldID()}" <c:if test="${results.worldTag eq w.getWorldID()}">selected</c:if> data-bs-toggle="tooltip" title="${w.getDescription()}">${w.getWorldID()}</option>
                    </c:forEach>
                </select>
                <label class="input-group-text" for="buildType">Build Type</label>
                <select class="form-select <c:if test="${not empty results.tagError}">is-invalid</c:if>" id="buildType" name="buildType">
                    <c:forEach items="${buildTypes}" var="bt">
                        <option value="${bt.getBuildTypeID()}" <c:if test="${results.buildType eq bt.getBuildTypeID()}">selected</c:if> data-bs-toggle="tooltip" title="${bt.getDescription()}">${bt.getBuildTypeID()}</option>
                    </c:forEach>
                </select>
                <c:if test="${not empty results.tagError}">
                    <div class="invalid-feedback">${results.tagError}</div>
                </c:if>
            </div>
        </div>
        <div class="d-flex justify-content-between p-0">
            <div class="btn btn-group p-0">
                <a href="${appURL}/server-builds" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Save changes</button>
            </div>
            <p class="form-text text-end">* Indicates required fields</p>
        </div>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>