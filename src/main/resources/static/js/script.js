let pizzaData = null

function setPizzaData(id, name) {
	pizzaData = {
		id: id,
		name: name
	}
	writePizzaName(pizzaData)
}

function writePizzaName(pizzaData) {
	let title = document.getElementById("deleteModal").getElementsByTagName("h5")
	title[0].textContent = pizzaData.name
}

function goToDelete() {
	location.replace("/delete/" + pizzaData.id)
}