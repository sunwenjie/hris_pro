<html>    
<head>    
<meta charset="utf-8">    
<title>JS-SDK Demo</title>    
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">    
<!--  <script src="${pageContext.request.contextPath}/weixin.js" type="text/javascript"></script>  -->
<!-- <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>  -->
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>    
<body ontouchstart="">       
<div class="lbox_close wxapi_form"> 
AccessToken��<%=request.getAttribute("accessToken") %><br/>  
        JSApi_Ticket��<%=request.getAttribute("jsapi_ticket") %><br/>  
        timestamp��<%=request.getAttribute("time") %><br/>  
        nonceStr��<%=request.getAttribute("randomStr") %><br/>  
        signature��<%=request.getAttribute("signature") %><br/>
           
<h3 id="menu-basic">Base</h3>    
<span class="desc">�жϵ�ǰ�ͻ����Ƿ�֧��ָ��JS�ӿ�</span>    
<button class="btn btn_primary" id="checkJsApi">checkJsApi</button>      
<h3 id="menu-device">�豸��Ϣ�ӿ�</h3>    
<span class="desc">��ȡ����״̬�ӿ�</span>    
<button class="btn btn_primary" id="getNetworkType">getNetworkType</button>    
<h3 id="menu-location">����λ�ýӿ�</h3>    
<span class="desc">ʹ��΢�����õ�ͼ�鿴λ�ýӿ�</span>    
<button class="btn btn_primary" id="openLocation">openLocation</button>    
<span class="desc">��ȡ����λ�ýӿ�</span>    
<button class="btn btn_primary" id="getLocation">getLocation</button>      
</div>    
</body>    
<script>    
/*    
* ע�⣺    
* 1. ���е�JS�ӿ�ֻ���ڹ��ںŰ󶨵������µ��ã����ںſ�������Ҫ�ȵ�¼΢�Ź���ƽ̨���롰���ں����á��ġ��������á�����д��JS�ӿڰ�ȫ��������    
* 2. ��������� Android ���ܷ����Զ������ݣ��뵽�����������µİ����ǰ�װ��Android �Զ������ӿ��������� 6.0.2.58 �汾�����ϡ�    
* 3. ���� JS-SDK �ĵ���ַ��http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html    
*    
* ����������ͨ����������������    
* �����ַ��weixin-open@qq.com    
* �ʼ����⣺��΢��JS-SDK��������������    
* �ʼ�����˵�����ü��������������������ڣ��������������������ĳ������ɸ��Ͻ���ͼƬ��΢���Ŷӻᾡ�촦����ķ�����    
*/    
wx.config({    
debug: true,    
appId: 'wx059471abeb063951',    
timestamp: '<%=request.getAttribute("time") %>',    
nonceStr: '<%=request.getAttribute("randomStr") %>',    
signature: '<%=request.getAttribute("signature") %>',    
jsApiList: [    
'checkJsApi',      
'getNetworkType',    
'openLocation',    
'getLocation'  
]    
});    
</script>    
<script type="text/javascript"> 
/*
 * ע�⣺
 * 1. ���е�JS�ӿ�ֻ���ڹ��ںŰ󶨵������µ��ã����ںſ�������Ҫ�ȵ�¼΢�Ź���ƽ̨���롰���ں����á��ġ��������á�����д��JS�ӿڰ�ȫ��������
 * 2. ��������� Android ���ܷ����Զ������ݣ��뵽�����������µİ����ǰ�װ��Android �Զ������ӿ��������� 6.0.2.58 �汾�����ϡ�
 * 3. ���� JS-SDK �ĵ���ַ��http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 *
 * ����������ͨ����������������
 * �����ַ��weixin-open@qq.com
 * �ʼ����⣺��΢��JS-SDK��������������
 * �ʼ�����˵�����ü��������������������ڣ��������������������ĳ������ɸ��Ͻ���ͼƬ��΢���Ŷӻᾡ�촦����ķ�����
 */
wx.config({
    debug: true,
    appId: 'wx059471abeb063951',    
    timestamp: '<%=request.getAttribute("time") %>',    
    nonceStr: '<%=request.getAttribute("randomStr") %>',    
    signature: '<%=request.getAttribute("signature") %>', 
    jsApiList: [
	'checkJsApi',      
	'getNetworkType',    
	'openLocation',    
	'getLocation'
    ]
});
/*
* ע�⣺
* 1. ���е�JS�ӿ�ֻ���ڹ��ںŰ󶨵������µ��ã����ںſ�������Ҫ�ȵ�¼΢�Ź���ƽ̨���롰���ں����á��ġ��������á�����д��JS�ӿڰ�ȫ��������
* 2. ��������� Android ���ܷ����Զ������ݣ��뵽�����������µİ����ǰ�װ��Android �Զ������ӿ��������� 6.0.2.58 �汾�����ϡ�
* 3. ���� JS-SDK �ĵ���ַ��http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
*
* ����������ͨ����������������
* �����ַ��weixin-open@qq.com
* �ʼ����⣺��΢��JS-SDK��������������
* �ʼ�����˵�����ü��������������������ڣ��������������������ĳ������ɸ��Ͻ���ͼƬ��΢���Ŷӻᾡ�촦����ķ�����
*/
wx.ready(function () {
// 1 �жϵ�ǰ�汾�Ƿ�֧��ָ�� JS �ӿڣ�֧�������ж�
document.querySelector('#checkJsApi').onclick = function () {
  wx.checkJsApi({
    jsApiList: [
      'getNetworkType',
      'previewImage'
    ],
    success: function (res) {
      alert(JSON.stringify(res));
    }
  });
};
// 6 �豸��Ϣ�ӿ�
// 6.1 ��ȡ��ǰ����״̬
document.querySelector('#getNetworkType').onclick = function () {
  wx.getNetworkType({
    success: function (res) {
      alert(res.networkType);
    },
    fail: function (res) {
      alert(JSON.stringify(res));
    }
  });
};
// 7 ����λ�ýӿ�
// 7.1 �鿴����λ��
document.querySelector('#openLocation').onclick = function () {
  wx.openLocation({
    latitude: 23.099994,
    longitude: 113.324520,
    name: 'TIT ����԰',
    address: '�����к������¸���· 397 ��',
    scale: 14,
    infoUrl: 'http://weixin.qq.com'
  });
};
// 7.2 ��ȡ��ǰ����λ��
document.querySelector('#getLocation').onclick = function () {
  wx.getLocation({
    success: function (res) {
      alert(JSON.stringify(res));
    },
    cancel: function (res) {
      alert('�û��ܾ���Ȩ��ȡ����λ��');
    }
  });
};

var shareData = {
  title: '΢��JS-SDK Demo',
  desc: '΢��JS-SDK,����������Ϊ�û��ṩ�����ʵ��ƶ�web����',
  link: 'http://demo.open.weixin.qq.com/jssdk/',
  imgUrl: 'http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0'
};
wx.onMenuShareAppMessage(shareData);
wx.onMenuShareTimeline(shareData);
});
wx.error(function (res) {
alert(res.errMsg);
});
</script>    
</html>