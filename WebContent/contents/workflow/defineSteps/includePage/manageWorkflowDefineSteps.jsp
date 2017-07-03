<%@ page import="com.kan.base.web.actions.workflow.WorkflowDefineAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div class="top">
	<div id="messageWrapper">
		<logic:present name="MESSAGE_STEPS">
			<logic:present name="MESSAGE">
				<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
					<bean:write name="MESSAGE" />
	    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
				</div>
			</logic:present>
		</logic:present>
	</div>
	<!-- 隐藏的添加审核步骤 formClass .steps_form-->
	<kan:auth right="modify" action="<%=WorkflowDefineAction.accessAction%>">
		<input type="button" class="save" name="btnDefineStepsEdit" id="btnDefineStepsEdit" value="<bean:message bundle="public" key="button.add" />" /> 
		<input type="button" class="reset" name="btnDefineStepsCancel" id="btnDefineStepsCancel" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />	
		<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm('selectedIds_steps', '<bean:message bundle="public" key="popup.select.delete.records" />' , '<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('liststeps_form', 'deleteObjects', null, null, null, 'tableWrapper_steps');" />	
 	</kan:auth>
</div>

<!-- defineRequirementsForm -->
<div id="defineStepsFormWrapper" style="display:none" >
	<jsp:include page="/contents/workflow/defineSteps/form/manageWorkflowDefineStepsForm.jsp"></jsp:include>
</div>

	<!-- WorkflowDefineSteps Form -->
	<html:form action="workflowDefineStepsAction.do?proc=list_object" styleClass="liststeps_form">
		<input type="hidden" name="defineId" name="defineId" value="<bean:write name="workflowDefineForm" property="encodedId"/>"/>			
		<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="workflowDefineStepsHolder" property="sortColumn" />" /> 
		<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="workflowDefineStepsHolder" property="sortOrder" />" />
		<input type="hidden" name="page" id="page" value="<bean:write name="workflowDefineStepsHolder" property="page" />" />
		<input type="hidden" name="selectedIds_steps" id="selectedIds_steps" value="<bean:write name="workflowDefineStepsHolder" property="selectedIds" />" />
		<input type="hidden" name="subAction" id="subAction" value="" />					
	</html:form>
	
<div class="bottom"></div>
<div id="tableWrapper_steps">
	<!-- Include table jsp 包含tabel对应的jsp文件 -->  
	<jsp:include page="/contents/workflow/defineSteps/table/listWorkflowDefineStepsTable.jsp" flush="true"/>
</div>

<script type="text/javascript">
	(function($) {
		kanList_init("tableWrapper_steps","selectedIds_steps");	
		kanCheckbox_init("tableWrapper_steps","selectedIds_steps");
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>

		// WorkflowDefineStepsForm Add button点击事件
		$('#btnDefineStepsEdit').click(function(){
			cleanError( "workflowDefineSteps_positionName" );
			cleanError( "workflowDefineSteps_staffName" );
			// 判断是添加、查看还是修改
			if($('.workflowDefineSteps_form .subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnDefineStepsCancel').show();
				// 显示Steps Detail Form
				$('#defineStepsFormWrapper').show();
				// 设置SubAction为新建
				$('.workflowDefineSteps_form .subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnDefineStepsEdit').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.workflowDefineSteps_form .subAction').val() == 'viewObject'){
				// 编辑操作，Enable整个Form
				enableForm('workflowDefineSteps_form');
				// 设置SubAction为编辑
				$('.workflowDefineSteps_form .subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnDefineStepsEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
	    		$('.workflowDefineSteps_form').attr('action', 'workflowDefineStepsAction.do?proc=modify_object');
			}else{
				var flag = 0;
				
				flag = flag + validate("workflowDefineSteps_stepType", true, "select", 0, 0);
				flag = flag + validate("workflowDefineSteps_auditType", true, "select", 0, 0);
				
				if( $('.workflowDefineSteps_auditType').val() == 1){
					if( $('.workflowDefineSteps_positionId').val() == ''){
						flag = flag + 1;
						addError( "workflowDefineSteps_positionName" , "请检查联想是否生效；" );
					}
				}else if( $('.workflowDefineSteps_auditType').val() == 4){
					if( $('.workflowDefineSteps_staffId').val() == ''){
						flag = flag + 1;
						addError( "workflowDefineSteps_staffName" , "请检查联想是否生效；" );
					}
				}
				if( $('.workflowDefine_form select#workflowDefine_approvalType').val() == '1'){
					flag = flag + validate("workflowDefineSteps_stepIndex", true, "numeric", 4, 0);
				}
				flag = flag + validate("workflowDefineSteps_status", true, "select", 0, 0);
				
				if( flag == 0 ){
					submit("workflowDefineSteps_form");
				}
			}
		});
		
		// DefineSteps 取消按钮点击事件
		$('#btnDefineStepsCancel').click(function(){
			if( agreest() )
				link('workflowDefineAction.do?proc=to_objectModify&&defineId=<bean:write name="workflowDefineForm" property="encodedId" />');
		});
	})(jQuery);
	
	// Ajax去修改界面
	function to_objectModify_steps( stepId ){
		loadHtmlWithRecall('#defineStepsFormWrapper', 'workflowDefineStepsAction.do?proc=to_objectModify_ajax&stepId=' + stepId, true, null ); 
		// 显示Cancel按钮
		$('#btnDefineStepsCancel').show();
		// 显示DefineSteps Detail Form
		$('#defineStepsFormWrapper').show();	
		// 设定SubAction值，区分Add和Modify
		$('.liststeps_form input#subAction').val('viewObject');
		// 修改按钮显示名称
		$('#btnDefineStepsEdit').val('<bean:message bundle="public" key="button.edit" />');
		// 绑定审核职位联想功能
		bindThinkingToPositionName();
		bindThinkingToStaffName();
	};
</script>