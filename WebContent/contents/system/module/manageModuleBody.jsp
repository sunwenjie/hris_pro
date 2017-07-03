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
			<label id="pageTitle">模块新增</label>
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
							<label>模块名称 （中文）<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="module_namezh" /> 
						</li>
						<li>
							<label>模块名称 （英文）<em> *</em></label> 
							<html:text property="nameEN" maxlength="100" styleClass="module_nameen" /> 
						</li>
						<li>
							<label>模块标识 （中文）</label> 
							<html:text property="titleZH" maxlength="100" styleClass="module_namezh" /> 
						</li>
						<li>
							<label>模块标识 （英文）</label> 
							<html:text property="titleEN" maxlength="100" styleClass="module_nameen" /> 
						</li>
						<li>
							<label>模块ID<em> *</em></label> 
							<html:text property="moduleName" maxlength="50" styleClass="module_moduleName" />
						</li>
						<li>
							<label>资源标签<em> *</em></label> 
							<html:text property="property" maxlength="100" styleClass="module_property" />
						</li>
						<li>
							<label>模块类型<em> *</em></label> 
							<html:select property="moduleType" styleClass="module_moduleType">
								<html:optionsCollection property="moduleTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>访问链接</label>
							<html:text property="accessAction" maxlength="100" styleClass="module_accessAction"/>
						</li>
						<li>
							<label>默认操作</label>
							<html:text property="defaultAction" maxlength="100" styleClass="module_defaultAction"/>
						</li>
						<li>
							<label>列表操作</label> 
							<html:text property="listAction" maxlength="100" styleClass="module_listAction" />
						</li>
						<li>
							<label>新建转向</label> 
							<html:text property="toNewAction" maxlength="100" styleClass="module_toNewAction" />
						</li>
						<li>
							<label>新建操作</label> 
							<html:text property="newAction" maxlength="100" styleClass="module_newAction" />
						</li>
						<li>
							<label>修改转向</label> 
							<html:text property="toModifyAction" maxlength="100" styleClass="module_toModifyAction" />
						</li>
						<li>
							<label>修改操作</label> 
							<html:text property="modifyAction" maxlength="100" styleClass="module_modifyAction" />
						</li>
						<li>
							<label>删除操作（单个）</label> 
							<html:text property="deleteAction" maxlength="100" styleClass="module_deleteAction" />
						</li>
						<li>
							<label>删除操作（多个）</label> 
							<html:text property="deletesAction" maxlength="100" styleClass="module_deletesAction" />
						</li>
						<li>
							<label>上级模块</label> 
							<html:text property="parentModuleId" maxlength="25" styleClass="module_parentModuleId" />
						</li>
						<li>
							<label>菜单ID（一级）</label> 
							<html:text property="levelOneModuleName" maxlength="50" styleClass="module_levelOneModuleName" />
						</li>
						<li>
							<label>菜单ID（二级）</label> 
							<html:text property="levelTwoModuleName" maxlength="50" styleClass="module_levelTwoModuleName" />
						</li>
						<li>
							<label>菜单ID（三级）</label> 
							<html:text property="levelThreeModuleName" maxlength="50" styleClass="module_levelThreeModuleName" />
						</li>
						<li>
							<label>模块顺序<em> *</em></label> 
							<html:text property="moduleIndex" maxlength="4" styleClass="module_moduleIndex" />
						</li>
						<li>
							<label>种类<em> *</em></label> 
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
								<li id="tabMenu1" onClick="changeTab(1,3)" class="hover first">权限</li> 
								<li id="tabMenu2" onClick="changeTab(2,3)" >规则</li> 
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
		// 设置菜单选择样式
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_Module').addClass('selected');
		
		// 保存按钮点击事件
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('module_form');
				// 更改Subaction
        		$('.subAction').val('modifyObject');
        		// 更换Page Title
    			$('#pageTitle').html('模块编辑');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.module_form').attr('action', 'moduleAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// 初始化隐藏编辑按钮
		$('#btnEdit').hide();
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('module_form');
			// 更换Page Title
			$('#pageTitle').html('模块查询');
			// 更换按钮Value
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		$('#btnCancel').click( function () {
			link('moduleAction.do?proc=list_object');
		});
	})(jQuery);
	
	// 按钮提交事件
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
