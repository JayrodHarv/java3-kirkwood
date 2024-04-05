<%@include file="/WEB-INF/learnx/top.jsp"%>
<main>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h1>${pageTitle}</h1>
                <p>There ${courses.size() eq 1 ? "is" : "are"}&nbsp;${courses.size()} course${courses.size() ne 1 ? "s" : ""}</p>
                <c:if test="${courses.size() > 0}">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Name</th>
                                <th scope="col">Description</th>
                                <th scope="col">Level</th>
                                <th scope="col">Picture</th>
                                <th scope="col">Teacher</th>
                                <th scope="col">Category</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${courses}" var="course">
                                <tr>
                                    <td>${course.id}</td>
                                    <td>${course.name}</td>
                                    <td>${course.description}</td>
                                    <td>${course.level}</td>
                                    <td>${course.picture}</td>
                                    <td>${course.teacherFirstName}&nbsp;${course.teacherLastName}</td>
                                    <td>${course.categoryName}</td>
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
<%@include file="/WEB-INF/learnx/bottom.jsp"%>