<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div class="box" id="workflowDefine-information">
		<div class="head">
			<label><bean:message bundle="workflow" key="workflow.define.title.setting" /></label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE_DEFINE">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<!-- jspInclude 包含form表单对应jsp页面  formClass .workflowDefine_form -->
			<jsp:include page="/contents/workflow/define/form/manageWorkflowDefineFormInHouse.jsp"></jsp:include>
			<!-- tab  部分 -->
			<logic:notEmpty name="workflowDefineForm" property="defineId">
				<logic:equal name="isLeaveOrOTAccessAction" value="true">
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,2)" class="first hover">1 - <bean:message bundle="public" key="menu.table.title.trigger.condition" /></li> 
							<li id="tabMenu2" onClick="changeTab(2,2)" >2 - <bean:message bundle="public" key="menu.table.title.supplementary.steps" /></li> 
						</ul> 
					</div> 
					<div class="tabContent"> 
						<div id="tabContent1" class="kantab kanThinkingCombo" >
							<!-- 包含step1页面 -->
							<jsp:include page="/contents/workflow/defineRequirements/includePage/manageWorkflowDefineRequirements.jsp" flush="true"></jsp:include>	
						</div>
						<div id="tabContent2" class="kantab kanThinkingCombo" style="display:none">
							<!-- 包含step2页面 -->
							<jsp:include page="/contents/workflow/defineSteps/includePage/manageWorkflowDefineSteps.jsp" flush="true"></jsp:include>	
						</div>
					</div>
				</logic:equal>
				<logic:equal name="isLeaveOrOTAccessAction" value="false">
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" class="first hover"><bean:message bundle="public" key="menu.table.title.supplementary.steps" /></li> 
						</ul> 
					</div> 
					<div class="tabContent"> 
						<div id="tabContent2" class="kantab kanThinkingCombo"  class="first hover">
							<!-- 包含step2页面 -->
							<jsp:include page="/contents/workflow/defineSteps/includePage/manageWorkflowDefineSteps.jsp" flush="true"></jsp:include>	
						</div>
					</div>
				</logic:equal>
			</logic:notEmpty>
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
		if( $('.workflowDefine_form input#subAction').val() == 'viewObject'){
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
		}
		
		// .workflowDefine_form 按钮提交事件
		function btnDefineSubmit() {
			var flag = 0;
			flag = flag + validate("workflowDefine_workflowModuleId", true, "select", 100, 0);
			flag = flag + validate("workflowDefine_nameZH", true, "common", 100, 0);
			flag = flag + validate("workflowDefine_approvalType", true, "select", 0, 0);
			flag = flag + validate("workflowDefine_status", true, "select", 0, 0);
			var chkLength = $("input[name='rightIdsArray']:checked").length;
			if(chkLength==0){
				flag = flag + 1;
				cleanError("rightIds_div");
				addError("rightIds_div",'<bean:message bundle="public" key="error.not.check" />');
			}else{
				cleanError("rightIds_div");
			}
			if(flag == 0){
				//全部 变为可编辑
        		enableForm('workflowDefine_form');
        		$('.workflowDefine_form .subAction').val('');
				submit('workflowDefine_form');
			}
		};
		// 保存按钮点击事件
		$('.workflowDefine_form input#btnSave').click( function () { 
			btnDefineSubmit();
		});
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
		
		// 工作流模块绑定 onChange事件
		$(".workflowDefine_workflowModuleId").change(function (){
			var workflowModuleId = $(this).val();
			if(workflowModuleId==0){
				$("#rightIds_div").html("<bean:message bundle="workflow" key="workflow.define.trigger.right.init" /></label>");
			}else{
				$("#rightIds_div").load("workflowModuleAction.do?proc=list_Module_rightIds_html_checkBox",{"workflowModuleId":workflowModuleId,"checkBoxName":"rightIdsArray"});
			}
		});
		//------.workflowDefine_form  end--------------
	})(jQuery);
</script>