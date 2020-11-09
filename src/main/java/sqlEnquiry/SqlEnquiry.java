package sqlEnquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sqlObjects.GeneralTable;
import sqlObjects.WorkersAndID;


public class SqlEnquiry {

	static Connection connection;
	List<GeneralTable> general_table_list;
	List<WorkersAndID> list_of_people_in_FAT_since;
	List<WorkersAndID> current_state_of_pople_in_FAT;
	
	public SqlEnquiry()
	{
		general_table_list= new ArrayList<GeneralTable>();
		list_of_people_in_FAT_since = new ArrayList<WorkersAndID>();
		current_state_of_pople_in_FAT= new ArrayList<WorkersAndID>();
	}
	
	public List<WorkersAndID> getCurrentStateOfPeopleInFat() 
	{
		connection=  DBconnector.Connection2DB.dbConnector();


		
		current_state_of_pople_in_FAT.clear();
		
			String sql = "select a.id, a.id_karty ,MAX(a.akcja) as akcja ,MAX(a.`data`), cnsn.nazwisko_imie  as nazwisko_imie from fat.access a  \r\n" + 
					"left join fat.cards_name_surname_nrhacosoft cnsn \r\n" + 
					"on a.id_karty =cnsn.id_karty \r\n" + 
					"where DATE(a.`data`) like curdate() \r\n" + 
					"and a.id_karty  <> '0'\r\n" + 
					"group by a.id_karty \r\n" + 
					"order by a.`data` desc ";
			
			try
			{
				PreparedStatement stmnt = connection.prepareStatement(sql);
				ResultSet rs = stmnt.executeQuery();
				
				while(rs.next())
				{
					String akcja = rs.getString("akcja");
					String id_karty = rs.getString("id_karty");
					String naziwsko_imie = rs.getString("nazwisko_imie");
					
					if(akcja.equals("wejscie"))
					{
						WorkersAndID obj = new WorkersAndID(naziwsko_imie,Integer.parseInt(id_karty));
						
						current_state_of_pople_in_FAT.add(obj);
					}
					
				}
				stmnt.close();
				rs.close();
				
			}
			catch(Exception e)
			{
			}
		
		
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		
		return current_state_of_pople_in_FAT;
	}
	
	public List<WorkersAndID> peopleInFATlist()
	{
		connection=  DBconnector.Connection2DB.dbConnector();

		
		list_of_people_in_FAT_since.clear();
		
		String sql = "select fat.cards_name_surname_nrhacosoft.nazwisko_imie as record, fat.cards_name_surname_nrhacosoft.id_karty \r\n" + 
				"									from fat.access\r\n" + 
				"									left join fat.cards_name_surname_nrhacosoft\r\n" + 
				"									on fat.access.id_karty = fat.cards_name_surname_nrhacosoft.id_karty\r\n" + 
				"									where fat.cards_name_surname_nrhacosoft.nazwisko_imie like '%%'\r\n" + 
				"									and fat.access.`data` between  DATE_ADD(now(), INTERVAL -30 day) and now()\r\n" + 
				"									and fat.cards_name_surname_nrhacosoft.nazwisko_imie not like '%GOSC%'\r\n" + 
				"									group by fat.access.id_karty \r\n" + 
				"									order by fat.cards_name_surname_nrhacosoft.nazwisko_imie asc";
		
		try
		{
		PreparedStatement stmnt = connection.prepareStatement(sql);
		ResultSet rs = stmnt.executeQuery();
		
		while(rs.next())
		{
			String naz_imie = rs.getString(1);
			int id_karty  = rs.getInt(2);
			
			WorkersAndID obj = new WorkersAndID(naz_imie, id_karty);
			
			
			list_of_people_in_FAT_since.add(obj);
		}
			stmnt.close();
			rs.close();
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return list_of_people_in_FAT_since;
	}
	
	
	public List<GeneralTable> mainEnguiry(int limit)
	{
		
		connection=  DBconnector.Connection2DB.dbConnector();

		general_table_list.clear();
		
		String sql  = "select  fat.access.id, fat.access.id_karty, fat.access.akcja, fat.access.`data`,\r\n" + 
				"fat.cards_name_surname_nrhacosoft.nazwisko_imie, fat.cards_name_surname_nrhacosoft.HacoSoftnumber,\r\n" + 
				"fat.cards_name_surname_nrhacosoft.stanowisko, fat.cards_name_surname_nrhacosoft.firma\r\n" + 
				"from fat.access\r\n" + 
				"left join fat.cards_name_surname_nrhacosoft\r\n" + 
				"on fat.access.id_karty = fat.cards_name_surname_nrhacosoft.id_karty\r\n" + 
				"where fat.cards_name_surname_nrhacosoft.nazwisko_imie like '%%'\r\n" + 
				"order by fat.access.`data` desc limit ?";
		
		  try
	        {
	            PreparedStatement takeDate = connection.prepareStatement(sql);
	            takeDate.setInt(1, limit);
	            ResultSet r = takeDate.executeQuery();

	            while(r.next())
	            {
	            	String id = r.getString(1); 
	                String id_karty = r.getString(2);   
	                String akcja = r.getString(3);
	                String  data = r.getString(4);
	                String  nazwisko_imie = r.getString(5);
	                String hacosoftnumber = r.getString(6);
	                String stanowisko = r.getString(7);
	                String firma = r.getString(8);
	                
	                GeneralTable obj = new GeneralTable(id, id_karty, akcja, data, nazwisko_imie, hacosoftnumber, stanowisko, firma);
	                
	                general_table_list.add(obj);
	            }      
	            takeDate.close();
	            r.close();
	        }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		
	return general_table_list;	
	}
	
	
}
