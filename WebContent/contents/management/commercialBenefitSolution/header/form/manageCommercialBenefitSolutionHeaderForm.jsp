<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO" %>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = (CommercialBenefitSolutionHeaderVO)request.getAttribute("commercialBenefitSolutionHeaderForm");
%>

<html:form action="commercialBenefitSolutionHeaderAction.do?proc=add_object" styleClass="manageCommercialBenefitSolutionHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="commercialBenefitSolutionHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="commercialBenefitSolutionHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageCommercialBenefitSolutionHeader_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageCommercialBenefitSolutionHeader_nameEN" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.header.start.date" /><em> *</em></label> 
				<input type="text" id="validFrom" name="validFrom" readonly="readonly" class="manageCommercialBenefitSolutionHeader_validFrom Wdate" value="<bean:write name="commercialBenefitSolutionHeaderForm" property="validFrom"/>" 
				onfocus="WdatePicker({onpicked:function(){validEnd.focus();},maxDate:'#F{$dp.$D(\'validEnd\')||\'2030-10-01\'}'})"/>
			</li>	
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.header.end.date" /><em> *</em></label> 
				<input type="text" id="validEnd" name="validEnd" readonly="readonly" class="manageCommercialBenefitSolutionHeader_validEnd Wdate" value="<bean:write name="commercialBenefitSolutionHeaderForm" property="validEnd"/>" 
				onfocus="WdatePicker({minDate:'#F{$dp.$D(\'validFrom\')}',maxDate:'2030-10-01'})">
			</li>
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.header.calculate.type" /><em> *</em></label> 
				<html:select property="calculateType" styleClass="manageCommercialBenefitSolutionHeader_calculateType">
					<html:optionsCollection property="calculateTypies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto">		
			<li>
				<label><bean:message bundle="public" key="public.accuracy" /></label> 
				<html:select property="accuracy" styleClass="manageCommercialBenefitSolutionHeader_accuracy">
					<html:optionsCollection property="accuracys" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="public" key="public.round" /></label> 
				<html:select property="round" styleClass="manageCommercialBenefitSolutionHeader_round">
					<html:optionsCollection property="rounds" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>
		<ol class="auto">		
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.header.free.short.of.month" /></label> 
				<html:select property="freeShortOfMonth" styleClass="manageCommercialBenefitSolutionHeader_freeShortOfMonth">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li id="chargeFullMonthLI">
				<label><bean:message bundle="management" key="management.cb.solution.header.charge.full.month" /></label> 
				<html:select property="chargeFullMonth" styleClass="manageCommercialBenefitSolutionHeader_chargeFullMonth">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>
		<ol class="auto">		
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageCommercialBenefitSolutionHeader_description"></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageCommercialBenefitSolutionHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
		</ol>	
		<div id="tab"> 
			<div class="tabMenu"> 
				<ul> 
					<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first"><bean:message bundle="public" key="menu.table.title.attachment" /> (<span id="numberOfAttachment"><bean:write name="listAttachmentCount"/></span>)</li> 
				</ul>	
			</div>
			<div class="tabContent"> 
				<div id="tabContent1" class="kantab kanThinkingCombo" >	
					<span><a name="uploadAttachment" id="uploadAttachment" class="kanhandle" style="display: none;" onclick="uploadObject.submit();"><bean:message bundle="public" key="link.upload.attachment" /></a></span>	
					<div id="attachmentsDiv">
						<ol id="attachmentsOL" class="auto">
							<%= AttachmentRender.getAttachments(request, commercialBenefitSolutionHeaderVO.getAttachmentArray(), null) %>
						</ol>
					</div>
				</div>
			</div>
		</div>
	</fieldset>
</html:form>


<script type="text/javascript">
	(function($) {
		
		// JS验证
		validate_manage_primary_form = function() {
		    var flag = 0;
		    
		    flag = flag + validate("manageCommercialBenefitSolutionHeader_nameZH", true, "common", 100, 0);
			flag = flag + validate("manageCommercialBenefitSolutionHeader_validFrom", true, "common", 10, 0);
			flag = flag + validate("manageCommercialBenefitSolutionHeader_validEnd", true, "common", 10, 0);
			flag = flag + validate("manageCommercialBenefitSolutionHeader_calculateType", true, "select", 0, 0);
			flag = flag + validate("manageCommercialBenefitSolutionHeader_description", false, "common", 500, 0);
			flag = flag + validate("manageCommercialBenefitSolutionHeader_status", true, "select", 0, 0);
		    
		    return flag;
		};
		
		 //绑定 不全月免费 和 按月计费 事件
		 $('.manageCommercialBenefitSolutionHeader_freeShortOfMonth').change(function(){
			 if( $(this).val() == 2){
				 $('#chargeFullMonthLI').show();
			 }else{
				 $('.manageCommercialBenefitSolutionHeader_chargeFullMonth').val('0');
				 $('#chargeFullMonthLI').hide();
			 }
		 });
		 $('.manageCommercialBenefitSolutionHeader_freeShortOfMonth').trigger('change');
	})(jQuery);
</script>
