<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.workflow.WorkflowDefineAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div class="top">
	<div id="messageWrapper">
		<logic:present name="MESSAGE_REQUIREMENTS">
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
		<input type="button" class="save" name="btnDefineRequirementsEdit" id="btnDefineRequirementsEdit" value="<bean:message bundle="public" key="button.add" />" /> 
		<input type="button" class="reset" name="btnDefineRequirementsCancel" id="btnDefineRequirementsCancel" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />	
		<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( 'selectedIds_req', '<bean:message bundle="public" key="popup.select.delete.records" />', '<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listRequirements_form', 'deleteObjects', null, null, null, 'tableWrapper_req');" />	
	</kan:auth>
</div>

<!-- defineRequirementsForm -->
<div id="defineRequirementsFormWrapper" style="display:none" >
	<jsp:include page="/contents/workflow/defineRequirements/form/manageWorkflowDefineRequirementsForm.jsp"></jsp:include>
</div>

	<!-- WorkflowDefineRequirements Form -->
	<html:form action="workflowDefineRequirementsAction.do?proc=list_object" styleClass="listRequirements_form">
		<input type="hidden" name="defineId" name="defineId" value="<bean:write name="workflowDefineForm" property="encodedId"/>"/>			
		<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="workflowDefineRequirementsHolder" property="sortColumn" />" /> 
		<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="workflowDefineRequirementsHolder" property="sortOrder" />" />
		<input type="hidden" name="page" id="page" value="<bean:write name="workflowDefineRequirementsHolder" property="page" />" />
		<input type="hidden" name="selectedIds_req" id="selectedIds_req" value="<bean:write name="workflowDefineRequirementsHolder" property="selectedIds" />" />
		<input type="hidden" name="subAction" id="subAction" value="" />					
	</html:form>
	
<div class="bottom"></div>		
<div id="tableWrapper_req">	
	<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
	<jsp:include page="/contents/workflow/defineRequirements/table/listWorkflowDefineRequirementsTable.jsp" flush="true"/>
</div>

<script type="text/javascript">
	(function($) {
		kanList_init("tableWrapper_req","selectedIds_req");	
		kanCheckbox_init("tableWrapper_req","selectedIds_req");
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>
		
		// WorkflowDefineRequirementsForm Add button����¼�
		$('#btnDefineRequirementsEdit').click( function(){
			// �ж�����ӡ��鿴�����޸�
			if($('.listRequirements_form input#subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnDefineRequirementsCancel').show();
				// ��ʾRequirements Form
				$('#defineRequirementsFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.listRequirements_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnDefineRequirementsEdit').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.listRequirements_form input#subAction').val() == 'viewObject'){
				// Enable����Form
				enableForm('workflowDefineRequirements_form');
				// ����SubActionΪ�༭
				$('.listRequirements_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnDefineRequirementsEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
	    		$('.workflowDefineRequirements_form').attr( 'action', 'workflowDefineRequirementsAction.do?proc=modify_object' );
			}else{
				// ��֤ 
				if( validate_defineRequirements_form()==0 ){
					// �ύform
					submit('workflowDefineRequirements_form');
				}
			}
		});
		
		// WorkflowDefineRequirementsForm Add Cancel����¼�
		$('#btnDefineRequirementsCancel').click(function(){
			if(agreest())
				link('workflowDefineAction.do?proc=to_objectModify&defineId=<bean:write name="workflowDefineForm" property="encodedId" />');
		});
	})(jQuery);

		// JS��֤workflowDefineRequirements_form
		function validate_defineRequirements_form(){
			var flag = 0;
			flag = flag + validate( 'manageWorkflowDefineRequirements_columnIndex', true, 'numeric', 2, 0 );
			flag = flag + validate( 'manageWorkflowDefineRequirements_compareType', true, 'select', 0, 0 );
			flag = flag + validate( 'manageWorkflowDefineRequirements_compareValue', true, 'numeric', 4, 0 );
			flag = flag + validate( 'manageWorkflowDefineRequirements_status', true, 'select', 0, 0 );
			return flag;
		};
		
		// Ajaxȥ�޸Ľ���
		function to_objectModify_requirements( requirementId ){
			var compareColumnName = '<logic:equal name="isLeaveAccessAction" value="true">���ʱ��</logic:equal><logic:equal name="isOTAccessAction" value="true">�Ӱ�ʱ��</logic:equal><em>*</em>';
			var callback = '$("#compareColumnId").html( "' + compareColumnName + '")';
			loadHtmlWithRecall('#defineRequirementsFormWrapper', 'workflowDefineRequirementsAction.do?proc=to_objectModify_ajax&requirementId=' + requirementId, true, callback); 
			// ��ʾCancel��ť
			$('#btnDefineRequirementsCancel').show();
			// ��ʾRequirements Form
			$('#defineRequirementsFormWrapper').show();	
			// �趨SubActionֵ������Add��Modify
			$('.listRequirements_form input#subAction').val('viewObject');
			// �޸İ�ť��ʾ����
			$('#btnDefineRequirementsEdit').val('<bean:message bundle="public" key="button.edit" />');		
		};
</script>