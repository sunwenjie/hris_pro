<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.renders.management.SkillRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	
<%
	final PagedListHolder clientHolder = (PagedListHolder) request.getAttribute("clientHolder");
%>

<!-- Module Box HTML: Begins -->
<div class="modal hide" id="modalId">
    <div class="modal-header">
        <a class="close" data-dismiss="modal" onclick="$('#modalId').addClass('hide');$('#shield').hide();"">×</a>
        <label>技能添加</label>
    </div>
    <div class="modal-body">
        <form id="" method="post" action="">
        	<ol class="static">
               	<li>点击“技能名称”显示下一级技能列表</li>
            </ol>
            <fieldset>
                <div id="skill_level_div">
					<%= SkillRender.getSkillListOrderDiv( request, "0", null ) %>
				</div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
    	<input type="button" id="btn_AddSkill_lv" class="btn" value="<bean:message bundle="public" key="button.add" />" onclick="addSkills()"/>
    	<input type="button" id="btn_cancel_lv" name="btn_cancel_lv" class="btn reset" data-dismiss="modal" value="Cancel" />
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	
	// 移除规则事件
	function removeSkill(id){
		if(confirm("确定删除选中技能？")){
			$('#' + id).remove();
		};
		
		var selectedIdString = "";
		//	遍历skillinfo区域生成selectedIdString
		$('input[id="skillIdArray"]').each(function(i) {
			if(selectedIdString == ""){
				selectedIdString = $(this).val();
			}else{
				selectedIdString = selectedIdString + ',' + $(this).val();
			}
		});
		//	给skill赋值
		$('#skill').val(selectedIdString);
	}; 
	
	//	根据parentSkillId获得子childSkillList
	function showNextSkillListLvl(parentSkillId){
		loadHtmlWithRecall('#skill_level_div', 'skillAction.do?proc=list_skill_order_ajax&parentSkillId=' + parentSkillId, null, null);
	};
	
	//	skill区域返回上一级事件
	function pageBack(){
		var parentSkillId = $('#skill_lv_body_parentId').val();
		loadHtmlWithRecall('#skill_level_div', 'skillAction.do?proc=list_skill_order_ajax&presentSkillId=' + parentSkillId, null, null);
	};
	
	//	skill区域添加事件
	function addSkills(){
		modifySkill();
		loadHtmlWithRecall('#mannageSkill_ol', 'skillAction.do?proc=getSkillNameCombo_ajax&skill=' + $('.managePosition_skill').val(), null, "$('img[id^=warning_img]').each(function(i){$(this).show();}), $('img[id^=disable_img]').each(function(i){$(this).hide();})");
	};
	
	//	变更skill事件
	function modifySkill() {
		var selectedIdArray = $("#skill").val().split(",");
		var selectedIdString = "";
		var selectedNumber = 0;
	
		$('input[id^="kanList_chkSelectRecord_"]').each(function(i) {
			var exist = false;
			var i = 0;
	
			for (i = 0; i < selectedIdArray.length; i++) {
				if (selectedIdArray[i] == $(this).val()) {
					if (this.checked) {
						exist = true;
					} 
				}
			}
	
			if (exist == false && this.checked) {
				selectedIdArray[i] = $(this).val();
			}
		});
	
		for ( var i = 0; i < selectedIdArray.length; i++) {
			if (selectedIdArray[i] != "") {
				if (selectedNumber == 0) {
					selectedIdString = selectedIdString + selectedIdArray[i];
				} else {
					selectedIdString = selectedIdString + "," + selectedIdArray[i];
				}
				selectedNumber = selectedNumber + 1;
			}
		}
	
		$("#skill").val(selectedIdString);
	};

	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#orderModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>