<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<h1 class="text-center text-white mb-4">Add new car</h1>
<div>
    <form:form method="POST" modelAttribute="car" class="text-center text-white mb-4" action="/cars/add">
        <table align="center">
            <c:if test="${!empty car.model}">
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
                    <fmt:message key="model"/>
                </td>
                <td>
                    <spring:bind path="model">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="model" class="form-control"/>
                            <form:errors path="model" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <fmt:message key="number"/>
                </td>
                <td>
                    <spring:bind path="number">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="number" class="form-control"/>
                            <form:errors path="number" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <fmt:message key="driver"/>
                </td>
                <td>
                    <spring:bind path="driver">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="driver" class="form-control"/>
                            <form:errors path="driver" cssClass="errorForm"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="state"><fmt:message key="state"/></label>
                </td>
                <td>
                    <select class="form-control form-control-lg rounded-0" name="state" id="state">
                        <option selected value="FREE">Free</option>
                        <option value="BUSY">Busy</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="carType"><fmt:message key="car.type"/></label>
                </td>
                <td>
                    <select class="form-control form-control-lg rounded-0" name="carType" id="carType">
                        <option selected value="standard">Standard</option>
                        <option value="comfort">Comfort</option>
                        <option value="minibus">Minibus</option>
                        <option value="premium">Premium</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <c:if test="${!empty car.model}">
                        <input type="submit"
                               value="<spring:message text="Edit Car"/>"/>
                    </c:if>
                    <c:if test="${empty car.model}">
                        <input type="submit"
                               value="<spring:message text="Add Car"/>"/>
                    </c:if>
                </td>
            </tr>
        </table>
    </form:form>
</div>