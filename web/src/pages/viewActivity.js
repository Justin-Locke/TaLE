import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submitNewComment', 'submitUpdatedComment', 'addActivityToPage',
         'redirectToCreateComment', 'addCommentsToPage', 'addCommentToModal', 'deleteComment',
         'redirectToEditActivity', 'redirectToEditComment', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addActivityToPage);
        this.dataStore.addChangeListener(this.addCommentsToPage);
        this.authenticator = new Authenticator();

        this.header = new Header(this.dataStore);
    }

    async clientLoaded() {
        const userLoggedIn = await this.authenticator.isUserLoggedIn();
        const logoutButton = document.getElementById('loginButton')

        if (userLoggedIn) {
            const user = await this.client.getIdentity();
            const personalBttn = document.getElementById('personalPage');
            personalBttn.classList.remove('subnavbtn.hidden');
            personalBttn.classList.add('subnavbtn');
            personalBttn.removeAttribute('hidden');
            logoutButton.innerText = `Logout: ${user.name}`;
            logoutButton.addEventListener('click', this.createLogoutButton(user));
        }
        if (!userLoggedIn) {
            logoutButton.innerText = `Login`;
            logoutButton.addEventListener('click', this.createLoginButton());
        }

        const newCommentButton = document.getElementById('createCommentButton');
        const commentModal = document.getElementById("commentModal");
        const editCommentModal = document.getElementById('editCommentModal');
        const span = document.getElementsByClassName("close")[0];
        newCommentButton.onclick = function() {
            commentModal.style.display = "block";
        }
        span.onclick = function() {
            commentModal.style.display = "none";
            editCommentModal.style.display = "none";
        }

        window.onclick = function(event) {
            if (event.target == commentModal) {
                commentModal.style.display = "none";
            }

            if (event.target == editCommentModal) {
                editCommentModal.style.display = "none";
            }
        }



        
        const urlParams = new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');

        this.dataStore.set('activityId', activityId);
        const activity = await this.client.viewActivity(activityId);
        this.dataStore.set('activity', activity);
        const comments = await this.client.viewCommentsForActivity(activityId);
        this.dataStore.set('comments', comments);
        
    }

    mount() {  
        document.getElementById('create').addEventListener('click', this.submitNewComment);
        document.getElementById('submitUpdatedComment').addEventListener('click', this.submitUpdatedComment);


        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async addActivityToPage() {
        const activity = this.dataStore.get('activity');
        if (activity == null) {
            return;
        }
        const activityId = this.dataStore.get('activityId');

        document.getElementById('activityName').innerText = activity.activityName;
        document.getElementById('description').innerText = activity.description;
        document.getElementById('posterExperience').innerText = activity.posterExperience;

        const user = await this.client.getIdentity();

        if (user && user.email === activity.userId) {
            const editButton = document.getElementById('editActivityButton');
            editButton.removeAttribute("hidden");
            editButton.addEventListener('click', () => this.redirectToEditActivity(activityId));
        }

        if (user != null) {
            document.getElementById('createCommentButton').removeAttribute("hidden");
        }

    }

    async addCommentsToPage() {
        
        const activityId = await this.dataStore.get('activityId');

        const comments = await this.dataStore.get('comments');
        if (comments == null) {
            return;
        }
        
        const currentUser = await this.client.getIdentity();

        const commentsContainer = document.getElementById('commentsContainer');

        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.classList.add('comment');

            const titleElement = document.createElement('h3');
            titleElement.textContent = comment.title;
            commentDiv.appendChild(titleElement);
            const line = document.createElement('hr');
            commentDiv.appendChild(line);

            const messageElement = document.createElement('p');
            messageElement.textContent = comment.message;
            commentDiv.appendChild(messageElement);
            const line2 = document.createElement('hr');
            commentDiv.appendChild(line2);

            
            // Check if the current user is the author of the comment
            if (currentUser && currentUser.email === comment.userId) {
                const buttonGroup = document.createElement('button-group');
                const updateButton = document.createElement('button');
                updateButton.classList.add('editButton');
                updateButton.textContent = 'Edit';
                const editCommentModal = document.getElementById("editCommentModal");
                updateButton.style.background = "none"
                updateButton.style.font
                updateButton.addEventListener('click', () => this.addCommentToModal(activityId, comment.commentId));
                
                updateButton.onclick = () => {
                    editCommentModal.style.display = "block";
                }
                


                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'Delete';
                deleteButton.classList.add('deleteButton');

                deleteButton.addEventListener('click', () => this.deleteComment(activityId, comment.commentId));

                buttonGroup.appendChild(updateButton);
                buttonGroup.appendChild(deleteButton);
                commentDiv.appendChild(buttonGroup);
            }

            commentsContainer.appendChild(commentDiv);
        });

    }

    async submitNewComment(evt) {

        const activityId = this.dataStore.get('activityId');
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Creating..';

        const title = document.getElementById('title').value;
        const message = document.getElementById('message').value;

        const comment = await this.client.createComment(activityId, title, message, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('comment', comment);   
        document.getElementById('commentModal').style.display = "none";
        location.reload();
    }

    async deleteComment(activityId, commentId) {
        const response = await this.client.deleteComment(activityId, commentId);
        if (response != null) {
            location.reload();
        }
    }

    async redirectToEditActivity(activityId) {
        const activity = await this.client.viewActivity(activityId);
        if (activity != null) {
            window.location.href = `/editActivity.html?activityId=${activityId}`;
        }
    }

    async addCommentToModal(activityId, commentId) {
        const comment = await this.client.viewComment(activityId, commentId);
        if (comment != null) {
            document.getElementById('commentTitle').value = comment.title;
            document.getElementById('commentMessage').value = comment.message;
        }
        console.log("Comment = " + comment);
        this.dataStore.set('commentId', commentId);
    }

    async submitUpdatedComment(evt) {
        
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        
        const activityId = this.dataStore.get('activityId');
        console.log(JSON.stringify(activityId + "= activityId"));
        const commentId = this.dataStore.get('commentId');
        
        const createButton = document.getElementById('submitUpdatedComment');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Updating..';

        const title = document.getElementById('commentTitle').value;
        const message = document.getElementById('commentMessage').value;

        const updatedComment = await this.client.editComment(activityId, commentId, title, message, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('updatedComment', updatedComment);
        document.getElementById('editCommentModal').style.display = "none";
        location.reload();

        
    }


    redirectToCreateComment() {
        const activity = this.dataStore.get('activity');
        console.log("redirecting");
        if (activity != null) {
            window.location.href = `/createComment.html?activityId=${activity.activityId}`;
        }
    }

    async redirectToEditComment(activityId, commentId) {
        const comment = await this.client.viewComment(activityId, commentId);
        if (comment != null) {
             window.location.href = `/viewComment.html?activityId=${activityId}&commentId=${commentId}`;
        }
    }

    async loginOrOut() {
        const user = await this.client.getIdentity();
        if (user != null) {
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
    const viewActivity = new ViewActivity();
    viewActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);