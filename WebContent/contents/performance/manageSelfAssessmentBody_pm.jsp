<%@page import="com.kan.hro.domain.biz.performance.SelfAssessmentVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.hro.web.actions.biz.performance.SelfAssessmentAction"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%--

	�������� 

--%>

<style type="text/css" >
	div.pm_title{ padding-top: 10px; padding-bottom: 10px; font-family: 黑体, Calibri; color: #5d5d5d; text-align: center; }
	div.pm_title span{ text-align: center; }
	.manageSelfAssessment_form #tab .tabContent table{ width: 100%; border-spacing: 0; border-collapse: collapse; border: 1px solid #dedede; margin: 10px 0px 10px 0px; }
	.manageSelfAssessment_form #tab .tabContent table th{ font-size: 13px; font-family: 黑体, Calibri; background: #217FC4; color: #fff; padding: 7px; border: 1px solid #fff; text-align: left; }
	.manageSelfAssessment_form #tab .tabContent table td{ padding: 7px; line-height: 23px; }
	.pm-div-error { color: #FF0000; width: auto; font-size: 13px; font-family: 黑体, Calibri; padding-left: 10px; border: none; background: none; }
	.manageSelfAssessment_form #tab .tabContent ol li{ width: 100%; }
	.manageSelfAssessment_form #tab .tabContent ol li textarea{ width: 98%; }
	.manageSelfAssessment_form #tab .tabContent ol h2 em{ color: #aa4935 }
	.manageSelfAssessment_form #tab .tabMenu ul li em{ color: red }
	.manageSelfAssessment_form #tab .tabContent ol li.radio_li label{ padding-left: 5px; }
	
	.red-font{ color: #C70404 }
</style>

<div id="content">
	<div id="manageBank" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="performance" key="emp.self.assessment" /></label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<%--�����༭ --%>
				<logic:empty name="editBtnHide">
					<logic:notEmpty name="selfAssessmentForm" property="encodedId">
						<kan:auth right="modify" action="<%=SelfAssessmentAction.ACCESS_ACTION%>">
							<input type="button" class="btnEdit" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
						</kan:auth>
					</logic:notEmpty>
				</logic:empty>
				
				<%--�������ձ༭ --%>
				<logic:notEmpty name="finalEditBtnShow">
					<kan:auth right="modify" action="<%=SelfAssessmentAction.ACCESS_ACTION%>">
						<input type="button" class="btnFinalEdit" name="btnFinalEdit" id="btnFinalEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				
				<%--ҵ������|�����ύ --%>
				<kan:auth right="submit" action="<%=SelfAssessmentAction.ACCESS_ACTION%>">
					<input type="button" class="function hide" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
					<input type="button" class="function hide" id="btnSubmit_bm" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				
				<input type="button" class="function hide" id="btnConfirm" value="<bean:message bundle="public" key="button.confirm" />" />
				<kan:auth right="list" action="<%=SelfAssessmentAction.ACCESS_ACTION%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			
			<div class="pm_title">
				<h1><bean:message bundle="performance" key="emp.self.assessment.title.two" arg0="${selfAssessmentForm.year}" /></h1>
				<logic:equal name="pageRole" value="2">
					<span><bean:message bundle="performance" key="emp.self.assessment.preparation.period" arg0="${periodObject.startDate_bl}" arg1="${periodObject.endDate_bl}"/></span> 
				</logic:equal>
				<logic:notEqual name="pageRole" value="2">
					<span><bean:message bundle="performance" key="emp.self.assessment.preparation.period" arg0="${periodObject.startDate_pm}" arg1="${periodObject.endDate_pm}"/></span> 
				</logic:notEqual>
			</div>
			
			<html:form action="selfAssessmentAction.do?proc=add_object" styleClass="manageSelfAssessment_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="id" name="id" value="<bean:write name="selfAssessmentForm" property="encodedId" />"/>
				<input type="hidden" name="subAction" class="subAction" id="subAction" value="<bean:write name="selfAssessmentForm" property="subAction" />">
				<input type="hidden" name="emp_role" class="empl_role" id="emp_role" value="${pageRole}" />
				<html:hidden property="parentPositionId" styleClass="parentPositionId" styleId="parentPositionId" /> 
				<html:hidden property="status_bm" styleClass="status_bm" styleId="status_bm" /> 
				<html:hidden property="status_pm" styleClass="status_pm" styleId="status_pm" /> 
				<html:hidden property="status_bu" styleClass="status_bu" styleId="status_bu" /> 
				<html:hidden property="status_pm1" styleClass="status_pm1" styleId="status_pm1" />
				<html:hidden property="status_pm2" styleClass="status_pm2" styleId="status_pm2" />
				<html:hidden property="status_pm3" styleClass="status_pm3" styleId="status_pm3" />
				<html:hidden property="status_pm4" styleClass="status_pm4" styleId="status_pm4" />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<div class="pm-div">
						<ol class="auto">
							<li>
								<label><bean:message bundle="public" key="public.employee2.id" /></label>
								<html:text property="employeeId" styleClass="employeeId" styleId="employeeId" />
							</li>
							<li>
								<label>BU/Function</label>
								<html:select property="parentBranchId" styleClass="parentBranchId" styleId="parentBranchId">
									<html:optionsCollection property="branchs" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
							<li>
								<label><bean:message bundle="public" key="public.employee2.name.cn" /></label>
								<html:text property="employeeNameZH" styleClass="employeeNameZH" styleId="employeeNameZH" />
							</li>
							<li>
								<label><bean:message bundle="public" key="public.employee2.name.en" /></label>
								<html:text property="employeeNameEN" styleClass="employeeNameEN" styleId="employeeNameEN" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="emp.self.assessment.directLeaderNameZH" /></label>
								<html:text property="directLeaderNameZH" styleClass="directLeaderNameZH" styleId="directLeaderNameZH" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="emp.self.assessment.directLeaderNameEN" /></label>
								<html:text property="directLeaderNameEN" styleClass="directLeaderNameEN" styleId="directLeaderNameEN" />
							</li>
						</ol>
						<ol class="auto">
							<li>
								<label><bean:message bundle="public" key="public.status" /></label>
								<html:select property="status" styleClass="status" styleId="status">
									<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
								</html:select>
							</li>	
						</ol>
					</div>
					
					
					
					<div id="tab"> 
						<div class="tabMenu"> 
							<ul>
								<%-- Employee --%>
								<li id="tabMenu1" class="hover first"><bean:message bundle="performance" key="emp.self.assessment.tab1" />&nbsp;<img src="images/true.png" style="width:14px;height:14px;"></li>
								
								<%-- Business leader status: SUBMITED --%>
								<logic:equal name="selfAssessmentForm" property="status_bm" value="2">
									<li id="tabMenu2"><bean:message bundle="performance" key="emp.self.assessment.tab2" />&nbsp;<img src="images/true.png" style="width:14px;height:14px;"></li>
								</logic:equal>
								
								<%-- Business leader status: TODO --%>
								<logic:notEqual name="selfAssessmentForm" property="status_bm" value="2">
									<li id="tabMenu2" style="color: red"><bean:message bundle="performance" key="emp.self.assessment.tab2" /></li>
								</logic:notEqual>
								
								<%-- Direct people manager status: SUBMITED --%>
								<logic:equal name="selfAssessmentForm" property="status_pm" value="2">
									<li id="tabMenu3"><bean:message bundle="performance" key="emp.self.assessment.tab3" />&nbsp;<img src="images/true.png" style="width:14px;height:14px;"></li>
								</logic:equal>
								
								<%-- Direct people manager status: TODO --%>
								<logic:notEqual  name="selfAssessmentForm" property="status_pm"  value="2">
									<li id="tabMenu3" style="color: red"><bean:message bundle="performance" key="emp.self.assessment.tab3" /></li>
								</logic:notEqual>
								
								<%-- BU Head status: SUBMITED --%> 
								<logic:equal name="selfAssessmentForm" property="status_bu" value="2">
									<li id="tabMenu4"><bean:message bundle="performance" key="emp.self.assessment.tab4" />&nbsp;<img src="images/true.png" style="width:14px;height:14px;"></li> 
								</logic:equal>
								
								<%-- BU Head status: TODO --%> 
								<logic:notEqual name="selfAssessmentForm" property="status_bu" value="2">
									<li id="tabMenu4" style="color: red"><bean:message bundle="performance" key="emp.self.assessment.tab4" /></li> 
								</logic:notEqual>
								
								<%-- Peers --%>
								<div id=tabMenu_XX>
									<%-- TODO Loading --%>
								</div>
							</ul> 
						</div>
				
						<div class="tabContent"> 
<%-----------------Ա������ Start----------------%>	
							<div id="tabContent1" class="kantab">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="accomplishments" styleClass="accomplishments" styleId="accomplishments" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths.dd3" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfStrengths" styleClass="areasOfStrengths" styleId="areasOfStrengths" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfImprovement" styleClass="areasOfImprovement" styleId="areasOfImprovement" />
									</li>
									
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors.tips" />" />
									</h2>
									<li>
										<html:text property="successors" styleClass="successors" styleId="successors" />
									</li>
									<h2>
										<label><bean:message bundle="performance" key="emp.self.assessment.otherComments" /></label>
									</h2>
									<li>
										<html:textarea property="otherComments" styleClass="otherComments" styleId="otherComments" />
									</li>
								</ol>
							</div>
<%-----------------Ա������ end----------------%>
<%-----------------Business Leader Start----------------%>		
							<div id="tabContent2" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="accomplishments_bm" styleClass="accomplishments_bm" styleId="accomplishments_bm" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd3" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfStrengths_bm" styleClass="areasOfStrengths_bm" styleId="areasOfStrengths_bm" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfImprovement_bm" styleClass="areasOfImprovement_bm" styleId="areasOfImprovement_bm" />
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm" />
									</h2>
									<li class="radio_li">
										<label><html:radio property="isPromotion_bm" styleClass="isPromotion_bm" styleId="isPromotion_bm" value="1" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
										<label><html:radio property="isPromotion_bm" styleClass="isPromotion_bm" styleId="isPromotion_bm" value="2" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
										<label><html:radio property="isPromotion_bm" styleClass="isPromotion_bm" styleId="isPromotion_bm" value="3" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.promotionReason_pm" /></label>
										<html:textarea property="promotionReason_bm" styleClass="promotionReason_bm" styleId="promotionReason_bm" />
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors_pm" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors_pm.tips" />" />
									</h2>
									<li>
										<html:text property="successors_bm" styleClass="successors_bm" styleId="successors_bm" />
									</li>
								</ol>
							</div>
<%-----------------Business Leader end----------------%>	
<%-----------------People Manager Start----------------%>	
							<div id="tabContent3" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="accomplishments_pm" styleClass="accomplishments_pm" styleId="accomplishments_pm" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd3" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfStrengths_pm" styleClass="areasOfStrengths_pm" styleId="areasOfStrengths_pm" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfImprovement_pm" styleClass="areasOfImprovement_pm" styleId="areasOfImprovement_pm" />
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm" />
									</h2>
									<li class="radio_li">
										<label><html:radio property="isPromotion_pm" styleClass="isPromotion_pm" styleId="isPromotion_pm" value="1" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
										<label><html:radio property="isPromotion_pm" styleClass="isPromotion_pm" styleId="isPromotion_pm" value="2" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
										<label><html:radio property="isPromotion_pm" styleClass="isPromotion_pm" styleId="isPromotion_pm" value="3" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.promotionReason_pm" /></label>
										<html:textarea property="promotionReason_pm" styleClass="promotionReason_pm" styleId="promotionReason_pm" />
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors_pm" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors_pm.tips" />" />
									</h2>
									<li>
										<html:text property="successors_pm" styleClass="successors_pm" styleId="successors_pm" />
									</li>
									
									<dl class="special_DL">
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.rating" /></h2></a></dt>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating5" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating4" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating3" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark4" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating2" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark3" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating1" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark3" /></dd>
											</dl>
										</dd>
									</dl>
									<table>
										<thead>
											<tr>
												<th></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.one" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.two" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.three" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.four" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.five" /></th>
											</tr>
										</thead>
										<tbody>
											<tr id="rating_pm_row" class="even">
												<td>
													Rating
													<%-- <bean:message bundle="performance" key="emp.self.assessment.PM.rating" /> --%>
												</td>
												<td>
													<label><html:radio property="rating_pm" styleClass="rating_pm" value="5" />5</label>
												</td>
												<td>
													<label><html:radio property="rating_pm" styleClass="rating_pm" value="4.5" />4.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_pm" styleClass="rating_pm" value="4" />4</label>
												</td>
												<td>
													<label><html:radio property="rating_pm" styleClass="rating_pm" value="3.5" />3.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_pm" styleClass="rating_pm" value="3" />3</label>
												</td>
												<td>
													<label><html:radio property="rating_pm" styleClass="rating_pm" value="2.5" />2.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_pm" styleClass="rating_pm" value="2" />2</label>
												</td>
												<td>
													<label><html:radio property="rating_pm" styleClass="rating_pm" value="1" />1</label>
												</td>
											</tr>
											<tr id="rating_final_row" class="odd">
												<td>Final Rating</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="5">
															<label><input type="radio" checked="checked">5</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="4.5">
															<label><input type="radio" checked="checked">4.5</label>
														</logic:equal>
													</logic:notEmpty>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="4">
															<label><input type="radio" checked="checked">4</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="3.5">
															<label><input type="radio" checked="checked">3.5</label>
														</logic:equal>
													</logic:notEmpty>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="3">
															<label><input type="radio" checked="checked">3</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="2.5">
															<label><input type="radio" checked="checked">2.5</label>
														</logic:equal>
													</logic:notEmpty>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="2">
															<label><input type="radio" checked="checked">2</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="1">
															<label><input type="radio" checked="checked">1</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
											</tr>
										</tbody>
									</table>
								</ol>
							</div>
<%-----------------People Manager Start----------------%>
<%-----------------BU Head Start----------------%>
							<div id="tabContent4" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="accomplishments_bu" styleClass="accomplishments_bu" styleId="accomplishments_bu" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd3" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfStrengths_bu" styleClass="areasOfStrengths_bu" styleId="areasOfStrengths_bu" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfImprovement_bu" styleClass="areasOfImprovement_bu" styleId="areasOfImprovement_bu" />
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm" />
									</h2>
									<li class="radio_li">
										<label><html:radio property="isPromotion_bu" styleClass="isPromotion_bu" styleId="isPromotion_bu" value="1" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
										<label><html:radio property="isPromotion_bu" styleClass="isPromotion_bu" styleId="isPromotion_bu" value="2" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
										<label><html:radio property="isPromotion_bu" styleClass="isPromotion_bu" styleId="isPromotion_bu" value="3" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.promotionReason_pm" /></label>
										<html:textarea property="promotionReason_bu" styleClass="promotionReason_bu" styleId="promotionReason_bu" />
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors_pm" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors_pm.tips" />" />
									</h2>
									<li>
										<html:text property="successors_bu" styleClass="successors_bu" styleId="successors_bu" />
									</li>
								
								
									<dl class="special_DL">
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.rating" /></h2></a></dt>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating5" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating4" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating3" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark4" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating2" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark3" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating1" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark3" /></dd>
											</dl>
										</dd>
									</dl>
									<table>
										<thead>
											<tr>
												<th></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.one" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.two" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.three" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.four" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.five" /></th>
											</tr>
										</thead>
										<tbody>
											<tr id="rating_bu_row" class="even">
												<td>
													Rating
													<%-- <bean:message bundle="performance" key="emp.self.assessment.BUH.rating" /> --%>
												</td>
												<td>
													<label><html:radio property="rating_bu" styleClass="rating_bu" value="5" />5</label>
												</td>
												<td>
													<label><html:radio property="rating_bu" styleClass="rating_bu" value="4.5" />4.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_bu" styleClass="rating_bu" value="4" />4</label>
												</td>
												<td>
													<label><html:radio property="rating_bu" styleClass="rating_bu" value="3.5" />3.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_bu" styleClass="rating_bu" value="3" />3</label>
												</td>
												<td>
													<label><html:radio property="rating_bu" styleClass="rating_bu" value="2.5" />2.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_bu" styleClass="rating_bu" value="2" />2</label>
												</td>
												<td>
													<label><html:radio property="rating_bu" styleClass="rating_bu" value="1" />1</label>
												</td>
											</tr>
											<tr id="rating_final_row" class="odd">
												<td>Final Rating</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="5">
															<label><input type="radio" checked="checked">5</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="4.5">
															<label><input type="radio" checked="checked">4.5</label>
														</logic:equal>
													</logic:notEmpty>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="4">
															<label><input type="radio" checked="checked">4</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="3.5">
															<label><input type="radio" checked="checked">3.5</label>
														</logic:equal>
													</logic:notEmpty>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="3">
															<label><input type="radio" checked="checked">3</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="2.5">
															<label><input type="radio" checked="checked">2.5</label>
														</logic:equal>
													</logic:notEmpty>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="2">
															<label><input type="radio" checked="checked">2</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
												<td>
													<logic:notEmpty name="selfAssessmentForm" property="rating_final">
														<logic:equal name="selfAssessmentForm" property="rating_final" value="1">
															<label><input type="radio" checked="checked">1</label>
														</logic:equal>
													</logic:notEmpty>
												</td>
											</tr>
										</tbody>
									</table>
								</ol>
							</div>
<%-----------------BU Head end----------------%>	
<%-----------------��ֱϵ���� Start----------------%>							
							<jsp:include page="/contents/performance/extends/manageSelfAssessment_non_pm.jsp" flush="false" />
							
<%-----------------���������� Start----------------%>
							<div id=tabContent_XX>
									<%-- TODO Loading --%>
							</div>
							
						</div>
					</div>
				
				</fieldset>
			</html:form>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/inviteAssessment.jsp"></jsp:include>
</div>	
							
<script type="text/javascript">
	(function($) {
		var shapeBase = new ShapeBase();
		shapeBase.init();
		
		// ���������ձ༭
		$('#btnFinalEdit').click( function() {
			shapeBase.finalEditBtnClick();
		});
		
		// �󶨱༭�����水ť�¼�
		$('#btnEdit').click( function(){
			shapeBase.editAndSaveBtnClick();
		});
		
		// �ύ
		$('#btnSubmit').click( function(){
			shapeBase.submitBtnClick();
		});
		
		// �ύ
		$('#btnSubmit_bm').click( function(){
			shapeBase.submit_bmBtnClick();
		});
		
	})(jQuery);
	
	// ����Ƿ񳬹����������
	function checkMaxInvitaion(){
		$.ajax({
			url: "inviteAssessmentAction.do?proc=checkMaxInvitaion_ajax",
			data: { "assessmentId" : $("#id").val() },
			type: "GET",
			dataType: "html",
			success: function(data){
				if(data==''){
					popupInviteBox(true);
				}else{
					alert(data);
				}
			}
		});
	};
	
	function load_invite_assessment_list_noParatemer(){
		load_invite_assessment_list( '${selfAssessmentForm.status}','${pageRole}');
	};
	
	// ajax�������������б�
	function load_invite_assessment_list( status, role ){
		var assessmentId = $("#id").val();
		$.ajax({
			url: "inviteAssessmentAction.do?proc=load_objects_ajax",
			data: { "assessmentId" : assessmentId },
			type: "GET",
			dataType: "json",
			success: function(data){
				if(data){
					var li_total = 10 + data.length;
					var tabMenu_html = "";
					var tabContent_html = "";
					for( var i in data ){
						var li_index = 10 + 1 + parseInt(i);
						tabMenu_html += '<li id="tabMenu' + li_index + '"' + (data[i].status == 1 ? " style='color: red;'" : "" ) + '>' + data[i].employeeName + ( data[i].status == 2 ? '&nbsp;<img src="images/true.png" style="width:14px;height:14px;">' : "") + '</li>';
						tabContent_html += '<div id="tabContent' + li_index + '" class="kantab hide">';
						tabContent_html += '<ol>';
						tabContent_html += '<h2><bean:message bundle="performance" key="invite.assessment.ask1" /></h2>';
						tabContent_html += '<li><textarea disabled="true">' + data[i].answer1 + '</textarea></li>';
						tabContent_html += '<h2><bean:message bundle="performance" key="invite.assessment.ask2" /></h2>';
						tabContent_html += '<li><textarea disabled="true">' + data[i].answer2 + '</textarea></li>';
						tabContent_html += '<h2><bean:message bundle="performance" key="invite.assessment.ask3" /></h2>';
						tabContent_html += '<li><textarea disabled="true">' + data[i].answer3 + '</textarea></li>';
						tabContent_html += '</ol>';
						tabContent_html += '</div>';
					}
					// ֻ�����ܲ�������
					if( status < 3 && (role == 3 || role == 6) ){
						tabMenu_html += '<li><img onclick="checkMaxInvitaion();" src="images/add.png" title="<bean:message bundle='performance' key='invite.peer.assessment' />" /></li>';
					}
					// ����ѡ��˵�
					$('#tabMenu_XX').html(tabMenu_html);
					// ����ѡ�����
					$('#tabContent_XX').html(tabContent_html);
					// ��ѡ�����¼�
					for( var i = 1; i <= li_total; i++ ){
						$('#tabMenu' + i ).attr('onClick','changeTab(' + i +', '+ li_total +')');
					}
					// ��ʾ���ܲ���ѡ�
					$('#tabMenu' + (role == 6 ? 3 : role)).trigger('click');
				}	
			}
		});
	};
	
	/*
		��ɫ
		1��Ա������
		2��ҵ������
		3��ֱ������
		4�������ϴ�
		5��HR�Խ���
		6�������ϴ��ֱ������
		7,8,9,10����ֱϵ����
	*/
	function ShapeBase(){
		this.role = '${pageRole}';
		this.status = '${selfAssessmentForm.status}';
		this.status_bm = '${selfAssessmentForm.status_bm}';
		this.init = function(){
			// ��ʼ���˵���ʽ
			$('#menu_performance_Modules').addClass('current');
			$('#menu_performance_SelfAssessment').addClass('selected');
			// �ص�����
			gotoTop(); 
			// ��Form��ΪDisable
			disableForm('manageSelfAssessment_form');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// ���б�ť�¼�
			this.bindListBtnClick();
			
			// bu�ύ���ܱ༭
			if( this.status >= 3 ){
				$('#btnEdit').remove();
			}
			
			// ֻ�����ܲ������ձ༭
			if( this.status > 5 && this.role != 3 && this.role != 6){
				$('#btnFinalEdit').remove();
			}
			
			// �����ҵ������
			if( this.role == 2 ){
				// ���ܿ���ֱ�����ܵ�����
				$('#tabMenu3,#tabMenu4').remove();
				$('#tabContent3,#tabContent4').remove();
				$('#tabMenu1').attr('onClick','changeTab(1,2)');
				$('#tabMenu2').attr('onClick','changeTab(2,2)');
				$('#tabMenu2').trigger('click');
				// ��ӱ������
				$('#tabContent2 h2:lt(4)').append('<em> *</em>');
				if( this.status_bm == 2 ){
					$('#btnEdit').remove();
				}
			}
			// ֻ��ֱ�����ܻ����ϴ���ܿ�����������Щ�ģ�������ͬ������
			if( this.role == 3){
				if('${selfAssessmentForm.status_pm}' == 2){
                    $('#btnEdit').remove();
                }
				// ��ӱ������
				$('#tabContent3 h2:lt(4)').append('<em> *</em>');
				$('#tabContent3 h2:eq(5)').append('<em> *</em>');
				// ���������б�
				load_invite_assessment_list(this.status,this.role);
			}
			// �����ϴ� || �����ϴ��ֱ������
			if( this.role == 4 || this.role == 6){
				if('${selfAssessmentForm.status_bu}' == 2){
					$('#btnEdit').remove();
				}
				// ��ӱ������
				$('#tabContent4 h2').filter(':last').append('<em> *</em>');
				// ���������б�
				load_invite_assessment_list(this.status,this.role);
			}
			// HR
			if( this.role == 5 ){
				// ���ܱ༭
				$('#btnEdit').remove();
				// ���������б�
				load_invite_assessment_list(this.status,1);
			}
			
			// ��ֱϵ����
			if( this.role == 7 || this.role == 8 || this.role == 9 || this.role == 10 ){
				$('#btnEdit').remove();
				// ���������б�
				load_invite_assessment_list(this.status,this.role);
			}
			
			// �������ܼ�ֱ������û��Ҫ��ʾֱ������ѡ�
			if( this.role == 6 ){
				// ����BU Headѡ�
				$('#tabMenu3').remove();
				$('#tabContent3').remove();
			}
			
			// Biz Leader & People Manager
			if('${selfAssessmentForm.parentPositionId}' == '${selfAssessmentForm.bizLeaderPositionId}'){
				$('#tabMenu2').remove();
				$('#tabContent2').remove();
			}
			
			// û��ϵ���ϼ�
			if( this.role == '' ){
				// ���ܱ༭
                $('#btnEdit').remove();
                // ���������б�
                load_invite_assessment_list(this.status,1);
			}
		};
		// �б�ť����¼�
		this.bindListBtnClick = function(){
			$('#btnList').click(function(){
				if (agreest())
				link('selfAssessmentAction.do?proc=list_object'); 
			});
		};
		
		// �������ձ༭
		this.finalEditBtnClick = function(){
			if($('.manageSelfAssessment_form input.subAction').val() == 'viewObject' ){
				if(this.role == 6){
					$('#tabContent4 input, #tabContent4 select, #tabContent4 textarea').attr('disabled',false);
					$('#tabMenu4').trigger('click');
					$('#rating_bu_row input').attr('disabled',true);
				}else{
				    $('#tabContent3 input, #tabContent3 select, #tabContent3 textarea').attr('disabled',false);
				    $('#tabMenu3').trigger('click');
				    $('#rating_pm_row input').attr('disabled',true);
				}
				$('#rating_final_row input').attr('disabled',true);
				// ����Subaction
        		$('.manageSelfAssessment_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnFinalEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.manageSelfAssessment_form').attr('action', 'selfAssessmentAction.do?proc=modify_object');
			}else{
        		var flag = this.validate_Form();
    			if(flag == 0){
    				enableForm('manageSelfAssessment_form');
    				submit('manageSelfAssessment_form');
    			}
        	}
		};
		// �༭��ť����¼�
		this.editAndSaveBtnClick = function(){
			if($('.manageSelfAssessment_form input.subAction').val() == 'viewObject'){
				// disabled element
				if( this.role == 2 ){
					// ��ʾ�ύ��ť
					$('#btnSubmit_bm').removeClass('hide');
					$('#tabContent2 input, #tabContent2 select, #tabContent2 textarea').attr('disabled',false);
				}else if( this.role == 3){
					// ��ʾ�ύ��ť
					$('#btnSubmit_bm').removeClass('hide');
					$('#tabContent3 input, #tabContent3 select, #tabContent3 textarea').attr('disabled',false);
					$('#rating_bu_row input').attr('disabled',true);
					$('#rating_final_row input').attr('disabled',true);
				}else if( this.role == 4 || this.role == 6 ){
					// ��ʾ�ύ��ť
					$('#btnSubmit').removeClass('hide');
					$('#tabContent4 input, #tabContent4 select, #tabContent4 textarea').attr('disabled',false);
					$('#rating_bu_row input').attr('disabled',false);
					$('#rating_final_row input').attr('disabled',true);
				}
				// �л�ѡ�
				$('#tabMenu' + this.role).trigger('click');
				// ����Subaction
        		$('.manageSelfAssessment_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.manageSelfAssessment_form').attr('action', 'selfAssessmentAction.do?proc=modify_object');
        	}else{
        		var flag = this.validate_Form();
    			if(flag == 0){
    				enableForm('manageSelfAssessment_form');
    				submit('manageSelfAssessment_form');
    			}
        	}
		};
		// �ύ��ť����¼�
		this.submitBtnClick = function(){
			if(confirm("<%=KANUtil.getProperty( request.getLocale(), "self.assessment.pm.confirm.submit", ((SelfAssessmentVO)request.getAttribute( "selfAssessmentForm" )).getEmployeeName()  )%>")){
				var flag = this.validate_Form();
				if(flag == 0){
					$('#status').val('3');
					$('#status_bu').val('2');
					$('.manageSelfAssessment_form input.subAction').val('submitObject');
					enableForm('manageSelfAssessment_form');
    				submit('manageSelfAssessment_form');
				}
			}
		};
		// �ύ��ť����¼�
		this.submit_bmBtnClick = function(){
			// �ύ
			if(confirm("<%=KANUtil.getProperty( request.getLocale(), "self.assessment.pm.confirm.submit", ((SelfAssessmentVO)request.getAttribute( "selfAssessmentForm" )).getEmployeeName()  )%>")){
				var flag = this.validate_Form();
				if(flag == 0){
					if(this.role == 2){
						$('#status_bm').val('2');
					}else if(this.role == 3){
						$('#status_pm').val('2');
					}else if(this.role == 7){
						$('#status_pm1').val('2');
					}else if(this.role == 8){
						$('#status_pm2').val('2');
					}else if(this.role == 9){
						$('#status_pm3').val('2');
					}else if(this.role == 10){
						$('#status_pm4').val('2');
					}
					
					$('.manageSelfAssessment_form input.subAction').val('submitObject');
					enableForm('manageSelfAssessment_form');
    				submit('manageSelfAssessment_form');
				}
			}
		};
		this.bindConfirmBtnClick = function(){
			// ȷ��
			$('#btnConfirm').click( function(){
				if(confirm("���Ѻ�[ " + $("#employeeNameZH").val() + "]��̸���ˣ�ȷ�����ݣ�")){
					$('#status').val('4');
					// ����Form Action
	        		$('.manageSelfAssessment_form').attr('action', 'selfAssessmentAction.do?proc=modify_object');
					$('.manageSelfAssessment_form input.subAction').val('submitObject');
					enableForm('manageSelfAssessment_form');
	   				submit('manageSelfAssessment_form');
				}
			});
		};
		// ����֤
		this.validate_Form = function(){
			var flag = 0;
			$('.pm-div-error').remove();
			if(this.role == 2){
				flag = flag + validate("accomplishments_bm", true, "common", 20000, 0);
				flag = flag + validate("areasOfStrengths_bm", true, "common", 20000, 0);
				flag = flag + validate("areasOfImprovement_bm", true, "common", 20000, 0);
				flag = flag + validate("promotionReason_bm", false, "common", 20000, 0);
				flag = flag + validate("successors_bm", false, "common", 50, 0);
			}else if(this.role == 3){
				flag = flag + validate("accomplishments_pm", true, "common", 20000, 0);
				flag = flag + validate("areasOfStrengths_pm", true, "common", 20000, 0);
				flag = flag + validate("areasOfImprovement_pm", true, "common", 20000, 0);
				flag = flag + validate("promotionReason_pm", false, "common", 20000, 0);
				flag = flag + validate("successors_pm", false, "common", 50, 0);
				var radioCheckedSize = $('input:checked[type="radio"][name="rating_pm"]').size();
				if(radioCheckedSize==0){
					flag = flag + 1;
					$('#rating_pm_row td:first').append('<label class="pm-div-error">&#8226; <bean:message bundle="performance" key="emp.self.assessment.please.check.one" /></label>');
					$('.successors_pm').focus();
				}
			}else if(this.role == 4){
				flag = flag + validate("accomplishments_bu", false, "common", 20000, 0);
				flag = flag + validate("areasOfStrengths_bu", false, "common", 20000, 0);
				flag = flag + validate("areasOfImprovement_bu", false, "common", 20000, 0);
				flag = flag + validate("promotionReason_bu", false, "common", 20000, 0);
				flag = flag + validate("successors_bu", false, "common", 50, 0);
				var radioCheckedSize = $('input:checked[type="radio"][name="rating_bu"]').size();
				if(radioCheckedSize==0){
					flag = flag + 1;
					$('#rating_bu_row td:first').append('<label class="pm-div-error">&#8226; <bean:message bundle="performance" key="emp.self.assessment.please.check.one" /></label>');
					$('.successors_bu').focus();
				}
			}
			
			if(flag > 0){
				$('.error').first().focus();
			}
			return flag;
		};
	};
</script>