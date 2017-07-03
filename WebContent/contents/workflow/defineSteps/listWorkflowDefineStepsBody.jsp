<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div class="box" id="workflowDefine-information">
		<div class="head">
			<label>����������</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_HEADER">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">����ɹ���</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">�༭�ɹ���</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<!-- jspInclude ����form����Ӧjspҳ��  formClass .workflowDefine_form -->
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
			<label>���������� ���Զ��壩</label>
		</div>
		<!-- Inner -->
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_DETAIL">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">����ɹ���</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">�༭�ɹ���</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<!-- ���ص������˲��� formClass .steps_form-->
				<input type="button" class="addbutton" name="btnDefineStepsAdd" id="btnDefineStepsAdd" value="<bean:message key="button.add" />" /> 
				<input type="button" class="reset" name="btnDefineStepsCancel" id="btnDefineStepsCancel" value="<bean:message key="button.cancel" />" style="display:none" />	
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('liststeps_form', 'deleteObjects', null, null, null, 'tableWrapper');" />	
		  	</div>
		  	<div id="defineSteps" style="display:none" >
				<jsp:include page="/contents/workflow/defineSteps/form/manageWorkflowDefineStepsForm.jsp"></jsp:include>
				<!-- ����workflowDefine id -->
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
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
		// ��ʼ���˵�
		$('#menu_workflow_Modules').addClass('current');			
		$('#menu_workflow_Configuration').addClass('selected');
		
		//------.workflowDefine_form  begin--------------
		//  �༭ģʽ�� ���ع���������Ȩ��
		$("#rightIds_div").load("workflowModuleAction.do?proc=list_Module_rightIds_html_checkBox",
				{"workflowModuleId":$('.workflowDefine_workflowModuleId').val(),"checkBoxName":"rightIdsArray","rightIds":'<bean:write name="workflowDefineForm" property="rightIds"/>'},
				function(){
					$(".workflowDefine_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
				});
		//  �༭ģʽ�� ������÷�Χ���ⲿ����ȥ�������������͵�{������֯�ܹ�}
		if($('.workflowDefine_scope').val()== 2){
			$("#workflowDefine_approvalType option ").each(function(){
				if($(this).val()==1){
			 		$(this).remove();
				}
			});
		}
		// disableForm Define��
		disableForm('workflowDefine_form');
		
		// ��ʼ������Define��Ӱ�ť 
		$('.workflowDefine_form input#btnSave').hide();
		
		// .workflowDefine_form ��ť�ύ�¼�
		function btnDefineSubmit() {
			var flag = 0;
			flag = flag + validate("workflowDefine_workflowModuleId", true, "select", 100, 0);
			flag = flag + validate("workflowDefine_nameZH", true, "common", 100, 0);
			flag = flag + validate("workflowDefine_nameEN", true, "common", 100, 0);
			flag = flag + validate("workflowDefine_scope", true, "select", 0, 0);
			flag = flag + validate("workflowDefine_approvalType", true, "select", 0, 0);
			flag = flag + validate("workflowDefine_status", true, "select", 0, 0);
			if(flag == 0){
				//ȫ�� ��Ϊ�ɱ༭
        		enableForm('workflowDefine_form');
        		$('.workflowDefine_form .subAction').val('');
				submit('workflowDefine_form');
			}
		};
		
		// ���÷�ΧworkflowDefine_scope�ı��¼�
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
		
		// ְλ�ⲿ������ʱ������������ֵ
		if($('.workflowDefine_form .workflowDefine_scope').val()==2){
			var value = $('.workflowDefine_form #workflowDefine_approvalType_0').val();
			$('.workflowDefine_form #workflowDefine_approvalType').html($('.workflowDefine_form #workflowDefine_approvalType_2').html());
			$('.workflowDefine_form #workflowDefine_approvalType').val(value);
			
			$('.workflowDefine_form .workflowDefine_positionGradeId').html($('.workflowDefine_form .workflowDefine_positionGradeId_2').html());
		}
		
		// Define�༭��ť����¼�			
        $('.workflowDefine_form input#btnEdit').click(function(){

        	var $subAction = $('.workflowDefine_form  .subAction ');
        	if($subAction.val() == 'viewObject'){
        		//ȫ�� ��Ϊ�ɱ༭
        		enableForm('workflowDefine_form');
        		// ������ģ��Ȩ�� ���ֲ��ɱ༭
        		// $(".workflowDefine_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
        		//������ģ��id���ɸ�
        		$('.workflowDefine_workflowModuleId').attr('disabled', 'disabled');
				// ����Subaction
        		$('.workflowDefine_form .subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('.workflowDefine_form #btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����action ����
				$('.workflowDefine_form').attr('action','workflowDefineAction.do?proc=modify_object');
        	}else if($('.workflowDefine_form .subAction').val() == 'modifyObject'){
        		
        		//�ύ��
        		btnDefineSubmit();
        	}
        });
		
		//Define ȡ����ť�����¼�
		$('.workflowDefine_form #btnList').click( function () {
			if(agreest())
			link('workflowDefineAction.do?proc=list_object');
		});	
		
		//------.workflowDefine_form  end--------------
		
		
		//DefineSteps ��Ӱ�ť����¼�
		$('#btnDefineStepsAdd').click(function(){
			// �ж�����ӡ��鿴�����޸�
			if($('.steps_form .subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnDefineStepsCancel').show();
				// ��ʾSteps Detail Form
				$('#defineSteps').show();
				// ����SubActionΪ�½�
				$('.steps_form .subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnDefineStepsAdd').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.steps_form .subAction').val() == 'viewObject'){
				// �༭������Enable����Form
				enableForm('steps_form');
				// ����SubActionΪ�༭
				$('.steps_form .subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnDefineStepsAdd').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
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
		
		// DefineSteps ȡ����ť����¼�
		$('#btnDefineStepsCancel').click(function(){
			if(agreest())
			link('workflowDefineStepsAction.do?proc=list_object&defineId=<bean:write name="workflowDefineForm" property="encodedId" />');
		});
		
		// DefineSteps ���ʱĬ����Ϣ��ʾѡ��� Define��Ϣ��ʾѡ����ͬ
		
		$('.steps_form input[name=sendEmail]').attr('checked',$('.workflowDefine_form input[name=sendEmail]').attr('checked'));
		$('.steps_form input[name=sendSMS]').attr('checked',$('.workflowDefine_form input[name=sendSMS]').attr('checked'));
		$('.steps_form input[name=sendInfo]').attr('checked',$('.workflowDefine_form input[name=sendInfo]').attr('checked'));
		
		kanList_init();
		kanCheckbox_init();
		
		// �����ְλ���빦��
		bindThinkingToPositionName();
	})(jQuery);
	
	
	function workflowDefineSteps(stepId){
		// ��ʾCancel��ť
		$('#btnDefineStepsCancel').show();
		// ��ʾDefineSteps Detail Form
		$('#defineSteps').show();		
		// Ajax����Option Detail�޸�ҳ��
		//loadHtml('#defineSteps', 'workflowDefineStepsAction.do?proc=to_objectModify_ajax&stepId=' + stepId, true);
		
		$('#defineSteps').load('workflowDefineStepsAction.do?proc=to_objectModify_ajax&stepId=' + stepId,function(){
			// disableForm ��
			disableForm('steps_form');
			
			//Ϊ defineId ��ֵ
			$('.steps_form input#defineId').val('<bean:write name="workflowDefineForm" property="encodedId" />');
			// �޸İ�ť��ʾ����
			$('#btnDefineStepsAdd').val('<bean:message bundle="public" key="button.edit" />');
			
			// �����ְλ���빦��
			bindThinkingToPositionName();
		});
	};
	
	 //	Think�¼�
	function bindThinkingToPositionName(){
		// Use the common thinking
		kanThinking_column('workflowDefineSteps_positionId', 'positionId', 'positionAction.do?proc=list_object_json');
		
	};
</script>