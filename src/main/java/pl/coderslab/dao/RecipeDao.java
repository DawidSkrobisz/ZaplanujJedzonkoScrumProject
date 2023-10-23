package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, ingredients, description, created, updated, preparationTime, preparation) VALUES (?,?,?,?,?,?,?);";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe where id = ?;";
    private static final String FIND_ALL_RECIPE_QUERY = "SELECT * FROM recipe;";
    private static final String READ_RECIPE_QUERY = "SELECT * from recipe where id = ?;";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ? , ingrediens = ?, description = ?, created = ?, updated = ?, preparationTime = ?, preparation = ? WHERE	id = ?;";

    public Recipe read(Integer recipeId) {
        Recipe recipe = new Recipe();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPE_QUERY)
        ) {
            statement.setInt(1, recipeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recipe.setId(resultSet.getInt("id"));
                    recipe.setName(resultSet.getString("name"));
                    recipe.setIngredients(resultSet.getString("ingredients"));
                    recipe.setDescription(resultSet.getString("description"));
                    recipe.setCreated(resultSet.getDate("created"));
                    recipe.setUpdated(resultSet.getDate("updated"));
                    recipe.setPreparation_time(resultSet.getInt("preparationTime"));
                    recipe.setPreparation(resultSet.getString("preparation"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipe;
    }

    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPE_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Recipe recipetoAdd = new Recipe();
                recipetoAdd.setId(resultSet.getInt("id"));
                recipetoAdd.setName(resultSet.getString("name"));
                recipetoAdd.setIngredients(resultSet.getString("ingredients"));
                recipetoAdd.setDescription(resultSet.getString("description"));
                recipetoAdd.setCreated(resultSet.getDate("created"));
                recipetoAdd.setUpdated(resultSet.getDate("updated"));
                recipetoAdd.setPreparation_time(resultSet.getInt("preparationTime"));
                recipetoAdd.setPreparation(resultSet.getString("preparation"));
                recipeList.add(recipetoAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setDate(3, (Date) plan.getCreated());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(1, plan.getId());
            statement.setString(2, plan.getName());
            statement.setString(3, plan.getDescription());
            statement.setDate(4, (Date) plan.getCreated());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
