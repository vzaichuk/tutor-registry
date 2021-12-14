FROM nginx
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY ./dist /usr/share/nginx/html
EXPOSE ${CLIENT_PORT}
