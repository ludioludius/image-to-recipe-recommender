package dev.ayush.imagetoreciperecommender.services;
import com.clarifai.credentials.ClarifaiCallCredentials;
import com.clarifai.grpc.api.*;
import com.clarifai.grpc.api.status.StatusCode;
import com.google.protobuf.ByteString;
import dev.ayush.imagetoreciperecommender.model.DetectedObject;
import org.springframework.stereotype.Component;

import com.clarifai.channel.ClarifaiChannel;
import io.grpc.Channel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


// TODO: make it return List<DetectedObject>
@Component
public class ClarifaiClient {

    static final String MODEL_ID = "general-image-detection";
    static final String MODEL_VERSION_ID = "aa7f35c01e0642fda5cf400f543e7c40";
    static final String IMAGE_FILE_LOCATION = "/Users/ayushbharat/IdeaProjects/image-to-recipe-recommender/src/main/java/dev/ayush/imagetoreciperecommender/services/carrots_potatoes2.png";

    private String apiKey;

    // returns the data returned by a call the api with the given image
    public List<DetectedObject> ObjectsFromImage(String image) throws IOException {
        List<DetectedObject> detectedObjectList = new ArrayList<DetectedObject>();

        V2Grpc.V2BlockingStub stub = V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                .withCallCredentials(new ClarifaiCallCredentials("d6a40aee9fb047b7a96c3538ad21532b"));
        MultiOutputResponse response = stub.postModelOutputs(
                PostModelOutputsRequest.newBuilder()
                        .setModelId(MODEL_ID)
                        .addInputs(
                                Input.newBuilder().setData(
                                        Data.newBuilder().setImage(
                                                Image.newBuilder()
                                                        .setBase64(ByteString.copyFrom(Files.readAllBytes(
                                                                new File(IMAGE_FILE_LOCATION).toPath()
                                                        )))
                                        )
                                )
                        )
                        .build()
        );

        if (response.getStatus().getCode() != StatusCode.SUCCESS) {
            throw new RuntimeException("Post model outputs failed, status: " + response.getStatus());
        }

        List<Region> regions = response.getOutputs(0).getData().getRegionsList();

        for (Region region : regions) {
            // Accessing and rounding the bounding box values
            double topRow = region.getRegionInfo().getBoundingBox().getTopRow();
            double leftCol = region.getRegionInfo().getBoundingBox().getLeftCol();
            double bottomRow = region.getRegionInfo().getBoundingBox().getBottomRow();
            double rightCol = region.getRegionInfo().getBoundingBox().getRightCol();

            for (Concept concept : region.getData().getConceptsList()) {
                // Accessing and rounding the concept value
                String label = concept.getName();
                double value = concept.getValue();
                detectedObjectList();

                System.out.println(label + ": " + value + " BBox: " + topRow + ", " + leftCol + ", " + bottomRow + ", " + rightCol);
            }
        }
        return "returns processed ingredients from api call";
    }
}
