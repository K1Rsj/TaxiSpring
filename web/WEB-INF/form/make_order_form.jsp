<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../components/addition.jsp" %>
<jsp:include page="../components/head.jsp"/>

<div class="container py-5 " style="padding-top: 12rem!important;">
    <c:if test="${not empty requestScope.orderInformationMessage}">
        <div class="alert alert-info text-center">${requestScope.orderInformationMessage}</div>
    </c:if>
    <div class="row">
        <div class="col-md-12">
            <h2 class="text-center text-white mb-4"><fmt:message key="make.your.order"/></h2>
            <div class="row text-center">
                <div class="col-md-5 ml-auto mr-auto">
                    <div class="card rounded-0">
                        <div class="card-header">
                        </div>
                        <div class="card-body">
                            <%--<form class="form" method="post" action="<c:url value="/make_order"/>">--%>
                            <%--<div class="form-group">--%>
                            <%--<label for="departure"><fmt:message key="departure.street"/></label>--%>
                            <%--<input type="text"--%>
                            <%--class="form-control form-control-lg rounded-0"--%>
                            <%--name="departure" id="departure">--%>
                            <%--</div>--%>

                            <%--<div class="form-group">--%>
                            <%--<label for="destination"><fmt:message--%>
                            <%--key="destination.street"/></label>--%>
                            <%--<input type="text"--%>
                            <%--class="form-control form-control-lg rounded-0"--%>
                            <%--name="destination" id="destination">--%>
                            <%--</div>--%>

                            <%--<div class="form-group">--%>
                            <%--<label for="type"><fmt:message key="car.type"/></label>--%>
                            <%--<select class="form-control form-control-lg rounded-0"--%>
                            <%--name="type" id="type">--%>
                            <%--<option selected value="standard">Standard</option>--%>
                            <%--<option value="comfort">Comfort</option>--%>
                            <%--<option value="minibus">Minibus</option>--%>
                            <%--<option value="premium">Premium</option>--%>
                            <%--</select>--%>
                            <%--</div>--%>
                            <%--<button type="submit" class="btn btn-success btn-lg float-right"--%>
                            <%--id="btnLogin"><fmt:message key="confirm"/></button>--%>
                            <%--</form>--%>
                            <form:form method="POST" modelAttribute="order" class="form" action="/make_order">

                                <fmt:message key="departure.street"/>
                                <spring:bind path="departureStreet">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="text" path="departureStreet" class="form-control"
                                                    placeholder="departure street"/>
                                        <form:errors path="departureStreet" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <fmt:message key="destination.street"/>
                                <spring:bind path="destinationStreet">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:input type="text" path="destinationStreet" class="form-control"
                                                    placeholder="destination street"/>
                                        <form:errors path="destinationStreet" cssClass="errorForm"/>
                                    </div>
                                </spring:bind>

                                <label for="type"><fmt:message key="car.type"/></label>
                                <select class="form-control form-control-lg rounded-0" name="type" id="type">
                                    <option selected value="standard">Standard</option>
                                    <option value="comfort">Comfort</option>
                                    <option value="minibus">Minibus</option>
                                    <option value="premium">Premium</option>
                                </select>

                                <button type="submit" class="btn btn-success btn-lg float-right" id="btnLogin">
                                    <fmt:message key="confirm"/>
                                </button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>