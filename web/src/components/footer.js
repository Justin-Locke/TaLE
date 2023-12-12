export default class Footer {
    //Add footer to page//
    addFooterToPage() {
        const footerDiv = document.getElementById('footer');
        footerDiv.classList.add('footer');
        const infoColumn = this.createColumn('Information', ['About us', 'Customer Service', 'Privacy Policy', 'Sitemap']);
        footerDiv.appendChild(infoColumn);
        const accountColumn = this.createColumn('My Account', ['Sign In/Out', 'My Wishlist', 'Help']);
        footerDiv.appendChild(accountColumn);
        const newsletterColumn = this.createColumnWithInput('Newsletter', 'Subscribe');
        footerDiv.appendChild(newsletterColumn);
        
    }

    createColumn(title, items) {
        const column = document.createElement('h4');
        column.classList.add('column');

        const heading = document.createElement('h4');
        heading.textContent = title;
        column.appendChild(heading)

        const list = document.createElement('ul');
        items.forEach(itemText => {
            const listItem = document.createElement('li');
            listItem.textContent = itemText;
            list.appendChild(listItem);
        });
        column.appendChild(list);
        return column;
    }

    createColumnWithInput(title, buttonText) {
        const column = this.createColumn(title, []);

        const input = document.createElement('input');
        input.type = "email";
        input.placeholder = 'Enter your email address';
        column.appendChild(input);

        const subscribeButton = document.createElement('button');
        subscribeButton.textContent = buttonText;
        column.appendChild(subscribeButton);
        return column;
    }
}