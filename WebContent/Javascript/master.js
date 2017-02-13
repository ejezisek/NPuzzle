function getFirstBlogPost()
{
	var blogPosts=getBlogPosts();
	  blogPosts.sort(function(a,b){
		    return a.id-b.id;
		});
	  return blogPosts[0];
}
function getPages()
{
	var index={page:"Home", url:"index.html"};
	var blogPosts=getBlogPosts();
	  blogPosts.sort(function(a,b){
		    return a.id-b.id;
		});
	var blog={page:"Blog", url:blogPosts[0].url};
	var aboutUs={page:"About Me", url:"about.html"};
	var pages=[index, aboutUs, blog];
	return pages;
}
function getBlogPosts()
{
	var preorder={page: "Pre-Order Traversal", url:"preordertraversal.html", date: "08/02/2015", 
		description:"Discusses the Pre-Order Traversal, how to implement it," 
		+" how to use it, how to test it and demonstrates an interactive display of a pre-order traversal.",
		displaySnippet:"<canvas width=300 height=201></canvas>"+
				"<br>" +
				"<p align=\"center\">" +
					"<button type=\"button\" onclick=\"transmit()\">Next</button>" +
				"</p>"+
				"<script src=\"Javascript/preorderTraversal.js\"></script>"+
				"<script>"+
					"drawCenteredTree(val, 15, 300);"+
				"</script>",
		index: 0,
		image: "preorder.png",
		shortDescription: "Pre-Order Traversal of a Binary Tree"}
	var postOrder={page: "Post-Order Traversal", url:"postordertraversal.html", date:"08/05/2015",
			description:"Discusses the Post-Order Traversal, how to implement it, " 
				+"how to use it, how to test it and demonstrates an interactive display of a post-order traversal.",
				displaySnippet:"<canvas width=300 height=201></canvas>"+
						"<br>" +
						"<p align=\"center\">" +
							"<button type=\"button\" onclick=\"transmit()\">Next</button>" +
						"</p>"+
						"<script src=\"Javascript/postorderTraversal.js\"></script>"+
						"<script>"+
							"drawCenteredTree(val, 15, 300);"+
						"</script>",
						index:1,
						image: "postorder.png",
						shortDescription: "Post-Order Traversal of a Binary Tree"}
	var inOrder={page: "In-Order Traversal", url:"inordertraversal.html", date:"08/07/2015",
			description:"Discusses the In-Order Traversal, how to implement it, " 
				+"how to use it, how to test it and demonstrates an interactive display of a in-order traversal.",
				displaySnippet:"<canvas width=300 height=201></canvas>"+
						"<br>" +
						"<p align=\"center\">" +
							"<button type=\"button\" onclick=\"transmit()\">Next</button>" +
						"</p>"+
						"<script src=\"Javascript/inorderTraversal.js\"></script>"+
						"<script>"+
							"drawCenteredTree(val, 15, 300);"+
						"</script>",
						index:2,
						image: "inorder.png",
						shortDescription: "In-Order Traversal of a Binary Tree"}

	var nPuzzle={page: "N-Puzzle", url:"npuzzle.html", date: "02/13/2017", description: "Discusses research regarding the N-Puzzle", displaySnippet:"No Display Snippet yet", index:1, image: "", shortDescription: "N Puzzle problem."}
	
	return [preorder, postOrder, inOrder];
}
function assignLinks()
{
	var html="<ul>";
	var pages=getPages();
	for(var i in pages)
	{
		var page=pages[i];
		html+="<li "
		if(page.page === pageName)
		html+="class=selected";
		html+="><a href=\""+page.url+"\">"+page.page+"</a></li>";
	}
	html+="</ul>";
	var el=$("div#navigation").html(html);
}
function assignHeaderLink()
{
	var html="<a href=\"index.html\" id=\"logo\">Jezisek Software</a>";
	$("div#headerLink").html(html);
}
function assignFooter()
{
	var html="<ul class=\"contacts\">"+
	"<li><h3>Contact Us</h3></li>" +
	"<li><span>Email</span><p>: help@jeziseksoftware.com</p></li>"+
	"</ul>" +
	"<ul id=\"connect\">" + 
	"<li><h3>Get Updated</h3></li>" + 
	"<li><a href=\""+getFirstBlogPost().url+"\">Blog</a></li>" +
	"<li><a href=\"https://www.facebook.com/JEZ3Apps\" target=\"_blank\">Facebook</a></li>" +
	"</ul>" +
	"<span class=\"footnote\">&copy; Copyright &copy; 2015. All rights reserved</span>";
	$("div#footer").html(html);
}
$(window).load(function(){
  assignLinks();
  assignFooter();
  assignHeaderLink();
});
document.title = "JEZ3 Software Development";
