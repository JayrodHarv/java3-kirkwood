<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid py-3" style="margin-bottom: 40px">
    <div class="d-flex justify-content-between mb-4">
        <h2>${pageTitle}</h2>
        <div class="text-end">
            <a href="${appURL}/add-vote" class="btn btn-primary">Create Vote</a>
        </div>
    </div>
    <div class="container">
        <c:if test="${not empty results.getVoteListError}">
            <p class="alert alert-danger my-2">${results.getVoteListError}</p>
        </c:if>
        <div class="row row-cols-1 g-4">
            <c:if test="${votes.size() > 0}">
                <c:forEach items="${votes}" var="v">
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <h5>${v.getVoteID()}</h5>
                                    <p class="text-end m-0">${v.getNumberOfVotes()}</p>
                                </div>
                                <p class="card-text">${v.getDescription()}</p>
                                <c:if test="${sessionScope.activeSMPUser.getUserID() == v.getUserID()}">
                                    <div class="text-end">
                                        <a href="${appURL}/edit-vote?vote_id=${v.getVoteID()}" class="btn btn-warning">Edit</a>
                                        <a href="${appURL}/delete-vote?vote_id=${b.getVoteID()}" class="btn btn-danger">Delete</a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>