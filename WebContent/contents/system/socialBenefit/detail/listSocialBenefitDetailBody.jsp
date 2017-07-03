<%@page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.actions.system.SocialBenefitDetailAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="socialBenefitHeader - information">
		<div class="head">
			<label id="pageTitle"></label>
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
				<logic:equal value="1" name="PAGE_ACCOUNT_ID">
					<input type="button" class="editbutton" name=btnEditSocialBenefitHeader id="btnEditSocialBenefitHeader" value="<bean:message bundle="public" key="button.edit" />" />
				</logic:equal> 
				<kan:auth right="list" action="<%=SocialBenefitDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnCancelSocialBenefitHeader" id="btnCancelSocialBenefitHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/system/socialBenefit/header/form/manageSocialBenefitHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- SocialBenefitDetail - information -->
	<div class="box" id="SocialBenefitDetail-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="system" key="system.sb.detail.search.title" /></label>
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
			<logic:equal value="1" name="PAGE_ACCOUNT_ID">
				<div class="top">	
					<input type="button" class="editbutton" name="btnEditSocialBenefitDetail" id="btnEditSocialBenefitDetail" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelSocialBenefitDetail" id="btnCancelSocialBenefitDetail" value="取消" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listsocialBenefitDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</div>	
			</logic:equal>
			<div id="detailFormWrapper" style="display:none" >
				<jsp:include page="/contents/system/socialBenefit/detail/form/manageSocialBenefitDetailForm.jsp" flush="true"/>
			</div>		
			<!-- if exits bean socialBenefitDetailHolder -->
			<logic:notEmpty name="socialBenefitDetailHolder">														
				<html:form action="socialBenefitDetailAction.do?proc=list_object" styleClass="listsocialBenefitDetail_form">
					<input type="hidden" name="id" value="<bean:write name="socialBenefitHeaderForm" property="encodedId" />"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="socialBenefitDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="socialBenefitDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="socialBenefitDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="socialBenefitDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<jsp:include page="/contents/system/socialBenefit/detail/table/listSocialBenefitDetailTable.jsp" flush="true"/>
				</div>
			</logic:notEmpty>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->		
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_sb_Modules').addClass('current');
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_SB').addClass('selected');
		$('#menu_sb_sbPolicy').addClass('selected');
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		//初始化省份控件
		provinceChange('sb_provinceId', 'viewObject', $('#temp_cityId').val(), 'cityId');
		
		// 绑定省份Change事件
		$('.sb_provinceId').change( function () { 
			cleanError('cityId');
			provinceChange('sb_provinceId', 'modifyObject', 0, 'cityId');
		});
		
		 // 编辑按钮点击事件 - SocialBenefit Header
		$('#btnEditSocialBenefitHeader').click(function(){
			if( getSubAction() == 'viewObject'){  
				// Enable整个Form
        		enableForm('manageSocialBenefitHeader_form');
        		// 设置当前Form的SubAction为修改状态
        		$('.manageSocialBenefitHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.manageSocialBenefitHeader_form').attr('action', 'socialBenefitHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$('#btnEditSocialBenefitHeader').val('<bean:message bundle="public" key="button.save" />');
        		// 修改Page Title
        		$('#pageTitle').html('<bean:message bundle="system" key="system.sb.header" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
				// 通过验证提交表单	
				if( validate_manage_primary_form() == 0){
					submit('manageSocialBenefitHeader_form');
				}
			}
		});
	    
		
		// 编辑按钮点击事件 - SocicalBenefit Detail
		$('#btnEditSocialBenefitDetail').click(function(){	
			// 添加 
			if($('.manageSocialBenefitDetail_form input#subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnCancelSocialBenefitDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.manageSocialBenefitDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEditSocialBenefitDetail').val('<bean:message bundle="public" key="button.save" />');
				// 添加时继承主表初始化
				extendMainTable();
				// 触发是否补缴change事件
				if( $('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_makeup').val() == '1'){
					$('.manageSocialBenefitDetail_makeup').trigger('change');
					$('.manageSocialBenefitDetail_makeupMonth').val($('.manageSocialBenefitHeader_makeupMonth').val());
					$('.manageSocialBenefitDetail_makeupCrossYear').val($('.manageSocialBenefitHeader_makeupCrossYear').val());
				}
			}
			// 查看
			else if($('.manageSocialBenefitDetail_form input#subAction').val() == 'viewObject'){
				// 编辑操作，Enable整个Form
				enableForm('manageSocialBenefitDetail_form');
				// 设置SubAction为编辑
				$('.manageSocialBenefitDetail_form input#subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnEditSocialBenefitDetail').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.manageSocialBenefitDetail_form').attr('action', 'socialBenefitDetailAction.do?proc=modify_object');
			}
			// 修改
			else{
				if( validate_detail_form() == 0){
					submit('manageSocialBenefitDetail_form');
				}
			}
		});
		
		// 列表按钮点击事件 - SocialBenefit Header 
		$('#btnCancelSocialBenefitHeader').click( function () {
			if (agreest())
			link('socialBenefitHeaderAction.do?proc=list_object');
		});	
		
		// 取消按钮点击事件 - SocialBenefit Header 
		$('#btnCancelSocialBenefitDetail').click( function () {
			if (agreest())
			link('socialBenefitDetailAction.do?proc=list_object&id=<bean:write name="socialBenefitHeaderForm" property="encodedId"/>');
		});
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageSocialBenefitHeader_form');
			$('#SocialBenefitDetail-Information').show();
			$('.manageSocialBenefitHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="system" key="system.sb.header" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<bean:message bundle="public" key="oper.new" /> <bean:message bundle="system" key="system.sb.header" />');
		    $('#btnEditSocialBenefitHeader').val('<bean:message bundle="public" key="button.save" />');
		}
	})(jQuery);
	
	// 添加时继承主表extendMainTable
	function extendMainTable(){
		//添加时继承主表的缴纳周期
		if($('.manageSocialBenefitHeader_form .manageSocialBenefitHeader_termMonth').val() != ''){
			var termMonthArray = $('.manageSocialBenefitHeader_form .manageSocialBenefitHeader_termMonth').val().replace('{','').replace('}','').split(',');
			for( var month in termMonthArray){
				$('.manageSocialBenefitDetail_form #ck_termMonth_' + termMonthArray[month]).attr('checked',true);
			}
		}
		$('.manageSocialBenefitDetail_residency').val($('.manageSocialBenefitHeader_residency').val());
		$('.manageSocialBenefitDetail_adjustMonth').val($('.manageSocialBenefitHeader_adjustMonth').val());
		$('.manageSocialBenefitDetail_attribute').val($('.manageSocialBenefitHeader_attribute').val());
		$('.manageSocialBenefitDetail_effective').val($('.manageSocialBenefitHeader_effective').val());
		$('.manageSocialBenefitDetail_startDateLimit').val($('.manageSocialBenefitHeader_startDateLimit').val());
		$('.manageSocialBenefitDetail_endDateLimit').val($('.manageSocialBenefitHeader_endDateLimit').val());
		$('.manageSocialBenefitDetail_startRule').val($('.manageSocialBenefitHeader_startRule').val());
		$('.manageSocialBenefitDetail_startRuleRemark').val($('.manageSocialBenefitHeader_startRuleRemark').val());
		$('.manageSocialBenefitDetail_endRule').val($('.manageSocialBenefitHeader_endRule').val());
		$('.manageSocialBenefitDetail_endRuleRemark').val($('.manageSocialBenefitHeader_endRuleRemark').val());
		$('.manageSocialBenefitDetail_makeup').val($('.manageSocialBenefitHeader_makeup').val());
		$('.manageSocialBenefitDetail_accuracy').val($('.manageSocialBenefitHeader_accuracy').val());
		$('.manageSocialBenefitDetail_round').val($('.manageSocialBenefitHeader_round').val());
		
		// init detail form
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_companyPercentLow').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_companyPercentHight').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_personalPercentLow').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_personalPercentHight').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_companyFloor').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_companyCap').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_personalFloor').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_personalCap').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_companyFixAmount').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_personalFixAmount').val('0.00');
		$('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_status').val('1');
	};
	
	// to_objectModify_ajax check month
	function check_month(){
		// 选中缴纳周期
		if( $('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_termMonth').val() != ''){ 
			var termMonthArray = $('.manageSocialBenefitDetail_form .manageSocialBenefitDetail_termMonth').val().replace('{','').replace('}','').split(',');
			for( var month in termMonthArray){
				$('.manageSocialBenefitDetail_form #ck_termMonth_' + termMonthArray[month]).attr('checked',true);
			}
		}
	};
	
	// 点击超链接，ajax调用去修改页面
	function socialDetailModify( detailId ){
		// 显示Cancel按钮
		$('#btnCancelSocialBenefitDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// Ajax加载Option Detail修改页面
		loadHtmlWithRecall('#detailFormWrapper', 'socialBenefitDetailAction.do?proc=to_objectModify_ajax&detailId=' + detailId, true , 'check_month();');
		// 修改按钮显示名称
		$('#btnEditSocialBenefitDetail').val('<bean:message bundle="public" key="button.edit" />');	
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageSocialBenefitHeader_form input#subAction').val();
	};
</script>