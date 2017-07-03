<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%
	PagedListHolder provinceHolder = (PagedListHolder)request.getAttribute("provinceHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="province-information">
		<div class="head">
			<label>����</label>
		</div>
		<div class="inner">
			<html:form action="countryAction.do?proc=modify_object" styleClass="country_form">
			<%= BaseAction.addToken( request ) %>
				<fieldset>
					<ol>	
						<li>
							<label>�����������ģ�<em> *</em></label> 
							<html:text property="countryNameZH" maxlength="50" styleClass="country_countryNameZH" /> 
						</li>
						<li>
							<label>��������Ӣ�ģ�<em> *</em></label> 
							<html:text property="countryNameEN" maxlength="50" styleClass="country_countryNameEN" /> 
						</li>
						<li>
							<label>���ұ��<em> *</em></label> 
							<html:text property="countryNumber" maxlength="3" styleClass="country_countryNumber" />
						</li>
						<li>
							<label>���ұ��루��д��<em> *</em></label> 
							<html:text property="countryCode" maxlength="2" styleClass="country_countryCode" />
						</li>
						<li>
							<label>���ұ��루ISO3��<em> *</em></label> 
							<html:text property="countryISO3" maxlength="3" styleClass="country_countryISO3" />
						</li>
						<li>
							<label>״̬  <em>*</em></label> 
							<html:select property="status" styleClass="country_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>					
						<li class="required"><label><em>* </em>Required field</label></li>
					</ol>
					<p>
						<input type="hidden" name="countryId" id="countryId" value="<bean:write name="countryForm" property="encodedId"/>"/>
						<input type="hidden" name="editAction" id="editAction" value="viewObject" />
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="�༭" /> 
						<input type="button" class="reset" name="btnCancel" id="btnCancel" value="ȡ��" />
					</p>
				</fieldset>
			</html:form>
		</div>	
	</div>
		
	<!-- inner -->
	<div class="inner">
		<html:form action="provinceAction.do?proc=list_object" styleClass="listprovince_form">
			<input type="hidden" name="countryId" id="countryId" value="<bean:write name="countryForm" property="encodedId"/>"/>			
			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="provinceHolder" property="sortColumn" />" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="provinceHolder" property="sortOrder" />" />
			<input type="hidden" name="page" id="page" value="<bean:write name="provinceHolder" property="page" />" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="provinceHolder" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />					
		</html:form>		
	</div>
	<!-- Country-information -->
	<div class="box" id="search-results">
		<div class="head">
			<label><bean:write name="countryForm" property="countryNameZH"/> &gt;&gt; ʡ��</label>
		</div>
		<!-- Inner -->
		<div class="inner">
			<div class="top">
				<!-- ���������ʡ�� -->
				<div id="provinceDiv" style="display:none" >
					<html:form action="provinceAction.do?proc=add_object" styleClass="province_form">
						<%= BaseAction.addToken( request ) %>
						<fieldset>
							<ol class="auto">						
								<li>
									<label>ʡ���������ģ�<em> *</em></label> 
									<html:text property="provinceNameZH" maxlength="50" styleClass="province_provinceNameZH" />
									<input type="hidden" name="countryId" id="countryId" value="<bean:write name="countryForm" property="encodedId"/>"/>
								</li>
								<li>
									<label>ʡ������Ӣ�ģ�<em> *</em></label> 
									<html:text property="provinceNameEN" maxlength="50" styleClass="province_provinceNameEN" />
								</li>					
								<li>
									<label>״̬  <em>*</em></label> 
									<html:select property="status" styleClass="province_status">
										<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
									</html:select>
								</li>
							</ol>
							<ol>
								<li class="required"><label><em>* </em>Required field</label></li>
							</ol>
							<p/>
						</fieldset>
					</html:form>
				</div>
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />"  />
				<input type="button" class="reset" name="btnCancelProvince" id="btnCancelProvince" value="ȡ��" style="display:none" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listprovince_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</div>
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/system/province/table/listProvinceTable.jsp" flush="true"/>
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
			<!-- frmList_ohrmListComponent -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->		
</div>
							
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_system_Modules').addClass('current');			
		$('#menu_system_City').addClass('selected');
		
		// ��Form��ΪDisable
		disableForm('country_form');
		// �༭��ť����¼�			
        $('#btnEdit').click(function(){
        	if($('#editAction').val() == 'viewObject'){   
        		enableForm('country_form');
        		$('#editAction').val('modifyObject');       		
        		$('#btnEdit').val('����');
        	}else{
        		var flag = 0;
        		
        		flag = flag + validate("country_countryNameZH", true, "common", 50, 0);
    			flag = flag + validate("country_countryNameEN", true, "common", 50, 0);
    			flag = flag + validate("country_countryNumber", true, "numeric", 3, 0);
    			flag = flag + validate("country_countryCode", true, "character", 2, 0);
    			flag = flag + validate("country_countryISO3", true, "character", 3, 0);
    			flag = flag + validate("country_status", true, "common", 0, 0);
    			
    			if(flag == 0){
    				$('.country_form').submit();
    			}
        	}
        });
		
		//��Ӱ�ť����¼�
		$('#btnAdd').click(function(){
			if( $('#provinceDiv').is(":visible") ) {
				var flag = 0;
				
				flag = flag + validate("province_provinceNameZH", true, "common", 50, 0);
				flag = flag + validate("province_provinceNameEN", true, "common", 50, 0);
				flag = flag + validate("province_status", true, "common", 0, 0);
				
				if(flag == 0){
					$(".province_form").submit();
				}
			}else{
				//��ʾȡ����ť��Province��ӵĽ���
				$('#btnCancelProvince').show();
				$('#provinceDiv').show();
				$('#btnAdd').val('����');
			}
		});
		
		//ȡ����ť����¼�
		$('#btnCancelProvince').click(function(){
			$('#provinceDiv').hide();
			$('#btnCancelProvince').hide();
			$('#btnAdd').val('���');
		});
		
		// ʡ�ݰ�ťȡ���¼�
		$("#btnCancel").click( function () {
			// ����Ǳ༭״̬��Disable Form���޸�Edit Action��ֵ���޸İ�ť����
			if($('#editAction').val() == 'modifyObject'){
				disableForm('country_form');
				$('#editAction').val('viewObject');
				$('#btnEdit').val('�༭');
			}
			// ����ǲ鿴״ֱ̬�ӷ���
			else{
				back();
			}
		});
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
</script>