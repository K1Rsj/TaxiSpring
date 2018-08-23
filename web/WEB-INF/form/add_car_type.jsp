<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<h1 class="text-center text-white mb-4">Add new car type</h1>

<form:form action="/types/add" modelAttribute="carType" class="text-center text-white mb-4">
    <table  align="center">
        <c:if test="${!empty carType.type}">
            <tr>
                <td>
                    <form:label path="id">
                    </form:label>
                </td>
                <td>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="type">
                    <spring:message text="Type"/>
                </form:label>
            </td>
            <td>
                <form:input path="type"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="startingPrice">
                    <spring:message text="Starting Price"/>
                </form:label>
            </td>
            <td>
                <form:input path="startingPrice"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="pricePerKilometer">
                    <spring:message text="Price Per Kilometer"/>
                </form:label>
            </td>
            <td>
                <form:input path="pricePerKilometer"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="discount">
                    <spring:message text="Discount"/>
                </form:label>
            </td>
            <td>
                <form:input path="discount"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${!empty carType.type}">
                    <input type="submit"
                           value="<spring:message text="Edit Type"/>"/>
                </c:if>
                <c:if test="${empty carType.type}">
                    <input type="submit"
                           value="<spring:message text="Add type"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
</form:form>