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
			<!-- jspInclude ����form����Ӧjspҳ��  formClass .workflowDefine_form -->
			<jsp:include page="/contents/workflow/define/form/manageWorkflowDefineForm.jsp"></jsp:include>
			
			<!-- tab���� -->
			<logic:notEmpty name="workflowDefineForm" property="defineId">
				<logic:equal name="isLeaveOrOTAccessAction" value="true">
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,3)" class="first hover">1 - <bean:message bundle="public" key="menu.table.title.trigger.condition" /></li> 
							<!-- �ⲿ���������貹������ -->
							<logic:equal name="workflowDefineForm" property="scope" value="1">
								<li id="tabMenu2" onClick="changeTab(2,3)" >2 - <bean:message bundle="public" key="menu.table.title.supplementary.steps" /></li> 
							</logic:equal>
						</ul> 
					</div> 
					<div class="tabContent"> 
						<div id="tabContent1" class="kantab kanThinkingCombo" >
							<!-- ����step1ҳ�� -->
							<jsp:include page="/contents/workflow/defineRequirements/includePage/manageWorkflowDefineRequirements.jsp" flush="true"></jsp:include>	
						</div>
						<!-- �ⲿ���������貹������ -->
						<logic:equal name="workflowDefineForm" property="scope" value="1">
						<div id="tabContent2" class="kantab kanThinkingCombo" style="display:none">
							<!-- ����step2ҳ�� -->
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
							<!-- ����step2ҳ�� -->
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
		// ��ʼ���˵���ʽ
		$('#menu_workflow_Modules').addClass('current');			
		$('#menu_workflow_Configuration').addClass('selected');
		
		// ������ģ��� change�¼�
		$(".workflowDefine_workflowModuleId").change(function (){
			var workflowModuleId = $(this).val();
			if(workflowModuleId==0){
				$("#rightIds_div").html("<label><bean:message bundle="workflow" key="workflow.define.trigger.right.init" /></label>");
			}else{
				$("#rightIds_div").load("workflowModuleAction.do?proc=list_Module_rightIds_html_checkBox",{"workflowModuleId":workflowModuleId,"checkBoxName":"rightIdsArray"});
			}
		});
		
		// ���水ť����¼�
		$('#btnEdit').click(function(){
			if( getSubAction() == 'viewObject' ){
				// Enable����Form
        		enableForm('workflowDefine_form');
        		// ������ģ��id���ɸ�
        		$('.workflowDefine_workflowModuleId').attr('disabled', 'disabled');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.workflowDefine_form input#subAction').val('modifyObject'); 
        		// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����action ����
				$('.workflowDefine_form').attr('action','workflowDefineAction.do?proc=modify_object');
			}else{
				validate_form();
			}
		});
		
		// �б�ť�����¼�
		$('#btnList').click( function(){
			if(agreest())
				link('workflowDefineAction.do?proc=list_object');
		});	
		
		// �½����޸�ģʽ�µ�JS����
		if( getSubAction() != 'createObject' ){
			// form���ɱ༭
			disableForm('workflowDefine_form');
			// ���ع���������Ȩ��
			$("#rightIds_div").load("workflowModuleAction.do?proc=list_Module_rightIds_html_checkBox",
					{"workflowModuleId":$('.workflowDefine_workflowModuleId').val(),"checkBoxName":"rightIdsArray","rightIds":'<bean:write name="workflowDefineForm" property="rightIds"/>'},
					function(){
						$(".workflowDefine_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
					});
		} else if ( getSubAction() == 'createObject' ) {
			// ���İ�ťֵ
		    $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		}
		
	})(jQuery);
	
	// ��֤��
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
			addError("rightIds_div","���ٹ�ѡһ��");
		}
		
		if( flag == 0 ){
			// enable����Form
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