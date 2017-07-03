<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div class="box" id="workflowDefine-information">
		<div class="head">
			<label>����������</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_HEADER">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">����ɹ���</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">�༭�ɹ���</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<!-- jspInclude ����form����Ӧjspҳ��  formClass .workflowDefine_form -->
			<jsp:include page="/contents/workflow/define/form/manageWorkflowDefineFormInHouse.jsp"></jsp:include>
			<div class="tabMenu"> 
				<ul> 
					<li id="tabMenu1" onClick="changeTab(1,3)" class="first hover">1 - ��������</li> 
					<li id="tabMenu2" onClick="changeTab(2,3)" >2 - ���貹��</li> 
				</ul> 
			</div> 
			<div class="tabContent"> 
				<div id="tabContent1" class="kantab kanThinkingCombo" >
					<!-- ����step1ҳ�� -->
<%-- 					<jsp:include page="/contents/workflow/defineSteps/includePage/manageDefineSteps.jsp" flush="true"></jsp:include>	 --%>
				</div>
				<div id="tabContent2" class="kantab kanThinkingCombo" style="display:none">
					<!-- ����step2ҳ�� -->
					<jsp:include page="/contents/workflow/defineSteps/includePage/manageWorkflowDefineSteps.jsp" flush="true"></jsp:include>	
				</div>
				
			</div>
		</div>	
		
	</div>	
	<!-- search-results -->

</div>
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_workflow_Modules').addClass('current');			
		$('#menu_workflow_Configuration').addClass('selected');
		
		//------.workflowDefine_form  begin--------------
		//  �༭ģʽ�� ���ع���������Ȩ��
		$("#rightIds_div").load("workflowModuleAction.do?proc=list_Module_rightIds_html_checkBox",
				{"workflowModuleId":$('.workflowDefine_workflowModuleId').val(),"checkBoxName":"rightIdsArray","rightIds":'<bean:write name="workflowDefineForm" property="rightIds"/>'},
				function(){
					$(".workflowDefine_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
				});
		//  �༭ģʽ�� ������÷�Χ���ⲿ����ȥ�������������͵�{������֯�ܹ�}
		if($('.workflowDefine_scope').val()== 2){
			$("#workflowDefine_approvalType option ").each(function(){
				if($(this).val()==1){
			 		$(this).remove();
				}
			});
		}
		// disableForm Define��
		disableForm('workflowDefine_form');
		
		// ��ʼ������Define��Ӱ�ť 
		$('.workflowDefine_form input#btnSave').hide();
		
		// .workflowDefine_form ��ť�ύ�¼�
		function btnDefineSubmit() {
			var flag = 0;
			flag = flag + validate("workflowDefine_workflowModuleId", true, "select", 100, 0);
			flag = flag + validate("workflowDefine_nameZH", true, "common", 100, 0);
			flag = flag + validate("workflowDefine_nameEN", true, "common", 100, 0);
			flag = flag + validate("workflowDefine_approvalType", true, "select", 0, 0);
			flag = flag + validate("workflowDefine_status", true, "select", 0, 0);
			if(flag == 0){
				//ȫ�� ��Ϊ�ɱ༭
        		enableForm('workflowDefine_form');
        		$('.workflowDefine_form .subAction').val('');
				submit('workflowDefine_form');
			}
		};
		
		// Define�༭��ť����¼�			
        $('.workflowDefine_form input#btnEdit').click(function(){

        	var $subAction = $('.workflowDefine_form  .subAction ');
        	if($subAction.val() == 'viewObject'){
        		//ȫ�� ��Ϊ�ɱ༭
        		enableForm('workflowDefine_form');
        		// ������ģ��Ȩ�� ���ֲ��ɱ༭
        		// $(".workflowDefine_form input[name=rightIdsArray]").each(function(){$(this).attr("disabled","disabled");});
        		//������ģ��id���ɸ�
        		$('.workflowDefine_workflowModuleId').attr('disabled', 'disabled');
				// ����Subaction
        		$('.workflowDefine_form .subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('.workflowDefine_form #btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����action ����
				$('.workflowDefine_form').attr('action','workflowDefineAction.do?proc=modify_object');
        	}else if($('.workflowDefine_form .subAction').val() == 'modifyObject'){
        		
        		//�ύ��
        		btnDefineSubmit();
        	}
        });
		
		//Define ȡ����ť�����¼�
		$('.workflowDefine_form #btnList').click( function () {
			if(agreest())
			link('workflowDefineAction.do?proc=list_object');
		});	
		
		//------.workflowDefine_form  end--------------
		
	})(jQuery);
</script>