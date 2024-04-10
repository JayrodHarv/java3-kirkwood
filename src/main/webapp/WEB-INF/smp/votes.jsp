<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-top: 75px; margin-bottom: 40px;">

    <!-- Flash Message -->
    <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

    <div class="d-flex justify-content-between mb-4">
        <h2>${pageTitle}</h2>
        <div class="text-end">
            <a href="${appURL}/add-vote" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add Vote"><i class="bi bi-plus-lg"></i></a>
        </div>
    </div>
    <div class="container-fluid p-0">
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
                <c:choose>
                    <c:when test="${results.page == 'active'}">
                        <%@include file="/WEB-INF/smp/active-vote-list.jsp"%>
                    </c:when>
                    <c:when test="${results.page == 'myVotes'}">
                        <%@include file="/WEB-INF/smp/my-votes-list.jsp"%>
                    </c:when>
                    <c:when test="${results.page == 'concluded'}">
                        <%@include file="/WEB-INF/smp/concluded-vote-list.jsp"%>
                    </c:when>
                </c:choose>
            </c:if>
        </div>
    </div>

    <!-- Delete Modal -->
    <div class="modal fade" id="deleteModal" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form action="${appURL}/delete-vote" method="POST">
                    <input type="hidden" id="deleteVoteID" name="voteID"/>
                    <div class="modal-header">
                        <h5 class="modal-title">Delete Vote</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to delete this vote?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Stop Modal -->
    <div class="modal fade" id="stopModal" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form action="${appURL}/stop-vote" method="POST">
                    <input type="hidden" id="stopVoteID" name="voteID"/>
                    <div class="modal-header">
                        <h5 class="modal-title">Stop Vote</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to stop voting for this vote?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-danger">Stop</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<script>
    function ConfirmDeletion(id) {
        $('#deleteVoteID').val(id);
        $('#deleteModal').modal('show');
    }
    function ConfirmStop(id) {
        $('#stopVoteID').val(id);
        $('#stopModal').modal('show');
    }
</script>

<%@ include file="/WEB-INF/smp/bottom.jsp"%>