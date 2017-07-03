<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<style type="text/css">
	#ckbDiv
	{
		width: 215px;
		height: 15px;	
		padding: 5px 12px 12px 5px;
	}
	
	#ckbDiv span
	{
	    position:relative;
	    top: 2px;
	    right: -5px;
	}
</style>
	
<html:form action="shiftExceptionAction.do?proc=add_object" styleClass="manageShiftException_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="subAction" id="subAction" class="subAction" value="<bean:write name="shiftExceptionForm" property="subAction" />" />
	<input type="hidden" name="exceptionId" value="<bean:write name="shiftExceptionForm" property="encodedId" />" />
	<input type="hidden" name="id" value="<bean:write name="shiftHeaderForm" property="encodedId" />" />
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.shift.exception.name.cn" /><em>* </em></label>
				<html:text property="nameZH" maxlength="100" styleClass="manageShiftException_nameZH" />
			</li>
			<li>
				<label><bean:message bundle="management" key="management.shift.exception.name.en" /></label>
				<html:text property="nameEN" maxlength="50" styleClass="manageShiftException_nameEN" />
			</li>
			<li>
				<label><bean:message bundle="management" key="management.shift.exception.type" /><em>* </em></label>
				<html:select property="exceptionType" styleClass="manageShiftException_exceptionType" >
					<html:optionsCollection label="mappingValue" property="exceptionTypes" value="mappingId" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.shift.exception.day.type" /></label>
				<html:select property="dayType" styleClass="manageShiftException_dayType" >
					<html:optionsCollection label="mappingValue" property="dayTypes" value="mappingId" />
				</html:select>
			</li>	
			<li>
				<label><bean:message bundle="management" key="management.shift.exception.day" /></label> 
				<html:text property="exceptionDay" maxlength="10" readonly="true" styleClass="Wdate manageShiftException_exceptionDay" onfocus="WdatePicker();"/>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.shift.exception.item" /><img src="images/tips.png" title="<bean:message bundle="management" key="management.shift.exception.item.tips" />" /></label>
				<html:select property="itemId" styleClass="manageShiftException_itemId" >
					<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
					<html:optionsCollection label="mappingValue" property="leaveItems" value="mappingId" styleClass="leave_item" style="display:none;" />
					<html:optionsCollection label="mappingValue" property="otItems" value="mappingId" styleClass="ot_item" style="display:none;" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label>
				<html:select property="status" styleClass="manageShiftException_status" >
					<html:optionsCollection label="mappingValue" property="statuses" value="mappingId" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.shift.detail.period" /><em> *</em></label>
				<input type="hidden" id="pArray" value="<bean:write name="shiftExceptionForm" property="exceptionPeriod" />" />
				<div id="ckbDiv">
					<span>
						<input type="checkbox" id="chk_all_e"  /><bean:message bundle="management" key="management.shift.select.all" />
					</span>
					<span style="float: right;position: relative;left: 0px;">
						<input type="checkbox" id="openORClose" class="inquiry"  /><bean:message bundle="management" key="management.shift.show.or.hide" />
					</span>
				</div>
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label></label>
				<div id="all_time" style="width: 215px;">
					<logic:iterate id="shiftPeriod" name="shiftExceptionForm" property="shiftPeriods" indexId="number">
						<label>
							<input type="checkbox" id="chk_<%=number+1 %>" class="manageShiftException_shiftPeriodArray" name="shiftPeriodArray" value="<%=number+1 %>" />
							<span><bean:write name="shiftPeriod" property="mappingValue" /></span>
						</label>
					</logic:iterate>	
				</div>
			</li>
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		shiftDefineInitHide_e();
		
		// 全选 
		$('form.manageShiftException_form #chk_all_e').click(function(){
			var flag = $(this).is(':checked');
			$('form.manageShiftException_form input[id^="chk_"]').not(':hidden').attr('checked',flag); 
		});
		
		// 修改状态下，选中checkbox
		if($('form.manageShiftException_form input#subAction').val() == 'viewObject'){
			var array = '<bean:write name="shiftExceptionForm" property="exceptionPeriod" />'.replace('{','').replace('}','').split(',');
			for( var val in array){
				$('form.manageShiftException_form #chk_' +array[val] +'').attr('checked',true); 
			}
		}
		
		// 显示、隐藏时间段Click
		$('form.manageShiftException_form #openORClose').click(function(){
			if($(this).hasClass('inquiry')){
				shiftDefineShowAll_e();
				$(this).removeClass('inquiry');
			}else{
				shiftDefineInitHide_e();
				$(this).addClass('inquiry');
			}
		});
		
		// 例外类型change
		$('.manageShiftException_exceptionType').change( function(){
			$('.leave_item').hide();
			$('.ot_item').hide();
			if( $(this).val() == 1){
				$('.leave_item').show();
			}else if( $(this).val() == 2){
				$('.ot_item').show();
			}
		});
		
		// 日期类型change
		$('.manageShiftException_dayType').change( function(){
			if($(this).val() != 0){
				$('.manageShiftException_exceptionDay').val('');
				$('.manageShiftException_exceptionDay').attr('disabled', 'disabled');
			}else{
				$('.manageShiftException_exceptionDay').removeAttr('disabled');
			}
		});
		
		
		// 日期focus
		$('.manageShiftException_exceptionDay').focus( function(){
			if($(this).val() != ''){
				$('.manageShiftException_dayType').val('0');
				$('.manageShiftException_dayType').attr('disabled', 'disabled');
			}else{
				$('.manageShiftException_dayType').removeAttr('disabled');
			}
		});
		
		// 默认触发事件
		$('.manageShiftException_exceptionType').trigger('change');
	})(jQuery);
	
	function shiftDefineInitHide_e(){
		var minPeriod = 19;
		var maxPeriod = 36;
		var periodArray = new Array();
		
		if( $('#pArray').val() != '' ){
			periodArray = $('#pArray').val().replace( "{", "" ).replace( "}", "" ).replace( ":", "," ).split( "," );
			if( periodArray.length > 0){
				minPeriod = periodArray[0];
				maxPeriod = periodArray[periodArray.length - 1];
			} 
		}
	
		$('form.manageShiftException_form #all_time input[type="checkbox"]').each(function(){
			if($(this).val() < minPeriod || $(this).val() > maxPeriod){
				$(this).parents('label').hide();
			}
		});
	};
	
	function shiftDefineShowAll_e(){
		$('form.manageShiftException_form #all_time input[type="checkbox"]').each(function(){
			if($(this).is(':hidden')){
				$(this).parents('label').show();
			}
		});
	}; 
</script>
