<%@page import="com.kan.hro.web.actions.biz.performance.SelfAssessmentAction"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%--
	
	不用这个jsp了
	
 --%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="GBK">
<style type="text/css" >
	div.pm_title{ padding-top: 10px; padding-bottom: 10px; font-family: 榛戜綋, Calibri; color: #5d5d5d; text-align: center; }
	div.pm_title span{ text-align: center; }
	.manageSelfAssessment_form #tab .tabContent table{ width: 100%; border-spacing: 0; border-collapse: collapse; border: 1px solid #dedede; margin: 10px 0px 10px 0px; }
	.manageSelfAssessment_form #tab .tabContent table th{ font-size: 13px; font-family: 榛戜綋, Calibri; background: #217FC4; color: #fff; padding: 7px; border: 1px solid #fff; text-align: left; }
	.manageSelfAssessment_form #tab .tabContent table td{ padding: 7px; line-height: 23px; }
	.pm-div-error { color: #FF0000; width: auto; font-size: 13px; font-family: 榛戜綋, Calibri; padding-left: 10px; border: none; background: none; }
	.manageSelfAssessment_form #tab .tabContent ol li{ width: 100%; }
	.manageSelfAssessment_form #tab .tabContent ol li textarea{ width: 98%; }
	.manageSelfAssessment_form #tab .tabContent ol h2 em{ color: #aa4935 }
	.manageSelfAssessment_form #tab .tabContent ol li.radio_li label{ padding-left: 5px; }
	body,.box{margin: 0px!important;}
	.auto li label{width: 200px;!important;height:23px;}
</style>
<link href="css/kanpower.css" rel="stylesheet">
</head>
<body>
<div id="content1">
	<div class="box toggableForm">
		<div class="inner">
			<div class="pm_title">
				<h1><bean:message bundle="performance" key="emp.self.assessment.title.one" arg0="${selfAssessmentForm.year }" /></h1>
				<span><bean:message bundle="performance" key="emp.self.assessment.preparation.period" arg0="${periodObject.startDate}" arg1="${periodObject.endDate}"/></span> 
			</div>
			
			<html:form action="selfAssessmentAction.do?proc=exportPDF" styleClass="manageSelfAssessment_form">
				<fieldset>
<%-----------------员工基本信息----------------%>						
					<div class="pm-div" id="emp-base-info-DIV">
						<ol class="auto">
							<li>
								<label><strong><bean:message bundle="public" key="public.employee2.id" /></strong></label>
								<label>${selfAssessmentForm.employeeId}</label>
							</li>
							<li>
								<label><strong>BU/Function</strong></label>
								<label>${selfAssessmentForm.decodeParentBranchId}</label>
							</li>
							<li>
								<label><strong><bean:message bundle="public" key="public.employee2.name.cn" /></strong></label>
								<label>${selfAssessmentForm.employeeNameZH}</label>
							</li>
							<li>
								<label><strong><bean:message bundle="public" key="public.employee2.name.en" /></strong></label>
								<label>${selfAssessmentForm.employeeNameEN}</label>
							</li>
							<li>
								<label><strong><bean:message bundle="performance" key="emp.self.assessment.directLeaderNameZH" /></strong></label>
								<label>${selfAssessmentForm.directLeaderNameZH}</label>
							</li>
							<li>
								<label><strong><bean:message bundle="performance" key="emp.self.assessment.directLeaderNameEN" /></strong></label>
								<label>${selfAssessmentForm.directLeaderNameEN}</label>
							</li>
							<li>
                                <label><strong><bean:message bundle="public" key="public.status" /></strong></label>
                                <label>${selfAssessmentForm.decodeStatus}</label>
                            </li> 
						</ol>
					</div>
					
					<div id="tab"> 
						<div class="tabMenu"> 
							<ul>
								<li id="tabMenu1" onclick="changeTab(1,2);" class="hover first"><bean:message bundle="performance" key="emp.self.assessment.tab1" /></li>
							</ul> 
						</div>
				
						<div class="tabContent"> 
<%-----------------员工自评 Start----------------%>	
							<div id="tabContent1" class="kantab">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /><em> *</em></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments.dd4" /></dd>
									</dl>
									<li>
										<html:textarea property="accomplishments" styleClass="accomplishments" styleId="accomplishments" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths" /><em> *</em></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths.dd3" /></dd>
									</dl>
									<li>
										<html:textarea property="areasOfStrengths" styleClass="areasOfStrengths" styleId="areasOfStrengths" />
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement" /><em> *</em></h2></a></dt>
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
<%-----------------员工自评 end----------------%>
                                

<%-----------------People Manager Start----------------%>
                            <div id="tab"> 
		                        <div class="tabMenu"> 
		                            <ul>
		                                <li id="tabMenu2" class="hover first"><bean:message bundle="performance" key="emp.self.assessment.tab3" /></li>
		                            </ul> 
		                        </div>
		                     </div>
							<div id="tabContent2" class="kantab">
								<ol style="border-bottom: none;">
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
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.rating" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="ss5" />" />
									</h2>
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
											<tr id="rating_final_row" class="odd">
												<td><bean:message bundle="performance" key="emp.self.assessment.final.rating" /></td>
												<td>
													<label><html:radio property="rating_final" styleClass="rating_final" value="5" />5</label>
												</td>
												<td>
													<label><html:radio property="rating_final" styleClass="rating_final" value="4.5" />4.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_final" styleClass="rating_final" value="4" />4</label>
												</td>
												<td>
													<label><html:radio property="rating_final" styleClass="rating_final" value="3.5" />3.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_final" styleClass="rating_final" value="3" />3</label>
												</td>
												<td>
													<label><html:radio property="rating_final" styleClass="rating_final" value="2.5" />2.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><html:radio property="rating_final" styleClass="rating_final" value="2" />2</label>
												</td>
												<td>
													<label><html:radio property="rating_final" styleClass="rating_final" value="1" />1</label>
												</td>
											</tr>
										</tbody>
									</table>
								</ol>
							</div>
<%-----------------People Manager Start----------------%>
						</div>
					</div>
					
					<logic:equal name="selfAssessmentForm" property="status" value="5">
						<div class="bottom">
							<div class="message warning fadable">
								<input type="button" class="function hide" id="btnAcknowledge" value="<bean:message bundle="public" key="button.acknowledge" />" />
								<bean:message bundle="performance" key="self.assessment.btnAcknowledge.tips" />
							</div>
						</div>
					</logic:equal>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
<div id="footer">
    <p>&copy;iClick</p>
</div> 

</body>
</html>
