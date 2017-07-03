<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.MappingHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<div class="box searchForm toggableForm" id="mappingHeader-information">
		<div class="head">
			<label><%=request.getAttribute( "flag" ).equals( "import" ) ? "导入" : "导出" %>匹配信息</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listmappingHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="mappingHeaderAction.do?proc=list_object" styleClass="listmappingHeader_form">
				<input type="hidden" name="mappingHeaderId" id="mappingHeaderId" value="" />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="mappingHeaderPagedListHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="mappingHeaderPagedListHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="mappingHeaderPagedListHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="mappingHeaderPagedListHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="flag" id="flag" value="<bean:write name="flag" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><%=request.getAttribute( "flag" ).equals( "import" ) ? "导入" : "导出" %>名称（中文）</label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchMappingHeader_nameZH" /> 							
						</li>
						<li>
							<label><%=request.getAttribute( "flag" ).equals( "import" ) ? "导入" : "导出" %>名称（英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchMappingHeader_nameEN" /> 
						</li>												
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchMappingHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- MappingHeader - information -->

	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<%String accession = (String)request.getAttribute("authAccessAction");%>
				<kan:auth right="new" action="<%=accession%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('mappingHeaderAction.do?proc=to_objectNew&flag=<bean:write name="flag" />');" />
				</kan:auth>

				<kan:auth right="delete" action="<%=accession%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listmappingHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/define/mapping/header/table/listMappingHeaderTable.jsp" flush="true"/>
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

<script type="text/javascript">
	(function($) {
		$('#menu_client_Modules').addClass('current');
		if("<bean:write name='flag' />" == "import"){
			$('#menu_client_Import').addClass('selected');
		}else{
			$('#menu_client_Export').addClass('selected');
		}
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 重置按钮事件
	function resetForm(){		
		$('.searchMappingHeader_nameZH').val('');
		$('.searchMappingHeader_nameEN').val('');
		$('.searchMappingHeader_status').val('0');
	};
</script>