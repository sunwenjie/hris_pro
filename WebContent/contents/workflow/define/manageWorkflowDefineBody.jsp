<%@ page import="com.kan.base.web.actions.workflow.WorkflowDefineAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="workflowDefine-information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="workflow" key="workflow.define.title.setting" /></label>
		    <logic:notEmpty name="workflowDefineForm" property="defineId">
       			<label class="recordId"> &nbsp; (ID: <bean:write name="workflowDefineForm" property="defineId" />)</label>
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
				<kan:auth right="modify" action="<%=WorkflowDefineAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
				</kan:auth>	
				<kan:auth right="list" action="<%=WorkflowDefineAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth> 
			</div>
			<!-- jspInclude 包含form表单对应jsp页面  formClass .workflowDefine_form -->
			<jsp:include page="/contents/workflow/define/form/manageWorkflowDefineForm.jsp"></jsp:include>
			
			<!-- tab部分 -->
			<logic:notEmpty name="workflowDefineForm" property="defineId">
				<logic:equal name="isLeaveOrOTAccessAction" value="true">
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,3)" class="first hover">1 - <bean:message bundle="public" key="menu.table.title.trigger.condition" /></li> 
							<!-- 外部工作流无需补充条件 -->
							<logic:equal name="workflowDefineForm" property="scope" value="1">
								<li id="tabMenu2" onClick="changeTab(2,3)" >2 - <bean:message bundle="public" key="menu.table.title.supplementary.steps" /></li> 
							</logic:equal>
						</ul> 
					</div> 
					<div class="tabContent"> 
						<div id="tabContent1" class="kantab kanThinkingCombo" >
							<!-- 包含step1页面 -->
							<jsp:include page="/contents/workflow/defineRequirements/includePage/manageWorkflowDefineRequirements.jsp" flush="true"></jsp:include>	
						</div>
						<!-- 外部工作流无需补充条件 -->
						<logic:equal name="workflowDefineForm" property="scope" value="1">
						<div id="tabContent2" class="kantab kanThinkingCombo" style="display:none">
							<!-- 包含step2页面 -->
							<jsp:include page="/contents/workflow/defineSteps/includePage/manageWorkflowDefineSteps.jsp" flush="true"></jsp:include>	
						</div>
						</logic:equal>
					</div>
				</logic:equal>
				<logic:equal name="isLeaveOrOTAccessAction" value="false">
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu2" class="first hover"><bean:message bundle="public" key="menu.table.title.supplementary.steps" /></li> 
						</ul> 
					</div> 
					<div class="tabContent"> 
						<div id="tabContent2" class="kantab kanThinkingCombo" class="first hover">
							<!-- 包含step2页面 -->
							<jsp:include page="/contents/workflow/defineSteps/includePage/manageWorkflowDefineSteps.jsp" flush="true"></jsp:include>	
						</div>
					</div>
				</logic:equal>
			</logic:notEmpty>
		</div>	
	</div>	
</div>

<script type="text/javascript">
	(function($) {
		// 初始化菜单样式
		$('#menu_workflow_Modules').addClass('current');			
		$('#menu_workflow_Configuration').addClass('selected');
		
		// 工作流模块绑定 change事件
		$(".workflowDefine_workflowModuleId").change(function (){
			var workflowModuleId = $(this).val();
			if(workflowModuleId==0){
				$("#rightIds_div").html("<label><bean:message bundle="workflow" key="workflow.define.trigger.right.init" /></label>");
			}else{
				$("#rightIds_div").load("workflowModuleAction.do?proc=list_Module_rightIds_html_checkBox",{"workflowModuleId":workflowModuleId,"checkBoxName":"rightIdsArray"});
			}
		});
		
		// 保存按钮点击事件
		$('#btnEdit').click(function(){
			if( getSubAction() == 'viewObject' ){
				// Enable整个Form
        		enableForm('workflowDefine_form');
        		// 工作流模块id不可改
        		$('.workflowDefine_workflowModuleId').attr('disabled', 'disabled');
        		// 设置当前Form的SubAction为修改状态
        		$('.workflowDefine_form input#subAction').val('modifyObject'); 
        		// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改action 名字
				$('.workflowDefine_form').attr('action','workflowDefineAction.do?proc=modify_object');
			}else{
				validate_form();
			}
		});
		
		// 列表按钮单击事件
		$('#btnList').click( function(){
			if(agreest())
				link('workflowDefineAction.do?proc=list_object');
		});	
		
		// 新建、修改模式下的JS动作
		if( getSubAction() != 'createObject' ){
			// form不可编辑
			disableForm('workflowDefine_form');
			// 加载工作流触发权限
			$("#rightIds_div").load("workflowModuleAction.do?proc=list_Module_rightIds_html_checkBox",
					{"workflowModuleId":$('.workflowDefine_workflowModuleId').val(),"checkBoxName":"rightIdsArray","rightIds":'<bean:write name="workflowDefineForm" property="rightIds"/>'},
					function(){
						$(".workflowDefine_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
					});
		} else if ( getSubAction() == 'createObject' ) {
			// 更改按钮值
		    $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		}
		
	})(jQuery);
	
	// 验证表单
	function validate_form() {
		var flag = 0;
		
		flag = flag + validate("workflowDefine_workflowModuleId", true, "select", 100, 0);
		flag = flag + validate("workflowDefine_nameZH", true, "common", 100, 0);
		flag = flag + validate("workflowDefine_scope", true, "select", 0, 0);
		flag = flag + validate("workflowDefine_approvalType", true, "select", 0, 0);
		flag = flag + validate("workflowDefine_status", true, "select", 0, 0);
		
		cleanError("rightIds_div");
		var chkLength = $("input[name='rightIdsArray']:checked").length;
		if(chkLength==0){
			flag = flag + 1;
			addError("rightIds_div","至少勾选一项");
		}
		
		if( flag == 0 ){
			// enable整个Form
    		enableForm('workflowDefine_form');
    		$('.workflowDefine_form .subAction').val('');
			submit('workflowDefine_form');
		}
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.workflowDefine_form input#subAction').val();
	};
</script>