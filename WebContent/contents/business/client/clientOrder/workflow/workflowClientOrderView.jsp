<%@ page import="com.kan.base.domain.MappingVO"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderHeaderVO"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final ClientOrderHeaderVO passObject = ( ClientOrderHeaderVO )request.getAttribute( "passObject" );
	final ClientOrderHeaderVO originalObject = ( ClientOrderHeaderVO )request.getAttribute( "originalObject" );
%>

<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- �޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			out.print(KANUtil.getCompareHTML(passObject.getClientNumber(),originalObject.getClientNumber(),"�ͻ����"));
			out.print(KANUtil.getCompareHTML(passObject.getOrderHeaderId(),originalObject.getOrderHeaderId(),"����ID"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameZH(),originalObject.getClientNameZH(),"�ͻ�������"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameEN(),originalObject.getClientNameEN(),"�ͻ�Ӣ����"));
			out.print(KANUtil.getCompareHTML(passObject.getClientName(),originalObject.getClientName(),"�ͻ�����"));
			out.print(KANUtil.getCompareHTML(passObject.getClientId(),originalObject.getClientId(),"�ͻ�ID"));
			out.print(KANUtil.getCompareHTML(passObject.getContractNumber(),originalObject.getContractNumber(),"��ͬ���"));
			out.print(KANUtil.getCompareHTML(passObject.getContractId(),originalObject.getContractId(),"�����ͬ"));
			out.print(KANUtil.getCompareHTML(passObject.getBusinessTypeId(),originalObject.getBusinessTypeId(),"ҵ������"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"�������ƣ����ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"�������ƣ�Ӣ�ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getInvoiceAddressId(),originalObject.getInvoiceAddressId(),"��Ʊ��ַ"));
			out.print(KANUtil.getCompareHTML(passObject.getStartDate(),originalObject.getStartDate(),"��ʼʱ��"));
			out.print(KANUtil.getCompareHTML(passObject.getEndDate(),originalObject.getEndDate(),"����ʱ��"));
			out.print(KANUtil.getCompareHTML(passObject.getSalesType(),originalObject.getSalesType(),"���۷�ʽ"));
			out.print(KANUtil.getCompareHTML(passObject.getServiceScope(),originalObject.getServiceScope(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getSettlementType(),originalObject.getSettlementType(),"���㷽ʽ"));
			out.print(KANUtil.getCompareHTML(passObject.getInvoiceType(),originalObject.getInvoiceType(),"��Ʊ��ʽ"));
			out.print(KANUtil.getCompareHTML(passObject.getEntityId(),originalObject.getEntityId(),"����ʵ��"));
			out.print(KANUtil.getCompareHTML(passObject.getTaxId(),originalObject.getTaxId(),"˰��"));
			out.print(KANUtil.getCompareHTML(passObject.getAttendanceCheckType(),originalObject.getAttendanceCheckType(),"���ڷ�ʽ"));
			out.print(KANUtil.getCompareHTML(passObject.getAttendanceGenerate(),originalObject.getAttendanceGenerate(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getPayrollDay(),originalObject.getPayrollDay(),"��н���ڣ�ÿ�£�"));
			out.print(KANUtil.getCompareHTML(passObject.getCircleStartDay(),originalObject.getCircleStartDay(),"��н���ڣ���ʼ��"));
			out.print(KANUtil.getCompareHTML(passObject.getCircleEndDay(),originalObject.getCircleEndDay(),"��н���ڣ�������"));
			out.print(KANUtil.getCompareHTML(passObject.getCalendarId(),originalObject.getCalendarId(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getShiftId(),originalObject.getShiftId(),"�Ű�"));
			out.print(KANUtil.getCompareHTML(passObject.getSalaryMonth(),originalObject.getSalaryMonth(),"�����·�"));
			out.print(KANUtil.getCompareHTML(passObject.getSbMonth(),originalObject.getSbMonth(),"�籣�������·�"));
			out.print(KANUtil.getCompareHTML(passObject.getCbMonth(),originalObject.getCbMonth(),"�̱��·�"));
			out.print(KANUtil.getCompareHTML(passObject.getProbationMonth(),originalObject.getProbationMonth(),"������"));
			out.print(KANUtil.getCompareHTML(passObject.getPersonalSBBurden(),originalObject.getPersonalSBBurden(),"��˾�е������籣������"));
			out.print(KANUtil.getCompareHTML(passObject.getLocked(),originalObject.getLocked(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"��ע","textarea"));
			out.print(KANUtil.getCompareHTML(passObject.getStatus(),originalObject.getStatus(),"״̬"));
			out.print(KANUtil.getCompareHTML(passObject.getBranch(),originalObject.getBranch(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getOwner(),originalObject.getOwner(),"������"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>�ͻ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNumber"/>' /></li>
		<li><label>����ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="orderHeaderId"/>' /></li>
		<li><label>�ͻ�������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameZH"/>' /></li>
		<li><label>�ͻ�Ӣ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameEN"/>' /></li>
		<li><label>�ͻ�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientName"/>' /></li>
		<li><label>�ͻ�ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientId"/>' /></li>
		<%-- <li><label>�Ƿ�󶨺�ͬ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="orderBindContract"/>' /></li> --%>
		<li><label>��ͬ���</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNumber"/>' /></li>
		<li><label>��ͬ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameZH"/>' /></li>
		<li><label>�����ͬ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractId"/>' /></li>
		<li><label>ҵ������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="businessTypeId"/>' /></li>
		<li><label>�������ƣ����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>�������ƣ�Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>��Ʊ��ַ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="invoiceAddressId"/>' /></li>
		<li><label>��ʼʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
		<li><label>����ʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
		<li><label>���۷�ʽ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="salesType"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="serviceScope"/>' /></li>
		<li><label>���㷽ʽ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="settlementType"/>' /></li>
		<li><label>��Ʊ��ʽ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="invoiceType"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="entityId"/>' /></li>
		<li><label>˰��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="taxId"/>' /></li>
		<li><label>���ڷ�ʽ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="attendanceCheckType"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="attendanceGenerate"/>' /></li>
		<li><label>��н���ڣ�ÿ�£�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="payrollDay"/>' /></li>
		<li><label>��н���ڣ���ʼ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="circleStartDay"/>' /></li>
		<li><label>��н���ڣ�������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="circleEndDay"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="calendarId"/>' /></li>
		<li><label>�Ű�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="shiftId"/>' /></li>
		<li><label>�����·�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="salaryMonth"/>' /></li>
		<li><label>�籣�������·�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbMonth"/>' /></li>
		<li><label>�̱��·�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="cbMonth"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="probationMonth"/>' /></li>
		<li><label>��˾�е������籣������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="personalSBBurden"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="locked"/>' /></li>
		<li><label>��ע</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="status"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="branch"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="owner"/>' /></li>
	</ol>
</div>
</logic:equal>

<%-- ���޸����͵��������� --%>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
	<div id="tabContent1"  class="kantab"  >
		<ol class="auto">
			<li><label>����ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="orderHeaderId"/>' /></li>
		</ol>
		<ol class="auto">
			<li><label>��ʼʱ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="startDate"/>' /></li>
			<li><label>����ʱ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="endDate"/>' /></li>
			<li><label>��ҵID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientId"/>' /></li>
			<li><label>�������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNumber"/>' /></li>
			<li><label>��ҵ���ƣ����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameZH"/>' /></li>
			<li><label>��ҵ���ƣ�Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameEN"/>' /></li>
		</ol>	
		<ol class="auto">
			<li><label>�����ͬID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractId"/>' /></li>
		</ol>
		<ol class="auto">	
			<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeEntityId"/>' /></li>
			<li><label>ҵ������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBusinessTypeId"/>' /></li>
			<li><label>���㷽ʽ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeSettlementType"/>' /></li>
			<li><label>��Ʊ��ʽ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeInvoiceType"/>' /></li>
			<li><label>���۷�ʽ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeSalesType"/>' /></li>
			<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeServiceScope"/>' /></li>
			<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeLocked"/>' /></li>
			<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeStatus"/>' /></li>
		</ol>
		<ol class="auto">	
			<li><label>��ע</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
			<li>
				<label>��ͬ��������</label>
				<%
					final List< MappingVO > tipTypes = KANUtil.getMappings( request.getLocale(), "business.client.order.header.noticeExpire" );
				
				    String[] noticeExpireArray = null;
				    
					if( passObject != null )
					{
					   noticeExpireArray = KANUtil.jasonArrayToStringArray( passObject.getNoticeExpire() );
					}
					if( tipTypes != null && tipTypes.size() > 0 )
					{
					   out.print( "<span>" );
					   for( MappingVO mappingVO : tipTypes )
					   {
					      String checked = "";
					      if( noticeExpireArray != null && noticeExpireArray.length > 0 )
					      {
					         for( String s : noticeExpireArray )
					         {
					            if( mappingVO.getMappingId().equals( s ) )
					            {
					               checked = "checked=\"checked\"";
					               break;
					            }
					         }
					      }  
					      out.print( "<input type=\"checkbox\" disabled " + checked + "value=\"" + mappingVO.getMappingId() + "\" />" + mappingVO.getMappingValue() + " " );
					   }
					   out.print( "</span>" );
					}
				%>
			</li>
			<li>
				<label>�����ڵ�������</label>
				<%
				
				    String[] noticeProbationExpireArray = null;
				    
					if( passObject != null )
					{
					   noticeProbationExpireArray = KANUtil.jasonArrayToStringArray( passObject.getNoticeProbationExpire() );
					}
					if( tipTypes != null && tipTypes.size() > 0 )
					{
					   out.print( "<span>" );
					   for( MappingVO mappingVO : tipTypes )
					   {
					      String checked = "";
					      if( noticeProbationExpireArray != null && noticeProbationExpireArray.length > 0 )
					      {
					         for( String s : noticeProbationExpireArray )
					         {
					            if( mappingVO.getMappingId().equals( s ) )
					            {
					               checked = "checked=\"checked\"";
					               break;
					            }
					         }
					      }  
					      out.print( "<input type=\"checkbox\" disabled " + checked + "value=\"" + mappingVO.getMappingId() + "\" />" + mappingVO.getMappingValue() + " " );
					   }
					   out.print( "</span>" );
					}
				%>
			</li>
		</ol>
		<ol class="auto">	
			<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBranch"/>' /></li>
			<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeOwner"/>' /></li>
		</ol>
		<br/>
		<div id="special_info_tab">
		
		</div>
	</div>
</logic:notEqual>

<script type="text/javascript">
	// ����Tab
	loadHtml('#special_info_tab', 'clientOrderHeaderAction.do?proc=list_special_info_html&comeFrom=workflow&orderHeaderId=<bean:write name="originalObject" property="encodedId"/>', true );
</script>

