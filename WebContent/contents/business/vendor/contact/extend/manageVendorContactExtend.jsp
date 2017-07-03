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
		// ��֤�û����Ƿ��ظ� - ����ʧȥ�¼�
		$('.vendor_user_username').blur( function () { 
			// ���ҳ�������ʽ
			$(".vendor_user_username_error").remove(); 
			// ��ӳ�����ʾLabel
			$('.vendor_user_username').after('<label class="error vendor_user_username_error"/>');
			
			// Ajax�����첽����
			$.ajax({ url: 'vendorUserAction.do?proc=check_object_html&username=' + $('.vendor_user_username').val() + '&date=' + new Date(), success: function(html){
				$('.vendor_user_username_error').html(html);
					
				// ����û�������������⣬Rollball�������
				if(html != ''){
					$('.vendor_user_username').val($('#usernameBackup').val());
				}
			}});
		});
			
		// ��֤�û����Ƿ��ظ� - �����û��¼�
		$('.vendor_user_username').keyup( function () { 
			// ���ҳ�������ʽ
			$(".vendor_user_username_error").remove();
			// ��ӳ�����ʾLabel
			$('.vendor_user_username').after('<label class="error vendor_user_username_error"/>');
				
			// Ajax�����첽����
			$.ajax({ url: 'vendorUserAction.do?proc=check_object_html&username=' + $('.vendor_user_username').val() + '&date=' + new Date(), success: function(html){
					$('.vendor_user_username_error').html(html);
				}
			});
		});
			
	})(jQuery);

		// ��������
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
