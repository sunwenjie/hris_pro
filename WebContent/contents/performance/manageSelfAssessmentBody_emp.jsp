<%@page import="com.kan.hro.domain.biz.performance.SelfAssessmentVO"%>
<%@page import="com.kan.hro.web.actions.biz.performance.SelfAssessmentAction"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%--
	员工填写自评的页面
	
	1、员工只能填写“员工自评部分”；
	2、员工在final rating时，才能看到看到主管的评价，但是只能看到（final rating）； 
	
 --%>

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
</style>

<div id="content">
	<div class="box toggableForm">
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
				<logic:empty name="selfAssessmentForm" property="encodedId">
					<input type="button" class="btnEdit" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="selfAssessmentForm" property="encodedId">
					<kan:auth right="modify" action="<%=SelfAssessmentAction.ACCESS_ACTION%>">
						<input type="button" class="btnEdit" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<input type="button" class="function" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				<kan:auth right="list" action="<%=SelfAssessmentAction.ACCESS_ACTION%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
				<logic:equal value="viewObject" property="subAction" name="selfAssessmentForm">
				    <input type="button" class="function" name="exportPDF" id="exportPDF" value="<bean:message bundle="performance" key="performance.export.pdf" />" />
				</logic:equal>
			</div>
			
			<div class="pm_title">
				<h1><bean:message bundle="performance" key="emp.self.assessment.title.one" arg0="${selfAssessmentForm.year }" /></h1>
				<span><bean:message bundle="performance" key="emp.self.assessment.preparation.period" arg0="${periodObject.startDate}" arg1="${periodObject.endDate}"/></span> 
			</div>
			
			<html:form action="selfAssessmentAction.do?proc=add_object" styleClass="manageSelfAssessment_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="id" name="id" value="<bean:write name="selfAssessmentForm" property="encodedId" />"/>
				<html:hidden property="subAction" styleClass="subAction" /> 
				<input type="hidden" name="emp_role" class="emp_role" id="emp_role" value="1" />
				<html:hidden property="selfPositionId" styleClass="selfPositionId" styleId="selfPositionId" /> 
				<html:hidden property="parentPositionId" styleClass="parentPositionId" styleId="parentPositionId" /> 
				<html:hidden property="bizLeaderPositionId" styleClass="bizLeaderPositionId" styleId="bizLeaderPositionId" /> 
				<html:hidden property="buLeaderPositionId" styleClass="buLeaderPositionId" styleId="buLeaderPositionId" /> 
				<html:hidden property="hrOwnerPositionId" styleClass="hrOwnerPositionId" styleId="hrOwnerPositionId" /> 
				<input type="hidden" name="saveCurrAndToGoalModify" class="saveCurrAndToGoalModify" id="saveCurrAndToGoalModify" value="" />
				<html:hidden property="nextYearGoals" styleClass="nextYearGoals" styleId="nextYearGoals" />
				<html:hidden property="nextYearPlans" styleClass="nextYearPlans" styleId="nextYearPlans" />
				<html:hidden property="hrOwnerPositionId" styleClass="hrOwnerPositionId" styleId="hrOwnerPositionId" />
				<html:hidden property="pm1PositionId" styleClass="pm1PositionId" styleId="pm1PositionId" /> 
				<html:hidden property="pm2PositionId" styleClass="pm2PositionId" styleId="pm2PositionId" /> 
				<html:hidden property="pm3PositionId" styleClass="pm3PositionId" styleId="pm3PositionId" /> 
				<html:hidden property="pm4PositionId" styleClass="pm4PositionId" styleId="pm4PositionId" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
