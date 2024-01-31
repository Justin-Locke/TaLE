import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';
import NavBar from '../components/navBar';

class ViewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submitNewComment', 'submitUpdatedComment', 'submitUpdatedActivity', 'addActivityToPage',
         'addCommentsToPage', 'addCommentToModal', 'deleteComment', 'deleteActivity',
         'redirectToViewCity'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addActivityToPage);
        this.dataStore.addChangeListener(this.addCommentsToPage);
        this.authenticator = new Authenticator();
        this.loadingSpinner = new LoadingSpinner();
        this.header = new Header(this.dataStore);
        this.navbar = new NavBar();
        this.footer = new Footer();
    }

    async clientLoaded() {

        const newCommentButton = document.getElementById('createCommentButton');
        const commentModal = document.getElementById("commentModal");
        const editCommentModal = document.getElementById('editCommentModal');
        const editActivityButton = document.getElementById('editActivityButton');
        const editActivityModal = document.getElementById('editActivityModal');
        const newCommentspan = document.getElementsByClassName("close")[0];
        const editCommentspan = document.getElementsByClassName("close")[1];
        const editActivityspan = document.getElementsByClassName("close")[2];
        newCommentButton.onclick = function() {
            commentModal.style.display = "block";
        }
        editActivityButton.onclick = function() {
            editActivityModal.style.display = "block";
        }

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
        this.navbar.addNavbarWithExtraButtonsResizable();
        this.footer.addFooterToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async addActivityToPage() {
        this.loadingSpinner.showLoadingSpinner("Loading this activity...");
        const activity = this.dataStore.get('activity');
        if (activity == null) {
            return;
        } 
        //Add Activity to HTML Elements//
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
            deleteButton.addEventListener('click', () => {
                deleteModal.style.display = "block";
                const verifyDelete = document.getElementById('verified-delete');
                verifyDelete.addEventListener('click', () => {
                verifyDelete.innerText = "DELETING"
                this.deleteActivity(activity.activityId);
            });
            const verifyCancel = document.getElementById('cancel-delete');
            verifyCancel.addEventListener('click', function() {
                verifyCancel.innerText = "CANCELING";
                deleteModal.style.display = "none";
            })
            })
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
            //Create Comment div//
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
            
            //DeleteModal//
            const deleteModal = document.getElementById('deleteModal');

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
                deleteButton.addEventListener('click', () => {
                    deleteModal.style.display = "block";
                    const verifyDelete = document.getElementById('verified-delete');
                    verifyDelete.addEventListener('click', () => {
                    verifyDelete.innerText = "DELETING";
                    this.deleteComment(activityId, comment.commentId);
                });
                    const verifyCancel = document.getElementById('cancel-delete');
                    verifyCancel.addEventListener('click', function() {
                    verifyCancel.innerText = "CANCELING";
                    deleteModal.style.display = "none";
                })
                })

                buttonGroup.appendChild(updateButton);
                buttonGroup.appendChild(deleteButton);
                commentDiv.appendChild(buttonGroup);
                
            }
            //Add Comment to commentDiv//
            commentsContainer.appendChild(commentDiv);
        });

        this.loadingSpinner.hideLoadingSpinner();

    }

    async submitNewComment(evt) {
        this.loadingSpinner.showLoadingSpinner("Making a real post with your comment")
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
            this.loadingSpinner.hideLoadingSpinner();
        });
        if (comment != null) {
            this.dataStore.set('comment', comment);   
            document.getElementById('commentModal').style.display = "none";
            location.reload();
        }

    }

    async deleteComment(activityId, commentId) {
        this.loadingSpinner.showLoadingSpinner();
        const response = await this.client.deleteComment(activityId, commentId);
        if (response != null) {
            location.reload();
        }
    }

    async deleteActivity(activityId) {
        this.loadingSpinner.showLoadingSpinner();
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

        const activityName = document.getElementById('editActivityName').value.trim();
        const description = document.getElementById('editActivityDescription').value.trim();
        const posterExperience = document.getElementById('editPosterExperience').value.trim();

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

}

const main = async () => {
    const viewActivity = new ViewActivity();
    viewActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);