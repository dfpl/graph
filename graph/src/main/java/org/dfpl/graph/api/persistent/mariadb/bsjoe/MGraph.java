package org.dfpl.graph.api.persistent.mariadb.bsjoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import org.json.JSONObject;

import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class MGraph implements Graph {

	private Connection connection;
	private Statement statement;

	public MGraph(String id, String pw, String graphName) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", id, pw);
		statement = connection.createStatement();

		statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + graphName);
		statement.executeUpdate("USE " + graphName);
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS v (id VARCHAR(50), properties JSON)");
		statement.executeUpdate(
				"CREATE TABLE IF NOT EXISTS e (id VARCHAR(50), outV VARCHAR(50), inV VARCHAR(50), label VARCHAR(50), properties JSON)");
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public Vertex addVertex(String id) throws IllegalArgumentException {
		if (id.contains("|"))
			throw new IllegalArgumentException("id cannot contain '|'");
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM v WHERE id = \'" + id + "\';");
			if (!rs.next()) {
				statement.executeUpdate("INSERT INTO v VALUES (\'" + id + "\', null);");
			}
		} catch (Exception e) {
		}
		return new MVertex(this, id);
	}

	@Override
	public Vertex getVertex(String id) {
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM v WHERE id = \'" + id + "\';");
			if (rs.next()) {
				return new MVertex(this, id);
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public Collection<Vertex> getVertices() {
		java.util.ArrayList<Vertex> list = new java.util.ArrayList<Vertex>();
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM v;");
			while (rs.next()) {
				String tid = rs.getString(1);
				list.add(new MVertex(this, tid));
			}
		} catch (Exception e) {
		}
		return list;
	}
	
	@Override
	public Collection<Vertex> getVertices (String key, Object value) {
		java.util.ArrayList<Vertex> list = new java.util.ArrayList<Vertex>();
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM v;");
			while (rs.next()) {
				Object result = rs.getObject(2);
				JSONObject obj = new JSONObject(result.toString());
				Object prop = obj.get(key);
				if (prop.equals(value))
				{
					String tid = rs.getString(1);
					list.add(new MVertex(this, tid));
				}
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label)
			throws IllegalArgumentException, NullPointerException {
		if (label.contains("|")) {
			throw new IllegalArgumentException("label cannot contain '|'");
		}
		if (outVertex == null) {
			throw new NullPointerException("outVertex cannot be null");
		}

		if (inVertex == null) {
			throw new NullPointerException("inVertex cannot be null");
		}
		try {
			String outV = outVertex.getId();
			String inV = inVertex.getId();
			String id = outV + "|" + label + "|" + inV;
			ResultSet rs = statement.executeQuery("SELECT * FROM e WHERE id = \'" + id + "\';");
			if(!rs.next())
			{
				statement.executeUpdate("INSERT INTO e VALUES ( \'" + id + "\' , \'" + outV + "\' , \'" + inV + "\' , \'" + label + "\' , null);");
			}
			return new MEdge(this, id, inVertex, outVertex, label);
		} catch (Exception e) {
		}
		return null;
	}
	

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge getEdge(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Edge> getEdges() {
		java.util.ArrayList<Edge> list = new java.util.ArrayList<Edge>();
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM e;");
			while (rs.next()) {
				String tid = rs.getString(1);
				String _toutV = rs.getString(2);
				String _tinV = rs.getString(3);
				String tlabel = rs.getString(4);
				
				Vertex toutV = getVertex(_toutV);
				Vertex tinV = getVertex(_tinV);
				
				list.add(new MEdge(this, tid, toutV, tinV, tlabel));
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		java.util.ArrayList<Edge> list = new java.util.ArrayList<Edge>();
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM e;");
			while (rs.next()) {
				Object result = rs.getObject(5);
				JSONObject obj = new JSONObject(result.toString());
				Object prop = obj.get(key);
				if (prop.equals(value))
				{
					String tid = rs.getString(1);
					String _toutV = rs.getString(2);
					String _tinV = rs.getString(3);
					String tlabel = rs.getString(4);
					
					Vertex toutV = getVertex(_toutV);
					Vertex tinV = getVertex(_tinV);
					
					list.add(new MEdge(this, tid, toutV, tinV, tlabel));
				}
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
	}
	/*
	https://mvnrepository.com/artifact/org.mariadb.jdbc/mariadb-java-client/3.1.3
	UPDATE v SET properties = '{ "k1" : "v1" }' WHERE id = 1;
	*/
}
