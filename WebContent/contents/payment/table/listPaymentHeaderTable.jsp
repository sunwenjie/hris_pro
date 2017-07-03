<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kan.base.util.KANUtil" %>
<%@ page import="com.kan.base.page.PagedListHolder" %>
<%@ page import="com.kan.base.domain.MappingVO" %>
<%@ page import="com.kan.hro.domain.biz.payment.PaymentDTO" %>
<%@ page import="com.kan.hro.domain.biz.payment.PaymentHeaderVO" %>
<%@ page import="com.kan.hro.domain.biz.payment.PaymentDetailVO" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder paymentDTOHolder = (PagedListHolder) request.getAttribute("paymentDTOHolder");
	
	// 是否扁平模式
	boolean flat = false;
	
	// 是否存在子对象
   	boolean subObject = false;
	
	if( paymentDTOHolder != null && paymentDTOHolder.getSource() != null && paymentDTOHolder.getSource().size() > 0 )
	{
	   flat = true;
	   
	   for( Object paymentDTOObject : paymentDTOHolder.getSource() )
	   {
	      final PaymentDTO paymentDTO = ( PaymentDTO ) paymentDTOObject;
	      
	      if( paymentDTO != null && paymentDTO.getPaymentDetailVOs() != null && paymentDTO.getPaymentDetailVOs().size() > 0 )
	      {
	         for( PaymentDetailVO paymentDetailVO : paymentDTO.getPaymentDetailVOs() )
	         {
	            if( KANUtil.filterEmpty( paymentDetailVO.getItemType() ) != null && paymentDetailVO.getItemType().equals( "7" ) )
	            {
	               subObject = true;
	               
	               break;
	            }
	         }
	      }
	   }
	}
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("paymentHeaderId")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'paymentHeaderId', '<%=paymentDTOHolder.getNextSortOrder("paymentHeaderId")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.header.id" />&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("employeeId")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'employeeId', '<%=paymentDTOHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("employeeNameZH")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'employeeNameZH', '<%=paymentDTOHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("employeeNameEN")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'employeeNameEN', '<%=paymentDTOHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("certificateNumber")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'certificateNumber', '<%=paymentDTOHolder.getNextSortOrder("certificateNumber")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.certificate.number" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("bankId")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'bankId', '<%=paymentDTOHolder.getNextSortOrder("bankId")%>', 'tableWrapper');">
					<bean:message bundle="payment" key="payment.header.bank.name" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("bankAccount")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'bankAccount', '<%=paymentDTOHolder.getNextSortOrder("bankAccount")%>', 'tableWrapper');">
				<bean:message bundle="payment" key="payment.header.bank.account" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("monthly")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'monthly', '<%=paymentDTOHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">
				<bean:message bundle="payment" key="payment.header.salary.month" />&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</th>
			<logic:equal name="role" value="1">
				<th class="header <%=paymentDTOHolder.getCurrentSortClass("clientId")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
					<a onclick="submitForm('searchPaymentHeader_form', null, null, 'clientId', '<%=paymentDTOHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				</th>
			</logic:equal>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("orderId")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'orderId', '<%=paymentDTOHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=paymentDTOHolder.getCurrentSortClass("orderContractId")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'orderContractId', '<%=paymentDTOHolder.getNextSortOrder("orderContractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<logic:notEmpty name="paymentDTOHolder" property="additionalObject">
				<logic:iterate id="item" name="paymentDTOHolder" property="additionalObject">
					<logic:equal name="item" property="mappingTemp" value="7">
						<th class="header-nosort center" <%=( flat && subObject ) ? "colspan=\"2\"" : null %> >
							<%
								if( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ){
							%>
								<bean:write name="item" property="mappingValue"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%
								} else { 
							%>
								<bean:write name="item" property="mappingStatus"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%
								}
							%>
						</th>
					</logic:equal>
					<logic:notEqual name="item" property="mappingTemp" value="7">
						<th class="header-nosort center"  <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
							<%
								if( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ){
							%>
								<bean:write name="item" property="mappingValue"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%
								} else { 
							%>
								<bean:write name="item" property="mappingStatus"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%
								}
							%>
						</th>
					</logic:notEqual>
				</logic:iterate>
			</logic:notEmpty>
			<th class="header-nosort" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<bean:message bundle="payment" key="payment.header.before.tax.salary" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th class="header-nosort" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<bean:message bundle="payment" key="payment.header.tax.agent.amount.personal" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th class="header-nosort" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<bean:message bundle="payment" key="payment.header.tax.amount.personal" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th class="header-nosort" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<bean:message bundle="payment" key="payment.header.tax.annual.bonus" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th class="header-nosort" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<bean:message bundle="payment" key="payment.header.after.tax.annual.bonus" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th class="header-nosort" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<bean:message bundle="payment" key="payment.header.after.tax.salary" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th class="header  <%=paymentDTOHolder.getCurrentSortClass("status")%>" <%=( flat && subObject ) ? "rowspan=\"2\"" : null %> >
				<a onclick="submitForm('searchPaymentHeader_form', null, null, 'status', '<%=paymentDTOHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</th>
		</tr>	
		<tr>
			<logic:notEmpty name="paymentDTOHolder" property="additionalObject">
				<logic:iterate id="item" name="paymentDTOHolder" property="additionalObject">
					<logic:equal name="item" property="mappingTemp" value="7">
						<th class="header-nosort">
							<bean:message bundle="payment" key="payment.header.company" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</th>
						<th class="header-nosort">
							<bean:message bundle="payment" key="payment.header.personal" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</th>
					</logic:equal>
				</logic:iterate>
			</logic:notEmpty>
		</tr>
	</thead>
	<logic:notEqual name="paymentDTOHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="paymentDTO" name="paymentDTOHolder" property="source" indexId="number">
				<%
					final PaymentDTO tempPaymentDTO = (PaymentDTO) pageContext.getAttribute("paymentDTO");
				%>
				<bean:define id="paymentHeaderVO" name="paymentDTO" property="paymentHeaderVO" ></bean:define>
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="paymentHeaderVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="paymentHeaderVO" property="encodedId"/>" />
					</td>
					<td class="left"><bean:write name="paymentHeaderVO" property="paymentHeaderId"/></td>
					<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="paymentHeaderVO" property="encodedEmployeeId"/>');"><bean:write name="paymentHeaderVO" property="employeeId" /></a></td>
					<td class="left"><bean:write name="paymentHeaderVO" property="employeeNameZH"/></td>
					<td class="left"><bean:write name="paymentHeaderVO" property="employeeNameEN"/></td>
					<td class="left"><bean:write name="paymentHeaderVO" property="certificateNumber"/></td>
					<td class="left"><bean:write name="paymentHeaderVO" property="bankName"/></td>
					<td class="left"><bean:write name="paymentHeaderVO" property="bankAccount"/></td>
					<td class="left"><bean:write name="paymentHeaderVO" property="monthly"/></td>
					<logic:equal name="role" value="1">
						<td class="left"><a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="paymentHeaderVO" property="encodedClientId"/>');"><bean:write name="paymentHeaderVO" property="clientId" /></a></td>
					</logic:equal>
					<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="paymentHeaderVO" property="encodedOrderId"/>');"><bean:write name="paymentHeaderVO" property="orderId"/></a></td>
					<td class="left"><a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="paymentHeaderVO" property="encodedContractId"/>');"><bean:write name="paymentHeaderVO" property="contractId"/></a></td>
					<logic:iterate id="item" name="paymentDTOHolder" property="additionalObject">	
						<%
							final MappingVO mappingVO = (MappingVO) pageContext.getAttribute("item");
						%>
						<logic:equal name="item" property="mappingTemp" value="7">
							<td class="right">
								<%= tempPaymentDTO.getAmountCompany( mappingVO.getMappingId() ) %>
							</td>
							<td class="right">
								<%= tempPaymentDTO.getAmountPersonal( mappingVO.getMappingId() ) %>
							</td>
						</logic:equal>
						<logic:notEqual name="item" property="mappingTemp" value="7">
							<td class="right">
								<%= tempPaymentDTO.getAmountPersonal( mappingVO.getMappingId() ) %>
							</td>
						</logic:notEqual>
					</logic:iterate>
					<td class="right"><bean:write name="paymentHeaderVO" property="beforeTaxSalary"/></td>
					<td class="right"><bean:write name="paymentHeaderVO" property="taxAgentAmountPersonal"/></td>
					<td class="right"><bean:write name="paymentHeaderVO" property="taxAmountPersonal"/></td>
					<td class="right"><bean:write name="paymentHeaderVO" property="annualBonusTax"/></td>
					<td class="right"><bean:write name="paymentHeaderVO" property="afterTaxAnnualBonus"/></td>
					<td class="right"><bean:write name="paymentHeaderVO" property="afterTaxSalary"/></td>
					<td class="left"><bean:write name="paymentHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="paymentDTOHolder">
		<tfoot>
			<tr class="total">
				<td colspan="100" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="paymentDTOHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="paymentDTOHolder" property="indexStart" /> - <bean:write name="paymentDTOHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchPaymentHeader_form', null, '<bean:write name="paymentDTOHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchPaymentHeader_form', null, '<bean:write name="paymentDTOHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchPaymentHeader_form', null, '<bean:write name="paymentDTOHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchPaymentHeader_form', null, '<bean:write name="paymentDTOHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="paymentDTOHolder" property="realPage" />/<bean:write name="paymentDTOHolder" property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="paymentDTOHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();

		var dataObj=eval("(" + '<%=paymentDTOHolder.getSummarization() %>' + ")");
		
		// 页面Json值赋值
		$('#countEmployeeIds').html(dataObj.countEmployeeIds);
		$('#countContractIds').html(dataObj.countContractIds);
		$('#costAmountCompany').html(dataObj.costAmountCompany);
		$('#billAmountPersonal').html(dataObj.billAmountPersonal);
		$('#costAmountPersonal').html(dataObj.costAmountPersonal);
		$('#taxAmountPersonal').html(dataObj.taxAmountPersonal);
	})(jQuery);

	function forward() {
		var value = Number($('#forwardPage').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('searchPaymentHeader_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>
