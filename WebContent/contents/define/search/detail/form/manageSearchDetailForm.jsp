<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="searchDetailAction.do?proc=add_object" styleClass="manageSearchDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="id" class="searchHeaderId" value="<bean:write name="searchHeaderForm" property="encodedId"/>"/>
	<input type="hidden" name="searchDetailId" class="searchDetailId" value="<bean:write name="searchDetailForm" property="encodedId"/>"/>
	<input type="hidden" name="subAction" class="subAction" id="subAction" value="<bean:write name="searchDetailForm" property="subAction"/>"/>
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<logic:equal name="searchHeaderForm" property="tableId" value="0">
					<label><bean:message bundle="define" key="define.search.detail.parameter.name" /><em> *</em></label>
					<html:text property="propertyName" maxlength="100" styleClass="manageSearchDetail_propertyName" /> 
				</logic:equal>
				<logic:notEqual name="searchHeaderForm" property="tableId" value="0">
					<label><bean:message bundle="define" key="define.column" /><em> *</em></label>
					<select name="columnId" class="manageSearchDetail_columnId">
						<logic:notEmpty name="searchDetailForm" property="columns">
							<logic:iterate id="column" name="searchDetailForm" property="columns">
								<option <logic:equal name="column" property="mappingStatus" value="2">disabled="disabled"</logic:equal> value='<bean:write name="column" property="mappingId" />'>
									<bean:write name="column" property="mappingValue" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</logic:notEqual>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageSearchDetail_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageSearchDetail_nameEN" /> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.column.column.index" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.detail.column.index.tips" />" /><em> *</em></label>
				<html:text property="columnIndex" maxlength="2" styleClass="manageSearchDetail_columnIndex" /> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.search.detail.font.size" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.detail.font.size.tips" />" /></label>
				<html:select property="fontSize" styleClass="manageSearchDetail_fontSize">
					<html:optionsCollection property="fontSizes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.column.use.thinking" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.detail.use.thinking.tips" />" /></label>
				<html:select property="useThinking" styleClass="manageSearchDetail_useThinking">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li id="thinkingActionLi" style="display: none;">
				<label><bean:message bundle="define" key="define.column.use.thinking.aciton" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.detail.thinking.aciton.tips" />" /></label>
				<html:text property="thinkingAction" maxlength="100" styleClass="manageSearchDetail_thinkingAction" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.search.detail.content.type" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.detail.content.type.tips" />" /></label>
				<html:select property="contentType" styleClass="manageSearchDetail_contentType">
					<html:optionsCollection property="contentTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li id="manageSearchDetail_content_li" style="display: none;">
				<label><bean:message bundle="define" key="define.search.detail.column.value" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.detail.column.value.tips.one" />" /></label>
				<html:text property="content" maxlength="100" styleClass="manageSearchDetail_content" /> 
			</li>
			<li id="manageSearchDetail_range_li" style="display: none;">
				<label><bean:message bundle="define" key="define.search.detail.column.value" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.search.detail.column.value.tips.two" />" /></label>
				<input type="text" name="rangeMin" id="rangeMin" maxlength="100" class="manageSearchDetail_rangeMin small" />
				<input type="text" name="rangeMax" id="rangeMax" maxlength="100" class="manageSearchDetail_rangeMax small" />
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.serach.detail.is.display" />   <img src="images/tips.png" title="<bean:message bundle="define" key="define.serach.detail.is.display.tips" />" /></label>
				<html:select property="display" styleClass="manageSearchDetail_display">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageSearchDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageSearchDetail_description"></html:textarea>
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
			if($('.searchHeader_form .manageSearchHeader_useJavaObject').is(':checked')){
				flag = flag + validate('manageSearchDetail_propertyName', true, 'common', 100, 0);
			}else{
			    flag = flag + validate('manageSearchDetail_columnId', true, 'select', 0, 0);
			}

			flag = flag + validate('manageSearchDetail_nameZH', true, 'common', 100, 0);
			flag = flag + validate('manageSearchDetail_columnIndex', true, 'numeric', 2, 0);
			
			//	特殊验证
			flag = flag + validate('manageSearchDetail_description', false, 'common', 500, 0);
			flag = flag + validate('manageSearchDetail_status', true, 'select', 0, 0);
			
		    return flag;
		};
		
		// 搜索字段值类型onchange事件
		$('.manageSearchDetail_contentType').change(function(){
			$('#manageSearchDetail_content_li').hide();
			$('#manageSearchDetail_range_li').hide();
			// 直接值
			if($('.manageSearchDetail_contentType').val() == '1'){
				$('#manageSearchDetail_content_li').show();
				$('#manageSearchDetail_range_li').hide();
			}
			// 区间值
			else if($('.manageSearchDetail_contentType').val() == '2'){
				$('#manageSearchDetail_content_li').hide();
				$('#manageSearchDetail_range_li').show();
			}
		});
		
		// 是否使用联想change事件
		$('.manageSearchDetail_useThinking').change(function(){
			if($(this).val() == '1'){
				$('#thinkingActionLi').show();
			}else{
				$('#thinkingActionLi').hide();
			}
		});
		
		// 页面加载触发字段值类型
		$('.manageSearchDetail_contentType').trigger('change');
		// 页面加载触发是否使用联想
		$(".manageSearchDetail_useThinking").trigger("change");
	})(jQuery);
</script>