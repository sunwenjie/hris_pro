<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first">登录账户</li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kantree">
			<ol class="auto">
				<li style="margin-bottom: 0px;">
					<label>用户名</label> 
					<input type="text" id="username" name="username" maxlength="20" class="client_user_username" value="<bean:write name="username"/>" />
					<input type="hidden" id="usernameBackup" name="usernameBackup" value="<bean:write name="username"/>" />
				</li>
				<li style="margin-bottom: 0px;">
					<label>密码</label>
					<label style="width: 220px;"><a id="resetPassword" onclick="resetPassword('<bean:write name="clientUserId"/>');" style="display: none" href="#">重设密码</a> <span>（自动发送至“公司邮箱”）</span></label>
				</li>
			</ol>
		</div>
	</div> 
</div>	
						
<script type="text/javascript">
		(function($) {
			// 验证用户名是否重复 - 焦点失去事件
			$('.client_user_username').blur( function () {
				// 清除页面出错样式
				$(".client_user_username_error").remove();
				// 添加出错显示Label
				$('.client_user_username').after('<label class="error client_user_username_error"/>');
				
				// Ajax进行异步调用
				$.ajax({ url: 'clientUserAction.do?proc=check_object_html&username=' + $('.client_user_username').val() + '&clientId=' + $('.clientId').val() + '&date=' + new Date(), success: function(html){
					$('.client_user_username_error').html(html);
						
					// 如果用户名输入出现问题，Rollball此输入框
					if(html != ''){
						$('.client_user_username').val($('#usernameBackup').val());
					}
				}});
			});
		})(jQuery);
		
		function resetPassword(staffId){
			if($('#username').val()!=''){ 
				var checkUsername = validate('bizEmail',true, 'bizEmail', 0 ,0, 0,0);
				if(checkUsername>0){
					return;
				}
			};
			loadHtml('#messageWrapper', 'clientUserAction.do?proc=reset_password_html&staffId=' + staffId, false);
		}
</script>
