<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="systemCountry" class="box toggableForm">
		<div class="head">
			<label>添加国家</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<html:form action="countryAction.do?proc=add_object" styleClass="country_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="countryId" name="countryId" value="<bean:write name="countryForm" property="encodedId" />" />
				<input type="hidden" name="subAction" id="subAction" value="createObject" />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>国家名称（中文）<em> *</em></label> 
							<html:text property="countryNameZH" maxlength="50" styleClass="country_countryNameZH" /> 
						</li>
						<li>
							<label>国家名称（英文）<em> *</em></label> 
							<html:text property="countryNameEN" maxlength="50" styleClass="country_countryNameEN" /> 
						</li>
						<li>
							<label>国家编号<em> *</em></label> 
							<html:text property="countryNumber" maxlength="3" styleClass="country_countryNumber" />
						</li>
						<li>
							<label>国家编码（简写）<em> *</em></label> 
							<html:text property="countryCode" maxlength="2" styleClass="country_countryCode" />
						</li>
						<li>
							<label>国家编码（ISO3）<em> *</em></label> 
							<html:text property="countryISO3" maxlength="3" styleClass="country_countryISO3" />
						</li>
						<li>
							<label>状态  <em>*</em></label> 
							<html:select property="status" styleClass="country_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		$('#menu_system_Modules').addClass('current');			
		$('#menu_system_City').addClass('selected');
		
		$("#btnEdit").click( function () { 
			var flag = 0;
			
			flag = flag + validate("country_countryNameZH", true, "common", 50, 0);
			flag = flag + validate("country_countryNameEN", true, "common", 50, 0);
			flag = flag + validate("country_countryNumber", true, "numeric", 3, 0);
			flag = flag + validate("country_countryCode", true, "character", 2, 0);
			flag = flag + validate("country_countryISO3", true, "character", 3, 0);
			flag = flag + validate("country_status", true, "common", 0, 0);
			
			if(flag == 0){
				$(".country_form").submit();
			}
		});
		
		$("#btnCancel").click( function () {
			back();
		});
	})(jQuery);
</script>
