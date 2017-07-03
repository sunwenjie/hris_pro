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
		// 设置菜单选择样式
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_SMS').addClass('selected');
		$('#searchDiv').hide();
		
		// 如果是viewObject 隐藏删除
		if($('.subAction').val()=='viewObject'){
			$(".closebutton").hide();
		}
		
		// 按钮提交事件
		function btnSubmit() {
			var flag = 0;
			flag = flag + validate("messageSms_content", true, "common", 500, 0);
			if(flag == 0){
				submit('messageSms_form');
			}
		};
		
		// 保存按钮点击事件
		$('#btnSave').click( function () { 
			btnSubmit();
		});
		
		// 暂停发送并保存按钮点击事件
		$('#btnCancelSendAndSave').click( function () {
			$('form .messageSms_status').val("4");
			// 更改Subaction
    		$('.subAction').val('modifyObject');
			// 更改Form Action
    		$('.messageSms_form').attr('action', 'messageSmsAction.do?proc=modify_object');
    		enableForm('messageSms_form');
			btnSubmit();
		});
		
		// 恢复发送并保存按钮点击事件
		$('#btnSandAndSave').click( function () { 
			$('form .messageSms_status').val("1");
			// 更改Subaction
    		$('.subAction').val('modifyObject');
			// 更改Form Action
    		$('.messageSms_form').attr('action', 'messageSmsAction.do?proc=modify_object');
    		enableForm('messageSms_form');
			btnSubmit();
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('messageSms_form');
				//
        		$(".closebutton").hide();
        		// 状态的始终都是不可编辑状态
				$('form .messageSms_status').attr('disabled','disabled');
				// Enable ckeditor
				try{
					//var editor = CKEDITOR.instances.content; 默认使用方式编辑器对象
					//jquery 方式获得编辑器对象
					var editor = $('.messageSms_content').ckeditorGet();
					if(editor) {
						editor.setReadOnly(false);
					}
				}catch (e) {
					// 异常，可能是手机浏览器不兼容等原因造成
				}
				// 更改Subaction
        		$('.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.messageSms_form').attr('action', 'messageSmsAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// 初始化隐藏编辑按钮
		$('#btnEdit').hide();
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// reception隐藏并且清空值
			$(".messageSms_reception").val("");
			$(".messageSms_reception").hide();
			
			// 将Form设为Disable
			disableForm('messageSms_form');
			// 更换Page Title
			$('#pageTitle').html('短信发送查看');
			// 更换按钮Value
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
						// 添加提示
						$("._contactDiv_error").remove();
						$("._contactDiv").after('<label class="error _contactDiv_error">&#8226; 非法手机格式</label>');
					}else{
						// 遍历是否已存在手机号码
						
						var flag = false;
						$(".holder span input.user_smses").each(function(){
							if($(this).val()==sms){
								// 已存在
								flag = true;
							}
						});
						
						if(flag==false){
							$(".holder").append('<span class="bit-box">'+sms+'<a class="closebutton" href="#" onclick="$(this).parent().remove(); "></a><input value="'+sms+'" type="hidden" class="user_smses" name="smses"></span>');
						}
						$(".messageSms_reception").val("");
						$(".messageSms_reception").css("color","black");
						// 删除提示
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
							