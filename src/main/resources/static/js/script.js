let data = null
let ids = []

function setData(id, name, path) {
	data = {
		id: id,
		name: name,
		path: path
	}
	writeName(data)
}

function writeName(data) {
	let title = document.getElementById("deleteModal").getElementsByTagName("h5")
	title[0].textContent = data.name
}

function goToDelete() {
	location.replace(data.path + "delete/" + data.id)
}

function setInputDateMinMax(startDate, endDate) {
	let currentDate = new Date().toJSON().slice(0, 10)
	let startDateElement = document.getElementById("startDate")
	let endDateElement = document.getElementById("endDate")

	startDateElement.min = currentDate
	if (startDate != undefined)
		startDateElement.value = startDate
	else
		startDateElement.value = currentDate

	endDateElement.min = currentDate
	if (endDate != undefined)
		endDateElement.value = endDate
	else
		endDateElement.value = currentDate
}

function toggleConnection(id, name) {
	let checkElement = document.getElementById(name + id).parentElement.querySelector("i:last-of-type")
	checkElement.classList.toggle("d-none")
	
	if (ids.includes(id))
		ids = ids.filter(vId => vId != id)
	else
		ids.push(id)
}

function submitForm(formName, hiddenTextName) {
	let form = document.getElementById(formName)
	let hiddenText = document.getElementById(hiddenTextName)

	hiddenText.value = ids.toString()
	form.submit()
}

function goBack() {
	window.history.back()
}