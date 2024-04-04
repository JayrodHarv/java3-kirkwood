<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-bottom: 40px">
    <form action="${appURL}/edit-vote" method="POST">

        <%-- Get Vote Error--%>
        <c:if test="${not empty results.getVoteError}">
            <p class="alert alert-danger my-2">${results.getVoteError}</p>
        </c:if>

        <div class="container-fluid p-0">
            <h2 class="form-label">Edit Vote</h2>

            <!-- VoteID -->
            <div class="input-group mb-4">
                <label for="voteID" class="input-group-text">Vote Name *</label>
                <input type="text" class="form-control rounded-end <c:if test="${not empty results.voteIDError}">is-invalid</c:if>" placeholder="Ex. SummerSMP2024 World Seed Vote" id="voteID" name="voteID" value="${results.voteID}">
                <c:if test="${not empty results.voteIDError}">
                    <div class="invalid-feedback">${results.voteIDError}</div>
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

<%--            <!-- Start & End Time -->--%>
<%--            <div class="input-group mb-4">--%>
<%--                <label for="startTime" class="input-group-text">Start Time</label>--%>
<%--                <input type="datetime-local" class="form-control <c:if test="${not empty results.timePeriodError}">is-invalid</c:if>" id="startTime" name="startTime" value="${results.startTime}">--%>
<%--                <label for="endTime" class="input-group-text">End Time</label>--%>
<%--                <input type="datetime-local" class="form-control rounded-end <c:if test="${not empty results.timePeriodError}">is-invalid</c:if>" id="endTime" name="endTime" value="${results.endTime}">--%>
<%--                <c:if test="${not empty results.timePeriodError}">--%>
<%--                    <div class="invalid-feedback">${results.timePeriodError}</div>--%>
<%--                </c:if>--%>
<%--            </div>--%>

            <!-- Vote Options -->
            <div class="d-flex justify-content-between">
                <h2>Vote Options</h2>
                <div class="text-end">
                    <a href="${appURL}/add-option?voteID=${results.voteID}" class="btn btn-primary">Add Option</a>
                </div>
            </div>
            <div class="container mb-4">
                <c:if test="${not empty results.getOptionListError}">
                    <p class="alert alert-danger my-2">${results.getOptionListError}</p>
                </c:if>
                <div class="row row-cols-1 g-4">
                    <c:if test="${options.size() > 0}">
                        <c:forEach items="${options}" var="o">
                            <div class="col">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between">
                                            <h5>${o.getTitle()}</h5>
<%--                                            <c:if test="${results.endTime < now}"></c:if>--%>
<%--                                            <p class="text-end m-0">${o.getNumberOfVotes()}</p>--%>
                                        </div>
                                        <p class="card-text">${o.getDescription()}</p>
                                        <c:if test="${sessionScope.activeSMPUser.getUserID() == results.userID}">
                                            <div class="text-end">
                                                <a href="${appURL}/edit-option?optionID=${o.getOptionID()}" class="btn btn-warning">Edit</a>
                                                <a href="${appURL}/delete-option?optionID=${o.getOptionID()}" class="btn btn-danger">Delete</a>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>

            <!-- Start Vote & Duration of vote -->
            <div class="container-fluid p-0">
                <div class="input-group mb-4">
                    <label for="duration" class="input-group-text">End Time</label>
                    <input type="time" class="form-control" id="duration" name="duration" value="${results.duration}">
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="start" name="start">
                    <label class="form-check-label" for="start">
                        Start Vote On Submission
                    </label>
                </div>
            </div>

        </div>
        <div class="d-flex justify-content-between p-0">
            <div class="btn btn-group p-0">
                <a href="${appURL}/votes" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Submit Changes</button>
            </div>
            <p class="form-text text-end">* Indicates required fields</p>
        </div>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
