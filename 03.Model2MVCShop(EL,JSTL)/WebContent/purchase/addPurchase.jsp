<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
	<%
	User user=(User)request.getAttribute("user");
	Product product=(Product)request.getAttribute("product");
	Purchase purchase=(Purchase)request.getAttribute("purchase");
--%>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td>${product.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td>${user.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td>
		
<%-- 	<%if(purchase.getPaymentOption().equals("1")){ %>
		���ݱ���
		<%}else if(purchase.getPaymentOption().equals("2")){ %>
		�ſ뱸��
		<%} %>
--%>

		<c:if test="${purchase.paymentOption=='1'}">
		���ݱ���
		</c:if>
		
		<c:if test="${purchase.paymentOption=='2'}">
		�ſ뱸��
		</c:if>
				
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td>${user.userName}</td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td>${user.phone}</td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td>${user.addr}</td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td>${purchase.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td>${purchase.divyDate}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>