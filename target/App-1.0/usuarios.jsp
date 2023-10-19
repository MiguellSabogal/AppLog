<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="recursos/estilos.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado de registro</title>
    </head>
    <body>
        Resultado de Registro
        <br>
        <p><%=request.getAttribute("message")%></p>
        <br>
        <br>
        <a href="<%=request.getContextPath()%>/ServletReg">Registrarse</a>
        <br>
        <a href="<%=request.getContextPath()%>/ServletLog"> Volver </a>
    </body>
</html>
 