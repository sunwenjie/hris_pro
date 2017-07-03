<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div class="box" id="workflowDefine-information">
		<div class="head">
			<label>工作流设置</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_HEADER">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<!-- jspInclude 包含form表单对应jsp页面  formClass .workflowDefine_form -->
			<jsp:include page="/contents/workflow/define/form/manageWorkflowDefineForm.jsp"></jsp:include>
		</div>	
	</div>
		
	<!-- inner -->
	<div class="inner">
		<html:form action="workflowDefineStepsAction.do?proc=list_object" styleClass="liststeps_form">
			<input type="hidden" name="defineId" name="defineId" value="<bean:write name="workflowDefineForm" property="encodedId"/>"/>			
			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="workflowDefineStepsHolder" property="sortColumn" />" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="workflowDefineStepsHolder" property="sortOrder" />" />
			<input type="hidden" name="page" id="page" value="<bean:write name="workflowDefineStepsHolder" property="page" />" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="workflowDefineStepsHolder" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />					
		</html:form>
	</div>

	<div class="box" id="search-results">
		<div class="head">
			<label>工作流步骤 （自定义）</label>
		</div>
		<!-- Inner -->
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_DETAIL">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<!-- 隐藏的添加审核步骤 formClass .steps_form-->
				<input type="button" class="addbutton" name="btnDefineStepsAdd" id="btnDefineStepsAdd" value="<bean:message key="button.add" />" /> 
				<input type="button" class="reset" name="btnDefineStepsCancel" id="btnDefineStepsCancel" value="<bean:message key="button.cancel" />" style="display:none" />	
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('liststeps_form', 'deleteObjects', null, null, null, 'tableWrapper');" />	
		  	</div>
		  	<div id="defineSteps" style="display:none" >
				<jsp:include page="/contents/workflow/defineSteps/form/manageWorkflowDefineStepsForm.jsp"></jsp:include>
				<!-- 隐藏workflowDefine id -->
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/workflow/defineSteps/table/listWorkflowDefineStepsTable.jsp" flush="true"/>
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
			</div>
			<!-- frmList_ohrmListComponent -->
		</div>
		<!-- inner -->
		
	</div>
	<!-- search-results -->

