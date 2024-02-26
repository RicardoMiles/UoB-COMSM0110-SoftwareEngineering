Button[] buttons;
boolean displayTextBox = false;

void setup() {
  size(960, 820);
  background(0);
  
  // Initialize Button
  buttons = new Button[3];
  buttons[0] = new Button(50, 50, 100, 50, "Back");
  buttons[1] = new Button(50, 150, 100, 50, "Start Menu");
  buttons[2] = new Button(50, 250, 100, 50, "Tutorial");
  
  //Initialize Material
  desertImage = loadImage("地图背景贴图.jpg"); // 替换为图片的实际路径
  
  staticCircles = createGraphics(embeddedCanvasWidth, contentHeight);
  staticCircles.beginDraw();
  staticCircles.clear(); // 使背景透明
  staticCircles.fill(255, 0, 0); // 圆的颜色
  
  // 在staticCircles上绘制圆
  for (int i = 0; i < 8; i++) { // 前8个横排，每个至少有两个圆
    for (int j = 0; j < 2; j++) { // 每个横排绘制两个圆，您可以根据需要增加更多圆
      staticCircles.ellipse(
        random(20, embeddedCanvasWidth - 20), // 圆的x坐标随机
        i * (contentHeight / 9.0) + (contentHeight / 9.0) / 2, // 调整圆的y坐标使其位于横排中间
        30, // 圆的宽度
        30  // 圆的高度
      );
    }
  }

  // 单独处理最底部的横排，只绘制一个圆
  staticCircles.ellipse(
    random(20, embeddedCanvasWidth - 20), // 圆的x坐标随机
    8 * (contentHeight / 9.0) + (contentHeight / 9.0) / 2, // 最底部的横排
    30, // 圆的宽度
    30  // 圆的高度
  );

  staticCircles.endDraw();

  
}

void draw() {
  //background(0); // Refresh Screen
  // Draw Button
  for (int i = 0; i < buttons.length; i++) {
    buttons[i].display();
  }
  
  // Draw Status Information
  drawStatusInfo();
 
  // Draw Map
  drawEmbeddedCanvas();
  
  // If Tutorial button clicked show text 
  if (displayTextBox) {
    drawTextBox();
  }
}



void drawStatusInfo() {
  // Draw Health Point
  fill(255, 0, 0);
  ellipse(870, 50, 30, 30); // Red shape
  fill(255);
  textSize(12);
  textAlign(RIGHT, CENTER);
  text("HP 70/70", 850, 50); // HP value info
  
  // Draw Move Point
  fill(0, 255, 0);
  ellipse(870, 90, 30, 30); // Green shape
  fill(255);
  text("MP 01/18", 850, 90); // MP value info
}


void drawTextBox() {
  fill(255);
  rect(325, 300, 300, 100); 
  textAlign(LEFT, TOP);
  fill(0);
  String textContent = "Scroll down or using ↑/↓ on keyboard to preview the route to top of Tower;\nClick to choose the start node or next node;\nUsing move point to move up;\nDouble click to close Tutorial.";
  text(textContent, 325, 300, 300, 100); 
}



class Button {
  float x, y, w, h;
  String label;
  
  Button(float x, float y, float w, float h, String label) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.label = label;
  }
  
  void display() {
    stroke(255);
    fill(100);
    rect(x, y, w, h);
    textAlign(CENTER, CENTER);
    fill(255);
    text(label, x + w/2, y + h/2);
  }
  
  boolean isMouseOver() {
    return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
  }
}
