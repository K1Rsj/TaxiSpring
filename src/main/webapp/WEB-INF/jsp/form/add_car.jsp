<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<h1 class="text-center text-white mb-4">Add new car</h1>
<div>
    <form:form action="/cars/add" modelAttribute="car" class="text-center text-white mb-4">
        <table align="center">
            <c:if test="${!empty car.number}">
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
                    <form:label path="model">
                        <spring:message text="Model"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="model"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="number">
                        <spring:message text="Number"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="number"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="state">
                        <spring:message text="State"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="state"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="driver">
                        <spring:message text="Driver"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="driver"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:if test="${!empty car.number}">
                        <input type="submit"
                               value="<spring:message text="Edit Car"/>"/>
                    </c:if>
                    <c:if test="${empty car.number}">
                        <input type="submit"
                               value="<spring:message text="Add Car"/>"/>
                    </c:if>
                </td>
            </tr>
        </table>
    </form:form>
</div>