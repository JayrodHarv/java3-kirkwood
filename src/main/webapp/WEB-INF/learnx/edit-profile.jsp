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
                        <form action="${appURL}/edit-profile" method="post" class="row">
                            <%-- First Name--%>
                            <div class="col-md-6">
                                <label class="form-label" for="firstNameInput">First Name</label>
                                <input class="form-control" type="text" id="firstNameInput" name="firstNameInput"
                                       value="${activeUser.firstName}">
                            </div>
                            <%-- Last Name--%>
                            <div class="col-md-6">
                                <label class="form-label" for="lastNameInput">Last Name</label>
                                <input class="form-control" type="text" id="lastNameInput" name="lastNameInput"
                                       value="${activeUser.lastName}">
                            </div>

                            <!-- Email id -->
                            <div class="col-md-6">
                                <label class="form-label" for="emailInput">Email</label>
                                <input class="form-control" type="text" id="emailInput" name="emailInput" value="${activeUser.email}">
                            </div>

                            <!-- Phone number -->
                            <div class="col-md-6">
                                <label class="form-label" for="phoneInput">Phone number</label>
                                <input type="text" class="form-control" id="phoneInput" name="phoneInput" value="${activeUser.phone}">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label" for="languageInput">Language</label>
                                <select class="form-select" name="languageInput" id="languageInput">
                                    <option value="en-US" <c:if test="${activeUser.language eq 'en-US'}">selected</c:if>>English</option>
                                    <option value="fr-FR" <c:if test="${activeUser.language eq 'fr-FR'}">selected</c:if>>French</option>
                                    <option value="en-US" <c:if test="${activeUser.language eq 'es-MX'}">selected</c:if>>English</option>
                                </select>
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