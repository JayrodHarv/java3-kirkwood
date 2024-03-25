<%@include file="/WEB-INF/smp/top.jsp"%>
<main>
    <section class="p-0 d-flex align-items-center position-relative overflow-hidden">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12 col-lg-8 m-auto">
                    <div class="row my-5">
                        <div class="col-sm-10 col-xl-8 m-auto">
                            <h2>Nice to meet you!</h2>
                            <p class="lead mb-4">Please sign up for an account.</p>

                            <c:if test="${not empty results.userAddSuccess}">
                                <div class="alert alert-success mb-2" role="alert">
                                        ${results.userAddSuccess}
                                </div>
                            </c:if>
                            <c:if test="${not empty results.userAddFail}">
                                <div class="alert alert-danger mb-2" role="alert">
                                        ${results.userAddFail}
                                </div>
                            </c:if>

                            <!-- Form START -->
                            <form method="POST" action="${appURL}/smp-signup">
                                <!-- Email -->
                                <div class="mb-4">
                                    <label for="inputEmail1" class="form-label">Email address *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="bi bi-envelope-fill"></i></span>
                                        <input type="text" class="<c:if test="${not empty results.emailError}">is-invalid</c:if> form-control border-0 bg-light rounded-end ps-1" placeholder="E-mail" id="inputEmail1" name="inputEmail1" value="${results.email}">
                                        <c:if test="${not empty results.emailError}">
                                            <div class="invalid-feedback">${results.emailError}</div>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- Password -->
                                <div class="mb-4">
                                    <label for="inputPassword1" class="form-label">Password *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="<c:if test="${not empty results.password1Error}">is-invalid</c:if> form-control border-0 bg-light rounded-end ps-1" placeholder="*********" id="inputPassword1" name="inputPassword1" value="${results.password1}">
                                        <c:if test="${not empty results.password1Error}">
                                            <div class="invalid-feedback">${results.password1Error}</div>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- Confirm Password -->
                                <div class="mb-4">
                                    <label for="inputPassword2" class="form-label">Confirm Password *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="<c:if test="${not empty results.password2Error}">is-invalid</c:if> form-control border-0 bg-light rounded-end ps-1" placeholder="*********" id="inputPassword2" name="inputPassword2" value="${results.password2}">
                                        <c:if test="${not empty results.password2Error}">
                                            <div class="invalid-feedback">${results.password2Error}</div>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- DisplayName -->
                                <div class="mb-4">
                                    <label for="inputDisplayName" class="form-label">Display Name *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="bi bi-envelope-fill"></i></span>
                                        <input type="text" class="<c:if test="${not empty results.displayNameError}">is-invalid</c:if> form-control border-0 bg-light rounded-end ps-1" placeholder="Display Name" id="inputDisplayName" name="inputDisplayName" value="${results.displayName}">
                                        <c:if test="${not empty results.displayNameError}">
                                            <div class="invalid-feedback">${results.displayNameError}</div>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- Check box -->
                                <div class="mb-4">
                                    <div class="form-check">
                                        <input <c:if test="${results.terms eq 'agree'}">checked</c:if> type="checkbox" class="<c:if test="${not empty results.termsOfServiceError}">is-invalid</c:if> form-check-input" id="checkbox-1" name="checkbox-1" value="agree">
                                        <label class="form-check-label" for="checkbox-1">By signing up, you agree to the <a href="${appURL}/smp-terms">terms and conditions</a></label>
                                        <c:if test="${not empty results.termsOfServiceError}">
                                            <div class="invalid-feedback">${results.termsOfServiceError}</div>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- Button -->
                                <div class="align-items-center mt-0">
                                    <div class="d-grid">
                                        <button class="btn btn-orange mb-0" type="submit">Sign Up</button>
                                    </div>
                                </div>
                            </form>
                            <!-- Form END -->

                            <!-- Sign in link -->
                            <div class="mt-4 text-center">
                                <span>Already have an account? <a href="${appURL}/smp-login">Log in here</a></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<%@include file="/WEB-INF/smp/bottom.jsp"%>