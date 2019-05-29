package controller;

import model.Category;
import repo.CategoryRepository;
import model.Task;
import repo.TaskRepository;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaskController implements Initializable {

    @FXML
    CategoryRepository categoryRepository;
    @FXML
    TaskRepository taskRepository;
    @FXML
    TableView<Task> taskTableView;
    @FXML
    TableColumn<Task, String> nameCol;
    @FXML
    TableColumn<Task, DatePicker> startDateCol;
    @FXML
    TableColumn<Task, Integer> remainingDaysCol;
    @FXML
    TableColumn<Task, Category> categoryCol;
    @FXML
    TableColumn<Task, String> statusCol;
    @FXML
    ComboBox<Category> categoryCMB;
    @FXML
    TextField nameField;
    @FXML
    TextField leftDayField;
    @FXML
    CheckBox FindCheckBox;
    @FXML
    Label messageLabel;
    @FXML
    Label rowCountLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeNumeric();
        setValue();
        fillTable();
        fillCategoryCMB();
        writeMessage("Welcome");
    }

    public void makeNumeric() {
        leftDayField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,5}(\\d{0,0})?")) {
                    leftDayField.setText(oldValue);
                }
            }
        });
    }

    public void setValue() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        remainingDaysCol.setCellValueFactory(new PropertyValueFactory<>("remainingDays"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void fillTable() {
        try {
            taskRepository = new TaskRepository();
            List<Task> task = taskRepository.findAll();
            taskTableView.getItems().setAll(task);
            rowCountLabel.setText(Integer.toString(task.size()) + " tasks");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearField() {
        FindCheckBox.setSelected(false);
        nameField.setText("");
        leftDayField.setText("");
        fillCategoryCMB();
        fillTable();
    }

    @FXML
    public void remove() {
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();//Опять же выбранный студент 
        Task task = new Task();
        task.setId(selectedTask.getId());//Опять же даем ему id 
        task.setStatus(selectedTask.getStatus());
        taskRepository.remove(task);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Task deleted successfully", ButtonType.OK);
        alert.show();
        writeMessage("Task deleted successfully");
        clearField();
        fillTable();
    }

    @FXML
    public void find() {
        taskRepository = new TaskRepository();
        Category category = new Category();
        if (FindCheckBox.isSelected()) {
            if (categoryCMB.getValue().getName().equalsIgnoreCase("ALL category")) {
                category.setName("");
            } else {
                category.setName(categoryCMB.getValue().getName());
            }
            Map<String, String> map = new TreeMap<>();
            map.put("name", nameField.getText());
            map.put("left", leftDayField.getText());
            map.put("category", category.getName());
            List<Task> tasks = taskRepository.find(map);
            taskTableView.getItems().setAll(tasks);

            if (tasks.size() == 0) {
                writeMessage("Nothing found");
                rowCountLabel.setText(tasks.size() + " tasks");
            } else {
                writeMessage(tasks.size() + " tasks was founded");
                rowCountLabel.setText(tasks.size() + " tasks");
            }
        }
    }

    @FXML
    public void refresh() {
        fillTable();
        clearField();
        fillCategoryCMB();
        writeMessage("Table rereshed");
    }

    @FXML
    public void save() {
        taskRepository = new TaskRepository();
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();//Нажимаем на табличку 
        boolean isFilled = checkTask();
        if (isFilled) {
            if (selectedTask == null) {//Если selectedStudent не имеется тоесть мы ничего не выбрали и поля заполнены то мы 
                Task task = new Task();//создаем объект 
                task.setName(nameField.getText().trim());//даем ему значение от полей 
                task.setRemainingDays(Integer.parseInt(leftDayField.getText().trim()));
                task.setCategory(categoryCMB.getValue());
                taskRepository.add(task);
                writeMessage("Task saved");
                clearField();
                fillTable();
            } else if (selectedTask != null) {//Сдесь все тоже самое 
                Task task = new Task();
                task.setId(selectedTask.getId());
                task.setName(nameField.getText().trim());
                task.setRemainingDays(Integer.parseInt(leftDayField.getText().trim()));
                task.setCategory(categoryCMB.getValue());
                taskRepository.update(task);//просто выполняется update 
                writeMessage("Task updated");
                fillTable();
                clearField();
            }
        } else {
            writeMessage("Please fill all the fields and choose category");
        }
    }

    public boolean checkTask() {
        boolean taskIsField = false;
        if (!nameField.getText().trim().isEmpty() && !leftDayField.getText().trim().isEmpty() && !categoryCMB.getValue().getName().equalsIgnoreCase("All category")) {
            taskIsField = true;
        } else {
            taskIsField = false;
        }
        return taskIsField;
    }

    public void writeMessage(String message) {
        messageLabel.setText(message);
    }

    public void fillCategoryCMB() {
        categoryRepository = new CategoryRepository();
        List<Category> categoryList = categoryRepository.findAll();
        Category category = new Category();
        category.setName("All category");
        categoryList.add(0, category);
        categoryCMB.getItems().setAll(categoryList);
        categoryCMB.getSelectionModel().selectFirst();
    }

    @FXML
    public void onTasktSelect() {
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();//Когда мы выбираем студента он у на с вон тут хранится selectedStudent
        if (selectedTask != null) {//Если selectedStudent не 0 то он заполняется 
            FindCheckBox.setSelected(false);
            leftDayField.setText(Integer.toString(selectedTask.getRemainingDays()));
            nameField.setText(selectedTask.getName());//именем выбранного студента 
            categoryCMB.setValue(selectedTask.getCategory());//У этого выбранного студента мы заберем целый объект и отправим в univerCmb
            writeMessage("Task selected");
        } else {
            clearField();
        }
    }

    @FXML
    public void addCategory() {
        categoryRepository = new CategoryRepository();
        List<Category> categories = categoryRepository.findAll();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Category");
        dialog.setHeaderText("Add Category");
        dialog.setContentText("Input category name");
        Optional<String> result = dialog.showAndWait();
        boolean unique = false;
        if (!result.get().trim().isEmpty()) {
            while (!unique) {
                for (Category c : categories) {
                    if (!c.getName().equalsIgnoreCase(result.get().trim())) {
                        unique = true;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "This category already exist", ButtonType.OK);
                        alert.showAndWait();
                        writeMessage("This category already exist");
                        result = dialog.showAndWait();
                        unique = false;
                        break;
                    }
                }
            }
            Category category = new Category();
            category.setName(result.get());
            categoryRepository.add(category);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, " Category add successfully ", ButtonType.OK);
            alert.show();
            writeMessage("Category add successfully");
            fillCategoryCMB();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, " The field must not be empty ", ButtonType.OK);
            alert.show();
            writeMessage("The field must not be empty");
        }
    }

    @FXML
    public void showAll() {
        fillTable();
        writeMessage("All tasks showed");
    }

    @FXML
    public void showDoned() {
        try {
            taskRepository = new TaskRepository();
            List<Task> task = taskRepository.findDoned();
            taskTableView.getItems().setAll(task);
            rowCountLabel.setText(task.size() + " tasks");
            writeMessage("Found " + task.size() + " done tasks");
        } catch (Exception e) {
            e.printStackTrace();
        }
        writeMessage("Doned tasks showed");
    }

    @FXML
    public void showNotDoned() {
        try {
            taskRepository = new TaskRepository();
            List<Task> task = taskRepository.findNotDoned();
            taskTableView.getItems().setAll(task);
            rowCountLabel.setText(task.size() + " tasks");
            writeMessage("Found " + task.size() + " not done tasks");
        } catch (Exception e) {
            e.printStackTrace();
        }
        taskRepository = new TaskRepository();

        writeMessage("Not doned tasks showed");
    }

    @FXML
    public void makeDone() {
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();//Опять же выбранный студент 
        Task task = new Task();
        task.setId(selectedTask.getId());//Опять же даем ему id 
        taskRepository.doneSelected(task);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This task successfully doned", ButtonType.OK);
        alert.show();
        writeMessage("This task successfully doned");
        clearField();
        fillTable();
    }

    @FXML
    public void makeNotDone() {
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();//Опять же выбранный студент 
        Task task = new Task();
        task.setId(selectedTask.getId());//Опять же даем ему id 
        taskRepository.notDoneSelected(task);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This task successfully not doned", ButtonType.OK);
        alert.show();
        writeMessage("This task successfully not doned");
        clearField();
        fillTable();
    }

    @FXML
    public void makeAllDone() {
        taskRepository.doneAll();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "All task successfully doned", ButtonType.OK);
        alert.show();
        writeMessage("All task successfully doned");
        clearField();
        fillTable();
    }
}
