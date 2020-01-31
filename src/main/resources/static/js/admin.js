$(document).ready(function () {
    var body = $('body');

    body.on('click', '#editSaveUser', function(event){
        let userData = {};
        userData.id = $('#userId').val();
        userData.username = $('#editUserLogin').val();
        userData.password = $('#editUserPassword').val();
        userData.enabled = $('#editUserEnabled').val();
        userData.roles = null;
        $.ajax({
            type : "PUT",
            contentType: "application/json; charset=utf-8",
            url : window.location.origin + "/users",
            data : JSON.stringify(userData),
            success : function(response) {
                alert("Успешно!");
                window.location.reload();
            },
            error: function(response) {
                alert('Ошибка при сохранении');
            },
            complete: function() {
            }
        }).done(function() {
        });
    });

    body.on('click', '#deleteUser', function(event){
        $.ajax({
            type : "DELETE",
            contentType: "application/json; charset=utf-8",
            url : window.location.origin + "/users?userId=" + $('#userId').val(),
            success : function(response) {
                alert("Успешно!");
                window.location.reload();
            },
            error: function(response) {
                alert('Ошибка при удалении');
            },
            complete: function() {
            }
        }).done(function() {
        });
    });

    body.on('click', '#editMessageUser', function(event){
        let messageId = $('#messageId').val();
        let newContent = $('#newContent').val();

        $.ajax({
            type : "PATCH",
            contentType: "application/json; charset=utf-8",
            url : window.location.origin + "/chat/" + messageId + "?newContent=" + newContent,
            success : function(response) {
                alert("Успешно!");
                window.location.reload();
            },
            error: function(response) {
                alert('Ошибка при сохранении');
            },
            complete: function() {
            }
        }).done(function() {
        });
    });

    body.on('click', '#deleteMessage', function(event){
        $.ajax({
            type : "DELETE",
            contentType: "application/json; charset=utf-8",
            url : window.location.origin + "/chat/" + $('#messageId').val(),
            success : function(response) {
                alert("Успешно!");
                window.location.reload();
            },
            error: function(response) {
                alert('Ошибка при удалении');
            },
            complete: function() {
            }
        }).done(function() {
        });
    });

    if ($.fn.dataTable.isDataTable('#usersTable')) {
        usersTable = $('#usersTable').DataTable();
        usersTable.ajax.reload();
    } else {
        usersTable = $('#usersTable').DataTable({
            "cursor": "pointer",
            columns: [
                {"data": "id"},
                {"data": "username"},
                {"data": "password"},
                {"data": "roles"},
                {"data": "enabled"}
            ],
            ajax: {
                url: 'http://' + window.location.host + '/users',
                dataType: 'json',
                contentType: 'application/json',
                type: 'GET',
                dataSrc: '',
            },
            'createdRow': function (row, data, dataIndex, cells) {
                $(cells)[4].innerHTML = data.enabled === true ? 'Активен' : 'Заблокирован';
                $(row).click(function () {
                    $('#userId').val(data.id);
                    $('#editUserLogin').val(data.username);
                    $('#editUserPassword').val(data.password);
                    $('#editUserEnabled').val(data.enabled);
                    $('#editUserModal').modal('show');
                });
            }
        });
    }

    if ($.fn.dataTable.isDataTable('#messagesTable')) {
        messagesTable = $('#messagesTable').DataTable();
        messagesTable.ajax.reload();
    } else {
        messagesTable = $('#messagesTable').DataTable({
            "cursor": "pointer",
            columns: [
                {"data": "id"},
                {"data": "fromUser"},
                {"data": "toUser"},
                {"data": "content"}
            ],
            ajax: {
                url: 'http://' + window.location.host + '/chat/messages',
                dataType: 'json',
                contentType: 'application/json',
                type: 'GET',
                dataSrc: '',
            },
            'createdRow': function (row, data, dataIndex, cells) {
                if (data.toUser === null || data.toUser === undefined || data.toUser === "") {
                    $(cells)[2].innerHTML = "Все";
                }
                $(row).click(function () {
                    $('#messageId').val(data.id);
                    $('#newContent').val(data.content);
                    $('#editMessageModal').modal('show');
                });
            }
        });
    }
});