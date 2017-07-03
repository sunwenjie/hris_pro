<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.web.actions.management.MembershipAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="membership" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.membership" /></label>
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
				<logic:empty name="membershipForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="membershipForm" property="encodedId">
					<kan:auth right="modify" action="<%=MembershipAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=MembershipAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="membershipAction.do?proc=add_object" styleClass="manageMembership_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="membershipId" id="membershipId" value='<bean:write name="membershipForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="membershipForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.membership.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageMembership_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.membership.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageMembership_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageMembership_description"></html:textarea>
						</li>					
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageMembership_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>	
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.modify.by" /></label> 
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
		$('#menu_employee_Modules').addClass('current');	
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_Membership').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageMembership_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageMembership_form');
				// 修改人、修改时间不可编辑
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.manageMembership_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.membership" /> <bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.manageMembership_form').attr('action', 'membershipAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageMembership_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageMembership_description", false, "common", 500, 0);
    			flag = flag + validate("manageMembership_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageMembership_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageMembership_form input.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('manageMembership_form');
			// 更改Subaction
    		$('.manageMembership_form input.subAction').val('viewObject');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="management" key="management.membership" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// 创建模式
		if($('.manageMembership_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('membershipAction.do?proc=list_object');
		});
	})(jQuery);
</script>
