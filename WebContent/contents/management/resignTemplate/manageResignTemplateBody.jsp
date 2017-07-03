<%@ page import="com.kan.base.web.actions.management.ResignTemplateAction"%>
<%@ page import="com.kan.base.domain.management.ResignTemplateVO"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>

<%
	final ResignTemplateVO resignTemplateVO = (ResignTemplateVO)request.getAttribute("resignTemplateForm");
%>

<div id="content">
	<div id="resignTemplate" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.employee.resign.template" />
			</label>
			<logic:notEmpty name="resignTemplateForm" property="templateId" ><label class="recordId"> &nbsp; (ID: <bean:write name="resignTemplateForm" property="templateId" />)</label></logic:notEmpty>
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
				<logic:notEmpty name="resignTemplateForm" property="encodedId">
					<kan:auth right="export" action="<%=ResignTemplateAction.accessAction%>">
						<input type="button" class="function" name="btnExportPDF" id="btnExportPDF" value="<bean:message bundle="public" key="button.export.pdf" />" onclick="exportPDF();">
					</kan:auth>
				</logic:notEmpty>
				<logic:empty name="resignTemplateForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="resignTemplateForm" property="encodedId">
					<kan:auth right="modify" action="<%=ResignTemplateAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ResignTemplateAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="resignTemplateAction.do?proc=add_object" styleClass="manageResignTemplate_form submitForm">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="templateId" id="templateId" value='<bean:write name="resignTemplateForm" property="encodedId" />'>
				<input type="hidden" name="resignTemplateId" id="resignTemplateId" value='<bean:write name="resignTemplateForm" property="templateId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="resignTemplateForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label><bean:message bundle="management" key="management.employee.resign.template.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageResignTemplate_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.employee.resign.template.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageResignTemplate_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.employee.resign.template.type" /><em> *</em></label> 
							<html:select property="templateType" styleClass="manageResignTemplate_templateType">
								<html:optionsCollection property="templateTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.employee.resign.template.content.type" /><em> *</em></label> 
							<html:select property="contentType" styleClass="manageResignTemplate_contentType">
								<html:optionsCollection property="contentTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageResignTemplate_description"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageResignTemplate_status">
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
							<html:textarea property="content" styleId="manageResignTemplate_content" styleClass="manageResignTemplate_content"/>
							<script type="text/javascript">
							//<![CDATA[
							$(function()
							{
								$('.manageResignTemplate_content').ckeditor(
										{
								        toolbar : 'Template'
								    }
								);
							});

							//]]>
							</script>
						</li>
					</ol>
					 
					<div class="bottom">
						<p>
					</div> 
				</fieldset>
				<jsp:include page="/popup/selectConstantsPopup.jsp">
					<jsp:param name="scopeType" value="5"/>
					<jsp:param name="CKEditElementId" value="manageResignTemplate_content"/>
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
		$('#menu_resign_template').addClass('selected');
		$('form select.manageResignTemplate_status option').last().remove();
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageResignTemplate_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageResignTemplate_form');
        		// 修改人、修改时间不可编辑
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
        		//var editor = CKEDITOR.instances.content; 默认使用方式编辑器对象
				try{//jquery 方式获得编辑器对象
					var editor = $('.manageResignTemplate_content').ckeditorGet();
					if(editor) {
						editor.setReadOnly(false);
					}
				}catch (e) {
					// 异常，可能是手机浏览器不兼容等原因造成
				}
				// 更改Subaction
        		$('.manageResignTemplate_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.employee.resign.template" /> <bean:message bundle="public" key="oper.edit" />');
         		//	显示移除技能图标
         		$('img[id^=warning_img]').each(function(i){
         			$(this).show();
         		});
         		$('img[id^=disable_img]').each(function(i){
         			$(this).hide();
         		});
				// 更改Form Action
        		$('.manageResignTemplate_form').attr('action', 'resignTemplateAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageResignTemplate_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageResignTemplate_description", false, "common", 500, 0);
    			flag = flag + validate("manageResignTemplate_templateType", true, "select", 0, 0);
    			flag = flag + validate("manageResignTemplate_status", true, "select", 0, 0);
    			flag = flag + validate("manageResignTemplate_contentType", true, "select", 0, 0);
    			if(flag == 0 && checkHTML()){
    				submit('manageResignTemplate_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageResignTemplate_form input.subAction').val() != 'createObject'){
			// Enable ckeditor
			//var editor = CKEDITOR.instances.content; 默认使用方式编辑器对象
			// 更改SubAction
			$('.manageResignTemplate_form input.subAction').val('viewObject');
			
			try{//jquery 方式获得编辑器对象
				var editor = $('.manageResignTemplate_content').ckeditorGet();
				if(editor) {
					editor.setReadOnly(false);
				}
			}catch (e) {
				// 异常，可能是手机浏览器不兼容等原因造成
			}
			// Enable form
			// 将Form设为Disable
			disableForm('manageResignTemplate_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="management" key="management.employee.resign.template" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// 修改人、修改时间、合同类型不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// 创建模式
		if($('.manageResignTemplate_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('resignTemplateAction.do?proc=list_object');
		});
		
	})(jQuery);
	 
	function exportPDF(){
		link('resignTemplateAction.do?proc=export_pdf&templateId=<bean:write name="resignTemplateForm" property="encodedId" />');
	};
	
	function checkHTML(){
		var returnValue = false;
		$.ajax({
			url : "resignTemplateAction.do?proc=checkHTML", 
			dataType : "json",
			type: 'POST',
			async:false,
			data:'templateContent='+encodeURIComponent(encodeURIComponent($('.manageResignTemplate_content').val())),
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
	};
</script>