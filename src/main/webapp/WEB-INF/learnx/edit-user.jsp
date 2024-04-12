<jsp:include page="/WEB-INF/learnx/top.jsp"></jsp:include>
<main>
  <!-- Page content START -->
  <section class="pt-0">
    <div class="container">
      <div class="row">

        <jsp:include page="/WEB-INF/learnx/dashboard-sidebar.jsp"></jsp:include>

        <!-- Main content START -->
        <div class="col-xl-9">
          <!-- Edit profile START -->
          <div class="card bg-transparent border rounded-3">
            <!-- Card header -->
            <div class="card-header bg-transparent border-bottom">
              <h3 class="card-header-title mb-0">${pageTitle}</h3>
            </div>
            <!-- Card body START -->
            <div class="card-body">
              <c:if test="${not empty results.editUserSuccess}">
                <div class="alert alert-success mb-2" role="alert">
                    ${results.editUserSuccess}
                </div>
              </c:if>
              <c:if test="${not empty results.editUserWarning}">
                <div class="alert alert-warning mb-2" role="alert">
                    ${results.editUserWarning}
                </div>
              </c:if>

              <!-- Form -->
              <form class="row g-4" method="POST" action="${appURL}/edit-user?id=${user.id}">
                <input type="hidden" name="id" value="${user.id}">

                <!-- First name -->
                <div class="col-md-6">
                  <label class="form-label" for="firstNameInput">First Name</label>
                  <input class="form-control <c:if test="${not empty results.firstNameError}">is-invalid</c:if>" type="text" id="firstNameInput" name="firstNameInput" value="${user.firstName}">
                  <c:if test="${not empty results.firstNameError}"><div class="invalid-feedback">${results.firstNameError}</div></c:if>
                </div>

                <!-- Last name -->
                <div class="col-md-6">
                  <label class="form-label" for="lastNameInput">Last Name</label>
                  <input type="text" class="form-control <c:if test="${not empty results.lastNameError}">is-invalid</c:if>" id="lastNameInput" name="lastNameInput" value="${user.lastName}">
                  <c:if test="${not empty results.lastNameError}"><div class="invalid-feedback">${results.lastNameError}</div></c:if>
                </div>

                <!-- Email -->
                <div class="col-md-6">
                  <label class="form-label" for="emailInput">Email</label>
                  <input class="form-control <c:if test="${not empty results.emailError}">is-invalid</c:if>" type="text" id="emailInput" name="emailInput" value="${user.email}">
                  <c:if test="${not empty results.emailError }"><div class="invalid-feedback">${results.emailError}</div></c:if>
                </div>

                <!-- Phone number -->
                <div class="col-md-6">
                  <label class="form-label" for="phoneInput">Phone number</label>
                  <input type="text" class="form-control <c:if test="${not empty results.phoneError}">is-invalid</c:if>" id="phoneInput" name="phoneInput" value="${user.phone}">
                  <c:if test="${not empty results.phoneError }"><div class="invalid-feedback">${results.phoneError}</div></c:if>
                </div>

                <!-- Select option -->
                <div class="col-md-6">
                  <!-- Language Preference -->
                  <label class="form-label">Language</label>
                  <select class="form-select <c:if test="${not empty results.languageError}">is-invalid</c:if>" aria-label=".form-select-sm" id="languageInput" name="languageInput">
                    <option value="en-US" ${user.language == 'en-US' ? 'selected' : ''}>English</option>
                    <option value="es-MX" ${user.language == 'es-MX' ? 'selected' : ''}>Spanish</option>
                    <option value="fr-FR" ${user.language == 'fr-FR' ? 'selected' : ''}>French</option>
                  </select>
                  <c:if test="${not empty results.languageError }"><div class="invalid-feedback">${results.languageError}</div></c:if>
                </div>

                <!-- Select option -->
                <div class="col-md-6">
                  <!-- Status Preference -->
                  <label class="form-label">Status</label>
                  <select class="form-select <c:if test="${not empty results.statusError}">is-invalid</c:if>" aria-label=".form-select-sm" id="statusInput" name="statusInput">
                    <option value="inactive" ${user.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                    <option value="active" ${user.status == 'active' ? 'selected' : ''}>Active</option>
                    <option value="locked" ${user.status == 'locked' ? 'selected' : ''}>Locked</option>
                  </select>
                  <c:if test="${not empty results.statusError }"><div class="invalid-feedback">${results.statusError}</div></c:if>
                </div>

                <!-- Select option -->
                <div class="col-md-6">
                  <!-- Privileges Preference -->
                  <label class="form-label">Privileges</label>
                  <select class="form-select <c:if test="${not empty results.privilegesError}">is-invalid</c:if>" aria-label=".form-select-sm" id="privilegesInput" name="privilegesInput">
                    <option value="student" ${user.privileges == 'student' ? 'selected' : ''}>Student</option>
                    <option value="teach" ${user.privileges == 'teach' ? 'selected' : ''}>Teacher</option>
                    <option value="admin" ${user.privileges == 'admin' ? 'selected' : ''}>Admin</option>
                  </select>
                  <c:if test="${not empty results.privilegesError }"><div class="invalid-feedback">${results.privilegesError}</div></c:if>
                </div>

                <!-- Save button -->
                <div class="d-sm-flex justify-content-end">
                  <button type="submit" class="btn btn-success mb-0 me-2" name="submit-btn" value="save">Save changes</button>
                  <button type="submit" class="btn btn-danger mb-0" name="submit-btn" value="delete">Delete user</button>
                </div>
              </form>
            </div>
            <!-- Card body END -->
          </div>
          <!-- Edit profile END -->
        </div>
        <!-- Main content END -->
      </div><!-- Row END -->
    </div>
  </section>
</main>
<jsp:include page="/WEB-INF/learnx/bottom.jsp"></jsp:include>