<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder answerHolder = (PagedListHolder) request.getAttribute("answerHolder");
%>

<div class="top">
	<a id="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="export_answer();"><img src="images/appicons/excel_16.png" /></a> 
</div>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 10%" class="header <%=answerHolder.getCurrentSortClass("weChatId")%>">
				<a onclick="submitForm('listAnswer_form', null, null, 'weChatId', '<%=answerHolder.getNextSortOrder("weChatId")%>', 'answerTableWrapper');">
					<span id="weChatId">WeChat ID</span>
				</a>
			</th>
			<th style="width: 10%" class="header <%=answerHolder.getCurrentSortClass("answer")%>">
				<a onclick="submitForm('listAnswer_form', null, null, 'answer', '<%=answerHolder.getNextSortOrder("answer")%>', 'answerTableWrapper');">
					<span id="answer">Answer</span>
				</a>
			</th>
			<th style="width: 10%" class="header <%=answerHolder.getCurrentSortClass("submitDate")%>">
				<a onclick="submitForm('listAnswer_form', null, null, 'submitDate', '<%=answerHolder.getNextSortOrder("submitDate")%>', 'answerTableWrapper');">
					<span id="submitDate">Submit Time</span>
				</a>
			</th>
			<th style="width: 15%" class="header-nosort">
				<% if("zh".equalsIgnoreCase( request.getLocale().getLanguage() )){ %>
					<span id="employeeNameZH">Employee Name</span>
				<%}else{ %>
				 	<span id="employeeNameEN">Employee Name</span>
				<%}%>
			</th>
			<th style="width: 10%" class="header-nosort">
				<span id="shortName">Short Name</span>
			</th>
			<th style="width: 15%" class="header-nosort">
				<span id="decodeDepartment">Department</span>
			</th>
			<th style="width: 10%" class="header-nosort">
				<span id="decodeLocation">Location</span>
			</th>
			<th style="width: 20%" class="header-nosort">
				<span id="bizEmail">Mail Account</span>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="answerHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="anwerVO" name="answerHolder" property="source" indexId="index">
				<tr class='<%=index % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="anwerVO" property="weChatId" /></td>
					<td class="left"><bean:write name="anwerVO" property="answer" /></td>
					<td class="left"><bean:write name="anwerVO" property="submitDate" /></td>
					<td class="left">
						<% if("zh".equalsIgnoreCase( request.getLocale().getLanguage() )){ %>
							<bean:write name="anwerVO" property="employeeNameZH" />
						<%}else{ %>
						 	<bean:write name="anwerVO" property="employeeNameEN" />
						<%}%>
					</td>
					<td class="left"><bean:write name="anwerVO" property="shortName" /></td>
					<td class="left"><bean:write name="anwerVO" property="decodeDepartment" /></td>
					<td class="left"><bean:write name="anwerVO" property="decodeLocation" /></td>
					<td class="left"><bean:write name="anwerVO" property="bizEmail" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="answerHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="answerHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="answerHolder" property="indexStart" /> - <bean:write name="answerHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="if( checkSearchVendor() ) submitForm('listAnswer_form', null, '<bean:write name="answerHolder" property="firstPage" />', null, null, 'answerTableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchVendor() ) submitForm('listAnswer_form', null, '<bean:write name="answerHolder" property="previousPage" />', null, null, 'answerTableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchVendor() ) submitForm('listAnswer_form', null, '<bean:write name="answerHolder" property="nextPage" />', null, null, 'answerTableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchVendor() ) submitForm('listAnswer_form', null, '<bean:write name="answerHolder" property="lastPage" />', null, null, 'answerTableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="answerHolder" property="realPage" />/<bean:write name="answerHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		// Checkbox Ñ¡ÔñÊÂ¼þ
		$('#answerTableWrapper input[id^="kanList_chkSelectRecord_"]').click(function(){
			if($(this).attr("checked")=="checked"){
				$('#answerTableWrapper input[id^="kanList_chkSelectRecord_"]').not($(this)).attr('checked', false);
			}
		});
		
		
	    $(function(){
			var selectValue= $('.listAnswer_form input[id="selectedIds"]').val();
			if(selectValue!=null||selectValue!=""){
				$("tbody tr").children("td").children("input[value="+selectValue+"]").attr("checked",'true');
			}
		});
	})(jQuery);
</script>