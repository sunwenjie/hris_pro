<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%
	PagedListHolder cityHolder = (PagedListHolder)request.getAttribute("cityHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="city-information">
		<div class="head">
			<label>ʡ��</label>
		</div>
		<div class="inner">
			<html:form action="provinceAction.do?proc=modify_object" styleClass="province_form">
				<fieldset>
					<ol>
						<li>
							<label>ʡ���������ģ�<em> *</em></label> 
							<html:text property="provinceNameZH" maxlength="50" styleClass="province_provinceNameZH" /> 
						</li>
						<li>
							<label>ʡ������Ӣ�ģ�<em> *</em></label> 
							<html:text property="provinceNameEN" maxlength="50" styleClass="province_provinceNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="province_status">
								<logic:notEqual  name="mappingId" value="0">
									<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
								</logic:notEqual>
							</html:select>
						</li>						
						<li class="required"><label><em>* </em>Required field</label></li>
					</ol>
					<p>
						<input type="hidden" name="provinceId" id="provinceId" value="<bean:write name="provinceForm" property="encodedId"/>"/>
						<input type="hidden" name="editAction" id="editAction" value="viewObject" />
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="�༭" /> 
						<input type="button" class="reset" name="btnCancelProvince" id="btnCancelProvince" value="ȡ��" />
						<%= BaseAction.addToken( request ) %>
					</p>
				</fieldset>
			</html:form>
		</div>	
	</div>
		
	<!-- inner -->
	<div class="inner">
		<html:form action="cityAction.do?proc=list_object" styleClass="listcity_form">
			<input type="hidden" name="provinceId" name="provinceId" value="<bean:write name="provinceForm" property="encodedId"/>"/>			
			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="cityHolder" property="sortColumn" />" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="cityHolder" property="sortOrder" />" />
			<input type="hidden" name="page" id="page" value="<bean:write name="cityHolder" property="page" />" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="cityHolder" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />			
		</html:form>
	</div>
	<!-- Country-information -->
	<div class="box" id="search-results">
		<div class="head">
			<label><bean:write name="countryNameZH" /> &gt;&gt; <bean:write name="provinceForm" property="provinceNameZH"/> &gt;&gt; ����</label>
		</div>
		<!-- Inner -->
		<div class="inner">
			<div class="top">
				<!-- ���ص���ӳ��� -->
				<div id="cityDiv" style="display:none" >
					<html:form action="cityAction.do?proc=add_object" styleClass="city_form">
						<%= BaseAction.addToken( request ) %>
						<fieldset>
							<ol class="auto">						
								<li>
									<label>�����������ģ�<em> *</em></label>
									<html:text property="cityNameZH" maxlength="50" styleClass="city_cityNameZH" />
									<input type="hidden" name="provinceId" id="provinceId" value="<bean:write name="provinceForm" property="encodedId"/>"/>
								</li>
								<li>
									<label>��������Ӣ�ģ�<em> *</em></label> 
									<html:text property="cityNameEN" maxlength="50" styleClass="city_cityNameEN" />
								</li>
								<li>
									<label>���б��<em> *</em></label> 
									<html:text property="cityCode" maxlength="4" styleClass="city_cityCode" />
								</li>
								<li>
									<label>���б��루ISO3��<em> *</em></label> 
									<html:text property="cityISO3" maxlength="3" styleClass="city_cityISO3" />
								</li>					
								<li>
									<label>״̬  <em>*</em></label> 
									<html:select property="status" styleClass="city_status">
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
				<input type="hidden" name="saveAction" id="saveAction" value="createObject" />
				<input type="button" class="addbutton" name="btnAdd" id="btnAdd" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnCancelCity" id="btnCancelCity" value="ȡ��" style="display:none" />	
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listcity_form', 'deleteObjects', null, null, null, 'tableWrapper');" />	
		  	</div>
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/system/city/table/listCityTable.jsp" flush="true"/>
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
		
		//����FORM
		disableForm('province_form');
		
		// �༭��ť����¼�			
        $('#btnEdit').click(function(){
        	if($('#editAction').val() == 'viewObject'){   
        		enableForm('province_form');
        		$('#editAction').val('modifyObject');       		
        		$('#btnEdit').val('����');
        	}else{
        		var flag = 0;
    			
        		flag = flag + validate("province_provinceNameZH", true, "common", 50, 0);
        		flag = flag + validate("province_provinceNameEN", true, "common", 50, 0);
        		flag = flag + validate("province_status", true, "common", 0, 0);
        		
    			if(flag == 0){
    				$('.province_form').submit();
    			}
        	}
        });
		
		//��Ӱ�ť����¼�
		$('#btnAdd').click(function(){
			if( $('#cityDiv').is(":visible") ) {
				var flag = 0;
				
				flag = flag + validate("city_cityNameZH", true, "common", 50, 0);
				flag = flag + validate("city_cityNameEN", true, "common", 50, 0);
				flag = flag + validate("city_cityCode", false, "numeric", 4, 0);
				flag = flag + validate("city_cityISO3", false, "character", 3, 0);
				flag = flag + validate("city_status", true, "common", 0, 0);
				
				if(flag == 0){
					$(".city_form").submit();
				}
			}else{
				//��ʾȡ����ť��Province��ӵĽ���
				$('#btnCancelCity').show();
				$('#cityDiv').show();
				$('#btnAdd').val('����');
			}
		});
		
		//ȡ����ť����¼�
		$('#btnCancelCity').click(function(){
			$('#cityDiv').hide();
			$('#btnCancelCity').hide();
			$('#btnAdd').val('���');
		});
		
		// ʡ�ݰ�ťȡ���¼�
		$("#btnCancelProvince").click( function () {
			// ����Ǳ༭״̬��Disable Form���޸�Edit Action��ֵ���޸İ�ť����
			if($('#editAction').val() == 'modifyObject'){
				disableForm('province_form');
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