<%@page import="com.kan.base.domain.management.ShiftHeaderVO"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table class="table" id="resultTable">
	<thead>
		<tr>
			<th class="header-nosort"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th class="header-nosort" style="width: 20%;"><bean:message bundle="management" key="management.shift.detail.date" /></th>
			<th class="header-nosort" style="width: 80%;"><bean:message bundle="management" key="management.shift.detail.period" /></th>
		</tr>
	</thead>
	<logic:notEqual name="shiftDetailHolder" property="holderSize" value="0">			
		<tbody>
			<logic:iterate id="shiftDetailVO" name="shiftDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="shiftDetailVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="shiftDetailVO" property="encodedId"/>" />
					</td>
					<td>
						<a onclick="to_objectModify_ajax('<bean:write name="shiftDetailVO" property="encodedId" />');">
							<bean:write name="shiftDetailVO" property="shiftDay" />
						</a>
					</td>
					<td><bean:write name="shiftDetailVO" property="decodedShiftPeriod" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>	
	<logic:present name="shiftDetailHolder">
			<tfoot>
			<tr class="total">
				<td  colspan="4" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="shiftDetailHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="shiftDetailHolder" property="indexStart" /> - <bean:write name="shiftDetailHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a onclick="submitForm('listShiftDetail_form', null, '<bean:write name="shiftDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listShiftDetail_form', null, '<bean:write name="shiftDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listShiftDetail_form', null, '<bean:write name="shiftDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listShiftDetail_form', null, '<bean:write name="shiftDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="shiftDetailHolder" property="realPage" />/<bean:write name="shiftDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>