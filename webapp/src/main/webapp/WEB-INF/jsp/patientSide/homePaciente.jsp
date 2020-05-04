<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file = "../head.jsp" %>
    <link rel="stylesheet" href='<c:url value="/css/patient/homePaciente.css"/>' />
  </head>
  <body>
    <%@ include file="../navbar/navbarLogged.jsp"%>

    <div class="container h-75 w-100 mt-5">
      <div class="row">
        <h4><spring:message code="MyAppointments"/></h4>
      </div>
      <div class="row h-100">
        <div class="col h-100 pl-0 mr-5 w-100">
          <ul class="list-group turno-list mr-2 w-100 h-100 overflow-auto">
            <c:forEach var="appointment" items="${appointments}">
              <li class="list-group-item turno-item mb-3">
                <div class="container">
                  <div class="row">
                    <div class="col-4">
                      <img
                        class="w-100"
                        src="https://fonts.gstatic.com/s/i/materialicons/account_circle/v4/24px.svg"
                        alt=""
                      />
                    </div>
                    <div class="col-7">
                      <div class="row justify-content-start">
                        <h5>${appointment.staff.firstName} ${appointment.staff.surname}</h5>
                      </div>
                      <div class="row">
                        <c:forEach var="specialty" items="${appointment.staff.staffSpecialties}">
                          <p class="m-0">${specialty.name}</p>
                        </c:forEach>
                      </div>
                      <div class="row">
                        <p class="m-0">${appointment.staff.office.street}</p>
                      </div>
                      <div class="row">
                        <p class="m-0"><c:choose>
                            <c:when test="${appointment.fromDate.dayOfWeek == 1}"><spring:message code="Monday"/></c:when>
                            <c:when test="${appointment.fromDate.dayOfWeek == 2}"><spring:message code="Tuesday"/></c:when>
                            <c:when test="${appointment.fromDate.dayOfWeek == 3}"><spring:message code="Wednesday"/></c:when>
                            <c:when test="${appointment.fromDate.dayOfWeek == 4}"><spring:message code="Thursday"/></c:when>
                            <c:when test="${appointment.fromDate.dayOfWeek == 5}"><spring:message code="Friday"/></c:when>
                            <c:when test="${appointment.fromDate.dayOfWeek == 6}"><spring:message code="Saturday"/></c:when>
                            <c:when test="${appointment.fromDate.dayOfWeek == 7}"><spring:message code="Sunday"/></c:when>
                            <c:otherwise>${appointment.fromDate.dayOfWeek}</c:otherwise>
                            </c:choose> ${appointment.fromDate.dayOfMonth} <spring:message code="of"/> <c:choose>
                            <c:when test="${appointment.fromDate.monthOfYear == 1}"><spring:message code="January"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 2}"><spring:message code="February"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 3}"><spring:message code="March"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 4}"><spring:message code="April"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 5}"><spring:message code="May"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 6}"><spring:message code="June"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 7}"><spring:message code="July"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 8}"><spring:message code="August"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 9}"><spring:message code="September"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 10}"><spring:message code="October"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 11}"><spring:message code="November"/></c:when>
                            <c:when test="${appointment.fromDate.monthOfYear == 12}"><spring:message code="December"/></c:when>
                            <c:otherwise>${appointment.fromDate.monthOfYear}</c:otherwise></c:choose>

                          <c:if test="${appointment.fromDate.hourOfDay < 10}">0</c:if>${appointment.fromDate.hourOfDay}:<c:if test="${appointment.fromDate.minuteOfHour < 10}">0</c:if>${appointment.fromDate.minuteOfHour}hs - <c:if test="${appointment.toDate.hourOfDay < 10}">0</c:if>${appointment.toDate.hourOfDay}:<c:if test="${appointment.toDate.minuteOfHour < 10}">0</c:if>${appointment.toDate.minuteOfHour}hs
                      </div>
                    </div>
                    <div class="col-1 justify-content-start">
                        <div class="dropdown">
                            <!-- TODO connect icon -->
                            <img src='<c:url value="/img/moreOptions.svg"/>' class="moreOptionsButton" alt="nore options" data-toggle="dropdown">
                            <div class="dropdown-menu">
                              <%-- TODO add reprogramar --%>
                            <a class="dropdown-item cancelAppointmentBtn" href="<c:url value="/patient/appointment/${appointment.id}"/>"><spring:message code="Cancel"/></a>
                          </div>
                        </div>
                    </div>
                  </div>
                </div>
              </li>
            </c:forEach>
          </ul>
        </div>
        <div class="col">
          <form action="<c:url value="/mediclist/1"/>" class="container p-5 filter-form">
            <div class="row justify-content-start">
              <h3 class="form-title"><spring:message code="SearchMedics"/></h3>
            </div>
            <div class="row justify-content-start my-3">
              <input class="w-100 form-control" type="text" name="name" id="name" placeholder="<spring:message code="NameAndOrSurname"/>">
            </div>
            <div class="row justify-content-start my-3">
              <select name="specialties" class="form-control">
                <option value="-1" disabled selected><spring:message code="Specialty"/></option>
                <option value="-1"><spring:message code="Any"/></option>
                <c:forEach var="specialty" items="${specialties}">
                  <option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
                </c:forEach>
              </select>
            </div>
            <div class="row justify-content-start my-3">
              <select name="localities" class="form-control">
                <option value="-1" disabled selected><spring:message code="Locality"/></option>
                <option value="-1"><spring:message code="Any"/></option>
                <c:forEach var="locality" items="${localities}">
                  <option value="<c:out value="${locality.id}"/>"><c:out value="${locality.name}"/></option>
                </c:forEach>
              </select>
            </div>
            <div class="row justify-content-start my-3">
              <button class="w-100 btn rounded-pill btn-light header-btn-element"><spring:message code="SearchMedics"/></button>
            </div>
          </form>
        </div>
      </div>
    </div>
  <script src="<c:url value="/js/scripts/patient/homePatient.js"/>"></script>
  </body>
</html>