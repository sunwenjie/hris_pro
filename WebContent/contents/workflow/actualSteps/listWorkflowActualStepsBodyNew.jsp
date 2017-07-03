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
				<input type="hidden" id="includeJSP" name="includeJSP" value="<bean:write name="workflowActualVO" property="includeViewObjJsp"/>" />
				<input type="hidden" id="historyId" name="historyId" value="<bean:write name="historyVO" property="historyId"/>" />
				
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
				
				<div id="tab"> 
					<div class="tabMenu">
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first"><bean:message bundle="workflow" key="workflow.audit.content.detail" /></li>
							<li id="tabMenu2" onClick="changeTab(2,2)" class="hide"><bean:message bundle="workflow" key="workflow.audit.steps" /></li> 
						</ul> 
					</div>
					<div class="tabContent" id="tabContent"> 
					
						<div id="tabContent1" class="kantab" >
							<div id="includeContent" style="overflow: auto;">
<!-- 								jsp:include 动态包含table对应的jsp文件 -->
<%-- 								<jsp:include page="/contents/workflow/actualSteps/table/listWorkflowActualStepsTable.jsp"></jsp:include> --%>
							</div>
						</div>
						<div id="tabContent2" class="kantab" style="display: none;" >
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
		workflowInclude();
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

	function workflowInclude(){
		
		var url = $("#includeJSP").val();
		url += "&historyId="+$("#historyId").val();
		if(url!=null&&url!=""){
			$.ajax({ url: url + '&date=' + new Date(), 
				success: function(html){
					$("#tabContent1").html(html);
				}
			});
		}
	}
	
	function workflowAudit(status){
		//从列表的的隐藏input中获取stepId的值，赋值到form的stepId中
		$('.actualSteps_form input[name=stepId]').val($("input[name=allow_audit_stepId]").val());
		$('.actualSteps_form input[name=status]').val(status);
		submit('actualSteps_form');
	};
</script>