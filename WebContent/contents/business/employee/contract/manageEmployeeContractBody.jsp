<%@	page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, EmployeeContractAction.accessActionLabor, "employeeContractForm", true ) %>
	<% final EmployeeContractVO employeeContractVO = (EmployeeContractVO)request.getAttribute("employeeContractForm"); %>
	<input id="comeFrom" name="comeFrom" type="hidden" value="<%=(request.getParameter("comeFrom")==null||request.getParameter("comeFrom").isEmpty())?"":request.getParameter("comeFrom")%>"/>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
</div>	

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/calendar.jsp"></jsp:include>
</div>	

<div id="workflowPopupWrapper"></div>
<%-- �Ƿ��������������ҳ�� --%>
<input id="rePageFlag_orderId" name="rePageFlag_orderId" type="hidden" value="false"/>
<input id="rePageFlag_employeeId" name="rePageFlag_employeeId" type="hidden" value="false"/>

<script type="text/javascript">
	(function($) {	
		// JS of the List - �ύʱ��Status Disabled ��ΪFalse
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
			
			final StringBuffer editCallBack = new StringBuffer();
			
			if(employeeContractVO != null && employeeContractVO.getStatus().equals("1")) {
				editCallBack.append("$('#employeeId').addClass('important');");
			}
			editCallBack.append("$('#uploadAttachment').show();");
			editCallBack.append("$('#employeeId').attr('disabled', 'disabled');");
			editCallBack.append("$('#employeeNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#employeeNameEN').attr('disabled', 'disabled');");
			editCallBack.append("$('#status').attr('disabled', 'disabled');");
			
			if(employeeContractVO != null && (employeeContractVO.getStatus().equals("3") || employeeContractVO.getStatus().equals("6")))
			{
			   editCallBack.append("$('#contractNo').attr('disabled', 'disabled');");
			}
			
			if(employeeContractVO != null && (employeeContractVO.getStatus().equals("3") || employeeContractVO.getStatus().equals("5") || employeeContractVO.getStatus().equals("6")))
			{
			   editCallBack.append("$('#orderId').attr('disabled', 'disabled');");
			   editCallBack.append("$('#nameZH').attr('disabled', 'disabled');");
			   editCallBack.append("$('#nameEN').attr('disabled', 'disabled');");
			   editCallBack.append("$('#templateId').attr('disabled', 'disabled');");
			   editCallBack.append("$('#startDate').attr('disabled', 'disabled');");
			   editCallBack.append("$('#endDate').attr('disabled', 'disabled');");
// 			   editCallBack.append("$('#branch').attr('disabled', 'disabled');");
			   editCallBack.append("$('#entityId').attr('disabled', 'disabled');");
			   editCallBack.append("disableLinkById('#employeeSearch');");
			}
			
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append("if(flag == 0){$('#status').attr('disabled', false);}");
			submitAdditionalCallback.append("if(checkContractConflict() == true){flag = flag + 1;}");
			
		%>
		<%= ManageRender.generateManageJS(request, EmployeeContractAction.accessActionLabor, initCallBack.toString(), editCallBack.toString(), submitAdditionalCallback.toString(),null) %>
		// ��ӡ����ٲ鿴������
		$('#calendarId').after('&nbsp;&nbsp;<a class="kanhandle" onclick="quickCalendarPopup(\'<bean:write name="employeeContractForm" property="encodedCalendarId" />\');"><img src="images/search.png" title="���ٲ鿴����" /></a>');
		/**
       	* define  ������� 
       	**/
		// ��ӡ�������Ϣ��
		$('#clientName').after('<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="�����ͻ���¼" /></a>');
		
		// ������ģʽ��ӡ��鿴�ͻ���Ϣ��������������ӡ��鿴��Ա��Ϣ�������鿴������Ϣ��
		if(getSubAction() != 'createObject'){
			$('#clientIdLI label').append(' <a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedClientId" />\');" ><img src="images/find.png" title="�鿴�ͻ���¼" /></a>');
			
			if( $('#rePageFlag_orderId').val() == 'false')
			{
				$('#orderIdLI label').append(' <a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedOrderId" />\');" ><img src="images/find.png" title="�鿴������¼" /></a>');
			}
			
			if( $('#rePageFlag_employeeId').val() == 'false')
			{
				$('#employeeIdLI label').append(' <a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="�鿴��Ա��¼" /></a>');
			}
			
		}
		
		var disable = true;
		if($('.subAction').val() == 'createObject'){
			disable = false;
		};
		
		var disable = true;
		if($('.subAction').val() == 'createObject'){
			disable = false;
		};
		
       /**
       * loadHtml ����ҳ��
       **/

        loadHtml('#special_info', 'employeeContractAction.do?proc=list_special_info_html&contractId=' + $('#id').val(),disable);
       
    	// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// ��ʼ������ͬ������
		loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
		
		// ��ʼ�����غ�ͬģ��������
		loadLaborContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
		
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="employeeContractForm" property="workflowId"/>');
       /**
       * bind ���¼�
       **/
		
		$('#btnNext').click( function () { generateContract(); } );

		// ���»�鵵��ť
		if($('#status').val()=='3'){
			$('#btnSealed').show().click(function(){
				$('#status').val('5');
				enableForm('manage_primary_form');
				$('.manage_primary_form').attr('action','employeeContractAction.do?proc=modify_object');
				$('.manage_primary_form').submit();
			});
		}else if($('#status').val()=='5'){
			$('#btnArchive').val('��д�鵵���').show().click(function(){
				if(this.value=='��д�鵵���'){
					$(this).val('�鵵');
					$('#contractNo').attr('disabled',false).focus();
				}else{
					$('#status').val('6');
					enableForm('manage_primary_form');
					$('.manage_primary_form').attr('action','employeeContractAction.do?proc=modify_object');
					$('.manage_primary_form').submit();
				}
			});
		}
		
		// ���б�ť����¼�
		$('#btnList').click( function () {
			if (agreest())
			link('employeeContractAction.do?proc=list_object');
		});
		
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
		$('#businessTypeId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
				// �Զ����ɷ���Э������
				if($('#businessTypeId').val() != '' && $('#businessTypeId').val() != null){
				var date = new Date();
				var contractNameZH = $('#businessTypeId option:selected').text() + '�Ͷ���ͬ��' + $('#employeeNameZH').val() + '�� - ' + date.getFullYear();
				var contractNameEN = $('#businessTypeId option:selected').text() + 'Labor Contract(' + $('#employeeNameEN').val() + ') - ' + date.getFullYear();
				if($('#employeeNameEN').val().trim().length==0){
					contractNameEN = 'Labor Contract  ' + date.getFullYear();
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
			}
		});
		
		// ҳ���ʼ�� - ��ʾ ���ύ����ť ���״̬Ϊ�½��������˻�
		if("<bean:write name='employeeContractForm' property='status' />" == 1||"<bean:write name='employeeContractForm' property='status' />" == 4){
			$('#btnSubmit').show();
		}
		$('#btnSubmit').click( function(){
			enableForm('manage_primary_form'); 
			// ������½�������ύ��add������������޸ĵ���ύ��modify����
			if(getSubAction() == 'viewObject'){
				// ������޸�
				if("<bean:write name='employeeContractForm' property='status' />" == 1||"<bean:write name='employeeContractForm' property='status' />" == 4){
					$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=modify_object&flag=1');
				}
			}else{
				// �½�
				$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=add_object&flag=1');
			}
			$('.manage_primary_form input#subAction').val("submitObject"); 
			$('#btnEdit').click();
		} );
		
       /***
       * init ��ʼ�����JS
       **/
		
		//�������ʵ�������ѡ��ֻ��һ����Ĭ��ѡ��
	    var entityIdLength = $('#entityId option').length;
		if(entityIdLength==2 && $('#entityId option')[0].value==0){
			$("#entityId option:nth-child(2)").attr("selected" , "selected");  
			
		}
		
		//����Ű�����ѡ��ֻ��һ����Ĭ��ѡ��
	    var shiftIdLength = $('#shiftId option').length;
		if( shiftIdLength==2 && $('#shiftId option')[0].value==0){
			$("#shiftId option:nth-child(2)").attr("selected" , "selected");  
		}
       
		// ��ʼ��ʱ״̬���ɸı�
		$('#status').attr('disabled',true);
		$('#employeeNameZH').attr('disabled',true);
		$('#employeeNameEN').attr('disabled',true);
		
		// ��ʼ������Ϊreadonly
		$('#startDate,#endDate').attr("readonly","readonly");
		
		// ��ʼ����<input type="hidden" name="comeFrom"���뵽from��
		$('.manage_primary_form').append($('#comeFrom'));
		
		// ��ʾ����һ������ť
		$('#btnNext').show();
		
		// ����Ǵ���˻��߽���״̬�����ر༭��ť
		if( $('#status').val() == '2' || $('#status').val() == '7'){
			$('#btnEdit').hide();
		}
		
		// ��ӡ�������Ա��Ϣ��
		$('#employeeId').after('<a onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="������Ա��¼" /></a>');
		
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
							if($('#businessTypeId').val() != '0'){
								// �Զ����ɷ���Э������
								var date = new Date();
								var contractNameZH = $('#businessTypeId option:selected').text() + '�Ͷ���ͬ��' + $('#employeeNameZH').val() + '�� - ' + date.getFullYear();
								var contractNameEN = $('#businessTypeId option:selected').text() + '�Ͷ���ͬ��' + $('#employeeNameEN').val() + '�� - ' + date.getFullYear();
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
						}else if(data.success == 'false'){
							$('#encodedEmployeeId').val('');
							//$('#employeeId').val('');
							$('#employeeNameZH').val('');
							$('#employeeNameEN').val('');
							addError('employeeId', data.errorMsg );
						}
					}
				});
			}
		});
		$("#employeeId").blur(function(){
			if($('#employeeId').val().length<9){
				cleanError('employeeId');
				addError('employeeId', '��ԱID��Ч��');
			}else{
				cleanError('employeeId');
			}
		});
		
		// �Ͷ���ͬ��ʼʱ���Э�����ʱ�䣬ʧȥ�����¼�   ��֤��ʱ������Ƿ��Ѵ����Ͷ���ͬ
		$('#startDate,#endDate').addClass('Wdate');
		$('#startDate').focus(function(){
			WdatePicker({
						maxDate:'#F{$dp.$D(\'endDate\')}',
						onpicked :checkContractConflict
			});
		});
		$('#endDate').focus(function(){
			WdatePicker({
						minDate:'#F{$dp.$D(\'startDate\')}',
						onpicked:checkContractConflict
			});
		});
		
		function checkContractConflict(){
			var overlap = false;
			$.ajax({
				url : 'employeeContractAction.do?proc=checkContractConflict&flag=1&clientId='+$('#clientId').val()+'&employeeId='+$('#employeeId').val()+'&startDate='+$('#startDate').val()+'&contractId='+$('#contractId').val()+'&endDate='+$('#endDate').val()+'&entityId='+$('#entityId').val()+'&orderId='+$('#orderId').val(), 
				type : 'POST',
				async : false,
				success : function(data){
					if(data=='1'){
						cleanError('startDate');
						addError('startDate', '��ʱ�����ͬ����ʵ���Ѵ����Ͷ���ͬ��');
						overlap = true;
					}else{
						cleanError('startDate');
						overlap = false;
					}
				}
			});
			return overlap;
		};
		
		// �鿴ģʽ �������
		if(getSubAction() == 'viewObject'){
			$('#uploadAttachment').attr("disabled", true);
			$('#uploadAttachment').addClass("disabled");
		};

	})(jQuery);

	// To Next Page - Generate Contract
	function generateContract(){
		// Update the form action
		$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=generate_contract');
		var flag = 0;
		flag += validate_manage_primary_form();
		if(flag == 0){
			// Enable the form
			enableForm('manage_primary_form');			
			$('#status').attr('disabled', false);
			submit('manage_primary_form');
		};
	}
	
	//	��֤��ͬģ���¼�
	function checkContractTemplate(){
		
		//	���ģ��ID
		var templateID = $('#templateId').val();
		//	����û��ϴ�����
		var attachmentLength = $('#attachmentsOL > li').length;
		
		if( templateID == 0 && attachmentLength == 0 )
		{
			return true;
		}else{
			return false;		
		};
		
	};
	
	// ��������ͬ�������¼�
	function loadContractOptions(masterContractId){
		loadHtml('#masterContractId', 'employeeContractAction.do?proc=list_object_options_ajax&contractId=' + masterContractId + '&employeeId=' + $('#employeeId').val()+ '&entityId=' + $('#entityId').val(), false );
	};
	
	
	// ���غ�ͬģ���������¼�
	function loadLaborContractTemplateOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val()+'&clientIdPage='+$('#clientId').val()+'&contractTypeId=1', false );
	};
	
	// ����ѡ�еĿͻ����ƣ����ͻ����Ͷ���ͬ��������Ӣ����
	function thinkingEmployeeNameCallBack(event,item){
		if(getSubAction() != 'viewObject'){// ��ת��VIEWҳ��ʱ����Ҫ�����Ͷ���ͬ���������������Ӣ������
			var employeeNameArray = item['employeeName'].split('-');
			var year = new Date().getYear()+1900;
			$('#nameZH').val('�Ͷ���ͬ '+employeeNameArray[0]+'��'+year+'��');
			$('#nameEN').val('Labor Contract '+employeeNameArray[1]+'('+year+')');
			
			loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
		}
	}
</script>

