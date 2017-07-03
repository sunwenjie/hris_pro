<%@page import="com.kan.base.domain.HistoryVO"%>
<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@page import="com.kan.base.web.renders.workflow.WorkflowRender"%>
<%@page import="java.util.Locale"%>
<%@page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final boolean inHouse = request.getAttribute( "role" ).equals( "2" );
	final EmployeeContractVO passObject = ( EmployeeContractVO )request.getAttribute( "passObject" );
	final EmployeeContractVO originalObject = ( EmployeeContractVO )request.getAttribute( "originalObject" );
	final Locale locale = request.getLocale();
%>
<logic:equal name="historyVO" property="remark5" value="3">
	<div id="tabContent1"  class="kantab"  >
		<ol class="auto">
			<% 
				final String employeeIdLabel = inHouse ? KANUtil.getProperty( locale, "public.employee2.id" ) : KANUtil.getProperty( locale, "public.employee1.id" );
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeId(), originalObject.getEmployeeId(), employeeIdLabel ) );
			%>
		</ol>
		<ol class="auto">
			<% 
				final String employeeNameZHLabel = inHouse ? KANUtil.getProperty( locale, "public.employee2.name.cn" ) : KANUtil.getProperty( locale, "public.employee1.name.cn" );
				final String employeeNameENLabel = inHouse ? KANUtil.getProperty( locale, "public.employee2.name.en" ) : KANUtil.getProperty( locale, "public.employee1.name.en" );
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeNameZH(), originalObject.getEmployeeNameZH(), employeeNameZHLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeNameEN(), originalObject.getEmployeeNameEN(), employeeNameENLabel ) );
			%>
		</ol>
		<ol class="auto">
			<% 
				final String orderIdLabel = inHouse ? KANUtil.getProperty( locale, "public.order2" ) : KANUtil.getProperty( locale, "public.order1" );
				out.print( KANUtil.getCompareHTML( passObject.getOrderId() + " - " + passObject.getDecodeEntityId() , originalObject.getOrderId() + " - " + originalObject.getDecodeEntityId(), orderIdLabel ) );
			%>
		</ol>
		<ol class="auto">
			<% 
				if( !inHouse )
				{
				   out.print( KANUtil.getCompareHTML( passObject.getClientId(), originalObject.getClientId(), "客户ID" ) );
				}
			%>
		</ol>
		<ol class="auto">
			<% 
				if( !inHouse )
				{
				   out.print( KANUtil.getCompareHTML( passObject.getClientNameZH(), originalObject.getClientNameZH(), "客户名称（中文）" ) );
				   out.print( KANUtil.getCompareHTML( passObject.getClientNameEN(), originalObject.getClientNameEN(), "客户名称（英文）" ) );
				}
				final String contractNameZHLabel = inHouse ? KANUtil.getProperty( locale, "public.contract2.name.cn" ) : KANUtil.getProperty( locale, "public.contract1.name.cn" );
				final String contractNameENLabel = inHouse ? KANUtil.getProperty( locale, "public.contract2.name.en" ) : KANUtil.getProperty( locale, "public.contract1.name.en" );
			  	out.print( KANUtil.getCompareHTML( passObject.getNameZH(), originalObject.getNameZH(), contractNameZHLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getNameEN(), originalObject.getNameEN(), contractNameENLabel ) );
			%>
		</ol>
		<ol class="auto">
			<% 
				final String contractNoLabel = inHouse ? KANUtil.getProperty( locale, "public.contract2.no" ) : KANUtil.getProperty( locale, "public.contract1.no" );
				final String contractTemplateLabel = inHouse ? KANUtil.getProperty( locale, "public.contract2.template" ) : KANUtil.getProperty( locale, "public.contract1.template" );
				final String contractStartDateLabel = inHouse ? KANUtil.getProperty( locale, "public.contract2.start.date" ) : KANUtil.getProperty( locale, "public.contract1.start.date" );
				final String contractEndDateLabel = inHouse ? KANUtil.getProperty( locale, "public.contract2.end.date" ) : KANUtil.getProperty( locale, "public.contract1.end.date" );
			  	final String departmentLabel = KANUtil.getProperty( locale, "business.employee.dept" );
			  	final String positionLabel = KANUtil.getProperty( locale, "business.employee.position" );
			  	final String additionalPositionLabel = KANUtil.getProperty( locale, "business.employee.spare.position" );
				out.print( KANUtil.getCompareHTML( passObject.getContractNo(), originalObject.getContractNo(), contractNoLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeTemplateId(), originalObject.getDecodeTemplateId(), contractTemplateLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getStartDate(), originalObject.getStartDate(), contractStartDateLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getEndDate(), originalObject.getEndDate(), contractEndDateLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeDepartment(), originalObject.getDecodeDepartment(), departmentLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodePositionId(), originalObject.getDecodePositionId(), positionLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getAdditionalPosition(), originalObject.getAdditionalPosition(), additionalPositionLabel ) );
			%>
		</ol>
		<ol class="auto">
			<%
				final String leaveReasonsLabel = KANUtil.getProperty( locale, "business.employee.resign.reasons" );
			    final String changeReasonLabel = KANUtil.getProperty( locale, "employee.position.change.description" );
				out.print( KANUtil.getCompareHTML( passObject.getLeaveReasons(), originalObject.getLeaveReasons(), leaveReasonsLabel ) );
			    out.print( KANUtil.getCompareHTML( passObject.getDecodeChangeReason(), originalObject.getDecodeChangeReason(), changeReasonLabel ) );
			%>
		</ol>
		<ol class="auto">
			<% 
				final String resignDateLabel = KANUtil.getProperty( locale, "business.employee.resign.date" );
				final String employeeStatusLabel = KANUtil.getProperty( locale, "business.employee.employment.status" );
				final String descriptionLabel = KANUtil.getProperty( locale, "public.description" );
				final String status = inHouse ? KANUtil.getProperty( locale, "public.employee2.status" ) : KANUtil.getProperty( locale, "public.employee1.status" );
			    out.print( KANUtil.getCompareHTML( passObject.getResignDate(), originalObject.getResignDate(), resignDateLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeEmployStatus(), originalObject.getDecodeEmployStatus(), employeeStatusLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDescription(), originalObject.getDescription(), descriptionLabel , "textarea" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeStatus(), originalObject.getDecodeStatus(), status ) );
			%>
		</ol>
		
		<ol class="auto">
			<% 
				final String settlemenetBranchLabel = inHouse ? KANUtil.getProperty( locale, "business.employee2.cost.branch" ) : KANUtil.getProperty( locale, "business.employee1.cost.branch" );
				final String lockedLabel = KANUtil.getProperty( locale, "business.employee.locked" );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeSettlementBranch(), originalObject.getDecodeSettlementBranch(), settlemenetBranchLabel ) );
				if( ! inHouse )
				{
					out.print( KANUtil.getCompareHTML( passObject.getDecodeLocked(), originalObject.getDecodeLocked(), lockedLabel ) );
				}
			%>
		</ol>
		<ol class="auto">
			<% 
				final String branchLabel = KANUtil.getProperty( locale, "business.employee.branch" );
				final String ownerLabel = KANUtil.getProperty( locale, "business.employee.owner" );
				final String modifyByLabel = KANUtil.getProperty( locale, "public.modify.by" );
				final String modifyDateLabel = KANUtil.getProperty( locale, "public.modify.date" );
				final String parentBranchLabel = KANUtil.getProperty( locale, "business.employee.contract.parent.branch" );
				final String lineManagerLabel = KANUtil.getProperty( locale, "business.employee.contract.line.manager" );
			    out.print( KANUtil.getCompareHTML( passObject.getDecodeBranch(), originalObject.getDecodeBranch(), branchLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeOwner(), originalObject.getDecodeOwner(), ownerLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeModifyBy(), originalObject.getDecodeModifyBy(), modifyByLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeModifyDate(), originalObject.getDecodeModifyDate(), modifyDateLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getParentBranchName(), originalObject.getParentBranchName(), parentBranchLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getLineManager(), originalObject.getLineManager(), lineManagerLabel ) );
			%>
		</ol>
		<br/><br/>
		<ol class="auto">	
			<%
				final String entityLabel = KANUtil.getProperty( locale, "security.entity" ); 
				final String businessTypeLabel = KANUtil.getProperty( locale, "business.employee.businessType" ); 
				final String calendarLabel = KANUtil.getProperty( locale, "business.employee.calendar" ); 
				final String shiftLabel = KANUtil.getProperty( locale, "business.employee.shift" ); 
				final String probationMonthLabel = KANUtil.getProperty( locale, "business.employee.probation.month" ); 
				final String probationEndDateLabel = KANUtil.getProperty( locale, "business.employee.probation.end.date" ); 
				final String attendanceCheckTypeLabel = KANUtil.getProperty( locale, "business.employee.attendance.check.type" ); 
				out.print( KANUtil.getCompareHTML( passObject.getDecodeEntityId(), originalObject.getDecodeEntityId(), entityLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeBusinessTypeId(), originalObject.getDecodeBusinessTypeId(), businessTypeLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeCalendarId(), originalObject.getDecodeCalendarId(), calendarLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeShiftId(), originalObject.getDecodeShiftId(), shiftLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeProbationMonth(), originalObject.getDecodeProbationMonth(), probationMonthLabel ) );
				out.print( KANUtil.getCompareHTML( passObject.getProbationEndDate(), originalObject.getProbationEndDate(), probationEndDateLabel ) );
				
				if( ! inHouse )
				{
				   out.print( KANUtil.getCompareHTML( passObject.getDecodeAttendanceCheckType(), originalObject.getDecodeAttendanceCheckType(), attendanceCheckTypeLabel ) );
				}
				final String approveTypeLabel = KANUtil.getProperty( locale, "business.employee.approve.type" ); 
				out.print( KANUtil.getCompareHTML( passObject.getDecodeApproveType(), originalObject.getDecodeApproveType(), approveTypeLabel ) );
			%>
		</ol>
		<%=WorkflowRender.generateHtmlForWorkflowApprovalPageDefineColumn( request, EmployeeContractAction.getAccessAction( request, null ), originalObject, passObject, true ) %> 
		<ol class="auto">
			<%
				 final String attachmentLabel = KANUtil.getProperty( locale, "menu.table.title.attachment" ); 
				 if ( passObject.getAttachmentArray() != null && passObject.getAttachmentArray().length > 0 )
				 {
				     int count = 0; 
				     for ( String attachment : passObject.getAttachmentArray() )
			         {
				        if ( attachment.indexOf( "##" ) > 0 )
			            {
				           count ++;
				           out.print( "<li><label>" + attachmentLabel + count + "</label>" );
				           out.print( "<a onclick=\"encodedLink('downloadFileAction.do?proc=download&fileString=" + attachment.split( "##" )[ 0 ] + "');\">" + 	attachment.split( "##" )[ 1 ] );
				           out.print( "</a></li>" );
			            }
			         }
				 }
			%>
		</ol>
		<div id="special_info_1"></div>
	</div>
	<div id="tabContent2"  class="kantab" style="display: none" >
			<ol class="auto">
				<li><label><%=employeeIdLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
			</ol>
			<ol class="auto">
				<li><label><%=employeeNameZHLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameZH"/>' /></li>
				<li><label><%=employeeNameENLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameEN"/>' /></li>
			</ol>
			<ol class="auto">
				<li><label><%=orderIdLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="orderId"/> - <bean:write name="originalObject" property="decodeEntityId"/>' /></li>
			</ol>
			<logic:equal name="role" value="1">
			<ol class="auto">
				<li><label>客户ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientId"/>' /></li>
			</ol>	
			</logic:equal>
			<ol class="auto">
				<logic:equal name="role" value="1">
				<li><label>客户名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameZH"/>' /></li>
				<li><label>客户名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameEN"/>' /></li>
				</logic:equal>
				<li><label><%=contractNameZHLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
				<li><label><%=contractNameENLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
			</ol>	
			<ol class="auto">
				<li><label><%=contractNoLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNo"/>' /></li>
				<li><label><%=contractTemplateLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeTemplateId"/>' /></li>
				<li><label><%=contractStartDateLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
				<li><label><%=contractEndDateLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
				<li><label><%=departmentLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeDepartment"/>' /></li>
			    <li><label><%=positionLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePositionId"/>' /></li>
			    <li><label><%=additionalPositionLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="additionalPosition"/>' /></li>
			</ol>
			<ol class="auto">
				<li><label><%=leaveReasonsLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="leaveReasons"/>' /></li>
				<li><label><%=changeReasonLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeChangeReason"/>' /></li>
			</ol>	
			<ol class="auto">
				<li><label><%=resignDateLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="resignDate"/>' /></li>
				<li><label><%=employeeStatusLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeEmployStatus"/>' /></li>
				<li><label><%=descriptionLabel %></label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
				<li><label><%=status %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
			</ol>	
			<ol class="auto">
				<li><label><%=settlemenetBranchLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSettlementBranch"/>' /></li>
				<logic:equal name="role" value="1">
					<li><label><%=lockedLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeLocked"/>' /></li>
				</logic:equal>
			</ol>	
			<ol class="auto">
				<li><label><%=branchLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeBranch"/>' /></li>
				<li><label><%=ownerLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
				<li><label><%=modifyByLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeModifyBy"/>' /></li>
				<li><label><%=modifyDateLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeModifyDate"/>' /></li>
				<li><label><%=parentBranchLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="parentBranchName"/>' /></li>
				<li><label><%=lineManagerLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="lineManager"/>' /></li>
			</ol>	
			<br/><br/>
			<ol class="auto">
				<li><label><%=entityLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeEntityId"/>' /></li>
				<li><label><%=businessTypeLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeBusinessTypeId"/>' /></li>
				<li><label><%=calendarLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeCalendarId"/>' /></li>
				<li><label><%=shiftLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeShiftId"/>' /></li>
				<li><label><%=probationMonthLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeProbationMonth"/>' /></li>
				<li><label><%=probationEndDateLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="probationEndDate"/>' /></li>
				<logic:equal name="role" value="1">
				<li><label><%=attendanceCheckTypeLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeAttendanceCheckType"/>' /></li>
				</logic:equal>
				<li><label><%=approveTypeLabel %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeApproveType"/>' /></li>
			</ol>
			<!-- 审核显示自定义字段 -->
			<%=WorkflowRender.generateHtmlForWorkflowApprovalPageDefineColumn( request, EmployeeContractAction.getAccessAction( request, null ), originalObject, passObject, false ) %>
			<ol class="auto">
				<%
					 if ( originalObject.getAttachmentArray() != null && originalObject.getAttachmentArray().length > 0 )
					 {
					     int count = 0; 
					     for ( String attachment : originalObject.getAttachmentArray() )
				         {
					        if ( attachment.indexOf( "##" ) > 0 )
				            {
					           count ++;
					           out.print( "<li><label>" + attachmentLabel + count + "</label>" );
					           out.print( "<a onclick=\"encodedLink('downloadFileAction.do?proc=download&fileString=" + attachment.split( "##" )[ 0 ] + "');\">" + 	attachment.split( "##" )[ 1 ] );
					           out.print( "</a></li>" );
				            }
				         }
					 }
				%>
			</ol> 
			<div id="special_info_2"></div>
	</div>
	
	<script type="text/javascript">
		loadHtml('#special_info_1', "employeeContractAction.do?proc=list_special_info_html&historyId=<%=((HistoryVO)request.getAttribute( "historyVO" )).getHistoryId()%>&type=3&tabIndex=1&comeFrom=workflow&contractId=<%=((EmployeeContractVO)request.getAttribute("passObject")).getEncodedId()%>", true, null);
		loadHtml('#special_info_2', "employeeContractAction.do?proc=list_special_info_html&historyId=<%=((HistoryVO)request.getAttribute( "historyVO" )).getHistoryId()%>&type=3&tabIndex=2&comeFrom=workflow&contractId=<%=((EmployeeContractVO)request.getAttribute("passObject")).getEncodedId()%>", true, null);
	</script>
