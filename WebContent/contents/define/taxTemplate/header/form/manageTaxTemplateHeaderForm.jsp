<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="taxTemplateHeaderAction.do?proc=add_object" styleClass="manageTaxTemplateHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="cityIdTemp" name="cityIdTemp" class="taxTemplateHeader_cityIdTemp" value="<bean:write name='taxTemplateHeaderForm' property='cityId' />">
	<input type="hidden" id="id" name="id" value="<bean:write name="taxTemplateHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="taxTemplateHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.province.city" /><em> *</em></label> 
				<html:select property="provinceId" styleClass="manageTaxTemplateHeader_provinceId" >
					<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.tax.template.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageTaxTemplateHeader_nameZH" /> 					
			</li>
			<li>
				<label><bean:message bundle="management" key="management.tax.template.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageTaxTemplateHeader_nameEN" />
			</li>
		</ol>	
		<ol class="auto">					
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" cols="3" styleClass="manageTaxTemplateHeader_description"></html:textarea>
			</li>																
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageTaxTemplateHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// JS验证
		validate_manage_primary_form = function() {
		    var flag = 0;
		    
		    flag = flag + validate('manageTaxTemplateHeader_provinceId', true, 'select', 0, 0); 
		    flag = flag + validate('cityId', true, 'select', 0, 0); 
		    
			flag = flag + validate('manageTaxTemplateHeader_taxId', true, 'select', 0, 0); 
			flag = flag + validate('manageTaxTemplateHeader_nameZH', true, 'common', 100, 0);			
			flag = flag + validate('manageTaxTemplateHeader_status', true, 'select', 0, 0);
			flag = flag + validate('manageTaxTemplateHeader_description', false, 'common', 500, 0);
		    return flag;
		};

		// 初始化省份控件
		provinceChange('manageTaxTemplateHeader_provinceId', 'viewObject', $('.taxTemplateHeader_cityIdTemp').val(), 'cityId');
		
		// 绑定省Change事件
		$('.manageTaxTemplateHeader_provinceId').change( function () { 
			cleanError('cityId');
			provinceChange('manageTaxTemplateHeader_provinceId', 'modifyObject', 0, 'cityId');
		});
	})(jQuery);
</script>