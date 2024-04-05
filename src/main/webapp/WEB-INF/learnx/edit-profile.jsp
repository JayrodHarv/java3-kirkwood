<%@include file="/WEB-INF/learnx/top.jsp" %>
<main>
    <%@include file="/WEB-INF/learnx/dashboard-header.jsp" %>

    <div class="container">
        <div class="row">
            <%@include file="/WEB-INF/learnx/dashboard-sidebar.jsp" %>

            <div class="col-xl-9">
                <div class="card">
                    <div class="card-header bg-dark">
                        <h3 class="card-header-title text-light">Edit Profile</h3>
                    </div>
                    <div class="card-body">

                        <%@include file="/WEB-INF/learnx/flash-message.jsp"%>

                        <form action="${appURL}/edit-profile" method="post" class="row">
                            <%-- First Name--%>
                            <div class="col-md-6">
                                <label class="form-label" for="firstNameInput">First Name</label>
                                <input class="form-control" type="text" id="firstNameInput" name="firstNameInput"
                                       value="${fn:escapeXml(activeUser.firstName)}">
                            </div>
                            <%-- Last Name--%>
                            <div class="col-md-6">
                                <label class="form-label" for="lastNameInput">Last Name</label>
                                <input class="form-control" type="text" id="lastNameInput" name="lastNameInput"
                                       value="${fn:escapeXml(activeUser.lastName)}">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label" for="languageInput">Language</label>
                                <select class="form-select <c:if test="${not empty results.languageError}">is-invalid</c:if>" name="languageInput" id="languageInput">
                                    <option value="en" <c:if test="${activeUser.language eq 'en'}">selected</c:if>>English</option>
                                    <option value="fr" <c:if test="${activeUser.language eq 'fr'}">selected</c:if>>French</option>
                                    <option value="es" <c:if test="${activeUser.language eq 'de'}">selected</c:if>>German</option>
                                </select>
                                <c:if test="${not empty results.languageError}">
                                    <div class="invalid-feedback">${results.languageError}</div>
                                </c:if>
                            </div>

                            <!-- Save button -->
                            <div class="d-sm-flex justify-content-end">
                                <button type="submit" class="btn btn-primary mb-0">Save changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</main>
<%@include file="/WEB-INF/learnx/bottom.jsp" %>