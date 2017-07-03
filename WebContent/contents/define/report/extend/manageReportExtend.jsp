<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.define.ReportHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
	<style type="text/css">
.mydiv {
	border: 1px solid rgba(0, 0, 0, 0.3)!important ;
	border-radius: 6px!important ;
	box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3)!important ;
	overflow: auto!important ;
	height: 100%;
	background-clip: padding-box;
	border-radius: 6px;
	overflow: auto;
	float: left!important;
	margin: 0 !important;
	padding: 0 !important;
	width:230px;
	overflow:visible ;
}

.mydiv_header {
	float: none!important ;
	border:0px!important ;
	background-color: rgba(0, 0, 0, 0.3)!important ;;
	box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
	border-top-left-radius: 4px!important;
	border-top-right-radius: 4px!important;
	border-bottom-left-radius: 0px!important;
	border-bottom-right-radius: 0px!important;
	color: #5D5D5D;
	font-size: 13px;
	padding: 8px 15px!important;
	margin: 0 !important;
}

.mydiv_body {
	float: none!important ;
	border:0px!important ;
	color: #5D5D5D;
	font-size: 13px;
	height: 300px;
	min-height: 50px;
	overflow-y: auto;
	margin: 0 !important;
	padding: 15px!important;
	overflow-y:scroll;
}

.mydiv_foot {
	float: none!important ;
	border:0px!important ;
	background-color: #F7F6F6;
	border-radius: 0 0 4px 4px!important;
	font-size: 13px;
	margin-bottom: 0;
	margin: 0 !important;
	padding: 0 10px 10px!important;
	text-align: left;
	background-color: rgba(0, 0, 0, 0.3)!important ;
}
.mydiv_body  ul{
	width:100%!important;
	padding: 0 !important;
	float: left!important ;
}

.mydiv_body  li{
	width:100%!important;
	float: none!important ;
	line-height: 24px;
	margin: 0!important ;
}
.mydiv_body  ul li ul {
	width:100%!important;
	padding: 0 15px 0 0 !important;
	float: left!important ;
	margin: 0 0 12px;
}

.mydiv_body  ul li label {
	width:100%!important;
	padding: 0 15px 0 0 !important;
	float: none!important ;
	margin: 0 0 12px;
}

.mydiv_body  ul li ul li{
	padding-left:15px;!important;
	width:100%!important;
	float: none!important ;
}
.mydiv_body  ul li ul li:hover{background: rgba(0, 0, 0, 0.3); filter : alpha(opacity=20);cursor:pointer;border-radius:4px;}
.mydiv_body  ul li:hover{background: rgba(0, 0, 0, 0.3); filter : alpha(opacity=20);cursor:pointer;border-radius:4px;}


.mydiv_body  ul li ul li a {
	float: right!important ;
	margin: 0 12px 0px 0px;
}
.myli {
 	width:100%!important; 
}
.mydivblank{
width:30px;
border: 0 !important ;
}

</style>
<script type="text/javascript">
	//ul 不可编辑
	function disableUl(divClass){
	    $('form.' + divClass + ' div.mydiv_body li').each(function(){
			if($(this).attr("ondblclick") != null && $(this).attr("ondblclick").indexOf('X') < 0){
				$(this).attr("ondblclick", 'X' + $(this).attr("ondblclick"));
			}
			
			//$(this).addClass("disabled");
		});
	    
	};
	function enableUl(divClass){
		 $('form.' + divClass + ' div.mydiv_body li').each(function(){
	    	if($(this).attr("ondblclick") != null && $(this).attr("ondblclick").length > 0 && $(this).attr("ondblclick").indexOf('X') >= 0){
	    		$(this).attr("ondblclick", $(this).attr("ondblclick").substring(1));
	    	}
			
		});
	    
	};
