<%@ taglib prefix="mytags" uri="https://mytags.com.ua" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>


<div class="content container">

    <c:if test="${not empty requestScope.message}">
        <p>${requestScope.message}</p>
    </c:if>


    <table class="table table-striped bg-white text-black">
        <caption class="text-white"><fmt:message key="car.type"/></caption>
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><fmt:message key="type"/></th>
            <th scope="col"><fmt:message key="starting.price"/></th>
            <th scope="col"><fmt:message key="price.per.km"/></th>
            <th scope="col"><fmt:message key="discount.value"/></th>
            <sec:authorize access="hasRole('ADMIN')">
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.carTypes}" var="type">
            <c:set var="count" value="${pageScope.count + 1}" scope="page"/>
            <tr>
                <td><c:out value="${count}"/></td>
                <td><c:out value="${type.type}"/></td>
                <td><mytags:formatMoney money="${type.startingPrice}"/> <fmt:message
                        key="hryvnia"/></td>
                <td><mytags:formatMoney money="${type.pricePerKilometer}"/> <fmt:message
                        key="hryvnia"/></td>
                <td><c:out value="${type.discount}%"/></td>
                <sec:authorize access="hasRole('ADMIN')">
                    <td>
                        <form method="post" action="/types/edit/${type.id}">
                            <input class="btn btn-danger" type="submit" value="Edit" name="edit">
                        </form>
                    </td>
                    <td>
                        <form method="post" action="/types/remove/${type.id}">
                            <input class="btn btn-danger" type="submit" value="Remove" name="remove">
                        </form>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


