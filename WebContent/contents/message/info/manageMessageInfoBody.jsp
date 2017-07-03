<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>

<div id="content">
	<div id="managementMessageTemplate" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="message" key="message.system.info" /></label>
		</div>
		<div class="inner">
			<html:form action="messageInfoAction.do?proc=add_object" styleClass="messageInfo_form">
				<div class="top">
					<html:hidden property="subAction" styleClass="subAction" />
						<input type="hidden" id="infoId" name="infoId" value="<bean:write name="messageInfoForm" property="encodedId" />" />
						<input type="hidden" id="infoStatus" name="status" value="" />
						<logic:empty name="messageInfoForm" property="infoId">
							<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" />
						</logic:empty>
						<logic:equal name="messageInfoForm" property="status" value="1">
							<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" />
							<input type="button" class="editbutton" name="btnSend" id="btnSend" value="<bean:message bundle="public" key="button.send" />">
						</logic:equal>
					<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" />
				</div>
				<%=BaseAction.addToken(request)%>
				<input type="hidden" class="hiddenReception" id="hiddenReception" name="hiddenReception" value="<bean:write name="messageInfoForm" property="staffName" />" />
				<input type="hidden" id="mailId" name="mailId" value="<bean:write name="messageInfoForm" property="encodedId" />" />
				<input type="hidden" id="subAction" name="subAction" value="<bean:write name="messageInfoForm" property="subAction"/>" />
				<input  type="hidden" class="checkType" id="checkType" name="checkType" value="info"/> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.system.info.recipient" /><em> *</em></label>
							<div class="recLabel" style="width: 216px;height:100px;display: block;background-color: white"></div>
							<a id="contactSearch" onclick="popupContactSearch();" class="kanhandle"><img src="images/add.png" title="<bean:message bundle="public" key="button.add" />" /></a>
						</li>	
					</ol>
					<ol>
						<li>
							<label><bean:message bundle="message" key="message.system.info.title" /><em> *</em></label>
						    <html:text property="title" maxlength="25" styleClass="messageInfo_title" />
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.define.info.template" /></label> 
							<html:select property="templateId" styleClass="messageTemplateId_contentType">
								<html:optionsCollection property="templateIds" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="static">
						<li>
							<label class="systemInfoContent"><bean:message bundle="message" key="message.system.info.content" /><em> *</em></label>
						</li>
						<li>	
							<html:textarea property="content" styleId="messageInfo_content" styleClass="messageInfo_content" />
							<script type="text/javascript">
								$(function() {
									$('.messageInfo_content').ckeditor({
										toolbar : 'Template'
									});
								});
							</script>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.system.info.expired.time" /><em> *</em></label> 
							<input type="text" class="Wdate messageInfo_expiredTime" id="expiredTime" name="expiredTime" readonly="readonly" value="<bean:write name="messageInfoForm" property="expiredTime" format="yyyy-MM-dd" />" />
						</li>
					</ol>
				</fieldset>
				
				<jsp:include page="/popup/selectConstantsPopup.jsp">
					<jsp:param name="scopeType" value="1" />
					<jsp:param name="CKEditElementId" value="messageInfo_content" />
				</jsp:include>
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
		$('#menu_message_Info').addClass('selected');
		$('#searchDiv').hide();
		
		// 如果是viewObject 隐藏删除
		if($('.subAction').val()=='viewObject'){
			$(".closebutton").hide();
		}

		// 为过期时间输入框添加DataPicker插件
		$('#expiredTime').focus(function() {
			WdatePicker({
				minDate : "%y-%M-\#{%d+1}"
			});
		});
		// 去除默认值
		$(".messageInfo_reception").val("");

		// 按钮提交事件
		function btnSubmit() {
			var flag = 0;
			flag = flag + validate("messageInfo_title", true, "common", 100, 0);

			if ($(".messageInfo_content").val() == '') {
				flag = flag + 1;
				$("._systemInfoContent_error").remove();
				$(".systemInfoContent").after('<label class="error _systemInfoContent_error">&#8226; 不能为空</label>');
			} else {
				$("._systemInfoContent_error").remove();
			}

			flag = flag + validate("messageInfo_expiredTime", true, "common", 0, 0);
			if (flag == 0) {
				$('.messageInfo_form').submit();
			}
		};

		// 保存按钮点击事件
		$('#btnSave').click(function() {
			btnSubmit();
		});

		// 保存并发送按钮点击事件
		$('#btnSend').click(
				function() {
					enableForm('messageInfo_form');
					<logic:notEmpty name="messageInfoForm" property="infoId" >
					// 修改时，更改提交的form的action
					// 更改Subaction
					$('.subAction').val('modifyObject');
					// 更改Form Action
					$('.messageInfo_form').attr('action',
							'messageInfoAction.do?proc=modify_object');
					</logic:notEmpty>
					$('#infoStatus').val("2");
					btnSubmit();
				});

		// 编辑按钮点击事件
		$('#btnEdit').click(
				function() {
					if ($('.subAction').val() == 'viewObject') {
						// Enable form
						enableForm('messageInfo_form');
						//
						$(".closebutton").show();
						$("#contactSearch").show();
						// Enable ckeditor
						try {
							//var editor = CKEDITOR.instances.content; 默认使用方式编辑器对象
							//jquery 方式获得编辑器对象
							var editor = $('.messageInfo_content')
									.ckeditorGet();
							if (editor) {
								editor.setReadOnly(false);
							}
						} catch (e) {
							// 异常，可能是手机浏览器不兼容等原因造成
						}
						// 更改Subaction
						$('.subAction').val('modifyObject');
						// 更改按钮显示名
						$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
						// 更改Form Action
						$('.messageInfo_form').attr('action',
								'messageInfoAction.do?proc=modify_object');
					} else {
						btnSubmit();
					}
				});

		// 初始化隐藏编辑按钮
		$('#btnEdit').hide();

		// 查看模式
		if ($('.subAction').val() == 'viewObject') {
			// reception隐藏并且清空值
			$(".messageInfo_reception").val("");
			$(".messageInfo_reception").hide();

			// 将Form设为Disable
			disableForm('messageInfo_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="message" key="message.system.info" /> <bean:message bundle="public" key="oper.view" />');
			<logic:notEqual name="messageInfoForm" property="status" value="1">
				$('#btnSave').hide();
			</logic:notEqual>
			$('#btnEdit').show();
		}

		$('#btnCancel').click(function() {
			back();
		});

		$(".messageTemplateId_contentType").change(
				function() {
					if ($(this).val() != 0) {
						$.post(
								'messageTemplateAction.do?proc=getMessageTemplateContent_ajax&templateId='
										+ $(this).val(), function(data) {
									$('.messageInfo_content').val(data);
								}, 'text');
					}
				});

	})(jQuery);

</script>
