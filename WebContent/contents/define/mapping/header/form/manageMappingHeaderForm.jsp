<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="mappingHeaderAction.do?proc=add_object&flag=${flag}" styleClass="mappingHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="id" id="mappingHeaderId" value="<bean:write name="mappingHeaderForm" property="encodedId"/>"/>
	<input type="hidden" name="mappingHeaderId" id="mappingHeaderId" value="<bean:write name="mappingHeaderForm" property="encodedId"/>"/>
	<input type="hidden" name="subAction" id="subAction" value="<bean:write name="mappingHeaderForm" property="subAction"/>"/> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label>�ͻ�ID<em> *</em></label> 
				<html:select property="clientId" styleClass="managemappingHeader_clientId">
					
				</html:select>		
			</li>
			<li>
				<logic:equal value="import" name="flag">
					<label>����ID<em> *</em></label> 
					<html:select property="importId" styleClass="managemappingHeader_importId">
						<html:optionsCollection property="importIds" value="mappingId" label="mappingValue" />
					</html:select>
				</logic:equal>			
					
				<logic:equal value="export" name="flag">
					<label>����/�б� <img src="images/tips.png" title="ѡ��һ����������б����ѡ��һ�" /><em> *</em></label> 
					<html:select property="reportId" styleClass="managemappingHeader_reportId small">
						<html:optionsCollection property="reportIds" value="mappingId" label="mappingValue" />
					</html:select>
					<html:select property="listId" styleClass="managemappingHeader_listId small">
						<html:optionsCollection property="listIds" value="mappingId" label="mappingValue" />
					</html:select>
				</logic:equal>				
			</li>
			<li>
				<label>ѡ�����ƣ����ģ�<em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="managemappingHeader_nameZH" /> 					
			</li>
			<li>
				<label>ѡ�����ƣ�Ӣ�ģ�<em> *</em></label> 
				<html:text property="nameEN" maxlength="100" styleClass="managemappingHeader_nameEN" />
			</li>
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="managemappingHeader_description"></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="managemappingHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>				
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// JS��֤
		validate_manage_primary_form = function() {
		    var flag = 0;
		    
		    flag = flag + validate('managemappingHeader_clientId', true, 'select', 0, 0);	
			 // ����ǵ���
    		if( '<bean:write name="flag" />' == 'import'){
    			flag = flag + validate('managemappingHeader_importId', true, 'select', 0, 0);	
    		}
    		// ����ǵ���
    		else if( '<bean:write name="flag" />' == 'export' ){
    			// ������б����ѡ��һ��
    			if( $('.managemappingHeader_reportId').val() == '0' && $('.managemappingHeader_listId').val() == '0' ){
    				flag = flag + validate('managemappingHeader_reportId', true, 'select', 0, 0);	
    			}else if( $('.managemappingHeader_reportId').val() != '0' && $('.managemappingHeader_listId').val() == '0' ){
    				flag = flag + validate('managemappingHeader_reportId', true, 'select', 0, 0);	
    				cleanError('managemappingHeader_listId');
    			}else if( $('.managemappingHeader_reportId').val() == '0' && $('.managemappingHeader_listId').val() != '0' ){
    				cleanError('managemappingHeader_reportId');
    				flag = flag + validate('managemappingHeader_listId', true, 'select', 0, 0);	
    			}
    		}
       		
			flag = flag + validate('managemappingHeader_nameZH', true, 'common', 100, 0);	
			flag = flag + validate('managemappingHeader_nameEN', true, 'common', 100, 0);	
			flag = flag + validate('managemappingHeader_description', false, 'common', 500, 0);	
			flag = flag + validate('managemappingHeader_status', true, 'select', 0, 0);
		    
		    return flag;
		};
		
		// ���ؿͻ�ID
		loadClientIds("managemappingHeader_clientId","<bean:write name='mappingHeaderForm' property='clientId'/>");
		
		// ѡ�񱨱�change�¼�
		$('.managemappingHeader_reportId').change( function(){
			$('.managemappingHeader_listId').val('0');
		});
		
		// ѡ���б�change�¼�
		$('.managemappingHeader_listId').change( function(){
			$('.managemappingHeader_reportId').val('0');
		});
		
		
		/*
		// ���ص���ID
		if("import" == "<bean:write name='flag'/>"){
			loadImportIds("managemappingHeader_importId","<bean:write name='mappingHeaderForm' property='importId'/>");
		}
		// ���ر���ID
		if("report" == "<bean:write name='flag'/>"){
			loadReportIds("managemappingHeader_reportId","<bean:write name='mappingHeaderForm' property='reportId'/>");
		}*/
	})(jQuery);
	
	function loadClientIds(clientIdClass,clientId){
		$.post("clientAction.do?proc=list_options_ajax_byAccountId",{"clientId":clientId},function(data){
			$("."+clientIdClass).html(data);
		},"text");
	}
	/*
	function loadClientIds(clientIdClass,clientId){
		$.post("clientAction.do?proc=list_options_ajax_byAccountId",{"clientId":clientId},function(data){
			$("."+clientIdClass).html(data);
		},"text");
	}
	
	function loadImportIds(importIdClass,importId){
		$.post("importHeaderAction.do?proc=list_options_ajax_byAccountId",{"importId":importId},function(data){
			$("."+importIdClass).html(data);
		},"text");
	}
	
	function loadReportIds(reportIdClass,reportId){
		$.post("reportHeaderAction.do?proc=list_options_ajax_byAccountId",{"reportId":reportId},function(data){
			$("."+reportIdClass).html(data);
		},"text");
	}*/
</script>
