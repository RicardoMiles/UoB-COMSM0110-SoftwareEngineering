int numNodes = 9;
Node[] nodes = new Node[numNodes];
boolean isReturning = false;

void setup() {
  size(600, 400);
  
  // Initialize nodes
  for (int i = 0; i < numNodes; i++) {
    float x = random(width);
    float y = random(height);
    nodes[i] = new Node(x, y);
  }
}

void draw() {
  background(255);
  
  // Draw connections between nodes
  for (int i = 0; i < numNodes; i++) {
    for (int j = 0; j < numNodes; j++) {
      if (i != j) {
        stroke(0);
        line(nodes[i].x, nodes[i].y, nodes[j].x, nodes[j].y);
      }
    }
  }
  
  // Draw nodes
  for (int i = 0; i < numNodes; i++) {
    nodes[i].display();
  }
  
  // Draw return button
  drawReturnButton();
}




class Node {
  float x, y;
  color nodeColor = color(255, 0, 0);
  
  Node(float x, float y) {
    this.x = x;
    this.y = y;
  }
  
  void display() {
    fill(nodeColor);
    ellipse(x, y, 20, 20); // Draw a circle for the node
  }
}
