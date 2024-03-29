/* autogenerated by Processing revision 1293 on 2024-02-26 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class GameState_Map extends PApplet {

Button[] buttons;
boolean displayTextBox = false;

public void setup() {
  /* size commented out by preprocessor */;
  background(0);
  
  // Initialize Button
  buttons = new Button[3];
  buttons[0] = new Button(50, 50, 100, 50, "Back");
  buttons[1] = new Button(50, 150, 100, 50, "Start Menu");
  buttons[2] = new Button(50, 250, 100, 50, "Tutorial");
  
  //Initialize Material
  desertImage = loadImage("MapBackground.jpg"); 
  
  //Initialize Map nodes visual elements  
  staticCircles = createGraphics(embeddedCanvasWidth, contentHeight);
  staticCircles.beginDraw();
  staticCircles.clear(); // Make the background transparent
  staticCircles.fill(0, 47, 167); // Set the color of node
  
  //Draw nodes on staticCircles
  float minDistance = 30; 
  float diameter = 30;
  for (int i = 5; i > 0; i--) { 
    ArrayList<PVector> row = new ArrayList<PVector>();
    int circlesInRow = PApplet.parseInt(random(1, 5)); 
    float sectionWidth = (embeddedCanvasWidth - (circlesInRow + 1) * minDistance) / circlesInRow;
    for (int j = 0; j < circlesInRow; j++) { 
      float xStart = minDistance + j * (sectionWidth + minDistance); // Start point of segments
      float x = random(xStart, xStart + sectionWidth); // chose Xpos in segments randomly
      float y = i * (contentHeight / 7.0f) + (contentHeight / 7.0f) / 2; // Calculate Ypos
      staticCircles.ellipse(x,y,diameter, diameter );
      row.add(new PVector(x, y)); // Add postion to current row
    }
    circlesByRow.add(row);//Add current row to a row list
  }

  // Fix the top and bottom
  staticCircles.ellipse(embeddedCanvasWidth/2.0f, 0 * (contentHeight / 7.0f) + (contentHeight / 7.0f) / 2, diameter, diameter);
  ArrayList<PVector> topRow = new ArrayList<PVector>();
  topRow.add(new PVector(embeddedCanvasWidth/2.0f, 0 * (contentHeight / 7.0f) + (contentHeight / 7.0f) / 2));
  circlesByRow.add(0, topRow); 

  int circlesAtBottom = 3; 
  ArrayList<PVector> bottomRow = new ArrayList<PVector>();
  float bottomSectionWidth = embeddedCanvasWidth / (circlesAtBottom + 1); // Divide into segments
  for (int i = 1; i <= circlesAtBottom; i++) {
    float x = i * bottomSectionWidth; // Calculate the Xpos_bottom
    staticCircles.ellipse(x, 6 * (contentHeight / 7.0f) + (contentHeight / 7.0f) / 2, diameter, diameter); 
    bottomRow.add(new PVector(x, 6 * (contentHeight / 7.0f) + (contentHeight / 7.0f) / 2));
  }
  circlesByRow.add(bottomRow);

  // 设置连接线的颜色
  staticCircles.stroke(255); // 使用白色绘制连接线

  // 由于是从第一排向下连接，我们从第二排开始遍历每一排
  for (int i = 1; i < circlesByRow.size(); i++) {
      ArrayList<PVector> currentRow = circlesByRow.get(i); // 当前排的圆
      ArrayList<PVector> previousRow = circlesByRow.get(i - 1); // 上一排的圆

      for (PVector circle : currentRow) {
          // 由于规则限制，每个圆只能与上一排的一个圆连接
          PVector closest = null; // 找到最近的圆
          float closestDist = Float.MAX_VALUE; // 初始化最近距离为最大值

          // 遍历上一排的每个圆，找到最近的圆
          for (PVector prevCircle : previousRow) {
              float dist = PVector.dist(circle, prevCircle);
              if (dist < closestDist) {
                  closestDist = dist;
                  closest = prevCircle;
              }
          }

          // 绘制连接线，从最近的上一排圆连接到当前圆
          if (closest != null) {
              staticCircles.line(closest.x, closest.y, circle.x, circle.y);
          }
      }
  }


  staticCircles.endDraw();
}

