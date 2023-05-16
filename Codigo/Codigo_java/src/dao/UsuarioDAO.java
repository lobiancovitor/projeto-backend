package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Usuario;

public class UsuarioDAO {
    
    public boolean create(Usuario usuario) throws SQLException{
        CriaConexao criaConexao = new CriaConexao();
        Connection connection = criaConexao.recuperarConexao();

        String sql = "INSERT INTO usuario(nome, email, senha, cargo, setor, telefone) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = (PreparedStatement) connection.prepareStatement(sql)) {
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getCargo());
            pstm.setString(5, usuario.getSetor());
            pstm.setString(6, usuario.getTelefone());

            connection.close();
            return pstm.execute();
        }
    }

    public boolean delete(Usuario usuario) throws SQLException {
        CriaConexao criaConexao = new CriaConexao();
        Connection connection = criaConexao.recuperarConexao();

        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement pstm = (PreparedStatement) connection.prepareStatement(sql)) {
            pstm.setInt(1, usuario.getId());
            pstm.execute();
            if (pstm.execute() == false) {
                System.out.println("Usuário não encontrado.");
            } else {
                System.out.println("Usuário excluído com sucesso.");
            }
            connection.close();

            return pstm.execute();  
        }
    }

    public Usuario getById(int id) throws SQLException {
        CriaConexao criaConexao = new CriaConexao();
        Connection connection = criaConexao.recuperarConexao();
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (PreparedStatement pstm = (PreparedStatement) connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            ResultSet rst = pstm.executeQuery();
            Usuario usuario = null;
            if (rst.next()) {
                usuario = new Usuario(rst.getInt("id"), rst.getString("nome"), rst.getString("email"), rst.getString("senha"), rst.getString("cargo"), rst.getString("setor"), rst.getString("telefone"));
            }

            return usuario;
        } 
    }

    public ArrayList<Usuario> retriveAll() throws SQLException {
        CriaConexao criaConexao = new CriaConexao();
        Connection connection = criaConexao.recuperarConexao();
        Statement stm = connection.createStatement();
        String sql = "SELECT * FROM usuario";

        stm.execute(sql);
        ResultSet rst = stm.getResultSet();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        while (rst.next()){
            int id = rst.getInt("id");
            String nome = rst.getString("nome");
            String email = rst.getString("email");
            String senha = rst.getString("senha");
            String cargo = rst.getString("cargo");
            String setor = rst.getString("setor");
            String telefone = rst.getString("telefone");
            

            Usuario u = new Usuario(id, nome, email, senha, cargo, setor, telefone);
            usuarios.add(u);
        }
        if (!rst.next()) {
            System.out.println("Não existem dados para exibição.");
        }
        connection.close();

        return usuarios;
    }

    public boolean update(Usuario usuario) throws SQLException {
        CriaConexao criaConexao = new CriaConexao();
        Connection connection = criaConexao.recuperarConexao();

        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, cargo = ?, setor = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement pstm = (PreparedStatement) connection.prepareStatement(sql)) {
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getCargo());
            pstm.setString(5, usuario.getSetor());
            pstm.setString(6, usuario.getTelefone());
            pstm.setInt(7, usuario.getId());
            pstm.execute();
            System.out.println("Usuário atualizado.");

            connection.close();

            return pstm.execute();  
        }
    }
}
