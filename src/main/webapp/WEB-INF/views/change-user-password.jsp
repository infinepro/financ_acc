<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Change password</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/resources/css/style.css"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <script src="/resources/js/show-hide-messages.js"></script>
</head>
<body>
<div class="alert alert-secondary central-form" role="alert">

    <div class="alert alert-danger hide-panel" role="alert" id="suc">
        Wrong old password!
    </div>

    <script>
        ifAttributeThenShowElement("change-password=false","suc")
    </script>

    <form method="post" name="changePassword" action="/app/change-password">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label for="InputOldPassword">Old password</label>
            <input name="oldPassword" type="password" class="form-control" id="InputOldPassword" placeholder="Password"
                   placeholder="Enter old password" size="50">
        </div>
        <div class="form-group">
            <label for="InputNewPassword">New password</label>
            <input name="newPassword" type="password" class="form-control" id="InputNewPassword" placeholder="Password"
                   size="50">
        </div>
        <button type="submit" class="btn btn-primary">Shut up and change my password!</button>
    </form>
</div>

</body>
</html>