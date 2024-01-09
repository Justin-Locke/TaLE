import TaLEClient from "../api/TaLEClient";
import Authenticator from "../api/authenticator";

export default class NavBar {
    constructor() {
        this.authenticator = new Authenticator();
        this.client = new TaLEClient();
    }
    
    async addNavBarToPage() {
        const userLoggedIn = await this.authenticator.isUserLoggedIn();
        
        if (userLoggedIn) {
            const user = await this.client.getIdentity();
            const personalBttn = document.getElementById('personalPage');
            personalBttn.classList.remove('subnavbtn.hidden');
            personalBttn.classList.add('subnavbtn');
            personalBttn.removeAttribute('hidden');
            const myActivitiesButton = document.getElementById('my-activities-button');
            const myCommentsButton = document.getElementById("my-comments-button");
            myActivitiesButton.classList.add('my-activities-button');
            myCommentsButton.classList.add('my-comments-button');
            const logoutButton = document.getElementById('loginButton');
            logoutButton.innerText = `Logout: ${user.name}`;
            logoutButton.addEventListener('click', this.createLogoutButton(user));
            if(document.getElementById('createNewActivityButton')) {
                document.getElementById('createNewActivityButton').removeAttribute("hidden");
            }

        }
        if (!userLoggedIn) {
            const loginButton = document.getElementById('loginButton');
            loginButton.innerText = `Login`;
            loginButton.addEventListener('click', this.createLoginButton());
        }
    }

    async addNavbarWithExtraButtonsResizable() {
        const userLoggedIn = await this.authenticator.isUserLoggedIn();

        if (userLoggedIn) {
            const user = await this.client.getIdentity();
            const personalBttn = document.getElementById('personalPage');
            personalBttn.classList.remove('subnavbtn.hidden');
            personalBttn.classList.add('subnavbtn');
            personalBttn.removeAttribute('hidden');
            
            const logoutButton = document.getElementById('loginButton');
            logoutButton.innerText = `Logout: ${user.name}`;
            logoutButton.addEventListener('click', this.createLogoutButton(user));
            
            const navbar = document.getElementById('navbar');

            if (window.innerWidth > 800) {
                navbar.style.display = "flex";
                navbar.style.flexWrap = "nowrap";
            }
            window.addEventListener("resize", function() {
                if (window.innerWidth > 800) {
                    navbar.style.display = "flex";
                    navbar.style.flexWrap = "nowrap";
                } else {
                    navbar.style.display = "block";
                }
            });

        }
        if (!userLoggedIn) {
            const loginButton = document.getElementById('loginButton');
            loginButton.innerText = `Login`;
            loginButton.addEventListener('click', this.createLoginButton());
        }

    }

    addExtraActivitySubnav(activityId) {
        
    }

    async loginOrOut() {
        const userLoggedIn = await this.authenticator.isUserLoggedIn();
        if (userLoggedIn) {
            return this.client.logout;
        } else {
            return this.client.login;
        }

    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.getElementById('loginButton');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;

    }
}