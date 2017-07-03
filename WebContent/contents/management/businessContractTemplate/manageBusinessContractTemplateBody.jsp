<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.management.EntityRender"%>
<%@ page import="com.kan.base.web.renders.management.BusinessTypeRender"%>
<%@ page import="com.kan.base.domain.management.BusinessContractTemplateVO"%>
<%@ page import="com.kan.base.web.actions.management.BusinessContractTemplateAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>

<%
	final BusinessContractTemplateVO businessContractTemplateVO = (BusinessContractTemplateVO) request.getAttribute("businessContractTemplateForm");
%>
<div id="content">
	<div id="businessContractTemplate" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">�����ͬģ�����</label><logic:notEmpty name="businessContractTemplateForm" property="templateId" ><label class="recordId"> &nbsp; (ID: <bean:write name="businessContractTemplateForm" property="templateId" />)</label></logic:notEmpty>
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
				<logic:notEmpty name="businessContractTemplateForm" property="encodedId">
					<kan:auth right="export" action="<%=BusinessContractTemplateAction.accessAction%>">
						<input type="button" class="" name="btnExportPDF" id="btnExportPDF" value="����PDF" onclick="exportPDF();">
					</kan:auth>
				</logic:notEmpty>
				<logic:empty name="businessContractTemplateForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="businessContractTemplateForm" property="encodedId">
					<kan:auth right="modify" action="<%=BusinessContractTemplateAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=BusinessContractTemplateAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="businessContractTemplateAction.do?proc=add_object" styleClass="manageBusinessContractTemplate_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="templateId" id="templateId" value='<bean:write name="businessContractTemplateForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="businessContractTemplateForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label>ģ�����ƣ����ģ�<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageBusinessContractTemplate_nameZH" /> 
						</li>
						<li>
							<label>ģ�����ƣ�Ӣ�ģ�</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageBusinessContractTemplate_nameEN" /> 
						</li>
						<li>
							<label>��������</label> 
							<html:select property="contentType" styleClass="manageBusinessContractTemplate_contentType">
								<html:optionsCollection property="contentTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageBusinessContractTemplate_status">
								<option value="0">��ѡ��</option>
								<option value="1" selected="selected">����</option>
								<%-- <html:optionsCollection property="statuses" value="mappingId" label="mappingValue"/> --%>
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageBusinessContractTemplate_description"></html:textarea>
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
							<html:textarea property="content" styleId="manageBusinessContractTemplate_content" styleClass="manageBusinessContractTemplate_content"/>
							<script type="text/javascript">
								//<![CDATA[
									$(function()
									{
										$('.manageBusinessContractTemplate_content').ckeditor(
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
								<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first">����ʵ�� (<span id="numberOfEntity"><bean:write name="entityCount"/></span>)</li> 
								<li id="tabMenu2" onClick="changeTab(2,2)">ҵ������ (<span id="numberOfBusinessType"><bean:write name="businessTypeCount"/></span>)</li> 
							</ul> 
						</div> 
						<div class="tabContent"> 
							<div id="tabContent1" class="kantab">
								<ol class="auto">
									<li>
										<html:select property="entityId" styleId="entityId" styleClass="manageBusinessContractTemplate_entityId">
											<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
										</html:select>
										<logic:empty name="businessContractTemplateForm" property="encodedId">
											<kan:auth right="new" action="<%=BusinessContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddEntity" id="btnAddEntity" value="<bean:message bundle="public" key="button.add" />" onclick="addEntity();"/>
											</kan:auth>
										</logic:empty>
										<logic:notEmpty name="businessContractTemplateForm" property="encodedId">
											<kan:auth right="modify" action="<%=BusinessContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddEntity" id="btnAddEntity" value="<bean:message bundle="public" key="button.add" />" onclick="addEntity();"/>
											</kan:auth>
										</logic:notEmpty>
									</li>
								</ol>
								<%= EntityRender.getBusinessContractEntitiesManagement( request, businessContractTemplateVO.getEntityIdArray() ) %>
							</div>
							<div id="tabContent2" class="kantab" style="display:none">
								<ol class="auto">
									<li>
										<html:select property="businessTypeId" styleId="businessTypeId" styleClass="manageBusinessContractTemplate_businessTypeId">
											<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
										</html:select>
										<logic:empty name="businessContractTemplateForm" property="encodedId">
											<kan:auth right="new" action="<%=BusinessContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddBusinessType" id="btnAddBusinessType" value="<bean:message bundle="public" key="button.add" />" onclick="addBusinessType();"/>
											</kan:auth>
										</logic:empty>
										<logic:notEmpty name="businessContractTemplateForm" property="encodedId">
											<kan:auth right="modify" action="<%=BusinessContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddBusinessType" id="btnAddBusinessType" value="<bean:message bundle="public" key="button.add" />" onclick="addBusinessType();"/>
											</kan:auth>
										</logic:notEmpty>
									</li>
								</ol>
								<%= BusinessTypeRender.getBusinessTypeManagement( request, businessContractTemplateVO.getBusinessTypeIdArray() ) %>
							</div> 
						</div> 
					</div>
					<div class="bottom">
						<p>
					</div> 
				</fieldset>
				<!-- Module Box HTML: Begins -->
				<jsp:include page="/popup/selectConstantsPopup.jsp">
					<jsp:param name="scopeType" value="2"/>
					<jsp:param name="CKEditElementId" value="manageBusinessContractTemplate_content"/>
				</jsp:include>
				<!-- Module Box HTML: Ends -->
			</html:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		
		// ���ò˵�ѡ����ʽ
		$('#menu_client_Modules').addClass('current');	
		$('#menu_client_Configuration').addClass('selected');
		$('#menu_client_BusinessContractTemplate').addClass('selected');

		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageBusinessContractTemplate_form input.subAction').val() == 'viewObject'){
				// Enable ckeditor
				try{
					//JQuery ��ʽ��ñ༭������
					var editor = $('.manageBusinessContractTemplate_content').ckeditorGet();
					if(editor) {
						editor.setReadOnly(false);
					}
				}catch (e) {
					// �쳣���������ֻ�����������ݵ�ԭ�����
				}
				// Enable form
        		enableForm('manageBusinessContractTemplate_form');
				// �޸��ˡ��޸�ʱ�䲻�ɱ༭
        		$('.decodeModifyBy').attr('disabled', 'disabled');
        		$('.decodeModifyDate').attr('disabled', 'disabled');
				// ����Subaction
        		$('.manageBusinessContractTemplate_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
        		$('#pageTitle').html('�����ͬģ��༭');
         		// ��ʾ�Ƴ�����ͼ��
         		$('img[id^=warning_img]').each(function(i){
         			$(this).show();
         		});
         		$('img[id^=disable_img]').each(function(i){
         			$(this).hide();
         		});
				// ����Form Action
        		$('.manageBusinessContractTemplate_form').attr('action', 'businessContractTemplateAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageBusinessContractTemplate_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageBusinessContractTemplate_status", true, "select", 0, 0);
    			flag = flag + validate("manageBusinessContractTemplate_description", false, "common", 500, 0);
    			
    			if(flag == 0 && checkHTML()){
    				var entityIdArraySize = $("input[id='entityIdArray']").size();
    				var businessTypeIdArraySize = $("input[id='businessTypeIdArray']").size();
    				
    				if(entityIdArraySize == 0 || businessTypeIdArraySize == 0)
    				{
    					if(confirm("����ʵ���ҵ������Ϊ�գ������ͬ����ʱ�޷���ȡ��ͬģ�塣�Ƿ������")){
		    				submit('manageBusinessContractTemplate_form');
    					}
    				}else{
    					submit('manageBusinessContractTemplate_form');
    				}
    				
    			}
        	}
		});
		
		// �鿴ģʽ
		if($('.manageBusinessContractTemplate_form input.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('manageBusinessContractTemplate_form');
			// ����ҳ���ʼ��SubAction
			$('.manageBusinessContractTemplate_form input.subAction').val('viewObject');
			// ����Page Title
			$('#pageTitle').html('�����ͬģ���ѯ');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		$('#btnList').click( function () {
			if (agreest())
			link('businessContractTemplateAction.do?proc=list_object');
		});
		
		//	Module Cancel����ȡ���¼�
		$('#btn_modal_cancel').click(function(){
			$('#modalId').addClass('hide');
			$('#shield').hide();
		});

		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
	})(jQuery);

	// ��ӷ���ʵ���¼�
	// Reviewed by Kevin Jin at 2013-11-07
	function addEntity(){
		if($('.manageBusinessContractTemplate_form input.subAction').val() == 'createObject'){
			cleanError("manageBusinessContractTemplate_entityId");
			addError("manageBusinessContractTemplate_entityId", "���ȱ���ģ��");
			return false;
		}else{
			cleanError("manageBusinessContractTemplate_entityId");
		}
		
		// �ж��ظ��ı��
		var repeatFlag = false;
		// ��ȡ��Ҫ��ӵķ���ʵ��ID
		var entityId = $('#entityId').val();
		
		// �����ж��Ƿ��ظ�
		$("input[id='entityIdArray']").each( 
			function(){
				if($(this).val() == entityId){
					repeatFlag = true;
				}
			}
		);
		
		//	����Ѿ�����������ӣ�����������
		if($('#entityId').val() == '' || $('#entityId').val() == '0'){
			alert("��ѡ����Ч��¼��");
		}else if(repeatFlag){
			alert("��ͬ����ʵ��ֻ�����һ�Σ�");
		}else{
			$('#mannageEntityOL').append("<li id=\"mannageEntity_" + $('#entityId').val() + "\"><input type=\"hidden\" id=\"entityIdArray\" name=\"entityIdArray\" value=\"" + $('#entityId').val() + "\"><img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\"><img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeExtraObject('businessContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&entityId="
                    + $('#entityId').val() + "&businessContractTemplateId=" + <bean:write name="businessContractTemplateForm" property="templateId" /> + "', this, '#numberOfEntity');\"/> &nbsp;&nbsp; " + $('select[id=entityId]').find("option:selected").text() + "</li>" );
			addExtraObject('businessContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=addObject&entityId=' + $('#entityId').val() + '&businessContractTemplateId=<bean:write name="businessContractTemplateForm" property="templateId" />', true, true, '#numberOfEntity');
		}
	};
	
	// ���ҵ�������¼�
	// Reviewed by Kevin Jin at 2013-11-07
	function addBusinessType(){
		if($('.manageBusinessContractTemplate_form input.subAction').val() == 'createObject'){
			cleanError("manageBusinessContractTemplate_businessTypeId");
			addError("manageBusinessContractTemplate_businessTypeId", "���ȱ���ģ��");
			return false;
		}else{
			cleanError("manageBusinessContractTemplate_businessTypeId");
		}
		
		// �ж��ظ��ı��
		var repeatFlag = false;
		// ��ȡ��Ҫ��ӵ�ҵ������ID
		var businessTypeId = $('#businessTypeId').val();
		
		// �����ж��Ƿ��ظ�
		$("input[id='businessTypeIdArray']").each( 
			function(){
				if($(this).val() == businessTypeId){
					repeatFlag = true;
				}
			}
		);
		
		// ����Ѿ�����������ӣ�����������
		if($('#businessTypeId').val() == '' || $('#businessTypeId').val() == '0'){
			alert("��ѡ����Ч��¼��");
		}else if(repeatFlag){
			alert("��ͬҵ������ֻ�����һ�Σ�");
		}else{
			$('#mannageBusinessTypeOL').append("<li id=\"mannageBusinessType_" + $('#businessTypeId').val() + "\"><input type=\"hidden\" id=\"businessTypeIdArray\" name=\"businessTypeIdArray\" value=\"" + $('#businessTypeId').val() + "\"><img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\"><img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeExtraObject('businessContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&businessTypeId="
                    + $('#businessTypeId').val() + "&businessContractTemplateId=" + <bean:write name="businessContractTemplateForm" property="templateId" /> + "', this, '#numberOfBusinessType');\"/> &nbsp;&nbsp; " + $('select[id=businessTypeId]').find("option:selected").text() + "</li>" ); 
			addExtraObject('businessContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=addObject&businessTypeId=' + $('#businessTypeId').val() + '&businessContractTemplateId=<bean:write name="businessContractTemplateForm" property="templateId" />', true, true, '#numberOfBusinessType');
		}
	};
	
	// ����ģʽ
	if($('.manageBusinessContractTemplate_form input.subAction').val() == 'createObject'){
		$('.decodeModifyDate').val('');
	}
	
	function exportPDF(){
		link('businessContractTemplateAction.do?proc=export_contract_pdf&id=<bean:write name="businessContractTemplateForm" property="encodedId" />');
	};
	
	function checkHTML(){
		var returnValue = false;
		$.ajax({
			url : "resignTemplateAction.do?proc=checkHTML", 
			dataType : "json",
			type: 'POST',
			async:false,
			data:'templateContent='+encodeURIComponent(encodeURIComponent($('.manageBusinessContractTemplate_content').val())),
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
