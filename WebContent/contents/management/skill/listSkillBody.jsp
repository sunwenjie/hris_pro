<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.management.SkillRender"%>
<%@ page import="com.kan.base.web.actions.management.SkillAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box toggableForm" id="skill-information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="management" key="management.skill.search.titile" /></label>
		</div>
	</div>
	<!-- Skill-information -->
	<div class="box noHeader" id="search-results">
		<!-- inner -->
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=SkillAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('skillAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="delete" action="<%=SkillAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanTree_deleteConfirm('kantree','<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) deletePosition();" />				
				</kan:auth>
			</div>
			<!-- top -->
			<html:form action="skillAction.do?proc=list_object" styleClass="skill_tree_form">
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="" />
				<div id="skill_info" class="kantab kantree">
					<%= SkillRender.getSkillTree( request ) %> 
				</div>
			</html:form>
			<div class="bottom">
				<p>
			</div>
			<!-- List Component -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->
</div>

<script type="text/javascript">
(function($) {
	$('#menu_employee_Modules').addClass('current');			
	$('#menu_employee_Configuration').addClass('selected');
	$('#menu_employee_Skills').addClass('selected');
	
	$("#tree").css("float", "left");
	$("#skillInfo").css("float", "right");
	$("#newSkillInfo").css("float", "right");
	$('#newSkillInfo').hide();
		
	})(jQuery);
	
	// 修改或新建Position
	function manageSkill(skillId){
		link('skillAction.do?proc=to_objectModify&skillId=' + skillId + new Date());
	};
	
	// 删除Position
	function deletePosition() {
		// 提交删除操作
		submitFormWithActionAndCallback('skill_tree_form', 'deleteObjects', null, null, null, '', null, '');
		
		// 删除界面树节点
		$('.kantree input[type=checkbox]:checked').each(function(i) {
			$('li[id$="_N' + $(this).val() + '"]').remove();
			$('li[id*="_N' + $(this).val() + '_"]').remove();
		});
	};
	
	//	添加子技能事件
	function addChildSkill(parentSkillId){
		link('skillAction.do?proc=to_objectNew&parentSkillId=' + parentSkillId);
	}
	
</script>