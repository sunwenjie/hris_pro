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
				<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first">客户 (<span id="numberOfClient"><bean:write name="clientCount"/></span>)</li> 
			</kan:auth>
		</ul> 
	</div> 
	<div class="tabContent">
		<kan:auth action="<%=ClientAction.accessAction%>" right="view">
			<div id="tabContent1" class="kantab">
				<kan:auth right="new" action="<%=ClientAction.accessAction%>">
					<span><a onclick="addExtraObject('clientAction.do?proc=to_objectNew&groupId=<bean:write name="clientGroupForm" property="encodedId" />', '<bean:write name="clientGroupForm" property="encodedId" />');" class="kanhandle" >新增客户</a></span>
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
		
		// 如果是新建则隐藏客户联想添加栏
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

	// 添加客户事件
	// Reviewed by Kevin Jin at 2013-11-07
	function addClient(){
		// 重复标记
		var repeatFlag = false;
		// 获取所要添加的客户ID
		var clientId = $('#clientId').val();
		// 遍历判断是否重复
		$("input[id='clientIdArray']").each( 
			function(){
				if($(this).val() == clientId){
					repeatFlag = true;
				}
			}
		);
		
		// 如果已经包含则不再添加，否则进行添加
		if($('#clientId').val() == '')
		{
			alert("请填写必要的信息！");
		}
		else if(repeatFlag)
		{
			alert("相同客户只能添加一次！");
		}
		else
		{
			$.ajax({url: 'clientAction.do?proc=get_object_json&clientId=' + clientId + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					if(data.success == 'true'){
						if(data.groupId){
							// 如果建立集团与客户从属关系
							if(confirm("选定客户（ " + data.clientName + " ）已绑定集团（ " + data.groupName + " ），确定移除该客户的现有从属关系并与当前集团建立从属关系？")){
								
								// ajax修改客户与集团关系
								$.ajax({url: 'clientAction.do?proc=modify_object_ajax&clientId=' + clientId + '&groupId=' + $('#clientGroupId').val() + '&date=' + new Date(),
									dataType : 'json',
									success: function(data){
										if(data.success == 'true'){
											$('#manageClientOL').append("<li id=\"mannageClient_" + $('#clientId').val() + "\">"
													+ "<input type=\"hidden\" id=\"clientIdArray\" name=\"clientIdArray\" value=\"" + $('#clientId').val() + "\">"
													+ "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">"
													+ "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onclick=\"removeExtraObject('clientAction.do?proc=delete_object_ajax&clientId=" + clientId + "', this, '#numberOfClient');\"/>&nbsp;&nbsp;"
													+ "<a onclick=link('clientAction.do?proc=to_objectModify&id=" + data.clientVO.encodedId + "') >" + data.clientVO.clientName + "</li>");
											$('#messageWrapper').html('<div class="message success fadable">添加成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
											messageWrapperFada();
											$('#numberOfClient').html(eval(parseInt($('#numberOfClient').html()) + 1));
											clearClientInfo();
										}
									}
								});
								
							}
							// 如果不建立集团与客户从属关系
							else
							{
								clearClientInfo();
								return;
							}
						}
						// 如果该客户没有与某个集团建立关系
						else
						{

							// ajax修改客户与集团关系
							$.ajax({url: 'clientAction.do?proc=modify_object_ajax&clientId=' + clientId + '&groupId=' + $('#clientGroupId').val() + '&date=' + new Date(),
								dataType : 'json',
								success: function(data){
									if(data.success == 'true'){
										$('#manageClientOL').append("<li id=\"mannageClient_" + $('#clientId').val() + "\">"
												+ "<input type=\"hidden\" id=\"clientIdArray\" name=\"clientIdArray\" value=\"" + $('#clientId').val() + "\">"
												+ "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">"
												+ "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onclick=\"removeExtraObject('clientAction.do?proc=delete_object_ajax&clientId=" + clientId + "', this, '#numberOfClient');\"/>&nbsp;&nbsp;"
												+ "<a onclick=link('clientAction.do?proc=to_objectModify&id=" + data.clientVO.encodedId + "') >" + data.clientVO.clientName + "</li>");
										$('#messageWrapper').html('<div class="message success fadable">添加成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
										messageWrapperFada();
										$('#numberOfClient').html(eval(parseInt($('#numberOfClient').html()) + 1));
										clearClientInfo();
									}
								}
							});
							
						}
					}
					// 联想查询客户无效
					else if(data.success == 'false'){
						clearClientInfo();
						alert("选择客户无效，请重新选择客户！");
					}
				}
			});
		}

	};
	
	//	清空客户联想信息输入框
	function clearClientInfo(){
		$('#clientId').val('');
		$('#clientName').val('');
		$('#clientName').val('输入关键字查看提示...').addClass(hintClass);
	}
</script>