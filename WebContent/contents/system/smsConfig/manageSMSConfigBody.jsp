<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="systemSMSConfig" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">短信配置新增</label>
		</div>
		<div class="inner">
			<html:form action="smsConfigAction.do?proc=add_object" styleClass="smsConfig_form">
				<fieldset>
					<ol class="auto">
						<li>
							<label>配置名 （中文）<em> *</em></label> 
							<html:text property="nameZH" maxlength="50" styleClass="smsConfig_namezh" /> 
						</li>
						<li>
							<label>配置名 （英文）<em> *</em></label> 
							<html:text property="nameEN" maxlength="50" styleClass="smsConfig_nameen" /> 
						</li>
						<li>
							<label>服务器地址<em> *</em></label> 
							<html:text property="serverHost" maxlength="100" styleClass="smsConfig_serverHost" /> 
						</li>
						<li>
							<label>服务器端口<em> *</em></label> 
							<html:text property="serverPort" maxlength="5" styleClass="smsConfig_serverPort" /> 
						</li>
						<li>
							<label>用户名<em> *</em></label> 
							<html:text property="username" maxlength="25" styleClass="smsConfig_username" /> 
						</li>
						<li>
							<label>密码<em> *</em></label> 
							<html:password property="password" maxlength="25" styleClass="smsConfig_password" /> 
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>价格（元）</label> 
							<html:text property="price" maxlength="10" styleClass="smsConfig_price" /> 
						</li>
						<li>
							<label>发送延时</label> 
							<html:text property="sendTime" maxlength="10" styleClass="smsConfig_sendTime" /> 
						</li>
						<li>
							<label>发送类型</label> 
							<html:text property="sendType" maxlength="5" styleClass="smsConfig_sendType" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="smsConfig_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol>
						<li class="required"><label><em>* </em>Required field</label></li>
					</ol>
					<p>
						<input type="hidden" id="configId" name="configId" value="<bean:write name="smsConfigForm" property="encodedId" />" />
						<html:hidden property="subAction" styleClass="subAction" /> 
						<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" /> 
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
						<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
					</p>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_SMSConfig').addClass('selected');
		
		// 按钮提交事件
		function btnSubmit() {
			var flag = 0;
			flag = flag + validate("smsConfig_namezh", true, "common", 50, 0);
			flag = flag + validate("smsConfig_nameen", true, "common", 50, 0);
			flag = flag + validate("smsConfig_serverHost", true, "common", 100, 0);
			flag = flag + validate("smsConfig_serverPort", true, "numeric", 5, 0);
			flag = flag + validate("smsConfig_username", true, "common", 25, 0);
			flag = flag + validate("smsConfig_password", true, "common", 25, 0);
			flag = flag + validate("smsConfig_price", false, "currency", 0, 0);
			flag = flag + validate("smsConfig_status", true, "common", 0, 0);
			
			if(flag == 0){
				$('.smsConfig_form').submit();
			}
		};
		
		// 保存按钮点击事件
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('smsConfig_form');
				// 更改Subaction
        		$('.subAction').val('modifyObject');
        		// 更改页面标题
        		$('#pageTitle').html('短信配置编辑');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.smsConfig_form').attr('action', 'smsConfigAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// 初始化隐藏编辑按钮
		$('#btnEdit').hide();
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('smsConfig_form');
			// 更换Page Title
			$('#pageTitle').html('短信配置查询');
			// 更换按钮Value
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		$('#btnCancel').click( function () {
			back();
		});
	})(jQuery);
</script>
