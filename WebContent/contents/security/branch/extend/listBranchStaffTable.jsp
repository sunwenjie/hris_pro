<%@page import="com.kan.base.domain.security.BranchVO"%>
<%@page import="java.util.List"%>
<%@page import="com.kan.base.domain.security.StaffVO"%>
<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html:form  action="branchAction.do?proc=list_special_info_list" styleClass="list_form" method="post" >
	<input type="hidden" name="branchId" id="branchId" value="<bean:write name="branchForm" property="branchId" />" />
	<input type="hidden" name="page" id="page" value="<bean:write name="staffListHolder" property="page" />" />
</html:form>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.job.number" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.name.cn" />
			</th>	 
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.name.en" />
			</th>	 
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.gender" />
			</th>	
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.position" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.position.grade" />
			</th>	
			 <th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.work.place" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.hight.degree" />
			</th>	 
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.graduation.school" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.major" />
			</th>	 
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.graduation.date" />
			</th>	 
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.birthday" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="security" key="security.staff.onboard.date" />
			</th>	 
		</tr>
	</thead>
	<logic:notEqual name="staffListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="staffVO" name="staffListHolder" property="source" indexId="number">
				<logic:notEmpty name="staffVO" property="employeeVO">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<bean:define id="defEmployeeVO" name="staffVO" property="employeeVO"></bean:define>
					<logic:notEmpty name="staffVO" property="positionVO">
						<bean:define id="defPositionVO" name="staffVO" property="positionVO"></bean:define>
					</logic:notEmpty>
					<td class="left">
						<logic:notEmpty name="staffVO" property="staffNo">
							<bean:write name="staffVO" property="staffNo" />
						</logic:notEmpty>
						<logic:empty name="staffVO" property="staffNo">
							<bean:message bundle="security" key="security.staff.job.number.miss" />
						</logic:empty>
					</td>								
					<td class="left"><bean:write name="staffVO" property="nameZH" /></td>								
					<td class="left"><bean:write name="staffVO" property="nameEN" /></td>								
					<td class="left"><bean:write name="staffVO" property="decodeSalutation" /></td>
					<td>
						<logic:notEmpty name="staffVO" property="employeeVO">
							<bean:write name="staffVO" property="employeeVO.decode_tempPositionIds" />
						</logic:notEmpty>
					</td>
					<td>
						<logic:notEmpty name="staffVO" property="employeeVO">
							<bean:write name="staffVO" property="employeeVO.decode_tempPositionGradeIds" />
						</logic:notEmpty>
					</td>
					
					<td class="left"><bean:write name="defEmployeeVO" property="workPlace" /></td>	
					<td class="left"><bean:write name="defEmployeeVO" property="decodeHighestEducation" /></td>
					<td class="left"><bean:write name="defEmployeeVO" property="graduateSchool" /></td>	
					<td class="left"><bean:write name="defEmployeeVO" property="major" /></td>	
					<td class="left"><bean:write name="defEmployeeVO" property="graduationDate" /></td>	
					<td class="left"><bean:write name="defEmployeeVO" property="birthday" /></td>	
					<td class="left"><bean:write name="defEmployeeVO" property="onboardDate" /></td>	
				</tr>
				</logic:notEmpty>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="staffListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="staffListHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="staffListHolder" property="indexStart" /> - <bean:write name="staffListHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="staffListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="staffListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="staffListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="staffListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="staffListHolder" property="realPage" />/<bean:write name="staffListHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>