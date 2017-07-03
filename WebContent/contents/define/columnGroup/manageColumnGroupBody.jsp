<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.ColumnGroupAction"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="table" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="define" key="define.column.group" /></label>
		</div>
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
				<logic:empty name="columnGroupForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="columnGroupForm" property="encodedId">
					<kan:auth right="modify" action="<%=ColumnGroupAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ColumnGroupAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="columnGroupAction.do?proc=add_object" styleClass="columnGroup_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="groupId" name="id" value="<bean:write name="columnGroupForm" property="encodedId"/>"/>
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="columnGroupForm" property="subAction"/>" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.group.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="managecolumnGroup_nameZH" /> 					
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.group.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="managecolumnGroup_nameEN" />
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.group.use.name" />  <a title="<bean:message bundle="define" key="define.column.group.use.name.tips" />"><img src="images/tips.png" /></a></label>
							<html:select property="useName" styleClass="managecolumnGroup_useName">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.group.use.border" />  <a title="<bean:message bundle="define" key="define.column.group.use.border.tips" />"><img src="images/tips.png" /></a></label>
							<html:select property="useBorder" styleClass="managecolumnGroup_useBorder">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.group.use.padding" />  <a title="<bean:message bundle="define" key="define.column.group.use.padding.tips" />"><img src="images/tips.png" /></a></label>
							<html:select property="usePadding" styleClass="managecolumnGroup_usePadding">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.group.use.margin" />  <a title="<bean:message bundle="define" key="define.column.group.use.margin.tips" />"><img src="images/tips.png" /></a></label>
							<html:select property="useMargin" styleClass="managecolumnGroup_useMargin">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.group.is.flexable" />  <a title="<bean:message bundle="define" key="define.column.group.is.flexable.tips" />"><img src="images/tips.png" /></a></label>
							<html:select property="isFlexable" styleClass="managecolumnGroup_isFlexable">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>	
						<li>
							<label><bean:message bundle="define" key="define.column.group.is.display" />  <a title="<bean:message bundle="define" key="define.column.group.is.display.tips" />"><img src="images/tips.png" /></a></label>
							<html:select property="isDisplayed" styleClass="managecolumnGroup_isDisplayed">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>					
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" cols="3" styleClass="managecolumnGroup_description"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="managecolumnGroup_status">
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
		$('#menu_define_ColumnGroup').addClass('selected');
        
		// 编辑按钮点击事件
        $('#btnEdit').click(function(){
        	if($('.columnGroup_form input.subAction').val() == 'viewObject'){
        		enableForm('columnGroup_form');
    			// 更改SubAction
        		$('.columnGroup_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		$('#pageTitle').html('<bean:message bundle="define" key="define.column.group" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.columnGroup_form').attr('action', 'columnGroupAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate('managecolumnGroup_nameZH', true, 'common', 100, 0);	    						
    			flag = flag + validate('managecolumnGroup_description', false, 'common', 500, 0);			
    			flag = flag + validate('managecolumnGroup_status', true, 'select', 0, 0);
    						
    			if(flag == 0){
    				submit('columnGroup_form');
    			}
        	}
        });
			
		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('columnGroupAction.do?proc=list_object');		
		});	
		
		// 查看模式
		if($('.columnGroup_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('columnGroup_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="define" key="define.column.group" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
	})(jQuery);
</script>
