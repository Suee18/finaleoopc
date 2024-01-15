/*Class sequence 
 * -Data members of widest scope
 * -log in process methods 
 * -sign up process methods 
 * -Main menu methods (Where nour will event hadle)
 * -Design methods{ you can use ->createPurpleButton("Title on button")
 *  and ->setBackgroundImage(Layout) methods }
 */
package pack1;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.stage.Stage;

public class CashierGUI extends Application {
    // Cashier class object
    private Cashier c;
    // GUI Members

    // GUI Members
    /* adam */
    public TextField Add;
    public String AddedProduct;
    public TextField REM;
    public String RemovedProduct;
    public double totalPayment;

    private Scene viewProductsScene;
  
    public Stage primaryStage;
    private VBox loginPanel;
    private VBox signupPanel;
    TextField cloginUNTextfield = new TextField();
    TextField cloginPTextField = new PasswordField();
    Label cloggedInStatus = new Label();
    int loginAttempts = 0;
    int maxLoginAttempts = 3;
    TextField csignUpUNField = new TextField();
    TextField cSignuppasswordField = new PasswordField();
    TextField cpasswordConfirmField = new PasswordField();
    Label signupStatusLabel = new Label();

    Products p = new Products("shorts", 11);
    Products p2 = new Products("tshirt", 22);

    String backGroundPath = "file:C:/Users/shex/Downloads/pexels-codioful-(formerly-gradienta)-7130470.jpg";
    String homeIconPath = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-08_052643-removebg-preview (1).png";
    String cartIconPath = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-07_024257-removebg-preview.png";
    String historyIconPath = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-07_024302-removebg-preview (1).png";

    public CashierGUI() {
        c = new Cashier();
        c.readFromFile();
        c.readfromList();
        Products.fillProducts(p);
        Products.fillProducts(p2);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Registering");

        Button loginButton = createPurpleButton("Log-in");
        Button signUpButton = createPurpleButton("Sign-up");

        loginButton.setOnAction(e -> slideLoginPanel());
        signUpButton.setOnAction(e -> slideSignupPanel());

        VBox buttonBox = new VBox(10, loginButton, signUpButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 20, 20, 20));

        loginPanel = createLoginPanel();
        loginPanel.setTranslateX(-800);

        signupPanel = createSignupPanel();
        signupPanel.setTranslateX(800);

        setBackgroundImage(buttonBox);

        StackPane root = new StackPane();
        root.getChildren().addAll(buttonBox, loginPanel, signupPanel);

        Scene scene = new Scene(root, 800, 450);
        primaryStage.setScene(scene);
       
