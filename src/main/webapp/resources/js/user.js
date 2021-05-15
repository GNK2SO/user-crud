
$(document).ready(function () {

  const createFormValidation = {
    rules: {
      new_name: {
        required: true,
        not_blank: true,
        only_alphanumeric_whitespace: true,
        rangelength: [6, 24]
      },
      new_email: {
        email: true,
        required: true,
        maxlength: 64,
      },
      new_password: {
        required: true,
        not_blank: true,
        not_whitespace: true,
        rangelength: [6, 16]
      }
    }
  }

  $("#new-user-button").on('click', (event) => {
    $("#create-user-form").validate(createFormValidation);
    if ($("#create-user-form").valid()) {
      $.ajax({
        type: 'POST',
        url: "/manager/users?" + $.param({
          "name": $("#new-name").val(),
          "email": $("#new-email").val(),
          "password": $("#new-password").val(),
        }),
      }).done(() => {
        location.reload();
      });
    }
  });

  $('.modal-handler').on('click', (event) => {
    $("#edit-id").val(event.currentTarget.value);
    new bootstrap.Modal(document.getElementById('edit')).toggle();
  });

  const editFormValidation = {
    rules: {
      edit_id: {
        required: true,
        only_digit: true
      },
      edit_name: {
        required: true,
        not_blank: true,
        only_alphanumeric_whitespace: true,
        rangelength: [6, 24]
      },
    }
  }

  $("#update-user-button").on('click', (event) => {
    $("#edit-user-form").validate(editFormValidation);
    if ($("#edit-user-form").valid()) {
      $.ajax({
        type: 'PUT',
        url: "/manager/users?" + $.param({
          "id": $("#edit-id").val(),
          "name": $("#edit-name").val(),
        }),
      }).done(() => {
        location.reload();
      });
    }
  });

  $(".delete-button").on('click', (event) => {
    $.ajax({
      type: 'DELETE',
      url: "/manager/users?id=" + event.currentTarget.value,
    }).done(() => {
      location.reload();
    });
  });

});
