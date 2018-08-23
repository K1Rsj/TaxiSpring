<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<div class="content container">

    <c:if test="${not empty requestScope.message}">
        <p>${requestScope.message}</p>
    </c:if>

    <c:set var="count" value="${requestScope.firstIndex}" scope="page"/>
    <table class="table table-striped bg-white text-black">
        <caption class="text-white"><fmt:message key="all.cars"/></caption>
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><fmt:message key="model"/></th>
            <th scope="col"><fmt:message key="number"/></th>
            <th scope="col"><fmt:message key="state"/></th>
            <th scope="col"><fmt:message key="driver"/></th>
            <th scope="col"><fmt:message key="type"/></th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.cars}" var="car">
            <c:set var="count" value="${count + 1}" scope="page"/>
            <tr>
                <td><c:out value="${count}"/></td>
                <td><c:out value="${car.model}"/></td>
                <td><c:out value="${car.number}"/></td>
                <td><c:out value="${car.state}"/></td>
                <td><c:out value="${car.driver}"/></td>
                <td><c:out value="${car.carType.type}"/></td>
                <td>
                    <form method="post" action="/cars/edit/${car.id}">
                        <input class="btn btn-danger" type="submit" value="Edit" name="edit">
                    </form>
                </td>
                <td>
                    <form method="post" action="/cars/remove/${car.id}">
                        <input class="btn btn-danger" type="submit" value="Remove" name="remove">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>