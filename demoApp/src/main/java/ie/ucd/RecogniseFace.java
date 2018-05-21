package ie.ucd;

import java.io.File;
import java.net.URI;

import com.vaadin.ui.Image;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import elemental.json.JsonString;

 class RecogniseFace{
     // Replace <Subscription Key> with your valid subscription key.
     private static final String subscriptionKey = "9c83418445c747a58eccbc1614d8dea7";

     // NOTE: You must use the same region in your REST call as you used to
     // obtain your subscription keys. For example, if you obtained your
     // subscription keys from westus, replace "westcentralus" in the URL
     // below with "westus".
     // Free trial subscription keys are generated in the westcentralus region. If you
     // use a free trial subscription key, you shouldn't need to change this region.
     private static final String uriBase =
         "https://northeurope.api.cognitive.microsoft.com/face/v1.0/detect";
 
    // private static final String imageWithFaces =
      //  "{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}";

       private static final String imageWithFaces = 
       "{\"url\":\"http://com.vaadin.server.FileResource@2d137fbe\"}";
    
 
     private static final String faceAttributes =
         "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise";
 
     public static String recogniseFaces(String pathImage)
     {
         HttpClient httpclient = new DefaultHttpClient();
        //String imageTry = "{\"url\":\"+pathImage
         try
         {
             URIBuilder builder = new URIBuilder(uriBase);
 
             // Request parameters. All of them are optional.
             builder.setParameter("returnFaceId", "true");
             builder.setParameter("returnFaceLandmarks", "false");
             builder.setParameter("returnFaceAttributes", faceAttributes);
 
             // Prepare the URI for the REST API call.
             URI uri = builder.build();
             HttpPost request = new HttpPost(uri);
 
             // Request headers.
             request.setHeader("Content-Type", "application/json");
             request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
 
             // Request body.
             StringEntity reqEntity = new StringEntity(imageWithFaces);
             //StringEntity reqEntity = new StringEntity(pathImage);
             request.setEntity(reqEntity);
             
 
             // Execute the REST API call and get the response entity.
             HttpResponse response = httpclient.execute(request);
             HttpEntity entity = response.getEntity();
            /*
             if (entity != null)
             {
                 // Format and display the JSON response.
                 System.out.println("REST Response:\n");
 
                 String jsonString = EntityUtils.toString(entity).trim();
                 
                 if (jsonString.charAt(0) == '[') {
                     JSONArray jsonArray = new JSONArray(jsonString);
                     System.out.println(jsonArray.toString(2));
                 }
                 else if (jsonString.charAt(0) == '{') {
                     JSONObject jsonObject = new JSONObject(jsonString);
                     System.out.println(jsonObject.toString(2));
                 } else {
                     System.out.println(jsonString);
                 }
                 
             }
             */
            String jsonString = EntityUtils.toString(entity).trim();
            return jsonString;
           }
         catch (Exception e)
         {
             // Display error message.
             System.out.println(e.getMessage());
                return e.getMessage();
         }
     }
 }