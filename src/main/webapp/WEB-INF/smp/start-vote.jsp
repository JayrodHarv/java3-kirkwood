<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-bottom: 40px">
    <form action="${appURL}/start-vote" method="POST">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <%-- Start Vote Error--%>
        <c:if test="${not empty results.startVoteError}">
            <p class="alert alert-danger my-2">${results.startVoteError}</p>
        </c:if>

        <div class="container-fluid p-0">
            <h2 class="form-label">Start Vote</h2>

            <!-- VoteID -->
            <input type="hidden" value="${results.voteID}" name="voteID">

            <!-- Start Vote & Duration of vote -->
            <div class="container p-0">
                <div class="input-group mb-4">
                    <label for="duration" class="input-group-text">Vote Duration *</label>
                    <input type="number" class="form-control <c:if test="${not empty results.durationError}">is-invalid</c:if>" id="duration" name="duration" value="${results.duration}">
                    <select class="form-select <c:if test="${not empty results.timeUnitError}">is-invalid</c:if>" id="timeUnit" name="timeUnit">
                        <option value="minutes" <c:if test="${results.timeUnit eq 'minutes'}">selected</c:if>>Minutes</option>
                        <option value="hours" <c:if test="${results.timeUnit eq 'hours'}">selected</c:if>>Hours</option>
                        <option value="days" <c:if test="${results.timeUnit eq 'days'}">selected</c:if>>Days</option>
                    </select>
                    <c:if test="${not empty results.durationError}">
                        <div class="invalid-feedback">${results.durationError}</div>
                    </c:if>
                    <c:if test="${not empty results.timeUnitError}">
                        <div class="invalid-feedback">${results.timeUnitError}</div>
                    </c:if>
                </div>
            </div>

        </div>
        <div class="d-flex justify-content-between p-0">
            <div class="btn btn-group p-0">
                <a href="${appURL}/votes" class="btn btn-secondary">Cancel</a>
                <button type="submit" class="btn btn-primary">Begin Voting</button>
            </div>
            <p class="form-text text-end">* Indicates required fields</p>
        </div>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
