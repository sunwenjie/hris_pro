<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="calendarDetailAction.do?proc=add_object" styleClass="manageCalendarDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="headerId" value="<bean:write name="calendarHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="calendarDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="calendarDetailForm" property="subAction"/>" /> 
		<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.calendar.detail.day.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageCalendarDetail_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.calendar.detail.day.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageCalendarDetail_nameEN" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.calendar.detail.day" /><em> *</em></label> 
				<html:text property="day" maxlength="10" styleClass="Wdate manageCalendarDetail_day" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.calendar.detail.day.type" /><em> *</em></label> 
				<html:select property="dayType" styleClass="manageCalendarDetail_dayType">
					<html:optionsCollection property="dayTypies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li id="changeDay_LI" style="display: none;">
				<label><bean:message bundle="management" key="management.calendar.detail.change.day" /> <em> *</em></label> 
				<html:text property="changeDay" maxlength="10" styleClass="Wdate manageCalendarDetail_changeDay"  onfocus="WdatePicker({lang:'en',dateFmt:'yyyy-MM-dd'})"/>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageCalendarDetail_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageCalendarDetail_status">
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
		    
			flag = flag + validate("manageCalendarDetail_nameZH", true, "common", 100, 0);
			flag = flag + validate("manageCalendarDetail_day", true, "common", 10, 0);
			flag = flag + validate("manageCalendarDetail_dayType", true, "select", 0, 0);
			if($('.manageCalendarDetail_dayType').val() == '1'){
				flag = flag + validate("manageCalendarDetail_changeDay", true, "common", 10, 0);
			}
			flag = flag + validate("manageCalendarDetail_description", false, "common", 500, 0);
			flag = flag + validate("manageCalendarDetail_status", true, "select", 0, 0);
		    
		    return flag;
		};
		
		// detail - 日期类型change事件
		$('.manageCalendarDetail_dayType').change(function(){
			if($(this).val() == '1'){
				$('#changeDay_LI').show();
			}else{
				$('#changeDay_LI').hide();
			}
		});
	})(jQuery);
</script>