<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="recursos/estilos.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        

    </head>
    <body>
        
        <h1>Ingresar</h1>

        <form action="<%=request.getContextPath()%>/ServletLog" method="post">

            <table style="width:50%">
                <tr>
                    <td>Usuario</td>
                    <td><input type="text" name="nUsuario"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Ingresar"/></td>
                </tr>
            </table>
        </form>

        <br>
        <ul>
            <li><a href="<%=request.getContextPath()%>/ServletReg">Crear Usuario</a></li>
        </ul>        

    </body>
</html>
