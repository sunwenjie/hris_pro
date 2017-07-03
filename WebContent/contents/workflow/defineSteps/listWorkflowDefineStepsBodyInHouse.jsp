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
			<jsp:include page="/contents/workflow/define/form/manageWorkflowDefineFormInHouse.jsp"></jsp:include>
			<div class="tabMenu"> 
				<ul> 
					<li id="tabMenu1" onClick="changeTab(1,3)" class="first hover">1 - 触发条件</li> 
					<li id="tabMenu2" onClick="changeTab(2,3)" >2 - 步骤补充</li> 
				</ul> 
			</div> 
			<div class="tabContent"> 
				<div id="tabContent1" class="kantab kanThinkingCombo" >
					<!-- 包含step1页面 -->
<%-- 					<jsp:include page="/contents/workflow/defineSteps/includePage/manageDefineSteps.jsp" flush="true"></jsp:include>	 --%>
				</div>
				<div id="tabContent2" class="kantab kanThinkingCombo" style="display:none">
					<!-- 包含step2页面 -->
					<jsp:include page="/contents/workflow/defineSteps/includePage/manageWorkflowDefineSteps.jsp" flush="true"></jsp:include>	
				</div>
				
			</div>
		</div>	
		
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
			flag = flag + validate("workflowDefine_approvalType", true, "select", 0, 0);
			flag = flag + validate("workflowDefine_status", true, "select", 0, 0);
			if(flag == 0){
				//全部 变为可编辑
        		enableForm('workflowDefine_form');
        		$('.workflowDefine_form .subAction').val('');
				submit('workflowDefine_form');
			}
		};
		
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
		
	})(jQuery);
</script>