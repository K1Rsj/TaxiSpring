<%@ include file="components/addition.jsp" %>
<jsp:include page="components/head.jsp"/>
<div class="alert text-white text-center">
    <div class="alert alert-danger text-center error"><fmt:message
            key="something.went.wrong"/></div>
            <form method="post" action="<c:url value="/home"/>" class="inline">
                <input type="hidden" name="extra_submit_param" value="extra_submit_value">
                <button type="submit" name="submit_param" value="submit_value" class="btn btn-danger">
                    <fmt:message key="home"/>
                </button>
            </form>
</div>