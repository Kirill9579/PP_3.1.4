const url = 'http://localhost:8080/api/admin/';
const usersList = document.getElementById('listUsers');

window.addEventListener('DOMContentLoaded', getUsers);
getAuthUser();

// Запрос на сервер всех пользователй
async function getUsers() {
    await fetch(url)
        .then(res => res.json())
        .then(data => userToHTML(data));
};

// Вывод таблицы всех пользователей
function userToHTML(users) {
    
    users.forEach((user) => {
        let userRole = '';
        user.roles.forEach((role) => {
            userRole = userRole + role.name.substring(5) + " ";   
        });
        let table = `
                <tr>                                
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${userRole}</td>
                    <td><button type="button" class="btn btn-info" data-toggle="modal"
                        href="#edit-user" id="btn-edit" onclick="modalEdit('${user.id}')">Edit</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal"
                        href="#delete-user" id="btn-delete" onclick="deleteUser(${user.id})">Delete</button>
                    </td>
                </tr>
                `;
        usersList.innerHTML += table;
    });

    //заполнение ролей
    fetch('http://localhost:8080/api/roles/')
        .then(res => res.json())
        .then(data => {
            let listOptionRoles = '';
            data.forEach(role => {
                listOptionRoles += '<option value="' + role.id + '">' + role.name.substring(5) + '</option>';
            });
            document.querySelector('#role_new_user').innerHTML = listOptionRoles;
            $('#role_new_user option[value="2"]').prop('selected', true)
            document.querySelector('#modal-delete-role').innerHTML = listOptionRoles;
            document.querySelector('#edit-roles').innerHTML = listOptionRoles;
        });
    
};


// Модальное окно удаления
function deleteUser(id){

    let user_id = document.querySelector('#user-id-delete');
    let firstName = document.querySelector('#firstname-delete');
    let lastName = document.querySelector('#lastname-delete');
    let age = document.querySelector('#age-delete');
    let email = document.querySelector('#email-delete');

    user_id.value = '';
    firstName.value = '';
    lastName.value = '';
    age.value = '';
    email.value = '';
    fetch(`http://localhost:8080/api/admin/${id}`)
        .then(res => res.json())
        .then(data => {

            user_id.value = data.id;
            firstName.value = data.firstName;
            lastName.value = data.lastName;
            age.value = data.age;
            email.value = data.email;
        });
    
};

// Отправка формы удаления
document.getElementById('delete-user-btn').addEventListener('click', (e) => {
    e.preventDefault();
    let id = document.querySelector('#user-id-delete').value;
    
    fetch(`http://localhost:8080/api/admin/${id}`, {
        method: 'DELETE'
    })
    .then(res => res.json())
    .then(() => {
        document.getElementById('modal-delete-close').click();
        console.log(id);
        usersList.innerHTML = '';
        getUsers();
    });
});

// Окно нового пользователя
$('#button_add_user').click(async (e) => {
    e.preventDefault();

    let roles = []
    const values = $('#role_new_user').val()
    const text = $('#role_new_user option:selected').toArray().map(item => item.text)

    for (let i = 0; i < values.length; i++) {
        roles.push({
            id: values[i],
            name: 'ROLE_' + text[i]
        })
    }


    let firstName = document.querySelector('#firstname').value;
    let lastName = document.querySelector('#lastname').value;
    let age = document.querySelector('#age').value;
    let email = document.querySelector('#email').value;
    let password = document.querySelector('#password').value;
    

    const user = {
        "firstName": firstName,
         "lastName": lastName,
         "age": age, 
         "password": password, 
         "email": email, 
         "roles": roles
    };

    await fetch(url, {
        method: 'POST', 
        headers: {
            'Content-Type': 'application/json'
        }, 
        body: JSON.stringify(user)
    })
    .then(res => res.json())
    .then(() => {
        document.getElementById('nav-home-tab').click();
        usersList.innerHTML = '';
        getUsers();
        const values = document.querySelector('#new_user').querySelectorAll('input').values();
        for (let val of values) {val.value = ''};
    });
});

