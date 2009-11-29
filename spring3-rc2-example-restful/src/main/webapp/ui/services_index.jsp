<%@ include file="../public/jsp/include.jsp" %>
<tags:layout>
    <form id="servicesIndexForm" action="${pageContext.request.contextPath}/ui/services/list_connection.do" method="post">
    <table width="100%">
        <tr>
            <td>
                <fmt:message key="services_index.connections.label"/>

                <select name="connectionName" onchange="document.getElementById('servicesIndexForm').submit()">
                    <option value="">--------------</option>
                    <c:forEach items="${requestScope.connections}" var="connection">
                        <option value="${connection.name}">${connection.name} - ${connection.description}</option>
                    </c:forEach>
                </select>
                <fmt:message key="services_index.connections.please_select_connection"/>
            </td>
        </tr>
        <c:if test="${not empty requestScope.activeConnection}">
        <tr>
            <td>
                <div class="title">
                    <b>${requestScope.activeConnection.name}:${requestScope.activeConnection.description}</b> &gt; <fmt:message key="services_index.connections.connection_details"/></div>
            </td>
        </tr>
        <tr>
            <td>
                <display:table list="${requestScope.connectionItems}" class="filelist" uid="connectionItem">
                    <display:column property="tableName" titleKey="services_index.connections.table_name"/>
                    <display:column titleKey="services_index.connections.ws_url">
                        http://servername:port${connectionItem.wsUrl}
                    </display:column>


                    <display:column titleKey="services_index.connections.operations">
                        <a href="${connectionItem.wsUrl}" target="_blank">
                            <fmt:message key="services_index.connections.view_xml"/>
                        </a>
                        &nbsp;&nbsp;
                        <a href="${pageContext.request.contextPath}/ui/services/table_detail.do?connectionName=${requestScope.activeConnection.name}&tableName=${connectionItem.tableName}" target="_blank">
                            <fmt:message key="services_index.connections.view_columns"/>
                        </a>
                    </display:column>
                </display:table>
            </td>
        </tr>
        </c:if>
    </table>
    </form>
</tags:layout>


