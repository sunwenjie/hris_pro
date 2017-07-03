<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="mappingDetailAction.do?proc=add_object&flag=${flag}" styleClass="mappingDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="id" id="mappingHeaderId" value="<bean:write name="mappingHeaderForm" property="encodedId"/>">
	<input type="hidden" name="mappingDetailId" id="mappingDetailId" value="<bean:write name="mappingDetailForm" property="encodedId"/>">		
	<input type="hidden" name="subAction" id="subAction" value="<bean:write name="mappingDetailForm" property="subAction"/>"/>		
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<logic:notEmpty name="useJavaObject">
					<label>������<em> *</em></label>
					<html:text property="propertyName" maxlength="100" styleClass="manageMappingDetail_propertyName" /> 
				</logic:notEmpty>
				<logic:empty name="useJavaObject">
					<label>�ֶ�<em> *</em></label>
					<select name="columnId" class="manageMappingDetail_columnId">
						<logic:notEmpty name="columns">
							<logic:iterate id="columnMappingVO" name="columns">
								<option value="<bean:write name="columnMappingVO" property="mappingId" />">
									<bean:write name="columnMappingVO" property="mappingValue"/>
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</logic:empty>
			</li>
		</ol>
		<ol class="auto" id="column_numeric" style="display: none;">
			<li>
				<label>���� <img src="images/tips.png" title="����С��λ��" /></label>
				<html:select property="accuracy" styleClass="manageMappingDetail_accuracy">
					<html:optionsCollection property="accuracys" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
			<li>
				<label>ȡ�� <img src="images/tips.png" title="С��λ��������ʽ����ȡ - ֱ��Ĩȥ����ȡС��λ�����Ͻ�λ - ������λ��������ȡ" /></label>
				<html:select property="round" styleClass="manageMappingDetail_round">
					<html:optionsCollection property="rounds" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
		</ol>
		<ol class="auto" id="column_date" style="display: none;">
			<li>
				<label>���ڸ�ʽ</label>
				<html:select property="datetimeFormat" styleClass="manageMappingDetail_datetimeFormat">
					<html:optionsCollection property="datetimeFormats" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>�ֶ����ƣ����ģ�<em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageMappingDetail_nameZH" />
			</li>							
			<li>
				<label>�ֶ����ƣ�Ӣ�ģ�<em> *</em></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageMappingDetail_nameEN" />
			</li>	
		</ol>
		<ol class="auto">							
			<li>
				<label>�ֶο��</label> 
				<html:text property="columnWidth" maxlength="100" styleClass="manageMappingDetail_columnWidth" />
			</li>							
			<li>
				<label>�ֶ�˳��<em> *</em></label> 
				<html:text property="columnIndex" maxlength="100" styleClass="manageMappingDetail_columnIndex" />
			</li>							
			<li>
				<label>�����С</label> 
				<html:select property="fontSize" styleClass="manageMappingDetail_fontSize" >
					<html:optionsCollection property="fontSizes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
			<li>
				<label>�Ƿ�ת��  </label> 
				<html:select property="isDecoded" styleClass="manageMappingDetail_isDecoded">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 								
			</li>						
			<li>
				<label>�ֶζ���</label> 
				<html:select property="align" styleClass="manageMappingDetail_align" >
					<html:optionsCollection property="aligns" value="mappingId" label="mappingValue" />
				</html:select>
			</li>							
			<li>
				<label>״̬  <em>*</em></label> 
				<html:select property="status" styleClass="manageMappingDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 	
				<html:textarea property="description" cols="3" styleClass="manageMappingDetail_description"></html:textarea>
			</li>								
		</ol>
	</fieldset>
</html:form>
<script type="text/javascript">
	(function($) {
		
		// Detail JS��֤
		validate_manage_secondary_form = function() {
		    var flag = 0;
		    
		    <logic:notEmpty name="useJavaObject">
		   		flag = flag + validate('manageMappingDetail_propertyName', true, 'common', 100, 0);
		    </logic:notEmpty>
		   
		    <logic:empty name="useJavaObject">
		  		flag = flag + validate('manageMappingDetail_columnId', true, 'select', 0, 0);
		    </logic:empty>
		   
			flag = flag + validate('manageMappingDetail_nameZH', true, 'common', 100, 0);
			flag = flag + validate('manageMappingDetail_nameEN', true, 'common', 100, 0);
			// ��֤��ֵ
			flag = flag + validate('manageMappingDetail_columnWidth', false, 'numeric', 3, 0);
			flag = flag + validate('manageMappingDetail_columnIndex', true, 'numeric', 3, 0);
			
			flag = flag + validate('manageMappingDetail_status', true, 'select', 0, 0);
			flag = flag + validate('manageMappingDetail_description', false, 'common', 500, 0);
		    
		    return flag;
		};
		
		// ��ֵĬ��ƥ���ֶ�����
		$(".manageMappingDetail_columnId").change(function(){
			$.post("mappingDetailAction.do?proc=get_object_json&flag=<bean:write name='flag'/>",{"columnId":$(this).val()},function(data){
				$(".manageMappingDetail_nameZH").val(data.nameZH);
				$(".manageMappingDetail_nameEN").val(data.nameEN);
			},"json");
		});
	})(jQuery);
</script>