// Текущий пользователь
async function getAuthUser() {
    const authUser = document.querySelector('#auth_user');

    const res = await fetch('http://localhost:8080/api/auth/').then(res => res.json());
    showAuthUser(res);

    function showAuthUser({id, firstName, lastName, age, email, roles}) {
        const authUser = document.getElementById('auth_user');
        let userRole = '';
        for(let role of roles) {
            userRole = userRole + role.name.substring(5) + " "
        }
        
        if (userRole.indexOf('ADMIN') == -1) {
            $('#admin-tab').toggleClass('active');
            $('#user-tab').toggleClass('active');
            $('#user-view').toggleClass('show active');
            $('#admin-panel').toggleClass('show active');
        }
        authUser.insertAdjacentHTML("beforebegin",
            `
                        <td>${id}</td>
                        <td>${firstName}</td>
                        <td>${lastName}</td>
                        <td>${age}</td>
                        <td>${email}</td>
                        <td>${userRole}</td>
            `
        )
        const navbarTitle = document.getElementById('nav-bar');
        navbarTitle.insertAdjacentHTML("beforebegin",
                `
                <h5 style="color: white"><strong>${email}</strong> with roles: ${userRole}</h5>
                `
        );
    };
}

// Модальное окно редактирования пользователя
function modalEdit(user_id) {
    
    let id = document.querySelector('#user-id-edit');
    let firstName = document.querySelector('#firstname-edit');
    let lastName = document.querySelector('#lastname-edit');
    let age = document.querySelector('#age-edit');
    let email = document.querySelector('#email-edit');
    let password = document.querySelector('#password-edit');

    fetch(`http://localhost:8080/api/admin/${user_id}`)
        .then(res => res.json())
        .then(data => {
            
            id.value = data.id;
            firstName.value = data.firstName;
            lastName.value = data.lastName;
            age.value = data.age;
            email.value = data.email;
            password.value = data.password;

            if (data.roles[0].name === 'ROLE_ADMIN') {
                $('#edit-roles option[value="1"]').prop('selected', true)
            }
            if (data.roles[0].name === 'ROLE_USER') {
                $('#edit-roles option[value="2"]').prop('selected', true)
            }
            if (data.roles.length > 1) {
                $('#edit-roles option').prop('selected', true)
            }
    });
    $('#edit-user').on('hidden.bs.modal', () => {
        $('#edit-roles option').prop('selected', false);
    });    
};

// Отправка формы редактирования
document.querySelector('#edit-button').addEventListener('click', (e) => {
    e.preventDefault();
    let roles = [];

    const values = $('#edit-roles').val()
    const text = $('#edit-roles option:selected').toArray().map(item => item.text)

    for (let i = 0; i < values.length; i++) {
        roles.push({
            id: values[i],
            name: 'ROLE_' + text[i]
        });
    };
    let id = document.querySelector('#user-id-edit').value;
    let firstName = document.querySelector('#firstname-edit').value;
    let lastName = document.querySelector('#lastname-edit').value;
    let age = document.querySelector('#age-edit').value;
    let email = document.querySelector('#email-edit').value;
    let password = document.querySelector('#password-edit').value;

    const user = {
        "id" : id,
        "firstName" : firstName,
        "lastName" : lastName,
        "age" : age, 
        "email" : email, 
        "password" : password, 
        "roles" : roles
    };
    

    fetch('http://localhost:8080/api/admin/', {
            method: 'PATCH', 
            headers: {
                'Content-Type': 'application/json'
            }, 
            body: JSON.stringify(user)
        })
        .then(res => res.json())
        .then((data) => {
            console.log(data.id);
            document.getElementById('modal-edit-close').click();
            usersList.innerHTML = '';
            getUsers();
        });
});
