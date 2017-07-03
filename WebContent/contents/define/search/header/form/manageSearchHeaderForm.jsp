<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="searchHeaderAction.do?proc=add_object" styleClass="searchHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="searchHeaderId" name="id" class="manageSearchHeader_searchHeaderId" value="<bean:write name="searchHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" value="<bean:write name="searchHeaderForm" property="subAction"/>" />	
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.search.header.use.java.object" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.header.use.java.object.tips" />" /></label> 	
				<html:checkbox property="useJavaObject" styleClass="manageSearchHeader_useJavaObject" />
			</li>
			<li style="display: none;" id="javaObjectNameLI">
				<label><bean:message bundle="define" key="define.search.header.java.object.name" /><em> *</em></label> 	
				<html:text property="javaObjectName" maxlength="100" styleClass="manageSearchHeader_javaObjectName" /> 	
			</li>
			<li id="tableIdLI">
				<label><bean:message bundle="define" key="define.table" /><em> *</em></label> 	
				<html:select property="tableId" styleClass="manageSearchHeader_tableId" >
					<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
				</html:select>		
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.search.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageSearchHeader_nameZH" /> 					
			</li>
			<li>
				<label><bean:message bundle="define" key="define.search.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageSearchHeader_nameEN" />
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageSearchHeader_description"></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageSearchHeader_status" >
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>					
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($){
		switchUseJavaObjectLi();
		
		// Header Form JS验证
		validate_manage_primary_form = function() {
		    var flag = 0;
		    
			// 如果使用JAVA对象
			if($('.manageSearchHeader_useJavaObject').is(':checked')){
				flag = flag + validate('manageSearchHeader_javaObjectName', true, 'common', 100, 0);
			}else{
				flag = flag + validate('manageSearchHeader_tableId', true, 'select', 0, 0);
			}
		
			flag = flag + validate('manageSearchHeader_nameZH', true, 'common', 100, 0);				
			flag = flag + validate('manageSearchHeader_status', true, 'select', 0, 0);
			flag = flag + validate('manageSearchHeader_description', false, 'common', 500, 0);	
		    
		    return flag;
		};
			
		// 使用java对象点击事件 
		$('.manageSearchHeader_useJavaObject').click(function(){
			switchUseJavaObjectLi();
	    });
	})(jQuery);
	
	function switchUseJavaObjectLi(){
		if($('.manageSearchHeader_useJavaObject').is(':checked')) {
			$('.manageSearchHeader_tableId').val('0');
			$('#tableIdLI').hide();
        	$('#javaObjectNameLI').show();
        } else {
        	$('#javaObjectNameLI').hide();
        	$('#tableIdLI').show();
        }
	};
</script>
