<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="sickLeaveSalaryDetailAction.do?proc=add_object" styleClass="managesickLeaveSalaryDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="sickLeaveSalaryHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="sickLeaveSalaryDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="sickLeaveSalaryDetailForm" property="subAction"/>" /> 
		<fieldset>
 		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">		
			<li>
				<label><bean:message bundle="management" key="management.sick.leave.detail.range.from" /><em> *</em></label> 
				<html:select property="rangeFrom" styleClass="managesickLeaveSalaryDetail_rangeFrom">
					<html:optionsCollection property="rangeFroms" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sick.leave.detail.range.to" /></label> 
				<html:select property="rangeTo" styleClass="managesickLeaveSalaryDetail_rangeTo">
					<html:optionsCollection property="rangeTos" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sick.leave.detail.percentage" />  <img src="images/tips.png" title="<bean:message bundle="management" key="management.sick.leave.detail.percentage.tips" />"><em> *</em></label> 
				<html:text property="percentage" maxlength="10" styleClass="managesickLeaveSalaryDetail_percentage" /> 
			</li>
			<li >
				<label><bean:message bundle="management" key="management.sick.leave.detail.deduct" /></label> 
				<html:text property="deduct" maxlength="10" styleClass="managesickLeaveSalaryDetail_deduct" /> 
			</li>
			<li style="display:none">
				<label><bean:message bundle="management" key="management.sick.leave.detail.fix" /></label> 
				<html:text property="fix" maxlength="10" styleClass="managesickLeaveSalaryDetail_fix" /> 
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="managesickLeaveSalaryDetail_description" ></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="managesickLeaveSalaryDetail_status">
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
			flag = flag + validate("managesickLeaveSalaryDetail_rangeFrom", false, "select", 0, 0);
			flag = flag + validate("managesickLeaveSalaryDetail_status", true, "select", 0, 0);
			flag = flag + validate("managesickLeaveSalaryDetail_percentage", true, "numeric", 0,0,100,0);
			flag = flag + validate("managesickLeaveSalaryDetail_fix", false, "numeric", 0, 0, 0, 0);
			var rangeTo=$('.managesickLeaveSalaryDetail_rangeTo').val();
			var rangeFrom=$('.managesickLeaveSalaryDetail_rangeFrom').val();
			if($('.managesickLeaveSalaryDetail_rangeTo').val()!="0"&&Number(rangeTo)<Number(rangeFrom)){
				$('.managesickLeaveSalaryDetail_rangeTo').after('<label class="error ' + 'managesickLeaveSalaryDetail_rangeTo' +'_error"">&#8226; ' + '工作月数（至）不能大于 工作月数（从）' + '</label>');
				flag=1;
			}
			var value=$('.managesickLeaveSalaryDetail_deduct').val();
			var re =  /^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/; 
			if (!re.test(value)){
					cleanError('managesickLeaveSalaryDetail_deduct');
					$('.managesickLeaveSalaryDetail_deduct').after('<label class="error managesickLeaveSalaryDetail_deduct_error">&#8226; ' + '请输入数字' + '</label>');
				flag=1;
			}else if(value==''){
				$('.managesickLeaveSalaryDetail_deduct').val('0');
			}
		    return flag;
		};
	})(jQuery);	
</script>