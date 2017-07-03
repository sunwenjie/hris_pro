<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.OTHeaderAction"%>
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
	        <label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="business" key="business.ot" /></label>
	        <logic:notEmpty name="otHeaderForm" property="otHeaderId" >
	         	<label class="recordId"> &nbsp; (ID: <bean:write name="otHeaderForm" property="otHeaderId" />)</label>
	        </logic:notEmpty>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				
				<logic:empty name="otHeaderForm" property="encodedId">
					<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />">
				</logic:empty>
				<logic:notEmpty name="otHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=OTHeaderAction.accessAction%>">
						<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />">
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="submit" action="<%=OTHeaderAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;"/> 
				</kan:auth>
				<kan:auth right="confirm" action="<%=OTHeaderAction.accessAction%>">
					<input type="button" class="function" name="btnConfirm" id="btnConfirm" value="<bean:message bundle="public" key="button.confirm" />" style="display: none;"/> 
				</kan:auth>
				<kan:auth right="list" action="<%=OTHeaderAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />">
				</kan:auth>
				<kan:auth right="delete" action="<%=OTHeaderAction.accessAction%>">
					<logic:equal name="hasDeleteRight" value="true">
						<logic:equal name="username" value="Administrator">
							<input type="button" class="delete" name="btnDelete" id="btnDelete" value="<bean:message bundle="public" key="button.delete" />">
						</logic:equal>
					</logic:equal>
				</kan:auth>
		    </div>
			<!-- Include Form JSP 包含Form对应的jsp文件 --> 
			<jsp:include page="/contents/business/attendance/ot/from/manageOTForm.jsp" flush="true"></jsp:include>		
	    </div>
	</div>
</div>

<!-- Employee Popup Box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
</div>	
						
