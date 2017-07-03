<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder employeeContractCBHolder = (PagedListHolder) request.getAttribute("employeeContractCBHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="employeeContractCB-information">
		<div class="head">
			<label id="itleLable">
				<logic:equal name="role" value="1"><bean:message bundle="cb" key="cb.employee1.buy.back" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="cb" key="cb.employee2.buy.back" /></logic:equal>
			</label>
		</div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeContractCBAction.do?proc=list_object" styleClass="list_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeContractCBHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeContractCBHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeContractCBHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeContractCBHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户ID</label>
							<html:text property="corpId" styleId="corpId" maxlength="10" styleClass="searchEmployeeContractCB_corpId" />
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
							</label>
							<logic:equal name="role" value="1">
								<html:text property="orderId" maxlength="10" styleClass="searchEmployeeContractCB_orderId" />
							</logic:equal>
							<logic:equal name="role" value="2">
								<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchEmployeeContractCB_orderId">
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
							<html:text property="contractId" styleId="contractId" maxlength="10" styleClass="searchEmployeeContractCB_contractId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label> 
							<html:text property="employeeId" maxlength="10" styleClass="searchEmployeeContractCB_employeeCBId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label> 
							<html:text property="employeeNameZH" styleId="employeeNameZH" maxlength="50" styleClass="searchEmployeeContractCB_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label> 
							<html:text property="employeeNameEN" styleId="employeeNameEN" maxlength="50" styleClass="searchEmployeeContractCB_employeeNameEN" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label> 
							<html:select property="contractStatus" styleClass="searchEmployeeContractCB_contractStatus">
								<html:optionsCollection property="contractStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- employeeContractCB-information -->
	<div class="box noHeader" id="search-results">
		<!-- Include table jsp 包含tabel对应的jsp文件 -->
		<jsp:include page="/contents/cb/setting/table/listEmployeeContractCBTable.jsp" flush="true"/> 
	</div>
	
	<div id="popupWrapper">
		<jsp:include page="/popup/manageEmployeeContractCB.jsp"></jsp:include>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_cb_Modules').addClass('current');
		$('#menu_cb_Purchase').addClass('selected');
		
		$('#searchDiv').hide();
	})(jQuery);

	function resetForm() {
		$('.searchEmployeeContractCB_employeeCBId').val('');
		$('.searchEmployeeContractCB_corpId').val('');
		<logic:equal name="role" value="1">
			$('.searchEmployeeContractCB_orderId').val('');
		</logic:equal>	
		<logic:equal name="role" value="2">
			$('.searchEmployeeContractCB_orderId').val('0');
		</logic:equal>	
		$('.searchEmployeeContractCB_employeeCBId').val('');
		$('.searchEmployeeContractCB_employeeNameZH').val('');
		$('.searchEmployeeContractCB_employeeNameEN').val('');
		$('.searchEmployeeContractCB_contractId').val('');
		$('.searchEmployeeContractCB_contractStatus').val('0');
	};
</script>