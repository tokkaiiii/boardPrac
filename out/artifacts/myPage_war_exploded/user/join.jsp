<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-07-15
  Time: 오전 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/np/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    />
    <title>Document</title>
    <style>
      * {
        box-sizing: border-box;
        padding: 0;
        margin: 0;
      }

      body {

        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 100vw;
        height: 100vh;
        background-image: url("https://cdn.pixabay.com/photo/2018/11/02/10/30/black-and-white-cat-3790003_1280.jpg");
        background-size: cover;
        background-repeat: no-repeat;
      }

      main {
        background: linear-gradient(
            124.47deg,
            rgba(255, 255, 255, 0),
            rgba(255, 255, 255, 0),
            rgba(255, 255, 255, 0)
        );
        border-radius: 16px;
        box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
        backdrop-filter: blur(0px);
      }

      main:hover {
        background: linear-gradient(
            124.47deg,
            rgba(255, 255, 255, 0.2),
            rgba(255, 255, 255, 0),
            rgba(255, 255, 255, 0.2)
        );
        border-radius: 16px;
        box-shadow: 0 4px 30px rgba(0, 0, 0, 0.3);
        backdrop-filter: blur(5px);
      }

      .hide {
        display: none;
      }

      .logo {

        width: 170px;
        margin: 12px auto 30px;
      }

      fieldset {
        border: 1px solid green;
        border-radius: 50px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        margin: 10px;
        padding: 10px 12px;
      }

      fieldset:not(:last-of-type) {
        background: linear-gradient(
            124.47deg,
            rgba(255, 255, 255, 0.2),
            rgba(255, 255, 255, 0),
            rgba(255, 255, 255, 0.2)
        );
        box-shadow: 0 4px 30px rgba(0, 0, 0, 0.2);
        backdrop-filter: blur(0px);
        -webkit-backdrop-filter: blur(0px);
        border: 1px solid rgba(255, 255, 255, 0.3);
      }

      fieldset:not(:last-of-type):hover {
        background: linear-gradient(
            124.47deg,
            rgba(255, 255, 255, 0.5),
            rgba(255, 255, 255, 0.3),
            rgba(255, 255, 255, 0.5)
        );
        box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
        backdrop-filter: blur(5px);
        -webkit-backdrop-filter: blur(5px);
        border: 1px solid rgba(255, 255, 255, 0.3);
      }

      input {
        /*border: 1px solid purple;*/
        border: none;
        background-color: transparent;
        font-size: 16px;
        color: rgba(255, 255, 255, 0.9);
        width: 15rem;
      }

      select {
        display: flex;
        flex-direction: column;
        align-items: center;
        border: none;
        background-color: transparent;
        font-size: 16px;
        color: rgba(255, 255, 255, 0.9);
        outline: none;
      }

      .select {
        border: none;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin: 10px;
        padding: 10px 12px;
      }

      input::placeholder {
        color: rgba(255, 255, 255, 0.5);
      }

      input:focus {
        outline: none;
      }

      button {
        /* border: 1px solid purple; */
        border: 1px solid rgba(255, 255, 255, 0.3);
        border-radius: 50px;
        color: rgba(255, 255, 255, 0.9);
        font-size: 16px;
        box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
        background: linear-gradient(
            124.47deg,
            rgba(61, 151, 215, 0.2),
            rgba(61, 151, 215, 0),
            rgba(61, 151, 215, 0.2)
        );
        backdrop-filter: blur(5px);
        cursor: pointer;
      }

      button:hover {
        background: linear-gradient(
            124.47deg,
            rgba(61, 151, 215, 0.8),
            rgba(61, 151, 215, 0.6),
            rgba(61, 151, 215, 0.8)
        );
        top: 1px;
        margin-top: 1px;
      }

      button:active {
        position: relative;
        top: 5px;
        margin-top: 5px;
      }

      main > div {
        color: #ffffff;
        font-size: 14px;
        padding: 0px 30px 0px 30px;
      }

      .join {
        border: none;
      }

      a {
        margin: 5px auto 10px;
        color: #ffffff;
        text-decoration: none;
        display: flex;
        align-items: center;
        justify-content: center;
      }

      a:hover {
        color: rgba(28, 152, 244, 0.8)
      }
      .option{
        color: gray;
      }
    </style>
</head>
<body>
<main>
    <div class="logo">logo</div>
    <form name="join-form" action="../user.do?method=join" method="post">
        <fieldset>
            <input type="text" name="id" id="id" placeholder="아이디" autofocus required/>
        </fieldset>
        <div class="success-id hide">사용할 수 있는 아이디입니다</div>
        <div class="failed-id hide">아이디는 4~12글자여야 합니다</div>
        <div class="failed-id2 hide">영어 또는 숫자만 가능합니다</div>

        <fieldset>
            <input type="password" name="password" id="password" placeholder="비밀번호" required/>
        </fieldset>
        <div class="str-pwd-msg hide">
            8글자 이상, 영문 소문자,대문자 숫자, 특수문자(@$!%*#?&)를 사용하세요
        </div>
        <fieldset>
            <input type="password" name="password-retype" id="password-retype"
                   placeholder="비밀번호 확인"/>
        </fieldset>
        <fieldset>
            <input type="text" name="name" id="name" placeholder="이름"/>
        </fieldset>
        <fieldset>
            <input type="text" name="nickname" id="nickname" placeholder="닉네임"/>
        </fieldset>
        <fieldset>
            <input type="email" name="email" id="email" placeholder="이메일"/>
        </fieldset>
        <div class="select">
            <select id="email-select">
                <option class="option" value="none" >직접입력</option>
                <option class="option" value="naver" >naver.com</option>
                <option class="option" value="gmail" >gmail.com</option>
            </select>
        </div>
        <div class="miss-pwd hide">비밀번호가 일치하지 않습니다</div>
        <fieldset class="join">
            <button type="submit" id="join" onkeydown="submit()">회원가입</button>
        </fieldset>
    </form>
</main>
<script>
  function submit() {
    document.signup - form.submit();
  }

  function domain_remove(email) {
    email = email.trim();
    let atIndex = email.indexOf('@');
    if (atIndex != -1) {
      return email.substring(0, atIndex);
    }
    return email
  }

  const email = document.getElementById('email');
  const email_select = document.getElementById('email-select');
  email_select.addEventListener('change', (evt) => {
        if (evt.target.value !== 'none') {
          email.value = domain_remove(email.value);
          const e = email.value + '@' + email_select.options[email_select.selectedIndex].innerText;
          email.value = e;
        } else {
          email.value = '';
        }
      }
  )

</script>
</body>
</html>