</logic:equal>

<!-- 非修改类型的审批流程 -->
<logic:notEqual name="historyVO" property="remark5" value="3">
	<div id="tabContent1"  class="kantab"  >
		<ol class="auto">
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeId"/>' />
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeNameZH"/>' />
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeNameEN"/>' />
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="orderId"/> - <bean:write name="originalObject" property="decodeEntityId"/>' />
			</li>
		</ol>
		<logic:equal name="role" value="1">
		<ol class="auto">
			<li><label>客户ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientId"/>' /></li>
		</ol>	
		</logic:equal>
		<ol class="auto">
			<logic:equal name="role" value="1">
			<li><label>客户名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameZH"/>' /></li>
			<li><label>客户名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameEN"/>' /></li>
			</logic:equal>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name.cn" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' />
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name.en" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' />
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.no" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.no" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractNo"/>' />
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.template" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.template" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeTemplateId"/>' />
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.start.date" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.start.date" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="startDate"/>' />
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.end.date" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.end.date" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="endDate"/>' />
			</li>
			<li><label><bean:message bundle="business" key="business.employee.dept" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeDepartment"/>' /></li>
		    <li><label><bean:message bundle="business" key="business.employee.position" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodePositionId"/>' /></li>
		    <li><label><bean:message bundle="business" key="business.employee.spare.position" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="additionalPosition"/>' /></li>
			<logic:equal name="role" value="1">
			<li><label><bean:message bundle="business" key="business.employee.line.manager" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeLineManagerName"/>' /></li>
			</logic:equal>
		</ol>	
		<ol class="auto">
			<li><label><bean:message bundle="business" key="business.employee.resign.reasons" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="leaveReasons"/>' /></li>
			<li><label><bean:message bundle="business" key="employee.position.change.description" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeChangeReason"/>' /></li>
		</ol>
		<ol class="auto">
			<li><label><bean:message bundle="business" key="business.employee.resign.date" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="resignDate"/>' /></li>
			<li><label><bean:message bundle="business" key="business.employee.employment.status" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeEmployStatus"/>' /></li>
			<li><label><bean:message bundle="public" key="public.description" /></label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.status" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeStatus"/>' />
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="business" key="business.employee1.cost.branch" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="business" key="business.employee2.cost.branch" /></logic:equal>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeSettlementBranch"/>' />
			</li>
			<logic:equal name="role" value="1">
				<li><label><bean:message bundle="business" key="business.employee.locked" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeLocked"/>' /></li>
			</logic:equal>
		</ol>	
		<ol class="auto">
			<li><label><bean:message bundle="business" key="business.employee.branch" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBranch"/>' /></li>
			<li><label><bean:message bundle="business" key="business.employee.owner" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeOwner"/>' /></li>
			<li><label><bean:message bundle="public" key="public.modify.by" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeModifyBy"/>' /></li>
			<li><label><bean:message bundle="public" key="public.modify.date" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeModifyDate"/>' /></li>
			<li><label><bean:message bundle="business" key="business.employee.contract.parent.branch" /></label><bean:write name="passObject" property="parentBranchName"/></li>
			<li><label><bean:message bundle="business" key="business.employee.contract.line.manager" /></label><bean:write name="passObject" property="lineManager"/></li>
		</ol>	
		<br/><br/>
		<ol class="auto">
			<li><label><bean:message bundle="security" key="security.entity" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeEntityId"/>' /></li>
			<li><label><bean:message bundle="business" key="business.employee.businessType" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBusinessTypeId"/>' /></li>
			<li><label><bean:message bundle="business" key="business.employee.calendar" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeCalendarId"/>' /></li>
			<li><label><bean:message bundle="business" key="business.employee.shift" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeShiftId"/>' /></li>
			<li><label><bean:message bundle="business" key="business.employee.probation.month" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeProbationMonth"/>' /></li>
			<li><label><bean:message bundle="business" key="business.employee.probation.end.date" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="probationEndDate"/>' /></li>
			<logic:equal name="role" value="1">
			<li><label><bean:message bundle="business" key="business.employee.attendance.check.type" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeAttendanceCheckType"/>' /></li>
			</logic:equal>
			<li><label><bean:message bundle="business" key="business.employee.approve.type" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeApproveType"/>' /></li>
		</ol>
		<!-- 审核显示自定义字段 -->
		<%=WorkflowRender.generateHtmlForWorkflowApprovalPageDefineColumn( request, EmployeeContractAction.getAccessAction( request, null ), originalObject, passObject, false ) %>
		<ol id="attachmentsOL" class="auto">
			<%
				 final String attachmentLabel = KANUtil.getProperty( locale, "menu.table.title.attachment" ); 
				 if ( passObject.getAttachmentArray() != null && passObject.getAttachmentArray().length > 0 )
				 {
				     int count = 0; 
				     for ( String attachment : passObject.getAttachmentArray() )
			         {
				        if ( attachment.indexOf( "##" ) > 0 )
			            {
				           count ++;
				           out.print( "<li><label>" + attachmentLabel + count + "</label>" );
				           out.print( "<a onclick=\"encodedLink('downloadFileAction.do?proc=download&fileString=" + attachment.split( "##" )[ 0 ] + "');\">" + 	attachment.split( "##" )[ 1 ] );
				           out.print( "</a></li>" );
			            }
			         }
				 }
			%>
		</ol> 
	</div>
	<script type="text/javascript">
		loadHtml('#special_info_1', "employeeContractAction.do?proc=list_special_info_html&type=1&tabIndex=0&comeFrom=workflow&contractId=<%=((EmployeeContractVO)request.getAttribute("passObject")).getEncodedId()%>", true, null);
	</script>
</logic:notEqual> 
