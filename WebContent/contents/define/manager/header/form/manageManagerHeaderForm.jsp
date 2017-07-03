<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="managerHeaderAction.do?proc=add_object" styleClass="manageManagerHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="id" name="id" value="<bean:write name="managerHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="managerHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.table" /><em> *</em></label> 	
				<html:select property="tableId" styleClass="manageManagerHeader_tableId">
					<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
				</html:select>		
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.manager.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageManagerHeader_nameZH" /> 					
			</li>
			<li>
				<label><bean:message bundle="define" key="define.manager.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageManagerHeader_nameEN" />
			</li>
			<li>
				<label><bean:message bundle="define" key="define.manager.header.note" /></label> 
				<html:textarea property="comments" cols="3" styleClass="manageManagerHeader_comments"></html:textarea>
			</li>
		</ol>	
		<ol class="auto">					
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" cols="3" styleClass="manageManagerHeader_description"></html:textarea>
			</li>																
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageManagerHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// JS验证
		validate_manage_primary_form = function() {
		    var flag = 0;
		    
			flag = flag + validate('manageManagerHeader_tableId', true, 'select', 0, 0);
			flag = flag + validate('manageManagerHeader_nameZH', true, 'common', 100, 0);			
			flag = flag + validate('manageManagerHeader_comments', false, 'common', 500, 0);
			flag = flag + validate('manageManagerHeader_status', true, 'select', 0, 0);
			flag = flag + validate('manageManagerHeader_description', false, 'common', 500, 0);
		    
		    return flag;
		};
		// 表 - 视图控件Change事件
		$('.manageManagerHeader_tableId').change(function(){
			$.ajax({
				url:"tableAction.do?proc=get_object_json&tableId=" + $(this).val(),
				dataType:"json",
				success:function(data){
					if( data.success == 'true' ){
						$('.manageManagerHeader_nameZH').val(data.nameZH);
						$('.manageManagerHeader_nameEN').val(data.nameEN);
					}else{
						$('.manageManagerHeader_nameZH').val('');
						$('.manageManagerHeader_nameEN').val('');
					}
				}	
			});
		});
	})(jQuery);
</script>