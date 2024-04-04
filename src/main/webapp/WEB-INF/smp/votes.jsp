<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid py-3" style="margin-bottom: 40px">
    <div class="d-flex justify-content-between mb-4">
        <h2>${pageTitle}</h2>
        <div class="text-end">
            <a href="${appURL}/add-vote" class="btn btn-primary">Create Vote</a>
        </div>
    </div>
    <div class="container">
        <ul class="nav nav-underline mb-4">
            <li class="nav-item">
                <a class="nav-link <c:if test="${results.page eq 'active'}">active</c:if>" aria-current="page" href="${appURL}/votes?page=active">Active</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${results.page eq 'myVotes'}">active</c:if>" aria-current="page" href="${appURL}/votes?page=myVotes">My Votes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${results.page eq 'concluded'}">active</c:if>" aria-current="page" href="${appURL}/votes?page=concluded">Concluded</a>
            </li>
        </ul>
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
                                    <p class="text-end m-0">Number of votes: ${v.getNumberOfVotes()}</p>
                                </div>
                                <p class="card-text">${v.getDescription()}</p>
                                <c:if test="${sessionScope.activeSMPUser.getUserID() == v.getUserID()}">
                                    <div class="text-end">
                                        <c:if test="${v.getStartTime() == null}">
                                            <a href="${appURL}/start-vote?voteID=${v.getVoteID()}" class="btn btn-success">Begin Voting</a>
                                        </c:if>
                                        <a href="${appURL}/edit-vote?voteID=${v.getVoteID()}" class="btn btn-warning">Edit</a>
                                        <a href="${appURL}/delete-vote?voteID=${v.getVoteID()}" class="btn btn-danger">Delete</a>
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