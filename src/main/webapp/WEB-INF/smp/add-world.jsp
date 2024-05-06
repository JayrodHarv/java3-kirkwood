<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-top: 75px; margin-bottom: 40px;">
    <form action="${appURL}/add-world" method="POST">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <div class="container-fluid p-0">
            <h2 class="mb-4">${pageTitle}</h2>

            <!-- World Name -->
            <div class="input-group mb-4">
                <label for="worldID" class="input-group-text">World Name *</label>
                <input type="text" class="form-control rounded-end <c:if test="${not empty results.worldIDError}">is-invalid</c:if>" placeholder="Ex. Capitol" id="worldID" name="worldID" value="${results.worldID}">
                <c:if test="${not empty results.worldIDError}">
                    <div class="invalid-feedback">${results.worldIDError}</div>
                </c:if>
            </div>

            <!-- Date Started -->
            <div class="input-group mb-4">
                <label for="dateStarted" class="input-group-text">Date Started</label>
                <input type="date" class="datepicker form-control rounded-end <c:if test="${not empty results.dateStartedError}">is-invalid</c:if>" id="dateStarted" name="dateStarted" value="${results.dateStarted}">
                <c:if test="${not empty results.dateStartedError}">
                    <div class="invalid-feedback">${results.dateStartedError}</div>
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
                <a href="${appURL}/smp-worlds" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Add World</button>
            </div>
            <p class="form-text text-end">* Indicates required field</p>
        </div>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
