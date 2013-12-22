package MongoDB.StatusAgainstFriends;

import java.util.List;

public class StaAndFriends
{

	public StaAndFriends()
	{
		// TODO Auto-generated constructor stub
	}

	private int Sta;
	private List<Integer> Friends;

	public int getSta()
	{
		return Sta;
	}

	public void setSta(int sta)
	{
		Sta = sta;
	}

	public List<Integer> getFriends()
	{
		return Friends;
	}

	public void setFriends(List<Integer> friends)
	{
		Friends = friends;
	}

}
