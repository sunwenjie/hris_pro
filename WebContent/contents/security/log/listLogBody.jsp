<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="log-information">
		<div class="head">
			<label>System Log</label>
			<%
				final PagedListHolder listHolder = (PagedListHolder)request.getAttribute( "logHolder" );
				System.out.println(listHolder.getSource().size(  ));
			%>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('log_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="logAction.do?proc=list_object" styleClass="log_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="logHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="logHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="logHolder" property="page" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>Type</label> 
							 <html:select property="type" styleClass="logForm_type">
								<html:optionsCollection property="types" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>Module</label> 
							 <html:select property="module" styleClass="logForm_module">
								<html:optionsCollection property="modules" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>IP</label> 
							<html:text property="ip" maxlength="100" styleClass="logForm_ip" /> 
						</li>
						<li>
							<label>Operate By</label> 
							<html:text property="operateBy" maxlength="100" styleClass="logForm_operateBy" /> 
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- log-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include log JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/security/log/listLogTable.jsp" flush="true"/> 
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

<div id="popupWrapper">
	
</div>


<script type="text/javascript">
	(function($) {
	
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_Log').addClass('selected');
		$('#searchDiv').hide();
	})(jQuery);
	
	function formatJson(id) {
		link("logAction.do?proc=formatJson&id="+id);
	};
	
	// 搜索重置
	function resetForm(){
		$('.logForm_type').val('0');
		$('.logForm_module').val('0');
		$('.logForm_ip').val('');
		$('.logForm_operateBy').val('');
	};
</script>