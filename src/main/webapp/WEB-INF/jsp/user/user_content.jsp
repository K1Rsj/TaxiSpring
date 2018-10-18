<%@ taglib prefix="mytags" uri="https://mytags.com.ua" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>


<div class="content container">
    <c:if test="${not empty requestScope.informationMessage}">
        <h3>${requestScope.informationMessage}</h3>
    </c:if>

    <c:if test="${not empty sessionScope.order}">

        <table class="table table-striped bg-white text-black">
            <caption class="text-white"><fmt:message key="current.order"/></caption>
            <thead>
            <tr>
                <th scope="col"><fmt:message key="full.name"/></th>
                <th scope="col"><fmt:message key="your.car.is"/></th>
                <th scope="col"><fmt:message key="your.trip.costs"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><c:out value="${sessionScope.order.user.firstName} ${sessionScope.order.user.secondName}"/></td>
                <td><c:out value="${sessionScope.order.car.model} ${sessionScope.order.car.number}"/></td>
                <td><mytags:formatMoney money="${sessionScope.order.price}"/> <fmt:message key="hryvnia"/></td>
            </tr>
            <tr>
            </tr>
            </tbody>
        </table>
        <div>
            <form method="post" action="<c:url value="/confirm_order"/>" class="inline">
                <input type="hidden" name="extra_submit_param" value="extra_submit_value">
                <button type="submit" name="submit_param" value="submit_value" class="btn btn-success">
                    <fmt:message key="confirm.order"/>
                </button>
            </form>

            <form method="post" action="<c:url value="/cancel_order"/>" class="inline">
                <input type="hidden" name="extra_submit_param" value="extra_submit_value">
                <button type="submit" name="submit_param" value="submit_value" class="btn btn-danger">
                    <fmt:message key="cancel.order"/>
                </button>
            </form>
        </div>
    </c:if>
</div>
