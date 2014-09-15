/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guicalendar;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class CalendarController implements Initializable {

    /**
     * Fields for calendar.
     */
    private static ArrayList<String> months = new ArrayList<>();

    private static int realDay;
    private static int realMonth;
    private static int realYear;

    private static int currentMonth;
    private static int currentYear;

    private static Label monthLabel;
    private static int currentDay;
    private static GridPane calendarGrid;
    private static ComboBox yearCombo;
    private static VBox calendarBox;
    private static Button prevButton;
    private static Button nextButton;

    /**
     * Fields for schedule
     */
    private static Label scheduleLabel;
    private static Label scheduleDateLabel;
    /**
     * Initializes the controller class.
     */
    @FXML
    private GridPane pane;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HBox calDashBoard = new HBox();
        calDashBoard.getChildren().add(initCalendar());
        calDashBoard.getChildren().add(initSchedule());
        anchorPane.getChildren().add(calDashBoard);
    }

    public VBox initCalendar() {
        //Controls
        monthLabel = new Label("Januray");
        yearCombo = new ComboBox();
        prevButton = new Button("<<");
        prevButton.setPrefWidth(50);
        nextButton = new Button(">>");
        nextButton.setPrefWidth(50);
        calendarGrid = new GridPane();
        calendarBox = new VBox();
        calendarBox.setPadding(new Insets(10, 10, 10, 10));

        //Set actionlistners
        prevButton.setOnAction((ActionEvent event) -> {
            if (currentMonth == 0) { //Back a year
                currentMonth = 11;
                currentYear -= 1;
                yearCombo.setValue(Integer.toString(currentYear));
            } else { //foward a month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        });
        nextButton.setOnAction((ActionEvent event) -> {
            if (currentMonth == 11) { //Foward one year
                currentMonth = 0;
                currentYear += 1;
                yearCombo.setValue(Integer.toString(currentYear));
            } else { //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        });
        yearCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (yearCombo.getValue() != null && !yearCombo.getValue().toString().isEmpty()) {
                    String b = yearCombo.getValue().toString();
                    currentYear = Integer.parseInt(b);
                    refreshCalendar(currentMonth, currentYear);
                }
            }
        });

        //Add padding
//        monthLabel.setPadding(new Insets(5, 5, 5, 5));
//        yearCombo.setPadding(new Insets(5, 5, 5, 5));
//        prevButton.setPadding(new Insets(5, 5, 5, 5));
//        nextButton.setPadding(new Insets(5, 5, 5, 5));
        //Add controls to pane
        TilePane controlPane = new TilePane();
        controlPane.setPrefColumns(4);
        controlPane.getChildren().add(prevButton);
        controlPane.getChildren().add(monthLabel);
        controlPane.getChildren().add(yearCombo);
        controlPane.getChildren().add(nextButton);

        calendarBox.getChildren().add(controlPane);

        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Current month and year is what the calendar is viewing
        currentYear = realYear;

        //GridPane for headers
        HBox headerBox = new HBox();
        Label sunLabel = new Label("Sun");
        Label monLabel = new Label("Mon");
        Label tueLabel = new Label("Tue");
        Label wedLabel = new Label("Wed");
        Label thuLabel = new Label("Thu");
        Label friLabel = new Label("Fri");
        Label satLabel = new Label("Sat");
        sunLabel.setPrefSize(50, 30);
        monLabel.setPrefSize(50, 30);
        tueLabel.setPrefSize(50, 30);
        wedLabel.setPrefSize(50, 30);
        thuLabel.setPrefSize(50, 30);
        friLabel.setPrefSize(50, 30);
        satLabel.setPrefSize(50, 30);
        headerBox.getChildren().add(sunLabel);
        headerBox.getChildren().add(monLabel);
        headerBox.getChildren().add(tueLabel);
        headerBox.getChildren().add(wedLabel);
        headerBox.getChildren().add(thuLabel);
        headerBox.getChildren().add(friLabel);
        headerBox.getChildren().add(satLabel);

        calendarBox.getChildren().add(headerBox);

        //Add Calendar Scroll Grid to box
        calendarBox.getChildren().add(calendarGrid);

        //Populate year combobox
        for (int i = realYear - 3; i <= realYear + 3; i++) {
            yearCombo.getItems().add(String.valueOf(i));
        }
        yearCombo.setValue(currentYear);

        //Refresh calendar
        refreshCalendar(realMonth, realYear);

        return calendarBox;
    }

    private static void refreshCalendar(int month, int year) {
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        int numberOfDays;
        int startOfMonth;

        prevButton.setDisable(false);
        nextButton.setDisable(false);

        if (month == 0 && year <= realYear - 5) {
            prevButton.setDisable(true); //too early
        }

        if (month == 11 && year >= realYear + 5) {
            nextButton.setDisable(true); //too late
        }
        monthLabel.setText(months.get(month)); //refresh month label
        //TO:DO re-align month label

        // yearCombo.setValue(String.valueOf(year)); //select correct year in combo box
        //Clear table
        calendarGrid.getChildren().removeAll();
        calendarGrid.getChildren().clear();

        Button[][] cellButton = new Button[7][6];

        //Column is a vertical line and row is a horizontal line
        //Two FOR loops used for creating 2D array of buttons with values i,j
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {

                //Initializing 2D buttons with values i,j
                cellButton[i][j] = new Button("");
                cellButton[i][j].setPrefSize(50, 50);

                calendarGrid.add(cellButton[i][j], i, j);
            }
        }
        renderCalendar();

        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);

        //Draw calendar
        for (int i = 1; i <= numberOfDays; i++) {
            int row = ((i + startOfMonth - 2) / 7);
            int column = (i + startOfMonth - 2) % 7;

            cellButton[column][row].setText(String.valueOf(i));

            //Event handler setting day label in schedule
            cellButton[column][row].setOnAction((ActionEvent event) -> { //Select a cell button
                currentDay = Integer.parseInt(cellButton[column][row].getText());
                refreshSchedule(currentDay, currentMonth, currentYear);

                //render all cells other than currently selected
                for (int x = 0; x < 7; x++) {
                    for (int y = 0; y < 6; y++) {
                        //highlight future days
                        if (!cellButton[x][y].getText().equals("") && Integer.parseInt(cellButton[x][y].getText()) > realDay && currentMonth
                                == realMonth && currentYear >= realYear) { //Today
                            cellButton[x][y].setStyle("-fx-background-color: aquamarine; -fx-alignment: center;");
                        }
                        if (!cellButton[x][y].getText().equals("") && currentYear > realYear) {
                            cellButton[x][y].setStyle("-fx-background-color: aquamarine; -fx-alignment: center;");
                        }
                        //highlight future months in current year
                        if (!cellButton[x][y].getText().equals("") && currentMonth > realMonth && currentYear == realYear) {
                            cellButton[x][y].setStyle("-fx-background-color: aquamarine; -fx-alignment: center;");
                        }
                        //highlight today's cell
                        if (!cellButton[x][y].getText().equals("") && Integer.parseInt(cellButton[x][y].getText()) == realDay && currentMonth
                                == realMonth && currentYear == realYear) { //Today
                            cellButton[x][y].setStyle("-fx-background-color: lightsalmon; -fx-alignment: center;");
                        }
                    }
                }
                cellButton[column][row].setStyle("-fx-background-color: cyan; -fx-alignment: center;");
            });

            //highlight future days in month
            if (Integer.parseInt(cellButton[column][row].getText()) > realDay && currentMonth
                    == realMonth && currentYear >= realYear) { //Today
                cellButton[column][row].setStyle("-fx-background-color: aquamarine; -fx-alignment: center;");
            }
            //highlight all future years 
            if (currentYear > realYear) {
                cellButton[column][row].setStyle("-fx-background-color: aquamarine; -fx-alignment: center;");
            }
            //highlight future months in current year
            if (currentMonth > realMonth && currentYear == realYear) {
                cellButton[column][row].setStyle("-fx-background-color: aquamarine; -fx-alignment: center;");
            }
            //highlight today's cell
            if (Integer.parseInt(cellButton[column][row].getText()) == realDay && currentMonth
                    == realMonth && currentYear == realYear) { //Today
                cellButton[column][row].setStyle("-fx-background-color: lightsalmon; -fx-alignment: center;");
                currentDay = i; //initializes the schedule date label
            }
            
        }
    }

    public static void renderCalendar() {
        // make all of the Controls and Panes inside the grid fill their grid cell, 
        // align them in the center and give them a filled background.
        // you could also place each of them in their own centered StackPane with 
        // a styled background to achieve the same effect.
        for (Node n : calendarGrid.getChildren()) {
            if (n instanceof Control) {
                Control control = (Control) n;
                control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                control.setStyle("-fx-background-color: lightcyan; -fx-alignment: center;");
            }

            if (n instanceof Pane) {
                Pane pane = (Pane) n;
                pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pane.setStyle("-fx-background-color: darkturquoise; -fx-alignment: center;");
            }
        }
        // style the grid so that it has a background and gaps around the grid and between the 
        // grid cells so that the background will show through as grid lines.
        calendarGrid.setStyle("-fx-background-color: darkturquoise; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");
        // turn layout pixel snapping off on the grid so that grid lines will be an even width.
        calendarGrid.setSnapToPixel(false);

    }

    /**
     * Initialize the Schedule Box
     *
     * @return VBox
     */
    public static VBox initSchedule() {
        VBox scheduleBox = new VBox();
        HBox scheduleLabelBox = new HBox();

        scheduleLabel = new Label("Showing times for: ");
        scheduleDateLabel = new Label(currentDay + getDateSuffix(currentDay)
                + " " + months.get(currentMonth) + " " + currentYear);

        scheduleDateLabel.setTextFill(Color.web("#0076a3"));

        scheduleLabelBox.setPadding(new Insets(20, 10, 20, 0));

        scheduleBox.setPadding(new Insets(10, 10, 10, 10));
        ScrollPane scheduleScroll = new ScrollPane();
        TilePane scheduleTile = new TilePane();
        scheduleTile.setPrefColumns(4);

        //Create times 9-4.45pm, 15 min intervals
        for (int i = 0; i < 32; i++) {

            Button timeBtn = new Button("Put time here");
            timeBtn.setPrefSize(100, 50);

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 9);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.AM_PM, Calendar.AM);
            cal.add(Calendar.MINUTE, 15 * i);

            Date date = cal.getTime();

            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

            timeBtn.setText(timeFormat.format(date));
            scheduleTile.getChildren().add(timeBtn);
        }

        scheduleScroll.setPrefSize(420, 315);
        scheduleScroll.setContent(scheduleTile);

        scheduleLabelBox.getChildren().add(scheduleLabel);
        scheduleLabelBox.getChildren().add(scheduleDateLabel);
        scheduleBox.getChildren().add(scheduleLabelBox);
        scheduleBox.getChildren().add(scheduleScroll);

        return scheduleBox;
    }

    public static void refreshSchedule(int day, int month, int year) {
        scheduleDateLabel.setText(currentDay + getDateSuffix(currentDay)
                + " " + months.get(currentMonth));
    }

    public static String getDateSuffix(int day) {
        switch (day) {
            case 1:
            case 21:
            case 31:
                return ("st");

            case 2:
            case 22:
                return ("nd");

            case 3:
            case 23:
                return ("rd");

            default:
                return ("th");
        }
    }
}
