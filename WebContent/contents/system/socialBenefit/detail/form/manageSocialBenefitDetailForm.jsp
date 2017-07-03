<%@page import="com.kan.base.domain.system.SocialBenefitDetailVO"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="socialBenefitDetailAction.do?proc=add_object" styleClass="manageSocialBenefitDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="socialBenefitHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="socialBenefitDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="socialBenefitDetailForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="system" key="system.sb.detail.item" /><em> *</em></label> 
				<html:select property="itemId" styleClass="manageSocialBenefitDetail_itemId">
					<html:optionsCollection property="items" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="system" key="system.sb.detail.percent.company" />  <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.detail.percent.tips" />" /></label> 
				<html:text property="companyPercentLow" maxlength="25" styleClass="manageSocialBenefitDetail_companyPercentLow" /> 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.detail.percent.personal" />  <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.detail.percent.tips" />" /></label> 
				<html:text property="personalPercentLow" maxlength="25" styleClass="manageSocialBenefitDetail_personalPercentLow" /> 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.detail.floor.cap.company" />  <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.detail.floor.cap.tips" />" /></label> 
				<html:text property="companyFloor" maxlength="10" styleClass="small manageSocialBenefitDetail_companyFloor" /> 
				<html:text property="companyCap" maxlength="100" styleClass="small manageSocialBenefitDetail_companyCap" /> 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.detail.floor.cap.personal" />  <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.detail.floor.cap.tips" />" /></label> 
				<html:text property="personalFloor" maxlength="10" styleClass="small manageSocialBenefitDetail_personalFloor" /> 
				<html:text property="personalCap" maxlength="10" styleClass="small manageSocialBenefitDetail_personalCap" /> 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.detail.fix.amount.company" /></label> 
				<html:text property="companyFixAmount" maxlength="10" styleClass="manageSocialBenefitDetail_companyFixAmount" /> 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.detail.fix.amount.personal" /></label> 
				<html:text property="personalFixAmount" maxlength="10" styleClass="manageSocialBenefitDetail_personalFixAmount" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="system" key="system.sb.header.term.month" /></label>
				<html:hidden property="termMonth" styleClass="manageSocialBenefitDetail_termMonth"/>
				<div style="width: 215px;">
					<logic:present name="socialBenefitDetailForm" property="adjustMonths">
						<logic:iterate id="adjustMonth" name="socialBenefitDetailForm" property="adjustMonths" indexId="index">
							<logic:notEqual name="index" value="0">
								<label class='auto month_<bean:write name="adjustMonth" property="mappingId"/>'>
									<input type="checkbox" name="termMonthArray" id="ck_termMonth_<bean:write name="index"/>" value="<bean:write name="index"/>"/>
									<bean:write name="adjustMonth" property="mappingValue"/>
								</label>
							</logic:notEqual>
						</logic:iterate>
					</logic:present>
				</div>
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.adjustment.month" /></label>
				<html:select property="adjustMonth" styleClass="manageSocialBenefitDetail_adjustMonth">
					<html:optionsCollection property="adjustMonths" value="mappingId" label="mappingValue" />
				</html:select>	 
			</li>	
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="system" key="system.sb.header.effective" /></label> 
				<html:select property="effective" styleClass="manageSocialBenefitDetail_effective">
					<html:optionsCollection property="effectives" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>	
			<li>
				<label><bean:message bundle="system" key="system.sb.header.attribute" /><em> *</em></label> 
				<html:select property="attribute" styleClass="manageSocialBenefitDetail_attribute">
					<html:optionsCollection property="attributes" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="system" key="system.sb.header.start.date.limit" /><em> *</em></label> 
				<html:select property="startDateLimit" styleClass="manageSocialBenefitDetail_startDateLimit">
					<html:optionsCollection property="dates" value="mappingId" label="mappingValue" />
				</html:select>	
			</li> 
			<li>
				<label><bean:message bundle="system" key="system.sb.header.end.date.limit" /><em> *</em></label> 
				<html:select property="endDateLimit" styleClass="manageSocialBenefitDetail_endDateLimit">
					<html:optionsCollection property="dates" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="system" key="system.sb.header.start.rule" /></label> 
				<html:select property="startRule" styleClass="manageSocialBenefitDetail_startRule">
					<html:optionsCollection property="rules" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.start.rule.remark" /> <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.rule.remark.tips" />" /></label> 
				<html:text property="startRuleRemark" maxlength="12" styleClass="manageSocialBenefitDetail_startRuleRemark" />
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.end.rule" /></label> 
				<html:select property="endRule" styleClass="manageSocialBenefitDetail_endRule">
					<html:optionsCollection property="rules" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.end.rule.remark" /> <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.rule.remark.tips" />" /></label> 
				<html:text property="endRuleRemark" maxlength="12" styleClass="manageSocialBenefitDetail_endRuleRemark" /> 
			</li>
		</ol>
		<ol class="auto">		
			<li>
				<label><bean:message bundle="system" key="system.sb.header.makeup" /></label> 
				<html:select property="makeup" styleClass="manageSocialBenefitDetail_makeup">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>
		<ol class="auto" style="display: none;" id="isMakeup">	
			<li>
				<label><bean:message bundle="system" key="system.sb.header.makeup.month" /> <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.makeup.month.tips" />" /></label> 
				<html:select property="makeupMonth" styleClass="manageSocialBenefitDetail_makeupMonth">
					<html:optionsCollection property="adjustMonths" value="mappingId" label="mappingValue" />
				</html:select>	 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.makeup.cross.year" /></label> 
				<html:select property="makeupCrossYear" styleClass="manageSocialBenefitDetail_makeupCrossYear">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>	
		<ol class="auto">		
			<li>
				<label><bean:message bundle="public" key="public.accuracy.company" /></label> 
				<html:select property="companyAccuracy" styleClass="manageSocialBenefitDetail_accuracyCompany">
					<html:optionsCollection property="accuracys" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="public" key="public.accuracy.personal" /></label> 
				<html:select property="personalAccuracy" styleClass="manageSocialBenefitDetail_accuracyPersonal">
					<html:optionsCollection property="accuracys" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.round" /> </label> 
				<html:select property="round" styleClass="manageSocialBenefitDetail_round">
					<html:optionsCollection property="rounds" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageSocialBenefitDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageSocialBenefitDetail_description"></html:textarea>
			</li>					
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// detail 是否补缴绑定chang事件
		$('.manageSocialBenefitDetail_makeup').bind('change',function(){
			if($('.manageSocialBenefitDetail_makeup').val() == '1'){
				$('.manageSocialBenefitDetail_form #isMakeup').show();
			}else{
				$('.manageSocialBenefitDetail_form #isMakeup').hide();
			}
		});
		
		// 触发detail 是否补缴绑定chang事件
		$('.manageSocialBenefitDetail_makeup').trigger('change');
	})(jQuery);
	
	// 验证form
	function validate_detail_form(){
		var flag = 0;
		flag = flag + validate("manageSocialBenefitDetail_itemId", true, "select", 0, 0); 
	
		if($('.manageSocialBenefitDetail_companyFloor').val() - $('.manageSocialBenefitDetail_companyCap').val() > 0 ){
			flag++;
			addError( 'manageSocialBenefitDetail_companyCap', '公司基数由小到大；');
		}else{
			flag = flag + validate("manageSocialBenefitDetail_companyFloor", false, "currency", 10, 0);
			flag = flag + validate("manageSocialBenefitDetail_companyCap", false, "currency", 10, 0);
		}
		
		if( $('.manageSocialBenefitDetail_personalFloor').val() - $('.manageSocialBenefitDetail_personalCap').val() > 0 ){
			flag++;
			addError( 'manageSocialBenefitDetail_companyCap', '个人基数由小到大；');
			$('.manageSocialBenefitDetail_personalFloor').val('');
			$('.manageSocialBenefitDetail_personalCap').val('');
		}else{
			flag = flag + validate("manageSocialBenefitDetail_personalFloor", false, "currency", 10, 0);
			flag = flag + validate("manageSocialBenefitDetail_personalCap", false, "currency", 10, 0);
		}
		flag = flag + validate("manageSocialBenefitDetail_companyFixAmount", false, "currency", 10, 0);
		flag = flag + validate("manageSocialBenefitDetail_personalFixAmount", false, "currency", 10, 0);
		// “社保公积金所属月”，“申报开始日期”，“申报结束日期”必填；
		flag = flag + validate("manageSocialBenefitDetail_attribute", true, "select", 0, 0);
		flag = flag + validate("manageSocialBenefitDetail_startDateLimit", true, "select", 0, 0);
		flag = flag + validate("manageSocialBenefitDetail_endDateLimit", true, "select", 0, 0);
		// 当“缴纳规则”不为请选择时，“规则参数”必填；“规则参数”验证整数；
		if($('.manageSocialBenefitDetail_startRule').val() != '0'){
			flag = flag + validate("manageSocialBenefitDetail_startRuleRemark", true, "numeric", 2, 0);
		}
		if($('.manageSocialBenefitDetail_endRule').val() != '0'){
			flag = flag + validate("manageSocialBenefitDetail_endRuleRemark", true, "numeric", 2, 0);
		}
		if($('.manageSocialBenefitDetail_makeup').val() == '1'){
			flag = flag + validate("manageSocialBenefitDetail_makeupMonth", false, "numeric", 2, 0);
		}
		
		flag = flag + validate("manageSocialBenefitDetail_description", false, "common", 500, 0);
		flag = flag + validate("manageSocialBenefitDetail_status", true, "select", 0, 0);
		
		return flag;
	};
</script>
	