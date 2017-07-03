<%@page import="com.kan.hro.domain.biz.attendance.TimesheetReportExportVO"%>
<%@page import="com.kan.base.domain.MappingVO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
   final PagedListHolder timesheetReportExportHolder = ( PagedListHolder ) request.getAttribute( "timesheetReportExportHolder" );
   List< MappingVO > items = null;
   boolean isRowspan = false;
   if( timesheetReportExportHolder != null && timesheetReportExportHolder.getSource() != null && timesheetReportExportHolder.getSource().size() > 0 ){
  	 items = ( ( TimesheetReportExportVO )timesheetReportExportHolder.getSource().get( 0 ) ).getItems();
  	 isRowspan = true;
   }
   final boolean IN_HOUSE = request.getAttribute( "role" ).equals( KANConstants.ROLE_IN_HOUSE );
   /* final boolean isHRFunction = BaseAction.isHRFunction( request, null ); */
   /* request.setAttribute( "isHRFunction", isHRFunction ? "1" : "2" ); */
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>   class="header <%=timesheetReportExportHolder.getCurrentSortClass( "employeeId" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeId', '<%=timesheetReportExportHolder.getNextSortOrder( "employeeId" )%>', 'tableWrapper');">
					<span id="employeeId">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					</span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>  class="header <%=timesheetReportExportHolder.getCurrentSortClass( "employeeNameZH" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeNameZH', '<%=timesheetReportExportHolder.getNextSortOrder( "employeeNameZH" )%>', 'tableWrapper');">
					<span id="employeeNameZH">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>   class="header <%=timesheetReportExportHolder.getCurrentSortClass( "employeeNameEN" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeNameEN', '<%=timesheetReportExportHolder.getNextSortOrder( "employeeNameEN" )%>', 'tableWrapper');">
					<span id="employeeNameEN">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</span>
				</a>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>   class="header <%=timesheetReportExportHolder.getCurrentSortClass( "entityId" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'entityId', '<%=timesheetReportExportHolder.getNextSortOrder( "entityId" )%>', 'tableWrapper');">
					<span id="decodeEntityId"><bean:message bundle="security" key="security.entity" /></span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>   class="header <%=timesheetReportExportHolder.getCurrentSortClass( "_tempParentBranchIds" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, '_tempParentBranchIds', '<%=timesheetReportExportHolder.getNextSortOrder( "_tempParentBranchIds" )%>', 'tableWrapper');">
					<span id="decodeTempParentBranchIds"><bean:message bundle="business" key="business.ts.report.temp.parent.branch" /></span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>  class="header <%=timesheetReportExportHolder.getCurrentSortClass( "_tempBranchIds" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, '_tempBranchIds', '<%=timesheetReportExportHolder.getNextSortOrder( "_tempBranchIds" )%>', 'tableWrapper');">
					<span id="decodeTempBranchIds"><bean:message bundle="business" key="business.ts.report.temp.branch" /></span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>  class="header <%=timesheetReportExportHolder.getCurrentSortClass( "_tempPositionIds" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, '_tempPositionIds', '<%=timesheetReportExportHolder.getNextSortOrder( "_tempPositionIds" )%>', 'tableWrapper');">
					<span id="decodeTempPositionIds"><bean:message bundle="business" key="business.ts.report.temp.position" /></span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>  class="header <%=timesheetReportExportHolder.getCurrentSortClass( "onboardDate" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'onboardDate', '<%=timesheetReportExportHolder.getNextSortOrder( "onboardDate" )%>', 'tableWrapper');">
					<span id="onboardDate"><bean:message bundle="business" key="business.ts.report.onboardDate" /></span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>   class="header <%=timesheetReportExportHolder.getCurrentSortClass( "resignDate" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'resignDate', '<%=timesheetReportExportHolder.getNextSortOrder( "resignDate" )%>', 'tableWrapper');">
					<span id="resignDate"><bean:message bundle="business" key="business.ts.report.resignDate" /></span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>   class="header <%=timesheetReportExportHolder.getCurrentSortClass( "certificateNumber" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'certificateNumber', '<%=timesheetReportExportHolder.getNextSortOrder( "certificateNumber" )%>', 'tableWrapper');">
					<span id="certificateNumber"><bean:message bundle="public" key="public.certificate.number" /></span>
				</a>  
			</th>

			<logic:iterate id="timesheetday" name="listTimesheetDatys" indexId="number">
				<logic:equal name="timesheetday" property="dayType" value="1">
					<th nowrap  style="font-weight: normal;" class="noTitle"><bean:write name="timesheetday" property="day" /></th>
				</logic:equal>
				<logic:notEqual name="timesheetday" property="dayType" value="1">
					<th nowrap  style="color: red;font-weight: normal;" class="noTitle"><bean:write name="timesheetday" property="day" /></th>
				</logic:notEqual>
			</logic:iterate>

			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %> class="header <%=timesheetReportExportHolder.getCurrentSortClass( "totalFullDays" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'totalFullDays', '<%=timesheetReportExportHolder.getNextSortOrder( "totalFullDays" )%>', 'tableWrapper');">
					<span id="totalFullDays"><bean:message bundle="business" key="business.ts.report.totalFullDays" /></span>
				</a>
			</th>
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>  class="header <%=timesheetReportExportHolder.getCurrentSortClass( "totalWorkDays" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'totalWorkDays', '<%=timesheetReportExportHolder.getNextSortOrder( "totalWorkDays" )%>', 'tableWrapper');">
					<span id="totalWorkDays"><bean:message bundle="business" key="business.ts.report.totalWorkDays" /></span>
				</a>
			</th>
			
			<%
				if( items != null && items.size() > 0 )
				{
				   for( MappingVO item : items ) 
				   {
			%>
				<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %> class="header-nosort noTitle"><%=item.getMappingId() %>&nbsp;&nbsp;</th>
			<%
				   }
				}
			%>
			
			<th nowrap  <%= isRowspan ? "rowspan=\"2\"" : "" %>   class="header <%=timesheetReportExportHolder.getCurrentSortClass( "status" )%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'status', '<%=timesheetReportExportHolder.getNextSortOrder( "status" )%>', 'tableWrapper');">
					<span id="decodeStatus"><bean:message bundle="public" key="public.status" /></span>
				</a>
			</th>
		</tr>

		<tr>
			<logic:iterate id="timesheetday" name="listTimesheetDatys">
				<logic:equal name="timesheetday" property="dayType" value="1">
					<th class="center" nowrap style="font-weight: normal;" align="center"><bean:write name="timesheetday" property="remark1" /></th>
				</logic:equal>
				<logic:notEqual name="timesheetday" property="dayType" value="1">
					<th class="center" nowrap style="color: red;font-weight: normal;"><bean:write name="timesheetday" property="remark1" /></th>
				</logic:notEqual>
			</logic:iterate>
		</tr>
	</thead>
	<logic:notEqual name="timesheetReportExportHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="timesheetHeaderVO" name="timesheetReportExportHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<bean:write name="timesheetHeaderVO" property="employeeId" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="employeeNameZH" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="employeeNameEN" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="decodeEntityId" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="decodeTempParentBranchIds" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="decodeTempBranchIds" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="decodeTempPositionIds" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="onboardDate" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="resignDate" />
					</td>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="certificateNumber" />
					</td>

					<logic:iterate id="timesheetDetailVO" name="timesheetHeaderVO" property="detailList">
						<logic:equal name="timesheetDetailVO" property="dayType" value="1">
							<td class="right"><bean:write name="timesheetDetailVO" property="workHours" /></td>
						</logic:equal>
						<logic:notEqual name="timesheetDetailVO" property="dayType" value="1">
							<td class="right" style="color: red;"><bean:write name="timesheetDetailVO" property="workHours" /></td>
						</logic:notEqual>
					</logic:iterate>

					<td class="right">
						<bean:write name="timesheetHeaderVO" property="totalFullDays" />
					</td>
					<td class="right">
						<bean:write name="timesheetHeaderVO" property="totalWorkDays" />
					</td>
					
					<logic:iterate id="item" name="timesheetHeaderVO" property="items">
						<td class="right"><bean:write name="item" property="mappingValue" /></td>
					</logic:iterate>
					
					<td class="right">
						<bean:write name="timesheetHeaderVO" property="decodeStatus" />
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="timesheetReportExportHolder">
		<tfoot>
			<tr class="total">
				<td class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="timesheetReportExportHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="timesheetReportExportHolder" property="indexStart" /> - <bean:write name="timesheetReportExportHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetReportExportHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetReportExportHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetReportExportHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetReportExportHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="timesheetReportExportHolder" property="realPage" />/<bean:write name="timesheetReportExportHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<script>
	(function($){
		var colspanSize = 13;
		if( $('tbody#mytab tr').find('td').length != 0 ){
			colspanSize = $('tbody#mytab tr').find('td').length;
		}
		$('tr.total td').attr( 'colspan', colspanSize );
		<logic:notEqual name="timesheetReportExportHolder" property="holderSize" value="0">
			$("#resultTable").fixTable({
				fixColumn: 2,//固定列数
				width:0,//显示宽度
				height:450//显示高度
			});
		</logic:notEqual>
	})(jQuery);
</script>