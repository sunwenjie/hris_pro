<%@page import="com.kan.base.util.KANUtil"%>
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
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content"> 
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, EmployeeContractAction.getAccessAction(request, response), "employeeContractForm", true ) %>
	
	<% final EmployeeContractVO employeeContractVO = (EmployeeContractVO)request.getAttribute("employeeContractForm"); %>
	<input id="comeFrom" name="comeFrom" type="hidden" value="<%=(request.getParameter("comeFrom")==null||request.getParameter("comeFrom").isEmpty())?"":request.getParameter("comeFrom")%>"/>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
	<jsp:include page="/popup/createEmployee.jsp"></jsp:include>
</div>
	
<div id="workflowPopupWrapper"></div>

<%-- �Ƿ��������������ҳ�� --%>
<input id="rePageFlag_orderId" name="rePageFlag_orderId" type="hidden" value="false"/>
<input id="rePageFlag_employeeId" name="rePageFlag_employeeId" type="hidden" value="false"/>
<input id="rePageFlag_employeeId" name="rePageFlag_employeeId" type="hidden"/>
<input id="probationMonthHide" type="hidden"/>
<input id="contractPeriodHide" type="hidden"/>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/calendar.jsp"></jsp:include>
</div>	

<script type="text/javascript">
Date.prototype.format =function(format)
{
var o = {
"M+" : this.getMonth()+1, //month
"d+" : this.getDate(), //day
"h+" : this.getHours(), //hour
"m+" : this.getMinutes(), //minute
"s+" : this.getSeconds(), //second
"q+" : Math.floor((this.getMonth()+3)/3), //quarter
"S" : this.getMilliseconds() //millisecond
}
if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
(this.getFullYear()+"").substr(4- RegExp.$1.length));
for(var k in o)if(new RegExp("("+ k +")").test(format))
format = format.replace(RegExp.$1,
RegExp.$1.length==1? o[k] :
("00"+ o[k]).substr((""+ o[k]).length));
return format;
}
function changeStartDate(){
		if("modifyObject"==$('#subAction').val()||$('#startDate').val()==null||$('#startDate').val()==""){
			return;
		}
// 	alert($('#contractPeriodHide').val());
// 	alert($('#probationMonthHide').val());
	
	if($('#probationMonthHide').val()!=null&&$('#probationMonthHide').val()!=0){
		
		
		var probationEndDate=new Date($('#startDate').val()); // ��ȡ���
		probationEndDate.setMonth(probationEndDate.getMonth() + Number($('#probationMonthHide').val())); // ϵͳ��ת��
		probationEndDate.setDate(probationEndDate.getDate() -1 ); // ϵͳ��ת��
		
// 		alert(probationEndDate.format('yyyy-MM-dd'));
// 		var dateArr = $('#startDate').val().split('-');
// 		var month = Number(dateArr[1])+Number($('#probationMonthHide').val());
// 		var year =Number(dateArr[0]);
// 		if(month>12){
// 			year++;
// 			month -= 12;
// 		}
		$('#probationEndDate').val(probationEndDate.format('yyyy-MM-dd'));
	}
	
	if($('#contractPeriodHide').val()!=null&&$('#contractPeriodHide').val()!=0){
// 		var dateArr = $('#startDate').val().split('-');
// 		var year =Number(dateArr[0])+Number($('#contractPeriodHide').val());
// 		var month = Number(dateArr[1]);
		var endDate=new Date($('#startDate').val()); // ��ȡ���
		endDate.setFullYear(endDate.getFullYear() + Number($('#contractPeriodHide').val())); // ϵͳ��ת��
		endDate.setDate(endDate.getDate() -1 ); // ϵͳ��ת��
		
// 		alert(endDate.format('yyyy-MM-dd'));
		$('#endDate').val(endDate.format('yyyy-MM-dd'));
	}
};	

	function checkContractConflict(){
		var overlap = false;
		$.ajax({
			url : 'employeeContractAction.do?proc=checkContractConflict&flag=2&clientId='+$('#clientId').val()+'&employeeId='+$('#employeeId').val()+'&startDate='+$('#startDate').val()+'&contractId='+$('#contractId').val()+'&endDate='+$('#endDate').val()+'&entityId='+$('#entityId').val()+'&orderId='+$('#orderId').val(), 
			type : 'POST',
			async : false,
			success : function(data){
				if(data=='1'){
					cleanError('startDate');
					addError('startDate', '<bean:message bundle="public" key="error.time.period.exist.contract" />');
					overlap = true;
				}else{
					cleanError('startDate');
					overlap = false;
					changeStartDate();
				}
			}
		});
		return overlap;
	};

	(function($) {	
		
		// JS of the List
		<%
			final StringBuffer initCallBack = new StringBuffer();
// 			initCallBack.append("init();");
			initCallBack.append("if(getSubAction() == 'createObject'){$('#employeeId').addClass('important');}");
			initCallBack.append("addExtendInfo();");
			initCallBack.append("$('#lastWorkDate').attr('disabled','disabled');");
			
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
			
			// ��׼�����¡��鵵 ״̬��ʾ���ڰ�ť
			if(employeeContractVO != null && (employeeContractVO.getStatus().equals("3") || employeeContractVO.getStatus().equals("5") || employeeContractVO.getStatus().equals("6")))
			{
			   initCallBack.append(" $('#btnRenew').show();");
			}

			final StringBuffer editCallBack = new StringBuffer();
			
			if(employeeContractVO != null && (employeeContractVO.getStatus().equals("1") || employeeContractVO.getStatus().equals("4"))) {
				editCallBack.append("$('#employeeId').addClass('important');");
				editCallBack.append("$('#resignDate').attr('disabled', 'disabled');");
				editCallBack.append("$('#employStatus').attr('disabled', 'disabled');");
			}
			editCallBack.append("$('#employStatus').attr('disabled', 'disabled');");
			editCallBack.append("$('#employeeId').attr('disabled', 'disabled');");
			editCallBack.append("$('#employeeNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#employeeNameEN').attr('disabled', 'disabled');");
			editCallBack.append("$('#entityId').attr('disabled', 'disabled');");
			editCallBack.append("$('#status').attr('disabled', 'disabled');");
			editCallBack.append("$('#resignDate').attr('disabled','disabled');");
			editCallBack.append("$('#lastWorkDate').attr('disabled','disabled');");
			
			// �����������޸�
		    editCallBack.append("$('#orderId').removeAttr('disabled');");
			
			if(employeeContractVO != null && (employeeContractVO.getStatus().equals("3") || employeeContractVO.getStatus().equals("6")))
			{
			   editCallBack.append("$('#contractNo').attr('disabled', 'disabled');");
			}
			
			if(employeeContractVO != null && (employeeContractVO.getStatus().equals("5") || employeeContractVO.getStatus().equals("6")))
			{
			   editCallBack.append("$('#templateId').attr('disabled', 'disabled');");
			}
			
			if(employeeContractVO != null && (employeeContractVO.getStatus().equals("3") || employeeContractVO.getStatus().equals("5") || employeeContractVO.getStatus().equals("6")))
			{
			   editCallBack.append("$('#nameZH').attr('disabled', 'disabled');");
			   editCallBack.append("$('#nameEN').attr('disabled', 'disabled');");
			   editCallBack.append("$('#startDate').attr('disabled', 'disabled');");
			   editCallBack.append("disableLinkById('#employeeSearch');");
			}
			
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append("if(checkContractConflict() == true){flag = flag + 1;}");
			submitAdditionalCallback.append("if(($('#employStatus').val() == '0' || $('#employStatus').val() == '1') && $('#resignDate').val() != ''){addError('employStatus', '" + KANUtil.getProperty( request.getLocale(), "popup.employment.status.error" ) + "');flag = flag + 1;}");
			submitAdditionalCallback.append("if($('#employStatus').val() != '1'){flag = flag + validate('resignDate', true, 'common', 100, 0);}");
		%>
		
		<%= ManageRender.generateManageJS(request, EmployeeContractAction.getAccessAction(request, response), initCallBack.toString(), editCallBack.toString(), null, submitAdditionalCallback.toString()) %>
		
		// HRM��������
		$('#lockedLI').hide();
		// ��ӡ��������ٲ鿴��
		$('#calendarId').after('&nbsp;&nbsp;<a class="" onclick="quickCalendarPopup(\'<bean:write name="employeeContractForm" property="encodedCalendarId" />\');"><img src="images/search.png" /></a>');
		
		<%
		  	String more = "";
			if( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
			{
			   more = "���" + (BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "�������" : "����" ) + "���趨������";
			   more = more + (BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "�������" : "����" ) + "�����趨";
 			}
			else 
			{
			   more = "If the " + (BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "Calculation Rule" : "Order" ) + " has been set, the reference ";
			   more = more + (BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "Calculation Rule" : "Order" ) + " setup";
			}   
		%>
		// ��Ӹ�����Ϣ����
		$('#HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE_CG13').html('<ol class="auto"><li><label style="width: 280px;"><%=more%></label></li></ol>' + $('#HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE_CG13').html());
		
		// ����Ǵ���˻��߽���״̬�����ء��༭����ť
		if( $('#status').val() == '2' || $('#status').val() == '7'){
			$('#btnEdit').hide();
		}
		
		// ������½������˻�״̬����ʾ ���ύ����ť
		if( ($('#status').val() == '1' && getSubAction() != 'createObject') || $('#status').val() == '4' ){
			$('#btnSubmit').show();
		}
		
		// ҳ���ʼ�� - ��ʾ����һ������ť
		$('#btnNext').show();
		$('#btnNext').click( function (){ generateContract(); } );
		
		// ��׼�����º͹鵵��������ύ����������桱
		$('#btnEdit').click( function(){
			$('#uploadAttachment').show();
			if( $('#status').val() == '3' || $('#status').val() == '5' || $('#status').val() == '6' ){
				$('#btnSubmit').show();
				$('#btnEdit').hide();
				$('#btnRenew').hide();
			}
		});
		
		$('#btnSubmit').click( function(){
			if(validate_manage_primary_form() == 0){
				// ��֤��ͬģ����߸�������Ϊ��
				if($('#templateId').val() == 0 && $('#attachmentsOL #attachmentArray').size() == 0){
					alert('<bean:message bundle="public" key="popup.not.template.or.attachment" />');
					return false;
				}	
				
				// ҳ���޸����ݱ�������
				if(getSubAction() == 'modifyObject' && !confirm('<bean:message bundle="public" key="popup.leave.and.save.current.page" />')){
					return false;
				}
				
				// Update the form action & subAction
				$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=modify_object&flag=2');
				$('.manage_primary_form input#subAction').val("submitObject"); 
				
				// Enable the form
				enableForm('manage_primary_form');
				submit('manage_primary_form');
			}
		});
		
		//	����accountId ���ض�Ӧ����Ӷ״̬��
		<% if(employeeContractVO.getAccountId() != null){ %>
		loadHtml('#employStatus', 'employeeContractAction.do?proc=list_employStatuses_options_ajax&accountId=' + '<%= employeeContractVO.getAccountId()%>&employStatus=' + '<%= employeeContractVO.getEmployStatus()%>' );
		<% } %>

		//	������ ����ť�¼��������ֻ�򿪺�ͬ�������ڣ�Ȼ������ύ��
		$('#btnRenew').click( function(){
			if(confirm('<bean:message bundle="public" key="popup.contract.relation.solution.setting.renew" />')){
				$('#endDate').removeAttr('disabled');
				$('#endDate').attr('onfocus', 'WdatePicker({minDate:\'' + $('#endDate').val() + '\'})');
				$('#btnSubmit').show();
				$('#btnEdit').hide();
				$('#btnRenew').hide();
			}
		});
		
		// ����������ӡ��鿴��Ա��Ϣ�������鿴������Ϣ��
		if(getSubAction() != 'createObject'){
			if( $('#rePageFlag_orderId').val() == 'false')
			{
				$('#orderIdLI label:eq(0)').append(' <a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedOrderId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			}
			
			if( $('#rePageFlag_employeeId').val() == 'false')
			{
				$('#employeeIdLI label:eq(0)').append(' <a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			}
		}
		
		// Load Special Info
		loadHtml('#special_info', 'employeeContractAction.do?proc=list_special_info_html&contractId=' + $('#id').val() + '&flag=' + $('#flag').val(), getSubAction() == 'viewObject');

		// ��ӡ�����Ա����Ϣ��
		$('#employeeId').after('<a id="employeeSearch" onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>');
		// ��ʼ�� ����ʵ��
		loadEntitiesByClientId();
		// ��ʼ�� ҵ������
		loadBusinessTypesByClientId();
		// ��ʼ������
		loadOrderIdByClientId();
		// ��ʼ���Ͷ���ͬģ��
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=<bean:write name="employeeContractForm" property="templateId"/>&entityId=<bean:write name="employeeContractForm" property="entityId"/>'
				+ '&businessTypeId=<bean:write name="employeeContractForm" property="businessTypeId"/>&contractTypeId=2', false );
		
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="employeeContractForm" property="workflowId"/>');
		
		// ��ԱID�����¼�
		$("#employeeId").bind('keyup', function(){
			if($("#employeeId").val().length >= 9){
				$.ajax({url: 'employeeAction.do?proc=get_object_json&employeeId=' + $(this).val() + '&date=' + new Date(),
					dataType : 'json',
					success: function(data){
						cleanError('employeeId');
						
						if(data.success == 'true'){
							
							$('#encodedEmployeeId').val(data.encodedId);
							$('#employeeNameZH').val(data.nameZH);
							$('#employeeNameEN').val(data.nameEN);
							
							// �Զ������Ͷ���ͬ����
							var date = new Date();
							var contractNameZH = '�Ͷ���ͬ��' + $('#employeeNameZH').val() + '�� - ' + date.getFullYear();
							var contractNameEN = '';
							
							if($('#employeeNameEN').val().trim().length == 0){
								contractNameEN = 'Labor Contract  ' + date.getFullYear();
							}
							else{
								contractNameEN = 'Labor Contract (' + $('#employeeNameEN').val() + ') - ' + date.getFullYear();
							}
							
							if(date.getMonth() + 1 < 10){
								contractNameZH = contractNameZH + "0";
								contractNameEN = contractNameEN + "0";
							}
							
							contractNameZH = contractNameZH + eval(date.getMonth() + 1);
							contractNameEN = contractNameEN + eval(date.getMonth() + 1);
							
							if( $('#nameZH').val() == '' ){
								$('#nameZH').val(contractNameZH);
							}
							if( $('#nameEN').val() == '' ){
								$('#nameEN').val(contractNameEN);
							}

							if(	getSubAction() == "createObject" && data.hireAgain=='2'){
								addError('employeeId','��Ա���Ѿ�������Ϊ���ɷ�Ƹ!');
							}
							
						}else if(data.success == 'false'){
							$('#encodedEmployeeId').val('');
							$('#employeeNameZH').val('');
							$('#employeeNameEN').val('');
							addError('employeeId', data.errorMsg);
						}
					}
				});
			}else{
				cleanError('employeeId');
				showErrorMsg();
			}
		});
		
		$("#employeeId").blur(function(){
			if($('#employeeId').val().length<9){
				cleanError('employeeId');
				showErrorMsg();
			}else{
				cleanError('employeeId');
			}
		});
		
		// ����ID�����¼�
		$("#orderId").bind('change', function(){
			
			if(getSubAction() == "viewObject"){
				return;
			}
			
			$.ajax({url: 'clientOrderHeaderAction.do?proc=get_object_json&orderHeaderId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					cleanError('orderId');
					
					if(data.success == 'true'){
						$('#encodedOrderId').val(data.encodedId);
						$('#encodedClientId').val(data.encodedClientId);
						$('#clientId').val(data.clientId);
						$('#clientNameZH').val(data.clientNameZH);
						$('#clientNameEN').val(data.clientNameEN);
						$('#entityId').val(data.entityId);
						$('#businessTypeId').val(data.businessTypeId);
						// ����ʱ���жϲ�����
						/* if(data.endDate < $("#endDate").val()){
							$("#endDate").val(data.endDate);
						} */
						if(data.EndDate!=null&&"modifyObject"!=$('#subAction').val()){
							$("#endDate").val(data.EndDate);
						}
						
						if(data.probationEndDate!=null&&"modifyObject"!=$('#subAction').val()){
							$("#probationEndDate").val(data.probationEndDate);
						}
						if(data.probationMonth!=null&&"modifyObject"!=$('#subAction').val()){
							$("#probationMonthHide").val(data.probationMonth);
						}
						if(data.contractPeriod!=null&&"modifyObject"!=$('#subAction').val()){
							$("#contractPeriodHide").val(data.contractPeriod);
						}
						$('div.top').show();
						// ��������޸ģ���֤ʱ��
						checkContractConflict();
					}else if(data.success == 'false'){
						var jsDate = new Date();
						$('#encodedOrderId').val('');
						$('#orderId').val('');
						$('#encodedClientId').val('');
						$('#clientId').val('');
						$('#clientNameZH').val('');
						$('#clientNameEN').val('');			
						$('#entityId').val('0');
						$('#businessTypeId').val('0');
						addError('orderId', '<bean:message bundle="public" key="error.input.id.invalid" />');
					}
					
					// ���غ�ͬ
					loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
				}
			});
		});
		
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// �󶨲���Change�¼�
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		
	
		
		// �󶨷���ʵ��change�¼�
		$('#entityId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
			loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
		});
		
		//	��ҵ������change�¼�
		//$('#businessTypeId').change(function (){
		//	loadLaborContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
		//});
		
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
		
		// �춯ԭ��ѡ����ʾ
		changeReason_option_tips();
		
		// ���б�ť����¼�
		$('#btnList').click( function () {
			if (agreest())
			link('employeeContractAction.do?proc=list_object&flag=2');
		}); 
			
		// ����Ͷ���ͬ��ǩ��ť
		<kan:auth right="new" action='<%=EmployeeContractAction.ACCESS_ACTION_SERVICE_IN_HOUSE%>'>
		<%
	    	if(employeeContractVO != null && !("1".equals(employeeContractVO.getIsContinued()))) {
	  	 %>
			   if($('#contractId').val() != ''){
			   	 $('#btnList').after('<a name="copyInfo" title="<bean:message bundle='public' key='link.renew.tips' />" class="commonTools" id="copyInfo" onclick="if(confirm(\'<bean:message bundle='public' key='popup.confirm.renew.contract' />\')){link(\'employeeContractAction.do?proc=continue_object&contractId=<bean:write name="employeeContractForm" property="encodedId"/>&com.kan.token=' + $("#com.kan.token").val() + '\');}">&nbsp;&nbsp;<u><bean:message bundle="public" key="link.renew" /></u></a>');
			   }
		   <%
		    }
		   %>
		</kan:auth>
	})(jQuery);
	
	// ���غ�ͬģ���������¼�
	function loadContractOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val() + '&contractTypeId=2', getSubAction() == 'viewObject' );
	};	
	
	// �����Ͷ���ͬ
	function generateContract(){
		
		// ��֤��ͬģ����߸�������Ϊ��
		if( $('#templateId').val() == 0 && $('#attachmentsOL #attachmentArray').size() == 0){
			alert('<bean:message bundle="public" key="popup.not.template.or.attachment" />');
			return false;
		}	
		
		// ������δ�༭״̬
		if( getSubAction() == 'modifyObject'){
			alert('<bean:message bundle="public" key="popup.first.save.or.submit" />');
			return false;
		}
		
		if( validate_manage_primary_form() == 0){

			// ҳ���޸����ݱ�������
			if(!confirm('<bean:message bundle="public" key="popup.leave.and.save.current.page" />')){
				return false;
			}
			
			// Update the form action
			$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=generate_contract');
			
			// Enable the form
			enableForm('manage_primary_form');
			submit('manage_primary_form');
		}
	};
	
	function loadEntitiesByClientId(){ 
		$.ajax({ url: 'entityAction.do?proc=list_object_options_ajax_byClientIdForInHouse&date=' + new Date(), success: function(html){
			$('.entityId').html(html);
			$('.entityId').val('<bean:write name="employeeContractForm" property="entityId"/>');
		}});
	};
	
	function loadBusinessTypesByClientId(){ 
		$.ajax({ url: 'businessTypeAction.do?proc=list_object_options_ajax_byClientIdForInHouse&date=' + new Date(), success: function(html){
			$('.businessTypeId').html(html);
			$('.businessTypeId').val('<bean:write name="employeeContractForm" property="businessTypeId"/>');
		}});
	};
	
	function loadOrderIdByClientId(){ 
		$.ajax({ url: 'clientOrderHeaderAction.do?proc=list_object_options_ajax&date=' + new Date(), success: function(html){
			$('.orderId').html(html);
			$('.orderId').val('<bean:write name="employeeContractForm" property="orderId"/>');
			
			if($("#orderId").val != '' && $("#orderId").val() != '0'){
				$("#orderId").trigger('change');
			}
			
			$('div.top').show();
		}});
	};
	
	// ���غ�ͬģ���������¼�
	function loadLaborContractTemplateOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val()+"&contractTypeId=2", false );
	};
	
	function showErrorMsg(){
		cleanError('employeeId');
		addError('employeeId','<bean:message bundle="public" key="error.input.id.invalid" />');
	};
	
	/**
	function init(){
		// ��ʼ��Ӫ�ղ���
		loadSettlementBranch('<bean:write name="employeeContractForm" property="employeeId"/>','<bean:write name="employeeContractForm" property="settlementBranch"/>');
		replacePleaseSelects();
	};
	
	function loadSettlementBranch(employeeId, settlementBranch){
		$.post("branchAction.do?proc=getSettlementBranchOptions", {"employeeId":employeeId, "settlementBranch":settlementBranch}, function(data){
			jQuery(data).appendTo("#settlementBranch");
		}, "text");
	};
	*/
	
	function replacePleaseSelects(){
		//1. ����,2. �Ű�,3. ���ڷ�ʽ,4. �������,5. ����������,
		 <%
		    String optionVal = "";
		 	if( "1".equals( BaseAction.getRole( request, null ) ) ) 
		 	{
		 	   optionVal = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "�ο���������" : "Quote Order";
		 	} else {
		 	   optionVal = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "�ο������������" : "Quote Calculation Rule";
		 	}
		 	out.print( "$('#shiftId option[value=\"0\"]').text('" + optionVal + "');" );
		 	out.print( "$('#calendarId option[value=\"0\"]').text('" + optionVal + "');" );
		 	out.print( "$('#attendanceCheckType option[value=\"0\"]').text('" + optionVal + "');" );
		 	out.print( "$('#probationMonth option[value=\"0\"]').text('" + optionVal + "');" );
		 %>
		//6. н�깩Ӧ��,7. ��˰������8. ��˰˰�ʣ�
		//9. ���ٹ��ʣ�10. �Ӱ���Ҫ���룬11. �����ռӰ����棬12. ��Ϣ�ռӰ�������13. �ڼ��ռӰ�������
		//��java����
	};
	
	// ����ϼ����ź�ֱ�߾���
	function addExtendInfo(){
		if(getSubAction() != 'createObject'){
			var html = "<li style='width:50%'>";
			html += "<label><bean:message bundle="business" key="business.employee.contract.parent.branch" /></label>";
			html += "<label><bean:write name="employeeContractForm" property="parentBranchName" /></label>";
			html += "</li>";
			html += "<li style='width:50%'>";
			html += "<label><bean:message bundle="business" key="business.employee.contract.line.manager" /></label>";
			html += "<label><bean:write name="employeeContractForm" property="lineManager" /></label>";
			html += "</li>";
			$("#decodeModifyDateLI").after(html);
		}
	};
	
	// �춯ԭ��ѡ����ʾ
	function changeReason_option_tips(){
		$('#remark3 option').each( function(){
			if( $(this).val() == 1){
				$(this).attr('title','New to iClick; or Company Code Change');
			}else if( $(this).val() == 2){
				$(this).attr('title','Employment Category Change');
			}else if( $(this).val() == 3){
				$(this).attr('title','Org. Structure Change');
			}else if( $(this).val() == 4){
				$(this).attr('title','BU/Function; Department; Job Role');
			}else if( $(this).val() == 5){
				$(this).attr('title','New Manager on board; People Manager/Team Lead Change');
			}else if( $(this).val() == 6){
				$(this).attr('title','Job Grade Change');
			}else if( $(this).val() == 7){
				$(this).attr('title','IC/M Shift; or Working Title Change');
			}else if( $(this).val() == 8){
				$(this).attr('title','Location Change');
			}else if( $(this).val() == 9){
				$(this).attr('title','Pay Structure Change; Pay Change');
			}else if( $(this).val() == 10){
				$(this).attr('title','Leave iClick; or Company Code Change');
			}else if( $(this).val() == 11){
				$(this).attr('title','Data Correction only');
			}
		})
	};
</script>

