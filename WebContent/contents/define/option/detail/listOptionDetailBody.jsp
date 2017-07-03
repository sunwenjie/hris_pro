<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.OptionDetailAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="OptionHeader-Information">
		<div class="head">
			<label id="pageTitle"></label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE_HEADER">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<logic:empty name="optionHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditOptionHeader" id="btnEditOptionHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="optionHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=OptionDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditOptionHeader" id="btnEditOptionHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=OptionDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnListOptionHeader" id="btnListOptionHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/define/option/header/form/manageOptionHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- OptionDetail Information -->
	<div class="box" id="OptionDetail-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="define" key="define.option.detail.search.title" /></label>
		</div>		
		<div class="inner">	
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE_DETAIL">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=OptionDetailAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEditOptionDetail" id="btnEditOptionDetail" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelOptionDetail" id="btnCancelOptionDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listoptionDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />					
				</kan:auth>
			</div>	
			<!-- OptionDetail - information -->	
			<div id="detailFormWrapper" style="display: none" >
				<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/define/option/detail/form/manageOptionDetailForm.jsp" flush="true"/>
			</div>			
			<!-- if exist bean optionDetailHolder -->
			<logic:notEmpty name="optionDetailHolder">													
				<html:form action="optionDetailAction.do?proc=list_object" styleClass="listoptionDetail_form">
					<input type="hidden" name="id" id="optionHeaderId" value="<bean:write name="optionHeaderForm" property="encodedId"/>"/>			
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="optionDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="optionDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="optionDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="optionDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/define/option/detail/table/listOptionDetailTable.jsp" flush="true"/>
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
		// ��ʼ���˵�
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_Option').addClass('selected');
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		// �༭��ť����¼� - Option Header			
        $('#btnEditOptionHeader').click(function(){
        	if( getSubAction() == 'viewObject'){  
        		// �༭������Enable����Form
        		enableForm('optionHeader_form');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.optionHeader_form input#subAction').val('modifyObject'); 
        		// ����Form Action
        		$('.optionHeader_form').attr('action', 'optionHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$('#btnEditOptionHeader').val('<bean:message bundle="public" key="button.save" />');
        		// �޸�Page Title
        		$('#pageTitle').html('<bean:message bundle="define" key="define.option" /> <bean:message bundle="public" key="oper.edit" />');
        	}else{
    			if( validate_manage_primary_form() == 0 ){
    				submit('optionHeader_form');
    			}
        	}
        });
    	    	
		// �༭��ť����¼� - Option Detail
		$('#btnEditOptionDetail').click(function(){
			// �ж�����ӡ��鿴�����޸�
			if($('.optionDetail_form input#subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnCancelOptionDetail').show();
				// ��ʾOption Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.optionDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditOptionDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.optionDetail_form input#subAction').val() == 'viewObject'){
				// �༭������Enable����Form
				enableForm('optionDetail_form');
				// ����SubActionΪ�༭
				$('.optionDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditOptionDetail').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.optionDetail_form').attr('action', 'optionDetailAction.do?proc=modify_object');
			}else{
				if( validate_manage_secondary_form() == 0 ){
					submit('optionDetail_form');
				}
			}
		});
		
		// �б�ť����¼�- Option Header
		$('#btnListOptionHeader').click( function () {
			if (agreest())
			link('optionHeaderAction.do?proc=list_object');
		});	
		
		// ȡ����ť����¼� - Option Detail
		$('#btnCancelOptionDetail').click(function(){
			link('optionDetailAction.do?proc=list_object&id='+$("#optionHeaderId").val());
		});	
		
		if( getSubAction() != 'createObject' ) {
			disableForm('optionHeader_form');
			$('#OptionDetail-Information').show();
			$('.optionHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="define" key="define.option" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<bean:message bundle="define" key="define.option" /> <bean:message bundle="public" key="oper.new" />');
		    $('#btnEditOptionHeader').val('<bean:message bundle="public" key="button.save" />');
		}
					
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function optionDetailModify(optionDetailId){
		// ��ʾCancel��ť
		$('#btnCancelOptionDetail').show();
		// ��ʾOption Detail Form
		$('#detailFormWrapper').show();		
		// Ajax����Option Detail�޸�ҳ��
		loadHtml('#detailFormWrapper', 'optionDetailAction.do?proc=to_objectModify_ajax&optionDetailId=' + optionDetailId, true);
		// �޸İ�ť��ʾ����
		$('#btnEditOptionDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.optionHeader_form input#subAction').val();
	};
</script>