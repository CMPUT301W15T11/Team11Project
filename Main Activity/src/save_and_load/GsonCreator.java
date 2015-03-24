package save_and_load;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonCreator {

	public GsonCreator() {
		
	};

	public Gson getGson() {
		GsonBuilder builder = new GsonBuilder();
		
		return builder.create();
	}

}
