
/*Class sequence 
 * -Data members of widest scope
 * -log in process methods 
 * -sign up process methods 
 * -Main menu methods (Where moe will event hadle)
 * -Design methods{ you can use ->createPurpleButton("Title on button")
 *  and ->setBackgroundImage(Layout) methods }
 */

package pack1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.stage.Stage;

public class AdminGUI extends Application {
    // confirmButton confirmButtonn, String u,p in log in String uSU,pSU
    public Stage primaryStage;
    private VBox loginPanel;
    private VBox SUpanel;
    private Admin admin;
    private TextField userNameTextField;
    private PasswordField passwordField;
    private Label loginStatusLabel;
    private int loginAttempts = 0;
    private TextField signupUserNameTextField;
    private PasswordField signUpPasswordField;
    private PasswordField passwordConfirmField;
    private Label signupStatusLabel;
    private final int MAX_LOGIN_ATTEMPTS = 4;

    /* !!DYNAMIC PATH */
    // String backGroundPath =
    // "file:C:/Users/shex/Downloads/pexels-codioful-(formerly-gradienta)-7130470.jpg";
    // String
    // backGroundPath="file:C:/Users/shex/Downloads/pexels-codioful-(formerly-gradienta)-7130540.jpg"
    // ;
    String backGroundPath = "file:C:/Users/shex/Downloads/pexels-codioful-(formerly-gradienta)-7130488.jpg";

    String homeIconPath = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-08_052643-removebg-preview (1).png";
    String cartIconPath = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-07_024257-removebg-preview.png";
    String ordersTrendIcon = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-07_000922-removebg-preview.png";
    String usersGraphIcon = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-07_000913-removebg-preview (1).png";
    String manageUsersIcon = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-06_202110-removebg-preview.png";
    String productsIcon = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-07_000906-removebg-preview.png";
    String truckOnmanageSuppliers = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-06_202843-removebg-preview.png";
    String iconOn_viewListOfSuppliersButton = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-06_202843-removebg-preview.png";
    String iconOn_SuppliersTrendsButton = "file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-06_202843-removebg-preview.png";

    public AdminGUI() {
        admin = new Admin();
        admin.readFromFile();
    }

    // LOG IN OR SIGN UP BUTTONS
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Registering");

        Button loginButton = createPurpleButton("Log-in");
        Button signUpButton = createPurpleButton("Sign-up");

        loginButton.setOnAction(e -> slideLogInPanel());
        signUpButton.setOnAction(e -> slideSignUpPanel());

        VBox buttonBox = new VBox(10, loginButton, signUpButton);
        buttonBox.setAlignment(Pos.CENTER); // Center the buttons in the HBox
        buttonBox.setPadding(new Insets(20, 20, 20, 20));

        loginPanel = createLoginPanel();
        loginPanel.setTranslateX(-800); // Initial translation to hide the panel

        SUpanel = createSignUpPanel();
        SUpanel.setTranslateX(800);

        setBackgroundImage(buttonBox);

        StackPane root = new StackPane();
        root.getChildren().addAll(buttonBox, loginPanel, SUpanel);

        Scene scene = new Scene(root, 800, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    /* LOG IN */
    // log in panel
    private VBox createLoginPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);");

        // Create the login scene components in a method and put it in a vbox
        VBox loginFieldsScene = createLoginFieldsScene();
        // Add the vbox on the panel
        panel.getChildren().add(loginFieldsScene);
        // Set alignment for the login fields scene
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    // THE SLIDING movement
    // Update: A THREAD(HIDE AND SEEK) ONLY 1 PANEL AT A TIME

