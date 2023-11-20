import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addActivityToPage', 'redirectToCreateComment'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addActivityToPage);

        this.header = new Header(this.dataStore);
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');

        const activity = await this.client.viewActivity(activityId);
        this.dataStore.set('activity', activity);
    }

    mount() {  
        document.getElementById('createCommentButton').addEventListener('click', this.redirectToCreateComment);

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

    redirectToCreateComment() {
        const activity = this.dataStore.get('activity');
        console.log("redirecting");
        if (activity != null) {
            window.location.href = `/createComment.html?activityId=${activity.activityId}`;
        }
    }

}

const main = async () => {
    const viewActivity = new ViewActivity();
    viewActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);