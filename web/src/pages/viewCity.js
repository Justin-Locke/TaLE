import TaLEClient from '../api/TaLEClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewCity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCityToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addCityToPage);

        this.header = new Header(this.dataStore);
        console.log("ViewCity constructor");
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const cityId = urlParams.get('cityId');

        const city = await this.client.viewCity(cityId);
        this.dataStore.set('city', city);
    }

    mount() {
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();

    }

    addCityToPage() {
        const city = this.dataStore.get('city');
        if (city == null) {
            return;
        }

        document.getElementById('cityName').innerText = city.cityName;

        let activityListHtml = '';
        let activityList;
        for (activityList of city.activityList) {
            activityListHtml += '<div class="activity">' + activityList + '</div>';
        }
        document.getElementById('activityList').innerHTML = activityListHtml;
    }
}

    const main = async () => {
        const viewCity = new ViewCity();
        viewCity.mount();
    };

    window.addEventListener('DOMContentLoaded', main);
