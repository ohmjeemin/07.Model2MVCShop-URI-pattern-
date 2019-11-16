<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>�Ǹ� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/purchase/listSale" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">�Ǹ� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>


	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			
			<c:if test="${!empty search.searchCondition}">
				<td align="right">
				<select name="searchCondition" class="ct_input_g" style="width:80px">
					<c:if test="${search.searchCondition==0}">

						<option value="0" selected>�ŷ���ȣ</option>
						<option value="1">��ǰ��</option>
					</c:if>
					<c:if test="${search.searchCondition==1}">
						<option value="0">�ŷ���ȣ</option>
						<option value="1" selected>��ǰ��</option>
					</c:if>
				</select>
						
				<input 	type="text" name="searchKeyword" 
								class="ct_input_g" style="width:200px; height:19px" >
			</td>
			</c:if>
		
		
		<c:if test="${empty search.searchCondition}">
			<td align="right">
					<select name="searchCondition" class="ct_input_g" style="width:80px">
						<option value="0">�ŷ���ȣ</option>
						<option value="1">��ǰ��</option>
					</select>
					
					<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" >
				</td>
			</c:if>
			
			
			<td align="right" width="70">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="17" height="23">
							<img src="/images/ct_btnbg01.gif" width="17" height="23">
						</td>
						<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
							<a href="javascript:fncGetList('1');">�˻�</a>
						</td>
						<td width="14" height="23">
							<img src="/images/ct_btnbg03.gif" width="14" height="23">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			<td colspan="11" >��ü  ${resultPage.totalCount} �Ǽ�, ����  ${resultPage.currentPage} ������</td>
		</tr>
		<tr>
			<td class="ct_list_b" width="100">No</td>
			<td class="ct_line02"></td>
			<td class="ct_list_b" width="150">TranNo</td>
			<td class="ct_line02"></td>
			<td class="ct_list_b" width="150">��ǰ��</td>
			<td class="ct_line02"></td>
			<td class="ct_list_b" width="150">����</td>	
			<td class="ct_line02"></td>
			<td class="ct_list_b" width="150">��ۻ���</td>
			<td class="ct_line02"></td>	
		</tr>
		<tr>
			<td colspan="11" bgcolor="808285" height="1"></td>
		</tr>

		<c:set var="i" value="0"/>
		<c:forEach var="purchase" items="${list}">		
			<c:set var="i" value="${i+1}"/>
	
		<tr class="ct_list_pop">
			<td align="center">${i}</td>
			<td></td>
	
			<td align="left">
				<a href="/purchase/getPurchase?tranNo=${purchase.tranNo}"> ${purchase.tranNo} </a> 
			</td>
			<td></td>
		
		
			<td align="left">
				<a href="/product/getProduct?prodNo=${purchase.purchaseProd.prodNo}">${purchase.purchaseProd.prodName}</a> 
			</td>
			<td></td>
			
			<td align="left">${purchase.purchaseProd.price}</td>
			<td></td>
			
			<td align="left">
			
	
								<c:if test = "${purchase.tranCode.trim()=='0'}"> 
									�Ǹ���
								</c:if>
								<c:if test = "${purchase.tranCode.trim()=='1'}"> 
									���ſϷ� <a href="/purchase/updateTranCodeByProd?prodNo=${purchase.purchaseProd.prodNo}&tranCode=2">����ϱ�</a>
								</c:if>
								<c:if test = "${purchase.tranCode.trim()=='2'}"> 
									�����
								</c:if>
								<c:if test = "${purchase.tranCode.trim()=='3'}"> 
									��ۿϷ�
								</c:if>
							
							
			</td>
			<td></td>
			
			</tr>
		
		</c:forEach>
			<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
		 
	</table>
	
<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		   
				<%-- page���� �±׵� ���ȭ�ؼ� jsp���Ϸ� �������--%>
				<jsp:include page="../common/pageNavigator.jsp"/>	
    	</td>
	</tr>
</table>
<!-- PageNavigation End... -->
</form>
</div>

</body>
</html>