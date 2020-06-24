<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../../partials/head.jsp" %>
    <link rel="stylesheet" href='<c:url value="/css/reservarTurno.css"/>'/>
</head>
<body class="container-fluid p-0 m-0 d-flex flex-column">
<%@ include file="../navbar/navbarLogged.jsp" %>
<div class="container fill-height">
    <div class="row mt-4">
        <c:url value="/patient/appointment/${staffId}/${year}/${month}/${day}/${hour}/${minute}"
               var="createAppointmentUrl"/>
        <form:form modelAttribute="appointmentForm" action="${createAppointmentUrl}" method="post"
                   class="col d-flex flex-column" id="appointment-request-form">
            <h4 class="text-muted"><spring:message code="ScheduleAppointment"/></h4>
            <p class="mt-3 text-muted"><spring:message code="Motive"/></p>
            <spring:message var="motivePlaceholder" code="Motive"/>
            <label for="motive"></label><form:input path="motive" placeholder="${motivePlaceholder}" type="text"
                                                    name="motive" id="motive" class="form-control w-50"/>
            <p class="mt-3 text-muted mb-1"><spring:message code="PersonalData"/></p>
            <div class="container-fluid p-0 mb-1 d-flex flex-row">
                <div class="col px-0">
                    <p><c:out value="${user.get().firstName}"/></p>
                </div>
                <div class="col p-0 ml-2">
                    <p><c:out value="${user.get().surname}"/></p>
                </div>
            </div>
            <spring:message var="phonePlaceholder" code="Phone"/>
            <form:input path="phone" placeholder="${phonePlaceholder}" type="text" name="phone" id="phone"
                        class="form-control w-50 mb-1"/>
            <p><c:out value="${user.get().email}"/></p>
            <spring:message var="commentPlaceholder" code="OptionalComment"/>
            <form:textarea path="comment" placeholder="${commentPlaceholder}" class="form-control mt-3" name="comment"
                           id="comment" cols="30" rows="5"/>
            <button type="button" id="appointment-request-button" class="btn btn-info mt-3 w-100"><spring:message code="ScheduleAppointment"/></button>

            <form:errors path="*" cssClass="mt-4 mb-0 text-danger" element="p"/>
        </form:form>
        <div class="col">
            <div class="container details-container mt-5 p-3 w-75">
                <div class="row justify-content-center">
                    <h4 class="white-text"><spring:message code="AppointmentDetails"/></h4>
                </div>
                <div class="row justify-content-center border-top border-light py-2">
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
                    <div class="col p-0">
                        <p class="m-0 white-text"><c:out value="${staff.user.firstName} ${staff.user.surname}"/></p>
                        <small class="white-text">
                            <c:forEach var="specialty" items="${staff.staffSpecialties}" varStatus="loop">
                                <c:out value="${specialty.name} "/>
                                <c:if test="${!loop.last}">,</c:if>
                            </c:forEach>
                        </small>
                    </div>
                </div>
                <div class="row justify-content-center border-top border-light py-2">
                    <div class="col-3 d-flex align-items-center justify-content-center">
                        <img src='<c:url value="/img/calendarIcon.svg"/>' class="w-75" alt="calendar icon">
                    </div>
                    <div class="col p-0">
                        <p class="m-0 white-text">
                            <c:choose>
                                <c:when test="${date.dayOfWeek == 1}"><spring:message code="Monday" var="vdateDayOfWeek"/></c:when>
                                <c:when test="${date.dayOfWeek == 2}"><spring:message code="Tuesday" var="vdateDayOfWeek"/></c:when>
                                <c:when test="${date.dayOfWeek == 3}"><spring:message code="Wednesday" var="vdateDayOfWeek"/></c:when>
                                <c:when test="${date.dayOfWeek == 4}"><spring:message code="Thursday" var="vdateDayOfWeek"/></c:when>
                                <c:when test="${date.dayOfWeek == 5}"><spring:message code="Friday" var="vdateDayOfWeek"/></c:when>
                                <c:when test="${date.dayOfWeek == 6}"><spring:message code="Saturday" var="vdateDayOfWeek"/></c:when>
                                <c:when test="${date.dayOfWeek == 7}"><spring:message code="Sunday" var="vdateDayOfWeek"/></c:when>
                                <c:otherwise><c:set var="vdateDayOfWeek" value="${date.dayOfWeek}"/></c:otherwise>
                            </c:choose>
                             <c:choose>
                                <c:when test="${date.monthOfYear == 1}"><spring:message code="January" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 2}"><spring:message code="February" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 3}"><spring:message code="March" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 4}"><spring:message code="April" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 5}"><spring:message code="May" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 6}"><spring:message code="June" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 7}"><spring:message code="July" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 8}"><spring:message code="August" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 9}"><spring:message code="September" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 10}"><spring:message code="October" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 11}"><spring:message code="November" var="vdateMonthOfYear"/></c:when>
                                <c:when test="${date.monthOfYear == 12}"><spring:message code="December" var="vdateMonthOfYear"/></c:when>
                                <c:otherwise><c:set value="${date.monthOfYear}" var="vdateMonthOfYear"/></c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${date.hourOfDay < 10}"><c:set var="vdateHourOfDay" value="0${date.hourOfDay}"/></c:when>
                                <c:otherwise><c:set var="vdateHourOfDay" value="${date.hourOfDay}"/></c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${date.minuteOfHour < 10}"><c:set var="vdateMinuteOfHour" value="0${date.minuteOfHour}"/></c:when>
                                <c:otherwise><c:set var="vdateMinuteOfHour" value="${date.minuteOfHour}"/></c:otherwise>
                            </c:choose>
                            <spring:message arguments="${vdateDayOfWeek};${date.dayOfMonth};${vdateMonthOfYear};${vdateHourOfDay};${vdateMinuteOfHour}" argumentSeparator=";" code="dow_dom_moy_hod_moh"/>
                        </p>
                        <a href="<c:url value="/appointment/${staffId}/0"/> "><small class="white-text"><spring:message
                                code="ChangeDate"/></small></a>
                    </div>
                </div>
                <div class="row justify-content-center border-top border-light py-2">
                    <div class="col-3 d-flex align-items-center justify-content-center">
                        <img src='<c:url value="/img/mapIcon.svg"/> ' class="w-75" alt="map icon">
                    </div>
                    <div class="col p-0">
                        <p class="m-0 white-text"><c:out value="${staff.office.street} - ${staff.office.locality.name}"/></p>
                        <a
                                class="link"
                                href="https://www.google.com/maps/search/?api=1&query=${staff.office.locality.name},${staff.office.street}"
                                target="_blank"
                        >
                            <small class="white-text m-0"><spring:message code="SeeInGoogleMaps"/></small>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    let strings = new Array();
    strings['title'] = "<spring:message code='YouAreAboutToScheduleAnAppointment' javaScriptEscape='true' />";
    strings['body'] = "<spring:message code='DoYouWantToContinue' javaScriptEscape='true' />";
    strings['accept'] = "<spring:message code='Accept' javaScriptEscape='true' />";
    strings['cancel'] = "<spring:message code='Cancel' javaScriptEscape='true' />";
</script>
<script src="<c:url value="/js/scripts/AppointmentRequest.js"/>"></script>
<script>
    AppointmentRequest.init();
</script>
</html>
