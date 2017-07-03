<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
   final PagedListHolder employeeAddSubtractHolder = ( PagedListHolder ) request.getAttribute( "employeeAddSubtractHolder" );
   final boolean IN_HOUSE = request.getAttribute( "role" ).equals( KANConstants.ROLE_IN_HOUSE );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "employeeName" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'employeeName', '<%=employeeAddSubtractHolder.getNextSortOrder( "employeeName" )%>', 'tableWrapper');"><%=IN_HOUSE ? "员工" : "雇员"%>姓名</a></th>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "certificateNumber" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'certificateNumber', '<%=employeeAddSubtractHolder.getNextSortOrder( "certificateNumber" )%>', 'tableWrapper');">身份证号码</a></th>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "contractId" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'contractId', '<%=employeeAddSubtractHolder.getNextSortOrder( "contractId" )%>', 'tableWrapper');"> <%=IN_HOUSE ? "劳动合同" : "派遣协议"%>编号 </a></th>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "startDate" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'startDate', '<%=employeeAddSubtractHolder.getNextSortOrder( "startDate" )%>', 'tableWrapper');">合同开始日期</a></th>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "endDate" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'endDate', '<%=employeeAddSubtractHolder.getNextSortOrder( "endDate" )%>', 'tableWrapper');">合同结束日期</a></th>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "sbName" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'sbName', '<%=employeeAddSubtractHolder.getNextSortOrder( "sbName" )%>', 'tableWrapper');">方案名称</a></th>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "planStartDate" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'planStartDate', '<%=employeeAddSubtractHolder.getNextSortOrder( "planStartDate" )%>', 'tableWrapper');">方案开始日期</a></th>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "planEndDate" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'planEndDate', '<%=employeeAddSubtractHolder.getNextSortOrder( "planEndDate" )%>', 'tableWrapper');">方案结束日期</a></th>
			<th nowrap class="header <%=employeeAddSubtractHolder.getCurrentSortClass( "base" )%>"><a onclick="submitForm('employeeAddSubtract_form', null, null, 'base', '<%=employeeAddSubtractHolder.getNextSortOrder( "base" )%>', 'tableWrapper');">基数</a></th>
		</tr>
	</thead>
	<logic:notEqual name="employeeAddSubtractHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="employeeAddSubtract" name="employeeAddSubtractHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="employeeAddSubtract" property="employeeName" /></td>
					<td class="left"><bean:write name="employeeAddSubtract" property="certificateNumber" /></td>
					<td class="left"><bean:write name="employeeAddSubtract" property="contractId" /></td>
					<td class="left"><bean:write name="employeeAddSubtract" property="startDate" /></td>
					<td class="left"><bean:write name="employeeAddSubtract" property="endDate" /></td>
					<td class="left"><bean:write name="employeeAddSubtract" property="sbName" /></td>
					<td class="left"><bean:write name="employeeAddSubtract" property="planStartDate" /></td>
					<td class="left"><bean:write name="employeeAddSubtract" property="planEndDate" /></td>
					<td class="right"><bean:write name="employeeAddSubtract" property="base" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeAddSubtractHolder">
		<tfoot>
			<tr class="total">
				<td colspan="23" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeAddSubtractHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeAddSubtractHolder" property="indexStart" /> - <bean:write name="employeeAddSubtractHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('employeeAddSubtract_form', null, '<bean:write name="employeeAddSubtractHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('employeeAddSubtract_form', null, '<bean:write name="employeeAddSubtractHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('employeeAddSubtract_form', null, '<bean:write name="employeeAddSubtractHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('employeeAddSubtract_form', null, '<bean:write name="employeeAddSubtractHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeAddSubtractHolder" property="realPage" />/<bean:write name="employeeAddSubtractHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>