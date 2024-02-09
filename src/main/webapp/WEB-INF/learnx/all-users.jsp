<%@ include file="/WEB-INF/learnx/top.jsp"%>
<main>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h1>All LearnX Users</h1>
                <p>There ${users.size() == 1 ? "is" : "are"}&nbsp;${users.size()} user${users.size() != 1 ? "s" : ""}</p>
                <c:if test="${users.size() > 0}">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">First name</th>
                                    <th scope="col">Last name</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Phone</th>
                                    <th scope="col">Language</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Privileges</th>
                                    <th scope="col">Created At</th>
                                    <th scope="col">Last Logged In</th>
                                    <th scope="col">Updated At</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${users}" var="user">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.firstName}</td>
                                        <td>${user.lastName}</td>
                                        <td>${user.email}</td>
                                        <td>${user.phone}</td>
                                        <td>${user.language}</td>
                                        <td>${user.status}</td>
                                        <td>${user.privileges}</td>
                                        <td>${user.created_at}</td>
                                        <td>${user.last_logged_in}</td>
                                        <td>${user.updated_at}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/learnx/bottom.jsp"%>