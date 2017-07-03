<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>
<div id="content"> 
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, EmployeeContractAction.getAccessAction(request, response), "employeeContractTempForm", true ) %>
	
	<% final EmployeeContractVO employeeContractVO = (EmployeeContractVO)request.getAttribute("employeeContractTempForm"); %>
	<input id="comeFrom" name="comeFrom" type="hidden" value="<%=(request.getParameter("comeFrom")==null||request.getParameter("comeFrom").isEmpty())?"":request.getParameter("comeFrom")%>"/>
</div>

<!-- <div id="popupWrapper"> -->
<%-- 	<jsp:include page="/popup/searchOrder.jsp"></jsp:include> --%>
<%-- 	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include> --%>
<%-- 	<jsp:include page="/popup/createEmployee.jsp"></jsp:include> --%>
<!-- </div>	 -->
<div id="workflowPopupWrapper"></div>
<%-- �Ƿ��������������ҳ�� --%>
<input id="rePageFlag_orderId" name="rePageFlag_orderId" type="hidden" value="false"/>
<input id="rePageFlag_employeeId" name="rePageFlag_employeeId" type="hidden" value="false"/>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/calendar.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {	
		// ���ö����˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_BatchUpdateContract').addClass('selected');
		var overlap = false;
		// JS of the List
		<%
			final StringBuffer initCallBack = new StringBuffer();
			initCallBack.append("if(getSubAction() == 'createObject'){$('#employeeId').addClass('important');}");

			if(request.getAttribute("orderIdError") != null)
			{
				final String orderIdError = (String)request.getAttribute("orderIdError");
				initCallBack.append("addError('orderId', '" + orderIdError + "');");
				initCallBack.append("$('#rePageFlag_orderId').val('true');");
			}
			
			if(request.getAttribute("employeeIdError") != null)
			{
				final String employeeIdError = (String)request.getAttribute("employeeIdError");
				initCallBack.append("addError('employeeId', '" + employeeIdError + "');");
				initCallBack.append("$('#rePageFlag_employeeId').val('true');");
			}
			
		%>
		
		<%= ManageRender.generateManageJS(request, EmployeeContractAction.getAccessAction(request, response), initCallBack.toString(), null,null, null) %>
		$('#menu_employee_ServiceAgreement').removeClass('selected');
		// ��ӡ��������ٲ鿴��
		$('#calendarId').after('&nbsp;&nbsp;<a class="" onclick="quickCalendarPopup(\'<bean:write name="employeeContractTempForm" property="encodedCalendarId" />\');"><img src="images/search.png" title="���ٲ鿴����" /></a>');
		
		$('#btnAdd,#btnEdit,#btnNext').hide();
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />').click(function(){
			link('employeeContractTempAction.do?proc=list_detail&batchId=<bean:write name="employeeContractTempForm" property="batchId" />');
		});
		
		// �޸��Ͷ���ͬģ����ʾ��ʾ
		$("#templateIdLI").find("img").attr("title","��Ҫѡ���ͬģ����ϴ���ͬģ��");

		// ����������ӡ��鿴��Ա��Ϣ�������鿴������Ϣ��
		if(getSubAction() != 'createObject'){
			if( $('#rePageFlag_orderId').val() == 'false')
			{
				$('#orderIdLI label:eq(0)').append(' <a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractTempForm" property="encodedOrderId" />\');" ><img src="images/find.png" title="�鿴������¼" /></a>');
			}
			
			if( $('#rePageFlag_employeeId').val() == 'false')
			{
				$('#employeeIdLI label:eq(0)').append(' <a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractTempForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="�鿴��Ա��¼" /></a>');
			}
		}
		
		// Load Special Info
		loadHtml('#special_info', 'employeeContractTempAction.do?proc=list_special_info_html&contractId=' + $('#id').val() + '&flag=' + $('#flag').val(), getDisable(),function(){
			// ���ó�����
			$('#tab a').attr('onClick','');
		});

		// ��ʼ�� ����ʵ��
		loadEntitiesByClientId();
		// ��ʼ�� ҵ������
		loadBusinessTypesByClientId();
		// ��ʼ������
		loadOrderIdByClientId();
		// ��ʼ���Ͷ���ͬģ��
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=<bean:write name="employeeContractTempForm" property="templateId"/>&entityId=<bean:write name="employeeContractTempForm" property="entityId"/>'
				+ '&businessTypeId=<bean:write name="employeeContractTempForm" property="businessTypeId"/>&contractTypeId=2', false );
		
		// ��ʼ���������鿴��ť
