# Image to Recipe Recommender

Project Description
---

Do you often find yourself with leftover ingredients in your kitchen, unsure of what to make with them? This web application addresses that exact problem. By simply uploading a photo of your leftover ingredients, the app utilizes advanced object detection and recipe suggestion APIs to provide you with a list of potential recipes. It maximizes the use of ingredients you already have and minimizes the need for additional items, helping you make the most out of what’s readily available in your kitchen and minimize food waste.

---

Insert Demo Jiff here

---

API Integration and Functionality Overview
---

To develop an API that converts an uploaded image into a list of recipe suggestions, two key sub-problems need to be addressed:

- Detecting and Identifying Ingredients in an Uploaded Image

  To detect and identify ingredients in an uploaded image, I needed an object detection model that could accurately recognize food ingredients. After researching various options, I chose Clarifai’s 
  general object detection model. I integrated this endpoint into the system and refined the output to include only detected objects that were food ingredients with a probability label greater than      0.5. The frontend facilitates the image upload process by allowing users to easily take a photo if they're on a mobile device or upload an image file if they're on a laptop/desktop.

- Converting the List of Ingredients into Recipe Suggestions

  To convert the identified ingredients into recipe suggestions, I utilized Spoonacular’s "Get Recipes by Ingredients" endpoint. Retrieving complete recipe information required additional calls to 
  other endpoints within Spoonacular’s API. The final result is displayed within the recipe cards in the frontend UI, presenting the information in a way that is both useful and aesthetically 
  pleasing to the end user.

## Project setup

- If you use Intellij as your IDE, it will automatically install the dependencies in the pom.xml file as a part of the build process. Running tests should also be straighforward through an IDE. If your IDE does not have built in support for JUnit, maven or Java you will have to install them and look up commands to make them work. The github actions workflows provides steps for manually seting up the backend through command line scripts. To run the frontend you will have to cd into the recommender-frontend folder and run `npm install` then `npm start`.


## Deployement

This project is deployed as a multicontiner application setup using docker compose on an EC2 instance. The entry point into the applicaiton is an nginx proxy server that routes requests to the Spring backend or an nginx server that serves the frontend static build files generates as a part of the build process. The containers are deployed onto an AWS EC2 instance setup to run docker. Port 80 is used as an entry point into the applicaiton, other parts of the system (ex: Spring server) cannot be accessed unless through the proxy on port 80. The deployemnt process has been automated using github actions (see deployement yml file for more details)


---

  Insert Deployement Diagram
  
---
