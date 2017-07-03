<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.SocialBenefitSolutionDetailAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.management.SocialBenefitSolutionHeaderVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder socialBenefitSolutionDetailHolder = ( PagedListHolder )request.getAttribute( "socialBenefitSolutionDetailHolder" );
	final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO )request.getAttribute( "socialBenefitSolutionHeaderForm" );
%>

<div id="messageWrapper">
	<logic:present name="MESSAGE">
		<logic:present name="MESSAGE">
			<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
				<bean:write name="MESSAGE" />
    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
			</div>
		</logic:present>
	</logic:present>
</div>
<div class="top">
	<logic:empty name="socialBenefitSolutionHeaderForm" property="encodedId">
		<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.add" />" /> 
	</logic:empty>
	<logic:notEmpty name="socialBenefitSolutionHeaderForm" property="encodedId">
		<kan:auth right="modify" action="<%=SocialBenefitSolutionDetailAction.accessAction%>">
			<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.add" />" /> 
		</kan:auth>
	</logic:notEmpty>
	<kan:auth right="list" action="<%=SocialBenefitSolutionDetailAction.accessAction%>">
		<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
	</kan:auth>
</div>
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="headerId" value="<bean:write name="socialBenefitSolutionHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="socialBenefitSolutionHeaderForm" property="subAction"/>" /> 
	<input type="hidden" id="cityIdTemp" name="cityIdTemp" class="location_cityIdTemp" value="<bean:write name="socialBenefitSolutionHeaderForm" property="cityId" />">
	<fieldset>
	<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="management" key="management.sb.solution.header.city" /><em> *</em></label> 
			<html:select property="provinceId" styleClass="location_provinceId" >
				<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
			</html:select>
		</li>
		<li id="getSocialBenefitHeaderByCityId">
			<label><bean:message bundle="management" key="management.sb.solution.header.system" /><em> *</em></label> 
			<select name="sysHeaderId" class="manageSocialBenefitSolutionHeader_sysHeaderId">
				<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
			</select>
		</li>
	</ol>	
	<ol class="auto">
		<li>
			<label><bean:message bundle="management" key="management.sb.solution.header.name.cn" /><em> *</em></label> 
			<html:text property="nameZH" maxlength="100" styleClass="manageSocialBenefitSolutionHeader_nameZH" /> 
		</li>
		<li>
			<label><bean:message bundle="management" key="management.sb.solution.header.name.en" /></label> 
			<html:text property="nameEN" maxlength="100" styleClass="manageSocialBenefitSolutionHeader_nameEN" /> 
		</li>
	</ol>	
	<ol class="auto">	
		<li>
			<label><bean:message bundle="management" key="management.sb.solution.header.type" /><em> *</em> </label> 
			<html:select property="sbType" styleClass="manageSocialBenefitSolutionHeader_sbType">
				<html:optionsCollection property="sbTypes" value="mappingId" label="mappingValue" />
			</html:select>
		</li>
	</ol>	
	<ol class="auto">	
		<li>
			<label><bean:message bundle="public" key="public.description" /></label> 
			<html:textarea property="description" styleClass="manageSocialBenefitSolutionHeader_description"></html:textarea>
		</li>
		<li>
			<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
			<html:select property="status" styleClass="manageSocialBenefitSolutionHeader_status">
				<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
			</html:select>
		</li>	
	</ol>
	<ol class="auto">
		<li><a id="moreInfo_LINK"><bean:message bundle="public" key="link.more.info" /></a></li>
	</ol>
	<div id="moreInfo" style="display:none; border: 1px solid rgb(170, 170, 170); border-top-left-radius: 3px; border-top-right-radius: 3px; border-bottom-right-radius: 3px; border-bottom-left-radius: 3px; margin: 2px 10px 20px; padding: 10px 10px 0px;">
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.effective" /></label> 
				<html:select property="effective" styleClass="manageSocialBenefitSolutionHeader_effective">
					<html:optionsCollection property="effectives" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.attribute" /><em> *</em></label> 
				<html:select property="attribute" styleClass="manageSocialBenefitSolutionHeader_attribute">
					<html:optionsCollection property="attributes" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>
		<ol class="auto" id="dateLimit">	
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.start.date.limit" /><em> *</em></label> 
				<html:select property="startDateLimit" styleClass="manageSocialBenefitSolutionHeader_startDateLimit">
					<html:optionsCollection property="dates" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.end.date.limit" /><em> *</em></label> 
				<html:select property="endDateLimit" styleClass="manageSocialBenefitSolutionHeader_endDateLimit">
					<html:optionsCollection property="dates" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.start.rule" /></label> 
				<html:select property="startRule" styleClass="manageSocialBenefitSolutionHeader_startRule">
					<html:optionsCollection property="rules" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.start.rule.remark" /> <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.rule.remark.tips" />" /></label> 
				<html:text property="startRuleRemark" maxlength="12" styleClass="manageSocialBenefitSolutionHeader_startRuleRemark" />
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.end.rule" /></label> 
				<html:select property="endRule" styleClass="manageSocialBenefitSolutionHeader_endRule">
					<html:optionsCollection property="rules" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.end.rule.remark" /> <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.rule.remark.tips" />" /></label> 
				<html:text property="endRuleRemark" maxlength="12" styleClass="manageSocialBenefitSolutionHeader_endRuleRemark" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sb.solution.header.personal.burden" /></label> 
				<html:select property="personalSBBurden" styleClass="manageSocialBenefitSolutionHeader_personalSBBurden">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</div>
	<div id="tab"> 
		<div class="tabMenu"> 
			<ul> 
				<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first"><bean:message bundle="public" key="menu.table.title.attachment" /></li> 
			</ul>	
		</div>
		<div class="tabContent"> 
			<div id="tabContent1" class="kantab kanThinkingCombo" >	
				<span><a name="uploadAttachment" id="uploadAttachment" class="kanhandle" onclick="uploadObject.submit();"><bean:message bundle="public" key="link.upload.attachment" /></a></span>	
				<ol id="attachmentsOL" class="auto">
					<%= AttachmentRender.getAttachments(request, socialBenefitSolutionHeaderVO.getAttachmentArray(), null) %>
				</ol>
			</div>
		</div>
	</div>	
</fieldset>
