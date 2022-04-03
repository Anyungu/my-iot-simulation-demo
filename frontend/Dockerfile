#*** Make sure this and the production Dockerfile use the same node tag.***#
FROM node:16.13-alpine as build-stage
# Shared base image with dependencies installed
WORKDIR /app

COPY package*.json ./

RUN npm install

COPY ./ .

RUN npm run build


FROM nginx:1.21.3 as production-stage
# Minimal image running app and nginx

RUN mkdir /app

COPY --from=build-stage /app/dist /app

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 8080