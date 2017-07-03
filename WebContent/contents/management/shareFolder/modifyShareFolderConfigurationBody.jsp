<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.management.ShareFolderConfigurationAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="options" class="box toggableForm">
		<div class="head">
			<label><bean:message bundle="system" key="system.share.folder.configuration" /></label>
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
				<kan:auth right="modify" action="<%=ShareFolderConfigurationAction.accessAction%>">
					<input type="button" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
				</kan:auth>
				<kan:auth right="testlink" action="<%=ShareFolderConfigurationAction.accessAction%>">
					<input type="button" class="function" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.test.link" />" /> 
				</kan:auth>
			</div>
			<html:form action="shareFolderConfigurationAction.do?proc=modify_object" styleClass="shareFolderConfiguration_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="subAction" id="subAction" value="viewObject" />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.share.folder.configuration.host" /><em> *</em></label> 
							<html:text property="host" maxlength="100" styleClass="shareFolderConfiguration_host" />
						</li>
						<li>
							<label><bean:message bundle="system" key="system.share.folder.configuration.port" /></label> 
							<html:text property="port" maxlength="5" styleClass="shareFolderConfiguration_port" />
						</li>
						<li>
							<label><bean:message key="logon.username" /></label> 
							<html:text property="username" maxlength="50" styleClass="shareFolderConfiguration_username" />
						</li>
						<li>
							<label><bean:message key="logon.password" /></label> 
							<html:password property="password" maxlength="50" styleClass="shareFolderConfiguration_password" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.share.folder.configuration.directory" /></label> 
							<html:text property="directory" maxlength="100" styleClass="shareFolderConfiguration_directory" />
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
		$('#menu_management_ShareFolderConfiguration').addClass('selected');
		
		// 将Form设为Disable
		disableForm('shareFolderConfiguration_form');
        
		// 邮件列表选择事件
        $('#btnEdit').click(function(){
        	if($('#subAction').val() == 'viewObject'){
        		enableForm('shareFolderConfiguration_form');
        		$('#subAction').val('modifyObject');
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        	}else{
        		var flag = 0;
    			
        		flag = flag + validate('shareFolderConfiguration_host', true, 'common', 100, 0);
        		flag = flag + validate('shareFolderConfiguration_port', false, 'numeric', 5, 0);
        		
    			if(flag == 0){
    				submit('shareFolderConfiguration_form');
    			}
        	}
        });
	})(jQuery);
</script>
