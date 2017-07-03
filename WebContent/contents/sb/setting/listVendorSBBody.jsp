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
		<div class="head"><label id="itleLable"><bean:message bundle="sb" key="sb.vendor.setting" /></label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeContractSBAction.do?proc=list_object_vendor" styleClass="list_form">
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
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
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
							<label><bean:message bundle="sb" key="sb.status" /></label>
							<html:select property="status" styleClass="searchEmployeeContractSB_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
							</label>
							<html:select property="contractStatus" styleClass="searchEmployeeContractSB_contractStatus">
								<html:optionsCollection property="contractStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- employeeContractSB-information -->
	<div class="box noHeader" id="search-results">
		<!-- Include table jsp 包含tabel对应的jsp文件 -->
		<jsp:include page="/contents/sb/setting/table/listVendorSBTable.jsp" flush="true"/>
	</div>
	<div id="append_info"></div>
	<div id="popupWrapper">
		<jsp:include page="/popup/manageVendorSB.jsp"></jsp:include>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_VendorSetting').addClass('selected');
		
		$('#searchDiv').hide();
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
		$('.searchEmployeeContractSB_contractId').val('');
		$('.searchEmployeeContractSB_status').val('0');
		$('.searchEmployeeContractSB_contractStatus').val('0');
	};
</script>