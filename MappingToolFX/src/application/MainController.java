package application;
/**
Alonso
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;

public class MainController implements Initializable {

	static List<Node> mapNodes = new ArrayList<Node>();
	static List<Node> map1TransitionNodes = new ArrayList<Node>();
	static List<Node> map2TransitionNodes = new ArrayList<Node>();


	File file = new File("C://Users/Alonso/Documents/Stratton/map.png");
	final Image mapImage = new Image(file.toURI().toString());
	@FXML
	private Pane pane = new Pane();

	private BufferedImage img;

	private Image image;
	@FXML
	private ImageView mapView = new ImageView();

	@FXML
	private Label name = new Label();

	@FXML
	private Label description = new Label();


	@FXML
	private TextField nodeName = new TextField();

	@FXML
	private TextField nodeDescription = new TextField();

	@FXML
	private Canvas imageCanvas = new Canvas();

	@FXML
	private Button genSupermap = new Button();

	@FXML
	private  Button loadMap1 = new Button();

	@FXML
	private ComboBox<?> map1Dropdown = new ComboBox();

	private MenuItem addEdge = new MenuItem("Add Edge");

	private MenuItem deleteEdge = new MenuItem("Delete Edge");

	private MenuItem showEdges = new MenuItem("Show Edges");

	private MenuItem addNode = new MenuItem("Add Node");

	private MenuItem deleteNode = new MenuItem("Delete Node");
	@FXML
	private Button loadMap2 = new Button();

	@FXML
	private ComboBox<?> map2Dropdown = new ComboBox();

	@FXML
	private CheckBox isTransitionCheckbox = new CheckBox();

	@FXML
	private Button makeTransButton = new Button();

	@FXML
	private MenuButton nodeOptions = new MenuButton();

	private MenuItem modifyNode = new MenuItem("Modify Node");

	@FXML
	private MenuButton edgeOptions = new MenuButton();

	private int imageWidth = 0;
	private int imageHeight = 0;
	private int duplicateNode = 1;
	private int firstNodeLoc = 0;
	private int secondNodeLoc =  0;
	private volatile int numberClicks = 0;
	private boolean doOnce = true;
	private int currentNodeLoc = -1;
	private String nodeMapName;
	private static String path;

	private static boolean shouldAddNode = false;
	private static boolean shouldDeleteNode = false;
	private static boolean shouldAddEdge = false;
	private static boolean shouldDeleteEdge = false;
	private static boolean shouldModifyNode = false;
	private static boolean shouldShowEdges = false;
	private static boolean shouldMakeEdge = false;
	private static boolean shouldRemoveEdge = false;
	static volatile boolean oneSelected = false;
	static volatile boolean twoSelected = false;
	Node currentNode;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		pane.setStyle("-fx-background-color: #205080;");


		nodeOptions.getItems().remove(0);
		nodeOptions.getItems().remove(0);
		edgeOptions.getItems().remove(0);
		edgeOptions.getItems().remove(0);


		addNode.setOnAction(new EventHandler(){

			@Override
			public void handle(Event event) {

				shouldAddEdge = false;
				shouldDeleteEdge = false;
				shouldShowEdges = false;
				shouldAddNode = true;
				shouldDeleteNode = false;
				nodeOptions.setText("Adding Node");
				edgeOptions.setText("Edge Options");
				if(shouldAddNode){

					imageCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if(shouldAddNode){
								System.out.println("In Add");
								System.out.println(mapNodes);
								shouldDeleteNode = false;
								System.out.println(event.getX());
								System.out.println(event.getY());
								event.getX();
								event.getY();
								event.consume();

								GraphicsContext gc = imageCanvas.getGraphicsContext2D();
								//imageCanvas.getGraphicsContext2D().clearRect(0, 0, imageCanvas.getWidth(), imageCanvas.getHeight());
								if(doOnce){
									if(mapNodes.size() ==0){
										Node node = new Node("node",(int)event.getX()-5,(int)event.getY()-5,0,nodeMapName);
										mapNodes.add(node);
										gc.setFill(Color.RED);
										gc.fillOval(event.getX()-5, event.getY()-5, 10, 10);
										System.out.println(mapNodes);

										doOnce = false;
										System.out.println("Zero map Node");
									}
								} else{

									for(int i = 0; i < mapNodes.size();i++){
										if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
											System.out.println("Node already exists here");
											System.out.println("********1**********");
											System.out.println(mapNodes.get(i).xPos);
											System.out.println(mapNodes.get(i).yPos);
											System.out.println("Counter is: "+i);
											System.out.println(event.getX()-5);
											System.out.println(event.getY()-5);
											System.out.println("********1**********");
											duplicateNode++;
										}
									}
								}
								if(duplicateNode == 0){
									System.out.println("In duplicate node");
									Node node = new Node("node",(int)event.getX()-5,(int)event.getY()-5,0,"Stratton");
									mapNodes.add(node);
									gc.setFill(Color.RED);
									gc.fillOval(event.getX()-5, event.getY()-5, 10, 10);
									System.out.println(mapNodes);
								} else {
									duplicateNode = 0;
								}





							}



						}
					});		
				} else {
					System.out.println("Not clicking");
				}
			}
		});

		deleteNode.setOnAction(new EventHandler(){

			@Override
			public void handle(Event event) {

				shouldAddEdge = false;
				shouldDeleteEdge = false;
				shouldDeleteNode = true;
				shouldAddNode = false;
				nodeOptions.setText("Deleting Node");

				if(shouldDeleteNode){

					imageCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if(shouldDeleteNode){
								System.out.println("In Delete");

								shouldAddNode = false;




								System.out.println("Trying to delete");
								for(int i = 0; i < mapNodes.size();i++){
									if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
										System.out.println("Node already exists here");
										fixEdges(mapNodes.get(i));
										mapNodes.remove(i);
										clearCanvas();
										renderEdges();
										renderNodes();
										System.out.println(mapNodes);
									}
								}


							}

						}
					});		
				} else {
					System.out.println("Not clicking");
				}
			}
		});

		

		nodeOptions.getItems().add(addNode);
		nodeOptions.getItems().add(deleteNode);
		nodeOptions.getItems().add(modifyNode);

		edgeOptions.getItems().add(addEdge);
		edgeOptions.getItems().add(deleteEdge);
		edgeOptions.getItems().add(showEdges);


		isTransitionCheckbox.setOnAction(new EventHandler(){

			@Override
			public void handle(Event arg0) {
				mapNodes.get(currentNodeLoc).isTransitionNode = !mapNodes.get(currentNodeLoc).isTransitionNode;
			}
			
		});
		
		
		nodeDescription.setOnAction(new EventHandler(){

			@Override
			public void handle(Event event) {
				if(currentNodeLoc !=-1){
					mapNodes.get(currentNodeLoc).description = nodeDescription.getText();
					nodeDescription.setStyle("-fx-text-fill: green;-fx-font-weight: bold;");

				} else {
					nodeDescription.setStyle("-fx-text-fill: red;-fx-font-weight: bold;");
				}
			}

		});


		nodeName.setOnAction(new EventHandler(){

			@Override
			public void handle(Event event) {
				if(currentNodeLoc !=-1){
					mapNodes.get(currentNodeLoc).nodeName = nodeName.getText();
					nodeName.setStyle("-fx-text-fill: green;-fx-font-weight: bold;");

				} else {
					nodeName.setStyle("-fx-text-fill: red;-fx-font-weight: bold;");
				}
			}

		});

		modifyNode.setOnAction(new EventHandler(){

			@Override
			public void handle(Event event) {
			
				shouldAddEdge = false;
				shouldAddNode = false;
				shouldDeleteNode = false;
				shouldDeleteEdge = false;
				shouldShowEdges = false;
				shouldModifyNode = true;
				nodeOptions.setText("Modifying Node");
				imageCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						
						clearCanvas();
						renderNodes();
						renderEdges();
						nodeName.setStyle(null);
						nodeDescription.setStyle(null);
						for(int i = 0; i < mapNodes.size();i++){
							if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
								System.out.println("Clicked a node");
								System.out.println(mapNodes.get(i).nodeName);
								showNodeData();
								currentNodeLoc = i;
								nodeName.setText(mapNodes.get(i).nodeName);
								nodeDescription.setText(mapNodes.get(i).description);
								if(mapNodes.get(i).isTransitionNode){
									isTransitionCheckbox.setSelected(true);
								} else {
									isTransitionCheckbox.setSelected(false);
								}
								GraphicsContext gc = imageCanvas.getGraphicsContext2D();
								gc.setFill(Color.GOLD);
								gc.fillOval((double)mapNodes.get(i).xPos,(double)mapNodes.get(i).yPos, 10, 10);
								gc.setFill(Color.RED);
								gc.fillOval((double)mapNodes.get(i).xPos+1.5,(double)mapNodes.get(i).yPos+1.5, 7, 7);
							}
						}
					}
				});		

			}
		});



		loadMap1.setOnAction(new EventHandler() {

			@Override
			public void handle(Event e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					path = path+"\\";
					nodeMapName = selectedFile.getName();
					try {
						img = ImageIO.read(new File(Paths.get(path+"map.png").toString()));
						image = SwingFXUtils.toFXImage(img, null);
						loadMap1.setText(selectedFile.getName());
						imageWidth = (int) image.getWidth();
						imageHeight = (int) image.getHeight();
						mapView.setFitHeight(imageHeight);
						mapView.setFitHeight(imageWidth);
						mapView.setImage(image);
						System.out.println(imageWidth);
						nodeOptions.setLayoutX(1020);
						edgeOptions.setLayoutX(1020);
						nodeName.setLayoutY(550);
						nodeDescription.setLayoutY(550);
						name.setLayoutY(nodeName.getLayoutY()-17);
						imageCanvas.setWidth(mapView.getFitWidth());
						imageCanvas.setHeight(mapView.getFitHeight());
						description.setLayoutY(nodeDescription.getLayoutY()-17);
						isTransitionCheckbox.setLayoutY(nodeName.getLayoutY()+4);						
						genSupermap.setLayoutX(1020);
						genSupermap.setLayoutY(nodeName.getLayoutY()-21);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}   
		});



		loadMap2.setOnAction(new EventHandler(){

			@Override
			public void handle(Event e) {
				String path;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					path = path+"\\";
					try {
						img = ImageIO.read(new File(Paths.get(path+"map.png").toString()));
						image = SwingFXUtils.toFXImage(img, null);
						loadMap2.setText(selectedFile.getName());
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
			
		});
		
		addEdge.setOnAction(new EventHandler(){

			@Override
			public void handle(Event arg0) {
				shouldAddEdge = true;
				shouldAddNode = false;
				shouldDeleteNode = false;
				shouldDeleteEdge = false;
				shouldShowEdges = false;
				edgeOptions.setText("Adding Edge");
				nodeOptions.setText("Node Options");
				numberClicks = 0;
				if(shouldAddEdge){

					imageCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {


							if(shouldAddEdge){
								System.out.println("***********");
								System.out.println(numberClicks);
								System.out.println(firstNodeLoc);
								System.out.println(secondNodeLoc);
								System.out.println("***********");

								System.out.println("Trying to add edge");
								if(numberClicks == 0){
									for(int i = 0; i < mapNodes.size();i++){
										if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
											System.out.println("CLICKED THE FIRST NODE");
											firstNodeLoc = i;
											numberClicks += 1;
											GraphicsContext gc = imageCanvas.getGraphicsContext2D();
											//gc.clearRect(event.getX()-5, event.getY()-5, 30, 30);
											gc.setFill(Color.GOLD);
											gc.fillOval((double)mapNodes.get(i).xPos,(double)mapNodes.get(i).yPos, 10, 10);
											gc.setFill(Color.RED);
											gc.fillOval((double)mapNodes.get(i).xPos+1.5,(double)mapNodes.get(i).yPos+1.5, 7, 7);

										} 
									}
								} else if(numberClicks != 0){
									for(int i = 0; i < mapNodes.size();i++){
										if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
											System.out.println("CLICKED THE SECOND NODE");
											secondNodeLoc = i;
											numberClicks = 2;
											shouldMakeEdge = true;
											GraphicsContext gc = imageCanvas.getGraphicsContext2D();

											gc.setFill(Color.GOLD);
											gc.fillOval((double)mapNodes.get(i).xPos,(double)mapNodes.get(i).yPos, 10, 10);
											gc.setFill(Color.RED);
											gc.fillOval((double)mapNodes.get(i).xPos+1.5,(double)mapNodes.get(i).yPos+1.5, 7, 7);
										} 
									}
								}
							}

							if(shouldMakeEdge && firstNodeLoc != secondNodeLoc){
								shouldMakeEdge = false;

								System.out.println("Making an edge at " +firstNodeLoc + " "+secondNodeLoc);

								if(!mapNodes.get(firstNodeLoc).neighbors.contains(mapNodes.get(secondNodeLoc))){
									mapNodes.get(firstNodeLoc).neighbors.add(mapNodes.get(secondNodeLoc));
									mapNodes.get(secondNodeLoc).neighbors.add(mapNodes.get(firstNodeLoc));
									System.out.println(mapNodes.get(firstNodeLoc).neighbors);
									GraphicsContext gc = imageCanvas.getGraphicsContext2D();
									gc.setFill(Color.WHEAT);
									gc.strokeLine(mapNodes.get(firstNodeLoc).xPos+5, mapNodes.get(firstNodeLoc).yPos+5, mapNodes.get(secondNodeLoc).xPos+5, mapNodes.get(secondNodeLoc).yPos+5);
									gc.setFill(Color.RED);
									gc.fillOval((double)mapNodes.get(firstNodeLoc).xPos,(double)mapNodes.get(firstNodeLoc).yPos, 10, 10);
									gc.fillOval((double)mapNodes.get(secondNodeLoc).xPos,(double)mapNodes.get(secondNodeLoc).yPos, 10, 10);

								}
								firstNodeLoc = 0;
								secondNodeLoc = 0;
								numberClicks = 0;
								System.out.println("RESTARTING");
								System.out.println("RESTARTING");
								System.out.println("RESTARTING");
								System.out.println("RESTARTING");
								System.out.println("RESTARTING");

							} else {
								numberClicks = 1;
								secondNodeLoc = 0;
								shouldMakeEdge = false;
							}



						}
					});		
				} else {
					System.out.println("Not clicking");
				}

			}    

		});


		genSupermap.setOnAction(new EventHandler(){

			@Override
			public void handle(Event event) {

					saveMapNodes(Paths.get(path).toString()+"\\mapNodes.csv");

					
			}
			
		});



		deleteEdge.setOnAction(new EventHandler(){

			@Override
			public void handle(Event arg0){
				System.out.println("Starting event handler for delete");
				oneSelected = false;
				twoSelected = false;
				shouldAddEdge = false;
				shouldAddNode = false;
				shouldDeleteNode = false;
				shouldDeleteEdge = true;
				shouldShowEdges = false;
				edgeOptions.setText("Deleting Edge");
				nodeOptions.setText("Node Options");




				if(shouldDeleteEdge){


					imageCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							System.out.println(oneSelected);
							System.out.println(twoSelected);
							System.out.println(event.getClickCount());
							event.consume();
							if(!oneSelected){
								for(int i = 0; i < mapNodes.size();i++){
									if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
										System.out.println("CLICKED THE FIRST NODE");
										oneSelected = true;
										firstNodeLoc = i;
										break;
										//									firstNodeLoc = i;
										//									numberClicks =2;
										//									GraphicsContext gc = imageCanvas.getGraphicsContext2D();
										//									gc.setFill(Color.GOLD);
										//									gc.fillOval((double)mapNodes.get(i).xPos,(double)mapNodes.get(i).yPos, 10, 10);
										//									gc.setFill(Color.RED);
										//									gc.fillOval((double)mapNodes.get(i).xPos+1.5,(double)mapNodes.get(i).yPos+1.5, 7, 7);
										//									event.consume();	
									} 
								}
								System.out.println(oneSelected);
								System.out.println(twoSelected);
							} else if(oneSelected){
								for(int i = 0; i < mapNodes.size();i++){
									if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
										System.out.println("CLICKED THE SECOND NODE");
										twoSelected = true;
										secondNodeLoc = i;
										break;
									}
								}
							}

							if(oneSelected && twoSelected){
								System.out.println("Selected two nodes");
								oneSelected = false;
								twoSelected = false;
								if(mapNodes.get(firstNodeLoc).neighbors.contains(mapNodes.get(secondNodeLoc)) && mapNodes.get(secondNodeLoc).neighbors.contains(mapNodes.get(firstNodeLoc))){
									mapNodes.get(firstNodeLoc).neighbors.remove(mapNodes.get(secondNodeLoc));
									mapNodes.get(secondNodeLoc).neighbors.remove(mapNodes.get(firstNodeLoc));
									clearCanvas();
									renderEdges();
									renderNodes();
									System.out.println(mapNodes.get(firstNodeLoc).neighbors);
								}
							}


						}

					});		
				} 


			}
			//			public void handle(Event arg0) {
			//				shouldAddEdge = false;
			//				shouldAddNode = false;
			//				shouldDeleteNode = false;
			//				shouldDeleteEdge = true;
			//				shouldShowEdges = false;
			//				edgeOptions.setText("Deleting Edge");
			//				nodeOptions.setText("Node Options");
			//				firstNodeLoc = -1;
			//				secondNodeLoc = -1;
			//
			//				numberClicks = 0;
			//				System.out.println(numberClicks);
			//				if(shouldDeleteEdge){
			//					imageCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			//						@Override
			//						public void handle(MouseEvent event) {
			//
			//
			//							if(shouldDeleteEdge){
			//								System.out.println("***********");
			//								System.out.println(numberClicks);
			//								System.out.println(firstNodeLoc);
			//								System.out.println(secondNodeLoc);
			//								System.out.println("***********");
			//
			//								System.out.println("Trying to delete edge");
			//								if(numberClicks == 1 && firstNodeLoc==-1){
			//									for(int i = 0; i < mapNodes.size();i++){
			//										if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
			//											System.out.println("CLICKED THE FIRST NODE");
			//											firstNodeLoc = i;
			//											numberClicks =2;
			//											GraphicsContext gc = imageCanvas.getGraphicsContext2D();
			//											//gc.clearRect(event.getX()-5, event.getY()-5, 30, 30);
			//											gc.setFill(Color.GOLD);
			//											gc.fillOval((double)mapNodes.get(i).xPos,(double)mapNodes.get(i).yPos, 10, 10);
			//											gc.setFill(Color.RED);
			//											gc.fillOval((double)mapNodes.get(i).xPos+1.5,(double)mapNodes.get(i).yPos+1.5, 7, 7);
			//											event.consume();	
			//											break;
			//										} 
			//									}
			//									System.out.println("Number Clicks is: " +numberClicks);
			//								} 
			//								
			//								
			//								System.out.println("Number Clicks is: " +numberClicks);
			//								System.out.println(firstNodeLoc);
			//								
			//									if(numberClicks !=1 && firstNodeLoc !=-1){
			//									for(int i = 0; i < mapNodes.size();i++){
			//										if(mapNodes.get(i).xPos >=event.getX()-5-10&& mapNodes.get(i).xPos <=event.getX()-5+10 && mapNodes.get(i).yPos>=event.getY()-5-10 && mapNodes.get(i).yPos<=event.getY()-5+10){
			//											System.out.println("CLICKED THE SECOND NODE");
			//											secondNodeLoc = i;
			//											numberClicks = 3;
			//											shouldRemoveEdge = true;
			//											GraphicsContext gc = imageCanvas.getGraphicsContext2D();
			//
			//											gc.setFill(Color.GOLD);
			//											gc.fillOval((double)mapNodes.get(i).xPos,(double)mapNodes.get(i).yPos, 10, 10);
			//											gc.setFill(Color.RED);
			//											gc.fillOval((double)mapNodes.get(i).xPos+1.5,(double)mapNodes.get(i).yPos+1.5, 7, 7);
			//										} 
			//									}
			//								}
			//							}
			//
			//							if(shouldRemoveEdge && firstNodeLoc != secondNodeLoc){
			//								shouldRemoveEdge = false;
			//
			//								System.out.println("Removing an edge at " +firstNodeLoc + " "+secondNodeLoc);
			//
			//								if(mapNodes.get(firstNodeLoc).neighbors.contains(mapNodes.get(secondNodeLoc))){
			//									mapNodes.get(firstNodeLoc).neighbors.remove(mapNodes.get(secondNodeLoc));
			//									mapNodes.get(secondNodeLoc).neighbors.remove(mapNodes.get(firstNodeLoc));
			//
			//									
			//									
			//									System.out.println(mapNodes.get(firstNodeLoc).neighbors);
			//									GraphicsContext gc = imageCanvas.getGraphicsContext2D();
			//									gc.setFill(Color.WHEAT);
			//									gc.strokeLine(mapNodes.get(firstNodeLoc).xPos+5, mapNodes.get(firstNodeLoc).yPos+5, mapNodes.get(secondNodeLoc).xPos+5, mapNodes.get(secondNodeLoc).yPos+5);
			//									gc.setFill(Color.RED);
			//									//gc.fillOval((double)mapNodes.get(firstNodeLoc).xPos,(double)mapNodes.get(firstNodeLoc).yPos, 10, 10);
			//									//gc.fillOval((double)mapNodes.get(secondNodeLoc).xPos,(double)mapNodes.get(secondNodeLoc).yPos, 10, 10);
			//									clearCanvas();
			//									renderNodes();
			//									renderEdges();
			//								}
			//								firstNodeLoc = 0;
			//								secondNodeLoc = 0;
			//								numberClicks = 0;
			//								System.out.println("RESTARTING");
			//								System.out.println("RESTARTING");
			//								System.out.println("RESTARTING");
			//								System.out.println("RESTARTING");
			//								System.out.println("RESTARTING");
			//
			//							} else {
			//								System.out.println("IN ELSE");
			//								numberClicks = 2;
			//								secondNodeLoc = 0;
			//								shouldRemoveEdge = false;
			//							}
			//
			//
			//
			//						}
			//					});		
			//				} else {
			//					System.out.println("Not clicking");
			//				}
			//
			//			}    

		});




















		showEdges.setOnAction(new EventHandler(){

			@Override
			public void handle(Event arg0) {
				clearCanvas();
				renderEdges();
			}

		});

	}


	protected void clearCanvas(){
		GraphicsContext gc = imageCanvas.getGraphicsContext2D();
		gc.clearRect(0,0,imageCanvas.getWidth(),imageCanvas.getHeight());	
	}

	protected void renderNodes(){
		for(Node n: mapNodes){
			GraphicsContext gc = imageCanvas.getGraphicsContext2D();
			gc.setFill(Color.RED);
			gc.fillOval(n.xPos, n.yPos, 10, 10);
		}


	}

	protected void fixEdges(Node node){
		for (Node n: mapNodes){
			if(n.neighbors.contains(node)){
				n.neighbors.remove(node);
				System.out.println("Removed");
			}
		}
	}

	
	protected void showNodeData(){
		System.out.println("*************************");

		for(int i = 0; i < mapNodes.size();i++){
			System.out.println(mapNodes.get(i).nodeName);
			System.out.println(mapNodes.get(i).description);
			System.out.println(mapNodes.get(i).isTransitionNode);
		}

		System.out.println("*************************");

	}
	
	
	
	
	
	private static void saveMapNodes(String fileName){

		try
		{
			FileWriter writer = new FileWriter(fileName);


			for (int i = 0; i<mapNodes.size();i++){
				writer.append(mapNodes.get(i).nodeName);
				writer.append(',');
				writer.append(Integer.toString(mapNodes.get(i).xPos));
				writer.append(',');
				writer.append(Integer.toString(mapNodes.get(i).yPos));
				writer.append(',');
				writer.append(Integer.toString(mapNodes.get(i).zPos));
				writer.append(',');
				writer.append(mapNodes.get(i).map);
				writer.append(",");
				writer.append(mapNodes.get(i).description);
				writer.append("\n");
				System.out.println(mapNodes.get(i).map);
			}
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} 
	}
	
	protected void renderEdges(){
		List<Node> edgeNodes = new ArrayList<Node>();




		GraphicsContext gc = imageCanvas.getGraphicsContext2D();



		for(int i = 0; i < mapNodes.size()-1;i++){
			for(int j = 0; j <mapNodes.get(i).neighbors.size();j++){

				int x1 = mapNodes.get(i).xPos+5;
				int y1 = mapNodes.get(i).yPos+5;
				int x2 = mapNodes.get(i).neighbors.get(j).xPos+5;
				int y2 = mapNodes.get(i).neighbors.get(j).yPos+5;
				gc.setFill(Color.WHITE);
				gc.strokeLine(x1, y1, x2, y2);
			}

		}
	}



}
