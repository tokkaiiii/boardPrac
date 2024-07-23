<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
      /* border: 1px solid purple; */
      border: none;
      background-color: transparent;
      font-size: 16px;
      color: rgba(255, 255, 255, 0.9);
      width: 15rem;
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
    .signup {
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
    a:hover{
      color: rgba(28, 152, 244, 0.8)
    }
  </style>
</head>
<body>
<main>
  <div class="logo"></div>
  <form name="signup-form" action="user.do?method=signup" method="post">
    <fieldset>
      <input type="text" name="id" id="id" placeholder="아이디" autofocus required/>
    </fieldset>
    <div class="success-id hide">사용할 수 있는 아이디입니다</div>
    <div class="failed-id hide">아이디는 4~12글자여야 합니다</div>
    <div class="failed-id2 hide">영어 또는 숫자만 가능합니다</div>

    <fieldset>
      <input type="text" name="password" id="password" placeholder="비밀번호" required/>
    </fieldset>
    <div class="str-pwd-msg hide">
      8글자 이상, 영문 소문자,대문자 숫자, 특수문자(@$!%*#?&)를 사용하세요
    </div>
    <fieldset class="signup">
      <button type="submit" id="signup" onkeydown="submit()">로그인</button>
    </fieldset>
    <a href="user.do?method=joinDo">join</a>
  </form>
</main>
<script>
  function submit(){
    document.signup-form.submit();
  }
</script>
</body>
</html>