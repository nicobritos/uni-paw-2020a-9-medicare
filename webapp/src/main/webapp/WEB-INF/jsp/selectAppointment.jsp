<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../partials/head.jsp" %>
    <link rel="stylesheet" href='<c:url value="/css/selectTurno.css"/>'/>
</head>
<body class="container-fluid d-flex flex-column p-0">
<%@ include file="navbar/navbar.jsp" %>
<div class="container ml-0 mr-0 pr-0 fill-height">
    <div class="row h-100">
        <div class="col-4 h-100 grey-background">
            <div class="row mt-4">
                <div class="col-3 d-flex flex-column justify-content-center">
                    <div class="profile-picture-container">
                        <div style="margin-top: 100%;"></div>
                        <img
                                class="profile-picture rounded-circle"
                                src="<c:url value="/profilePics/${staff.user.profilePicture.id}"/>"
                                alt=""
                        />
                    </div>
                </div>
                <div class="col mr-3">
                    <div class="row mt-2">
                        <h5><c:out value="${staff.firstName} ${staff.surname}"/></h5>
                    </div>
                    <div class="row mt-3 d-flex justify-content-start">
                        <p>
                            <c:forEach items="${staff.staffSpecialties}" var="specialty">
                                <c:out value="${specialty.name} "/>
                            </c:forEach>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row mt-3 pl-4">
                <p><spring:message code="Address"/>: <c:out
                        value="${staff.office.street} - ${staff.office.locality.name}"/></p>
            </div>
            <div class="row pl-4">
                <p><spring:message code="Phones"/>:</p>
            </div>
            <div class="row pl-4">
                <ul>
                    <c:if test="${staff.user.phone != null}">
                        <li><c:out value="${staff.user.phone}"/> (<spring:message code="Personal"/>)</li>
                    </c:if>
                    <c:if test="${staff.office.phone != null}">
                        <li><c:out value="${staff.office.phone} (${staff.office.name})"/></li>
                    </c:if>
                </ul>
            </div>
            <div class="row pl-4">
                <p><spring:message code="Email"/>: ${staff.email}</p>
            </div>
        </div>
        <div class="col ml-5 mt-3 p-0">
            <div class="row">
                <h4><spring:message code="SelectAppointment"/></h4>
            </div>
            <div class="row">
                <div class="col-1 p-0">
                    <button class="btn" id="day-left"><</button>
                </div>
                <div class="d-flex flex-horizontal" id="week-container">
                    <div class="d-flex flex-vertical" id="day-container">

                    </div>
                </div>

                <c:forEach var="i" begin="0" end="6">
                    <div class="col-1 mr-4 p-0">
                            <span class="d-flex flex-column align-items-center">
                                <p class="mb-0"><c:choose>
                                    <c:when test="${monday.plusDays(i).dayOfWeek == 1}"><spring:message code="Monday"/></c:when>
                                    <c:when test="${monday.plusDays(i).dayOfWeek == 2}"><spring:message code="Tuesday"/></c:when>
                                    <c:when test="${monday.plusDays(i).dayOfWeek == 3}"><spring:message
                                            code="Wednesday"/></c:when>
                                    <c:when test="${monday.plusDays(i).dayOfWeek == 4}"><spring:message
                                            code="Thursday"/></c:when>
                                    <c:when test="${monday.plusDays(i).dayOfWeek == 5}"><spring:message code="Friday"/></c:when>
                                    <c:when test="${monday.plusDays(i).dayOfWeek == 6}"><spring:message
                                            code="Saturday"/></c:when>
                                    <c:when test="${monday.plusDays(i).dayOfWeek == 7}"><spring:message code="Sunday"/></c:when>
                                    <c:otherwise><c:out value="${monday.plusDays(i).dayOfWeek}"/></c:otherwise>
                                </c:choose>
                                </p>
                                <!-- day/month -->
                                <p class="my-0"><c:out value="${monday.plusDays(i).dayOfMonth}"/> <spring:message
                                        code="of"/> <c:choose>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 1}"><spring:message
                                            code="January"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 2}"><spring:message
                                            code="February"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 3}"><spring:message code="March"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 4}"><spring:message code="April"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 5}"><spring:message
                                            code="May"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 6}"><spring:message code="June"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 7}"><spring:message code="July"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 8}"><spring:message
                                            code="August"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 9}"><spring:message
                                            code="September"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 10}"><spring:message
                                            code="October"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 11}"><spring:message
                                            code="November"/></c:when>
                                    <c:when test="${monday.plusDays(i).monthOfYear == 12}"><spring:message
                                            code="December"/></c:when>
                                    <c:otherwise><c:out value="${monday.plusDays(i).monthOfYear}"/></c:otherwise>
                                </c:choose>
                                </p>
                            </span>
                        <div class="d-flex flex-column align-content-center">
                            <c:forEach var="timeslot" items="${weekSlots.get(monday.plusDays(i).dayOfWeek)}">
                                <a href="<c:url value="/patient/appointment/${staff.id}/${timeslot.date.year}/${timeslot.date.monthOfYear}/${timeslot.date.dayOfMonth}/${timeslot.date.hourOfDay}/${timeslot.date.minuteOfHour}"/>"
                                   class="btn btn-sm btn-secondary mb-2">
                                    <p class="m-0"><c:if test="${timeslot.date.hourOfDay < 10}">0</c:if><c:out
                                            value="${timeslot.date.hourOfDay}:"/><c:if
                                            test="${timeslot.date.minuteOfHour < 10}">0</c:if><c:out
                                            value="${timeslot.date.minuteOfHour}hs"/></p>
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
                <div class="col-1 p-0 flex-shrink-1">
                    <button id="day-right" class="btn">></button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script src="<c:url value="/js/scripts/SelectAppointment.js"/>"></script>
<script>
    SelectAppointment.init(parseInt(${id}), parseInt(${week}));
</script>
