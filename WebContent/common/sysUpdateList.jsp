<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<style type="text/css">
<!--
.changeul {
	margin-top: 10px;
	display: block;
	list-style-type: disc;
	-webkit-margin-before: 1em;
	-webkit-margin-after: 1em;
	-webkit-margin-start: 0px;
	-webkit-margin-end: 0px;
	-webkit-padding-start: 40px;
}

.changeli {
	line-height: 22px;
	text-align: left;
	display: list-item;
	text-align: -webkit-match-parent;
}
-->
</style>
<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="sysUpdateListModalId">
	<div class="modal-header" id="passwordHeader" style="cursor: move;">
		<a class="close" data-dismiss="modal"
			onclick="$('#sysUpdateListModalId').hide();$('#shield').hide();">×</a>
		<label>更新一览</label>
	</div>
	<div class="modal-body">
		<ul class="changeul">
			<li class="changeli">增加调薪功能-- 2015.2.13</li>
			<li class="changeli">增加职位异动功能-- 2015.2.12</li>
			<li class="changeli">微信版本支持英文-- 2015.2.10</li>
			<li class="changeli">结算支持多种货币-- 2015.2.08</li>
		</ul>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 弹出模态窗口
	function popupSystemUpdatePage() {
		$('#sysUpdateListModalId').show('hide');
		$('#shield').show();
	};

	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e) {
		if (e.keyCode == 27) {
			$('#sysUpdateListModalId').hide();
			$('#shield').hide();
		}
	});
</script>