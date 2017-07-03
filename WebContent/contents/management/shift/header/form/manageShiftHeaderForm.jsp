<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<fieldset>
	<ol class="auto">
		<li class="required">
			<label><em>* </em><bean:message bundle="public" key="required.field" /></label>
			<span class="highlight"><bean:message bundle="management" key="management.shift.header.tips" /></span>
		</li>
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="management" key="management.shift.header.name.cn" /><em> *</em></label> 
			<logic:notEmpty name="minPeriod">
				<input type="hidden" value="<bean:write name="minPeriod" />" id="minPeriod"  />
			</logic:notEmpty>
			<logic:notEmpty name="maxPeriod">
				<input type="hidden" value="<bean:write name="maxPeriod" />" id="maxPeriod" />
			</logic:notEmpty>
			<html:text property="nameZH" maxlength="100" styleClass="manageShiftHeader_nameZH" /> 
		</li>
		<li>
			<label><bean:message bundle="management" key="management.shift.header.name.en" /></label> 
			<html:text property="nameEN" maxlength="100" styleClass="manageShiftHeader_nameEN" /> 
		</li>
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="management" key="management.shift.header.type" /><em> *</em></label> 
			<html:select property="shiftType" styleClass="manageShiftHeader_shiftType">
				<html:optionsCollection property="shiftTypies" value="mappingId" label="mappingValue" />
			</html:select>
		</li>
	</ol>
	<ol class="auto" id="shiftDetailOL">	
		<li style="display: none;">
			<label><bean:message bundle="management" key="management.shift.header.start.date" /><em> *</em></label> 
			<html:text property="startDate" maxlength="10" styleClass="Wdate manageShiftHeader_startDate" />
		</li>
		<li style="display: none;">
			<label><bean:message bundle="management" key="management.shift.header.cycle" /><em> *</em></label> 
			<html:text property="shiftIndex" maxlength="2" styleClass="manageShiftHeader_shiftIndex" /> 
		</li>
	</ol>	
	<ol class="auto">	
		<li>
			<label><bean:message bundle="public" key="public.description" /></label> 
			<html:textarea property="description" styleClass="manageShiftHeader_description"></html:textarea>
		</li>
		<li>
			<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
			<html:select property="status" styleClass="manageShiftHeader_status">
				<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
			</html:select>
		</li>
	</ol>	
</fieldset>

<script type="text/javascript">
		(function($) {
		    // 编辑按钮点击事件 - SocialBenefit Header
			$('#btnEditShiftHeader').click(function(){
				if($('.manageShift_form input#subAction').val() == 'viewObject'){  
					// Enable整个Form
	        		enableForm('manageShift_form');
	        		// 设置当前Form的SubAction为修改状态
	        		$('.manageShift_form input#subAction').val('modifyObject'); 
	        		// 更改Form Action
	        		$('.manageShift_form').attr('action', 'shiftHeaderAction.do?proc=modify_object');
	        		// 修改按钮显示名称
	        		$('#btnEditShiftHeader').val('<bean:message bundle="public" key="button.save" />');
	        		// 修改Page Title
	        		$('#pageTitle').html('<bean:message bundle="management" key="management.shift" /> <bean:message bundle="public" key="oper.edit" />'); 
	        		if($('.manageShift_form .manageShiftHeader_shiftType').val() == '3'){
	        			$('#btnDefine').show();
	        		}
				}else{
					if(validate_form() == 0){
						submit('manageShift_form');
					}
				}
			});
		    
			if($('.manageShift_form input#subAction').val() == 'viewObject'){  
	    		// 修改Page Title
	    		$('#pageTitle').html('<bean:message bundle="management" key="management.shift" /> <bean:message bundle="public" key="oper.view" />'); 
			}
	    		
			// header 取消按钮
			$('#btnCancelShiftHeader').click(function(){
				if(agreest())
				link('shiftHeaderAction.do?proc=list_object');
			});
	
			// 排班类型change事件
			$('.manageShiftHeader_shiftType').change(function(){
				if( $('.manageShift_form input#subAction').val() != 'viewObject'){
					$('#special_info').html('');
					$('.manageShiftHeader_shiftIndex').val('');
					$('.manageShiftHeader_startDate').val('');
					$('#ShiftDetail-information').hide();
				}
				$('#shiftDetailOL li').hide();
				if($(this).val()=='1' || $(this).val()=='2'){
					$('#shiftDetailOL li').show();
				}
			});
		})(jQuery);
		
		function validate_form(){
			var flag = 0;
			
			flag = flag + validate("manageShiftHeader_nameZH", true, "common", 100, 0);
			flag = flag + validate("manageShiftHeader_shiftType", true, "select", 0, 0);
			flag = flag + validate_part();
			flag = flag + validate("manageShiftHeader_description", false, "common", 500, 0);
			flag = flag + validate("manageShiftHeader_status", true, "select", 0, 0);
			
			return flag;
		};
		
		function validate_part(){
			var flag = 0;
			
			if($('.manageShiftHeader_shiftType').val() == '1'){
				flag = flag + validate("manageShiftHeader_shiftIndex", true, "numeric", 2, 0, 4, 1);
				flag = flag + validate("manageShiftHeader_startDate", true, "common", 10, 0);
			}else if($('.manageShiftHeader_shiftType').val() == '2'){
				flag = flag + validate("manageShiftHeader_shiftIndex", true, "numeric", 2, 0, 10, 1);
				flag = flag + validate("manageShiftHeader_startDate", true, "common", 10, 0);
			}
			
			return flag;
		};
</script>
