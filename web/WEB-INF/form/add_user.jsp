<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<h1 class="text-center text-white mb-4">Add new user</h1>
<div>
    <form:form action="/register" modelAttribute="user" class="text-center text-white mb-4">
        <table align="center">
            <c:if test="${!empty user.login}">
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
                    <form:label path="login">
                        <spring:message text="Login"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="login"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="password">
                        <spring:message text="Password"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="password"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="email">
                        <spring:message text="Email"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="email"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="firstName">
                        <spring:message text="First name"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="firstName"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="secondName">
                        <spring:message text="Second name"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="secondName"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="phoneNumber">
                        <spring:message text="Phone number"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="phoneNumber"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="moneySpent">
                        <spring:message text="Money spent"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="moneySpent"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="role">
                        <spring:message text="Role"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="role"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:if test="${!empty user.login}">
                        <input type="submit"
                               value="<spring:message text="Edit User"/>"/>
                    </c:if>
                    <c:if test="${empty user.login}">
                        <input type="submit"
                               value="<spring:message text="Add User"/>"/>
                    </c:if>
                </td>
            </tr>
        </table>
    </form:form>
</div>