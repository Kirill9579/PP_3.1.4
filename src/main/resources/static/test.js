const url = 'http://localhost:8080/api/admin/';
window.addEventListener('DOMContentLoaded', getUsers);
getAuthUser();

async function getUsers() {
    const request = new XMLHttpRequest();
    request.open("GET", url);
    request.send();
    console.log(JSON.parse(request.response));
    
    const res = await fetch(url).then(res => res.json());
    res.forEach(user => userToHTML(user));
};

const request = new XMLHttpRequest();
request.open("GET", url);
request.send();
console.log(JSON.parse(request.response));


function userToHTML({id, firstName, lastName, age, email, roles}){
    const usersList = document.getElementById('listUsers');
    let userRole = '';
    roles.forEach((role) => {
        userRole = userRole + role.name.substring(5) + " "
    });
    usersList.insertAdjacentHTML("beforebegin", `
    
        <td>${id}</td>
        <td>${firstName}</td>
        <td>${lastName}</td>
        <td>${age}</td>
        <td>${email}</td>
        <td>${userRole}</td>
        <td>
            <button type="button" class="btn btn-info" data-toggle="modal"
            data-target="#edit_user">
                Edit
            </button>
        </td>
        <td>
            <button type="button" class="btn btn-danger" data-toggle="modal"
            data-target="#delete_user">
            Delete
            </button>
        </td>

    `)
};

document.querySelector('#button_add_user').addEventListener('click', async () => {
    let firstName = document.querySelector('#firstname').value;
    let lastName = document.querySelector('#lastname').value;
    let age = document.querySelector('#age').value;
    let email = document.querySelector('#email').value;
    let password = document.querySelector('#password').value;
    let roles = getRoles(Array.from(document.getElementById('role_new_user').selectedOptions).map(r => r.value));

    const user = {
        "firstName": firstName, "lastName": lastName, "age": age, "password": password, "email": email, "roles": roles
    };


    const sendUser = await fetch(url, {
        method: 'POST', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify(user)
    })
    const request = await sendUser.json();
    console.log(request);

});

function getRoles(list) {
    let roles = [];
    if (list.indexOf("USER") >= 0) {
        roles.push({"id": 2});
    }
    if (list.indexOf("ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    return roles;
}



async function getAuthUser() {
    const authUser = document.querySelector('#auth_user');

    const res = await fetch('http://localhost:8080/api/auth/').then(res => res.json());
    showAuthUser(res);


}

function showAuthUser({id, firstName, lastName, age, email, roles}) {
    const authUser = document.getElementById('auth_user');
    let userRole = '';
    roles.forEach((role) => {
        userRole = userRole + role.name.substring(5) + " "
    });
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
    )
};