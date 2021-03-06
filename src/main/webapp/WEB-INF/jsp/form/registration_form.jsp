<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<div class="container py-5 " style="padding-top: 12rem!important;">
    <c:if test="${not empty requestScope.informationMessage}">
        <div class="alert alert-info text-center">${requestScope.informationMessage}</div>
    </c:if>
    <div class="row">
        <div class="col-md-12">
            <h2 class="text-center text-white mb-4"><fmt:message key="registration"/></h2>
            <div class="row text-center">
                <div class="col-md-5 ml-auto mr-auto">
                    <div class="card rounded-0">
                        <div class="card-header">
                        </div>
                        <div class="card-body">

                            <form:form method="POST" modelAttribute="user" class="form" action="/registration">

                                <fmt:message key="login.main"/>
                                <spring:bind path="login">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="text" path="login" class="form-control" placeholder="login"
                                                    autofocus="true"/>
                                        <form:errors path="login" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <fmt:message key="password"/>
                                <spring:bind path="password">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="password" path="password" class="form-control"
                                                    placeholder="password"/>
                                        <form:errors path="password" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <fmt:message key="repeat.password"/>
                                <spring:bind path="confirmationPassword">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="password" path="confirmationPassword" class="form-control"
                                                    placeholder="confirm your password"/>
                                        <form:errors path="confirmationPassword" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <fmt:message key="email"/>
                                <spring:bind path="email">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="text" path="email" class="form-control" placeholder="email"/>
                                        <form:errors path="email" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <fmt:message key="first.name"/>
                                <spring:bind path="firstName">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="text" path="firstName" class="form-control"
                                                    placeholder="first name"/>
                                        <form:errors path="firstName" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <fmt:message key="second.name"/>
                                <spring:bind path="secondName">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="text" path="secondName" class="form-control"
                                                    placeholder="second name"/>
                                        <form:errors path="secondName" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <fmt:message key="phone.number"/>
                                <spring:bind path="phoneNumber">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="number" path="phoneNumber" class="form-control"
                                                    placeholder="phone number"/>
                                        <form:errors path="phoneNumber" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <button type="submit"
                                        class="btn btn-success btn-lg float-right" id="btnLogin"><fmt:message
                                        key="create.account"/>
                                </button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>