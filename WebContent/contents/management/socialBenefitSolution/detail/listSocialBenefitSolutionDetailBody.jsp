<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="socialBenefitHeader" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.sb.solution" /></label>
		</div>
		<div class="inner">
			<html:form action="socialBenefitSolutionHeaderAction.do?proc=add_object" styleClass="manageSocialBenefitSolutionHeader_form">
				<!-- Include Form JSP 包含Form对应的jsp文件 -->  
				<jsp:include page="/contents/management/socialBenefitSolution/header/form/manageSocialBenefitSolutionHeaderForm.jsp" flush="true"/>
				<p>
				<div id="tableWrapper">
					<!-- Include Form JSP 包含Form对应的jsp文件--> 
					<jsp:include page="/contents/management/socialBenefitSolution/detail/table/listSocialBenefitSolutionDetailTable.jsp" flush="true"/>
				</div>	
				<br>
			</html:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_sb_Modules').addClass('current');			
		$('#menu_sb_Configuration').addClass('selected');
		$('#menu_sb_Solution').addClass('selected');
		
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		// 更改按钮显示名
		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		
		if($('.manageSocialBenefitSolutionHeader_form input#subAction').val() == 'viewObject'){
			//FORM不可编辑
			disableForm('manageSocialBenefitSolutionHeader_form');
			// 更改Header取消按钮显示名
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			$('#pageTitle').html('<bean:message bundle="management" key="management.sb.solution" /> <bean:message bundle="public" key="oper.view" />');
			cityIdChange();
		}
	
		// 初始化省份控件
		provinceChange('location_provinceId', 'viewObject', $('.location_cityIdTemp').val(), 'cityId');
		
		// 绑定省份Change事件
		$('.location_provinceId').change( function () { 
			cleanError('cityId');
			provinceChange('location_provinceId', 'modifyObject', 0, 'cityId');
		});
		
		// 点击事件
		$('#btnEdit').click(function(){
			// 判断是添加、查看还是修改 
			if($('.manageSocialBenefitSolutionHeader_form input#subAction').val() == ''){
				// 设置SubAction为新建
				$('.manageSocialBenefitSolutionHeader_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageSocialBenefitSolutionHeader_form input#subAction').val() == 'viewObject'){
				// Enable删除小图标
	   			$('img[id^=warning_img]').each(function(i){
	   				$(this).show();
	   			});
	   			$('img[id^=disable_img]').each(function(i){
	   				$(this).hide();
	   			});
				// 编辑操作，Enable整个Form
				enableForm('manageSocialBenefitSolutionHeader_form');
				// 修改按钮显示名称
				$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.manageSocialBenefitSolutionHeader_form').attr('action', 'socialBenefitSolutionHeaderAction.do?proc=modify_object');
				// 更改subAction的值
        		$('.manageSocialBenefitSolutionHeader_form input#subAction').val('modifyObejct');
				// 编辑改变pageTitle的值
				$('#pageTitle').html('<bean:message bundle="management" key="management.sb.solution" /> <bean:message bundle="public" key="oper.edit" />');
        		// 字段依旧Disable
        		$('.location_provinceId').attr('disabled','disabled');	
        		$('.cityId').attr('disabled','disabled');	
        		$('.manageSocialBenefitSolutionHeader_sysHeaderId').attr('disabled','disabled');	
			}else{
				// 在此做添加或修改的验证
				var flag = 0;
				// 验证主表
				flag = flag + validate("location_provinceId", true, "select", 0, 0);
				if( $('.location_provinceId').val() != '' || $('.location_provinceId').val() == '0' ){
					flag = flag + validate("cityId", true, "select", 0, 0);
        		}
				flag = flag + validate("manageSocialBenefitSolutionHeader_sysHeaderId", true, "select", 0, 0);
				flag = flag + validate("manageSocialBenefitSolutionHeader_nameZH", true, "common", 100, 0);
				flag = flag + validate("manageSocialBenefitSolutionHeader_startDateLimit", true, "select", 0, 0);
				flag = flag + validate("manageSocialBenefitSolutionHeader_endDateLimit", true, "select", 0, 0);
				flag = flag + validate("manageSocialBenefitSolutionHeader_sbType", true, "select", 0, 0);
				flag = flag + validate("manageSocialBenefitSolutionHeader_description", false, "common", 500, 0);
				flag = flag + validate("manageSocialBenefitSolutionHeader_status", true, "select", 0, 0);
				var flag_detail = check_checkBox();
				if(flag_detail == 0){
					alert('<bean:message bundle="public" key="popup.must.select.a.item" />');
					flag = flag + 1;
				}
				if(flag == 0){
					submit('manageSocialBenefitSolutionHeader_form');
				}
			}
			
		});
		
		// 更多设置点击事件
		$('#moreInfo_LINK').click(function(){
			if($('#moreInfo').is(':visible')){
				$('#moreInfo').hide();
			}else{
				$('#moreInfo').show();
			}
		});
		
		// 取消按钮点击事件
		$('#btnList').click( function () {
			if(agreest())
			link('socialBenefitSolutionHeaderAction.do?proc=list_object');
		});	
		
		var uploadObject = createUploadObject('uploadAttachment', 'all', '<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_SB %>/<bean:write name="accountId" />/<bean:write name="username" />/');
	})(jQuery);
	// 根据城市ID，获得社保公积金类型列表（系统） 
	function cityIdChange() { 
		cleanError('cityId');
		var cityId = $('.cityId').val();
		if(cityId == undefined){
			cityId = $('.location_cityIdTemp').val();
		}
		var disabled = false;
		var headerId = '';
		if($('.manageSocialBenefitSolutionHeader_form input#subAction').val() == 'viewObject'){
			disabled = true;
			headerId = '<bean:write name="socialBenefitSolutionHeaderForm" property="sysHeaderId"/>';
		}
	
		var callback = "check_city_sb();";
		if( !disabled){
			callback += "sysHeaderIdChange();";
		}
		
		loadHtmlWithRecall('#getSocialBenefitHeaderByCityId', 'socialBenefitHeaderAction.do?proc=list_object_html&cityId=' + cityId +'&headerId=' + headerId , disabled, callback);
	};
	// 选择社保公积金change()事件
	function sysHeaderIdChange(){
		var sysHeaderId = $('.manageSocialBenefitSolutionHeader_sysHeaderId option:selected').val();
		var callJS = "$('.manageSocialBenefitSolutionHeader_startDateLimit').val($('#startDate').val());";
		callJS += "$('.manageSocialBenefitSolutionHeader_endDateLimit').val($('#endDate').val());";
		callJS += "$('.manageSocialBenefitSolutionHeader_effective').val($('#effective').val());";
		callJS += "$('.manageSocialBenefitSolutionHeader_attribute').val($('#attribute').val());";
		callJS += "$('.manageSocialBenefitSolutionHeader_startRule').val($('#startRule').val());";
		callJS += "$('.manageSocialBenefitSolutionHeader_startRuleRemark').val($('#startRuleRemark').val());";
		callJS += "$('.manageSocialBenefitSolutionHeader_endRule').val($('#endRule').val());";
		callJS += "$('.manageSocialBenefitSolutionHeader_endRuleRemark').val($('#endRuleRemark').val());";
		loadHtmlWithRecall('#tableWrapper','socialBenefitSolutionDetailAction.do?proc=to_objectNew_ajax&sysHeaderId=' + sysHeaderId + '&ajax=true&date=' + new Date(), false, callJS);
	};
	
	function check_city_sb(){
		if($('.cityId').val() != '0' && $('.manageSocialBenefitSolutionHeader_sysHeaderId').find('option').length == '1'){
			addError('cityId', '<bean:message bundle="public" key="error.city.no.sb" />');
		}
	};
	
	function check_checkBox(){
		var flag = 0;
		$('#resultTable tr td:first-child input[id^="indexArray_"]').each(function(){
			if($(this).attr("checked")=='checked'){
				flag ++;
			}
		});
		return flag;
	};
</script>