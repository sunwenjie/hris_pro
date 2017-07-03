<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.management.EmailConfigurationAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="options" class="box toggableForm">
		<div class="head">
			<label><bean:message bundle="system" key="system.email.configuration" /></label>
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
				<kan:auth right="modify" action="<%=EmailConfigurationAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" />
				</kan:auth>
			</div>
			<html:form action="emailConfigurationAction.do?proc=modify_object" styleClass="modifyemailConfiguration_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="subAction" id="subAction" value="viewObject" />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.show.name" /><em> *</em></label> 
							<html:text property="showName" maxlength="50" styleClass="modifyemailConfiguration_showName" />
						</li>
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.mail.type" /></label> 
							<html:select property="mailType" styleClass="modifyemailConfiguration_mailType">
								<html:optionsCollection property="mailTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.send.as" /><em> *</em></label> 
							<html:text property="sentAs" maxlength="50" styleClass="modifyemailConfiguration_sentAs" />
						</li>
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.account.name" /><em> *</em></label> 
							<html:text property="accountName" maxlength="50" styleClass="modifyemailConfiguration_accountName" />
						</li>
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.smtp.host" /><em> *</em></label> 
							<html:text property="smtpHost" maxlength="50" styleClass="modifyemailConfiguration_smtpHost" />
						</li>
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.smtp.port" /><em> *</em></label> 
							<html:text property="smtpPort" maxlength="5" styleClass="modifyemailConfiguration_smtpPort" />
						</li>
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.smtp.auth.type" /></label> 
							<html:select property="smtpAuthType" styleClass="modifyemailConfiguration_smtpAuthType">
								<html:optionsCollection property="smtpAuthTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message key="logon.username" /><em> *</em></label> 
							<html:text property="username" maxlength="50" styleClass="modifyemailConfiguration_username" />
						</li>
						<li>
							<label><bean:message key="logon.password" /><em> *</em></label> 
							<html:password property="password" maxlength="50" styleClass="modifyemailConfiguration_password" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.smtp.security.type" /></label> 
							<html:select property="smtpSecurityType" styleClass="modifyemailConfiguration_smtpSecurityType">
								<html:optionsCollection property="smtpSecurityTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.config.pop3" /></label>
  							<input type="checkbox" class="modifyemailConfiguration_configPop3" value="on" name="configPop3" id="configPop3" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.pop3.host" /><em> *</em></label> 
							<html:text property="pop3Host" maxlength="50" styleClass="modifyemailConfiguration_pop3Host" />
						</li>
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.pop3.port" /><em> *</em></label> 
							<html:text property="pop3Port" maxlength="5" styleClass="modifyemailConfiguration_pop3Port" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.send.test.mail" /></label>
  							<input type="checkbox" class="modifyemailConfiguration_sendTestMail" value="on" name="sendTestMail" id="sendTestMail" />
						</li>
						<li>
							<label><bean:message bundle="system" key="system.email.configuration.test.mail.address" /><em> *</em></label>
							<html:text property="testMailAddress" maxlength="50" styleClass="modifyemailConfiguration_testMailAddress"/>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_management_Modules').addClass('current');			
		$('#menu_management_MailConfiguration').addClass('selected');
		
		// 隐藏密码修改
		$('.modifyemailConfiguration_pop3Host').parent('li').hide();
		$('.modifyemailConfiguration_pop3Port').parent('li').hide();
		$('.modifyemailConfiguration_testMailAddress').parent('li').hide();
		// 将Form设为Disable
		disableForm('modifyemailConfiguration_form');
        
		// 邮件列表选择事件
        $('#btnEdit').click(function(){
        	if($('#subAction').val() == 'viewObject'){
        		enableForm('modifyemailConfiguration_form');
        		$('#subAction').val('modifyObject');
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        	}else{
        		var flag = 0;
    			
        		flag = flag + validate('modifyemailConfiguration_showName', true, 'common', 0, 0);
        		if($('.modifyemailConfiguration_mailType').val() != '2'){
        			flag = flag + validate('modifyemailConfiguration_accountName', true, 'common', 0, 0);
        		}else{
        			flag = flag + validate('modifyemailConfiguration_sentAs', true, 'email', 0, 0);
        			flag = flag + validate('modifyemailConfiguration_smtpHost', true, 'common', 0, 0);
        			flag = flag + validate('modifyemailConfiguration_smtpPort', true, 'numeric', 5, 0);
        			if($('#configPop3').is(':checked')){
	        			flag = flag + validate('modifyemailConfiguration_pop3Host', true, 'common', 0, 0);
	        			flag = flag + validate('modifyemailConfiguration_pop3Port', true, 'numeric', 5, 0);
        			}
        			if($('.modifyemailConfiguration_smtpAuthType').val() == '1'){
	        			flag = flag + validate('modifyemailConfiguration_username', true, 'common', 0, 0);
	        			flag = flag + validate('modifyemailConfiguration_password', true, 'common', 0, 0);
        			}
        		}
        		if($('#sendTestMail').is(':checked')){
        			flag = flag + validate('modifyemailConfiguration_testMailAddress', true, 'email', 0, 0);
        		}
        		
    			if(flag == 0){
    				submit('modifyemailConfiguration_form');
    			}
        	}
        });
		
		// 编辑Mail Type选择事件
        $('.modifyemailConfiguration_mailType').change(function(){
        	if($('.modifyemailConfiguration_mailType').val() != '2'){
        		$('.modifyemailConfiguration_sentAs').parent('li').hide();
        		$('.modifyemailConfiguration_smtpHost').parent('li').hide();
        		$('.modifyemailConfiguration_smtpPort').parent('li').hide();
        		$('.modifyemailConfiguration_configPop3').parent('li').hide();
        		$('.modifyemailConfiguration_username').parent('li').hide();
        		$('.modifyemailConfiguration_password').parent('li').hide();
        		$('.modifyemailConfiguration_smtpAuthType').parent('li').hide();
        		$('.modifyemailConfiguration_smtpSecurityType').parent('li').hide();
        		$('.modifyemailConfiguration_pop3Host').parent('li').hide();
	    		$('.modifyemailConfiguration_pop3Port').parent('li').hide();
        		$('.modifyemailConfiguration_accountName').parent('li').show();
    		}else{
    			$('.modifyemailConfiguration_sentAs').parent('li').show();
        		$('.modifyemailConfiguration_smtpHost').parent('li').show();
        		$('.modifyemailConfiguration_smtpPort').parent('li').show();
        		$('.modifyemailConfiguration_configPop3').parent('li').show();
        		if($('.modifyemailConfiguration_smtpAuthType').val() != '2'){
	        		$('.modifyemailConfiguration_username').parent('li').show();
	        		$('.modifyemailConfiguration_password').parent('li').show();
        		}
        		$('.modifyemailConfiguration_smtpAuthType').parent('li').show();
        		$('.modifyemailConfiguration_smtpSecurityType').parent('li').show();
        		if($('#configPop3').is(':checked')){
	        		$('.modifyemailConfiguration_pop3Host').parent('li').show();
		    		$('.modifyemailConfiguration_pop3Port').parent('li').show();
        		}
    			$('.modifyemailConfiguration_accountName').parent('li').hide();
    		}
        });
		
     	// 编辑SMTP验证选择事件
        $('.modifyemailConfiguration_smtpAuthType').change(function(){
        	if($('.modifyemailConfiguration_smtpAuthType').val() != '2'){
        		$('.modifyemailConfiguration_username').parent('li').show();
        		$('.modifyemailConfiguration_password').parent('li').show();
        	}else{
        		$('.modifyemailConfiguration_username').parent('li').hide();
        		$('.modifyemailConfiguration_password').parent('li').hide();
        	}
        });
		
     	// 当点击配置接收邮件服务器
		$('#configPop3').click(function(){
	        if($(this).is(':checked')) {
	        	$('.modifyemailConfiguration_pop3Host').parent('li').show();
	    		$('.modifyemailConfiguration_pop3Port').parent('li').show();
	    		if(($('.modifyemailConfiguration_smtpHost').val() != null || $('.modifyemailConfiguration_smtpHost').val() == '') 
	    				&& ($('.modifyemailConfiguration_pop3Host').val() == null || $('.modifyemailConfiguration_pop3Host').val() == '')){
	    			$('.modifyemailConfiguration_pop3Host').val($('.modifyemailConfiguration_smtpHost').val());
	    		}
	        } else {
	        	$('.modifyemailConfiguration_pop3Host').parent('li').hide();
	    		$('.modifyemailConfiguration_pop3Port').parent('li').hide();
	        }
	    });
     	
		// 当点击发送测试邮件
		$('#sendTestMail').click(function(){
	        if($(this).is(':checked')) {
	        	$('.modifyemailConfiguration_testMailAddress').parent('li').show();
	        } else {
	        	$('.modifyemailConfiguration_testMailAddress').parent('li').hide();
	        }
	    });
		
		// 发出使用SMTP验证Change事件
        $('.modifyemailConfiguration_smtpAuthType').trigger("change");
		// 发出Mail Type的Change事件
        $('.modifyemailConfiguration_mailType').trigger("change");
	})(jQuery);
</script>
