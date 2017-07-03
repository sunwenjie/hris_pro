<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="managementWorkflowModule" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">���������</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<html:form action="workflowModuleAction.do?proc=add_object" styleClass="workflowModule_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="workflowModuleId" name="workflowModuleId" value="<bean:write name="workflowModuleForm" property="encodedId" />" />
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="workflowModuleForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>ϵͳģ�飨��ʶ��<em> *</em></label>
							<input type="text" id="moduleTitle" name="moduleTitle" class="workflowModule_moduleTitle" value='<bean:write name="workflowModuleForm" property="moduleTitle"/>' />
							<input type="hidden" id="moduleId" name="moduleId" class="workflowModule_moduleId" value='<bean:write name="workflowModuleForm" property="moduleId"/>'/>
						</li>
						<li>
							<label>���÷�Χ<em> *</em></label> 
							<html:select property="scopeType" styleClass="workflowModule_scopeType">
								<html:optionsCollection property="scopeTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>��������������Ȩ��<em> *</em></label>
							<div id="rightIds_div"  style="font-size:13px;">����ѡ������ģ��</div>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>���������ƣ����ģ�<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="workflowModule_nameZH" /> 
						</li>
						<li>
							<label>���������ƣ�Ӣ�ģ�</label> 
							<html:text property="nameEN" maxlength="100" styleClass="workflowModule_nameEN" /> 
						</li>
						<li>
							<label>��˶����jspҳ��</label> 
							<html:text property="includeViewObjJsp" maxlength="500" styleClass="workflowModule_includeViewObjJsp" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="workflowModule_description" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="workflowModule_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_workflow_modules').addClass('current');
		$('#searchDiv').hide();
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () {
			if($('.workflowModule_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('workflowModule_form');
				//ϵͳģ�������ɸ���
				$('#moduleTitle').attr('disabled', 'disabled');
				// ����Subaction
        		$('.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
    			$('#pageTitle').html('�������༭');
				// ����Form Action
        		$('.workflowModule_form').attr('action', 'workflowModuleAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			flag = flag + validate("workflowModule_moduleId", true, "common", 0, 0);
    			flag = flag + validate("workflowModule_nameZH", true, "common", 100, 0);
    			flag = flag + validate("workflowModule_includeViewObjJsp", false, "common", 500, 0);
    			flag = flag + validate("workflowModule_status", true, "select", 0, 0);
    			flag = flag + validate("workflowModule_description", false, "false", 500, 0);
    			
    			if(flag == 0){
    				submit('workflowModule_form');
    			}
        	}
		});
		
		// �鿴ģʽ
		if($('.subAction').val() == 'viewObject'){
			// ��Form��ΪDisable
			disableForm('workflowModule_form');
			// ���İ�ť��ʾ��
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// ����Page Title
			$('#pageTitle').html('��������ѯ');
			//  �鿴ģʽ�� ���ع���������Ȩ��
			$("#rightIds_div").load("moduleAction.do?proc=list_Module_rightIds_html_checkBox",
					{"moduleId":$('.workflowModule_moduleId').val(),"checkBoxName":"rightIdsArray","rightIds":'<bean:write name="workflowModuleForm" property="rightIds"/>'},
					function(){
						$(".workflowModule_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
					});
		}
		
		$('#btnCancel').click( function () {
			link('workflowModuleAction.do?proc=list_object');
		});
		// �鿴ģʽ
		if($('.subAction').val() != 'viewObject'){
			// Use the common thinking
			kanThinking_column('workflowModule_moduleTitle', 'workflowModule_moduleId', 'moduleAction.do?proc=list_object_json',
			function (){
				var moduleId = $('.workflowModule_moduleId').val();
				if(moduleId==0){
					$("#rightIds_div").html("--δ��Ӧ--");
				}else{
					$("#rightIds_div").load("moduleAction.do?proc=list_Module_rightIds_html_checkBox",{"moduleId":moduleId,"checkBoxName":"rightIdsArray"});
				}
			});
		}
		
	})(jQuery);
	
</script>
							