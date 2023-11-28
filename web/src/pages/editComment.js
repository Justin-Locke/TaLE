import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class EditComment extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewActivity', 'addCommentToPage'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.addCommentToPage);
    }

    async clientLoaded() {
        const urlParams =  new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');
        this.dataStore.set('activityId', activityId);
        console.log(JSON.stringify("ActivityId = " + activityId));
        const commentId = urlParams.get('commentId');
        this.dataStore.set('commentId', commentId);
        console.log(JSON.stringify("Comment Id = " + commentId));
        const comment = await this.client.viewComment(activityId, commentId);
        this.dataStore.set('comment', comment);
       
    }

    mount() {
        document.getElementById('submitUpdatedComment').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async submit(evt) {
        
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        
        const activityId = this.dataStore.get('activityId');
        console.log(JSON.stringify(activityId + "= activityId"));
        const commentId = this.dataStore.get('commentId');
        
        const createButton = document.getElementById('submitUpdatedComment');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Creating..';

        const title = document.getElementById('commentTitle').value;
        const message = document.getElementById('commentMessage').value;

        const updatedComment = await this.client.editComment(activityId, commentId, title, message, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('updatedComment', updatedComment);
        this.redirectToViewActivity();
        
    }

    // addCommentToPage() {
    //     const comment = this.dataStore.get('comment');
    //     document.getElementById('commentTitle').value = comment.title;
    //     document.getElementById('commentMessage').value = comment.message;
    // }

    redirectToViewActivity() {
        console.log("redirecting now");
        const activityId = this.dataStore.get('activityId');
        if (activityId != null) {
            console.log("activiy is not null");
            window.location.href = `/viewActivity.html?activityId=${activityId}`;
        }
        console.log("redirect finished");
        

    }

    addCommentToPage() {
        const comment = this.dataStore.get('comment');
        if (comment != null) {
            document.getElementById('commentTitle').value = comment.title;
            document.getElementById('commentMessage').value = comment.message;
        }
    }
}

const main = async () => {
    const editComment = new EditComment();
    editComment.mount();
};

window.addEventListener('DOMContentLoaded', main);
