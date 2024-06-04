FROM node:20 AS build

WORKDIR /app

COPY frontend/recommender-frontend/package*.json ./
RUN npm install

COPY frontend/recommender-frontend .
RUN npm run build

FROM nginx
EXPOSE 3000

COPY docker/frontend/nginx/default.conf /etc/nginx/conf.d/default.conf
# Copy the built files from the build stage
COPY --from=build /app/build /usr/share/nginx/html

# Expose port 80 to the outside world
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]