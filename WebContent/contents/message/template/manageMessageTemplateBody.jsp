<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.message.MessageTemplateAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>

<div id="content">
	<div id="managementMessageTemplate" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="message" key="message.template" /></label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" /> 
				<kan:auth right="modify" action="<%=MessageTemplateAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
				</kan:auth>
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="messageTemplateAction.do?proc=add_object" styleClass="messageTemplate_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="id" name="id" value="<bean:write name="messageTemplateForm" property="encodedId" />" />
				<input type="hidden" class="subAction" id="subAction" name="subAction" value="<bean:write name="messageTemplateForm" property="subAction"/>" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.template.name.zh" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="25" styleClass="messageTemplate_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.template.name.en" /></label> 
							<html:text property="nameEN" maxlength="25" styleClass="messageTemplate_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.template.type" /><em> *</em></label> 
							<html:select property="templateType" styleClass="messageTemplate_TemplateType">
								<html:optionsCollection property="templateTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="message" key="message.template.content.type" /><em> *</em></label> 
							<html:select property="contentType" styleClass="messageTemplate_contentType">
								<html:optionsCollection property="contentTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="static">
						<li>
							<html:textarea property="content"  styleClass="messageTemplate_content" styleId="messageTemplate_content"/>
							<script type="text/javascript">
							//<![CDATA[
							$(function()
							{
								//CKEDITOR.replace( 'content' );
								$('.messageTemplate_content').ckeditor({ toolbar : 'Template'});
							});

							//]]>
							</script>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.description" /> </label> 
							<html:textarea property="description"  styleClass="messageTemplate_description" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="messageTemplate_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
				<jsp:include page="/popup/selectConstantsPopup.jsp">
					<jsp:param name="scopeType" value="1"/>
					<jsp:param name="CKEditElementId" value="messageTemplate_content"/>
				</jsp:include>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_Template').addClass('selected');
		
		// 按钮提交事件
		function btnSubmit() {
			var flag = 0;
			
			flag = flag + validate("messageTemplate_nameZH", true, "common", 25, 0);			
			flag = flag + validate("messageTemplate_TemplateType", true, "select", 0, 0);
			flag = flag + validate("messageTemplate_contentType", true, "select", 0, 0);
			flag = flag + validate("messageTemplate_content", false, "common", 5000, 0);
			flag = flag + validate("messageTemplate_description", false, "common", 900, 0);
			flag = flag + validate("messageTemplate_status", true, "common", 0, 0);
			
			if(flag == 0){
				submit('messageTemplate_form');
			}
		};
		
		// 保存按钮点击事件
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('messageTemplate_form');
				// Enable ckeditor
				//var editor = CKEDITOR.instances.content; 默认使用方式编辑器对象
				try{//jquery 方式获得编辑器对象
// 					var editor = $('.messageTemplate_content').ckeditorGet();
// 					if(editor) {
// 						editor.setReadOnly(false);
// 					}
// 					CKEDITOR.config.readOnly = false;
					if(CKEDITOR.instances){$.each(CKEDITOR.instances,function(id,instances){instances.setReadOnly(false);});}
				}catch (e) {
					// 异常，可能是手机浏览器不兼容等原因造成
				}
				// 更改Subaction
        		$('.subAction').val('modifyObject');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="message" key="message.template" /> <bean:message bundle="public" key="oper.edit" />');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.messageTemplate_form').attr('action', 'messageTemplateAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// 初始化隐藏编辑按钮
		$('#btnEdit').hide();
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('messageTemplate_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="message" key="message.template" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		$('#btnList').click( function () {
			if (agreest())
				link('messageTemplateAction.do?proc=list_object'); 
		});
	})(jQuery);
</script>
							