<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.PositionGradeCurrencyAction"%>
<%@ page import="com.kan.base.web.actions.management.PositionGradeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%					
	final PagedListHolder positionGradeCurrencyHolder = ( PagedListHolder )request.getAttribute( "positionGradeCurrencyHolder" );
%>

<div id="content">
	<!-- PositionGrade-information -->
	<div class="box" id="PositionGrade-information">
		<div class="head">
			<label id="pageTitle">
				<logic:equal value="1" name="role">职位等级（外部）查询</logic:equal>
				<logic:equal value="2" name="role">职位等级查询</logic:equal>
			</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE_HEADER">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEditPositionGrade" id="btnEditPositionGrade" value="<bean:message bundle="public" key="button.edit" />" /> 
				</kan:auth>
				
				<kan:auth right="list" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<!-- Include table jsp （编辑header信息的jsp文件）  --> 
			<jsp:include page="/contents/management/positionGrade/positionGrade/form/managePositionGradeForm.jsp" flush="true"/> 
		</div>
	</div>
	
	<!-- PositionGradeCurrency-information -->
	<div class="box" id="PositionGradeCurrencyDiv" style="display: none;">
		<!-- Inner -->
		<div class="head">
			<label>指定货币</label>
		</div>						
		<div class="inner">		
			<div id="messageWrapper">
				<logic:present name="MESSAGE_DETAIL">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=PositionGradeCurrencyAction.accessAction%>">
					<input type="button" id="btnEditPositionGradeCurrency" name="btnEditPositionGradeCurrency" value="<bean:message bundle="public" key="button.add" />" />
				</kan:auth>
				
				<kan:auth right="list" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="reset" name="btnCancelPositionGradeCurrency" id="btnCancelPositionGradeCurrency" value="取消" style="display:none" />
				</kan:auth>	
				
				<kan:auth right="delete" action="<%=PositionGradeCurrencyAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listPositionGradeCurrency_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>
			<!-- positionGradeCurrencyFormWrapper -->
			<div id="positionGradeCurrencyFormWrapper" style="display:none" >
				<jsp:include page="/contents/management/positionGrade/positionGradeCurrency/form/managePositionGradeCurrencyForm.jsp" flush="true"/> 
			</div>
			
			<!-- if exits bean positionGradeCurrencyHolder -->		
			<logic:notEmpty name="positionGradeCurrencyHolder">						
				<html:form action="mgtPositionGradeCurrencyAction.do?proc=list_object" styleClass="listPositionGradeCurrency_form">
					<input type="hidden" name="positionGradeId" value="<bean:write name="mgtPositionGradeForm" property="encodedId"/>"/>			
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="positionGradeCurrencyHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="positionGradeCurrencyHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="positionGradeCurrencyHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="positionGradeCurrencyHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />		
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含table对应的jsp文件 --> 
					<jsp:include page="/contents/management/positionGrade/positionGradeCurrency/table/listPositionGradeCurrencyTable.jsp" flush="true"/> 
				</div>
			</logic:notEmpty>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>	
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_PositionGrades').addClass('selected');
		
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();

		// 字段OnChange事件
		$('.managePositionGradeCurrency_currencyType').change(function(){
			var flag = true;
			$('input[id^="currencyType"]').each(function(i) {
	    		if($(this).val() ==  $('.managePositionGradeCurrency_currencyType').val()){    
	    			alert('该货币类型已经存在，请重新选择！');
					$('.managePositionGradeCurrency_currencyType').val('0');
					flag = false;
	    		}	
	    	});
			
			if(flag){
				var currencyType = $('.managePositionGradeCurrency_currencyType').val();
				var positionGradeId = '<bean:write name="mgtPositionGradeForm" property="encodedId"/>';
				loadHtml('#managePositionGradeCurrency_moreinfo', 'mgtPositionGradeCurrencyAction.do?proc=to_objectModify_ajax&currencyType=' + currencyType + "&positionGradeId=" + positionGradeId, false);
			}
		});
		
		// 编辑按钮点击事件 Position Grade
		$('#btnEditPositionGrade').click( function () { 
			if($('.positionGrade_form input#subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('positionGrade_form');
				// 修改人、修改时间不可编辑
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// 设置当前Form的SubAction为修改状态
        		$('.positionGrade_form input#subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEditPositionGrade').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.positionGrade_form').attr('action', 'mgtPositionGradeAction.do?proc=modify_object');
        		// 更换Page Title
        		if('<bean:write name="role"/>' == 1 ){
	    			$('#pageTitle').html('职位等级（外部）编辑');
        		}else{
	    			$('#pageTitle').html('职位等级编辑');
        		}
        	}else{
        		var flag = 0;
    			flag = flag + validate("managePositionGrade_gradeNameZH", true, "common", 100, 0);
    			flag = flag + validate("managePositionGrade_status", true, "select", 0, 0);
    			flag = flag + validate("managePositionGrade_weight", false, "numeric", 0, 0);
    			flag = flag + validate("managePositionGrade_description", false, "common", 500, 0);
    			
    			if(flag == 0){
    				submit("positionGrade_form");
    			}
        	}
		});

		// 编辑按钮点击事件 Position Grade Currency
		$('#btnEditPositionGradeCurrency').click(function(){
			// 判断是添加、查看还是修改
			if($('.managePositionGradeCurrency_form input#subAction').val() == ""){
				// 显示Cancel按钮
				$('#btnCancelPositionGradeCurrency').show();
				//	显示detail修改区域信息
				$('#positionGradeCurrencyFormWrapper').show();
				// 修改按钮显示名称
				$('#btnEditPositionGradeCurrency').val('保存');
				// 设置SubAction为新建
				$('.managePositionGradeCurrency_form input#subAction').val('createObject');
			}else if($('.managePositionGradeCurrency_form input#subAction').val() == 'viewObject'){
				//	enable整个form
				enableForm('managePositionGradeCurrency_form');
				// 修改按钮显示名称
				$('#btnEditPositionGradeCurrency').val('保存');
				// 设置SubAction为编辑
				$('.managePositionGradeCurrency_form input#subAction').val('modifyObject');
				//	货币类型不可编辑
				$('.managePositionGradeCurrency_currencyType').attr('disabled', 'disabled');
				// 更改Form Action
	    		$('.managePositionGradeCurrency_form').attr('action', 'mgtPositionGradeCurrencyAction.do?proc=modify_object');
			}else{
				// 在此做添加或修改的验证
				var flag = 0;
				//	必须填写 
				flag = flag + validate('managePositionGradeCurrency_currencyType', true, 'select', 0, 0);
				flag = flag + validate('managePositionGradeCurrency_status', true, 'select', 0, 0);
				
				if(flag == 0){
					submit('managePositionGradeCurrency_form');
				}
			}
		});
		
		// 列表按钮点击事件 - Position Grade
		$('#btnList').click( function () {
			if (agreest())
				link("mgtPositionGradeAction.do?proc=list_object");
		});
		
		// 取消按钮点击事件  Position Grade Currency
		$('#btnCancelPositionGradeCurrency').click(function(){
			if(agreest())
				link('mgtPositionGradeCurrencyAction.do?proc=list_object&positionGradeId=<bean:write name="mgtPositionGradeForm" property="encodedId"/>"');
		});
		
		// 新建、编辑模式下JS动作
		if( getSubAction() != 'createObject' ){
			// 将Form设为不可编辑状态
			disableForm('positionGrade_form');
			// 更改按钮显示名
    		$('#btnEditPositionGrade').val('<bean:message bundle="public" key="button.edit" />');
    		// 更换Page Title
    		if('<bean:write name="role"/>' == 1 ){
				$('#pageTitle').html('职位等级（外部）查询');
			}else{
				$('#pageTitle').html('职位等级查询');
			}
    		$('#PositionGradeCurrencyDiv').show();
		} else if( getSubAction() == 'createObject' ){
			// 更改按钮显示名
    		$('#btnEditPositionGrade').val('保存');
    		$('.decodeModifyDate').val('');
    		// 更换Page Title
			if('<bean:write name="role"/>' == 1 ){
				$('#pageTitle').html('职位等级（外部）新增');
			}else{
				$('#pageTitle').html('职位等级新增');
			}
		}
	})(jQuery);
	
	//	点击超链接，ajax调用修改页面
	function to_positionGradeCurrencyModify( currencyId ){
		//	加载ajax调用
		loadHtmlWithRecall('#positionGradeCurrencyFormWrapper', 'mgtPositionGradeCurrencyAction.do?proc=to_objectModify_ajax&currencyId=' + currencyId, true, null );
		//	显示取消按钮
		$('#btnCancelPositionGradeCurrency').show();
		//	显示positionGradeCurrencyFormWrapper
		$('#positionGradeCurrencyFormWrapper').show();
		//	修改按钮显示名称
		$('#btnEditPositionGradeCurrency').val('编辑');
		//	设置subAction值，区分add还是modify
		$('.managePositionGradeCurrency_form input#subAction').val('viewObject');
	};
	
	// Get SubAction
	function getSubAction(){
		return $('.positionGrade_form input#subAction').val();
	};
</script>