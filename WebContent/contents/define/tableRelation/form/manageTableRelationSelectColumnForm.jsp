<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="messageWrapper">
	<logic:present name="MESSAGE">
		<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
			<bean:write name="MESSAGE" />
   			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
		</div>
	</logic:present>
</div>
<%= BaseAction.addToken( request ) %>
			<ol class="auto">
				<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
				<input type="hidden" id="tableRelationId" name="tableRelationId" class="tableRelationId" value="<bean:write name="tableRelationForm" property="tableRelationId" />" /> 
			</ol>
			
			<ol class="auto">
			
				<li>
					<label>主模块字典名称<em> *</em></label>
					<logic:equal name="tableRelationForm" property="subAction" value="createObject">
					<html:select name="tableRelationForm" property="masterTableId"  styleId="masterTableId" styleClass="manageTableRelationDetail_masterTableId" >
						<html:optionsCollection name="tablelist" value="mappingId" label="mappingValue"/>
					</html:select>
					</logic:equal>
					
					<logic:notEqual name="tableRelationForm" property="subAction" value="createObject" >
					<label><bean:write name="tableRelationForm" property="decodeMasterTableName"/></label>
					<input type="hidden" id="masterTableId" name="masterTableId" class="manageTableRelationDetail_masterTableId" value="<bean:write name="tableRelationForm" property="masterTableId" />" />
					</logic:notEqual>
					 
				</li>
				<li>
					<label>子模块字典名称<em> *</em></label>
					 <html:select name="tableRelationForm" property="slaveTableId" styleId="slaveTableId" styleClass="manageTableRelationDetail_slaveTableId">
					 	<html:option value="">请选择</html:option>
						<html:optionsCollection name="tablelist" value="mappingId" label="mappingValue"/>
					</html:select>
				</li>
			</ol>
				<ol class="auto"  >
					<li>
              			<label>关联类型 </label>
	               		<html:select name="tableRelationForm" property="joinType" styleClass="manageTableRelationDetail_joinType">
	               			<html:option value="left join">left join</html:option>
	               			<html:option value="inner join">inner join</html:option>
	               			<html:option value="right join">right join</html:option>
						</html:select>
           			</li>
           			<li>
          				<label>主表join on 字段 </label>
           				<html:select name="tableRelationForm" property="masterColumn" styleId="masterColumn" styleClass="manageTableRelationDetail_masterColumn">

						</html:select>
           			</li>
           			<li>
          				<label>子表join on 字段</label>
           				<html:select name="tableRelationForm" property="slaveColumn" styleId="slaveColumn" styleClass="manageTableRelationDetail_slaveColumn">

						</html:select>
						
           			</li>
         		 </ol>
				 
       	 	
									
<script type="text/javascript">
	(function($) {
		
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>
		
		
		loadHtml('#masterColumn', 'tableRelationAction.do?proc=list_object_options_ajax&tableId=' + $('#masterTableId').val()+'&columnId='+encodeURI('<bean:write name="tableRelationForm" property="slaveColumn" />'), true );
		loadHtml('#slaveColumn', 'tableRelationAction.do?proc=list_object_options_ajax&tableId=' + $('#slaveTableId').val()+'&columnId='+encodeURI('<bean:write name="tableRelationForm" property="slaveColumn" />'), true );

	
		// 选择字段change事件
		$('#masterTableId').change(function(){
			loadHtml('#masterColumn', 'tableRelationAction.do?proc=list_object_options_ajax&tableId=' + $('#masterTableId').val(), true );
		});
		
		// 选择字段change事件
		$('#slaveTableId').change(function(){
			loadHtml('#slaveColumn', 'tableRelationAction.do?proc=list_object_options_ajax&tableId=' + $('#slaveTableId').val(), true );
		});
		
		
	})(jQuery);
</script>
