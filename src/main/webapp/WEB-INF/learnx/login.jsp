<%@include file="/WEB-INF/learnx/top.jsp"%>
<main>
    <section class="p-0 d-flex align-items-center position-relative overflow-hidden">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12 col-lg-8 m-auto">
                    <div class="row my-5">
                        <div class="col-sm-10 col-xl-8 m-auto">
                            <h2>${pageTitle}</h2>
                            <p class="lead mb-4">Please log in to your account.</p>

                            <c:if test="${not empty results.loginFail}">
                                <p class="alert alert-danger my-2">${results.loginFail}</p>
                            </c:if>
                            <c:if test="${not empty flashMessageSuccess}">
                                <div class="alert alert-success mb-2" role="alert">
                                        ${flashMessageSuccess}
                                </div>
                                <c:remove var="flashMessageSuccess" scope="session" />
                            </c:if>

                            <!-- Form START -->
                            <form method="post" action="${appURL}/login">
                                <!-- Email -->
                                <div class="mb-4">
                                    <label for="inputEmail1" class="form-label">Email address *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="bi bi-envelope-fill"></i></span>
                                        <input type="text" class="form-control border-0 bg-light rounded-end ps-1" placeholder="E-mail" id="inputEmail1" name="inputEmail1" value="${results.email}">
                                    </div>
                                </div>
                                <!-- Password -->
                                <div class="mb-4">
                                    <label for="inputPassword1" class="form-label">Password *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="form-control border-0 bg-light rounded-end ps-1" placeholder="*********" id="inputPassword1" name="inputPassword1" value="${results.password1}">
                                    </div>
                                </div>

                                <!-- Check box -->
                                <div class="mb-4">
                                    <div class="form-check">
                                        <input <c:if test="${results.remember eq 'remember'}">checked</c:if> type="checkbox" class="form-check-input" id="checkbox-1" name="checkbox-1" value="remember">
                                        <label class="form-check-label" for="checkbox-1">Remember me for 30 days</label>
                                    </div>
                                </div>
                                <!-- Button -->
                                <div class="align-items-center mt-0">
                                    <div class="d-grid">
                                        <button class="btn btn-orange mb-0" type="submit">Login</button>
                                    </div>
                                </div>
                            </form>
                            <!-- Form END -->

                            <!-- Password reset link -->
                            <div class="mt-4 text-center">
                                <span><a href="${appURL}/password-reset">Forgot password?</a></span>
                            </div>

                            <!-- Sign in link -->
                            <div class="mt-4 text-center">
                                <span>Don't have an account? <a href="${appURL}/login">Sign up here</a></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<%@include file="/WEB-INF/learnx/bottom.jsp"%>