import TaLEClient from '../api/TaLEClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewCity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCityToPage', 'redirectToCreateNewActivity', 'addActivitiesToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addCityToPage);
        this.dataStore.addChangeListener(this.addActivitiesToPage);

        this.header = new Header(this.dataStore);
        console.log("ViewCity constructor");
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const cityId = urlParams.get('cityId');

        const city = await this.client.viewCity(cityId);
        this.dataStore.set('city', city);
        
        const activityIdList = city.activityList;
        
        const allActivities = [];
        for (const activityId of activityIdList) {
            const activity = await this.client.viewActivity(activityId);
            allActivities.push(activity);
        }
        this.dataStore.set('allActivities', allActivities);
        }

        
       
        
        
    

    mount() {
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
        document.getElementById('createNewActivityButton').addEventListener('click', this.redirectToCreateNewActivity);

    }

    addCityToPage() {
        const city = this.dataStore.get('city');
        if (city == null) {
            return;
        }

        document.getElementById('cityName').innerText = city.cityName;
        
        // var listOfActivityIds = city.activityList;
        // console.log(JSON.stringify(listOfActivityIds));
        // let activityListHtml = '';
        // // let activityId;
        // listOfActivityIds.forEach(function(activityId) {
        //     this.client.viewActivity(activityId).then(function)(activity) {
        //         var activityList = document.getElementById('activityList');
        //         var listItem = document.createElement('li');
        //         listItem.textContent = activity.activityName;
        //         activityList.appendChild(listItem);
        //     }
        // })
        // for (activityId of listOfActivityIds) {
        //     activityListHtml += '<div class="activity">' + activityId + '</div>';
        // }
        // document.getElementById('activityList').innerHTML = activityListHtml;
    }

    addActivitiesToPage() {
        const activityList = this.dataStore.get('allActivities');
        if (activityList == null) {
            console.log("ActivityList is null");
            return;
        }

        let activityHtml = '<table><tr><th>Activities</th></tr>';

        for (const activity of activityList) {
            activityHtml += `
            <tr>
                <td>
                    <a href="/viewActivity.html?activityId=${activity.activityId}">${activity.activityName}</a>
                </td>
            </tr>
            `;
        }
        document.getElementById('activityList').innerHTML = activityHtml;
    }



     redirectToCreateNewActivity() {
        const city = this.dataStore.get('city');
        if (city != null) {
            window.location.href = `/createNewActivity.html?cityId=${city.cityId}`;
        }
    }
}

    const main = async () => {
        const viewCity = new ViewCity();
        viewCity.mount();
    };

    window.addEventListener('DOMContentLoaded', main);
