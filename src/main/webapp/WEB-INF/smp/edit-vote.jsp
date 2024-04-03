<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-bottom: 40px">
    <form action="${appURL}/edit-vote" method="POST">

        <div class="container-fluid p-0">
            <h5 class="form-label">Edit Vote</h5>

            <!-- Vote Name -->
            <div class="input-group mb-4">
                <label for="voteName" class="input-group-text">Vote Name *</label>
                <input type="text" class="form-control rounded-end <c:if test="${not empty results.voteNameError}">is-invalid</c:if>" placeholder="Ex. SummerSMP2024 World Seed Vote" id="voteName" name="voteName" value="${results.voteName}">
                <c:if test="${not empty results.voteNameError}">
                    <div class="invalid-feedback">${results.voteNameError}</div>
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

            <!-- Start & End Time -->
            <div class="input-group mb-4">
                <label for="startTime" class="input-group-text">Start Time</label>
                <input type="datetime-local" class="form-control <c:if test="${not empty results.timePeriodError}">is-invalid</c:if>" id="startTime" name="startTime" value="${results.startTime}">
                <label for="endTime" class="input-group-text">End Time</label>
                <input type="datetime-local" class="form-control rounded-end <c:if test="${not empty results.timePeriodError}">is-invalid</c:if>" id="endTime" name="endTime" value="${results.endTime}">
                <c:if test="${not empty results.timePeriodError}">
                    <div class="invalid-feedback">${results.timePeriodError}</div>
                </c:if>
            </div>
        </div>
        <div class="d-flex justify-content-between p-0">
            <div class="btn btn-group p-0">
                <a href="${appURL}/votes" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Next</button>
            </div>
            <p class="form-text text-end">* Indicates required fields</p>
        </div>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
