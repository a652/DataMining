package MongoDB.StatusAgainstFriends;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import MongoDB.getMongoDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

/**
 * @author John
 * 
 */
public class AbstractStatusAginFriends
{

	public AbstractStatusAginFriends()
	{
		// TODO Auto-generated constructor stub
	}

	private String host = "10.108.192.165";
	private String DBName = "datamining";
	private String username = "ittcdb";
	private String password = "ittc706706";

	// private String collectionName = "UserData";
	Logger logger = Logger.getLogger(AbstractStatusAginFriends.class);

	@SuppressWarnings("unchecked")
	public void abstractStatusAginFriendsToText(String collectionName,
			String fieldName, String fileName) throws IOException
	{
		FileWriter FW = new FileWriter(new File(fileName));
		DBCollection collection = null;
		try
		{
			collection = getMongoDBCollection.getMongoDBColl(host, DBName,
					username, password, collectionName);
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBCursor cursor = collection.find();
		int count = 0;
		while (cursor.hasNext())
		{
			List<Integer> StatusNumbers = new ArrayList<Integer>();
			int Fol = 0;
			BasicDBObject cur = (BasicDBObject) cursor.next();
			try
			{
				Fol = cur.getInt(fieldName);
				StatusNumbers = (List<Integer>) cur.get("Status");
			}
			catch (Exception e)
			{
				continue;
			}

			float Average = 0;
			float Median = 0;
			Average = getAverage(StatusNumbers);
			Median = getMedian(StatusNumbers);

			FW.append(Fol + " " + Average + " " + Median + "\n");
			count++;
			logger.info(count);

		}
		FW.flush();
		FW.close();

	}

	/**
	 * @param list
	 * @return 返回平均值
	 */
	private static float getAverage(List<Integer> list)
	{
		float ave = 0;
		int sum = 0;
		int length = list.size();
		for (int i = 0; i < length; i++)
		{
			sum += list.get(i);
		}
		ave = (float) sum / length;
		return ave;
	}

	/**
	 * @param list
	 * @return 返回中位数
	 */
	private static float getMedian(List<Integer> list)
	{
		float median = 0;
		int length = list.size();

		Collections.sort(list);
		if (length % 2 == 0)
		{
			median = (float) (list.get(length / 2 - 1) + list.get(length / 2)) / 2;
		}
		else
		{
			median = list.get((length + 1) / 2 - 1);
		}
		return median;
	}

	// 用于测试以上的方法
	// public static void main(String[] args)
	// {
	// List<Integer> list = new ArrayList<>();
	// list.add(2);
	// list.add(5);
	// list.add(1);
	// list.add(3);
	// list.add(10);
	// float a = getAverage(list);
	// float m = getMedian(list);
	// for (int i = 0; i < list.size(); i++)
	// {
	//
	// System.out.println(list.get(i));
	// }
	// System.out.println(a + " " + m);
	// }

}
