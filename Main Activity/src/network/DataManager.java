package network;

import java.util.ArrayList;

import com.example.team11xtremexpensetracker.ExpenseClaim;

public interface DataManager {

	public ArrayList<ExpenseClaim> searchClaim(String searchString, String field);
	public ExpenseClaim getClaim(int id);
	public void addClaim(ExpenseClaim claim);
	public void deleteClaim(int id);
	
}
