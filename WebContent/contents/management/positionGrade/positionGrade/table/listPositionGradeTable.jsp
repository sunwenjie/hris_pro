<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder positionGradeHolder = ( PagedListHolder ) request.getAttribute( "positionGradeHolder" );
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 30%" class="header  <%=positionGradeHolder.getCurrentSortClass("gradeNameZH")%>">
				<a onclick="submitForm('listPositionGrade_form', null, null, 'gradeNameZH', '<%=positionGradeHolder.getNextSortOrder("gradeNameZH")%>', 'tableWrapper');">职级名称（中文）</a>
			</th>
			<th style="width: 30%" class="header  <%=positionGradeHolder.getCurrentSortClass("gradeNameEN")%>">
				<a onclick="submitForm('listPositionGrade_form', null, null, 'gradeNameEN', '<%=positionGradeHolder.getNextSortOrder("gradeNameEN")%>', 'tableWrapper');">职级名称（英文）</a>
			</th>
			<th style="width: 12%" class="header-nosort">修改人</th>	
			<th style="width: 20%" class="header-nosort">修改时间</th>	
			<th style="width: 8%" class="header <%=positionGradeHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listPositionGrade_form', null, null, 'status', '<%=positionGradeHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>	
		</tr>
	</thead>
	<logic:notEqual name="positionGradeHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="positionGradeVO" name="positionGradeHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:notEqual value="1" name="positionGradeVO" property="extended">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="positionGradeVO" property="positionGradeId"/>" name="chkSelectRow[]" value="<bean:write name="positionGradeVO" property="positionGradeId"/>" />
						</logic:notEqual>
					</td>
					<td class="left"><a onclick="link('mgtPositionGradeCurrencyAction.do?proc=list_object&positionGradeId=<bean:write name="positionGradeVO" property="encodedId"/>');"><bean:write name="positionGradeVO" property="gradeNameZH" /></a></td>
					<td class="left"><a onclick="link('mgtPositionGradeCurrencyAction.do?proc=list_object&positionGradeId=<bean:write name="positionGradeVO" property="encodedId"/>');"><bean:write name="positionGradeVO" property="gradeNameEN" /></a></td>
					<td class="left"><bean:write name="positionGradeVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="positionGradeVO" property="decodeModifyDate"/></td>								
					<td class="left"><bean:write name="positionGradeVO" property="decodeStatus" /></td>									
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="positionGradeHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="positionGradeHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="positionGradeHolder" property="indexStart" /> - <bean:write name="positionGradeHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listPositionGrade_form', null, '<bean:write name="positionGradeHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGrade_form', null, '<bean:write name="positionGradeHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGrade_form', null, '<bean:write name="positionGradeHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGrade_form', null, '<bean:write name="positionGradeHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="positionGradeHolder" property="realPage" />/<bean:write name="positionGradeHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>