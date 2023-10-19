<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="recursos/estilos.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo usuario</title>
    </head>
    <body>
    <h1>Nuevo usuario</h1>

    <form action="<%=request.getContextPath()%>/ServletReg" method="post">
        <table style="width:80%">
            
            <tr>
                <td>Nombre</td>
                <td><input type="text" name="nombre"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="text" name="pass"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Grabar"/></td>
            </tr>
        </table>
    </form>
    </body>
</html>
