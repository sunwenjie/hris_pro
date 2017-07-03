<%@page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html:form action="vendorServiceAction.do?proc=add_object" styleClass="manageVendorService_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="subAction" class="subAction" id="subAction" value="<bean:write name="vendorServiceForm" property="subAction" />" />
	<input type="hidden" name="serviceId" class="manageVendorService_serviceId" id="manageVendorService_vendorId" value="<bean:write name="vendorServiceForm" property="encodedId" />" />
	<input type="hidden" name="id" class="manageVendorService_vendorId" id="manageVendorService_vendorId" value="" />
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.city" /><em> *</em></label> 
				<input type="hidden" name="cityServiceId" class="cityServiceId"/>
				<html:select property="provinceId" styleClass="manageVendorCityService_provinceId1">
					<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
			<li>
				<label><bean:message bundle="business" key="business.vendor.service.sb.type" /></label> 
				<select name="sbHeaderId" class="manageVendorService_sbHeaderId">
					<option value="0"><%=KANUtil.getEmptyMappingVO(	request.getLocale() ).getMappingValue() %></option>
				</select>
			</li>
			<li>
				<label><bean:message bundle="business" key="business.vendor.service.scope" /></label>
				<input type="hidden" name="serviceIds" id="serviceIds" class="serviceIds"/>
				<div id="service_div" class="service_div auto" style="width: 215px;">
					<logic:present name="vendorServiceForm" property="serviceContents">
						<logic:iterate id="c" name="vendorServiceForm" property="serviceContents" >								
							<label class='auto serviceType_<bean:write name="c" property="mappingId"/>'>
								<input type="checkbox" name="serviceArray" class="ckb_service_<bean:write name="c" property="mappingId"/>" id="ckb_service_<bean:write name="c" property="mappingId"/>" value="<bean:write name="c" property="mappingId" />" />
								<bean:write name="c" property="mappingValue" />
							</label>
							<%
								if( request.getLocale().getLanguage().equalsIgnoreCase( "en" ) )
								{
								   out.print( "<br/>" );
								}
							%>
						</logic:iterate>	
					</logic:present>
				</div>
			</li>
			<li>
				<label><bean:message bundle="business" key="business.vendor.service.fee" /><em> *</em></label>
				<html:text property="serviceFee" maxlength="10" styleClass="manageVendorService_serviceFee" />
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" cols="3" styleClass="manageVendorService_description"></html:textarea>
			</li>																
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageVendorService_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>
<div class="bottom"><p></p></div>
