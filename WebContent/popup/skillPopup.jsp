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
        <a class="close" data-dismiss="modal" onclick="$('#modalId').addClass('hide');$('#shield').hide();"">��</a>
        <label>�������</label>
    </div>
    <div class="modal-body">
        <form id="" method="post" action="">
        	<ol class="static">
               	<li>������������ơ���ʾ��һ�������б�</li>
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
	
	// �Ƴ������¼�
	function removeSkill(id){
		if(confirm("ȷ��ɾ��ѡ�м��ܣ�")){
			$('#' + id).remove();
		};
		
		var selectedIdString = "";
		//	����skillinfo��������selectedIdString
		$('input[id="skillIdArray"]').each(function(i) {
			if(selectedIdString == ""){
				selectedIdString = $(this).val();
			}else{
				selectedIdString = selectedIdString + ',' + $(this).val();
			}
		});
		//	��skill��ֵ
		$('#skill').val(selectedIdString);
	}; 
	
	//	����parentSkillId�����childSkillList
	function showNextSkillListLvl(parentSkillId){
		loadHtmlWithRecall('#skill_level_div', 'skillAction.do?proc=list_skill_order_ajax&parentSkillId=' + parentSkillId, null, null);
	};
	
	//	skill���򷵻���һ���¼�
	function pageBack(){
		var parentSkillId = $('#skill_lv_body_parentId').val();
		loadHtmlWithRecall('#skill_level_div', 'skillAction.do?proc=list_skill_order_ajax&presentSkillId=' + parentSkillId, null, null);
	};
	
	//	skill��������¼�
	function addSkills(){
		modifySkill();
		loadHtmlWithRecall('#mannageSkill_ol', 'skillAction.do?proc=getSkillNameCombo_ajax&skill=' + $('.managePosition_skill').val(), null, "$('img[id^=warning_img]').each(function(i){$(this).show();}), $('img[id^=disable_img]').each(function(i){$(this).hide();})");
	};
	
	//	���skill�¼�
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

	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#orderModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>