<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid p-0" style="margin-top: 75px; margin-bottom: 40px;">
    <div class="container py-3">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <div class="d-flex justify-content-between mb-4">
            <h2>Worlds</h2>
            <div class="text-end">
                <c:if test="${sessionScope.activeSMPUser.getRole().canAddWorlds()}">
                    <a href="${appURL}/add-world" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add World"><i class="bi bi-plus-lg"></i></a>
                </c:if>
            </div>
        </div>
        <table class="table table-responsive table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>World Name</th>
                <th>Date Started</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${worlds}" var="world">
                <tr>
                    <td>${world.getWorldID()}</td>
                    <td><fmt:formatDate value="${world.getDateStarted()}" type="date" dateStyle="long" timeStyle="long"></fmt:formatDate></td>
                    <td>${world.getDescription()}</td>
                    <td>
                        <div class="btn-group">
                            <c:if test="${sessionScope.activeSMPUser.getRole().canEditWorlds()}">
                                <a href="${appURL}/edit-world?worldID=${world.getWorldID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit World"><i class="bi bi-pencil-fill"></i></a>
                            </c:if>
                            <c:if test="${sessionScope.activeSMPUser.getRole().canDeleteWorlds()}">
                                <a class="btn btn-outline-danger" onclick="Confirm('${world.getWorldID()}')" data-bs-toggle="tooltip" data-bs-title="Delete World"><i class="bi bi-trash3-fill"></i></a>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Delete Modal -->
    <div class="modal" id="deleteModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Delete World</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this world?</p>
                </div>
                <form action="${appURL}/delete-world" method="POST">
                    <input type="hidden" id="worldID" name="worldID"/>
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
            $('#worldID').val(id);
            $('#deleteModal').modal('show');
        }
    </script>

</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>