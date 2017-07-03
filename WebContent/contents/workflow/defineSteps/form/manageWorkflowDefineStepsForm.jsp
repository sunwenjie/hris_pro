<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="workflowDefineStepsAction.do?proc=add_object" styleClass="workflowDefineSteps_form">
	<%= BaseAction.addToken( request ) %>
	<html:hidden property="subAction" styleClass="subAction" />
	<input type="hidden" name="stepId" id="stepId" value="<bean:write name="workflowDefineStepsForm" property="encodedId"/>">	
	<input type="hidden" name="defineId" id="defineId" value="<bean:write name="workflowDefineForm" property="encodedId"/>" />
	<fieldset>
		<ol class="auto">
			<li class="required">
				<label><em>* </em><bean:message bundle="public" key="required.field" /></label>
			</li>
		</ol>
		<ol class="auto">						
			<li>
				<label><bean:message bundle="workflow" key="workflow.step.step.type" /><em> *</em></label>
				<html:select property="stepType" styleClass="workflowDefineSteps_stepType">
					<html:optionsCollection property="stepTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>  
				<label><bean:message bundle="workflow" key="workflow.step.audit.type" /><em> *</em></label>
				<html:select property="auditType" styleClass="workflowDefineSteps_auditType">
					<html:optionsCollection property="auditTypes" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li style="display: none;" id="workflowDefineSteps_positionId_LI">  
				<label><bean:message bundle="workflow" key="workflow.step.audit.position" /><em> *</em></label>
				<input id="workflowDefineSteps_positionName" type="text" class="workflowDefineSteps_positionName" 
				<logic:notEmpty name="workflowDefineStepsForm" property="searchField.titleZH"> value="<bean:write name="workflowDefineStepsForm" property="searchField.titleZH"/> - <bean:write name="workflowDefineStepsForm" property="searchField.titleEN"/> </logic:notEmpty>  "/>
				<html:hidden property="positionId" styleClass="workflowDefineSteps_positionId" styleId="workflowDefineSteps_positionId"/>
			</li>
			<li style="display: none;" id="workflowDefineSteps_staffName_LI">
				<label><bean:message bundle="workflow" key="workflow.step.audit.staff" /><em> *</em></label>
				<input id="workflowDefineSteps_staffName"  type="text"  class="workflowDefineSteps_staffName" />
				<html:hidden property="staffId" styleClass="workflowDefineSteps_staffId" styleId="workflowDefineSteps_staffId"/>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="workflow" key="workflow.step.join.type" /></label>
				<html:select property="joinType" styleClass="workflowDefineSteps_joinType">
					<html:optionsCollection property="joinTypes" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
			<li style="display: none;" id="workflowDefineSteps_referPositionId_LI">  
				<label><bean:message bundle="workflow" key="workflow.step.refer.position" /></label>
				<input id="workflowDefineSteps_referPositionName" type="text" class="workflowDefineSteps_referPositionName" />
				<html:hidden property="referPositionId" styleClass="workflowDefineSteps_referPositionId" styleId="workflowDefineSteps_referPositionId"/>
			</li>
			<li style="display: none;" id="workflowDefineSteps_referPositionGrade_LI">
				<label><bean:message bundle="workflow" key="workflow.step.refer.position.grade" /></label>
				<html:select property="referPositionGrade" styleClass="workflowDefineSteps_referPositionGrade" >
					<logic:equal name="workflowDefineForm" property="scope" value="1">
						<html:optionsCollection property="positionGrades" value="mappingId" label="mappingValue" />
					</logic:equal>
					<logic:equal name="workflowDefineForm" property="scope" value="2">
						<html:optionsCollection property="employeePositionGrades" value="mappingId" label="mappingValue" />
					</logic:equal>
				</html:select>
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="workflow" key="workflow.step.join.order.type" /></label>
				<html:select property="joinOrderType" styleClass="workflowDefineSteps_joinOrderTypes">
					<html:optionsCollection property="joinOrderTypes" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
		    <logic:equal name="workflowDefineForm" property="approvalType" value="1">
			<li>
				<label><bean:message bundle="workflow" key="workflow.step.step.index" /><em> *</em></label> 
				<html:text property="stepIndex" maxlength="3" styleClass="workflowDefineSteps_stepIndex" />
			</li>
			</logic:equal>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="workflow" key="workflow.define.msg.remind" /></label>
				<div id="rightIds_div">
					<label class='auto serviceType_3'>
						<html:checkbox property="sendEmail" value="1" styleClass="workflowDefineStepsForm_sendEmail" onclick="checkForWorkflowDefineStepsMessageTemp('1',this.checked);" >
							<logic:iterate id="msgs" name="workflowDefineStepsForm" property="msgRemindTypes">
								<logic:equal name="msgs" property="mappingId" value="0"><bean:write name="msgs" property="mappingValue" /></logic:equal>
							</logic:iterate>
						</html:checkbox>
					</label>
					<label class='auto serviceType_3'>
						<html:checkbox property="sendSMS" value="1" styleClass="workflowDefineStepsForm_sendSMS" onclick="checkForWorkflowDefineStepsMessageTemp('2',this.checked);">
							<logic:iterate id="msgs" name="workflowDefineStepsForm" property="msgRemindTypes">
								<logic:equal name="msgs" property="mappingId" value="1"><bean:write name="msgs" property="mappingValue" /> </logic:equal>
							</logic:iterate>
						</html:checkbox>
					</label>
					<label class='auto serviceType_3'>
						<html:checkbox property="sendInfo" value="1" styleClass="workflowDefineStepsForm_sendInfo" onclick="checkForWorkflowDefineStepsMessageTemp('3',this.checked);">
							<logic:iterate id="msgs" name="workflowDefineStepsForm" property="msgRemindTypes">
								<logic:equal name="msgs" property="mappingId" value="2"><bean:write name="msgs" property="mappingValue" /> </logic:equal>
							</logic:iterate>
						</html:checkbox>
					</label>
				</div>
			</li>	
		</ol>	
		<ol class="auto">	
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
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="workflowDefineSteps_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>
<br/>

