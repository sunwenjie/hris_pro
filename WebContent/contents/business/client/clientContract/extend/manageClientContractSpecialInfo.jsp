<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.hro.web.renders.biz.client.ClientRender"%>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderHeaderVO"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientContractVO"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<% 
	final ClientContractVO clientContractVO = (ClientContractVO) request.getAttribute("clientContractForm"); 
%>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<kan:auth action="<%=ClientOrderHeaderAction.getAccessAction(request,response)%>" right="view">
				<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first" >服务订单 (<span id="numberOfClientOrder"><bean:write name="clientOrderHeaderCount"/></span>)</li> 
			</kan:auth>
			<li id="tabMenu2" onClick="changeTab(2,2)" >附件 (<span id="numberOfAttachment"><bean:write name="attachmentCount"/></span>)</li> 
		</ul> 
	</div> 
	<div class="tabContent">
		<kan:auth action="<%=ClientOrderHeaderAction.getAccessAction(request,response)%>" right="view">
			<div id="tabContent1" class="kantab" >
				<kan:auth right="new" action="<%=ClientOrderHeaderAction.getAccessAction(request,response)%>" owner="<%=clientContractVO.getOwner() %>">
					<span><a id="addOrder" onclick="addExtraObject('clientOrderHeaderAction.do?proc=to_objectNew&contractId=<bean:write name="clientContractForm" property="encodedId"/>', '<bean:write name="clientContractForm" property="encodedId"/>');" class="kanhandle" >添加订单</a></span>	
				</kan:auth>
				<ol class="auto">
					<%
						final List< Object > clientOrderHeaderVOs = ( List< Object > ) request.getAttribute( "clientOrderHeaderVOs" );
							
				      	if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
				      	{
				         	// 遍历
				         	for ( Object clientOrderHeaderVOObject : clientOrderHeaderVOs ){
				         	   final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) clientOrderHeaderVOObject;
				         	   clientOrderHeaderVO.reset(null, request);
					%>
							<li>
								<kan:auth right="delete" action="<%=ClientOrderHeaderAction.getAccessAction(request,response)%>" owner="<%=clientOrderHeaderVO.getOwner()%>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
								<%
									if(clientOrderHeaderVO.getStatus() != null && (clientOrderHeaderVO.getStatus().trim().equals("1") || clientOrderHeaderVO.getStatus().trim().equals("4") || clientOrderHeaderVO.getStatus().trim().equals("8"))){
								%>	
										<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderHeaderAction.do?proc=delete_object_ajax&orderHeaderId=<%=clientOrderHeaderVO.getEncodedId()%>', this, '#numberOfClientOrder');" src="images/warning-btn.png">
								<%
									}else{
								%>
										<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" src="images/disable-btn.png">
								<%
									}
								%>
								</kan:auth>
								&nbsp;&nbsp;
								<kan:auth right="modify" action="<%=ClientOrderHeaderAction.getAccessAction(request,response)%>" owner="<%=clientOrderHeaderVO.getOwner()%>">
								<a href="#" onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<%=clientOrderHeaderVO.getEncodedId()%>');">
								</kan:auth>
									<%=clientOrderHeaderVO.getDescription()%> <%=clientOrderHeaderVO.getStartDate()%><%=clientOrderHeaderVO.getEndDate() != null && !clientOrderHeaderVO.getEndDate().trim().isEmpty() ? " ~ " + clientOrderHeaderVO.getEndDate() : ""%>
								<kan:auth right="modify" action="<%=ClientOrderHeaderAction.getAccessAction(request,response)%>" owner="<%=clientOrderHeaderVO.getOwner()%>">
								</a>
								</kan:auth>
								<%
									if(clientOrderHeaderVO.getStatus() != null && (clientOrderHeaderVO.getStatus().trim().equals("3") || clientOrderHeaderVO.getStatus().trim().equals("5"))){
								%>	
										<span class="agreelight">（<%=clientOrderHeaderVO.getDecodeStatus() %>）</span>
								<%   
									}else if(clientOrderHeaderVO.getStatus() != null && (clientOrderHeaderVO.getStatus().trim().equals("6") || clientOrderHeaderVO.getStatus().trim().equals("7"))){
								%>
										<span class="highlight">（<%=clientOrderHeaderVO.getDecodeStatus() %>）</span>
								<%   
									}else{
								%>
										<span class="">（<%=clientOrderHeaderVO.getDecodeStatus() %>）</span>
								<%   
									}
								%>
							</li>
					<%
					    	}
				      	}
					%>
				</ol>
			</div>
		</kan:auth>
		<div id="tabContent2" class="kantab" style="display:none" >
			<span><a name="uploadAttachment" id="uploadAttachment" onclick="uploadObject.submit();" class="kanhandle" >上传附件</a></span>	
			<ol id="attachmentsOL" class="auto">
				<%= AttachmentRender.getAttachments(request, clientContractVO.getAttachmentArray(), null) %>
			</ol>
		</div>
	</div>
</div>	

<input type="hidden" id="forwardURL" name="forwardURL" value="" />
						
<script type="text/javascript">
	(function($){
		
		$(".tabMenu li").first().addClass('hover');
		$(".tabMenu li").first().addClass('first');
		$("#tabContent .kantab").first().show();
		
		// 页面初始化 - 除新建情况
		if(getSubAction() != 'createObject'){
			// 加载合同选项
			loadContractOptions('<bean:write name="clientContractForm" property="masterContractId"/>');
			// 加载账单地址选项
			loadInvoiceAddressOptions('<bean:write name="clientContractForm" property="invoiceAddressId"/>');
			// 加载商务合同模板选项
			loadBusinessContractTemplateOptions('<bean:write name="clientContractForm" property="templateId"/>');
			
			disableLink('manage_primary_form');
			$('.manage_primary_form input#subAction').val('viewObject');
			
			<%
				if(clientContractVO.getStatus() != null && (clientContractVO.getStatus().trim().equals("3") || clientContractVO.getStatus().trim().equals("5") || clientContractVO.getStatus().trim().equals("6"))){
			%>
					enableLinkById('#addOrder');
					$('li #warning_img').each(function(){$(this).show();}); 
					$('li #disable_img').each(function(){$(this).hide();});
			<%	   
				}
			%>
		}
		// 页面初始化 - 新建
		else{
			// 存在ClientId的情况
			if( $('#clientId').val() != '' ){
				$('#clientId').trigger('keyup');
			}
			
			// 客户ID添加着重背景
			$('#clientId').addClass('important');
			
			// Disable控件
			$('#clientNameZH').attr('disabled', 'disabled');
			$('#clientNameEN').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
			
			disableLinkById('#addOrder');
		}
		
		var uploadObject = createUploadObject('uploadAttachment', 'common', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT_CONTRACT %>/<%= BaseAction.getAccountId(request, response) %>/<%= BaseAction.getUsername(request, response) %>/');
	})(jQuery);
</script>