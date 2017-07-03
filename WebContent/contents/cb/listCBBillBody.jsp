<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.web.actions.biz.cb.CBHeaderAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		  <div class="head">
	        <label id="pageTitle">商保单</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchCBBill_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="cbBillViewAction.do?proc=list_object" method="post" styleClass="searchCBBill_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="cbBillListHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="cbBillListHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="cbBillListHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="cbBillListHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="searchHeader" id="searchHeader" class="searchHeader" value="true"/>
				<fieldset>
					<ol class="auto">
							<li>
								<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="4">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchCBBill_employeeId" /> 
							</li>
							<li>
								<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="4">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>编号</label>
								<html:text property="employeeNo" maxlength="50" styleClass="searchCBBill_employeeNo" /> 
							</li>
							<li>
								<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="4">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label>
								<html:text property="employeeNameZH" maxlength="100" styleClass="searchCBBill_employeeNameZH" /> 
							</li>
							<li>
								<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="4">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label>
								<html:text property="employeeNameEN" maxlength="100" styleClass="searchCBBill_employeeNameEN" /> 
							</li>
							<li>
								<label>证件号码</label>
								<html:text property="certificateNumber" maxlength="100" styleClass="searchCBBill_certificateNumber" /> 
							</li> 
							<li>
								<label>户口性质</label> 
								<html:select property="residencyType" styleClass="searchCBBill_residencyType">
									<html:optionsCollection property="residencyTypes" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
							<logic:equal name="role" value="2">
							<li>
								<label>结算规则ID</label>
								<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchCBBill_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
								</logic:notEmpty>
									<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchCBBill_orderId" /> 
			   						</logic:empty>
		   					</li>
		   					</logic:equal>
			   				<li>
								<label><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="4">派送信息</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</label>
								<html:text property="contractId" maxlength="10" styleClass="searchCBBill_contractId" /> 
							</li> 
						<li>
							<label>商保方案</label> 
							<html:select property="cbId" styleClass="searchCBBill_sbSolutionId">
								<html:optionsCollection property="commercialBenefitSolutions" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>月份</label> 
							<html:select property="monthly" styleClass="searchCBBill_detailMonthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>商保状态</label> 
							<div style="width: 235px;">
								<logic:iterate id="cbStatus" name="cbBillForm" property="cbStatuses" indexId="index">
									<label class="auto">
										<input type="checkbox" name="cbStatusArray" id="cbStatus_<bean:write name="cbStatus" property="mappingId" />" class="cbStatus_<bean:write name="cbStatus" property="mappingId" />" value="<bean:write name="cbStatus" property="mappingId" />" />
										<bean:write name="cbStatus" property="mappingValue" />
									</label>
								</logic:iterate>
							</div>
						</li>
						<li>
							<label>状态</label>
							<html:select property="status" styleClass="searchCBBill_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue"  />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>

	<!-- Information Manage Form -->
	<div class="box noHeader">
	    <div class="inner">
	        <div id="messageWrapper">
	        </div>
	        <div class="top">
	            <%-- <logic:equal name="isExportExcel" value="1"> --%>
	            	<kan:auth right="export" action="<%=CBHeaderAction.ACCESSACTION_CBBILL%>">
            			<a id="exportExcel" name="exportExcel" class="commonTools" title="导出Excel文件"><img src="images/appicons/excel_16.png" /></a> 
            		</kan:auth>
	            <%-- </logic:equal>	 --%>
	            <a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>	
	         <!-- 包含商保方案列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="table/listCBBillTable.jsp"></jsp:include>
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单样式、按钮
		$('#menu_cb_Modules').addClass('current');			
		$('#menu_cb_Bill').addClass('selected');
		$('#exportExcel').click( function(){
			linkForm('searchCBBill_form', null, 'cbBillViewAction.do?proc=exportReport', null);
		});
	})(jQuery);
	
	function resetForm(){
		$(".searchCBBill_employeeId").val('');
		$(".searchCBBill_employeeNo").val('');
		$(".searchCBBill_employeeNameZH").val('');
		$(".searchCBBill_employeeNameEN").val('');
		$(".searchCBBill_certificateNumber").val('');
		$(".searchCBBill_residencyType").val('0');
		$(".searchCBBill_contractId").val('');
		$(".searchCBBill_sbSolutionId").val('0');
		$(".searchCBBill_detailMonthly").val('0');
		$(".searchCBBill_cbStatus").get(0).selectedIndex=0 ;
	}
</script>