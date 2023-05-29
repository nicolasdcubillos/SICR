### Etapa de compilación ###
FROM node:14.20.0-alpine as builder

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

### Etapa de producción ###
FROM nginx:1.21.0-alpine

COPY --from=builder /app/dist/sicr-web-publico /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
