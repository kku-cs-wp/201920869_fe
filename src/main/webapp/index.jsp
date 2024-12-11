<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Webapp seok</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,500&display=swap" rel="stylesheet">
	<script type="text/javascript" src="js/common.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        header {
            background-color: #007bff;
            color: white;
            padding: 20px;
            font-size: 24px;
            position: sticky;
            top: 0;
            display: flex;
            align-items: center;
            justify-content: space-between; /* 좌우로 배치 */
            z-index: 1001;
        }

        header button {
            background-color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
            color: #007bff;
            margin-right: 10px;
        }

        header button:hover {
            background-color: #0056b3;
            color: white;
        }
        
        /* 모달 배경 스타일 */
        #modal-background {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 999;
        }

        /* 모달 창 스타일 */
        #modal {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: white;
            width: 400px;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
            z-index: 1000;
            text-align: center;
        }

        /* 모달의 content div 스타일 */
        #modal-content {
            margin-bottom: 20px;
        }

        #modal-content h2 {
            margin-bottom: 10px;
        }

        #modal-content p {
            margin-bottom: 20px;
        }

        #modal button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        #modal button:hover {
            background-color: #0056b3;
        }
        
        /* 팝업 메시지 스타일 */
        #popup-message {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 300px;
            background-color: white;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
            border-radius: 10px;
            z-index: 2000;
            padding: 20px;
            text-align: center;
        }

        #popup-message h2 {
            font-size: 18px;
            margin-bottom: 10px;
        }

        #popup-message p {
            margin-bottom: 20px;
        }

        #popup-message button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        #popup-message button:hover {
            background-color: #0056b3;
        }

        /* 배경 어둡게 처리 */
        #popup-background {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 1999;
        }
        
        #site-name {
	        color: white;
	        text-decoration: none;
	        font-size: 24px;
	    }
	
	    #site-name:hover {
	        color: #e0e0e0; /* 사이트 이름을 클릭할 때 색상 변화 */
	    }
	    
	    #auth-section {
	        display: flex;
	        align-items: center;
	    }
	
	    #auth-section button {
	        background-color: white;
	        border: none;
	        padding: 10px;
	        border-radius: 5px;
	        cursor: pointer;
	        font-size: 18px;
	        color: #007bff;
	        margin-left: 10px;
	    }
	
	    #auth-section button:hover {
	        background-color: #0056b3;
	        color: white;
	    }

        .layout {
            display: flex;
            flex: 1;
        }

        .sidebar-container {
            width: 250px;
            background-color: #343a40;
            color: white;
            padding: 20px;
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .sidebar-container.hidden {
            width: 0;
            padding: 0;
            overflow: hidden;
        }

        nav ul {
            list-style: none;
            padding-left: 0;
        }

        nav ul li {
            margin-bottom: 15px;
        }

        nav ul li a {
            color: white;
            text-decoration: none;
            font-size: 18px;
            display: block;
        }

        nav ul li a:hover {
            color: #007bff;
        }

        .submenu {
            margin-left: 20px;
            display: none;
        }

        .submenu.active {
            display: block;
        }

        main {
            flex: 1;
            padding: 20px;
            position: relative;
            overflow-y: auto; /* 메인 콘텐츠에서 스크롤이 가능하게 설정 */
        }

        .page-card {
            display: none;
            width: 100%;
            min-height: 100%; /* 페이지 카드의 높이를 부모 요소에 맞추어 설정 */
            padding: 20px;
            background-color: white;
            overflow-y: auto; /* 페이지 카드 내에서 스크롤 가능 */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        
        .page-card-home {
            display: none;
            width: 100%;
            min-height: 100%; /* 페이지 카드의 높이를 부모 요소에 맞추어 설정 */
            /* padding: 20px; */
            background-color: white;
            overflow-y: auto; /* 페이지 카드 내에서 스크롤 가능 */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .page-card.active {
            display: block;
        }

        footer {
            background-color: #007bff;
            color: white;
            text-align: center;
            padding: 10px;
            margin-top: auto;
            width: 100%;
        }

        @media (max-width: 768px) {
            .sidebar-container {
                position: relative;
                width: 100%;
                height: auto;
                padding: 20px;
                overflow: hidden;
                transition: height 0.3s ease;
                z-index: 1000;
            }

            .sidebar-container.hidden {
                display: none;
            }

            .layout {
                flex-direction: column;
            }

            main {
                padding: 20px;
            }
        }
    </style>

    <script>
    
    	//login
    	// 로그인 상태 체크를 위한 변수
	    let isLoggedIn = false;
	
	    // 로그인 폼을 표시하는 함수
	    function showLogin() {
	    	window.location.href = context + '/login';
	    }
	
	    // 로그아웃 처리 함수
	    function logout() {
	    	// 로그아웃 폼을 생성하고 제출하는 함수
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'logout';  // 로그아웃을 처리하는 서버의 URL

            document.body.appendChild(form);  // 폼을 동적으로 문서에 추가
            form.submit();  // 폼 제출
	    }
	
	    // 페이지 로드 시 로그인 상태 초기화
	    /* document.addEventListener('DOMContentLoaded', function () {
	        if (isLoggedIn) {
	            document.getElementById("login-button").style.display = "none";
	            document.getElementById("user-info").style.display = "block";
	        } else {
	        	document.getElementById("login-button").style.display = "block";
	            document.getElementById("user-info").style.display = "none";
	        }
	    }); */

	    function getExtension(url) {
	   		const regex = /\.([0-9a-z]+)(?:[\?#]|$)/i;
	    	const extension = url.match(regex);
	    	if (extension) {
	    	    return extension[1].toLowerCase();
	    	}

	    	return '';
	    }
		
	 	// 페이지를 로드하고 카드를 동적으로 생성하는 함수
	    async function loadPage(pageId, pageUrl, reload = false ) {
	        const mainContent = document.querySelector('main');
	        const existingPage = document.getElementById(pageId);

	        // 이미 로드된 페이지가 있으면 reload 조건에 따라 처리
	        if (existingPage) {
	            if (reload) {
	                existingPage.remove();
	            } else {
	                setActivePage(pageId);
	                return;
	            }
	        }

	        // POST 요청을 보내면서 파라미터를 전달
	        const response = await fetch(pageUrl, {
	            method: getExtension(pageUrl) == 'html' ? 'GET':'POST'
	        });
	        
	        const isOk = response.ok;
	        const data = await response.text();

	        if (!isOk) {
	            openModalFetch(data);  // 오류 발생 시 모달 창 호출
	        } else {
	            const newPageCard = document.createElement('div');
	            newPageCard.id = pageId;
	            newPageCard.classList.add('page-card');
	            newPageCard.innerHTML = data;
	            mainContent.appendChild(newPageCard);
	            setActivePage(pageId);
	            adjustPaddingForHome(pageId);
	            
	            // 페이지 내 script 태그 재실행 처리
	            const scripts = newPageCard.getElementsByTagName('script');
	            Array.from(scripts).forEach((script, i) => {
		            console.log()
	                /* const scriptId = `${pageId}_script_${i}`; */
	                const scriptId = pageId + '_script_' + i;
	                console.log(pageId + '_script_' + i);
	                const existingScript = document.getElementById(scriptId);
	                if (existingScript) existingScript.remove();
	                
	                const newScript = document.createElement('script');
	                newScript.id = scriptId;
	                newScript.text = script.text;
	                document.body.appendChild(newScript);
	            });
	        }
	    }

        
     	// padding을 home 페이지일 때만 0으로 설정하는 함수
        function adjustPaddingForHome(pageId) {
            const pageElement = document.getElementById(pageId);
            
            if (pageId === 'home') {
                pageElement.style.padding = '0px';  // home 페이지의 padding을 0으로 설정
            } else {
                pageElement.style.padding = '20px';  // 다른 페이지의 padding을 기본값으로 설정
            }
        }

        // 특정 페이지를 활성화하는 함수
        function setActivePage(pageId) {
            const pages = document.querySelectorAll('.page-card');
            pages.forEach(page => {
                page.classList.remove('active');
            });

            const targetPage = document.getElementById(pageId);
            if (targetPage) {
                targetPage.classList.add('active');
                localStorage.setItem('currentPage', pageId); // 현재 페이지 상태 저장
            }
        }

        // 서브메뉴 토글 함수
        function toggleSubmenu() {
            const submenu = document.getElementById('submenu');
            submenu.classList.toggle('active');
        }

        // 메뉴 접기/펼치기 버튼 동작
        function toggleSidebar() {
            const sidebar = document.querySelector('.sidebar-container');
            sidebar.classList.toggle('hidden');
            sidebar.classList.toggle('active');
        }

        document.addEventListener('DOMContentLoaded', function () {
           loadPage('home', 'html/home.html'); // 기본 페이지를 로드
        });
        
    	// 팝업 메시지 표시
        function showPopupMessage() {
            document.getElementById('popup-message').style.display = 'block';
            document.getElementById('popup-background').style.display = 'block';
        }

        // 팝업 메시지 숨기기
        function hidePopupMessage() {
            document.getElementById('popup-message').style.display = 'none';
            document.getElementById('popup-background').style.display = 'none';
        }
        
     	// local에서 모달 창 열기
        function openModal(message) {
            document.getElementById('modal-message').innerText = message;
            document.getElementById('modal').style.display = 'block';
            document.getElementById('modal-background').style.display = 'block';
        }
     	
        // 모달 창 닫기
        function closeModal() {
            document.getElementById('modal').style.display = 'none';
            document.getElementById('modal-background').style.display = 'none';
        }
        
     	// 모달 창 닫기
        function openModalFetch(html) {
            document.getElementById('modal').style.display = 'block';
            document.getElementById('modal-content').innerHTML= html;
            document.getElementById('modal-background').style.display = 'block';
        }
     	

     	// 모달 밖 클릭 시 닫기
       /*  window.onclick = function(event) {
            if (event.target == document.getElementById('modal-background')) {
                closeModal();
            }
        } */
    </script>
</head>
<body>

    <header>
	    <button onclick="toggleSidebar()">☰</button>
	    <a href="layout.html" id="site-name">Webapp seok</a> <!-- 사이트 이름을 클릭하면 홈으로 이동 -->
	    <div id="auth-section">
	        <!-- 로그인 또는 사용자 정보가 표시되는 영역 -->
			<c:if test="${empty sessionScope.user}">      
	        	<button id="login-button" onclick="showLogin()">로그인</button>
			</c:if>	  
			<c:if test="${not empty sessionScope.user}"> 
		        <div id="user-info" style="display: ${not empty sessionScope.user ? block : none} ;">
		            <span id="username" title="${sessionScope.user.roles[0]} ${sessionScope.user.email}">${sessionScope.user.name}</span>
		            <button id="logout-button" onclick="logout()">로그아웃</button>
		        </div>
	        </c:if>	 
	    </div>
	</header>

    <div class="layout">
        <div class="sidebar-container hidden">
            <nav>
                <ul>
                    <li><a href="#" onclick="loadPage('home', 'html/home.html')">홈</a></li>
                   <%--  <c:if test="${not empty sessionScope.user }"> --%>
	                    <li><a href="#" onclick="toggleSubmenu()">사용자 관리</a></li>
	                    <ul id="submenu" class="submenu">
	                        <li><a href="#" onclick="loadPage('userList', 'user/userlist')">사용자 목록</a></li>
	                        <li><a href="#" onclick="loadPage('user', 'user/form', true)">사용자 입력</a></li>
	                    </ul>
	                    <li><a href="#" onclick="loadPage('service', 'service.html')">서비스</a></li>
	                    <li><a href="#" onclick="loadPage('contact', 'contact.html')">연락처</a></li>
                  <%--   </c:if> --%>
                </ul>
            </nav>
        </div>

        <main>
            <!-- 페이지 카드들이 동적으로 여기에 생성됩니다 -->
        </main>
    </div>

    <footer>
        © 2024 내 웹사이트 - 모든 권리 보유
    </footer>
    
    <!-- 팝업 배경 -->
    <div id="popup-background" onclick="hidePopupMessage()"></div>

    <!-- 팝업 메시지 창 -->
    <div id="popup-message">
        <h2>알림</h2>
        <p>이것은 팝업 메시지입니다.</p>
        <button onclick="hidePopupMessage()">닫기</button>
    </div>
    
    <!-- 모달 배경 -->
    <div id="modal-background"></div>

    <!-- 모달 창 -->
    <div id="modal">
        <div id="modal-content">
            <h2 id="modal-title">알림</h2>
            <p id="modal-message">이것은 모달 팝업 메시지입니다.</p>
        </div>
        <button onclick="closeModal()">닫기</button>
    </div>

</body>
</html>
