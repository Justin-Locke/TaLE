import TaLEClient from '../api/TaLEClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";

class Homepage extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['clientLoaded', 'mount'], this);

        this.header = new Header(this.dataStore);

        console.log("Homepage constructor");
    }

    async clientLoaded() {
        const user = await this.client.getIdentity();
        if (user != null) {
            document.getElementById('personalPage').removeAttribute("hidden");
        }
    }

    mount() {
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }



}

const main = async () => {
    const homePage = new Homepage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);