<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.workflow.WorkflowDefineAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<html:form action="workflowDefineAction.do?proc=add_object" styleClass="workflowDefine_form">
	<%= BaseAction.addToken( request ) %>
	<div class="top">
		<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save"/>" />
		<kan:auth right="modify" action="<%=WorkflowDefineAction.accessAction%>">
			<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
		</kan:auth>
		<kan:auth right="list" action="<%=WorkflowDefineAction.accessAction%>">
			<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
		</kan:auth>
	</div>
	<fieldset>
		<input type="hidden" id="defineId" name="defineId" value="<bean:write name="workflowDefineForm" property="encodedId" />" />
		<input type="hidden" id="subAction" name="subAction" class='subAction' value="<bean:write name="workflowDefineForm" property="subAction" />" />
		<input type="hidden" id="scope" name="scope" value="1" />
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.module" /><em> *</em></label> 
				<html:select property="workflowModuleId" styleClass="workflowDefine_workflowModuleId">
					<html:optionsCollection property="workflowModules" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.trigger.right" /><em> *</em></label>
				<div id="rightIds_div" class="rightIds_div" style="font-size:13px;">
					<label><bean:message bundle="workflow" key="workflow.define.trigger.right.init" /></label>
				</div>
			</li>
		</ol>
		<ol class="auto" >
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="workflowDefine_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="workflowDefine_nameEN" /> 
			</li>
		</ol>	
		<ol class="auto" >	
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.approval.type" /><em> *</em></label> 
				<html:select property="approvalType" styleId="workflowDefine_approvalType" styleClass="workflowDefine_approvalType" >
					<html:optionsCollection property="approvalTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.approval.steps" /></label> 
					<script type="text/javascript">
						var lang = '<%=request.getLocale().getLanguage()%>'
						document.write("<select name=\"steps\" id = \"workflowDefine_steps\">");
						document.write("<option value=0>" + (lang == "en" ? "Please Select" : "请选择") + "</option>");
						var stepsLength = 10;
						var selectStep = '<bean:write name="workflowDefineForm" property="steps"/>';
						for(var i=1;i<=10;i++){
							document.write("<option value=\""+i+"\""+ (selectStep==i?"selected='selected'":"")+">"+i+"</option>");
						}
						document.write("</select>");
					</script>
			</li>
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.max.position.grade" /></label> 
				<html:select property="topPositionGrade" styleClass="workflowDefine_positionGradeId">
					<html:optionsCollection property="positionGrades" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto" >	
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.msg.remind" /></label> 
				<div id="rightIds_div">
					<label class='auto serviceType_3'>
						<html:checkbox property="sendEmail" value="1" onclick="checkService('1',this.checked);" >
							<logic:iterate id="msgs" name="workflowDefineForm" property="msgRemindTypes">
								<logic:equal name="msgs" property="mappingId" value="0"><bean:write name="msgs" property="mappingValue" /></logic:equal>
							</logic:iterate>
						</html:checkbox>
					</label>
					<label class='auto serviceType_3'>
						<html:checkbox property="sendSMS" value="1" onclick="checkService('2',this.checked);">
							<logic:iterate id="msgs" name="workflowDefineForm" property="msgRemindTypes">
								<logic:equal name="msgs" property="mappingId" value="1"><bean:write name="msgs" property="mappingValue" /> </logic:equal>
							</logic:iterate>
						</html:checkbox>
					</label>
					<label class='auto serviceType_3'>
						<html:checkbox property="sendInfo" value="1" onclick="checkService('3',this.checked);">
							<logic:iterate id="msgs" name="workflowDefineForm" property="msgRemindTypes">
								<logic:equal name="msgs" property="mappingId" value="2"><bean:write name="msgs" property="mappingValue" /> </logic:equal>
							</logic:iterate>
						</html:checkbox>
					</label>
				</div>
			</li>
		</ol>	
		<ol class="auto" >		
			<li style="display: none">
				<label><bean:message bundle="workflow" key="workflow.define.email.template" /></label> 
				<html:select property="emailTemplateId" styleClass="workflowDefine_emailTemplateId">
					<html:optionsCollection property="emailTemplateIds" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
			<li style="display: none">
				<label><bean:message bundle="workflow" key="workflow.define.sms.template" /></label> 
				<html:select property="smsTemplateId" styleClass="workflowDefine_smsTemplateId">
					<html:optionsCollection property="smsTemplateIds" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
			<li style="display: none">
				<label><bean:message bundle="workflow" key="workflow.define.info.template" /></label> 
				<html:select property="infoTemplateId" styleClass="workflowDefine_infoTemplateId">
					<html:optionsCollection property="infoTemplateIds" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
		</ol>	
		<ol class="auto" >
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="workflowDefine_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	function checkService(type,flag){
		if(type=="1"){
			// email
			if(flag==true){
				$(".workflowDefine_form .workflowDefine_emailTemplateId").parent().show();
			}else{
				$(".workflowDefine_form .workflowDefine_emailTemplateId").parent().hide();
				$(".workflowDefine_form .workflowDefine_emailTemplateId").val("0");
			}
		}else if(type=="2"){
			//sms
			if(flag==true){
				$(".workflowDefine_form .workflowDefine_smsTemplateId").parent().show();
			}else{
				$(".workflowDefine_form .workflowDefine_smsTemplateId").parent().hide();
				$(".workflowDefine_form .workflowDefine_smsTemplateId").val("0");
			}
		}else if(type=="3"){
			// info
			if(flag==true){
				$(".workflowDefine_form .workflowDefine_infoTemplateId").parent().show();
			}else{
				$(".workflowDefine_form .workflowDefine_infoTemplateId").parent().hide();
				$(".workflowDefine_form .workflowDefine_infoTemplateId").val("0");
			}
		}
	};
	
	(function($) {
		if($('.subAction').val() == 'viewObject'){
			// disableForm Define表单
			disableForm('workflowDefine_form');
			$('.workflowDefine_form input#btnSave').hide();
			// 浏览模式。
			
			if("<bean:write name='workflowDefineForm' property='sendEmail'/>" >0 ){
				$(".workflowDefine_form .workflowDefine_emailTemplateId").parent().show();
			}
			if("<bean:write name='workflowDefineForm' property='sendSMS'/>" >0 ){
				$(".workflowDefine_form .workflowDefine_smsTemplateId").parent().show();
			}
			if("<bean:write name='workflowDefineForm' property='sendInfo'/>" >0 ){
				$(".workflowDefine_form .workflowDefine_infoTemplateId").parent().show();
			}
		}else{
			$('.workflowDefine_form input#btnEdit').hide();
		}
	})(jQuery);
</script>
						