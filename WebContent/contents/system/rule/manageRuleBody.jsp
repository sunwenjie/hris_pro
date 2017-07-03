<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="systemRule" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">规则增加</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="ruleAction.do?proc=add_object" styleClass="rule_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="ruleId" name="ruleId" value="<bean:write name="ruleForm" property="encodedId" />" />
				<html:hidden property="subAction" styleClass="subAction" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>规则名 （中文）<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="rule_namezh" /> 
						</li>
						<li>
							<label>规则名 （英文）<em> *</em></label> 
							<html:text property="nameEN" maxlength="100" styleClass="rule_nameen" /> 
						</li>
						<li>
							<label>规则类型<em> *</em></label> 
							<html:select property="ruleType" styleClass="rule_ruleType">
								<html:optionsCollection property="ruleTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="rule_status">
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
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_Rule').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('rule_form');
				// 更改Subaction
        		$('.subAction').val('modifyObject');
        		// 更换Page Title
    			$('#pageTitle').html('规则编辑');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
				// 更改Form Action
        		$('.rule_form').attr('action', 'ruleAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			flag = flag + validate("rule_namezh", true, "common", 100, 0);
    			flag = flag + validate("rule_nameen", true, "common", 100, 0);
    			flag = flag + validate("rule_ruleType", true, "common", 0, 0);
    			flag = flag + validate("rule_status", true, "common", 0, 0);
    			
    			if(flag == 0){
    				$('.rule_form').submit();
    			}
        	}
		});
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('rule_form');
			// 更换Page Title
			$('#pageTitle').html('规则查询');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		$('#btnList').click( function () {
			if (agreest())
			link('ruleAction.do?proc=list_object');
		});
	})(jQuery);
</script>
