<%@page import="com.kan.base.web.renders.define.ImportRender"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
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
<div id="detailFormWrapper" >
	<html:form action="importDetailAction.do?proc=add_object" styleClass="manageImportDetail_form">
		<%= BaseAction.addToken( request ) %>
		<input type="hidden" name="subAction" class="subAction" id="subAction" value="<bean:write name="importDetailForm" property="subAction"/>"/>
   		<input type="hidden" name="importHeaderId" value="<bean:write name="importHeaderForm" property="encodedId"/>"/>
   		<input type="hidden" name="importDetailId" value="<bean:write name="importDetailForm" property="encodedId"/>"/>
		<fieldset>
			<ol class="auto">
				<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="define" key="define.column" /><em> *</em></label>
					<html:select property="columnId" styleClass="manageImportDetail_columnId">
						<html:optionsCollection property="columns" value="mappingId" label="mappingValue"/>
					</html:select>
				</li>
			</ol>
			<div id="manageImportDetail_moreinfo">
				<ol class="auto" id="decimalFormatOL"  style="display: none;">
					<li>
              			 <label><bean:message bundle="public" key="public.accuracy" /> <img src="images/tips.png" title="<bean:message bundle="public" key="public.accuracy.tips" />" /></label>
	               		 <html:select property="accuracy" styleClass="manageImportDetail_accuracy">
							 <html:optionsCollection property="accuracys" value="mappingId" label="mappingValue"/>
						 </html:select>
           			</li>
           			<li>
          				 <label><bean:message bundle="public" key="public.round" /> <img src="images/tips.png" title="<bean:message bundle="public" key="public.round.tips" />" /></label>
           				 <html:select property="round" styleClass="manageImportDetail_accuracy">
						 	 <html:optionsCollection property="rounds" value="mappingId" label="mappingValue"/>
						 </html:select>
           			 </li>
         		 </ol>
				 <ol id="datetimeFormatOL" class="auto" style="display: none;">
      				 <li>
	      				 <label><bean:message bundle="public" key="public.date.format" /></label>
	           			 <html:select property="datetimeFormat" styleClass="manageImportDetail_accuracy">
							 <html:optionsCollection property="datetimeFormats" value="mappingId" label="mappingValue"/>
					 	 </html:select>
      				 </li>
       	 		</ol>
				<ol class="auto">		
			         <li>
			         	 <label><bean:message bundle="define" key="define.manager.detail.name.cn" /><em> *</em></label>
			             <html:text property="nameZH" maxlength="100" styleClass="manageImportDetail_nameZH" />
			         </li>
			         <li>
				         <label><bean:message bundle="define" key="define.manager.detail.name.en" /></label>
						 <html:text property="nameEN" maxlength="100" styleClass="manageImportDetail_nameEN" />
				     </li>
				     <li>
				     	 <label><bean:message bundle="define" key="define.column.is.primary.key" /></label>
				     	 <input type="checkbox" id="chk_isPrimaryKey">
				     	 <input type="hidden" name="isPrimaryKey" class="manageImportDetail_isPrimaryKey" value="0">
				     </li>
				     <li>
				     	 <label><bean:message bundle="define" key="define.column.is.foreign.key" /></label>
				     	 <input type="checkbox" id="chk_isForeignKey">
				     	 <input type="hidden" name="isForeignKey" class="manageImportDetail_isForeignKey" value="0">
				     </li>
				     
			         <li>
			         	 <label><bean:message bundle="define" key="define.list.detail.column.width" /></label> 
			             <html:text property="columnWidth" maxlength="100" styleClass="manageImportDetail_columnWidth" />
			         </li>
			         <li>
				         <label><bean:message bundle="define" key="define.column.column.index" /></label> 
				         <html:text property="columnIndex" maxlength="100" styleClass="manageImportDetail_columnIndex" />
			         </li>
			         <li>
				         <label><bean:message bundle="define" key="define.list.detail.font.size" /></label> 
				         <html:select property="fontSize" styleClass="manageImportDetail_fontSize">
							<html:optionsCollection property="fontSizes" value="mappingId" label="mappingValue"/>
						 </html:select>
			         </li>
			         <li>
			         	<label><bean:message bundle="define" key="define.import.detail.speical.value" /><img src="images/tips.png" title="<bean:message bundle="define" key="define.import.detail.speical.value.tips" />" /></label> 
			            <html:select property="specialField" styleClass="manageImportSpecialField_tempValue">
							<html:optionsCollection property="specialFields" value="mappingId" label="mappingValue"/>
						</html:select>
			         </li>
			         <li>
			         	<label><bean:message bundle="define" key="define.import.detail.demo.value" /><img src="images/tips.png" title="<bean:message bundle="define" key="define.import.detail.demo.value.tips" />" /></label> 
			            <html:text property="tempValue" maxlength="100" styleClass="manageImportDetail_tempValue" />
			         </li>
			         <li>
			         	<label><bean:message bundle="define" key="define.list.detail.is.decode" /></label>
			         	<html:select property="isDecoded" styleClass="manageImportDetail_isDecoded">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue"/>
						</html:select>
			         </li>
			         <li>
			         	<label><bean:message bundle="define" key="define.column.is.required" /></label>
			         	<html:select property="isIgnoreDefaultValidate" styleClass="manageImportDetail_isIgnoreDefaultValidate">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue"/>
						</html:select>
			         </li>
		         </ol>
		         <ol class="auto">
			         <li>
			         	<label><bean:message bundle="define" key="define.list.detail.align" /></label>
				        <html:select property="align" styleClass="manageImportDetail_align">
							<html:optionsCollection property="aligns" value="mappingId" label="mappingValue"/>
						</html:select>
			         </li>
			         <li>
			         	<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
			          	<html:select property="status" styleClass="manageImportDetail_status">
							<html:optionsCollection property="statuses" value="mappingId" label="mappingValue"/>
						</html:select>
			         </li>
			         <li>
				         <label><bean:message bundle="public" key="public.description" /></label> 
				         <html:textarea property="description" styleClass="manageImportDetail_description"></html:textarea>
			         </li>
		         </ol>
			</div>
		</fieldset>	
	</html:form>
