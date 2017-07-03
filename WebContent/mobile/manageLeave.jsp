<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <!--
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, maximum-scale=1">
 -->
<link rel="stylesheet" href="mobile/css/mobile.css" />
<script src="js/kan.js"></script>
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.attachment.js"></script>
<script src="js/ajaxupload.3.6.js"></script>
<!-- mobiscroll -->
   <link href="plugins/PluginDatetime/css/mobiscroll.custom-2.5.2.min.css" rel="stylesheet" type="text/css" />
   <script src="plugins/PluginDatetime/js/mobiscroll.custom-2.5.2.min.js" data-main="scripts/main"></script>
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
<style type="text/css">
	#pltsTipLayer{width:100%;font-size:36pt; color:red; text-indent:30px; float:left; display:inline;}
	ol li{font-size:28pt;}
	.btndef{ background-color: #B0E2FF;margin-top: 30px; color: black}
</style>
</head>
<body>
<div id="layout">
<html:form action="leaveHeaderAction.do?proc=add_object&mobile=1" styleClass="manageLeave_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="timesheetId" name="timesheetId" value="<bean:write name="leaveHeaderForm" property="timesheetId"/>" />
	<input type="hidden" id="leaveHeaderId" name="id" value="<bean:write name="leaveHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="leaveHeaderForm" property="subAction"/>" /> 
	<input type="hidden" id="unread" name="unread" class="unread" value="1" /> 
	<input type="hidden" id="employeeId" name="employeeId" class="employeeId" value="<bean:write name="leaveHeaderForm" property="employeeId"/>" /> 
	<input type="hidden" id="status" name="status" class="manageLeave_status" value="<bean:write name="leaveHeaderForm" property="status"/>" /> 
	<input type="hidden" id="tempCount" name="tempCount" class="tempCount" value="0" />
	<html:hidden styleClass="useNextYearHours" styleId="useNextYearHours" property="useNextYearHours" /> 
	<div class="waikuang">
			<!-- tip -->
			 <div id='pltsTipLayer' onclick="$(this).hide();" style='display:none;top:5px;position: absolute;background-color: #dff6c6;border: 1px solid #bdde98;color:#860404'>
			 	<logic:equal value="true" name="isForeign">
    				<bean:message bundle="wx" key="wx.leave.tip.foreign" />
    			</logic:equal>
    			<logic:notEqual value="true" name="isForeign">
    				<bean:message bundle="wx" key="wx.leave.tip" />
    			</logic:notEqual>
    			</div>  
	          <div class="neirong1 div_contracts" style="display: none;"><span><bean:message bundle="wx" key="wx.leave.contract" />��</span>
	          	<select name="contractId" class="shurukuang6 manageLeave_contractId" id="contractId">
					<option value="0"><bean:message bundle="wx" key="wx.please.select" /></option>
				</select>
			  </div>
	          <div class="neirong1"><sup><bean:message bundle="wx" key="wx.leave.type" />��</sup>
	          	<select name="itemId" class="shurukuang6 manageLeave_itemId" id="itemId">
						<option value="0"><bean:message bundle="wx" key="wx.please.select" /></option>
				</select>
			  </div>
			  <div class="neirong1" id="Wdate_start"><sup><bean:message bundle="wx" key="wx.start.date" />��</sup>
			  	<html:text  property="estimateStartDate" maxlength="20" readonly="true" styleClass="shurukuang7 manageLeave_estimateStartDate" styleId="estimateStartDate" />
			  </div>
			  <div class="neirong1" id="Wdate_end"><sup><bean:message bundle="wx" key="wx.end.date" />��</sup>
			  	<html:text  property="estimateEndDate" maxlength="20" readonly="true" styleClass="shurukuang7 manageLeave_estimateEndDate" styleId="estimateEndDate" />
			  </div>
			  <div class="neirong1" style="display: none;" id="used_legal"><sup><bean:message bundle="wx" key="wx.leave.use.in.law" />:</sup>
			  	<input readonly="readonly" name="estimateLegalHours" type="text" class="shurukuang8" id="estimateLegalHours" />
			  </div>
			  <div class="neirong2"><sup><bean:message bundle="wx" key="wx.leave.leave.time" />��</sup>
			  	<input readonly="readonly" name="estimateBenefitHours" type="text" class="shurukuang8" id="estimateBenefitHours" value="<bean:write name="leaveHeaderForm" property="estimateBenefitHours" />"/>
			  </div>
			  <div class="neirong3"><span><bean:message bundle="wx" key="wx.description" />��</span>
			  	<html:textarea property="description" name="leaveHeaderForm" styleClass="staffForm_personalAddress shurukuang3" />
			  </div>
			  <div class="neirong3" id="attachement">
			  	 <span><button class="btndef" name="uploadAttachment" id="uploadAttachment" class="kanhandle"><bean:message bundle="public" key="link.upload.attachment" /></button></span>
			  	 <ol id="attachmentsOL" class="auto"></ol>
			  </div>
				
	</div>
	</html:form>
	<div class="daiban" style="border:none;" id="leaveTime_special_info">
	   
	</div>
	<a href="#" class="button orange" onclick="btnClick();"><bean:message bundle="wx" key="wx.btn.confirm" /></a>
	<div id="attachmentsDiv"></div>
	<br clear="all" />
</div>

<div id="shield" style="position: fixed; left: 0px; top: 0px; display: none; z-index: 9998; opacity: 0.8; background: #7D7159; width: 100%; height: 100%;">
<img src="images/loading_s.gif" style="position: absolute; top: 300px; left: 48%;" /></div>

</body>

<script type="text/javascript">
	 (function($) {	
		 
		 //�ȼ�������
		 $('#shield').show();
		 
		 // ������޸�
		 if ( getSubAction() == 'viewObject' ) {
			 $('.manageLeave_form input#subAction').val('submitObject');
			 $('.manageLeave_form').attr('action', 'leaveHeaderAction.do?proc=modify_object&from=mobile');
		 };
		 
		 
		// ��������ϢChange�¼���
		$('#contractId').change(function(){
			// Load Item Options
			_loadHtml('#itemId', 'employeeContractLeaveAction.do?proc=list_object_options_ajax&itemId=<bean:write name="leaveHeaderForm" property="itemId" />&contractId=' + $('#contractId').val(), getDisable(), 'if(getSubAction()==\'submitObject\'){$(\'#itemId\').change();}');
			
			//�ر�����
			$('#shield').hide();
			setTimeout("hideNotice();",10000);
			
			// Locad Special Info
			//loadHtml('#special_info', 'leaveHeaderAction.do?proc=list_special_info_html&leaveHeaderId=<bean:write name="leaveHeaderForm" property="leaveHeaderId" />&contractId=' + $('#contractId').val(), false, '$(\'#itemId\').change();');
			$.post("leaveHeaderAction.do?proc=list_special_info_mobile",{'contractId':$(this).val()},function(resultData){
				var data = resultData.employeeContractLeaveData;
				var inProbationAndCannotUse = resultData.inProbationAndCannotUse;
				// ���� - ��ȫн���¼١����١�����١�ɥ��Сʱ�ܼ�Ϊ�㣬����Ϊ������
				var html = "<h2><bean:message bundle="wx" key="wx.leave.my.left.hour.in.salary" />��</h2>";
				for(var i = 0 ; i<data.length;i++){
					var employeeContractLeave = data[i];
					var itemId = employeeContractLeave.itemId;
					var itemName = employeeContractLeave.decodeItemId;
					// ʣ��Сʱ
					var hours = parseFloat(employeeContractLeave.leftBenefitQuantity);
					if(employeeContractLeave.itemId=='41' || employeeContractLeave.itemId=='48' ){
						hours = hours + parseFloat(employeeContractLeave.leftLegalQuantity) ;
						// �����¼�
						disabledNoPayLeave(hours,inProbationAndCannotUse);
					}
					html+= "<h3><input type=\"hidden\" id=\"item_" +itemId+ "\" value=\"" +hours+ "\" />";
					if( parseFloat(employeeContractLeave.benefitQuantity) == 0 && ( itemId == '43' || itemId == '44' || itemId == '45' || itemId == '46' || itemId == '47' ) ){
						 // ����ʾ
					}else{
						html+= itemName+"��"+hours+"<bean:message bundle="wx" key="wx.hour" />";
						if(itemId == '41'){
							var tmpDays = Math.floor(hours/8);
							var tmpHours = hours%8;
							var tmpShow="";
							if(tmpHours==0){
								tmpShow=tmpDays+"<bean:message bundle="wx" key="wx.day" />";
							}else{
								tmpShow=tmpDays+"<bean:message bundle="wx" key="wx.day" /> "+tmpHours+"<bean:message bundle="wx" key="wx.hour" />";
							}
							html+=" ("+tmpShow+")";
						}
						
						if(employeeContractLeave.useNextYearHours=='1'){
							html+='<span id="useNextYearHours_tips" style="color:red;"> Only ' + employeeContractLeave.year + ' use</span>'
						}
					}
					html+= "<input type=\"hidden\" id=\"left_fd_" +itemId+ "\" value=\"" +employeeContractLeave.leftLegalQuantity+ "\" />";
					html+= "<input type=\"hidden\" id=\"total_fd_" +itemId+ "\" value=\"" +employeeContractLeave.legalQuantity+ "\" />";
					html+= "<input type=\"hidden\" id=\"left_fl_" +itemId+ "\" value=\"" +employeeContractLeave.leftBenefitQuantity+ "\" />";
					html+= "<input type=\"hidden\" id=\"total_fl_" +itemId+ "\" value=\"" +employeeContractLeave.benefitQuantity+ "\" />";
					html+= "</h3>";
				}
				$("#leaveTime_special_info").html(html);
			},"json");
		}); 
		
		//	�����Ͷ���ͬ
		_loadHtml('#contractId', 'employeeContractAction.do?proc=list_object_options_ajax_mobile&flag=2', false, "checkContractId();");
		
		
		// �󶨿�ĿChange�¼�������/��ʾ��Ԥ�� ��������Сʱ�� 
		// ����û�з���Сʱ��.ͳһ����ʾ
		$('#itemId').change(function(){
			
			/*if( $(this).val() == '41'){
				$('#used_legal').show();
			}else {
				$('#used_legal').hide();
			}*/
			// change֮ǰ������ڿؼ��󶨵��¼�
			$('#Wdate_start').html($('#Wdate_start').clone().html());
			$('#Wdate_end').html($('#Wdate_end').clone().html());
			if($("#tempCount").val()==0&&getSubAction()!='createObject'){
				// ������޸ģ�Ĭ�����ʱ���ǰ������ݿ���㣬����ᱻ���
				$("#tempCount").val(1);
			}else{
				$('#estimateLegalHours').val('');
				$('#estimateBenefitHours').val('');
			}
			
			
			if($(this).val() != '0'){
				var leftHours = getLeftHours($(this).val());
				if( leftHours == -1 || leftHours > 0){
					// �������
					$('#shield').show();
					$.ajax({
						url : "leaveHeaderAction.do?proc=item_change_ajax&contractId=" + $('#contractId').val() + "&itemId=" + $(this).val(), 
						dataType : "json",
						success : function(data){
							// ȥ������
							$('#shield').hide();
							<logic:notEmpty name="currDay">
								focusStartDate_for_timesheet('<bean:write name="currDay" />','<bean:write name="nextDay" />');
							</logic:notEmpty>
							<logic:empty name="currDay">
							
								if( getStatus() == '3'){
									focusSickLeave('<bean:write name="leaveHeaderForm" property="estimateStartDate" />','<bean:write name="leaveHeaderForm" property="estimateEndDate" />');
								}else{
									focusStartDate(data.startDate, data.endDate, data.defDate);
								}
								if( !getStatus() == '3' && getSubAction() == 'viewObject'){
									<logic:notEmpty name="leaveEndDate">
										focusEndDate('<bean:write name="leaveEndDate" />');
									</logic:notEmpty>
								}
							</logic:empty>
						}
					});
				}else{
					if($('#itemId').val() != '0'){
						alert("<bean:message bundle="wx" key="wx.leave.time.up" />");						
					}
				}
			}
			
		});
		
	})(jQuery);
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		if($('.manageLeave_form input#subAction').val() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};
	
	// �������ʱ���ȣ���λ��Сʱ��
	function calculateLeaveHours( startDate , endDate ){
		 if(startDate != '' && endDate != ''){
    		 $.ajax({
				url : "leaveHeaderAction.do?proc=calculate_leave_hours_ajax&itemId=" + $('#itemId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + startDate + "&endDate=" + endDate,
				success : function(html){
					if(html != ''){

						// JS��ȡ����������ʣ��Сʱ
						var left_fd = $('#leaveTime_special_info h3 input#left_fd_' + $('#itemId').val()).val();
						var left_fl = $('#leaveTime_special_info h3 input#left_fl_' + $('#itemId').val()).val();
						if($('#itemId').val()=='41'){
							if( parseFloat(html) > parseFloat(left_fd) ){
								$('#estimateLegalHours').val( left_fd );
								$('#estimateBenefitHours').val( parseFloat(html) - parseFloat(left_fd) );
							}else{
								$('#estimateLegalHours').val(html);
								$('#estimateBenefitHours').val('0.0');
							}
						}else{
							$('#estimateBenefitHours').val(parseFloat(html));
						}
					}
				 }
			 });
		 }
	};
	
	// ��ʼʱ��Focus�¼�
	function focusStartDate(startDateStr, endDateStr, defDateStr){
		var startDate = new Date(startDateStr.replace(/-/g,"/"));
		var endDate = new Date(endDateStr.replace(/-/g,"/"));
		var defDate = new Date(defDateStr.replace(/-/g,"/"));
		var hours = 0.0;
		var optDate = {  
	            preset: 'datetime', //����  
	            theme: 'android-ics light', //Ƥ����ʽ  
	            display: 'bottom', //��ʾ��ʽ   
	            mode: 'scroller', //����ѡ��ģʽ  
	            showNow: true,  
	            nowText: "Today",  
	           	minDate:startDate,
	           	maxDate:endDate,
	           	defaultValue :defDate,
	            stepMinute: 30,
				yearText: "Year",
				monthText: "Month",
				dayText: "Day",
				hourText: "Hour",
				minuteText: "Minute",
				setText: 'Confirm',
				cancelText: 'Cancel',
				dateFormat: 'yy-mm-dd',
				timeFormat: 'HH:ii',
				timeWheels: 'HHii',
				dateOrder: 'yymmdd', 
				height: 108,
				width:140,
				onShow : function(valueText,inst){
					// ��ʾ
				},
				onChange :function(valueText,inst){
					$('#estimateEndDate').val('');
				},
				onSelect: function(valueText,inst){
					var result = checkStartEndDate(valueText,true);
					// ѡ��
					if( valueText !='' && result==0){
						$.ajax({
							url:"leaveHeaderAction.do?proc=get_endDate_ajax&contractId=" + $('#contractId').val() + "&date=" + valueText + "&leftHours=" + getLeftHours($('#itemId').val()) + '&hours=' + hours,
							success : function(html){
								if( html != '' ){
									focusEndDate(html,valueText,endDateStr);
								}
							}
						});
					}
					calculateLeaveHours( valueText, $('#estimateEndDate').val() );
				} ,
				onCancel: function(valueText,inst){
					// ȡ��
				} 
	        };  
		
		$("#estimateStartDate").mobiscroll(optDate);
	};
	
	// ����ʱ��Focus�¼�
	function focusEndDate(maxDateStr,startDateStr,stopDateStr){
		var endDate = new Date(maxDateStr.replace(/-/g,"/"));
		var startDate = new Date(startDateStr.replace(/-/g,"/"));
		
		var actualEndDate = new Date(stopDateStr.replace(/-/g,"/"));
		var optDate = {  
	            preset: 'datetime', //����  
	            theme: 'android-ics light', //Ƥ����ʽ  
	            display: 'bottom', //��ʾ��ʽ   
	            mode: 'scroller', //����ѡ��ģʽ  
	            lang:'zh',  
	            showNow: true,  
	            nowText: "Today",  
	        	minDate:startDate,
	           	maxDate:actualEndDate > endDate ? endDate : actualEndDate,
	            stepMinute: 30,
				yearText: "Year",
				monthText: "Month",
				dayText: "Day",
				hourText: "Hour",
				minuteText: "Minute",
				setText: 'Confirm',
				cancelText: 'Cancel',
				dateFormat: 'yy-mm-dd',
				timeFormat: 'HH:ii',
				timeWheels: 'HHii',
				dateOrder: 'yymmdd',
				height: 108,
				width:140,
				onShow : function(valueText,inst){
					if(valueText==null && valueText !=''){
						$.ajax({
							url : "leaveHeaderAction.do?proc=check_leave_date&leaveHeaderId=" + $('#leaveHeaderId').val()+ "&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val() + "&endDate=" + valueText ,
							success : function(html){
								if(html == 'true'){
									alert('<bean:message bundle="wx" key="wx.leave.leaving.during" />��');
									$('#estimateBenefitHours').val('');
									$('#estimateLegalHours').val('');
								}
							} 
						});
					}
				},
				onSelect: function(valueText,inst){
					var result = checkStartEndDate(valueText,false);
					// ѡ��
					if(result == 0){
						//��֤Ĭ��ֵ
						$.ajax({
							url : "leaveHeaderAction.do?proc=check_leave_date&leaveHeaderId=" + $('#leaveHeaderId').val()+ "&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val() + "&endDate=" +  $('#estimateEndDate').val() ,
							success : function(html){
								if(html == 'true'){
									alert('<bean:message bundle="public" key="error.time.period.exist.leave" />');
									$('#estimateEndDate').val('');
								}else{
									calculateLeaveHours( $('#estimateStartDate').val() , valueText );
								}
							} 
						});
						
					}
				} 
				
	        };  
		$("#estimateEndDate").mobiscroll(optDate);
		
	};
	
	// ʣ��Сʱ
	function getLeftHours( itemId ){
		var left_benefit = $('#leaveTime_special_info h3 input#left_fl_' + itemId).val();
		var left_legal = $('#leaveTime_special_info h3 input#left_fd_' + itemId).val();
		// JS��ȡ�����������ܼ�Сʱ
		var total_benefit =$('#leaveTime_special_info h3 input#total_fl_' + itemId).val();
		var total_legal = $('#leaveTime_special_info h3 input#total_fd_' + itemId).val();
		
		 // ��������
		 if( itemId == '41' ) {
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
	
	function btnClick(){
		if($("#itemId").val()==0){
			alert("<bean:message bundle="wx" key="wx.leave.error.item" />");
			return;
		}
		else if($("#estimateStartDate").val()==''){
			alert("<bean:message bundle="wx" key="wx.leave.error.start.date" />");
			return;
		}
		else if($("#estimateEndDate").val()==''){
			alert("<bean:message bundle="wx" key="wx.leave.error.end.date" />");
			return;
		}
		else if($("#estimateBenefitHours").val()==0||$("#estimateBenefitHours").val()==null||$("#estimateBenefitHours").val()==''){
			alert("<bean:message bundle="wx" key="wx.leave.error.leave.hours" />");
			return;
		}
		else{
			if(confirm("<bean:message bundle="wx" key="wx.confirm.submit" />?")){
				useNextYearHours();
				$(".manageLeave_form").submit();
			}
		}
	}
	
	function checkContractId(){
		var len = $("#contractId").find("option").length;
		if(len==2){
			// ���ֻ��һ����ѡ�����
			$("#contractId option:nth-child(2)").attr("selected","selected");
			$("#contractId").change();
		}else if(len>2){
			// ���
			$(".div_contracts").show();
		}
	}
	
	function _loadHtml(reloadTarget, action, disable, js){
		$.ajax({ url: action + '&date=' + new Date(), success: function(html){
			$(reloadTarget).html(html);
			if(disable){
				$(reloadTarget + ' input, ' + reloadTarget + ' select, ' + reloadTarget + ' textarea').attr('disabled', 'disabled');
			}
			
			if(js != null && typeof(js) == 'string'){
		    	eval(js);
			}
		    if(jQuery.isFunction(js)){
			   js(html);
			}
		}});
	};
	
	// ��ȡ��ǰSubAction
	function getSubAction(){
		return $('.manageLeave_form input#subAction').val();
	};
	
	// ��ȡ���״̬
	function getStatus(){
		return $('.manageLeave_status').val();
	};
	
	// ��ȡ����״̬
	function getRetrieveStatus(){
		return $('.manageLeave_retrieveStatus').val();
	};
	
	function hideNotice(){
		$("#pltsTipLayer").hide();
	};
	
	// �����ύ��ť�¼�
	var uploadObject = _createUploadObject('uploadAttachment', 'image', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE %>/<%= BaseAction.getAccountId(request, response) %>/<%=java.net.URLEncoder.encode( java.net.URLEncoder.encode(BaseAction.getUsername(request, response),"utf-8"),"utf-8") %>/');

	function _createUploadObject(id, fileType, attachmentFolder) {
		var postfixRandom = generateMixed(6);
		var uploadObject = new AjaxUpload(id, {
			// �ļ��ϴ�Action
			action : 'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom,
			// ����
			name : 'file',
			// �Զ��ϴ�
			autoSubmit : true,
			// ����Text��ʽ����
			responseType : false,
			// �ϴ�����
			onSubmit : function(filename, ext) {
				createFileProgressDIV(filename);
				setTimeout("ajaxBackState('uploadFileAction.do?proc=getStatusMessage&postfixRandom=" + postfixRandom + "')", 500);
				// �ϴ�������
				$('#' + id).hide();
				$('#' + id).after("<span id='uploading'>uploading...</span>");
			},

			// �ϴ���ɺ�ȡ���ļ���filenameΪ����ȡ�õ��ļ�����msgΪ���������ص���Ϣ
			onComplete : function(filename, msg) {
				// �ϴ�����
				$('#' + id).show();
				$('#uploading').remove();
			}
		});

		return uploadObject;
	};
	
	function ajaxBackState(progressURL) {
		// Ajax���ò�ˢ�µ�ǰ�ϴ�״̬
		$.post(progressURL+'&date=' + new Date(), null, 
			function(result) {
				var obj = result;
				var readedBytes = obj["readedBytes"];
				var totalBytes = obj["totalBytes"];
				var statusMsg = obj["statusMsg"];

				progressbar(readedBytes, totalBytes);
				
				// ����״̬
				if (obj["info"] == "0") {
					setTimeout("ajaxBackState('" + progressURL + "')", 100);
				}
				// �ϴ�ʧ�ܣ���ʾʧ��ԭ��
				else if (obj["info"] == "1") {
					alert(obj["statusMsg"]);
					$('#btnAddAttachment').attr("disabled", false);
					// ɾ���ϴ�ʧ�ܵ��ļ��ͽ�����Ϣ
					$('#uploadFileLi').remove();
				}
				// �ϴ����
				else if (obj["info"] == "2") {
					$('#uploadAttachment').attr("disabled", false);
					$("#attachmentsOL").html($("#attachmentsOL").html()
						+ '<li><input type="hidden" id="attachmentArray" name="attachmentArray" value="'
						+ statusMsg
						+ '" />'
						+ statusMsg.split('##')[1]
						+ ' &nbsp; <img onclick=\"removeAttachment(this);\" src=\"images/warning-btn-m.png\">'
						+ "</li>");
					$('#uploadFileLi').remove();

					if ($('#numberOfAttachment').html() != null) {
						$('#numberOfAttachment').html(eval(parseInt($('#numberOfAttachment').html()) + 1));
					}
				}
			},'json');
	};
	
	function checkStartEndDate(date,isStart){
		var result = 0;
		
		// ����������,��ʼʱ�������8:30  13:30  17:30
		var time = date.substr(11,5);
		
		<logic:equal value="true" name="isForeign">
			if(isStart && !(time=='08:30' || time=='13:30' || time=='17:30')){
				alert('<bean:message bundle="wx" key="wx.leave.invalid.start.date.foreign" />');
				result = result+1;
			}else if(!isStart && !(time=='08:30' || time=='13:30' || time=='17:30')){
				alert('<bean:message bundle="wx" key="wx.leave.invalid.end.date.foreign" />');
				result = result+1;
			}
		</logic:equal>
		
		
		//����ǲ��������,��ʼ����ʱ�������9:00 14:00  18:00
		<logic:notEqual value="true" name="isForeign">
			if(isStart && !(time=='09:00' || time=='14:00' || time=='18:00')){
				alert('<bean:message bundle="wx" key="wx.leave.invalid.start.date" />');
				result = result+1;
			}else if(!isStart && !(time=='09:00' || time=='14:00' || time=='18:00')){
				alert('<bean:message bundle="wx" key="wx.leave.invalid.end.date" />');
				result = result+1;
			}
		</logic:notEqual>
		// ʱ�������4�ı���
		if(result>0&&isStart){
			$('#estimateStartDate').val('');
		}
		
		if(result>0&&!isStart){
			$('#estimateEndDate').val('');
		}
		return result;
	};
	
	function useNextYearHours(){
		if($('#itemId').val()=='41'&&$('span#useNextYearHours_tips').length==1){
			$('#useNextYearHours').val('1');
		}else{
			$('#useNextYearHours').val('2');
		}
	};
	
	// ���ʣ����ٴ���0. ���������,�����¼�
	// true �����������, ���������ڲ��������, ���ҳ���¼�ѡ��
    // false �������ҳ���¼�ѡ��
	function disabledNoPayLeave(hour,inProbationAndCannotUse){
		if(hour && hour > 0 && !inProbationAndCannotUse){
			$("#itemId").find("#option_itemId_44").attr("disabled","disabled");
		}
	}
</script>

