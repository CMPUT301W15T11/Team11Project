package save_and_load;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import android.content.Context;

import com.example.team11xtremexpensetracker.ClaimListController;
import com.example.team11xtremexpensetracker.ClaimsList;
import com.example.team11xtremexpensetracker.UserController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileManager {
	

	public ClaimsList loadFromFile(Context context) {
		Gson gson = new Gson();
		ClaimsList dataList = new ClaimsList();
		try {
			FileInputStream fis = context.getApplicationContext().openFileInput(UserController.getFILENAME());
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<ClaimsList>() {
			}.getType();
			dataList = gson.fromJson(in, typeOfT);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataList;
	}
	
	public void saveInFile(Context context) {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = context.getApplicationContext().openFileOutput(UserController.getFILENAME(), Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(ClaimListController.getClaimsList(), osw);
			osw.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
