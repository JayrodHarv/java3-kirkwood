<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-top: 75px; margin-bottom: 40px;">
  <form action="${appURL}/add-role" method="POST">

    <!-- Flash Message -->
    <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

    <div class="container-fluid p-0">
      <h2 class="mb-4">${pageTitle}</h2>

      <!-- Role Name -->
      <div class="input-group mb-4">
        <label for="buildTypeID" class="input-group-text">Role Name *</label>
        <input type="text" class="form-control rounded-end <c:if test="${not empty results.buildTypeError}">is-invalid</c:if>" placeholder="Ex. Capitol" id="buildTypeID" name="buildTypeID" value="${results.buildTypeID}">
        <c:if test="${not empty results.buildTypeIDError}">
          <div class="invalid-feedback">${results.buildTypeIDError}</div>
        </c:if>
      </div>

      <!-- Permissions Accordion -->
      <h4 class="mb-4">Permissions</h4>
      <div class="accordion mb-4" id="permissionsAccordion">
        <div class="accordion-item">
          <h2 class="accordion-header">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
              Build Permissions *
            </button>
          </h2>
          <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
            <div class="accordion-body">

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canAddBuilds" name="canAddBuilds">
                <label class="form-check-label" for="canAddBuilds">Can Add Builds</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canEditAllBuilds" name="canEditAllBuilds">
                <label class="form-check-label" for="canEditAllBuilds">Can Edit All Builds</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canDeleteAllBuilds" name="canDeleteAllBuilds">
                <label class="form-check-label" for="canDeleteAllBuilds">Can Delete All Builds</label>
              </div>

            </div>
          </div>
        </div>
        <div class="accordion-item">
          <h2 class="accordion-header">
            <button class="accordion-button collapse" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
              Build Type Permissions *
            </button>
          </h2>
          <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
            <div class="accordion-body">

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canViewBuildTypes" name="canViewBuildTypes">
                <label class="form-check-label" for="canViewBuildTypes">Can View Build Types</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canAddBuildTypes" name="canAddBuildTypes">
                <label class="form-check-label" for="canAddBuildTypes">Can Add Build Types</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canEditBuildTypes" name="canEditBuildTypes">
                <label class="form-check-label" for="canEditBuildTypes">Can Edit Build Types</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canDeleteBuildTypes" name="canDeleteBuildTypes">
                <label class="form-check-label" for="canDeleteBuildTypes">Can Delete Build Types</label>
              </div>

            </div>
          </div>
        </div>
        <div class="accordion-item">
          <h2 class="accordion-header">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
              World Permissions *
            </button>
          </h2>
          <div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
            <div class="accordion-body">

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canViewWorlds" name="canViewWorlds">
                <label class="form-check-label" for="canViewWorlds">Can View Worlds</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canAddWorlds" name="canAddWorlds">
                <label class="form-check-label" for="canAddWorlds">Can Add Worlds</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canEditWorlds" name="canEditWorlds">
                <label class="form-check-label" for="canEditWorlds">Can Edit Worlds</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canDeleteWorlds" name="canDeleteWorlds">
                <label class="form-check-label" for="canDeleteWorlds">Can Delete Worlds</label>
              </div>

            </div>
          </div>
        </div>
        <div class="accordion-item">
          <h2 class="accordion-header">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
              Vote Permissions *
            </button>
          </h2>
          <div id="collapseFour" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
            <div class="accordion-body">

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canViewAllVotes" name="canViewAllVotes">
                <label class="form-check-label" for="canViewAllVotes">Can View All Votes</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canAddVotes" name="canAddVotes">
                <label class="form-check-label" for="canAddVotes">Can Add Votes</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canEditAllVotes" name="canEditAllVotes">
                <label class="form-check-label" for="canEditAllVotes">Can Edit All Votes</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canDeleteAllVotes" name="canDeleteAllVotes">
                <label class="form-check-label" for="canDeleteAllVotes">Can Delete All Votes</label>
              </div>

            </div>
          </div>
        </div>
        <div class="accordion-item">
          <h2 class="accordion-header">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
              Role Permissions *
            </button>
          </h2>
          <div id="collapseFive" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
            <div class="accordion-body">

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canViewRoles" name="canViewRoles">
                <label class="form-check-label" for="canViewRoles">Can View Roles</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canAddRoles" name="canAddRoles">
                <label class="form-check-label" for="canAddRoles">Can Add Roles</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canEditRoles" name="canEditRoles">
                <label class="form-check-label" for="canEditRoles">Can Edit Roles</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canDeleteRoles" name="canDeleteRoles">
                <label class="form-check-label" for="canDeleteRoles">Can Delete Roles</label>
              </div>

            </div>
          </div>
        </div>
        <div class="accordion-item">
          <h2 class="accordion-header">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
              User Permissions *
            </button>
          </h2>
          <div id="collapseSix" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
            <div class="accordion-body">

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canViewUsers" name="canViewUsers">
                <label class="form-check-label" for="canViewUsers">Can View Users</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canAddUsers" name="canAddUsers">
                <label class="form-check-label" for="canAddUsers">Can Add Users</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canEditUsers" name="canEditUsers">
                <label class="form-check-label" for="canEditUsers">Can Edit Users</label>
              </div>

              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="canBanUsers" name="canBanUsers">
                <label class="form-check-label" for="canBanUsers">Can Ban Users</label>
              </div>

            </div>
          </div>
        </div>
      </div>

      <!-- Description -->
      <div class="input-group mb-4">
        <label for="description" class="input-group-text">Description *</label>
        <input type="text" class="form-control rounded-end <c:if test="${not empty results.descriptionError}">is-invalid</c:if>" id="description" name="description" value="${results.description}">
        <c:if test="${not empty results.descriptionError}">
          <div class="invalid-feedback">${results.descriptionError}</div>
        </c:if>
      </div>
    </div>
    <div class="d-flex justify-content-between p-0">
      <div class="btn btn-group p-0">
        <a href="${appURL}/smp-users" class="btn btn-secondary">Back</a>
        <button type="submit" class="btn btn-primary">Add Role</button>
      </div>
      <p class="form-text text-end">* Indicates required field</p>
    </div>
  </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
