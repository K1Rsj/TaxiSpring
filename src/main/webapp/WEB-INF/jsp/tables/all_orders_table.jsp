<%@ taglib prefix="mytags" uri="https://mytags.com.ua" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<div class="content container">

    <c:if test="${not empty requestScope.message}">
        <p>${requestScope.message}</p>
    </c:if>

    <c:set var="count" value="${requestScope.firstIndex}" scope="page"/>
    <table class="table table-striped bg-white text-black">
        <caption class="text-white">All orders</caption>
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><fmt:message key="departure.street"/></th>
            <th scope="col"><fmt:message key="destination.street"/></th>
            <th scope="col"><fmt:message key="price"/></th>
            <th scope="col"><fmt:message key="model"/></th>
            <th scope="col">Client</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.orders}" var="order">
            <c:set var="count" value="${pageScope.count + 1}" scope="page"/>
            <tr>
                <td><c:out value="${count}"/></td>
                <td><c:out value="${order.departureStreet}"/></td>
                <td><c:out value="${order.destinationStreet}"/></td>
                <td><mytags:formatMoney money="${order.price}"/> <fmt:message key="hryvnia"/></td>
                <td><c:out value="${order.car.model}"/></td>
                <td><c:out value="${order.user.firstName} ${order.user.secondName}"/></td>
                <td>
                    <form method="post" action="/orders/remove/${order.id}">
                        <input class="btn btn-danger" type="submit" value="Remove" name="remove">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>