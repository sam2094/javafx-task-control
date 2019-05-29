package repo;

import repo.Repository;
import model.Category;
import database.DataManager;
import model.Task;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskRepository extends DataManager implements Repository<Task> {

    @Override
    public void add(Task task) {
        String query = "insert into task (name,remaining_days,category_id,start_date) "
                + "values (?,?,?,NOW())";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);//Весь этот код обрабатывает данный запрос в даннм случае query и дает мне тип preparedStatement
            preparedStatement.setString(1, task.getName());//Нам надо забрать имя из-за этого мы его ствим в коробку Стринг.А первый это означает то чтобы он подставлялся на первый вопросик все остальное идет по тому же принципу
            preparedStatement.setInt(2, task.getRemainingDays());
            preparedStatement.setInt(3, task.getCategory().getId());
            preparedStatement.execute();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    @Override
    public void update(Task task) {
        String query = "update task set"
                + " name = ? ,"
                + "remaining_days = ? ,"
                + "category_id = ? "
                + "where id = ?";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, task.getName());
            preparedStatement.setInt(2, task.getRemainingDays());
            preparedStatement.setInt(3, task.getCategory().getId());
            preparedStatement.setInt(4, task.getId());
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
        }

    }

    @Override
    public boolean remove(Task task) {
        String query = "update task set active = 0 where id = ?";//Когда мы удоляем мы якобы удаляем но понастоящему мы меняем ему статус на 0
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, task.getId());
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Task> findAll() {
        String query = "SELECT * FROM task left join category on task.category_id = category.id where active = 1";
        List<Task> tasks = new ArrayList<Task>();
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                Category category = new Category();
                task.setId(resultSet.getInt("task.id"));
                task.setName(resultSet.getString("task.name"));
                task.setStartDate(resultSet.getDate("task.start_date"));
                task.setRemainingDays(resultSet.getInt("task.remaining_days"));
                task.setStartDate(resultSet.getDate("task.start_date"));
                task.setStatus(resultSet.getString("task.status"));
                category.setId(resultSet.getInt("category.id"));
                category.setName(resultSet.getString("category.name"));
                task.setCategory(category);
                tasks.add(task);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tasks;
    }

    @Override
    public List<Task> find(Map<String, String> map) {
        ArrayList<Task> tasks = new ArrayList();
        String query = "select * from task left join category on task.category_id = category.id where(task.name like '%" + map.get("name") + "%' and task.remaining_days like '%" + map.get("left") + "%' and category.name like '%" + map.get("category") + "%') and task.active = 1";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                Category category = new Category();
                task.setId(resultSet.getInt("task.id"));
                task.setName(resultSet.getString("task.name"));
                task.setStartDate(resultSet.getDate("task.start_date"));
                task.setStatus(resultSet.getString("task.status"));
                task.setRemainingDays(resultSet.getInt("task.remaining_days"));
                category.setId(resultSet.getInt("category.id"));
                category.setName(resultSet.getString("category.name"));
                task.setCategory(category);
                tasks.add(task);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tasks;
    }

    public void doneAll() {
       String query = "update task set status = 'Done' where status = 'Not done' ";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
     public void doneSelected(Task task) {
       String query = "update task set status = 'Done' where id = ?";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, task.getId());
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
      public void notDoneSelected(Task task) {
       String query = "update task set status = 'Not done' where id = ?";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, task.getId());
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
         
    public List<Task> findDoned() {
        String query = "SELECT * FROM task left join category on task.category_id = category.id where status = 'Done' and active = 1";
        List<Task> tasks = new ArrayList<Task>();
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                Category category = new Category();
                task.setId(resultSet.getInt("task.id"));
                task.setName(resultSet.getString("task.name"));
                task.setStartDate(resultSet.getDate("task.start_date"));
                task.setRemainingDays(resultSet.getInt("task.remaining_days"));
                task.setStartDate(resultSet.getDate("task.start_date"));
                task.setStatus(resultSet.getString("task.status"));
                category.setId(resultSet.getInt("category.id"));
                category.setName(resultSet.getString("category.name"));
                task.setCategory(category);
                tasks.add(task);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tasks;
    }
     
        public List<Task> findNotDoned() {
        String query = "SELECT * FROM task left join category on task.category_id = category.id where status = 'Not done' and active = 1";
        List<Task> tasks = new ArrayList<Task>();
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                Category category = new Category();
                task.setId(resultSet.getInt("task.id"));
                task.setName(resultSet.getString("task.name"));
                task.setStartDate(resultSet.getDate("task.start_date"));
                task.setRemainingDays(resultSet.getInt("task.remaining_days"));
                task.setStartDate(resultSet.getDate("task.start_date"));
                task.setStatus(resultSet.getString("task.status"));
                category.setId(resultSet.getInt("category.id"));
                category.setName(resultSet.getString("category.name"));
                task.setCategory(category);
                tasks.add(task);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tasks;
    }
}