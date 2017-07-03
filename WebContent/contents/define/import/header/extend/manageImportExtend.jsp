<%@page import="com.kan.base.web.renders.security.PositionRender"%>
<%@page import="com.kan.base.web.renders.security.GroupRender"%>
<%@page import="com.kan.base.web.renders.define.ImportRender"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.domain.define.ImportHeaderVO"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.define.ImportHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="tab"> 
	<div id="popupWrapper">
		<jsp:include page="/popup/quickChoose.jsp"></jsp:include>
	</div>	
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="if(checkContext(1))changeTab(1,3)" class="first hover">1 - <bean:message bundle="public" key="menu.table.title.base.info" /></li> 
			<li id="tabMenu2" onClick="if(checkContext(2))changeTab(2,3)" >2 - <bean:message bundle="public" key="menu.table.title.column" /></li> 
			<li id="tabMenu3" onClick="if(checkContext(3))changeTab(3,3)" >3 - <bean:message bundle="public" key="menu.table.title.release" /></li>
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kanThinkingCombo" >
			<!-- 包含step1页面 -->
			<jsp:include page="/contents/define/import/header/form/manageImportHeaderForm.jsp" flush="true"></jsp:include>	
		</div> 
		<div id="tabContent2" class="kantab kanMultipleChoice1" style="display:none">
			<div class="top">
				<logic:empty name="importHeaderForm" property="encodedId">
					 <input type="button" class="save" name="btnSaveStep2" id="btnSaveStep2" value="<bean:message bundle="public" key="button.save" />" />
				</logic:empty>
				<logic:notEmpty name="importHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ImportHeaderAction.accessAction%>">
						 <input type="button" class="save" name="btnSaveStep2" id="btnSaveStep2" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>
				<input type="button" class="save" name="btnQuickChoose" id="btnQuickChoose" value="<bean:message bundle="public" key="button.quick.choose" />" />
			</div>
			<div id="contentStep2">
				<logic:notEmpty name="importDetailHolder">
					<!-- 包含step2：选择字段form页面 -->
					<jsp:include page="/contents/define/import/header/includePage/manageImportSelectColumn.jsp" flush="true"></jsp:include>	
				</logic:notEmpty>
			</div>
			<!-- ajax提交form -->
			<logic:notEmpty name="importDetailPagedListHolder">
				<html:form action="importDetailAction.do?proc=list_object" styleClass="listImportDetail_form">
					<fieldset>		
						<input type="hidden" name="importHeaderId" value="<bean:write name="importHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="importDetailPagedListHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="importDetailPagedListHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="importDetailPagedListHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="importDetailPagedListHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
			</logic:notEmpty>
		</div>
		<div id="tabContent3" class="kantab kanMultipleChoice1" style="display:none">
			<div class="top">
				<logic:empty name="importHeaderForm" property="encodedId">
					 <input type="button" class="save" name="btnSaveStep3" id="btnSaveStep3" value="<bean:message bundle="public" key="button.release" />" />
				</logic:empty>
				<logic:notEmpty name="importHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ImportHeaderAction.accessAction%>">
						 <input type="button" class="save" name="btnSaveStep3" id="btnSaveStep3" value="<bean:message bundle="public" key="button.release" />" />
					</kan:auth>
				</logic:notEmpty>
			</div>
			<div id="contentStep3" >
				<!-- 包含step3：确认发布form页面 -->
				<jsp:include page="/contents/define/import/header/includePage/manageImportConfirmPublish.jsp" flush="true"></jsp:include>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>
		
		// step2保存按钮click事件 
		$('#btnSaveStep2').click(function(){
			// 验证step2表单
			if($('.manageImportDetail_form input#subAction').val() == 'viewObject'){
				// Enable整个Form
				enableForm('manageImportDetail_form');
				// 修改按钮显示名称
				$('#btnSaveStep2').val('<bean:message bundle="public" key="button.save" />');
				// 设置SubAction为编辑
				$('.manageImportDetail_form input#subAction').val('modifyObject');
				// 更改Form Action
				$('.manageImportDetail_form').attr('action', 'importDetailAction.do?proc=modify_object');
				// 字段相关依旧Disable
        		$('.manageImportDetail_columnId').attr('disabled','disabled');	
			}else if ($('.manageImportDetail_form input#subAction').val() == ''){
				// step2验证表单
				if( validate_form_step2() == 0 ){
					enableForm('manageImportDetail_form');
					// Ajax提交form
					submitFormWithActionAndCallbackAndDecode( 'manageImportDetail_form', null, null, null, null, 'contentStep2', null, "", false);
				}
			}else{
				// step2验证表单
				if( validate_form_step2() == 0 ){
					enableForm('manageImportDetail_form');
					// Ajax提交form
					submitFormWithActionAndCallbackAndDecode( "manageImportDetail_form", null, null, null, null, "contentStep2", null, "", false); 
				}
			}
			
		});
		
		$("#btnQuickChoose").click(function(){
			popupQuickChoose();
		});
		
		// step3保存按钮事件 
		$('#btnSaveStep3').click(function(){
			// Ajax提交form
			submitFormWithActionAndCallbackAndDecode('manageImportHeader_form_publish', null, null, null, null, 'contentStep3', null, null, false);
		});
		
		// step2 Tab click事件 
		$('#tabMenu2').click(function(){
			// 获取主表ID
			setHeaderId('manageImportDetail_form');
			// 初始化form
			init_form_step2();
			var tableId = getTableId();
			if( tableId  != '' && tableId != '0' ){
				// TODO
				//loadHtml('.manageImportDetail_columnId', 'importDetailAction.do?proc=list_available_options_ajax&tableId=' + tableId + '&importHeaderId=' + getHeaderId() + '&flag=1', null);
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
		flag = flag + validate('manageImportHeader_tableId', true, 'select', 0, 0);	
		flag = flag + validate('manageImportHeader_searchId', true, 'select', 0, 0);	
		flag = flag + validate('manageImportHeader_nameZH', true, 'common', 100, 0);	
		flag = flag + validate('manageImportHeader_nameEN', true, 'common', 100, 0);	
		// 选择需要分页才验证以下两项
		if($('.manageImportHeader_usePagination').is(':checked')){
			flag = flag + validate('manageImportHeader_pageSize', true, 'numeric', 2, 0);
			flag = flag + validate('manageImportHeader_loadPages', false, 'numeric', 1, 0);
		}	
		flag = flag + validate('manageImportHeader_description', false, 'common', 500, 0);
		flag = flag + validate('manageImportHeader_status', true, 'select', 0, 0);
		return flag;
	};
	
	// 验证step2 form
	function validate_form_step2(){
		var flag = 0;
		
		flag = flag + validate('manageImportDetail_columnId', true, 'select', 0, 0);	
		flag = flag + validate('manageImportDetail_nameZH', true, 'common', 100, 0);	
		flag = flag + validate('manageImportDetail_columnWidth', false, 'numeric', 2, 0);
		flag = flag + validate('manageImportDetail_columnIndex', true, 'numeric', 2, 0);
		flag = flag + validate('manageImportDetail_tempValue', false, 'common', 100, 0);
		flag = flag + validate('manageImportDetail_description', false, 'common', 500, 0);
		flag = flag + validate('manageImportDetail_status', true, 'select', 0, 0); 
		
		return flag;
	};
	
	// 设置form主表ID
	function setHeaderId( formClass ){
		$('.' + formClass + ' #importHeaderId').val( getHeaderId() );
	};
	
	// 获得主表ID
	function getHeaderId(){
		return $('#tabContent1 .manageImportHeader_form #importHeaderId').val();
	};
	
	// 获得tableId
	function getTableId(){
		return $('#tabContent1 .manageImportHeader_form .manageImportHeader_tableId').val();
	};
	
	// 初始化
	function init_form_step2(){
		$('.manageImportDetail_columnIndex').val('0');
		$('.manageImportDetail_fontSize').val('13');
		$('.manageImportDetail_columnWidthType').val('1');
		$('.manageImportDetail_align').val('1');
		$('.manageImportDetail_sort').val('1');
		$('.manageImportDetail_display').val('1');
		$('.manageImportDetail_status').val('1');
	};
	
	
	// 移除已选定字段
	function removeColumn( importDetailId, columnName ){
		if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){ 
			loadHtml('#contentStep2', 'importDetailAction.do?proc=list_object&subAction=deleteObjects&selectedIds=' + importDetailId + '&importHeaderId=' + getHeaderId() + '&ajax=true', false);
		}
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
    
</script>
