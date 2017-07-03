<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first">��¼�˻�</li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kantree">
			<ol class="auto">
				<li style="margin-bottom: 0px;">
					<label>�û���</label> 
					<input type="text" id="username" name="username" maxlength="20" class="client_user_username" value="<bean:write name="username"/>" />
					<input type="hidden" id="usernameBackup" name="usernameBackup" value="<bean:write name="username"/>" />
				</li>
				<li style="margin-bottom: 0px;">
					<label>����</label>
					<label style="width: 220px;"><a id="resetPassword" onclick="resetPassword('<bean:write name="clientUserId"/>');" style="display: none" href="#">��������</a> <span>���Զ�����������˾���䡱��</span></label>
				</li>
			</ol>
		</div>
	</div> 
</div>	
						
<script type="text/javascript">
		(function($) {
			// ��֤�û����Ƿ��ظ� - ����ʧȥ�¼�
			$('.client_user_username').blur( function () {
				// ���ҳ�������ʽ
				$(".client_user_username_error").remove();
				// ��ӳ�����ʾLabel
				$('.client_user_username').after('<label class="error client_user_username_error"/>');
				
				// Ajax�����첽����
				$.ajax({ url: 'clientUserAction.do?proc=check_object_html&username=' + $('.client_user_username').val() + '&clientId=' + $('.clientId').val() + '&date=' + new Date(), success: function(html){
					$('.client_user_username_error').html(html);
						
					// ����û�������������⣬Rollball�������
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
