package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.CoupleExhibition;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObject() {
		
		String sql = "SELECT * from objects";

		List<ArtObject> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> getAnni() {
		String sql = "SELECT DISTINCT begin from exhibitions";

		List<Integer> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new Integer(res.getInt("begin")));
				}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Exhibition> findVertices(int anno, Map<Integer, Exhibition> exhibitions) {
		String sql = "SELECT e.exhibition_id AS id, begin, end, COUNT(object_id) AS numObjects "+
				"FROM exhibitions e, exhibition_objects eo "+
				"WHERE e.exhibition_id = eo.exhibition_id "+
				"AND e.`begin`>= ? "+
				"GROUP BY e.exhibition_id";

		List<Exhibition> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				exhibitions.put(res.getInt("id"), new Exhibition(res.getInt("id"),res.getInt("begin"),
						res.getInt("end"),res.getInt("numObjects")));
				result.add(exhibitions.get(res.getInt("id")));
			}
			

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<CoupleExhibition> getEdges(int anno, Map<Integer, Exhibition> exhibitions) {
		
		String sql = "SELECT e1.exhibition_id AS id1, e2.exhibition_id AS id2 FROM exhibitions e1, exhibitions e2 "+
				"WHERE e1.`begin`<e2.`begin` AND e1.`end`> e2.`begin` AND e1.exhibition_id!=e2.exhibition_id "+
				"AND e1.begin >= ? AND e2.`begin`>= ?";

		List<CoupleExhibition> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Exhibition e1 = exhibitions.get(res.getInt("id1"));
				Exhibition e2 = exhibitions.get(res.getInt("id2"));
				if(e1 != null && e2 != null){
				CoupleExhibition ce = new CoupleExhibition(e1, e2);
				result.add(ce);
				}
			}
			

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
}
