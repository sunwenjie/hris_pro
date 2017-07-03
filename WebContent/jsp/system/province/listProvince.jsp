<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" type="String"></tiles:put>
  <tiles:put name="body" value="/contents/system/province/listProvinceBody.jsp" />
</tiles:insert>