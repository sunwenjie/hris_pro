<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.base.web.renders.security.PositionRender"%>
<%@ page import="com.kan.base.domain.security.StaffVO"%>

<%
	final StaffVO staffVO = (StaffVO) request.getAttribute("staffForm");
	final String username = (staffVO != null && staffVO.getUsername() != null && !staffVO.getUsername().equals("") && !staffVO.getUsername().equalsIgnoreCase("null")) ? staffVO.getUsername() : "";
%>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first"><bean:message bundle="public" key="menu.table.title.position" /> (<bean:write name="positionCount"/>)</li> 
			<li id="tabMenu2" onClick="changeTab(2,2)" ><bean:message bundle="public" key="menu.table.title.account" /></li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kantree">
			<span><a onclick="link('positionAction.do?proc=to_objectNew');"><bean:message bundle="public" key="link.new.position" /></a></span>
			<ol class="static">
				<%= PositionRender.getPositionTreeByStaffId( request, staffVO != null ? staffVO.getStaffId() : "" ) %> 
			</ol>
		</div>
		<div id="tabContent2" class="kantab" style="display:none">
			<ol class="auto">
				<li>
					<label><bean:message bundle="public" key="public.user.name" /></label> 
					<input type="text" id="username" name="username" maxlength="20" class="user_username" value="<%= username %>" />
					<input type="hidden" id="usernameBackup" name="usernameBackup" value="<%= username %>" />
				</li>
				<li>
					<label><bean:message bundle="public" key="public.password" /></label>
					<label style="width: 220px;">
						<a id="resetPassword" onclick="resetPassword();" style="display: none" href="#"><bean:message bundle="security" key="security.staff.reset.password" /></a> 
						<span><bean:message bundle="security" key="security.staff.reset.password.tips" /></span>
					</label>
				</li>
			</ol>
		</div>
	</div>
</div>	
						
<script type="text/javascript">
	(function($) {
		// ��֤�û����Ƿ��ظ� - ����ʧȥ�¼�
		$('.user_username').blur( function () { 
			// ���ҳ�������ʽ
			$(".user_username_error").remove();
			// ��ӳ�����ʾLabel
			$('.user_username').after('<label class="error user_username_error"/>');
			var parameters = encodeURI(encodeURI('username=' + $('.user_username').val()+'&staffId=<bean:write name="staffForm" property="staffId"/>'));
			// Ajax�����첽����
			$.ajax({ url: 'userAction.do?proc=check_object_html&'+parameters+'&date=' + new Date(), success: function(html){
				$('.user_username_error').html(html);
					
				// ����û�������������⣬Rollball�������
				if(html != ''){
					$('.user_username').val($('#usernameBackup').val());
				}
			}});
		});
		
		// ��֤�û����Ƿ��ظ� - �����û��¼�
		$('.user_username').keyup( function () { 
			// ���ҳ�������ʽ
			$(".user_username_error").remove();
			// ��ӳ�����ʾLabel
			$('.user_username').after('<label class="error user_username_error"/>');
			var parameters = encodeURI(encodeURI('username=' + $('.user_username').val()+'&staffId=<bean:write name="staffForm" property="staffId"/>'));
			// Ajax�����첽����
			$.ajax({ url: 'userAction.do?proc=check_object_html&'+parameters+'&date=' + new Date(), success: function(html){
				$('.user_username_error').html(html);
					
				// ����û�������������⣬Rollball�������
				if(html != ''){
					$('.user_username').val($('#usernameBackup').val());
				}
			}});
		});
	})(jQuery);
    
 	// ��������
	function resetPassword() {
		loadHtml('#messageWrapper', 'userAction.do?proc=reset_password_html&staffId=' + $('#id').val() + "&email=" + $('#bizEmail').val(), false);
    };
</script>
