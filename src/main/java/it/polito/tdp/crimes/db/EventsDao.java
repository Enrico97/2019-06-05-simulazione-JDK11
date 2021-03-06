package it.polito.tdp.crimes.db;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Vertici> vertici(int anno){
		String sql = "SELECT distinct avg(geo_lon), avg(geo_lat), district_id FROM events WHERE YEAR(events.reported_date)=? group by district_id order by district_id";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			List<Vertici> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Vertici(res.getDouble("avg(geo_lon)"), res.getDouble("avg(geo_lat)"), res.getInt("district_id")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Map<Integer, Integer> criminalita(int anno) {
			String sql = "select district_id as id, count(distinct incident_id) as tot from events where year(reported_date)=? group by district_id";
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				st.setInt(1, anno);
				Map<Integer, Integer> list = new HashMap<>() ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
						list.put(res.getInt("id"), res.getInt("tot"));
					} catch (Throwable t) {
						t.printStackTrace();
						System.out.println(res.getInt("id"));
					}
				}
				
				conn.close();
				return list ;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		}
}
