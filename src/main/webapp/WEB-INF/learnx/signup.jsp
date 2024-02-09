<%@include file="/WEB-INF/learnx/top.jsp"%>
<main>
    <section class="p-0 d-flex align-items-center position-relative overflow-hidden">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12 col-lg-8 m-auto">
                    <div class="row my-5">
                        <div class="col-sm-10 col-xl-8 m-auto">
                            <h2>Nice to meet you!</h2>
                            <p class="lead mb-4">Please sign up for an account.</p>

                            <!-- Form START -->
                            <form>
                                <!-- Email -->
                                <div class="mb-4">
                                    <label for="inputEmail1" class="form-label">Email address *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="bi bi-envelope-fill"></i></span>
                                        <input type="text" class="form-control border-0 bg-light rounded-end ps-1" placeholder="E-mail" id="inputEmail1">
                                    </div>
                                </div>
                                <!-- Password -->
                                <div class="mb-4">
                                    <label for="inputPassword1" class="form-label">Password *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="form-control border-0 bg-light rounded-end ps-1" placeholder="*********" id="inputPassword1">
                                    </div>
                                </div>
                                <!-- Confirm Password -->
                                <div class="mb-4">
                                    <label for="inputPassword2" class="form-label">Confirm Password *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="form-control border-0 bg-light rounded-end ps-1" placeholder="*********" id="inputPassword2">
                                    </div>
                                </div>
                                <!-- Check box -->
                                <div class="mb-4">
                                    <div class="form-check">
                                        <input type="checkbox" class="form-check-input" id="checkbox-1">
                                        <label class="form-check-label" for="checkbox-1">By signing up, you agree to the <a href="${appURL}/terms">terms and conditions</a></label>
                                    </div>
                                </div>
                                <!-- Button -->
                                <div class="align-items-center mt-0">
                                    <div class="d-grid">
                                        <button class="btn btn-primary mb-0" type="submit">Sign Up</button>
                                    </div>
                                </div>
                            </form>
                            <!-- Form END -->

                            <!-- Sign in link -->
                            <div class="mt-4 text-center">
                                <span>Already have an account? <a href="${appURL}/login">Log in here</a></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<%@include file="/WEB-INF/learnx/bottom.jsp"%>