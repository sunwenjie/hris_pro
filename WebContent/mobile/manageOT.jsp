<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="mobile/css/mobile.css" />
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>
<!-- mobiscroll -->
   <link href="plugins/PluginDatetime/css/mobiscroll.custom-2.5.2.min.css" rel="stylesheet" type="text/css" />
   <script src="plugins/PluginDatetime/js/mobiscroll.custom-2.5.2.min.js" data-main="scripts/main"></script>

<title><bean:message bundle="wx" key="wx.logon.title" /></title>
</head>
<body>
<div id="layout">
<html:form action="otHeaderAction.do?proc=add_object&mobile=1" styleClass="manageOT_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="timesheetId" name="timesheetId" value="<bean:write name="otHeaderForm" property="timesheetId"/>" />
	<input type="hidden" id="otHeaderId" name="id" value="<bean:write name="otHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="submitObject" />
	<input type="hidden" id="unread" name="unread" class="unread" value="1" />
	<input type="hidden" id="employeeId" name="employeeId" class="employeeId" value="<bean:write name="otHeaderForm" property="employeeId"/>" /> 
	<input type="hidden" id="status" name="status" class="status" value="1" /> 
	 
	<div class="waikuang">
	          <div class="neirong1"><sup><bean:message bundle="wx" key="wx.leave.contract" />：</sup>
	          	<select name="contractId" class="shurukuang6 manageLeave_contractId" id="contractId">
					<option value="0"><bean:message bundle="wx" key="wx.please.select" /></option>
				</select>
			  </div>
			  <div class="neirong1" id="Wdate_start"><sup><bean:message bundle="wx" key="wx.start.date" />：</sup>
			  	<html:text disabled="disabled" property="estimateStartDate" maxlength="20" readonly="true" styleClass="shurukuang7 manageLeave_estimateStartDate" styleId="estimateStartDate" />
			  </div>
			  <div class="neirong1" id="Wdate_end"><sup><bean:message bundle="wx" key="wx.end.date" />：</sup>
			  	<html:text disabled="disabled" property="estimateEndDate" maxlength="20" readonly="true" styleClass="shurukuang7 manageLeave_estimateEndDate" styleId="estimateEndDate" />
			  </div>
			  <div class="neirong2" style="border:none;"><sup><bean:message bundle="wx" key="wx.ot.hours" />：</sup>
			  	<input readonly="readonly" name="estimateHours" type="text" class="shurukuang8" id="estimateHours" />
			  </div>
	</div>
	</html:form>
	<div class="daiban" style="border:none;" id="otTime_special_info">
	   
	</div>
	<a href="#" class="button orange" onclick="btnClick();"><bean:message bundle="wx" key="wx.btn.confirm" /></a>
	<br clear="all" />
