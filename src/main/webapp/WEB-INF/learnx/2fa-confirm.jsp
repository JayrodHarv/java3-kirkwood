<jsp:include page="/WEB-INF/learnx/top.jsp"></jsp:include>
<main>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xl-6 col-lg-8 col-md-10 col-sm-12">
                <h2>${pageTitle}</h2>
                <p>Please enter the code that was sent to your email.</p>
                <c:if test="${not empty results.codeError}">
                    <p class="alert alert-danger my-2">${results.codeError}</p>
                </c:if>
                <c:if test="${not empty codeResent}">
                    <p class="alert alert-success my-2">${codeResent}</p>
                </c:if>
                <form action="${appURL}/confirm" method="POST">
                    <!-- Email -->
                    <div class="mb-4">
                        <label for="inputCode" class="form-label">Code</label>
                        <div class="input-group input-group-lg">
                            <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="fas fa-lock"></i></span>
                            <input type="text" class="form-control border-0 bg-light rounded-end ps-1" placeholder="XXXXXX" id="inputCode" name="inputCode" value="${results.code}">
                        </div>
                    </div>
                    <!-- Button -->
                    <div class="align-items-center mt-0">
                        <div class="d-grid">
                            <button class="btn btn-orange mb-0" type="submit">Confirm</button>
                        </div>
                    </div>
                    <div class="mt-4 text-center">
                        <span>Didn't receive the code? <a href="${appURL}/confirm?resend">Resend</a></span>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/learnx/bottom.jsp"></jsp:include>