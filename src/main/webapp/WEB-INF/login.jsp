<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <title>Login</title>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="resources/css/style.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
  <script src="resources/js/validation.js"></script>
  <script src="resources/js/toast.js"></script>
  <script src="resources/js/login.js"></script>
</head>

<body>

  <div class="d-flex justify-content-center align-itens-center my-5">
    <div class="col col-4">
      <div class="card">

        <ul class="nav nav-tabs nav-fill" role="tablist">
          <li class="nav-item" role="presentation">
            <button class="nav-link active h4" data-bs-toggle="tab" data-bs-target="#login">Login</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link h4" data-bs-toggle="tab" data-bs-target="#register">Register</button>
          </li>
        </ul>

        <div class="card-body">

          <div class="tab-content">
            <div class="tab-pane fade show active" id="login" role="tabpanel">
              <form id="login-form">
                <input type="email" class="form-control mt-4" placeholder="Email" id="login-email" name="login_email">
                <input type="password" class="form-control mt-4" placeholder="Password" id="login-password" name="login_password">
                <div class="d-flex flex-row-reverse mt-4">
                  <button type="button" class="btn btn-primary col-4" id="login-button">Sign In</button>
                </div>
              </form>
            </div>

            <div class="tab-pane fade" id="register" role="tabpanel">
              <form id="register-form">
                <input type="text" class="form-control mt-4" placeholder="Name" id="register-name" name="register_name">
                <input type="email" class="form-control mt-4" placeholder="Email" id="register-email" name="register_email">
                <input type="password" class="form-control mt-4" placeholder="Password" id="register-password" name="register_password">
                <div class="d-flex flex-row-reverse mt-4">
                  <button type="button" class="btn btn-primary col-4" id="register-button">Sign Up</button>
                </div>
              </form>
            </div>
          </div>

        </div>
      </div>
    </div>

  </div>

</body>

</html>