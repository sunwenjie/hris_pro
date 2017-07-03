<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.system.ModuleRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientVO"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientDTO"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientInvoiceVO"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientContactVO"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientContractVO"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderHeaderVO"%>
<%@ page import="com.kan.hro.web.renders.biz.client.ClientContactRender"%>
<%@ page import="com.kan.hro.web.renders.biz.client.ClientOrderHeaderRender"%>
<%@ page import="com.kan.hro.web.renders.biz.client.ClientInvoiceRender"%>
<%@ page import="com.kan.hro.web.renders.biz.client.ClientContractRender"%>

<%@ page import="com.kan.hro.web.actions.biz.client.ClientContactAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderHeaderAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientInvoiceAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientContractAction"%>


<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final ClientDTO clientDTO = (ClientDTO) request.getAttribute("clientDTO");
	ClientVO clientVO = new ClientVO();
	
	if( clientDTO != null )
	{
	   clientVO = clientDTO.getClientVO();
	}
	else
	{
	   clientVO = (ClientVO) request.getAttribute("clientForm");
	}
	
	clientVO.reset(null, request);
%>
<style type="text/css">
	.deleteImg{
		position : absolute;  
	    top : -9px;  
	    right : 3px;  
	    z-index: 1001;
	    display: none;
	    cursor: pointer;
	    width: 32px;
	    height: 32px;
	}
	.imgtooltip{
		position : relative;  
	    margin-top: 10px; 
	}
