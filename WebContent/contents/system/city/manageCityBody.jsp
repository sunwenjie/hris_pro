<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	PagedListHolder provinceHolder = (PagedListHolder)request.getAttribute("provinceHolder");
%>

<div id="content">
	<div id="systemPosition" class="box toggableForm">
		<div class="head">
			<label>城市</label>
		</div>		
		<div class="inner">
			<html:form action="cityAction.do?proc=modify_object" styleClass="city_form">
				<fieldset>
					<ol>
						<li>
							<label>城市名（中文）<em> *</em></label> 
							<html:text property="cityNameZH" maxlength="20" styleClass="city_cityNameZH" /> 
						</li>
						<li>
							<label>城市名（英文）<em> *</em></label> 
							<html:text property="cityNameEN" maxlength="20" styleClass="city_cityNameEN" /> 
						</li>
						<li>
							<label>城市编号<em> *</em></label> 
							<html:text property="cityCode" maxlength="20" styleClass="city_cityCode" /> 						
						</li>
						<li>
							<label>城市编码（ISO3）<em> *</em></label> 
							<html:text property="cityISO3" maxlength="20" styleClass="city_cityISO3" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="city_status">
								<logic:notEqual  name="mappingId" value="0">
									<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
								</logic:notEqual>
							</html:select>
						</li>						
						<li class="required"><label><em>* </em>Required field</label></li>
					</ol>
					<p>
						<input type="hidden" id="cityId" name="cityId" value="<bean:write name="cityForm" property="encodedId" />" />
						<input type="hidden" id="provinceId" name="provinceId" value="<bean:write name="provinceVO" property="encodedId" />" />
						<input type="hidden" name="editAction" id="editAction" value="viewObject" />
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="编辑" /> 
						<input type="button" class="reset" name="btnCancel" id="btnCancel" value="取消" />
					</p>
				</fieldset>
			</html:form>
		</div>	
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_system_Modules').addClass('current');			
		$('#menu_system_City').addClass('selected');
		
		//隐藏FORM
		disableForm('city_form');
		// 编辑按钮点击事件			
        $('#btnEdit').click(function(){
        	if($('#editAction').val() == 'viewObject'){   
        		enableForm('city_form');
        		$('#editAction').val('modifyObject');       		
        		$('#btnEdit').val('保存');
        	}else{
        		var flag = 0;
    			
        		flag = flag + validate("city_cityNameZH", true, "common", 50, 0);
				flag = flag + validate("city_cityNameEN", true, "common", 50, 0);
				flag = flag + validate("city_cityCode", false, "numeric", 4, 0);
				flag = flag + validate("city_cityISO3", false, "character", 3, 0);
				flag = flag + validate("city_status", true, "common", 0, 0);
				
    			if(flag == 0){
    				$('.city_form').submit();
    			}
        	}
        });
		
		$("#btnCancel").click( function () {
			if(agreest())
			back();
		});	
	})(jQuery);
</script>