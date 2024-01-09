import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';
import NavBar from '../components/navBar';

class PersonalComments extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCommentsToPage', 'redirectToViewActivity'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.navbar = new NavBar();
        this.footer = new Footer();
        this.loadingSpinner = new LoadingSpinner();
        this.authenticator = new Authenticator();
        this.dataStore.addChangeListener(this.addCommentsToPage);
        this.dataStore.addChangeListener(this.loadingSpinner.hideLoadingSpinner);
    }

    async clientLoaded() {
        this.loadingSpinner.showLoadingSpinner("Loading your personal comments..");
        const comments = await this.client.viewPersonalComments();
        this.dataStore.set('comments', comments);        
    }

    mount() {
        
        this.header.addHeaderToPage();
        this.navbar.addNavBarToPage();
        this.footer.addFooterToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }


    addCommentsToPage() {
        const comments = this.dataStore.get('comments');

        const outlineComments = document.createElement('div');

        const commentsContainer = document.getElementById('commentsContainer');

        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.classList.add('personal-comments');

            const commentTitle = document.createElement('h1');
            commentTitle.textContent = comment.title;
            commentTitle.addEventListener('click', () => this.redirectToViewActivity(comment.activityId));
            commentDiv.appendChild(commentTitle);

            const commentMessage = document.createElement('p');
            commentMessage.textContent = comment.message;
            commentDiv.appendChild(commentMessage);

            commentsContainer.appendChild(commentDiv);
        })

        if (commentsContainer.childElementCount === 0) {
            const noCommentsContainer = document.createElement('div');
            
            const message = document.createElement('p');
            message.textContent = "Hmmm... It seems like you haven't made any comments yet."
            noCommentsContainer.appendChild(message);

            const message2 = document.createElement('p');
            message2.textContent = "Wanna try now?"
            noCommentsContainer.appendChild(message2);

            const button = document.createElement('button');
            button.innerText = "Comment"
            button.addEventListener('click', () => {
                button.innerText = "Here. We. GOOO!"
                
            });
            noCommentsContainer.appendChild(button);
            
        }

        console.log("spinner Done");
        this.loadingSpinner.hideLoadingSpinner();

    }

    redirectToViewActivity(activityId) {
        this.loadingSpinner.showLoadingSpinner();
        if (activityId != null) {
            window.location.href = `/viewActivity.html?activityId=${activityId}`;
        }
    }
}

const main = async () => {
    const personalComments = new PersonalComments();
    personalComments.mount();
}

window.addEventListener('DOMContentLoaded', main);