<%-----------------员工基本信息----------------%>						
					<div class="pm-div" id="emp-base-info-DIV">
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
								<li id="tabMenu1" onclick="changeTab(1,2);" class="hover first"><bean:message bundle="performance" key="emp.self.assessment.tab1" /></li>
								<li id="tabMenu2" onclick="changeTab(2,2);" class="hide"><bean:message bundle="performance" key="emp.self.assessment.tab3" /></li>
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
                            <%
                               SelfAssessmentVO assessmentVO = (SelfAssessmentVO)request.getAttribute("selfAssessmentForm");
                               if(assessmentVO.getParentPositionId().equals(assessmentVO.getBuLeaderPositionId())){
                                 request.setAttribute("pageRole", "6");
                               }else{
                                 request.setAttribute("pageRole", "1");
                               }
                            %>
							<div id="tabContent2" class="kantab hide">
								<ol style="border-bottom: none;">
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd4" /></dd>
									</dl>
									<li>
									   <logic:equal value="6" name="pageRole">
										    <html:textarea property="accomplishments_bu" styleClass="accomplishments_bu" styleId="accomplishments_bu" />
									   </logic:equal>	
									   <logic:notEqual value="6" name="pageRole">
									       <html:textarea property="accomplishments_pm" styleClass="accomplishments_pm" styleId="accomplishments_pm" />
									   </logic:notEqual>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd3" /></dd>
									</dl>
									<li>
									<logic:equal value="6" name="pageRole">
										<html:textarea property="areasOfStrengths_bu" styleClass="areasOfStrengths_bu" styleId="areasOfStrengths_bu" />
									</logic:equal>	
									<logic:notEqual value="6" name="pageRole">
										<html:textarea property="areasOfStrengths_pm" styleClass="areasOfStrengths_pm" styleId="areasOfStrengths_pm" />
									</logic:notEqual>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd4" /></dd>
									</dl>
									<li>
									<logic:equal value="6" name="pageRole">
										<html:textarea property="areasOfImprovement_bu" styleClass="areasOfImprovement_bu" styleId="areasOfImprovement_bu" />
									</logic:equal>
									<logic:notEqual value="6" name="pageRole">
										<html:textarea property="areasOfImprovement_pm" styleClass="areasOfImprovement_pm" styleId="areasOfImprovement_pm" />
									</logic:notEqual>
									</li>
									
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm" />
									</h2>
									<li class="radio_li">
										<logic:equal value="6" name="pageRole">
											<label><html:radio property="isPromotion_bu" styleClass="isPromotion_bu" styleId="isPromotion_bu" value="1" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
											<label><html:radio property="isPromotion_bu" styleClass="isPromotion_bu" styleId="isPromotion_bu" value="2" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
											<label><html:radio property="isPromotion_bu" styleClass="isPromotion_bu" styleId="isPromotion_bu" value="3" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
										</logic:equal>
										<logic:notEqual value="6" name="pageRole">
										    <label><html:radio property="isPromotion_pm" styleClass="isPromotion_pm" styleId="isPromotion_pm" value="1" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
                                            <label><html:radio property="isPromotion_pm" styleClass="isPromotion_pm" styleId="isPromotion_pm" value="2" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
                                            <label><html:radio property="isPromotion_pm" styleClass="isPromotion_pm" styleId="isPromotion_pm" value="3" /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
										</logic:notEqual>
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.promotionReason_pm" /></label>
										<logic:equal value="6" name="pageRole">
										  <html:textarea property="promotionReason_bu" styleClass="promotionReason_pm" styleId="promotionReason_bu" />
										</logic:equal>
										<logic:notEqual value="6" name="pageRole">
										  <html:textarea property="promotionReason_pm" styleClass="promotionReason_pm" styleId="promotionReason_pm" />
										</logic:notEqual>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors_pm" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors_pm.tips" />" />
									</h2>
									<li>
									<logic:equal value="6" name="pageRole">
										<html:text property="successors_bu" styleClass="successors_bu" styleId="successors_bu" />
									</logic:equal>
									<logic:notEqual value="6" name="pageRole">
										<html:text property="successors_pm" styleClass="successors_pm" styleId="successors_pm" />
									</logic:notEqual>
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
							<div class="message warning">
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
							
