<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder employeeInsuranceNoHeaderHolder = (PagedListHolder) request.getAttribute("employeeInsuranceNoHeaderHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header-nosort">
				派送协议ID
			</th>
			<th style="width: 17%" class="header-nosort">
				派送协议名称
			</th>
			<th style="width: 10%" class="header-nosort ">
				雇员ID
			</th>
			<th style="width: 10%" class="header-nosort">
				雇员姓名
			</th>
			<th style="width: 10%" class="header-nosort">
				身份证号
			</th>
			<th style="width: 10%" class="header-nosort">
				医保卡账号
			</th>
			<th style="width: 10%" class="header-nosort">
				社保卡账号
			</th>
			<th style="width: 10%" class="header-nosort">
				公积金卡账号
			</th>
			<th style="width: 10%" class="header-nosort">
				房贴号
			</th>
			<th style="width: 10%" class="header-nosort">
				商保方案
			</th>
			<th style="width: 10%" class="header-nosort">
				商保保单号
			</th>
			
			<th style="width: 10%" class="header-nosort">
				商保方案B
			</th>
			<th style="width: 10%" class="header-nosort">
				商保保单号B
			</th>
			
			<th style="width: 10%" class="header-nosort">
				状态
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeeInsuranceNoHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="employeeInsuranceNoImportHeaderVO" name="employeeInsuranceNoHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="employeeInsuranceNoImportHeaderVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeInsuranceNoImportHeaderVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="employeeInsuranceNoImportHeaderVO" property="cardnoId"/>" />
						</logic:equal>
					</td>
					<td class="left"><bean:write name="employeeInsuranceNoImportHeaderVO" property="contractId" /></td>								
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="contractName"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="employeeId"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="employeeName"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="certificateNumber"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="medicalNumber"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="sbNumber"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="fundNumber"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="hsNumber"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="cbName"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="cbNumber"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="decodeSolutionIdB"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="cbNumberB"/>  </td>
					<td class="left"> <bean:write name="employeeInsuranceNoImportHeaderVO" property="decodeStatus"/>  </td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeInsuranceNoHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="16" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeInsuranceNoHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeInsuranceNoHeaderHolder" property="indexStart" /> - <bean:write name="employeeInsuranceNoHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('searchEmployeeInsuranceNoImportHeader_form', null, '<bean:write name="employeeInsuranceNoHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('searchEmployeeInsuranceNoImportHeader_form', null, '<bean:write name="employeeInsuranceNoHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('searchEmployeeInsuranceNoImportHeader_form', null, '<bean:write name="employeeInsuranceNoHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('searchEmployeeInsuranceNoImportHeader_form', null, '<bean:write name="employeeInsuranceNoHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeInsuranceNoHeaderHolder" property="realPage" />/<bean:write name="employeeInsuranceNoHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	
</script>