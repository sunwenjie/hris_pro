<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeLogAction"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.security.StaffVO"%>
<%@ page import="com.kan.base.web.renders.security.PositionRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeSkillVO"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeVO"%>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.base.web.renders.management.SkillRender"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeEducationAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeWorkAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeSkillAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeLanguageAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeCertificationAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeMembershipAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeEmergencyAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeePositionChangeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
 	final EmployeeVO employeeVO = (EmployeeVO)request.getAttribute("employeeForm");
	String[] skillIdArray = employeeVO.getSkillIdArray();
%>
<div id="attachement" class="kantab">
	<span><a name="uploadAttachment" id="uploadAttachment" class="kanhandle"><bean:message bundle="public" key="link.upload.attachment" /></a></span>
	<div id="attachmentsDiv">
		<ol id="attachmentsOL" class="auto">
			<%= AttachmentRender.getAttachments(request, employeeVO.getAttachmentArray(), null) %>
		</ol>
	</div>
</div>
<div id="tab"> 
	<div class="tabMenu">
		<ul>
			<li id="tabMenu1" onClick="changeTab(1,11)" class="first hover"><bean:message bundle="public" key="menu.table.title.position" /></li>
			<li id="tabMenu2" onClick="changeTab(2,11)"><bean:message bundle="public" key="menu.table.title.account" /></li>
			<kan:auth right="view" action="<%=EmployeeWorkAction.accessAction %>">
				<li id="tabMenu3" onClick="changeTab(3,11)"><bean:message bundle="public" key="menu.table.title.work" />(<span id="span_employeeWorkAction"><bean:write name="listEmployeeWorkCount" /></span>)</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeEducationAction.accessAction %>">
				<li id="tabMenu4" onClick="changeTab(4,11)"><bean:message bundle="public" key="menu.table.title.education" />(<span id="span_employeeEducationAction"><bean:write name="listEmployeeEducationCount" /></span>)</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeSkillAction.accessAction %>">
				<li id="tabMenu5" onClick="changeTab(5,11)"><bean:message bundle="public" key="menu.table.title.skill" />(<span id="span_employeeSkillAction"><bean:write name="listEmployeeSkillCount" /></span>)</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeLanguageAction.accessAction %>">
				<li id="tabMenu6" onClick="changeTab(6,11)"><bean:message bundle="public" key="menu.table.title.language" />(<span id="span_employeeLanguageAction"><bean:write name="listEmployeeLanguageCount" /></span>)</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeCertificationAction.accessAction %>">
				<li id="tabMenu7" onClick="changeTab(7,11)"><bean:message bundle="public" key="menu.table.title.certification" />(<span id="span_employeeCertificationAction"><bean:write name="listEmployeeCertificationCount" /></span>)</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeMembershipAction.accessAction %>">
				<li id="tabMenu8" onClick="changeTab(8,11)"><bean:message bundle="public" key="menu.table.title.membership" />(<span id="span_employeeMembershipAction"><bean:write name="listEmployeeMembershipCount" /></span>)</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeEmergencyAction.accessAction %>">
				<li id="tabMenu9" onClick="changeTab(9,11)"><bean:message bundle="public" key="menu.table.title.emergency" />(<span id="span_employeeEmergencyAction"><bean:write name="listEmployeeEmergencyCount" /></span>)</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeLogAction.accessAction %>">
				<li id="tabMenu10" onClick="changeTab(10,11)"><bean:message bundle="public" key="menu.table.title.log" />(<span id="span_employeeLogAction"><bean:write name="listEmployeeLogCount" /></span>)</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeePositionChangeAction.accessAction %>">
				<li id="tabMenu11" onClick="changeTab(11,11)"><bean:message bundle="public" key="menu.table.title.position.change" /></li>
			</kan:auth>
		</ul>
	</div>
	<div class="tabContent">
		<%
			final StaffVO staffVO = (StaffVO) request.getAttribute("staffVO");
			final String username = (staffVO != null && staffVO.getUsername() != null && !staffVO.getUsername().equals("") && !staffVO.getUsername().equalsIgnoreCase("null")) ? staffVO.getUsername() : "";
		%>
		<div id="tabContent1" class="kantab">
			<ol class="auto">
				<li style="width: 50%">
					<label class="auto"><bean:message bundle="business" key="business.employee.position.branch"/>:</label>
					<%=KANUtil.getSelectHTML(employeeVO.getBranchs(), "branchId", "managePositionVO_branchId", null, "getPositionVOs()", null)%>
					<select class="managePositionVO_positionId" id="positionId">
						<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
					</select>
					<input type="button" onclick="addPosition();" value="<bean:message bundle="public" key="button.add" />" id="btnAddStaff" name="btnAddStaff" class="addbutton">
				</li>				
			</ol>
			<ol id="positionOL" class="auto">
				<%= PositionRender.getPositionsByStaffId( request,staffVO!=null?staffVO.getStaffId():"" ) %>
			</ol>
		</div>
		<div id="tabContent2" class="kantab" style="display: none">
			<ol class="auto">
				<li>
					<label><bean:message bundle="public" key="public.user.name" /></label> 
					<input type="text" id="username" name="username" maxlength="20" class="user_username" value='<%= username %>' /> 
					<input type="hidden" id="usernameBackup" name="usernameBackup" value="<%= username %>" />
				</li>
				<li>
					<label><bean:message bundle="public" key="public.password" /></label>
					<label style="width: 220px;">
						<a id="resetPassword" onclick="resetPassword('<%=staffVO==null?"":staffVO.getEncodedId()%>');" style="display: none" href="#"><bean:message bundle="security" key="security.staff.reset.password" /></a>
						<span><bean:message bundle="security" key="security.staff.reset.password.tips" /></span>
					</label>
				</li>
			</ol>
		</div>
		<kan:auth right="view" action="<%=EmployeeWorkAction.accessAction %>">
		<div id="tabContent3" class="kantab" style="display: none">
			<kan:auth right="new" action="<%=EmployeeWorkAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
				<span>
					<a onclick="addEmployeeWork();" id="addEmployeeWork" class="kanhandle"><bean:message bundle="public" key="link.add.employee.work" /></a>
				</span>
			</kan:auth >
			<ol class="auto">
				<logic:notEmpty name="listEmployeeWork">
					<logic:iterate id="employeeWorkVO" name="listEmployeeWork" indexId="number">
						<li>
							<kan:auth right="delete" action="<%=EmployeeWorkAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png"> 
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeWorkAction.do?proc=delete_object_ajax&employeeWorkId=<bean:write name="employeeWorkVO" property="encodedId" />', this, '#span_employeeWorkAction');" src="images/warning-btn.png"> 
							</kan:auth >
							&nbsp;&nbsp; 
							<kan:auth right="view" action="<%=EmployeeWorkAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<a onclick="link('employeeWorkAction.do?proc=to_objectModify&id=<bean:write name="employeeWorkVO" property="encodedId" />');">
							</kan:auth >
								<bean:write name="employeeWorkVO" property="remark" /> 
							<kan:auth right="view" action="<%=EmployeeWorkAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								</a>
							</kan:auth >
							<logic:equal name="employeeWorkVO" property="status" value="1">
								<span class="highlight noPosition">
							</logic:equal>
								（<bean:write name="employeeWorkVO" property="decodeStatus" />）
							<logic:equal name="employeeWorkVO" property="status" value="1">
								</span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeEducationAction.accessAction %>">
		<div id="tabContent4" class="kantab" style="display: none">
			<kan:auth right="new" action="<%=EmployeeEducationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
				<span>
					<a onclick="addEmployeeEducation();" id="addEmployeeEducation" class="kanhandle"><bean:message bundle="public" key="link.add.employee.education" /></a>
				</span>
			</kan:auth >
			<ol class="auto">
				<logic:notEmpty name="listEmployeeEducation">
					<logic:iterate id="employeeEducationVO" name="listEmployeeEducation" indexId="number">
						<li>
							<kan:auth  right="delete" action="<%=EmployeeEducationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png"> 
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeEducationAction.do?proc=delete_object_ajax&employeeEducationId=<bean:write name="employeeEducationVO" property="encodedId" />', this, '#span_employeeEducationAction');" src="images/warning-btn.png"> 
							</kan:auth >
							&nbsp;&nbsp; 
							<kan:auth  right="view" action="<%=EmployeeEducationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<a onclick="link('employeeEducationAction.do?proc=to_objectModify&id=<bean:write name="employeeEducationVO" property="encodedId" />');">
							</kan:auth >	
								<bean:write name="employeeEducationVO" property="remark" /> 
							<kan:auth right="view" action="<%=EmployeeEducationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								</a>
							</kan:auth >
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeSkillAction.accessAction %>">
		<div id="tabContent5" class="kantab" style="display: none">
			<kan:auth right="new" action="<%=EmployeeSkillAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
				<span>
					<a onclick="addEmployeeSkill();" id="addEmployeeSkill" class="kanhandle"><bean:message bundle="public" key="link.add.employee.skill" /></a>
				</span>
			</kan:auth >
			<ol class="auto">
				<logic:notEmpty name="listEmployeeSkill">
					<logic:iterate id="employeeSkillVO" name="listEmployeeSkill" indexId="number">
						<li id="mannageEmployeeSkill_6" style="margin: 5px 0px 0px 0px; width: 50%;">
							<kan:auth  right="delete" action="<%=EmployeeSkillAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png" /> 
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeSkillAction.do?proc=delete_object_ajax&employeeSkillId=<bean:write name="employeeSkillVO" property="encodedId" />', this, '#span_employeeSkillAction');" src="images/warning-btn.png" />
							</kan:auth >
							 &nbsp;&nbsp; 
							<bean:write name="employeeSkillVO" property="decodeSkillName" /> 
							<input type="hidden" name="skillIdArray" value="<bean:write name="employeeSkillVO" property="skillId" />">
						</li>
					</logic:iterate>
				</logic:notEmpty>
				<logic:empty name="listEmployeeSkill">
					<li id="mannageEmployeeSkill_6" style="margin: 0px; width: 50%;">
					</li>
				</logic:empty>
			</ol>
			<input type="hidden" id="skill" name="skill" value='' />

			<!-- Module Box HTML: Begins -->
			<div class="modal hide" id="modalId">
				<div class="modal-header">
					<a class="close" data-dismiss="modal" onclick="$('#modalId').addClass('hide');$('#shield').hide();"">×</a>
					<label><bean:message bundle="business" key="business.employee.add.skill" /></label>
				</div>
				<div class="modal-body">
					<html:form action="employeeAction.do?proc=modify_object_skill" styleClass="manageSkill_form">
						<%= BaseAction.addToken( request ) %>
						<input type="hidden" name="employeeId" value="<bean:write name="employeeForm" property="encodedId"/>">
						<input type="hidden" name="id" value="<bean:write name="employeeForm" property="encodedId"/>">
						<input type="hidden" name="subAction" class="manageSkill_form_subAction" value="">
						<input type="hidden" name="skillId" class="manageSkill_form_skillId" value="">
						<ol class="static">
							<li><bean:message bundle="business" key="business.employee.add.skill.tips" /></li>
						</ol>
						<fieldset>
							<div id="skill_level_div">
								<%= SkillRender.getSkillListOrderDiv( request, "0", skillIdArray ) %>
							</div>
						</fieldset>
					</html:form>
				</div>
				<div class="modal-footer">
					<input type="button" id="btn_AddSkill_lv" class="btn" value="<bean:message bundle="public" key="button.add" />" onclick="addSkills()" /> 
					<input type="button" id="btn_cancel_lv" name="btn_cancel_lv" class="btn reset" data-dismiss="modal" value="<bean:message bundle="public" key="button.cancel" />" />
				</div>
			</div>
			<!-- Module Box HTML: Ends -->

		</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeLanguageAction.accessAction %>">
			<div id="tabContent6" class="kantab" style="display: none">
				<kan:auth right="new" action="<%=EmployeeLanguageAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
					<span><a onclick="addEmployeeLanguage();"
						id="addEmployeeLanguage" class="kanhandle"><bean:message bundle="public" key="link.add.employee.language" /></a>
					</span>
				</kan:auth >
				<ol class="auto">
					<logic:notEmpty name="listEmployeeLanguage">
						<logic:iterate id="employeeLanguageVO" name="listEmployeeLanguage" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=EmployeeLanguageAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png"> 
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeLanguageAction.do?proc=delete_object_ajax&employeeLanguageId=<bean:write name="employeeLanguageVO" property="encodedId" />', this, '#span_employeeLanguageAction');" src="images/warning-btn.png">
								</kan:auth >
								 &nbsp;&nbsp; 
								<kan:auth right="view" action="<%=EmployeeLanguageAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">	 
									<a onclick="link('employeeLanguageAction.do?proc=to_objectModify&id=<bean:write name="employeeLanguageVO" property="encodedId" />');">
								</kan:auth >
									<bean:write name="employeeLanguageVO" property="decodeLanguageName" />
								<kan:auth  right="view" action="<%=EmployeeLanguageAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									</a>
								</kan:auth >
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeCertificationAction.accessAction %>">
		<div id="tabContent7" class="kantab" style="display: none">
			<kan:auth right="new" action="<%=EmployeeCertificationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
				<span>
					<a onclick="addEmployeeCertification();" id="addEmployeeCertification" class="kanhandle"><bean:message bundle="public" key="link.add.employee.certification" /></a>
				</span>
			</kan:auth >
			<ol class="auto">
				<logic:notEmpty name="listEmployeeCertification">
					<logic:iterate id="employeeCertificationVO" name="listEmployeeCertification" indexId="number">
						<li>
							<kan:auth right="delete" action="<%=EmployeeCertificationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png"> 
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeCertificationAction.do?proc=delete_object_ajax&employeeCertificationId=<bean:write name="employeeCertificationVO" property="encodedId" />', this, '#span_employeeCertificationAction');" src="images/warning-btn.png">
							</kan:auth >
							&nbsp;&nbsp; 
							<kan:auth right="view" action="<%=EmployeeCertificationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">	
								<a onclick="link('employeeCertificationAction.do?proc=to_objectModify&id=<bean:write name="employeeCertificationVO" property="encodedId" />');">
							</kan:auth >	
								<bean:write name="employeeCertificationVO" property="remark" /> 
							<kan:auth right="view" action="<%=EmployeeCertificationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								</a>
							</kan:auth >	
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeMembershipAction.accessAction %>">
		<div id="tabContent8" class="kantab" style="display: none">
			<kan:auth right="new" action="<%=EmployeeMembershipAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
				<span>
					<a onclick="addEmployeeMembership();" id="addEmployeeMembership" class="kanhandle"><bean:message bundle="public" key="link.add.employee.membership" /></a>
				</span>
			</kan:auth >	
			<ol class="auto">
				<logic:notEmpty name="listEmployeeMembership">
					<logic:iterate id="employeeMembershipVO" name="listEmployeeMembership" indexId="number">
						<li>
							<kan:auth right="delete" action="<%=EmployeeMembershipAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png"> 
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeMembershipAction.do?proc=delete_object_ajax&employeeMembershipId=<bean:write name="employeeMembershipVO" property="encodedId" />', this, '#span_employeeMembershipAction');" src="images/warning-btn.png">
							</kan:auth >	 
							&nbsp;&nbsp;
							<kan:auth right="view" action="<%=EmployeeMembershipAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<a onclick="link('employeeMembershipAction.do?proc=to_objectModify&id=<bean:write name="employeeMembershipVO" property="encodedId" />');">
							</kan:auth >
								<bean:write name="employeeMembershipVO" property="remark" />
							<kan:auth right="view" action="<%=EmployeeMembershipAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								</a>
							</kan:auth >
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeEmergencyAction.accessAction %>">
		<div id="tabContent9" class="kantab" style="display: none">
			<kan:auth right="new" action="<%=EmployeeEmergencyAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
			<span>
				<a onclick="addEmployeeEmergency();" id="addEmployeeEmergency" class="kanhandle"><bean:message bundle="public" key="link.add.employee.emergency" /></a>
			</span>
			</kan:auth>
			<ol class="auto">
				<logic:notEmpty name="listEmployeeEmergency">
					<logic:iterate id="employeeEmergencyVO" name="listEmployeeEmergency" indexId="number">
						<li>
							<kan:auth right="delete" action="<%=EmployeeEmergencyAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png" /> 
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeEmergencyAction.do?proc=delete_object_ajax&employeeEmergencyId=<bean:write name="employeeEmergencyVO" property="encodedId" />', this, '#span_employeeEmergencyAction');" src="images/warning-btn.png">
							</kan:auth>
							 &nbsp;&nbsp; 
							<kan:auth right="modify" action="<%=EmployeeEmergencyAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">	 
								<a href="#" onclick="link('employeeEmergencyAction.do?proc=to_objectModify&flag=2&id=<bean:write name="employeeEmergencyVO" property="encodedId" />');">
							</kan:auth>
							<bean:write name="employeeEmergencyVO" property="name" /> 
							（<bean:write name="employeeEmergencyVO" property="decodeRelationshipId" />）
							M：<bean:write name="employeeEmergencyVO" property="mobile" />
							<kan:auth right="modify" action="<%=EmployeeEmergencyAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								</a>
							</kan:auth>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeLogAction.accessAction %>">
		<div id="tabContent10" class="kantab" style="display: none">
			<kan:auth right="new" action="<%=EmployeeLogAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
			<span>
				<a onclick="addEmployeeLog();" id="addEmployeeLog" class="kanhandle"><bean:message bundle="public" key="link.add.employee.log" /></a>
			</span>
			</kan:auth>
			<ol class="auto">
				<logic:notEmpty name="listEmployeeLog">
					<logic:iterate id="employeeLogVO" name="listEmployeeLog" indexId="number">
						<li>
							<kan:auth right="delete" action="<%=EmployeeLogAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png" /> 
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeLogAction.do?proc=delete_object_ajax&employeeLogId=<bean:write name="employeeLogVO" property="encodedId" />', this, '#span_employeeLogAction');" src="images/warning-btn.png">
							</kan:auth> 
							 &nbsp;&nbsp; 
							<kan:auth right="modify" action="<%=EmployeeLogAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">	 
								<a onclick="link('employeeLogAction.do?proc=to_objectModify&flag=2&id=<bean:write name="employeeLogVO" property="encodedId" />');">
							</kan:auth>
								<bean:write name="employeeLogVO" property="decodeCreateDate" /> - <bean:write name="employeeLogVO" property="content" />
							<kan:auth right="modify" action="<%=EmployeeLogAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">	 	
						   	 </a>
						    </kan:auth>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeePositionChangeAction.accessAction %>">
			<div id="tabContent11" class="kantab" style="display: none;">
				<div id="tableWrapper" style="width: 1170px;">
					<jsp:include page="/contents/business/employee/employee/extend/listPositionChangeTable.jsp" flush="true"/> 
				</div>
			</div>
		</kan:auth>
	</div>