<script type="text/javascript">
	(function($) {
		var shapeBase = new ShapeBase();	
		shapeBase.init();
		
		// 编辑、保存
		$('#btnEdit').click( function(){
			shapeBase.editAndSaveBtnClick();
		});
		
		// 提交
		$('#btnSubmit').click( function(){
			shapeBase.submitBtnClick();
		});
		
		$("#exportPDF").click(function(){
			var bakUrl = $(".manageSelfAssessment_form").attr("action");
	        $(".manageSelfAssessment_form").attr("action","selfAssessmentAction.do?proc=exportPDF&assessmentId=<bean:write name="selfAssessmentForm" property="encodedId" />");
	        $('.manageSelfAssessment_form').submit();
	        $(".manageSelfAssessment_form").attr("action",bakUrl);
		});
		
		// 已知悉
		$('#btnAcknowledge').click( function(){
			$('#status').val('6');
			// 更改Form Action
       		$('.manageSelfAssessment_form').attr('action', 'selfAssessmentAction.do?proc=modify_object');
			$('.manageSelfAssessment_form input.subAction').val('submitObject');
			enableForm('manageSelfAssessment_form');
  			submit('manageSelfAssessment_form');
		});
	})(jQuery);
	
	// 加载员工信息
	function employeeId_keyup_ajax(){
		$.ajax({
			url: 'selfAssessmentAction.do?proc=employeeId_keyup_ajax',
			type: 'POST',
			data : {employeeId:$("#employeeId").val()},
			dataType: 'json',
			async: true,
			success: function(data){
				 $.each(data,function(k,v){  
					  $("#" + k).val(v);
				 });
		
			}
		})
	};
	
	function ShapeBase(){
		this.status = $('#status').val();
		this.subAction = $('.manageSelfAssessment_form input.subAction').val();
		this.canSubmit = true;
		this.init = function(){
			// 初始化菜单样式
			$('#menu_performance_Modules').addClass('current');
			$('#menu_performance_SelfAssessment').addClass('selected');
			this.bindListBtnClick();
			// 回到顶部
			gotoTop(); 
			// 如果是新建， 触发employeeId的keyup事件，远程加载员工的基础信息
			if( this.subAction == 'createObject' ){
				employeeId_keyup_ajax();
				// 员工基本信息始终disabled
				$('#emp-base-info-DIV input, #emp-base-info-DIV select, #emp-base-info-DIV textarea').attr('disabled',true);
			}else if( this.subAction == 'viewObject' ){
				// 将Form设为Disable
				disableForm('manageSelfAssessment_form');
				// 更换按钮Value
				$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			}
			
			if(this.status == 1){
				$('#btnEdit').show();
			}else{
				$('#btnSubmit').hide();
				$('#btnEdit').hide();
			}
			
			if( this.status == 5 ){
				$('#btnAcknowledge').removeClass('hide');
			}
			
			// rating final显示
			if( this.status == 1 || this.status == 2 || this.status == 3 || this.status == 4){
				$('#tabMenu2, #tabContent2').remove();
			}
		};
		this.bindListBtnClick = function(){
			$('#btnList').click(function(){
				if (agreest())
				link('selfAssessmentAction.do?proc=list_object'); 
			});
		};
		this.editAndSaveBtnClick = function(){
			this.subAction = $('.manageSelfAssessment_form input.subAction').val();
			if( this.subAction == 'viewObject' ){
				// disabled element
				$('#tabContent1 input, #tabContent1 select, #tabContent1 textarea').attr('disabled',false);
				// 更改Subaction
        		$('.manageSelfAssessment_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.manageSelfAssessment_form').attr('action', 'selfAssessmentAction.do?proc=modify_object');
        	}else{
        		var flag = this.vilidateForm();
    			if(flag == 0){
    				enableForm('manageSelfAssessment_form');
    				submit('manageSelfAssessment_form');
    			}
        	}
		};
		this.submitBtnClick = function(){
			var flag = this.vilidateForm();
			this.canSubmit = $('#nextYearGoals').val() != '' && $('#nextYearPlans').val() != '';
			if( this.canSubmit == true ){
				if(flag == 0){
					if(confirm("<bean:message bundle='performance' key='self.assessment.confirm.submit' />")){
						if($('#id').val()!= null&& $('#id').val()!=''){
							$('.manageSelfAssessment_form').attr('action', 'selfAssessmentAction.do?proc=modify_object');
						}
						$('#status').val('2');
						$('.manageSelfAssessment_form input.subAction').val('submitObject');
						enableForm('manageSelfAssessment_form');
	    				submit('manageSelfAssessment_form');
					}
				}	
			}else{
				if( flag == 0 ){
					if(confirm("<bean:message bundle='performance' key='self.assessment.not.set.next.year.goal' />")){
						if($('#id').val()!= null&& $('#id').val()!=''){
							$('.manageSelfAssessment_form').attr('action', 'selfAssessmentAction.do?proc=modify_object');
						}
						$("#saveCurrAndToGoalModify").val(true);
						enableForm('manageSelfAssessment_form');
	    				submit('manageSelfAssessment_form');
					}
				}
			}
		};
		this.vilidateForm = function(){
			var flag = 0;
			
			flag = flag + validate("accomplishments", true, "common", 12000, 0);
			flag = flag + validate("areasOfStrengths", true, "common", 12000, 0);
			flag = flag + validate("areasOfImprovement", true, "common", 12000, 0);
			flag = flag + validate("successors", false, "common", 50, 0);
			flag = flag + validate("otherComments", false, "common", 12000, 0);
			
			if(flag > 0){
				$('.error').first().focus();
			}
			
			return flag;
		};
	};
	
</script>