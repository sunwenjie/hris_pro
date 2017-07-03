<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder reportDetailPagedListHolder = ( PagedListHolder )request.getAttribute( "reportDetailPagedListHolder" );
%>

<table class="table hover" id="resultTable2">
	<thead>
		<tr>
			<th class=""></th>
			<th style="width: 20%" class="header <%=reportDetailPagedListHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listReportDetail_form', null, null, 'nameZH', '<%=reportDetailPagedListHolder.getNextSortOrder("nameZH")%>', 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.manager.detail.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=reportDetailPagedListHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listReportDetail_form', null, null, 'nameEN', '<%=reportDetailPagedListHolder.getNextSortOrder("nameEN")%>', 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.manager.detail.name.en" /></a>
			</th>
			<th style="width: 20%" class="header <%=reportDetailPagedListHolder.getCurrentSortClass("columnId")%>">
				<a onclick="submitForm('listReportDetail_form', null, null, 'columnId', '<%=reportDetailPagedListHolder.getNextSortOrder("columnId")%>', 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.manager.detail.original.column.name" /></a>
			</th>
			<th style="width: 8%" class="header <%=reportDetailPagedListHolder.getCurrentSortClass("columnWidth")%>">
				<a onclick="submitForm('listReportDetail_form', null, null, 'columnWidth', '<%=reportDetailPagedListHolder.getNextSortOrder("columnWidth")%>', 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.list.detail.column.width" /></a>
			</th>
			<th style="width: 8%" class="header <%=reportDetailPagedListHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listReportDetail_form', null, null, 'columnIndex', '<%=reportDetailPagedListHolder.getNextSortOrder("columnIndex")%>', 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.column.column.index" /></a>
			</th>
			<th style="width: 8%" class="header <%=reportDetailPagedListHolder.getCurrentSortClass("fontSize")%>">
				<a onclick="submitForm('listReportDetail_form', null, null, 'fontSize', '<%=reportDetailPagedListHolder.getNextSortOrder("fontSize")%>', 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.search.detail.font.size" /></a>
			</th>
			<th style="width: 8%" class="header <%=reportDetailPagedListHolder.getCurrentSortClass("isDecoded")%>">
				<a onclick="submitForm('listReportDetail_form', null, null, 'isDecoded', '<%=reportDetailPagedListHolder.getNextSortOrder("isDecoded")%>', 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.list.detail.is.decode" /></a>
			</th>
			<th style="width: 8%" class="header <%=reportDetailPagedListHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listReportDetail_form', null, null, 'status', '<%=reportDetailPagedListHolder.getNextSortOrder("status")%>', 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="public" key="public.status" /></a>
			</th>				
		</tr>
		</thead>
			<logic:notEmpty name="reportDetailPagedListHolder">
				<logic:notEqual name="reportDetailPagedListHolder" property="holderSize" value="0">
					<tbody>
						<logic:iterate id="reportDetailVO" name="reportDetailPagedListHolder" property="source" indexId="number">
							<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
								<td>
									<img title="<bean:message bundle="public" key="button.delete" />" src="images/warning-btn.png" width="12px" height="12px" id="warning_img" name="warning_img"  onclick="removeColumn('<bean:write name="reportDetailVO" property="reportDetailId" />','<bean:write name="reportDetailVO" property="nameZH" />');" />
								</td>
								<td class="left"><a onclick="reportDetailModify('<bean:write name="reportDetailVO" property="encodedId" />','<bean:write name="reportDetailVO" property="columnId" />')"><bean:write name="reportDetailVO" property="nameZH" /></a></td>
								<td class="left"><bean:write name="reportDetailVO" property="nameEN" /></td>
								<td class="left"><bean:write name="reportDetailVO" property="decodeColumn" /></td>
								<td class="left"><bean:write name="reportDetailVO" property="columnWidth" /></td>
								<td class="left"><bean:write name="reportDetailVO" property="columnIndex" /></td>
								<td class="left"><bean:write name="reportDetailVO" property="fontSize" /></td>
								<td class="left"><bean:write name="reportDetailVO" property="decodeIsDecoded" /></td>
								<td class="left"><bean:write name="reportDetailVO" property="decodeStatus" /></td>
							</tr>
						</logic:iterate>
					</tbody>
				</logic:notEqual>
			</logic:notEmpty>
		<logic:present name="reportDetailPagedListHolder">
			<tfoot>
				<tr class="total">
					<td colspan="10" class="left">
						<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="reportDetailPagedListHolder" property="holderSize" /> </label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="reportDetailPagedListHolder" property="indexStart" /> - <bean:write name="reportDetailPagedListHolder" property="indexEnd" /></label> 
						<label>&nbsp;&nbsp;<a  onclick="submitForm('listReportDetail_form', null, '<bean:write name="reportDetailPagedListHolder" property="firstPage" />', null, null, 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="public" key="page.first" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listReportDetail_form', null, '<bean:write name="reportDetailPagedListHolder" property="previousPage" />', null, null, 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="public" key="page.previous" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listReportDetail_form', null, '<bean:write name="reportDetailPagedListHolder" property="nextPage" />', null, null, 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="public" key="page.next" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listReportDetail_form', null, '<bean:write name="reportDetailPagedListHolder" property="lastPage" />', null, null, 'contentStep2', null, '$(\'#tabMenu2\').trigger(\'click\')', false);"><bean:message bundle="public" key="page.last" /></a></label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="reportDetailPagedListHolder" property="realPage" />/<bean:write name="reportDetailPagedListHolder" 	property="pageCount" /></label>&nbsp;
					</td>
				</tr>
			</tfoot>
		</logic:present>
</table>