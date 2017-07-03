<%@page import="java.util.Date"%>
<%@page import="com.kan.hro.web.actions.biz.performance.SelfAssessmentAction"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%--

	目标设定

--%>


<style type="text/css" >
	div.pm_title{ padding-top: 10px; padding-bottom: 10px; font-family: 榛戜綋, Calibri; color: #5d5d5d; text-align: center; }
	div.pm_title span{ text-align: center; }
	.manageSelfAssessment_form #tab .tabContent table{ width: 100%; border-spacing: 0; border-collapse: collapse; border: 1px solid #dedede; margin: 10px 0px 10px 0px; }
	.manageSelfAssessment_form #tab .tabContent table th{ font-size: 13px; font-family: 榛戜綋, Calibri; background: #217FC4; color: #fff; padding: 7px; border: 1px solid #fff; text-align: left; }
	.manageSelfAssessment_form #tab .tabContent table td{ padding: 7px; /* background: #fff; */ line-height: 23px; }
	.pm-div-error { color: #FF0000; width: auto; font-size: 13px; font-family: 榛戜綋, Calibri; padding-left: 10px; border: none; background: none; }
	.manageSelfAssessment_form #tab .tabContent ol li{ width: 100%; }
	.manageSelfAssessment_form #tab .tabContent ol li textarea{ width: 98%; }
	.manageSelfAssessment_form #tab .tabContent ol h2 em{ color: #aa4935 }
	.manageSelfAssessment_form #tab .tabContent ol li.radio_li label{ padding-left: 5px; }
</style>

<div id="content">
	<div id="manageBank" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="performance" key="goal.setting" /></label>
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
				<logic:empty name="selfAssessmentForm" property="encodedId">
					<input type="button" class="btnEdit" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="selfAssessmentForm" property="encodedId">
					<kan:auth right="modify" action="<%=SelfAssessmentAction.ACCESS_ACTION_GOAL%>">
						<input type="button" class="btnEdit" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=SelfAssessmentAction.ACCESS_ACTION_GOAL%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			
			<html:form action="selfAssessmentAction.do?proc=add_object" styleClass="manageSelfAssessment_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="id" name="id" value="<bean:write name="selfAssessmentForm" property="encodedId" />"/>
				 <%-- <html:text property="subAction" styleClass="subAction" />  --%>
				<html:hidden property="subAction" styleClass="subAction" /> 
				<input type="hidden" name="emp_role" class="empl_role" id="emp_role" value="${pageRole}" />
				<html:hidden property="parentPositionId" styleClass="parentPositionId" styleId="parentPositionId" /> 
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
								<li id="tabMenu1" onclick="changeTab(1,3);" class="hover first"><bean:message bundle="performance" key="goal.setting.tab1" /></li>
								<li id="tabMenu2" onclick="changeTab(2,3);"><bean:message bundle="performance" key="goal.setting.tab2" /></li>
								<li id="tabMenu3" onclick="changeTab(3,3);"><bean:message bundle="performance" key="goal.setting.tab3" /></li> 
							</ul> 
						</div>
				
						<div class="tabContent"> 
<%-----------------员工自评 Start----------------%>	
							<div id="tabContent1" class="kantab">
								<ol>
									<dl class="special_DL">
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans" arg0="${nextYear}" /></h2></a></dt>
										<dd class="hide"><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans.dd1" arg0="${nextYear}" /></dd>
										<dd class="hide"><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans.dd2" arg0="${nextYear}" /></dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans.smart.dt" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans.smart.dd1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans.smart.dd2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans.smart.dd3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans.smart.dd4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans.smart.dd5" /></dd>
											</dl>
										</dd>
									</dl>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.please.write" /><bean:message bundle="performance" key="emp.self.assessment.goals" /><em>* </em></label>
										<html:textarea style="width: 100%" property="nextYearGoals" styleClass="nextYearGoals" styleId="nextYearGoals" />
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.please.write" /><bean:message bundle="performance" key="emp.self.assessment.plans" /><em>* </em></label>
										<html:textarea style="width: 100%" property="nextYearPlans" styleClass="nextYearPlans" styleId="nextYearPlans" />
									</li>
								</ol>
							</div>
<%-----------------员工自评 end----------------%>
<%-----------------Business Leader Start----------------%>		
							<div id="tabContent2" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans_pm" arg0="${nextYear}" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans_pm.dd2" /></dd>
									</dl>
									<li>
										<label style="width: 100%"><bean:message bundle="performance" key="emp.self.assessment.please.write" /><em>* </em>
										<bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans_pm.tips" /></label>
										<html:textarea style="width: 100%" property="nextYearGoalsAndPlans_bm" styleClass="nextYearGoalsAndPlans_bm" styleId="nextYearGoalsAndPlans_bm" />
									</li>
								</ol>
							</div>
<%-----------------Business Leader end----------------%>	
<%-----------------People Manager Start----------------%>	
							<div id="tabContent3" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans_pm" arg0="${nextYear}" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans_pm.dd2" /></dd>
									</dl>
									<li>
										<label style="width: 100%"><bean:message bundle="performance" key="emp.self.assessment.please.write" /><em>* </em>
										<bean:message bundle="performance" key="emp.self.assessment.nextYearGoalsAndPlans_pm.tips" /></label>
										<html:textarea style="width: 100%" property="nextYearGoalsAndPlans_pm" styleClass="nextYearGoalsAndPlans_pm" styleId="nextYearGoalsAndPlans_pm" />
									</li>
								</ol>
							</div>
<%-----------------People Manager Start----------------%>
							
						</div>
					</div>
				
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		
		var shapeBase = new ShapeBase();
		shapeBase.init();
		
		// 列表
		$('#btnList').click(function(){
			shapeBase.btnListClick();
		});
		
		// 编辑、保存
		$('#btnEdit').click( function(){
			shapeBase.btnEditClick();
		});
		
	})(jQuery);
	
	
	/*
		角色
		1、员工本人
		2、业务主管
		3、直线主管
		4、部门老大
		5、HR对接人
		6、部门老大兼直线主管
	*/
	function ShapeBase(){
		
		this.role = '${pageRole}';
		this.init = function(){
			// 初始化菜单样式
			$('#menu_performance_Modules').addClass('current');
			$('#menu_performance_GoalSetting').addClass('selected');
			// 回到顶部
			gotoTop(); 
			// disabled整个form表单
			disableForm('manageSelfAssessment_form');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			
			// 员工不能看到选项卡tabContent2和tabContent3
			if( this.role == 1 ){
				$('#tabMenu2, #tabMenu3').remove();
				$('#tabContent2, #tabContent3').remove();
			}
			
			// 部门老大和HR对接人不具备编辑权限
			if( this.role == 4 || this.role == 5 ){
				$('#btnEdit').remove();
			}
			
			// 根据角色切换对象的选项卡
			if( this.role == 2 ) {
				$('#tabMenu2').trigger('click');
			} else if( this.role == 3 || this.role == 6 ){
				$('#tabMenu3').trigger('click');
			}
		};
		// 列表按钮点击事件
		this.btnListClick = function(){
			if (agreest())
				link('selfAssessmentAction.do?proc=list_goal'); 
		};
		// 验证表单
		this.validateForm = function(){
			var flag = 0;
			if( this.role == 1 ){
				flag = flag + validate("nextYearGoals", true, "common", 50000, 0);
				flag = flag + validate("nextYearPlans", true, "common", 50000, 0);
			}else if( this.role == 2 ){
				flag = flag + validate("nextYearGoalsAndPlans_bm", true, "common", 50000, 0);
			}else if( this.role == 3 ){
				flag = flag + validate("nextYearGoalsAndPlans_pm", true, "common", 50000, 0);
			}
			
			if(flag > 0){
				$('.error').first().focus();
			}
			return flag;
		};
		// 编辑按钮点击事件
		this.btnEditClick = function(){
			var currRole = this.role == 6 ? 3 : this.role;
			if( $('.manageSelfAssessment_form input.subAction').val() == 'viewObject'){
				// disabled element
        		$('#tabContent' + currRole + ' input, #tabContent' + currRole + ' select, #tabContent' + currRole + ' textarea').attr('disabled',false);
        		$('#tabMenu' + currRole).trigger('click');
				// 更改Subaction
        		$('.manageSelfAssessment_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.manageSelfAssessment_form').attr('action', 'selfAssessmentAction.do?proc=modify_goal');
        	}else{
        		var flag = this.validateForm();
    			if(flag == 0){
    				enableForm('manageSelfAssessment_form');
    				submit('manageSelfAssessment_form');
    			}
        	}
		};
	};
</script>