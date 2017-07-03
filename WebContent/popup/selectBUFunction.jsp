<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="selectBUFunction">
    <div class="modal-header" id="selectBUFunctionHeader"> 
        <a class="close" data-dismiss="modal" onclick="$('#selectBUFunction').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label>
        	Export excel by BU/Function
        </label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnConrirm" id="btnConrirm" value="<bean:message bundle="public" key="button.selected" />" />
	    </div>
        <html:form action="performanceAction">
			<fieldset>
				<ol class="auto">
					
					<li>
						<label>BU/Function</label>
						<html:select property="selectBUFunctionName" styleClass="selectBUFunctionName_select" styleId="selectBUFunctionName_select">
							<html:optionsCollection property="buFunctions" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					
				</ol>
			</fieldset>
		</html:form >
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		// 选择模板导出事件
		$('#btnConrirm').click( function() {
			var buFunctionName = $('#selectBUFunctionName_select option:selected').text();
			if( buFunctionName != 'All' ){
				$('form.searchPerformanceForm .selectBUFunctionName').val(buFunctionName);
			}
			submitFormForDownload('searchPerformanceForm');
			$('form.searchPerformanceForm .selectBUFunctionName').val('');
		});
	})(jQuery);

	// 选择重置
	function resetFrom(){
		$('#selectBUFunctionName_select').val('0');
	};
	
	// 弹出模态窗口
	function popupSelectBUFunction( selectBUFunctionOption ){
		$('#selectBUFunction').removeClass('hide');
		$('#selectBUFunctionName_select').val(selectBUFunctionOption);
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#selectBankTemplate').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
</script>