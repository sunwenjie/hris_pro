<%@page import="com.kan.base.web.renders.util.DashboardRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<style>
#content {
	color: #5d5d5d;
	font-size: 13px;
	font-family: 黑体, Calibri;
}

#baseInfo,#message {
	width: 400px;
	height: 353px;
	float: left;
	border:1px;
	border-color:#000;
	background-color:#FFF;
}

#dataView,#clientContract,#orders,#contractService,#attendance,#sb,#cb,#employeeChange,#payment {
	width: 420px;
	height: 390px;
	float: left;
	margin-left: 20px;
}

#settlement {
	width: 690px;
	height: 390px;
	float: left;
	margin-left: 20px;
}
</style>
<div id="content">
	<div id="options" class="box ">
		<div class="head">
			<label>Dashboard</label>
		</div>
		<div class="inner">

			<%= DashboardRender.generateDashboard(request)%>

		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {

		// ajax for baseInfo
		if ('<bean:write name="settingVO" property="baseInfo"/>' == 1) {
			$("#baseInfo").show();
			$.post('dashboardAction.do?proc=get_baseInfo_ajax', '', function(
					data) {
				var name = data.nameCN;
				if( data.nameEN != '' && data.nameEN != null ){
					name = name + "（" + data.nameEN + "）";
				}
				$("#nameCN").html(name);
				$("#entityName").html(data.entityName);
				$("#bizPhone").html(data.bizPhone);
				$("#bizEmail").html(data.bizEmail);
				$("#department").html(data.department);
			}, 'json');
		}
		// ajax for message
		// 毫秒，3分钟定时刷新一次
		if ('<bean:write name="settingVO" property="message"/>' == 1) {
			$('#message').show();
			var refreshTime = 1000 * 180;

			var handler = function() {
				$.ajax({
					url : 'messageInfoAction.do?proc=get_notReadCount',
					dataType:'json',
					success : function(data) {
						var sum = 0 ;
						var title = "";
						for(var i = 0; i < data.length;i++){
							var accountData = data[i];
							sum+=accountData.count;
							title+=accountData.accountId+"("+accountData.count+")\r\n";
						}
						$('#sys_messageInfo_d').html("" + sum + "");
						$('#sys_messageInfo_d').attr("title",title);
					}
				});
			};

			var handler_allCount = function() {
				$.ajax({
					url : 'messageInfoAction.do?proc=get_allCount',
					success : function(html) {
						$('#sys_allMessageInfo_d').html(html);
					}
				});
			};

			var handlerForTaskInfo = function() {
				$.ajax({
					url : 'workflowActualAction.do?proc=get_notReadCount',
					dataType:'json',
					success : function(data) {
						var sum = 0 ;
						var title = "";
						for(var i = 0; i < data.length;i++){
							var accountData = data[i];
							sum = sum + parseInt(accountData.count);
							title+=accountData.accountId+"("+accountData.count+")\r\n";
						}
						$('#sys_taskInfo_d').html("<span title='"+title+"'>" + sum + "</span>");
					}
				});
			};

			// 加载即执行
			handler();
			handler_allCount();
			handlerForTaskInfo();
			// 延迟一段时间加载
			setInterval(handler, refreshTime);
			setInterval(handlerForTaskInfo, refreshTime);
		}

		// ajax for dataView
		if ('<bean:write name="settingVO" property="dataView"/>' == 1) {
			$('#dataView').show();
			$.post('dashboardAction.do?proc=get_dataView_ajax', '', function(
					data) {
				var myChart = new FusionCharts(
						"plugins/fusionCharts/charts/MSBar3D.swf",
						"chart_dataView_id", "400", "350", "0", "1");
				myChart.setXMLData(data);
				myChart.render("chart_dataView");
			}, 'text');
		}
		// ajax for clientContract
		if ('<bean:write name="settingVO" property="clientContract"/>' == 1) {
			$('#clientContract').show();
			$.post('dashboardAction.do?proc=get_clientContract_ajax', '',
					function(data) {
						var myChart = new FusionCharts(
								"plugins/fusionCharts/charts/Doughnut3D.swf",
								"chart_clientContract_id", "400", "350", "0",
								"1");
						myChart.setXMLData(data);
						myChart.render("chart_clientContract");
					}, 'text');
		}

		// ajax for orders
		if ('<bean:write name="settingVO" property="orders"/>' == 1) {
			$('#orders').show();
			$.post('dashboardAction.do?proc=get_orders_ajax', '',
					function(data) {
						var myChart = new FusionCharts(
								"plugins/fusionCharts/charts/Pie2D.swf",
								"chart_orders_id", "400", "350", "0", "1");
						myChart.setXMLData(data);
						myChart.render("chart_orders");
					}, 'text');
		}

		// ajax for contractService
		if ('<bean:write name="settingVO" property="contractService"/>' == 1) {
			$('#contractService').show();
			$.post('dashboardAction.do?proc=get_contractService_ajax', '',
					function(data) {
						var myChart = new FusionCharts(
								"plugins/fusionCharts/charts/Pie2D.swf",
								"chart_contractService_id", "400", "350", "0",
								"1");
						myChart.setXMLData(data);
						myChart.render("chart_contractService");
					}, 'text');
		}

		// ajax for attendance  MSColumn3D.swf柱状
		if ('<bean:write name="settingVO" property="attendance"/>' == 1) {
			$('#attendance').show();
			$.post('dashboardAction.do?proc=get_attendance_ajax', '', function(
					data) {
				var myChart = new FusionCharts(
						"plugins/fusionCharts/charts/MSLine.swf",
						"chart_attendance_id", "400", "350", "0", "1");
				myChart.setXMLData(data);
				myChart.render("chart_attendance");
			}, 'text');
		}

		// ajax for sb
		if ('<bean:write name="settingVO" property="sb"/>' == 1) {
			$('#sb').show();
			$.post('dashboardAction.do?proc=get_sb_ajax', '', function(data) {
				var myChart = new FusionCharts(
						"plugins/fusionCharts/charts/MSLine.swf",
						"chart_sb_id", "400", "350", "0", "1");
				myChart.setXMLData(data);
				myChart.render("chart_sb");
			}, 'text');
		}

		// ajax for cb
		if ('<bean:write name="settingVO" property="cb"/>' == 1) {
			$('#cb').show();
			$.post('dashboardAction.do?proc=get_cb_ajax', '', function(data) {
				var myChart = new FusionCharts(
						"plugins/fusionCharts/charts/MSLine.swf",
						"chart_cb_id", "400", "350", "0", "1");
				myChart.setXMLData(data);
				myChart.render("chart_cb");
			}, 'text');
		}

		// ajax for settlement
		if ('<bean:write name="settingVO" property="settlement"/>' == 1) {
			$('#settlement').show();
			$.post('dashboardAction.do?proc=get_settlement_ajax', '', function(
					data) {
				var myChart = new FusionCharts(
						"plugins/fusionCharts/charts/MSColumn3DLineDY.swf",
						"chart_settlement_id", "630", "350", "0", "1");
				myChart.setXMLData(data);
				myChart.render("chart_settlement");
			}, 'text');
		}

		// ajax for payment
		if ('<bean:write name="settingVO" property="payment"/>' == 1) {
			$('#payment').show();
			$.post('dashboardAction.do?proc=get_payment_ajax', '', function(
					data) {
				var myChart = new FusionCharts(
						"plugins/fusionCharts/charts/StackedColumn3D.swf",
						"chart_payment_id", "400", "350", "0", "1");
				myChart.setXMLData(data);
				myChart.render("chart_payment");
			}, 'text');
		}
		
		// ajax for employeeChange
		if ('<bean:write name="settingVO" property="employeeChange"/>' == 1) {
			$('#employeeChange').show();
			$.post('dashboardAction.do?proc=get_employeeChange_ajax', '', function(
					data) {
				var myChart = new FusionCharts(
						"plugins/fusionCharts/charts/MSLine.swf",
						"chart_employeeChange_id", "400", "350", "0", "1");
				myChart.setXMLData(data);
				myChart.render("chart_employeeChange");
			}, 'text');
		}
	})(jQuery);
</script>
