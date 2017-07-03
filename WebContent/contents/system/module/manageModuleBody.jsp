<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.system.RightRender"%>
<%@ page import="com.kan.base.web.renders.system.RuleRender"%>
<%@ page import="com.kan.base.domain.system.ModuleVO"%>

<%
	final ModuleVO moduleVO = (ModuleVO) request.getAttribute("moduleForm");
	String moduleId = null;
	
	if(moduleVO != null && moduleVO.getModuleId() != null){
	   moduleId = moduleVO.getModuleId();
	}
%>

<div id="content">
	<div id="systemModule" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">ģ������</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<html:form action="moduleAction.do?proc=add_object" styleClass="module_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="moduleId" name="moduleId" value="<bean:write name="moduleForm" property="encodedId" />" />
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="moduleForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>ģ������ �����ģ�<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="module_namezh" /> 
						</li>
						<li>
							<label>ģ������ ��Ӣ�ģ�<em> *</em></label> 
							<html:text property="nameEN" maxlength="100" styleClass="module_nameen" /> 
						</li>
						<li>
							<label>ģ���ʶ �����ģ�</label> 
							<html:text property="titleZH" maxlength="100" styleClass="module_namezh" /> 
						</li>
						<li>
							<label>ģ���ʶ ��Ӣ�ģ�</label> 
							<html:text property="titleEN" maxlength="100" styleClass="module_nameen" /> 
						</li>
						<li>
							<label>ģ��ID<em> *</em></label> 
							<html:text property="moduleName" maxlength="50" styleClass="module_moduleName" />
						</li>
						<li>
							<label>��Դ��ǩ<em> *</em></label> 
							<html:text property="property" maxlength="100" styleClass="module_property" />
						</li>
						<li>
							<label>ģ������<em> *</em></label> 
							<html:select property="moduleType" styleClass="module_moduleType">
								<html:optionsCollection property="moduleTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>��������</label>
							<html:text property="accessAction" maxlength="100" styleClass="module_accessAction"/>
						</li>
						<li>
							<label>Ĭ�ϲ���</label>
							<html:text property="defaultAction" maxlength="100" styleClass="module_defaultAction"/>
						</li>
						<li>
							<label>�б����</label> 
							<html:text property="listAction" maxlength="100" styleClass="module_listAction" />
						</li>
						<li>
							<label>�½�ת��</label> 
							<html:text property="toNewAction" maxlength="100" styleClass="module_toNewAction" />
						</li>
						<li>
							<label>�½�����</label> 
							<html:text property="newAction" maxlength="100" styleClass="module_newAction" />
						</li>
						<li>
							<label>�޸�ת��</label> 
							<html:text property="toModifyAction" maxlength="100" styleClass="module_toModifyAction" />
						</li>
						<li>
							<label>�޸Ĳ���</label> 
							<html:text property="modifyAction" maxlength="100" styleClass="module_modifyAction" />
						</li>
						<li>
							<label>ɾ��������������</label> 
							<html:text property="deleteAction" maxlength="100" styleClass="module_deleteAction" />
						</li>
						<li>
							<label>ɾ�������������</label> 
							<html:text property="deletesAction" maxlength="100" styleClass="module_deletesAction" />
						</li>
						<li>
							<label>�ϼ�ģ��</label> 
							<html:text property="parentModuleId" maxlength="25" styleClass="module_parentModuleId" />
						</li>
						<li>
							<label>�˵�ID��һ����</label> 
							<html:text property="levelOneModuleName" maxlength="50" styleClass="module_levelOneModuleName" />
						</li>
						<li>
							<label>�˵�ID��������</label> 
							<html:text property="levelTwoModuleName" maxlength="50" styleClass="module_levelTwoModuleName" />
						</li>
						<li>
							<label>�˵�ID��������</label> 
							<html:text property="levelThreeModuleName" maxlength="50" styleClass="module_levelThreeModuleName" />
						</li>
						<li>
							<label>ģ��˳��<em> *</em></label> 
							<html:text property="moduleIndex" maxlength="4" styleClass="module_moduleIndex" />
						</li>
						<li>
							<label>����<em> *</em></label> 
							<html:select property="moduleFlag" styleClass="module_moduleFlag">
								<html:optionsCollection property="moduleFlags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="module_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="module_description" />
						</li>
					</ol>
					<div id="tab"> 
						<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,3)" class="hover first">Ȩ��</li> 
								<li id="tabMenu2" onClick="changeTab(2,3)" >����</li> 
							</ul> 
						</div> 
						<div class="tabContent"> 
							<div id="tabContent1" class="kantab">
								<%= RightRender.getRightMultipleChoice(request, moduleId, true) %>
							</div>
							<div id="tabContent2" class="kantab" style="display:none">
								<%= RuleRender.getRuleMultipleChoice(request, moduleId) %>
							</div> 
						</div> 
					</div>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_Module').addClass('selected');
		
		// ���水ť����¼�
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('module_form');
				// ����Subaction
        		$('.subAction').val('modifyObject');
        		// ����Page Title
    			$('#pageTitle').html('ģ��༭');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.module_form').attr('action', 'moduleAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// ��ʼ�����ر༭��ť
		$('#btnEdit').hide();
		
		// �鿴ģʽ
		if($('.subAction').val() == 'viewObject'){
			// ��Form��ΪDisable
			disableForm('module_form');
			// ����Page Title
			$('#pageTitle').html('ģ���ѯ');
			// ������ťValue
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		$('#btnCancel').click( function () {
			link('moduleAction.do?proc=list_object');
		});
	})(jQuery);
	
	// ��ť�ύ�¼�
	function btnSubmit() {
		var flag = 0;
		
		flag = flag + validate("module_namezh", true, "common", 100, 0);
		flag = flag + validate("module_nameen", true, "common", 100, 0);
		flag = flag + validate("module_moduleName", true, "common", 50, 0);
		flag = flag + validate("module_property", true, "common", 100, 0);
		flag = flag + validate("module_moduleType", true, "common", 0, 0);
		flag = flag + validate("module_status", true, "common", 0, 0);
		flag = flag + validate("module_description", false, "common", 500, 0);
		flag = flag + validate("module_moduleIndex", true, "numeric", 4, 0);
		
		if(flag == 0){
			submit('module_form');
		}
	};
</script>
