<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeLogAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.hro.web.renders.biz.vendor.VendorRender"%>
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
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeSkillVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan" %>

<%
 	final EmployeeVO employeeVO = (EmployeeVO)request.getAttribute("employeeForm");
	final String[] skillIdArray = employeeVO.getSkillIdArray();
%>

<div id="attachement" class="kantab">
	<span><a name="uploadAttachment" id="uploadAttachment"
		class="kanhandle">�ϴ�����</a>
	</span>
	<div id="attachmentsDiv">
		<ol id="attachmentsOL" class="auto">
			<%= AttachmentRender.getAttachments(request, employeeVO.getAttachmentArray(), null) %>
		</ol>
	</div>
</div>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<kan:auth right="view" action="<%=EmployeeWorkAction.accessAction %>">
				<li id="tabMenu1" onClick="changeTab(1,9)" class="first hover">�������� (<span id="span_employeeWorkAction"><bean:write name="listEmployeeWorkCount"/></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeEducationAction.accessAction %>">				
				<li id="tabMenu2" onClick="changeTab(2,9)" >ѧϰ���� (<span id="span_employeeEducationAction"><bean:write name="listEmployeeEducationCount"/></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeSkillAction.accessAction %>">	
				<li id="tabMenu3" onClick="changeTab(3,9)" >���� (<span id="span_employeeSkillAction"><bean:write name="listEmployeeSkillCount"/></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeLanguageAction.accessAction %>">	
				<li id="tabMenu4" onClick="changeTab(4,9)" >�������� (<span id="span_employeeLanguageAction"><bean:write name="listEmployeeLanguageCount"/></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeCertificationAction.accessAction %>">	
				<li id="tabMenu5" onClick="changeTab(5,9)" >֤�� - ���� (<span id="span_employeeCertificationAction"><bean:write name="listEmployeeCertificationCount"/></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeMembershipAction.accessAction %>">	
				<li id="tabMenu6" onClick="changeTab(6,9)" >��� (<span id="span_employeeMembershipAction"><bean:write name="listEmployeeMembershipCount"/></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeEmergencyAction.accessAction %>">	
				<li id="tabMenu7" onClick="changeTab(7,9)" >������ϵ�� (<span id="span_employeeEmergencyAction"><bean:write name="listEmployeeEmergencyCount"/></span>) </li> 
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeEmergencyAction.accessAction %>">	
				<li id="tabMenu8" onClick="changeTab(8,9)">��¼�˻�</li>
			</kan:auth>
			<kan:auth right="view" action="<%=EmployeeLogAction.accessAction %>">	
				<li id="tabMenu9" onClick="changeTab(9,9)">��¼����(<span id="span_employeeLogAction"><bean:write name="listEmployeeLogCount"/></span>)</li>
			</kan:auth>
		</ul> 
	</div> 
	<div class="tabContent"> 
		<kan:auth right="view" action="<%=EmployeeWorkAction.accessAction %>">
			<div id="tabContent1" class="kantab" >
				<kan:auth right="new" action="<%=EmployeeWorkAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
					<span><a onclick="addEmployeeWork();" id="addEmployeeWork" class="kanhandle" >��ӹ�������</a></span>
				</kan:auth >
				<ol class="auto">
					<logic:notEmpty name="listEmployeeWork">
						<logic:iterate id="employeeWorkVO" name="listEmployeeWork" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=EmployeeWorkAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeEmployeeWork(this,'<bean:write name="employeeWorkVO" property="encodedId" />');" src="images/warning-btn.png">
								</kan:auth >
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=EmployeeWorkAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<a href="#" onclick="link('employeeWorkAction.do?proc=to_objectModify&id=<bean:write name="employeeWorkVO" property="encodedId" />');">
								</kan:auth >
								<bean:write name="employeeWorkVO" property="companyName" /> ��<bean:write name="employeeWorkVO" property="decodePositionName" />�� <logic:notEmpty name="employeeWorkVO" property="startDate" ><bean:write name="employeeWorkVO" property="startDate" /></logic:notEmpty> ~ <logic:notEmpty name="employeeWorkVO" property="endDate" ><bean:write name="employeeWorkVO" property="endDate" /></logic:notEmpty><logic:empty name="employeeWorkVO" property="endDate">����</logic:empty>
								<kan:auth  right="modify" action="<%=EmployeeWorkAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									</a>
								</kan:auth >
								<logic:equal name="employeeWorkVO" property="status" value="1"><span class="agreelight">��<bean:write name="employeeWorkVO" property="decodeStatus" />��</span></logic:equal><logic:equal name="employeeWorkVO" property="status" value="3">��<bean:write name="employeeWorkVO" property="decodeStatus" />��</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeEducationAction.accessAction %>">
			<div id="tabContent2" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=EmployeeEducationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
					<span><a onclick="addEmployeeEducation();" id="addEmployeeEducation" class="kanhandle" >���ѧϰ����</a></span>
				</kan:auth >
				<ol class="auto">
					<logic:notEmpty name="listEmployeeEducation">
						<logic:iterate id="employeeEducationVO" name="listEmployeeEducation" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=EmployeeEducationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img"  src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeEmployeeEducation(this,'<bean:write name="employeeEducationVO" property="encodedId" />');" src="images/warning-btn.png">
								</kan:auth >
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=EmployeeEducationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<a href="#" onclick="link('employeeEducationAction.do?proc=to_objectModify&id=<bean:write name="employeeEducationVO" property="encodedId" />');">
								</kan:auth >
								<bean:write name="employeeEducationVO" property="schoolName" /> ��<bean:write name="employeeEducationVO" property="decodeEducationId" />�� <logic:notEmpty name="employeeEducationVO" property="startDate" ><bean:write name="employeeEducationVO" property="startDate" /></logic:notEmpty> ~ <logic:notEmpty name="employeeEducationVO" property="endDate" ><bean:write name="employeeEducationVO" property="endDate" /></logic:notEmpty><logic:empty name="employeeEducationVO" property="endDate">����</logic:empty>
								<kan:auth  right="modify" action="<%=EmployeeEducationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									</a>
								</kan:auth >
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeSkillAction.accessAction %>">
			<div id="tabContent3" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=EmployeeSkillAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
					<span><a onclick="addEmployeeSkill();" id="addEmployeeSkill" class="kanhandle" >��Ӽ���</a></span>
				</kan:auth >
				<ol class="auto">
					<logic:notEmpty name="listEmployeeSkill">
						<logic:iterate id="employeeSkillVO" name="listEmployeeSkill" indexId="number">
							<li id="mannageEmployeeSkill_6" style="margin: 5px 0px 0px 0px;">
								<kan:auth  right="delete" action="<%=EmployeeSkillAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png" />
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeEmployeeSkill(this,'<bean:write name="employeeSkillVO" property="encodedId" />');" src="images/warning-btn.png" />
								</kan:auth >
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=EmployeeSkillAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<a href="#" onclick="link('skillAction.do?proc=to_objectModify&skillId=<bean:write name="employeeSkillVO" property="encodedSkillId" />');">
								</kan:auth >	
									<bean:write name="employeeSkillVO" property="decodeSkillName" />
								<kan:auth  right="modify" action="<%=EmployeeSkillAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									</a>
								</kan:auth >	
								<input type="hidden" name="skillIdArray" value="<bean:write name="employeeSkillVO" property="skillId" />">
							</li>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty name="listEmployeeSkill">
						<li id="mannageEmployeeSkill_6" style="margin: 0px 0px;">
						</li>
					</logic:empty>
				</ol>
				
				<!-- Module Box HTML: Begins -->
				<div class="modal hide" id="modalId">
				    <div class="modal-header">
					        <a class="close" data-dismiss="modal" onclick="$('#modalId').addClass('hide');$('#shield').hide();"">��</a>
					        <label>�������</label>
				    </div>
				    <div class="modal-body">
						<html:form action="employeeAction.do?proc=modify_object_skill" styleClass="manageSkill_form">	
				        	<%= BaseAction.addToken( request ) %>
							<input type="hidden" name="employeeId" value="<bean:write name="employeeForm" property="encodedId"/>">
							<input type="hidden" name="subAction" class="manageSkill_form_subAction" value="">
				        	<ol class="static">
			                	<li>������������ơ���ʾ��һ�������б�</li>
				            </ol>
				            <fieldset>
				                <div id="skill_level_div">
									<%= SkillRender.getSkillListOrderDiv( request, "0", skillIdArray ) %>
								</div>
				            </fieldset>
				        </html:form>
				    </div>
				    <div class="modal-footer">
				    	<input type="button" id="btn_AddSkill_lv" class="btn" value="<bean:message bundle="public" key="button.add" />" onclick="addSkills()"/>
				    	<input type="button" id="btn_cancel_lv" name="btn_cancel_lv" class="btn reset" data-dismiss="modal" value="ȡ��" />
				    </div>
				</div>
				<!-- Module Box HTML: Ends -->
				
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeLanguageAction.accessAction %>">
			<div id="tabContent4" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=EmployeeLanguageAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
					<span><a onclick="addEmployeeLanguage();" id="addEmployeeLanguage"  class="kanhandle">�����������</a></span>
				</kan:auth >
				<ol class="auto">
					<logic:notEmpty name="listEmployeeLanguage">
						<logic:iterate id="employeeLanguageVO" name="listEmployeeLanguage" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=EmployeeLanguageAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img"  src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeEmployeeLanguage(this,'<bean:write name="employeeLanguageVO" property="encodedId" />');" src="images/warning-btn.png">
								</kan:auth >
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=EmployeeLanguageAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<a href="#" onclick="link('employeeLanguageAction.do?proc=to_objectModify&id=<bean:write name="employeeLanguageVO" property="encodedId" />');">
								</kan:auth >
									<bean:write name="employeeLanguageVO" property="decodeLanguageName" />
								<kan:auth  right="modify" action="<%=EmployeeLanguageAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									</a>
								</kan:auth >
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeCertificationAction.accessAction %>">
			<div id="tabContent5" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=EmployeeCertificationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
					<span><a onclick="addEmployeeCertification();" id="addEmployeeCertification"  class="kanhandle">��ӽ���</a></span>
				</kan:auth>
				<ol class="auto">
					<logic:notEmpty name="listEmployeeCertification">
						<logic:iterate id="employeeCertificationVO" name="listEmployeeCertification" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=EmployeeCertificationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img"  src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeEmployeeCertification(this,'<bean:write name="employeeCertificationVO" property="encodedId" />');" src="images/warning-btn.png">
								</kan:auth >
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=EmployeeCertificationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<a href="#" onclick="link('employeeCertificationAction.do?proc=to_objectModify&id=<bean:write name="employeeCertificationVO" property="encodedId" />');">
								</kan:auth >
									<bean:write name="employeeCertificationVO" property="decodeCertificationName" /> <logic:notEmpty name="employeeCertificationVO" property="awardFrom" >��<bean:write name="employeeCertificationVO" property="awardFrom" />��</logic:notEmpty> <logic:notEmpty name="employeeCertificationVO" property="awardDate"><bean:write name="employeeCertificationVO" property="awardDate" /></logic:notEmpty>
								<kan:auth  right="modify" action="<%=EmployeeCertificationAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">	
									</a>
								</kan:auth >
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeMembershipAction.accessAction %>">
			<div id="tabContent6" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=EmployeeMembershipAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
					<span><a onclick="addEmployeeMembership();" id="addEmployeeMembership" class="kanhandle" >������Ż</a></span>
				</kan:auth >
				<ol class="auto">
					<logic:notEmpty name="listEmployeeMembership">
						<logic:iterate id="employeeMembershipVO" name="listEmployeeMembership" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=EmployeeMembershipAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img"  src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeEmployeeMembership(this,'<bean:write name="employeeMembershipVO" property="encodedId" />');" src="images/warning-btn.png">
								</kan:auth >	
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=EmployeeMembershipAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<a href="#" onclick="link('employeeMembershipAction.do?proc=to_objectModify&id=<bean:write name="employeeMembershipVO" property="encodedId" />');">
								</kan:auth >	
								<bean:write name="employeeMembershipVO" property="decodeMembershipName" /> <logic:notEmpty name="employeeMembershipVO" property="activeFrom"><bean:write name="employeeMembershipVO" property="activeFrom" /></logic:notEmpty> ~ <logic:notEmpty name="employeeMembershipVO" property="activeTo"><bean:write name="employeeMembershipVO" property="activeTo" /></logic:notEmpty><logic:empty name="employeeMembershipVO" property="activeTo">����</logic:empty>
								<kan:auth  right="modify" action="<%=EmployeeMembershipAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									</a>
								</kan:auth >	
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeEmergencyAction.accessAction %>">
			<div id="tabContent7" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=EmployeeEmergencyAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
					<span><a onclick="addEmployeeEmergency();" id="addEmployeeEmergency" class="kanhandle" >��ӽ�����ϵ��</a></span>
				</kan:auth >	
				<ol class="auto">
					<logic:notEmpty name="listEmployeeEmergency">
						<logic:iterate id="employeeEmergencyVO" name="listEmployeeEmergency" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=EmployeeEmergencyAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img"  src="images/disable-btn.png"/>
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeEmployeeEmergency(this,'<bean:write name="employeeEmergencyVO" property="encodedId" />');" src="images/warning-btn.png">
								</kan:auth >
								&nbsp;&nbsp;
								<kan:auth  right="modify" action="<%=EmployeeEmergencyAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									<a href="#" onclick="link('employeeEmergencyAction.do?proc=to_objectModify&flag=2&id=<bean:write name="employeeEmergencyVO" property="encodedId" />');">
								</kan:auth >
									<bean:write name="employeeEmergencyVO" property="name" /> ��<bean:write name="employeeEmergencyVO" property="decodeRelationshipId" />�� M��<bean:write name="employeeEmergencyVO" property="mobile" />
								<kan:auth  right="modify" action="<%=EmployeeEmergencyAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
									</a>
							 	</kan:auth >
							 </li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeEmergencyAction.accessAction %>">
			<div id="tabContent8" class="kantab" style="display: none">
				<ol class="auto">
					<li><label>�û���</label> <input type="text" id="username"
						name="username" maxlength="20" class="user_username"
						value='<%=employeeVO.getUsername()%>' /> <input
						type="hidden" id="usernameBackup" name="usernameBackup"
						value="<%=employeeVO.getUsername()%>" /></li>
					<li><label>����</label> <label style="width: 220px;"><a
							id="resetPassword"
							onclick="resetPassword('<%=employeeVO==null?"":employeeVO.getEncodedId()%>');"
							style="display: none" href="#">��������</a> <span>���Զ�����������˾���䡱��</span>
					</label></li>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=EmployeeLogAction.accessAction %>">
			<div id="tabContent9" class="kantab" style="display: none">
				<kan:auth right="new" action="<%=EmployeeLogAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
				<span>
					<a onclick="addEmployeeLog();" id="addEmployeeLog" class="kanhandle">�����־����</a>
				</span>
				</kan:auth>
				<ol class="auto">
					<logic:notEmpty name="listEmployeeLog">
						<logic:iterate id="employeeLogVO" name="listEmployeeLog" indexId="number">
							<li>
								<kan:auth right="delete" action="<%=EmployeeLogAction.accessAction %>" owner="<%=employeeVO.getOwner() %>">
								<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png" /> 
								<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeEmployeeLog(this,'<bean:write name="employeeLogVO" property="encodedId" />');" src="images/warning-btn.png">
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
	</div>
</div>
<logic:notEqual name="role" value="5">
	<div class="kantab">
		<div >
			<kan:auth  right="new" action="<%=EmployeeContractAction.getAccessAction(request, response) %>" owner="<%=employeeVO.getOwner() %>">
				<span><a onclick="addEmployeeContract('2');" id="addEmployeeContract" class="kanhandle" >�������Э��</a></span>
				<div class="buttom">
					<p/>
				</div>
			</kan:auth>
		</div>
		<div id="tableWrapper">
			<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/business/employee/employee/extend/listEmployeeContractTable.jsp" flush="true"/> 
		</div>
	</div>
</logic:notEqual>

<input type="hidden" id="forwardURL" name="forwardURL" value="" />
<!-- ���ص�������skill -->
<input type="hidden" id="skill" name="skill"  value='' />

<script type="text/javascript">
		/**
		* define  �������
		**/
		var employeeId = '<bean:write name="employeeForm" property="encodedId"/>';
	
		// �鿴ģʽ
		if(getSubAction() == 'viewObject'){
			
			disableForm('manage_primary_form');
		};
		
		/***
		* init ��ʼ�����JS
		**/
		$(function(){
			$(".tabMenu li").first().addClass('hover');
			$(".tabMenu li").first().addClass('first');
			$("#tabContent .kantab").first().show();
			
			//	skill����ȡ���¼�
			$('#btn_cancel_lv').click(function(){
				$('#modalId').addClass('hide');$('#shield').hide();
			});
			
			// ��ʼ�� $("#skill")��ֵ
			var skillStr = "";
			$("input[name=skillIdArray]").each(function(){
				skillStr += this.value+",";
			});
			skillStr = skillStr.substring(0,skillStr.lastIndexOf(","));
			$("#skill").val(skillStr);
			
			// ��֤�û����Ƿ��ظ� - ����ʧȥ�¼�
			$('.user_username').blur( function () { 
				if($('.user_username').val()==$('#usernameBackup').val()){
					return;
				}
				// ���ҳ�������ʽ
				$(".user_username_error").remove();
				// ��ӳ�����ʾLabel
				$('.user_username').after('<label class="error user_username_error"/>');
				// Ajax�����첽����
				var parameters = encodeURI(encodeURI('username=' + $('.user_username').val()+'&employeeId='+ $("#employeeId").val()));
				$.ajax({ url: 'employeeSecurityAction.do?proc=check_object_html&'+parameters+'&date=' + new Date(), success: function(html){
					$('.user_username_error').html(html);
					
					// ����û�������������⣬Rollball�������
					if(html != ''){
						$('.user_username').val($('#usernameBackup').val());
						flag = flag + 1;
					}
					
				}});
			});
			
			// ��֤�û����Ƿ��ظ� - �����û��¼�
			$('.user_username').keyup( function () { 
				if($('.user_username').val()==$('#usernameBackup').val()){
					return;
				}
				// ���ҳ�������ʽ
				$(".user_username_error").remove();
				// ��ӳ�����ʾLabel
				$('.user_username').after('<label class="error user_username_error"/>');
				// Ajax�����첽����
				var parameters = encodeURI(encodeURI('username=' + $('.user_username').val()+'&employeeId='+ $("#employeeId").val()));
				$.ajax({ url: 'employeeUserAction.do?proc=check_object_html&'+parameters+'&date=' + new Date(), success: function(html){
					$('.user_username_error').html(html);
					
					// ����û�������������⣬Rollball�������
					if(html != ''){
						$('.user_username').val($('#usernameBackup').val());
						flag = flag + 1;
					}
					
				}});
			});
		});
		
		/**
		* �����б�
		**/
		function addEmployeeContract(flag){
			addEmployeeExtObj('employeeContractAction.do?proc=to_objectNew&flag='+flag+'&comeFrom=1');
		}
		
		function removeEmployeeContract(dom,employeeContractId,role){
			if(!confirm("ȷ��ɾ����"+ (role==1?"����Э��?":"�Ͷ���ͬ?")) ){ return false; }
			removeEmployeeExtObj("employeeContractAction.do?proc=list_object&selectedIds=",employeeContractId,dom);
		};
		
		function addEmployeeEmergency(){
			addEmployeeExtObj('employeeEmergencyAction.do?proc=to_objectNew');
		};
		
		function removeEmployeeEmergency(dom,employeeEmergencyId){
			if(!confirm("ȷ��ɾ���ý�����ϵ��?")){ return false; }
			removeEmployeeExtObj("employeeEmergencyAction.do?proc=list_object&selectedIds=",employeeEmergencyId,dom);
		};
		
		function addEmployeeEducation(){
			addEmployeeExtObj('employeeEducationAction.do?proc=to_objectNew');
		};
		
		function removeEmployeeEducation(dom,employeeEducationId){
			if(!confirm("ȷ��ɾ���ý�������?")){ return false; }
			removeEmployeeExtObj("employeeEducationAction.do?proc=list_object&selectedIds=",employeeEducationId,dom);
		};
		
		function addEmployeeWork(){
			addEmployeeExtObj('employeeWorkAction.do?proc=to_objectNew');
		};
		
		function removeEmployeeWork(dom,employeeWorkId){
			if(!confirm("ȷ��ɾ���ù�������?")){ return false; }
			removeEmployeeExtObj("employeeWorkAction.do?proc=list_object&selectedIds=",employeeWorkId,dom);
		};
		
		function addEmployeeSkill(){
			$('#modalId').removeClass('hide');$('#shield').show();
		};
		
		function removeEmployeeSkill(dom,employeeSkillId){
			 if(!confirm("ȷ��ɾ���ü���?")){ return false; }
			 removeEmployeeExtObj("employeeSkillAction.do?proc=list_object&selectedIds=",employeeSkillId,dom);
		};
		
		function addEmployeeLanguage(){
			addEmployeeExtObj('employeeLanguageAction.do?proc=to_objectNew');
		};
		
		function removeEmployeeLanguage(dom,employeeLanguageId){
			if(!confirm("ȷ��ɾ������������?")){ return false; }
			removeEmployeeExtObj("employeeLanguageAction.do?proc=list_object&selectedIds=",employeeLanguageId,dom);
		};
		
		function addEmployeeCertification(){
			addEmployeeExtObj('employeeCertificationAction.do?proc=to_objectNew');
		};
		
		function removeEmployeeCertification(dom,employeeCertificationId){
			if(!confirm("ȷ��ɾ���ý���?")){ return false; }
			removeEmployeeExtObj("employeeCertificationAction.do?proc=list_object&selectedIds=",employeeCertificationId,dom);
		};
		
		function addEmployeeMembership(){
			addEmployeeExtObj('employeeMembershipAction.do?proc=to_objectNew');
		};
		
		function removeEmployeeMembership(dom,employeeMembershipId){
			if(!confirm("ȷ��ɾ�������Ż?")){ return false; }
			removeEmployeeExtObj("employeeMembershipAction.do?proc=list_object&selectedIds=",employeeMembershipId,dom);
		};
		
		function addEmployeeLog(){
			addEmployeeExtObj('employeeLogAction.do?proc=to_objectNew');
		};
		
		function removeEmployeeLog(dom,employeeLogId){
			if(!confirm("ȷ��ɾ������־����?")){ return false; }
			removeEmployeeExtObj("employeeLogAction.do?proc=list_object&selectedIds=",employeeLogId,dom);
		};
		
		function addEmployeeExtObj(targetURL){
			if(employeeId==''){
				// �����ض�����ת��url
				$("#forwardURL").val(targetURL);
				// �ύ��
				$('#btnEdit').click();
				// ����ؼ�����Focus
				$('.error')[0].focus();
			}else{
				//�޸Ĺ�Ա��ֱ����ת��
				link(targetURL+'&employeeId='+employeeId);
			}
		};
	
		function removeEmployeeExtObj(targetURL,id,dom){
			// ��ɾ������ȷɾ����remove����Ӧ��DOM 
			$.ajax({url:targetURL+encodeURI(encodeURI(id))+"&subAction=deleteObjects&r="+new Date(),
			   success:function(data, textStatus){
				   if(textStatus=='success'){
				   	$(dom).parent().remove();
				   	removeSuccess(targetURL);
				   }
			   }
			});
		};
		
		//���ɾ���ɹ������¼�����Ŀ
		function removeSuccess(targetURL){
			var urlParams = targetURL.split(".");
			var operType = urlParams[0];
			var employeeId = $("#employeeId").val();
			$.post("employeeAction.do?proc=refresh_list_special_info_html",{"employeeId":employeeId,"operType":operType},function(data){
				if(operType=='employeeContractAction'){
					// Ϊ����Э��������Ͷ���ͬ,���ʱ��������ֵ
					var values = data.split(",");
					$("#"+"span_"+urlParams[0]+"1").html(values[0]);
					$("#"+"span_"+urlParams[0]+"2").html(values[1]);
				}else{
					$("#"+"span_"+urlParams[0]).html(data);
				}
			},"text");
		};			
		
		/**
		* skill �������ӦJS
		**/
		// �Ƴ������¼�
		function removeSkill(id){
			if(confirm("ȷ��ɾ��ѡ�м��ܣ�")){
				$('#' + id).remove();
			}
			
			var selectedIdString = "";
			//	����skillinfo��������selectedIdString
			$('input[id="skillIdArray"]').each(function(i) {
				if(selectedIdString == ""){
					selectedIdString = $(this).val();
				}else{
					selectedIdString = selectedIdString + ',' + $(this).val();
				}
			});
			//	��skill��ֵ
			$('#skill').val(selectedIdString);
		}; 
		
		// ����parentSkillId�����childSkillList
		function showNextSkillListLvl(parentSkillId){
			loadHtmlWithRecall('#skill_level_div', 'skillAction.do?proc=list_skill_order_ajax&parentSkillId=' + parentSkillId + '&employeeId=<bean:write name="employeeForm" property="encodedId"/>', null, null);
		};
		
		// skill���򷵻���һ���¼�
		function pageBack(){
			var parentSkillId = $('#skill_lv_body_parentId').val();
			loadHtmlWithRecall('#skill_level_div', 'skillAction.do?proc=list_skill_order_ajax&presentSkillId=' + parentSkillId + '&employeeId=<bean:write name="employeeForm" property="encodedId"/>', null, null);
		};
		
		//	skill��������¼�
		function addSkills(){
			$('.manageSkill_form_subAction').val('addObjects');
			submitForm('manageSkill_form');
		};
		
		//	���skill�¼�
		function modifySkill() {
			var selectedIdArray = $("#skill").val().split(",");
			var selectedIdString = "";
			var selectedNumber = 0;

			$('input[id^="kanList_chkSelectRecord_"]').each(function(i) {
				var exist = false;
				var i = 0;

				for (i = 0; i < selectedIdArray.length; i++) {
					if (selectedIdArray[i] == $(this).val()) {
						if (this.checked) {
							exist = true;
						} 
					}
				}

				if (exist == false && this.checked) {
					selectedIdArray[i] = $(this).val();
				}
			});

			for ( var i = 0; i < selectedIdArray.length; i++) {
				if (selectedIdArray[i] != "") {
					if (selectedNumber == 0) {
						selectedIdString = selectedIdString + selectedIdArray[i];
					} else {
						selectedIdString = selectedIdString + "," + selectedIdArray[i];
					}
					selectedNumber = selectedNumber + 1;
				}
			}

			$("#skill").val(selectedIdString);
		};
		// ��������
		function resetPassword(employeeId) {
			if($('#username').val()!=''){ 
				var checkUsername = validate('email1',true, 'email', 0 ,0, 0,0);
				if(checkUsername>0){
					return;
				}
			};
			loadHtml('#messageWrapper', 'employeeSecurityAction.do?proc=reset_password_html&email='+$('.email1').val()+'&employeeId=' + employeeId, false);
	    };
	    
		// �����ύ��ť�¼�
		var uploadObject = createUploadObject('uploadAttachment', 'common', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE %>/<%= BaseAction.getAccountId(request, response) %>/<%= BaseAction.getUsername(request, response) %>/');
</script>