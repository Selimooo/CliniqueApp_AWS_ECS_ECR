# build angular
############
##  docker build -t clinic-frontend .   ##
##  docker run -p 4200:80 --name clinic-frontend-container clinic-frontend  ##
############
FROM node:20 As build

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build

# nginx pour servir le front
FROM nginx:alpine

COPY --from=build /app/dist/clinic_frontend/browser/ /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf


EXPOSE 80