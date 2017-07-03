<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:notEmpty name="reportSearchDetailPagedListHolder">
<%
	final PagedListHolder reportSearchDetailPagedListHolder = ( PagedListHolder )request.getAttribute( "reportSearchDetailPagedListHolder" );
%>

<table class="table hover" id="resultTable3">
	<thead>
		<tr>
			<th class=""></th>
			<th style="width: 15%" class="header <%=reportSearchDetailPagedListHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listReportSearchDetail_form', null, null, 'nameZH', '<%=reportSearchDetailPagedListHolder.getNextSortOrder("nameZH")%>', 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.manager.detail.name.cn" /></a>
			</th>
			<th style="width: 15%" class="header <%=reportSearchDetailPagedListHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listReportSearchDetail_form', null, null, 'nameEN', '<%=reportSearchDetailPagedListHolder.getNextSortOrder("nameEN")%>', 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.manager.detail.name.en" /></a>
			</th>
			<th style="width: 15%" class="header <%=reportSearchDetailPagedListHolder.getCurrentSortClass("columnId")%>">
				<a onclick="submitForm('listReportSearchDetail_form', null, null, 'columnId', '<%=reportSearchDetailPagedListHolder.getNextSortOrder("columnId")%>', 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.manager.detail.original.column.name" /></a>
			</th>
			<th style="width: 10%" class="header <%=reportSearchDetailPagedListHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listReportSearchDetail_form', null, null, 'columnIndex', '<%=reportSearchDetailPagedListHolder.getNextSortOrder("columnIndex")%>', 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.column.column.index" /></a>
			</th>
			<th style="width: 10%" class="header <%=reportSearchDetailPagedListHolder.getCurrentSortClass("combineType")%>">
				<a onclick="submitForm('listReportSearchDetail_form', null, null, 'combineType', '<%=reportSearchDetailPagedListHolder.getNextSortOrder("combineType")%>', 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.report.detail.logic.condition" /></a>
			</th>
			<th style="width: 10%" class="header <%=reportSearchDetailPagedListHolder.getCurrentSortClass("condition")%>">
				<a onclick="submitForm('listReportSearchDetail_form', null, null, 'condition', '<%=reportSearchDetailPagedListHolder.getNextSortOrder("condition")%>', 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="define" key="define.report.detail.select.condition" /></a>
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="define" key="define.report.detail.condition" />
			</th>
			<th style="width: 10%" class="header <%=reportSearchDetailPagedListHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listReportSearchDetail_form', null, null, 'status', '<%=reportSearchDetailPagedListHolder.getNextSortOrder("status")%>', 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
		</thead>
			<logic:notEmpty name="reportSearchDetailPagedListHolder">
				<logic:notEqual name="reportSearchDetailPagedListHolder" property="holderSize" value="0">
					<tbody>
						<logic:iterate id="reportSearchDetailVO" name="reportSearchDetailPagedListHolder" property="source" indexId="number">
							<tr class='<%=number % 2 == 1 ? "odd" : "even"%>' id="tr_<bean:write name="reportSearchDetailVO" property="columnId" />">
								<td>
									<img title="<bean:message bundle="public" key="button.delete" />" src="images/warning-btn.png" width="12px" height="12px" id="warning_img" name="warning_img"  onclick="removeSearchCondition('<bean:write name="reportSearchDetailVO" property="reportSearchDetailId" />','<bean:write name="reportSearchDetailVO" property="nameZH" />');" />
								</td>
								<td class="left"><a onclick="reportSearchDetailModify('<bean:write name="reportSearchDetailVO" property="encodedId" />','<bean:write name="reportSearchDetailVO" property="columnId" />');"><bean:write name="reportSearchDetailVO" property="nameZH" /></a></td>
								<td class="left"><bean:write name="reportSearchDetailVO" property="nameEN" /></td>
								<td class="left"><bean:write name="reportSearchDetailVO" property="decodeColumn" /></td>
								<td class="left"><bean:write name="reportSearchDetailVO" property="columnIndex" /></td>
								<td class="left"><bean:write name="reportSearchDetailVO" property="decodeCombineType" /></td>
								<td class="left"><bean:write name="reportSearchDetailVO" property="decodeCondition" /></td>
								<td class="left">
									<logic:notEqual name="reportSearchDetailVO" property="condition" value="8">
										<bean:write name="reportSearchDetailVO" property="content" />
									</logic:notEqual>
									<logic:equal name="reportSearchDetailVO" property="condition" value="8">
										<bean:write name="reportSearchDetailVO" property="range" />
									</logic:equal>
								</td>
								<td class="left"><bean:write name="reportSearchDetailVO" property="decodeStatus" /></td>
							</tr>
						</logic:iterate>
					</tbody>
				</logic:notEqual>
			</logic:notEmpty>
		<logic:present name="reportSearchDetailPagedListHolder">
			<tfoot>
				<tr class="total">
					<td colspan="10" class="left">
						<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="reportSearchDetailPagedListHolder" property="holderSize" /> </label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="reportSearchDetailPagedListHolder" property="indexStart" /> - <bean:write name="reportSearchDetailPagedListHolder" property="indexEnd" /></label> 
						<label>&nbsp;&nbsp;<a  onclick="submitForm('listReportSearchDetail_form', null, '<bean:write name="reportSearchDetailPagedListHolder" property="firstPage" />', null, null, 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="public" key="page.first" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listReportSearchDetail_form', null, '<bean:write name="reportSearchDetailPagedListHolder" property="previousPage" />', null, null, 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="public" key="page.previous" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listReportSearchDetail_form', null, '<bean:write name="reportSearchDetailPagedListHolder" property="nextPage" />', null, null, 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="public" key="page.next" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listReportSearchDetail_form', null, '<bean:write name="reportSearchDetailPagedListHolder" property="lastPage" />', null, null, 'contentStep3', null, '$(\'#tabMenu3\').trigger(\'click\')', false);"><bean:message bundle="public" key="page.last" /></a></label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="reportSearchDetailPagedListHolder" property="realPage" />/<bean:write name="reportSearchDetailPagedListHolder" 	property="pageCount" /></label>&nbsp;
					</td>
				</tr>
			</tfoot>
		</logic:present>
</table>
</logic:notEmpty>