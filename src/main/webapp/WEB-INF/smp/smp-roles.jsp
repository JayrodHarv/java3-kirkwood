<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid p-0" style="margin-top: 75px; margin-bottom: 40px;">
  <div class="container py-3">

    <!-- Flash Message -->
    <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

    <div class="d-flex justify-content-between mb-4">
      <h2>${pageTitle}</h2>
      <div class="text-end">
        <a href="${appURL}/add-role" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add Role"><i class="bi bi-plus-lg"></i></a>
      </div>
    </div>
    <table class="table table-responsive table-bordered table-striped table-hover">
      <thead>
      <tr>
        <th>Role Name</th>
        <th>Description</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${roles}" var="role">
        <tr>
          <td>${role.getRoleID()}</td>
          <td>${role.getDescription()}</td>
          <td>
            <div class="btn-group">
              <a href="${appURL}/edit-role?roleID=${role.getRoleID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit Role"><i class="bi bi-pencil-fill"></i></a>
              <a class="btn btn-outline-danger" onclick="Confirm('${role.getRoleID()}')" data-bs-toggle="tooltip" data-bs-title="Delete Role"><i class="bi bi-trash3-fill"></i></a>
            </div>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>

  <!-- Modal -->
  <div class="modal fade" id="deleteModal" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <form action="${appURL}/delete-role" method="POST">
          <input type="hidden" id="roleID" name="roleID"/>
          <div class="modal-header">
            <h5 class="modal-title">Delete Role</h5>
            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete this role?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-danger">Confirm</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script>
      function Confirm(id) {
          $('#roleID').val(id);
          $('#deleteModal').modal('show');
      }
  </script>

</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>