import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submitNewComment', 'submitUpdatedComment', 'submitUpdatedActivity', 'addActivityToPage',
         'redirectToCreateComment', 'addCommentsToPage', 'addCommentToModal', 'deleteComment', 'deleteActivity',
         'redirectToEditActivity', 'redirectToEditComment', 'redirectToViewCity', 'loginOrOut'], this);
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
            
            if (window.innerWidth > 800) {
                navbar.style.display = "flex";
                navbar.style.flexWrap = "nowrap";
            }
            window.addEventListener("resize", function() {
                const navbar = document.getElementById('navbar');
                if (window.innerWidth > 800) {
                    navbar.style.display = "flex";
                    navbar.style.flexWrap = "nowrap";
                } else {
                    navbar.style.display = "block";
                }
            });

            


        }
        if (!userLoggedIn) {
            logoutButton.innerText = `Login`;
            logoutButton.addEventListener('click', this.createLoginButton());
        }

        const newCommentButton = document.getElementById('createCommentButton');
        const commentModal = document.getElementById("commentModal");
        const editCommentModal = document.getElementById('editCommentModal');
        const editActivityButton = document.getElementById('editActivityButton');
        const editActivityModal = document.getElementById('editActivityModal');
        // const deleteActivityButton = document.getElementById('delete-activity-button');
        const deleteActivityModal = document.getElementById('deleteModal');
        const newCommentspan = document.getElementsByClassName("close")[0];
        const editCommentspan = document.getElementsByClassName("close")[1];
        const editActivityspan = document.getElementsByClassName("close")[2];
        const deleteActivityspan = document.getElementsByClassName("warning-sign");
        newCommentButton.onclick = function() {
            commentModal.style.display = "block";
        }
        editActivityButton.onclick = function() {
            editActivityModal.style.display = "block";
        }
        // deleteActivityButton.onclick = function() {
        //     deleteActivityModal.style.display = "block"
        // }
        newCommentspan.onclick = function() {
            commentModal.style.display = "none";
            document.getElementById('title').value = '';
            document.getElementById('message').value = '';
            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = '';
            errorMessageDisplay.classList.add('hidden');
        }

        editCommentspan.onclick = function() {
            editCommentModal.style.display = "none";

        }

        editActivityspan.onclick = function() {
            const errorMessageDisplay = document.getElementById('edit-error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');
            editActivityModal.style.display = "none";
        }

        // deleteActivityspan.onclick = function() {
        //     deleteActivityModal.style.display = "none";
        // }

        window.onclick = function(event) {
            if (event.target == commentModal) {
                commentModal.style.display = "none";
                const errorMessageDisplay = document.getElementById('error-message');
                errorMessageDisplay.innerText = '';
                errorMessageDisplay.classList.add('hidden');
                
            }

            if (event.target == editCommentModal) {
                editCommentModal.style.display = "none";
            }

            if (event.target == editActivityModal) {
                const errorMessageDisplay = document.getElementById('edit-activity-error-message');
                errorMessageDisplay.innerText = ``;
                errorMessageDisplay.classList.add('hidden');
                editActivityModal.style.display = "none";
            }

 
        }



        
        const urlParams = new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');

        this.dataStore.set('activityId', activityId);
        const activity = await this.client.viewActivity(activityId);
        if (activity == null) {
            document.getElementById('activityName').innerText = "DELETED BY ORIGINAL AUTHOR";
            document.getElementById('description').innerText = "DELETED BY ORIGINAL AUTHOR";
            document.getElementById('posterExperience').innerText = "DELETED BY ORIGINAL AUTHOR";
        }
        this.dataStore.set('activity', activity);
        const comments = await this.client.viewCommentsForActivity(activityId);
        this.dataStore.set('comments', comments);
        this.addActivityToModal(activity);
        
    }

    mount() {  
        document.getElementById('create').addEventListener('click', this.submitNewComment);
        document.getElementById('submitUpdatedComment').addEventListener('click', this.submitUpdatedComment);
        document.getElementById('submitUpdatedActivity').addEventListener('click', this.submitUpdatedActivity);
        document.getElementById('message').addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                document.getElementById("create").click();
            }
        })

        
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async addActivityToPage() {
        const activity = this.dataStore.get('activity');
        if (activity == null) {
            return;
        } 

        document.getElementById('activityName').innerText = activity.activityName;
        document.getElementById('description').innerText = activity.description;
        document.getElementById('posterExperience').innerText = activity.posterExperience;
        

        const user = await this.client.getIdentity();

        if (user && user.email === activity.userId) {
            const pageBttn = document.getElementById('editable-activities');
            pageBttn.classList.remove('subnavbtn.hidden');
            pageBttn.classList.add('subnavbtn2');
            pageBttn.removeAttribute('hidden');
            const editButton = document.getElementById('editActivityButton');
            editButton.removeAttribute("hidden");
            const deleteModal = document.getElementById('deleteModal');
            const deleteButton = document.getElementById('delete-activity-button');
            deleteButton.removeAttribute("hidden");
            deleteButton.onclick = function() {
                deleteModal.style.display = "block";
            }
            document.getElementById('verified-delete').addEventListener('click', () => {

            
                this.deleteActivity(activity.activityId);
            });
            document.getElementById('cancel-delete').addEventListener('click', function() {
                deleteModal.style.display = "none";
            })
        }

        if (user != null) {
            document.getElementById('createCommentButton').removeAttribute("hidden");
        }

        if (document.readyState == "complete" && activity == null) {
  
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

            const datePosted = document.createElement('small');
            datePosted.textContent = "Posted on " + comment.datePosted;
            commentDiv.appendChild(datePosted);
            

            const titleElement = document.createElement('h3');
            titleElement.textContent = comment.title;
            
            commentDiv.appendChild(titleElement);
            
            if (comment.edited) {
                const edited = document.createElement('edited');
                edited.textContent = 'edited';
                commentDiv.appendChild(edited);
            }
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
                updateButton.addEventListener('click', () => this.addCommentToModal(comment));

                
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
        if (comment != null) {
            this.dataStore.set('comment', comment);   
            document.getElementById('commentModal').style.display = "none";
            location.reload();
        }

    }

    async deleteComment(activityId, commentId) {
        const response = await this.client.deleteComment(activityId, commentId);
        if (response != null) {
            location.reload();
        }
    }

    async deleteActivity(activityId) {
        const response = await this.client.deleteActivity(activityId);
        if (response != null) {
            this.redirectToViewCity("N012012V");
        }
    }

    async redirectToViewCity(cityId) {
        if (cityId != null) {
            window.location.href = `/viewCity.html?cityId=${cityId}`;
        }
    }

    async redirectToEditActivity(activityId) {
        const activity = await this.client.viewActivity(activityId);
        if (activity != null) {
            window.location.href = `/editActivity.html?activityId=${activityId}`;
        }
    }

    async addCommentToModal(comment) {
        if (comment != null) {
            document.getElementById('commentTitle').value = comment.title;
            document.getElementById('commentMessage').value = comment.message;
            document.getElementById('commentId').value = comment.commentId;
        }
    }

    async addActivityToModal(activity) {
        if (activity != null) {
            document.getElementById('editActivityName').value = activity.activityName;
            document.getElementById('editActivityDescription').value = activity.description;
            document.getElementById('editPosterExperience').value = activity.posterExperience;
        }
    }

    async submitUpdatedComment(evt) {
        
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('edit-error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        
        const activityId = this.dataStore.get('activityId');
        console.log(JSON.stringify(activityId + "= activityId"));
        const commentId = document.getElementById('commentId').value;
        
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
        if (updatedComment != null) {
            document.getElementById('editCommentModal').style.display = "none";
            location.reload();
        }
        
    }

    async submitUpdatedActivity(evt) {

        const errorMessageDisplay = document.getElementById('edit-activity-error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        
        const activityId = this.dataStore.get('activityId');

        const createButton = document.getElementById('submitUpdatedActivity');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Updating..';

        const activityName = document.getElementById('editActivityName').value;
        const description = document.getElementById('editActivityDescription').value;
        const posterExperience = document.getElementById('editPosterExperience').value;

        const updatedActivity = await this.client.editActivity(activityId, activityName, description, posterExperience, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        if (updatedActivity != null) {
            this.dataStore.set('updatedActivity', updatedActivity);
            location.reload();
        }
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