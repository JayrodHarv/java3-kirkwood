<div class="d-flex justify-content-between mb-4">
    <h2>Worlds</h2>
    <div class="text-end">
        <a href="${appURL}/add-world" class="btn btn-success" data-bs-toggle="tooltip" data-bs-title="Add World"><i class="bi bi-plus-lg"></i></a>
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
                    <a href="${appURL}/edit-world?worldId=${user.getWorldID()}" class="btn btn-outline-warning" data-bs-toggle="tooltip" data-bs-title="Edit World"><i class="bi bi-pencil-fill"></i></a>
                    <a class="btn btn-outline-danger" onclick="Confirm('${user.getWorldID()}')" data-bs-toggle="tooltip" data-bs-title="Delete World"><i class="bi bi-trash3-fill"></i></a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>