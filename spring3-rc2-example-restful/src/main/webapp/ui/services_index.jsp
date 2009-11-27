<%@ include file="../public/jsp/include.jsp" %>
<tags:layout>
    <form id="servicesIndexForm" action="${pageContext.request.contextPath}/ui/services/list_connection.do" method="post">
    <table width="100%">
        <tr>
            <td>
                <fmt:message key="services_index.connections.label"/>

                <select name="connectionName" onchange="document.getElementById('servicesIndexForm').submit()">
                    <option value="">--------------</option>
                    <c:forEach items="${requestScope.connectionNames}" var="connectionName">
                        <option value="${connectionName}">${connectionName}</option>
                    </c:forEach>
                </select>
                <fmt:message key="services_index.connections.please_select_connection"/>
            </td>
        </tr>
        <c:if test="${not empty requestScope.connectionName}">
        <tr>
            <td>
                <div class="title"><fmt:message key="services_index.connections.connection_details"/>
                    - ${requestScope.connectionName}</div>
            </td>
        </tr>
        <tr>
            <td>
                <display:table list="${requestScope.connectionItems}" class="filelist" uid="connectionItem">
                    <display:column property="tableName" titleKey="services_index.connections.table_name"/>
                    <display:column property="wsUrl" titleKey="services_index.connections.ws_url"/>
                    <display:column titleKey="services_index.connections.operations">
                        <a href="#" onclick="window.showModalDialog('${connectionItem.wsUrl}', '', 'resizable=yes');">
                            <fmt:message key="services_index.connections.view_xml"/>
                        </a>
                    </display:column>
                </display:table>
            </td>
        </tr>
        </c:if>
    </table>
    </form>
</tags:layout>


