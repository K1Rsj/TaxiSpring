<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAnonymous()">
    <jsp:include page="../components/header.jsp"/>
</sec:authorize>
<sec:authorize access="hasRole('USER')">
    <jsp:include page="user_header.jsp"/>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <jsp:include page="../admin/admin_header.jsp"/>
</sec:authorize>

<jsp:include page="../tables/all_car_types_table.jsp"/>

<sec:authorize access="hasRole('ADMIN')">
<jsp:include page="../form/add_car_type.jsp"/>
</sec:authorize>
