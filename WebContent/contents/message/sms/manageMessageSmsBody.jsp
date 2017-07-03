<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.actions.message.MessageSmsAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>

<div id="content">
	<div id="managementMessageTemplate" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="message" key="message.sms" /></label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.send" />" /> 
				<logic:notEmpty name="messageSmsForm" property="smsId">
					<logic:equal name="messageSmsForm" property="status" value="1"> 
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
						<input type="button" class="addbutton" name="btnCancelSendAndSave" id="btnCancelSendAndSave" value="<bean:message bundle="public" key="button.stop.send.and.save" />" />
					</logic:equal>
					<logic:equal name="messageSmsForm" property="status" value="4"> 
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
						<input type="button" class="addbutton" name="btnSandAndSave" id="btnSandAndSave" value="<bean:message bundle="public" key="button.recovery.send.and.save" />" />
					</logic:equal> 
				</logic:notEmpty>
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" />
				
			</div>
			<html:form action="messageSmsAction.do?proc=add_object" styleClass="messageSms_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" class="hiddenReception" id="hiddenReception" name="hiddenReception" value="<bean:write name="messageSmsForm" property="reception" />" />
				<input type="hidden" class="smsId" id="smsId" name="smsId" value="<bean:write name="messageSmsForm" property="encodedId" />" />
				<input type="hidden" class="subAction" id="subAction" name="subAction" value="<bean:write name="messageSmsForm" property="subAction"/>" />
				<input  type="hidden" class="checkType" id="checkType" name="checkType" value="sms"/> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="static">
						<li>
							<label><bean:message bundle="message" key="message.sms.reception" />  <img src="images/tips.png" title="<bean:message bundle="message" key="message.mail.reception.tips" />"><em> *</em></label>
							<div class="recLabel" style="width: 216px;height:100px;display: block;background-color: white">
							
							</div>
							<a id="contactSearch" onclick="popupContactSearch();" class="kanhandle"><img src="images/add.png" title="<bean:message bundle="message" key="message.mail.reception.img.tips" />" /></a>
						</li>
						
						<li>
							<label><bean:message bundle="workflow" key="workflow.define.sms.template" /></label> 
							<html:select property="templateId" styleClass="messageTemplateId_contentType" >
								<html:optionsCollection property="templateIds" value="mappingId" label="mappingValue"  />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="message" key="message.sms.content" /><em> *</em></label>
							<html:textarea property="content"  styleClass="messageSms_content"/>
						</li>
						<logic:notEmpty name="messageSmsForm" property="smsId" >
							<li>
								<label><bean:message bundle="public" key="public.status" /><em> *</em></label>
								<html:select property="status" styleClass="messageSms_status" >
									<html:optionsCollection property="statuses" value="mappingId" label="mappingValue"  />
								</html:select>
							</li>
						</logic:notEmpty>
					</ol>
				</fieldset>
				
				<div id="popupWrapper">
					<jsp:include page="/popup/searchContact.jsp"></jsp:include>
				</div>
				
			</html:form>
		</div>
	</div>
</div>


