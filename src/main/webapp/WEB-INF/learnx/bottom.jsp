<%@ include file="/WEB-INF/learnx/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.js"></script>
<script src="${appURL}/js/loading.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@srexi/purecounterjs@1.5.0/dist/purecounter_vanilla.js"></script>
<script>
    new PureCounter();
</script>
<c:if test="${pageTitle eq 'Group Chat'}">
  <script src="${appURL}/js/group-chat.js"></script>
</c:if>
<%@ include file="/WEB-INF/shared/bottom.jsp"%>