</div>
</body>
<script type="text/javascript">
	(function($) {	
		 // 绑定服务协议Change事件，绑定开始、结束时间focus事件； 
	    $('#contractId').bind('change',function(){
	    	// change之前清空日期控件绑定的事件
	    	// change之前清空日期控件绑定的事件
			$('#Wdate_start').html($('#Wdate_start').clone().html());
			$('#Wdate_end').html($('#Wdate_end').clone().html());
	    	
	    	if( $(this).val() != '' ){
	    		$.ajax({
					url : "otHeaderAction.do?proc=contract_change_ajax&contractId=" + $(this).val(), 
					dataType : "json",
					success : function(data){
						if( data.startDate!=undefined && data.endDate!=undefined){
							focusDate(data.startDate, data.endDate, data.defDate);
						}
					}
				});
	    		
	    		$.ajax({
					url : "otHeaderAction.do?proc=list_special_info_html_mobile&contractId=" + $(this).val(), 
					dataType : "json",
					success : function(data){
						var html = "<h2><bean:message bundle="wx" key="wx.ot.record" />：</h2>";
						if(data.length>0){
							for( var i=0;i<data.length;i++){
								html+="<h3>" +data[i].monthly+"："+data[i].totalOTHours+"<bean:message bundle="wx" key="wx.hour" />";
								html+="</h3>";
							}
						}
						$("#otTime_special_info").html(html);
					}
				});
	    		
	    		// Locad Special Info
				//loadHtml('#special_info', 'otHeaderAction.do?proc=list_special_info_html_mobile&contractId=' + $('#contractId').val(), false, null); 
	    	}
	    });
		
		//	加载劳动合同
		_loadHtml('#contractId', 'employeeContractAction.do?proc=list_object_options_ajax_mobile&flag=2', false, "checkContractId();");
		
	})(jQuery);
	
	// 绑定开始、结束时间focus事件
	function focusDate( startDate, endDate , defDate ){
	
		 if( startDate != null && endDate != null && defDate != null){
			 focusStartDate( startDate, endDate, defDate);
		 }
	}; 
	
	// 开始时间focus事件
	function focusStartDate( startDate, endDate, defDate ){
		$('#estimateEndDate').val('');
		$('#estimateHours').val('');
		<logic:notEmpty name="currDay">
			defDate = '<bean:write name="currDay" />' + ':00';
		</logic:notEmpty>
		var startDateStr = getMinDate( startDate, 'start' );
		var endDateStr = getMaxDate( endDate, 'start' );	
		var optDate = {  
	            preset: 'datetime', //日期  
	            theme: 'android-ics light', //皮肤样式  
	            display: 'bottom', //显示方式   
	            mode: 'scroller', //日期选择模式  
	            showNow: true,  
	            nowText: "今天",  
	           	minDate:new Date(startDateStr.replace(/-/g,"/")),
	           	maxDate:new Date(endDateStr.replace(/-/g,"/")),
	           	defaultValue :defDate,
	            stepMinute: 30,
				yearText: "年",
				monthText: "月",
				dayText: "日",
				hourText: "时",
				minuteText: "分",
				setText: '确定',
				cancelText: '取消',
				dateFormat: 'yy-mm-dd',
				timeFormat: 'HH:ii',
				timeWheels: 'HHii',
				dateOrder: 'yymmdd', 
				height: 108,
				width:140,
				onShow : function(valueText,inst){
					// 显示
				},
				onChange :function(valueText,inst){
					$('#estimateEndDate').val('');
				},
				onSelect: function(valueText,inst){
					calculateOTHours( valueText, $('#estimateEndDate').val() );
					focusEndDate( valueText, endDate, defDate );
				} ,
				onCancel: function(valueText,inst){
					// 取消
				} 
	        };  
			
			$("#estimateStartDate").mobiscroll(optDate);
			/*			
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
	    	    disabledDates:['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]'],
	    	    skin:'whyGreen'
	    	});*/
	};
	
	// 结束时间focus事件
	function focusEndDate( startDate, endDate, defDate ){
		var startDateStr = getMinDate( startDate, 'end' );
		var endDateStr = getMaxDate( endDate, 'end' );	
		<logic:notEmpty name="currDay">
			defDate = '<bean:write name="currDay" />' + ':00';
		</logic:notEmpty>
		var optDate = {  
	            preset: 'datetime', //日期  
	            theme: 'android-ics light', //皮肤样式  
	            display: 'bottom', //显示方式   
	            mode: 'scroller', //日期选择模式  
	            showNow: true,  
	            nowText: "今天",  
	           	minDate:new Date(startDateStr.replace(/-/g,"/")),
	           	maxDate:new Date(endDateStr.replace(/-/g,"/")),
	            stepMinute: 30,
				yearText: "年",
				monthText: "月",
				dayText: "日",
				hourText: "时",
				minuteText: "分",
				setText: '确定',
				cancelText: '取消',
				dateFormat: 'yy-mm-dd',
				timeFormat: 'HH:ii',
				timeWheels: 'HHii',
				dateOrder: 'yymmdd', 
				height: 108,
				width:140,
				onShow : function(valueText,inst){
					// 显示
				},
				onChange :function(valueText,inst){
					
				},
				onSelect: function(valueText,inst){
					// 选择
					$.ajax({
						url : "otHeaderAction.do?proc=check_ot_date&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val() + "&endDate=" + valueText ,
						success : function(html){
							if(html == 'true'){
								if( !$('.manageOT_estimateEndDate').hasClass('error') ){
									addError('manageOT_estimateEndDate', '<bean:message bundle="wx" key="wx.ot.oting.during" />；');
									$('#estimateHours').val('');
								}
							}
						} 
					});
					calculateOTHours( $('#estimateStartDate').val() , valueText );
				} ,
				onCancel: function(valueText,inst){
					// 取消
				} 
	        };  
		
		$("#estimateEndDate").mobiscroll(optDate);
		
			
	    	/* WdatePicker({
	    		// WdatePicker插件内部事件，确认时
				onpicking :function(dp){
					$.ajax({
						url : "otHeaderAction.do?proc=check_ot_date&employeeId=" + $('#employeeId').val() + "&contractId=" + $('#contractId').val() + "&startDate=" + $('#estimateStartDate').val() + "&endDate=" + dp.cal.getNewDateStr() ,
						success : function(html){
							if(html == 'true'){
								if( !$('.manageOT_estimateEndDate').hasClass('error') ){
									addError('manageOT_estimateEndDate', '该时间段已经加过班；');
									$('#estimateHours').val('');
								}
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
	    		startDate : $('#estimateStartDate').val() == '' ? defDate : $('#estimateStartDate').val().split(':')[0] + ':00:00',
	    	    dateFmt:'yyyy-MM-dd HH:mm',
	    	    minDate:startDate,
	    	    maxDate:endDate,  
	    	    disabledDates:['....-..-.. ..\:(0|3)[1-9]','....-..-.. ..\:(1|2|4|5)[0-9]'],
	    	    skin:'whyGreen'
	    	}); */
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
		if(obj=='start'){
			return  minDate;
		}else if(obj=='end'){
			return $('#estimateStartDate').val()==''?minDate:$('#estimateStartDate').val();
		}
	};
	
	// 获取maxDate
	function getMaxDate( maxDate, obj ){
		if(obj=='start'){
			return  maxDate;
		}else if(obj=='end'){
			return maxDate;
		}
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
	
	function btnClick(){
		if(confirm("<bean:message bundle="wx" key="wx.confirm.submit" />?"))
		$(".manageOT_form").submit();
	};
	
	function checkContractId(){
		var len = $("#contractId").find("option").length;
		if(len==2){
			// 如果只有一个则选中这个
			$("#contractId option:nth-child(2)").attr("selected","selected");
			$("#contractId").change();
		} 
	};
	
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
</script>

