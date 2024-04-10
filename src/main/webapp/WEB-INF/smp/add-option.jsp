<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-top: 75px; margin-bottom: 40px;">
    <form action="${appURL}/add-option" method="POST" enctype="multipart/form-data">

        <%-- Add Option Error--%>
        <c:if test="${not empty results.addOptionError}">
            <p class="alert alert-danger my-2">${results.addOptionError}</p>
        </c:if>

        <div class="container-fluid p-0">
            <h5 class="form-label">${pageTitle}</h5>

            <!-- Hidden VoteID -->
            <input type="hidden" name="voteID" value="${results.voteID}">

            <!-- Title -->
            <div class="input-group mb-4">
                <label for="title" class="input-group-text">Title *</label>
                <input type="text" class="form-control rounded-end <c:if test="${not empty results.titleError}">is-invalid</c:if>" placeholder="Ex. Seed #" id="title" name="title" value="${results.title}">
                <c:if test="${not empty results.titleError}">
                    <div class="invalid-feedback">${results.titleError}</div>
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

            <!-- Image -->
            <div class="container-fluid p-0 mb-4">
                <h5 class="form-label">Image</h5>
                <div class="input-group">
                    <input type="file" accept="image/png, image/jpeg" class="form-control <c:if test="${not empty results.imageError}">is-invalid</c:if>" id="image" name="image" value="${results.image}">
                    <c:if test="${not empty results.imageError}">
                        <div class="invalid-feedback">${results.imageError}</div>
                    </c:if>
                </div>
                <div id="imagePreview">

                </div>
            </div>

        </div>
        <div class="d-flex justify-content-between p-0">
            <div class="btn btn-group p-0">
                <a href="${appURL}/edit-vote?voteID=${results.voteID}" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Add Option to Vote</button>
            </div>
            <p class="form-text text-end">* Indicates required field</p>
        </div>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
