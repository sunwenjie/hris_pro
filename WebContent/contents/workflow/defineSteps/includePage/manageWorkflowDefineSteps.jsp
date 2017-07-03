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
	<!-- ���ص������˲��� formClass .steps_form-->
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
	<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
	<jsp:include page="/contents/workflow/defineSteps/table/listWorkflowDefineStepsTable.jsp" flush="true"/>
</div>

<script type="text/javascript">
	(function($) {
		kanList_init("tableWrapper_steps","selectedIds_steps");	
		kanCheckbox_init("tableWrapper_steps","selectedIds_steps");
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>

		// WorkflowDefineStepsForm Add button����¼�
		$('#btnDefineStepsEdit').click(function(){
			cleanError( "workflowDefineSteps_positionName" );
			cleanError( "workflowDefineSteps_staffName" );
			// �ж�����ӡ��鿴�����޸�
			if($('.workflowDefineSteps_form .subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnDefineStepsCancel').show();
				// ��ʾSteps Detail Form
				$('#defineStepsFormWrapper').show();
				// ����SubActionΪ�½�
				$('.workflowDefineSteps_form .subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnDefineStepsEdit').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.workflowDefineSteps_form .subAction').val() == 'viewObject'){
				// �༭������Enable����Form
				enableForm('workflowDefineSteps_form');
				// ����SubActionΪ�༭
				$('.workflowDefineSteps_form .subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnDefineStepsEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
	    		$('.workflowDefineSteps_form').attr('action', 'workflowDefineStepsAction.do?proc=modify_object');
			}else{
				var flag = 0;
				
				flag = flag + validate("workflowDefineSteps_stepType", true, "select", 0, 0);
				flag = flag + validate("workflowDefineSteps_auditType", true, "select", 0, 0);
				
				if( $('.workflowDefineSteps_auditType').val() == 1){
					if( $('.workflowDefineSteps_positionId').val() == ''){
						flag = flag + 1;
						addError( "workflowDefineSteps_positionName" , "���������Ƿ���Ч��" );
					}
				}else if( $('.workflowDefineSteps_auditType').val() == 4){
					if( $('.workflowDefineSteps_staffId').val() == ''){
						flag = flag + 1;
						addError( "workflowDefineSteps_staffName" , "���������Ƿ���Ч��" );
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
		
		// DefineSteps ȡ����ť����¼�
		$('#btnDefineStepsCancel').click(function(){
			if( agreest() )
				link('workflowDefineAction.do?proc=to_objectModify&&defineId=<bean:write name="workflowDefineForm" property="encodedId" />');
		});
	})(jQuery);
	
	// Ajaxȥ�޸Ľ���
	function to_objectModify_steps( stepId ){
		loadHtmlWithRecall('#defineStepsFormWrapper', 'workflowDefineStepsAction.do?proc=to_objectModify_ajax&stepId=' + stepId, true, null ); 
		// ��ʾCancel��ť
		$('#btnDefineStepsCancel').show();
		// ��ʾDefineSteps Detail Form
		$('#defineStepsFormWrapper').show();	
		// �趨SubActionֵ������Add��Modify
		$('.liststeps_form input#subAction').val('viewObject');
		// �޸İ�ť��ʾ����
		$('#btnDefineStepsEdit').val('<bean:message bundle="public" key="button.edit" />');
		// �����ְλ���빦��
		bindThinkingToPositionName();
		bindThinkingToStaffName();
	};
</script>