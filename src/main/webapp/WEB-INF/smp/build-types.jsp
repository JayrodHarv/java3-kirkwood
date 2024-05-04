<div class="d-flex justify-content-between mb-4">
    <h2>Build Types</h2>
    <div class="text-end">
        <a href="${appURL}/add-build-type" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add Build Type"><i class="bi bi-plus-lg"></i></a>
    </div>
</div>
<table class="table table-responsive table-bordered table-striped table-hover">
    <thead>
    <tr>
        <th scope="col">Build Type Name</th>
        <th scope="col">Description</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${buildTypes}" var="buildType">
        <tr>
            <td>${buildType.getBuildTypeID()}</td>
            <td>${buildType.getDescription()}</td>
            <td>
                <div class="btn-group">
                    <a href="${appURL}/edit-build-type?buildTypeID=${buildType.getBuildTypeID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit Build Type"><i class="bi bi-pencil-fill"></i></a>
                    <a class="btn btn-outline-danger" onclick="Confirm('${buildType.getBuildTypeID()}')" data-bs-toggle="tooltip" data-bs-title="Delete Build Type"><i class="bi bi-trash3-fill"></i></a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Modal -->
<div class="modal fade" id="deleteModal" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="${appURL}/delete-build-type" method="POST">
                <input type="hidden" id="buildTypeID" name="buildTypeID"/>
                <div class="modal-header">
                    <h5 class="modal-title">Delete Build Type</h5>
                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete this build type?
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
        $('#buildTypeID').val(id);
        $('#deleteModal').modal('show');
    }
</script>
