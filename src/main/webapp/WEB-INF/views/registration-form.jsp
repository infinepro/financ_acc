
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Please sign up!</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/resources/css/style.css"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">



</head>
<body>
<div class="alert alert-secondary central-form" role="alert">
    <h5 class="title form-header">Registration</h5>
    <form method="post" name="registration" action="/reg">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label for="InputLogin">Login</label>
            <input name="username" type="text" class="form-control" id="InputLogin" aria-describedby="loginHelp"
                   placeholder="Enter login" size="50">
        </div>
        <div class="form-group">
            <label for="InputEmail">Email address</label>
            <input name="email" type="email" class="form-control" id="InputEmail" aria-describedby="emailHelp"
                   placeholder="Enter email" size="50">
        </div>
        <div class="form-group">
            <label for="InputPassword">Password</label>
            <input name="password" type="password" class="form-control" id="InputPassword" placeholder="Password" size="50">
        </div>
        <button type="submit" class="btn btn-primary">Sign up!</button>
    </form>
</div>

</body>
</html>