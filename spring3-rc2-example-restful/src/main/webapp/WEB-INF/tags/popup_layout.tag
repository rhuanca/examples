<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <title><fmt:message key="application.title"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/base.css"/>
</head>
<body>
	<jsp:doBody/>
</body>
</html>
