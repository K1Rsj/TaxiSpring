<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<div class="content container">

    <c:if test="${not empty requestScope.message}">
        <p>${requestScope.message}</p>
    </c:if>

    <c:set var="count" value="${requestScope.firstIndex}" scope="page"/>
    <table class="table table-striped bg-white text-black">
        <caption class="text-white"><fmt:message key="all.users"/></caption>
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><fmt:message key="login.main"/></th>
            <th scope="col"><fmt:message key="email"/></th>
            <th scope="col"><fmt:message key="first.name"/></th>
            <th scope="col"><fmt:message key="second.name"/></th>
            <th scope="col"><fmt:message key="phone.number"/></th>
            <th scope="col"><fmt:message key="role"/></th>
            <th scope="col">Edit</th>
            <th scope="col"><fmt:message key="delete"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.users}" var="user">
            <c:set var="count" value="${count + 1}" scope="page"/>
            <tr>
                <td><c:out value="${count}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.secondName}"/></td>
                <td><c:out value="${user.phoneNumber}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td>
                    <form method="post" action="/users/edit/${user.id}">
                        <input class="btn btn-danger" type="submit" value="Edit" name="edit">
                    </form>
                </td>
                <td>
                    <form method="post" action="/users/remove/${user.id}">
                        <input class="btn btn-danger" type="submit" value="Remove" name="remove">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>