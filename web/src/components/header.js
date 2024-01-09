import TaLEClient from '../api/TaLEClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new TaLEClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        const logoButton = document.createElement('a');
        const logo = document.createElement('img');
        logo.src = '/photos/TaLE (1).png';
        logo.width = 125;
        logo.height = 125;
        const homeButton = document.createElement('a');
        logoButton.classList.add('header_home');
        logoButton.href = '/';
//        homeButton.innerText = 'TaLE \n  (Travel: a Local Experience)';

        const siteTitle = document.createElement('div');
        logoButton.appendChild(logo);
        siteTitle.appendChild(homeButton);
        siteTitle.appendChild(logoButton);


        return siteTitle;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        // const childContent = currentUser
        //     ? this.createLogoutButton(currentUser)
        //     : this.createLoginButton();

        // userInfo.appendChild(childContent);

        return userInfo;
    }

    // createLoginButton() {
    //     return this.createButton('Login', this.client.login);
    // }

    // createLogoutButton(currentUser) {
    //     return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    // }

    // createButton(text, clickHandler) {
    //     const button = document.createElement('a');
    //     button.classList.add('button');
    //     button.href = '#';
    //     button.innerText = text;

    //     button.addEventListener('click', async () => {
    //         await clickHandler();
    //     });

    //     return button;
    // }
}
