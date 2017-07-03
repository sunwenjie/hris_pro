<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@page import="com.kan.base.domain.system.SocialBenefitHeaderVO"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final SocialBenefitHeaderVO socialBenefitHeaderVO = ( SocialBenefitHeaderVO )request.getAttribute( "socialBenefitHeaderForm" );
	String[] attachmentArray = null;
	if( socialBenefitHeaderVO != null )
	{
	   attachmentArray = socialBenefitHeaderVO.getAttachmentArray();
	}
%>

<html:form action="socialBenefitHeaderAction.do?proc=add_object" styleClass="manageSocialBenefitHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="socialBenefitHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="socialBenefitHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="system" key="system.sb.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageSocialBenefitHeader_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageSocialBenefitHeader_nameEN" /> 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.city" /><em> *</em></label> 
				<html:hidden property="cityIdTemp" styleClass="temp_cityId" styleId="temp_cityId" />
				<html:select property="provinceId" styleClass="sb_provinceId" >
					<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.adjustment.month" /></label>
				<html:select property="adjustMonth" styleClass="manageSocialBenefitHeader_adjustMonth">
					<html:optionsCollection property="adjustMonths" value="mappingId" label="mappingValue" />
				</html:select>	 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="system" key="system.sb.header.residency.type" /><em> *</em></label>
				<html:hidden property="residency" styleClass="manageSocialBenefitHeader_residency" /> 
				<div style="width: 215px;">
					<logic:present name="socialBenefitHeaderForm" property="residencys">
						<logic:iterate id="residency" name="socialBenefitHeaderForm" property="residencys" indexId="index">
							<logic:notEqual name="index" value="0">
								<label class='auto residency_<bean:write name="residency" property="mappingId"/>'>
									<input type="checkbox" name="residencyArray" id="ck_residency_<bean:write name="residency" property="mappingId"/>" value="<bean:write name="residency" property="mappingId"/>"/>
								 	<bean:write name="residency" property="mappingValue" />
								</label>
							</logic:notEqual>	
						</logic:iterate>
					</logic:present>
				</div>
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.term.month" /></label>
				<html:hidden property="termMonth" styleClass="manageSocialBenefitHeader_termMonth" />
				<div style="width: 215px;">
				<logic:present name="socialBenefitHeaderForm" property="adjustMonths">
					<logic:iterate id="adjustMonth" name="socialBenefitHeaderForm" property="adjustMonths" indexId="index">
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
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="system" key="system.sb.header.effective" /></label> 
				<html:select property="effective" styleClass="manageSocialBenefitHeader_effective">
					<html:optionsCollection property="effectives" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.attribute" /><em> *</em></label> 
				<html:select property="attribute" styleClass="manageSocialBenefitHeader_attribute">
					<html:optionsCollection property="attributes" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="system" key="system.sb.header.start.date.limit" /><em> *</em></label> 
				<html:select property="startDateLimit" styleClass="manageSocialBenefitHeader_startDateLimit">
					<html:optionsCollection property="dates" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.end.date.limit" /><em> *</em></label> 
				<html:select property="endDateLimit" styleClass="manageSocialBenefitHeader_endDateLimit">
					<html:optionsCollection property="dates" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="system" key="system.sb.header.start.rule" /></label> 
				<html:select property="startRule" styleClass="manageSocialBenefitHeader_startRule">
					<html:optionsCollection property="rules" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.start.rule.remark" /><img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.rule.remark.tips" />" /></label> 
				<html:text property="startRuleRemark" maxlength="12" styleClass="manageSocialBenefitHeader_startRuleRemark" />
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.end.rule" /></label> 
				<html:select property="endRule" styleClass="manageSocialBenefitHeader_endRule">
					<html:optionsCollection property="rules" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.end.rule.remark" /><img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.rule.remark.tips" />" /></label> 
				<html:text property="endRuleRemark" maxlength="12" styleClass="manageSocialBenefitHeader_endRuleRemark" /> 
			</li>
		</ol>
		<ol class="auto">		
			<li>
				<label><bean:message bundle="system" key="system.sb.header.makeup" /></label> 
				<html:select property="makeup" styleClass="manageSocialBenefitHeader_makeup">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>
		<ol class="auto" style="display: none;" id="isMakeup">	
			<li>
				<label><bean:message bundle="system" key="system.sb.header.makeup.month" /><img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.makeup.month.tips" />" /></label> 
				<html:select property="makeupMonth" styleClass="manageSocialBenefitHeader_makeupMonth" onchange="isMakeup();">
					<html:optionsCollection property="adjustMonths" value="mappingId" label="mappingValue" />
				</html:select>	 
			</li>
			<li>
				<label><bean:message bundle="system" key="system.sb.header.makeup.cross.year" /></label> 
				<html:select property="makeupCrossYear" styleClass="manageSocialBenefitHeader_makeupCrossYear">
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
				<html:select property="round" styleClass="manageSocialBenefitHeader_round">
					<html:optionsCollection property="rounds" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageSocialBenefitHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto">		
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageSocialBenefitHeader_description"></html:textarea>
			</li>
		</ol>
		<div id="tab"> 
			<div class="tabMenu"> 
				<ul> 
					<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first"><bean:message bundle="public" key="menu.table.title.attachment" /></li> 
				</ul>	
			</div>
			<div class="tabContent"> 
				<div id="tabContent1" class="kantab kanThinkingCombo" >	
					<logic:equal value="1" name="PAGE_ACCOUNT_ID">
						<span><a name="uploadAttachment" id="uploadAttachment" onclick="uploadObject.submit();"><bean:message bundle="public" key="link.upload.attachment" /></a></span>
					</logic:equal>	
					<ol id="attachmentsOL" class="auto">
						<%= AttachmentRender.getAttachments(request, attachmentArray, null) %>
					</ol>
				</div>
			</div>
		</div>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// JS验证 header form 
		validate_manage_primary_form = function() {
		    var flag = 0;
			flag = flag + validate("manageSocialBenefitHeader_nameZH", true, "common", 100, 0);
			flag = flag + validate("manageSocialBenefitHeader_nameEN", false, "common", 100, 0);
// 			flag = flag + validate("manageSocialBenefitHeader_residency", true, "select", 0, 0);
			flag = flag + validate("sb_provinceId", true, "select", 0, 0);
			
			flag = flag + validateResidency();
			
			if( $('.sb_provinceId').val() != '0' ){
				flag = flag + validate("cityId", true, "select", 0, 0);
			}
		
			// “社保公积金所属月”，“申报开始日期”，“申报结束日期”必填；
			flag = flag + validate("manageSocialBenefitHeader_attribute", true, "select", 0, 0);
			flag = flag + validate("manageSocialBenefitHeader_startDateLimit", true, "select", 0, 0);
			flag = flag + validate("manageSocialBenefitHeader_endDateLimit", true, "select", 0, 0);
			// 当“缴纳规则”不为请选择时，“规则参数”必填；“规则参数”验证整数；
			if($('.manageSocialBenefitHeader_startRule').val() != '0'){
				flag = flag + validate("manageSocialBenefitHeader_startRuleRemark", true, "numeric", 2, 0);
			}else{
				flag = flag + validate("manageSocialBenefitHeader_startRuleRemark", false, "numeric", 2, 0);
			}
			if($('.manageSocialBenefitHeader_endRule').val() != '0'){
				flag = flag + validate("manageSocialBenefitHeader_endRuleRemark", true, "numeric", 2, 0);
			}else{
				flag = flag + validate("manageSocialBenefitHeader_endRuleRemark", false, "numeric", 2, 0);
			}
			
			if($('.manageSocialBenefitHeader_makeup').val() == '1'){
				flag = flag + validate("manageSocialBenefitHeader_makeupMonth", false, "numeric", 2, 0);
			}
		
			flag = flag + validate("manageSocialBenefitHeader_description", false, "common", 500, 0);
			flag = flag + validate("manageSocialBenefitHeader_status", true, "select", 0, 0);
		    
		    return flag;
		};
		
		// 户籍类型点击事件
		$('input[id^="ck_residency_"]').not('#ck_residency_7').click( function(){
			var residencySize = $('input[id^="ck_residency_"]').length - 1;
			var residencyAllChecked = residencySize == $('input[id^="ck_residency_"]').not('#ck_residency_7').filter(':checked').length;
			$('input[id^="ck_residency_7"]').attr('checked', residencyAllChecked);
		});
		
		// 户籍类型（全适用）点击事件 
		$('input#ck_residency_7').click( function(){ 
			$('input[id^="ck_residency_"]').not('#ck_residency_7').attr('checked', $(this).is(':checked'));
		});
		
		// 新增时默认选中所有 
		if($('.manageSocialBenefitHeader_form input#subAction').val() == 'createObject'){  
			$('input[id^="ck_termMonth_"]').attr('checked',true);
		}else{
			if($('.manageSocialBenefitHeader_termMonth').val() != ''){
				var termMonthArray = $('.manageSocialBenefitHeader_termMonth').val().replace('{','').replace('}','').split(',');
				for( var month in termMonthArray){
					$('#ck_termMonth_' + termMonthArray[month]).attr('checked',true);
				}
			}
		}
		
		// 户籍类型选中
		if($('.manageSocialBenefitHeader_residency').val() != ''){
			var residencyArray = $('.manageSocialBenefitHeader_residency').val().replace('{','').replace('}','').split(',');
			for( var r in residencyArray){
				$('#ck_residency_' + residencyArray[r]).attr('checked',true);
			}
		}
		
		// 是否可以补缴 change事件
		$('.manageSocialBenefitHeader_form select.manageSocialBenefitHeader_makeup').change(function(){
			if($(this).val() == '1'){
				$('.manageSocialBenefitHeader_form #isMakeup').show();
			}else{
				$('.manageSocialBenefitHeader_form #isMakeup').hide();
			}
		});
		
	    // 触发是否可以补缴change事件
	    $('.manageSocialBenefitHeader_form select.manageSocialBenefitHeader_makeup').trigger('change');
		var uploadObject = createUploadObject('uploadAttachment', 'all', '<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_SB %>/<bean:write name="accountId" />/<bean:write name="username" />/');
	})(jQuery);
	 
    function validateResidency(){
    	cleanError('manageSocialBenefitHeader_residency');
    	
    	var flag = 1;
		$('input[id^="ck_residency_"]').each(function(){
			if($(this).is(':checked')){
				flag = 0;
				return false;
			}
		});
		
		if(flag == 1){
			addError('manageSocialBenefitHeader_residency', '请选择');
		}
		return flag;
    };
</script>
