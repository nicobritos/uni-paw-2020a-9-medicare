<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
    <head>
        <%@ include file = "head.jsp" %>
    </head>
    <body>
        <%@ include file = "header.jsp" %>
        <%@ include file = "navbar.jsp" %>
        <br>
        <h2>Hello MediCare!</h2>
        <br>
        <div style="overflow-x:auto;">
            <table>
                <tr>
                    <th>Nombre</th>
                    <th>Especialidad</th>
                    <th>Correo electrónico</th>
                    <th>Teléfono</th>
                </tr>
                <c:forEach var="member" items="${staff}">
                    <tr>
                        <td><c:out value="${member.surname}"/>,<c:out value="${member.firstName}"/></td>
                        <td>
                            <c:forEach var="specialty" items="${member.staffSpecialties}">
                                <c:out value="${specialty.name}"/>
                            </c:forEach>
                        </td>
                        <td><c:out value="${member.email}"/></td>
                        <td><c:out value="${member.phone}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>