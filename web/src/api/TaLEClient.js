import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";


export default class TaLEClient extends BindingClass {

    constructor(props = {}) {
    super();

    const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 
    'viewCity', 'viewCities', 'viewAllActivitiesForCity',
      'createNewActivity', 'viewActivity', 'viewPersonalActivities',
      'editActivity', 'deleteActivity',
       'viewCommentsForActivity', 'viewPersonalComments',
      'viewComment', 'deleteComment', 'editComment'];
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

    /**
    * Get the identity of the current user
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The user information for the current user.
    */
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
            return response.data.cityModel;
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

    async createNewActivity(cityId, activityName, description, posterExperience, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create an Activity Post.");
            const response = await this.axiosClient.post(`cities/${cityId}/activities`, {
                activityName: activityName,
                description: description,
                posterExperience: posterExperience
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.activityModel;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async viewActivity(activityId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`activities/${activityId}`);
            return response.data.activityModel;
          } catch (error) {
            this.handleError(error, errorCallback)
          }
    }

    async viewAllActivitiesForCity(cityId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`cities/${cityId}/activities`);
            return response.data.activityModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async editActivity(activityId, activityName, description, posterExperience, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users cand update Comments");
            const response = await this.axiosClient.put(`activities/${activityId}`, {
                updatedActivityName: activityName,
                updatedDescription: description,
                updatedPosterExperience: posterExperience
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.activityModel;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async viewPersonalActivities(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only Authenticated users can view these Activities.");
            const response = await this.axiosClient.get(`activities`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.activityModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async viewPersonalComments(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only Authenticated users can view these Comments.");
            const response = await this.axiosClient.get(`comments`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.commentModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async createComment(activityId, commentTitle, commentMessage, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can post a Comment.");
            const response = await this.axiosClient.post(`activities/${activityId}/comments`, {
                title: commentTitle,
                message: commentMessage
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.commentModel;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async viewCommentsForActivity(activityId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`activities/${activityId}/comments`);
            return response.data.commentModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async viewComment(activityId, commentId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only Authenticated users can see this comment.");
            const response = await this.axiosClient.get(`activities/${activityId}/comments/${commentId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.commentModel;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async deleteComment(activityId, commentId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete a Comment");
            const response = await this.axiosClient.delete(`activities/${activityId}/comments/${commentId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            
            });
            return response.data.deleteResult;
        }
         catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async deleteActivity(activityId, errorCallback) {
        try {
                const token = await this.getTokenOrThrow("Only authenticated users can delete a Comment");
                const response = await this.axiosClient.delete(`activities/${activityId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                return response.data.deleteResult;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async editComment(activityId, commentId, commentTitle, commentMessage, errorCallback) {
        try{
            const token = await this.getTokenOrThrow("Only authenticated users cand update Comments");
            const response = await this.axiosClient.put(`activities/${activityId}/comments/${commentId}`, {
                title: commentTitle,
                message: commentMessage
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.commentModel;
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