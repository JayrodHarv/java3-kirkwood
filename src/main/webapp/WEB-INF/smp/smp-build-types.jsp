<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid p-0" style="margin-top: 75px; margin-bottom: 40px;">
    <div class="container py-3">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <div class="d-flex justify-content-between mb-4">
            <h2>Build Types</h2>
            <div class="text-end">
                <c:if test="${sessionScope.activeSMPUser.getRole().canAddBuildTypes()}">
                    <a href="${appURL}/add-build-type" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add Build Type"><i class="bi bi-plus-lg"></i></a>
                </c:if>
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
                            <c:if test="${sessionScope.activeSMPUser.getRole().canEditBuildTypes()}">
                                <a href="${appURL}/edit-build-type?buildTypeID=${buildType.getBuildTypeID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit Build Type"><i class="bi bi-pencil-fill"></i></a>
                            </c:if>
                            <c:if test="${sessionScope.activeSMPUser.getRole().canDeleteBuildTypes()}">
                                <a class="btn btn-outline-danger" onclick="Confirm('${buildType.getBuildTypeID()}')" data-bs-toggle="tooltip" data-bs-title="Delete Build Type"><i class="bi bi-trash3-fill"></i></a>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Delete Modal -->
        <div class="modal" id="deleteModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Delete Build Type</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this world?</p>
                    </div>
                    <form action="${appURL}/delete-build-type" method="POST">
                        <input type="hidden" id="buildTypeID" name="buildTypeID"/>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
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
    </div>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
