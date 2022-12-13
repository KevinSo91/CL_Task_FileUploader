// Close Message on click
		$(document).ready(function(){
		  $("#messageSuccess").click(function(){
		    $(this).hide();
		  });
		});


// Close Message on click
		$(document).ready(function(){
		  $("#messageError").click(function(){
		    $(this).hide();
		  });
		});
		
		
// Hides Message after Fadeout
		setTimeout(() => {
		  const box = document.getElementById('messageSuccess');

		  // hides element (still takes up space on page)
		  box.style.visibility = 'hidden';
		
		  // removes element from DOM
		  //box.style.display = 'none';
		}, 3000); // time in milliseconds		
		
		
// Hides Message after Fadeout
		setTimeout(() => {
		  const box = document.getElementById('messageError');

		  // hides element (still takes up space on page)
		  box.style.visibility = 'hidden';
		
		  // removes element from DOM
		  //box.style.display = 'none';
		}, 3000); // time in milliseconds