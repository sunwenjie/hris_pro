<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.define.ColumnAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.domain.define.ColumnVO"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final ColumnVO columnVO = (ColumnVO) request.getAttribute("columnForm");
%>

<div id="content">
	<div id="systemUser" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="define" key="define.column" /></label>
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
				<logic:empty name="columnForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="columnForm" property="encodedId">
					<kan:auth right="modify" action="<%=ColumnAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ColumnAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="columnAction.do?proc=add_object" styleClass="column_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="columnId" name="id" value="<bean:write name="columnForm" property="encodedId"/>" />
				<input type="hidden" id="subAction" name="subAction" value="<bean:write name="columnForm" property="subAction"/>" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label><bean:message bundle="define" key="define.table" /><em> *</em></label> 
							<html:select property="tableId" styleClass="manageColumn_tableId">
								<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
							</html:select>																
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.group" /></label> 		
							<html:select property="groupId" styleClass="manageColumn_groupId">
								<html:optionsCollection property="columnGroups" value="mappingId" label="mappingValue" />
							</html:select>								
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.name.db" /><img src="images/tips.png" title="<bean:message bundle="define" key="define.column.name.db.tips" />" /><em> *</em></label> 
							<html:text property="nameDB" maxlength="100" styleClass="manageColumn_nameDB" /> 						
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.name.sys" /></label> 
							<html:text property="nameSys" maxlength="100" styleClass="manageColumn_nameSys" />
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageColumn_nameZH" />
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageColumn_nameEN" />
						</li>
						<li class="onlySuper">
							<label><bean:message bundle="define" key="define.column.is.primary.key" /></label>
							<html:select property="isPrimaryKey" styleClass="manageColumn_isPrimaryKey">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>							
						</li>
						<li class="onlySuper">
							<label><bean:message bundle="define" key="define.column.is.foreign.key" /></label> 
							<html:select property="isForeignKey" styleClass="manageColumn_isForeignKey">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select> 								
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.is.db.column" /></label>
							<html:select property="isDBColumn" styleClass="manageColumn_isDBColumn">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>							
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.is.can.import" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.is.can.import.tips" />" /></label></label>
							<html:checkbox property="canImport" styleClass="managetable_canImport"></html:checkbox>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="define" key="define.column.is.required" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.is.required.tips" />" /></label>
							<html:select property="isRequired" styleClass="manageColumn_isRequired">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>							
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.is.editable" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.is.editable.tips" />" /></label> 
							<html:select property="editable" styleClass="manageColumn_editable">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select> 								
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.value.type" /><em> *</em></label> 
							<html:select property="valueType" styleClass="manageColumn_valueType">
								<html:optionsCollection property="valueTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.column.index" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.column.index.tips" />" /><em> *</em></label> 
							<html:text property="columnIndex" maxlength="3" styleClass="manageColumn_columnIndex" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.input.type" /><em> *</em></label> 
							<html:select property="inputType" styleClass="manageColumn_inputType" >
								<html:optionsCollection property="inputTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol id="column_options" class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.option.type" /><em> *</em></label> 
							<html:select property="optionType" styleClass="manageColumn_optionType">
								<html:optionsCollection property="optionTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li id="manageColumn_option_type_value"></li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="define" key="define.column.validate.type" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.validate.type.tips" />" /></label> 
							<html:select property="validateType" styleClass="manageColumn_validateType" >
								<html:optionsCollection property="validateTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li id="column_validation_length">
							<label><bean:message bundle="define" key="define.column.validate.length" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.validate.length.tips" />" /><em> *</em></label> 
							<html:text property="validateLengthMin" maxlength="4" styleClass="manageColumn_validateLengthMin small" />
							<html:text property="validateLengthMax" maxlength="4" styleClass="manageColumn_validateLengthMax small" />
						</li>
						<li id="column_validation_range">
							<label><bean:message bundle="define" key="define.column.validate.range" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.validate.range.tips" />" /></label> 
							<html:text property="validateRangeMin" maxlength="10" styleClass="manageColumn_validateRangeMin small" />
							<html:text property="validateRangeMax" maxlength="10" styleClass="manageColumn_validateRangeMax small" />
						</li>
					</ol>	
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.use.thinking" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.use.thinking.tips" />" /></label> 
							<html:select property="useThinking" styleClass="manageColumn_useThinking">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select> 								
						</li>
					</ol>
					<ol id="column_thinking_ol" class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.use.thinking.db" /><em> *</em></label> 
							<html:text property="thinkingId" maxlength="100" styleClass="manageColumn_thinkingId" />
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.use.thinking.aciton" /><em> *</em></label> 
							<html:text property="thinkingAction" maxlength="100" styleClass="manageColumn_thinkingAction" />
						</li>
					</ol>	
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.use.title" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.use.title.tips" />" /></label> 
							<html:select property="useTitle" styleClass="manageColumn_useTitle">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select> 								
						</li>
					</ol>
					<ol id="column_title_ol" class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.use.title.name.cn" /><em> *</em></label> 
							<html:textarea property="titleZH" styleClass="manageColumn_titleZH"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.use.title.name.en" /></label> 
							<html:textarea property="titleEN" styleClass="manageColumn_titleEN"></html:textarea>
						</li>
					</ol>	
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.display.type" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.display.type.tips" />" /></label>
							<html:select property="displayType" styleClass="manageColumn_displayType">
								<html:optionsCollection property="displayTypes" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageColumn_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li class="onlySuper">
							<label><bean:message bundle="define" key="define.column.css.style" /></label> 
							<html:textarea property="cssStyle" styleClass="manageColumn_cssStyle"></html:textarea>
						</li>
						<li class="onlySuper">
							<label><bean:message bundle="define" key="define.column.js.event" />）</label> 
							<html:textarea property="jsEvent" styleClass="manageColumn_jsEvent"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageColumn_description"></html:textarea>
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
		$('#menu_define_Column').addClass('selected');
		
		// 只有“super”才能查看的字段
		if( '<bean:write name="accountId" />' != '1' ){
			$('.onlySuper').hide();
		}
		
		// 编辑按钮点击事件
        $('#btnEdit').click(function(){
        	if($('.column_form input#subAction').val() == 'viewObject'){
        		enableForm('column_form');
        		// 表 - 视图字段不能编辑（需要添加隐藏字段）
        		$('.manageColumn_tableId').after('<input type="hidden" id="tableId" name="tableId" value="' + $('.manageColumn_tableId').val() + '" />');
        		$('.manageColumn_tableId').attr('disabled', 'disabled');
    			// 更改Subaction
        		$('.column_form input#subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		$('#pageTitle').html('<bean:message bundle="define" key="define.column" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.column_form').attr('action', 'columnAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
        });
		
     	// 绑定事件
		$('.manageColumn_inputType').change( function () { 
			if($(this).val() == 2 || $(this).val() == 3 || $(this).val() == 4){
				$('#column_options').show();
			}else{
				$('#column_options').hide();
			}
		});
		
     	// 绑定事件
		$('.manageColumn_isDBColumn').change( function () { 
			if($(this).val() == 1){
				$('.managetable_canImport').attr("checked", true);
			}else{
				$('.managetable_canImport').attr("checked", false);
			}
		});
     	
		// 加载触发Change事件
		$(".manageColumn_inputType").trigger("change");
		
		// 绑定事件
		$('.manageColumn_validateType').change( function () { 
			if($(this).val() == 1 || $(this).val() == 3 || $(this).val() == 5 || $(this).val() == 8 || $(this).val() == 9 || $(this).val() == 10){
				$('#column_validation_length').show();
				$('#column_validation_range').hide();
			}else if($(this).val() == 2 || $(this).val() == 4){
				$('#column_validation_length').show();
				$('#column_validation_range').show();
			}else{
				$('#column_validation_length').hide();
				$('#column_validation_range').hide();
			}
		});
     	
		// 加载触发Change事件
		$(".manageColumn_validateType").trigger("change");
		
		// 绑定事件
		$('.manageColumn_useThinking').change( function () { 
			if($(this).val() == 1){
				$('#column_thinking_ol').show();
			}else{
				$('#column_thinking_ol').hide();
			}
		});
     	
		// 加载触发Change事件
		$(".manageColumn_useThinking").trigger("change");
		
		// 绑定事件
		$('.manageColumn_useTitle').change( function () { 
			if($(this).val() == 1){
				$('#column_title_ol').show();
			}else{
				$('#column_title_ol').hide();
			}
		});
     	
		// 加载触发Change事件
		$(".manageColumn_useTitle").trigger("change");
		
		// 绑定事件 - 选项来源（1:系统常量；2:账户常量；3:用户自定义；4:直接值；5:预留）
		$('.manageColumn_optionType').change( function () { 
			// 显示选项来源值
			$('#manageColumn_option_type_value').show();
			
			// 按照类型显示不同选项来源值
			if($(this).val() == 1){
				$('#manageColumn_option_type_value').html('<label><bean:message bundle="define" key="define.column.option" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.option.tips.one" />" /><em> *</em></label><%= KANUtil.getSelectHTML(columnVO.getSystemDefines(), "optionValue", "manageColumn_optionValue", columnVO.getOptionValue(), null, null) %>');
			}else if($(this).val() == 2){
				$('#manageColumn_option_type_value').html('<label><bean:message bundle="define" key="define.column.option" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.option.tips.two" />" /><em> *</em></label><%= KANUtil.getSelectHTML(columnVO.getAccountDefines(), "optionValue", "manageColumn_optionValue", columnVO.getOptionValue(), null, null) %>');
			}else if($(this).val() == 3){
				$('#manageColumn_option_type_value').html('<label><bean:message bundle="define" key="define.column.option" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.option.tips.three" />" /><em> *</em></label><%= KANUtil.getSelectHTML(columnVO.getUserDefines(), "optionValue", "manageColumn_optionValue", columnVO.getOptionValue(), null, null) %>');
			}else if($(this).val() == 4){
				$('#manageColumn_option_type_value').html('<label><bean:message bundle="define" key="define.column.option" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.option.tips.four" />" /><em> *</em></label><input type="text" id="optionValue" name="optionValue" maxlength="500" class="manageColumn_optionValue" value="<%= columnVO.getOptionValue() != null ? columnVO.getOptionValue() : "" %>"/>');
			}else{
				$('#manageColumn_option_type_value').html('');
				$('#manageColumn_option_type_value').hide();
			}
		});
		
		// 加载触发Change事件 - 选项来源
		$(".manageColumn_optionType").trigger("change");
		
		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('columnAction.do?proc=list_object');
		});
		
		// 查看模式
		if($('.column_form input#subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('column_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="define" key="define.column" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		};
		//字段名称（系统）联动 显示名称（中文）
		$('.manageColumn_nameSys').blur(function (){
			var nameSys=$('.manageColumn_nameSys').val();
			var nameZH=$('.manageColumn_nameZH');
			if(nameSys!=null&&nameSys!=""){
				nameZH.val(nameSys);
			}
		});
		//显示名称（中文）联动字段名称（系统）
		$('.manageColumn_nameZH').blur(function (){
			var nameSys=$('.manageColumn_nameSys');
			var nameZH=$('.manageColumn_nameZH').val();
			if(nameSys.val()==null||nameSys.val()==""){
				nameSys.val(nameZH);
			}
		});
	})(jQuery);

	// 按钮提交事件
	function btnSubmit() {
		var flag = 0;
		//	必须填写
		flag = flag + validate('manageColumn_tableId', true, 'select', 0, 0);
		flag = flag + validate('manageColumn_nameDB', true, 'character', 100, 0);
		flag = flag + validate('manageColumn_nameSys', false, 'common', 100, 0);
		flag = flag + validate('manageColumn_status', true, 'select', 0, 0);
		flag = flag + validate('manageColumn_inputType', true, 'select', 0, 0);
		flag = flag + validate('manageColumn_valueType', true, 'select', 0, 0);
		
		// 控件类型
		if($('.manageColumn_inputType').val() == 2 || $('.manageColumn_inputType').val() == 3 || $('.manageColumn_inputType').val() == 4){
			flag = flag + validate('manageColumn_optionType', true, 'select', 0, 0);
			flag = flag + validate('manageColumn_optionValue', true, 'select', 0, 0);
		}
		
		// 验证类型
		if($('.manageColumn_validateType').val() == 1 || $('.manageColumn_validateType').val() == 2 ||  $('.manageColumn_validateType').val() == 3 || 
				 $('.manageColumn_validateType').val() == 4 ||  $('.manageColumn_validateType').val() == 5 ||  $('.manageColumn_validateType').val() == 8 || 
				 $('.manageColumn_validateType').val() == 9 ||  $('.manageColumn_validateType').val() == 10){
			flag = flag + validate('manageColumn_validateLengthMin', true, 'numeric', 4, 0);
			flag = flag + validate('manageColumn_validateLengthMax', true, 'numeric', 4, 0);
		}
		
		// 验证数值
		flag = flag + validate('manageColumn_columnIndex', true, 'numeric', 4, 0);
		
		// 验证联想
		if($('.manageColumn_useThinking').val() == 1){
			flag = flag + validate('manageColumn_thinkingId', true, 'common', 100, 0);
			flag = flag + validate('manageColumn_thinkingAction', true, 'common', 100, 0);
		}
		
		// 验证提示
		if($('.manageColumn_useTitle').val() == 1){
			flag = flag + validate('manageColumn_titleZH', true, 'common', 1000, 0);
			flag = flag + validate('manageColumn_titleEN', false, 'common', 1000, 0);
		}
				
		// 验证Textarea的长度
		flag = flag + validate('manageColumn_cssStyle', false, 'common', 500, 0);	
		flag = flag + validate('manageColumn_jsEvent', false, 'common', 500, 0);			
		flag = flag + validate('manageColumn_description', false, 'common', 500, 0);	
		
		if(flag == 0){
			submit('column_form');
		}
	};
    
</script>
