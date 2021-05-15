$(document).ready(function () {

  const loginFormValidation = {
    rules: {
      login_email: {
        email: true,
        required: true,
        maxlength: 64,
      },
      login_password: {
        required: true,
        not_blank: true,
        not_whitespace: true,
        rangelength: [6, 16]
      }
    }
  }

  $("#login-button").on('click', (event) => {
    $("#login-form").validate(loginFormValidation);
    if ($("#login-form").valid()) {
      $.ajax({
        type: 'POST',
        url: "/manager/auth?" + $.param({
          "email": $("#login-email").val(),
          "password": $("#login-password").val(),
        }),
      }).done(() => {
        window.location.assign("/manager/users");
      }).fail((response) => {
        $.snack('error', response.responseJSON.message, 3000);
      });
    }
  });



  const registerFormValidation = {
    rules: {
      register_name: {
        required: true,
        not_blank: true,
        only_alphanumeric_whitespace: true,
        rangelength: [6, 24]
      },
      register_email: {
        email: true,
        required: true,
        maxlength: 64,
      },
      register_password: {
        required: true,
        not_blank: true,
        not_whitespace: true,
        rangelength: [6, 16]
      }
    }
  }

  $("#register-button").on('click', (event) => {
    $("#register-form").validate(registerFormValidation);
    if ($("#register-form").valid()) {
      let name = $("#register-name").val();
      let email = $("#register-email").val();
      let password = $("#register-password").val();
      $.ajax({
        type: 'POST',
        url: "/manager/users?" + $.param({
          "name": name,
          "email": email,
          "password": password,
        }),
      }).done(() => {
        $.snack('success', "sign up successfully", 3000);
        authenticate(email, password);
      }).fail((response) => {
        $.snack('error', response.responseJSON.message, 3000);
      });
    }
  });

  function authenticate(email, password) {
    $.ajax({
      type: 'POST',
      url: "/manager/auth?" + $.param({
        "email": email,
        "password": password,
      }),
    }).done(() => {
      window.location.assign("/manager/users");
    }).fail((response) => {
      $.snack('error', response.responseJSON.message, 3000);
    });
  }

});


