<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<h1 class="text-center text-white mb-4">Add new user</h1>
<div>
    <form:form method="POST" modelAttribute="user" class="text-center text-white mb-4" action="/register">
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
                    <fmt:message key="login.main"/>
                </td>
                <td>
                    <spring:bind path="login">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="login" class="form-control"/>
                            <form:errors path="login" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <fmt:message key="password"/>
                </td>
                <td>
                    <spring:bind path="password">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="password" path="password" class="form-control"/>
                            <form:errors path="password" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <fmt:message key="email"/>
                </td>
                <td>
                    <spring:bind path="email">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="email" class="form-control"/>
                            <form:errors path="email" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <fmt:message key="first.name"/>
                </td>
                <td>
                    <spring:bind path="firstName">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="firstName" class="form-control"/>
                            <form:errors path="firstName" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <fmt:message key="second.name"/>
                </td>
                <td>
                    <spring:bind path="secondName">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="secondName" class="form-control"/>
                            <form:errors path="secondName" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <fmt:message key="phone.number"/>
                </td>
                <td>
                    <spring:bind path="phoneNumber">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="number" path="phoneNumber" class="form-control"/>
                            <form:errors path="phoneNumber" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <fmt:message key="money.spent"/>
                </td>
                <td>
                    <spring:bind path="moneySpent">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="number" path="moneySpent" class="form-control"/>
                            <form:errors path="moneySpent" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="role"><fmt:message key="role"/></label>
                </td>
                <td>
                    <select class="form-control form-control-lg rounded-0" name="role" id="role">
                        <option selected value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                        <option value="DRIVER">Driver</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <c:if test="${!empty user.login}">
                        <input type="submit" value="<spring:message text="Edit User"/>"/>
                    </c:if>
                    <c:if test="${empty user.login}">
                        <input type="submit" value="<spring:message text="Add User"/>"/>
                    </c:if>
                </td>
            </tr>
        </table>
    </form:form>
</div>