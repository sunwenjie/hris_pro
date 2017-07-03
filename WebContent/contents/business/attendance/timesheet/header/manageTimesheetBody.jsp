<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetBatchAction"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<%
	final boolean isHRFunction = BaseAction.isHRFunction( request, null );
	request.setAttribute( "isHRFunction", isHRFunction ? "1" : "2" );
%>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	         <label id="pageTitle"><bean:message bundle="business" key="business.ts" /></label>
	         <label class="recordId"> &nbsp; (ID: <bean:write name="timesheetHeaderForm" property="headerId" />)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message success fadable">
						<bean:write name="MESSAGE" />
			    		<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<!-- HRService -->
				<logic:equal name="role" value="1">
					<kan:auth right="modify" action="<%=TimesheetBatchAction.accessActionInHouse%>">
						<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
					<kan:auth right="submit" action="<%=TimesheetBatchAction.accessActionInHouse%>">
						<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;"/> 
					</kan:auth>	
				</logic:equal>
				
				<!-- InHouse -->
				<logic:equal name="role" value="2">
					<logic:equal name="isHRFunction" value="1">
						<kan:auth right="modify" action="<%=TimesheetBatchAction.accessActionInHouse%>">
							<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
						</kan:auth>
						<kan:auth right="submit" action="<%=TimesheetBatchAction.accessActionInHouse%>">
							<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;"/> 
						</kan:auth>	
					</logic:equal>
				</logic:equal>
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.back.fh" />">
		    </div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/business/attendance/timesheet/header/form/manageTimesheetHeaderForm.jsp" flush="true"></jsp:include>	
	    </div>
	</div>
</div>

<!-- popup box -->
<div id="popupWrapperAllowance">
	<jsp:include page="/popup/manageTimesheetAllowance.jsp"></jsp:include>
</div>	


