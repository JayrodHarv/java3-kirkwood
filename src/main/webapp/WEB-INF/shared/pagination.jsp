<%-- Start Pagination--%>
<nav aria-label="pagination" class="my-2">
    <ul class="pagination justify-content-end mb-0">
        <li class="page-item <c:if test="${page eq 1}">disabled</c:if>">
            <a class="page-link" <c:if test="${page ne 1}">href="${appURL}/server-builds?page=1"</c:if>>First</a>
        </li>
        <li class="page-item <c:if test="${page eq 1}">disabled</c:if>">
            <a class="page-link" <c:if test="${page ne 1}">href="${appURL}/server-builds?page=${page - 1}"</c:if>>Previous</a>
        </li>
        <c:forEach var="i" begin="${beginPage}" end="${endPage}">
            <li class="page-item <c:if test="${page eq i}">active</c:if>">
                <a class="page-link" href="${appURL}/server-builds?page=${i}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item <c:if test="${page eq numberOfPages}">disabled</c:if>">
            <a class="page-link" <c:if test="${page ne numberOfPages}">href="${appURL}/server-builds?page=${page + 1}"</c:if>>Next</a>
        </li>
        <li class="page-item <c:if test="${page eq numberOfPages}">disabled</c:if>">
            <a class="page-link" <c:if test="${page ne numberOfPages}">href="${appURL}/server-builds?page=${numberOfPages}"</c:if>>Last</a>
        </li>
    </ul>
</nav>
<%-- Stop Pagination--%>