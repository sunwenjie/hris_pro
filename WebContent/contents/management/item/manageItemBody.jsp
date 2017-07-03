<%@page import="com.sun.org.apache.xalan.internal.xsltc.compiler.sym"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import="com.kan.base.web.actions.management.ItemAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>


<div id="content">
	<div id="manageItem" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.item" /></label>
		</div>
		<div class="inner">
			<div class="top">
				<logic:empty name="itemForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
				</logic:empty>
				<logic:notEmpty name="itemForm" property="encodedId">
					<kan:auth right="modify" action="<%=ItemAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ItemAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<html:form action="itemAction.do?proc=add_object" styleClass="manageItem_form">
				<%= BaseAction.addToken( request ) %>
				<html:hidden property="encodedId" /> 
				<html:hidden property="isCascade" styleClass="manageItem_isCascade" value="0"/> 
				<html:hidden property="subAction" styleClass="subAction" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.item.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageItem_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageItem_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.type" /><em> *</em></label> 
							<html:select property="itemType" styleClass="manageItem_itemType">
								<html:optionsCollection property="itemTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.no" /><em> *</em></label> 
							<html:text property="itemNo" maxlength="25" styleClass="manageItem_itemNo" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.billRateCompany" /><em> *</em> <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.common.tips" />" /></label> 
							<html:text property="billRateCompany" maxlength="10" styleClass="manageItem_billRateCompany" /> 		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.costRateCompany" /><em> *</em> <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.common.tips" />" /></label> 
							<html:text property="costRateCompany" maxlength="10" styleClass="manageItem_costRateCompany" /> 		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.billRatePersonal" /><em> *</em> <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.common.tips" />" /></label> 
							<html:text property="billRatePersonal" maxlength="10" styleClass="manageItem_billRatePersonal" /> 		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.costRatePersonal" /><em> *</em> <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.common.tips" />" /></label> 
							<html:text property="costRatePersonal" maxlength="10" styleClass="manageItem_costRatePersonal" /> 		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.billFixCompany" /><em> *</em></label> 
							<html:text property="billFixCompany" maxlength="10" styleClass="manageItem_billFixCompany" /> 		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.costFixCompany" /><em> *</em></label> 
							<html:text property="costFixCompany" maxlength="10" styleClass="manageItem_costFixCompany" /> 		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.billFixPersonal" /><em> *</em></label> 
							<html:text property="billFixPersonal" maxlength="10" styleClass="manageItem_billFixPersonal" /> 		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.costFixPersonal" /><em> *</em></label> 
							<html:text property="costFixPersonal" maxlength="10" styleClass="manageItem_costFixPersonal" /> 		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.personalTax" /><em> *</em> <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.personalTax.tips" />" /></label> 
							<html:select property="personalTax" styleClass="manageItem_personalTax">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.companyTax" /><em> *</em> <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.companyTax.tips" />" /></label> 
							<html:select property="companyTax" styleClass="manageItem_companyTax">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.personalTaxAgent" /> <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.personalTaxAgent.tips" />" /></label> 
							<html:select property="personalTaxAgent" styleClass="manageItem_personalTaxAgent">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>		
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.calculateType" /></label> 
							<html:select property="calculateType" styleClass="manageItem_calculateType">
								<html:optionsCollection property="calculateTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.itemCap" />  <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.itemCap.tips" />" /></label> 
							<html:text property="itemCap" maxlength="25" styleClass="manageItem_itemCap" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.itemFloor" />  <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.itemFloor.tips" />" /></label> 
							<html:text property="itemFloor" maxlength="25" styleClass="manageItem_itemFloor" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.sequence" /><em> *</em> <img src="images/tips.png" title="<bean:message bundle="management" key="management.item.sequence.tips" />" /></label> 
							<html:text property="sequence" maxlength="4" styleClass="manageItem_sequence" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageItem_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageItem_description"></html:textarea>
						</li>	
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式，如果当前用户是Super
		if('<bean:write name="accountId" />' == '1'){
			$('#menu_system_Modules').addClass('current');
			$('#menu_system_Item').addClass('selected');
		}else{
			$('#menu_finance_Modules').addClass('current');	
			$('#menu_finance_Configuration').addClass('selected');
			$('#menu_finance_Item').addClass('selected');
		}
		
		// 如果当前是系统元素并且当前用户非Super，隐藏编辑按钮。
		if('<bean:write name="itemForm" property="accountId" />' == '1' && '<bean:write name="accountId" />' != '1'){
			$('#btnEdit').hide();
		}
		
		// 取消按钮点击事件
		$('#btnList').click(function(){
			if (agreest())
			link('itemAction.do?proc=list_object'); 
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageItem_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageItem_form');
				// 更改Subaction
        		$('.manageItem_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.item" /> <bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.manageItem_form').attr('action', 'itemAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageItem_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageItem_itemType", true, "select", 0, 0);
    			flag = flag + validate("manageItem_itemNo", true, "common", 25, 0);
    			flag = flag + validate("manageItem_billRatePersonal", true, "currency", 6, 0);
    			flag = flag + validate("manageItem_billRateCompany", true, "currency", 6, 0);
    			flag = flag + validate("manageItem_costRatePersonal", true, "currency", 6, 0);
    			flag = flag + validate("manageItem_costRateCompany", true, "currency", 6, 0);
    			flag = flag + validate("manageItem_billFixPersonal", true, "currency", 8, 0);
    			flag = flag + validate("manageItem_billFixCompany", true, "currency", 8, 0);
    			flag = flag + validate("manageItem_costFixPersonal", true, "currency", 8, 0);
    			flag = flag + validate("manageItem_costFixCompany", true, "currency", 8, 0);
    			flag = flag + validate("manageItem_personalTax", true, "select", 0, 0);
    			flag = flag + validate("manageItem_companyTax", true, "select", 0, 0);
    			flag = flag + validate("manageItem_itemCap", false, "currency", 10, 0);
    			flag = flag + validate("manageItem_itemFloor", false, "currency", 10, 0);
    			flag = flag + validate("manageItem_sequence", true, "numeric", 4, 0);
    			flag = flag + validate("manageItem_description", false, "common", 500, 0);
    			flag = flag + validate("manageItem_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				popIsCascade();
    			}
        	}
		});
		
		// 查看模式
		if($('.manageItem_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('manageItem_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="management" key="management.item" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		$(".manageItem_nameZH").blur(function(){
			checkName($(this).val());
		});
	})(jQuery);
	
	// 检查名字是否重复
	function checkName(name){
		name = encodeURI(encodeURI(name));
		$.post("itemAction.do?proc=checkNameExist",{"name":name},function(data){
			cleanError("manageItem_nameZH");
			if(data=="true"){
				addError("manageItem_nameZH","已存在元素名");
				$(".manageItem_nameZH").focus();
			}
		},"text");
	};
	
	// 检查名字是否重复
	function popIsCascade(){
		if( confirm("是否添加到工资单列表 - 明细，工资导入明细？") )
		{ 
			$('.manageItem_isCascade').val('1');
		}else{
			$('.manageItem_isCascade').val('0');
		}
		submit('manageItem_form');
	};
</script>
