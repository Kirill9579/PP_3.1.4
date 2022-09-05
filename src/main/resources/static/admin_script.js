const url = 'http://localhost:8080/api/admin/';
window.addEventListener('DOMContentLoaded', getUsers);
getAuthUser();

// Выводит таблицу всех юзеров
async function getUsers() {
    await fetch(url)
        .then(res => res.json())
        .then(data => data.forEach(user => userToHTML(user)));

    const on = (element, event, selector, handler) => {
        element.addEventListener(event, e => {
            if (e.target.closest(selector)) {
                handler(e);
            };
        });
    };
    
    on(document, 'click', '#btn-edit', e => {
        const fila = e.target.parentNode.parentNode;
        const id =fila.firstElementChild.innerHTML;
        console.log(id);
        modalEdit(id);
    });
    
    on(document, 'click', '#btn-delete', e => {
        const fila = e.target.parentNode.parentNode;
        const id = fila.firstElementChild.innerHTML;
        console.log(id);
        deleteUser(id);
    });
    
};

const userToHTML = ({id, firstName, lastName, age, email, roles}) => {
    const usersList = document.getElementById('listUsers');
    let userRole = '';
    roles.forEach((role) => {
        if (role.name == null) {
            userRole = "empty";
        } else {
            userRole = userRole + role.name.substring(5) + " ";   
        }
        
    });
    usersList.insertAdjacentHTML("beforebegin", `
                                                
                    <td>${id}</td>
                    <td>${firstName}</td>
                    <td>${lastName}</td>
                    <td>${age}</td>
                    <td>${email}</td>
                    <td>${userRole}</td>
                    <td><button type="button" class="btn btn-info" data-toggle="modal"
                        href="#edit-user" id="btn-edit">Edit</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal"
                        href="#delete-user" id="btn-delete">Delete</button>
                    </td>
                `
    );
};

// Модальное окно удаления
function deleteUser(id){
    $.ajax({
        url: `http://localhost:8080/api/admin/${id}`,
        dataType: 'json'
    }).done(function (data) {
        
    document.querySelector('#firstname-delete').value = data.firstName;
    document.querySelector('#lastname-delete').value = data.lastName;
    document.querySelector('#age-delete').value = data.age;
    document.querySelector('#email-delete').value = data.email;
    });

    $('#delete-user-btn').click(async () => {
        await fetch(`http://localhost:8080/api/admin/${id}`, {
            method: 'DELETE'
        })
        .then(res => res.json())
        .then(() => location.reload());
    });
};

// Окно нового пользователя
$('#button_add_user').click(async () => {
    
    let firstName = document.querySelector('#firstname').value;
    let lastName = document.querySelector('#lastname').value;
    let age = document.querySelector('#age').value;
    let email = document.querySelector('#email').value;
    let password = document.querySelector('#password').value;
    let roles = getRoles(Array.from(document.getElementById('role_new_user').selectedOptions).map(r => r.value));

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
    .then(() => location.reload());
});

function getRoles(list) {
    let roles = [];
    if (list.indexOf("USER") >= 0) {
        roles.push({"id": 2, "name": 'ROLE_USER'});
    }
    if (list.indexOf("ADMIN") >= 0) {
        roles.push({"id": 1, "name": 'ROLE_ADMIN'});
    }
    return roles;
}


// Текущий пользователь
async function getAuthUser() {
    const authUser = document.querySelector('#auth_user');

    const res = await fetch('http://localhost:8080/api/auth/').then(res => res.json());
    showAuthUser(res);

    function showAuthUser({id, firstName, lastName, age, email, roles}) {
        const authUser = document.getElementById('auth_user');
        let userRole = '';
        roles.forEach((role) => {
            userRole = userRole + role.name.substring(5) + " "
        });
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
// Модальное окно изменения пользователя
function modalEdit(user_id){
    $.ajax({
        url: `http://localhost:8080/api/admin/${user_id}`,
        dataType: 'json'
    }).done(function (data) {
            console.log(data);
            
            let firstName = document.querySelector('#firstname-edit');
            firstName.value = data.firstName;
            let lastName = document.querySelector('#lastname-edit');
            lastName.value = data.lastName;
            let age = document.querySelector('#age-edit');
            age.value = data.age;
            let email = document.querySelector('#email-edit');
            email.value = data.email;
            let password = document.querySelector('#password-edit');
            password.value = data.password;
            
        
        $('#edit-button').click(async function(){
            let id = data.id;
            firstName = firstName.value;
            lastName = lastName.value;
            age = age.value;
            email = email.value;
            password = password.value;
            let roles = getRoles(Array.from(document.getElementById('edit-roles').selectedOptions).map(r => r.value));

            const user = {id, firstName, lastName, age, email, password, roles};
            console.log(roles);
            console.log(user);

            const updateUser = await fetch('http://localhost:8080/api/admin/', {
                method: 'PATCH', 
                headers: {
                    'Content-Type': 'application/json'
                }, 
                body: JSON.stringify(user)
            })
            .then(res => res.json())
            .then(() => location.reload());
        });
    });
};