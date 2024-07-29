package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;
	private PreparedStatement st;
	private ResultSet rs;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement("Insert into department"
					+ "(Name)"
					+ "values"
					+ "(?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
			conn.commit();
			
		}catch(SQLException e) {
			try {
				conn.rollback();
				throw new DbException(e.getMessage());
			} catch (SQLException roll) {
				throw new DbException(roll.getMessage());
			}finally {
				DB.closeStatement(st);
			}
		}

	}

	@Override
	public void update(Department obj) {
		try {
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement("UPDATE Department set name = ? where id = ?");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
			
			conn.commit();
		}catch(SQLException e) {
			try {
				conn.rollback();
				throw new DbException(e.getMessage());
			}catch(SQLException roll) {
				throw new DbException(roll.getMessage());
			}
		}

	}

	@Override
	public void deleteById(Integer id) {
		try {
			conn.setAutoCommit(false);

			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			st.setInt(1, id);

			st.executeUpdate();

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException(e.getMessage());
			} catch (SQLException rollback) {
				throw new DbException(rollback.getMessage());
			}
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {
		try {
			st = conn.prepareStatement("SELECT * FROM DEPARTMENT " + "WHERE Id = ?");
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				return new Department(rs.getInt("Id"), rs.getString("Name"));
			}

			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {
		try {
			st = conn.prepareStatement("SELECT * FROM DEPARTMENT");

			rs = st.executeQuery();

			Map<Integer, Department> map = new HashMap<>();
			List<Department> departments = new ArrayList<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("Id"));

				if (dep == null) {
					dep = new Department(rs.getInt("Id"), rs.getString("Name"));
					map.put(dep.getId(), dep);
				}

				departments.add(dep);
			}

			return departments;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