public void draw() {
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

public void drawStatusInfo() {
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

public void drawTextBox() {
  fill(255);
  rect(325, 300, 300, 100); 
  textAlign(LEFT, TOP);
  fill(0);
  String textContent = "Scroll down or using ↑/↓ on keyboard to preview the route to top of Tower;\nClick to choose the start node or next node;\nUsing move point to move up;\nDouble click to close Tutorial.";
  text(textContent, 325, 300, 300, 100); 
}




// The visible dimensions of the embedded canvas
int embeddedCanvasWidth = 500;
int embeddedCanvasHeight = 650;

// The content height of the embedded canvas is taller than its actual height, allowing for scrolling
int contentHeight = 1200;

public void drawEmbeddedCanvas() {
  // Set the visible range of the embedded canvas based on the scroll offset
  int canvasX = width/2 - embeddedCanvasWidth/2;
  int canvasY = height/2 - embeddedCanvasHeight/2;
    
  // Save the current drawing state
  pushMatrix();
  pushStyle();
  
  // Set the clipping area to only display a portion of the embedded canvas's content
  clip(canvasX, canvasY, embeddedCanvasWidth, embeddedCanvasHeight);
  
  // Draw the actual content
  //translate(canvasX, canvasY - scrollOffset);
  //fill(200);
  //rect(0, 0, embeddedCanvasWidth, contentHeight); // Assume the content is a gray rectangle
  
  // Display the corresponding part of the image based on the scroll offset
  image(desertImage, canvasX, canvasY - scrollOffset, embeddedCanvasWidth, desertImage.height);
  // 根据滚动偏移量调整staticCircles的位置并显示
  image(staticCircles, canvasX, canvasY - scrollOffset);
  // Restore the previous drawing state
  popStyle();
  popMatrix();
}
PGraphics staticCircles; // Used to store static node visual element
PImage desertImage;//Used to generate a nice background image of map
ArrayList<ArrayList<PVector>> circlesByRow = new ArrayList<ArrayList<PVector>>(); //Store the position of each node
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
  
  public void display() {
    stroke(255);
    fill(100);
    rect(x, y, w, h);
    textAlign(CENTER, CENTER);
    fill(255);
    text(label, x + w/2, y + h/2);
  }
  
  public boolean isMouseOver() {
    return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
  }
}
//Scroll offset used to control map display part
float scrollOffset = 0;

public void mouseWheel(MouseEvent event) {
  float e = event.getCount();
  scrollOffset += e*20; // Move 20 pixels each scrolling
  scrollOffset = constrain(scrollOffset, 0, contentHeight - embeddedCanvasHeight);
}

public void keyPressed() {
  if (key == CODED) {
    if (keyCode == UP) {
      scrollOffset -= 20;
    } else if (keyCode == DOWN) {
      scrollOffset += 20;
    }
    scrollOffset = constrain(scrollOffset, 0, contentHeight - embeddedCanvasHeight);
  }
}

public void mousePressed() {
  for (int i = 0; i < buttons.length; i++) {
    if (buttons[i].isMouseOver()) {
      println("Button " + (i + 1) + " clicked: " + buttons[i].label);
      if (buttons[i].label.equals("Tutorial")) {
        displayTextBox = !displayTextBox; // Change the state
        break; // Don't check another operation
      }
    }
  }
}
// void calculateCirclePositions() {
//   int[] circlesPerRow = {1, 3, 4, 4, 5}; // 每排的圆数配置
//   float ySpacing = height / (rows + 1); // 计算y坐标的间距
  
//   for (int i = 0; i < rows; i++) {
//     yPositions[i] = (i + 1) * ySpacing;
//     float xSpacing = width / (circlesPerRow[i] + 1); // 计算x坐标的间距
//     ArrayList<PVector> thisRow = new ArrayList<PVector>();
    
//     for (int j = 0; j < circlesPerRow[i]; j++) {
//       float x = xSpacing * (j + 1);
//       thisRow.add(new PVector(x, yPositions[i]));
//     }
//     circlePositions.add(thisRow);
//   }
// }

// void drawCirclesAndLines() {
//   staticCircles.beginDraw();
//   staticCircles.background(desertImage); // 使用沙漠背景
//   staticCircles.fill(0, 102, 153);

//   // 绘制圆
//   for (ArrayList<PVector> row : circlePositions) {
//     for (PVector circle : row) {
//       staticCircles.ellipse(circle.x, circle.y, circleDiameter, circleDiameter);
//     }
//   }

//   // 绘制连接线
//   // [此处添加连接线的绘制逻辑，根据circlePositions中的圆的位置]

//   staticCircles.endDraw();
// }


  public void settings() { size(960, 820); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GameState_Map" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
