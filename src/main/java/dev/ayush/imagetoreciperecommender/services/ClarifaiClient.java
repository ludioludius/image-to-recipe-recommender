package dev.ayush.imagetoreciperecommender.services;
import com.clarifai.credentials.ClarifaiCallCredentials;
import com.clarifai.grpc.api.*;
import com.clarifai.grpc.api.status.StatusCode;
import com.google.protobuf.ByteString;
import dev.ayush.imagetoreciperecommender.model.DetectedObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.clarifai.channel.ClarifaiChannel;
import io.grpc.Channel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles communication with the clarifai API's object detection model
 */
@Component
public class ClarifaiClient {

    static final String MODEL_ID = "general-image-detection";
    @Value("${api.key}")
    private String apiKey;

    // returns the data returned by a call the api with the given image
    public List<DetectedObject> ObjectsFromImage(byte[] imageFile) throws IOException {
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
                                                        .setBase64(ByteString.copyFrom(imageFile))
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
                String detectedObject = concept.getName();
                double probability = concept.getValue();
                detectedObjectList.add(new DetectedObject(probability, detectedObject));
                System.out.println(detectedObject + ": " + probability + " BBox: " + topRow + ", " + leftCol + ", " + bottomRow + ", " + rightCol);
            }
        }
        return detectedObjectList;
    }
}
