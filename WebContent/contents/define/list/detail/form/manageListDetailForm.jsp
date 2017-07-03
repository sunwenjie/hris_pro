<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="listDetailAction.do?proc=add_object" styleClass="listDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="listHeaderId" name="listHeaderId" value="<bean:write name="listHeaderForm" property="encodedId"/>"/>
	<input type="hidden" id="listDetailId" name="listDetailId" value="<bean:write name="listDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="listDetailForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<logic:equal name="listHeaderForm" property="tableId" value="0">
					<label><bean:message bundle="define" key="define.search.detail.parameter.name" /><em> *</em></label>
					<html:text property="propertyName" maxlength="100" styleClass="manageListDetail_propertyName" /> 
				</logic:equal>
				<logic:notEqual name="listHeaderForm" property="tableId" value="0">
					<label><bean:message bundle="define" key="define.column" /><em> *</em></label>
					<select name="columnId" class="manageListDetail_columnId">
						<logic:notEmpty name="listDetailForm" property="columns">
							<logic:iterate id="column" name="listDetailForm" property="columns">
								<option <logic:equal name="column" property="mappingStatus" value="2">disabled="disabled"</logic:equal> value='<bean:write name="column" property="mappingId" />'>
									<bean:write name="column" property="mappingValue" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</logic:notEqual>
			</li>
		</ol>
		<ol class="auto" id="column_numeric" style="display: none;">
			<li>
				<label><bean:message bundle="public" key="public.accuracy" /> <img src="images/tips.png" title="<bean:message bundle="public" key="public.accuracy.tips" />" /></label>
				<html:select property="accuracy" styleClass="manageListDetail_accuracy">
					<html:optionsCollection property="accuracys" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.round" /> <img src="images/tips.png" title="<bean:message bundle="public" key="public.round.tips" />" /></label>
				<html:select property="round" styleClass="manageListDetail_round">
					<html:optionsCollection property="rounds" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
		</ol>
		<ol class="auto" id="column_date" style="display: none;">
			<li>
				<label><bean:message bundle="public" key="public.date.format" /></label>
				<html:select property="datetimeFormat" styleClass="manageListDetail_datetimeFormat">
					<html:optionsCollection property="datetimeFormats" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageListDetail_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageListDetail_nameEN" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.list.detail.column.width" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.column.width.tips" />" /></label>
				<html:select property="columnWidthType" styleClass="manageListDetail_columnWidthType small">
					<html:optionsCollection property="columnWidthTypes" value="mappingId" label="mappingValue"/>
				</html:select>
				<html:text property="columnWidth" maxlength="3" styleClass="manageListDetail_columnWidth small" /> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.column.column.index" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.column.index.tips" />" /><em> *</em></label>
				<html:text property="columnIndex" maxlength="2" styleClass="manageListDetail_columnIndex" /> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.font.size" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.font.size.tips" />" /></label>
				<html:select property="fontSize" styleClass="manageListDetail_fontSize">
					<html:optionsCollection property="fontSizes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.is.decode" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.is.decode.tips" />" /> </label>
				<html:select property="isDecoded" styleClass="manageListDetail_isDecoded">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.list.detail.is.linked" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.is.linked.tips" />" /></label>
				<html:select property="isLinked" styleClass="manageListDetail_isLinked">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto" id="linkedDetailOl" style="display: none;">	
			<li>
				<label><bean:message bundle="define" key="define.list.detail.linked.action" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.linked.action.tips" />" /><em> *</em></label>
				<html:text property="linkedAction" maxlength="1000" styleClass="manageListDetail_linkedAction" /> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.linked.target" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.linked.target.tips" />" /> </label>
				<html:select property="linkedTarget" styleClass="manageListDetail_linkedTarget">
					<html:optionsCollection property="linkedTargets" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.list.detail.append.content" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.append.content.tips" />" /></label>
				<html:textarea property="appendContent" styleClass="manageListDetail_appendContent"></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.properties" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.properties.tips" />" /></label>
				<html:text property="properties" maxlength="100" styleClass="manageListDetail_properties" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.list.detail.align" /></label>
				<html:select property="align" styleClass="manageListDetail_align">
					<html:optionsCollection property="aligns" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.sort" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.sort.tips" />" /></label>
				<html:select property="sort" styleClass="manageListDetail_sort">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.display" /><img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.display.tips" />" /></label>
				<html:select property="display" styleClass="manageListDetail_display">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageListDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageListDetail_description"></html:textarea>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// Detail JS验证
		validate_manage_secondary_form = function() {
		    var flag = 0;
		    
		 	// 如果使用JAVA对象
			if($('.listHeader_form .manageListHeader_useJavaObject').is(':checked')){
				flag = flag + validate('manageListDetail_propertyName', true, 'common', 100, 0);
			}else{
			    flag = flag + validate('manageListDetail_columnId', true, 'select', 0, 0);
			}
		
			flag = flag + validate('manageListDetail_nameZH', true, 'common', 100, 0);	
			
			// 如果用户使用百分比，列宽只能填2位数；如果用户使用固定值，列宽则能填3位数
			if($('.manageListDetail_columnWidthType').val() == 1){
				flag = flag + validate('manageListDetail_columnWidth', false, 'numeric', 2, 0);
			}else{
				flag = flag + validate('manageListDetail_columnWidth', false, 'numeric', 3, 0);
			}
				
			flag = flag + validate('manageListDetail_columnIndex', true, 'numeric', 2, 0);	
			
			// 如果使用超链接，则需要验证链接地址
			if($('.manageListDetail_isLinked').val() == 1){
				flag = flag + validate('manageListDetail_linkedAction', true, 'common', 1000, 0);	
			}
			
			flag = flag + validate('manageListDetail_description', false, 'common', 500, 0);					
			flag = flag + validate('manageListDetail_status', true, 'select', 0, 0);
			
		    return flag;
		};
		// 字段Change事件
		$('.manageListDetail_columnId').change(function(){
			 $('#column_numeric').hide();
			 $('#column_date').hide();
			 $.ajax({
				url:"columnAction.do?proc=get_object_json&columnId=" + $(this).val(),
				dataType:"json",
				success:function(data){
					if( data.success == 'true' ){
						if( data.valueType == '1'){
							$('#column_numeric').show();
						} else if( data.valueType == '3' ){
							$('#column_date').show();
						}
						
						if($('.listDetail_form#subAction') == 'createObject'){
							$('.manageListDetail_nameZH').val(data.nameZH);
							$('.manageListDetail_nameEN').val(data.nameEN);
						}
					}
				}	
			}); 
		});
		
		// 绑定事件 - 是否是超链接 
		$('.manageListDetail_isLinked').change( function () {
			if($(this).val() == 1){
				$('#linkedDetailOl').show();
			}else{
				$('#linkedDetailOl').hide();
			}
		}); 
		
		// 触发事件 -是否为超链接
		$('.manageListDetail_isLinked').trigger('change');
		
	})(jQuery);
</script>