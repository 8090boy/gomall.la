<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<html>
<head>
<title>Client Flow Example</title>
</head>

<body>
	<script> 
           function callback(user) 
           {
              var userName = document.getElementById('userName');
              var greetingText = document.createTextNode('Greetings, '+ user.openid + '.');
              userName.appendChild(greetingText);
           }


           //应用的APPID，请改为你自己的
            var appID = "100342945";
           //成功授权后的回调地址，请改为你自己的
            var redirectURI = "http://www.legendshop.cn";


           //构造请求
           if (window.location.hash.length == 0) 
           {
              var path = 'https://graph.qq.com/oauth2.0/authorize?';
              var queryParams = ['client_id=' + appID,'redirect_uri=' + redirectURI,'scope=' + 'get_user_info,list_album,upload_pic,add_feeds,do_like','response_type=token'];
        
              var query = queryParams.join('&');
              var url = path + query;
             // alert(url);
              window.open(url);
           }

           else 
           {
              //获取access token
              var accessToken = window.location.hash.substring(1);

              //使用Access Token来获取用户的OpenID
              var path = "https://graph.qq.com/oauth2.0/me?";
              var queryParams = [accessToken, 'callback=callback'];
              var query = queryParams.join('&');
              var url = path + query;

              var script = document.createElement('script');
              script.src = url;
                alert(url);
              document.body.appendChild(script);        
           }
        </script>

</body>
</html>

