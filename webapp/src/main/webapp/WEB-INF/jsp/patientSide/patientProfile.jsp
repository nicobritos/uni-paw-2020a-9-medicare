<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
  <head>
    <%@ include file = "../head.jsp" %>
    <link rel="stylesheet" href='<c:url value="/css/patient/patientProfile.css"/> ' />
  </head>
  <body class="d-flex flex-column">
    <%@ include file="../navbar/navbarLogged.jsp" %>
    <div class="container flex-fill mx-5 pl-5 mt-3 w-100">
        <div class="row">
            <div class="col-4 align-items-start d-flex flex-column">
                <!-- TODO connect imagen -->
                <div class="picture-container no-select">
                    <img id="profilePic" src="https://fonts.gstatic.com/s/i/materialicons/account_circle/v4/24px.svg" alt="">
                    <div class="picture-overlay d-flex flex-column align-items-center justify-content-end pb-3">
                        <input id="profile-picture-input" style="display: none;" type="file" accept="image/jpeg">
                        <i class="fas fa-pencil-alt"></i>
                    </div>
                </div>
                <!-- TODO Connect-->
<%--                <a class="mt-3" href=""><spring:message code="ChangePassword"/></a>--%>
            </div>
            <div class="col-6">
                <div class="container p-0 pt-4 m-0">
                    <c:url value="/patient/profile" var="patientProfileUrl"/>
                    <form:form modelAttribute="patientProfileForm" action="${patientProfileUrl}" method="post">
                        <div class="row">
                            <div class="col p-0 m-0">
                                <!-- TODO Connect image function-->
                                <h3><spring:message code="Name"/> <label for="firstName" class="toggle-readonly"><img type="button" src='<c:url value="/img/editPencil.svg"/>' alt="editar"/></label></h3>
                                <form:input class="form-control mb-3 w-75" id="firstName" name="firstName" value="${user.get().firstName}" path="firstName" readonly="true"/>
                            </div>
                            <div class="col p-0 m-0">
                                <!-- TODO Connect image function-->
                                <h3><spring:message code="Surname"/> <label for="surname" class="toggle-readonly"><img type="button" src='<c:url value="/img/editPencil.svg"/>' alt="editar"/></label></h3>
                                <form:input class="form-control mb-3 w-75" name="surname" id="surname" value="${user.get().surname}" path="surname" readonly="true"/>
                            </div>
                        </div>
                        <div class="row">
<%--                            <div class="col p-0 m-0">--%>
<%--                                <!-- TODO Connect image function-->--%>
<%--                                <h3><spring:message code="Phone"/><img type="button" src='<c:url value="/img/editPencil.svg"/>' alt="editar"></h3>--%>
<%--                                <!-- TODO Connect telefono-->--%>
<%--                                <c:forEach var="patient" items="${patients}">--%>
<%--                                    <label for="phone"></label><input class="form-control mb-3 w-75" id="phone" name="phone" value="${user.get().phone}" readonly/>--%>
<%--                                </c:forEach>--%>
<%--                            </div>--%>
                            <div class="col p-0 m-0">
                                <!-- TODO Connect image function-->
                                <h3><spring:message code="Email"/> <label for="email" class="toggle-readonly"><img type="button" src='<c:url value="/img/editPencil.svg"/>' alt="editar"/></label></h3>
                                <form:input class="form-control mb-3 w-75" id="email" name="email" value="${user.get().email}" path="email" readonly="true"/>
                            </div>
                        </div>
                      <div class="row">
                        <div class="col p-0 m-0">
                          <h3><spring:message code="Password"/> <label for="password" class="toggle-readonly"><img type="button" src='<c:url value="/img/editPencil.svg"/>' alt="editar"/></label></h3>
                          <form:input type="password" class="form-control mb-3 w-75" id="password" name="password" path="password" readonly="true"/>
                          <label for="password" class="toggle-visibility"><img src='<c:url value="/img/eye.svg"/> ' style="display: none;"><img src='<c:url value="/img/noeye.svg"/>' style="display: none;"></label>
                        </div>
                        <div class="col p-0 m-0" id="repeat-password-container" style="display: none">
                          <h3><spring:message code="RepeatPassword"/></h3>
                          <label for="repeatPassword" class="toggle-readonly">
                              <form:input visible="false" type="password" class="form-control mb-3 w-75" id="repeatPassword" name="repeatPassword" path="repeatPassword" readonly="true"/>
                            <label for="repeatPassword" class="toggle-visibility"><img src='<c:url value="/img/eye.svg"/> ' style="display: none;"><img src='<c:url value="/img/noeye.svg"/>' style="display: none;"></label>
                        </div>
                      </div>
                        <form:errors path="*" cssClass="mt-4 mb-0 text-danger" element="p"/>
                        <div class="form-row justify-content-between align-items-end mt-2">
                            <button type="submit" class="btn btn-info"><spring:message code="ConfirmChanges"/></button>
                        </div>
                    </form:form>
          <div class="col-2">
          </div>
        </div>
      </div>
      </div>
    </div>
    <script src='<c:url value="/js/scripts/patient/patientProfile.js"/> '></script>
  <script>Profile.init()</script>
  </body>
</html>
