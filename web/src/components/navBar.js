import TaLEClient from "../api/TaLEClient";
import BindingClass from "../util/bindingClass";

export default class LoginButton extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'createloginAndLogOutButton', 'createLoginButton', 'createLogoutButton', 'createButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new TaLEClient();

        
    }

    async createLoginAndLogOutButton() {
        const user = await this.client.getIdentity();
        
        if (user != null) {
            document.getElementById('personalPage').removeAttribute("hidden");
            document.getElementById('loginButton').innerText = `Logout: ${user.name}`;
            document.getElementById('loginButton').addEventListener('click', this.createLogoutButton(user));
        }
        if (user == null) {
            document.getElementById('loginButton').innerText = `Login`;
            document.getElementById('loginButton').addEventListener('click', this.createLoginButton());
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