</div>
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_workflow_Modules').addClass('current');			
		$('#menu_workflow_Configuration').addClass('selected');
		
		//------.workflowDefine_form  begin--------------
		//  编辑模式下 加载工作流触发权限
		$("#rightIds_div").load("workflowModuleAction.do?proc=list_Module_rightIds_html_checkBox",
				{"workflowModuleId":$('.workflowDefine_workflowModuleId').val(),"checkBoxName":"rightIdsArray","rightIds":'<bean:write name="workflowDefineForm" property="rightIds"/>'},
				function(){
					$(".workflowDefine_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
				});
		//  编辑模式下 如果作用范围是外部，则去掉审批过程类型的{基于组织架构}
		if($('.workflowDefine_scope').val()== 2){
			$("#workflowDefine_approvalType option ").each(function(){
				if($(this).val()==1){
			 		$(this).remove();
				}
			});
		}
		// disableForm Define表单
		disableForm('workflowDefine_form');
		
		// 初始化隐藏Define添加按钮 
		$('.workflowDefine_form input#btnSave').hide();
		
		// .workflowDefine_form 按钮提交事件
		function btnDefineSubmit() {
			var flag = 0;
			flag = flag + validate("workflowDefine_workflowModuleId", true, "select", 100, 0);
			flag = flag + validate("workflowDefine_nameZH", true, "common", 100, 0);
			flag = flag + validate("workflowDefine_nameEN", true, "common", 100, 0);
			flag = flag + validate("workflowDefine_scope", true, "select", 0, 0);
			flag = flag + validate("workflowDefine_approvalType", true, "select", 0, 0);
			flag = flag + validate("workflowDefine_status", true, "select", 0, 0);
			if(flag == 0){
				//全部 变为可编辑
        		enableForm('workflowDefine_form');
        		$('.workflowDefine_form .subAction').val('');
				submit('workflowDefine_form');
			}
		};
		
		// 作用范围workflowDefine_scope改变事件
		$('.workflowDefine_form .workflowDefine_scope').change(function (){
			var scope = $(this).val();
			var approvalTypeOptionHtml ="";
			var positionGradeIdOptionHtml ="";
			if(scope==0){
				$('.workflowDefine_form input #workflowDefine_approvalType').html("");
				$('.workflowDefine_form input .workflowDefine_positionGradeId').html("");
			}else if(scope==1){
				approvalTypeOptionHtml += $('.workflowDefine_form #workflowDefine_approvalType_0').html();
				positionGradeIdOptionHtml += $('.workflowDefine_form .workflowDefine_positionGradeId_0').html();
			}else if(scope==2){
				approvalTypeOptionHtml += $('.workflowDefine_form #workflowDefine_approvalType_2').html();
				positionGradeIdOptionHtml += $('.workflowDefine_form .workflowDefine_positionGradeId_2').html();
			}
			$(".workflowDefine_form #workflowDefine_approvalType").html(approvalTypeOptionHtml);
			$(".workflowDefine_form .workflowDefine_positionGradeId").html(positionGradeIdOptionHtml);
		});
		
		// 职位外部工作流时，更正下拉框值
		if($('.workflowDefine_form .workflowDefine_scope').val()==2){
			var value = $('.workflowDefine_form #workflowDefine_approvalType_0').val();
			$('.workflowDefine_form #workflowDefine_approvalType').html($('.workflowDefine_form #workflowDefine_approvalType_2').html());
			$('.workflowDefine_form #workflowDefine_approvalType').val(value);
			
			$('.workflowDefine_form .workflowDefine_positionGradeId').html($('.workflowDefine_form .workflowDefine_positionGradeId_2').html());
		}
		
		// Define编辑按钮点击事件			
        $('.workflowDefine_form input#btnEdit').click(function(){

        	var $subAction = $('.workflowDefine_form  .subAction ');
        	if($subAction.val() == 'viewObject'){
        		//全部 变为可编辑
        		enableForm('workflowDefine_form');
        		// 工作流模块权限 部分不可编辑
        		// $(".workflowDefine_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
        		//工作流模块id不可改
        		$('.workflowDefine_workflowModuleId').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.workflowDefine_form .subAction').val('modifyObject');
				// 更改按钮显示名
        		$('.workflowDefine_form #btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改action 名字
				$('.workflowDefine_form').attr('action','workflowDefineAction.do?proc=modify_object');
        	}else if($('.workflowDefine_form .subAction').val() == 'modifyObject'){
        		
        		//提交表单
        		btnDefineSubmit();
        	}
        });
		
		//Define 取消按钮单击事件
		$('.workflowDefine_form #btnList').click( function () {
			if(agreest())
			link('workflowDefineAction.do?proc=list_object');
		});	
		
		//------.workflowDefine_form  end--------------
		
		
		//DefineSteps 添加按钮点击事件
		$('#btnDefineStepsAdd').click(function(){
			// 判断是添加、查看还是修改
			if($('.steps_form .subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnDefineStepsCancel').show();
				// 显示Steps Detail Form
				$('#defineSteps').show();
				// 设置SubAction为新建
				$('.steps_form .subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnDefineStepsAdd').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.steps_form .subAction').val() == 'viewObject'){
				// 编辑操作，Enable整个Form
				enableForm('steps_form');
				// 设置SubAction为编辑
				$('.steps_form .subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnDefineStepsAdd').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.steps_form').attr('action', 'workflowDefineStepsAction.do?proc=modify_object');
			}else{
				var flag = 0;
				flag = flag + validate("workflowDefineSteps_stepType", true, "select", 0, 0);
				flag = flag + validate("positionId", true, "common", 4, 0);
				flag = flag + validate("workflowDefineSteps_stepIndex", true, "numeric", 4, 0);
				flag = flag + validate("workflowDefineSteps_status", true, "select", 0, 0);
				
				if(flag == 0){
					submit("steps_form");
				}
			}
		});
		
		// DefineSteps 取消按钮点击事件
		$('#btnDefineStepsCancel').click(function(){
			if(agreest())
			link('workflowDefineStepsAction.do?proc=list_object&defineId=<bean:write name="workflowDefineForm" property="encodedId" />');
		});
		
		// DefineSteps 添加时默认消息提示选项和 Define消息提示选项相同
		
		$('.steps_form input[name=sendEmail]').attr('checked',$('.workflowDefine_form input[name=sendEmail]').attr('checked'));
		$('.steps_form input[name=sendSMS]').attr('checked',$('.workflowDefine_form input[name=sendSMS]').attr('checked'));
		$('.steps_form input[name=sendInfo]').attr('checked',$('.workflowDefine_form input[name=sendInfo]').attr('checked'));
		
		kanList_init();
		kanCheckbox_init();
		
		// 绑定审核职位联想功能
		bindThinkingToPositionName();
	})(jQuery);
	
	
	function workflowDefineSteps(stepId){
		// 显示Cancel按钮
		$('#btnDefineStepsCancel').show();
		// 显示DefineSteps Detail Form
		$('#defineSteps').show();		
		// Ajax加载Option Detail修改页面
		//loadHtml('#defineSteps', 'workflowDefineStepsAction.do?proc=to_objectModify_ajax&stepId=' + stepId, true);
		
		$('#defineSteps').load('workflowDefineStepsAction.do?proc=to_objectModify_ajax&stepId=' + stepId,function(){
			// disableForm 表单
			disableForm('steps_form');
			
			//为 defineId 赋值
			$('.steps_form input#defineId').val('<bean:write name="workflowDefineForm" property="encodedId" />');
			// 修改按钮显示名称
			$('#btnDefineStepsAdd').val('<bean:message bundle="public" key="button.edit" />');
			
			// 绑定审核职位联想功能
			bindThinkingToPositionName();
		});
	};
	
	 //	Think事件
	function bindThinkingToPositionName(){
		// Use the common thinking
		kanThinking_column('workflowDefineSteps_positionId', 'positionId', 'positionAction.do?proc=list_object_json');
		
	};
</script>