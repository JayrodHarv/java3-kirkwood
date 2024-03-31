<%@ include file="/WEB-INF/smp/top.jsp"%>
<main class="container-fluid p-3" style="margin-bottom: 40px">
  <div class="d-flex justify-content-md-between">
    <h2>${pageTitle}</h2>
    <div class="text-end">
      <a href="${appURL}/add-build" class="btn btn-primary">Add New Build</a>
    </div>
  </div>
  <div class="container-fluid">
    <c:if test="${not empty results.getBuildListError}">
      <p class="alert alert-danger my-2">${results.getBuildListError}</p>
    </c:if>
    <div class="row-lg-10">
      <c:if test="${builds.size() > 0}">
        <c:forEach items="${builds}" var="b">
          <div class="card mb-4">
            <div class="card-header d-flex justify-content-md-between">
              <h5>${b.getBuildID()}</h5>
              <p class="text-end">${b.getDateBuilt()}</p>
            </div>
            <img class="card-img-middle" src="images/smp/void.png">
            <div class="card-body">

              <p class="card-text">${b.getDescription()}</p>
            </div>
          </div>
        </c:forEach>
      </c:if>
    </div>
  </div>
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
