<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder locationHolder = (PagedListHolder) request.getAttribute("locationHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=locationHolder.getCurrentSortClass("locationId")%>">
				<a onclick="submitForm('listlocation_form', null, null, 'locationId', '<%=locationHolder.getNextSortOrder("locationId")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.office.location.id" />
				</a>
			</th>
			<th style="width: 12%" class="header <%=locationHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listlocation_form', null, null, 'nameZH', '<%=locationHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.office.location.name.cn" />
				</a>
			</th>
			<th style="width: 8%" class="header <%=locationHolder.getCurrentSortClass("cityId")%>">
				<a onclick="submitForm('listlocation_form', null, null, 'cityId', '<%=locationHolder.getNextSortOrder("cityId")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.city" />
				</a>
			</th>
			<th style="width: 25%" class="header <%=locationHolder.getCurrentSortClass("addressZH")%>">
				<a onclick="submitForm('listlocation_form', null, null, 'addressZH', '<%=locationHolder.getNextSortOrder("addressZH")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.office.location.address.cn" />
				</a>
			</th>
			<th style="width: 9%" class="header <%=locationHolder.getCurrentSortClass("telephone")%>">
				<a onclick="submitForm('listlocation_form', null, null, 'telephone', '<%=locationHolder.getNextSortOrder("telephone")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.telephone" />
				</a>
			</th>
			<th style="width: 9%" class="header <%=locationHolder.getCurrentSortClass("fax")%>">
				<a onclick="submitForm('listlocation_form', null, null, 'fax', '<%=locationHolder.getNextSortOrder("fax")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.fax" />
				</a>
			</th>
			<th style="width: 6%" class="header <%=locationHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listlocation_form', null, null, 'status', '<%=locationHolder.getNextSortOrder("status")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.status" />
				</a>
			</th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 15%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="locationHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="locationVO" name="locationHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<logic:equal name="locationVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="locationVO" property="locationId"/>" name="chkSelectRow[]" value="<bean:write name="locationVO" property="locationId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('secLocationAction.do?proc=to_objectModify&id=<bean:write name="locationVO" property="encodedId"/>');"><bean:write name="locationVO" property="locationId"/></a>
					</td>
					<td class="left"><bean:write name="locationVO" property="nameZH"/></td>
					<td class="left"><bean:write name="locationVO" property="decodeCity"/></td>
					<td class="left"><bean:write name="locationVO" property="addressZH"/></td>
					<td class="left"><bean:write name="locationVO" property="telephone"/></td>
					<td class="left"><bean:write name="locationVO" property="fax"/></td>
					<td class="left"><bean:write name="locationVO" property="decodeLocationStatus" /></td>
					<td class="left"><bean:write name="locationVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="locationVO" property="decodeModifyDate" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="locationHolder">
		<tfoot>
			<tr class="total">
	  			<td colspan="10" class="left"> 
	  				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="locationHolder" property="holderSize" /></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="locationHolder" property="indexStart" /> - <bean:write name="locationHolder" property="indexEnd" /></label>
	  				<label>&nbsp;&nbsp;<a onclick="submitForm('listlocation_form', null, '<bean:write name="locationHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listlocation_form', null, '<bean:write name="locationHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listlocation_form', null, '<bean:write name="locationHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listlocation_form', null, '<bean:write name="locationHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="locationHolder" property="realPage" />/<bean:write name="locationHolder" property="pageCount" /></label>&nbsp;
	  			</td>					
	    	</tr>
		</tfoot>
	</logic:present>
</table>