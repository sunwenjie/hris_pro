<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="listHeaderAction.do?proc=add_object" styleClass="listHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="listHeaderId" name="listHeaderId" value="<bean:write name="listHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="listHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.search.header.use.java.object" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.header.use.java.object.tips" />" /></label> 	
				<html:checkbox property="useJavaObject" styleClass="manageListHeader_useJavaObject" />
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.header.parent.list" />   <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.header.parent.list.tips" />" /></label> 	
				<html:select property="parentId" styleClass="manageListHeader_parentId">
					<html:optionsCollection property="parents" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li style="display: none;" id="javaObjectNameLI">
				<label><bean:message bundle="define" key="define.search.header.java.object.name" /><em> *</em></label> 	
				<html:text property="javaObjectName" maxlength="100" styleClass="manageListHeader_javaObjectName" /> 	
			</li>
			<li id="tableIdLI">
				<label><bean:message bundle="define" key="define.table" /><em> *</em></label> 	
				<html:select property="tableId" styleClass="manageListHeader_tableId">
					<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
				</html:select>		
			</li>
			<li>
				<label><bean:message bundle="define" key="define.search" /></label> 
				<html:select property="searchId" styleClass="manageListHeader_searchId">
					<html:optionsCollection property="searchHeaders" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageListHeader_nameZH" /> 					
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageListHeader_nameEN" />
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.list.header.use.pagination" /></label> 
				<html:checkbox property="usePagination" styleClass="manageListHeader_usePagination" />	
			</li>
		</ol>	
		<ol id="paginationDetailOl" class="auto">
			<li>
				<label><bean:message bundle="define" key="define.list.header.page.size" /><em> *</em></label> 
				<html:text property="pageSize" maxlength="2" styleClass="manageListHeader_pageSize" />
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.header.load.pages" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.header.load.pages.tips" />" /></label> 
				<html:text property="loadPages" maxlength="1" styleClass="manageListHeader_loadPages" />
			</li>
		</ol>	
		<ol class="auto">					
			<li>
				<label><bean:message bundle="define" key="define.list.header.search.first" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.header.search.first.tips" />" /><em>* </em></label> 
				<html:select property="isSearchFirst" styleClass="manageListHeader_isSearchFirst">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>	
			<li>
				<label><bean:message bundle="define" key="define.list.header.export.report" /></label> 
				<html:select property="exportExcel" styleClass="manageListHeader_exportExcel">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" cols="3" styleClass="manageListHeader_description"></html:textarea>
			</li>																
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageListHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		switchPaginationDetailOl();
		switchUseJavaObjectLi();
		
		validate_manage_primary_form = function() {
		    var flag = 0;
		    
			// 如果使用JAVA对象
			if($('.manageListHeader_useJavaObject').is(':checked')){
				flag = flag + validate('manageListHeader_javaObjectName', true, 'common', 100, 0);
			}else{
				flag = flag + validate('manageListHeader_tableId', true, 'select', 0, 0);
			}
		
			flag = flag + validate('manageListHeader_nameZH', true, 'common', 100, 0);			
			
			// 选择需要分页才验证以下两项
			if($('.manageListHeader_usePagination').is(':checked')){
				flag = flag + validate('manageListHeader_pageSize', true, 'numeric', 2, 0);
				flag = flag + validate('manageListHeader_loadPages', false, 'numeric', 1, 0);
			}	
			flag = flag + validate('manageListHeader_isSearchFirst', true, 'select', 0, 0);
			flag = flag + validate('manageListHeader_status', true, 'select', 0, 0);
			flag = flag + validate('manageListHeader_description', false, 'common', 500, 0);
		    
		    return flag;
		};
		
		// 使用分页字段点击事件 
		$('.manageListHeader_usePagination').click(function(){
			switchPaginationDetailOl();
	    });
		
		// 使用java对象点击事件 
		$('.manageListHeader_useJavaObject').click(function(){
			switchUseJavaObjectLi();
	    });
		
		// 表 - 视图控件Change事件
		$('.manageListHeader_tableId').change(function(){
			loadHtml('.manageListHeader_searchId', 'searchHeaderAction.do?proc=list_object_options_ajax&tableId=' + $('.manageListHeader_tableId').val(), false);
		});
	})(jQuery);
	
	function switchPaginationDetailOl(){
		if($('.manageListHeader_usePagination').is(':checked')) {
        	$('#paginationDetailOl').show();
        } else {
        	$('#paginationDetailOl').hide();
        }
	};
	
	function switchUseJavaObjectLi(){
		if($('.manageListHeader_useJavaObject').is(':checked')) {
			$('.manageListHeader_parentId').removeAttr('disabled');
			$('.manageListHeader_tableId').val('0');
			$('#tableIdLI').hide();
        	$('#javaObjectNameLI').show();
        } else {
        	$('.manageListHeader_parentId').val('0');
        	$('#javaObjectNameLI').hide();
        	$('#tableIdLI').show();
        	$('.manageListHeader_parentId').attr('disabled','disabled');
        }
	};
</script>