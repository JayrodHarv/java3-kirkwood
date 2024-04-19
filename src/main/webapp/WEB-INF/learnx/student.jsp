<%@include file="/WEB-INF/learnx/top.jsp" %>
<main>
    <%@include file="/WEB-INF/learnx/dashboard-header.jsp" %>

    <div class="container">
        <div class="row">
            <%@include file="/WEB-INF/learnx/dashboard-sidebar.jsp" %>

            <!-- Main content START -->
            <div class="col-xl-9">

                <!-- Counter boxes START -->
                <div class="row mb-4">
                    <!-- Counter item -->
                    <div class="col-sm-6 col-lg-4 mb-3 mb-lg-0">
                        <div class="d-flex justify-content-center align-items-center p-4 bg-orange bg-opacity-15 rounded-3">
                            <span class="display-6 lh-1 text-orange mb-0"><i class="fas fa-tv fa-fw"></i></span>
                            <div class="ms-4">
                                <div class="d-flex">
                                  <h5 class="purecounter mb-0 fw-bold" data-purecounter-start="0" data-purecounter-end="${fn:length(enrollments)}" data-purecounter-delay="200">${fn:length(enrollments)}</h5>
                                </div>
                                <p class="mb-0 h6 fw-light">Total Courses</p>
                            </div>
                        </div>
                    </div>
                    <!-- Counter item -->
<%--                    <div class="col-sm-6 col-lg-4 mb-3 mb-lg-0">--%>
<%--                        <div class="d-flex justify-content-center align-items-center p-4 bg-purple bg-opacity-15 rounded-3">--%>
<%--                            <span class="display-6 lh-1 text-purple mb-0"><i class="fas fa-clipboard-check fa-fw"></i></span>--%>
<%--                            <div class="ms-4">--%>
<%--                                <div class="d-flex">--%>
<%--                                    <h5 class="purecounter mb-0 fw-bold" data-purecounter-start="0" data-purecounter-end="52"	data-purecounter-delay="200">0</h5>--%>
<%--                                </div>--%>
<%--                                <p class="mb-0 h6 fw-light">Lessons Complete</p>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                    <!-- Counter item -->
<%--                    <div class="col-sm-6 col-lg-4 mb-3 mb-lg-0">--%>
<%--                        <div class="d-flex justify-content-center align-items-center p-4 bg-success bg-opacity-10 rounded-3">--%>
<%--                            <span class="display-6 lh-1 text-success mb-0"><i class="fas fa-medal fa-fw"></i></span>--%>
<%--                            <div class="ms-4">--%>
<%--                                <div class="d-flex">--%>
<%--                                    <h5 class="purecounter mb-0 fw-bold" data-purecounter-start="0" data-purecounter-end="8" data-purecounter-delay="300">0</h5>--%>
<%--                                </div>--%>
<%--                                <p class="mb-0 h6 fw-light">Course Certificates</p>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                </div>
                <!-- Counter boxes END -->

                <div class="card bg-transparent border rounded-3">
                    <!-- Card header START -->
                    <div class="card-header bg-transparent border-bottom">
                        <h3 class="mb-0">My Courses</h3>
                    </div>
                    <!-- Card header END -->

                  <!-- Flash Message -->
                  <%@include file="flash-message.jsp"%>

                    <!-- Card body START -->
                    <div class="card-body">

                        <!-- Course list table START -->
                        <div class="table-responsive border-0">
                            <table class="table table-dark-gray align-middle p-4 mb-0 table-hover">
                                <!-- Table head -->
                                <thead>
                                <tr>
                                    <th scope="col" class="border-0 rounded-start">Course Title</th>
<%--                                    <th scope="col" class="border-0">Total Lessons</th>--%>
<%--                                    <th scope="col" class="border-0">Completed Lessons</th>--%>
                                    <th scope="col" class="border-0 rounded-end">Percent Complete</th>
                                </tr>
                                </thead>
                                <!-- Table body START -->
                                <tbody>

                                <c:forEach items="${enrollments}" var="enrollment">
                                  <!-- Table item -->
                                  <tr>
                                      <!-- Table data -->
                                      <td>
                                          <div class="d-flex align-items-center">
                                              <div class="mb-0 ms-2">
                                                  <!-- Title -->
                                                  <h6><a href="#">${enrollment.key.name}</a></h6>
                                              </div>
                                          </div>
                                      </td>

                                      <!-- Table data -->
  <%--                                    <td>56</td>--%>

                                      <!-- Table data -->
  <%--                                    <td>40</td>--%>

                                      <!-- Table data -->
                                      <td>
                                          <!-- Info -->
                                          <div class="overflow-hidden">
                                            <c:set value="${23 / 24}" var="percent_complete"></c:set>
                                            <h6 class="mb-0"><fmt:formatNumber value="${percent_complete}" type="percent" maxFractionDigits="1"></fmt:formatNumber></h6>
                                              <div class="progress progress-sm bg-primary bg-opacity-10">
                                                  <div class="progress-bar bg-primary aos" role="progressbar" data-aos="slide-right" data-aos-delay="200" data-aos-duration="1000" data-aos-easing="ease-in-out" style="width: ${percent_complete * 100}%" aria-valuenow="${percent_complete * 100}" aria-valuemin="0" aria-valuemax="100">
                                                  </div>
                                              </div>
                                          </div>
                                      </td>
                                  </tr>
                                </c:forEach>
                                </tbody>
                                <!-- Table body END -->
                            </table>
                        </div>
                        <!-- Course list table END -->
                    </div>
                    <!-- Card body START -->
                </div>
            </div>
            <!-- Main content END -->

        </div>
    </div>
</main>
<%@include file="/WEB-INF/learnx/bottom.jsp" %>