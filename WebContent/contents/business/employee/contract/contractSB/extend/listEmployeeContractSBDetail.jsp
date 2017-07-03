<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.domain.biz.vendor.VendorVO"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.hro.domain.biz.vendor.VendorContactVO"%>
<%@ page import="com.kan.hro.web.renders.biz.vendor.VendorRender"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<% request.setAttribute( "role", BaseAction.getRole( request, response ) ); %>
<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first"><bean:message bundle="business" key="business.employee.contract.sb.detail" /></li> 
		</ul> 
	</div> 
	<div class="tabContent">
		<div id="tabContent1" class="kantab">
			<logic:notEqual name="role" value="5">
			<ol>
				<li>
					<input type="button" class="hover" id="convenientSettingA" name="convenientSettingA" value="<bean:message bundle="business" key="business.employee.contract.sb.quick.setting.base" />" onclick="convenientSetting();">
					<img title="<bean:message bundle="business" key="business.employee.contract.sb.quick.setting.base.tips" />" src="images/tips.png"> &nbsp;
				 	<input id="convenienceValue" class="manageSBDetail_convenienceValue">
			 	</li>
			 </ol>
			</logic:notEqual>
			<div id="tableWrapper">
				<!-- Include Table --> 
				<jsp:include page="/contents/business/employee/contract/contractSB/table/listEmployeeContractSBDetailTable.jsp" flush="true"></jsp:include>		
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="forwardURL" name="forwardURL" value="" />

<script type="text/javascript">
	(function($) {	
		var employeeSBId = '<bean:write name="employeeContractSBForm" property="employeeSBId"/>';
		
		if(getSubAction() == 'viewObject'){
			disableLinkById('#convenientSettingA');
		}
	})(jQuery);
	
	function convenientSetting(){
		var convenienceValue = Number($('.manageSBDetail_convenienceValue').val());
		
		$("#special_info table tbody tr td input[name^='base']").each(function(){
			var floorValue = Number($(this).prev('input').val());
			var capValue = Number($(this).next('input').val());
			
			if(validate("manageSBDetail_convenienceValue", true, "currency", 999999999, 0) == 0){
				if(convenienceValue <= capValue && convenienceValue >= floorValue){
					$(this).val(convenienceValue);
				}else if(convenienceValue<floorValue){
					$(this).val(floorValue);
				}else if(convenienceValue>capValue){
					$(this).val(capValue);
				}
			}
			
		});
		
		$("#sbBase").val(convenienceValue);
	};
</script>