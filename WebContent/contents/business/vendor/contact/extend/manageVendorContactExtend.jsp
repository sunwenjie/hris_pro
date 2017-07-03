<%@page import="com.kan.hro.domain.biz.vendor.VendorContactVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first"><bean:message bundle="public" key="menu.table.title.account" /></li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kantree">
			<ol class="auto">
				<li style="margin-bottom: 0px;">
					<label><bean:message bundle="public" key="public.user.name" /></label>
					<input type="text" id="username" name="username" maxlength="20" class="vendor_user_username" value='<logic:present name="vendorContactForm"><bean:write name="vendorContactForm" property="username"/></logic:present>'/>
					<input type="hidden" id="usernameBackup" name="usernameBackup" value='<logic:present name="vendorContactForm"><bean:write name="vendorContactForm" property="username"/></logic:present>'/>
				</li>
				<li style="margin-bottom: 0px;">
					<label><bean:message bundle="public" key="public.password" /></label>
					<label style="width: 220px;"><a id="resetPassword" onclick="resetPassword();" style="display: none" href="#"><bean:message bundle="security" key="security.staff.reset.password" /></a> <span><bean:message bundle="security" key="security.staff.reset.password.tips" /></span></label>
				</li>
			</ol>
		</div>
	</div> 
</div>	
						
<script type="text/javascript">
	(function($) {
		// 验证用户名是否重复 - 焦点失去事件
		$('.vendor_user_username').blur( function () { 
			// 清除页面出错样式
			$(".vendor_user_username_error").remove(); 
			// 添加出错显示Label
			$('.vendor_user_username').after('<label class="error vendor_user_username_error"/>');
			
			// Ajax进行异步调用
			$.ajax({ url: 'vendorUserAction.do?proc=check_object_html&username=' + $('.vendor_user_username').val() + '&date=' + new Date(), success: function(html){
				$('.vendor_user_username_error').html(html);
					
				// 如果用户名输入出现问题，Rollball此输入框
				if(html != ''){
					$('.vendor_user_username').val($('#usernameBackup').val());
				}
			}});
		});
			
		// 验证用户名是否重复 - 键盘敲击事件
		$('.vendor_user_username').keyup( function () { 
			// 清除页面出错样式
			$(".vendor_user_username_error").remove();
			// 添加出错显示Label
			$('.vendor_user_username').after('<label class="error vendor_user_username_error"/>');
				
			// Ajax进行异步调用
			$.ajax({ url: 'vendorUserAction.do?proc=check_object_html&username=' + $('.vendor_user_username').val() + '&date=' + new Date(), success: function(html){
					$('.vendor_user_username_error').html(html);
				}
			});
		});
			
	})(jQuery);

		// 重设密码
		function resetPassword() {
			if($('#username').val()!=''){ 
				var checkUsername = validate('bizEmail',true, 'email', 0 ,0, 0,0);
				if(checkUsername>0){
					return;
				}
			};
			loadHtml('#messageWrapper', 'vendorUserAction.do?proc=reset_password_html&email='+$('.bizEmail').val()+'&vendorContactId=<logic:present name="vendorContactForm"><bean:write name="vendorContactForm" property="encodedId"/></logic:present>', false);
	    };
</script>
