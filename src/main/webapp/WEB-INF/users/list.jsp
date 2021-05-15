<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <title>Users</title>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="resources/css/style.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
  <script src="resources/js/validation.js"></script>
  <script src="resources/js/auth.js"></script>
  <script src="resources/js/user.js"></script>
  <script src="resources/js/phone.js"></script>
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
              <button type="button" class="btn btn-primary shadow-sm mx-3" data-bs-toggle="modal" data-bs-target="#details${user.id}">
                <i class="fas fa-phone"></i>
              </button>
              <button type="button" class="btn btn-primary shadow-sm mx-3 modal-handler"  value="${user.id}">
                <i class="fas fa-edit"></i>
              </button>
              <button type="button" class="btn btn-primary shadow-sm mx-3 delete-button" value="${user.id}">
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
            <input type="text" class="form-control mt-3" placeholder="Name" id="new-name" name="new_name">
            <input type="email" class="form-control mt-3" placeholder="Email" id="new-email" name="new_email">
            <input type="password" class="form-control mt-3" placeholder="Password" id="new-password" name="new_password">
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
            <input type="hidden" id="edit-id" name="edit-id">
            <input type="text" class="form-control mt-3" placeholder="Name" id="edit-name" name="edit_name">
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
                <input type="text" class="form-control phone-number" placeholder="New Phone" id="phone-number-${user.id}" name="phone_number">
              </div>
              <div class="col-auto">
                <button type="button" class="btn btn-primary mb-3 add-phone-button" value="${user.id}">Add Phone</button>
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
                <button type="button" class="btn btn-primary shadow-sm mx-3 col-1 delete-phone" value="${phone.id}-${user.id}">
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
  
</body>

</html>