</div>	
														
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
		
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>
		<logic:notEmpty name="columnVO">
			<logic:equal name="columnVO" property="valueType" value="1">$('#decimalFormatOL').show();</logic:equal>
			<logic:equal name="columnVO" property="valueType" value="3">$('#datetimeFormatOL').show();</logic:equal>
		</logic:notEmpty>

		if('<bean:write name="importDetailForm" property="isPrimaryKey"/>'=="1"){
			$("#chk_isPrimaryKey").attr("checked","checked");
			$(".manageImportDetail_isPrimaryKey").val("1");
		}
		if('<bean:write name="importDetailForm" property="isForeignKey"/>'=="1"){
			$("#chk_isForeignKey").attr("checked","checked");
			$(".manageImportDetail_isForeignKey").val("1");
		}
		
		// Ñ¡Ôñ×Ö¶ÎchangeÊÂ¼þ
		$('.manageImportDetail_columnId').change(function(){
			$.ajax({
				url:"importDetailAction.do?proc=columnId_change_ajax&columnId=" + $(this).val(),
				dataType:"json",
				success:function(data){
					$('#datetimeFormatOL').hide();
					$('#decimalFormatOL').hide();
					if( data.success == 'true' ){
						$('.manageImportDetail_nameZH').val(data.nameZH);
						$('.manageImportDetail_nameEN').val(data.nameEN);
						if(data.valueType=='1'){
							$('#decimalFormatOL').show();
						}else if( data.valueType == '3' ){
							$('#datetimeFormatOL').show();
						}
					}else{
						$('.manageImportDetail_nameZH').val('');
						$('.manageImportDetail_nameEN').val('');
					}
				}
			});
		});
		
		$('.manageImportDetail_isLinked').change(function(){
			isLinkedChange();
		});
		
		$("#chk_isPrimaryKey").click(function(){
			if($("#chk_isPrimaryKey").attr("checked") == "checked"){
				$(".manageImportDetail_isPrimaryKey").val("1");
			}else{
				$(".manageImportDetail_isPrimaryKey").val("0");
			}
		});
		$("#chk_isForeignKey").click(function(){
			if($("#chk_isForeignKey").attr("checked") == "checked"){
				$(".manageImportDetail_isForeignKey").val("1");
			}else{
				$(".manageImportDetail_isForeignKey").val("0");
			}
		});
	})(jQuery);
</script>