<script type="text/javascript">
	(function($) {
		// 绑定审核职位联想功能
		bindThinkingToPositionName();
		bindThinkingToStaffName();
		
		// DefineSteps 添加时默认消息提示选项和 Define消息提示选项相同
		if($('.workflowDefineSteps_form .subAction').val() != 'viewObject'){
			$('.workflowDefineSteps_form input[name=sendEmail]').attr('checked',$('.workflowDefine_form input[name=sendEmail]').attr('checked'));
			$('.workflowDefineSteps_form input[name=sendSMS]').attr('checked',$('.workflowDefine_form input[name=sendSMS]').attr('checked'));
			$('.workflowDefineSteps_form input[name=sendInfo]').attr('checked',$('.workflowDefine_form input[name=sendInfo]').attr('checked'));
		}
		
		if("<bean:write name='workflowDefineStepsForm' property='sendEmail'/>" >0 ){
			$(".workflowDefineSteps_form .workflowDefine_emailTemplateId").parent().show();
		}
		if("<bean:write name='workflowDefineStepsForm' property='sendSMS'/>" >0 ){
			$(".workflowDefineSteps_form .workflowDefine_smsTemplateId").parent().show();
		}
		if("<bean:write name='workflowDefineStepsForm' property='sendInfo'/>" >0 ){
			$(".workflowDefineSteps_form .workflowDefine_infoTemplateId").parent().show();
		}
		
		// 绑定参与审批范围change事件
		$(".workflowDefineSteps_form select.workflowDefineSteps_auditType").change(function(){
			if($(this).val() == 1){
				$("#workflowDefineSteps_positionId_LI").show();	
				$("#workflowDefineSteps_staffName_LI").hide();	
				$("#workflowDefineSteps_staffName_LI input").val('');
			}else if($(this).val() == 4){
				$("#workflowDefineSteps_positionId_LI").hide();	
				$("#workflowDefineSteps_staffName_LI").show();	
				$("#workflowDefineSteps_positionId_LI input").val('');	
			}else{
				$("#workflowDefineSteps_positionId_LI").hide();	
				$("#workflowDefineSteps_staffName_LI").hide();
				$("#workflowDefineSteps_staffName_LI input").val('');
				$("#workflowDefineSteps_positionId_LI input").val('');	
			}
		});
		
		// 绑定参与方式改变change事件   
		$(".workflowDefineSteps_form select.workflowDefineSteps_joinType").change(function(){
			if($(this).val() == 1){
				$("#workflowDefineSteps_referPositionId_LI").show();	
				$("#workflowDefineSteps_referPositionGrade_LI").hide();	
			}else if($(this).val() == 2){
				$("#workflowDefineSteps_referPositionId_LI").hide();	
				$("#workflowDefineSteps_referPositionGrade_LI").show();	
			}else{
				$("#workflowDefineSteps_referPositionId_LI").hide();	
				$("#workflowDefineSteps_referPositionGrade_LI").hide();	
			}
		});
		
		// 给select添加title
		$('.workflowDefineSteps_form select.workflowDefineSteps_stepType option').each( function(){
			if( $(this).val() == 1){
				$(this).attr('title','<bean:message bundle="workflow" key="workflow.step.step.type.tips.one" />');
			}else if( $(this).val() == 2){
				$(this).attr('title','<bean:message bundle="workflow" key="workflow.step.step.type.tips.two" />');
			}else if( $(this).val() == 3){
				$(this).attr('title','<bean:message bundle="workflow" key="workflow.step.step.type.tips.three" />');
			}
		});
		
		// 给select添加title
		$('.workflowDefineSteps_form select.workflowDefineSteps_joinOrderTypes option').each( function(){
			if( $(this).val() == 1){
				$(this).attr('title','<bean:message bundle="workflow" key="workflow.step.join.order.type.tips.one" />');
			}else if( $(this).val() == 2){
				$(this).attr('title','<bean:message bundle="workflow" key="workflow.step.join.order.type.tips.two" />');
			}else if( $(this).val() == 3){
				$(this).attr('title','<bean:message bundle="workflow" key="workflow.step.join.order.type.tips.three" />');
			}
		});
		
		messageNoitceType();
		
		// 触发参与审批范围change事件
		$(".workflowDefineSteps_auditType").change();
		
		// 触发参与方式改变change事件   
		$(".workflowDefineSteps_joinType").change();
	})(jQuery);
	
	// Use the common thinking
	function bindThinkingToPositionName(){
		var action = null;
		if( '<bean:write name="workflowDefineForm" property="scope" />' == '1' )
		{
			action = 'positionAction.do?proc=list_object_json';
		}
		else
		{
			action = 'mgtPositionAction.do?proc=list_object_json';
		}
		kanThinking_column('workflowDefineSteps_positionName', 'workflowDefineSteps_positionId', action );
		kanThinking_column('workflowDefineSteps_referPositionName', 'workflowDefineSteps_referPositionId', action );
	};
	//	Think事件
	function bindThinkingToStaffName(){
		kanThinking_column('workflowDefineSteps_staffName', 'workflowDefineSteps_staffId', 'staffAction.do?proc=list_object_json');
	};
	
	// 消息提醒checkbox
	function checkForWorkflowDefineStepsMessageTemp(type,flag){
		if(type=="1"){
			// email
			if(flag==true){
				$(".workflowDefineSteps_form .workflowDefine_emailTemplateId").parent().show();
			}else{
				$(".workflowDefineSteps_form .workflowDefine_emailTemplateId").parent().hide();
				$(".workflowDefineSteps_form .workflowDefine_emailTemplateId").val("0");
			}
		}else if(type=="2"){
			//sms
			if(flag==true){
				$(".workflowDefineSteps_form .workflowDefine_smsTemplateId").parent().show();
			}else{
				$(".workflowDefineSteps_form .workflowDefine_smsTemplateId").parent().hide();
				$(".workflowDefineSteps_form .workflowDefine_smsTemplateId").val("0");
			}
		}else if(type=="3"){
			// info
			if(flag==true){
				$(".workflowDefineSteps_form .workflowDefine_infoTemplateId").parent().show();
			}else{
				$(".workflowDefineSteps_form .workflowDefine_infoTemplateId").parent().hide();
				$(".workflowDefineSteps_form .workflowDefine_infoTemplateId").val("0");
			}
		}
	};
	
	function messageNoitceType(){
		if( $(".workflowDefineSteps_form .workflowDefineStepsForm_sendEmail").attr('checked')=='checked'){
			$(".workflowDefineSteps_form .workflowDefine_emailTemplateId").parent().show();
		}
		if( $(".workflowDefineSteps_form .workflowDefineStepsForm_sendSMS").attr('checked')=='checked'){
			$(".workflowDefineSteps_form .workflowDefine_smsTemplateId").parent().show();
		}
		if( $(".workflowDefineSteps_form .workflowDefineStepsForm_sendInfo").attr('checked')=='checked'){
			$(".workflowDefineSteps_form .workflowDefine_infoTemplateId").parent().show();
		}
	};
</script>