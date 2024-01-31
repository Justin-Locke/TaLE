export default class ActivityCard {
    // Create Activity Card
    CreateActivityCard(activity) {
        const activityDiv = document.createElement('div');
            activityDiv.classList.add('activity-card');

            const activityName = document.createElement('h3');
            if (activity.activityName == null) {
                activityName.textContent = "*ACTIVITY NAME NOT FOUND*"
            } else {
                activityName.textContent = activity.activityName;
            }

            activityName.addEventListener('click', () => {this.redirectToViewActivity(activity)});
            activityDiv.appendChild(activityName);
        
            const line = document.createElement('hr');
            activityDiv.appendChild(line);
            return activityDiv;
    }

    redirectToViewActivity(activity) {
        if (activity != null) {
        window.location.href = `/viewActivity.html?activityId=${activity.activityId}`
    }
}
}