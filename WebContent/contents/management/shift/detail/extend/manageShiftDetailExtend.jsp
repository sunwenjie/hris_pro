<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" class="first hover"><bean:message bundle="management" key="management.shift.detail.search.title" /></li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kanThinkingCombo" >
			<div class="top">
				<a id="isShowAllTime" name="isShowAllTime" class="inquiry"><bean:message bundle="management" key="management.shift.show.all.period" /></a>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/management/shift/detail/table/listShiftDetailTableByWeekOrDay.jsp" flush="true"/>
			</div>	
		</div> 
	</div>
</div>

<script type="text/javascript">
	(function($){
		// ��ʼ�����ش�Ҫʱ��
		initHidePeriod();
		
		// checkboxȫѡ�¼�
		$('#resultTable input[id^="chk_all_"][type="checkbox"]').click(function(){
			var flag = $(this).is(':checked');
			$('#resultTable tbody tr td input[id^="chk_' + $(this).val() + '_"][type="checkbox"]').not(':hidden').each(function(){
				$(this).attr('checked',flag);
			});
		});
		
		// �޸�״̬�£�ѡ��checkbox
		$('.manageShift_form input[id^="shiftPeriod_"]').each(function(){ 
			if($(this).val() != ''){
				var index = $(this).attr('id').replace('shiftPeriod_','');
				var array = $(this).val().replace('{','').replace('}','').split(',');
				for( var val in array){
					$('#chk_' +index+ '_' +array[val] +'').attr('checked',true); 
				}
			}
		});
		
		// ��ʾ������ʱ���Click
		$('#isShowAllTime').click(function(){
			if($(this).hasClass('inquiry')){
				showAllPeriod();
				$(this).html('<bean:message bundle="management" key="management.shift.hide.part.period" />');  
				$(this).removeClass('inquiry');
			}else{
				initHidePeriod();
				$(this).html('<bean:message bundle="management" key="management.shift.show.all.period" />');
				$(this).addClass('inquiry');
			}
		});
	})(jQuery);

	function initHidePeriod(){
		var minPeriod = 19;
		var maxPeriod = 36;
		if( $('#minPeriod').val() != '' && $('#minPeriod').val() != undefined){
			minPeriod = $('#minPeriod').val();
		}
		if( $('#maxPeriod').val() != '' && $('#maxPeriod').val() != undefined){
			maxPeriod = $('#maxPeriod').val();
		}
		$('#resultTable tbody tr td input[type="checkbox"]').each(function(){
			if( parseInt($(this).val()) < parseInt(minPeriod) || parseInt($(this).val()) > parseInt(maxPeriod) ){
				$(this).hide();
				$(this).next().hide();
			}
		});
	};
	
	function showAllPeriod(){
		$('#resultTable tbody tr td input[type="checkbox"]').each(function(){
			if($(this).is(':hidden')){
				$(this).show();
				$(this).next().show();
			}
		});
	}; 
</script>