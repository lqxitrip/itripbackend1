<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>生成二维码</title>
    <script src="js/jquery.min.js" type="text/javascript"></script>
    <script src="js/qrcode.min.js" type="text/javascript"></script>
</head>
<body>
<div id="qrcode"></div>
<script>
    $.ajax({
        url : "api/wx/createCode/D10000012017063016272052c377",
        method : "GET",
        success : function(data) {
            new QRCode(document.getElementById('qrcode'), data.data.code_url);
        }

    });
    //查询订单
    function queryOrder() {
        $.ajax({
            url : "api/wx/queryOrderStatus/D10000012017063016272052c377",
            method : "GET",
            success : function(result) {
                if (result.success == 'true') {
                    var orderStatus = result.data.orderStatus;
                    if (orderStatus == 2) { //支付成功后，跳转到百度页面。
                        window.location.href="http://www.baidu.com";
                    }
                }
            }
        });
    }
    //每隔5秒查看下订单的状态
    setInterval(queryOrder, 5000);

</script>
</body>
</html>
