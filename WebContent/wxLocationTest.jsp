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
AccessToken：<%=request.getAttribute("accessToken") %><br/>  
        JSApi_Ticket：<%=request.getAttribute("jsapi_ticket") %><br/>  
        timestamp：<%=request.getAttribute("time") %><br/>  
        nonceStr：<%=request.getAttribute("randomStr") %><br/>  
        signature：<%=request.getAttribute("signature") %><br/>
           
<h3 id="menu-basic">Base</h3>    
<span class="desc">判断当前客户端是否支持指定JS接口</span>    
<button class="btn btn_primary" id="checkJsApi">checkJsApi</button>      
<h3 id="menu-device">设备信息接口</h3>    
<span class="desc">获取网络状态接口</span>    
<button class="btn btn_primary" id="getNetworkType">getNetworkType</button>    
<h3 id="menu-location">地理位置接口</h3>    
<span class="desc">使用微信内置地图查看位置接口</span>    
<button class="btn btn_primary" id="openLocation">openLocation</button>    
<span class="desc">获取地理位置接口</span>    
<button class="btn btn_primary" id="getLocation">getLocation</button>      
</div>    
</body>    
<script>    
/*    
* 注意：    
* 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。    
* 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。    
* 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html    
*    
* 如有问题请通过以下渠道反馈：    
* 邮箱地址：weixin-open@qq.com    
* 邮件主题：【微信JS-SDK反馈】具体问题    
* 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。    
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
 * 注意：
 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
 * 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 *
 * 如有问题请通过以下渠道反馈：
 * 邮箱地址：weixin-open@qq.com
 * 邮件主题：【微信JS-SDK反馈】具体问题
 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
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
* 注意：
* 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
* 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
* 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
*
* 如有问题请通过以下渠道反馈：
* 邮箱地址：weixin-open@qq.com
* 邮件主题：【微信JS-SDK反馈】具体问题
* 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
*/
wx.ready(function () {
// 1 判断当前版本是否支持指定 JS 接口，支持批量判断
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
// 6 设备信息接口
// 6.1 获取当前网络状态
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
// 7 地理位置接口
// 7.1 查看地理位置
document.querySelector('#openLocation').onclick = function () {
  wx.openLocation({
    latitude: 23.099994,
    longitude: 113.324520,
    name: 'TIT 创意园',
    address: '广州市海珠区新港中路 397 号',
    scale: 14,
    infoUrl: 'http://weixin.qq.com'
  });
};
// 7.2 获取当前地理位置
document.querySelector('#getLocation').onclick = function () {
  wx.getLocation({
    success: function (res) {
      alert(JSON.stringify(res));
    },
    cancel: function (res) {
      alert('用户拒绝授权获取地理位置');
    }
  });
};

var shareData = {
  title: '微信JS-SDK Demo',
  desc: '微信JS-SDK,帮助第三方为用户提供更优质的移动web服务',
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