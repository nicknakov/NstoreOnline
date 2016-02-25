var windowSize = '';
var windowWidth = 0;
var actualSize = 0;

$(document).ready(function(){

// animating FLASH Struni
	$(".main_div")
		.delay(500)
		.animate({left:-600}, 500)
		.animate({top: "+=100"}, "slow")
		.delay(3000)
		.animate({left:0, top: "-=100"}, "slow")
// End of annimation     


//Checking JQuery with application running on TomCat server!! Nothing with NstoreOnline
	/*$.getJSON('http://localhost:8080/fitnes-tracker-Jpa/activities.json', {ajax:'true'}, function(data){
			alert('Ok');
				var myhtml = '<option value="">--Please select one--</option>';
				var len = data.length;
				for(var i=0; i< len; i++){
					myhtml += '<option value="' + data[i].desc + '">'+ data[i].desc + '</option>';
				}
				myhtml += '</option>';
				alert(myhtml);
				//$("#activities").html(myhtml);
	});*/




	checkBrowserSize();
	setInterval('checkBrowserSize()',100);

	// Top Menu

	$('#menu a').click(function(e){

		$('#menu a').removeClass('top_active');
		$(this).addClass('top_active');
	});

	// Rullers Mreja and List
	$('#izgled a').click(function(){
		$('#izgled a').removeClass('active-rul');
		$(this).addClass('active-rul');
		if($(this).attr("id") == 'mreja'){
			$('#contentarea #maincontent .thumbs_list').removeClass('thumbs_list').addClass('thumbs');
		}else{
			$('#contentarea #maincontent .thumbs').addClass('thumbs_list').removeClass('thumbs');
		}
	});
	

	// Left small menu loader!
	$('#mob_left_menu').click(function(e){
		e.preventDefault();
		$('#contentarea').toggleClass('menuDisplayed');
		$(this).toggleClass('selected1');
	});
	
	//Left menu DropDown script
	
	$('#leftside_menu > ul > li > a').click(function() {
          $('#leftside_menu li').removeClass('active');
          $(this).closest('li').addClass('active');	
          var checkElement = $(this).next();
          if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
            $(this).closest('li').removeClass('active');
            checkElement.slideUp('normal');
          }
          if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
            $('#leftside_menu ul ul:visible').slideUp('normal');
            checkElement.slideDown('normal');
          }
          if($(this).closest('li').find('ul').children().length == 0) {
            return true;
          } else {
            return false;	
          }		
        });


	// Top menu click loade
	$('a.mobile_menu').on('click',function(){
		var navHeight = $('#nav-bar').height();
		var newNavHeight = $('#nav-bar #menu').height();
		if(navHeight == 0){
			$('#nav-bar').animate({'height':newNavHeight+'px'},500);
			$(this).addClass('selected');
		}else{
			$('#nav-bar').animate({'height':'0px'},500);
			$(this).removeClass('selected');
		}
	});

});


function checkBrowserSize(){
	windowWidth = window.outerWidth;
	var contentWidth = $('body').width();
	var sizeDiff = windowWidth- contentWidth;
	actualSize = windowWidth - sizeDiff; 
	newNavWidt = (actualSize*18)/100;
	
	if(actualSize > 920){newWindowSize = 'large';}
	if(actualSize <= 920 && actualSize > 600){newWindowSize = 'mediam';}
	if(actualSize <= 600){newWindowSize = 'small';}
	
		if(windowSize != newWindowSize){
			windowSize = newWindowSize;
			loadMobileMenu();
		}
}


function loadMobileMenu(){

	if(windowSize == 'large'){
		$('#nav-bar').css('height','auto');
	}
	
	if(windowSize == 'mediam'){
		if(actualSize > 600){
			$('#nav-bar').css('height','auto');
		}	
	}
	
	if(windowSize == 'small'){
		if(actualSize < 600){
			$('#nav-bar').css('height','0px');
		}	
	}
}



