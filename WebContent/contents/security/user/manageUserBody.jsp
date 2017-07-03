<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.action.BaseAction"%>

<div id="content">
	<div id="systemUser" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message key="user.adduser"/></label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<html:form action="userAction.do?proc=add_object" styleClass="user_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="userId" name="userId" value="<bean:write name="userForm" property="encodedId" />" />
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="userForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message key="user.empname"/><em> *</em></label> 
							<html:text property="staffName" maxlength="50" styleClass="user_staffname" /> 
							<html:hidden property="staffId" styleClass="user_staffId" />
						</li>
						<li>
							<label><bean:message key="user.username"/><em> *</em></label> 
							<html:text property="username" maxlength="20" styleClass="user_username" />
							<input type="hidden" id="usernameBackup" name="usernameBackup">
						</li>
						<li>
							<label><bean:message key="user.bindingIPaddress"/></label> 
							<html:text property="bindIP" maxlength="50" styleClass="user_bindip" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="user_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="static">
						<li>
							<label><bean:message bundle="security" key="security.staff.reset.password" /></label>
  							<input type="checkbox" class="modifyuser_chnagepassword" value="on" name="changePassword" id="changePassword" />
						</li>
						<li>
							<label><bean:message key="user.password"/><em> *</em></label>
							<html:password property="password" maxlength="20" styleClass="user_password"/>
						</li>
						<li>
							<label><bean:message key="user.confirmpassword"/><em> *</em></label> 
							<html:password property="passwordConfirm" maxlength="20" styleClass="user_passwordconfirm" />
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
		$('#menu_security_Modules').addClass('current');
		$('#menu_security_User').addClass('selected');
		
		// 隐藏密码修改
		$('.user_password').parent('li').hide();
		$('.user_passwordconfirm').parent('li').hide();
		
		// 缓存用户名
		$('#usernameBackup').val($('.user_username').val());
		
		// 当点击需要修改密码
		$('#changePassword').click(function(){
	        if($(this).is(':checked')) {
	        	$('.user_password').parent('li').show();
	    		$('.user_passwordconfirm').parent('li').show();
	        } else {
	        	$('.user_password').parent('li').hide();
	    		$('.user_passwordconfirm').parent('li').hide();
	        }
	    });
		
		// 验证用户名是否重复 - 焦点失去事件
		$('.user_username').blur( function () { 
			// 清除页面出错样式
			$(".user_username_error").remove();
			// 如果用户名修改或增加
			if($('#usernameBackup').val() != $('.user_username').val()){
				// 添加出错显示Label
				$('.user_username').after('<label class="error user_username_error"/>');
				
				// Ajax进行异步调用
				$.ajax({ url: 'userAction.do?proc=check_object_html&username=' + $('.user_username').val() + '&date=' + new Date(), success: function(html){
					$('.user_username_error').html(html);
					
					// 如果用户名输入出现问题，Rollball此输入框
					if(html != ''){
						$('.user_username').val($('#usernameBackup').val());
					}
				}});
			}
		});
		
		// 验证用户名是否重复 - 键盘敲击事件
		$('.user_username').keyup( function () { 
			// 清除页面出错样式
			$(".user_username_error").remove();
			// 如果用户名修改或增加
			if($('#usernameBackup').val() != $('.user_username').val()){
				// 添加出错显示Label
				$('.user_username').after('<label class="error user_username_error"/>');
				
				// Ajax进行异步调用
				$.ajax({ url: 'userAction.do?proc=check_object_html&username=' + $('.user_username').val() + '&date=' + new Date(), success: function(html){
					$('.user_username_error').html(html);
				}});
			}
		});
		
		// 按钮提交事件
		function btnSubmit() {
			var flag = 0;
			if($('.subAction').val() != 'modifyObject'){
				flag = flag + validate('user_staffname', true, 'common', 50, 2);
			}
			
			if($('.user_username').val() != 'Admin'){
				flag = flag + validate('user_username', true, 'common', 20, 2);
			}
			
			flag = flag + validate('user_bindip', false, 'ip', 0, 0);
			flag = flag + validate('user_status', true, 'common', 0, 0);
			
			if($('#changePassword').is(':checked')){
    			flag = flag + validate('user_password', true, 'password', 20, 6);
    			flag = flag + validate('user_passwordconfirm', true, 'passwordconfirm', 20, 6);
			}
			
			if(flag == 0){
				enableForm('user_form');
				$('.user_username').val('<bean:write name="userForm" property="username" />');
				submit('user_form');
			}
		};
        
		// 编辑按钮点击事件
        $('#btnEdit').click(function(){
        	if($('.subAction').val() == 'viewObject'){
        		enableForm('user_form');
        		<logic:equal name="userForm" property="username" value="Admin">
    				$('.user_role').attr('disabled', 'disabled');
    				$('.user_username').attr('disabled', 'disabled');
    				$('.user_status').attr('disabled', 'disabled');
    			</logic:equal>
    			// 员工姓名不可修改
    			$('.user_staffname').attr('disabled', 'disabled');
    			// 用户名不可编辑
    			$('.user_username').attr('disabled', 'disabled');
    			// 更改Subaction
        		$('.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.user_form').attr('action', 'userAction.do?proc=modify_object');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message key="user.change"/>');
        	}else{
        		btnSubmit();
        	}
        });
		
     	// 保存按钮点击事件
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
     	// 初始化隐藏编辑按钮
		$('#btnEdit').hide();
		
		// 取消按钮点击事件
		$('#btnCancel').click( function () {
			if($('.subAction').val() == 'modifyObject'){
				//	Form不可编辑
				disableForm('user_form');
				//	修改subAction
				$('.subAction').val('viewObject');
				//	修改编辑按钮
	    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
				//	修改标题名称
				$('#pageTitle').html('<bean:message key="user.view"/>');
			}else{
				//	跳转到list页面
				link('userAction.do?proc=list_object');
			}
		});
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('user_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message key="user.view"/>');
			// 更换按钮Value
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		// 密码输入框键盘输入事件
		$('.user_password').keyup( function () { 
			validate('user_password', true, 'password', 20, 6);
		});
	})(jQuery);
	
</script>
