<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="table" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="define" key="define.table" /></label>
		</div>
		<div class="inner">
			<div class="top">
				<logic:present name="accountId">
					<logic:equal name="accountId" value="1">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</logic:equal>
				</logic:present>
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="tableAction.do?proc=add_object" styleClass="table_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="tableId" name="tableId" value="<bean:write name="tableForm" property="encodedId"/>"/>
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="tableForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
							<li>
								<label><bean:message bundle="define" key="define.table.name.cn" /><em> *</em></label> 
								<html:text property="nameZH" maxlength="100" styleClass="managetable_nameZH" /> 						
							</li>
							<li>
								<label><bean:message bundle="define" key="define.table.name.en" /></label> 
								<html:text property="nameEN" maxlength="100" styleClass="managetable_nameEN" />
							</li>
							<li>
								<label><bean:message bundle="public" key="public.type" /><em> *</em></label> 	
								<html:select property="tableType" styleClass="managetable_tableType">
									<html:optionsCollection property="tableTypes" value="mappingId" label="mappingValue" />
								</html:select>							
							</li>
							<li>
								<label><bean:message bundle="define" key="define.table.role" /></label> 	
								<html:select property="role" styleClass="managetable_role">
									<html:optionsCollection property="roles" value="mappingId" label="mappingValue" />
								</html:select>							
							</li>
							<li>
								<label><bean:message bundle="public" key="public.access.action" /></label> 
								<html:text property="accessAction" maxlength="100" styleClass="managetable_accessAction" />
							</li>
							<li>
								<label><bean:message bundle="define" key="define.table.name.db" /></label> 
								<html:text property="accessName" maxlength="100" styleClass="managetable_accessName" />
							</li>
							<li>
								<label><bean:message bundle="define" key="define.table.menu.module" /></label> 	
								<html:select property="moduleType" styleClass="managetable_moduleType">
									<html:optionsCollection property="moduleTypes" value="mappingId" label="mappingValue" />
								</html:select>							
							</li>
							<li>
								<label><bean:message bundle="public" key="public.show.index" /></label> 
								<html:text property="tableIndex" maxlength="3" styleClass="managetable_tableIndex" />
							</li>
						</ol>	
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.table.can.manager" /></label>
							<html:checkbox property="canManager" styleClass="managetable_canManager"></html:checkbox>
						</li>
						<li>
							<label><bean:message bundle="define" key="define.table.can.list" /></label>
							<html:checkbox property="canList" styleClass="managetable_canList"></html:checkbox>
						</li>
						<li>
							<label><bean:message bundle="define" key="define.table.can.search" /></label>
							<html:checkbox property="canSearch" styleClass="managetable_canSearch"></html:checkbox>
						</li>
						<li>
							<label><bean:message bundle="define" key="define.table.can.report" /></label>
							<html:checkbox property="canReport" styleClass="managetable_canReport"></html:checkbox>
						</li>
						<li>
							<label><bean:message bundle="define" key="define.table.can.import" /></label>
							<html:checkbox property="canImport" styleClass="managetable_canImport"></html:checkbox>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="managetable_description"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="managetable_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>					
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_Table').addClass('selected');
     	
		// 编辑按钮点击事件
        $('#btnEdit').click(function(){
        	if($('.subAction').val() == 'viewObject'){
        		enableForm('table_form');
    			// 更改Subaction
        		$('.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="define" key="define.table" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.table_form').attr('action', 'tableAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
        		
    			flag = flag + validate('managetable_nameZH', true, 'common', 100, 0);    						
    			flag = flag + validate('managetable_tableType', true, 'select', 0, 0);
    			flag = flag + validate('managetable_description', false, 'common', 500, 0);			
    			flag = flag + validate('managetable_status', true, 'select', 0, 0);    			
    			// 验证数值
    			flag = flag + validate('managetable_tableIndex', true, 'numeric', 3, 0);
    						
    			if(flag == 0){
    				submit('table_form');
    			}
        	}
        });
		
    	// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('tableAction.do?proc=list_object');
		});	
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('table_form');
			// 更改按钮显示名
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="define" key="define.table" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
		}
	})(jQuery);
</script>
