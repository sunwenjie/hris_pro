<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="business" key="business.ot.monthly" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="business" key="business.ot.begin.day" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="business" key="business.ot.end.day" />
			</th>
			<th style="width: 30%" class="header-nosort">
				<bean:message bundle="business" key="business.ot.total.hours" />
			</th>
			<th style="width: 30%" class="header-nosort">
				<bean:message bundle="business" key="business.ot.limit.month" />
			</th>
		</tr>
	</thead>
	<tbody>
		<logic:present name="otHeaderVOs">
			<logic:iterate id="otHeaderVO" name="otHeaderVOs" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td class="left"><bean:write name="otHeaderVO" property="monthly"/></td>
					<td class="left"><bean:write name="otHeaderVO" property="circleStartDay"/></td>
					<td class="left"><bean:write name="otHeaderVO" property="circleEndDay"/></td>
					<td class="right"><bean:write name="otHeaderVO" property="totalOTHours"/></td>
					<logic:equal name="otHeaderVO" property="otLimitByMonth" value="${otHeaderVO.totalOTHours}">
						<td class="right agreelight"><bean:write name="otHeaderVO" property="otLimitByMonth"/></td>
					</logic:equal>
					<logic:notEqual name="otHeaderVO" property="otLimitByMonth" value="${otHeaderVO.totalOTHours}">
						<td class="right agreelight"><bean:write name="otHeaderVO" property="otLimitByMonth"/></td>
					</logic:notEqual>
				</tr>
			</logic:iterate>
		</logic:present>
	</tbody>
	<tfoot>
		<tr class="total">
			<td colspan="5" class="left"> 
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º<logic:empty name="otHeaderVOs">0</logic:empty><logic:notEmpty name="otHeaderVOs"><bean:write name="countOTHeaderVO" /></logic:notEmpty></label>
				<label>&nbsp;&nbsp;</label>
				<label>&nbsp;&nbsp;</label>
				<label>&nbsp;</label>
				<label>&nbsp;</label>
				<label>&nbsp;</label>
				<label>&nbsp;&nbsp;</label>&nbsp;
			</td>					
		</tr>
	</tfoot>	
</table>

<script type="text/javascript">
		(function($) {
			
			$('#resultTable tbody tr').each(function(i){
				var t1 = $('#resultTable tbody tr:eq(' + i +') td').eq('3').html();
				var t2 = $('#resultTable tbody tr:eq(' + i +') td').eq('4').html();
				if( t1 == t2 ){
					$('#resultTable tbody tr:eq(' + i +') td').eq('4').removeClass('agreelight');
					$('#resultTable tbody tr:eq(' + i +') td').eq('4').addClass('highlight');
				}
			});
			
		})(jQuery);
</script>