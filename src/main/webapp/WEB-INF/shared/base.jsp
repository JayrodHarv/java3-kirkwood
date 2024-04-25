<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="language" scope="session" value="${not empty language ? language : 'en'}"></c:set>
<fmt:setLocale value="${language}"></fmt:setLocale>
<fmt:setBundle basename="translation"></fmt:setBundle>

<c:choose>
    <c:when test="${pageContext.request.serverName eq 'localhost' }">
        <c:set var="appURL" value="${initParam['appURLLocal']}"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="appURL" value="${initParam['appURLCloud']}"></c:set>
    </c:otherwise>
</c:choose>
<c:set var="businessName" value="LearnX"></c:set>
<c:set var="smpName" value="No Go Outside SMP"></c:set>
