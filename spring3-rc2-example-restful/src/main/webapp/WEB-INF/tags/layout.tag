<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
    <title><fmt:message key="application.title"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/base.css"/>
</head>
<body>
	<table border="0" width="100%" height="100%">
		<tr>
            <td>
                <table border="0" width="100%">
                    <tr>
                        <td>
                            <span class="app_title">
                                <fmt:message key="application.title"/>
                            </span>
                        </td>
                        <td>
                            <div class="user_name">Welcome!!!</div>
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
                            <div class="active_tab">
                                <fmt:message key="application.tab.list_web_services"/>
                            </div>
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
