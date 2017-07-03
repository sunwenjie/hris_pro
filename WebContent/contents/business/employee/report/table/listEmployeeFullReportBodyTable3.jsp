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
			<th class="header <%=employeeReportHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'employStatus', '<%=employeeReportHolder.getNextSortOrder("employStatus")%>', 'tableWrapper');">
					<span id="decodeEmployStatus"><bean:message bundle="business" key="business.employee.report.employ.status" /></span>&nbsp;&nbsp;
				</a>
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
				<span id="dynaColumns.jobrole"><bean:message bundle="business" key="business.employee.report.working.title" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.guyuanleixinger"><bean:message bundle="business" key="business.employee.report.employee.type" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.neibuchengwei"><bean:message bundle="business" key="business.employee.report.inner.title" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhiweimingchengyingwen"><bean:message bundle="business" key="business.employee.report.job.role" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("_tempPositionGradeIds")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, '_tempPositionGradeIds', '<%=employeeReportHolder.getNextSortOrder("_tempPositionGradeIds")%>', 'tableWrapper');">
					<span id="decode_tempPositionGradeIds"><bean:message bundle="business" key="business.employee.report.job.grade" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.yewuhuibaoxianjingli"><bean:message bundle="business" key="business.employee.report.biz.report.manager" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("_tempParentPositionOwners")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, '_tempParentPositionOwners', '<%=employeeReportHolder.getNextSortOrder("_tempParentPositionOwners")%>', 'tableWrapper');">
					<span id="decode_tempParentPositionOwners"><bean:message bundle="business" key="business.employee.report.line.manager" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.minzu"><bean:message bundle="business" key="business.employee.report.nation" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("nationNality")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'nationNality', '<%=employeeReportHolder.getNextSortOrder("nationNality")%>', 'tableWrapper');">
					<span id="decodeNationNality"><bean:message bundle="business" key="business.employee.report.nationNality" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("salutation")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'salutation', '<%=employeeReportHolder.getNextSortOrder("salutation")%>', 'tableWrapper');">
					<span id="decodeSalutation"><bean:message bundle="business" key="business.employee.report.salutation" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("certificateType")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'certificateType', '<%=employeeReportHolder.getNextSortOrder("certificateType")%>', 'tableWrapper');">
					<span id="decodeCertificateType"><bean:message bundle="business" key="business.employee.report.certificateType" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("certificateNumber")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'certificateNumber', '<%=employeeReportHolder.getNextSortOrder("certificateNumber")%>', 'tableWrapper');">
					<span id="certificateNumber"><bean:message bundle="business" key="business.employee.report.certificateNumber" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("certificateEndDate")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'certificateEndDate', '<%=employeeReportHolder.getNextSortOrder("certificateEndDate")%>', 'tableWrapper');">
					<span id="certificateEndDate"><bean:message bundle="business" key="business.employee.report.certificateEndDate" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("birthday")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'birthday', '<%=employeeReportHolder.getNextSortOrder("birthday")%>', 'tableWrapper');">
					<span id="birthday"><bean:message bundle="business" key="business.employee.report.birthday" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("maritalStatus")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'maritalStatus', '<%=employeeReportHolder.getNextSortOrder("maritalStatus")%>', 'tableWrapper');">
					<span id="decodeMaritalStatus"><bean:message bundle="business" key="business.employee.report.maritalStatus" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("highestEducation")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'highestEducation', '<%=employeeReportHolder.getNextSortOrder("highestEducation")%>', 'tableWrapper');">
					<span id="decodeHighestEducation"><bean:message bundle="business" key="business.employee.report.highestEducation" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="schoolNames"><bean:message bundle="business" key="business.employee.report.schoolNames" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="majors"><bean:message bundle="business" key="business.employee.report.majors" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="decodeGraduateDates"><bean:message bundle="business" key="business.employee.report.graduateDates" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("personalAddress")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'personalAddress', '<%=employeeReportHolder.getNextSortOrder("personalAddress")%>', 'tableWrapper');">
					<span id="personalAddress"><bean:message bundle="business" key="business.employee.report.personalAddress" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("residencyType")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'residencyType', '<%=employeeReportHolder.getNextSortOrder("residencyType")%>', 'tableWrapper');">
					<span id="decodeResidencyType"><bean:message bundle="business" key="business.employee.report.residencyType" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.hukouxingzhier"><bean:message bundle="business" key="business.employee.report.residencyType2" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("residencyAddress")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'residencyType', '<%=employeeReportHolder.getNextSortOrder("residencyAddress")%>', 'tableWrapper');">
					<span id="residencyAddress"><bean:message bundle="business" key="business.employee.report.residencyAddress" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("mobile1")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'mobile1', '<%=employeeReportHolder.getNextSortOrder("mobile1")%>', 'tableWrapper');">
					<span id="mobile1"><bean:message bundle="business" key="business.employee.report.mobile1" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("phone1")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'phone1', '<%=employeeReportHolder.getNextSortOrder("phone1")%>', 'tableWrapper');">
					<span id="phone1"><bean:message bundle="business" key="business.employee.report.phone1" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="emergencyNames"><bean:message bundle="business" key="business.employee.report.emergencyNames" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="decodeRelationshipIds"><bean:message bundle="business" key="business.employee.report.relationshipIds" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="decodeContactType"><bean:message bundle="business" key="business.employee.report.phones" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("bizEmail")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'bizEmail', '<%=employeeReportHolder.getNextSortOrder("bizEmail")%>', 'tableWrapper');">
					<span id="bizEmail"><bean:message bundle="business" key="business.employee.report.bizEmail" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("personalEmail")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'personalEmail', '<%=employeeReportHolder.getNextSortOrder("personalEmail")%>', 'tableWrapper');">
					<span id="personalEmail"><bean:message bundle="business" key="business.employee.report.personalEmail" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("bankId")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'bankId', '<%=employeeReportHolder.getNextSortOrder("bankId")%>', 'tableWrapper');">
					<span id="decodeBank"><bean:message bundle="business" key="business.employee.report.bankId" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("bankAccount")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'bankAccount', '<%=employeeReportHolder.getNextSortOrder("bankAccount")%>', 'tableWrapper');">
					<span id="bankAccount"><bean:message bundle="business" key="business.employee.report.bankAccount" /></span>&nbsp;&nbsp;
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
				<span id="dynaColumns.shiyongqijiezhishijian"><bean:message bundle="business" key="business.employee.report.shiyongqijiezhishijian" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="probationEndDate"><bean:message bundle="business" key="business.employee.report.probationEndDate" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhaopinqudao"><bean:message bundle="business" key="business.employee.report.recruitType" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhaopinqudaobeizhu"><bean:message bundle="business" key="business.employee.report.recruitType.remark" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhaopinyuanyin"><bean:message bundle="business" key="business.employee.report.recruitReason" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zhaopinyuanyinbeizhu"><bean:message bundle="business" key="business.employee.report.recruitReason.remark" /></span>&nbsp;&nbsp;
			</th>
			<%=EmployeeFullReportRender.generateEmployeeFullReportPartTHeader( request ) %>
			<th class="header-nosort">
				<span id="dynaColumns.jixiaonianfen"><bean:message bundle="business" key="business.employee.report.performanceYear" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.jixiaodengji"><bean:message bundle="business" key="business.employee.report.performanceRating" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("residencyCityId")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'residencyCityId', '<%=employeeReportHolder.getNextSortOrder("residencyCityId")%>', 'tableWrapper');">
					<span id="decodeResidencyCityId"><bean:message bundle="business" key="business.employee.report.residencyCityId" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.dushenzinvzhenglingquri"><bean:message bundle="business" key="business.employee.report.onlyChildCertificateStartDate" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.jiehunzhenglingquri"><bean:message bundle="business" key="business.employee.report.marriageCertificateStartDate" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zuijingongzuogongsi"><bean:message bundle="business" key="business.employee.report.recentlyWorkCompany" /></span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort">
				<span id="dynaColumns.zuijingongzuozhiwei"><bean:message bundle="business" key="business.employee.report.recentlyWorkPosition" /></span>&nbsp;&nbsp;
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("resignDate")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'resignDate', '<%=employeeReportHolder.getNextSortOrder("resignDate")%>', 'tableWrapper');">
					<span id="resignDate"><bean:message bundle="business" key="business.employee.report.resign.date" /></span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=employeeReportHolder.getCurrentSortClass("leaveReasons")%>">
				<a onclick="submitForm('searchEmployeeReport_form', null, null, 'leaveReasons', '<%=employeeReportHolder.getNextSortOrder("leaveReasons")%>', 'tableWrapper');">
					<span id="leaveReasons"><bean:message bundle="business" key="business.employee.report.resign.reason" /></span>&nbsp;&nbsp;
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
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="jiancheng">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeEmployStatus" /></td>
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
							<logic:equal name="dynaColumn" property="key" value="jobrole">
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
					<td class="left"><bean:write name="employeeReportVO" property="decode_tempPositionGradeIds" /></td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="yewuhuibaoxianjingli">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="decode_tempParentPositionOwners" /></td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="minzu">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeNationNality" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeSalutation" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeCertificateType" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="certificateNumber" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="certificateEndDate" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="birthday" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeMaritalStatus" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeHighestEducation" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="schoolNames" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="majors" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeGraduateDates" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="personalAddress" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeResidencyType" /></td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="hukouxingzhier">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="residencyAddress" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="mobile1" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="phone1" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="emergencyNames" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeRelationshipIds" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeContactType" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="bizEmail" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="personalEmail" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeBank" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="bankAccount" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="startWorkDate" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="contractStartDate" /></td>
					<td>	
						<% 
							final Map< String, Object > shiyongqijiezhishijianMap = ( ( EmployeeReportVO )request.getAttribute( "defineEmployeeReportVO" ) ).getDynaColumns();
							final String shiyongqijiezhishijianStr = KANUtil.formatDate( shiyongqijiezhishijianMap.get( "shiyongqijiezhishijian" ), "yyyy-MM-dd", true );
							out.print( shiyongqijiezhishijianStr == null ? "" : shiyongqijiezhishijianStr ); 
						%>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="probationEndDate" /></td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="zhaopinqudao">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="zhaopinqudaobeizhu">
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
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="zhaopinyuanyinbeizhu">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
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
					<td class="left"><bean:write name="employeeReportVO" property="decodeResidencyCityId" /></td>
					<td class="left">
						<% 
							final Map< String, Object > dushenzinvzhenglingquriMap = ( ( EmployeeReportVO )request.getAttribute( "defineEmployeeReportVO" ) ).getDynaColumns();
							final String dushenzinvzhenglingquriStr = KANUtil.formatDate( dushenzinvzhenglingquriMap.get( "dushenzinvzhenglingquri" ), "yyyy-MM-dd", true );
							out.print( dushenzinvzhenglingquriStr == null ? "" : dushenzinvzhenglingquriStr ); 
						%>
					</td>
					<td class="left">
						<% 
							final Map< String, Object > jiehunzhenglingquriMap = ( ( EmployeeReportVO )request.getAttribute( "defineEmployeeReportVO" ) ).getDynaColumns();
							final String jiehunzhenglingquriStr = KANUtil.formatDate( jiehunzhenglingquriMap.get( "jiehunzhenglingquri" ), "yyyy-MM-dd", true );
							out.print( jiehunzhenglingquriStr == null ? "" : jiehunzhenglingquriStr ); 
						%>
					</td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="zuijingongzuogongsi">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left">
						<logic:iterate id="dynaColumn" name="employeeReportVO" property="dynaColumns">
							<logic:equal name="dynaColumn" property="key" value="zuijingongzuozhiwei">
								<bean:write name="dynaColumn" property="value" />
							</logic:equal>
						</logic:iterate>
					</td>
					<td class="left"><bean:write name="employeeReportVO" property="resignDate" /></td>
					<td class="left"><bean:write name="employeeReportVO" property="decodeLeaveReasons" /></td>
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