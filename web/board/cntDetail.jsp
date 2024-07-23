<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
      table {
        width: 100%;
        border-collapse: collapse;
      }
      th, td {
        border-bottom: 1px solid black;
        padding: 8px;
      }
      input[type=text], select {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
      }
      input[type=submit] {
        width: auto;
        padding: 14px 20px;
      }
      .button-container {
        display: flex;
        justify-content: flex-end;
      }
      .content-container{
        padding: 0;
      }
      .content-area{
        border: none;
        border-radius: 5px;
        width: 100%;
      }
      textarea:focus{
        outline: none;
      }
      input:focus{
        outline: none;
      }
      .hide{
        display: none;
      }
      a{
        color: black;
        text-decoration: none;
      }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <form action="board.do?method=update&seq=${board.seq}" method="post">
            <table>
                <thead>
                <tr>
                    <th colspan="6"><input type="text" name="title"  value="${board.title}"></th>
                </tr>
                <tr>
                    <th>작성자</th>
                    <th><input type="text" name="nickname" value="${board.nickname}" disabled></th>
                    <th>날짜</th>
                    <th><input type="text" name="date" value="${board.date}" disabled></th>
                    <th>조회수</th>
                    <th>${view}</th>
                </tr>
                </thead>
                <tbody>
                <tr><td colspan="6" class="content-container">
                <textarea class="content-area" name="content" id="" cols="30" rows="10" maxlength="2048">
                    ${board.content}
                </textarea>
                </td></tr>
                </tbody>
            </table>
            <c:if test="${board.id == user}">
                <div class="button-container">
                    <button class="button" name="button" type="submit" value="modify">수정</button>
                    <button class="button" name="button" type="submit" value="delete">삭제</button>
                </div>
            </c:if>
        </form>
        <form action="reply.do?method=insert&seq=${board.seq}&view=${view}" method="post">
            Comment
            <table>
                <thead>
                </tr>
                <tr>
                    <td>번호</td>
                    <td colspan="3">댓글</td>
                    <td>작성자</td>
                    <td>시간</td>
                    <td></td>
                </tr>
                </thead>
                <tbody class="reply-container">

                </tbody>
            </table>
            <input type="text" name="commentNickname" value="${user.nickname}" disabled>
            <input type="text" name="commentContent" placeholder="댓글">
            <button name="button" type="submit" value="insert">등록</button>
            <div class="button-container">
                <button class="button" type="button" value="delete">삭제</button>
            </div>
        </form>
    </div>
</div>
<script>
  document.addEventListener('DOMContentLoaded',()=>{
    const reply_tbody = document.querySelector('.reply-container');
    const buttons = document.querySelector('.buttons');
    let replyData = [];
    const showRowsPerPage = 10;
    let currentPage = 1;
    //AJAX 요청으로 데이터 가져오기

    fetch('/reply.do?method=list&seq=${board.seq}')
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      replyData = data;
      renderReplyTable();
      setupPaginaition();
    })
    .catch(error => console.log('Error: ', error));

    function renderReplyTable(){
      const startIndex = (currentPage - 1) * showRowsPerPage;
      const endIndex = startIndex + showRowsPerPage;
      reply_tbody.innerHTML = '';
      replyData.slice(startIndex,endIndex).forEach(data=>{
        const {seq, content, nickname, date, group_depth} = data;
        const row = document.createElement('tr');
        row.classList.add('button'+seq);

        const replyDiv = document.createElement('div');
        replyDiv.classList.add('replyDiv' + seq , 'hide');
        row.innerHTML =
            "<td class='reply__seq'>" + seq + "</td>" +
            "<td colspan='3' class='reply__content'><a href='#'>" + content + "</a></td>" +
            "<td class='reply__id'>" + nickname + "</td>" +
            "<td class='reply__date'>" + date + "</td>"+
            "<td><button type='button' class=\"reply-btn"+seq+"\" onclick='reply("+seq+")' value='open' >대댓글</button></td>"+
            "<div class='hide'><input type='text' name='group_depth"+seq+"' value='"+group_depth+"'></div>"
        ;
        replyDiv.innerHTML =
            "<input type='text' name='replyNickname' value='${user.nickname}' disabled>"+
            "<input type='text' class='replyComment' name='replyComment"+seq+"' placeholder='댓글'>"+
            "<button type='submit' name='button' value='"+seq+"' >댓글쓰기</button>";
        reply_tbody.appendChild(row);
        reply_tbody.appendChild(replyDiv);

      })
    }

    function setupPaginaition(){
      const totalPages = Math.ceil(replyData.length / showRowsPerPage);
      buttons.innerHTML = '';
      for(let i =1 ; i<=totalPages ; i++){
        const button = document.createElement('button');
        button.classList.add('button');
        button.textContent = i;
        button.addEventListener('click',()=>{
          currentPage = i;
          renderReplyTable();
        });
        buttons.appendChild(button);
      }
    }
  })
 function reply(seq){
   const divClass = '.replyDiv'.concat(seq);
   const btnClass = '.reply-btn'.concat(seq);

   const replyDiv = document.querySelector(divClass);
   const replyBtn = document.querySelector(btnClass);
   if(replyBtn.value === 'open'){
   replyDiv.classList.remove('hide');
     replyBtn.value = 'close';
   }else{
     replyDiv.classList.add('hide');
     replyBtn.value = 'open';
   }
 }
 function submit(seq ,content){
   $.ajax({
     url:'/reply.do?method=reply',
     type:'POST',
     data:{
       seq,
       content
     },
     success:(data)=>{

     }
   })
 }
</script>
</body>
</html>