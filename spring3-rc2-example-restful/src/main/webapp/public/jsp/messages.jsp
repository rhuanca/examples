<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errors}">
    <br/>

    <c:forEach items="${errors}" var="error">
        <div style="color:black;width:100%;background-color:coral; padding:5px;font-family: Arial, Helvetica, sans-serif;">
            Error: <c:out value="${error}"/><br/>
        </div>
    </c:forEach>

</c:if>

<c:if test="${not empty messages}">
    <br/>

    <c:forEach items="${messages}" var="message">
        <div style="color:black;width:100%;background-color:lightblue;padding:5px;font-family: Arial, Helvetica, sans-serif;">
            <c:out value="${message}"/><br/>
        </div>
    </c:forEach>

</c:if>
