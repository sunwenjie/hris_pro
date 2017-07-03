<%@page import="com.kan.hro.web.renders.EmployeeFullReportRender"%>
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
			<th class="header <%=employeeReportHolder.getCurrentSortClass("bizEmail")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'bizEmail', '<%=employeeReportHolder.getNextSortOrder("bizEmail")%>', 'tableWrapper');">
					<span id="bizEmail"><bean:message bundle="business" key="business.employee.report.bizEmail" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("startWorkDate")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'startWorkDate', '<%=employeeReportHolder.getNextSortOrder("startWorkDate")%>', 'tableWrapper');">
					<span id="startWorkDate"><bean:message bundle="business" key="business.employee.report.first.in.group.date" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.shiyongqijiezhishijian"><bean:message bundle="business" key="business.employee.report.shiyongqijiezhishijian" /></span>&nbsp;&nbsp;
			</th>
			<%=EmployeeFullReportRender.generateEmployeeFullReportPartTHeader( request ) %>
			<th class="header-nosort">
				<span id="dynaColumns.jixiaonianfen"><bean:message bundle="business" key="business.employee.report.performanceYear" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.jixiaodengji"><bean:message bundle="business" key="business.employee.report.performanceRating" /></span>&nbsp;&nbsp;
			</th>
			
			<th class="header-nosort">
				<span id="dynaColumns.AnnualRemuneration"><bean:message bundle="business" key="business.employee.report.AnnualRemuneration" /></span>&nbsp;&nbsp;
			</th>
			<logic:iterate id="item" name="employeeReportHolder" property="object.salarys">
				<th class="header-nosort">
					<bean:write name="item" property="mappingValue" />
				</th>
			</logic:iterate>
		</tr>
	</thead>
	<logic:notEqual name="employeeReportHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="employeeReportVO" name="employeeReportHolder" property="source" indexId="number">
			<bean:define id="defineEmployeeReportVO" name="employeeReportVO" toScope="request"></bean:define>
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
					<td class="left"><bean:write name="employeeReportVO" property="bizEmail" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="startWorkDate" /></td>
					<td>	
						<% 
							final Map< String, Object > shiyongqijiezhishijianMap = ( ( EmployeeReportVO )request.getAttribute( "defineEmployeeReportVO" ) ).getDynaColumns();
							final String shiyongqijiezhishijianStr = KANUtil.formatDate( shiyongqijiezhishijianMap.get( "shiyongqijiezhishijian" ), "yyyy-MM-dd", true );
							out.print( shiyongqijiezhishijianStr == null ? "" : shiyongqijiezhishijianStr ); 
						%>
					</td>
					<%=EmployeeFullReportRender.generateEmployeeFullReportPartTBody( request ) %>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="jixiaonianfen">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="jixiaodengji">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="AnnualRemuneration">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					
					<!-- Employee Salary Info -->
					<logic:iterate id="item" name="employeeReportHolder" property="object.salarys"  indexId="number">
						<bean:define id="hasbase" value="0"></bean:define>
						<logic:iterate id="employeeContractSalary" name="employeeReportVO" property="salarys">
							<logic:equal name="employeeContractSalary" property="mappingId" value="${item.mappingId}">
								<td class="right">
									<%--如果是奖金（提成） --%>
									<logic:equal name="employeeContractSalary" property="mappingId" value="10155">
										<logic:equal name="employeeContractSalary" property="mappingValue" value="0.00">
											Yes
										</logic:equal>
										<logic:notEqual name="employeeContractSalary" property="mappingValue" value="0.00">
											<bean:write name="employeeContractSalary" property="mappingValue" />
										</logic:notEqual>
									</logic:equal>
									<logic:notEqual name="employeeContractSalary" property="mappingId" value="10155">
										<bean:write name="employeeContractSalary" property="mappingValue" />
									</logic:notEqual>
								</td>
								<bean:define id="hasbase" value="1"></bean:define>
							</logic:equal>
						</logic:iterate>
						<logic:equal name="hasbase" value="0">
							<td class="center">
								-
							</td>
						</logic:equal>
					</logic:iterate>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeReportHolder">
		<tfoot>
			<tr class="total">
				<td colspan="60" class="left">
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