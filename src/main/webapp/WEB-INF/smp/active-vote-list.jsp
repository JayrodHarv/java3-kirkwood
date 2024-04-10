<c:forEach items="${votes}" var="v">
    <div class="col">
        <div class="card">
            <div class="card-header">
                <div class="d-flex justify-content-between">
                    <h5>${v.getVoteID()}</h5>
                    <p class="text-end m-0">Number of votes: ${v.getNumberOfVotes()}</p>
                </div>
            </div>
            <div class="card-body">
                <p class="card-text">${v.getDescription()}</p>
                <p class="card-text">Created By: ${v.getUserDisplayName()}</p>
                <p class="card-text">Ends at: ${v.getEndTime()}</p>

                <div class="d-flex justify-content-between">
                    <a href="${appURL}/add-uservote?voteID=${v.getVoteID()}" class="btn btn-success">Vote</a>
                    <c:if test="${sessionScope.activeSMPUser.getUserID() == v.getUserID()}">
                        <div class="text-end">
                            <div class="btn-group">
                                <a href="${appURL}/vote-statistics?voteID=${v.getVoteID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="View Statistics"><i class="bi bi-archive-fill"></i></a>
                                <a class="btn btn-outline-danger" onclick="ConfirmStop('${v.getVoteID()}')" data-bs-toggle="tooltip" data-bs-title="Stop Vote"><i class="bi bi-stop-fill"></i></a>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