<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_SMS').addClass('selected');
		$('#searchDiv').hide();
		
		// �����viewObject ����ɾ��
		if($('.subAction').val()=='viewObject'){
			$(".closebutton").hide();
		}
		
		// ��ť�ύ�¼�
		function btnSubmit() {
			var flag = 0;
			flag = flag + validate("messageSms_content", true, "common", 500, 0);
			if(flag == 0){
				submit('messageSms_form');
			}
		};
		
		// ���水ť����¼�
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
		// ��ͣ���Ͳ����水ť����¼�
		$('#btnCancelSendAndSave').click( function () {
			$('form .messageSms_status').val("4");
			// ����Subaction
    		$('.subAction').val('modifyObject');
			// ����Form Action
    		$('.messageSms_form').attr('action', 'messageSmsAction.do?proc=modify_object');
    		enableForm('messageSms_form');
			btnSubmit();
		});
		
		// �ָ����Ͳ����水ť����¼�
		$('#btnSandAndSave').click( function () { 
			$('form .messageSms_status').val("1");
			// ����Subaction
    		$('.subAction').val('modifyObject');
			// ����Form Action
    		$('.messageSms_form').attr('action', 'messageSmsAction.do?proc=modify_object');
    		enableForm('messageSms_form');
			btnSubmit();
		});
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('messageSms_form');
				//
        		$(".closebutton").hide();
        		// ״̬��ʼ�ն��ǲ��ɱ༭״̬
				$('form .messageSms_status').attr('disabled','disabled');
				// Enable ckeditor
				try{
					//var editor = CKEDITOR.instances.content; Ĭ��ʹ�÷�ʽ�༭������
					//jquery ��ʽ��ñ༭������
					var editor = $('.messageSms_content').ckeditorGet();
					if(editor) {
						editor.setReadOnly(false);
					}
				}catch (e) {
					// �쳣���������ֻ�����������ݵ�ԭ�����
				}
				// ����Subaction
        		$('.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.messageSms_form').attr('action', 'messageSmsAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// ��ʼ�����ر༭��ť
		$('#btnEdit').hide();
		
		// �鿴ģʽ
		if($('.subAction').val() == 'viewObject'){
			// reception���ز������ֵ
			$(".messageSms_reception").val("");
			$(".messageSms_reception").hide();
			
			// ��Form��ΪDisable
			disableForm('messageSms_form');
			// ����Page Title
			$('#pageTitle').html('���ŷ��Ͳ鿴');
			// ������ťValue
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		$('#btnCancel').click( function () {
			back();
		});
		
		$(".messageTemplateId_contentType").change(function(){
			if($(this).val()!=0){
				$.post('messageTemplateAction.do?proc=getMessageTemplateContent_ajax&templateId='+$(this).val(),function(data){
					$('.messageSms_content').val(data);
				},'text');
			}
		});
		
		$(document).click(function(e){
			var $target =  $(e.target);
			if( $target.parent().hasClass("_contactDiv") || $target.hasClass("_contactDiv") || $target.parent().hasClass("bit-box") || $target.hasClass("bit-box")){
				$(".messageSms_reception").val("");
				if($('.subAction').val()!='viewObject'){
					$(".messageSms_reception").show();
				}
			}else{
				if($("._contactDiv .bit-box").size()>0){
					$(".messageSms_reception").hide();
				}
			}
		});
		
		$(".messageSms_reception").keyup(function(){
			var val = $(this).val();
			var lastChar = val.substr(val.length-1,val.length);
			if(lastChar == ";"){
				var smses = val.split(";");
				for(var i = 0 ; i<smses.length-1;i++){
					var sms = smses[i];	
					if((/[^0-9]+/.test(sms) || sms.length != 11 || sms.indexOf("1") != 0)){
						$(".messageSms_reception").val(val.substr(0,val.length-1));
						$(".messageSms_reception").css("color","red");
						// �����ʾ
						$("._contactDiv_error").remove();
						$("._contactDiv").after('<label class="error _contactDiv_error">&#8226; �Ƿ��ֻ���ʽ</label>');
					}else{
						// �����Ƿ��Ѵ����ֻ�����
						
						var flag = false;
						$(".holder span input.user_smses").each(function(){
							if($(this).val()==sms){
								// �Ѵ���
								flag = true;
							}
						});
						
						if(flag==false){
							$(".holder").append('<span class="bit-box">'+sms+'<a class="closebutton" href="#" onclick="$(this).parent().remove(); "></a><input value="'+sms+'" type="hidden" class="user_smses" name="smses"></span>');
						}
						$(".messageSms_reception").val("");
						$(".messageSms_reception").css("color","black");
						// ɾ����ʾ
						$("._contactDiv_error").remove();
					}
				}
			}
		}).blur(function(){
			var val = $(".messageSms_reception").val();
			var lastChar = val.substr(val.length-1,val.length);
			if(val!="" && lastChar!=";"){
				$(".messageSms_reception").val($(".messageSms_reception").val()+";");
				$(".messageSms_reception").keyup();
				$(document).click();
			}
		});
		
	})(jQuery);
</script>
							