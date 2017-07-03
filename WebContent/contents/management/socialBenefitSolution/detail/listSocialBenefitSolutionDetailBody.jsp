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
				<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/management/socialBenefitSolution/header/form/manageSocialBenefitSolutionHeaderForm.jsp" flush="true"/>
				<p>
				<div id="tableWrapper">
					<!-- Include Form JSP ����Form��Ӧ��jsp�ļ�--> 
					<jsp:include page="/contents/management/socialBenefitSolution/detail/table/listSocialBenefitSolutionDetailTable.jsp" flush="true"/>
				</div>	
				<br>
			</html:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_sb_Modules').addClass('current');			
		$('#menu_sb_Configuration').addClass('selected');
		$('#menu_sb_Solution').addClass('selected');
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		// ���İ�ť��ʾ��
		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		
		if($('.manageSocialBenefitSolutionHeader_form input#subAction').val() == 'viewObject'){
			//FORM���ɱ༭
			disableForm('manageSocialBenefitSolutionHeader_form');
			// ����Headerȡ����ť��ʾ��
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			$('#pageTitle').html('<bean:message bundle="management" key="management.sb.solution" /> <bean:message bundle="public" key="oper.view" />');
			cityIdChange();
		}
	
		// ��ʼ��ʡ�ݿؼ�
		provinceChange('location_provinceId', 'viewObject', $('.location_cityIdTemp').val(), 'cityId');
		
		// ��ʡ��Change�¼�
		$('.location_provinceId').change( function () { 
			cleanError('cityId');
			provinceChange('location_provinceId', 'modifyObject', 0, 'cityId');
		});
		
		// ����¼�
		$('#btnEdit').click(function(){
			// �ж�����ӡ��鿴�����޸� 
			if($('.manageSocialBenefitSolutionHeader_form input#subAction').val() == ''){
				// ����SubActionΪ�½�
				$('.manageSocialBenefitSolutionHeader_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageSocialBenefitSolutionHeader_form input#subAction').val() == 'viewObject'){
				// Enableɾ��Сͼ��
	   			$('img[id^=warning_img]').each(function(i){
	   				$(this).show();
	   			});
	   			$('img[id^=disable_img]').each(function(i){
	   				$(this).hide();
	   			});
				// �༭������Enable����Form
				enableForm('manageSocialBenefitSolutionHeader_form');
				// �޸İ�ť��ʾ����
				$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.manageSocialBenefitSolutionHeader_form').attr('action', 'socialBenefitSolutionHeaderAction.do?proc=modify_object');
				// ����subAction��ֵ
        		$('.manageSocialBenefitSolutionHeader_form input#subAction').val('modifyObejct');
				// �༭�ı�pageTitle��ֵ
				$('#pageTitle').html('<bean:message bundle="management" key="management.sb.solution" /> <bean:message bundle="public" key="oper.edit" />');
        		// �ֶ�����Disable
        		$('.location_provinceId').attr('disabled','disabled');	
        		$('.cityId').attr('disabled','disabled');	
        		$('.manageSocialBenefitSolutionHeader_sysHeaderId').attr('disabled','disabled');	
			}else{
				// �ڴ�����ӻ��޸ĵ���֤
				var flag = 0;
				// ��֤����
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
		
		// �������õ���¼�
		$('#moreInfo_LINK').click(function(){
			if($('#moreInfo').is(':visible')){
				$('#moreInfo').hide();
			}else{
				$('#moreInfo').show();
			}
		});
		
		// ȡ����ť����¼�
		$('#btnList').click( function () {
			if(agreest())
			link('socialBenefitSolutionHeaderAction.do?proc=list_object');
		});	
		
		var uploadObject = createUploadObject('uploadAttachment', 'all', '<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_SB %>/<bean:write name="accountId" />/<bean:write name="username" />/');
	})(jQuery);
	// ���ݳ���ID������籣�����������б�ϵͳ�� 
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
	// ѡ���籣������change()�¼�
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