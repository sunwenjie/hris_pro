<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import=" com.kan.base.web.actions.management.BankAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="manageBank" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.bank" /></label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<logic:empty name="bankForm" property="encodedId">
					<input type="button" class="btnEdit" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="bankForm" property="encodedId">
					<kan:auth right="modify" action="<%=BankAction.accessAction%>">
						<input type="button" class="btnEdit" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=BankAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<html:form action="bankAction.do?proc=add_object" styleClass="manageBank_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="id" name="id" value="<bean:write name="bankForm" property="encodedId" />"/>
				<html:hidden property="subAction" styleClass="subAction" /> 
				<input type="hidden" id="cityIdTemp" name="cityIdTemp" class="manageBank_cityIdTemp" value="<bean:write name='bankForm' property='cityId' />">
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.bank.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageBank_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.bank.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageBank_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.bank.address.cn" /></label> 
							<html:text property="addressZH" maxlength="100" styleClass="manageBank_addressZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.bank.address.en" /></label> 
							<html:text property="addressEN" maxlength="100" styleClass="manageBank_addressEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.province.city" /></label> 
							<html:select property="provinceId" styleClass="manageBank_provinceId" >
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.bank.postcode" /></label> 
							<html:text property="postcode" maxlength="25" styleClass="manageBank_postcode" /> 
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="management" key="management.bank.telephone" /></label> 
							<html:text property="telephone" maxlength="25" styleClass="manageBank_telephone" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.bank.fax" /></label> 
							<html:text property="fax" maxlength="25" styleClass="manageBank_fax" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.bank.email" /></label> 
							<html:text property="email" maxlength="100" styleClass="manageBank_email" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.bank.website" /></label> 
							<html:text property="website" maxlength="100" styleClass="manageBank_website" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageBank_description"></html:textarea>
						</li>	
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageBank_status">
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
		// 设置菜单选择样式，如果当前用户是Super
		if('<bean:write name="accountId" />' == '1'){
			$('#menu_system_Modules').addClass('current');
			$('#menu_system_Bank').addClass('selected');
		}else{
			$('#menu_finance_Modules').addClass('current');	
			$('#menu_finance_Configuration').addClass('selected');
			$('#menu_finance_Bank').addClass('selected');
		}
		// 初始化省份控件
		provinceChange('manageBank_provinceId', 'viewObject', $('.manageBank_cityIdTemp').val(), 'cityId');
		
		// 绑定省Change事件
		$('.manageBank_provinceId').change( function () { 
			provinceChange('manageBank_provinceId', 'modifyObject', 0, 'cityId');
		});
		
		// 如果当前是系统银行并且当前用户非Super，隐藏编辑按钮。
		if('<bean:write name="bankForm" property="accountId" />' == '1' && '<bean:write name="accountId" />' != '1'){
			$('#btnEdit').hide();
		}
		
		// 取消按钮点击事件
		$('#btnList').click(function(){
			if (agreest())
			link('bankAction.do?proc=list_object'); 
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageBank_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageBank_form');
				// 更改Subaction
        		$('.manageBank_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.bank" /> <bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.manageBank_form').attr('action', 'bankAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
        		
    			flag = flag + validate("manageBank_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageBank_telephone", false, "phone", 25, 0);
    			flag = flag + validate('manageBank_email', false, 'email', 100, 0);
    			flag = flag + validate('manageBank_website', false, 'website', 100, 0);
    			flag = flag + validate("manageBank_description", false, "common", 500, 0);
    			flag = flag + validate("manageBank_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageBank_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageBank_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('manageBank_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="management" key="management.bank" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
	})(jQuery);
</script>
