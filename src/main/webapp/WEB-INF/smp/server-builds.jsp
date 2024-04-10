<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid py-3" style="margin-top: 75px; margin-bottom: 40px;">

  <!-- Flash Message -->
  <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

  <div class="d-flex justify-content-between mb-4">
    <h2>${pageTitle}</h2>
    <div class="text-end">
      <a href="${appURL}/add-build" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add Build"><i class="bi bi-plus-lg"></i></a>
    </div>
  </div>
  <div class="container-fluid p-0">
    <c:if test="${not empty results.getBuildListError}">
      <p class="alert alert-danger my-2">${results.getBuildListError}</p>
    </c:if>
    <div class="row row-cols-1 row-cols-xxl-2 g-4">
      <c:if test="${builds.size() > 0}">
        <c:forEach items="${builds}" var="b">
          <div class="col">
            <div class="card">
              <img class="card-img-top" src="${b.getBase64Image()}"/>
              <div class="card-body">
                <div class="d-flex justify-content-between mb-2">
                  <div class="d-flex gap-2 align-items-center">
                    <h5 class="m-0">${b.getBuildID()}</h5>
                    <span class="badge bg-primary-subtle border border-primary-subtle text-primary-emphasis rounded-pill p-2" data-bs-toggle="tooltip" data-bs-title="${b.getWorld().getDescription()}">${b.getWorld().getWorldID()}</span>
                    <span class="badge bg-success-subtle border border-success-subtle text-success-emphasis rounded-pill p-2" data-bs-toggle="tooltip" data-bs-title="${b.getBuildType().getDescription()}">${b.getBuildType().getBuildTypeID()}</span>
                  </div>
                  <c:if test="${b.getDateBuilt() != null}">
                    <p class="text-end m-0">Date Built: ${b.getDateBuilt()}</p>
                  </c:if>
                </div>
                <div class="d-flex justify-content-between mb-2">
                  <p class="card-text">${b.getDescription()}</p>
                  <c:if test="${b.getCoordinates() != null}">
                    <p class="text-end m-0">Coords: ${b.getCoordinates()}</p>
                  </c:if>
                </div>
                <p class="text-end mb-2">Posted By: ${b.getUser().getDisplayName()}</p>
                <c:if test="${sessionScope.activeSMPUser.getUserID() == b.getUser().getUserID()}">
                  <div class="text-end">
                    <div class="btn-group">
                      <a href="${appURL}/edit-build?build_id=${b.getBuildID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit Build"><i class="bi bi-pencil-fill"></i></a>
                      <a class="btn btn-outline-danger" onclick="Confirm('${b.getBuildID()}')" data-bs-toggle="tooltip" data-bs-title="Delete Build"><i class="bi bi-trash3-fill"></i></a>
                    </div>
                  </div>
                </c:if>
              </div>
            </div>
          </div>
        </c:forEach>
      </c:if>
    </div>
  </div>

  <!-- Modal -->
  <div class="modal fade" id="deleteModal" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <form action="${appURL}/delete-build" method="POST">
          <input type="hidden" id="buildID" name="buildID"/>
          <div class="modal-header">
            <h5 class="modal-title">Delete Build</h5>
            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete this build?
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
  function Confirm(id) {
    $('#buildID').val(id);
    $('#deleteModal').modal('show');
  }
</script>

<%@ include file="/WEB-INF/smp/bottom.jsp"%>
