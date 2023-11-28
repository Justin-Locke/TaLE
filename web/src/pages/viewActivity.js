import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addActivityToPage',
         'redirectToCreateComment', 'addCommentsToPage', 'deleteComment',
         'redirectToEditActivity',
        'redirectToEditComment'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addActivityToPage);
        this.dataStore.addChangeListener(this.addCommentsToPage);

        this.header = new Header(this.dataStore);
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');

        this.dataStore.set('activityId', activityId);
        const activity = await this.client.viewActivity(activityId);
        this.dataStore.set('activity', activity);
        const comments = await this.client.viewCommentsForActivity(activityId);
        console.log(JSON.stringify(comments + " = comments "));
        this.dataStore.set('comments', comments);

        
    }

    mount() {  
        document.getElementById('createCommentButton').addEventListener('click', this.redirectToCreateComment);
        document.getElementById('')

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
        // if (user && user.email != activity.userId) {
        //     document.getElementById('editActivityButton').style.visibility='hidden';
        // }
        if (user && user.email === activity.userId) {
            document.getElementById('editActivityButton').removeAttribute("hidden");
            document.getElementById('editActivityButton').addEventListener('click', () => this.redirectToEditActivity(activityId));
        }

    }

    async addCommentsToPage() {
        const activityId = await this.dataStore.get('activityId');

        const comments = await this.dataStore.get('comments');
        if (comments == null) {
            console.log("Comments are null");
            return;
        }
        console.log(JSON.stringify("Comments =" + comments));
        
        const currentUser = await this.client.getIdentity();


        const commentsContainer = document.getElementById('commentsContainer');

        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.classList.add('comment');

            const titleElement = document.createElement('h3');
            titleElement.textContent = comment.title;
            commentDiv.appendChild(titleElement);

            const messageElement = document.createElement('p');
            messageElement.textContent = comment.message;
            commentDiv.appendChild(messageElement);
            
            // Check if the current user is the author of the comment
            if (currentUser && currentUser.email === comment.userId) {
                const updateButton = document.createElement('button');
                updateButton.textContent = 'Edit';
                updateButton.addEventListener('click', () => this.redirectToEditComment(activityId, comment.commentId));

                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'Delete';
                deleteButton.addEventListener('click', () => this.deleteComment(activityId, comment.commentId));

                commentDiv.appendChild(updateButton);
                commentDiv.appendChild(deleteButton);
            }

            commentsContainer.appendChild(commentDiv);
        });

        // let commentHtml = '<table><tr><th>Comments</th></tr><th>Comment Content</th>';

        // for (const comment of comments) {
        //     commentHtml += `
        //     <tr>
        //         <td>
        //             <a href="/viewComment.html?commentId=${comment.commentId}">${comment.title}</a>
        //         </td>
        //         <tr>
        //             <td> ${comment.message} </td>
        //         </tr>

        //     </tr>
        //     `;
        // }
        // document.getElementById('commentList').innerHTML = commentHtml;
    }
    async deleteComment(acitivityId, commentId) {
        const response = await this.client.deleteComment(acitivityId, commentId);
        if (response != null) {
            location.reload();
        }
    }

    async redirectToEditActivity(acitivityId) {
        const activity = await this.client.viewActivity(acitivityId);
        if (activity != null) {
            window.location.href = `/editActivity.html?activityId=${acitivityId}`;
        }
    }


    redirectToCreateComment() {
        const activity = this.dataStore.get('activity');
        console.log("redirecting");
        if (activity != null) {
            window.location.href = `/createComment.html?activityId=${activity.activityId}`;
        }
    }

    async redirectToEditComment(acitivityId, commentId) {
        const comment = await this.client.viewComment(acitivityId, commentId);
        if (comment != null) {
             window.location.href = `/viewComment.html?activityId=${acitivityId}&commentId=${commentId}`;
        }
    }

}

const main = async () => {
    const viewActivity = new ViewActivity();
    viewActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);