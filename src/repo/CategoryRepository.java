/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

import repo.Repository;
import database.DataManager;
import model.Category;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public class CategoryRepository extends DataManager implements Repository<Category> {

    @Override
    public void add(Category category) {
        String query = "insert into category (name) values(?)";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getName());
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Category o) {
    }

    @Override
    public boolean remove(Category o) {
        return false;
    }

    @Override
    public List<Category> find(Map<String, String> map) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        String query = "select * from category";
        List<Category> categorys = new ArrayList<>();
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categorys.add(category);
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
        return categorys;
    }

}
