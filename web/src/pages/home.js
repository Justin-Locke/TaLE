import TaLEClient from '../api/TaLEClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import NavBar from '../components/navBar';
import Authenticator from '../api/authenticator';

class Homepage extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['clientLoaded', 'mount', 'loginOrOut'], this);

        this.header = new Header(this.dataStore);
        this.authenticator = new Authenticator();
    }

    async clientLoaded() {
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
        }
        if (!userLoggedIn) {
            const loginButton = document.getElementById('loginButton');
            loginButton.innerText = `Login`;
            loginButton.addEventListener('click', this.createLoginButton());
        }
        
    }

    mount() {
        if ('caches' in window) {
            caches
            .open("my-cache").then(function (cache) {
                cache.match('http://localhost:8000/viewCity.html?cityId=N012012V#')
                .then(function (response) {
                    if (response) {
                        cache.delete('http://localhost:8000/viewCity.html?cityId=N012012V#')
                        .then(function (isDeleted) {
                        if (isDeleted) {
                            window.location.href = `http://localhost:8000/viewCity.html?cityId=N012012V#`;
                            console.log("cache deleted");
                        } else {
                        console.log("Cache is empty");
                        }
                        })
                        .catch(function (error) {
                            console.log("Failed to delete resource from cache");
                        })
                    } else {
                        console.log("Cache is empty");
                    }

                })
                
                
            })
        }
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
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

const main = async () => {
    const homePage = new Homepage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);