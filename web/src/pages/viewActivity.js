import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addActivityToPage', 'redirectToCreateComment', 'addCommentsToPage'], this);
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

    addActivityToPage() {
        const activity = this.dataStore.get('activity');
        if (activity == null) {
            return;
        }

        document.getElementById('activityName').innerText = activity.activityName;
        document.getElementById('description').innerText = activity.description;
        document.getElementById('posterExperience').innerText = activity.posterExperience;

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
        console.log(currentUser);
        console.log(JSON.stringify(currentUser));


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
                updateButton.addEventListener('click', )

                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'Delete';
                deleteButton.addEventListener('click', () => this.client.deleteComment(activityId, comment.commentId).then(location.reload()));

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

    redirectToCreateComment() {
        const activity = this.dataStore.get('activity');
        console.log("redirecting");
        if (activity != null) {
            window.location.href = `/createComment.html?activityId=${activity.activityId}`;
        }
    }

    redirectToEditComment() {
        
    }

}

const main = async () => {
    const viewActivity = new ViewActivity();
    viewActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);