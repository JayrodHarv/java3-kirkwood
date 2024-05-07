<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-top: 75px; margin-bottom: 40px;">
    <form action="${appURL}/edit-vote" method="POST">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <div class="container-fluid p-0">
            <h2 class="form-label">${pageTitle}</h2>

            <input type="hidden" name="oldVoteID" value="${results.voteID}">

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

            <!-- Vote Options -->
            <div class="d-flex justify-content-between">
                <h2>Vote Options</h2>
                <div class="text-end">
                    <a href="${appURL}/add-vote-option?voteID=${results.voteID}" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add Vote Option"><i class="bi bi-plus-lg"></i></a>
                </div>
            </div>
            <div class="container-fluid mb-4 p-0">
                <c:if test="${not empty results.getOptionListError}">
                    <p class="alert alert-danger my-2">${results.getOptionListError}</p>
                </c:if>
                <div class="row row-cols-1 g-4">
                    <c:if test="${options.size() > 0}">
                        <c:forEach items="${options}" var="o">
                            <div class="col">
                                <div class="card">
                                    <img src="${o.getBase64Image()}" class="card-img-top"/>
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between">
                                            <h5>${o.getTitle()}</h5>
                                        </div>
                                        <p class="card-text">${o.getDescription()}</p>
                                        <c:if test="${sessionScope.activeSMPUser.getUserID() == results.userID}">
                                            <div class="text-end">
                                                <a href="${appURL}/edit-vote-option?optionID=${o.getOptionID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit Vote Option"><i class="bi bi-pencil-fill"></i></a>
                                                <a class="btn btn-outline-danger" onclick="ConfirmDeletion('${o.getOptionID()}', '${results.voteID}')" data-bs-toggle="tooltip" data-bs-title="Delete Vote Option"><i class="bi bi-trash3-fill"></i></a>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-between p-0">
            <div class="btn btn-group p-0">
                <a href="${appURL}/votes?page=myVotes" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </div>
            <p class="form-text text-end">* Indicates required field</p>
        </div>
    </form>

    <!-- Delete Modal -->
    <div class="modal fade" id="deleteModal" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form action="${appURL}/delete-vote-option" method="POST">
                    <input type="hidden" id="deleteOptionID" name="optionID"/>
                    <input type="hidden" id="deleteVoteID" name="voteID"/>
                    <div class="modal-header">
                        <h5 class="modal-title">Delete Vote Option</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this vote option?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<script>
    function ConfirmDeletion(optionID, voteID) {
        $('#deleteOptionID').val(optionID);
        $('#deleteVoteID').val(voteID);
        $('#deleteModal').modal('show');
    }
</script>

<%@ include file="/WEB-INF/smp/bottom.jsp"%>
