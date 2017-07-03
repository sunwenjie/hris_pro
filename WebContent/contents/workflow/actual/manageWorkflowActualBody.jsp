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
			<label id="pageTitle">查看工作流</label>
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
							<label>系统模块<em> *</em></label>
							<input id="sysModules" type="text"  value='<bean:write name="sysMoldueNamelList"/>' class="sysModuleName" />
							<input id="moduleId" name="moduleId" type="hidden" class="moduleId" value="<bean:write name="workflowModuleForm"   property="moduleId"/>"/>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>工作流名称（中文）</label> 
							<html:text property="nameZH" maxlength="100" styleClass="workflowModule_nameZH" /> 
						</li>
						<li>
							<label>工作流名称（英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="workflowModule_nameEN" /> 
						</li>
						<li>
							<label>工作流状态</label> 
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
		// 设置菜单选择样式
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_Pending').addClass('selected');
		$('#searchDiv').hide();
		
		// 按钮提交事件
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
		
		// 保存按钮点击事件
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () {
			if($('.workflowModule_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('workflowModule_form');
				//系统模块名不可更改
				$('#sysModules').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.workflowModule_form').attr('action', 'workflowModuleAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// 初始化隐藏编辑按钮
		$('#btnEdit').hide();
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('workflowModule_form');
			// 更换Page Title
			$('#pageTitle').html('查看作业模块');
			// 更换按钮Value
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		$('#btnCancel').click( function () {
			back();
		});
		
		var moduleId = $("#moduleId").val();
		
		bindThinkingToPositionName();
		
		//Think事件
		function bindThinkingToPositionName(){
			// Use the common thinking
			kanThinking_column('sysModuleName', 'moduleId', 'moduleAction.do?proc=list_object_json',
			{"workflowModule_nameZH":"name","workflowModule_nameEN":"nameEN"});
		};
		
	})(jQuery);
</script>
							