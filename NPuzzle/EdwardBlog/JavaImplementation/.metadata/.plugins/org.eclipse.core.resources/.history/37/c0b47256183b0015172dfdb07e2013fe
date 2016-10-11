var nodeList=[];
var head;
var nodeStack=[];
var nodeNum;
var nodes;
var initialFill="#008CFF"
var textFill="#FFFFFF"
var activeFill="#110033"
function reset()
{
	nodes=[];
	head=null;
	nodeList=[];
	nodeStack=[];
	$('canvas').removeLayers();
}
function initializeNodeState()
{
	var layers=$('canvas').getLayers();
	nodes=[];
	for(var k=0; k<nodeNum; k++)
	{
		nodes.push(layers[k]);
	}
	nodes.reverse();
	for(var i=0; i<nodeNum; i++)
	{
		layer=nodes[i];
		layer.fillStyle=initialFill;
		var indexLeft=(i+1)*2-1;
		var indexRight=(i+1)*2;
		if(indexLeft<nodeNum)
		{
			layer.left=nodes[indexLeft];
			nodes[indexLeft].parent=layer;
		}

		if(indexRight<nodeNum)
		{
			layer.right=nodes[indexRight];
			nodes[indexRight].parent=layer;
		}
	}		
	head=nodes[0];
	nodeStack.push(head);
}
function refresh()
{
	nodeStack=[];
	for(var i=0; i<nodeNum; i++)
	{
		layer=nodes[i];
		layer.fillStyle=initialFill;
	}
	nodeStack.push(head);
}
function transmit()
{
	if(nodeStack.length>0)
	{
		traverse();
	}
	else
	{
		refresh();
		$('canvas').drawLayers();
	}
}

function traverse()
{
	var numCanTransmit=0;
	if(nodeStack.length>0)
	{
		var val=nodeStack.pop();
		val.fillStyle=activeFill;
		$('canvas').drawLayers();
		numCanTransmit++;
		if(val.right!=null)
		{
			nodeStack.push(val.right);
		}
		if(val.left!=null)
		{
			nodeStack.push(val.left);
		}
	}

}

//width should be at least numNodes*1.5*radius+radius/2
function drawCenteredTree(numNodes, radius, width)
{
	//The number of rows in the tree.
	nodeNum=numNodes;
	var numRows=Math.floor(Math.log(numNodes)/Math.log(2));
	var y=4*(numRows+1)*radius;
	var diff=radius/2;
	var columnSize=numNodes-((1<<numRows)-1);
	var xIncrease=radius*2+diff*2;
	var start=width/2-xIncrease*((1<<numRows)-1)/2-radius-diff;
	var xStart=radius+diff+start;
	var x=xStart;
	x+=xIncrease*(columnSize-1);
	for(var i=0; i<numNodes; i++)
	{
		var index=nodeList.length;
		nodeList[index]=new drawNode(x,y, radius*2,i);
		columnSize--;
		x-=xIncrease;
		if(columnSize==0)
		{
			xStart-=start;
			xStart*=2;
			xStart+=start;
			numRows--;
			columnSize=1<<numRows;
			x=xStart;
			xIncrease*=2;
			x+=xIncrease*(columnSize-1);
			y-=radius*4;	
		}
	}
	
	nodeList.reverse();
	for(var i=0; i<numNodes; i++)
	{
		var indexLeft=(i+1)*2-1;
		var indexRight=(i+1)*2;
		var item=nodeList[i];
		if(indexLeft<numNodes)
		{
			item.left=nodeList[indexLeft];
		}
		if(indexRight<numNodes)
		{
			item.right=nodeList[indexRight];
		}
	}
	
	var stack=[];
	stack.push(nodeList[0]);
	var index=0;
	while(stack.length>0)
	{
		var val=stack.pop();
		applyText(index++, val);
		if(val.right!=null)
		{
			stack.push(val.right);
		}
		if(val.left!=null)
		{
			stack.push(val.left);
		}
	}
	$('canvas').drawLayers();
    initializeNodeState();
}

function applyText(text, circle)
{
	$('canvas').drawText({
		layer: true,
		name: 'nodeText'+circle.id.toString(),
		fillStyle: textFill,
		x: circle.x,
		y: circle.y,
		intangible: true,
		fontSize: circle.radius/2,
		fontFamily: 'Verdana, sans-serif',
		text: text,
		draggable: false
	})
}
function removeNode(node)
{
	if(node==null)
	{
		return
	}
	var id=node.id;
	removeNode(node.left);
	removeNode(node.right);
	var name='node'+id.toString();
    $('canvas').removeLayer('nodeText'+id.toString());
	$('canvas').removeLayer(name);
	refresh();
}
function drawNode(X,Y, radius, id)
{
	var name='node'+id.toString();

	$('canvas').drawEllipse({
		  layer: true,
		  name: name,
		  fillStyle: initialFill,
		  x: X, y: Y,
		  active: true,
		  wait: false,
		  canTransmit: true,
		  dontDrag: false,
		  opacity: 1,
		  id: id,
		  width: radius, height: radius,
		  draggable: false,
		  click: function(layer) {
			  var par=$('canvas').getLayer('nodeText'+id.toString());

			  for(var i in nodes)
			  {
				  if(nodes[i].id==id)
			      {
					  var removeN=nodes[i];
			      }
				  if(nodes[i].left!=null && nodes[i].left.id==id)
				  {
					  nodes[i].left=null;
				  }
				  if(nodes[i].right!=null && nodes[i].right.id==id)
				  {
					  nodes[i].right=null;
				  }
			  }
			  removeNode(removeN)
			  $('canvas').drawLayers();
		  }
		});
	this.x=X;
	this.y=Y;
	this.radius=radius;
	this.id=id;
}
TotalWidth=500;
/*
(function(){
   var c = $("#canvas");
        ctx = c[0].getContext('2d');

    $(function(){
        // set width and height
         ctx.canvas.height = TotalWidth;
         ctx.canvas.width = TotalWidth;
    });
    
})();*/
