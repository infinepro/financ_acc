<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Please sign up!</title>

    <link rel="stylesheet" href="/resources/css/style.css"/>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<body>
<script type="text/javascript">
    function hide(id) {
            document.getElementById(id).style.display = "none";
    }

    function whenLoadHide() {
        let strGET = window.location.search;
        console.log(strGET);
        if (strGET === "?error") {
            hide("suc");
        } else if (strGET === "?logout") {
            hide("wro");
        } else {
            hide("suc");
            hide("wro");
        }
    }
</script>
<div class="alert alert-secondary central-form" role="alert">

    <div class="alert alert-success" role="alert" id="suc">
        You successfully logged out of the system!
    </div>

    <div class="alert alert-danger" role="alert" id="wro">
        Wrong login or password! please, try again!
    </div>

    <script>
        whenLoadHide();
    </script>

    <form method="post" name="auth-form" action="/auth">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label for="InputLogin">Login</label>
            <input name="username" type="text" class="form-control" id="InputLogin" aria-describedby="loginHelp"
                   placeholder="Enter login" size="50">
        </div>

        <div class="form-group">
            <label for="InputPassword">Password</label>
            <input name="password" type="password" class="form-control" id="InputPassword" placeholder="Password"
                   size="50">
        </div>
        <button type="submit" class="btn btn-primary">Sign in!</button>
        <a href="/reg" target="_self">
            <button type="button" class="btn btn-primary register-me">
                Please, register me!
            </button>
        </a>
    </form>
</div>
</body>
</html>