/*  $(function() {
	  var val=7;
    $( "#slider-range-max" ).slider({
      range: "max",
      min: 1,
      max: 15,
      value: val,
      slide: function( event, ui ) {
        $( "#amount" ).val( ui.value );
        val=ui.value;
        reset();
        drawCenteredTree(ui.value,15, 300);
      }
    });

    $( "#amount" ).val( $( "#slider-range-max" ).slider( "value" ) );*/
 // });
//    drawCenteredTree(val, 15, 300);

 /* function resetTree()
  {
	   drawCenteredTree(val, 15, 300);
  }*/
//var val=7;
//  	   drawCenteredTree(7, 15, 300);
activeNum=-1;
function scrollbarReference(slide)
{
	$('div.scrollbar').slick({
		  infinite: true,
		  slidesToShow: 3,
		  slidesToScroll: 3,
		  draggable: false,
		  dots:true
		});
}

  function displayBlogPosts(elementNumber)
  {
	  if(elementNumber!=activeNum)
	  {
	  activeNum=elementNumber;
	  val=7;
	  var el=$("div.images");
	  var html="<table>" +
	  		"<tr>"+
				"<td>";
	  var blogPosts=getBlogPosts();
	  blogPosts.sort(function(a,b){
		    return a.id-b.id;
		});
	  if(elementNumber<blogPosts.length)
	  {
		  var selectedPost=blogPosts[elementNumber];
		  html+=selectedPost.displaySnippet +"</td><td><h3><a href="+selectedPost.url+">"+selectedPost.page+"</a></h3>"+selectedPost.description+"</td></tr>";
		  var temp="";
		  html+=temp;
		  html+="</table>";
		  el.html(html);
	  }
	  }
  }
  function createScrollbar()
  {
	  var blogPosts=getBlogPosts();
	  blogPosts.sort(function(a,b){
		    return a.id-b.id;
		});
	  var html="";
	  for(var i in blogPosts)
	  {
		  var blogPost=blogPosts[i];
		  html+="<div onmouseover=\"dorefresh("+i.toString()+")\" id=\"blogItem\" onclick=\"location.href='"+blogPost.url+"'\";><img src=\"images/"+blogPost.image+"\" id=\"blogItemImage\"></img>"+blogPost.shortDescription + "</div>"
	  }
	  $('div.scrollbar').html(html);
  }
  function dorefresh(elNum)
  {
	  displayBlogPosts(elNum);
  }
  $(window).load(function(){
	  dorefresh(0);
	  createScrollbar();
	  scrollbarReference(0);
  });