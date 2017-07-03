<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import=" com.kan.base.web.actions.management.TaxAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>


<div id="content">
	<div id="tax" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">税率添加</label>
		</div>
		<div class="inner">
			<div class="top">
				<logic:empty name="taxForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
				</logic:empty>
				<logic:notEmpty name="taxForm" property="encodedId">
					<kan:auth right="modify" action="<%=TaxAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=TaxAction.accessAction%>">
					<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" />
				</kan:auth>
			</div>
			<html:form action="taxAction.do?proc=add_object" styleClass="manageTax_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="taxId" id="taxId" value='<bean:write name="taxForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="taxForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>选择法务实体<em> *</em></label> 
							<html:select property="entityId" styleClass="manageTax_entityId">
								<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>选择业务类型<em> *</em></label> 
							<html:select property="businessTypeId" styleClass="manageTax_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>税率名（中文）<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageTax_nameZH" /> 
						</li>
						<li>
							<label>税率名（英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageTax_nameEN" /> 
						</li>
						<li>
							<label>销售税率<em> *</em></label> 
							<html:text property="saleTax" maxlength="100" styleClass="manageTax_saleTax" /> 
						</li>
						<li>
							<label>成本税率<em> *</em></label> 
							<html:text property="costTax" maxlength="100" styleClass="manageTax_costTax" /> 
						</li>
						<li>
							<label>实缴税率<em> *</em></label> 
							<html:text property="actualTax" maxlength="100" styleClass="manageTax_actualTax" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageTax_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageTax_description"></html:textarea>
						</li>	
					</ol>	
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_finance_Modules').addClass('current');	
		$('#menu_finance_Configuration').addClass('selected');
		$('#menu_finance_Tax').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageTax_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageTax_form');
				// 更改Subaction
        		$('.manageTax_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('税率编辑');
				// 更改Form Action
        		$('.manageTax_form').attr('action', 'taxAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
        		
        		flag = flag + validate("manageTax_entityId", true, "select", 0, 0);
        		flag = flag + validate("manageTax_businessTypeId", true, "select", 0, 0); 
    			flag = flag + validate("manageTax_nameZH", true, "common", 100, 0);
    			
    			flag = flag + validate("manageTax_saleTax", true, "common", 100, 0);
    			flag = flag + validate("manageTax_costTax", true, "common", 100, 0);
    			flag = flag + validate("manageTax_actualTax", true, "common", 100, 0);
    			
    			flag = flag + validate("manageTax_description", false, "common", 500, 0);
    			flag = flag + validate("manageTax_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageTax_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageTax_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('manageTax_form');
			// 更换Page Title
			$('#pageTitle').html('税率查询');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		$('#btnCancel').click( function () {
			link('taxAction.do?proc=list_object');
		});
	})(jQuery);
</script>
