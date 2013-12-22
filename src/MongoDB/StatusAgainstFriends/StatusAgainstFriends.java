package MongoDB.StatusAgainstFriends;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import MongoDB.getMongoDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class StatusAgainstFriends
{

	public StatusAgainstFriends()
	{
		// TODO Auto-generated constructor stub
	}

	private String host = "10.108.192.165";
	private String dbName = "datamining";
	private String username = "ittcdb";
	private String password = "ittc706706";
	private String collectionName = "UserData";
	private DBCollection userDataCollection;
	private DBCollection newCollection;
	Logger logger = Logger.getLogger(StatusAgainstFriends.class);
	private long id;

	/**
	 * @param numberOfFriends
	 * @param newCollectionName
	 */
	@SuppressWarnings("unchecked")
	public void getStatusAgainstFriends(String numberOfFriends,
			String newCollectionName)
	{
		try
		{
			this.userDataCollection = getMongoDBCollection.getMongoDBColl(host,
					dbName, username, password, collectionName);
			this.newCollection = getMongoDBCollection.getMongoDBColl(host,
					dbName, username, password, newCollectionName);
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Integer> degreeList = new ArrayList<Integer>();
		DBCursor cursor = userDataCollection.find();
		int count = 0;
		while (cursor.hasNext() && count < 5)
		{
			BasicDBObject cur = (BasicDBObject) cursor.next();
			int numbOfFriends = cur.getInt(numberOfFriends);
			setId(cur.getLong("ID"));
			if (degreeList.contains(numbOfFriends))
			{
				BasicDBObject query = new BasicDBObject(numberOfFriends,
						numbOfFriends);
				DBObject newCur = newCollection.findOne(query);
				List<Integer> staList = new ArrayList<Integer>();
				staList = (List<Integer>) newCur.get("Status");
				staList.add(cur.getInt("Sta"));
				BasicDBObject doc = new BasicDBObject();
				doc.put("Status", staList);
				BasicDBObject updateSetValue = new BasicDBObject("$set", doc);
				newCollection.update(query, updateSetValue, true, false);
			}
			else
			{
				degreeList.add(numbOfFriends);
				List<Integer> staList = new ArrayList<Integer>();
				staList.add(cur.getInt("Sta"));
				BasicDBObject doc = new BasicDBObject();
				doc.put(numberOfFriends, numbOfFriends);
				doc.put("Status", staList);
				newCollection.insert(doc);

			}
			count++;
			if (count % 10 == 0)
			{
				logger.info(count);
			}
		}
		cursor.close();

	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
}
