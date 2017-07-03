<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="bankTemplateDetailAction?proc=add_object" styleClass="manageBankTemplateDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="bankTemplateHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="templateHeaderId" name="templateHeaderId" value="<bean:write name="bankTemplateHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="templateDetailId" name="templateDetailId" value="<bean:write name="bankTemplateDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="bankTemplateDetailForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.property.name" /><em> *</em></label> 
				<html:text property="propertyName" maxlength="100" styleClass="manageBankTemplateDetail_propertyName" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.column.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageBankTemplateDetail_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.column.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageBankTemplateDetail_nameEN" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.value.type" /><em> *</em></label> 
				<html:select property="valueType" styleClass="manageBankTemplateDetail_valueType">
					<html:optionsCollection property="valueTypes" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>
		</ol>
		<ol id="valueType_for_date" class="auto" style="display: none;">
			<li>
				<label><bean:message bundle="public" key="public.date.format" /></label> 
				<html:select property="datetimeFormat" styleClass="manageBankTemplateDetail_datetimeFormat">
					<html:optionsCollection property="datetimeFormats" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>
		</ol>	
		<ol id="valueType_for_numeric" class="auto" style="display: none;">
			<li>
				<label><bean:message bundle="public" key="public.accuracy" />  <img src="images/tips.png" title="<bean:message bundle="public" key="public.accuracy.tips" />" /> </label> 
				<html:select property="accuracy" styleClass="manageBankTemplateDetail_accuracy">
					<html:optionsCollection property="accuracies" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="public" key="public.round" />  <img src="images/tips.png" title="<bean:message bundle="public" key="public.round.tips" />" /></label> 
				<html:select property="round" styleClass="manageBankTemplateDetail_round">
					<html:optionsCollection property="rounds" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.column.width" /><em> *</em></label> 
				<html:text property="columnWidth" maxlength="3" styleClass="manageBankTemplateDetail_columnWidth" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.column.index" /><em> *</em></label> 
				<html:text property="columnIndex" maxlength="3" styleClass="manageBankTemplateDetail_columnIndex" /> 
			</li>	
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.font.size" /> </label> 
				<html:select property="fontSize" styleClass="manageBankTemplateDetail_fontSize">
					<html:optionsCollection property="fontSizes" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.decode" /></label> 
				<html:select property="isDecoded" styleClass="manageBankTemplateDetail_isDecoded">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 								
			</li>
			<li>
				<label><bean:message bundle="management" key="management.salary.template.detail.align.type" /></label>
				<html:select property="align" styleClass="manageBankTemplateDetail_align">
					<html:optionsCollection property="aligns" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageBankTemplateDetail_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageBankTemplateDetail_status">
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
		    
		    flag = flag + validate('manageBankTemplateDetail_propertyName', true, 'common', 100, 0);
			flag = flag + validate('manageBankTemplateDetail_nameZH', true, 'common', 100, 0);			
			flag = flag + validate('manageBankTemplateDetail_valueType', true, 'select', 0, 0);
			
			// 验证数值
			flag = flag + validate('manageBankTemplateDetail_columnWidth', true, 'numeric', 3, 0);
			flag = flag + validate('manageBankTemplateDetail_columnIndex', true, 'numeric', 3, 0);
			
			flag = flag + validate('manageBankTemplateDetail_status', true, 'select', 0, 0);
			flag = flag + validate('manageBankTemplateDetail_description', false, 'common', 500, 0);
		    
		    return flag;
		};
		
		// 绑定事件 - 数值类型
		$('.manageBankTemplateDetail_valueType').change( function () {
			$('#valueType_for_numeric').hide();
			$('#valueType_for_date').hide();
			if($(this).val() == 1){
				$('#valueType_for_numeric').show();
			}else if ($(this).val() == 3){
				$('#valueType_for_date').show();
			}
		}); 
		
		// 触发事件 -数值类型
		$('.manageBankTemplateDetail_valueType').trigger('change');
		
	})(jQuery);
</script>