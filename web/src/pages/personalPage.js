import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class PersonalPage extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addActivitiesToPage', 'addCommentsToPage'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }

    async clientLoaded() {
        const activities = await this.client.viewPersonalActivities();
        this.dataStore.set('activities', activities);
        const comments = await this.client.viewPersonalComments();
        this.dataStore.set('comments', comments);

        
    }

    mount() {
        document.getElementById('myActivityPosts').addEventListener('click', this.addActivitiesToPage);
        document.getElementById('myCommentPosts').addEventListener('click', this.addCommentsToPage);
        
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    addActivitiesToPage() {
        const activities = this.dataStore.get('activities');
        if (activities == null) {
            return;
        }

        const activitiesContainer = document.getElementById('activitiesContainer');

        activities.forEach(activity => {
            const activityDiv = document.createElement('div');
            activityDiv.classList.add('activity');

            const activityName = document.createElement('h3');
            activityName.textContent = activity.activityName;
            activityDiv.appendChild(activityName);
            
            activitiesContainer.appendChild(activityDiv);
            
            
        })
        document.getElementById('myActivityPosts').hidden=true;
        document.getElementById('myCommentPosts').hidden=false;
    }

    addCommentsToPage() {
        const comments = this.dataStore.get('comments');
        if (comments == null) {
            return;
        }

        const commentsContainer = document.getElementById('commentsContainer');

        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.classList.add('comment');

            const commentTitle = document.createElement('h3');
            commentTitle.textContent = comment.title;
            commentDiv.appendChild(commentTitle);

            commentsContainer.appendChild(commentDiv);
        })
        document.getElementById('myCommentPosts').hidden=true;
        document.getElementById('myActivityPosts').hidden=false;

    }

    redirectToViewActivity(activityId) {
        if (activityId != null) {
            window.location.href = '/viewActivity.html?activityId=${activityId}';
        }
    }

    
}

const main = async () => {
    const personalPage = new PersonalPage();
    personalPage.mount();
}

window.addEventListener('DOMContentLoaded', main);