<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>
<div class="container">
    <c:forEach var="board" items="${boards.content}">


    <div class="card m-2">
        <div class="card-body">
            <h4 class="card-title">${board.title}</h4>
            <a href="/board/${board.id}" class="btn btn-secondary">상세보기</a>
        </div>
    </div>
    </c:forEach>
    <ul class="pagination justify-content-center">

        <c:choose >
            <c:when test="${boards.first}">
                <li class="page-item disabled" style="display: none"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
            </c:otherwise>
        </c:choose>

        <c:choose >
            <c:when test="${boards.last}">
                <li class="page-item disabled" style="display: none"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
            </c:otherwise>
        </c:choose>

        <!-- test / DummyController list메서드를 잘 보자 !
        http://localhost:8383/dummy/user?page=0 ,1,2 .. 이런식으로 접근해서 찾아보자 Peagable 정보를 직접 봐서 접근해라!  -->
    </ul>


</div>

<br>

<%@ include file="layout/footer.jsp"%>
