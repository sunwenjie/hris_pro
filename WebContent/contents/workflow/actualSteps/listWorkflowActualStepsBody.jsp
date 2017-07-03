<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.workflow.WorkflowActualVO"%>
<%@ page import="com.kan.base.web.actions.workflow.WorkflowActualStepsAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<div id="content">
	<div class="box toggableForm" id="workflowModule-information">
		<div class="head">
			<label><bean:message bundle="workflow" key="workflow.audit.object" /></label>
		</div>
		<!-- inner -->
		<div  id="searchDiv" class="inner" >
			<html:form action="workflowActualStepsAction.do?proc=modify_object" styleClass="actualSteps_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="stepId" id="stepId" value="" />
				<input type="hidden" name="status" id="status" value="" />
				<input type="hidden" name="workflowId" id="workflowId" value="<bean:write name="enCodeWorkflowId" />"/>
				<div class="top audit_form" style="display: none;">
					<kan:auth right="agree" action="<%=WorkflowActualStepsAction.ACCESSACTION%>"> 
						<input type="button" class="" name="btnAgree" value="<bean:message bundle="public" key="button.agree" />" onclick="workflowAudit(3);" /> 
					</kan:auth>
					<kan:auth right="refuse" action="<%=WorkflowActualStepsAction.ACCESSACTION%>">
						<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.refuse" />" onclick="workflowAudit(4);" />
					</kan:auth>
					<input type="button" class="reset" name="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('workflowActualAction.do?proc=list_object_unfinished');" /> 
				</div>
				<ol class="auto audit_form" style="display: none;">
					<li><label><bean:message bundle="workflow" key="workflow.audit.opinion" /></label></li>
				</ol>
				<ol class="auto audit_form" style="display: none;">
					<li><textarea rows="4" name="description"></textarea></li>
				</ol>
				<fieldset>
				<%// 动态包含 展示审批对象的jsp 页面 。 request里面有passObject 和 failObject 可提供使用
					final WorkflowActualVO workflowActualVO = (WorkflowActualVO)request.getAttribute("workflowActualVO");
					final String includeJSP = workflowActualVO.getIncludeViewObjJsp();
					boolean hasJsp = includeJSP != null && !includeJSP.isEmpty() && !"#".equals(includeJSP);
				    String tabIndex = "2";
				%>
				<div id="tab"> 
					<div class="tabMenu"> 
						<ul> 
							<% if(hasJsp){%>
							<logic:equal name="historyVO" property="remark5" value="3">
									<li id="tabMenu1" onClick="changeTab(1,3)" class="hover first"><bean:message bundle="workflow" key="workflow.audit.content.detail" />  <img title="<bean:message bundle="workflow" key="workflow.audit.content.detail.original.tips" />" src="images/tips.png"></li>
									<li id="tabMenu2" onClick="changeTab(2,3)"><bean:message bundle="workflow" key="workflow.audit.content.detail.original" /></li>
									<li id="tabMenu3" onClick="changeTab(3,3)" class="hide"><bean:message bundle="workflow" key="workflow.audit.steps" /></li> 
									<%
									tabIndex = "3";
									%>
							</logic:equal>
							
							<logic:notEqual name="historyVO" property="remark5" value="3">
								<logic:notEqual name="workflowActualVO" property="rightId" value="3">
									<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first"><bean:message bundle="workflow" key="workflow.audit.content.detail" /></li>
									<li id="tabMenu2" onClick="changeTab(2,2)" class="hide"><bean:message bundle="workflow" key="workflow.audit.steps" /></li> 
									<%
									tabIndex = "2";
									%>
								</logic:notEqual>
								
								<logic:equal name="workflowActualVO" property="rightId" value="3">
									<li id="tabMenu1" onClick="changeTab(1,3)" class="hover first"><bean:message bundle="workflow" key="workflow.audit.content.detail" />  <img title="<bean:message bundle="workflow" key="workflow.audit.content.detail.original.tips" />" src="images/tips.png"></li>
									<li id="tabMenu2" onClick="changeTab(2,3)"><bean:message bundle="workflow" key="workflow.audit.content.detail.original" /></li>
									<li id="tabMenu3" onClick="changeTab(3,3)" class="hide"><bean:message bundle="workflow" key="workflow.audit.steps" /></li> 
									<%
									tabIndex = "3";
									%>
								</logic:equal>
							</logic:notEqual>
						
							<%} %>
						</ul> 
					</div>
					
					<div class="tabContent" id="tabContent"> 
						<%// 动态包含 展示审批对象的jsp 页面 。 request里面有passObject 和 failObject 可提供使用
						if(hasJsp){
							org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, includeJSP, out, false);
						}
						%>
						<div id="tabContent<%=tabIndex %>" class="kantab" <%=hasJsp==true?"style='display: none;'":"" %>>
							<div id="tableWrapper">
								<!-- jsp:include 动态包含table对应的jsp文件 -->
								<jsp:include page="/contents/workflow/actualSteps/table/listWorkflowActualStepsTable.jsp"></jsp:include>
							</div>
						</div>
					</div> 
				</div>
				</fieldset>
				<div id="special_info"></div>
				<div id="special_info_1"></div>
				<div class="bottom"><p></div>
			</html:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_Pending').addClass('selected');
		//$('#searchDiv').hide();
		
		//显示隐藏的审批按钮
		if($("input[name=allow_audit_stepId]").length>0){
			$('.audit_form').show();
		}
		// 加载审批对象信息
		kanList_init();
		kanCheckbox_init();
		
		$('li[id^="tabMenu"]').click( function(){
			if($(this).hasClass('hide')){
				$('#special_info_1').hide();
			}else{
				$('#special_info_1').show();
			}
		});
		
	})(jQuery);

	function workflowAudit(status){
		//从列表的的隐藏input中获取stepId的值，赋值到form的stepId中
		$('.actualSteps_form input[name=stepId]').val($("input[name=allow_audit_stepId]").val());
		$('.actualSteps_form input[name=status]').val(status);
		submit('actualSteps_form');
	};
</script>