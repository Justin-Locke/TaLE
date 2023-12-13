import TaLEClient from '../api/TaLEClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import NavBar from '../components/navBar';
import Authenticator from '../api/authenticator';
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';

class Homepage extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount'], this);

        this.header = new Header(this.dataStore);
        this.navBar = new NavBar();
        this.authenticator = new Authenticator();
        this.footer = new Footer();
        this.loadingSpinner = new LoadingSpinner();
    }

    mount() {
        if ('caches' in window) {
            caches
            .open("my-cache").then(function (cache) {
                cache.match('http://localhost:8000/viewCity.html?cityId=N012012V#')
                .then(function (response) {
                    if (response) {
                        cache.delete('http://localhost:8000/viewCity.html?cityId=N012012V#')
                        .then(function (isDeleted) {
                        if (isDeleted) {
                            window.location.href = `http://localhost:8000/viewCity.html?cityId=N012012V#`;
                            console.log("cache deleted");
                        } else {
                        console.log("Cache is empty");
                        }
                        })
                        .catch(function (error) {
                            console.log("Failed to delete resource from cache");
                        })
                    } else {
                        console.log("Cache is empty");
                    }

                })
                
                
            })
        }
        this.header.addHeaderToPage();
        this.navBar.addNavBarToPage();
        this.footer.addFooterToPage();
        this.client = new TaLEClient();
    }

    
}

const main = async () => {
    const homePage = new Homepage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);