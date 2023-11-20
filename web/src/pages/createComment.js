import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class CreateComment extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewActivity'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }

    async clientLoaded() {
        const urlParams =  new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');
        this.dataStore.set('activityId', activityId);
    }

    mount() {
        document.getElementById('create').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async submit(evt) {
        const acitivityId = this.dataStore.get('activityId');
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Creating..';

        const title = document.getElementById('title').value;
        const message = document.getElementById('message').value;

        const comment = await this.client.createComment(acitivityId, title, message, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('comment', comment);
        this.redirectToViewActivity();
        
    }

    redirectToViewActivity() {
        console.log("redirecting now");
        const activityId = this.dataStore.get('activityId');
        if (activityId != null) {
            console.log("activiy is not null");
            window.location.href = `/viewActivity.html?activityId=${activityId}`;
        }
        console.log("redirect finished");
        

    }
}

const main = async () => {
    const createComment = new CreateComment();
    createComment.mount();
};

window.addEventListener('DOMContentLoaded', main);