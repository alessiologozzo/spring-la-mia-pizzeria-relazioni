let deleteModalName = ""
let ids = []
let selectedIngredients = ""

function setDeleteAction(name, action) {
	const title = document.getElementById("deleteModal").getElementsByTagName("h5")
	title[0].textContent = name

	const form = document.getElementById("delete-form")
	form.action = action
}

function submitDeleteForm() {
	const form = document.getElementById("delete-form")
	form.submit()
}

function setInputDateMinMax(startDate, endDate) {
	const currentDate = new Date().toJSON().slice(0, 10)
	const startDateElement = document.getElementById("startDate")
	const endDateElement = document.getElementById("endDate")

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
	const checkElement = document.getElementById(name + id).parentElement.querySelector("i:last-of-type")
	checkElement.classList.toggle("d-none")

	if (ids.includes(id))
		ids = ids.filter(vId => vId != id)
	else
		ids.push(id)
}

function submitForm(formName, hiddenTextName) {
	const form = document.getElementById(formName)
	const hiddenText = document.getElementById(hiddenTextName)

	hiddenText.value = ids.toString()

	if (form.checkValidity())
		form.submit()
	else
		form.reportValidity()
}

function setTipsBottomPosition() {
	const resultBox = document.getElementById("result-box")
	resultBox.style.bottom = "-" + resultBox.offsetHeight + "px"
}

function shouldShowTips(event) {
	filterTips()
	if (countActiveTips() > 0) {
		const resultBox = document.getElementById("result-box")
		resultBox.classList.remove("d-none")
		setTipsBottomPosition()
		event.stopPropagation()
	}
	else
		hideTips()
}

function filterTips() {
	const resultBox = document.getElementById("result-box")
	const tips = resultBox.getElementsByTagName("li")
	const searchInput = document.getElementById("search").value

	for (let i = 0; i < tips.length; i++) {
		if (tips[i].textContent.toLowerCase().includes(searchInput.toLowerCase()) && !selectedIngredients.toLowerCase().includes(tips[i].textContent.toLowerCase()))
			tips[i].classList.remove("d-none")
		else
			tips[i].classList.add("d-none")
	}
	setTipsBottomPosition()
}

function hideTips() {
	const resultBox = document.getElementById("result-box")
	resultBox.classList.add("d-none")
}

function setSelectedIngredients(ingredients) {
	selectedIngredients = ingredients
}

function filterSelectedIngredients() {
	const selectedIngredientsElement = document.getElementById("selected-ingredients")
	const ingredientBadges = selectedIngredientsElement.getElementsByClassName("badge")
	const noSelectedIngredients = document.getElementById("no-selected-ingredients")
	let counter = 0

	for (let i = 0; i < ingredientBadges.length; i++) {
		if (selectedIngredients.toLowerCase().includes(ingredientBadges[i].textContent.toLowerCase())) {
			ingredientBadges[i].classList.remove("d-none")
			counter++
		}
		else
			ingredientBadges[i].classList.add("d-none")
	}

	if (counter > 0)
		noSelectedIngredients.classList.add("d-none")
	else
		noSelectedIngredients.classList.remove("d-none")
}

function addIngredient(name) {
	const searchBar = document.getElementById("search")
	searchBar.value = ""

	if (selectedIngredients.length > 0)
		selectedIngredients += ", " + name
	else
		selectedIngredients += name

	filterSelectedIngredients()
}

function countActiveTips() {
	const resultBox = document.getElementById("result-box")
	const tips = resultBox.getElementsByTagName("li")
	let result = 0

	for (let i = 0; i < tips.length; i++)
		if (!tips[i].classList.contains("d-none"))
			result++

	return result
}

function removeIngredient(name) {
	if (selectedIngredients.includes(name)) {
		if (selectedIngredients.includes(" " + name + ","))
			selectedIngredients = selectedIngredients.replace(" " + name + ",", "")
		else if (selectedIngredients.includes(name + ","))
			selectedIngredients = selectedIngredients.replace(name + ",", "")
		else if (selectedIngredients.includes(name))
			selectedIngredients = selectedIngredients.replace(name, "")

		if (selectedIngredients[selectedIngredients.length - 2] == "," || selectedIngredients[selectedIngredients.length - 2] == " ")
			selectedIngredients = selectedIngredients.substring(0, selectedIngredients.length - 2)

		if (selectedIngredients[0] == " ")
			selectedIngredients = selectedIngredients.substring(1)

		filterSelectedIngredients()
	}
}

function submitPizzaForm() {
	const form = document.getElementById("pizza-form")
	const pizzaIngredientsHiddenText = document.getElementById("pizza-ingredients-hidden-text")

	pizzaIngredientsHiddenText.value = selectedIngredients

	if (form.checkValidity())
		form.submit()
	else
		form.reportValidity()
}

function addCommasToIngredientsLinksContainer() {
	const ingredientsLinksContainer = document.getElementById("ingredients-links-container")
	
	if (ingredientsLinksContainer != null) {
		const links = ingredientsLinksContainer.getElementsByTagName("a")

		for (let i = 0; i < links.length; i++) {
			if (i + 1 < links.length)
				links[i].textContent += ","
		}
	}
}

function truncateElements(parentId, charLimit) {
	const parent = document.getElementById(parentId)
	const children = parent.getElementsByClassName("data-truncable-element")

	for (let i = 0; i < children.length; i++)
		if (children[i].textContent.length > charLimit)
			children[i].textContent = children[i].textContent.substring(0, charLimit) + "..."
}

function truncateElementsList(childrenName, childrenSize, charLimit) {
	for (let i = 0; i < childrenSize; i++) {
		const element = document.getElementById(childrenName + i)
		if (element != null && element.textContent.length > charLimit)
			element.textContent = element.textContent.substring(0, charLimit) + "..."
	}
}