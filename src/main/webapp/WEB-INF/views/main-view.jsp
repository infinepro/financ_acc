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

    <script src="/resources/js/main-js.js"></script>

</head>
<body>
<div class="alert alert-secondary central-form" role="alert">

    <div class="alert alert-success hide-panel" role="alert" id="ch-pass">
        Password changed!
    </div>
    <div class="alert alert-success hide-panel" role="alert" id="ch-mail">
        Email changed!
    </div>
    <div class="alert alert-danger hide-panel" role="alert" id="no-ch-pass">
        Wrong password!
    </div>
    <script>
        ifAttributeThenShowElement("change-password=false", "no-ch-pass")
        ifAttributeThenShowElement("change-password=true", "ch-pass");
        ifAttributeThenShowElement("change-email=true", "ch-mail")
        ifAttributeThenShowElement("change-email=false", "no-ch-pass")
    </script>
    <h5 class="title form-header">Use available features!</h5>
    <!-- modal window -->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">

                <div class="modal-body">

                    <%--for change password--%>
                    <form method="post" name="changePassword" action="/app/change-password" id="change-password">
                        <div class="modal-header">
                            <h5 class="modal-title">Change password</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="InputOldPassword">Old password</label>
                            <input name="oldPassword" type="password" class="form-control" id="InputOldPassword"
                                   placeholder="Password"
                                   placeholder="Enter old password" size="50">
                        </div>
                        <div class="form-group">
                            <label for="InputNewPassword">New password</label>
                            <input name="newPassword" type="password" class="form-control" id="InputNewPassword"
                                   placeholder="Password"
                                   size="50">
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Shut up and change my password!</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>

                    </form>

                    <%--for change email--%>
                    <form method="post" name="changePassword" action="/app/change-email" id="change-email">
                        <div class="modal-header">
                            <h5 class="modal-title">Change email</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="InputNewEmail">New Email</label>
                            <input name="newEmail" type="email" class="form-control" id="InputNewEmail"
                                   placeholder="Email"
                                   placeholder="Enter new email" size="50">
                        </div>
                        <div class="form-group">
                            <label for="InputPassword">Password</label>
                            <input name="password" type="password" class="form-control" id="InputPassword"
                                   placeholder="Password"
                                   size="50">
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Shut up and change my email now!</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>

                    </form>

                    <form method="post" name="changePassword" action="/app/delete-profile" id="delete-profile">
                        <div class="modal-header">
                            <h5 class="modal-title">Delete profile</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <br/>
                            <label for="InPassword">Password</label>
                            <input name="password" type="password" class="form-control" id="InPassword"
                                   placeholder="Password"
                                   size="50">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" name="checkbox" value="Yes" class="custom-control-input"
                                       id="customCheck1">
                                <label class="custom-control-label" for="customCheck1">Delete profile? Are you
                                    sure!?</label>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Yea! just do it!</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="btn-group dropright">
        <button type="button" class="mybtn btn btn-secondary">
            User settings
        </button>
        <button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split mybtn-split"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <span class="sr-only">Toggle Dropright</span>
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" data-toggle="modal" data-target="#exampleModal"
               onclick="showThisHideOther('change-password')">Change password</a>

            <a class="dropdown-item" data-toggle="modal" data-target="#exampleModal"
               onclick="showThisHideOther('change-email')">Change email</a>

            <a class="dropdown-item" data-toggle="modal" data-target="#exampleModal"
               onclick="showThisHideOther('delete-profile')">Delete my profile</a>
        </div>
    </div>

    <div class="btn-group dropright">
        <button type="button" class="mybtn btn btn-secondary">
            Account operations
        </button>
        <button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split mybtn-split"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <span class="sr-only">Toggle Dropright</span>
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="#">Change password</a>
            <a class="dropdown-item" href="#">Change email</a>
            <a class="dropdown-item" href="#">Delete my profile</a>
        </div>
    </div>

    <div class="btn-group dropright">
        <button type="button" class="mybtn btn btn-secondary" onClick="document.location = '/app/user-accounts'">
            Accounts and operations with them...
        </button>
        <%-- <button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split mybtn-split"
                 data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
             <span class="sr-only">Toggle Dropright</span>
         </button>--%>
        <%--<div class="dropdown-menu">
            <a class="dropdown-item" href="#">Add new account</a>
            <a class="dropdown-item" href="#">Add new account type</a>
            <a class="dropdown-item" href="#">Delete account</a>
            <a class="dropdown-item" href="#">Delete account type</a>
            <a class="dropdown-item" href="#">Delete account</a>

        </div>--%>
    </div>

    <div class="btn-group dropright">
        <button type="button" class="mybtn btn btn-secondary">
            Transaction operations
        </button>
        <button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split mybtn-split"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <span class="sr-only">Toggle Dropright</span>
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="#">Change password</a>
            <a class="dropdown-item" href="#">Change email</a>
            <a class="dropdown-item" href="#">Delete my profile</a>
        </div>
    </div>
    <div class="btn-group dropright btn-logout">
        <a type="button" href="/logout" class="btn btn-success mybtn" role="button">
            Logout...
        </a>
    </div>
</div>


<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>