/*Date and time on the botton of website*/
window.onload = function() {
	document.getElementById("date").innerHTML = Date();
}



/*Button for afterpay price check*/
function performOp() {
	var x =document.forms["serviceprice"]["number"].value;
	var changedX = x / 4;
	alert (changedX);
}


/*Button for register*/
function changecss() {
	document.getElementById("creat").innerHTML = "Your account has been created!";
	document.getElementById("creat").style.color="blue";
}








/*Button for submit payment*/
function validateForm() {
	var x =document.forms["practice"]["Number"].value;
	if (x !="") {
		alert("Thank you for the submit! Our staff member will contact you shortly.");
	}
	else {
		alert("Please fill the blank");
	}
}


