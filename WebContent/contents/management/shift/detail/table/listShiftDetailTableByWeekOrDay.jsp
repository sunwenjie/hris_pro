<%@page import="com.kan.base.domain.management.ShiftHeaderVO"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder shiftDetailHolder = ( PagedListHolder )request.getAttribute( "shiftDetailHolder" );
	
	final String width = String.valueOf( ( 100 / Integer.valueOf( shiftDetailHolder.getHolderSize() ) ));
%>

<table class="table" id="resultTable">
	<thead>
		<tr>
			<logic:notEqual name="shiftDetailHolder" property="holderSize" value="0">
				<logic:iterate id="shiftDetailVO" name="shiftDetailHolder" property="source" indexId="number">
					<th style="width: <%=width %>%" class="header-nosort">
						<input type="checkbox" id="chk_all_<bean:write name="shiftDetailVO" property="dayIndex" />" value="<bean:write name="shiftDetailVO" property="dayIndex" />" />
						<%
							if( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ){
						%>
							 <bean:write name="shiftDetailVO" property="nameZH" /> 
					    <%} else {%>
							 <bean:write name="shiftDetailVO" property="nameEN" /> 
						<%} %>
					</th>
				</logic:iterate>
			</logic:notEqual>
		</tr>
	</thead>			
	<tbody>
		<logic:notEqual name="shiftDetailHolder" property="holderSize" value="0">
			<tr>
				<logic:iterate id="shiftDetailVO" name="shiftDetailHolder" property="source" indexId="index">
					<td class="left">
						<logic:iterate id="shiftPeriod" name="shiftHeaderForm" property="shiftPeriods" indexId="number">
							<input type="checkbox" name="periodArray<%=index+1 %>" id="chk_<bean:write name="shiftDetailVO" property="dayIndex" />_<%=number+1 %>" name="shiftPeriodArray" value="<%=number + 1%>" />
							<label><bean:write name="shiftPeriod" property="mappingValue" /><br/></label>
						</logic:iterate>	
						<logic:notEmpty name="shiftDetailVO" property="shiftPeriod">
							<input type="hidden" id="shiftPeriod_<bean:write name="shiftDetailVO" property="dayIndex" />" value="<bean:write name="shiftDetailVO" property="shiftPeriod" />">
						</logic:notEmpty>
					</td>
				</logic:iterate>
			</tr>
		</logic:notEqual>
	</tbody>
	<logic:present name="shiftDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="<%=shiftDetailHolder.getHolderSize() %>" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="shiftDetailHolder" property="holderSize" /></label>
					&nbsp;
					&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>