<script type="text/javascript">
	(function($) {
		// ��ʼ���˵���ʽ
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Timesheet').addClass('selected');
		
		// ���½�����£���ӿ��ٲ���
		if( getSubAction() != 'createObject' ){
			$('#employeeIdLI label').append('&nbsp;<a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			$('#clientIdLI label').append('&nbsp;<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderForm" property="encodedClientId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			$('#orderIdLI label').append('&nbsp;<a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderForm" property="encodedOrderId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			$('#contractIdLI label').append('&nbsp;<a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderForm" property="encodedContractId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
		}
		
		if ($('.manageTimesheetHeader_form input#subAction').val() != 'createObject') {
		    disableForm('manageTimesheetHeader_form');
		    $('.manageTimesheetHeader_form input.subAction').val('viewObject');
		    $('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		} else if ($('.manageTimesheetHeader_form input#subAction').val() == 'createObject') {
		    $('#decodeModifyBy').attr('disabled', 'disabled');
		    $('#decodeModifyDate').attr('disabled', 'disabled');
		    $('#decodeModifyDate').val('');
		}
		
		$('#btnList').click(function() {
			if (agreest())
				link('timesheetHeaderAction.do?proc=list_object&batchId=<bean:write name="timesheetHeaderForm" property="encodedBatchId" />');
		});
		
		$('#btnEdit').click(function() {
			$('#btnSubmit').hide();
		    if ( getSubAction() == 'viewObject') {
		    	//changeTab(2,2);
		        <logic:notEmpty name="byHour">	
		    		// ���н�귽���Ļ��������ǰ�Сʱ�㣬����Сʱ�����ܣ��ɱ༭
					$('#totalWorkHours').removeAttr('disabled');
					// ����Сʱ���ɱ༭
					$('.manageTimesheetHeader_form input[id^="workHoursArray_"').removeAttr('disabled');
				</logic:notEmpty>
				// ��ؿؼ��ɱ༭
				$('.manageTimesheetHeader_form select[id^="dayTypeArray_"').removeAttr('disabled');
				$('.manageTimesheetHeader_form input[id^="workHoursArray_"').removeAttr('disabled');
				$('.manageTimesheetHeader_description').removeAttr('disabled');
				
				if($('#append_info').html().trim()==''){
					$('.manageTimesheetHeader_totalWorkHours').removeAttr('disabled');
					$('.manageTimesheetHeader_totalWorkDays').removeAttr('disabled');
					$('.manageTimesheetHeader_totalFullHours').removeAttr('disabled');
					$('.manageTimesheetHeader_totalFullDays').removeAttr('disabled');
				}
				
				// ���ɱ༭
				$('.manageTimesheetHeader_form input[id^="baseArray_"]').removeAttr('disabled');
				// ����subAction
		        $('.manageTimesheetHeader_form input#subAction').val('modifyObject');
		        $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		        $('.manageTimesheetHeader_form').attr('action', 'timesheetHeaderAction.do?proc=modify_object');
		     	// �������׼״̬���޸ģ����ر༭��ť
				if( status == '3' ){
					//$('#btnSubmit').show();
					//$('#btnSubmit').hide();
				}
		    } else {
		        var flag = 0;
		        flag = validate_manage_primary_form();
		        flag = flag + actual_validata_form();
		        if (flag == 0) {
		        	setValue();
		            enableForm('manageTimesheetHeader_form');
		            submit('manageTimesheetHeader_form');
		        }
		    }
		});
		
		/**
		* define  �������
		**/
		var id = '<bean:write name="timesheetHeaderForm" property="encodedId"/>';
		var disable = true;
		if($('.manage_primary_form input#subAction').val() == 'createObject'){
			disable = false;
		}
		
		var status = $('#status').val();
		if( status == '1' || status == '4'){
		 	$('#btnSubmit').show();	
		}else if( status == '2' || status == '3' || status == '5'  ){
			$('#btnEdit').hide(); 	    
		}
		
		// �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			// ��֤��
			if( validate_manage_primary_form() + actual_validata_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if( getSubAction() != 'createObject'){
					$('.manageTimesheetHeader_form').attr('action', 'timesheetHeaderAction.do?proc=modify_object');
				}
				$('.manageTimesheetHeader_form input#subAction').val('submitObject');
				setValue();
	    		enableForm('manageTimesheetHeader_form');
	    		submitForm('manageTimesheetHeader_form');
			}
		});
		
		// ������Ϣ����¼�
		$('#moreInfo_LINK').click(function(){
			if($('#moreInfo').is(':visible')){
				$('#moreInfo').hide();
			}else{
				$('#moreInfo').show();
			}
		});

		// ����Tabҳ��
		loadHtmlWithRecall( '#append_info', 'timesheetHeaderAction.do?proc=list_special_info_html&id=' + id, true , 'add_style();');
		
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="timesheetHeaderForm" property="workflowId"/>');
	})(jQuery);
	
	// ʵ�ʱ���֤JS
	function actual_validata_form(){
		var flag = 0;
		// ��֤�������� - ����Сʱ�������� - ����
		flag = flag + each_validate('workHoursArray') ;
		flag = flag + each_validate('baseArray');
		return flag;
	}
	
	function each_validate( id ){
		var flag = 0;
		$('.manageTimesheetHeader_form input[id^="' + id + '_"][type="text"]').each(function(i){
			$(this).removeClass('small-ex');
			$(this).removeClass('error');
			var className = $(this).attr('class');
			var maxRange = 0;
			if( id == 'workHoursArray' ){
				maxRange = 24;
			}
			flag = flag + validate( className, true, 'currency', 0, 0, maxRange, 0 );
		});
		return flag;
	};
	
	function setValue(){
		// ׷�ӹ���Сʱ�� - detail
		$('#append_info #resultTable input[id^="workHours_"]').each(function(){	
			var val = $(this).val()+ '_' + $(this).next('input').val();
			$(this).val(val);
		});
		// ׷�ӽ������  - allowance
		$('#append_info #resultTable input[id^="base_"]').each(function(){	
			var val = $(this).val()+ '_' + $(this).next('input').val();
			$(this).val(val);
		});
		// ׷���������� - detail 
		$('#append_info #resultTable input[id^="dayType_"]').each(function(){	
			var val = $(this).val()+ '_' + $(this).next('select').val();
			$(this).val(val);
		});
	};
	
	function add_style(){
		$('input[id^="dayType_"]').each(function(){
			if($(this).val() == '2' || $(this).val() == '3'){
				$(this).parents('tr').find('td').first().attr('style','color:red;'); 
				$(this).parents('tr').find('td select:visible,input:visible').attr('style','color:red;'); 
			}
		});
		$('#btnAddTimesheetAllowance').removeAttr('disabled');
		$('#btnAddTimesheetAllowance').click( function(){
			showPopup();
		});
	};
	
	// Link�������
	function to_leaveNew( timesheetId, employeeId, clientId, contractId, day){
		link('leaveHeaderAction.do?proc=to_objectNew_for_timesheet&timesheetId=' +timesheetId+ '&employeeId=' + employeeId + '&clientId=' + clientId + '&contractId=' + contractId + '&currDay=' + day);
	};
	
	// Link�Ӱ�����
	function to_otNew( timesheetId, employeeId, clientId, contractId, day ){
		link('otHeaderAction.do?proc=to_objectNew_for_timesheet&timesheetId=' +timesheetId+ '&employeeId=' + employeeId + '&clientId=' + clientId + '&contractId=' + contractId + '&currDay=' + day);
	};
	
	// �޸�ȥajax
	function to_timesheetAllowanceModify_ajax( allowanceId ){
		loadHtmlWithRecall('#popupWrapperAllowance', 'timesheetAllowanceAction.do?proc=to_objectModify_ajax&allowanceId=' + allowanceId, true, 'showPopup()');
	};
	
	// ��ȡ��ǰSubAction
	function getSubAction(){
		return $('.manageTimesheetHeader_form input#subAction').val();
	};
</script>
