<%@ include file="../public/jsp/include.jsp" %>
<tags:popup_layout>
    <div class="title">${requestScope.tableDetail.connectionName}
        - ${requestScope.tableDetail.connectionDescription}
        &gt; ${requestScope.tableDetail.name}
    </div>
    <display:table list="${requestScope.tableDetail.tableColumns}" class="filelist" uid="tableColumn">
        <display:column property="name" titleKey="services_index.connections.table_detail.column_name"/>
        <display:column property="type" titleKey="services_index.connections.table_detail.column_type"/>
    </display:table>
</tags:popup_layout>