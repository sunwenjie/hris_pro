<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder tablePagedListHolder = (PagedListHolder) request.getAttribute("tablePagedListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
	
			<th style="width: 20%" class="header <%=tablePagedListHolder.getCurrentSortClass("slaveTableId")%>">
				<a onclick="submitForm('tableRelation_form', null, null, 'nameZH', '<%=tablePagedListHolder.getNextSortOrder("slaveTableId")%>', 'tableWrapper');">模块字典名称</a>
			</th>
			
			<th style="width: 7%" class="header <%=tablePagedListHolder.getCurrentSortClass("joinType")%>">
				<a onclick="submitForm('tableRelation_form', null, null, 'tableType', '<%=tablePagedListHolder.getNextSortOrder("joinType")%>', 'tableWrapper');">链接类型</a>
			</th>
			<th style="width: 20%" class="header <%=tablePagedListHolder.getCurrentSortClass("masterColumn")%>">
				<a onclick="submitForm('tableRelation_form', null, null, 'accessAction', '<%=tablePagedListHolder.getNextSortOrder("masterColumn")%>', 'tableWrapper');">主表字段</a>
			</th>
			<th style="width: 20%" class="header <%=tablePagedListHolder.getCurrentSortClass("slaveColumn")%>">
				<a onclick="submitForm('tableRelation_form', null, null, 'accessName', '<%=tablePagedListHolder.getNextSortOrder("slaveColumn")%>', 'tableWrapper');">子表字段</a>
			</th>
	
		</tr>
	</thead>
	<logic:notEqual name="tablePagedListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="tableRelationVO" name="tablePagedListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
				<td class="left">
					<logic:present name="accountId">
						<logic:equal name="accountId" value="1">
							
								<img title="删除" src="images/warning-btn.png" width="12px" height="12px" id="warning_img" name="warning_img"  onclick="removeTableRelation('<bean:write name="tableRelationVO" property="tableRelationId" />','<bean:write name="tableRelationVO" property="masterTableId" />');" />
							
						</logic:equal>
					</logic:present>
					<a onclick="tableRelationModify('<bean:write name="tableRelationVO" property="tableRelationId"/>');"><bean:write name="tableRelationVO" property="decodeSlaveTableName" /></a></td>
					<td class="left"><bean:write name="tableRelationVO" property="joinType" /></td>
					<td class="left"><bean:write name="tableRelationVO" property="masterColumn" /></td>
					<td class="left"><bean:write name="tableRelationVO" property="slaveColumn" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="tablePagedListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="tablePagedListHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="tablePagedListHolder" property="indexStart" /> - <bean:write name="tablePagedListHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('tableRelation_form', null, '<bean:write name="tablePagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('tableRelation_form', null, '<bean:write name="tablePagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('tableRelation_form', null, '<bean:write name="tablePagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('tableRelation_form', null, '<bean:write name="tablePagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="tablePagedListHolder" property="realPage" />/<bean:write name="tablePagedListHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>