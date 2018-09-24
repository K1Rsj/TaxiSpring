<%@ include file="../components/addition.jsp" %>

<header id="header" class="header">
    <nav class="navbar navbar-expand-lg bg-black fixed-top">
        <a href="<c:url value='/user_home'/>"><img src="${pageContext.request.contextPath}/resources/img/logo.png"
                                              alt="Taxi" class="logo"></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse"
                    data-target="#navbarResponsive"
                    aria-controls="navbarResponsive" aria-expanded="false"
                    aria-label="Toggle navigation">
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto mr-auto">
                    <li class="nav-item">
                        <a href="<c:url value='/user_home'/>"><fmt:message key="home"/></a>
                    </li>
                    <li class="nav-item text-center">
                        <a href="<c:url value='/make_order'/>"><fmt:message key="make.order"/></a>
                    </li>
                    <li class="nav-item text-center">
                        <a href="<c:url value='/my_orders'/>"><fmt:message key="my.orders"/></a>
                    </li>
                    <li class="nav-item text-center">
                        <a href="<c:url value='/my_discount'/>"><fmt:message key="my.discount"/></a>
                    </li>
                    <li class="nav-item text-center">
                        <a href="<c:url value='/types'/>"><fmt:message key="types.and.prices"/></a>
                    </li>
                    <li class="nav-item text-center">
                        <a href="<c:url value='j_spring_security_logout'/>"><fmt:message key="log.out"/></a>
                    </li>
                    <li class="nav-item text-center">
                        <form>
                            <label for="language"></label><select class="nav-select"
                                                                  id="language" name="language"
                                                                  onchange="submit()">
                            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>
                                English
                            </option>
                            <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>
                                Українська
                            </option>
                        </select>
                        </form>
                    </li>
                </ul>
            </div>
    </nav>
</header>