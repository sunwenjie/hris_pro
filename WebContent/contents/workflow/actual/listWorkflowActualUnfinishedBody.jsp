<%@ page import="com.kan.base.web.actions.workflow.WorkflowActualStepsAction"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<div id="content">
	<div class="box searchForm toggableForm" id="workflowActual-information">
		<div class="head">
			<label><bean:message bundle="workflow" key="workflow.actual.pending.search.title" /></label>
		</div>
		<!-- Inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<html:form action="workflowActualAction.do?proc=list_object_unfinished" styleClass="listworkflowActual_form">
				<div class="top">
					<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listworkflowActual_form', 'searchObject', null, null, null, null);" />
					<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
				</div>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="workflowActualHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="workflowActualHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="workflowActualHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="workflowActualHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="stepStatus" id="stepStatus" value="" />
				<input type="hidden" name="description" id="description" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label> 
							<html:text property="contractId" maxlength="25" styleClass="search_workflowActual_contractId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name" /></logic:equal>
							</label> 
							<html:text property="chineseName" maxlength="25" styleClass="search_workflowActual_chineseName" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.short.name" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.short.name" /></logic:equal>
							</label> 
							<html:text property="shortName" maxlength="25" styleClass="search_workflowActual_shortName" /> 
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.actual.name.cn" /></label> 
							<html:text property="nameZH" maxlength="25" styleClass="search_workflowActual_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.actual.name.en" /></label> 
							<html:text property="nameEN" maxlength="25" styleClass="search_workflowActual_nameEN" /> 
						</li>
						<%-- <li>
							<label><bean:message bundle="workflow" key="workflow.actual.report.type" /></label> 
							<html:select property="rightId" styleClass="search_workflowActual_right">
								<html:optionsCollection property="rights" value="mappingId" label="mappingValue" />
							</html:select> 
						</li> --%>
						<li>
							<label><bean:message bundle="workflow" key="workflow.actual.report.time" /></label>
							<input type="text" name="createDateStr" onclick="WdatePicker()" class="Wdate" value="<bean:write name="workflowActualForm" property="createDateStr"/>"/>
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.actual.report.people" /></label> 
							<input type="text" name="thinking_staffName" id="thinking_staffName" class="thinking_staffName"/>
							<input type="hidden" name="createBy" id="createBy" class="createBy"  value="<bean:write name="workflowActualForm" property="createBy"/>"/>
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.actual.audit.status" /></label>
							<html:select property="actualStepStatus" styleClass="search_workflowActual_actualStepStatus">
								<html:optionsCollection property="actualStepStatuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
						<!-- 
						<li>
							<label>审批时间</label> 
							<input type="text" name="actualStepModifyDate" onclick="WdatePicker()" class="Wdate" value="<bean:write name="workflowActualForm" property="actualStepModifyDate"/>"/>
						</li>
						 -->
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="search_workflowActual_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
					</ol>
					
				</fieldset>
			</html:form>
		</div>
	</div>
	
	<!-- tableWrapper -->
	<div class="box noHeader" id="search-results">
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
				<kan:auth right="agree" action="<%=WorkflowActualStepsAction.ACCESSACTION%>"> 
					<input type="button" name="btnAgree" class="save" id="btnAgree" value="<bean:message bundle="public" key="button.agree" />" />
				</kan:auth>
				
				<kan:auth right="refuse" action="<%=WorkflowActualStepsAction.ACCESSACTION%>">
					<input type="button" name="btnRefuse" class="delete" id="btnRefuse" value="<bean:message bundle="public" key="button.refuse" />" />
				</kan:auth>	
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- jsp:include 动态包含table对应的jsp文件 -->
				<jsp:include page="/contents/workflow/actual/table/listWorkflowActualUnfinishedTable.jsp"></jsp:include>
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
			<!-- List Component -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/description.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {
		// 初始化菜单样式
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_Pending').addClass('selected');
		$('#searchDiv').hide();
		kanList_init();
		kanCheckbox_init();
		
		// createBy 绑定联想userID
		kanThinking_column('thinking_staffName', 'createBy', 'userAction.do?proc=list_object_json');		
		
		// 批量同意事件
		$('#btnAgree').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定同意选中？")){
					$('.listworkflowActual_form').attr('action', 'workflowActualAction.do?proc=submit_objects');
					$('#stepStatus').val('3');
					submitForm('listworkflowActual_form', "agreeObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
					$('.listworkflowActual_form').attr('action', 'workflowActualAction.do?proc=list_object_unfinished&actualStepStatus=2');
				}
			}else{
				alert("没有选中任何记录！");
			}
		});
		
		// 批量不同意事件
		$('#btnRefuse').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				popupDescription();
			}else{
				alert("没有选中任何记录！");
			}
		});
		
		// 确认退回
		$('#btnConfirm').click( function(){
			if(confirm("数据即将保存，确定不同意选中？")){ 
				$('.listworkflowActual_form input#description').val($('.tempForm #tempDescription').val());
				$('.listworkflowActual_form').attr('action', 'workflowActualAction.do?proc=submit_objects');
				$('#stepStatus').val('4');
				submitForm('listworkflowActual_form', "refuseObjects", null, null, null, 'tableWrapper');
				$('#descriptionModalId').addClass('hide');
		    	$('#shield').hide();
				$('#selectedIds').val('');
				$('.listworkflowActual_form input#description').val('');
				$('.listworkflowActual_form').attr('action', 'workflowActualAction.do?proc=list_object_unfinished&actualStepStatus=2'); 
			}
		});
	})(jQuery);

	function resetForm(){
		$('.search_workflowActual_contractId').val('');
		$('.search_workflowActual_chineseName').val('');
		$('.search_workflowActual_shortName').val('');
		$('.search_workflowActual_nameZH').val('');
		$('.search_workflowActual_nameEN').val('');
		$('.search_workflowActual_right').val('0');
		$('.search_workflowActual_status').val('');
		$('.search_workflowActual_actualStepStatus').val('');
	};
	
	function pageForward() {
		var value = Number($('#forwardPage_render').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage_render = Number($('#forwardPage_render').val()) - 1;
		submitForm('listworkflowActual_form', null, forwardPage_render, null, null, 'tableWrapper');
	};
</script>