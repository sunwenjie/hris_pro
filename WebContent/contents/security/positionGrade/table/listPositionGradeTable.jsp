<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder positionGradeHolder = (PagedListHolder) request.getAttribute("positionGradeHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header  <%=positionGradeHolder.getCurrentSortClass("positionGradeId")%>">
				<a onclick="submitForm('listPositionGrade_form', null, null, 'positionGradeId', '<%=positionGradeHolder.getNextSortOrder("positionGradeId")%>', 'tableWrapper');"><bean:message bundle="security" key="security.position.grade.id" /></a>
			</th>
			<th style="width: 25%" class="header  <%=positionGradeHolder.getCurrentSortClass("gradeNameZH")%>">
				<a onclick="submitForm('listPositionGrade_form', null, null, 'gradeNameZH', '<%=positionGradeHolder.getNextSortOrder("gradeNameZH")%>', 'tableWrapper');"><bean:message bundle="security" key="security.position.grade.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header  <%=positionGradeHolder.getCurrentSortClass("gradeNameEN")%>">
				<a onclick="submitForm('listPositionGrade_form', null, null, 'gradeNameEN', '<%=positionGradeHolder.getNextSortOrder("gradeNameEN")%>', 'tableWrapper');"><bean:message bundle="security" key="security.position.grade.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=positionGradeHolder.getCurrentSortClass("weight")%>">
				<a onclick="submitForm('listPositionGrade_form', null, null, 'weight', '<%=positionGradeHolder.getNextSortOrder("weight")%>', 'tableWrapper');"><bean:message bundle="security" key="security.position.grade.weight" /></a>
			</th>	
			<th style="width: 10%" class="header <%=positionGradeHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listPositionGrade_form', null, null, 'status', '<%=positionGradeHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>						
			<th style="width: 10%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 15%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="positionGradeHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="positionGradeVO" name="positionGradeHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="positionGradeVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="positionGradeVO" property="positionGradeId"/>" name="chkSelectRow[]" value="<bean:write name="positionGradeVO" property="positionGradeId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a onclick="link('positionGradeAction.do?proc=to_objectModify&id=<bean:write name="positionGradeVO" property="encodedId"/>');"><bean:write name="positionGradeVO" property="positionGradeId" /></a>
					</td>
					<td class="left"><bean:write name="positionGradeVO" property="gradeNameZH" /></td>	
					<td class="left"><bean:write name="positionGradeVO" property="gradeNameEN" /></td>
					<td class="left"><bean:write name="positionGradeVO" property="weight" /></td>	
					<td class="left"><bean:write name="positionGradeVO" property="decodeStatus" /></td>									
					<td class="left"><bean:write name="positionGradeVO" property="decodeModifyBy" /></td>									
					<td class="left"><bean:write name="positionGradeVO" property="decodeModifyDate" /></td>									
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="positionGradeHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="positionGradeHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="positionGradeHolder" property="indexStart" /> - <bean:write name="positionGradeHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listPositionGrade_form', null, '<bean:write name="positionGradeHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGrade_form', null, '<bean:write name="positionGradeHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGrade_form', null, '<bean:write name="positionGradeHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGrade_form', null, '<bean:write name="positionGradeHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="positionGradeHolder" property="realPage" />/<bean:write name="positionGradeHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>