import Spammers.Fris;

public class Launcher {

	public static void main(String[] args)
	{
		// FriFolRatio ffR = new FriFolRatio();
		// ffR.getFriFolRatio("data/FriFolRatio.txt");
		Fris fris = new Fris();
		fris.getUsersWithTooManyFri("data/usersWith1500Fris.txt");

	}

}
