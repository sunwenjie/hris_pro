<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.actions.message.MessageMailAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>
<style type="text/css">
	span.holder_toSendTime span.bit-box2 { padding-right: 15px !important; position: relative;}
	span.holder_toSendTime span.bit-box2 { -moz-border-radius: 6px; -webkit-border-radius: 6px; border-radius: 6px; border: 1px solid #CAD8F3; background: #DEE7F8; padding: 1px 5px 2px; }
	span.holder_toSendTime span.bit-box2, span.holder_toSendTime span.bit-input input { font: 11px "Lucida Grande", "Verdana"; }
	span.holder_toSendTime span { float: left; list-style-type: none; margin: 4px 5px 0px 0px !important; }
	span.holder_toSendTime { padding: 0}
	span.holder_toSendTime span.bit-box2 a.closebutton { position: absolute; right: 4px; top: 5px; display: block; width: 7px; height: 7px; font-size: 1px; background: url('images/close.gif'); }

</style>
<div id="content">
	<div id="managementMessageTemplate" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="message" key="message.mail" /></label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.send" />" /> 
				<logic:notEmpty name="messageMailForm" property="mailId">
					<logic:equal name="messageMailForm" property="status" value="1"> 
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
						<input type="button" class="addbutton" name="btnCancelSendAndSave" id="btnCancelSendAndSave" value="<bean:message bundle="public" key="button.stop.send.and.save" />" />
					</logic:equal>
					<logic:equal name="messageMailForm" property="status" value="4"> 
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
						<input type="button" class="addbutton" name="btnSendAndSave" id="btnSendAndSave" value="<bean:message bundle="public" key="button.recovery.send.and.save" />" />
					</logic:equal>
					<logic:equal name="messageMailForm" property="status" value="5"> 
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
					</logic:equal>
				</logic:notEmpty>
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.list" />" />
			</div>
			<html:form action="messageMailAction.do?proc=add_object" styleClass="messageMail_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" class="hiddenReception" id="hiddenReception" name="hiddenReception" value="<bean:write name="messageMailForm" property="reception" />" />
				<input type="hidden" class="mailId" id="mailId" name="mailId" value="<bean:write name="messageMailForm" property="encodedId" />" />
				<input type="hidden" class="subAction" id="subAction" name="subAction" value="<bean:write name="messageMailForm" property="subAction"/>" />
				<input type="hidden" name="repeatType" class="repeatType" id="repeatType" value="1">
				<input  type="hidden" class="checkType" id="checkType" name="checkType" value="email"/>  
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.mail.reception" /> <img src="images/tips.png" title="<bean:message bundle="message" key="message.mail.reception.tips" />"><em> *</em></label>
							<div class="recLabel" style="width: 216px;height:100px;display: block;background-color: white">
							
							</div>
							<a id="contactSearch" onclick="popupContactSearch();" class="kanhandle"><img src="images/add.png" title="<bean:message bundle="message" key="message.mail.reception.img.tips" />" /></a>
						</li>
						<li>
							<logic:equal value="5" name="messageMailForm" property="status">
							<logic:equal value="3" name="messageMailForm" property="sendType">
								<label><bean:message bundle="message" key="message.mail.to.send.time" /></label>
								<label><bean:write name="messageMailForm" property="toSendTime"/></label>
							</logic:equal>
							</logic:equal>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.mail.subject" /><em> *</em></label> 
							<html:text property="title" maxlength="25" styleClass="messageMail_title" /> 
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.define.email.template" /></label> 
							<html:select property="templateId" styleClass="messageTemplateId_contentType" >
								<html:optionsCollection property="templateIds" value="mappingId" label="mappingValue"  />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.content.type" />  <img src="images/tips.png" title="<bean:message bundle="message" key="message.mail.content.type.tips" />"><em> *</em></label> 
							<html:select property="contentType" styleClass="messageMail_contentType" >
								<html:optionsCollection property="contentTypes" value="mappingId" label="mappingValue"  />
							</html:select>
						</li>
						<logic:notEmpty name="messageMailForm" property="mailId">
							<li>
								<label><bean:message bundle="public" key="public.status" /><em> *</em></label>
								<html:select property="status" styleClass="messageMail_status" >
									<html:optionsCollection property="statuses" value="mappingId" label="mappingValue"  />
								</html:select>
							</li>
						</logic:notEmpty>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.mail.send.type" /></label>
							<html:select property="sendType" styleClass="messageMail_sendType">
								<html:optionsCollection property="sendTypes" value="mappingId" label="mappingValue"  />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li class="toSendTimeLI" style="display: none;">
							<label><bean:message bundle="message" key="message.mail.send.time" /></label>
							<div style="background-color:white;width: 220px;border-color: #D2D1D1; padding: 0 4px 0 4px; " class="_dateDiv" id="dateDiv"> 
							<span class="holder_toSendTime">
								<logic:notEmpty name="messageMailForm" property="toSendTime">
									<span class="bit-box2">
										<bean:write name="messageMailForm" property="toSendTime"/>
										<a class="closebutton" href="#" onclick="$(this).parent().remove(); "></a>
										<input value='<bean:write name="messageMailForm" property="toSendTime"/>' type="hidden" class="toSendTimes" name="toSendTimes">
									</span>
								</logic:notEmpty>
							</span>
							<input type="text" name="toSendTimeTemp" class="Wdate messageMail_toSendTime" style="float:left !important;border:0px;background-color:white;width:210px;">
							</div> 
						</li>
					</ol>
					<ol class="auto startEndDateTimeOL" style="display: none;">
						<li>
							<label><bean:message bundle="message" key="message.mail.start.date" /></label>
							<html:text property="startDateTime" styleClass="messageMail_startDateTime Wdate"></html:text>
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.end.date" /></label>
							<html:text property="endDateTime" styleClass="messageMail_endDateTime Wdate"></html:text>
						</li>
					</ol>
					<p>
 						<div id="tab" style="display: none;"> 
								
								<div class="_tabMenu tabMenu">
									<ul>
										<li id="_tabMenu1" onClick="_changeTab(1,3)" class="first hover"><bean:message bundle="public" key="menu.table.title.day" /></li>
										<li id="_tabMenu2" onClick="_changeTab(2,3)" ><bean:message bundle="public" key="menu.table.title.weekly" /></li>
										<li id="_tabMenu3" onClick="_changeTab(3,3)" ><bean:message bundle="public" key="menu.table.title.monthly" /></li>
									</ul>
								</div>
						</div>
						<div class="_tabContent" style="display: none;">
							<div id="_tabContent1" class="kantab">
								
										<label style="width: 20px !important;">每</label>
										<input type="text" name="period_day" id="period_day" class="period_day" style="width: 130px !important;">
										<label>天</label>
									
							</div>
							<div id="_tabContent2" class="kantab" style="display: none">
								<ol class="auto">
									<li>
										<label style="width: 85px !important;">重复间隔为(C)</label>
										<input type="text" name="period_week" id="period_week" class="period_week" style="width: 130px !important;">
										<label>周后的:</label>
									</li>
								</ol>
								<ol class="auto">
									<li>
										<div>
											<input type="checkbox" name="chk_weekPeriod" value="1"><bean:message bundle="public" key="ckb.mon" />&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="chk_weekPeriod" value="2"><bean:message bundle="public" key="ckb.tue" />&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="chk_weekPeriod" value="3"><bean:message bundle="public" key="ckb.wed" />&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="chk_weekPeriod" value="4"><bean:message bundle="public" key="ckb.thu" />&nbsp;&nbsp;&nbsp;&nbsp;
											<br>
											<br>
											<input type="checkbox" name="chk_weekPeriod" value="5"><bean:message bundle="public" key="ckb.fri" />&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="chk_weekPeriod" value="6"><bean:message bundle="public" key="ckb.sat" />&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="chk_weekPeriod" value="7"><bean:message bundle="public" key="ckb.sun" />&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									</li>
								</ol>
							</div>
							<div id="_tabContent3" class="kantab" style="display: none">
							
								<label style="width: 85px !important;">每(A)</label>
										<input type="text" name="period_month" id="period_month" class="period_month" style="width: 60px !important;">
								<label>个月的第</label>
										<input type="text" name="additionalPeriod" id="additionalPeriod" class="additionalPeriod" style="width: 60px !important;">
								<label>天</label>
										
							</div>
						</div>
					<p>
					<ol class="static">
						<li><label class='systemInfoContent'><bean:message bundle="message" key="message.mail.content" /><em> *</em></label></li>
						<li>
						<html:textarea property="content" styleId="messageMail_content"  styleClass="messageMail_content"/>
						<script type="text/javascript">
							$(function()
							{
								$('.messageMail_content').ckeditor(
									{
								        toolbar : 'Template'
								    }
								);
							});
						</script>
						</li>
					</ol>
				</fieldset>
				<jsp:include page="/popup/selectConstantsPopup.jsp">
					<jsp:param name="scopeType" value="1"/>
					<jsp:param name="CKEditElementId" value="messageMail_content"/>
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
		$('#menu_message_Mail').addClass('selected');
		$('#searchDiv').hide();
		
		// 为发送时间输入框添加DataPicker插件
		$('.messageMail_toSendTime').focus(function() {
			WdatePicker({
				readOnly:true,
				autoPickDate:false,
				dateFmt : "yyyy-MM-dd HH:mm:ss",
				onpicking : function(dp){
				},
				onpicked : function(dp){
						// 如果是定时发送
						var toSendTime = $(".messageMail_toSendTime").val();
						$(".holder_toSendTime").append('<span class="bit-box2">'+toSendTime+'<a class="closebutton" href="#" onclick="$(this).parent().remove(); "></a><input value="'+toSendTime+'" type="hidden" class="toSendTimes" name="toSendTimes"></span>');
						$(".messageMail_toSendTime").val('');
						$dp.hide();
				} 
			});
		});
		
		$('.messageMail_startDateTime').focus(function() {
			WdatePicker({
				readOnly:true,
				dateFmt : "yyyy-MM-dd HH:mm:ss"
			});
		});
		$('.messageMail_endDateTime').focus(function() {
			WdatePicker({
				readOnly:true,
				dateFmt : "yyyy-MM-dd HH:mm:ss"
			});
		});
		
		// 按钮提交事件
		function btnSubmit() {
			var flag = 0;
			var errorMsg = lang_en ? ' Can not be empty;' : '不能为空；';
			flag = flag + validate("messageMail_title", true, "common", 150, 0);
			
			if($(".messageMail_content").val()==''){
				flag = flag + 1;
				$("._systemInfoContent_error").remove();
				$(".systemInfoContent").after('<label class="error _systemInfoContent_error">' + errorMsg +'</label>');
			}else{
				$("._systemInfoContent_error").remove();
			}
			
			if($(".messageMail_sendType").val()=="2"){
				// 如果是定时发送
				if($("._dateDiv .bit-box2").size()==0){
					flag=flag+1;
					$("._dateDiv_error").remove();
					$("._dateDiv").after('<label class="error _dateDiv_error">' + errorMsg +'</label>');
					$("._dateDiv").css("background","#F1C6BD");
					$("._dateDiv").css("border","1px solid #D48574");
					$(".messageMail_toSendTime").css("background","#F1C6BD");
					
				}else{
					$("._dateDiv_error").remove();
					$("._dateDiv").css("background","#ffffff");
					$("._dateDiv").css("border","1px solid #D2D1D1");
					$(".messageMail_toSendTime").css("background","#FFFFFF");
				}
			}else if($(".messageMail_sendType").val()=="3"){
				flag = flag + validate("messageMail_startDateTime", true, "common", 0, 0);
				flag = flag + validate("messageMail_endDateTime", true, "common", 0, 0);
				if($("#repeatType").val()=="1"){
					// 按照天
					flag = flag + validate_nowords("period_day", true, "numeric", 0, 0,true);
				}else if($("#repeatType").val()=="2"){
					// 周
					flag = flag + validate_nowords("period_week", true, "numeric", 0, 0,true);
				}else if($("#repeatType").val()=="3"){
					// 月
					flag = flag + validate_nowords("period_month", true, "numeric", 0, 0,true);
					flag = flag + validate_nowords("additionalPeriod", true, "numeric", 0, 0,true);
				}
			}
			
			if(flag == 0){
				submit('messageMail_form');
			}
		};
		
		// 保存按钮点击事件
		$('#btnSave').click( function () { 
			btnSubmit();
		});
			
		// 暂停发送并保存按钮点击事件
		$('#btnCancelSendAndSave').click( function () { 
			$('form .messageMail_status').val("4");
			// 更改Subaction
    		$('.subAction').val('modifyObject');
			// 更改Form Action
    		$('.messageMail_form').attr('action', 'messageMailAction.do?proc=modify_object');
    		enableForm('messageMail_form');
			btnSubmit();
		});
		
		// 恢复发送并保存按钮点击事件
		$('#btnSendAndSave').click( function () { 
			$('form .messageMail_status').val("1");
			// 更改Subaction
    		$('.subAction').val('modifyObject');
			// 更改Form Action
    		$('.messageMail_form').attr('action', 'messageMailAction.do?proc=modify_object');
    		enableForm('messageMail_form');
			btnSubmit();
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				enableForm('messageMail_form');
				
				$(".closebutton").show();
				// 状态的始终都是不可编辑状态
				$('form .messageMail_status').attr('disabled','disabled');
				// Enable ckeditor
				try{
					//var editor = CKEDITOR.instances.content; 默认使用方式编辑器对象
					//jquery 方式获得编辑器对象
					var editor = $('.messageMail_content').ckeditorGet();
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
        		$('#pageTitle').html('<bean:message bundle="message" key="message.mail" /> <bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.messageMail_form').attr('action', 'messageMailAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// reception隐藏并且清空值  联系人有.box保存
			$(".messageMail_reception").val("");
			$(".messageMail_reception").hide();
			
			// 将Form设为Disable
			disableForm('messageMail_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="message" key="message.mail" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnSave').hide();
			$('#btnEdit').show();
			// 隐藏删除小图标
			$(".closebutton").hide();
			// 隐藏发送时间
			$(".messageMail_toSendTime").hide();
			// 查看模式下各种赋值和显示
			if("<bean:write name='messageMailForm' property='sendType'/>"==2){
				$(".toSendTimeLI").show();
			}else if("<bean:write name='messageMailForm' property='sendType'/>"==3){
				$("._tabContent").show();
				$("#tab").show();
				$(".startEndDateTimeOL").show();
				// 显示对应的tab
				var repeatType = "<bean:write name='messageMailForm' property='repeatType'/>";
				_changeTab(repeatType,3);
				// 对应的赋值
				var period = "<bean:write name='messageMailForm' property='period'/>";
				if(repeatType=="1"){
					$("#period_day").val(period);
				}else if(repeatType=="2"){
					$("#period_week").val(period);
					var weekPeriod = "<bean:write name='messageMailForm' property='weekPeriod'/>";
					$("input[name=chk_weekPeriod]").each(function(i){
						if(weekPeriod.charAt($(this).val()-1) == "1"){
							$(this).attr("checked","checked");
						}
					});
				}else if(repeatType=="3"){
					$("#period_month").val(period);
					$("#additionalPeriod").val("<bean:write name='messageMailForm' property='additionalPeriod'/>");
				}
			}
			
		}
		
		$('#btnCancel').click( function () {
			back();
		});

		$(".messageTemplateId_contentType").change(function(){
			if($(this).val()!=0){
				$.post('messageTemplateAction.do?proc=getMessageTemplateContent_ajax&templateId='+$(this).val(),function(data){
					$('.messageMail_content').val(data);
				},'text');
			}else{
				$('.messageMail_content').val("");
			}
		});
		
		$(".messageMail_sendType").change(function(){
			if($(this).val()=='2'){
				
				$(".toSendTimeLI").show();
				$("._tabContent").hide();
				$("#tab").hide();
				$(".startEndDateTimeOL").hide();
				
			}else if($(this).val()=='3'){
				
				$(".toSendTimeLI").hide();
				$("._tabContent").show();
				$("#tab").show();
				$(".startEndDateTimeOL").show();
				
			}else{
				
				$(".toSendTimeLI").hide();
				$("._tabContent").hide();
				$("#tab").hide();
				$(".startEndDateTimeOL").hide();
				
			}
		});
		
	})(jQuery);
	
	// Tab切换
	function _changeTab(cursel, n){ 
		for(i = 1; i <= n; i++){ 
			$('#_tabMenu' + i).removeClass('hover');
			$('#_tabContent' + i).hide();
		} 
		
		$('#_tabMenu' + cursel).addClass('hover');
		$('#_tabContent' + cursel).show();
		$("#repeatType").val(cursel);
	};
</script>
							