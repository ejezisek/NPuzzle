$(document).ready(function (){

  var skipSlider = document.getElementById('skipstep');
	  noUiSlider.create(skipSlider, {
      range: {
          'min': 1,
          '7%': 2,
          '14%': 3,
          '21%': 4,
  		  '28%': 5,
  		  '35%': 6,
  		  '42%': 7,
  		  '49%': 8,
  		  '56%': 9,
  		  '63%': 10,
  		  '70%': 11,
  		  '77%': 12,
  		  '84%': 13,
          '91%': 14,
          '100%': 15, 	
          'max': 15
      },
      snap: true,
      start: [7]
  });


    skipSlider.noUiSlider.on('update', function( values, handle ) {
        $( "#amount" ).val( values );
        reset();
        drawCenteredTree(values,15, 500);
    });

    $( "#amount" ).val( $( "#slider-range-max" ).slider( "value" ) );
    drawCenteredTree(val, 15, 500);
  });
  function resetTree()
  {
	   drawCenteredTree(val, 15, 500);
  }