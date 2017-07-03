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
<%-- 是否因输入错误重置页面 --%>
<input id="rePageFlag_orderId" name="rePageFlag_orderId" type="hidden" value="false"/>
<input id="rePageFlag_employeeId" name="rePageFlag_employeeId" type="hidden" value="false"/>

<script type="text/javascript">
	(function($) {	
		// JS of the List - 提交时将Status Disabled 改为False
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
		// 添加“快速查看日历”
		$('#calendarId').after('&nbsp;&nbsp;<a class="kanhandle" onclick="quickCalendarPopup(\'<bean:write name="employeeContractForm" property="encodedCalendarId" />\');"><img src="images/search.png" title="快速查看日历" /></a>');
		/**
       	* define  定义变量 
       	**/
		// 添加“搜索信息”
		$('#clientName').after('<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="搜索客户记录" /></a>');
		
		// 非新增模式添加“查看客户信息”，根据条件添加“查看雇员信息”、“查看订单信息”
		if(getSubAction() != 'createObject'){
			$('#clientIdLI label').append(' <a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedClientId" />\');" ><img src="images/find.png" title="查看客户记录" /></a>');
			
			if( $('#rePageFlag_orderId').val() == 'false')
			{
				$('#orderIdLI label').append(' <a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedOrderId" />\');" ><img src="images/find.png" title="查看订单记录" /></a>');
			}
			
			if( $('#rePageFlag_employeeId').val() == 'false')
			{
				$('#employeeIdLI label').append(' <a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="查看雇员记录" /></a>');
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
       * loadHtml 加载页面
       **/

        loadHtml('#special_info', 'employeeContractAction.do?proc=list_special_info_html&contractId=' + $('#id').val(),disable);
       
    	// 初始化部门控件
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// 初始化主合同下拉框
		loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
		
		// 初始化加载合同模板下拉框
		loadLaborContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
		
		// 初始化工作流查看按钮
		loadWorkflowSeach('status','<bean:write name="employeeContractForm" property="workflowId"/>');
       /**
       * bind 绑定事件
       **/
		
		$('#btnNext').click( function () { generateContract(); } );

		// 盖章或归档按钮
		if($('#status').val()=='3'){
			$('#btnSealed').show().click(function(){
				$('#status').val('5');
				enableForm('manage_primary_form');
				$('.manage_primary_form').attr('action','employeeContractAction.do?proc=modify_object');
				$('.manage_primary_form').submit();
			});
		}else if($('#status').val()=='5'){
			$('#btnArchive').val('填写归档编号').show().click(function(){
				if(this.value=='填写归档编号'){
					$(this).val('归档');
					$('#contractNo').attr('disabled',false).focus();
				}else{
					$('#status').val('6');
					enableForm('manage_primary_form');
					$('.manage_primary_form').attr('action','employeeContractAction.do?proc=modify_object');
					$('.manage_primary_form').submit();
				}
			});
		}
		
		// 绑定列表按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('employeeContractAction.do?proc=list_object');
		});
		
		// 绑定部门Change事件
		$('.branch').change( function () {
			branchChange('branch', null, 0, 'owner');
		});	
		
		// 绑定法务实体change事件
		$('#entityId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
			loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
		});
		
		//	绑定业务类型change事件
		$('#businessTypeId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractForm" property="templateId"/>');
				// 自动生成服务协议名称
				if($('#businessTypeId').val() != '' && $('#businessTypeId').val() != null){
				var date = new Date();
				var contractNameZH = $('#businessTypeId option:selected').text() + '劳动合同（' + $('#employeeNameZH').val() + '） - ' + date.getFullYear();
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
		
		// 页面初始化 - 显示 “提交”按钮 如果状态为新建或者是退回
		if("<bean:write name='employeeContractForm' property='status' />" == 1||"<bean:write name='employeeContractForm' property='status' />" == 4){
			$('#btnSubmit').show();
		}
		$('#btnSubmit').click( function(){
			enableForm('manage_primary_form'); 
			// 如果是新建，点击提交到add方法。如果是修改点击提交到modify方法
			if(getSubAction() == 'viewObject'){
				// 如果是修改
				if("<bean:write name='employeeContractForm' property='status' />" == 1||"<bean:write name='employeeContractForm' property='status' />" == 4){
					$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=modify_object&flag=1');
				}
			}else{
				// 新建
				$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=add_object&flag=1');
			}
			$('.manage_primary_form input#subAction').val("submitObject"); 
			$('#btnEdit').click();
		} );
		
       /***
       * init 初始化相关JS
       **/
		
		//如果法务实体除了请选择只有一个，默认选中
	    var entityIdLength = $('#entityId option').length;
		if(entityIdLength==2 && $('#entityId option')[0].value==0){
			$("#entityId option:nth-child(2)").attr("selected" , "selected");  
			
		}
		
		//如果排班了请选择只有一个，默认选中
	    var shiftIdLength = $('#shiftId option').length;
		if( shiftIdLength==2 && $('#shiftId option')[0].value==0){
			$("#shiftId option:nth-child(2)").attr("selected" , "selected");  
		}
       
		// 初始化时状态不可改变
		$('#status').attr('disabled',true);
		$('#employeeNameZH').attr('disabled',true);
		$('#employeeNameEN').attr('disabled',true);
		
		// 初始化日期为readonly
		$('#startDate,#endDate').attr("readonly","readonly");
		
		// 初始化将<input type="hidden" name="comeFrom"加入到from表单
		$('.manage_primary_form').append($('#comeFrom'));
		
		// 显示“下一步”按钮
		$('#btnNext').show();
		
		// 如果是待审核或者结束状态，隐藏编辑按钮
		if( $('#status').val() == '2' || $('#status').val() == '7'){
			$('#btnEdit').hide();
		}
		
		// 添加“搜索雇员信息”
		$('#employeeId').after('<a onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="搜索雇员记录" /></a>');
		
		// 雇员ID输入事件
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
								// 自动生成服务协议名称
								var date = new Date();
								var contractNameZH = $('#businessTypeId option:selected').text() + '劳动合同（' + $('#employeeNameZH').val() + '） - ' + date.getFullYear();
								var contractNameEN = $('#businessTypeId option:selected').text() + '劳动合同（' + $('#employeeNameEN').val() + '） - ' + date.getFullYear();
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
								
								// 初始化主合同下拉框
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
				addError('employeeId', '雇员ID无效；');
			}else{
				cleanError('employeeId');
			}
		});
		
		// 劳动合同开始时间和协议结束时间，失去焦点事件   验证该时间段内是否已存在劳动合同
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
						addError('startDate', '该时间段相同法务实体已存在劳动合同；');
						overlap = true;
					}else{
						cleanError('startDate');
						overlap = false;
					}
				}
			});
			return overlap;
		};
		
		// 查看模式 附件添加
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
	
	//	验证合同模板事件
	function checkContractTemplate(){
		
		//	获得模板ID
		var templateID = $('#templateId').val();
		//	获得用户上传附件
		var attachmentLength = $('#attachmentsOL > li').length;
		
		if( templateID == 0 && attachmentLength == 0 )
		{
			return true;
		}else{
			return false;		
		};
		
	};
	
	// 加载主合同下拉框事件
	function loadContractOptions(masterContractId){
		loadHtml('#masterContractId', 'employeeContractAction.do?proc=list_object_options_ajax&contractId=' + masterContractId + '&employeeId=' + $('#employeeId').val()+ '&entityId=' + $('#entityId').val(), false );
	};
	
	
	// 加载合同模板下拉框事件
	function loadLaborContractTemplateOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val()+'&clientIdPage='+$('#clientId').val()+'&contractTypeId=1', false );
	};
	
	// 根据选中的客户名称，填充客户的劳动合同中文名和英文名
	function thinkingEmployeeNameCallBack(event,item){
		if(getSubAction() != 'viewObject'){// 跳转到VIEW页面时不需要根据劳动合同名称填充中文名和英文名称
			var employeeNameArray = item['employeeName'].split('-');
			var year = new Date().getYear()+1900;
			$('#nameZH').val('劳动合同 '+employeeNameArray[0]+'（'+year+'）');
			$('#nameEN').val('Labor Contract '+employeeNameArray[1]+'('+year+')');
			
			loadContractOptions('<bean:write name="employeeContractForm" property="masterContractId"/>');
		}
	}
</script>