// 		loadWorkflowSeach('status','<bean:write name="employeeContractTempForm" property="workflowId"/>');
		
		
		$("#employeeId").blur(function(){
			if($('#employeeId').val().length<9){
				cleanError('employeeId');
				showErrorMsg();
			}else{
				cleanError('employeeId');
			}
		});
		
		
		$("#orderId").blur(function(){
			if($('#orderId').val().length<9){
				cleanError('orderId');
				addError('orderId', '����ID��Ч��');
			}else{
				cleanError('orderId');
			}
		});
		
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// �󶨲���Change�¼�
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// �󶨷���ʵ��change�¼�
		$('#entityId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractTempForm" property="templateId"/>');
			loadContractOptions('<bean:write name="employeeContractTempForm" property="masterContractId"/>');
		});
		
		//	��ҵ������change�¼�
		$('#businessTypeId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractTempForm" property="templateId"/>');
		});
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if(getSubAction() == 'modifyObject'){
				// �ϴ�������Ч
				$('#uploadAttachment').attr("disabled", false);
				$('#uploadAttachment').removeClass("disabled");
				// Enableɾ��Сͼ��
	   			$('img[id^=warning_img]').each(function(i){
	   				$(this).show();
	   			});
	   			$('img[id^=disable_img]').each(function(i){
	   				$(this).hide();
	   			});
			}
		});
		
	})(jQuery);
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		if(getSubAction() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};
	
	// ���غ�ͬģ���������¼�
	function loadContractOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val() + '&contractTypeId=2', getDisable() );
	};	
	
	function loadEntitiesByClientId(){ 
		$.ajax({ url: 'entityAction.do?proc=list_object_options_ajax_byClientIdForInHouse&date=' + new Date(), success: function(html){
			$('.entityId').html(html);
			$('.entityId').val('<bean:write name="employeeContractTempForm" property="entityId"/>');
		}});
	};
	
	function loadBusinessTypesByClientId(){ 
		$.ajax({ url: 'businessTypeAction.do?proc=list_object_options_ajax_byClientIdForInHouse&date=' + new Date(), success: function(html){
			$('.businessTypeId').html(html);
			$('.businessTypeId').val('<bean:write name="employeeContractTempForm" property="businessTypeId"/>');
		}});
	};
	
	function loadOrderIdByClientId(){ 
		$.ajax({ url: 'clientOrderHeaderAction.do?proc=list_object_options_ajax&date=' + new Date(), success: function(html){
			$('.orderId').html(html);
			$('.orderId').val('<bean:write name="employeeContractTempForm" property="orderId"/>');
		}});
	};
	
	// ���غ�ͬģ���������¼�
	function loadLaborContractTemplateOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val()+"&contractTypeId=2", false );
	};
	
	// �޸�ʱ������ʱ������   Ŀǰ��ȥ��
	function loadDateLimit(){
		if("<bean:write name='employeeContractTempForm' property='orderId' />"!="0"){
			$.ajax({url: 'clientOrderHeaderAction.do?proc=get_object_json&orderHeaderId=<bean:write name="employeeContractTempForm" property="orderId" />&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					if(data.success == 'true'){
						if($('#startDate').val()==""){
							$('#startDate').val(data.startDate);
						}
						if($('#endDate').val()==""){
							$('#endDate').val(data.endDate);
						}
						$('#startDate').attr('onFocus',"WdatePicker({minDate:'"+data.startDate+"',maxDate:'#F{ $dp.$D(\\\'endDate\\\') || $dp.$DV(\\\'"+data.endDate+"\\\') }'})" );
						$('#endDate').attr('onFocus',"WdatePicker({maxDate:'"+data.endDate+"',minDate:'#F{ $dp.$D(\\\'startDate\\\') || $dp.$DV(\\\'"+data.startDate+"\\\') }' })" );
					}
				}
			});
		}
	};
	
	function showErrorMsg(){
		cleanError('employeeId');
		var role = '<bean:write name="role" />' != 2 ? "��Ա" : "Ա��";
		addError('employeeId', role + 'ID��Ч��');
	};
</script>

