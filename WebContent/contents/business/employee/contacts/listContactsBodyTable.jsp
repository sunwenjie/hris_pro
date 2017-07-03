<%@page import="java.util.Map"%>
<%@page import="com.kan.hro.domain.biz.employee.EmployeeReportVO"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder employeeReportHolder = ( PagedListHolder ) request.getAttribute( "employeeReportHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'nameZH', '<%=employeeReportHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">
					<span id="nameZH"><bean:message bundle="business" key="business.employee.report.name.cn" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th id="nameEN" class="header <%=employeeReportHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'nameEN', '<%=employeeReportHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">
					<span id="nameEN"><bean:message bundle="business" key="business.employee.report.name.en" /></span>&nbsp;&nbsp;
				</a>
			</th>
			
			<th class="header <%=employeeReportHolder.getCurrentSortClass("entityNameZH")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'entityNameZH', '<%=employeeReportHolder.getNextSortOrder("entityNameZH")%>', 'tableWrapper');">
					<span id="entityNameZH"><bean:message bundle="business" key="business.employee.report.entity.name.cn" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("entityNameEN")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'entityNameEN', '<%=employeeReportHolder.getNextSortOrder("entityNameEN")%>', 'tableWrapper');">
					<span id="entityNameEN"><bean:message bundle="business" key="business.employee.report.entity.name.en" /></span>&nbsp;&nbsp;
				</a>
			</th>
			
			<th class="header <%=employeeReportHolder.getCurrentSortClass("parentBranchNameEN")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'parentBranchNameEN', '<%=employeeReportHolder.getNextSortOrder("parentBranchNameEN")%>', 'tableWrapper');">
					<span id="parentBranchNameEN"><bean:message bundle="business" key="business.employee.report.parent.branch" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("branchNameZH")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'branchNameZH', '<%=employeeReportHolder.getNextSortOrder("branchNameZH")%>', 'tableWrapper');">
					<span id="branchNameZH"><bean:message bundle="business" key="business.employee.report.dept.cn" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("branchNameEN")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'branchNameEN', '<%=employeeReportHolder.getNextSortOrder("branchNameEN")%>', 'tableWrapper');">
					<span id="branchNameEN"><bean:message bundle="business" key="business.employee.report.dept.en" /></span>&nbsp;&nbsp;
				</a>
			</th>
			
			<th class="header-nosort">
				<span id="dynaColumns.bangongdidian"><bean:message bundle="business" key="business.employee.report.location" /></span>&nbsp;&nbsp;
			</th>
			
			<th class="header-nosort">
				<span id="dynaColumns.neibuchengwei"><bean:message bundle="business" key="business.employee.report.inner.title" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhiweimingchengyingwen"><bean:message bundle="business" key="business.employee.report.job.role" /></span>&nbsp;&nbsp;
			</th>
			
			<th class="header <%=employeeReportHolder.getCurrentSortClass("mobile1")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'mobile1', '<%=employeeReportHolder.getNextSortOrder("mobile1")%>', 'tableWrapper');">
					<span id="mobile1"><bean:message bundle="business" key="business.employee.report.mobile1" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("email1")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'email1', '<%=employeeReportHolder.getNextSortOrder("email1")%>', 'tableWrapper');">
					<span id="email1"><bean:message bundle="business" key="business.employee.email1" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("phone2")%>">
				<a onclick="submitForm('searchContact_form', null, null, 'phone2', '<%=employeeReportHolder.getNextSortOrder("phone2")%>', 'tableWrapper');">
					<span id="phone2"><bean:message bundle="business" key="business.employee.report.phone2" /></span>&nbsp;&nbsp;
				</a>
			</th>
			
		</tr>
	</thead>
	<logic:notEqual name="employeeReportHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="employeeReportVO" name="employeeReportHolder" property="source" indexId="number">
			<bean:define id="defineEmployeeReportVO" name="employeeReportVO" toScope="request"></bean:define>
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="employeeReportVO" property="nameZH" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="nameEN" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="entityNameZH" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="entityNameEN" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="parentBranchNameEN" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="branchNameZH" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="branchNameEN" /></td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="bangongdidian">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="neibuchengwei">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="zhiweimingchengyingwen">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					
					<td class="left"><bean:write name="employeeReportVO" property="mobile1" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="email1" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="phone2" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeReportHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeReportHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeReportHolder" property="indexStart" /> - <bean:write name="employeeReportHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchContact_form', null, '<bean:write name="employeeReportHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchContact_form', null, '<bean:write name="employeeReportHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchContact_form', null, '<bean:write name="employeeReportHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchContact_form', null, '<bean:write name="employeeReportHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeReportHolder" property="realPage" />/<bean:write name="employeeReportHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<script>
	(function($){
		var colspanSize = 64;
		if( $('tbody#mytab tr').find('td').length != 0 ){
			colspanSize = $('tbody#mytab tr').find('td').length;
		}
		$('tr.total td').attr( 'colspan', colspanSize );
		$("#resultTable").fixTable({
			fixColumn: 2,//固定列数
			width:0,//显示宽度
			height:$("#resultTable").outerHeight()-$("#resultTable").find("tfoot").outerHeight()+17//显示高度
		});
	})(jQuery);
</script>