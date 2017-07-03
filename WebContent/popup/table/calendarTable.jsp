<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<style type="text/css">
	.calendar_d { width: 536px; position: relative; padding: 5px;  }
	.calendar_d h3 { text-align: center; position: relative; }
	.calendar_d h3 span { width:74px; height:54px; border:1px solid #fff; line-height: 54px; float: left; background: #217FC4; font-size: 20px;  color: #fff; }
	.calendar_d .day { width: 536px; }
	.calendar_d .day div { width:72px; height:52px; border:2px solid #fff; float: left; background: #ededed; text-align:center; position: relative; cursor:pointer; }
	.calendar_d .day div:hover { border:2px solid #B54A4A; }
	.calendar_d .day .normal { background:#f8f8f8; color:#ccc; }
	.calendar_d .day .normal span { color: #B7B7B7 }
	.calendar_d .day .today { border:2px solid #d30408; }
	.calendar_d .day .clickday { border:2px solid #B54A4A; }
</style>

<div class="calendar_d">
	<h3>
		<span>一</span>
		<span>二</span>
		<span>三</span>
		<span>四</span>
		<span>五</span>
		<span>六</span>
		<span>日</span>
	</h3>
	<div class="day">
		<logic:notEmpty name="calendarDTOs">
		<logic:iterate id="calendarDTO" name="calendarDTOs" indexId="number">
			<logic:iterate id="calendarDetailVO" name="calendarDTO" property="calendarDetailVOs" indexId="size">
				<div <logic:notEqual name="calendarDetailVO" property="monthType" value="2">class="normal"</logic:notEqual>>
					<input type="hidden" class="calendar_day" value="<bean:write name="calendarDetailVO" property="detailId" />" />
					<span <logic:equal name="calendarDetailVO" property="dayType" value="1">class="highlineFont"</logic:equal><logic:notEqual name="calendarDetailVO" property="dayType" value="1">class="highlight"</logic:notEqual>>
						<bean:write name="calendarDetailVO" property="detailId" />
					</span>
					<br/>
					<span <logic:equal name="calendarDetailVO" property="dayType" value="1">class="highlineFont"</logic:equal><logic:notEqual name="calendarDetailVO" property="dayType" value="1">class="highlight"</logic:notEqual>>
						<bean:write name="calendarDetailVO" property="nameZH" />
					</span>
				</div>	
			</logic:iterate>
		</logic:iterate>
		</logic:notEmpty>
	</div>
</div>
			
<script type="text/javascript">
	(function($) {
		// 当日期
		var currDate = new Date();
		var currYear = currDate.getFullYear();
		var currMonth = currDate.getMonth() + 1;
		var currDay = currDate.getDate();
		if( currMonth < 10 ){
			currMonth = '0' + currMonth; 
		}
		
		if( currYear == $('#year').val() && currMonth == $('#month').val() ){
			day_click( currDay );
		}
		
		// 日历点击事件
		$('.calendar_d .day div').click( function(){
			var clickDay = $(this).find('input').val();  
			if( !$(this).hasClass('today')){
				$('.calendar_d .day div').removeClass('clickday');
				$(this).addClass('clickday');
			}else{
				$('.calendar_d .day div').removeClass('clickday');
			}
			
			$('#tempDay').val( clickDay );
			if( $(this).hasClass('normal')){
				pageMonth( clickDay > 15 ? -1 : 1 );
			}else{
				day_click( clickDay );
			}
		});
		
		var maxDay = parseInt($('.calendar_d .day div:not(.normal)').last().find('input').val());
		var tempDay = parseInt($('#tempDay').val());
		if( tempDay > maxDay ){
			$('.calendar_d .day div:not(.normal)').first().addClass('clickday');
			day_click('1');
		} else {
			$('.calendar_d .day div:not(.normal)').each( function(){
				if( currYear == $('#year').val() && currMonth == $('#month').val() && $(this).find('input').val() == currDay ){
					$(this).addClass('today');
				}else{
					if( $(this).find('input').val() == $('#tempDay').val() ){
						$(this).addClass('clickday');
						day_click($(this).find('input').val());
					}
				}
			});
		}
	})(jQuery);
	
	// 月份改变
	function monthChange(){
		submitForm('generateCalendar_form', null, null, null, null, 'calendarTableWrapper');
	};
	
	// 日历点击
	function day_click( day ){
		$.ajax({
			url:'calendarHeaderAction.do?proc=get_date_detail&year=' + $('#year').val() + '&month=' + $('#month').val() + '&day=' + day,
			success: function(html){
				if(html != ''){
					$('#day_detail').html(html);
				}
			}		
		});
	};
	
	// 翻动月份
	function pageMonth(p,d){
		var year = $('.generateCalendar_form select#year').val();
		var month = $('.generateCalendar_form select#month').val();
		var targetMonth = parseInt(month) + parseInt(p);
		if( targetMonth == 13 ){
			$('.generateCalendar_form select#year').val(parseInt(year) + parseInt(1));
			$('.generateCalendar_form select#month').val('01');
		}else if(targetMonth == 0 ){
			$('.generateCalendar_form select#year').val(parseInt(year) + parseInt(-1));
			$('.generateCalendar_form select#month').val('12');
		}else{
			if( targetMonth < 10){
				targetMonth = "0" + targetMonth; 
			}
			$('.generateCalendar_form select#month').val(targetMonth);
		}
		monthChange();
		if(d != null){
			alert('to invoke ' + d);
			day_click(d);
		}
	};
</script>