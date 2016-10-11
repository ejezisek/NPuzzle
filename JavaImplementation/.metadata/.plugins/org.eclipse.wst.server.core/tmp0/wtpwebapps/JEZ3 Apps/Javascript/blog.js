function setupRecentPosts()
{
	var html="<h3>Recent Posts</h3>"+
	"<ul>";
	  var blogPosts=getBlogPosts();
	  blogPosts.sort(function(a,b){
		    return a.id-b.id;
		});
	  for(var i in blogPosts)
	  {
		  var blogPost=blogPosts[i];
		  html+="<li>"
			  html+="<a href=\""+blogPost.url+"\">"+blogPost.shortDescription+"</a>"
		  html+="</li>"
	  }
	html+="</ul>";
	$('.posts').html(html)
}
$(window).load(function(){
	setupRecentPosts();
});