<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first">��ϸ����  (<span id="numberOfOrderDetailRule"><bean:write name="clientOrderDetailRuleListSize" /></span>)</li> 
			<li id="tabMenu2" onClick="changeTab(2,2)">�籣���������  (<span id="numberOfOrderDetailSBRule"><bean:write name="clientOrderDetailSBRuleListSize" /></span>)</li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kanThinkingCombo" >
			<span><a onclick="addExtraObject('clientOrderDetailRuleAction.do?proc=to_objectNew&orderDetailId=<bean:write name="clientOrderDetailForm" property="encodedId" />', '<bean:write name="clientOrderDetailForm" property="encodedId" />');" class="kanhandle">�����ϸ����</a></span>
			<ol class="auto">
				<logic:notEmpty name="clientOrderDetailRuleList">
					<logic:iterate id="clientOrderDetailRuleVO" name="clientOrderDetailRuleList" indexId="number">
						<li>
							<img name="disable_img" width="12" height="12" id="disable_img" style="display: none;" src="images/disable-btn.png">
							<img name="warning_img" width="12" height="12" id="warning_img" onclick="removeExtraObject('clientOrderDetailRuleAction.do?proc=delete_object_ajax&clientOrderDetailRuleId=<bean:write name="clientOrderDetailRuleVO" property="encodedId" />', this, '#numberOfOrderDetailRule');" src="images/warning-btn.png">
							&nbsp;&nbsp; <a href="#" onclick="link('clientOrderDetailRuleAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailRuleVO" property="encodedId" />');"><bean:write name="clientOrderDetailRuleVO" property="decodeRuleType" /> <logic:equal name="clientOrderDetailRuleVO" property="ruleType" value="6">��</logic:equal><bean:write name="clientOrderDetailRuleVO" property="ruleValue" /><logic:equal name="clientOrderDetailRuleVO" property="ruleType" value="1">��</logic:equal><logic:equal name="clientOrderDetailRuleVO" property="ruleType" value="2">��</logic:equal><logic:equal name="clientOrderDetailRuleVO" property="ruleType" value="3">��</logic:equal><logic:equal name="clientOrderDetailRuleVO" property="ruleType" value="4">��</logic:equal><logic:equal name="clientOrderDetailRuleVO" property="ruleType" value="5">��</logic:equal> / <logic:equal name="clientOrderDetailRuleVO" property="chargeType" value="1">��</logic:equal><bean:write name="clientOrderDetailRuleVO" property="ruleResult" /><logic:equal name="clientOrderDetailRuleVO" property="chargeType" value="2">%</logic:equal></a>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		<div id="tabContent2" class="kantab kanThinkingCombo" style="display:none">
			<span><a onclick="addExtraObject('clientOrderDetailSBRuleAction.do?proc=to_objectNew&orderDetailId=<bean:write name="clientOrderDetailForm" property="encodedId" />', '<bean:write name="clientOrderDetailForm" property="encodedId" />');" class="kanhandle">����籣�����𲹽ɹ���</a></span>
			<ol class="auto">
				<logic:notEmpty name="clientOrderDetailSBRuleList">
					<logic:iterate id="clientOrderDetailSBRuleVO" name="clientOrderDetailSBRuleList" indexId="number">
						<li>
							<img name="disable_img" width="12" height="12" id="disable_img" style="display: none;" src="images/disable-btn.png">
							<img name="warning_img" width="12" height="12" id="warning_img" onclick="removeExtraObject('clientOrderDetailSBRuleAction.do?proc=delete_object_ajax&sbRuleId=<bean:write name="clientOrderDetailSBRuleVO" property="encodedId" />', this, '#numberOfOrderDetailSBRule');" src="images/warning-btn.png">
							&nbsp;&nbsp; <a href="#" onclick="link('clientOrderDetailSBRuleAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailSBRuleVO" property="encodedId" />');">
								��<bean:write name="clientOrderDetailSBRuleVO" property="amount" />(
								<bean:write name="clientOrderDetailSBRuleVO" property="decodeSBSolutionId" />/
								<bean:write name="clientOrderDetailSBRuleVO" property="decodeSBRuleType" />)
								<logic:equal value="2" name="clientOrderDetailSBRuleVO" property="status">
									<span class="highlight">ͣ��</span>
								</logic:equal>
							</a>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
	</div>
</div>

<input type="hidden" id="forwardURL" name="forwardURL" value="" />

<script type="text/javascript">
	(function($) {
		//�鿴ģʽ
		if($('.manage_primary_form input#subAction').val() == 'viewObject'){
			disableForm('manage_primary_form');
		};
	})(jQuery);
</script>