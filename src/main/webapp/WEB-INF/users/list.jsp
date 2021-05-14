<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <title>Users</title>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
  <style>
    .error {
      color: red;
    }
  </style>

</head>

<body>

  <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
    <div class="container d-flex justify-content-end">
      <button id="logout-button" class="btn">Logout</button>
    </div>
  </nav>

  <div class="container-sm">
    <div class="row my-3">
      <div class="col">
        <button class="btn btn-primary shadow-sm" data-bs-toggle="modal" data-bs-target="#create">
          <i class="fas fa-plus"></i> New User
        </button>
      </div>
    </div>

    <table class="table table-bordered">
      <thead>
        <tr>
          <th scope="col">Name</th>
          <th scope="col">E-mail</th>
          <th scope="col">Actions</th>
        </tr>
      </thead>
      <tbody id="users">
        <c:forEach items="${users}" var="user">
          <tr id="${user.id}">
            <td>
              <c:out value="${user.name}" />
            </td>
            <td>
              <c:out value="${user.email}" />
            </td>
            <td>
              <button type="button" class="btn btn-primary shadow-sm mx-3" data-bs-toggle="modal"
                data-bs-target="#details${user.id}">
                <i class="fas fa-phone"></i>
              </button>
              <button type="button" class="btn btn-primary shadow-sm mx-3"
                onClick="editUser(${user.id}, '${user.name}', '${user.email}');">
                <i class="fas fa-edit"></i>
              </button>
              <button type="button" class="btn btn-primary shadow-sm mx-3" onClick='deleteUser(${user.id});'>
                <i class="far fa-trash-alt"></i>
              </button>
            </td>
          </tr>
        </c:forEach>

      </tbody>
    </table>
  </div>

  <div class="modal fade" id="create">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">New User</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form id="create-user-form">
            <input type="text" class="form-control mt-3" placeholder="Name" id="new-name" required minlength="3"
              maxlength="24">
            <input type="email" class="form-control mt-3" placeholder="Email" id="new-email" required maxlength="64">
            <input type="password" class="form-control mt-3" placeholder="Password" id="new-password" required required
              minlength="6" maxlength="16">
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" id="new-user-button">Save User</button>
        </div>
      </div>
    </div>
  </div>


  <div class="modal fade" id="edit">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Edit User</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form id="edit-user-form">
            <input type="hidden" id="edit-id" required>
            <input type="text" class="form-control mt-3" placeholder="Name" id="edit-name" required minlength="3"
              maxlength="24">
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" id="update-user-button">Update User</button>
        </div>
      </div>
    </div>
  </div>


  <c:forEach items="${users}" var="user">
    <div class="modal fade" id="details${user.id}">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Phones -
              <c:out value="${user.name}" />
            </h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form class="row g-3 mt-1" id="add-phone-form-${user.id}">
              <input type="hidden" id="phone-id-${user.id}" value="${user.id}" required>
              <div class="col-auto">
                <select class="form-select" id="phone-type-${user.id}" required>
                  <option selected value="PERSONAL">Personal</option>
                  <option value="RESIDENTIAL">Residential</option>
                </select>
              </div>
              <div class="col-auto">
                <input type="text" class="form-control phone-number" id="phone-number-${user.id}"
                  placeholder="New Phone" required>
              </div>
              <div class="col-auto">
                <button type="button" class="btn btn-primary mb-3" onClick="addPhone(${user.id});">Add Phone</button>
              </div>
            </form>
            <c:forEach items="${user.phones}" var="phone">
              <div class="row mb-3">
                <div class=" col-4">
                  <div>
                    <b>Type:</b>
                    <c:out value="${phone.type}" />
                  </div>
                  <div>
                    <b>Number:</b>
                    <c:out value="${phone.getFormatedNumber()}" />
                  </div>
                </div>
                <button type="button" class="btn btn-primary shadow-sm mx-3 col-1"
                  onClick='deletePhone(${phone.id}, ${user.id});'>
                  <i class="far fa-trash-alt"></i>
                </button>
              </div>
            </c:forEach>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>

  <script>

    $('.phone-number').mask('(00) 00000-0000')

    $("#new-user-button").on('click', (event) => {
      $("#create-user-form").validate();
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


    function editUser(id, name, email) {
      $("#edit-id").val(id);
      $("#edit-name").val(name);
      $("#edit-email").val(email);
      new bootstrap.Modal(document.getElementById('edit')).toggle();
    }

    $("#update-user-button").on('click', (event) => {
      $("#edit-user-form").validate();
      if ($("#create-user-form").valid()) {
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

    function deleteUser(id) {
      $.ajax({
        type: 'DELETE',
        url: "/manager/users?id=" + id,
      }).done(() => {
        location.reload();
      });
    }

    $("#logout-button").on('click', (event) => {
      $.ajax({
        type: 'DELETE',
        url: "/manager/auth",
      }).done(() => {
        window.location.assign("/manager/");
      });
    });

    function addPhone(userID) {

      $("#add-phone-form-" + userID).validate();
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


    }

    function deletePhone(phoneId, userId) {
      $.ajax({
        type: 'DELETE',
        url: "/manager/phones?" + $.param({
          "userId": userId,
          "phoneId": phoneId,
        }),
      }).done(() => {
        location.reload();
      });
    }

  </script>


</body>

</html>