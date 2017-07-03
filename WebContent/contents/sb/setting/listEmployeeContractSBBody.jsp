<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder employeeContractSBHolder = (PagedListHolder) request.getAttribute("employeeContractSBHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="employeeContractSB-information">
		<div class="head">
			<label id="itleLable">
				<logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.employee1.solution.add.sub" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.employee2.solution.add.sub" /></logic:equal>
			</label>
		</div>
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeContractSBAction.do?proc=list_object" styleClass="list_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeContractSBHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeContractSBHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeContractSBHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeContractSBHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户ID</label>
							<html:text property="corpId" styleId="corpId" maxlength="10" styleClass="searchEmployeeContractSB_corpId" />
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
							</label>
							<logic:equal name="role" value="1">
								<html:text property="orderId" maxlength="10" styleClass="searchEmployeeContractSB_orderId" /> 
							</logic:equal>
							<logic:equal name="role" value="2">
								<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchEmployeeContractSB_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
							</logic:equal>
						</li>
		   				<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label> 
							<html:text property="contractId" styleId="contractId" maxlength="10" styleClass="searchEmployeeContractSB_contractId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label> 
							<html:text property="employeeId" maxlength="10" styleClass="searchEmployeeContractSB_employeeSBId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label> 
							<html:text property="employeeNameZH" styleId="employeeNameZH" maxlength="50" styleClass="searchEmployeeContractSB_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label> 
							<html:text property="employeeNameEN" styleId="employeeNameEN" maxlength="50" styleClass="searchEmployeeContractSB_employeeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.solution" /></label> 
							<html:select property="sbSolutionId" styleClass="searchEmployeeContractSB_sbSolutionId">
								<html:optionsCollection property="solutions" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.start.date" /></label> 
							<input name="startDate" class="startDate  Wdate " id="startDate" onfocus="WdatePicker()" value="<bean:write name="employeeContractSBForm" property="startDate" />" type="text" maxlength="10">
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.end.date" /></label> 
							<input name="endDate" class="endDate  Wdate " id="endDate" onfocus="WdatePicker()" value="<bean:write name="employeeContractSBForm" property="endDate" />" type="text" maxlength="10">
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.vendor.name.cn" /></label> 
							<html:text property="vendorNameZH" styleId="vendorNameZH" maxlength="50" styleClass="searchEmployeeContractSB_vendorNameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.vendor.name.en" /></label> 
							<html:text property="vendorNameEN" styleId="vendorNameEN" maxlength="50" styleClass="searchEmployeeContractSB_vendorNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.certificate.number" /></label> 
							<html:text property="certificateNumber" styleId="certificateNumber" maxlength="50" styleClass="searchEmployeeContractSB_certificateNumber" /> 
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.status" /></label> 
							<div style="width: 235px;">
								<logic:iterate id="status" name="employeeContractSBForm" property="statuses" indexId="index">
									<label class="auto">
										<input type="checkbox" name="statusArray" id="status_<bean:write name="status" property="mappingId" />" class="status_<bean:write name="status" property="mappingId" />" value="<bean:write name="status" property="mappingId" />" />
										<bean:write name="status" property="mappingValue" />
									</label>
								</logic:iterate>
							</div>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- employeeContractSB-information -->
	<div class="box noHeader" id="search-results">
		<!-- Include table jsp 包含tabel对应的jsp文件 -->
		<jsp:include page="/contents/sb/setting/table/listEmployeeContractSBTable.jsp" flush="true"/>
	</div>
	<div id="append_info"></div>
	<div id="popupWrapper">
		<jsp:include page="/popup/manageEmployeeContractSB.jsp"></jsp:include>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_Purchase').addClass('selected');
		
		$('#searchDiv').hide();
		
		var status = '<bean:write name="employeeContractSBForm" property="status" />';
		if( status != null && status != ''){
			var statusArray = status.split(',');
			for( var arr in statusArray){
				$('#status_' + statusArray[arr]).attr('checked',true);
			}
		}
	})(jQuery);

	function resetForm() {
		$('.searchEmployeeContractSB_employeeSBId').val('');
		$('.searchEmployeeContractSB_corpId').val('');
		<logic:equal name="role" value="1">
			$('.searchEmployeeContractSB_orderId').val('');
		</logic:equal>	
		<logic:equal name="role" value="2">
			$('.searchEmployeeContractSB_orderId').val('0');
		</logic:equal>	
		$('.searchEmployeeContractSB_employeeSBId').val('');
		$('.searchEmployeeContractSB_employeeNameZH').val('');
		$('.searchEmployeeContractSB_employeeNameEN').val('');
		$('.searchEmployeeContractSB_sbSolutionId').val('0');
		$('#startDate').val('');
		$('#endDate').val('');
		$('.searchEmployeeContractSB_vendorNameZH').val('');
		$('.searchEmployeeContractSB_vendorNameEN').val('');
		$('.searchEmployeeContractSB_contractId').val('');
		$('.searchEmployeeContractSB_certificateNumber').val('');
		$('div input[id^="status_"][type="checkbox"]').attr('checked',false);
	};
</script>