<script type="text/javascript">
	(function($) {
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Overtime').addClass('selected');
		
		// 添加“搜索雇员信息”
		<logic:notEqual name="role" value="5">
			$('#employeeId').after('<a onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>');
		</logic:notEqual>
		<logic:equal name="role" value="5">
   			$('#employeeId').attr('disabled', 'disabled');
   			getEmployee();
		</logic:equal>
		// 非新增模式添加“查看客户信息”
		if( getSubAction() != 'createObject' ){
			$('#employeeIdLI label').append('&nbsp;<a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="otHeaderForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			$('#contractIdLI label').append('&nbsp;<a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="otHeaderForm" property="encodedContractId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			$('#clientIdLI label').append('&nbsp;<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="otHeaderForm" property="encodedClientId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
		}
		
		if ($('.manageOT_form input#subAction').val() != 'createObject') {
		    disableForm('manageOT_form');
		    $('.manageOT_form input.subAction').val('viewObject');
		    $('#pageTitle').html('<bean:message bundle="business" key="business.ot" /> <bean:message bundle="public" key="oper.view" />');
		    $('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		} else if ($('.manageOT_form input#subAction').val() == 'createObject') {
		    <logic:notEqual name="role" value="5">
				$('#employeeId').addClass('important');
			</logic:notEqual>
			$('#employeeNo').attr('disabled', 'disabled');
			$('#certificateNumber').attr('disabled', 'disabled');
			$('#employeeNameZH').attr('disabled', 'disabled');
			$('#employeeNameEN').attr('disabled', 'disabled');
			$('#contractStartDate').attr('disabled', 'disabled');
			$('#contractEndDate').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
		}
		
		<%
			// 雇员ID错误消息
			final String employeeIdErrorMsg = ( String )request.getAttribute( "employeeIdErrorMsg" );
			
			if( KANUtil.filterEmpty( employeeIdErrorMsg ) != null )
			{
			   out.print( "addError('manageOT_employeeId', '" + employeeIdErrorMsg + "');" );
			}
		%>
		
		// 删除按钮点击事件
		$('#btnDelete').click( function(){
			if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){
				link('otHeaderAction.do?proc=delete_ot&otHeaderId=<bean:write name="otHeaderForm" property="otHeaderId" />');
			}
		});
		
		$('#btnList').click(function() {
			if (agreest())
		    link('otHeaderAction.do?proc=list_object');
		});
		
		// 加载tab
		loadHtml('#special_info', 'otHeaderAction.do?proc=list_special_info_html', true);
		
		 // 提交按钮事件
		$('#btnSubmit').click( function () { 
			if(validate_manage_primary_form() == 0){
				// 更改当前Form的SubAction
				if( getSubAction() != 'createObject'){
					$('.manageOT_form').attr('action', 'otHeaderAction.do?proc=modify_object');
				}
				$('.manageOT_form input#subAction').val('submitObject');
					
		    	enableForm('manageOT_form');
		    	submitForm('manageOT_form');
			}
		});
		 
		// 确认按钮时间
		$('#btnConfirm').click(function(){
			if(validate_manage_primary_form() == 0){
				if( getFlag() == false ){
					if(!confirm('<bean:message bundle="public" key="popup.ot.not.entry.ts" />')){
						return;
					}
				}
				// 更改当前Form的SubAction
				if( getSubAction() != 'createObject'){
					$('.manageOT_form').attr('action', 'otHeaderAction.do?proc=modify_object');
				}
				$('.manageOT_form input#subAction').val('confirmObject');
					
		    	enableForm('manageOT_form');
		    	submitForm('manageOT_form');
			}
	    });
		
		$('#btnEdit').click(function() {
		    if ($('.manageOT_form input#subAction').val() == 'viewObject') {
		        enableForm('manageOT_form');
		        $('.manageOT_form input#subAction').val('modifyObject');
		        $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		        $('.manageOT_form').attr('action', 'otHeaderAction.do?proc=modify_object');
		        $('#pageTitle').html('<bean:message bundle="business" key="business.ot" /> <bean:message bundle="public" key="oper.edit" />');
		        $('li #warning_img').each(function() {
		            $(this).show();
		        });
		        $('li #disable_img').each(function() {
		            $(this).hide();
		        });
		        
		    	<logic:equal name="role" value="5">
		    	 $('#employeeId').attr('disabled', 'disabled');
				</logic:equal>
		        
				<logic:notEqual name="role" value="5">
				 $('#employeeId').addClass('important');
				</logic:notEqual>
		       
		        $('#employeeNo').attr('disabled', 'disabled');
		        $('#certificateNumber').attr('disabled', 'disabled');
		        $('#employeeNameZH').attr('disabled', 'disabled');
		        $('#employeeNameEN').attr('disabled', 'disabled');
		    	$('#contractStartDate').attr('disabled', 'disabled');
				$('#contractEndDate').attr('disabled', 'disabled');
		        $('#status').attr('disabled', 'disabled');
		    } else {
		        var flag = 0;
		        flag = validate_manage_primary_form();
		        if (flag == 0) {
		            submit('manageOT_form');
		        }
		    }
		});
		
	    $('#estimateStartDate').addClass('Wdate');
	    $('#estimateEndDate').addClass('Wdate');
	     
	    if( getStatus() == '1' || getStatus() == '6' ){
			$('#btnSubmit').show();
		} 
	    
	    if( getStatus() == '2' || getStatus() == '3' || getStatus() == '4' || getStatus() == '5'){
			$('#btnEdit').hide();
		}
	    
	    if( getStatus() == '3' ){
			$('#btnConfirm').show();
			$('#estimateStartDate').removeAttr('disabled');
			$('#estimateEndDate').removeAttr('disabled');
		}
	    
	 	// 初始化工作流查看按钮
		loadWorkflowSeach('status','<bean:write name="otHeaderForm" property="workflowId"/>');
	    
	    /**
	     * bind 绑定事件
	     **/
	    
	    // 绑定服务协议Change事件，绑定开始、结束时间focus事件； 
	    $('#contractId').bind('change',function(){
	    	// change之前清空日期控件绑定的事件
	    	$('#otDateOL').html($('#otDateOL').clone().html());
	    	
	    	if( $(this).val() != '' ){
	    		$.ajax({
					url : "otHeaderAction.do?proc=contract_change_ajax&contractId=" + $(this).val(), 
					dataType : "json",
					success : function(data){
						cleanError('manageOT_contractId');
						
						<logic:notEmpty name="currDay">
							$('.manageOT_form #subAction').val('createObject');
						</logic:notEmpty>
					
						if( data.messageError == ''){
							$('#contractStartDate').val(data.contractStartDate);
							$('#contractEndDate').val(data.contractEndDate);
							focusDate(data.startDate, data.endDate, data.defDate);
						}else {
							$('#contractStartDate').val('');
							$('#contractEndDate').val('');
							$('#contractId').val() != '0' ? addError('manageOT_contractId',data.messageError) : ""; 
						}
					}
				});
	    		
	    		// Locad Special Info
				loadHtml('#special_info', 'otHeaderAction.do?proc=list_special_info_html&contractId=' + $('#contractId').val() + '&noTab=false', false, null); 
	    	}
	    });
	    
		// 绑定客户Change事件，加载派送信息下拉框； 
		$('#clientId').change(function(){
			loadHtml('#contractId', 'employeeContractAction.do?proc=list_object_options_ajax&clientId=' + $(this).val() + '&employeeId=' + $('#employeeId').val() + '&contractId=' + $('#contractId').val(), getDisable(), 'if($(\'#contractId\').children().length==\'2\' && getSubAction() != \'viewObject\'){$(\'#contractId option:nth-child(2)\').attr(\'selected\', \'selected\');}if(getSubAction()==\'viewObject\'){$(\'#contractId\').val(\'<bean:write name="otHeaderForm" property="contractId" />\');}$(\'#contractId\').change();');
		});
		
	    // 绑定雇员ID keyup事件，加载雇员相关信息；
	    <logic:notEqual name="role" value="5">
			$('#employeeId').bind('keyup',function(){
				if( $(this).val().length >= 9 ){
					getEmployee();
				}
			});
		if($('.manageOT_form #subAction').val()=='viewObject'){
	     	$('#employeeId').trigger('keyup');
	    }
		</logic:notEqual>
	    
		
		<logic:equal name="role" value="2">
			if( getSubAction() == 'createObject' ){
				$('#employeeId').trigger('keyup');
			}
		</logic:equal>
		
		<%
			final String day = ( String )request.getAttribute( "currDay" );
			if( day != null && !"".equals( day ) )
			{
			   out.println( "if($('.manageOT_form input#subAction').val() == 'createObject'){" );
			   out.println( "$('.manageOT_form input#subAction').val('viewObject');" );
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
	
	 /**
	  * 自定义函数
	  **/
	function getEmployee(){
		$.ajax({
			url : "employeeAction.do?proc=get_object_json&employeeId=" + $("#employeeId").val() + "&attendance=ot&subAction=" + getSubAction(), 
			dataType : "json", 
			success : function(data){
				cleanError('manageOT_employeeId');
				cleanError('manageOT_clientId');
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
					addError('manageOT_employeeId', data.errorMsg );
				}
				
				if( getSubAction() != 'viewObject'){
					$('#estimateStartDate').val('');
				    $('#estimateEndDate').val('');
				    $('#estimateBenefitHours').val('');
				    $('#estimateLegalHours').val('');
				}
				// 非inhouse Load客户下拉框
				<logic:notEqual name="role" value="2">
					loadHtml('#clientId', 'clientAction.do?proc=list_object_options_ajax&flag=2&clientId=<bean:write name="otHeaderForm" property="clientId" />&employeeId=' + $("#employeeId").val(), getDisable(), 'if($(\'#clientId\').children().length==\'2\' && getSubAction() != \'viewObject\'){$(\'#clientId option:nth-child(2)\').attr(\'selected\', \'selected\');}if($(\'#clientId\').children().length==\'1\' ){addError(\'manageOT_clientId\', \'<bean:message bundle="public" key="error.no.ot.condition" />\');}$(\'#clientId\').change();');
				</logic:notEqual>
				// inhouse Load劳动合同下拉框
				<logic:equal name="role" value="2">
					var employeeId = $('#employeeId').val();
					if( data.errorMsg != null){
						employeeId = "0";
					}	
					loadHtml('#contractId', 'employeeContractAction.do?proc=list_object_options_ajax&flag=2&employeeId=' + employeeId + '&contractId=' + $('#contractId').val(), getDisable(), 'if($(\'#contractId\').children().length==\'2\' && getSubAction() != \'viewObject\'){$(\'#contractId option:nth-child(2)\').attr(\'selected\', \'selected\');}if(getSubAction()==\'viewObject\'){$(\'#contractId\').val(\'<bean:write name="otHeaderForm" property="contractId" />\');}$(\'#contractId\').change();');
				</logic:equal>
			}
		});
	 }
	  
	// 绑定开始、结束时间focus事件
	function focusDate( startDate, endDate , defDate ){
		 if( startDate != null && endDate != null && defDate != null){
			 focusStartDate( startDate, endDate, defDate);
			 focusEndDate( startDate, endDate, defDate );
		 }
	}; 
	
	// 开始时间focus事件
	function focusStartDate( startDate, endDate, defDate ){
		$('#estimateStartDate').focus(function(){
			$('#estimateEndDate').val('');
			$('#estimateHours').val('');
			<logic:notEmpty name="currDay">
				defDate = '<bean:write name="currDay" />' + ':00';
			</logic:notEmpty>
			startDate = getMinDate( startDate, 'start' );
			endDate = getMaxDate( endDate, 'start' );	
	    	WdatePicker({
	    		// WdatePicker插件内部事件，确认时
	    		onpicking :function(dp){
	    			calculateOTHours( dp.cal.getNewDateStr(), $('#estimateEndDate').val() );
				},
				// WdatePicker插件内部事件，清空时
				onclearing : function(dp){
					$('#estimateHours').val('');
				},
	    		startDate : defDate,
	    	    dateFmt:'yyyy-MM-dd HH:mm',
	    	    minDate:startDate,
	    	    maxDate:endDate, 
	    	    disabledDates:['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]']
	    	});
	    });
	};
	
	// 结束时间focus事件
	function focusEndDate( startDate, endDate, defDate ){
		 $('#estimateEndDate').focus(function(){
			startDate = getMinDate( startDate, 'end' );
			endDate = dateCompare( endDate,  getMaxDate( endDate, 'end' ) ) == 3 ? endDate : getMaxDate( endDate, 'end' );	
			<logic:notEmpty name="currDay">
				defDate = '<bean:write name="currDay" />' + ':00';
			</logic:notEmpty>
	    	WdatePicker({
	    		// WdatePicker插件内部事件，确认时
				onpicking :function(dp){
					$.ajax({
						url : "otHeaderAction.do?proc=check_ot_date&otHeaderId=" + $('#otHeaderId').val()+ "&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val() + "&endDate=" + dp.cal.getNewDateStr() ,
						success : function(html){
							if(html == 'true'){
								if( !$('.manageOT_estimateEndDate').hasClass('error') ){
									addError('manageOT_estimateEndDate', '<bean:message bundle="public" key="error.time.period.exist.ot" />');
									$('#estimateHours').val('');
								}
							}else{
								cleanError('manageOT_estimateEndDate');
							}
						} 
					});
				},
	    		// WdatePicker插件内部事件，确认时
	    		onpicked :function(dp){
	    			calculateOTHours( $('#estimateStartDate').val() , dp.cal.getNewDateStr() );
				},
				// WdatePicker插件内部事件，清空时
				onclearing : function(dp){
					$('#estimateHours').val('');
				},
				// WdatePicker插件内部事件，清空后
				oncleared : function(dp){
					if( $('.manageOT_estimateEndDate').hasClass('error') ){
						cleanError('manageOT_estimateEndDate');
					}
				},
	    		startDate : $('#estimateStartDate').val() == '' ? defDate : $('#estimateStartDate').val().split(':')[0] + ':00:00',
	    	    dateFmt:'yyyy-MM-dd HH:mm',
	    	    minDate:startDate,
	    	    maxDate:endDate,  
	    	    disabledDates:['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]']
	    	});
	    });
	};
	
	// 计算加班时间跨度（单位：小时）
	function calculateOTHours( startDate , endDate ){
		 var otHours = null;
		 if( getSubAction() != 'createObject'){
			 otHours = '<bean:write name="otHeaderForm" property="estimateHours" />';
		 }
		 
		 if(startDate != '' && endDate != ''){
    		 $.ajax({
				url : "otHeaderAction.do?proc=calculate_ot_hours_ajax&contractId=" + $('#contractId').val() + "&startDate=" + startDate + "&endDate=" + endDate + "&otHours=" + otHours,
				success : function(html){
					if(html != ''){
						if( !$('.manageOT_estimateEndDate').hasClass('error') ){
							$('#estimateHours').val(html);
		    			}	
					}
				 }
			 });
		 }
	};
	
	// 获取minDate
	function getMinDate( minDate, obj ){
		if( getDayForTimesheet(obj, 'min') != null ){
			return getDayForTimesheet(obj, 'min');
		}
		if(obj=='start'){
			return $('#estimateEndDate').val() != '' ? '#F{$dp.$D(\'estimateEndDate\',{d:'+ (-3) +'});}' : minDate;
		}else if(obj=='end'){
			return $('#estimateStartDate').val()!='' ? '#F{$dp.$D(\'estimateStartDate\')}' : minDate;
		}
	};
	
	// 获取maxDate
	function getMaxDate( maxDate, obj ){
		if( getDayForTimesheet(obj, 'max') != null ){
			return getDayForTimesheet(obj, 'max');
		}
		if(obj=='start'){
			return $('#estimateEndDate').val() != '' ? $('#estimateEndDate').val() : maxDate;
		}else if(obj=='end'){
			return $('#estimateStartDate').val() != '' ? '#F{$dp.$D(\'estimateStartDate\',{d:'+ 3 +'});}' : maxDate;
		}
	};
	
	function getDayForTimesheet(dType,m){
		var currDay = null;
		var nextDay = null;
		<logic:notEmpty name="currDay">
			currDay = '<bean:write name="currDay" />';
		</logic:notEmpty>
		<logic:notEmpty name="nextDay">
			nextDay = '<bean:write name="nextDay" />';
		</logic:notEmpty>
		if(currDay==null||nextDay==null){return null;}
		if(dType=='start'){
			if(m=='min'){
				return currDay;
			}else if(m=='max'){
				return $('#estimateEndDate').val()!='' ? $('#estimateEndDate').val() :  nextDay;
			}
		}else if(dType=='end'){
			if(m=='min'){
				return $('#estimateStartDate').val()!='' ? '#F{$dp.$D(\'estimateStartDate\')}' : currDay;
			}else if(m=='max'){
				return nextDay;
			}
		}
	};
	
	// 比较日期
	function dateCompare(date1,date2){
		date1 = date1.replace(/\-/gi,"/");
		date2 = date2.replace(/\-/gi,"/");
		var time1 = new Date(date1).getTime();
		var time2 = new Date(date2).getTime();
		if(time1 > time2){
			return 1;
		}else if(time1 == time2){
			return 2;
		}else{
			return 3;
		}
	};
	
	// 确认时，若改月考勤表非新建、退回状态则给出提示。
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
	
	// 获取当前SubAction
	function getSubAction(){
		return $('.manageOT_form input#subAction').val();
	};
	
	// 当前是否需要Disable
	function getDisable(){
		return getSubAction() == 'viewObject' ? true : false;
	};
	
	function getStatus(){
		return $('.manageOT_status').val();
	};
</script>
