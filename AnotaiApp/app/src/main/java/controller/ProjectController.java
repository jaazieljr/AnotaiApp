package controller;

import java.sql.Connection;
import java.sql.Date;
import model.Project;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionFactory;

/**
 *
 * @author jaazi
 */
public class ProjectController {

    public void save(Project project) {
        String sql = "INSET INTO projects "
                + "(name, "
                + "description, "
                + "createdAt, "
                + "updatedAt) VALUES(?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar o projeto"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void update(Project project) throws SQLException {
        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ? "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();

        } catch (RuntimeException ex) {
            throw new SQLException("Erro ao Atualizar o projeto"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void removeById(int projectId) throws SQLException {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, projectId);

            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar o projeto"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection);
        }

    }

    public List<Project> getAll() {

        String sql = "SELECT * FROM projects WHERE id = ?,";
        Connection connection = null;
        PreparedStatement statement= null;
        
        ResultSet resultSet = null;

        List<Project> projects = new ArrayList<Project>();
        
        
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            
            resultSet = statement.executeQuery();
            
            
            while(resultSet.next()){
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projects.add(project);
            }
        }catch (Exception ex){
            throw new RuntimeException ("Erro ao inserir tarefa"
                    + ex.getMessage(), ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return projects;

    }

}
