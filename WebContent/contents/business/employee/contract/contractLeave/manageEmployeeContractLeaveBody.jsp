<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE", "employeeContractLeaveForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		<%
			final StringBuffer submitCallBack = new StringBuffer();
			submitCallBack.append( "if(checkEmployeeContractLeave()==true){flag ++;addError('itemId','已经存在该科目类型的休假设置；')}" );
		%>
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE", "init();", null, submitCallBack.toString() ) %>
	    //雇员登陆限制隐藏按钮
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE")%>
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		
		$('#btnList').click(function() {
			if(agreest())
			link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>');
		});
		
	 	//“科目”选中年假事件
		 $('#itemId').change(function(){
		 	if($(this).val() == '41' || $('#itemId option:selected').html() == '年假'){
			 	//$('#legalQuantityLI').show();
			 	$('#yearLI').show();
			 	$('#cycle').val('5');
				$('#cycleLI').hide();
			 	//$('li#benefitQuantityLI label').html('<bean:message bundle="business" key="business.leave.benefit.hours" />');
		 	}else{
			 	$('#legalQuantityLI').hide();		
			 	$('#yearLI').hide();
			 	$('#cycle').val('0');
				$('#cycleLI').show();
			 	$('li#benefitQuantityLI label').html('<bean:message bundle="business" key="business.leave.hours" />');
		 	}
	 	});
		 
		$('#itemId').change();	

	 	//“可以延迟使用”择中事件
	 	$('#delayUsing').change(function(){
		 	if($(this).val() == '1'){				 
			 	$("#legalQuantityDelayMonthLI").show();
			 	$("#benefitQuantityDelayMonthLI").show();
			}else{
			 	$("#legalQuantityDelayMonthLI").hide();
			 	$("#benefitQuantityDelayMonthLI").hide();
		 	}
	 	});

	 	$('#delayUsing').change();	
	})(jQuery);
	
	function init(){
		// 如果是Inhouse
		if('<bean:write name="role"/>' == 2){
			var html = '<ol class="auto"><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal></label>';
			html = html + '<span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span>';
			html = html + '</li><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name" /></logic:equal></label>';
			html = html + '<span><bean:write name="employeeContractVO" property="name" /></span></li></ol>';
			$('.required').parent().after( html );
			//$('#pageTitle').html('劳动合同 - 休假设置新增 ');
		}
		else{
			$('.required').parent().after(
					'<ol class="auto"><li><label><bean:write name="employeeContractVO" property="decodeFlag" />ID</label><span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span></li><li><label><bean:write name="employeeContractVO" property="decodeFlag" />名称</label><span><bean:write name="employeeContractVO" property="name" /></span></li></ol>'
			);
			$('#pageTitle').html('<bean:write name="employeeContractVO" property="decodeFlag" /> - 休假设置');
		}
		$("#menu_employee_ServiceAgreement").addClass('selected');
		
		// 排除已添加的
		var addedItems = "<bean:write name='addedItems'/>";
		$("#itemId option").each(function(){
			var items = addedItems.split(",");
			for(var i = 0 ; i<items.length;i++){
				if(items[i]==$(this).val()){
					$(this).remove();
				}
			}
		});
	};
	
	function checkEmployeeContractLeave(){
		var returnValue = false;
		$.ajax({
			url : "employeeContractLeaveAction.do?proc=checkEmployeeContractLeave_ajax&employeeLeaveId=<bean:write name="employeeContractLeaveForm" property="employeeLeaveId" />&contractId=<bean:write name="employeeContractLeaveForm" property="contractId" />&itemId=" + $('#itemId').val() + "&year=" + $('#year').val(), 
			type: 'POST',
			async:false,
			success : function(data){
				if(data=='2'){
					returnValue = true;
				}
			}
		});
		return returnValue;
	};
</script>

