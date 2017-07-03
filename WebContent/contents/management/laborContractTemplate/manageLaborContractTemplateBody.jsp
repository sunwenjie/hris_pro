<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.management.EntityRender"%>
<%@ page import="com.kan.base.web.renders.management.BusinessTypeRender"%>
<%@ page import="com.kan.base.domain.management.LaborContractTemplateVO"%>
<%@ page import="com.kan.base.web.actions.management.LaborContractTemplateAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>

<%
	LaborContractTemplateVO laborContractTemplateVO = (LaborContractTemplateVO)request.getAttribute("laborContractTemplateForm");
%>
<div id="content">
	<div id="laborContractTemplate" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">劳动合同模板添加</label><logic:notEmpty name="laborContractTemplateForm" property="templateId" ><label class="recordId"> &nbsp; (ID: <bean:write name="laborContractTemplateForm" property="templateId" />)</label></logic:notEmpty>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<logic:notEmpty name="laborContractTemplateForm" property="encodedId">
					<kan:auth right="export" action="<%=LaborContractTemplateAction.accessAction%>">
							<input type="button" class="" name="btnExportPDF" id="btnExportPDF" value="导出PDF" onclick="exportPDF();">
					</kan:auth>
				</logic:notEmpty>
				<logic:empty name="laborContractTemplateForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="laborContractTemplateForm" property="encodedId">
					<kan:auth right="modify" action="<%=LaborContractTemplateAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=LaborContractTemplateAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="laborContractTemplateAction.do?proc=add_object" styleClass="manageLaborContractTemplate_form submitForm">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="templateId" id="templateId" value='<bean:write name="laborContractTemplateForm" property="encodedId" />'>
				<input type="hidden" name="laborContractTemplateId" id="laborContractTemplateId" value='<bean:write name="laborContractTemplateForm" property="templateId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="laborContractTemplateForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label>模板中文名<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageLaborContractTemplate_nameZH" /> 
						</li>
						<li>
							<label>模板英文名</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageLaborContractTemplate_nameEN" /> 
						</li>
						<li>
							<label>合同类型<em> *</em></label> 
							<html:select property="contractTypeId" styleClass="manageLaborContractTemplate_contractTypeId">
								<%-- <html:optionsCollection property="contractTypes" value="mappingId" label="mappingValue" /> --%>
								<option value="0">请选择</option>
								<option value="1" selected="selected">派送协议</option>
							</html:select>
						</li>
						<li>
							<label>合同内容类型<em> *</em></label> 
							<html:select property="contentType" styleClass="manageLaborContractTemplate_contentType">
								<html:optionsCollection property="contentTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageLaborContractTemplate_description"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageLaborContractTemplate_status">
								<%-- <html:optionsCollection property="statuses" value="mappingId" label="mappingValue" /> --%>
								<option value="0">请选择</option>
								<option value="1" selected="selected">启用</option>
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>修改人</label> 
							<html:text property="decodeModifyBy" maxlength="100" disabled="disabled" styleClass="decodeModifyBy" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.modify.date" /></label> 
							<html:text property="decodeModifyDate" maxlength="100" disabled="disabled" styleClass="decodeModifyDate" /> 
						</li>
					</ol>						
					<ol class="static">
						<li>
							<html:textarea property="content" styleId="manageLaborContractTemplate_content" styleClass="manageLaborContractTemplate_content"/>
							<script type="text/javascript">
							//<![CDATA[
							$(function()
							{
								$('.manageLaborContractTemplate_content').ckeditor(
										{
								        toolbar : 'Template'
								    }
								);
							});

							//]]>
							</script>
						</li>
					</ol>
					 
					 <div id="tab"> 
						<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,3)" class="hover first">法务实体(<span id="numberOfEntity"><bean:write name="entityCount"/></span>)</li> 
								<li id="tabMenu2" onClick="changeTab(2,3)">业务类型(<span id="numberOfBusinessType"><bean:write name="businessTypeCount"/></span>)</li> 
								<li id="tabMenu3" onClick="changeTab(3,3)">客户(<span id="numberOfClient"><bean:write name="clientIdsCount"/></span>)</li> 
							</ul> 
						</div> 
						<div class="tabContent"> 
							<div id="tabContent1" class="kantab">
								<ol class="auto">
									<li>
										<html:select property="entityId" styleId="entityId" styleClass="manageBusinessContractTemplate_entityId">
											<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
										</html:select>
										<logic:empty name="laborContractTemplateForm" property="encodedId">
											<kan:auth right="new" action="<%=LaborContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddEntity" id="btnAddEntity" value="<bean:message bundle="public" key="button.add" />" onclick="addEntity();"/>
											</kan:auth>
										</logic:empty>
										<logic:notEmpty name="laborContractTemplateForm" property="encodedId">
											<kan:auth right="modify" action="<%=LaborContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddEntity" id="btnAddEntity" value="<bean:message bundle="public" key="button.add" />" onclick="addEntity();"/>
											</kan:auth>
										</logic:notEmpty>
									</li>
								</ol>
								<%= EntityRender.getLaborContractEntitiesManagement( request, laborContractTemplateVO.getEntityIdArray() ) %>
							</div>
							<div id="tabContent2" class="kantab" style="display:none">
								<ol class="auto">
									<li>
										<html:select property="businessTypeId" styleId="businessTypeId" styleClass="manageBusinessContractTemplate_businessTypeId">
											<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
										</html:select>
										<logic:empty name="laborContractTemplateForm" property="encodedId">
											<kan:auth right="new" action="<%=LaborContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddBusinessType" id="btnAddBusinessType" value="<bean:message bundle="public" key="button.add" />" onclick="addBusinessType();"/>
											</kan:auth>
										</logic:empty>
										<logic:notEmpty name="laborContractTemplateForm" property="encodedId">
											<kan:auth right="modify" action="<%=LaborContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddBusinessType" id="btnAddBusinessType" value="<bean:message bundle="public" key="button.add" />" onclick="addBusinessType();"/>
											</kan:auth>
										</logic:notEmpty>
									</li>
								</ol>
								<%= BusinessTypeRender.getLaborBusinessTypeManagement( request, laborContractTemplateVO.getBusinessTypeIdArray() ) %>
							</div>
							<div id="tabContent3" class="kantab" style="display:none">
								<ol class="auto">
									<li>
										<input type="text" id="clientIdInput" name="clientIdInput" class="manageBusinessContractTemplate_ClientId"/>
										<input type="hidden" id="clientIdInputHidden" name="clientIdInputHidden" class="manageBusinessContractTemplate_ClientId_Hidden" />
										<logic:empty name="laborContractTemplateForm" property="encodedId">
											<kan:auth right="new" action="<%=LaborContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddClient" id="btnAddClient" value="<bean:message bundle="public" key="button.add" />" onclick="return addClient();"/>
											</kan:auth>
										</logic:empty>
										<logic:notEmpty name="laborContractTemplateForm" property="encodedId">
											<kan:auth right="modify" action="<%=LaborContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddClient" id="btnAddClient" value="<bean:message bundle="public" key="button.add" />" onclick="return addClient();"/>
											</kan:auth>
										</logic:notEmpty>
									</li>
								</ol>
								<ol class="auto" id="mannageClientOL">
									<logic:iterate id="contractTemplateClientVo" name="clientIdsList" indexId="number">
										<li id='mannageClient_<bean:write name="contractTemplateClientVo" property="clientId"/>' style="margin: 2px 0px;">
											<input type="hidden" name="clientIdsArray" value='<bean:write name="contractTemplateClientVo" property="clientId"/>'>
											<logic:empty name="laborContractTemplateForm" property="encodedId">
												<kan:auth right="new" action="<%=LaborContractTemplateAction.accessAction%>">
													<img src="images/disable-btn.png" width="12px" height="12px" id="disable_img" name="disable_img">
													<img src="images/warning-btn.png" width="12px" height="12px" id="warning_img" name="warning_img" style="display: none;" onClick="removeExtraObjectByFormPost('laborContractTemplateAction.do?proc=modify_object_ajax_tab', this, '#numberOfClient')">
												</kan:auth>
											</logic:empty>
											<logic:notEmpty name="laborContractTemplateForm" property="encodedId">
												<kan:auth right="modify" action="<%=LaborContractTemplateAction.accessAction%>">
													<img src="images/disable-btn.png" width="12px" height="12px" id="disable_img" name="disable_img">
													<img src="images/warning-btn.png" width="12px" height="12px" id="warning_img" name="warning_img" style="display: none;" onClick="removeExtraObjectByFormPost('laborContractTemplateAction.do?proc=modify_object_ajax_tab', this, '#numberOfClient')">
												</kan:auth>
											</logic:notEmpty>
											&nbsp;&nbsp;<bean:write name="contractTemplateClientVo" property="clientName"/>
										</li>
									</logic:iterate>
								</ol>
							</div> 
						</div> 
					</div>
					<div class="bottom">
						<p>
					</div> 
				</fieldset>
				<jsp:include page="/popup/selectConstantsPopup.jsp">
					<jsp:param name="scopeType" value="3"/>
					<jsp:param name="CKEditElementId" value="manageLaborContractTemplate_content"/>
				</jsp:include>
			</html:form>
		</div>
	</div>
