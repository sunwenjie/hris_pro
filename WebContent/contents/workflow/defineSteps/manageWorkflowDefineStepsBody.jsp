<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="systemPosition" class="box toggableForm">
		<div class="head">
			<label>����</label>
		</div>		
		<div class="inner">
			<div class="top">
				<!-- ���ص������˲���-->
				<div id="defineSteps" >
					<jsp:include page="/contents/workflow/defineSteps/form/manageWorkflowDefineStepsForm.jsp" ></jsp:include>
				</div>
		  	</div>
			<!-- top -->
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
		</div>	
	</div>
</div>
<!-- ����ְλ�� -->
<div id="positiontree" class="kantree" style="display:none;position:absolute;margin-top:10px;border:1px solid #617775;background:#f0f6e4;overflow-x:auto;"></div>
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_workflow_Modules').addClass('current');			
		$('#menu_workflow_Configuration').addClass('selected');
		
		// ��ʼ�����ر༭��ť
		$('#btnEdit').hide();
		
		// ��ť�ύ�¼�
		function btnSubmit() {
			var flag = 0;
			flag = flag + validate("workflowDefineSteps_stepType", true, "select", 0, 0);
			flag = flag + validate("workflowDefineSteps_positionId", true, "common", 50, 0);
			flag = flag + validate("workflowDefineSteps_stepIndex", true, "numeric", 4, 0);
			flag = flag + validate("workflowDefineSteps_status", true, "select", 0, 0);
			
			if(flag == 0){
				submit("steps_form");
			}
		};
		
		// ���水ť����¼�
		$('#btnSave').click( function () {
			btnSubmit();
		});
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () {
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('steps_form');
				// Enable
				alert($('input[name=rightIdsArray]').length);
				$('input[name=rightIdsArray]').attr('disabled',false);
				//ϵͳģ�������ɸ���
				$('#sysModules').attr('disabled', 'disabled');
				// ����Subaction
        		$('.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.steps_form').attr('action', 'workflowDefineStepsAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// �鿴ģʽ
		if($('.subAction').val() == 'viewObject'){
			// ��Form��ΪDisable
			disableForm('steps_form');
			// ����Page Title
			$('#pageTitle').html('�鿴��ҵ����');
			// ������ťValue
			$('#btnSave').hide();
			$('#btnEdit').show();
		}
		
		$('#btnCancel').click( function () {
			back();
		});
		
		function showMenu() {
			var selectInputObj = $("#workflowDefineSteps_positionId");
			var selectInputObjOffset = $("#workflowDefineSteps_positionId").offset();
		    $("#positiontree").css({left:selectInputObjOffset.left + "px", top:selectInputObjOffset.top + selectInputObj.height()+ "px"}).width(selectInputObj.width()-12).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		};
		
		function hideMenu() {
			$("#positiontree").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		};
		
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "workflowDefineSteps_positionId" || event.target.id == "positiontree" || $(event.target).parents("#positiontree").length>0)) {
				hideMenu();
			}
		};
		
		// ����ְλ�� �󶨻ص�����
		$('#positiontree').load('secPositionAction.do?proc=list_object_html_tree',function(){
			var $positiontree = $(this);
			//ְλ���ڵ� �󶨵����¼� ��ʵ�ֵ�ѡ�ͽ�ѡ��ֵ���뵽form
			$(this).find('input[type=checkbox]').click(function(){
				$positiontree.find("input:checked ").attr('checked',false);
				$(this).attr('checked',true);
				$("#workflowDefineSteps_positionId").val($(this).next('a').text());
				$("#positionId").val($(this).val());
			});
		});
		
		$("#workflowDefineSteps_positionId").click(function(){showMenu();});
	})(jQuery);
</script>