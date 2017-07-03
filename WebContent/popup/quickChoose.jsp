<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal hide" id="quickChooseModalId">
    <div class="modal-header" id="quickChooseHeader"> 
        <a class="close" data-dismiss="modal" onclick="$('#quickChooseModalId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label><bean:message bundle="public" key="button.quick.choose" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" onclick="btnSave();" />
	   		<input id="checkAll_chooseColumns" type="checkbox" /><label><bean:message bundle="management" key="management.shift.select.all" /></label>
	    </div>
		    	
        <html:form action="importDetailAction.do?proc=quickChoose" styleClass="listChooseColumn_form">
        	<input type="hidden" name="importHeaderId" value='<bean:write name="importHeaderForm" property="encodedId"/>'>
        	<fieldset>
	        	<ol class="auto chooseColumnOL">
		        		
	        	</ol>
        	</fieldset>
        </html:form>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	
	// 弹出模态窗口
	function popupQuickChoose(){
		$('#quickChooseModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#quickChooseModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
	(function($) {
		//初始化加载 复选框
		$.post("importDetailAction.do?proc=list_object_ajax",{importHeaderId:'<bean:write name="importHeaderForm" property="encodedId"/>'},function(data){
			$(".chooseColumnOL").html(data);					
		},"text");
		
		// btnSave 点击
		$("#btnSave").click(function(){
			submitFormWithActionAndCallbackAndDecode( "listChooseColumn_form", null, null, null, null, "contentStep2", null, "$('#quickChooseModalId').addClass('hide');$('#shield').hide();", false); 
		});
		
		// 全选
		$("#checkAll_chooseColumns").click(function() {
            $('input[name="selectColumns"]').attr("checked",this.checked);
            var $selectColumns = $("input[name='selectColumns']");
            $selectColumns.click(function(){
                $("#checkAll_chooseColumns").attr("checked",$selectColumns.length == $("input[name='selectColumns']:checked").length ? true : false);
            });
        });
	})(jQuery);
</script>