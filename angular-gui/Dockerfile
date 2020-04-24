FROM node:12-alpine as frontend-builder

COPY . /angular-ui/
WORKDIR /angular-ui/
RUN npm install && npm run build

FROM nginx:latest

COPY ./nginx.conf /etc/nginx/nginx.conf
RUN rm -rf /usr/share/nginx/html/*
COPY --from=frontend-builder /angular-ui/dist/angular-gui/ /usr/share/nginx/html/
EXPOSE 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]
