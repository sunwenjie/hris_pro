<%@page import="com.kan.hro.web.actions.biz.performance.SelfAssessmentAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>


<div id="content">
	<div class="box searchForm toggableForm" id="bank-information">
		<div class="head">
			<label><bean:message bundle="performance" key="goal.setting" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listSelfAssessment_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="selfAssessmentAction.do?proc=list_goal" styleClass="listSelfAssessment_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="selfAssessmentHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="selfAssessmentHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="selfAssessmentHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="selfAssessmentHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.employee2.name.cn" /></label> 
							<html:text property="employeeNameZH" styleClass="search_employeeNameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.name.en" /></label> 
							<html:text property="employeeNameEN" styleClass="search_employeeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="performance" key="YERR.year" /></label> 
							<html:select property="year" styleClass="search_year">
								<html:optionsCollection property="years" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
				<div class="message warning fadable">
						<img src="images/tips.png"> <bean:message bundle="performance" key="self.assessment.not.fill.self.assessment" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
				</div>
			</div>
			<div class="top">
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/performance/table/listGoalTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/inviteAssessment.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {
		$('#menu_performance_Modules').addClass('current');
		$('#menu_performance_GoalSetting').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$(".listSelfAssessment_form input:visible, .listSelfAssessment_form textarea:visible").val('');
		$(".listSelfAssessment_form select:visible").val('0');
	};
</script>
