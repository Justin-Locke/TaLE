import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clintLoaded', 'mount', 'addActivityToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addActivityToPage);

        this.header = new Header(this.dataStore);
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');

        const activity = await this.clientLoaded.viewActivity(activityId);
        this.dataStore.set('activity', activity);
    }

    mount() {
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

}

const main = async () => {
    const viewActivity = new ViewActivity();
    viewActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);