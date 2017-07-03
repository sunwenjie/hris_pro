<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.define.ImportHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="messageWrapper">
	<logic:present name="MESSAGE">
		<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
			<bean:write name="MESSAGE" />
   			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
		</div>
	</logic:present>
</div>
<div class="top">
	<logic:empty name="importHeaderForm" property="encodedId">
		<input type="button" class="editbutton" name="btnEditImportHeader" id="btnEditImportHeader" value="<bean:message bundle="public" key="button.save" />" /> 
	</logic:empty>
	<logic:notEmpty name="importHeaderForm" property="encodedId">
		<kan:auth right="modify" action="<%=ImportHeaderAction.accessAction%>">
			 <input type="button" class="editbutton" name="btnEditImportHeader" id="btnEditImportHeader" value="<bean:message bundle="public" key="button.save" />" /> 
		</kan:auth>
	</logic:notEmpty>
<%-- 	<input type="button" class="reset" name="btnCancelImportHeader" id="btnCancelImportHeader" value="<bean:message bundle="public" key="button.cancel" />" />  --%>
</div>

<html:form action="importHeaderAction.do?proc=add_object" styleClass="manageImportHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="importHeaderId" name="importHeaderId" value="<bean:write name="importHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="importHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.table" /><em> *</em></label> 	
				<html:select property="tableId" styleClass="manageImportHeader_tableId">
					<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
				</html:select>		
			</li>
			<li>
				<label><bean:message bundle="define" key="define.import.header.parent" /></label> 	
				<html:select property="parentId" styleClass="manageImportHeader_parentId">
					<html:optionsCollection property="parentIds" value="mappingId" label="mappingValue" />
				</html:select>		
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.import.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageImportHeader_nameZH" /> 					
			</li>
			<li>
				<label><bean:message bundle="define" key="define.import.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageImportHeader_nameEN" />
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.import.header.batch" /></label> 
				<html:select property="needBatchId" styleClass="manageImportHeader_needBatchId">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
			<li style="display: none;">
				<label>从表表头设置</label> 
				<html:select property="matchConfig" styleClass="manageImportHeader_matchConfig">
					<html:optionsCollection property="matchConfigs" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.import.header.define.task" /></label> 
				<html:select property="handlerBeanId" styleClass="manageImportHeader_handlerId">
					<html:optionsCollection property="handlerBeanIds" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
		</ol>
		<ol class="auto">					
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" cols="3" styleClass="manageImportHeader_description"></html:textarea>
			</li>																
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageImportHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		
	<logic:notEmpty name="MESSAGE">
		messageWrapperFada();
	</logic:notEmpty>
		
		if ($('.manageImportHeader_form input#subAction').val() == 'viewObject') {
		    disableForm('manageImportHeader_form');
		    $('#pageTitle').html('<bean:message bundle="define" key="define.import" /> <bean:message bundle="public" key="oper.view" />');
		    $('#btnEditImportHeader').val('<bean:message bundle="public" key="button.edit" />');
		}
		
	    // 编辑按钮点击事件 - List Header
		$('#btnEditImportHeader').click(function(){
			if($('.manageImportHeader_form input#subAction').val() == 'viewObject'){  
				// Enable整个Form
        		enableForm('manageImportHeader_form');
        		// 设置当前Form的SubAction为修改状态
        		$('.manageImportHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.manageImportHeader_form').attr('action', 'importHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$('#btnEditImportHeader').val('<bean:message bundle="public" key="button.save" />');
        		// 修改Page Title
        		$('#pageTitle').html('<bean:message bundle="define" key="define.import" /> <bean:message bundle="public" key="oper.edit" />');
        		// Table字段依旧Disable
        		$('.manageImportHeader_tableId').attr('disabled','disabled');
			}else{
				var flag = 0;

        		flag = flag + validate('manageImportHeader_tableId', true, 'select', 0, 0);
    			flag = flag + validate('manageImportHeader_nameZH', true, 'common', 100, 0);    			
    			
    			flag = flag + validate('manageImportHeader_status', true, 'select', 0, 0);
    			flag = flag + validate('manageImportHeader_description', false, 'common', 500, 0);
    			
    			if(flag == 0){
    				submit('manageImportHeader_form');
    			}
			}
		});
	    
		// 表 - 视图控件Change事件
		$('.manageImportHeader_tableId').change(function(){
			loadHtml('.manageImportHeader_searchId', 'searchHeaderAction.do?proc=list_object_options_ajax&tableId=' + $('.manageImportHeader_tableId').val(), false);
		});
	})(jQuery);
	
</script>