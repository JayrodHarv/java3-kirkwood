<%@include file="/WEB-INF/learnx/top.jsp" %>
<main>
  <div class="container">
<%--    <div class="col-12 mb-4">--%>
<%--      <div class="card p-4">--%>
<%--        <div class="card-title">--%>
<%--          <h1>Admin Dashboard</h1>--%>
<%--        </div>--%>
<%--      </div>&lt;%&ndash; close card &ndash;%&gt;--%>
<%--    </div>&lt;%&ndash; close col &ndash;%&gt;--%>
    <!-- Advanced filter responsive toggler START -->
    <!-- Divider -->
    <hr class="d-xl-none">
    <div class="col-12 col-xl-3 d-flex justify-content-end align-items-center">
      <button class="btn btn-primary d-xl-none" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasSidebar" aria-controls="offcanvasSidebar">
        <i class="fas fa-bars"></i>
      </button>
    </div>
    <div class="row">
      <%@include file="/WEB-INF/learnx/dashboard-sidebar.jsp" %>
    </div>
  </div>
</main>
<%@include file="/WEB-INF/learnx/bottom.jsp" %>