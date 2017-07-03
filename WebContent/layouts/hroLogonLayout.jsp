<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.util.KANConstants"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="GBK">
<title><tiles:getAsString name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="shortcut icon" href="images/icons/iclick-ico.ico">
<!-- Loading Kanpower Style -->
<link href="css/kanpower.css" rel="stylesheet">

<!-- Loading Tip Tip Style -->
<link href="css/tipTip.css" rel="stylesheet">

<!-- Loading JQuery Auto Complate Style -->
<link href="css/jquery.autocomplete.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->

<!-- Load JS here for greater good =============================-->

<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script src="js/jquery-ui-1.10.0.custom.min.js"></script>
<script src="js/custom_checkbox_and_radio.js"></script>
<script src="js/custom_radio.js"></script>
<script src="js/jquery.tagsinput.js"></script>
<script src="js/bootstrap-tooltip.js"></script>
<script src="js/jquery.placeholder.js"></script>
<script src="js/jquery.clickoutside.js"></script>
<script src="js/jquery.tipTip.minified.js"></script>
<script src="js/kan.list.js"></script>
<script src="js/kan.validate.js"></script>
<script src="js/kan.thinking.js"></script>
<script src="js/kan.tree.js"></script>
<script src="js/kan.attachment.js"></script>
<script src="plugins/My97DatePicker/WdatePicker.js"></script>
<script src="plugins/fusionCharts/FusionCharts.js"></script>
<script src="plugins/fusionCharts/highcharts.js"></script>
<script src="plugins/fusionCharts/FusionChartsExportComponent.js"></script>
<%-- by siuvan
<script src="https://s.meiqia.com/js/mechat.js?unitid=5291" charset="UTF-8"></script>
<script src="https://s.meiqia.com/js/mechat.js?unitid=5291" charset="UTF-8"></script>
 --%>

<!--[if lt IE 8]>
      <script src="js/icon-font-ie7.js"></script>
      <script src="js/icon-font-ie7-24.js"></script>
    <![endif]-->
<style type="text/css">
#center {
	margin-right: auto;
	margain-left: auto;
}
</style>

<script>
	function closeWindow() {
		if (confirm('退出系统？'))
			window.close();
		else
			return;
	};

	function link(url) {
		window.location.href = url;
	};
	
	function submitForm() {
		$(".login_form").submit();
	};
	
	agreest= function () {
		var language = '<%=request.getLocale().getLanguage()%>';
		var msg = '<bean:message bundle="public" key="popup.confirm.back.to.list" />';
		var subAction = $(".subAction").val();
		if(subAction == 'createObject' || subAction == 'modifyObject'){
			if(language != 'en'){
				msg = msg.replace('XX',$('.subAction').val() == 'createObject'?'新增':'修改');
			}
			return confirm(msg);
		}
		return true;;
	};
</script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
	<div class="login">
		<tiles:insert attribute="body" />
		<%-- by siuvan <p>&copy;<%= request.getAttribute("title") %> <%= KANConstants.VERSION %>a 版 KANGROUP</p>--%>
		<p>&copy;iClick</p>
	</div>
	<!-- /container -->
</body>
</html>
