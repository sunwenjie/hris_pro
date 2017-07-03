<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.management.IndustryTypeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<div id="industryType" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">行业类型添加</label><logic:notEmpty name="industryTypeForm" property="typeId" ><label class="recordId"> &nbsp; (ID: <bean:write name="industryTypeForm" property="typeId" />)</label></logic:notEmpty>
		</div>
		<div class="inner">
			<div class="top">
				<logic:empty name="industryTypeForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="industryTypeForm" property="encodedId">
					<kan:auth right="modify" action="<%=IndustryTypeAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=IndustryTypeAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="industryTypeAction.do?proc=add_object" styleClass="manageIndustryType_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="typeId" id="typeId" value='<bean:write name="industryTypeForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="industryTypeForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label>行业类型名称（中文）<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageIndustryType_nameZH" /> 
						</li>
						<li>
							<label>行业类型名称（英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageIndustryType_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageIndustryType_description"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageIndustryType_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>					
					</ol>
					<ol class="auto">
						<li>
							<label>修改人</label> 
							<html:text property="decodeModifyBy" maxlength="100" disabled="disabled" styleClass="decodeModifyBy" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.modify.date" /></label> 
							<html:text property="decodeModifyDate" maxlength="100" disabled="disabled" styleClass="decodeModifyDate" /> 
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
		$('#menu_client_Modules').addClass('current');
		$('#menu_client_Configuration').addClass('selected');
		$('#menu_client_IndustryType').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageIndustryType_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageIndustryType_form');
        		// 修改人、修改时间不可编辑
        		$('.decodeModifyBy').attr('disabled', 'disabled');
        		$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.manageIndustryType_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('行业类型编辑');
				// 更改Form Action
        		$('.manageIndustryType_form').attr('action', 'industryTypeAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageIndustryType_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageIndustryType_description", false, "common", 500, 0);
    			flag = flag + validate("manageIndustryType_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageIndustryType_form');
    			}
        	}
		});

		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		
		// 查看模式
		if($('.manageIndustryType_form input.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('manageIndustryType_form');
			// 设置页面初始化SubAction
			$('.manageIndustryType_form input.subAction').val('viewObject');
			// 更换Page Title
			$('#pageTitle').html('行业类型查询');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// 创建模式
		if($('.manageIndustryType_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}
		
		$('#btnList').click( function () {
			if (agreest())
			link('industryTypeAction.do?proc=list_object');
		});
	})(jQuery);
</script>