        primaryStage.show();
    }

    private VBox createLoginPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);");

        VBox loginFieldsScene = createLoginFieldsScene();
        panel.getChildren().add(loginFieldsScene);
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    private void slideLoginPanel() {
        if (signupPanel.getTranslateX() == 510) {
            TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), signupPanel);
            t1.setToX(800);
            t1.setOnFinished(e -> {
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
                boolean isVisible = loginPanel.getTranslateX() == 0;
                double targetTranslation = isVisible ? -loginPanel.getWidth() : -510;
                tt.setToX(targetTranslation);
                tt.play();
            });
            t1.play();
        } else {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
            boolean isVisible = loginPanel.getTranslateX() == 0;
            double targetTranslation = isVisible ? -loginPanel.getWidth() : -510;
            tt.setToX(targetTranslation);
            tt.play();
        }
    }

    private VBox createSignupPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-background-color: rgba(100, 100, 100, 0.3);");

        VBox signupFieldsScene = createSignupFieldsScene();
        panel.getChildren().add(signupFieldsScene);
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    private void slideSignupPanel() {
        if (loginPanel.getTranslateX() == -510) {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
            tt.setToX(-loginPanel.getWidth());
            tt.setOnFinished(e -> {
                TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), signupPanel);
                boolean isVisible = signupPanel.getTranslateX() == 0;
                double targetTranslation = isVisible ? signupPanel.getWidth() : 510;
                t1.setToX(targetTranslation);
                t1.play();
            });
            tt.play();
        } else {
            TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), signupPanel);
            boolean isVisible = signupPanel.getTranslateX() == 0;
            double targetTranslation = isVisible ? signupPanel.getWidth() : 510;
            t1.setToX(targetTranslation);
            t1.play();
        }
    }

    private VBox createLoginFieldsScene() {
        VBox loginFieldsScene = new VBox(10);
        cloginUNTextfield = new TextField();
        cloginUNTextfield.setPromptText("Enter your username");
        cloginUNTextfield.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        cloginPTextField = new PasswordField();
        cloginPTextField.setPromptText("Enter your password");
        cloginPTextField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        cloggedInStatus = new Label();
        cloggedInStatus.setWrapText(true);

        Button lIButton = createPurpleButton("Log In");
        lIButton.setOnAction(e -> handleLogin());

        loginFieldsScene.getChildren().addAll(cloginUNTextfield, cloginPTextField, lIButton, cloggedInStatus);

        VBox.setMargin(cloginUNTextfield, new Insets(50, 40, 10, 540));
        VBox.setMargin(cloginPTextField, new Insets(0, 40, 10, 540));
        VBox.setMargin(lIButton, new Insets(0, 60, 10, 570));
        VBox.setMargin(cloggedInStatus, new Insets(0, 10, 0, 550));

        return loginFieldsScene;
    }

    private VBox createSignupFieldsScene() {
        VBox signupFieldsScene = new VBox(10);
        csignUpUNField = new TextField();
        csignUpUNField.setPromptText("Enter your username");
        csignUpUNField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        cSignuppasswordField = new PasswordField();
        cSignuppasswordField.setPromptText("Enter your password");
        cSignuppasswordField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        cpasswordConfirmField = new PasswordField();
        cpasswordConfirmField.setPromptText("Confirm your password");
        cpasswordConfirmField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        Button signUpButton = createPurpleButton("Sign-up");
        signUpButton.setOnAction(e -> handleSignup());

        signupStatusLabel = new Label("");
        signupStatusLabel.setWrapText(true);

        signupFieldsScene.getChildren().addAll(csignUpUNField, cSignuppasswordField, cpasswordConfirmField,
                signUpButton, signupStatusLabel);

        VBox.setMargin(csignUpUNField, new Insets(20, 540, 10, 40));
        VBox.setMargin(cSignuppasswordField, new Insets(0, 540, 10, 40));
        VBox.setMargin(cpasswordConfirmField, new Insets(0, 540, 10, 40));
        VBox.setMargin(signUpButton, new Insets(0, 60, 0, 50));
        VBox.setMargin(signupStatusLabel, new Insets(0, 540, 0, 70));

        return signupFieldsScene;
    }

    private void handleSignup() {
        String cu = csignUpUNField.getText();
        String cp = cSignuppasswordField.getText();
        String ccp = cpasswordConfirmField.getText();

        boolean repeatedUserName = c.checkUserName(cu);

        if (cp.equals(ccp) && !repeatedUserName) {
            c.signUp(cu, cp);
            c.writeToFile();
            signupStatusLabel.setText("Signed-up successfully");
        } else if (repeatedUserName) {
            signupStatusLabel.setText("User Name: (" + cu + ") already taken. Try another user name.");
            csignUpUNField.clear();
        } else if (!cp.equals(ccp)) {
            signupStatusLabel.setText("Passwords don't match, try again.");
            cpasswordConfirmField.clear();
            cSignuppasswordField.clear();
        }
    }

    private void handleLogin() 
    {
        if (loginAttempts < maxLoginAttempts) {
            String usern = cloginUNTextfield.getText();
            String pa = cloginPTextField.getText();

            boolean loginSuccess = c.checkUserData(usern, pa);
            loginAttempts++;

            if (loginSuccess) {
                cloggedInStatus.setText("Login successful!");
                openCashierMainMenu();
            } else {
                cloggedInStatus.setText(
                        "Login failed. Check your credentials. Attempts left: " + (maxLoginAttempts - loginAttempts));

                // Clear text fields if login fails
                cloginUNTextfield.clear();
                cloginPTextField.clear();
            }

            if (!loginSuccess && loginAttempts == maxLoginAttempts) {
                // Closing when exceeding trials
                primaryStage.close();
            }
        }
    }
    






    /* END OF LOG IN AND SIGN UP */
    /*********************************************************************************************************
     * *******************************************************************************************************
     * *****************************************************************************
     *//* CASHIER MAIN MENU */
    private void openCashierMainMenu() {
        FlowPane cashierOptions = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        cashierOptions.setAlignment(Pos.CENTER);

        primaryStage.setTitle("Cashier Main Menu");

        /* button1: Manage carts */
        Button manageCarts = createMainMenuButton("Manage carts", cartIconPath);
        manageCarts.setOnAction(e -> showManageCartsScene());

        cashierOptions.getChildren().addAll(manageCarts);
        VBox mainMenuLayout = new VBox(10, cashierOptions);
        mainMenuLayout.setAlignment(Pos.CENTER);
        setBackgroundImage(mainMenuLayout);

        Scene mainMenuScene = new Scene(mainMenuLayout, 800, 450);
        primaryStage.setScene(mainMenuScene);
       
        primaryStage.show();
    }

    /*
     * Adam's Event handling
     * //
     */
    private void showManageCartsScene() {
        primaryStage.setTitle("Manage Carts");

        BorderPane manageCartsElements = new BorderPane();
        setBackgroundImage(manageCartsElements);

        // home button
        Button h = HOME();
        h.setOnAction(e -> openCashierMainMenu());
        BorderPane.setAlignment(h, Pos.TOP_RIGHT);
        BorderPane.setMargin(h, new Insets(10, 10, 0, 0));

        // managing carts buttons
        Button addToCart = createPurpleButton("Add to cart");
        addToCart.setOnAction(e -> handleAdd());
        Add = new TextField();

        Button removeFromCart = createPurpleButton("Remove from cart");
        removeFromCart.setOnAction(e -> handleRemove());
        REM = new TextField();

        Button calculatePayment = createPurpleButton("Calculate Payment");
        calculatePayment.setOnAction(e -> handlePayment());
        

        Button DisplayCart = createPurpleButton("Display cart");
        DisplayCart.setOnAction(e -> handleDisplay());
        DisplayCart.setOnAction(e -> viewProductList());

        Button deleteCart = createPurpleButton("Delete a cart");
        deleteCart.setOnAction(e -> handleClear());

        HBox H = new HBox(15, addToCart, Add);
        HBox H2 = new HBox(15, removeFromCart, REM);

        VBox buttonsVBox = new VBox(10, H, H2, calculatePayment, DisplayCart, deleteCart);
        buttonsVBox.setPadding(new Insets(0, 20, 0, 20));
        buttonsVBox.setAlignment(Pos.CENTER_LEFT);

        FlowPane pane = new FlowPane();
        ObservableList<Products> productsList = FXCollections.observableArrayList(Products.productsDataList);
        ListView<Products> listview = new ListView<>(productsList);
        pane.getChildren().addAll(listview);

        manageCartsElements.setTop(h);
        manageCartsElements.setLeft(buttonsVBox);
        manageCartsElements.setRight(pane);

        Scene manageCartsScene = new Scene(manageCartsElements, 800, 450);
        primaryStage.setScene(manageCartsScene);
        c.writeArrayListtoFile();
        primaryStage.show();
    }






    public void handleAdd() {
        AddedProduct = Add.getText();
        c.addProductToCart(AddedProduct);
    }

    public void handleRemove() {
        RemovedProduct = REM.getText();
        c.removeProductFromCart(RemovedProduct);
    }

 
    public void handleClear() {
        c.deleteCart();
    }

    public void handleDisplay() {
        c.displayCarts();
    }

    public void viewProductList() 
    {
        FlowPane pane = new FlowPane();
        ObservableList<Products> productsList = FXCollections.observableArrayList(c.cartProducts);
        ListView<Products> listview = new ListView(productsList);
        Button backk=new Button("Back<-");
        // {primaryStage.setScene(RemoveUserSc);}
        backk.setOnAction(e->showManageCartsScene());
        pane.getChildren().addAll(listview,backk);
        viewProductsScene = new Scene(pane);
        primaryStage.setScene(viewProductsScene);
        primaryStage.show();

    }

     
    public void handlePayment() {

        totalPayment = c.calculatePayment();
        Receipt(c.calculatePayment());
    }

    private void Receipt(double totalPayment) 
    {
    VBox receipt = new VBox(10);
    receipt.setAlignment(Pos.CENTER);

    Label rLbl = new Label("Total Payment: $" + totalPayment);
     Button backButton = new Button("Back to Cart");
     backButton.setOnAction(e -> showManageCartsScene());   

    receipt.getChildren().addAll(rLbl,backButton);

    Scene receiptScene = new Scene(receipt, 800, 450);
    primaryStage.setScene(receiptScene);
    primaryStage.show();


}

    /*
     * Adam's Event handling
     */

    /***************************************
     * DESIGN****************************************************************
     * *********************************************************************************************
     * ************************************************************************
     */
    private Button createPurpleButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setStyle(
                "-fx-background-color: #615BA2; -fx-text-fill: white; -fx-font-size: 20; -fx-pref-width: 200; -fx-pref-height: 50; -fx-background-radius: 25;");
        return button;
    }

    private void setBackgroundImage(Region region) {
        Screen screen = Screen.getPrimary();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(backGroundPath),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight(), false,
                        false, false, true));
        Background background = new Background(backgroundImage);
        region.setBackground(background);
    }

    private Button createMainMenuButton(String buttonTitle, String iconPNGPath) {
        // Size variables
        double buttonWidth = 170;
        double buttonHeight = 170;
        double imageWidth = 100;
        double imageHeight = 150;

        Button tansButton = new Button(buttonTitle);
        tansButton.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);" + "-fx-text-fill: black;"
                + "-fx-font-size: 18px;" + "-fx-font-family:  Open Sans;");
        tansButton.setMinSize(buttonWidth, buttonHeight);
        tansButton.setMaxSize(buttonWidth, buttonHeight);
        ImageView iconOnButton = new ImageView(new Image(iconPNGPath));
        iconOnButton.setFitWidth(imageWidth);
        iconOnButton.setFitHeight(imageHeight);
        iconOnButton.setPreserveRatio(true);
        tansButton.setGraphic(iconOnButton);
        tansButton.setContentDisplay(ContentDisplay.TOP);

        return tansButton;
    }

    private Button HOME() {
        Button homeButton = new Button("");

        ImageView iconOnButton = new ImageView(new Image(homeIconPath));
        iconOnButton.setFitWidth(60);
        iconOnButton.setFitHeight(60);
        iconOnButton.setPreserveRatio(true);
        homeButton.setGraphic(iconOnButton);
        homeButton.setContentDisplay(ContentDisplay.CENTER);
        // css circle button
        homeButton.setStyle(
                "-fx-shape: \"M 15 15 A 15 15 0 1 0 15.001 15 Z\"; -fx-alignment: center;-fx-background-color: rgba(100, 100, 100, 0.2);");

        return homeButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
