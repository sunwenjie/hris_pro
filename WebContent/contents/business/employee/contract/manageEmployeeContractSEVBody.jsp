<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<% 
	final EmployeeContractVO employeeContractVO = (EmployeeContractVO)request.getAttribute("employeeContractForm"); 
%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, EmployeeContractAction.getAccessAction( request, response ), "employeeContractForm", true ) %>
	<input id="comeFrom" name="comeFrom" type="hidden" value="<%=(request.getParameter("comeFrom")==null||request.getParameter("comeFrom").isEmpty())?"":request.getParameter("comeFrom")%>"/>
	<input id="encodedEmployeeId" name="encodedEmployeeId" type="hidden" value="" />
	<input id="encodedOrderId" name="encodedOrderId" type="hidden" value="" />
	<input id="encodedClientId" name="encodedClientId" type="hidden" value="<bean:write name="employeeContractForm" property="encodedClientId" />" />
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
	<jsp:include page="/popup/createEmployee.jsp"></jsp:include>
</div>	

<%-- �Ƿ��������������ҳ�� --%>
<input id="rePageFlag_orderId" name="rePageFlag_orderId" type="hidden" value="false"/>
<input id="rePageFlag_employeeId" name="rePageFlag_employeeId" type="hidden" value="false"/>

<script type="text/javascript">
	var overlap = false;
	
	function checkContractConflict(){
		// ��֤��Ա��һ���ͻ�����ͬ����ʵ�����Ƿ����ʱ���ظ�������Э��
		if($('#employeeId').val() != '' && $('#clientId').val() != '' && $('#entityId').val() != '' && $('#entityId').val() != 0 && $('#startDate').val() != '' && $('#endDate').val() != '' && getSubAction() != 'viewObject' ){
			$.post('employeeContractAction.do?proc=checkContractConflict',
				{'clientId':$('#clientId').val(),
				 'employeeId':$('#employeeId').val(),
				 'startDate':$('#startDate').val(),
				 'contractId':$('#contractId').val(),
				 'endDate':$('#endDate').val(),
				 'entityId':$('#entityId').val(),
				 'flag':'2'},
			function(data){
				if(data=='1'){
					cleanError('startDate');
					addError('startDate', '��ʱ�����ͬ�ͻ�����ͬ����ʵ���Ѵ�������Э��');
					overlap = true;
				}else{
					cleanError('startDate');
					overlap = false;
				}
			},'text');
		};
		return false;
	};

	(function($) {	
		// JS of the List
		<%
			final StringBuffer initCallBack = new StringBuffer();
// 			initCallBack.append("init();");
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
			
			// ��׼�����¡��鵵 ״̬��ʾ���ڰ�ť
			if(employeeContractVO != null && (employeeContractVO.getStatus().equals("3") || employeeContractVO.getStatus().equals("5") || employeeContractVO.getStatus().equals("6")))
			{
			   initCallBack.append(" $('#btnRenew').show();");
			}

			final StringBuffer editCallBack = new StringBuffer();
			
			if(employeeContractVO != null && employeeContractVO.getStatus().equals("1")) {
			   	editCallBack.append("$('#employeeId').addClass('important');");
				editCallBack.append("$('#orderId').addClass('important');");
				editCallBack.append("$('#employeeId').addClass('important');");
			}
			
			editCallBack.append("$('#employeeId').attr('disabled', 'disabled');");
			editCallBack.append("$('#employeeNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#employeeNameEN').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientId').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientNameEN').attr('disabled', 'disabled');");
			editCallBack.append("$('#entityId').attr('disabled', 'disabled');");
			editCallBack.append("$('#businessTypeId').attr('disabled', 'disabled');");
			editCallBack.append("$('#status').attr('disabled', 'disabled');");
			
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
			   editCallBack.append("$('#orderId').attr('disabled', 'disabled');");
			   editCallBack.append("$('#nameZH').attr('disabled', 'disabled');");
			   editCallBack.append("$('#nameEN').attr('disabled', 'disabled');");
			   editCallBack.append("$('#startDate').attr('disabled', 'disabled');");
			   editCallBack.append("$('#endDate').attr('disabled', 'disabled');");
			   editCallBack.append("disableLinkById('#employeeSearch');");
			   editCallBack.append("disableLinkById('#orderSearch');");
			}
			
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append("if(overlap == true){flag = flag + 1;}");
			submitAdditionalCallback.append("if(($('#employStatus').val() == '0' || $('#employStatus').val() == '1') && $('#resignDate').val() != ''){addError('employStatus', '��Ӷ״̬����ȷ��');flag = flag + 1;}");
			submitAdditionalCallback.append("if($('#employStatus').val() != '1'){flag = flag + validate('resignDate', true, 'common', 100, 0);}");
		%>
		
		<%= ManageRender.generateManageJS( request, EmployeeContractAction.getAccessAction( request, response ), initCallBack.toString(), editCallBack.toString(), null,submitAdditionalCallback.toString()) %>

		// ��Ӹ�����Ϣ����
		$('#HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_CG13').html('<ol class="auto"><li><label style="width: 280px;">��<logic:equal name="role" value="1">����</logic:equal><logic:equal name="role" value="2">�������</logic:equal>���趨���μ�<logic:equal name="role" value="1">����</logic:equal><logic:equal name="role" value="2">�������</logic:equal>�����趨��</label></li></ol>' + $('#HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_CG13').html());
		
		
		// ����Ǵ���˻��߽���״̬�����ر༭��ť
		if( $('#status').val() == '2' || $('#status').val() == '7'){
			$('#btnEdit').hide();
		}
		
		// ������½������˻�״̬����ʾ ���ύ����ť
		if( ($('#status').val() == '1' && getSubAction() != 'createObject') || $('#status').val() == '4' ){
			$('#btnSubmit').show();
		}
		
		if( $('#status').val() == '3' || $('#status').val() == '5' || $('#status').val() == '6' ){
			<kan:auth right="modify" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
				$("#btnSubmit").before("<input type='button' name='btnTransfer' id='btnTransfer' value='ת��'>");
			</kan:auth>
		}
			
		$('#btnTransfer').click( function(){
			$('#btnSubmit').show();
			$('#btnEdit').hide();
			$(this).hide();
			$('#orderId').removeAttr('disabled');
			$('#orderId').addClass('important');
			$('#subAction').val("modifyObject");
			enableLinkById('#orderSearch');
		});
		
		// ҳ���ʼ�� - ��ʾ����һ������ť
		$('#btnNext').show();
		$('#btnNext').click( function () { generateContract(); } );
		
		// ��׼�����º͹鵵��������ύ����������桱
		$('#btnEdit').click( function(){
			if( $('#status').val() == '3' || $('#status').val() == '5' || $('#status').val() == '6' ){
				$('#btnSubmit').show();
				$('#btnEdit').hide();
			}
		});
		
		$('#btnSubmit').click( function(){
			if(validate_manage_primary_form() == 0){
				// ��֤��ͬģ����߸�������Ϊ��
				if($('#templateId').val() == 0 && $('#attachmentsOL #attachmentArray').size() == 0){
					alert("ȱ�ٺ�ͬģ����߸�����");
					return false;
				}	
				
				// ҳ���޸����ݱ�������
				if(getSubAction() == 'modifyObject' && !confirm("ҳ���޸����ݽ������棡")){
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
		
		//	������ ����ť�¼��������ֻ�򿪺�ͬ�������ڣ�Ȼ������ύ��
		$('#btnRenew').click( function(){
			$('#endDate').removeAttr('disabled');
			$('#btnEdit').hide();
			
			if('renewObject' != $('#subAction').val()){
				$('#btnRenew').val('�ύ');
				$('#subAction').val('renewObject');
			}else{
				if(validate_manage_primary_form() == 0){
					$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=renew_object');
					$('.manage_primary_form input#subAction').val("renewObject"); 
					// Enable the form
					enableForm('manage_primary_form');
					submit('manage_primary_form');
				}
			}
			
		});
		
		// ����������ӡ��鿴��Ա��Ϣ�������鿴������Ϣ��
		if(getSubAction() != 'createObject'){
			if( $('#rePageFlag_orderId').val() == 'false')
			{
				$('#orderIdLI label:eq(0)').append(' <a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedOrderId" />\');" ><img src="images/find.png" title="�鿴������¼" /></a>');
			}
			
			if( $('#rePageFlag_employeeId').val() == 'false')
			{
				$('#employeeIdLI label:eq(0)').append(' <a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="�鿴��Ա��¼" /></a>');
			}
			
			loadServiceContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
		}
		
		// Load Special Info
		loadHtml('#special_info', 'employeeContractAction.do?proc=list_special_info_html&flag=2&contractId=' + $('#id').val(), getSubAction() == 'viewObject');
		
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="employeeContractForm" property="workflowId"/>');

		// ��ӡ�������Ա��Ϣ��
		$('#employeeId').after('<a id="employeeSearch" onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="������Ա��¼" /></a>');
		
		// ��ӡ�����������Ϣ��
		$('#orderId').after('<a id="orderSearch" onclick="popupOrderSearch();" class="kanhandle"><img src="images/search.png" title="����������¼" /></a>');
		
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
							
							if($('#clientId').val() != ''){
								// �Զ����ɷ���Э������
								var date = new Date();
								var contractNameZH = $('#businessTypeId option:selected').text() + 'Э�飨' + $('#employeeNameZH').val() + '�� - ' + date.getFullYear();
								var contractNameEN = '';
								
								if($('#employeeNameEN').val().trim().length == 0){
									contractNameEN = 'Service Agreement ' + date.getFullYear();
								}
								else{
									contractNameEN = 'Service Agreement (' + $('#employeeNameEN').val() + ') - ' + date.getFullYear();
								}
								
								if(date.getMonth() + 1 < 10){
									contractNameZH = contractNameZH + "0";
									contractNameEN = contractNameEN + "0";
								}
								
								contractNameZH = contractNameZH + eval(date.getMonth() + 1);
								contractNameEN = contractNameEN + eval(date.getMonth() + 1);
								
								if($('#nameZH').val() == ''){
									$('#nameZH').val(contractNameZH);
								}
								
								if($('#nameEN').val() == ''){
									$('#nameEN').val(contractNameEN);
								}
								
								// ��ʼ������ͬ������
								loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
							}
						}
						else if(data.success == 'false'){
							$('#encodedEmployeeId').val('');
							$('#employeeId').val('');
							$('#employeeNameZH').val('');
							$('#employeeNameEN').val('');
							addError('employeeId', data.errorMsg);
						}
					}
				});
			}
		});
		
		$("#employeeId").blur(function(){
			if($('#employeeId').val().length < 9){
				cleanError('employeeId');
				addError('employeeId', '��ԱID��Ч��');
			}else{
				cleanError('employeeId');
			}
		});
		
		// ����ID�����¼�
		$("#orderId").bind('keyup', function(){
			if(getSubAction() == "viewObject"){
				return;
			}
			
			if($("#orderId").val().length >= 9){
				$.ajax({url: 'clientOrderHeaderAction.do?proc=get_object_json&orderHeaderId=' + $(this).val() + '&date=' + new Date(),
					dataType : 'json',
					success: function(data){
						var date = new Date();
						cleanError('orderId');
						
						if(data.success == 'true'){
							$('#encodedOrderId').val(data.encodedId);
							$('#clientId').val(data.clientId);
							$('#encodedClientId').val(data.encodedClientId);
							$('#clientNameZH').val(data.clientNameZH);
							$('#clientNameEN').val(data.clientNameEN);
							
							// ����ʱ���жϲ�����
							if(data.endDate < $("#endDate").val()){
								$("#endDate").val(data.endDate);
							}
							
							$('#startDate').attr('onFocus',"WdatePicker({minDate:'" + data.startDate + "',maxDate:'#F{ $dp.$D(\\\'endDate\\\') || $dp.$DV(\\\'" + data.endDate + "\\\') }',onpicked:checkContractConflict})" );
							$('#endDate').attr('onFocus',"WdatePicker({maxDate:'" + data.endDate + "',minDate:'#F{ $dp.$D(\\\'startDate\\\') || $dp.$DV(\\\'"+data.startDate+"\\\') }',onpicked:checkContractConflict})" );
							$('#entityId').val(data.entityId);
							$('#businessTypeId').val(data.businessTypeId);
							
							//�������ţ�������
							$("#branch").val(data.branch);
							branchChange('branch', null, data.owner, 'owner');	
							
							if($('#employeeId').val() != ''){
								// �Զ����ɷ���Э������
								var contractNameZH = $('#businessTypeId option:selected').text() + 'Э�飨' + $('#employeeNameZH').val() + '�� - ' + date.getFullYear();
								var contractNameEN = '';
								
								if($('#employeeNameEN').val().trim().length == 0){
									contractNameEN = 'Service Agreement ' + date.getFullYear();
								}
								else{
									contractNameEN = 'Service Agreement (' + $('#employeeNameEN').val() + ') - ' + date.getFullYear();
								}
								
								if(date.getMonth() + 1 < 10){
									contractNameZH = contractNameZH + "0";
									contractNameEN = contractNameEN + "0";
								}
								
								contractNameZH = contractNameZH + eval(date.getMonth() + 1);
								contractNameEN = contractNameEN + eval(date.getMonth() + 1);
								
								if($('#nameZH').val() == ''){
									$('#nameZH').val(contractNameZH);
								}
								
								if($('#nameEN').val() == ''){
									$('#nameEN').val(contractNameEN);
								}
								
								// ��ʼ������ͬ������
								loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
							}
							
							// ��ʼ����ͬģ��������
							loadServiceContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');	
							
							// ��ʼ��ֱ�߾���
							loadLineManagerOptions('<bean:write name="employeeContractForm" property="lineManagerId"/>');
						}else if(data.success == 'false'){
							var startDate = date.getFullYear();
							
							if(eval(date.getMonth() + 1) < 10){
								startDate = startDate + '-0' + eval(date.getMonth() + 1);
							}else{
								startDate = startDate + '-' + eval(date.getMonth() + 1);
							}
							
							if(date.getDate() < 10){
								startDate = startDate + '-0' + date.getDate();
							}else{
								startDate = startDate + '-' + date.getDate();
							}
							
							date.setFullYear(date.getFullYear() + 3, date.getMonth(), date.getDate() - 1);
							
							var endDate = date.getFullYear();
							
							if(eval(date.getMonth() + 1) < 10){
								endDate = endDate + '-0' + eval(date.getMonth() + 1);
							}else{
								endDate = endDate + '-' + eval(date.getMonth() + 1);
							}
							
							if(date.getDate() < 10){
								endDate = endDate + '-0' + date.getDate();
							}else{
								endDate = endDate + '-' + date.getDate();
							}
							
							$('#encodedOrderId').val('');
							$('#orderId').val('');
							$('#encodedClientId').val('');
							$('#clientId').val('');
							$('#clientNameZH').val('');
							$('#clientNameEN').val('');
							$('#startDate').val(startDate);
							$('#endDate').val(endDate);
							$('#entityId').val('0');
							$('#businessTypeId').val('0');
							addError('orderId', '����ID��Ч��');
						}
					}
				});
			}
		});
		
		$("#orderId").blur(function(){
			if($('#orderId').val().length < 9){
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
			loadServiceContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
			loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
		});
		
		//	��ҵ������change�¼�
		$('#businessTypeId').change(function (){
			loadServiceContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
		});
		
		// ���б�ť����¼�
		$('#btnList').click( function () {
			if (agreest())
			link('employeeContractAction.do?proc=list_object&flag=2');
		});

		if(<%=request.getAttribute("author_new")%> !=null && !<%=request.getAttribute("author_new")%>){
			$("#reAdd").css("display","none");
		}

		// ��ӡ�������Ϣ����ť
		if($('#contractId').val()){
			<kan:auth right="new" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
				$('#btnList').after('<a name="copyInfo" title="������Ϣ" class="commonTools" id="copyInfo" onclick="if(confirm(\'�Ƿ�ȷ�����Ʋ�������ͬ ������Э�顱��\')){link(\'employeeContractAction.do?proc=copy_object&contractId=<bean:write name="employeeContractForm" property="encodedId"/>&com.kan.token=' + $("#com.kan.token").val() + '\');}"><u>����</u></a>');
			</kan:auth>
		};
		
		//��Ա��½��������һЩ��ť
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_MANAGE")%>
		<%= EmployeeUserManageRender.generateDisabledJS( request, "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_MANAGE")%>
		loadLineManagerOptions('<bean:write name="employeeContractForm" property="lineManagerId"/>');
	})(jQuery);
	
	// ��������ͬ�������¼�
	function loadContractOptions(masterContractId){
		loadHtml('#masterContractId', 'employeeContractAction.do?proc=list_object_options_ajax&contractId=' + masterContractId + '&employeeId=' + $('#employeeId').val()+ '&entityId=' + $('#entityId').val(), getSubAction() == 'viewObject' );
	};
	
	// ���غ�ͬģ���������¼�
	function loadServiceContractTemplateOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val() + '&clientIdPage='+$('#clientId').val()+'&contractTypeId=1', getSubAction() == 'viewObject' );
	};	
	
	// ����ֱ�߾����������¼�
	function loadLineManagerOptions(lineManagerId){
		loadHtml('#lineManagerId', 'clientContactAction.do?proc=list_object_options_ajax&contactId=' + lineManagerId + '&clientId=' + $('#encodedClientId').val(), getSubAction() == 'viewObject' );
	};

	function generateContract(){
		if(validate_manage_primary_form() == 0){
			// ��֤��ͬģ����߸�������Ϊ��
			if($('#templateId').val()==0 && $('#attachmentsOL #attachmentArray').size()==0){
				alert("ȱ�ٺ�ͬģ����߸�����");
				return false;
			}	
			
			// ҳ���޸����ݱ�������
			if(getSubAction() == 'modifyObject' && !confirm("ҳ���޸����ݽ������棡")){
				return false;
			}
			
			// Update the form action
			$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=generate_contract');
			
			// Enable the form
			enableForm('manage_primary_form');
			submit('manage_primary_form');
		};
	};
	
	/**
	function init(){
		// ��ʼ��Ӫ�ղ���
		loadSettlementBranch('<bean:write name="employeeContractForm" property="employeeId"/>','<bean:write name="employeeContractForm" property="settlementBranch"/>');
		replacePleaseSelects();
	};
	
	function loadSettlementBranch(employeeId,settlementBranch){
		$.post("branchAction.do?proc=getSettlementBranchOptions",{"employeeId":employeeId,"settlementBranch":settlementBranch},function(data){
			jQuery(data).appendTo("#settlementBranch");
		},"text");
	};
	*/
	
	function replacePleaseSelects(){
		//1. ����,2. �Ű�,3. ���ڷ�ʽ,4. �������,5. ����������,
		 <%
		 	if("1".equals(BaseAction.getRole(request,null))){
		 	   %>
		 	  $("#calendarId option[value='0']").remove();
		 	 	$("#shiftId option[value='0']").remove();
		 	 	$("#attendanceCheckType option[value='0']").remove();
		 	 	$("#probationMonth option[value='0']").remove();
		 	 	$("#approveType option[value='0']").remove();
		 	 	$("#calendarId").prepend("<option value='0'>���ն�������</option>");
		 	 	$("#shiftId").prepend("<option value='0'>���ն�������</option>");
		 	 	$("#attendanceCheckType").prepend("<option value='0'>���ն�������</option>");
		 	 	$("#probationMonth").prepend("<option value='0'>���ն�������</option>");
		 	 	$("#approveType").prepend("<option value='0'>���ն�������</option>");
		 	   <%
		 	}else{
		 	  %>
		 	 	$("#calendarId option[value='0']").remove();
		 	 	$("#shiftId option[value='0']").remove();
		 	 	$("#attendanceCheckType option[value='0']").remove();
		 	 	$("#probationMonth option[value='0']").remove();
		 	 	$("#calendarId").prepend("<option value='0'>���ս������</option>");
		 	 	$("#shiftId").prepend("<option value='0'>���ս������</option>");
		 	 	$("#attendanceCheckType").prepend("<option value='0'>���ս������</option>");
		 	 	$("#probationMonth").prepend("<option value='0'>���ս������</option>");
		 	   <%
		 	}
		 %>
		 //6. н�깩Ӧ��,7. ��˰������8. ��˰˰�ʣ�
		//9. ���ٹ��ʣ�10. �Ӱ���Ҫ���룬11. �����ռӰ����棬12. ��Ϣ�ռӰ�������13. �ڼ��ռӰ�������
		//��java����
	}
</script>
				
