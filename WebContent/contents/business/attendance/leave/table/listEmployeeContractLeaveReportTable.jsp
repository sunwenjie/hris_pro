<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@page import="com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.kan.hro.domain.biz.employee.EmployeeContractLeaveReportVO"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final boolean is_cn = request.getLocale().getLanguage().equalsIgnoreCase( "zh" );
	final PagedListHolder employeeContractLeaveReportHolder = ( PagedListHolder ) request.getAttribute( "employeeContractLeaveReportHolder" );
	boolean isRowspan = false;
	if( employeeContractLeaveReportHolder != null && employeeContractLeaveReportHolder.getSource() != null && employeeContractLeaveReportHolder.getSource().size() > 0 ) {
	   isRowspan = true;
	}
	
	 final String[] split_cell_item_arr = new String[] { "41", "48", "49" }; 
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th nowrap <%= isRowspan ? "rowspan=\"2\"" : "" %> class="header <%=employeeContractLeaveReportHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, null, 'employeeId', '<%=employeeContractLeaveReportHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th nowrap <%= isRowspan ? "rowspan=\"2\"" : "" %> class="header <%=employeeContractLeaveReportHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, null, 'employeeNameZH', '<%=employeeContractLeaveReportHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th nowrap <%= isRowspan ? "rowspan=\"2\"" : "" %> class="header <%=employeeContractLeaveReportHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, null, 'employeeNameEN', '<%=employeeContractLeaveReportHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<th nowrap <%= isRowspan ? "rowspan=\"2\"" : "" %> class="header-nosort">
				<bean:message bundle="business" key="business.employee.report.nick.name" />
			</th>
			<th nowrap <%= isRowspan ? "rowspan=\"2\"" : "" %> class="header <%=employeeContractLeaveReportHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, null, 'contractId', '<%=employeeContractLeaveReportHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<th nowrap <%= isRowspan ? "rowspan=\"2\"" : "" %> class="header <%=employeeContractLeaveReportHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, null, 'orderId', '<%=employeeContractLeaveReportHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
				</a>
			</th>
			<%
				int sum = 0;
				if( employeeContractLeaveReportHolder != null && employeeContractLeaveReportHolder.getHolderSize() > 0 )
				{
				   final EmployeeContractLeaveReportVO vo = ( EmployeeContractLeaveReportVO )employeeContractLeaveReportHolder.getSource().get( 0 );
				   final List< EmployeeContractLeaveVO > leaveDetails = vo.getLeaveDetails();
				   for( EmployeeContractLeaveVO leaveDetail : leaveDetails )
				   {
				 	  // TODO
				      /* if("48".equals( leaveDetail.getItemId() ) ||"49".equals( leaveDetail.getItemId())  )
				            continue; */
				      if( !ArrayUtils.contains( split_cell_item_arr, leaveDetail.getItemId() ) )
				     	 out.println( "<th style=\"font-weight: normal;\" class=\"center header-nosort\" rowspan=\"2\">" + ( is_cn ? leaveDetail.getItemNameZH() : leaveDetail.getItemNameEN() ) + "</th>"); 
				      else
				         out.println( "<th style=\"font-weight: normal;\" class=\"center header-nosort\" colspan=\"2\">" + ( is_cn ? leaveDetail.getItemNameZH() : leaveDetail.getItemNameEN() ) + "</th>"); 
				   }
				   sum = leaveDetails.size();
				}
			%>
		</tr>
		<tr>
			<%
				if( employeeContractLeaveReportHolder != null && employeeContractLeaveReportHolder.getHolderSize() > 0 )
				{
				   final EmployeeContractLeaveReportVO vo = ( EmployeeContractLeaveReportVO )employeeContractLeaveReportHolder.getSource().get( 0 );
				   final List< EmployeeContractLeaveVO > leaveDetails = vo.getLeaveDetails();
				   for( EmployeeContractLeaveVO leaveDetail : leaveDetails )
				   {
				      // TODO
				      /* if("48".equals( leaveDetail.getItemId() ) ||"49".equals( leaveDetail.getItemId())  )
			            continue; */
				      if( ArrayUtils.contains( split_cell_item_arr, leaveDetail.getItemId() ) )
				      {
				         out.println( "<th class=\"center header-nosort\">" + ( is_cn ? "×Ü¼Æ" : "Total" ) + "</th>"); 
					     out.println( "<th class=\"center header-nosort\">" + ( is_cn ? "Ê£Óà" : "Left" ) + "</th>"); 
				      }
				   }
				}
			%>
		</tr>
	</thead>
	<logic:notEqual name="employeeContractLeaveReportHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="employeeContractLeaveReportVO" name="employeeContractLeaveReportHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="employeeContractLeaveReportVO" property="employeeId" /></td>
					<td class="left"><bean:write name="employeeContractLeaveReportVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="employeeContractLeaveReportVO" property="employeeNameEN" /></td>
					<td class="left"><bean:write name="employeeContractLeaveReportVO" property="employeeShortName" /></td>
					<td class="left"><bean:write name="employeeContractLeaveReportVO" property="contractId" /></td>
					<td class="left"><bean:write name="employeeContractLeaveReportVO" property="orderId" /></td>
					<logic:iterate id="leaveDetail" name="employeeContractLeaveReportVO" property="leaveDetails">
					
					
					<%-- TODO S --%>
					<%-- <logic:notEqual name="leaveDetail" property="itemId" value="48">
					<logic:notEqual name="leaveDetail" property="itemId" value="49"> --%>
					<%-- TODO E --%>
					
						<logic:notEqual name="leaveDetail" property="itemId" value="41">
						<logic:notEqual name="leaveDetail" property="itemId" value="48">
						<logic:notEqual name="leaveDetail" property="itemId" value="49">
							<td class="right"><bean:write name="leaveDetail" property="usedBenefitQuantity" /></td>
						</logic:notEqual>
						</logic:notEqual>
						</logic:notEqual>
						
						<bean:define id="isAnnualLeave" value="0"></bean:define>
						<logic:equal name="leaveDetail" property="itemId" value="41" >
							<bean:define id="isAnnualLeave" value="1"></bean:define>
						</logic:equal>
						<logic:equal name="leaveDetail" property="itemId" value="48" >
							<bean:define id="isAnnualLeave" value="1"></bean:define>
						</logic:equal>
						<logic:equal name="leaveDetail" property="itemId" value="49" >
							<bean:define id="isAnnualLeave" value="1"></bean:define>
						</logic:equal>
						<logic:equal name="isAnnualLeave" value="1" >
							<td class="right">
								<bean:write name="leaveDetail" property="benefitQuantity" />
							</td>
							<bean:define id="flag" value="0"></bean:define>
							<logic:equal name="leaveDetail" property="leftBenefitQuantity" value="0">
							<logic:equal name="leaveDetail" property="itemId" value="41">
								<td class="center reSize" style="color: red;"><bean:write name="leaveDetail" property="leftBenefitQuantity" /></td>
								<bean:define id="flag" value="1"></bean:define>
							</logic:equal>
							</logic:equal>
							<logic:notEqual name="flag" value="1">
								<td class="right"><bean:write name="leaveDetail" property="leftBenefitQuantity" /></td>
							</logic:notEqual>
						</logic:equal>
					
					<%-- TODO S --%>	
					<%-- </logic:notEqual>
					</logic:notEqual>	 --%>
					<%-- TODO E --%>
						
					</logic:iterate>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeContractLeaveReportHolder">
		<tfoot>
			<tr class="total">
				<td colspan="41" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="employeeContractLeaveReportHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="employeeContractLeaveReportHolder" property="indexStart" /> - <bean:write name="employeeContractLeaveReportHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, '<bean:write name="employeeContractLeaveReportHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, '<bean:write name="employeeContractLeaveReportHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, '<bean:write name="employeeContractLeaveReportHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, '<bean:write name="employeeContractLeaveReportHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="employeeContractLeaveReportHolder" property="realPage" />/<bean:write name="employeeContractLeaveReportHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<script type="text/javascript">
	useFixColumn(4);
</script>