</script>
<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="if(checkContext(1))changeTab(1,6)" class="first hover">1 - <bean:message bundle="public" key="menu.table.title.base.info" /></li> 
			<li id="tabMenu2" onClick="if(checkContext(2))changeTab(2,6)" >2 - <bean:message bundle="public" key="menu.table.title.column" /></li> 
			<li id="tabMenu3" onClick="if(checkContext(3))changeTab(3,6)" >3 - <bean:message bundle="public" key="menu.table.title.search" /></li> 
			<li id="tabMenu4" onClick="if(checkContext(4))changeTab(4,6)" >4 - <bean:message bundle="public" key="menu.table.title.sort" /></li> 
			<li id="tabMenu5" onClick="if(checkContext(5))changeTab(5,6)" >5 - <bean:message bundle="public" key="menu.table.title.group" /></li> 
			<li id="tabMenu6" onClick="if(checkContext(6))changeTab(6,6)" >6 - <bean:message bundle="public" key="menu.table.title.release" /></li>
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kanThinkingCombo" >
		
			<!-- 包含step1页面 -->
			<jsp:include page="/contents/define/report/includePage/manageReportBaseInfo.jsp" flush="true"></jsp:include>	
		</div> 
		<div id="tabContent2" class="kantab kanMultipleChoice1" style="display:none">
			<div class="top">
				<logic:empty name="reportHeaderForm" property="encodedId">
					 <input type="button" class="save" name="btnSaveStep2" id="btnSaveStep2" value='<bean:message bundle="public" key="button.save" />' />
				</logic:empty>
				<logic:notEmpty name="reportHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ReportHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnSaveStep2" id="btnSaveStep2" value='<bean:message bundle="public" key="button.save" />' />
					</kan:auth>
				</logic:notEmpty>
			</div>
			<div id="contentStep2">
				<!-- 包含step2页面 -->
				<jsp:include page="/contents/define/report/includePage/manageReportSelectColumn.jsp" flush="true"></jsp:include>	
			</div>
			<!-- ajax提交form -->
			<logic:notEmpty name="reportDetailPagedListHolder">
				<html:form action="reportDetailAction.do?proc=list_object" styleClass="listReportDetail_form">
					<fieldset>		
						<input type="hidden" name="reportHeaderId" value="<bean:write name="reportHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="reportDetailPagedListHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="reportDetailPagedListHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="reportDetailPagedListHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="reportDetailPagedListHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
			</logic:notEmpty>
		</div>
		<div id="tabContent3" class="kantab kanMultipleChoice2" style="display:none">
			<div class="top">
			    <logic:empty name="reportHeaderForm" property="encodedId">
					 <input type="button" class="save" name="btnSaveStep3" id="btnSaveStep3" value='<bean:message bundle="public" key="button.save" />' />
				</logic:empty>
				<logic:notEmpty name="reportHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ReportHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnSaveStep3" id="btnSaveStep3" value='<bean:message bundle="public" key="button.save" />' />
					</kan:auth>
				</logic:notEmpty>
			</div>
			<div id="contentStep3">
				<!-- 包含step3页面 -->
				<jsp:include page="/contents/define/report/includePage/manageReportSelectSearchCondition.jsp" flush="true"></jsp:include>
			</div>
			
			<!-- ajax提交form -->
			<logic:notEmpty name="reportSearchDetailPagedListHolder">
				<html:form action="reportSearchDetailAction.do?proc=list_object" styleClass="listReportSearchDetail_form">
					<fieldset>		
						<input type="hidden" name="reportHeaderId" value="<bean:write name="reportHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="reportSearchDetailPagedListHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="reportSearchDetailPagedListHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="reportSearchDetailPagedListHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="reportSearchDetailPagedListHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
			</logic:notEmpty>
		</div>
		<div id="tabContent4" class="kantab kanMultipleChoice3" style="display:none">
			<div class="top">
				 <logic:empty name="reportHeaderForm" property="encodedId">
					 <input type="button" class="save" name="btnSaveStep4" id="btnSaveStep4" value="<bean:message bundle="public" key="button.save" />" />
				</logic:empty>
				<logic:notEmpty name="reportHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ReportHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnSaveStep4" id="btnSaveStep4" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>
			</div>
			<div id="contentStep4">
				<!-- 包含step4：选择搜索条件form页面 -->
				<jsp:include page="/contents/define/report/includePage/manageReportSpecifySort.jsp" flush="true"></jsp:include>	
			</div>
		</div>
		<div id="tabContent5" class="kantab kanMultipleChoice4" style="display:none">
			<div class="top">
				<logic:empty name="reportHeaderForm" property="encodedId">
					<input type="button" class="save" name="btnSaveStep5" id="btnSaveStep5" value="<bean:message bundle="public" key="button.save" />" />
				</logic:empty>
				<logic:notEmpty name="reportHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ReportHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnSaveStep5" id="btnSaveStep5" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>
			</div>
			<div id="contentStep5">
				<!-- 包含step5：选择搜索条件form页面 -->
				<jsp:include page="/contents/define/report/includePage/manageReportSpecifyGroup.jsp" flush="true"></jsp:include>		
			</div>
		</div>
		<div id="tabContent6" class="kantab kanMultipleChoice5" style="display:none">
			<div class="top">
				<logic:empty name="reportHeaderForm" property="encodedId">
					<input type="button" class="save" name="btnSaveStep6" id="btnSaveStep6" value="<bean:message bundle="public" key="button.release" />" />
				</logic:empty>
				<logic:notEmpty name="reportHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ReportHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnSaveStep6" id="btnSaveStep6" value="<bean:message bundle="public" key="button.release" />" />
					</kan:auth>
				</logic:notEmpty>
			</div>
			<!-- 包含step6：确认发布form页面 -->	
			<div id="contentStep6">
				<jsp:include page="/contents/define/report/includePage/manageReportConfirmPublish.jsp" flush="true"></jsp:include>	
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>
		// step1保存按钮click事件 
		$('#btnSaveStep1').click(function(){
			if ($('.manageReportHeader_form input#subAction').val() == 'viewObject') {
				 enableForm('manageReportHeader_form');
				 enableUl("manageReportHeader_form");
				 //主表不可以修改
				 $('.manageReportHeader_tableId').attr('disabled', 'disabled');

				 $('.manageReportHeader_form input#subAction').val('modifyObject');
				 $('#btnSaveStep1').val('<bean:message bundle="public" key="button.save" />');
				 $('.manageReportHeader_form').attr('action', 'reportHeaderAction.do?proc=modify_object');
				 $('#pageTitle').html('<bean:message bundle="define" key="define.report" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
				// 验证表单
				if( validate_form_step1() == 0 ){
					submit('manageReportHeader_form');
				}
			}
		});
		
		// step2保存按钮click事件 
		$('#btnSaveStep2').click(function(){
			// 验证step2表单
			if($('.manageReportDetail_form input#subAction').val() == 'viewObject'){
				// Enable整个Form
				enableForm('manageReportDetail_form');
				// 修改按钮显示名称
				$('#btnSaveStep2').val('<bean:message bundle="public" key="button.save" />');
				// 设置SubAction为编辑
				$('.manageReportDetail_form input#subAction').val('modifyObject');
				// 更改Form Action
				$('.manageReportDetail_form').attr('action', 'reportDetailAction.do?proc=modify_object');
				// 字段相关依旧Disable
        		$('.manageReportDetail_columnId').attr('disabled','disabled');	
			}else if ($('.manageReportDetail_form input#subAction').val() == ''){
				// step2验证表单
				//if( validate_form_step2() == 0 ){
					enableForm('manageReportDetail_form');
					var callback = "$('#tabMenu2').trigger('click');";
					callback += "var tokenValue = $('.manageReportDetail_form input[id=\"com.kan.token\"]').val();"; 
					callback += "changeOtherToken(tokenValue);$('#shield img').hide(); $('#shield').hide();";
					//赋值
					$("#selectColumnsJson").val(JSON.stringify(selectColumns));
					// Ajax提交form
					$('#shield img').show();
					$('#shield').show();
					submitFormWithActionAndCallbackAndDecode( 'manageReportDetail_form', null, null, null, null, 'contentStep2', null, callback, false);
				//}
			}else{
				// step2验证表单
				//if( validate_form_step2() == 0 ){
					enableForm('manageReportDetail_form');
					var callback = "$('#tabMenu2').trigger('click');";
					callback += "var tokenValue = $('.manageReportDetail_form input[id=\"com.kan.token\"]').val();"; 
					callback += "changeOtherToken(tokenValue);";
					// Ajax提交form
					submitFormWithActionAndCallbackAndDecode( "manageReportDetail_form", null, null, null, null, "contentStep2", null, callback, false); 
				//}
			}
			
		});
		
		// step3保存按钮事件 
		$('#btnSaveStep3').click(function(){
			if($('.manageReportSearchDetail_form input#subAction').val() == 'viewObject'){
				// Enable整个Form
				enableForm('manageReportSearchDetail_form');
				// 修改按钮显示名称
				$('#btnSaveStep3').val('<bean:message bundle="public" key="button.save" />');
				// 设置SubAction为编辑
				$('.manageReportSearchDetail_form input#subAction').val('modifyObject');
				// 更改Form Action
				$('.manageReportSearchDetail_form').attr('action', 'reportSearchDetailAction.do?proc=modify_object');
				// 字段相关依旧Disable
        		$('.manageReportSearchDetail_form .manageReportSearchDetail_columnId').attr('disabled','disabled');	
        		$('.manageReportSearchDetail_form .manageReportSearchDetail_nameZH').attr('disabled','disabled');	
        		$('.manageReportSearchDetail_form .manageReportSearchDetail_nameEN').attr('disabled','disabled');	
			}else if ($('.manageReportSearchDetail_form input#subAction').val() == ''){
				// step3验证表单
				if( validate_form_step3() == 0 ){
					enableForm('manageReportSearchDetail_form');
					var callback = "$('#tabMenu3').trigger('click');";
					callback += "var tokenValue = $('.manageReportSearchDetail_form input[id=\"com.kan.token\"]').val();"; 
					callback += "changeOtherToken(tokenValue);$('#shield img').hide(); $('#shield').hide();";
					// Ajax提交form 
					$('#shield img').show();
					$('#shield').show();
					submitFormWithActionAndCallbackAndDecode( "manageReportSearchDetail_form", null, null, null, null, "contentStep3", null, callback, false);
				}
			}else{
				// step3验证表单
				if( validate_form_step3() == 0 ){
					enableForm('manageReportSearchDetail_form');
					var callback = "$('#tabMenu3').trigger('click');";
					callback += "var tokenValue = $('.manageReportSearchDetail_form input[id=\"com.kan.token\"]').val();"; 
					callback += "changeOtherToken(tokenValue)";
					// Ajax提交form
					submitFormWithActionAndCallbackAndDecode( "manageReportSearchDetail_form", null, null, null, null, "contentStep3", null, callback, false);
				}
			}
		});
		
		// step4保存按钮事件 
		$('#btnSaveStep4').click(function(){
			cleanError('manageReportDetail_form_sort_columnId');
			var flag = 0;
			
			flag = flag + validate('manageReportDetail_form_sort_columnId', true, 'select', 0, 0);	
			flag = flag + validate('manageReportDetail_form_sort_sortColumns', true, 'select', 0, 0);
			if( existSortColumn( $('.manageReportDetail_form_sort_columnId').val() ) ){
				addError('manageReportDetail_form_sort_columnId','<bean:message bundle="public" key="error.already.exists" />');
				flag = flag + 1; 
			}
			// 验证表单
			if( flag == 0 ){
				// Ajax提交form
				submitFormWithActionAndCallbackAndDecode('manageReportDetail_form_sort', null, null, null, null, 'contentStep4', null, "$('#tabMenu4').trigger('click');", false);
			}
		});
		
		// step5保存按钮事件 s
		$('#btnSaveStep5').click(function(){
			// 验证表单
			var flag = 0;
			
			flag = flag + validate('manageReportDetail_form_group_columnId', true, 'select', 0, 0);	
// 			flag = flag + validate('manageReportDetail_form_group_statisticsColumns', true, 'select', 0, 0);	
			
			if( existGroupColumn( $('.manageReportDetail_form_group_columnId').val() ) ){
				addError('manageReportDetail_form_group_columnId','<bean:message bundle="public" key="error.already.exists" />');
				flag = flag + 1; 
			}
			if( flag == 0 ){
				// Ajax提交form
				submitFormWithActionAndCallbackAndDecode('manageReportDetail_form_group', null, null, null, null, 'contentStep5', null, "$('#tabMenu5').trigger('click');", false);
			}
		});
		
		// step6保存按钮事件 
		$('#btnSaveStep6').click(function(){
			// 验证表单
			var flag = 0;
			flag = flag + validate('manageReportHeader_form_publish_moduleType', true, 'select', 0, 0);	
			if( flag == 0 ){
				// Ajax提交form
				var callback = "$('#tabMenu6').trigger('click');";
					callback += "var tokenValue = $('.manageReportSearchDetail_form input[id=\"com.kan.token\"]').val();"; 
					callback += "changeOtherToken(tokenValue);$('#shield img').hide(); $('#shield').hide();";
					// Ajax提交form 
					$('#shield img').show();
					$('#shield').show();
				submitFormWithActionAndCallbackAndDecode('manageReportHeader_form_publish', null, null, null, null, 'contentStep6', null, callback, false);
			}
		});
		
		// step2 Tab click事件 
		$('#tabMenu2').click(function(){
			if( $('.manageReportDetail_form #subAction').val() == '' ){
				// 获取主表ID
				setHeaderId('manageReportDetail_form');
				// 初始化form
//		init_form_step2();
// 				var tableId = getTableId();
// 				if( tableId  != '' && tableId != '0' ){
// 					loadHtml('.manageReportDetail_columnId', 'reportDetailAction.do?proc=list_available_options_ajax&tableId=' + tableId + '&reportHeaderId=' + getHeaderId() + '&flag=1', null);
// 				}	
			}
		});
		
		// step3 Tab click事件 
		$('#tabMenu3').click(function(){
			if( $('.manageReportSearchDetail_form #subAction').val() == '' ){
				// 获取主表ID
				setHeaderId('manageSearchReportDetail_form');
				// 初始化form
				init_form_step3();
				var tableId = getTableId();
				if( tableId  != '' && tableId  != '0' ){
					loadHtml('.manageReportSearchDetail_columnId', 'reportDetailAction.do?proc=list_available_options_ajax&tableId=' + tableId + '&reportHeaderId=' + getHeaderId() , null);
				}	
			}
		});
		
		// step4 Tab click事件 
		$('#tabMenu4').click(function(){
			// 获取主表ID
			setHeaderId('manageReportDetail_form_sort');
			loadHtml('.manageReportDetail_form_sort_columnId', 'reportDetailAction.do?proc=list_column_html&id=' + getHeaderId() +'&tableId=' + getTableId() , null);
		});
		
		// step5 Tab click事件 
		$('#tabMenu5').click(function(){
			// 获取主表ID
			setHeaderId('manageReportDetail_form_group');
			loadHtml('.manageReportDetail_form_group_columnId', 'reportDetailAction.do?proc=list_column_html&id=' + getHeaderId() +'&tableId=' + getTableId(), null);
			if( $('.manageReportDetail_form_group_columnId').val() != '0'){
				//loadHtml('#contentStep5', 'reportHeaderAction.do?proc=modify_object_add_group&reportHeaderId=' + getHeaderId() , null);
			}
		});
		
		// btnExecute
		$('#btnExecute').click(function(){
			if( getHeaderId() != '' ){
				link('reportHeaderAction.do?proc=execute_object&id='+getHeaderId()+'&subAction=approveObject');
			}else{
				alert('<bean:message bundle="public" key="popup.not.complete.report" />');
			}
		});
		
	})(jQuery);
	
	function checkContext( step ){
		if( step == '1' || step == '2' || step == '3' || step == '4' || step == '5' || step == '6' )
		{
			if( getHeaderId() == '' ){
				alert('<bean:message bundle="public" key="popup.not.input.base.information" />');
				return false;
			}
			return true;
		}
		return true;
	};
	
	// 验证step1 form
	function validate_form_step1(){
		var flag = 0;
		flag = flag + validate('manageReportHeader_tableId', true, 'select', 0, 0);	
		flag = flag + validate('manageReportHeader_searchId', true, 'select', 0, 0);	
		flag = flag + validate('manageReportHeader_nameZH', true, 'common', 100, 0);	
			
		// 选择需要分页才验证以下两项
		if($('.manageReportHeader_usePagination').is(':checked')){
			flag = flag + validate('manageReportHeader_pageSize', true, 'numeric', 2, 0);
			flag = flag + validate('manageReportHeader_loadPages', false, 'numeric', 1, 0);
		}	
		flag = flag + validate('manageReportHeader_description', false, 'common', 500, 0);
		flag = flag + validate('manageReportHeader_status', true, 'select', 0, 0);
		return flag;
	};
	
	// 验证step2 form
	function validate_form_step2(){
		var flag = 0;
		
		flag = flag + validate('manageReportDetail_columnId', true, 'select', 0, 0);	
		flag = flag + validate('manageReportDetail_nameZH', true, 'common', 100, 0);	
		flag = flag + validate('manageReportDetail_nameEN', true, 'common', 100, 0);
		
		// 如果用户使用百分比，列宽只能填2位数；如果用户使用固定值，列宽则能填3位数
		if($('.manageReportDetail_columnWidthType').val() == 1){
			flag = flag + validate('manageReportDetail_columnWidth', false, 'numeric', 2, 0, 99, 0);
		}else{
			flag = flag + validate('manageReportDetail_columnWidth', false, 'numeric', 3, 0);
		}
		
		// 如果用户使用链接，则链接Action必填；
		if($('.manageReportDetail_isLinked').val() == 1){
			flag = flag + validate('manageReportDetail_linkedAction', true, 'common', 100, 0);
		}else{
			flag = flag + validate('manageReportDetail_linkedAction', false, 'common', 100, 0);
		}
		
		flag = flag + validate('manageReportDetail_columnIndex', true, 'numeric', 2, 0);
		flag = flag + validate('manageReportDetail_accuracy', true, 'currency', 4, 0);
		flag = flag + validate('manageReportDetail_description', false, 'common', 500, 0);
		flag = flag + validate('manageReportDetail_status', true, 'select', 0, 0); 
		
		return flag;
	};
	
	// 验证step3 form
	function validate_form_step3(){
		var flag = 0;
		
		flag = flag + validate('manageReportSearchDetail_columnId', true, 'select', 0, 0);	
		flag = flag + validate('manageReportSearchDetail_nameZH', true, 'common', 100, 0);	
		flag = flag + validate('manageReportSearchDetail_nameEN', true, 'common', 100, 0);	
		
		if( $('.manageReportSearchDetail_condition').val() == '8'){
			flag = flag + validate('manageReportSearchDetail_rangeMin', true, 'common', 100, 0);	
			flag = flag + validate('manageReportSearchDetail_rangeMax', true, 'common', 100, 0);	
		}else if( $('.manageReportSearchDetail_condition').val() != '0' && $('.manageReportSearchDetail_condition').val() != '8'){
			//flag = flag + validate('manageReportSearchDetail_content', true, 'common', 100, 0);	
		}
		
		flag = flag + validate('manageReportSearchDetail_description', false, 'common', 500, 0);
		flag = flag + validate('manageReportSearchDetail_status', true, 'select', 0, 0); 
	
		return flag;
	};
	// 设置form主表ID
	function setHeaderId( formClass ){
		$('.' + formClass + ' #reportHeaderId').val( getHeaderId() );
	};
	
	// 获得主表ID
	function getHeaderId(){
		return $('#tabContent1 .manageReportHeader_form #reportHeaderId').val();
	};
	
	// 获得tableId
	function getTableId(){
		return $('#tabContent1 .manageReportHeader_form .manageReportHeader_tableId').val();
	};
	
	// 初始化
	function init_form_step2(){
		$('.manageReportDetail_columnIndex').val('0');
		$('.manageReportDetail_fontSize').val('13');
		$('.manageReportDetail_columnWidthType').val('1');
		$('.manageReportDetail_align').val('1');
		$('.manageReportDetail_sort').val('1');
		$('.manageReportDetail_display').val('1');
		$('.manageReportDetail_status').val('1');
	};
	
	// 初始化
	function init_form_step3(){
		$('.manageReportSearchDetail_columnIndex').val('0');
		$('.manageReportSearchDetail_fontSize').val('13');
		$('.manageReportSearchDetail_status').val('1');
		$('.manageReportSearchDetail_display').val('1');
	};
	
	// 重置form
	function reset_form_step3(){
		$('.manageReportSearchDetail_columnId').val('0');
		$('.manageReportSearchDetail_nameZH').val('');
		$('.manageReportSearchDetail_condition').val('1');
		if($('.manageReportSearchDetail_condition').val() == '8'){
			$('.manageReportSearchDetail_rangeMin').val('');
			$('.manageReportSearchDetail_rangeMax').val('');
		}else{
			$('.manageReportSearchDetail_content').val('');
		}
		$('.manageReportSearchDetail input#subAction').val('');
		$('.manageReportSearchDetail').attr('action', 'reportSearchDetailAction.do?proc=add_object');
	};
	
	// 移除已选定字段
	function removeColumn( reportDetailId, columnName ){
		if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){ 
			loadHtml('#contentStep2', 'reportDetailAction.do?proc=list_object&subAction=deleteObjects&selectedIds=' + reportDetailId + '&reportHeaderId=' + getHeaderId() + '&ajax=true', false, "$('#tabMenu2').trigger('click');");
		}
	};
	
	// 移除搜索条件
	function removeSearchCondition( reportSearchDetailId, columnName){
		if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){ 
			loadHtml('#contentStep3', 'reportSearchDetailAction.do?proc=list_object&subAction=deleteObjects&selectedIds=' + reportSearchDetailId + '&reportHeaderId=' + getHeaderId() + '&ajax=true', false, "$('#tabMenu3').trigger('click');");
		}
	};
	
	// 移除排序字段
	function removeSortColumn( columnId ){
		if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){
			loadHtml('#contentStep4', 'reportHeaderAction.do?proc=modify_object_remove_sort&reportHeaderId=' + getHeaderId() + '&columnId=' + columnId, false, "$('#tabMenu4').trigger('click');");
		}
	};
	
	// 移除分组字段
	function removeGroupColumn( columnId ){
		if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){
			loadHtml('#contentStep5', 'reportHeaderAction.do?proc=modify_object_remove_group&reportHeaderId=' + getHeaderId() + '&columnId=' + columnId, false, "$('#tabMenu5').trigger('click');");
		}
	};
	
	// 存在排序字段
	function existSortColumn( columnId ){
		var flag = false;
		$('.manageReportDetail_form_sort input[id^="sort_"]').each(function(){
			if( $(this).val() == columnId ){
				flag = true;
			}
		});	
		return flag;
	};
	
	// 存在分组字段
	function existGroupColumn( columnId ){
		var flag = false;
		$('.manageReportDetail_form_group input[id^="group_"]').each(function(){
			if( $(this).val() == columnId ){
				flag = true;
			}
		});	
		return flag;
	};
	
	 // Tab切换
    function change_Tab(cursel, n){ 
    	for(i = 1; i <= n; i++){ 
    		$('#tab_Menu' + i).removeClass('hover');
    		$('#tab_Content' + i).hide();
    	} 
    	
    	$('#tab_Menu' + cursel).addClass('hover');
    	$('#tab_Content' + cursel).show();
    };	
    
    // 一致Token值
    function changeOtherToken( tokenValue ){
    	$('form input[id="com.kan.token"]').val(tokenValue);
    };   
</script>
