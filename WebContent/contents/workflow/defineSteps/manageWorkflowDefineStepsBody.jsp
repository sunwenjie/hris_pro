<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="systemPosition" class="box toggableForm">
		<div class="head">
			<label>城市</label>
		</div>		
		<div class="inner">
			<div class="top">
				<!-- 隐藏的添加审核步骤-->
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
<!-- 隐藏职位树 -->
<div id="positiontree" class="kantree" style="display:none;position:absolute;margin-top:10px;border:1px solid #617775;background:#f0f6e4;overflow-x:auto;"></div>
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_workflow_Modules').addClass('current');			
		$('#menu_workflow_Configuration').addClass('selected');
		
		// 初始化隐藏编辑按钮
		$('#btnEdit').hide();
		
		// 按钮提交事件
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
		
		// 保存按钮点击事件
		$('#btnSave').click( function () {
			btnSubmit();
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () {
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('steps_form');
				// Enable
				alert($('input[name=rightIdsArray]').length);
				$('input[name=rightIdsArray]').attr('disabled',false);
				//系统模块名不可更改
				$('#sysModules').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.steps_form').attr('action', 'workflowDefineStepsAction.do?proc=modify_object');
        	}else{
        		btnSubmit();
        	}
		});
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('steps_form');
			// 更换Page Title
			$('#pageTitle').html('查看作业步骤');
			// 更换按钮Value
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
		
		// 加载职位树 绑定回调函数
		$('#positiontree').load('secPositionAction.do?proc=list_object_html_tree',function(){
			var $positiontree = $(this);
			//职位树节点 绑定单击事件 ，实现单选和将选中值放入到form
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