var nodeList=[];
var head;
var nodeStack=[];
var nodeNum;
function initializeNodeState()
{
	var layers=$('canvas').getLayers();
	var nodes=[];
	for(var k=0; k<nodeNum; k++)
	{
		nodes.push(layers[k]);
	}
	nodes.reverse();
	for(var i=0; i<nodeNum; i++)
	{
		layer=nodes[i];
		layer.fillStyle="#0000aa";
		var indexLeft=(i+1)*2-1;
		var indexRight=(i+1)*2;
		if(indexLeft<nodeNum)				
		{
			layer.left=nodes[indexLeft];
		}

		if(indexRight<nodeNum)
		{
			layer.right=nodes[indexRight];
		}			
	}		
	head=nodes[0];
	nodeStack.push(head);
}
function transmit()
{
	if(nodeStack.length>0)
	{
		traverse();
	}
	else if(head==null)
	{
		initializeNodeState();
		transmit();
	}
	else
	{
		head=null;
		initializeNodeState();
		$('canvas').drawLayers();
	}
}
function traverse()
{
	var numCanTransmit=0;
	if(nodeStack.length>0)
	{
		var val=nodeStack.pop();
		val.fillStyle="#8800aa";
		$('canvas').drawLayers();
		

		if(val.canTransmit)
		{
			numCanTransmit++;
//			if(numCanTransmit>1)
//				return false;
		}

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

}
function drawTree(numNodes, radius)
{
	var start=0;
	var numRows=Math.floor(Math.log(numNodes)/Math.log(2));
	var y=5*numRows*radius;
	var diff=radius/2;
	var xStart=radius+diff+start;
	var columnSize=numNodes-((1<<numRows)-1);
	var xIncrease=radius*2+diff*2;
	var x=xStart;
	x+=xIncrease*(columnSize-1);x
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
}
function applyText(text, circle)
{
	$('canvas').drawText({
		layer: true,
		fillStyle: '#FFFFFF',
		groups: [circle.id.toString()],
		x: circle.x,
		y: circle.y,
		intangible: true,
		fontSize: circle.radius/2,
		fontFamily: 'Verdana, sans-serif',
		text: text,
		draggable: false
	})
}
function drawNode(X,Y, radius, id)
{
	 $('canvas').drawEllipse({
		  layer: true,
		  fillStyle: '#0000aa',
		  x: X, y: Y,
		  active: true,
		  wait: false,
		  canTransmit: true,
		  dontDrag: false,
		  opacity: 1,
		  id: id,
		  width: radius, height: radius,
		  draggable: false,
		  disableTransmit: function(layer)
		  {
			 layer.canTransmit=false;  
			 if(layer.active)
				 layer.fillStyle='#000044';
			 else
				 layer.fillStyle='#000000';
			$('canvas').drawLayers();
		  },
		  enableTransmit: function(layer)
		  {
			 layer.canTransmit=true;
			 if(layer.active)
				 layer.fillStyle='#0000aa';
			 else
				 layer.fillStyle='#000000';
			$('canvas').drawLayers();

		  },
		  disableClick: function(layer)
		  {
			  layer.click=null;
			  alert('disabling click');
		  }
		  /* Clicking will be used for DCR disable it for the preorder traversal.
		  ,
		  click: function(layer)
		  {
			  /if(layer.dontDrag)
			  {
				  layer.dontDrag=false;
				  return;  
			  }
			  layer.dontDrag=false;
			  if(layer.active)
			  {
			      layer.fillStyle='#000000';  
			  }
			  else
		      {				
				  layer.fillStyle='#0000aa';
		      }
              $('canvas').drawLayers();			 
			  layer.active=!layer.active;
		  }
		  */
		});
	this.x=X;
	this.y=Y;
	this.radius=radius;
	this.id=id;
}

drawCenteredTree(7,20, 225);
