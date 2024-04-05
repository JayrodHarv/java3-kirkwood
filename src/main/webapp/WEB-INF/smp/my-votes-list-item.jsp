<c:forEach items="${votes}" var="v">
    <div class="col">
        <div class="card">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <h5>${v.getVoteID()}</h5>
                    <p class="text-end m-0">Number of votes: ${v.getNumberOfVotes()}</p>
                </div>
                <p class="card-text">${v.getDescription()}</p>
                <c:if test="${sessionScope.activeSMPUser.getUserID() == v.getUserID()}">
                    <div class="text-end">
                        <div class="btn-group">
                            <c:if test="${v.getStartTime() == null}">
                                <a href="${appURL}/start-vote?voteID=${v.getVoteID()}" class="btn btn-outline-success" data-bs-toggle="tooltip" data-bs-title="Start Vote"><i class="bi bi-play-fill"></i></a>
                            </c:if>
                            <a href="${appURL}/edit-vote?voteID=${v.getVoteID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit Vote"><i class="bi bi-pencil-fill"></i></a>
                            <a class="btn btn-outline-danger" onclick="Confirm('${v.getVoteID()}')" data-bs-toggle="tooltip" data-bs-title="Delete Vote"><i class="bi bi-trash3-fill"></i></a>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</c:forEach>