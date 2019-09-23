import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ObjectDetection {

	public static void main(String[] args) throws Exception{

		OkHttpClient client = new OkHttpClient();

		//Replace with location of image on the local file system 
		File file = new File("/Users/rodalton/Downloads/red-dots/2 red dots.jpeg");
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), file);


		//Replace API_KEY with the API Key for your Watson VR service instance 
		String usernamePassword = "apikey:w7lM4t9gpyF-cWY33ygJFEKbjFHk8RKuNuTrkB84P6q6";
		String basicAuthPayload = "Basic " + Base64.getEncoder().encodeToString(usernamePassword.getBytes());

		//Replace with your collection id
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("features", "objects")
				.addFormDataPart("collection_ids", "3ccb1e0f-518c-4c8f-bbbd-dbb9785aa9d5")
				.addFormDataPart("images_file","2 red dots.jpeg",fileBody)
				.build();

		Request request = new Request.Builder()
				.header("Authorization", basicAuthPayload)
				.url("https://gateway.watsonplatform.net/visual-recognition/api/v4/analyze?version=2019-02-11")
				.post(requestBody)
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException(response.toString());

		System.out.println(response.body().string());
	}

}
	