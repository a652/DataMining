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

public class FriFolRatio {
	private final String host = "10.108.192.165";
	private final String dbName = "datamining";
	private final String userName = "";
	private final String password = "";
	private final String collectionName = "UserData";

	private DBCollection dbColl;
	private FileWriter fileWriter;
	private double ratio = 0.0;
	private int count = 0;

	Logger logger = Logger.getLogger(FriFolRatio.class);

	/**
	 * @param outputFileName
	 *            此方法用于计算用户的关注数与其粉丝数的比值，并输出到文件里
	 */
	public void getFriFolRatio(String outputFileName)
	{
		long id = 0;
		double fri = 0;
		double fol = 0;
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
		while (cursor.hasNext())
		{
			BasicDBObject cur = (BasicDBObject) cursor.next();
			try
			{
				id = cur.getLong("ID");
				fri = cur.getDouble("Fri");
				fol = cur.getDouble("Fol");
			} catch (Exception e)
			{
				continue;
			}

			if (fol == 0.0)
			{
				ratio = 5000.0;
			} else
			{
				ratio = fri / fol;
			}

			String temp = String.valueOf(id) + " " + String.valueOf(ratio)
					+ "\n";

			try
			{
				fileWriter.write(temp);
			} catch (IOException e)
			{
				e.printStackTrace();
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
