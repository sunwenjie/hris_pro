<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="messageWrapper">
	<logic:present name="MESSAGE_SEARCH">
		<logic:present name="MESSAGE">
			<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
				<bean:write name="MESSAGE" />
    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
			</div>
		</logic:present>
	</logic:present>
</div>
<html:form action="reportSearchDetailAction.do?proc=add_object" styleClass="manageReportSearchDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="selectedIds" id="selectedIds" value="" />
		<input type="hidden" name="subAction" id="subAction" class="subAction" value="" />
	<input type="hidden" name="reportHeaderId" id="reportHeaderId" class="reportHeaderId" value="<bean:write name="reportHeaderForm" property="encodedId" />" />
	<input type="hidden" name="reportSearchDetailId" id="reportSearchDetailId" class="reportSearchDetailId" value="<bean:write name="reportSearchDetailForm" property="encodedId" />" />
	<input type="hidden" name="contenHelp" id="contenHelp" value="" />
	<fieldset>
		<ol class="auto">
			<li class="required">
				<label><em>* </em><bean:message bundle="public" key="required.field" /></label>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.column" /><em> *</em></label> 
				<html:select property="columnId" styleClass="manageReportSearchDetail_columnId" styleId="columnId">
					<html:optionsCollection property="columns" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>	
		</ol>
		
		<ol	class="auto">	
			<li id="conditionLI" style="display: none;">
				<label><bean:message bundle="define" key="define.report.detail.select.condition" /></label> 
				<html:select property="condition" styleClass="manageReportSearchDetail_condition">
					<html:optionsCollection property="conditions" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
			<li id="contentLI" style="display: none;">
			
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.cn" /><em> *</em></label> 					
				<html:text property="nameZH" maxlength="100" styleClass="manageReportSearchDetail_nameZH" />
			</li>
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageReportSearchDetail_nameEN" />
			</li>	
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.column.column.index" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.column.index.tips" />" /><em> *</em></label> 
				<html:text property="columnIndex" maxlength="2" styleClass="manageReportSearchDetail_columnIndex" />
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.font.size" /></label> 
				<html:select property="fontSize" styleClass="manageReportSearchDetail_fontSize">
					<html:optionsCollection property="fontSizes" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.report.detail.logic.condition" /></label> 
				<html:select property="combineType" styleClass="manageReportSearchDetail_combineType">
					<html:optionsCollection property="combineTypies" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
		</ol>	
		<ol id="conditionOL" class="auto" style="display: none;">	
		
		</ol>
		<ol class="auto" >	
			<li>
				<label><bean:message bundle="define" key="define.serach.detail.is.display" /></label> 
				<html:select property="display" styleClass="manageReportSearchDetail_display">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 	
				<html:textarea property="description" cols="3" styleClass="manageReportSearchDetail_description"></html:textarea>
			</li>				
			<li>
				<label><bean:message bundle="public" key="public.status" />  <em>*</em></label> 
				<html:select property="status" styleClass="manageReportSearchDetail_status">
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
		// 选择条件Change事件
		$('.manageReportSearchDetail_condition').change(function(){
			
			if($(this).val() == '8'){
				$('#contentLI').html('<label><bean:message bundle="define" key="define.report.detail.condition" /></label><input type="text" name="rangeMin" maxlength="10" class="small manageReportSearchDetail_rangeMin" /><input type="text" name="rangeMax" maxlength="10" class="small manageReportSearchDetail_rangeMax" />');
			}else{
				$('#contentLI').html('<label><bean:message bundle="define" key="define.report.detail.condition" /></label><input type="text" name="content" maxlength="100" class="manageReportSearchDetail_content" value="<bean:write name="reportSearchDetailForm" property="content" />" />');
			}
			
		});
		
		// 选择字段change事件
		$('.manageReportSearchDetail_form select#columnId').change(function(){
			$('.manageReportSearchDetail_condition').val('0');
			$('.manageReportSearchDetail_condition').trigger('change');
			
			$.ajax({
				url:"reportDetailAction.do?proc=columnId_change_ajax&columnId=" + $(this).val(),
				dataType:"json",
				success:function(data){
					$('#datetimeFormatOL').hide();
					$('#decimalFormatOL').hide();
					if( data.success == 'true' ){
						$('#contentLI').show();
						// 控件类型非下拉框
						if( data.inputType != '2'){
							$('#conditionLI').show();
							$('.manageReportSearchDetail_condition').val('<bean:write name="reportSearchDetailForm" property="condition" />');
						}else{
							$('#conditionLI').hide();
						}
						var disabled = false;
						var callback = "";
						if($('.manageReportSearchDetail_form input#subAction').val() == 'viewObject'){
							disabled = true;
							callback += "$('.manageReportSearchDetail_content').val('<bean:write name='reportSearchDetailForm' property='content' />');";
						}
						
						loadHtmlWithRecall('#contentLI', 'reportHeaderAction.do?proc=condition_change_html&columnId=' + $('.manageReportSearchDetail_form #columnId').val() + '&ajax=true', disabled, callback);
						$('.manageReportSearchDetail_nameZH').val(data.nameZH);
						$('.manageReportSearchDetail_nameEN').val(data.nameEN);
					}else{
						$('#contentLI').html('');
						$('.manageReportSearchDetail_nameEN').val('');
						$('.manageReportSearchDetail_nameZH').val('');
					}
				}
			});
		});
		
	})(jQuery);
	
	// 点击report Search Detail 的链接，Ajax调用修改页面	
	function reportSearchDetailModify( searchDetailId, columnId )
	{
		var tableId = '<bean:write name="reportHeaderForm" property="tableId" />';
		var callback = "$('.manageReportSearchDetail_form input#subAction').val('viewObject');";
		callback += "$('#btnSaveStep3').val('<bean:message bundle="public" key="button.edit" />');";
		callback += "$('.manageReportSearchDetail_columnId').val('" + columnId + "');";
		callback += "$('.manageReportSearchDetail_form select#columnId').trigger('change');";
		callback += "var tokenValue = $('.manageReportSearchDetail_form input[id=\"com.kan.token\"]').val();"; 
		callback += "changeOtherToken(tokenValue);";
		loadHtmlWithRecall('#detailForm2', 'reportSearchDetailAction.do?proc=to_objectModify_ajax&id=' + searchDetailId, true, callback);  
	};
	
</script>
