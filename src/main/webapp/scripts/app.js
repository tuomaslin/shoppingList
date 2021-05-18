class ShoppingListApp {

	constructor(container, itemTemplate, form, refreshButton) {
        this.container = container;
        this.itemTemplate = itemTemplate;
        this.initForm(form);
        
        this.restUrl = '/shop';
        this.items = [];

        refreshButton.onclick = () => {
            this.load();
        };
    }

	async function removeProduct(id) {
    // TODO: kutsu fetch-funktiota parametrilla { method: 'DELETE' }
    // lähettääksesi poistopyynnön palvelimelle. Käytä parametrina saatua
    // id-arvoa fetch-kutsussa yksilöidäksesi poistettavan rivin.
	
	try {
		let response = await fetch(this.restUrl + `?id=${deleted.id}`, { method: 'DELETE' });
		this.items = this.items.filter(item => item !== deleted);
		this.render();
		return true;
	} catch (error) {
		console.error(error);
		alert('An error occured. Please check the consoles of the browser and the backend.');
		return false;
	}


}
