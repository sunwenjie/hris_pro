<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<link rel="stylesheet" href="plugins/zTree_v3-master/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="plugins/zTree_v3-master/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plugins/zTree_v3-master/js/jquery.ztree.excheck-3.5.min.js"></script>

<div id="content">
	<div id="managementWorkflowModule" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">�鿴������</label>
		</div>
		<div class="inner">
			<html:form action="workflowModuleAction.do?proc=add_object" styleClass="workflowModule_form">
				<%= BaseAction.addToken( request ) %>
				<div class="top">
					<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" /> 
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
					<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
				</div>
				<input type="hidden" id="workflowModuleId" name="workflowModuleId" value="<bean:write name="workflowModuleForm" property="encodedId" />" />
				<html:hidden property="subAction" styleClass="subAction" />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>ϵͳģ��<em> *</em></label>
							<input id="sysModules" type="text"  value='<bean:write name="sysMoldueNamelList"/>' class="sysModuleName" />
							<input id="moduleId" name="moduleId" type="hidden" class="moduleId" value="<bean:write name="workflowModuleForm"   property="moduleId"/>"/>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>���������ƣ����ģ�</label> 
							<html:text property="nameZH" maxlength="100" styleClass="workflowModule_nameZH" /> 
						</li>
						<li>
							<label>���������ƣ�Ӣ�ģ�</label> 
							<html:text property="nameEN" maxlength="100" styleClass="workflowModule_nameEN" /> 
						</li>
						<li>
							<label>������״̬</label> 
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
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_Pending').addClass('selected');
		$('#searchDiv').hide();
		
		// ��ť�ύ�¼�
		function btnSubmit() {
			var flag = 0;
			
			flag = flag + validate("sysModule", true, "select", 0, 0);
			flag = flag + validate("workflowModule_nameZH", true, "common", 100, 0);
			flag = flag + validate("workflowModule_nameEN", true, "common", 100, 0);
			flag = flag + validate("workflowModule_status", true, "select", 0, 0);
			
			if(flag == 0){
				submit('workflowModule_form');
			}
		};
		
		// ���水ť����¼�
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () {
			if($('.workflowModule_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('workflowModule_form');
				//ϵͳģ�������ɸ���
				$('#sysModules').attr('disabled', 'disabled');
				// ����Subaction
        		$('.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.workflowModule_form').attr('action', 'workflowModuleAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// ��ʼ�����ر༭��ť
		$('#btnEdit').hide();
		
		// �鿴ģʽ
		if($('.subAction').val() == 'viewObject'){
			// ��Form��ΪDisable
			disableForm('workflowModule_form');
			// ����Page Title
			$('#pageTitle').html('�鿴��ҵģ��');
			// ������ťValue
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		$('#btnCancel').click( function () {
			back();
		});
		
		var moduleId = $("#moduleId").val();
		
		bindThinkingToPositionName();
		
		//Think�¼�
		function bindThinkingToPositionName(){
			// Use the common thinking
			kanThinking_column('sysModuleName', 'moduleId', 'moduleAction.do?proc=list_object_json',
			{"workflowModule_nameZH":"name","workflowModule_nameEN":"nameEN"});
		};
		
	})(jQuery);
</script>
							