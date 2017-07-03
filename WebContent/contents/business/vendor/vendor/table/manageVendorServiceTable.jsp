<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="public" key="public.city" />
			</th>
			<th style="width: 40%" class="header-nosort">
				<bean:message bundle="business" key="business.vendor.service.sb.type" />
			</th>
			<th style="width: 30%" class="header-nosort">
				<bean:message bundle="business" key="business.vendor.service.scope" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="business" key="business.vendor.service.fee" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
		</tr>
	</thead>
		<logic:notEmpty name="pagedListHolder">
			<tbody id="mytab">
				<logic:iterate id="vendorServiceVO" name="pagedListHolder" property="source" indexId="number">
					<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
						<td>
							<input type="hidden" class="city_service" value="<bean:write name="vendorServiceVO" property="cityId" />_<bean:write name="vendorServiceVO" property="sbHeaderId" />_<bean:write name="vendorServiceVO" property="serviceIds" />" />
							<logic:equal name="vendorServiceVO" property="extended" value="2">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="vendorServiceVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="vendorServiceVO" property="encodedId"/>" />
							</logic:equal>
						</td>
						<td class="left"><bean:write name="vendorServiceVO" property="decodeCityId" /></td>
						<td class="left"><bean:write name="vendorServiceVO" property="decodeSBs" /></td>
						<td class="left">
							<logic:empty name='noLink'>
								<a id="vendorServiceLink" onclick="to_objectModify('<bean:write name="vendorServiceVO" property="encodedId" />','<bean:write name="vendorServiceVO" property="cityId" />','<bean:write name="vendorServiceVO" property="sbHeaderId" />','<bean:write name="vendorServiceVO" property="serviceIds" />')">
								<bean:write name="vendorServiceVO" property="decodeServiceIds" />
								</a>
							</logic:empty>
							<logic:notEmpty name='noLink'>
								<bean:write name="vendorServiceVO" property="decodeServiceIds" />
							</logic:notEmpty>
						</td>
						<td class="right">гд<bean:write name="vendorServiceVO" property="serviceFee" /></td>
						<td class="left"><bean:write name="vendorServiceVO" property="decodeStatus" /></td>
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEmpty>	
		<logic:present name="pagedListHolder">
			<tfoot>
				<tr class="total">
				  	<td  colspan="7" class="left"> 
					  	<label>&nbsp;<bean:message bundle="public" key="page.total" />г║ <bean:write name="pagedListHolder" property="holderSize" /></label>
					  	&nbsp;
					  	&nbsp;
					</td>					
			   </tr>
			</tfoot>
		</logic:present>
</table>
<div class="bottom"><p></p></div>