<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="current" required="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
    <title><fmt:message key="label.app_tile"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>

    <!-- jcalendar -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/notesMigrator/calendar/calendar-blue.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/notesMigrator/calendar/calendar.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/notesMigrator/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/notesMigrator/calendar/lang/calendar-en.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/base.css"/>

    <!-- prototype -->
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/prototype/prototype.js"></script>--%>
</head>
<body>
	<table border="0" width="100%" height="100%">
		<tr>
            <td>
                <table border="0" width="100%">
                    <tr>
                        <td>
                            <a href="upload.do" style="border:0px;"><img align="middle" style="border:0px;text-align:center;" src="${pageContext.request.contextPath}/public/images/Microcat-LIVE-V02-Plus.png" alt="Microcat Live Plus"></a>
                            <span class="app_title">
                                <fmt:message key="label.app_name"/>
                            </span>
                        </td>
                        <td>
                            <div class="user_name">Welcome, ${sessionScope.userSession.user.fullName}</div>
                            <div class="user_name_info">Dealer Code: ${sessionScope.userSession.user.dealerCode}</div>
                            <div class="user_name_info">Franchise(s):
                            <c:forEach items="${sessionScope.userSession.user.franchises}" var="franchise" varStatus="status">
                                <c:out value="${fn:toUpperCase(franchise)}"/>
                                <c:if test="${!status.last}">,</c:if>
                            </c:forEach>
                            </div>
                            <div class="user_name_info"><a href="${pageContext.request.contextPath}/logout.do">Log out</a></div>
                        </td>
                    </tr>
                </table>
            </td>
		</tr>
		<tr height="100%">
            <td style="vertical-align:top;" >
                <table border="0" width="100%" style="border-collapse:collapse;">
                    <tr>
                        <td style="padding:0;">
                            <div class="${current == 'upload'?'active_tab':'inactive_tab'}">
                                <c:if test="${current == 'upload'}">
                                    <fmt:message key="label.upload_tab"/>
                                </c:if>
                                <c:if test="${current != 'upload'}">
                                    <a href="${pageContext.request.contextPath}/notesMigrator/upload.do"><fmt:message key="label.upload_tab"/></a>
                                </c:if>
                            </div>
                            <c:choose>
                                <c:when test="${fn:contains(applicationScope.config['notes-migrator.rights.admin_users'],sessionScope.userSession.user.username)}">
                                    <div class="${current == 'migrate'?'active_tab':'inactive_tab'}">
                                        <c:if test="${current == 'migrate'}">
                                            <fmt:message key="label.migrate_tab"/>
                                        </c:if>
                                        <c:if test="${current != 'migrate'}">
                                            <a href="${pageContext.request.contextPath}/notesMigrator/migrate.do"><fmt:message key="label.migrate_tab"/></a>
                                        </c:if>
                                    </div>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding:0px;"> <img src="${pageContext.request.contextPath}/public/images/bgSelected.jpg" width="100%" height="6" align="top"/></td>
                    </tr>
                    <tr>
                        <td>
                            <jsp:doBody/>
                        </td>
                    </tr>
                </table>
            </td>
		</tr>
	</table>
</body>
</html>
