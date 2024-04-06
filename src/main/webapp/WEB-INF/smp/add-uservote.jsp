<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container py-3" style="margin-bottom: 40px">
    <form action="${appURL}/add-uservote" method="POST">

        <!-- Flash Message -->
        <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

        <%-- Get Vote Error--%>
        <c:if test="${not empty results.getVoteError}">
            <p class="alert alert-danger my-2">${results.getVoteError}</p>
        </c:if>

        <div class="container-fluid p-0">
            <h2 class="form-label">${results.voteID}</h2>
            <h4 class="form-text mb-4">${results.description}</h4>

            <!-- Vote Options -->
            <div class="container mb-4">
                <c:if test="${not empty results.getOptionListError}">
                    <p class="alert alert-danger my-2">${results.getOptionListError}</p>
                </c:if>
                <div class="row row-cols-1 g-4">
                    <c:if test="${options.size() > 0}">
                        <ol>
                            <c:forEach items="${options}" var="o" varStatus="status">
                                <li class="mb-4">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="d-flex justify-content-between">
                                                <h5>${o.getTitle()}</h5>
                                            </div>
                                            <p class="card-text">${o.getDescription()}</p>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ol>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="card card-body shadow p-4 mb-4">
            <!-- Title -->
            <h4 class="mb-3">Select one of the options below:</h4>
            <ol class="list mb-0">
                <c:forEach items="${options}" var="o" varStatus="status">
                    <!-- Item -->
                    <li class="list-item mb-2">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="selectedOption" id="selectedOption">
                            <label class="form-check-label" for="selectedOption">
                                    ${o.getTitle()}
                            </label>
                        </div>
                    </li>
                </c:forEach>
            </ol>
        </div>

        <div class="d-flex justify-content-between p-0">
            <div class="btn btn-group p-0">
                <a href="${appURL}/votes" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </div>
            <p class="form-text text-end">* Indicates required fields</p>
        </div>
    </form>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