</style>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
		<kan:auth right="view" action="<%=ClientContractAction.accessAction %>">
			<li id="tabMenu1" onClick="changeTab(1,9)" class="hover first" >商务合同 (<span id="numberOfClientContract"><bean:write name="clientContractCount"/></span>)</li> 
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderHeaderAction.getAccessAction(request, response) %>">	
			<li id="tabMenu2" onClick="changeTab(2,9)" >服务订单 (<span id="numberOfClientOrder"><bean:write name="clientOrderHeaderCount"/></span>)</li> 
		</kan:auth>
		<kan:auth right="view" action="<%=ClientInvoiceAction.accessAction %>">	
			<li id="tabMenu3" onClick="changeTab(3,9)" >对账单 (0)</li> 
		</kan:auth>
		<kan:auth right="view" action="<%=ClientInvoiceAction.accessAction %>">	
			<li id="tabMenu4" onClick="changeTab(4,9)" >账单地址 (<span id="numberOfClientInvoice"><bean:write name="clientInvoiceCount"/></span>)</li> 
		</kan:auth>
		<kan:auth right="view" action="<%=ClientInvoiceAction.accessAction %>">	
			<li id="tabMenu5" onClick="changeTab(5,9)" >联系人 (<span id="numberOfClientContact"><bean:write name="clientContactCount"/></span>)</li>
		</kan:auth>	
			<li id="tabMenu6" onClick="changeTab(6,9)" >客户形象 <img src="images/tips.png" title="推荐图片比例4:3 （800*600，400*300）"> </li>
			<li id="tabMenu7" onClick="changeTab(7,9)" >其他设置</li> 
			<li id="tabMenu8" onClick="changeTab(8,9)" >客户菜单设置</li> 
			<li id="tabMenu8" onClick="changeTab(9,9)" >员工菜单设置</li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
	<kan:auth right="view" action="<%=ClientContractAction.accessAction %>">
		<div id="tabContent1" class="kantab" >
		<kan:auth right="new" action="<%=ClientContractAction.accessAction %>" owner="<%=clientVO.getOwner() %>">
			<span id="addClientContractSpan" ><a id="addContract" onclick="addExtraObject('clientContractAction.do?proc=to_objectNew&clientId=<%=clientVO.getEncodedId() %>', '<%=clientVO.getEncodedId() %>');" class="kanhandle">添加商务合同</a></span>
		</kan:auth >
			<ol class="auto">
				<%
			      	if ( clientDTO != null && clientDTO.getClientContractVOs() != null && clientDTO.getClientContractVOs().size() > 0 )
			      	{
			         	// 遍历
			         	for ( Object clientContractVOObject : clientDTO.getClientContractVOs() ){
			         	   final ClientContractVO clientContractVO = ( ClientContractVO ) clientContractVOObject;
			         	   clientContractVO.reset(null, request);
			         	   
				%>
						<li>
						<kan:auth  right="delete" action="<%=ClientContractAction.accessAction %>" owner="<%=clientVO.getOwner() %>">
							<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
							<%
								if(clientContractVO.getStatus() != null && (clientContractVO.getStatus().trim().equals("1") || clientContractVO.getStatus().trim().equals("4"))){
							%>	
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientContractAction.do?proc=delete_object_ajax&contractId=<%=clientContractVO.getEncodedId() %>', this, '#numberOfClientContract');" src="images/warning-btn.png">
							<%   
								}else{
							%>
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" src="images/disable-btn.png">
							<%   
								}
							%>
							</kan:auth >
							&nbsp;&nbsp; <a href="#" onclick="link('clientContractAction.do?proc=to_objectModify&id=<%=clientContractVO.getEncodedId() %>');"><%=clientContractVO.getContractId() %><%=clientContractVO.getName() != null && !clientContractVO.getName().trim().isEmpty() ? "（" + clientContractVO.getName() + "）" : "" %> <%=clientContractVO.getStartDate() %><%=clientContractVO.getEndDate() != null && !clientContractVO.getEndDate().trim().isEmpty() ? " ~ " + clientContractVO.getEndDate() : "" %></a> 
							<%
								if(clientContractVO.getStatus() != null && (clientContractVO.getStatus().trim().equals("3") || clientContractVO.getStatus().trim().equals("5") || clientContractVO.getStatus().trim().equals("6"))){
							%>	
									<span class="agreelight">（<%=clientContractVO.getDecodeStatus() %>）</span>
							<%   
								}else{
							%>
									<span class="">（<%=clientContractVO.getDecodeStatus() %>）</span>
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
		<kan:auth right="view" action="<%=ClientOrderHeaderAction.getAccessAction(request, response) %>">	
		<div id="tabContent2" class="kantab" style="display:none">
		<kan:auth  right="new" action="<%=ClientOrderHeaderAction.getAccessAction(request, response) %>" owner="<%=clientVO.getOwner() %>">
			<span id="addClientOrderSpan" ><a id="addOrder" onclick="addExtraObject('clientOrderHeaderAction.do?proc=to_objectNew&clientId=<%=clientVO.getEncodedId() %>', '<%=clientVO.getEncodedId() %>');" class="kanhandle">添加订单</a></span>
		</kan:auth >	
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
						<kan:auth  right="delete" action="<%=ClientOrderHeaderAction.getAccessAction(request, response) %>" owner="<%=clientVO.getOwner() %>">
							<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
							<%
								if(clientOrderHeaderVO.getStatus() != null && (clientOrderHeaderVO.getStatus().trim().equals("1") || clientOrderHeaderVO.getStatus().trim().equals("4") || clientOrderHeaderVO.getStatus().trim().equals("8"))){
							%>	
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderHeaderAction.do?proc=delete_object_ajax&orderHeaderId=<%=clientOrderHeaderVO.getEncodedId() %>', this, '#numberOfClientOrder');" src="images/warning-btn.png">
							<%   
								}else{
							%>
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" src="images/disable-btn.png">
							<%   
								}
							%>
							</kan:auth >
							&nbsp;&nbsp; <a href="#" onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<%=clientOrderHeaderVO.getEncodedId() %>');"><%=clientOrderHeaderVO.getOrderHeaderId() %> <%=clientOrderHeaderVO.getStartDate() %><%=clientOrderHeaderVO.getEndDate() != null && !clientOrderHeaderVO.getEndDate().trim().isEmpty() ? " ~ " + clientOrderHeaderVO.getEndDate() : "" %></a> 
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
		
		<kan:auth right="view" action="<%=ClientInvoiceAction.accessAction %>">	
		<div id="tabContent3" class="kantab" style="display:none">
		<kan:auth  right="new" action="<%=ClientInvoiceAction.accessAction %>" owner="<%=clientVO.getOwner() %>">
			<span id="addClientInvoiceSpan" ><a onclick="" class="kanhandle">添加对账单</a></span>
		</kan:auth >	
		</div>
		<div id="tabContent4" class="kantab" style="display:none">
		<kan:auth  right="new" action="<%=ClientInvoiceAction.accessAction %>" owner="<%=clientVO.getOwner() %>">
			<span id="addClientInvoiceSpan" ><a onclick="addExtraObject('clientInvoiceAction.do?proc=to_objectNew&clientId=<%=clientVO.getEncodedId() %>', '<%=clientVO.getEncodedId() %>');" class="kanhandle">添加账单地址</a></span>
		</kan:auth >
			<ol class="auto">
				<%
			      	if ( clientDTO != null && clientDTO.getClientInvoiceVOs() != null && clientDTO.getClientInvoiceVOs().size() > 0 )
			      	{
			         	// 遍历
			         	for ( Object clientInvoiceVOObject : clientDTO.getClientInvoiceVOs() ){
			         	   final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) clientInvoiceVOObject;
			         	   clientInvoiceVO.reset(null, request);
			         	   
				%>
						<li>
						<kan:auth  right="delete" action="<%=ClientInvoiceAction.accessAction %>" owner="<%=clientVO.getOwner() %>">
							<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
							<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientInvoiceAction.do?proc=delete_object_ajax&invoiceId=<%=clientInvoiceVO.getEncodedId() %>', this, '#numberOfClientInvoice');" src="images/warning-btn.png">
						</kan:auth >	
							&nbsp;&nbsp; <a href="#" onclick="link('clientInvoiceAction.do?proc=to_objectModify&id=<%=clientInvoiceVO.getEncodedId() %>');"><%=clientInvoiceVO.getAddress() != null && !clientInvoiceVO.getAddress().trim().isEmpty() ?  clientInvoiceVO.getAddress() : "" %><%=clientInvoiceVO.getName() != null && !clientInvoiceVO.getName().trim().isEmpty() ? " - " + clientInvoiceVO.getName() : "" %><%=clientInvoiceVO.getPosition() != null && !clientInvoiceVO.getPosition().trim().isEmpty() ? " / " + clientInvoiceVO.getPosition() : "" %></a> 
							<span class="">（<%=clientInvoiceVO.getDecodeStatus() %>）</span>
						</li>
				<%
				    	}
			      	}
				%>
			</ol>
		</div>
		</kan:auth>
		<kan:auth right="view" action="<%=ClientInvoiceAction.accessAction %>">	
		<div id="tabContent5" class="kantab" style="display:none" >
		<kan:auth  right="new" action="<%=ClientInvoiceAction.accessAction %>" owner="<%=clientVO.getOwner() %>">
			<span id="addClientContactSpan" ><a onclick="addExtraObject('clientContactAction.do?proc=to_objectNew&clientId=<%=clientVO.getEncodedId() %>', '<%=clientVO.getEncodedId() %>');" class="kanhandle">添加联系人</a></span>
		</kan:auth >	
			<ol class="auto">
				<%
			      	if ( clientDTO != null && clientDTO.getClientContactVOs() != null && clientDTO.getClientContactVOs().size() > 0 )
			      	{
			         	// 遍历
			         	for ( Object clientContactVOObject : clientDTO.getClientContactVOs() ){
			         	   final ClientContactVO clientContactVO = ( ClientContactVO ) clientContactVOObject;
			         	   clientContactVO.reset(null, request);
			         	   
				%>
						<li>
						<kan:auth  right="delete" action="<%=ClientInvoiceAction.accessAction %>" owner="<%=clientContactVO.getOwner() %>">
							<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
							<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientContactAction.do?proc=delete_object_ajax&contactId=<%=clientContactVO.getEncodedId() %>', this, '#numberOfClientContact');" src="images/warning-btn.png">
						</kan:auth >	
							&nbsp;&nbsp; <a href="#" onclick="link('clientContactAction.do?proc=to_objectModify&id=<%=clientContactVO.getEncodedId() %>');"><%=clientContactVO.getName() != null && !clientContactVO.getName().trim().isEmpty() ?  clientContactVO.getName() : "" %><%=clientContactVO.getTitle() != null && !clientContactVO.getTitle().trim().isEmpty() ? " / " + clientContactVO.getTitle() : "" %></a> 
							<span class="">（<%=clientContactVO.getDecodeStatus() %>）</span>
						</li>
				<%
				    	}
			      	}
				%>
			</ol>
		</div>
		</kan:auth>
		
		<div id="tabContent6" class="kantab" style="display: none">
			<span><a name="addPhoto" id="addPhoto" class="kanhandle">添加形象照片</a></span>
				<div id="attachmentsDiv">
				<ol id="attachmentsOL" class="auto thumb" >
					<% final ClientVO tempClientVO = (ClientVO)request.getAttribute("clientForm"); %>
					<%= AttachmentRender.getPhotos(request, tempClientVO.getImageFile(), null) %>
				</ol>
			</div>
		</div>
		<div id="tabContent7" class="kantab" style="display:none" >
			<ol class="auto" id="mannageOtherSettingDiv">
				 <li>
					<label>公司LOGO <a title="推荐图片尺寸160*38"><img src="images/tips.png" /></a></label> 
					<span id="logoFileOL">
						<span>
							<logic:notEmpty name="clientForm" property="logoFile">
								<input type="hidden" id="logoFile" name="logoFile" value='<bean:write name="clientForm" property="logoFile"/>' />
								<input type="hidden" id="logoFile" name="logoFileSize" value="<bean:write name="clientForm" property="logoFileSize"/>" />
								<img name="disable_img" width="12" height="12" id="disable_img"  src="images/disable-btn.png">
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="$(this).parent().remove();" src="images/warning-btn.png">
								&nbsp;<bean:write name="clientForm" property="logoFileName"/>
							</logic:notEmpty>
						</span>
					</span>
					<span ><a name="uploadLogoFile" id="uploadLogoFile" class="kanhandle" >上传图片</a></span>
				</li>
			</ol>
			<ol class="auto">
				 <li>
				 	<label>订单绑定合同</label>
				 	<%= KANUtil.getSelectHTML(clientVO.getOrderBindContracts(), "orderBindContract", "manageClient_orderBindContract", clientVO.getOrderBindContract(), null, null) %>
				 </li>
				 <li>
				 	<label>社保公积金申报条件</label>
				 	<%= KANUtil.getSelectHTML(clientVO.getSbGenerateConditions(), "sbGenerateCondition", "small manageClient_sbGenerateCondition", clientVO.getSbGenerateCondition(), null, null) %>
				 	<%= KANUtil.getSelectHTML(clientVO.getSbGenerateConditionSCs(), "sbGenerateConditionSC", "small manageClient_sbGenerateConditionSC", clientVO.getSbGenerateConditionSC(), null, null) %>
				 </li>
				 <li>
				 	<label>商保申购条件</label>
				 	<%= KANUtil.getSelectHTML(clientVO.getCbGenerateConditions(), "cbGenerateCondition", "small manageClient_cbGenerateCondition", clientVO.getCbGenerateCondition(), null, null) %>
				 	<%= KANUtil.getSelectHTML(clientVO.getCbGenerateConditionSCs(), "cbGenerateConditionSC", "small manageClient_cbGenerateConditionSC", clientVO.getCbGenerateConditionSC(), null, null) %>
				 </li>
				 <li>
				 	<label>结算处理条件</label>
				 	<%= KANUtil.getSelectHTML(clientVO.getSettlementConditions(), "settlementCondition", "small manageClient_settlementCondition", clientVO.getSettlementCondition(), null, null) %>
				 	<%= KANUtil.getSelectHTML(clientVO.getSettlementConditionSCs(), "settlementConditionSC", "small manageClient_settlementConditionSC", clientVO.getSettlementConditionSC(), null, null) %>
				 </li>
			</ol>
		</div>
		
		<div id="tabContent8" class="kantab kantree" style="display:none" >
			<%= ModuleRender.getClientTree( request,response,false,"4",clientVO.getClientId()) %>
		</div>
		
		<div id="tabContent9" class="kantab kantree" style="display:none" >
			<%= ModuleRender.getClientTree( request,response,false,"5",clientVO.getClientId()) %>
		</div>
		
	</div> 
</div>	

<input type="hidden" id="forwardURL" name="forwardURL" value="" />
					
<script type="text/javascript">
	(function($){
		
		$(".tabMenu li").first().addClass('hover');
		$(".tabMenu li").first().addClass('first');
		$("#tabContent .kantab").first().show();
		// 客户状态为批准才可新建“订单”、“合同”
		<%
			if(clientVO.getStatus() != null && !(clientVO.getStatus().trim().equals("3") ))
			{
		%>
				disableLinkById('#addContract');
				disableLinkById('#addOrder');
		<%	   
			}
		%>
		
		imageMouseover();
	})(jQuery);

	(function($){
		// 查看 页面模式
		if(getSubAction() == 'createObject')
		{
			$('#status').attr('disabled', 'disabled');
			if($('#status').val() != 3)
			{
				disableLinkById("#addContract");
				disableLinkById("#addOrder");
			}
		}else{
			disableLink('manage_primary_form');
		}
		 $('#btnEdit').click(function(){
			var uploadObject = __createUploadObject('uploadLogoFile', 'image', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_ACCOUNT %>/<%= BaseAction.getAccountId(request, response) %>/');
			$('img[id^=warning_img]').show();
			$('img[id^=disable_img]').hide();
		 });
		
	})(jQuery);
	
	// 移除客户联系人规则事件
	function removeClientContact(id){
		if(confirm("确定删除选中项？"))
		{
			$('#mannageClientContact_' + id).remove();
		};
		
		var selectedIdString = "";
		//	遍历clientinfo区域生成selectedIdString
		$('input[id="clientContactIdArray"]').each(function(i) {
			if(selectedIdString == ""){
				selectedIdString = $(this).val();
			}else{
				selectedIdString = selectedIdString + ',' + $(this).val();
			}
		});
		//	给clientContact赋值
		$('#clientContact').val(selectedIdString);
	}; 

	// 移除账单地址规则事件
	function removeClientInvoice(id){
		if(confirm("确定删除选中项？")){
			$('#mannageClientInvoice_' + id).remove();
		}
		
		var selectedIdString = "";
		//	遍历clientinfo区域生成selectedIdString
		$('input[id="clientInvoiceIdArray"]').each(function(i) {
			if(selectedIdString == ""){
				selectedIdString = $(this).val();
			}else{
				selectedIdString = selectedIdString + ',' + $(this).val();
			}
		});
		//	给clientInvoice赋值
		$('#clientInvoice').val(selectedIdString);
	}; 

	// 移除合同规则事件
	function removeClientContract(id){
		if(confirm("确定删除选中项？")){
			$('#mannageClientContract_' + id).remove();
		}
		
		var selectedIdString = "";
		//	遍历clientinfo区域生成selectedIdString
		$('input[id="clientContractIdArray"]').each(function(i) {
			if(selectedIdString == ""){
				selectedIdString = $(this).val();
			}else{
				selectedIdString = selectedIdString + ',' + $(this).val();
			}
		});
		//	给clientContract赋值
		$('#clientContract').val(selectedIdString);
	}; 

	// 移除订单事件
	function removeClientOrderHeader(id){
		if(confirm("确定删除选中项？")){
			$('#mannageClientOrderHeader_' + id).remove();
		}
		
		var selectedIdString = "";
		//	遍历clientinfo区域生成selectedIdString
		$('input[id="clientOrderHeaderIdArray"]').each(function(i) {
			if(selectedIdString == ""){
				selectedIdString = $(this).val();
			}else{
				selectedIdString = selectedIdString + ',' + $(this).val();
			}
		});
		//	给clientOrderHeader赋值
		$('#clientOrderHeader').val(selectedIdString);
	}; 

function __createUploadObject(id, fileType, attachmentFolder) {
	var postfixRandom = generateMixed(6);
	var uploadObject = new AjaxUpload(id, {
		// 文件上传Action
		action : 'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom,
		// 名称
		name : 'file',
		// 自动上传
		autoSubmit : true,
		// 返回Text格式数据
		responseType : false,
		// 上传处理
		onSubmit : function(filename, ext) {
			//createFileProgressDIV(filename);
			
			$("#logoFileOL").html($("#logoFileOL").html()
			+ '<li id="uploadFileLi" style="margin: 5px 0 0 0;"><div id="progressBarOut" style="text-align: center; width: 200px; height: 14px; padding: 1px 1px 1px 1px; font-size: 10px; border: solid #d4e4ff 1px;"><div id="progressBarIn" style="width: 0; height: 12px; background-color: #d4e4ff; margin: 0; padding: 0;"></div></div><div id="fileNameDiv" style="margin: 0 5px 0 5px; border: 0;">'
			+ filename
			+ '</div> &nbsp; <div id="progressSizeDiv" style="margin: 0; border: 0;"></div></li>');
			
			disableLinkById(id);
			setTimeout("__ajaxBackState(true,'uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"')", 500);
		},

		// 上传完成后取得文件名filename为本地取得的文件名，msg为服务器返回的信息
		onComplete : function(filename, msg) {
			// 上传可用
			enableLinkById(id);
		}
	});

	return uploadObject;
};

function __ajaxBackState(first,progressURL) {
	// Ajax调用并刷新当前上传状态
	$.post(progressURL+'&first=' + first+ '&date=' + new Date(),null,
			function(result) {
				var obj = result;
				var readedBytes = obj["readedBytes"];
				var totalBytes = obj["totalBytes"];
				var statusMsg = obj["statusMsg"];

				progressbar(readedBytes,totalBytes);

				// 正常状态
				if (obj["info"] == "0") {
					setTimeout("__ajaxBackState(false,'"+progressURL+"')", 100);
				}
				// 上传失败，提示失败原因
				else if (obj["info"] == "1") {
					alert(obj["statusMsg"]);
					$('#btnAddAttachment').attr("disabled", false);
					// 删除上传失败的文件和进度信息
					$('#uploadFileLi').remove();
				}
				// 上传完成
				else if (obj["info"] == "2") {
					$('#uploadAttachment').attr("disabled", false);
					$("#logoFileOL").html('<span><input type="hidden" id="logoFile" name="logoFile" value="'
											+ statusMsg.split('##')[0]
											+ '" /><input type="hidden" id="logoFile" name="logoFileSize" value="'
											+ statusMsg.split('##')[2]
											+ '" /><img onclick=\"$(this).parent().remove();\" src=\"images/warning-btn.png\"> &nbsp; '
											+ statusMsg.split('##')[1]
											+ "</span>");
					$('#uploadFileLi').remove();

				}
			},'json');
};
	
	function _createUploadObject(id, fileType, attachmentFolder) {
		var postfixRandom = generateMixed(6);
		var uploadObject = new AjaxUpload(id, {
			// 文件上传Action
			action : 'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom,
			// 名称
			name : 'file',
			// 自动上传
			autoSubmit : true,
			// 返回Text格式数据
			responseType : false,
			// 上传处理
			onSubmit : function(filename, ext) {
				createFileProgressDIV(filename);
				disableLinkById(id);
				setTimeout("_ajaxBackState(true,'"+"uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"',200)");
			},

			// 上传完成后取得文件名filename为本地取得的文件名，msg为服务器返回的信息
			onComplete : function(filename, msg) {
				// 上传可用
				enableLinkById(id);
			}
		});

		return uploadObject;
	};
	
	function _ajaxBackState(first,progressURL) {
		// Ajax调用并刷新当前上传状态
		$.post(progressURL+'&first=' + first+ '&date=' + new Date(),null,
				function(result) {
					var obj = result;
					var readedBytes = obj["readedBytes"];
					var totalBytes = obj["totalBytes"];
					var statusMsg = obj["statusMsg"];
					progressbar(readedBytes,totalBytes);
					
					// 正常状态
					if (obj["info"] == "0") {
						setTimeout("_ajaxBackState(false,'"+progressURL+"',200)");
					}
					// 上传失败，提示失败原因
					else if (obj["info"] == "1") {
						alert(obj["statusMsg"]);
						$('#btnAddAttachment').attr("disabled", false);
						// 删除上传失败的文件和进度信息
						$('#uploadFileLi').remove();
					}
					// 上传完成
					else if (obj["info"] == "2") {
						
						$('#uploadAttachment').attr("disabled", false);
						// xx/xx/图片.jpg
						var imagePath = statusMsg.split('##')[0];
						var imagePaths = imagePath.split('/');
						var imageDir = '';
						for(var i = 0;i<imagePaths.length-1;i++){
							imageDir = imageDir+imagePaths[i]+"/";
						}
						var imageName = encodeURIComponent(encodeURIComponent(imagePaths[imagePaths.length-1]));
						var imageSrc = "downloadFileAction.do?proc=download&fileString=/" + imageDir + imageName;
						
						$("#attachmentsOL").append('<li style="width:140px !important; height:110px !important;"><input type="hidden" id="imageFileArray" name="imageFileArray" value="'
												+ statusMsg.split('##')[0]
												+ '" />'
												+'<img src="images/close_pop.png" class="deleteImg" onclick="removeAttachment(this);">'
												+' <a class="tooltip" href="'+imageSrc+'" > <img class="imgtooltip" src="'+imageSrc+'" width="120" height="90" /> </a>'
												+ "</li>");
						$('#uploadFileLi').remove();
						$('img.deleteImg').each(function(i){
			   				$(this).show();
			   			});
						imageMouseover();
					}
				},'json');
	};
	
	function imageMouseover(){
		var x = 100;
		var y = 100;
		$("a.tooltip").mouseover(function(e){ 
			this.myTitle = this.title;
			this.title = "";
			var tooltip = "<div style='position: absolute;z-index: 1001';  id='tooltip'><img src='"+ $(this).find('img').attr('src') +"'/><\/div>"; //创建 div 元素
			$("body").append(tooltip);	//把它追加到文档中
			// 创建一个临时位置存放图片大小
			$("<img/>").attr("src", $(this).find('img').attr('src')).load(function() {
				y = this.height;
			});
			$("#tooltip")
				.css({
					"top": (e.pageY-y)+"px",
					"left": (e.pageX+x)+"px"
				}).show("fast");	  //设置x坐标和y坐标，并且显示
	    }).mouseout(function(){
			this.title = this.myTitle;	
			$("#tooltip").remove();	 //移除 
	    }).mousemove(function(e){
			$("#tooltip")
				.css({
					"top": (e.pageY-y) + "px",
					"left":  (e.pageX+x)  + "px"
				});
		});
	};
</script>
