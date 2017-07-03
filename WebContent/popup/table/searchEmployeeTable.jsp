<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder  employeeHolder = (PagedListHolder) request.getAttribute("employeeHolder");
%>

<div class="top">
	<input type="button" id="btn_select" class="btn" value="<bean:message bundle="public" key="button.selected" /> " onclick="selectEmployee()"/>
	<input type="button" id="btn_cancel" name="btn_cancel" class="btn reset" onclick="$('#employeeModalId').addClass('hide');$('#shield').hide();" value="<bean:message bundle="public" key="button.exit" />" />
</div>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" disabled="disabled" style="border-color:#CCCCCC" />
			</th>
			<th style="width: 10%" class="header <%=employeeHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="if( checkSearchEmployee() ) submitForm('listEmployee_form', null, null, 'employeeId', '<%=employeeHolder.getNextSortOrder("employeeId")%>', 'employeeTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.id" /></logic:notEqual>
				</a>
			</th>
			<th style="width: 15%" class="header <%=employeeHolder.getCurrentSortClass("certificateNumber")%>">
				<a onclick="if( checkSearchEmployee() ) submitForm('listEmployee_form', null, null, 'certificateNumber', '<%=employeeHolder.getNextSortOrder("certificateNumber")%>', 'employeeTableWrapper');">
					<bean:message bundle="public" key="public.certificate.number" />
				</a>
			</th>
			<th style="width: 40%" class="header <%=employeeHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="if( checkSearchEmployee() ) submitForm('listEmployee_form', null, null, 'nameZH', '<%=employeeHolder.getNextSortOrder("nameZH")%>', 'employeeTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:notEqual>
				</a>
			</th>
			<th style="width: 35%" class="header <%=employeeHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="if( checkSearchEmployee() ) submitForm('listEmployee_form', null, null, 'nameEN', '<%=employeeHolder.getNextSortOrder("nameEN")%>', 'employeeTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name.en" /></logic:notEqual>
				</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeeHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="employeeVO" name="employeeHolder" property="source" indexId="certificateNumber">
				<tr class='<%=certificateNumber % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeVO" property="employeeId"/>" name="chkSelectRow[]" value="<bean:write name="employeeVO" property="employeeId"/>" />
					</td>
					<td class="left"><bean:write name="employeeVO" property="employeeId" /></td>
					<td class="left"><bean:write name="employeeVO" property="certificateNumber" /></td>
					<td class="left"><bean:write name="employeeVO" property="nameZH" /></td>
					<td class="left"><bean:write name="employeeVO" property="nameEN" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeHolder">
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeHolder" property="indexStart" /> - <bean:write name="employeeHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="if( checkSearchEmployee() ) submitForm('listEmployee_form', null, '<bean:write name="employeeHolder" property="firstPage" />', null, null, 'employeeTableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchEmployee() ) submitForm('listEmployee_form', null, '<bean:write name="employeeHolder" property="previousPage" />', null, null, 'employeeTableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchEmployee() ) submitForm('listEmployee_form', null, '<bean:write name="employeeHolder" property="nextPage" />', null, null, 'employeeTableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchEmployee() ) submitForm('listEmployee_form', null, '<bean:write name="employeeHolder" property="lastPage" />', null, null, 'employeeTableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeHolder" property="realPage" />/<bean:write name="employeeHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		// Checkbox 选择事件
		$('#employeeTableWrapper input[id^="kanList_chkSelectRecord_"]').click(function(){
			if($(this).attr("checked")=="checked"){
				$('#employeeTableWrapper input[id^="kanList_chkSelectRecord_"]').not($(this)).attr('checked', false);
			}
		});
		
		//Checkbox双击事件
		  jQuery("tbody tr").dblclick(function(){ 
			 kanlist_dbclickDailogBox($(this),"listEmployee_form");
		 }); 
		  
		  $(function(){
				var selectValue= $('.listEmployee_form input[id="selectedIds"]').val();
				if(selectValue!=null||selectValue!=""){
					$("tbody tr").children("td").children("input[value="+selectValue+"]").attr("checked",'true');
				}
			});
	})(jQuery);
</script>