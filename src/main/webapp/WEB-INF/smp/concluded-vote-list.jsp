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

                <c:choose>
                    <c:when test="${v.getNumberOfVotes() == 0}">
                        Nobody voted...
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${v.getOptions()}" var="o">
                            <div class="progress mb-2" role="progressbar" aria-valuenow="${v.getPercentVotes(o.getNumberOfVotes())}" aria-valuemin="0" aria-valuemax="100" style="height: 40px">
                                <div class="progress-bar bg-primary overflow-visible" style="width: ${v.getPercentVotes(o.getNumberOfVotes())}%">
                                    <div class="text-dark d-flex justify-content-between">
                                        <div class="text-start p-2">${o.getTitle()}</div>
                                        <div class="text-end p-2">${v.getPercentVotes(o.getNumberOfVotes())}%</div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</c:forEach>
