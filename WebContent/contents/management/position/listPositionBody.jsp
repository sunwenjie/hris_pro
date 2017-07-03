<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.management.PositionRender"%>
<%@ page import="com.kan.base.web.actions.management.PositionAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<div class="box toggableForm" id="position-information">
		<div class="head">
			<label id="pageTitle">
			<logic:equal value="1" name="role">ְλ���ⲿ����Ϣ</logic:equal>
			<logic:equal value="2" name="role">ְλ��Ϣ</logic:equal>
			</label>
		</div>
	</div>
	<!-- Position-information -->
	<div class="box noHeader" id="search-results">
		<!-- inner -->
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">����ɹ���</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">�༭�ɹ���</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=PositionAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('mgtPositionAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="delete" action="<%=PositionAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanTree_deleteConfirm('kantree','<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) deletePosition();" />				
				</kan:auth>
			</div>
			<!-- top -->
			<html:form action="mgtPositionAction.do?proc=list_object" styleClass="position_tree_form">
				<input type="hidden" name="subAction" id="subAction" value="" />
				<div id="position_info" class="kantab kantree">
					<%= PositionRender.getPositionTree( request ) %> 
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
		$('#menu_employee_Position').addClass('selected');
	})(jQuery);
	
	// �޸Ļ��½�Position
	function managePosition(positionId){
		link('mgtPositionAction.do?proc=to_objectModify&positionId=' + positionId);
	};

	//	�����ְλ�¼�
	function addChildPosition(parentPositionId){
		link('mgtPositionAction.do?proc=to_objectNew&parentPositionId=' + parentPositionId);
	}
	
	// ɾ��Position
	function deletePosition() {
		// �ύɾ������
		submitFormWithActionAndCallback('position_tree_form', 'deleteObjects', null, null, null, '', null, '');
		
		// ɾ���������ڵ�
		$('.kantree input[type=checkbox]:checked').each(function(i) {
			$('li[id$="_N' + $(this).val() + '"]').remove();
			$('li[id*="_N' + $(this).val() + '_"]').remove();
		});
	};
	
</script>