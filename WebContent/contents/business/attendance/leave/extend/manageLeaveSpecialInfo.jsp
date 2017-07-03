<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.hro.domain.biz.attendance.LeaveHeaderVO"%>
<%@page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first" ><bean:message bundle="public" key="menu.table.title.leave.detail" /> </li> 
		</ul> 
		<ul> 
			<li id="tabMenu2" onClick="changeTab(2,2)" class="" ><bean:message bundle="public" key="menu.table.title.attachment" /> (<span id="numberOfAttachment"><bean:write name="listAttachmentCount"/></span>) </li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab" >
			<div id="tableWrapper1">
				<div id="tableWrapper">
					<logic:notEmpty name="contractStartDate" >
						<input type="hidden" name="contractStartDate" id="contractStartDate" value="<bean:write name="contractStartDate"/>"/>
					</logic:notEmpty>
					<logic:notEmpty name="contractEndDate" >
						<input type="hidden" name="contractEndDate" id="contractEndDate" value="<bean:write name="contractEndDate"/>"/>
					</logic:notEmpty>
					
					<jsp:include page="/contents/business/attendance/leave/table/listEmployeeContractLeaveTable.jsp" flush="true"/>
				</div>
			</div>
		</div> 
		<div id="tabContent2" class="kantab" style="display:none">
			<span><a name="uploadAttachment" id="uploadAttachment" class="kanhandle"><bean:message bundle="public" key="link.upload.attachment" /></a></span>	
			<div id="attachmentsDiv">
				<ol id="attachmentsOL" class="auto">
					<% 
						final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO )request.getAttribute( "leaveHeaderForm" );
						
					%>
					<%= AttachmentRender.getAttachments( request, leaveHeaderVO.getAttachmentArray(), null) %>
				</ol>
			</div>
		</div>
	</div> 
</div>	

<script type="text/javascript">
		// 附件提交按钮事件
		var uploadObject = createUploadObject('uploadAttachment', 'all', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_LEAVE %>/<%= BaseAction.getAccountId(request, response) %>/<%= BaseAction.getUsername(request, response) %>/');
</script>