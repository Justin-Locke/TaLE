import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewCities extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCitiesToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addCitiesToPage);
        this.header = new Header(this.dataStore);
        console.log("viewCities constructor");
    }


    async clientLoaded() {
        const cityList = await this.client.viewCities();
        this.dataStore.set('cityList', cityList);
        console.log("CityLIST = " + cityList.cityName);
    }


    mount() {
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();

    }

    async addCitiesToPage() {
        console.log("addCitiesToPage")
        const cityList = this.dataStore.get('cityList');
        console.log("CityList ++++=" + cityList);
        if (cityList == null) {
        console.log("cityList is null")
            return;
        }

        let cityHtml = '<table><tr><th>City</th></tr>';

        for (const city of cityList) {
            cityHtml += `
            <tr>
                <td>
                    <a href="/viewCity.html?cityId=${city.cityId}">${city.cityName}</a>
                </td>
            </tr>
            `;
        }
        document.getElementById('citiesList').innerHTML = cityHtml;
    }



}

const main = async () => {
    const viewCities = new ViewCities();
    viewCities.mount();
};

window.addEventListener('DOMContentLoaded', main);