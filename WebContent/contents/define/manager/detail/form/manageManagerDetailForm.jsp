<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="managerDetailAction?proc=add_object" styleClass="manageManagerDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="managerHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="managerHeaderId" name="managerHeaderId" value="<bean:write name="managerHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="managerDetailId" name="managerDetailId" value="<bean:write name="managerDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="managerDetailForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.column" /><em> *</em></label> 
				<select name="columnId" class="manageManagerDetail_columnId" id="columnId">	
					<!-- Waiting for loading -->
				</select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.column.group" /></label> 
				<html:select property="groupId" styleClass="manageManagerDetail_groupId">
					<html:optionsCollection property="columnGroups" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageManagerDetail_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageManagerDetail_nameEN" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.column.column.index" /><em> *</em></label> 
				<html:text property="columnIndex" maxlength="3" styleClass="manageManagerDetail_columnIndex" /> 
			</li>
			<li id="isRequiredLI">
				<label><bean:message bundle="define" key="define.column.is.required" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.is.required.tips" />" /></label>
				<logic:notEmpty name="system_isRequired">
					<input id="system_isRequired" type="hidden" value="<bean:write name="system_isRequired" />">
				</logic:notEmpty>
				<html:select property="isRequired" styleClass="manageManagerDetail_isRequired">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>							
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.column.display.type" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.display.type.tips" />" /></label>
				<html:select property="display" styleClass="manageManagerDetail_display">
					<html:optionsCollection property="displayTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.align.type" /></label>
				<html:select property="align" styleClass="manageManagerDetail_align">
					<html:optionsCollection property="aligns" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.column.use.title" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.use.title.tips" />" /></label> 
				<html:select property="useTitle" styleClass="manageManagerDetail_useTitle">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 								
			</li>
		</ol>
		<ol id="column_title_ol" class="auto">
			<li>
				<label><bean:message bundle="define" key="define.column.use.title.name.cn" /><em> *</em></label> 
				<html:textarea property="titleZH" styleClass="manageManagerDetail_titleZH"></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.column.use.title.name.en" /></label> 
				<html:textarea property="titleEN" styleClass="manageManagerDetail_titleEN"></html:textarea>
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageManagerDetail_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageManagerDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// Detail JS验证
		validate_manage_secondary_form = function() {
		    var flag = 0;
		    
			flag = flag + validate('manageManagerDetail_columnId', true, 'select', 0, 0);
			flag = flag + validate('manageManagerDetail_nameZH', true, 'common', 100, 0);
			// 验证数值
			flag = flag + validate('manageManagerDetail_columnIndex', true, 'numeric', 3, 0);
			
			// 验证提示
			if($('.manageManagerDetail_useTitle').val() == 1){
				flag = flag + validate('manageManagerDetail_titleZH', true, 'common', 1000, 0);
				flag = flag + validate('manageManagerDetail_titleEN', false, 'common', 1000, 0);
			}
			
			flag = flag + validate('manageManagerDetail_status', true, 'select', 0, 0);
			flag = flag + validate('manageManagerDetail_description', false, 'common', 500, 0);
		    
		    return flag;
		};
		// 字段Change事件
		$('.manageManagerDetail_columnId').change(function(){
			 $.ajax({
				url:"columnAction.do?proc=get_object_json&columnId=" + $(this).val(),
				dataType:"json",
				success:function(data){
					if( data.success == 'true' ){
						$('.manageManagerDetail_groupId').val(data.groupId);
						$('.manageManagerDetail_nameZH').val(data.nameZH);
						$('.manageManagerDetail_nameEN').val(data.nameEN);
						$('.manageManagerDetail_columnIndex').val(data.columnIndex);
						$('.manageManagerDetail_display').val(data.displayType);
						$('.manageManagerDetail_useTitle').val(data.useTitle);
						$('.manageManagerDetail_useTitle').trigger('change');
						$('.manageManagerDetail_titleZH').val(data.titleZH);
						$('.manageManagerDetail_titleEN').val(data.titleEN);
						$('.manageManagerDetail_isRequired').val(data.isRequired );
						if( data.isRequired == '1' ){
							$('.manageManagerDetail_isRequired').attr('disabled','disabled');
						}else{
							$('.manageManagerDetail_isRequired').removeAttr('disabled');
						}
					}else{
						$('.manageManagerDetail_nameZH').val('');
						$('.manageManagerDetail_nameEN').val('');
						$('.manageManagerDetail_columnIndex').val('');
						$('.manageManagerDetail_display').val('0');
						$('.manageManagerDetail_useTitle').val('0');
						$('.manageManagerDetail_useTitle').trigger('change');
						$('.manageManagerDetail_titleZH').val('');
						$('.manageManagerDetail_titleEN').val('');
					}
				}	
			}); 
		});
		
		// 绑定事件 - 是否使用启用提示 
		$('.manageManagerDetail_useTitle').change( function () {
			if($(this).val() == 1){
				$('#column_title_ol').show();
			}else{
				$('#column_title_ol').hide();
			}
		}); 
		
		// 触发事件 -是否使用启用提示
		$('.manageManagerDetail_useTitle').trigger('change');
	})(jQuery);
</script>