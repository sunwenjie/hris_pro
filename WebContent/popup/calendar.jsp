<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="calendarModalId">
    <div class="modal-header" id="calendarHeader"> 
        <a class="close" data-dismiss="modal" onclick="$('#calendarModalId').addClass('hide');$('#shield').hide();">×</a>
        <label>日历</label>
    </div>
    <div class="modal-body">
	    <html:form action="calendarHeaderAction.do?proc=generateCalendar_ajax" styleClass="generateCalendar_form">
	    	<input type="hidden" name="headerId" id="headerId" value="" />
	    	<input type="hidden" name="tempDay" id="tempDay" value="" />
			<fieldset>
				<ol class="auto">
					<li>
						<html:select property="year" styleId="year" styleClass="calendar_year" style="width: 112px;">
							<html:optionsCollection property="years" value="mappingId" label="mappingValue" />
						</html:select>
						<html:select property="month" styleId="month" styleClass="calendar_month" style="width: 112px;">
							<html:optionsCollection property="months" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<input type="button" class="function" id="btnPrevMonth" onclick="pageMonth('-1');" value="上月" />
						<input type="button" class="function" id="btnToDay" onclick="todayClick();" value="今天" />
						<input type="button" class="function" id="btnNextMonth" onclick="pageMonth('1');" value="下月" />
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
    
    <div class="modal-body-appand">
    	<span id="day_detail"></span>
		<div id="calendarTableWrapper">
			<!-- 包含Tab页面 -->
			<jsp:include page="/popup/table/calendarTable.jsp"></jsp:include>
		</div>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		// 日历年月change事件
		$('.calendar_year,.calendar_month').change( function(){
			monthChange();
		});
	})(jQuery);
	
	var date = new Date();
	// 今天点击事件
	function todayClick(){
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		if( month < 10 ){
			month = '0' + month; 
		}
		$('#tempDay').val( date.getDate());
		$('.generateCalendar_form select#year').val(year);
		$('.generateCalendar_form select#month').val(month);
		$('.calendar_year').trigger('change');
	};
		
	// 弹出模态窗口
	function quickCalendarPopup( headerId ){
		$('#calendarModalId').removeClass('hide');
    	$('#shield').show();
    	$('#tempDay').val( date.getDate());
    	$('.generateCalendar_form #headerId').val(headerId);
    	$('.calendar_year').trigger('change');
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#calendarModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>