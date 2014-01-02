package Spammers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import MongoDB.getMongoDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class Fris {
	private final String host = "10.108.192.165";
	private final String dbName = "datamining";
	private final String userName = "";
	private final String password = "";
	private final String collectionName = "UserData";

	private DBCollection dbColl;
	private FileWriter fileWriter;
	private int count = 0;

	Logger logger = Logger.getLogger(FriFolRatio.class);

	/**
	 * @param outputFileName
	 *            此方法用于将数据库中关注数大于1500的用户筛选出来，输出的文件中每行的内容分别为：用户ID 用户关注数 用户名字
	 */
	public void getUsersWithTooManyFri(String outputFileName)
	{
		long id = 0;
		int fri = 0;
		String name = "";
		try
		{
			fileWriter = new FileWriter(new File(outputFileName));
			dbColl = getMongoDBCollection.getMongoDBColl(host, dbName,
					userName, password, collectionName);
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		DBCursor cursor = dbColl.find().addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		while (cursor.hasNext() & count < 9973170)
		{
			BasicDBObject cur = (BasicDBObject) cursor.next();
			try
			{
				id = cur.getLong("ID");
				fri = cur.getInt("Fri");
				name = cur.getString("Name");
			} catch (Exception e)
			{
				continue;
			}
			if (fri > 1500 && cur.get("Update") != null)
			{
				String temp = String.valueOf(id) + " " + String.valueOf(fri)
						+ " " + name + "\n";
				try
				{
					fileWriter.write(temp);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			count++;
			if (count % 10000 == 0)
			{
				logger.info(count);
			}
		}
		try
		{
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		cursor.close();
	}
}
