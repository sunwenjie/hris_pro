<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.security.StaffRender"%>
<%@ page import="com.kan.base.web.renders.system.ModuleRender"%>
<%@ page import="com.kan.base.web.renders.system.RightRender"%>
<%@ page import="com.kan.base.web.renders.system.RuleRender"%>
<%@ page import="com.kan.base.domain.system.ModuleDTO"%>
<%@ page import="java.util.List"%>

<div id="content">
	<div id="systemLocation" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">ȫ�ֹ�������</label>
		</div>
		<div id="manageFormDiv" class="inner" >
			<div id="messageWrapper"></div>
			<div class="kantab kantree"> 
				<html:form action="moduleAction.do?proc=modify_accountModule_ajax" styleClass="module_form">
					<input type="hidden" id="moduleIdArray" name="moduleIdArray" />
					<div id="messageWrapper"></div>
					<ol class="auto">
						<li style="margin: 0px;">
							<div style="width: 100%; border: 0px;" id="globalFormDiv" >
								<ol class="static">
									<%= ModuleRender.getModuleTree( request, false ) %>
								</ol>
							</div>
						</li>
						<li style="margin: 0px;">
							<div id="right_rule" style="padding: 10px; display: none;"></div>
						</li>
					</ol>
				</html:form>
			</div>
			<div class="bottom">
				<p>
			</div>
		</div> 
	</div>
</div>
<div id="popupWrapper">
	<jsp:include page="/popup/security/rightRulePopup.jsp"></jsp:include>
</div>

<script type="text/javascript">
	// ���ò˵�ѡ����ʽ
	$('#menu_security_Modules').addClass('current');
	$('#menu_security_Rule').addClass('selected');
	
	//�л���������
	function changeRuleType(moduleId) {
		loadHtml('.rulecombo', 'ruleAction.do?proc=list_object_html_select&moduleId=' + moduleId + '&ruleType=' + $('.combo_ruleTypeId').val(), false);
	};
	
	//���Module���ڵ�
	function manageModule(element, accountId, moduleId){
		// ģ̬��moduleId ��ֵ
		$('.module_form input[id=moduleId]').val(moduleId);
		// �����ģ�飬����Right��Rule��ģ̬��
		loadHtmlWithRecall('#rightAndRuleDiv', 'moduleAction.do?proc=list_authority_combo_ajax&moduleId=' + moduleId, false, showPopup());
		
		$('input[type^="hidden"]#moduleIdArray').val(moduleId);
		
		($("a[id='manageModule']")).each(function(i) {
			$(this).removeClass('current');
		});
		
		$(element).addClass('current');
	};
    
 	// ��ӹ��� - Add by Kevin
	function addRule() {
 		if( $('.combo_ruleTypeId').val() != '0' && $('.combo_ruleId').val() != '0'){
 			var exist = false;
 			
 			$('input[id^="ruleIdArray"]').each(function(i) {
 				if( $(this).val().indexOf($('.combo_ruleTypeId' ).val() + '_') >= 0 ){
 					exist = true;
 				}
       		});
 			
 			if(!exist){	
	 			$('#combo_rule').append('<li id="rule_' + $('.combo_ruleId' ).val() + '"><input type="hidden" id="ruleIdArray" name="ruleIdArray" value="' + $('.combo_ruleTypeId' ).val() + '_' + $('.combo_ruleId' ).val() + '"><img src="images/warning-btn-s.png" onclick="removeRule(\'rule_' + $('.combo_ruleId' ).val() + '\');"/> &nbsp;&nbsp; ' + $('#option_ruleTypeId_' + $('.combo_ruleTypeId').val() ).html() + ' - ' + $('#option_ruleId_' + $('.combo_ruleId').val() ).html() + '</li>');
 			}else{
 				alert('��ͬ��������ֻ�����һ�Σ�');
 			}
 		}else{
 			alert('����д��Ҫ����Ϣ��');
 		}
    };
	
	// �Ƴ������¼� - Add by Kevin
	function removeRule(id){
		if(confirm("ȷ��ɾ������󶨣�")){
			$('#' + id).remove();
		}
	}; 
	
	// �������Ȩ��
	function saveRightRule(){
		submitForm('module_form', 'modifyObject', null, null, null, '', 'moduleAction.do?proc=modify_accountModule_ajax', 'hidePopup();checkImg();enableBtn();', true);
	}
</script>