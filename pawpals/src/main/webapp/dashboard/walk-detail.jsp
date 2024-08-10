<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap"
        rel="stylesheet">
    <link
        href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/root.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>PawPals | Walk Details</title>
</head>
<body class="background">
    <jsp:include page="./components/header.jsp" />
    <main>
        <a href="${user.isOwnerMode() ? './owner' : './walker'}" class="back_btn"> < Back to Dashboard</a>
        <section class="container flex space-evenly gap-8">
            <div class="flex-1">
                <header>
                    <h1 class="subtitle">Walk Details</h1>
                    <p>Status: ${walk.getStatus().toString()}</p>
                </header>
                <c:choose>
	                <c:when test="${!action.equals('edit')}">
		                <p>Location: ${walk.getLocation()} on ${walk.getFullDate()}</p>
		                <table class="temptable">
		                    <c:if test="${walk.getWalker() != null}">
		                        <c:if test="${walk.getWalkerId() != user.getUserId()}">
		                            <tr>
		                                <th>Walker</th>
		                                <td>${walk.getWalker().getFirstName()} ${walk.getWalker().getLastName()}</td>
		                            </tr>
		                        </c:if>
		                    </c:if>
		                </table>
		                <table class="temptable">
		                    <tr>
		                        <th>Dog Name(s)</th>
		                        <th>Size</th>
		                        <th>Special Needs</th>
		                    </tr>
		                    <c:forEach var="dog" items="${walkDogs}">
		                        <tr>
		                            <td>${dog.getName()}</td>
		                            <td>${dog.getSize()}</td>
		                            <td>${dog.getSpecialNeeds()}</td>
		                        </tr>
		                    </c:forEach>
		                </table>
		                <c:if test="${user.getUserId() == walk.getOwnerId() && !walk.isFinished() && !walk.beginsSoon()}">
		                	<a href="walkdetails?id=${walk.getWalkId()}&action=edit" class="btn mt-2">Update Walk</a>
		                    <a href="cancel-walk?id=${walk.getWalkId()}" class="btn cancel mt-2 ml-2">Cancel Walk</a>
		                </c:if>
	                </c:when>
	                <c:otherwise>
	                	<jsp:include page="./components/walkform.jsp" />
	                </c:otherwise>
                </c:choose>
            </div>
            <c:choose>
                <c:when test="${user.getUserId() == walk.getOwnerId()}">
                    <c:if test="${walk.getStatus() == 'OWNER_POSTED'}">
                        <div class="flex-1">
                            <h2 class="subtitle">Active Offers</h2>
                            <c:choose>
                                <c:when test="${offers.size() > 0}">
                                    <ul>
                                        <c:forEach var="walkOffer" items="${offers}">
                                            <li class="mt-2">
                                                <div>
                                                    <p>${walkOffer.getWalkOfferUser().getEmail()}</p>
                                                    <p><strong>Comment:</strong> ${walkOffer.getComment()}</p>
                                                </div>
                                                <a href="accept-offer?id=${walk.getWalkId()}&walker=${walkOffer.getWalkOfferUser().getUserId()}" class='btn ml-2'>Select</a>
                                                <a href="reject-offer?id=${walk.getWalkId()}&walker=${walkOffer.getWalkOfferUser().getUserId()}" class='btn ml-2'>Reject</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <p>No offers yet!</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <div class="flex-1">
                        <c:choose>
                            <c:when test="${walk.getStatus() == 'OWNER_POSTED'}">
                                <c:if test="${!offer}">
                                    <form action="${pageContext.request.contextPath}/dashboard/create-offer" method="post" class="comment-form">
                                        <input type="hidden" name="id" value="${walk.getWalkId()}">
                                        <div class="comment-box">
                                            
                                            <textarea id="comment" name="comment" placeholder="Enter your comment here" rows="4" cols="50"></textarea>
                                        </div>
                                        <button type="submit" class="btn mt-2">Offer Service</button>
                                    </form>
                                </c:if>
                                <c:if test="${offer}">
                                    <a href="cancel-offer?id=${walk.getWalkId()}" class="btn cancel">Cancel Offer</a>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${walk.getWalkerId() == user.getUserId()}">
                                    <c:if test="${!walk.isFinished()}">
                                        <a href="cancel-walk-walker?id=${walk.getWalkId()}" class="btn cancel mt-2">Cancel Walk</a>
                                    </c:if>
                                    
                                    <c:if test="${walk.getStatus() == 'WALKER_CHOSEN'}">
                                        <a href="start-walk-walker?id=${walk.getWalkId()}" class="btn cancel">Start Walk</a>
                                    </c:if>
                                    <c:if test="${walk.getStatus() == 'WALKER_STARTED'}">
                                        <a href="complete-walk-walker?id=${walk.getWalkId()}" class="btn cancel">Complete Walk</a>
                                    </c:if>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>
</body>
</html>
