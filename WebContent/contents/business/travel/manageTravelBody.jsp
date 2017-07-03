<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.hro.web.actions.biz.travel.TravelAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;差旅
			</label>
			<logic:notEmpty name="travelForm" property="travelId">
				<label class="recordId"> &nbsp; (ID: <bean:write name="travelForm" property="travelId" />)</label>
			</logic:notEmpty>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message success fadable">
						<bean:write name="MESSAGE" />
						<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<logic:empty name="travelForm" property="encodedId">
					<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />">
				</logic:empty>
				<logic:notEmpty name="travelForm" property="encodedId">
						<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />">
				</logic:notEmpty>
				<logic:notEqual name="role" value="5">
					<kan:auth right="sickleave" action="<%=TravelAction.accessAction%>">
						<input type="button" class="function" name="btnSickTravel" id="btnSickTravel" value="<bean:message bundle="public" key="button.retrieve" />" style="display: none;">
					</kan:auth>
				</logic:notEqual>
				<kan:auth right="submit" action="<%=TravelAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;" />
				</kan:auth>
				<kan:auth right="list" action="<%=TravelAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />">
				</kan:auth>
				<kan:auth right="delete" action="<%=TravelAction.accessAction%>">
					<logic:equal name="hasDeleteRight" value="true">
						<input type="button" class="delete" name="btnDelete" id="btnDelete" value="<bean:message bundle="public" key="button.delete" />">
					</logic:equal>
				</kan:auth>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->
			<jsp:include page="/contents/business/travel/form/manageTravelForm.jsp" flush="true"></jsp:include>
		</div>
	</div>
</div>
<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
</div>
<script type="text/javascript">
	(function($) {
		// 初始化菜单样式
		//$('#menu_attendance_Modules').addClass('current');
		//$('#menu_attendance_Travel').addClass('selected');
		$('#employeeId').after('<a onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>');
		<logic:equal name="role" value="5">
			$('#employeeId').attr('disabled', 'disabled');
			getEmployee();
	    </logic:equal>
	
		
		
		if ( getSubAction() != 'createObject' ) {
		    disableForm('manageTravel_form');
		    $('.manageTravel_form input.subAction').val('viewObject');
		    $('#pageTitle').html('<bean:message bundle="public" key="oper.view" />差旅信息');
		    $('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		if(getSubAction() == 'viewObject'){
			getEmployee();
	    }
		
			$('#nameZH').attr('disabled', 'disabled');
			$('#nameEN').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
		
		
		<%// 雇员ID错误消息
			final String employeeIdErrorMsg = (String) request
					.getAttribute("employeeIdErrorMsg");

			if (KANUtil.filterEmpty(employeeIdErrorMsg) != null) {
				out.print("addError('manageTravel_employeeId', '"
						+ employeeIdErrorMsg + "');");
			}%>
			
	})(jQuery);
	
	// 绑定雇员ID KeyUp事件，加载雇员相关信息；
	$('#employeeId').bind('keyup',function(){
		if( $("#employeeId").val().length >= 9 ){
			getEmployee();
		}
	});
	
	
	function getEmployee(){
		$.ajax({
			url : "employeeAction.do?proc=get_object_json&employeeId=" + $("#employeeId").val() + "&subAction=" + getSubAction(), 
			dataType : "json", 
			success : function(data){
				cleanError('manageTravel_employeeId');
				if(data.success=='true'){
					$('#nameZH').val(data.nameZH);
					$('#nameEN').val(data.nameEN);
					
				}else{
					$('#nameZH').val('');
					$('#nameEN').val('');
					addError('manageTravel_employeeId', data.errorMsg );
				}
				
			}
		});
	}
		
		// 删除请假记录
		$('#btnDelete').click( function(){
			if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){
				link('travelAction.do?proc=delete_objectList&selectedId=<bean:write name="travelForm" property="travelId" />');
			}
		});
		
		$('#btnList').click(function() {
			if(agreest())
		    link('travelAction.do?proc=list_object');
		});
		
		$('#btnEdit').click(function() {
		    if ( getSubAction() == 'viewObject') {
		        enableForm('manageTravel_form');
		        <logic:equal name="role" value="5">
		    	 	$('#employeeId').attr('disabled', 'disabled');
				</logic:equal>
		        
				<logic:notEqual name="role" value="5">
				 	$('#employeeId').addClass('important');
				</logic:notEqual>
		        $('#uploadAttachment').show();
		        $('.manageTravel_form input#subAction').val('modifyObject');
		        $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		        $('.manageTravel_form').attr('action', 'travelAction.do?proc=modify_object');
		        $('#pageTitle').html('差旅信息 <bean:message bundle="public" key="oper.edit" />');
		        $('li #warning_img').each(function() {
		            $(this).show();
		        });
		        $('li #disable_img').each(function() {
		            $(this).hide();
		        });
		        $('#nameZH').attr('disabled', 'disabled');
		        $('#nameEN').attr('disabled', 'disabled');
		    	$('#startDate').attr('disabled', 'disabled');
				$('#sndDate').attr('disabled', 'disabled');
		        $('#status').attr('disabled', 'disabled');
		    } else {
		    	
		        var flag = validate_manage_primary_form();
		        if (flag == 0) {
		            submit('manageTravel_form');
		        }
		    }
		});
	    
	   // 提交按钮事件
		$('#btnSubmit').click( function () { 
			if( validate_manage_primary_form() == 0){
				if( getFlag() == false ){
					if(!confirm('<bean:message bundle="public" key="popup.leave.not.entry.ts" />')){
						return;
					}
				}
				// 更改当前Form的SubAction
				if( getSubAction() != 'createObject'){
					$('.manageTravel_form').attr('action', 'travelAction.do?proc=modify_object');
				}
				$('.manageTravel_form input#subAction').val('submitObject');
					
		    	enableForm('manageTravel_form');
		    	submitForm('manageTravel_form');
			}
		});
	    
		// 获取当前SubAction
		function getSubAction(){
			return $('.manageTravel_form input#subAction').val();
		};
		
</script>
