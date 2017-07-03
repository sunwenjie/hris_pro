<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT", "employeeContractOTForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {	
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT", "init();", null, null, null)%>
		//雇员登陆限制隐藏按钮
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT")%>
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		
		$('#btnList').click(function() {
			if(agreest())
			link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>');
		});
		
		// “金额来源”添加Change事件
		$('#baseFrom').change(function(){
			if($(this).val() != 0){
				$('#percentageLI').show();
				$('#fixLI').show();
			}else{
				$('#percentageLI').hide();
				$('#fixLI').hide();
			}
		});
		
		$('#baseFrom').change();	 
		
		// 页面初始化 设置开始时间 结束时间
		
		$('#startDate').attr('onFocus',"WdatePicker({minDate:'<bean:write name="employeeContractVO" property="startDate"/>',maxDate:'#F{ $dp.$D(\\\'endDate\\\') || $dp.$DV(\\\'<bean:write name="employeeContractVO" property="endDate"/>\\\') }'})" );
		$('#endDate').attr('onFocus',"WdatePicker({maxDate:'<bean:write name="employeeContractVO" property="endDate"/>',minDate:'#F{ $dp.$D(\\\'startDate\\\') || $dp.$DV(\\\'<bean:write name="employeeContractVO" property="startDate"/>\\\') }' })" );
	})(jQuery);
	
	function init(){
		// 如果是inHouse
		if('<bean:write name="role"/>' == 2){
			var html = '<ol class="auto"><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal></label>';
			html = html + '<span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span>';
			html = html + '</li><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name" /></logic:equal></label>';
			html = html + '<span><bean:write name="employeeContractVO" property="name" /></span></li></ol>';
			$('.required').parent().after( html );
			//$('#pageTitle').html('劳动合同 - 加班设置新增 ');
		}
		else{
			$('.required').parent().after(
					'<ol class="auto"><li><label><bean:write name="employeeContractVO" property="decodeFlag" />ID</label><span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span></li><li><label><bean:write name="employeeContractVO" property="decodeFlag" />名称</label><span><bean:write name="employeeContractVO" property="name" /></span></li></ol>'
			);
			$('#pageTitle').html('<bean:write name="employeeContractVO" property="decodeFlag" /> - 加班设置');
		}
		$("#menu_employee_ServiceAgreement").addClass('selected');
	};
</script>
