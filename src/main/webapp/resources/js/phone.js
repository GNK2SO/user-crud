$(document).ready(function () {

  $('.phone-number').mask('(00) 00000-0000');

  let addPhoneFormValidation = {
    rules: {
      phone_number: {
        required: true,
        maxlength: 15,
        minlength: 15,
      }
    },
    messages: {
      phone_number: {
        maxlength: "Please enter a valid phone number.",
        minlength: "Please enter a valid phone number.",
      }
    }
  }

  $('.add-phone-button').on('click', (event) => {
    let userID =  event.currentTarget.value;
    $("#add-phone-form-" + userID).validate(addPhoneFormValidation);
    if ($("#add-phone-form-" + userID).valid()) {

      let phone = $("#phone-number-" + userID).val()
      let phoneDigits = phone.replace(/\(|\)/g, "");

      let ddd = phoneDigits.split(" ")[0];
      let number = phoneDigits.split(" ")[1];

      $.ajax({
        type: 'POST',
        url: "/manager/phones?" + $.param({
          "id": $("#phone-id-" + userID).val(),
          "type": $("#phone-type-" + userID).val(),
          "ddd": ddd,
          "number": number,
        }),
      }).done(() => {
        $("#phone-number-" + userID).val("")
        location.reload();
      });

    }
  });

  $('.delete-phone').on('click', (event) => {
    let value = event.currentTarget.value;
    $.ajax({
      type: 'DELETE',
      url: "/manager/phones?" + $.param({
        "userId": value.split("-")[1],
        "phoneId": value.split("-")[0],
      }),
    }).done(() => {
      location.reload();
    });
  });

});