</div>
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_LaborContractTemplate').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageLaborContractTemplate_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageLaborContractTemplate_form');
        		// 修改人、修改时间不可编辑
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
	    		/* $('.manageLaborContractTemplate_contractTypeId').attr('disabled', 'disabled'); */
        		//var editor = CKEDITOR.instances.content; 默认使用方式编辑器对象
				try{//jquery 方式获得编辑器对象
					var editor = $('.manageLaborContractTemplate_content').ckeditorGet();
					if(editor) {
						editor.setReadOnly(false);
					}
				}catch (e) {
					// 异常，可能是手机浏览器不兼容等原因造成
				}
    			//	显示添加法务实体和业务类型
    			$('#addEntityDiv').show();
    			$('#addBusinessTypeDiv').show();
				// 更改Subaction
        		$('.manageLaborContractTemplate_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('劳动合同模板编辑');
         		//	显示移除技能图标
         		$('img[id^=warning_img]').each(function(i){
         			$(this).show();
         		});
         		$('img[id^=disable_img]').each(function(i){
         			$(this).hide();
         		});
				// 更改Form Action
        		$('.manageLaborContractTemplate_form').attr('action', 'laborContractTemplateAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageLaborContractTemplate_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageLaborContractTemplate_description", false, "common", 500, 0);
    			flag = flag + validate("manageLaborContractTemplate_status", true, "select", 0, 0);
    			/* flag = flag + validate("manageLaborContractTemplate_contractTypeId", true, "select", 0, 0); */
    			flag = flag + validate("manageLaborContractTemplate_contentType", true, "select", 0, 0);
    			if(flag == 0 && checkHTML()){
   					var entityIdArraySize = $("input[id='entityIdArray']").size();
       				var businessTypeIdArraySize = $("input[id='businessTypeIdArray']").size();

    				if(entityIdArraySize == 0 || businessTypeIdArraySize == 0)
    				{
    					if(confirm("法务实体或业务类型为空，<logic:equal name='role' value='1'>派送协议</logic:equal><logic:equal name='role' value='2'>劳动合同</logic:equal>创建时无法获取合同模板。是否继续？")){
		    				submit('manageLaborContractTemplate_form');
    					}
    				}else{
    					submit('manageLaborContractTemplate_form');
    				}
    				
    			}
        	}
		});
		
		// 查看模式
		if($('.manageLaborContractTemplate_form input.subAction').val() != 'createObject'){
			// Enable ckeditor
			//var editor = CKEDITOR.instances.content; 默认使用方式编辑器对象
			// 更改SubAction
			$('.manageLaborContractTemplate_form input.subAction').val('viewObject');
			
			try{//jquery 方式获得编辑器对象
				var editor = $('.manageLaborContractTemplate_content').ckeditorGet();
				if(editor) {
					editor.setReadOnly(false);
				}
			}catch (e) {
				// 异常，可能是手机浏览器不兼容等原因造成
			}
			// Enable form
			// 将Form设为Disable
			disableForm('manageLaborContractTemplate_form');
			// 更换Page Title
			$('#pageTitle').html('劳动合同模板查询');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			//	隐藏添加法务实体和业务类型
			$('#addEntityDiv').hide();
			$('#addBusinessTypeDiv').hide();
		}

		// 修改人、修改时间、合同类型不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		/* $('.manageLaborContractTemplate_contractTypeId').attr('disabled', 'disabled'); */

		// 创建模式
		if($('.manageLaborContractTemplate_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('laborContractTemplateAction.do?proc=list_object');
		});
		
		kanThinking_column('manageBusinessContractTemplate_ClientId', 'manageBusinessContractTemplate_ClientId_Hidden', 'laborContractTemplateAction.do?proc=list_object_json');
	})(jQuery);
	 
	// 移除法务实体事件
	function removeEntity(id){
		if(confirm("确定删除选中法务实体？")){
			$('#mannageEntity_' + id).remove();
		}
	}; 
	
	// 移除法务实体事件
	function removeBusinessType(id){
		if(confirm("确定删除选中业务类型？")){
			$('#mannageBusinessType_' + id).remove();
		}
	}; 
	
	//	添加法务实体事件
	function addEntity(){
		//	申明一个布尔值判断是否重复添加
		var repeatFlag = false;
		//	获取所要添加的法务实体ID
		var entityId = $('#entityId').val();
		
		//	遍历编辑法务实体区域内的所有法务实体判断是否已包含该法务实体
		$("input[id='entityIdArray']").each( 
			function(){
				if($(this).val() == entityId){
					repeatFlag = true;
				}
			}
		);
		
		//	如果已经包含则不再添加，否则进行添加
		if($('#entityId').val() == '' || $('#entityId').val() == '0'){
			alert("请输入法务实体信息再添加!");
			return;
		}else if(repeatFlag){
			alert("已包含该法务实体,请重新选择!");
			return;
		}else{
			$('#mannageEntityOL').append("<li id=\"mannageEntity_" + $('#entityId').val() + "\" style=\"margin: 2px 0px;\">" + 
				"<input type=\"hidden\" id=\"entityIdArray\" name=\"entityIdArray\" value=\"" + $('#entityId').val() + "\">" +
				"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">" +
				"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeExtraObject('laborContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&entityId="
                    + $('#entityId').val() + "&laborContractTemplateId=" + '<bean:write name="laborContractTemplateForm" property="templateId" />' + "', this, '#numberOfEntity');\"/> &nbsp;&nbsp; " + $('select[id=entityId]').find("option:selected").text() + "</li>");
			addExtraObject('laborContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=addObject&entityId=' + $('#entityId').val() + '&laborContractTemplateId=<bean:write name="laborContractTemplateForm" property="templateId" />', true, true, '#numberOfEntity');
		}
	}
	
	//	添加业务类型事件
	function addBusinessType(){
		//	申明一个布尔值判断是否重复添加
		var repeatFlag = false;
		//	获取所要添加的业务类型ID
		var businessTypeId = $('#businessTypeId').val();
		
		//	遍历编辑业务类型区域内的所有业务类型判断是否已包含该业务类型
		$("input[id='businessTypeIdArray']").each( 
			function(){
				if($(this).val() == businessTypeId){
					repeatFlag = true;
				}
			}
		);
		
		//	如果已经包含则不再添加，否则进行添加
		if($('#businessTypeId').val() == '' || $('#businessTypeId').val() == '0'){
			alert("请输入业务类型信息再添加!");
			return;
		}else if(repeatFlag){
			alert("已包含该业务类型,请重新选择!");
			return;
		}else{
			$('#mannageBusinessTypeOL').append("<li id=\"mannageBusinessType_" + $('#businessTypeId').val() + "\" style=\"margin: 2px 0px;\">" + 
					"<input type=\"hidden\" id=\"businessTypeIdArray\" name=\"businessTypeIdArray\" value=\"" + $('#businessTypeId').val() + "\">" +
					"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">" +
					"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeExtraObject('laborContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&businessTypeId="
	                    + $('#businessTypeId').val() + "&laborContractTemplateId=" + '<bean:write name="laborContractTemplateForm" property="templateId" />' + "', this, '#numberOfBusinessType');\"/> &nbsp;&nbsp; " + $('select[id=businessTypeId]').find("option:selected").text() + "</li>");
				addExtraObject('laborContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=addObject&businessTypeId=' + $('#businessTypeId').val() + '&laborContractTemplateId=' + '<bean:write name="laborContractTemplateForm" property="templateId" />', true, true, '#numberOfBusinessType');
			}
	}
	
	function addClient(){
		
		//申明一个布尔值判断是否重复添加
		var repeatFlag = false;
		//	获取所要添加的业务类型ID
		var clientId = $('#clientIdInputHidden').val();
		
		//	遍历编辑业务类型区域内的所有业务类型判断是否已包含该业务类型
		$("input[name='clientIdsArray']").each( 
			function(){
				if($(this).val() == clientId){
					repeatFlag = true;
				}
			}
		);
		
		//	如果已经包含则不再添加，否则进行添加
		if($('#clientIdInputHidden').val() == ''){
			alert("请输客户信息再添加!");
			return;
		}else if(repeatFlag){
			alert("已包含该客户信息,请重新选择!");
			return;
		}else{
			if($('#laborContractTemplateId').val() == ""){
				$('#mannageClientOL').append("<li id=\"mannageClient_" + $('#clientIdInputHidden').val() + "\" style=\"margin: 2px 0px;\">" + 
						"<input type=\"hidden\" name=\"clientIdsArray\" value=\"" + $('#clientIdInputHidden').val() + "\">" +
						"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">" +
						"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeExtraObjectByFormPost('', this, '#numberOfClient')\"/> &nbsp;&nbsp; " + $('#clientIdInput').val() + "</li>");
				addExtraObjectDom('#numberOfClient');
			}
			if($('#laborContractTemplateId').val()!= ""){
				$('#mannageClientOL').append("<li id=\"mannageClient_" + $('#clientIdInputHidden').val() + "\" style=\"margin: 2px 0px;\">" + 
						"<input type=\"hidden\" name=\"clientIdsArray\" value=\"" + $('#clientIdInputHidden').val() + "\">" +
						"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">" +
						"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeExtraObjectByFormPost('laborContractTemplateAction.do?proc=modify_object_ajax_tab', this, '#numberOfClient')\"/> &nbsp;&nbsp; " + $('#clientIdInput').val() + "</li>");
				addExtraObjectByFormPost('laborContractTemplateAction.do?proc=modify_object_ajax_tab', true, true, '#numberOfClient');
			}
		}
	}
	
	function exportPDF(){
		link('laborContractTemplateAction.do?proc=export_contract_pdf&templateId=<bean:write name="laborContractTemplateForm" property="encodedId" />');
	};
	
	function checkHTML(){
		var returnValue = false;
		$.ajax({
			url : "resignTemplateAction.do?proc=checkHTML", 
			dataType : "json",
			type: 'POST',
			async:false,
			data:'templateContent='+encodeURIComponent(encodeURIComponent($('.manageLaborContractTemplate_content').val())),
			success : function(data){
				if(data.data == "success"){
					returnValue = true;
				}else{
					$('#messageWrapper').html('<div class="message error fadable">'+data.data+'<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					returnValue = false;
				}
			}
		});
		return returnValue;
	}
</script>