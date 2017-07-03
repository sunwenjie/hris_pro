<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="table-information">
		<div class="head">
			<label>模块字典信息</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listtable_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="tableRelationAction.do?proc=list_object" styleClass="listtable_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="tablePagedListHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="tablePagedListHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="tablePagedListHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="tablePagedListHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>模块字典名称（中文）</label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchtable_nameZH" /> 
							<input type="hidden" name="tableId" id="tableId" value="" />
						</li>
						<li>
							<label>模块字典名称（英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchtable_nameEN" /> 
						</li>
						<li>
							<label>类型</label> 
							<html:select property="tableType" styleClass="searchtable_tableType">
								<html:optionsCollection property="tableTypes" value="mappingId" label="mappingValue" />
							</html:select>						
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- Table-information -->

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
				<logic:present name="accountId">
					<logic:equal name="accountId" value="1">
						<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('tableRelationAction.do?proc=to_objectNew');" /> 
					</logic:equal>
				</logic:present>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/define/tableRelation/table/listTableRelationTable.jsp" flush="true"/> 
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
		$('#menu_define_Modules').addClass('current');
		$('#menu_define_Table').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 重置按钮
	function resetForm(){		
		$('.searchtable_nameZH').val('');
		$('.searchtable_nameEN').val('');
		$('.searchtable_tableType').val('0');
		$('.searchuser_status').val('0');
	};
	
	// 移除已选定字段
	function removeColumn( tableRelationId ){
		if(confirm('确认删除？')){ 
			loadHtml('#tableWrapper', 'tableRelationAction.do?proc=to_objectModify&subAction=deleteObjects&tableRelationId=' +tableRelationId + '&ajax=true', false);
		}
	};
	
	// 点击import Detail 的链接，Ajax调用修改页面	
	function tableRelationModify(tableRelationId)
	{
		loadHtmlWithRecall('#detailForm', 'tableRelationAction.do?proc=to_objectModify_ajax&tableRelationId=' + tableRelationId, true, null);  
	};
</script>