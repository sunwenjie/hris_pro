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
			<label id="pageTitle">�Ͷ���ͬģ�����</label><logic:notEmpty name="laborContractTemplateForm" property="templateId" ><label class="recordId"> &nbsp; (ID: <bean:write name="laborContractTemplateForm" property="templateId" />)</label></logic:notEmpty>
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
							<input type="button" class="" name="btnExportPDF" id="btnExportPDF" value="����PDF" onclick="exportPDF();">
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
							<label>ģ��������<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageLaborContractTemplate_nameZH" /> 
						</li>
						<li>
							<label>ģ��Ӣ����</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageLaborContractTemplate_nameEN" /> 
						</li>
						<li>
							<label>��ͬ����<em> *</em></label> 
							<html:select property="contractTypeId" styleClass="manageLaborContractTemplate_contractTypeId">
								<%-- <html:optionsCollection property="contractTypes" value="mappingId" label="mappingValue" /> --%>
								<option value="0">��ѡ��</option>
								<option value="1" selected="selected">����Э��</option>
							</html:select>
						</li>
						<li>
							<label>��ͬ��������<em> *</em></label> 
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
								<option value="0">��ѡ��</option>
								<option value="1" selected="selected">����</option>
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>�޸���</label> 
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
								<li id="tabMenu1" onClick="changeTab(1,3)" class="hover first">����ʵ��(<span id="numberOfEntity"><bean:write name="entityCount"/></span>)</li> 
								<li id="tabMenu2" onClick="changeTab(2,3)">ҵ������(<span id="numberOfBusinessType"><bean:write name="businessTypeCount"/></span>)</li> 
								<li id="tabMenu3" onClick="changeTab(3,3)">�ͻ�(<span id="numberOfClient"><bean:write name="clientIdsCount"/></span>)</li> 
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
		// ���ò˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_LaborContractTemplate').addClass('selected');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageLaborContractTemplate_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageLaborContractTemplate_form');
        		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
	    		/* $('.manageLaborContractTemplate_contractTypeId').attr('disabled', 'disabled'); */
        		//var editor = CKEDITOR.instances.content; Ĭ��ʹ�÷�ʽ�༭������
				try{//jquery ��ʽ��ñ༭������
					var editor = $('.manageLaborContractTemplate_content').ckeditorGet();
					if(editor) {
						editor.setReadOnly(false);
					}
				}catch (e) {
					// �쳣���������ֻ�����������ݵ�ԭ�����
				}
    			//	��ʾ��ӷ���ʵ���ҵ������
    			$('#addEntityDiv').show();
    			$('#addBusinessTypeDiv').show();
				// ����Subaction
        		$('.manageLaborContractTemplate_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
        		$('#pageTitle').html('�Ͷ���ͬģ��༭');
         		//	��ʾ�Ƴ�����ͼ��
         		$('img[id^=warning_img]').each(function(i){
         			$(this).show();
         		});
         		$('img[id^=disable_img]').each(function(i){
         			$(this).hide();
         		});
				// ����Form Action
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
    					if(confirm("����ʵ���ҵ������Ϊ�գ�<logic:equal name='role' value='1'>����Э��</logic:equal><logic:equal name='role' value='2'>�Ͷ���ͬ</logic:equal>����ʱ�޷���ȡ��ͬģ�塣�Ƿ������")){
		    				submit('manageLaborContractTemplate_form');
    					}
    				}else{
    					submit('manageLaborContractTemplate_form');
    				}
    				
    			}
        	}
		});
		
		// �鿴ģʽ
		if($('.manageLaborContractTemplate_form input.subAction').val() != 'createObject'){
			// Enable ckeditor
			//var editor = CKEDITOR.instances.content; Ĭ��ʹ�÷�ʽ�༭������
			// ����SubAction
			$('.manageLaborContractTemplate_form input.subAction').val('viewObject');
			
			try{//jquery ��ʽ��ñ༭������
				var editor = $('.manageLaborContractTemplate_content').ckeditorGet();
				if(editor) {
					editor.setReadOnly(false);
				}
			}catch (e) {
				// �쳣���������ֻ�����������ݵ�ԭ�����
			}
			// Enable form
			// ��Form��ΪDisable
			disableForm('manageLaborContractTemplate_form');
			// ����Page Title
			$('#pageTitle').html('�Ͷ���ͬģ���ѯ');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			//	������ӷ���ʵ���ҵ������
			$('#addEntityDiv').hide();
			$('#addBusinessTypeDiv').hide();
		}

		// �޸��ˡ��޸�ʱ�䡢��ͬ���Ͳ��ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		/* $('.manageLaborContractTemplate_contractTypeId').attr('disabled', 'disabled'); */

		// ����ģʽ
		if($('.manageLaborContractTemplate_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('laborContractTemplateAction.do?proc=list_object');
		});
		
		kanThinking_column('manageBusinessContractTemplate_ClientId', 'manageBusinessContractTemplate_ClientId_Hidden', 'laborContractTemplateAction.do?proc=list_object_json');
	})(jQuery);
	 
	// �Ƴ�����ʵ���¼�
	function removeEntity(id){
		if(confirm("ȷ��ɾ��ѡ�з���ʵ�壿")){
			$('#mannageEntity_' + id).remove();
		}
	}; 
	
	// �Ƴ�����ʵ���¼�
	function removeBusinessType(id){
		if(confirm("ȷ��ɾ��ѡ��ҵ�����ͣ�")){
			$('#mannageBusinessType_' + id).remove();
		}
	}; 
	
	//	��ӷ���ʵ���¼�
	function addEntity(){
		//	����һ������ֵ�ж��Ƿ��ظ����
		var repeatFlag = false;
		//	��ȡ��Ҫ��ӵķ���ʵ��ID
		var entityId = $('#entityId').val();
		
		//	�����༭����ʵ�������ڵ����з���ʵ���ж��Ƿ��Ѱ����÷���ʵ��
		$("input[id='entityIdArray']").each( 
			function(){
				if($(this).val() == entityId){
					repeatFlag = true;
				}
			}
		);
		
		//	����Ѿ�����������ӣ�����������
		if($('#entityId').val() == '' || $('#entityId').val() == '0'){
			alert("�����뷨��ʵ����Ϣ�����!");
			return;
		}else if(repeatFlag){
			alert("�Ѱ����÷���ʵ��,������ѡ��!");
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
	
	//	���ҵ�������¼�
	function addBusinessType(){
		//	����һ������ֵ�ж��Ƿ��ظ����
		var repeatFlag = false;
		//	��ȡ��Ҫ��ӵ�ҵ������ID
		var businessTypeId = $('#businessTypeId').val();
		
		//	�����༭ҵ�����������ڵ�����ҵ�������ж��Ƿ��Ѱ�����ҵ������
		$("input[id='businessTypeIdArray']").each( 
			function(){
				if($(this).val() == businessTypeId){
					repeatFlag = true;
				}
			}
		);
		
		//	����Ѿ�����������ӣ�����������
		if($('#businessTypeId').val() == '' || $('#businessTypeId').val() == '0'){
			alert("������ҵ��������Ϣ�����!");
			return;
		}else if(repeatFlag){
			alert("�Ѱ�����ҵ������,������ѡ��!");
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
		
		//����һ������ֵ�ж��Ƿ��ظ����
		var repeatFlag = false;
		//	��ȡ��Ҫ��ӵ�ҵ������ID
		var clientId = $('#clientIdInputHidden').val();
		
		//	�����༭ҵ�����������ڵ�����ҵ�������ж��Ƿ��Ѱ�����ҵ������
		$("input[name='clientIdsArray']").each( 
			function(){
				if($(this).val() == clientId){
					repeatFlag = true;
				}
			}
		);
		
		//	����Ѿ�����������ӣ�����������
		if($('#clientIdInputHidden').val() == ''){
			alert("����ͻ���Ϣ�����!");
			return;
		}else if(repeatFlag){
			alert("�Ѱ����ÿͻ���Ϣ,������ѡ��!");
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