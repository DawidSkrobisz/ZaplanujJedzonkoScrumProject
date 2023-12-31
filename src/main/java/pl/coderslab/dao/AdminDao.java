package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(firstName, lastName, email, password, superadmin, enable) VALUES (?,?,?,?,?,?);";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins where id = ?;";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE admin SET firstName = ? , lastName = ?, email = ?, password = ?, superadmin = ?, enable = ? WHERE	id = ?;";

    public Admin read(Integer adminId) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ADMIN_QUERY)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    admin.setId(resultSet.getInt("id"));
                    admin.setFirstName(resultSet.getString("firstName"));
                    admin.setLastName(resultSet.getString("lastName"));
                    admin.setEmail(resultSet.getString("email"));
                    admin.setPassword(resultSet.getString("password"));
                    admin.setSuperadmin(resultSet.getInt("superadmin"));
                    admin.setEnable(resultSet.getInt("enable"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    public List<Admin> findAll() {
        List<Admin> adminList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMINS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Admin admintoAdd = new Admin();
                admintoAdd.setId(resultSet.getInt("id"));
                admintoAdd.setFirstName(resultSet.getString("firstName"));
                admintoAdd.setLastName(resultSet.getString("lastName"));
                admintoAdd.setEmail(resultSet.getString("email"));
                admintoAdd.setPassword(resultSet.getString("password"));
                admintoAdd.setSuperadmin(resultSet.getInt("superadmin"));
                admintoAdd.setEnable(resultSet.getInt("enable"));
                adminList.add(admintoAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }

    public void create(Admin admin) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement insertStm = connection.prepareStatement(CREATE_ADMIN_QUERY);
            insertStm.setString(1, admin.getFirstName());
            insertStm.setString(2, admin.getLastName());
            insertStm.setString(3, admin.getEmail());
            insertStm.setString(4, BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
            insertStm.setInt(5, admin.getSuperadmin());
            insertStm.setInt(6, admin.getEnable());
            insertStm.executeUpdate();

                   } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void delete(Integer adminId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY)) {
            statement.setInt(1, adminId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Admin not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_QUERY)) {
            statement.setInt(1, admin.getId());
            statement.setString(2, admin.getFirstName());
            statement.setString(3, admin.getLastName());
            statement.setString(4, admin.getEmail());
            statement.setString(5, BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
            statement.setInt(6, admin.getSuperadmin());
            statement.setInt(7, admin.getEnable());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}