window.addEventListener('DOMContentLoaded', getAuthUser);

async function getAuthUser() {
    const res = await fetch('http://localhost:8080/api/auth/').then(res => res.json());
    showAuthUser(res);
}

function showAuthUser({id, firstName, lastName, age, email, roles}) {
    const authUser = document.getElementById('tbody-user');
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
    )};