    private void slideLogInPanel() {
        if (SUpanel.getTranslateX() == 510) {
            TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), SUpanel);
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
            // If SignUp panel is not visible, slide in LogIn panel
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
            boolean isVisible = loginPanel.getTranslateX() == 0;
            double targetTranslation = isVisible ? -loginPanel.getWidth() : -510;
            tt.setToX(targetTranslation);
            tt.play();
        }
    }

    // Create the login components and scene
    private VBox createLoginFieldsScene() {
        VBox loginFieldsScene = new VBox(10);
        userNameTextField = new TextField();
        userNameTextField.setPromptText("Enter your username");
        userNameTextField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");
        Button confirmButton = createPurpleButton("Login");
        confirmButton.setOnAction(e -> handleLogin());

        loginStatusLabel = new Label("");
        loginStatusLabel.setWrapText(true);
        loginFieldsScene.getChildren().addAll(userNameTextField, passwordField, confirmButton, loginStatusLabel);

        // Set margins to create space between elements
        VBox.setMargin(userNameTextField, new Insets(50, 40, 10, 540)); // top, right, bottom, left
        VBox.setMargin(passwordField, new Insets(0, 40, 10, 540));
        VBox.setMargin(confirmButton, new Insets(0, 60, 10, 570));
        VBox.setMargin(loginStatusLabel, new Insets(0, 10, 0, 550));

        return loginFieldsScene;
    }

    private void handleLogin() {
        if (loginAttempts < MAX_LOGIN_ATTEMPTS) {
            String u = userNameTextField.getText();
            String p = passwordField.getText();
            boolean loginSuccess = admin.checkUserData(u, p);

            loginAttempts++;

            if (loginSuccess) {
                // REDIRECTING NOT CHANGE OF LABEL
                loginStatusLabel.setText("Login successful!");
                loginStatusLabel.setStyle(
                        "-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: purple; -fx-font-size: 16px;-fx-font-family: 'comic sans';");
                primaryStage.close();
                openAdminMainMenu();

            } else {
                loginStatusLabel.setText("Login failed. Check your credentials. Attempts left: "
                        + (MAX_LOGIN_ATTEMPTS - loginAttempts));
                loginStatusLabel.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");

                // Clear text fields if login fails
                userNameTextField.clear();
                passwordField.clear();
            }

            if (!loginSuccess && loginAttempts == MAX_LOGIN_ATTEMPTS) {
                // Closing when exceeding trials
                primaryStage.close();
            }
        }
    }

    /* END OF LOGGGING IN */

    /**********************************************************************************
     * ********************************************************************************
     */

    /* SIGN UP */
    private VBox createSignUpPanel() {
        VBox panelS = new VBox(10);
        panelS.setStyle("-fx-background-color: rgba(100, 100, 100, 0.3);");

        // Create the login scene components in a method and put it in a vbox
        VBox signUpFieldsScene = createSignuPFieldsScene();
        // Add the vbox on the panel
        panelS.getChildren().add(signUpFieldsScene);
        // Set alignment for the login fields scene
        panelS.setAlignment(Pos.CENTER);
        return panelS;
    }

    private void slideSignUpPanel() {
        if (loginPanel.getTranslateX() == -510) {
            // If LogIn panel is out, hide it first
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
            tt.setToX(-loginPanel.getWidth());
            tt.setOnFinished(e -> {
                // After LogIn panel hides, show SignUp panel
                TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), SUpanel);
                boolean isVisible = SUpanel.getTranslateX() == 0;
                double targetTranslationn = isVisible ? SUpanel.getWidth() : 510;
                t1.setToX(targetTranslationn);
                t1.play();
            });
            tt.play();
        } else {
            // If LogIn panel is not visible, show SignUp panel normally
            TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), SUpanel);
            boolean isVisible = SUpanel.getTranslateX() == 0;
            double targetTranslationn = isVisible ? SUpanel.getWidth() : 510;
            t1.setToX(targetTranslationn);
            t1.play();
        }
    }

    // Create the login components and scene
    private VBox createSignuPFieldsScene() {
        VBox signUpFieldsScene = new VBox(10);

        signupUserNameTextField = new TextField();
        signupUserNameTextField.setPromptText("Enter your username");
        signupUserNameTextField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        signUpPasswordField = new PasswordField();
        signUpPasswordField.setPromptText("Enter your password");
        signUpPasswordField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        passwordConfirmField = new PasswordField();
        passwordConfirmField.setPromptText("confirm your password");
        passwordConfirmField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        Button confirmButtonn = createPurpleButton("Sign-Up");
        confirmButtonn.setOnAction(e -> handleSignup());

        signupStatusLabel = new Label("");
        signupStatusLabel.setWrapText(true);

        signUpFieldsScene.getChildren().addAll(signupUserNameTextField, signUpPasswordField, passwordConfirmField,
                confirmButtonn, signupStatusLabel);

        // Set margins to create space between elements
        VBox.setMargin(signupUserNameTextField, new Insets(20, 540, 10, 40)); // top, right, bottom, left
        VBox.setMargin(signUpPasswordField, new Insets(0, 540, 10, 40));
        VBox.setMargin(passwordConfirmField, new Insets(0, 540, 10, 40));
        VBox.setMargin(confirmButtonn, new Insets(0, 60, 0, 50));
        VBox.setMargin(signupStatusLabel, new Insets(0, 540, 0, 70));

        return signUpFieldsScene;
    }

    private void handleSignup() {
        String uSU = signupUserNameTextField.getText();
        String pSU = signUpPasswordField.getText();
        String cpSU = passwordConfirmField.getText();

        boolean repeatedUserName = admin.checkUserName(uSU);
        // Password confirmed, not repeated username
        if (pSU.equals(cpSU) && !repeatedUserName) {
            admin.signUp(uSU, pSU);
            admin.writeToFile();
            signupStatusLabel.setText("Signed-up successfully.Redirecting...");

            // Delay for second before redirecting
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.schedule(() -> {
                Platform.runLater(() -> openAdminMainMenu());
                executorService.shutdown();
            }, 1, TimeUnit.SECONDS);

        } // repeated username
        else if (repeatedUserName) {
            signupStatusLabel.setText("User Name: (" + uSU + ") already taken. Try another user name.");
            userNameTextField.clear();
        } // passwords dosnt match
        else if (!pSU.equals(cpSU)) {
            signupStatusLabel.setText("Passwords don't match, try again.");
            signUpPasswordField.clear();
            passwordConfirmField.clear();
        }
    }

    /* END OF SIGN UP */

    /**********************************************************************************
     * ********************************************************************************
     * *******************************************************************************
     * *******************************************************************************
     */

    /* OPENNING ADMIN MAIN MENU */
    // ::MISSING: logo
    // ::MIssing: Text fit to button
    // ::Missing: suppliers trends button image
    private void openAdminMainMenu() {
        primaryStage.setTitle("Admin Main Menu");

        /* manage users button */
        Button manageUsers = createMainMenuButton("Manage Users", manageUsersIcon);
        manageUsers.setOnAction(e -> showManageUsersScene());

        /* Manage Suppliers button */
        Button manageSuppliers = createMainMenuButton("Manage Suppliers", truckOnmanageSuppliers);
        manageSuppliers.setOnAction(e -> showManageSuppliersScene());

        /* manage products button */
        Button manageProducts = createMainMenuButton("Manage Products", productsIcon);
        manageProducts.setOnAction(e -> showManageProductsScene());

        /* users trends button */
        Button userTrends = createMainMenuButton("Users Trends", usersGraphIcon);
        userTrends.setOnAction(e -> showUsersTrendsScene());

        /* Orders trends button */
        Button ordersTrends = createMainMenuButton("Orders Trends", ordersTrendIcon);
        ordersTrends.setOnAction(e -> showOrdersTrendsScene());

        // The 2 flexible rows of butttons
        FlowPane firstRowLayout = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        firstRowLayout.setAlignment(Pos.CENTER);
        FlowPane secondRowLayout = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        secondRowLayout.setAlignment(Pos.CENTER);

        firstRowLayout.getChildren().addAll(manageUsers, manageSuppliers, manageProducts);
        secondRowLayout.getChildren().addAll(ordersTrends, userTrends);

        // /*logo */
        // FlowPane iconPane = new FlowPane();
        // ImageView iconView = new ImageView(new
        // Image("file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-07_010104-removebg-preview.png"));
        // iconPane.getChildren().add(iconView);
        // iconPane.setAlignment(Pos.TOP_RIGHT);
        // iconView.setFitWidth(100);
        // iconView.setFitHeight(100);
        // addicon to vbox

        VBox mainMenuLayout = new VBox(10, firstRowLayout, secondRowLayout);
        mainMenuLayout.setAlignment(Pos.CENTER);
        setBackgroundImage(mainMenuLayout);

        Scene mainMenuScene = new Scene(mainMenuLayout, 800, 450);
        primaryStage.setScene(mainMenuScene);

        // Show the primaryStage
        primaryStage.show();
    }

    /*
     * MOE---EVENT HANDLING-------------------------------------------------------
     */ // switching scenes next to the previous butons when the user clicks on it
        // addUserButton->add a user scene appear
        // removeUserButton->remove user scene
        // searchUser
        // editUser

    // MANAGEE USERS SCENE
    private void showManageUsersScene() {
        BorderPane manageUsersLayout = new BorderPane();
        setBackgroundImage(manageUsersLayout);
        primaryStage.setTitle("Manage Users");

        Button h = HOME();
        h.setOnAction(e -> openAdminMainMenu());
        BorderPane.setAlignment(h, Pos.TOP_RIGHT); // Set alignment to top right
        BorderPane.setMargin(h, new Insets(10, 10, 0, 0)); // Optional: Set margin for spacing

        /********************************************************************************************************************************************** */
        VBox Adduser = new VBox(15);
        // usename
        TextField UserName = new TextField();
        UserName.setPromptText("Example:Mo101");

        // group toggle HBox(for user type)
        HBox ToggleUserType = new HBox(10);
        Insets ToggleInsets = new Insets(5, 5, 5, 35);
        ToggleUserType.setPadding(ToggleInsets);
        // -------------------------------------------------------------------------------------------
        // toggle group (cashier,customer,supplier)
        ToggleGroup GroupUserTypeAdd = new ToggleGroup();
        RadioButton CustomerRadioAdd = new RadioButton("Customer");
        CustomerRadioAdd.setToggleGroup(GroupUserTypeAdd);
        RadioButton CashierRadioAdd = new RadioButton("Cashier");
        CashierRadioAdd.setToggleGroup(GroupUserTypeAdd);
        RadioButton SupplierRadioAdd = new RadioButton("Supplier");
        ToggleUserType.getChildren().addAll(CustomerRadioAdd, CashierRadioAdd, SupplierRadioAdd);

        // ------------------------------------------------------------------------------------------
        // password and confirm password
        PasswordField Password = new PasswordField();
        Password.setPromptText("Example:....");
        PasswordField ConfirmPassword = new PasswordField();
        ConfirmPassword.setPromptText("Example:....");
        // ------------------------------------------------------------------------------------------
        Button ConfirmAdd = new Button("Confirm");// Action: Add the user with the given information
        // Labels
        Label UserNameLab = new Label("Username");
        Label UserTypeLab = new Label("User Type");
        Label PasswordLab = new Label("Password");
        Label ConfirmPasswordLab = new Label("Confirm Password");
        // ------------------------------------------------------------------------------------------
        // Padding, alignment and Scene
        Adduser.setPadding(new Insets(20, 50, 50, 50));
        Adduser.getChildren().addAll(UserNameLab, UserName, UserTypeLab, ToggleUserType, PasswordLab, Password,
                ConfirmPasswordLab, ConfirmPassword, ConfirmAdd);
        Button managepanelsadd = new Button("Home");
        Adduser.getChildren().add(managepanelsadd);
        Adduser.setAlignment(Pos.CENTER);
        ScrollPane addpane = new ScrollPane(Adduser);
        Scene AddUserSc = new Scene(addpane, 390, 400);
        /**********************************************************************************************************************************************/
        VBox EditUser = new VBox(15);
        EditUser.setPadding(new Insets(10, 10, 10, 10));

        // -----------------------------------------------------------------------
        // current user type toggle group
        HBox ToggleEditUserType = new HBox(10);
        ToggleEditUserType.setPadding(new Insets(5, 5, 5, 30));
        ToggleGroup GroupUserTypeEdit = new ToggleGroup();

        RadioButton CustomerRadioEdit = new RadioButton("Customer");
        CustomerRadioEdit.setToggleGroup(GroupUserTypeEdit);

        RadioButton CashierRadioEdit = new RadioButton("Cashier");
        CashierRadioEdit.setToggleGroup(GroupUserTypeEdit);

        RadioButton SupplierRadioButtonEdit = new RadioButton("Supplier");
        SupplierRadioButtonEdit.setToggleGroup(GroupUserTypeEdit);

        ToggleEditUserType.getChildren().addAll(CustomerRadioEdit, CashierRadioEdit, SupplierRadioButtonEdit);
        // ----------------------------------------------------------------------------
        // texts and passwords fields
        TextField CurrentUserName = new TextField();

        TextField EditUserName = new TextField();
        EditUserName.setPromptText("Example: Mo101");

        PasswordField NewPassword = new PasswordField();
        NewPassword.setPromptText("Example:....");

        PasswordField ConfirmNewPassword = new PasswordField();
        ConfirmNewPassword.setPromptText("Example:....");
        // ----------------------------------------------------------------------------
        Button ConfirmEdit = new Button("Confirm");// Action: Update the user with the given information
        Label CurrentUserNameLab = new Label("Current Username");
        Label EditUserNameLab = new Label("new Username");
        Label EditUserTypeLab = new Label("User Type");
        Label NewPasswordLab = new Label("New Password");
        Label ConfirmNewPasswordLab = new Label("Confirm New Password");
        Button managepanelsedit = new Button("Home");
        EditUser.getChildren().addAll(CurrentUserNameLab, CurrentUserName, EditUserNameLab, EditUserName,
                EditUserTypeLab, ToggleEditUserType,
                NewPasswordLab, NewPassword, ConfirmNewPasswordLab, ConfirmNewPassword, ConfirmEdit, managepanelsedit);
        EditUser.setAlignment(Pos.CENTER);
        ScrollPane editpane = new ScrollPane(EditUser);
        Scene EditUserSc = new Scene(editpane, 350, 400);

        /************************************************************************************************* */
        // **************************************************************************************************
        /* Remove User */
        // Main VBox
        VBox MainRemoveUser = new VBox(15);
        MainRemoveUser.setPadding(new Insets(10, 10, 10, 10));
        // ----------------------------------------------------------------
        Label RemoveUserNamelab = new Label("User Name");
        TextField RemoveUserName = new TextField();
        // ----------------------------------------------------------------
        HBox ToggleRemoveUserType = new HBox(10);
        ToggleRemoveUserType.setPadding(new Insets(5, 5, 5, 30));
        ToggleGroup GroupUserTypeRemove = new ToggleGroup();

        RadioButton CustomerRadioRemove = new RadioButton("Customer");
        CustomerRadioRemove.setToggleGroup(GroupUserTypeRemove);

        RadioButton CashierRadioRemove = new RadioButton("Cashier");
        CashierRadioRemove.setToggleGroup(GroupUserTypeRemove);

        RadioButton SupplierRadioButtonRemove = new RadioButton("Supplier");
        SupplierRadioButtonRemove.setToggleGroup(GroupUserTypeRemove);

        ToggleRemoveUserType.getChildren().addAll(CustomerRadioRemove, CashierRadioRemove, SupplierRadioButtonRemove);

        // ------------------------------------------------------------------
        Button ConfirmRemove = new Button("Confirm");
        Button managepanelsRemove = new Button("Home");
        MainRemoveUser.getChildren().addAll(RemoveUserNamelab, RemoveUserName, ToggleRemoveUserType, ConfirmRemove,
                managepanelsRemove);

        Scene RemoveUserSc = new Scene(MainRemoveUser, 350, 350);
        /********************************************************************************************** */
        // **************************************************************************************************
        /* Search for a user */

        // Main VBox
        VBox MainSearchUser = new VBox(15);
        MainSearchUser.setPadding(new Insets(10, 10, 10, 10));
        // ----------------------------------------------------------------
        Label SearchUserNamelab = new Label("User Name");
        TextField SearchUserName = new TextField();
        // ----------------------------------------------------------------
        HBox ToggleSearchUserType = new HBox(10);
        ToggleEditUserType.setPadding(new Insets(5, 5, 5, 30));
        ToggleGroup GroupUserTypeSearch = new ToggleGroup();

        RadioButton CustomerRadioSearch = new RadioButton("Customer");
        CustomerRadioSearch.setToggleGroup(GroupUserTypeSearch);

        RadioButton CashierRadioSearch = new RadioButton("Cashier");
        CashierRadioSearch.setToggleGroup(GroupUserTypeSearch);

        RadioButton SupplierRadioButtonSearch = new RadioButton("Supplier");
        SupplierRadioButtonSearch.setToggleGroup(GroupUserTypeSearch);

        ToggleSearchUserType.getChildren().addAll(CustomerRadioSearch, CashierRadioSearch, SupplierRadioButtonSearch);

        // ------------------------------------------------------------------
        Button ConfirmSearch = new Button("Confirm");
        Button managepanelssearch = new Button("Home");
        MainSearchUser.getChildren().addAll(SearchUserNamelab, SearchUserName, ToggleSearchUserType, ConfirmSearch,
                managepanelssearch);

        Scene SearchUserSc = new Scene(MainSearchUser, 350, 350);

        /***************************************************************************************************************** */

        Button addUserButton = createPurpleButton("Add User");
        addUserButton.setOnAction(e -> {
            primaryStage.setScene(AddUserSc);
        });

        Button removeUserButton = createPurpleButton("Remove User");
        removeUserButton.setOnAction(e -> {
            primaryStage.setScene(RemoveUserSc);
        });

        Button searchUser = createPurpleButton("Search for User");
        searchUser.setOnAction((e -> {
            primaryStage.setScene(SearchUserSc);
        }));

        Button editUser = createPurpleButton("editUser");
        editUser.setOnAction(e -> {
            primaryStage.setScene(EditUserSc);
        });

        VBox buttonsVBox = new VBox(10, addUserButton, editUser, searchUser, removeUserButton);
        buttonsVBox.setPadding(new Insets(0, 20, 0, 20));
        buttonsVBox.setAlignment(Pos.CENTER_LEFT);

        manageUsersLayout.setTop(h);
        manageUsersLayout.setLeft(buttonsVBox);

        Scene manageUsersScene = new Scene(manageUsersLayout, 800, 450);
        primaryStage.setScene(manageUsersScene);

        ConfirmAdd.setOnAction(e -> {
            String username = UserName.getText();
            // -----------------------------------------------------------------------------------------------
            // Password and confirm password conflict
            if (!Password.getText().equals(ConfirmPassword.getText())) {
                Alert passerror = new Alert(AlertType.INFORMATION);
                passerror.setHeaderText("Password Confirmation");
                passerror.setHeaderText("Please reconfirm your password");
                passerror.showAndWait();
            }
            // setting the passwords
            String password = Password.getText();
            String confirmpassword = ConfirmPassword.getText();
            // -------------------------------------------------------------------------------------------------
            // setting the user type
            String userType;
            RadioButton selectedToggle1 = (RadioButton) GroupUserTypeAdd.getSelectedToggle();
            // Handle the case where no toggle is selected
            if (selectedToggle1 == null) {
                Alert TypeError = new Alert(AlertType.INFORMATION);
                TypeError.setHeaderText("User type selection");
                TypeError.setHeaderText("Please choose the user type");
                TypeError.showAndWait();
            }

            // no need for exception handelling
            userType = selectedToggle1.getText();

            admin.addUser(username, userType, password, confirmpassword);
            Label AddDone = new Label("User added...");
            Adduser.getChildren().add(AddDone);
        });
        // ****************************************************************************************************
        /* Confirm Edit User Button */

        ConfirmEdit.setOnAction(e -> {
            String CurrentUsername = CurrentUserName.getText();
            String newUsername = EditUserName.getText();
            // ----------------------------------------------------------------------------------------------------
            // Password and confirm password conflict
            if (!NewPassword.getText().equals(ConfirmNewPassword.getText())) {
                Alert passerror = new Alert(AlertType.INFORMATION);
                passerror.setHeaderText("Password Confirmation");
                passerror.setHeaderText("Please reconfirm your password");
                passerror.showAndWait();
            }

            String EditedPassword = NewPassword.getText();
            // -----------------------------------------------------------------------------------------------------
            // User type handle

            String CurrentUserType;
            RadioButton selectedToggle2 = (RadioButton) GroupUserTypeEdit.getSelectedToggle();
            if (selectedToggle2 == null) {
                Label usertype = new Label("User type is required");
                EditUser.getChildren().clear();

                EditUser.getChildren().addAll(CurrentUserNameLab, CurrentUserName, EditUserNameLab,
                        EditUserName, EditUserTypeLab, usertype, ToggleEditUserType,
                        NewPasswordLab, NewPassword, ConfirmNewPasswordLab,
                        ConfirmNewPassword, ConfirmEdit, managepanelsedit);
            } else {
                CurrentUserType = selectedToggle2.getText();
                admin.editUser(CurrentUsername, newUsername, CurrentUserType, EditedPassword);
            }
            Label doneedit = new Label("your edits are saved");
            EditUser.getChildren().clear();

            EditUser.getChildren().addAll(CurrentUserNameLab, CurrentUserName, EditUserNameLab,
                    EditUserName, EditUserTypeLab, ToggleEditUserType,
                    NewPasswordLab, NewPassword, ConfirmNewPasswordLab,
                    ConfirmNewPassword, ConfirmEdit, managepanelsedit, doneedit);
        });

        // ***********************************************************************************************
        /* Confirm Button Search */

        ConfirmSearch.setOnAction(e -> {
            String Username = SearchUserName.getText();
            String usertype;
            RadioButton Selectedtoggle3 = (RadioButton) GroupUserTypeSearch.getSelectedToggle();
            // ------------------------------------------------------------------------------------
            // handeling null type
            if (Selectedtoggle3 == null) {
                Label usertypelab = new Label("User type is required");
                MainSearchUser.getChildren().clear();
                MainSearchUser.getChildren().addAll(SearchUserNamelab, SearchUserName, ToggleSearchUserType,
                        ConfirmSearch, managepanelssearch, usertypelab);
            } else {
                usertype = Selectedtoggle3.getText();

                if (admin.searchUser(Username, usertype)) {
                    Label found = new Label("User found");
                    MainSearchUser.getChildren().clear();
                    MainSearchUser.getChildren().addAll(SearchUserNamelab, SearchUserName, ToggleSearchUserType,
                            ConfirmSearch, managepanelssearch, found);
                } else {
                    Label notfound = new Label("User not found");
                    MainSearchUser.getChildren().clear();
                    MainSearchUser.getChildren().addAll(SearchUserNamelab, SearchUserName, ToggleSearchUserType,
                            ConfirmSearch, managepanelssearch, notfound);
                }
            }

        });
        // **************************************************************************************************
        /* Confirm Remove User */
        ConfirmRemove.setOnAction(e -> {
            String Username = RemoveUserName.getText();
            String usertype;
            RadioButton Selectedtoggle4 = (RadioButton) GroupUserTypeRemove.getSelectedToggle();
            // ------------------------------------------------------------------------------------
            // handeling null type
            if (Selectedtoggle4 == null) {
                Label usertypelab = new Label("User type is required");
                MainRemoveUser.getChildren().clear();
                MainRemoveUser.getChildren().addAll(RemoveUserNamelab, RemoveUserName, ToggleRemoveUserType,
                        ConfirmRemove, managepanelsRemove, usertypelab);
            } else {
                usertype = Selectedtoggle4.getText();

                if (admin.removeUser(Username, usertype)) {
                    Label found = new Label("User removed");
                    MainRemoveUser.getChildren().clear();
                    MainRemoveUser.getChildren().addAll(RemoveUserNamelab, RemoveUserName, ToggleRemoveUserType,
                            ConfirmRemove, managepanelsRemove, found);
                } else {
                    Label notfound = new Label("User not found");
                    MainRemoveUser.getChildren().clear();
                    MainRemoveUser.getChildren().addAll(RemoveUserNamelab, RemoveUserName, ToggleRemoveUserType,
                            ConfirmRemove, managepanelsRemove, notfound);
                }
            }
        });

        primaryStage.show();
    }

    private void showManageSuppliersScene() {

        Supplier sup1 = new Supplier("Fine fabrics");
        Supplier.SupplierDataList.add(sup1);
        sup1.supplierProducts.add(new Products("Tshirt", 300));
        sup1.supplierProducts.add(new Products("Shorts", 450));
        sup1.supplierProducts.add(new Products("shoes", 900));
        sup1.supplierProducts.add(new Products("Hoodie", 600));
        sup1.supplierProducts.add(new Products("Cap", 250));

        Supplier sup2 = new Supplier("Tom's co");
        Supplier.SupplierDataList.add(sup2);
        sup2.supplierProducts.add(new Products("Hoodie", 900));
        sup2.supplierProducts.add(new Products("shoes", 1900));
        sup2.supplierProducts.add(new Products("Tshirt", 750));
        sup2.supplierProducts.add(new Products("Slides", 1000));
        sup2.supplierProducts.add(new Products("Hoodie", 1200));

        Supplier sup3 = new Supplier("Custom made");
        Supplier.SupplierDataList.add(sup3);
        sup3.supplierProducts.add(new Products("Jeans", 1200));
        sup3.supplierProducts.add(new Products("shoes", 3000));
        sup3.supplierProducts.add(new Products("Tshirt", 1150));
        sup3.supplierProducts.add(new Products("Slides", 1400));
        sup3.supplierProducts.add(new Products("Hoodie", 1900));

        Supplier sup4 = new Supplier("Threads");
        Supplier.SupplierDataList.add(sup4);
        sup4.supplierProducts.add(new Products("Beanie", 900));
        sup4.supplierProducts.add(new Products("shoes", 4000));
        sup4.supplierProducts.add(new Products("Tshirt", 2200));
        sup4.supplierProducts.add(new Products("Slides", 1600));
        sup4.supplierProducts.add(new Products("Hoodie", 1900));

        Supplier sup5 = new Supplier("Palms");
        Supplier.SupplierDataList.add(sup5);
        sup5.supplierProducts.add(new Products("Tshirt", 700));
        sup5.supplierProducts.add(new Products("Shorts", 920));
        sup5.supplierProducts.add(new Products("shoes", 1450));
        sup5.supplierProducts.add(new Products("Hoodie", 1100));
        sup5.supplierProducts.add(new Products("Cap", 460));

        // ********************************************************************************************************
        /* Manage supplier */

        HBox MainSupManagement = new HBox(30);
        MainSupManagement.setPadding(new Insets(10, 10, 10, 10));
        MainSupManagement.setAlignment(Pos.CENTER);
        VBox Sup1 = new VBox(10);

        Sup1.setPadding(new Insets(5, 5, 5, 5));
        Label sup1p1N = new Label(sup1.supplierProducts.get(0).productName);
        Label sup1p2N = new Label(sup1.supplierProducts.get(1).productName);
        Label sup1p3N = new Label(sup1.supplierProducts.get(2).productName);
        Label sup1p4N = new Label(sup1.supplierProducts.get(3).productName);
        Label sup1p5N = new Label(sup1.supplierProducts.get(4).productName);

        Label sup1p1P = new Label("" + sup1.supplierProducts.get(0).price);
        Label sup1p2P = new Label("" + sup1.supplierProducts.get(1).price);
        Label sup1p3P = new Label("" + sup1.supplierProducts.get(2).price);
        Label sup1p4P = new Label("" + sup1.supplierProducts.get(3).price);
        Label sup1p5P = new Label("" + sup1.supplierProducts.get(4).price);

        Label sup1Lab = new Label(Supplier.SupplierDataList.get(0).supplierName);

        Sup1.getChildren().addAll(sup1Lab,
                sup1p1N, sup1p1P,
                sup1p2N, sup1p2P,
                sup1p3N, sup1p3P,
                sup1p4N, sup1p4P,
                sup1p5N, sup1p5P);

        VBox Sup2 = new VBox(10);
        Sup2.setPadding(new Insets(5, 5, 5, 5));
        Label sup2p1N = new Label(sup2.supplierProducts.get(0).productName);
        Label sup2p2N = new Label(sup2.supplierProducts.get(1).productName);
        Label sup2p3N = new Label(sup2.supplierProducts.get(2).productName);
        Label sup2p4N = new Label(sup2.supplierProducts.get(3).productName);
        Label sup2p5N = new Label(sup2.supplierProducts.get(4).productName);

        Label sup2p1P = new Label("" + sup2.supplierProducts.get(0).price);
        Label sup2p2P = new Label("" + sup2.supplierProducts.get(1).price);
        Label sup2p3P = new Label("" + sup2.supplierProducts.get(2).price);
        Label sup2p4P = new Label("" + sup2.supplierProducts.get(3).price);
        Label sup2p5P = new Label("" + sup2.supplierProducts.get(4).price);
        Label sup2Lab = new Label(Supplier.SupplierDataList.get(1).supplierName);
        Sup2.getChildren().addAll(sup2Lab,
                sup2p1N, sup2p1P,
                sup2p2N, sup2p2P,
                sup2p3N, sup2p3P,
                sup2p4N, sup2p4P,
                sup2p5N, sup2p5P);

        VBox Sup3 = new VBox(10);
        Sup3.setPadding(new Insets(5, 5, 5, 5));

        Label sup3p1N = new Label(sup3.supplierProducts.get(0).productName);
        Label sup3p2N = new Label(sup3.supplierProducts.get(1).productName);
        Label sup3p3N = new Label(sup3.supplierProducts.get(2).productName);
        Label sup3p4N = new Label(sup3.supplierProducts.get(3).productName);
        Label sup3p5N = new Label(sup3.supplierProducts.get(4).productName);

        Label sup3p1P = new Label("" + sup3.supplierProducts.get(0).price);
        Label sup3p2P = new Label("" + sup3.supplierProducts.get(1).price);
        Label sup3p3P = new Label("" + sup3.supplierProducts.get(2).price);
        Label sup3p4P = new Label("" + sup3.supplierProducts.get(3).price);
        Label sup3p5P = new Label("" + sup3.supplierProducts.get(4).price);
        Label sup3Lab = new Label(Supplier.SupplierDataList.get(1).supplierName);
        Sup3.getChildren().addAll(sup3Lab,
                sup3p1N, sup3p1P,
                sup3p2N, sup3p2P,
                sup3p3N, sup3p3P,
                sup3p4N, sup3p4P,
                sup3p5N, sup3p5P);

        VBox Sup4 = new VBox(10);
        Sup4.setPadding(new Insets(5, 5, 5, 5));
        Label sup4p1N = new Label(sup4.supplierProducts.get(0).productName);
        Label sup4p2N = new Label(sup4.supplierProducts.get(1).productName);
        Label sup4p3N = new Label(sup4.supplierProducts.get(2).productName);
        Label sup4p4N = new Label(sup4.supplierProducts.get(3).productName);
        Label sup4p5N = new Label(sup4.supplierProducts.get(4).productName);

        Label sup4p1P = new Label("" + sup4.supplierProducts.get(0).price);
        Label sup4p2P = new Label("" + sup4.supplierProducts.get(1).price);
        Label sup4p3P = new Label("" + sup4.supplierProducts.get(2).price);
        Label sup4p4P = new Label("" + sup4.supplierProducts.get(3).price);
        Label sup4p5P = new Label("" + sup4.supplierProducts.get(4).price);
        Label sup4Lab = new Label(Supplier.SupplierDataList.get(1).supplierName);
        Sup4.getChildren().addAll(sup4Lab,
                sup4p1N, sup4p1P,
                sup4p2N, sup4p2P,
                sup4p3N, sup4p3P,
                sup4p4N, sup4p4P,
                sup4p5N, sup4p5P);

        VBox Sup5 = new VBox(10);
        Sup5.setPadding(new Insets(5, 5, 5, 5));
        Label sup5p1N = new Label(sup3.supplierProducts.get(0).productName);
        Label sup5p2N = new Label(sup3.supplierProducts.get(1).productName);
        Label sup5p3N = new Label(sup3.supplierProducts.get(2).productName);
        Label sup5p4N = new Label(sup3.supplierProducts.get(3).productName);
        Label sup5p5N = new Label(sup3.supplierProducts.get(4).productName);

        Label sup5p1P = new Label("" + sup3.supplierProducts.get(0).price);
        Label sup5p2P = new Label("" + sup3.supplierProducts.get(1).price);
        Label sup5p3P = new Label("" + sup3.supplierProducts.get(2).price);
        Label sup5p4P = new Label("" + sup3.supplierProducts.get(3).price);
        Label sup5p5P = new Label("" + sup3.supplierProducts.get(4).price);
        Label sup5Lab = new Label(Supplier.SupplierDataList.get(1).supplierName);
        Sup5.getChildren().addAll(sup5Lab,
                sup5p1N, sup5p1P,
                sup5p2N, sup5p2P,
                sup5p3N, sup5p3P,
                sup5p4N, sup5p4P,
                sup5p5N, sup5p5P);
        // // right top corner home button
        Button h1 = HOME();
        h1.setOnAction(e -> openAdminMainMenu());
        MainSupManagement.getChildren().addAll(Sup1, Sup2, Sup3, Sup4, Sup5, h1);
        Scene ManageSupSc = new Scene(MainSupManagement, 800, 450);

        primaryStage.setScene(ManageSupSc);

    }

    // ::IDK aout the elements of the window so add it and event handle it
    private void showManageProductsScene() {

        VBox MainAddP = new VBox(10); // 10 pixels spacing
        MainAddP.setPadding(new Insets(20, 20, 20, 20)); // Set padding
        Label Pnamelab = new Label("Product Name");
        TextField Pname = new TextField();

        Label pricelab = new Label("Product Price");
        TextField price = new TextField();

        Button confirmAddP = new Button("Confirm");
        Button managepanelAddP = new Button("Home");

        // Add nodes to VBox
        MainAddP.getChildren().addAll(Pnamelab, Pname, pricelab, price, confirmAddP, managepanelAddP);

        // Set up the scene
        Scene AddPSc = new Scene(MainAddP, 300, 250);

        confirmAddP.setOnAction(e -> {
            String productName = Pname.getText();
            String priceText = price.getText();

            VBox content = new VBox(10); // Use an appropriate layout container

            if (productName.isEmpty()) {
                content.getChildren().addAll(new Label("Please enter a product name"), Pname, pricelab, price,
                        confirmAddP, managepanelAddP);
            } else if (priceText.isEmpty()) {
                content.getChildren().addAll(Pnamelab, Pname, pricelab, price, new Label("Price cannot be zero"),
                        confirmAddP, managepanelAddP);
            } else {
                try {
                    int pprice = Integer.parseInt(priceText);
                    String pname = productName;
                    admin.addProducts(pname, pprice);
                    content.getChildren().addAll(new Label("Product added successfully"), Pnamelab, new TextField(),
                            pricelab, new TextField(), confirmAddP, managepanelAddP);
                } catch (NumberFormatException ex) {
                    content.getChildren().addAll(Pnamelab, Pname, pricelab, price, new Label("Invalid price format"),
                            confirmAddP, managepanelAddP);
                }
            }

            MainAddP.getChildren().setAll(content);
        });

        // search product scene and handeling
        Label searchLabel = new Label("Enter Product Name or ID:");
        TextField searchField = new TextField();
        Button confirmButton = new Button("Search");

        // Create a result label to display the search result
        Label resultLabel = new Label();

        // Handle search button click
        confirmButton.setOnAction(e -> {
            String searchText = searchField.getText();
            Products search = admin.searchProducts(searchText, searchText);
            resultLabel.setText("name: " + search.productName + "\n" + "price:" + search.price + "\n"
                    + "product serial number:" + search.PID);
        });

        // Create layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(searchLabel, searchField, confirmButton, resultLabel);
        layout.setPadding(new Insets(10));

        // Create scene
        Scene SearchPSc = new Scene(layout, 300, 150);

        // Remove product scene and handeling

        VBox mainRemoveP = new VBox(10); // 10 pixels spacing
        mainRemoveP.setPadding(new Insets(20, 20, 20, 20)); // Set padding

        Label nameLabel = new Label("Product Name or Serial Number");
        TextField nameOrSerialField = new TextField();

        Button confirmRemoveP = new Button("Confirm");
        Button managePanelRemoveP = new Button("Home");

        // Add nodes to VBox
        mainRemoveP.getChildren().addAll(nameLabel, nameOrSerialField, confirmRemoveP, managePanelRemoveP);

        // Set up the scene
        Scene removePScene = new Scene(mainRemoveP, 300, 250);

        // Set up action for the confirm button
        confirmRemoveP.setOnAction(e -> {
            String nameOrSerial = nameOrSerialField.getText();

            // Calling remove method
            boolean removed = admin.removeProducts(nameOrSerial, nameOrSerial);

            // Clear existing labels and add the result label
            mainRemoveP.getChildren().clear();

            Label resultLabel2 = new Label();
            if (removed) {
                // Product successfully removed
                resultLabel2.setText("Product removed successfully.");
            } else {
                // Product not found or removal failed
                resultLabel2.setText("Product not found or removal failed.");
            }

            mainRemoveP.getChildren().addAll(resultLabel2, managePanelRemoveP);
        });

        FlowPane manageProductssElements = new FlowPane(Orientation.HORIZONTAL, 10, 10);

        setBackgroundImage(manageProductssElements);
        primaryStage.setTitle("Mange Products");

        Button addProuctb = createPurpleButton("Add User");
        addProuctb.setOnAction(e -> {
            primaryStage.setScene(AddPSc);
        });

        Button removeProductP = createPurpleButton("Remove User");
        removeProductP.setOnAction(e -> {
            primaryStage.setScene(removePScene);
        });

        Button searchProductP = createPurpleButton("Search for User");
        searchProductP.setOnAction((e -> {
            primaryStage.setScene(SearchPSc);
        }));

        // right top corner home button
        Button h2 = HOME();
        h2.setOnAction(e -> openAdminMainMenu());
        FlowPane.setMargin(h2, new Insets(10, 0, 0, 710));

        manageProductssElements.getChildren().addAll(h2, addProuctb, removeProductP, searchProductP);
        Scene manageProductsScene = new Scene(manageProductssElements, 800, 450);
        primaryStage.setScene(manageProductsScene);
        manageProductssElements.getChildren().addAll(h2);

        primaryStage.show();

    }

    // ::switching scene to Users Trends Scene
    private void showUsersTrendsScene() {
        FlowPane usersTrendsLayout = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        // usersTrendsLayout.setAlignment(Pos.CENTER);
        setBackgroundImage(usersTrendsLayout);
        primaryStage.setTitle("Users Trends");

        // right top corner home button
        Button h2 = HOME();
        h2.setOnAction(e -> openAdminMainMenu());
        FlowPane.setMargin(h2, new Insets(10, 0, 0, 710));

        usersTrendsLayout.getChildren().addAll(h2);

        Scene manageUsersScene = new Scene(usersTrendsLayout, 800, 450);
        primaryStage.setScene(manageUsersScene);

        primaryStage.show();

    }

    // :: Switching scene to showOrdersTrends
    private void showOrdersTrendsScene() {

        FlowPane OrdersTrendsLayout = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        // OrdersTrendsLayout.setAlignment(Pos.CENTER);

        setBackgroundImage(OrdersTrendsLayout);
        primaryStage.setTitle("Orders Trends");

        // right top corner home button

        Button h3 = HOME();
        h3.setOnAction(e -> openAdminMainMenu());
        FlowPane.setMargin(h3, new Insets(10, 0, 0, 710));

        OrdersTrendsLayout.getChildren().addAll(h3);

        Scene manageUsersScene = new Scene(OrdersTrendsLayout, 800, 450);
        primaryStage.setScene(manageUsersScene);

        primaryStage.show();

    }

    /****************************************
     * *************************************************************************
     * **********************************************************************
     *********************************************************************************
     * ********************************************************************************
     */

    /* Design */
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

    // PURPLE BUTTONS
    private Button createPurpleButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setStyle(
                "-fx-background-color: #615BA2; -fx-text-fill: white; -fx-font-size: 20; -fx-pref-width: 200; -fx-pref-height: 50; -fx-background-radius: 25;");
        return button;
    }

    // Transparent buttons with icons
    private Button createMainMenuButton(String buttonTitle, String iconPNGPath) {
        // Size variables
        double buttonWidth = 170;
        double buttonHeight = 170;
        double imageWidth = 100;
        double imageHeight = 150;
        Button tansButton = new Button(buttonTitle);

        tansButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4);" + "-fx-text-fill: white;"
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

    // Home button
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
