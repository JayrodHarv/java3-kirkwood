<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid">
  <div class="d-flex justify-content-md-between">
    <h2>${pageTitle}</h2>
    <div class="text-end">
      <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#add-building">Add New Building</button>
    </div>
  </div>
  <div class="container-fluid">
    <div class="row-lg-10">
      <c:forEach items="${results.buildings}" var="b">
        <div class="card mb-4">
          <img class="card-img-top" src="data:image/png;base64,${b.BuildingID}">
          <div class="card-body">
            <h5 class="card-title">${b.Name}}</h5>
            <p class="card-text">${b.Description}</p>
          </div>
        </div>
      </c:forEach>
    </div>
  </div>

  <div class="modal fade" id="add-building" tabindex="-1" role="dialog" aria-labelledby="addBuildingLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <form>
          <div class="modal-header">
            <h5 class="modal-title" id="addBuildingLabel">Add New Building</h5>
            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">

            <!-- Building Name -->
            <div class="mb-4">
              <div class="input-group input-group-lg">
                <div class="form-floating mb-3">
                  <input type="text" class="<c:if test="${not empty results.buildingNameError}">is-invalid</c:if> form-control border-0 bg-light rounded-end ps-1" id="buildingName" name="buildingName" placeholder="beans">
                  <label for="buildingName">Building Name</label>
                </div>
                <c:if test="${not empty results.buildingNameError}">
                  <div class="invalid-feedback">${results.buildingNameError}</div>
                </c:if>
              </div>
            </div>

            <!-- Description -->
            <div class="mb-4">
              <div class="input-group input-group-lg">
                <div class="form-floating mb-3">
                  <input type="text" class="<c:if test="${not empty results.descriptionError}">is-invalid</c:if> form-control border-0 bg-light rounded-end ps-1" id="description" name="description" placeholder="beans">
                  <label for="description">Building Name</label>
                </div>
                <c:if test="${not empty results.descriptionError}">
                  <div class="invalid-feedback">${results.descriptionError}</div>
                </c:if>
              </div>
            </div>

            <!-- Tags TODO: Make it so user can create new tag if one doesn't exist for their needs -->
            <div class="mb-4">
              <label for="tags" class="form-label">Tags</label>
              <div class="input-group input-group-lg">
                <select multiple class="form-select" id="tags">
                  <c:forEach items="${results.tags}" var="t">
                    <option value="${t.TagID}">${t.TagID} - ${t.Description}</option>
                  </c:forEach>
                </select>
                <small>Select multiple by using Shift or control</small>
              </div>
            </div>

          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save changes</button>
          </div>
        </form>
      </div>
    </div>
  </div>

</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
