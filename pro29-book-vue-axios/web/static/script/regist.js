function $(id) {
    return document.getElementById(id);
}

function preRegist() {
    let unameText = $("unameTxt").value;
    // 用户名应为6~16位数组和字母组成
    let unameReg = /[0-9a-zA-Z]{6,16}/;
    if (!unameReg.test(unameText)) {
        $("unameSpan").innerText = "用户名应为6~16位数组和字母组成！";
        $("unameSpan").style.visibility = "visible";
        return false;
    } else {
        $("unameSpan").style.visibility = "hidden";
    }

    // 密码的长度至少为8位
    let pwdTxt = $("pwdTxt").value;
    let pwdReg = /[\w]{6,}/;
    if (!pwdReg.test(pwdTxt)) {
        $("pwdSpan").style.visibility = "visible";
        return false;
    } else {
        $("pwdSpan").style.visibility = "hidden";
    }

    // 密码两次输入不一致
    if ($("pwdTxt2").value != $("pwdTxt").value) {
        $("pwdSpan2").style.visibility = "visible";
        return false;
    } else {
        $("pwdSpan2").style.visibility = "hidden";
    }

    // 请输入正确的邮箱格式
    let emailTxt = $("emailTxt").value;
    let emailReg = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.]){1,2}[A-Za-z\d]{2,5}$/g;
    if (!emailReg.test(emailTxt)) {
        $("emailSpan").style.visibility = "visible";
        return false;
    } else {
        $("emailSpan").style.visibility = "hidden";
    }

    return true;
}

// 创建XMLHttpRequest对象
var xmlHttpRequest;

function createXMLHttpRequest() {
    if (window.XMLHttpRequest) {
        xmlHttpRequest = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        try {
            xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (e) {
            xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
        }
    }
}

// 姓名异步认证
function checkUname(uname) {
    createXMLHttpRequest();
    var url = "user.do?operate=checkUname&uname=" + uname;
    // true表示是否异步
    xmlHttpRequest.open("GET", url, true);
    // 设置回调函数
    xmlHttpRequest.onreadystatechange = checkUnameCallBack;
    // 发送请求
    xmlHttpRequest.send();
}

// 回调函数
function checkUnameCallBack() {
    // alert("hello world");
    if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
        // xmlHttpRequest.responseText服务器返回的内容
        // {'uname':'1'}
        let responseText = xmlHttpRequest.responseText;
        console.log(responseText);
        if (responseText == "{'uname':'1'}") {
            $("unameSpan").innerText = "用户已经注册，请重试！";
            $("unameSpan").style.visibility = "visible";
        } else if (responseText == "{'uname':'0'}") {
            $("unameSpan").style.visibility = "hidden";
        } else {
            $("unameSpan").innerText = responseText;
            $("unameSpan").style.visibility = "visible";
        }
    }
}