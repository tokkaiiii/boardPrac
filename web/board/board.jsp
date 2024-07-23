<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <style>
      * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
      }
      .pagination .active {
        font-weight: bold;
      }
      .buttons {
        position: relative;
        padding: 1rem 0;
        display: inline-flex;
        justify-content: center;
      }
      .button.active,
      .button:hover {
        color: #1f975d;
        font-weight: 600;
        text-decoration: underline;
      }
      body {
        width: 100vw;
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
      }
      #board-table {
        width: 800px;
        border-collapse: collapse;
      }
      .content {

        padding: 0.3em 0;

        border-bottom: 1px solid rgba(0, 0, 0, 0.08);
      }
      .content__seq,
      .content__writer,
      .content__date,
      .content__view{
        width: 15%;
      }
      .content__title {
        flex: 1;
        text-align: left;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      .content__title:hover{
        color: #1f975d;
        font-weight: 600;
        text-decoration: underline;
      }
      th{
        text-align: left;
      }
      a{
        text-decoration: none;
        color:black;
      }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<c:if test="${empty user}">
    <script>
        alert('회원가입 해주세요')
        location.href='/index.do';
    </script>
</c:if>
<nav>
    <select id="search-select">
        <option value="title">title</option>
        <option value="content">content</option>
        <option value="nickname">nickname</option>
    </select>
    <input id="search-field" type="text">
    <input id="search-button" type="button">
</nav>
<div class="container">
    <table id="board-table">
        <thead>
        <tr class="content">
            <th>#</th>
            <th>Title</th>
            <th>Nickname</th>
            <th>Date</th>
            <th>View</th>
        </tr>
        </thead>
        <tbody>
        <!-- board data will be added here -->
        </tbody>
    </table>
</div>
<div class="buttons"></div>
<div class="writeContainer">
    <form action="/board.do?method=write" method="post">
        <button type="submit">글쓰기</button>
    </form>
</div>
<script>
  document.addEventListener('DOMContentLoaded', () => {
    const my_tbody = document.querySelector('tbody');
    const paginationContainer = document.querySelector('.buttons');
    let boardData = [];
    const showRowsPerPage = 10;
    let currentPage = 1;
    const showPerButton = 5;
    let startButton = currentPage - (currentPage - 1) % showPerButton;


    // AJAX 요청으로 데이터 가져오기
    fetch('/board.do?method=boards') // 서블릿 URL
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      boardData = data; // 가져온 데이터를 boardData에 할당
      renderTable(); // 테이블 렌더링
      setupPagination(); // 페이지네이션 설정
    })
    .catch(error => console.error('Error:', error));

    let select = document.getElementById('search-select');
    let selectIndex = select.selectedIndex;
    let selectValue = select.options[selectIndex].innerText;
    $(()=>{
      $('#search-field').on('keyup',()=>{
        $.ajax({
          url:'/board.do?method='+selectValue,
          type:'POST',
          data:{searchField:$('#search-field').val()},
          success:(data)=>{
            boardData = data;
            currentPage = 1;
            renderTable();
            setupPagination();
          }
        })
      })
    });

    function renderTable() {
      const startIndex = (currentPage - 1) * showRowsPerPage;
      const endIndex = startIndex + showRowsPerPage;
      my_tbody.innerHTML = ''; // 기존 데이터 제거
      boardData.slice(startIndex, endIndex).forEach(data => {
        const {seq, title,nickname, date, group_depth, boardview} = data;
        const row = document.createElement('tr');
        row.classList.add('content');
        row.innerHTML =
            "<td class='content__seq'>"+seq+"</td>"+
            "<td class='content__title'><a href='/board.do?method=select&seq="+seq+"&group_depth="+group_depth+"'>"+title+"</a></td>"+
            "<td class='content__writer'>"+nickname+"</td>"+
            "<td class='content__date'>"+date+"</td>"+
            "<td class='content__view'>"+boardview+"</td>";
        my_tbody.appendChild(row);
      });
      highlightCurrentPageButton();
    }


    function setupPagination() {
      const totalPages = Math.ceil(boardData.length / showRowsPerPage);
      let lastButton = startButton+showPerButton-1;
      let endButton = lastButton < totalPages ? lastButton : totalPages;
      paginationContainer.innerHTML = ''; // 기존 버튼 제거

      for (let i = startButton; i <= endButton; i++) {
        const pageButton = document.createElement('button');
        pageButton.classList.add('button');
        pageButton.textContent = i;
        pageButton.addEventListener('click', () => {
          currentPage = i;
          renderTable();
        });
        paginationContainer.appendChild(pageButton);
      }
      highlightCurrentPageButton();
    }

    function highlightCurrentPageButton() {
      const pageButtons = paginationContainer.querySelectorAll('button');
      pageButtons.forEach(btn => {
        btn.classList.toggle('active', btn.textContent === currentPage.toString());
      });
    }
  });
</script>


</body>
</html>