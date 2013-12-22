package MongoDB;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.mongodb.Bytes;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class FollowerandFriends
{
	private String host = "10.108.192.165";
	private String dbName = "DataMining";
	private String username = "ittcdb";
	private String password = "ittc706706";
	private String collectionName = "UserInfo";
	Logger log = Logger.getLogger(FollowerandFriends.class.getName());

	public void FollFri(String pathname, int count) throws IOException
	{
		DBCollection userInfo = getMongoDBCollection.getMongoDBColl(host,
				dbName, username, password, collectionName);
		DBCursor cursor = userInfo.find().batchSize(50)
				.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		int Foll = 0, Fri = 0;
		String s = "";

		FileWriter fW = new FileWriter(new File(pathname));
		if (count == 0)
		{
			int num = 0;
			while (cursor.hasNext())
			{
				DBObject cur = cursor.next();
				Foll = (Integer) cur.get("Foll");
				Fri = (Integer) cur.get("Fri");
				s = Foll + " " + Fri + "\n";
				fW.append(s);
				num++;
				log.info(num);

			}
			fW.flush();
			fW.close();
		} else
		{
			int num = 0;
			while (cursor.hasNext() && num < count)
			{
				DBObject cur = cursor.next();
				Foll = (Integer) cur.get("Fol");
				Fri = (Integer) cur.get("Fri");
				s = Foll + " " + Fri + "\n";
				fW.append(s);
				num++;
				log.info(num);

			}
			fW.flush();
			fW.close();
		}

	}

}
