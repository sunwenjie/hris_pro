<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="constant" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">参数添加</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<html:form action="constantAction.do?proc=add_object" styleClass="manageConstant_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="constantId" id="constantId" value='<bean:write name="constantForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="constantForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>参数名称（中文）<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageConstant_nameZH" /> 
						</li>
						<li>
							<label>参数名称（英文）<em> *</em></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageConstant_nameEN" /> 
						</li>
						<li>
							<label>字段名称 <em> *</em></label> 
							<html:text property="propertyName" maxlength="100" styleClass="manageConstant_propertyName" /> 
						</li>
						<li>
							<label>字段长度</label> 
							<html:select property="lengthType" styleClass="manageConstant_lengthType">
								<html:optionsCollection property="lengthTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>内容类型</label> 
							<html:select property="valueType" styleClass="manageConstant_valueType">
								<html:optionsCollection property="valueTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>内容（默认）</label> 
							<html:text property="content" maxlength="500" styleClass="manageConstant_content" /> 
						</li>
						<li>
							<label>参数性质<em> *</em></label> 
							<html:select property="characterType" styleClass="manageConstant_characterType">
								<html:optionsCollection property="characterTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>适用范围<em> *</em></label> 
							<html:select property="scopeType" styleClass="manageConstant_scopeType">
								<html:optionsCollection property="scopeTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageConstant_description"></html:textarea>
						</li>	
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageConstant_status">
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
		$('#menu_system_Constant').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageConstant_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageConstant_form');
				// 更改SubAction
        		$('.manageConstant_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('参数编辑');
				// 更改Form Action
        		$('.manageConstant_form').attr('action', 'constantAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageConstant_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageConstant_nameEN", true, "common", 100, 0);
    			flag = flag + validate("manageConstant_propertyName", true, "common", 100, 0);
    			flag = flag + validate("manageConstant_characterType", true, "select", 0, 0);
    			flag = flag + validate("manageConstant_scopeType", true, "select", 0, 0);
    			flag = flag + validate("manageConstant_description", false, "common", 500, 0);
    			flag = flag + validate("manageConstant_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageConstant_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageConstant_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('manageConstant_form');
			// 更换Page Title
			$('#pageTitle').html('参数查询');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		$('#btnCancel').click( function () {
			link('constantAction.do?proc=list_object');
		});
	})(jQuery);
</script>