</div>
<kan:auth right="view" action="<%=EmployeeContractAction.getAccessAction(request, response) %>">
	<div class="kantab">
		<div>
			<kan:auth right="new" action="<%=EmployeeContractAction.getAccessAction(request, response) %>" owner="<%=employeeVO.getOwner() %>">
				<span>
					<a onclick="addEmployeeContract('2');" id="addEmployeeContract" class="kanhandle">
						<bean:message bundle="public" key="link.add.employee.contract" />
					</a>
				</span>
			</kan:auth >
		</div>
		<div class="buttom"><p/></div>
		<logic:present name="pagedListHolder">
			<html:form action="employeeAction.do?proc=list_special_info_list" styleClass="list_form" method="post" >
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="pagedListHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="pagedListHolder" property="sortOrder" />" />
				<input type="hidden" name="employeeId" id="employeeId" value="<bean:write name="employeeForm" property="employeeId" />" />
			</html:form>
		</logic:present>
		<div id="tableWrapper_employeeContractList">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->
			<jsp:include page="/contents/business/employee/employee/extend/listEmployeeContractTable.jsp" flush="true" />
		</div>
		<div class="buttom"></div>
	</div>
</kan:auth>
<input type="hidden" id="forwardURL" name="forwardURL" value="" />
<!-- 隐藏的区域存放skill -->
<input type="hidden" id="skill" name="skill" class="managePosition_skill" value="" />
<script type="text/javascript">
		/**
		* define  定义变量
		**/
		var employeeId = '<bean:write name="employeeForm" property="encodedId"/>';
	
		// 查看模式
		if(getSubAction() == 'viewObject'){
			disableForm('manage_primary_form');
		};
		
		/***
		* init 初始化相关JS
		**/
		// 去除添加代理人功能
		
		$(function(){
			
			$(".tabMenu li").first().addClass('hover');
			$(".tabMenu li").first().addClass('first');
			$("#tabContent .kantab").first().show();
			
			//	skill区域取消事件
			$('#btn_cancel_lv').click(function(){
				$('#modalId').addClass('hide');$('#shield').hide();
			});
			
			// 初始化 $("#skill")的值
			var skillStr = "";
			$("input[name=skillIdArray]").each(function(){
				skillStr += this.value+",";
			});
			skillStr = skillStr.substring(0,skillStr.lastIndexOf(","));
			$("#skill").val(skillStr);
			
			// 验证用户名是否重复 - 焦点失去事件
			$('.user_username').blur( function () { 
				// 清楚页面出错样式
				$(".user_username_error").remove();
				// 添加出错显示Label
				$('.user_username').after('<label class="error user_username_error"/>');
				// Ajax进行异步调用
				var parameters = encodeURI(encodeURI('username=' + $('.user_username').val()+'&employeeId='+ $("#employeeId").val()));
				$.ajax({ url: 'userAction.do?proc=check_object_html&'+parameters+'&date=' + new Date(), success: function(html){
					$('.user_username_error').html(html);
					
					// 如果用户名输入出现问题，Rollball此输入框
					if(html != ''){
						$('.user_username').val($('#usernameBackup').val());
						flag = flag + 1;
					}
					
				}});
			});
			
			// 验证用户名是否重复 - 键盘敲击事件
			$('.user_username').keyup( function () { 
				// 清楚页面出错样式
				$(".user_username_error").remove();
				// 添加出错显示Label
				$('.user_username').after('<label class="error user_username_error"/>');
				// Ajax进行异步调用
				var parameters = encodeURI(encodeURI('username=' + $('.user_username').val()+'&employeeId='+ $("#employeeId").val()));
				$.ajax({ url: 'userAction.do?proc=check_object_html&'+parameters+'&date=' + new Date(), success: function(html){
					$('.user_username_error').html(html);
					
					// 如果用户名输入出现问题，Rollball此输入框
					if(html != ''){
						$('.user_username').val($('#usernameBackup').val());
						flag = flag + 1;
					}
					
				}});
			});
			
		});
		
		
		/**
		* 函数列表
		**/
		function addEmployeeContract(flag){
			addEmployeeExtObj('employeeContractAction.do?proc=to_objectNew&flag='+flag+'&comeFrom=1');
		}
		
		function removeEmployeeContract(dom,employeeContractId,flag){
		if(!confirm("确定删除该"+ (flag==2?"服务协议?":"劳动合同?")) ){ return false; }
			removeEmployeeExtObj("employeeContractAction.do?proc=list_object&selectedIds=",employeeContractId,dom);
		}
		
		function addEmployeeEmergency(){
			addEmployeeExtObj('employeeEmergencyAction.do?proc=to_objectNew');
		}
		
		function addEmployeeEducation(){
			addEmployeeExtObj('employeeEducationAction.do?proc=to_objectNew');
		}
		
		function addEmployeeWork(){
			addEmployeeExtObj('employeeWorkAction.do?proc=to_objectNew');
		}
		
		function addEmployeeSkill(){
			// addEmployeeExtObj('employeeSkillAction.do?proc=to_objectNew');跳转页面自定义添加
			$('#modalId').removeClass('hide');$('#shield').show();
		}
		
		function addEmployeeLanguage(){
			addEmployeeExtObj('employeeLanguageAction.do?proc=to_objectNew');
		}
		
		function addEmployeeCertification(){
			addEmployeeExtObj('employeeCertificationAction.do?proc=to_objectNew');
		}
		
		function addEmployeeMembership(){
			addEmployeeExtObj('employeeMembershipAction.do?proc=to_objectNew');
		}
		
		function addEmployeeLog(){
			addEmployeeExtObj('employeeLogAction.do?proc=to_objectNew');
		};
		
		function addEmployeeExtObj(targetURL){
			if(employeeId==''){
				//设置重定向跳转的url
				$("#forwardURL").val(targetURL);
				//提交表单
				$('#btnEdit').click();
				
				$('.error')[0].focus();
				
			}else{
				//修改雇员，直接跳转。
				link(targetURL+'&employeeId='+employeeId);
			}
		}
		
		function removeEmployeeExtObj(targetURL,id,dom){
			//先删除、正确删除则remove掉对应的DOM 
			$.ajax({url:targetURL+encodeURI(encodeURI(id))+"&subAction=deleteObjects&page=0&r="+new Date(),
				   success:function(data, textStatus){
					   if(textStatus=='success'){
					   	$(dom).parent().remove();
					   	removeSuccess(targetURL);
					   	
					   }
				   }
			});
			
		}
		
		//如果删除成功就重新加载数目
		function removeSuccess(targetURL){
			var urlParams = targetURL.split(".");
			var operType = urlParams[0];
			var employeeId = $("#employeeId").val();
			$.post("employeeAction.do?proc=refresh_list_special_info_html",{"employeeId":employeeId,"operType":operType},function(data){
				if(operType=='employeeContractAction'){
					// 为服务协议或者是劳动合同,这个时候结果有两值
					var values = data.split(",");
					$("#"+"span_"+urlParams[0]+"1").html(values[0]);
					$("#"+"span_"+urlParams[0]+"2").html(values[1]);
				}else{
					$("#"+"span_"+urlParams[0]).html(data);
				}
			},"text");
		}
		
		/**
		* skill 弹出层对应JS
		**/
		// 根据parentSkillId获得子childSkillList
		function showNextSkillListLvl(parentSkillId){
			loadHtmlWithRecall('#skill_level_div', 'skillAction.do?proc=list_skill_order_ajax&parentSkillId=' + parentSkillId + '&employeeId=<bean:write name="employeeForm" property="encodedId"/>', null, null);
		};
		// skill区域返回上一级事件
		function pageBack(){
			var parentSkillId = $('#skill_lv_body_parentId').val();
			loadHtmlWithRecall('#skill_level_div', 'skillAction.do?proc=list_skill_order_ajax&presentSkillId=' + parentSkillId + '&employeeId=<bean:write name="employeeForm" property="encodedId"/>', null, null);
		};
		
		//	skill区域添加事件
		function addSkills(){
			$('.manageSkill_form_subAction').val('addObjects');
			submitForm('manageSkill_form');
		};
		
		// 移除规则事件
		function removeSkill(id){
			if(confirm("确定删除选中技能？")){
				$('.manageSkill_form_subAction').val('deleteObject');
				$('.manageSkill_form_skillId').val('id');
				submitForm('manageSkill_form');
			}
		}; 
		
		// 重设密码
		function resetPassword(encondeStaffId) {
			if($('#username').val()!='' && $("#email1").val()=='' && $("#email2").val()=='' ){ 
				var checkUsername = validate('email1',true, 'email', 0 ,0, 0,0);
				if(checkUsername>0){
					return;
				}
			}
			// 邮件1不为空
			else if($('#username').val()!='' && $("#email1").val()!=''){
				var checkUsername = validate('email1',true, 'email', 0 ,0, 0,0);
				if(checkUsername>0){
					return;
				}
			}
			// 邮件2不为空
			else if($('#username').val()!='' && $("#email2").val()!=''){
				var checkUsername = validate('email2',true, 'email', 0 ,0, 0,0);
				if(checkUsername>0){
					return;
				}
			}
			
			loadHtml('#messageWrapper', 'userAction.do?proc=reset_password_html&email='+$('.email1').val()+'&email2='+$('.email2').val()+'&staffId=' + encondeStaffId, false);
	    };
		
		// 添加职位Ajax
	    function addPosition() {
	    	cleanError('managePositionVO_positionId');
			if( employeeId == undefined || employeeId == '' ){
				addError('managePositionVO_positionId', '请先保存员工基本信息！');
			}else{
				var positionId = $('.managePositionVO_positionId').val();
				if( positionId == '' || positionId == '0') {
					cleanError('managePositionVO_positionId');
					addError('managePositionVO_positionId', '请选择；');
					return;
				} else {
					var positionValue = positionId + '_1_null_null';
					$("input[name=positionIdArray]").each(function(){
	 					positionValue += ',' + $(this).val();
	 				});
					$('#shield img').show();
					$('#shield').show();
					var url = "employeeAction.do?proc=modifyPosition&id=" + employeeId + "&positionValue=" + positionValue;
					loadHtml( '#positionOL', url, false, "$('#positionOL img[name^=\"disable\"]').hide();$('#positionOL img[name^=\"warning\"]').show();$('#shield img').hide();$('#shield').hide();" );
				}
				
		    	$('#branchId').val(0);
		    	$('#positionGradeId').val(0);
		    	getPositionVOs();
			}
	    };
	    
		// 移除职位事件
		function removePosition(id){
			cleanError('managePositionVO_positionId');
			if( employeeId == undefined || employeeId == '' ){
				addError('managePositionVO_positionId', '请先保存员工基本信息！');
			}else{
				if(confirm("确定删除职位绑定？")){
					$('#' + id).remove();
					// 后台删除
	 				var positionValue = "";
	 				$("input[name=positionIdArray]").each(function(){
	 					if( positionValue == '' ) {
	 						positionValue = $(this).val();
	 					}else{
	 						positionValue = positionValue + "," + $(this).val();
	 					}
	 				});
	 				$('#shield img').show();
					$('#shield').show();
	 				var url = "employeeAction.do?proc=modifyPosition&id=" + employeeId + "&positionValue=" + positionValue;
					loadHtml( '#positionOL', url, false, "$('#positionOL img[name^=\"disable\"]').hide();$('#positionOL img[name^=\"warning\"]').show();$('#shield img').hide();$('#shield').hide();" );
				}
				getPositionVOs();
			}
		}; 
		 
		// 获得职位事件
		function getPositionVOs(){
			var staffId = <%= staffVO != null ? staffVO.getStaffId() : null %>
			cleanError('managePositionVO_positionId');
			loadHtml('.managePositionVO_positionId', 'positionAction.do?proc=load_html_options_ajax&branchId=' + $('#branchId').val() + '&staffId=' + staffId, null, null );
		};
		
		// 附件提交按钮事件
		var uploadObject = createUploadObject('uploadAttachment', 'common', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE %>/<%= BaseAction.getAccountId(request, response) %>/<%=java.net.URLEncoder.encode( java.net.URLEncoder.encode(BaseAction.getUsername(request, response),"utf-8"),"utf-8") %>/');

</script>