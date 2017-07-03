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
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'nameZH', '<%=employeeReportHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">
					<span id="nameZH"><bean:message bundle="business" key="business.employee.report.name.cn" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th id="nameEN" class="header <%=employeeReportHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'nameEN', '<%=employeeReportHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">
					<span id="nameEN"><bean:message bundle="business" key="business.employee.report.name.en" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.jiancheng"><bean:message bundle="business" key="business.employee.report.nick.name" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("entityNameZH")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'entityNameZH', '<%=employeeReportHolder.getNextSortOrder("entityNameZH")%>', 'tableWrapper');">
					<span id="entityNameZH"><bean:message bundle="business" key="business.employee.report.entity.name.cn" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("entityNameEN")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'entityNameEN', '<%=employeeReportHolder.getNextSortOrder("entityNameEN")%>', 'tableWrapper');">
					<span id="entityNameEN"><bean:message bundle="business" key="business.employee.report.entity.name.en" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("entityTitle")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'entityTitle', '<%=employeeReportHolder.getNextSortOrder("entityTitle")%>', 'tableWrapper');">
					<span id="entityTitle"><bean:message bundle="business" key="business.employee.report.entity.title" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("parentBranchNameEN")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'parentBranchNameEN', '<%=employeeReportHolder.getNextSortOrder("parentBranchNameEN")%>', 'tableWrapper');">
					<span id="parentBranchNameEN"><bean:message bundle="business" key="business.employee.report.parent.branch" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("branchNameZH")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'branchNameZH', '<%=employeeReportHolder.getNextSortOrder("branchNameZH")%>', 'tableWrapper');">
					<span id="branchNameZH"><bean:message bundle="business" key="business.employee.report.dept.cn" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("branchNameEN")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'branchNameEN', '<%=employeeReportHolder.getNextSortOrder("branchNameEN")%>', 'tableWrapper');">
					<span id="branchNameEN"><bean:message bundle="business" key="business.employee.report.dept.en" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("settlementBranch")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'settlementBranch', '<%=employeeReportHolder.getNextSortOrder("settlementBranch")%>', 'tableWrapper');">
					<span id="decodeSettlementBranch"><bean:message bundle="business" key="business.employee.report.cost.center" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("businessTypeId")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'businessTypeId', '<%=employeeReportHolder.getNextSortOrder("businessTypeId")%>', 'tableWrapper');">
					<span id="decodeBusinessType"><bean:message bundle="business" key="business.employee.report.business.type" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.bangongdidian"><bean:message bundle="business" key="business.employee.report.location" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.guyuanleixinger"><bean:message bundle="business" key="business.employee.report.employee.type" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.jobrole"><bean:message bundle="business" key="business.employee.report.working.title" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("_tempPositionGradeIds")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, '_tempPositionGradeIds', '<%=employeeReportHolder.getNextSortOrder("_tempPositionGradeIds")%>', 'tableWrapper');">
					<span id="decode_tempPositionGradeIds"><bean:message bundle="business" key="business.employee.report.job.grade" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.neibuchengwei"><bean:message bundle="business" key="business.employee.report.inner.title" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhiweimingchengyingwen"><bean:message bundle="business" key="business.employee.report.job.role" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.yewuhuibaoxianjingli"><bean:message bundle="business" key="business.employee.report.biz.report.manager" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("_tempParentPositionOwners")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, '_tempParentPositionOwners', '<%=employeeReportHolder.getNextSortOrder("_tempParentPositionOwners")%>', 'tableWrapper');">
					<span id="decode_tempParentPositionOwners"><bean:message bundle="business" key="business.employee.report.line.manager" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("startWorkDate")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'startWorkDate', '<%=employeeReportHolder.getNextSortOrder("startWorkDate")%>', 'tableWrapper');">
					<span id="startWorkDate"><bean:message bundle="business" key="business.employee.report.first.in.group.date" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("contractStartDate")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'contractStartDate', '<%=employeeReportHolder.getNextSortOrder("contractStartDate")%>', 'tableWrapper');">
					<span id="contractStartDate"><bean:message bundle="business" key="business.employee.report.contract.hire.date" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhaopinqudao"><bean:message bundle="business" key="business.employee.report.recruitType" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhaopinyuanyin"><bean:message bundle="business" key="business.employee.report.recruitReason" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("contractStatus")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'contractStatus', '<%=employeeReportHolder.getNextSortOrder("contractStatus")%>', 'tableWrapper');">
					<span id="decodeContractStatus"><bean:message bundle="business" key="business.employee.report.contract.status" /></span>&nbsp;&nbsp;
				</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeeReportHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="employeeReportVO" name="employeeReportHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="employeeReportVO" property="nameZH" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="nameEN" /></td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="jiancheng">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="entityNameZH" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="entityNameEN" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="entityTitle" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="parentBranchNameEN" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="branchNameZH" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="branchNameEN" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeSettlementBranch" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeBusinessType" /></td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="bangongdidian">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="guyuanleixinger">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="jobrole">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="decode_tempPositionGradeIds" /></td>
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
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="yewuhuibaoxianjingli">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="decode_tempParentPositionOwners" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="startWorkDate" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="contractStartDate" /></td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="zhaopinqudao">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="zhaopinyuanyin">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeContractStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeReportHolder">
		<tfoot>
			<tr class="total">
				<td colspan="40" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeReportHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeReportHolder" property="indexStart" /> - <bean:write name="employeeReportHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchEmployeeReport_form', null, '<bean:write name="employeeReportHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeReport_form', null, '<bean:write name="employeeReportHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeReport_form', null, '<bean:write name="employeeReportHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeReport_form', null, '<bean:write name="employeeReportHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeReportHolder" property="realPage" />/<bean:write name="employeeReportHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<script>
	(function($){
		$("#resultTable").fixTable({
			fixColumn: 2,//固定列数
			width:0,//显示宽度
			height:$("#resultTable").outerHeight()-$("#resultTable").find("tfoot").outerHeight()+17//显示高度
		});
	})(jQuery);
</script>