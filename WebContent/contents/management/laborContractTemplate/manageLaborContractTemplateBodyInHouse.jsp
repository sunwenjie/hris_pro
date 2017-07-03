<%@ page import="com.kan.base.web.renders.management.BusinessTypeRender"%>
<%@ page import="com.kan.base.web.renders.management.EntityRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.management.LaborContractTemplateVO"%>
<%@ page import="com.kan.base.web.actions.management.LaborContractTemplateAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>

<%
	final LaborContractTemplateVO laborContractTemplateVO = (LaborContractTemplateVO)request.getAttribute("laborContractTemplateForm");
%>

<div id="content">
	<div id="laborContractTemplate" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.employee.contract.template" />
			</label>
			<logic:notEmpty name="laborContractTemplateForm" property="templateId" ><label class="recordId"> &nbsp; (ID: <bean:write name="laborContractTemplateForm" property="templateId" />)</label></logic:notEmpty>
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
						<input type="button" class="function" name="btnExportPDF" id="btnExportPDF" value="<bean:message bundle="public" key="button.export.pdf" />" onclick="exportPDF();">
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
			<html:form action="laborContractTemplateAction.do?proc=add_object" styleClass="manageLaborContractTemplate_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="templateId" id="templateId" value='<bean:write name="laborContractTemplateForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="laborContractTemplateForm" property="subAction" />' />
				<input type="hidden" name="contractTypeId" id="contractTypeId"  value='0' />
			
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label><bean:message bundle="management" key="management.employee.contract.template.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageLaborContractTemplate_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.employee.contract.template.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageLaborContractTemplate_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.employee.contract.template.content.type" /><em> *</em></label> 
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
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.modify.by" /></label> 
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
								<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first"><bean:message bundle="public" key="menu.table.title.entity" /> (<span id="numberOfEntity"><bean:write name="entityCount"/></span>)</li> 
								<%-- <li id="tabMenu2" onClick="changeTab(2,2)"><bean:message bundle="public" key="menu.table.title.business.type" /> (<span id="numberOfBusinessType"><bean:write name="businessTypeCount"/></span>)</li>  --%>
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
											<input type="button" class="internal addbutton" name="btnAddEntity" id="btnAddEntity" value="<bean:message bundle="public" key="button.add" />" onclick="addEntity();"/>
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
							<%-- <div id="tabContent2" class="kantab" style="display:none">
								<ol class="auto">
									<li>
										<html:select property="businessTypeId" styleId="businessTypeId" styleClass="manageBusinessContractTemplate_businessTypeId">
											<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
										</html:select>
										<logic:empty name="laborContractTemplateForm" property="encodedId">
											<input type="button" class="internal addbutton" name="btnAddBusinessType" id="btnAddBusinessType" value="<bean:message bundle="public" key="button.add" />" onclick="addBusinessType();"/>
										</logic:empty>
										<logic:notEmpty name="laborContractTemplateForm" property="encodedId">
											<kan:auth right="modify" action="<%=LaborContractTemplateAction.accessAction%>">
												<input type="button" class="internal addbutton" name="btnAddBusinessType" id="btnAddBusinessType" value="<bean:message bundle="public" key="button.add" />" onclick="addBusinessType();"/>
											</kan:auth>
										</logic:notEmpty>
									</li>
								</ol>
								<%= BusinessTypeRender.getLaborBusinessTypeManagement( request, laborContractTemplateVO.getBusinessTypeIdArray() ) %>
							</div>  --%>
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
		$('form select.manageLaborContractTemplate_status option').last().remove();

		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageLaborContractTemplate_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageLaborContractTemplate_form');
        		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
	    		$('.manageLaborContractTemplate_contractTypeId').attr('disabled', 'disabled');
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
        		$('#pageTitle').html('<bean:message bundle="management" key="management.employee.contract.template" /> <bean:message bundle="public" key="oper.edit" />');
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
    			flag = flag + validate("manageLaborContractTemplate_contractTypeId", true, "select", 0, 0);
    			flag = flag + validate("manageLaborContractTemplate_contentType", true, "select", 0, 0);
    			
    			if(flag == 0 && checkHTML()){
    				var entityIdArraySize = $("input[id='entityIdArray']").size();
       				var businessTypeIdArraySize = $("input[id='businessTypeIdArray']").size();

    				if(entityIdArraySize == 0 || businessTypeIdArraySize == 0)
    				{
    					var msg = "";
    					<logic:equal name="role" value="1">
    						msg = '<bean:message bundle="public" key="popup.invalid.entity.or.business.type.contract1" />';
    					</logic:equal>
    					<logic:equal name="role" value="2">
							msg = '<bean:message bundle="public" key="popup.invalid.entity.or.business.type.contract2" />';
						</logic:equal>
    					if(confirm(msg)){
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
			$('#pageTitle').html('<bean:message bundle="management" key="management.employee.contract.template" /> <bean:message bundle="public" key="oper.view" />');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			//	������ӷ���ʵ���ҵ������
			$('#addEntityDiv').hide();
			$('#addBusinessTypeDiv').hide();
		}

		// �޸��ˡ��޸�ʱ�䡢��ͬ���Ͳ��ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		$('.manageLaborContractTemplate_contractTypeId').attr('disabled', 'disabled');

		// ����ģʽ
		if($('.manageLaborContractTemplate_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('laborContractTemplateAction.do?proc=list_object');
		});
		
	})(jQuery);

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
			alert('<bean:message bundle="public" key="popup.not.selected.option" />');
			return;
		}else if(repeatFlag){
			alert('<bean:message bundle="public" key="popup.already.exists" />');
			return;
		}else{
			$('#mannageEntityOL').append("<li id=\"mannageEntity_" + $('#entityId').val() + "\" style=\"margin: 2px 0px;\">" + 
				"<input type=\"hidden\" id=\"entityIdArray\" name=\"entityIdArray\" value=\"" + $('#entityId').val() + "\">" +
				"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">" +
				"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeExtraObject('laborContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&entityId="
                    + $('#entityId').val() + "&laborContractTemplateId=" + '<bean:write name="laborContractTemplateForm" property="templateId" />' + "', this, '#numberOfEntity');\"/> &nbsp;&nbsp; " + $('select[id=entityId]').find("option:selected").text() + "</li>");
			addExtraObject('laborContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=addObject&entityId=' + $('#entityId').val() + '&laborContractTemplateId=' + '<bean:write name="laborContractTemplateForm" property="templateId" />', true, true, '#numberOfEntity');
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
			alert('<bean:message bundle="public" key="popup.not.selected.option" />');
			return;
		}else if(repeatFlag){
			alert('<bean:message bundle="public" key="popup.already.exists" />');
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
	
	// �Ƴ�����ʵ���¼�
	function removeEntity(id){
		alert($("#entityCount_span").val());
		if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){
			$('#mannageEntity_' + id).remove();
		}
	}; 
	
	// �Ƴ�����ʵ���¼�
	function removeBusinessType(id){
		alert($("#businessTypeCount_span").val());
		if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){
			$('#mannageBusinessType_' + id).remove();
		}
	}; 
	
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
