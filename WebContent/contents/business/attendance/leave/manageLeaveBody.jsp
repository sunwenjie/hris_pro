<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.LeaveHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="business" key="business.leave" /></label>
	        <logic:notEmpty name="leaveHeaderForm" property="leaveHeaderId">
	       		 <label class="recordId"> &nbsp; (ID: <bean:write name="leaveHeaderForm" property="leaveHeaderId" />)</label>
	        </logic:notEmpty>
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
				<logic:empty name="leaveHeaderForm" property="encodedId">
					<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />"> 
				</logic:empty>
				<logic:notEmpty name="leaveHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=LeaveHeaderAction.accessAction%>">
						<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />"> 
					</kan:auth>
				</logic:notEmpty>
				<logic:notEqual name="role" value="5">
					<kan:auth right="sickleave" action="<%=LeaveHeaderAction.accessAction%>">
						<input type="button" class="function" name="btnSickLeave" id="btnSickLeave" value="<bean:message bundle="public" key="button.retrieve" />" style="display: none;">
					</kan:auth>
				</logic:notEqual>
				<kan:auth right="submit" action="<%=LeaveHeaderAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;"/> 
				</kan:auth>
				<kan:auth right="list" action="<%=LeaveHeaderAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />">
				</kan:auth>
				<kan:auth right="delete" action="<%=LeaveHeaderAction.accessAction%>">
					<logic:equal name="hasDeleteRight" value="true">
						<logic:equal name="username" value="Administrator">
							<input type="button" class="delete" name="btnDelete" id="btnDelete" value="<bean:message bundle="public" key="button.delete" />">
						</logic:equal>
					</logic:equal>
				</kan:auth>
		    </div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/business/attendance/leave/form/manageLeaveForm.jsp" flush="true"></jsp:include>		
	    </div>
	</div>
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
</div>	
							
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵���ʽ
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Leave').addClass('selected');
		
		
		<logic:empty name="pm_hide">
		// ������ģʽ����ӿ��ٲ���
		if( getSubAction() != 'createObject' ){
			$('#employeeIdLI label').append('&nbsp;<a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			$('#contractIdLI label').append('&nbsp;<a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderForm" property="encodedContractId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			$('#clientIdLI label').append('&nbsp;<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderForm" property="encodedClientId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
		}
		</logic:empty>
		
		// ��ӡ�������Ա��Ϣ��
		<logic:notEqual name="role" value="5">
			$('#employeeId').after('<a onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>');
		</logic:notEqual>
		<logic:equal name="role" value="5">
			$('#employeeId').attr('disabled', 'disabled');
			getEmployee();
	    </logic:equal>
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="leaveHeaderForm" property="workflowId"/>');
		
		
		if ( getSubAction() != 'createObject' ) {
		    disableForm('manageLeave_form');
		    $('.manageLeave_form input.subAction').val('viewObject');
		    $('#pageTitle').html('<bean:message bundle="business" key="business.leave" /> <bean:message bundle="public" key="oper.view" />');
		    $('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		} else if ( getSubAction() == 'createObject') {
		    <logic:notEqual name="role" value="5">
				$('#employeeId').addClass('important');
		    </logic:notEqual>
			$('#employeeNameZH').attr('disabled', 'disabled');
			$('#employeeNameEN').attr('disabled', 'disabled');
			$('#certificateNumber').attr('disabled', 'disabled');
			$('#employeeNo').attr('disabled', 'disabled');
			$('#contractStartDate').attr('disabled', 'disabled');
			$('#contractEndDate').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
		}
		
		<%
			// ��ԱID������Ϣ
			final String employeeIdErrorMsg = ( String )request.getAttribute( "employeeIdErrorMsg" );
			
			if( KANUtil.filterEmpty( employeeIdErrorMsg ) != null )
			{
			   out.print( "addError('manageLeave_employeeId', '" + employeeIdErrorMsg + "');" );
			}
		%>
		
		// ɾ����ټ�¼
		$('#btnDelete').click( function(){
			if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){
				link('leaveHeaderAction.do?proc=delete_leave&leaveHeaderId=<bean:write name="leaveHeaderForm" property="leaveHeaderId" />');
			}
		});
		
		$('#btnList').click(function() {
			if(agreest())
		    link('leaveHeaderAction.do?proc=list_object');
		});
		
		$('#btnEdit').click(function() {
		    if ( getSubAction() == 'viewObject') {
		    	
		    	if(getStatus() != '3'){
		         	enableForm('manageLeave_form');
		    	}else{
		    		$('#estimateStartDate').attr('disabled', 'disabled');
					$('#estimateEndDate').attr('disabled', 'disabled');
		    	}
		        <logic:equal name="role" value="5">
		    	 	$('#employeeId').attr('disabled', 'disabled');
				</logic:equal>
		        
				<logic:notEqual name="role" value="5">
				 	$('#employeeId').addClass('important');
				</logic:notEqual>
		        $('#uploadAttachment').show();
		        $('.manageLeave_form input#subAction').val('modifyObject');
		        $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		        $('.manageLeave_form').attr('action', 'leaveHeaderAction.do?proc=modify_object');
		        $('#pageTitle').html('<bean:message bundle="business" key="business.leave" /> <bean:message bundle="public" key="oper.edit" />');
		        $('li #warning_img').each(function() {
		            $(this).show();
		        });
		        $('li #disable_img').each(function() {
		            $(this).hide();
		        });
		        $('#decodeModifyBy').attr('disabled', 'disabled');
		        $('#decodeModifyDate').attr('disabled', 'disabled');
		        $('#employeeNameZH').attr('disabled', 'disabled');
		        $('#employeeNameEN').attr('disabled', 'disabled');
		        $('#certificateNumber').attr('disabled', 'disabled');
		        $('#employeeNo').attr('disabled', 'disabled');
		    	$('#contractStartDate').attr('disabled', 'disabled');
				$('#contractEndDate').attr('disabled', 'disabled');
		        $('#status').attr('disabled', 'disabled');
		    } else {
		    	
		        var flag = validate_manage_primary_form();
		        if (flag == 0) {
		        	useNextYearHours()
		        	enableForm('manageLeave_form');
		            submit('manageLeave_form');
		        }
		    }
		});
	    
	    if( getStatus() == '1' || getStatus() == '4' ){
			$('#btnSubmit').show();
		}else if( getStatus() == '2' || getStatus() == '5'){
			$('#btnEdit').hide();
		}
	    
	  	// ����tab
		loadHtml('#special_info', 'leaveHeaderAction.do?proc=list_special_info_html&leaveHeaderId=<bean:write name="leaveHeaderForm" property="leaveHeaderId"/>&noTab=false', getDisable(), null);
	    // �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			if( validate_manage_primary_form() == 0){
				if( getFlag() == false ){
					if(!confirm('<bean:message bundle="public" key="popup.leave.not.entry.ts" />')){
						return;
					}
				}
				// ���ĵ�ǰForm��SubAction
				if( getSubAction() != 'createObject'){
					$('.manageLeave_form').attr('action', 'leaveHeaderAction.do?proc=modify_object');
				}
				$('.manageLeave_form input#subAction').val('submitObject');
				useNextYearHours()	
		    	enableForm('manageLeave_form');
		    	submitForm('manageLeave_form');
			}
		});
	    
		// ���ٰ�ť�¼�
	  	$('#btnSickLeave').click( function () { 
	  		var sickLeaveBeforeHours = 0;
	  		if( '<bean:write name="leaveHeaderForm" property="estimateLegalHours" />' != ''){
	  			sickLeaveBeforeHours = parseFloat(sickLeaveBeforeHours) + parseFloat('<bean:write name="leaveHeaderForm" property="estimateLegalHours" />');
			}
			
			if( '<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />' != ''){
				sickLeaveBeforeHours = parseFloat(sickLeaveBeforeHours) + parseFloat('<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />');
			}
	  		var sickLeaveAfterHours = 0;
	  		if( $('#estimateLegalHours').val() != '' ){
	  			sickLeaveAfterHours = parseFloat(sickLeaveAfterHours) + parseFloat($('#estimateLegalHours').val()); 
	  		}
	  		if( $('#estimateBenefitHours').val() != '' ){
	  			sickLeaveAfterHours = parseFloat(sickLeaveAfterHours) + parseFloat($('#estimateBenefitHours').val()); 
	  		}
	  		
	  		var msg = '<bean:message bundle="public" key="popup.sick.leave.number" />';
	  		msg = msg.replace('X', (sickLeaveBeforeHours - sickLeaveAfterHours));
	  		if(!confirm(msg)){
	  			return;
	  		}
	  		
	  		if( validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if( getSubAction() != 'createObject'){
					$('.manageLeave_form').attr('action', 'leaveHeaderAction.do?proc=sick_leave');
				}
				$('.manageLeave_form input#subAction').val('SICK_LEAVE');
					
		    	enableForm('manageLeave_form');
		    	submitForm('manageLeave_form');
			}
		});
	     
		// �󶨿�ĿChange�¼�������/��ʾ��Ԥ�� ��������Сʱ�� 
		$('#itemId').change(function(){
			// change֮ǰ������ڿؼ��󶨵��¼�
			$('#leaveDateOL').html($('#leaveDateOL').clone().html());
			// ��ʼ���������
			cleanError('manageLeave_itemId');
			if($(this).val() != '0'){
				var leftHours = getLeftHours($(this).val());
				if( leftHours != -1 && getSubAction() != 'createObject'){
					if( '<bean:write name="leaveHeaderForm" property="estimateLegalHours" />' != ''){
						leftHours = parseFloat(leftHours) + parseFloat('<bean:write name="leaveHeaderForm" property="estimateLegalHours" />');
					}
					
					if( '<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />' != ''){
						leftHours = parseFloat(leftHours) + parseFloat('<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />');
					}
				}
				
				if( leftHours == -1 || leftHours > 0){
					$.ajax({
						url : "leaveHeaderAction.do?proc=item_change_ajax&contractId=" + $('#contractId').val() + "&itemId=" + $(this).val(), 
						dataType : "json",
						success : function(data){
							<logic:notEmpty name="currDay">
								focusStartDate_for_timesheet('<bean:write name="currDay" />','<bean:write name="nextDay" />');
							</logic:notEmpty>
							<logic:empty name="currDay">
							
								if( getStatus() == '3'){
									focusSickLeave('<bean:write name="leaveHeaderForm" property="estimateStartDate" />','<bean:write name="leaveHeaderForm" property="estimateEndDate" />');
								}else{
									focusStartDate(data.startDate, data.endDate, data.defDate);
									focusEndDate(data.tmpLeaveEndDate,data.endDate);
									//����ʱĬ�Ͽ�ʼ����ʱ��
									if( getSubAction() == 'createObject' && $('#estimateStartDate').val() ==''){
										if( data.defDate != null && data.defEndDate != null){
											$('#estimateStartDate').val(data.defDate.substring(0,16));
											if(data.endDate>data.defEndDate){
												$('#estimateEndDate').val(data.defEndDate.substring(0,16));
												//��ȡĬ��ֵ
												$.ajax({
													url:"leaveHeaderAction.do?proc=get_endDate_ajax&itemId=" + $('#itemId').val() + "&contractId=" + $('#contractId').val() + "&date=" + data.defDate + "&leftHours=" + getLeftHours($('#itemId').val()) + '&hours=0.0',
													success : function(html){
														if( html.trim() != '' ){
															focusEndDate(html,data.endDate);
														}else{
															addError('manageLeave_contractId', '<bean:message bundle="public" key="error.invalid.calendr.or.shift" />');
														}
													}
												});
												//��֤Ĭ��ֵ
												$.ajax({
													url : "leaveHeaderAction.do?proc=check_leave_date&leaveHeaderId=" + $('#leaveHeaderId').val()+ "&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val() + "&endDate=" +  $('#estimateEndDate').val() ,
													success : function(html){
														if(html == 'true'){
															if( !$('.manageLeave_estimateEndDate').hasClass('error') ){
																addError('manageLeave_estimateEndDate', '<bean:message bundle="public" key="error.time.period.exist.leave" />');
																$('#estimateBenefitHours').val('');
																$('#estimateLegalHours').val('');
															}
														}else{
															cleanError('manageLeave_estimateEndDate');
														}
													} 
												});
												
												calculateLeaveHours( $('#estimateStartDate').val(), $('#estimateEndDate').val() );
											}
										}
									}
								}
								if( !getStatus() == '3' && getSubAction() == 'viewObject'){
									<logic:notEmpty name="leaveEndDate">
										focusEndDate('<bean:write name="leaveEndDate" />',data.endDate);
									</logic:notEmpty>
								}
							</logic:empty>
						}
					});
				}else{
					$('#itemId').val() != '0' ? addError('manageLeave_itemId', '<bean:message bundle="public" key="error.leave.no.left.hours" />') : "";
				}
			}
			
			if($(this).val() == '41'){
				$('#estimateLegalHoursLI').show();
				$('#benefit_hours').html('<bean:message bundle="business" key="business.leave.benefit.hours" /><em> *</em>'); 
			}else if($(this).val() == '48'){
				$('#estimateLegalHoursLI').show();
				$('#estimateBenefitHoursLI').hide();
			}else if($(this).val() == '49'){
				$('#estimateLegalHoursLI').hide();
				$('#estimateBenefitHoursLI').show();
			}
			else{
				$('#estimateLegalHoursLI').hide();
				$('#estimateBenefitHoursLI').show();
				$('#benefit_hours').html('<bean:message bundle="business" key="business.leave.hours" /><em> *</em>');
			}
			
			if( getSubAction() != 'viewObject' ){
				$('#estimateStartDate').val('');
			    $('#estimateEndDate').val('');
			    $('#estimateBenefitHours').val('');
			    $('#estimateLegalHours').val('');
			}
		});
		
		// ��������ϢChange�¼���
		$('#contractId').change(function(){
			<logic:notEmpty name="currDay">
				$('.manageLeave_form #subAction').val('createObject');
			</logic:notEmpty>
			if( getStatus() == '3' && (getRetrieveStatus() == 1 || getRetrieveStatus() == 4) && '<bean:write name="leaveHeaderForm" property="itemId" />' != 60 ){
				$('#btnSickLeave').show();
				$('#estimateStartDate').removeAttr('disabled');
				$('#estimateEndDate').removeAttr('disabled');
			}
			// get_object_json
			$.ajax({
				url : "employeeContractAction.do?proc=get_object_json&contractId=" + $(this).val(), 
				dataType : "json", 
				success : function(data){
					if(data.success=='true'){
						$('#contractStartDate').val(data.startDate);
						$('#contractEndDate').val(data.endDate);
					}else{
						$('#contractStartDate').val('');
						$('#contractEndDate').val('');
					}
				}
			});
			// Load Item Options
			loadHtml('#itemId', 'employeeContractLeaveAction.do?proc=list_object_options_ajax&itemId=<bean:write name="leaveHeaderForm" property="itemId" />&contractId=' + $('#contractId').val(), getDisable(), 'if($(\'#itemId\').children().length==\'2\' && getSubAction() != \'viewObject\'){$(\'#itemId option:nth-child(2)\').attr(\'selected\', \'selected\');} cleanError(\'manageLeave_contractId\'); if($(\'#itemId\').children().length <= 1 && $(\'#contractId\').val() != \'0\'){ addError(\'manageLeave_contractId\', \'<bean:message bundle="public" key="error.no.leave.condition" />\');}');
			
			// Locad Special Info
			loadHtml('#special_info', 'leaveHeaderAction.do?proc=list_special_info_html&leaveHeaderId=<bean:write name="leaveHeaderForm" property="leaveHeaderId" />&contractId=' + $('#contractId').val() + '&noTab=false', false, '$(\'#itemId\').change();'); 
		});
		
		// �󶨿ͻ�Change�¼�������������Ϣ������ 
		$('#clientId').change(function(){
			loadHtml('#contractId', 'employeeContractAction.do?proc=list_object_options_ajax&flag=2&clientId=' + $(this).val() + '&employeeId=' + $('#employeeId').val() + '&contractId=<bean:write name="leaveHeaderForm" property="contractId" />', getDisable(), 'if($(\'#contractId\').children().length==\'2\' && getSubAction() != \'viewObject\'){$(\'#contractId option:nth-child(2)\').attr(\'selected\', \'selected\');}$(\'#contractId\').change();');
		});
	
		// �󶨹�ԱID KeyUp�¼������ع�Ա�����Ϣ��
		$('#employeeId').bind('keyup',function(){
			if( $("#employeeId").val().length >= 9 ){
				getEmployee();
			}
		});
	
		if(getSubAction() == 'viewObject'){
	     	$('#employeeId').trigger('keyup');
	    }
		
		<logic:equal name="role" value="2">
			if( getSubAction() == 'createObject' ){
				$('#employeeId').trigger('keyup');
			}
		</logic:equal>
		
		<%
			final String day = ( String )request.getAttribute( "currDay" );
			if( day != null && !"".equals( day ) )
			{
			   out.println( "if($('.manageLeave_form input#subAction').val() == 'createObject'){" );
			   out.println( "$('.manageLeave_form input#subAction').val('viewObject');" );
			   out.println( "$('#employeeId').trigger('keyup');" );
			   out.println( "$('#employeeId').attr('disabled', 'disabled');" );
			   out.println( "$('#employeeNo').attr('disabled', 'disabled');" );
			   out.println( "$('#certificateNumber').attr('disabled', 'disabled');" );
			   out.println( "$('#employeeNameZH').attr('disabled', 'disabled');" );
			   out.println( "$('#employeeNameEN').attr('disabled', 'disabled');" );
			   out.println( "$('#clientId').attr('disabled', 'disabled');" );
			   out.println( "$('#contractId').attr('disabled', 'disabled');" );
			   out.println( "$('.kanhandle').remove();" );
			   out.println( "$('#employeeId').removeClass('important');" );
			   out.println( "}" );
			}
		%>
		
	})(jQuery);
	
	function getEmployee(){
		$.ajax({
			url : "employeeAction.do?proc=get_object_json&employeeId=" + $("#employeeId").val() + "&attendance=leave&subAction=" + getSubAction(), 
			dataType : "json", 
			success : function(data){
				cleanError('manageLeave_employeeId');
				cleanError('manageLeave_clientId');
				if(data.success=='true'){
					$('#employeeNameZH').val(data.nameZH);
					$('#employeeNameEN').val(data.nameEN);
					$('#certificateNumber').val(data.certificateNumber);
					$('#employeeNo').val(data.employeeNo);
				}else{
					$('#employeeNameZH').val('');
					$('#employeeNameEN').val('');
					$('#certificateNumber').val('');
					$('#employeeNo').val('');
					addError('manageLeave_employeeId', data.errorMsg );
				}
				
				// HR Service Load�ͻ�������
			    <logic:equal name="role" value="1">
			   	    loadHtml('#clientId', 'clientAction.do?proc=list_object_options_ajax&clientId=<bean:write name="leaveHeaderForm" property="clientId" />&employeeId=' + $("#employeeId").val(), getDisable(), 'if($(\'#clientId\').children().length==\'2\' && getSubAction() != \'viewObject\'){$(\'#clientId option:nth-child(2)\').attr(\'selected\', \'selected\');} if($(\'#clientId\').children().length==\'1\' ){addError(\'manageLeave_clientId\', \'<bean:message bundle="public" key="error.no.leave.condition" />\');}$(\'#clientId\').change();');
		  		</logic:equal>
		  		
		  		// hroservice Load�Ͷ���ͬ������
			  	 <logic:equal name="role" value="5">
			  		var employeeId = $('#employeeId').val();
					if( data.errorMsg != null){
						employeeId = "0";
					}	
		  			loadHtml('#contractId', 'employeeContractAction.do?proc=list_object_options_ajax&flag=2&employeeId=' + employeeId + '&contractId=<bean:write name="leaveHeaderForm" property="contractId" />', getDisable(), 'if($(\'#contractId\').children().length==\'2\' && getSubAction() != \'viewObject\'){$(\'#contractId option:nth-child(2)\').attr(\'selected\', \'selected\');} if($(\'#contractId\').children().length==\'1\' ){addError(\'manageLeave_contractId\', \'<bean:message bundle="public" key="error.no.leave.condition" />\');}$(\'#contractId\').change();');	
			  	 </logic:equal>
		  		<logic:equal name="role" value="2">
			  		var employeeId = $('#employeeId').val();
					if( data.errorMsg != null){
						employeeId = "0";
					}	
		  			loadHtml('#contractId', 'employeeContractAction.do?proc=list_object_options_ajax&flag=2&employeeId=' + employeeId + '&contractId=<bean:write name="leaveHeaderForm" property="contractId" />', getDisable(), 'if($(\'#contractId\').children().length==\'2\' && getSubAction() != \'viewObject\'){$(\'#contractId option:nth-child(2)\').attr(\'selected\', \'selected\');} if($(\'#contractId\').children().length==\'1\' ){addError(\'manageLeave_contractId\', \'<bean:message bundle="public" key="error.no.leave.condition" />\');}$(\'#contractId\').change();');	
			  	</logic:equal>
			}
		});
	}
	
	// ����
	function focusSickLeave( startDate, endDate ){
		$('#estimateStartDate').focus(function(){
			WdatePicker({
		   		dateFmt : 'yyyy-MM-dd HH:mm',
		   		minDate : startDate,
		   		maxDate : '#F{$dp.$D(\'estimateEndDate\')||\'' +endDate+ '\'}',
		   		disabledDates : ['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]'],
		   		// WdatePicker����ڲ��¼���ȷ�Ϻ� 
				onpicked :function(dp){
					calculateLeaveHours( dp.cal.getNewDateStr(), $('#estimateEndDate').val() );
				}
			});
		});
		
		$('#estimateEndDate').focus(function(){
			WdatePicker({
		    	dateFmt : 'yyyy-MM-dd HH:mm',
		    	minDate : '#F{$dp.$D(\'estimateStartDate\')||\'' +startDate+ '\'}',
		    	maxDate : endDate,
		    	disabledDates : ['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]'],
		    	// WdatePicker����ڲ��¼���ȷ�Ϻ� 
				onpicked :function(dp){
					calculateLeaveHours( $('#estimateStartDate').val() , dp.cal.getNewDateStr() );
				}
			});
		});
	};
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		if($('.manageLeave_form input#subAction').val() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};

	// ��ȡ��ǰSubAction
	function getSubAction(){
		return $('.manageLeave_form input#subAction').val();
	};
	 
	// ��ʼʱ��Focus�¼�
	function focusStartDate(startDate, endDate, defDate){
		// ��ʼ���������
		cleanError('manageLeave_contractId');
		if( startDate == '' || endDate == ''){
			addError('manageLeave_contractId', '<bean:message bundle="public" key="error.no.leave.condition" />');
		}else{
			$('#estimateStartDate').focus(function(){
				$('#estimateEndDate').unbind('focus');
				$('#estimateEndDate').val('');
				$('#estimateBenefitHours').val('');
				$('#estimateLegalHours').val('');
				var hours = 0.0;
				if( getSubAction() != 'createObject' ){
					if( '<bean:write name="leaveHeaderForm" property="estimateLegalHours" />' != ''){
						hours = parseFloat(hours) + parseFloat('<bean:write name="leaveHeaderForm" property="estimateLegalHours" />');
					}
					
					if( '<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />' != ''){
						hours = parseFloat(hours) + parseFloat('<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />');
					}
				}
				
				WdatePicker({
					// WdatePicker����ڲ��¼���ȷ��ʱ
					onpicking :function(dp){
						if( dp.cal.getNewDateStr() !='' ){
							$.ajax({
								url:"leaveHeaderAction.do?proc=get_endDate_ajax&itemId=" + $('#itemId').val() + "&contractId=" + $('#contractId').val() + "&date=" + dp.cal.getNewDateStr() + "&leftHours=" + getLeftHours($('#itemId').val()) + '&hours=' + hours,
								success : function(html){
									if( html.trim() != '' ){
										focusEndDate(html,endDate);
									}else{
										addError('manageLeave_contractId', '<bean:message bundle="public" key="error.invalid.calendr.or.shift" />');
									}
								}
							});
						}
						calculateLeaveHours( dp.cal.getNewDateStr(), $('#estimateEndDate').val() );
					},
					onpicked :function(dp){
						if( dp.cal.getNewDateStr() !='' ){
							$.ajax({
								url:"leaveHeaderAction.do?proc=get_endDate_ajax&itemId=" + $('#itemId').val() + "&contractId=" + $('#contractId').val() + "&date=" + dp.cal.getNewDateStr() + "&leftHours=" + getLeftHours($('#itemId').val()) + '&hours=' + hours,
								success : function(html){
									if( html.trim() != '' ){
										focusEndDate(html,endDate);
									}else{
										addError('manageLeave_contractId', '<bean:message bundle="public" key="error.invalid.calendr.or.shift" />');
									}
								}
							});
						}
						calculateLeaveHours( dp.cal.getNewDateStr(), $('#estimateEndDate').val() );
					},
					// WdatePicker����ڲ��¼������ʱ
					onclearing : function(dp){
						$('#estimateBenefitHours').val('');
						$('#estimateLegalHours').val('');
					},
					startDate : defDate,
			   		dateFmt : 'yyyy-MM-dd HH:mm',
			   		minDate : startDate,
			   		maxDate : endDate, 
			   		disabledDates : ['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]']
				});
			});
		}
	};
	
	// ����ʱ��Focus�¼�
	function focusEndDate(html,endDate){
		var endDate1 = new Date(html.replace(/-/g,"/"));
		var endDate2 = new Date(endDate.replace(/-/g,"/"));
		$('#estimateEndDate').unbind('focus');
		$('#estimateEndDate').focus(function(){
			WdatePicker({
				// WdatePicker����ڲ��¼���ȷ��ʱ
				onpicking :function(dp){
					$.ajax({
						url : "leaveHeaderAction.do?proc=check_leave_date&leaveHeaderId=" + $('#leaveHeaderId').val()+ "&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val() + "&endDate=" + dp.cal.getNewDateStr() ,
						success : function(html){
							if(html == 'true'){
								if( !$('.manageLeave_estimateEndDate').hasClass('error') ){
									addError('manageLeave_estimateEndDate', '<bean:message bundle="public" key="error.time.period.exist.leave" />');
									$('#estimateBenefitHours').val('');
									$('#estimateLegalHours').val('');
								}
							}else{
								cleanError('manageLeave_estimateEndDate');
							}
						} 
					});
				},
				// WdatePicker����ڲ��¼���ȷ�Ϻ� 
				onpicked :function(dp){
					calculateLeaveHours( $('#estimateStartDate').val() , dp.cal.getNewDateStr() );
				},
				// WdatePicker����ڲ��¼������ʱ
				onclearing : function(dp){
					$('#estimateBenefitHours').val('');
					$('#estimateLegalHours').val('');
				},
				// WdatePicker����ڲ��¼�����պ�
				oncleared : function(dp){
					if( $('.manageLeave_estimateEndDate').hasClass('error') ){
						cleanError('manageLeave_estimateEndDate');
					}
				},
				startDate : '#F{$dp.$D(\'estimateStartDate\')}',
		    	dateFmt : 'yyyy-MM-dd HH:mm',
		    	minDate : '#F{$dp.$D(\'estimateStartDate\')}',
		    	maxDate : endDate2 > endDate1 ? html : endDate,
		    	disabledDates : ['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]']
		    });	
		});
	};
	
	// ��ʼʱ��Focus�¼�
	function focusStartDate_for_timesheet( currDay, nextDay ){
		$('#estimateStartDate').focus(function(){
			WdatePicker({
				onpicking :function(dp){
					if( dp.cal.getNewDateStr() != '' ){
						$.ajax({
							url:"leaveHeaderAction.do?proc=get_endDate_ajax&itemId=" + $('#itemId').val() + "&itemId=" + $('#itemId').val() + "&contractId=" + $('#contractId').val() + "&date=" + dp.cal.getNewDateStr() + "&leftHours=" + getLeftHours($('#itemId').val()) + "&maxDate=" + currDay,
							success : function(html){
								if( html != '' ){
									focusEndDate_for_timesheet(html);
								}
							}
						});
					}
					calculateLeaveHours( dp.cal.getNewDateStr(), $('#estimateEndDate').val() );
				},
				onclearing : function(dp){
					$('#estimateBenefitHours').val('');
					$('#estimateLegalHours').val('');
				},
				startDate : currDay + ':00',
		   		dateFmt : 'yyyy-MM-dd HH:mm',
		   		minDate : currDay,
		   		maxDate : $('#estimateEndDate').val() == '' ? nextDay : '#F{$dp.$D(\'estimateEndDate\')}',  
		   		disabledDates : ['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]']
			});
		});
	};
	
	// ����ʱ��Focus�¼�
	function focusEndDate_for_timesheet( html ){
		$('#estimateEndDate').focus(function(){
			WdatePicker({
				// WdatePicker����ڲ��¼���ȷ��ʱ
				onpicking :function(dp){
					$.ajax({
						url : "leaveHeaderAction.do?proc=check_leave_date&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val() + "&endDate=" + dp.cal.getNewDateStr() ,
						success : function(html){
							if(html == 'true'){
								if( !$('.manageLeave_estimateEndDate').hasClass('error') ){
									addError('manageLeave_estimateEndDate', '<bean:message bundle="public" key="error.time.period.exist.leave" />');
									$('#estimateBenefitHours').val('');
									$('#estimateLegalHours').val('');
								}
							}else{
								cleanError('manageLeave_estimateEndDate');
							}
						} 
					});
				},
				// WdatePicker����ڲ��¼���ȷ�Ϻ� 
				onpicked :function(dp){
					calculateLeaveHours( $('#estimateStartDate').val() , dp.cal.getNewDateStr() );
				},
				// WdatePicker����ڲ��¼������ʱ
				onclearing : function(dp){
					$('#estimateBenefitHours').val('');
					$('#estimateLegalHours').val('');
				},
				// WdatePicker����ڲ��¼�����պ�
				oncleared : function(dp){
					if( $('.manageLeave_estimateEndDate').hasClass('error') ){
						cleanError('manageLeave_estimateEndDate');
					}
				},
				startDate : '#F{$dp.$D(\'estimateStartDate\')}',
		    	dateFmt : 'yyyy-MM-dd HH:mm',
		    	minDate : '#F{$dp.$D(\'estimateStartDate\')}',
		    	maxDate : html,
		    	disabledDates : ['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]']
		    });	
		});
	};
	 
	 
	// �������ʱ���ȣ���λ��Сʱ��
	function calculateLeaveHours( startDate , endDate ){
		 if(startDate != '' && endDate != ''){
    		 $.ajax({
				url : "leaveHeaderAction.do?proc=calculate_leave_hours_ajax&itemId=" + $('#itemId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + startDate + "&endDate=" + endDate,
				success : function(html){
					if(html != ''){
						if( !$('.manageLeave_estimateEndDate').hasClass('error') ){
							setLeaveHours($('#itemId').val(), html);
						}
					}
				 }
			 });
		 }
	};
	
	// ��ȡʣ��Сʱ��
	function getLeftHours( itemId ){
		 // JS��ȡ����������ʣ��Сʱ
		 var left_benefit = $('#resultTable tr[id^="tr_itemId_' + itemId +'"] td:nth-child(5)').html();
		 var left_legal = $('#resultTable tr[id^="tr_itemId_' + itemId +'"] td:nth-child(7)').html();
		 // JS��ȡ�����������ܼ�Сʱ
		 var total_benefit = $('#resultTable tr[id^="tr_itemId_' + itemId +'"] td:nth-child(4)').html();
		 var total_legal = $('#resultTable tr[id^="tr_itemId_' + itemId +'"] td:nth-child(6)').html();
		 
		 // ��������
		 if( itemId == '41') {
			 return parseFloat(left_benefit) + parseFloat(left_legal);
		 }
		 // ��ٷ���(ȥ��)
		 else if(itemId == '48'){
			 return parseFloat(left_benefit) + parseFloat(left_legal);
		 }
		 // ��ٸ���(ȥ��)
		 else if(itemId == '49'){
			 return parseFloat(left_benefit) + parseFloat(left_legal);
		 }
		 // ����ǲ��� - ȫн
		 else if( itemId == '42' ){
			 return parseFloat(left_benefit);
		 }
		 // �����ݼ����
		 else{
			 if( parseFloat(total_benefit) == 0 ){
				 return -1;
			 }else{
				 return parseFloat(left_benefit);
			 }
		 } 
		 return 0;
	};
	
	// ��ȡ�Ƿ����������
	function isLimit( itemId ){
		 // JS��ȡ�����������ܼ�Сʱ
		 var total_benefit = $('#resultTable tr[id^="tr_itemId_' + itemId +'"] td:nth-child(4)').html();
		 var total_legal = $('#resultTable tr[id^="tr_itemId_' + itemId +'"] td:nth-child(6)').html();
		 return total_benefit == '0.00' && total_legal == '0.00' ? true : false;
	};
	
	// �������ʱ��
	function setLeaveHours(itemId, hours){
		
		// ���������
		if(getStatus() == '3'&&getRetrieveStatus()=='1'){
			$('#estimateLegalHours').val('0.0');
			$('#estimateBenefitHours').val(hours);
			
			 // JS��ȡ�����������ܼ�Сʱ
			var total_benefit = $('#resultTable tr[id^="tr_itemId_' + itemId +'"] td:nth-child(4)').html();
			var total_legal = $('#resultTable tr[id^="tr_itemId_' + itemId +'"] td:nth-child(6)').html();
			
			var beforeLegalHours = 0;
			var befoteBenefitHours = 0;
	  		if( '<bean:write name="leaveHeaderForm" property="estimateLegalHours" />' != ''){
	  			beforeLegalHours = parseFloat('<bean:write name="leaveHeaderForm" property="estimateLegalHours" />');
			}
			 
			if( '<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />' != ''){
				befoteBenefitHours = parseFloat('<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />');
			}
			
			// ��������
			if( itemId == '41'){
				if( parseFloat(hours) > parseFloat(total_benefit) ){
		    		$('#estimateLegalHours').val( parseFloat(total_benefit) );
					$('#estimateBenefitHours').val( parseFloat(parseFloat(hours) - parseFloat(total_benefit)) );
		    	}else{
		    		$('#estimateLegalHours').val(parseFloat(hours));
					$('#estimateBenefitHours').val('0.0');
		    	}
			}else if(itemId == '48'){
				$('#estimateLegalHours').val(hours);
			}else if(itemId == '49'){
				$('#estimateBenefitHours').val(hours);
			}else{
				$('#estimateBenefitHours').val(hours);
			}
		}else{
			// JS��ȡ����������ʣ��Сʱ
			var left_benefit = $('#resultTable tr[id^="tr_itemId_' + itemId + '"] td:nth-child(7)').html();
			var left_legal = $('#resultTable tr[id^="tr_itemId_' + itemId + '"] td:nth-child(5)').html();
			if( getSubAction() == 'modifyObject'){
				left_benefit = parseFloat( left_benefit ) + parseFloat( '<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />' );
				left_legal = parseFloat( left_legal ) + parseFloat( '<bean:write name="leaveHeaderForm" property="estimateLegalHours" />' );
			}

		    if(itemId == '41'){
		    	if( parseFloat(hours) > parseFloat(left_legal) ){
		    		$('#estimateLegalHours').val( left_legal );
					$('#estimateBenefitHours').val( parseFloat(hours) - parseFloat(left_legal) );
		    	}else{
		    		$('#estimateLegalHours').val(hours);
					$('#estimateBenefitHours').val('0.0');
		    	}
		    }
		    else if(itemId == '48'){
		    	$('#estimateLegalHours').val(hours);
		    }
		    else if(itemId == '49'){
		    	$('#estimateBenefitHours').val(hours);
		    }
		    else{
				$('#estimateBenefitHours').val(hours);
		    }
		}
	};
	
	// �ύʱ�������¿��ڱ���½����˻�״̬�������ʾ��
	function getFlag(){
		var flag = true;
		$.ajax({
			url : "timesheetHeaderAction.do?proc=existAvailableTimesheet&submitType=1&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val(), 
			type: 'POST',
			async:false,
			success : function(data){
				if(data == "1"){
					flag = false;
				}
			}
		});
		return flag;
	};
	
	// ��ȡ���״̬
	function getStatus(){
		return $('.manageLeave_status').val();
	};
	
	// ��ȡ����״̬
	function getRetrieveStatus(){
		return $('.manageLeave_retrieveStatus').val();
	};
	
	function useNextYearHours(){
		if($('#itemId').val()=='41'&&$('img#useNextYearHours_tips').length==1){
			$('#useNextYearHours').val('1');
		}else{
			$('#useNextYearHours').val('2');
		}
	};
</script>
