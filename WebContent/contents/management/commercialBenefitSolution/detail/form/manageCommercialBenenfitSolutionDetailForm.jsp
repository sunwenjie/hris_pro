<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="commercialBenefitSolutionDetailAction.do?proc=add_object" styleClass="manageCommercialBenefitSolutionDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="commercialBenefitSolutionHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="commercialBenefitSolutionDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="commercialBenefitSolutionDetailForm" property="subAction"/>" /> 
		<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.detail.item" /><em> *</em></label> 
				<html:select property="itemId" styleClass="manageCommercialBenefitSolutionDetail_itemId">
					<html:optionsCollection property="cbItems" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto">		
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.detail.purchase.cost" /></label> 
				<html:text property="purchaseCost" maxlength="10" styleClass="manageCommercialBenefitSolutionDetail_purchaseCost" /> 
			</li>
			<logic:equal name="role" value="1">
				<li>
					<label><bean:message bundle="management" key="management.cb.solution.detail.sales.cost" /></label> 
					<html:text property="salesCost" maxlength="10" styleClass="manageCommercialBenefitSolutionDetail_salesCost" /> 
				</li>
				<li>
					<label><bean:message bundle="management" key="management.cb.solution.detail.sales.price" /><em> *</em></label> 
					<html:text property="salesPrice" maxlength="10" styleClass="manageCommercialBenefitSolutionDetail_salesPrice" /> 
				</li>
			</logic:equal>
			<li>
				<label><bean:message bundle="management" key="management.cb.solution.detail.calculate.type" /><em> *</em></label> 
				<html:select property="calculateType" styleClass="manageCommercialBenefitSolutionDetail_calculateType">
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
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageCommercialBenefitSolutionDetail_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageCommercialBenefitSolutionDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// Detail JS验证
		validate_manage_secondary_form = function() {
		    var flag = 0;
		    
		    flag = flag + validate("manageCommercialBenefitSolutionDetail_itemId", true, "select", 0, 0);
			flag = flag + validate("manageCommercialBenefitSolutionDetail_purchaseCost", false, "currency", 10, 0);
			if( '<bean:write name="commercialBenefitSolutionHeaderForm" property="role"/>' == 1){
				flag = flag + validate("manageCommercialBenefitSolutionDetail_salesCost", false, "currency", 10, 0);
				flag = flag + validate("manageCommercialBenefitSolutionDetail_salesPrice", true, "currency", 10, 0);
			}
			flag = flag + validate("manageCommercialBenefitSolutionDetail_calculateType", true, "select", 0, 0);
			flag = flag + validate("manageCommercialBenefitSolutionDetail_description", false, "common", 500, 0);
			flag = flag + validate("manageCommercialBenefitSolutionDetail_status", true, "select", 0, 0);
		    
		    return flag;
		};
		
		// 科目change事件
		$('.manageCommercialBenefitSolutionDetail_itemId').change(function(){
			var flag = true;
			$('input[id^="hiddenItem"]').each(function(i) {
	    		if($(this).val() ==  $('.manageCommercialBenefitSolutionDetail_itemId').val()){    
	    			alert('该科目已经存在，请重新选择！');
					$('.manageCommercialBenefitSolutionDetail_itemId').val('0');
					flag = false;
					return;
	    		}	
	    	});
		});
	})(jQuery);
</script>