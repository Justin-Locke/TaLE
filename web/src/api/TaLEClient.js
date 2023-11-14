import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";


export default class TaLEClient extends BindingClass {

    constructor(props = {}) {
    super();

    const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'viewCity', 'viewCities'];
    this.bindClassMethods(methodsToBind, this);

    this.authenticator = new Authenticator();;
    this.props = props;

    axios.defaults.baseURL = process.env.API_BASE_URL;
    this.axiosClient = axios;
    this.clientLoaded();

    }


    clientLoaded() {
    if (this.props.hasOwnProperty("onReady")) {
        this.props.onReady(this);
        }
    }

    async getIdentity(errorCallback) {
    try {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();

        if (!isLoggedIn) {
            return undefined;
        }

        return await this.authenticator.getCurrentUserInfo();
    } catch (error) {
        this.handleError(error, errorCallback)
    }
}

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    async viewCity(cityId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`cities/${cityId}`);
            return response.data.city;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async viewCities(errorCallback) {
        try {
        const response = await this.axiosClient.get(`cities`);
        return response.data.cityModelList;
      } catch (error) {
        this.handleError(error, errorCallback)
      }
    }

    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }

}