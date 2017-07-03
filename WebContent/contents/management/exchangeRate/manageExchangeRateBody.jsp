<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.web.actions.management.ExchangeRateAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="systemUser" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.exchange.rate" /></label>
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
				<logic:empty name="exchangeRateForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="exchangeRateForm" property="encodedId">
					<kan:auth right="modify" action="<%=ExchangeRateAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ExchangeRateAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="exchangeRateAction.do?proc=add_object" styleClass="manageExchangeRate_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="encodedId" value='<bean:write name="exchangeRateForm" property="encodedId" />'>
				<html:hidden property="subAction" styleClass="subAction" />
				<fieldset>
					<ol class="auto">	
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  					
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.exchange.rate.name.cn" /><em> *</em></label> 
							<html:text property="currencyNameZH" maxlength="50" styleClass="manageExchangeRate_currencyNameZH" />
						</li>
						<li>
							<label><bean:message bundle="management" key="management.exchange.rate.name.en" /></label> 
							<html:text property="currencyNameEN" maxlength="50" styleClass="manageExchangeRate_currencyNameEN" />
						</li>
						<li>
							<label><bean:message bundle="management" key="management.exchange.rate.currency.code" /></label> 
							<html:text property="currencyCode" maxlength="10" styleClass="manageExchangeRate_currencyCode" />
						</li>
						<li>
							<label><bean:message bundle="management" key="management.exchange.rate.from.usd" /></label>
							<html:text property="fromUSD" maxlength="10" styleClass="small manageExchangeRate_fromUSD" />
							<html:text property="toLocal" maxlength="10" styleClass="small manageExchangeRate_toLocal" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageExchangeRate_status">								
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
		$('#menu_employee_Modules').addClass('current');	
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_ExchangeRate').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageExchangeRate_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageExchangeRate_form');
				// 修改人、修改时间不可编辑
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.manageExchangeRate_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.exchange.rate" /> <bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.manageExchangeRate_form').attr('action', 'exchangeRateAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
				
    			flag = flag + validate("manageExchangeRate_currencyNameZH", true, "common", 50, 0);
    			flag = flag + validate("manageExchangeRate_currencyCode", false, "common", 10, 0);
    			flag = flag + validate("manageExchangeRate_fromUSD", true, "currency", 8, 0);
    			flag = flag + validate("manageExchangeRate_toLocal", true, "currency", 8, 0);
    			
    			flag = flag + validate("manageExchangeRate_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageExchangeRate_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageExchangeRate_form input.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('manageExchangeRate_form');
			// 更改Subaction
    		$('.manageExchangeRate_form input.subAction').val('viewObject');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="management" key="management.exchange.rate" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// 创建模式
		if($('.manageExchangeRate_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('exchangeRateAction.do?proc=list_object');
		});
	})(jQuery);
</script>
