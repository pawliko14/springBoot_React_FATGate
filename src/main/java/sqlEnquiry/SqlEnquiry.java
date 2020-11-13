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
	List<GeneralTable> list_of_people_in_FAT_since;
	List<WorkersAndID> current_state_of_pople_in_FAT;
	
	public SqlEnquiry()
	{
		general_table_list= new ArrayList<>();
		list_of_people_in_FAT_since = new ArrayList<>();
		current_state_of_pople_in_FAT= new ArrayList<>();
	}
	
	public List<WorkersAndID> getCurrentStateOfPeopleInFat() 
	{
		connection=  DBconnector.Connection2DB.dbConnector();


		
		current_state_of_pople_in_FAT.clear();
		
			String sql = "select a.id, a.id_karty ,a.akcja as akcja ,a.`data` as data_wej, cnsn.nazwisko_imie  as nazwisko_imie from fat.access a \n" +
					"inner join (\n" +
					"select id_karty , max(`data` ) as maxDate\n" +
					"from fat.access \n" +
					"group by id_karty \n" +
					") b\n" +
					"on a.id_karty  = b.id_karty and a.`data`  = b.maxDate\n" +
					"left join fat.cards_name_surname_nrhacosoft cnsn \n" +
					"on a.id_karty =cnsn.id_karty  \n" +
					"where DATE(a.`data`) like curdate() \n" +
					"and cnsn.nazwisko_imie  is not null \n"+
					"group by a.id_karty \n" +
					"order by a.`data`  desc\t\t";
			
			try
			{
				PreparedStatement stmnt = connection.prepareStatement(sql);
				ResultSet rs = stmnt.executeQuery();
				
				while(rs.next())
				{
					String akcja = rs.getString("akcja");
					String id_karty = rs.getString("id_karty");
					String nazwisko_imie = rs.getString("nazwisko_imie");
					String data_wej = rs.getString("data_wej");
					
					if(akcja.equals("wejscie"))
					{
						WorkersAndID obj = new WorkersAndID(nazwisko_imie,id_karty,data_wej);
						
						current_state_of_pople_in_FAT.add(obj);
					}
					
				}
				stmnt.close();
				rs.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println("number of indexes in current state of people: " + current_state_of_pople_in_FAT.size());

		
		return current_state_of_pople_in_FAT;
	}
	
	public List<GeneralTable> peopleInFATlist()
	{

		List<WorkersAndID> temporaryList_currentstate = getCurrentStateOfPeopleInFat();


		connection=  DBconnector.Connection2DB.dbConnector();
		list_of_people_in_FAT_since.clear();



		String sql = "select fat.cards_name_surname_nrhacosoft.nazwisko_imie as record, fat.cards_name_surname_nrhacosoft.id_karty, fat.cards_name_surname_nrhacosoft.firma as firma, fat.cards_name_surname_nrhacosoft.stanowisko as stanowisko\r\n" +
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
			String id_karty = rs.getString(2);
			String firma = rs.getString(3);
			String stanowisko = rs.getString(4);
			GeneralTable obj = new GeneralTable(naz_imie, id_karty,firma,stanowisko);
			
			
			list_of_people_in_FAT_since.add(obj);
		}
			stmnt.close();
			rs.close();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		// check if worker is in fat currently, if not set it 'red' or 'absence'

		return check_if_worker_is_currently_in_job(temporaryList_currentstate,list_of_people_in_FAT_since);
	}

	private List<GeneralTable> check_if_worker_is_currently_in_job(List<WorkersAndID> temporaryList_currentstate, List<GeneralTable> list_of_people_in_fat_since) {

	if(temporaryList_currentstate.size() == 0) {
		list_of_people_in_fat_since.forEach((l -> l.setAkcja("nieobecny")));
	}
	else {
		for(int i = 0 ; i < temporaryList_currentstate.size();i++) {
			for(int j = 0 ;j < list_of_people_in_fat_since.size();j++) {
					if (temporaryList_currentstate.get(i).getWorkerName().equals(list_of_people_in_fat_since.get(j).getNazwisko_imie())) {
						list_of_people_in_fat_since.get(j).setAkcja("jest w firmie");
				}
			}
		}





	}

		System.out.println("list_of_people_in_fat_since length " + list_of_people_in_fat_since.size());

		System.out.println("list_of_people_in_fat_since temporaryList_currentstate " + temporaryList_currentstate.size());

		return list_of_people_in_fat_since;
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
		  	e.printStackTrace();
		  }
		
	return general_table_list;	
	}
	
	
}
