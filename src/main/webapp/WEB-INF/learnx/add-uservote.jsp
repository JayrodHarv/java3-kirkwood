<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-bottom: 40px">
    <form action="${appURL}/add-uservote" method="POST">

        <%-- Get Vote Error--%>
        <c:if test="${not empty results.getVoteError}">
            <p class="alert alert-danger my-2">${results.getVoteError}</p>
        </c:if>

        <div class="container-fluid p-0">
            <h2 class="form-label">${results.voteID}</h2>
            <p>${results.description}</p>

            <!-- Vote Options -->
            <div class="d-flex justify-content-between mb-4">
                <h2>Vote Options</h2>
                <div class="text-end">
                    <a href="${appURL}/add-option?voteID=${results.voteID}" class="btn btn-primary">Add Option</a>
                </div>
            </div>
            <div class="container">
                <c:if test="${not empty results.getOptionListError}">
                    <p class="alert alert-danger my-2">${results.getOptionListError}</p>
                </c:if>
                <div class="row row-cols-1 g-4">
                    <c:forEach items="${options}" var="o" varStatus="status">
                        <div class="col">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <h5>Option ${status.index() + 1}) ${o.getTitle()}</h5>
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

