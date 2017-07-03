<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="popup modal small content hide" id="selectSBStatus">
    <div class="modal-header" id="selectSBStatusHeader"> 
        <a class="close" id="popup_class" data-dismiss="modal" onclick="$('#selectSBStatus').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="sb" key="sb.status.select" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnConrirm" id="btnConrirm" value="<bean:message bundle="public" key="button.confirm" />" onclick="confirmSBSubmitForm();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetSBSubmitFrom()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
	    <div style="color: red" id="sbStatusChangeWarningDiv"></div>
	    <form>
		    <fieldset>
			    <ol class="auto">
			    	<li>
				    <div id="sbToApplyForMoreDiv" class="hide">
				    	<label><bean:message bundle="sb" key="sb.add.status.select" />  <img title="<bean:message bundle="sb" key="sb.add.status.select.tips" />" src="images/tips.png"/></label> 
						<select id="sbStatusAdd" name="sbStatusAdd">
							<option value="2" label="<bean:message bundle="sb" key="sb.status.to.apply.for.more" />"><bean:message bundle="sb" key="sb.status.to.apply.for.more" /></option>
							<option value="0" label="<bean:message bundle="sb" key="sb.status.no.social.benefit" />"><bean:message bundle="sb" key="sb.status.no.social.benefit" /></option>
						</select>
				    </div>
				    </li>
			    </ol>
			 	 <ol class="auto">
			    	<li>
				    <div id="sbToApplyForResigningDiv" class="hide">
				    	<label><bean:message bundle="sb" key="sb.back.status.select" />  <img title="<bean:message bundle="sb" key="sb.back.status.select.tips" />" src="images/tips.png"/></label> 
						<select id="sbStatusBack" name="sbStatusBack">
							<option value="5" label="<bean:message bundle="sb" key="sb.status.to.apply.for.resigning" />"><bean:message bundle="sb" key="sb.status.to.apply.for.resigning" /></option>
							<option value="3" label="<bean:message bundle="sb" key="sb.status.normal" />"><bean:message bundle="sb" key="sb.status.normal" /></option>
						</select>
				    </div>
		    		</li>
			    </ol>
		    </fieldset>
	    </form>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">

	function resetSBSubmitFrom(){
		$("#sbStatusAdd").val("2");
		$("#sbStatusBack").val("5");
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#selectSBStatus').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>