<%@page import="com.kan.hro.domain.biz.client.ClientGroupVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.hro.web.renders.biz.client.ClientRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientGroupVO"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientAction"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<kan:auth action="<%=ClientAction.accessAction%>" right="view">
				<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first">�ͻ� (<span id="numberOfClient"><bean:write name="clientCount"/></span>)</li> 
			</kan:auth>
		</ul> 
	</div> 
	<div class="tabContent">
		<kan:auth action="<%=ClientAction.accessAction%>" right="view">
			<div id="tabContent1" class="kantab">
				<kan:auth right="new" action="<%=ClientAction.accessAction%>">
					<span><a onclick="addExtraObject('clientAction.do?proc=to_objectNew&groupId=<bean:write name="clientGroupForm" property="encodedId" />', '<bean:write name="clientGroupForm" property="encodedId" />');" class="kanhandle" >�����ͻ�</a></span>
				</kan:auth>
				<ol class="auto">
					<li>
						<input type="text" id="clientName" name="clientName" class="clientGroup_clientName" maxlength="50" />
						<input type="hidden" id="clientId" name="clientId" class="clientGroup_clientId" /> &nbsp; 
						<input type="button" value="<bean:message bundle="public" key="button.add" />" id="addClientBtn" onclick="addClient()" />
					</li>
				</ol>
				<ol class="auto" id="manageClientOL">
					<%= ClientRender.getClientNameCombo( request ) %>
				</ol>
			</div>
		</kan:auth>
	</div> 
</div>

<input type="hidden" id="forwardURL" name="forwardURL" value="" />
						
<script type="text/javascript">
	(function($){
		// Use the common thinking
		kanThinking_column('clientGroup_clientName', 'clientGroup_clientId', 'clientAction.do?proc=list_object_json_forFullView');
		
		if(!isCreate()){
			disableLink('manage_primary_form');
		}
		
		var subAction = $('.manage_primary_form #subAction').val();
		
		// ������½������ؿͻ����������
		if($('.manage_primary_form #subAction').val() == 'createObject'){
			$('div[id="tabContent1"] ol').eq(0).addClass('hide');
		}
		
		<%
			if(request.getAttribute("clientGroupForm") != null && ((ClientGroupVO)request.getAttribute("clientGroupForm")).getStatus() != null && ((ClientGroupVO)request.getAttribute("clientGroupForm")).getStatus().equals("2")){
		%>
				$('#btnEdit').click(function () {
					 $('li #warning_img').each(function() {
                         $(this).hide();
                     });
                     $('li #disable_img').each(function() {
                         $(this).show();
                     });
                     disableLink('manage_primary_form');
                     $('#special_info input, #special_info select, #special_info textarea').attr('disabled', 'disabled');
                     $('#special_info input.calendar').datepicker('disable');
                     $('#special_info input[type=button]:not(.internal)').removeAttr('disabled');
				});
		<%
			}
		%>
	})(jQuery);

	// ��ӿͻ��¼�
	// Reviewed by Kevin Jin at 2013-11-07
	function addClient(){
		// �ظ����
		var repeatFlag = false;
		// ��ȡ��Ҫ��ӵĿͻ�ID
		var clientId = $('#clientId').val();
		// �����ж��Ƿ��ظ�
		$("input[id='clientIdArray']").each( 
			function(){
				if($(this).val() == clientId){
					repeatFlag = true;
				}
			}
		);
		
		// ����Ѿ�����������ӣ�����������
		if($('#clientId').val() == '')
		{
			alert("����д��Ҫ����Ϣ��");
		}
		else if(repeatFlag)
		{
			alert("��ͬ�ͻ�ֻ�����һ�Σ�");
		}
		else
		{
			$.ajax({url: 'clientAction.do?proc=get_object_json&clientId=' + clientId + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					if(data.success == 'true'){
						if(data.groupId){
							// �������������ͻ�������ϵ
							if(confirm("ѡ���ͻ��� " + data.clientName + " ���Ѱ󶨼��ţ� " + data.groupName + " ����ȷ���Ƴ��ÿͻ������д�����ϵ���뵱ǰ���Ž���������ϵ��")){
								
								// ajax�޸Ŀͻ��뼯�Ź�ϵ
								$.ajax({url: 'clientAction.do?proc=modify_object_ajax&clientId=' + clientId + '&groupId=' + $('#clientGroupId').val() + '&date=' + new Date(),
									dataType : 'json',
									success: function(data){
										if(data.success == 'true'){
											$('#manageClientOL').append("<li id=\"mannageClient_" + $('#clientId').val() + "\">"
													+ "<input type=\"hidden\" id=\"clientIdArray\" name=\"clientIdArray\" value=\"" + $('#clientId').val() + "\">"
													+ "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">"
													+ "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onclick=\"removeExtraObject('clientAction.do?proc=delete_object_ajax&clientId=" + clientId + "', this, '#numberOfClient');\"/>&nbsp;&nbsp;"
													+ "<a onclick=link('clientAction.do?proc=to_objectModify&id=" + data.clientVO.encodedId + "') >" + data.clientVO.clientName + "</li>");
											$('#messageWrapper').html('<div class="message success fadable">��ӳɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
											messageWrapperFada();
											$('#numberOfClient').html(eval(parseInt($('#numberOfClient').html()) + 1));
											clearClientInfo();
										}
									}
								});
								
							}
							// ���������������ͻ�������ϵ
							else
							{
								clearClientInfo();
								return;
							}
						}
						// ����ÿͻ�û����ĳ�����Ž�����ϵ
						else
						{

							// ajax�޸Ŀͻ��뼯�Ź�ϵ
							$.ajax({url: 'clientAction.do?proc=modify_object_ajax&clientId=' + clientId + '&groupId=' + $('#clientGroupId').val() + '&date=' + new Date(),
								dataType : 'json',
								success: function(data){
									if(data.success == 'true'){
										$('#manageClientOL').append("<li id=\"mannageClient_" + $('#clientId').val() + "\">"
												+ "<input type=\"hidden\" id=\"clientIdArray\" name=\"clientIdArray\" value=\"" + $('#clientId').val() + "\">"
												+ "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">"
												+ "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onclick=\"removeExtraObject('clientAction.do?proc=delete_object_ajax&clientId=" + clientId + "', this, '#numberOfClient');\"/>&nbsp;&nbsp;"
												+ "<a onclick=link('clientAction.do?proc=to_objectModify&id=" + data.clientVO.encodedId + "') >" + data.clientVO.clientName + "</li>");
										$('#messageWrapper').html('<div class="message success fadable">��ӳɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
										messageWrapperFada();
										$('#numberOfClient').html(eval(parseInt($('#numberOfClient').html()) + 1));
										clearClientInfo();
									}
								}
							});
							
						}
					}
					// �����ѯ�ͻ���Ч
					else if(data.success == 'false'){
						clearClientInfo();
						alert("ѡ��ͻ���Ч��������ѡ��ͻ���");
					}
				}
			});
		}

	};
	
	//	��տͻ�������Ϣ�����
	function clearClientInfo(){
		$('#clientId').val('');
		$('#clientName').val('');
		$('#clientName').val('����ؼ��ֲ鿴��ʾ...').addClass(hintClass);
	}
</script>