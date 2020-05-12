<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <%@ include file="../head.jsp" %>
    <link rel="stylesheet" href='<c:url value="/css/authentication/register.css"/> '>
</head>
<body class="container-fluid w-100 m-0 p-0 d-flex flex-column justify-content-center align-items-center">
    <c:url value="/login" var="loginUrl"/>
    <c:url value="/signup/staff" var="signupUrl"/>
    <form:form modelAttribute="signupForm" class="register-form border p-5 rounded" action="${signupUrl}" method="POST"
               enctype="application/x-www-form-urlencoded">
        <div class="row">
            <h6>Medicare <img src='<c:url value="/img/logo.svg"/>' id="logo" alt="logo"/></h6>
        </div>
        <div class="row justify-content-start">
            <h1 class="register-form-title"><spring:message code="CreateAccount"/></h1>
        </div>
        <div class="form-group row">
            <div class="col">
                <label for="first_name"><spring:message code="Name"/></label>
            </div>
            <div class="col-8">
                <form:input path="firstName" class="form-control" type="text" name="firstName" id="first_name"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <label for="surname"><spring:message code="Surname"/></label>
            </div>
            <div class="col-8">
                <form:input path="surname" class="form-control" type="text" name="surname" id="surname"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <label for="medicare_email"><spring:message code="Email"/></label>
            </div>
            <div class="col-8">
                <form:input path="email" class="form-control" type="email" name="medicare_email" id="medicare_email"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <label for="medicare_password"><spring:message code="Password"/></label>
            </div>
            <div class="col-8">
                <form:input path="password" class="form-control pr-5" type="password" name="medicare_password" id="medicare_password"/>
                <!-- For this to work for must be the id of the password input -->
                <label for="medicare_password" class="toggle-visibility"><img src='<c:url value="/img/eye.svg"/> ' alt="not visible password"><img
                        src='<c:url value="/img/noeye.svg"/>' style="display: none;" alt="visible password"></label>
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <label for="medicare_repeatPassword"><spring:message code="RepeatPassword"/></label>
            </div>
            <div class="col-8">
                <form:input path="repeatPassword" class="form-control pr-5" type="password" name="medicare_repeatPassword"
                            id="medicare_repeatPassword"/>
                <!-- For this to work for must be the id of the password input -->
                <label for="medicare_repeatPassword" class="toggle-visibility"><img src='<c:url value="/img/eye.svg"/> ' alt="not visible password"><img
                        src='<c:url value="/img/noeye.svg"/> ' style="display: none;" alt="visible password"></label>
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <label for="country"><spring:message code="Country"/></label>
            </div>
            <div class="col-8">
                <form:select cssClass="form-control" cssStyle="width: 100%;" path="countryId" items="${countryMap}" id="country"/>
            </div>
        </div>
        <div class="form-group row" id="province-container">
            <div class="col">
                <label for="province"><spring:message code="Province"/></label>
            </div>
            <div class="col-8">
                <form:select cssClass="form-control" cssStyle="width: 100%;" path="provinceId" id="province"/>
            </div>
        </div>
        <div class="form-group row" id="locality-container">
            <div class="col">
                <label for="locality"><spring:message code="Locality"/></label>
            </div>
            <div class="col-8">
                <form:select cssClass="form-control" cssStyle="width: 100%;" path="localityId" id="locality"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <label for="address"><spring:message code="Address"/></label>
            </div>
            <div class="col-8">
                <form:input path="address" class="form-control" type="text" name="address" id="address"/>
            </div>
        </div>
        <div class="form-row justify-content-between align-items-end mt-2">
            <a class="form-link" href="${loginUrl}"><spring:message code="Login"/></a>
            <button type="submit" class="btn btn-primary"><spring:message code="Confirm"/></button>
        </div>

        <form:errors path="*" cssClass="mt-4 mb-0 text-danger" element="p"/>
    </form:form>
<script src='<c:url value="/js/scripts/authentication/register.js"/> '></script>
<script>
    $(document).ready(() => {
        Register.initStaff();
    })
</script>
</body>
</html>
